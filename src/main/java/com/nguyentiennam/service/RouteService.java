package com.nguyentiennam.service;


import com.nguyentiennam.model.Route;
import com.nguyentiennam.repository.RouteRepository;
import com.nguyentiennam.util.DataUtil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RouteService {
    private List<Route> routes;

    private final RouteRepository routeRepository = new RouteRepository();

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public void init() {
        this.setRoutes(routeRepository.getAll());
    }

    public void showAll() {
        this.routes.forEach(System.out::println);
    }

    public void createNew() {
        System.out.println("Xin mời nhập số tuyến xe mới muốn thêm: ");
        int routeNumber = -1;
        do {
            try {
                routeNumber = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Số xe muốn thêm cần nhập là một số nguyên, vui lòng nhập lại: ");
                continue;
            }
            if (routeNumber > 0) {
                break;
            }
            System.out.print("Số xe muốn thêm phải là số dương, vui lòng nhập lại: ");
        } while (true);

        List<Route> newRoutes = new ArrayList<>();
        for (int i = 0; i < routeNumber; i++) {
            Route route = inputInfo();
            this.routes.add(route);
            newRoutes.add(route);
        }

        this.routeRepository.saveAll(newRoutes);
    }

    private Route inputInfo() {
        Route route = new Route();
        System.out.println("Nhập khoảng cách của tuyến xe: ");
        float distance = -1;
        do {
            try {
                distance = new Scanner(System.in).nextFloat();
            } catch (InputMismatchException ex) {
                System.out.print("Khoảng cách phải là một số, vui lòng nhập lại: ");
                continue;
            }
            if (distance > 0) {
                break;
            }
            System.out.print("Khoảng cách phải là một số dương, vui lòng nhập lại: ");
        } while (true);
        route.setDistance(distance);

        System.out.println("Nhập số điểm dừng của tuyến xe: ");
        float numberBusStop = -1;
        do {
            try {
                numberBusStop = new Scanner(System.in).nextFloat();
            } catch (InputMismatchException ex) {
                System.out.print("Số điểm dừng phải là một số, vui lòng nhập lại: ");
                continue;
            }
            if (numberBusStop > 0) {
                break;
            }
            System.out.print("Số điểm dừng phải là một số dương, vui lòng nhập lại: ");
        } while (true);
        route.setNumberBusStop(numberBusStop);
        return route;
    }

    public boolean isEmptyBusLine() {
        return DataUtil.isEmptyCollection(this.routes);
    }

    public Route findById(int routeId) {
        return routeRepository.findById(routeId);
    }
}
