package com.nguyentiennam.service;

import com.nguyentiennam.Main.MainRun;
import com.nguyentiennam.dto.DriverBusManagementDto;
import com.nguyentiennam.dto.DriverBusManagementTempDto;
import com.nguyentiennam.model.Driver;
import com.nguyentiennam.model.DriverBusManagement;
import com.nguyentiennam.model.Route;
import com.nguyentiennam.repository.DriverBusManagementRepository;
import com.nguyentiennam.util.DataUtil;

import java.util.*;
import java.util.stream.Collectors;

public class DriverBusManagementService implements DataInitializing {

    private final DriverBusManagementRepository driverBusManagementRepository = new DriverBusManagementRepository();
    private List<DriverBusManagementDto> driverBusManagementDtos;
    public List<DriverBusManagementDto> getDriverBusManagementDtos() {
        return driverBusManagementDtos;
    }

    public void setDriverBusManagementDtos(List<DriverBusManagementDto> driverBusManagementDtos) {
        this.driverBusManagementDtos = driverBusManagementDtos;
    }

    @Override
    public void init() {
        List<DriverBusManagement> driverBusManagements = driverBusManagementRepository.getAll();
        this.setDriverBusManagementDtos(toDto(driverBusManagements));
    }

    private List<DriverBusManagementDto> toDto(List<DriverBusManagement> driverBusManagements) {
        if (DataUtil.isEmptyCollection(driverBusManagements)) {
            return Collections.emptyList();
        }

        List<DriverBusManagementTempDto> driverBusManagementTempDtos = new ArrayList<>();

        driverBusManagements.forEach(driverBusManagement -> {
            Long driverId = driverBusManagement.getDriverId();
            Driver driver = MainRun.driverService.findById(Math.toIntExact(driverId));
            Long routeId = driverBusManagement.getRouteId();
            Route route = MainRun.routeService.findById(Math.toIntExact(routeId));
            Integer roundNumber = driverBusManagement.getRoundNumber();
            driverBusManagementTempDtos.add(new DriverBusManagementTempDto(driver, route, roundNumber));
        });

        Map<Driver, Map<Route, Integer>> tempMap = driverBusManagementTempDtos
                .stream()
                .collect(
                        Collectors.groupingBy(
                                DriverBusManagementTempDto::getDriver,
                                Collectors.toMap(DriverBusManagementTempDto::getRoute, DriverBusManagementTempDto::getRound)
                        )
                );

        final List<DriverBusManagementDto> result = new ArrayList<>();
        tempMap.forEach((key, value) -> {
            DriverBusManagementDto driverBusManagementDto = new DriverBusManagementDto(key, value);
            driverBusManagementDto.setTotalDistance();
            result.add(driverBusManagementDto);
        });
        return result;
    }

    public List<DriverBusManagement> toEntity(List<DriverBusManagementDto> driverBusManagementDtos) {
        final List<DriverBusManagement> driverBusManagements = new ArrayList<>();
        driverBusManagementDtos.forEach(management -> {
            Driver driver = management.getDriver();
            management.getAssignedBuses().forEach((key, value) -> {
                DriverBusManagement temp = new DriverBusManagement();
                temp.setDriverId(driver.getId());
                temp.setRouteId(key.getId());
                temp.setRoundNumber(value);
                driverBusManagements.add(temp);
            });
        });
        return driverBusManagements;
    }

    public void showAll() {
        this.driverBusManagementDtos.forEach(System.out::println);
    }

    public void createNew() {
        if (MainRun.driverService.isEmptyDriver() || MainRun.routeService.isEmptyBusLine()) {
            System.out.println("Ch??a c?? th??ng tin t??i x??? ho???c tuy???n xe, vui l??ng nh???p t??i x??? ho???c tuy???n xe tr?????c.");
            return;
        }
        System.out.print("Xin m???i nh???p s??? t??i x??? mu???n ph??n c??ng l??i xe: ");
        int driverNumber = -1;
        do {
            try {
                driverNumber = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("S??? t??i x??? c???n nh???p l?? m???t s??? nguy??n, vui l??ng nh???p l???i: ");
                continue;
            }
            if (driverNumber > 0) {
                break;
            }
            System.out.print("S??? t??i x??? ph???i l?? s??? d????ng, vui l??ng nh???p l???i: ");
        } while (true);

        List<DriverBusManagementDto> driverBusManagementDtos = new ArrayList<>();

        for (int i = 0; i < driverNumber; i++) {
            System.out.println("Nh???p th??ng tin cho t??i x??? th??? " + (i + 1) + ": ");
            Driver driver = inputDriver();
            System.out.println("L???p b???ng danh s??ch tuy???n xe l??i trong ng??y c???a l??i xe n??y: ");
            Map<Route, Integer> routeMap = createRoute();
            DriverBusManagementDto driverBusManagementDto = new DriverBusManagementDto(driver, routeMap);
            driverBusManagementDto.setTotalDistance();
            driverBusManagementDtos.add(driverBusManagementDto);
        }
        driverBusManagementRepository.saveAll(toEntity(driverBusManagementDtos));
    }

    private Map<Route, Integer> createRoute() {
        System.out.print("Nh???p s??? l?????ng tuy???n m?? l??i xe n??y mu???n l??i: ");
        int routeNumber = -1;
        do {
            try {
                routeNumber = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("S??? l?????ng tuy???n c???n nh???p l?? m???t s??? nguy??n c?? 5 ch??? s???, vui l??ng nh???p l???i: ");
                continue;
            }
            if (routeNumber > 0) {
                break;
            }
            System.out.print("S??? l?????ng tuy???n ph???i l?? s??? d????ng, vui l??ng nh???p l???i: ");
        } while (true);
        int totalRound = 0;
        Map<Route, Integer> routeMap = new HashMap<>();
        for (int j = 0; j < routeNumber; j++) {
            System.out.println("Nh???p m?? tuy???n xe th??? " + (j + 1) + " m?? t??i x??? n??y mu???n l??i: ");
            Route route;
            do {
                int routeId = -1;
                do {
                    try {
                        routeId = new Scanner(System.in).nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.print("M?? tuy???n c???n nh???p l?? m???t s??? nguy??n c?? 3 ch??? s???, vui l??ng nh???p l???i: ");
                        continue;
                    }
                    if (routeId > 0) {
                        break;
                    }
                    System.out.print("M?? tuy???n ph???i l?? s??? d????ng, vui l??ng nh???p l???i: ");
                } while (true);
                route = MainRun.routeService.findById(routeId);
                if (!DataUtil.isEmptyObject(route)) {
                    break;
                }
                System.out.println("Kh??ng t??m th???y tuy???n xe c?? m?? " + routeId + ", vui l??ng nh???p l???i: ");
            } while (true);
            System.out.print("Nh???p s??? l?????t m?? t??i x??? n??y mu???n l??i: ");
            int busRound = -1;
            do {
                try {
                    busRound = new Scanner(System.in).nextInt();
                } catch (InputMismatchException ex) {
                    System.out.print("S??? l?????t c???n nh???p l?? m???t s??? nguy??n, vui l??ng nh???p l???i: ");
                    continue;
                }
                if (busRound > 0) {
                    break;
                }
                System.out.print("S??? l?????t ph???i l?? s??? d????ng, vui l??ng nh???p l???i: ");
            } while (true);
            totalRound += busRound;
            if (totalRound > 15) {
                System.out.println("T??i x??? kh??ng ???????c l??i qu?? 15 l?????t 1 ng??y, d???ng ph??n c??ng t???i ????y.");
                break;
            }
            routeMap.put(route, busRound);
        }
        return routeMap;
    }

    private Driver inputDriver() {
        Driver driver;
        System.out.println("Nh???p m?? t??i x???");
        do {
            int driverId = -1;
            do {
                try {
                    driverId = new Scanner(System.in).nextInt();
                } catch (InputMismatchException ex) {
                    System.out.print("M?? t??i x??? c???n nh???p l?? m???t s??? nguy??n c?? 5 ch??? s???, vui l??ng nh???p l???i: ");
                    continue;
                }
                if (driverId > 0) {
                    break;
                }
                System.out.print("M?? t??i x??? ph???i l?? s??? d????ng, vui l??ng nh???p l???i: ");
            } while (true);

            driver = MainRun.driverService.findById(driverId);
            if (!DataUtil.isEmptyObject(driver)) {
                break;
            }
            System.out.println("Kh??ng t??m th???y t??i x??? c?? m?? " + driverId + ", vui l??ng nh???p l???i: ");
        } while (true);
        return driver;
    }

    public void sort() {
        if (MainRun.driverService.isEmptyDriver() || MainRun.routeService.isEmptyBusLine()) {
            System.out.println("Ch??a c?? th??ng tin t??i x??? ho???c tuy???n xe, vui l??ng nh???p t??i x??? ho???c tuy???n xe tr?????c.");
            return;
        }
        System.out.println("S???p x???p danh s??ch ph??n c??ng l??i xe theo: ");
        System.out.println(" 1. H??? t??n l??i xe");
        System.out.println(" 2. S??? tuy???n ?????m nh???n trong ng??y (gi???m d???n)");
        System.out.print("Vui l??ng nh???p l???a ch???n: ");
        int choice = -1;
        do {
            try {
                choice = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Gi?? tr??? c???n nh???p l?? m???t s??? nguy??n, vui l??ng nh???p l???i: ");
                continue;
            }
            if (choice == 1 || choice == 2) {
                break;
            }
            System.out.print("Gi?? tr??? l???a ch???n kh??ng t???n t???i, vui l??ng nh???p l???i: ");
        } while (true);

        switch (choice) {
            case 1:
                sortByDriverName();
                break;
            case 2:
                sortByBusLineNumber();
                break;
        }
    }

    private void sortByBusLineNumber() {
        this.driverBusManagementDtos.sort(Comparator.comparing(DriverBusManagementDto::getTotalDistance).reversed());
        this.showAll();
    }

    private void sortByDriverName() {
        this.driverBusManagementDtos.sort(Comparator.comparing(o -> o.getDriver().getName()));
        this.showAll();
    }

}