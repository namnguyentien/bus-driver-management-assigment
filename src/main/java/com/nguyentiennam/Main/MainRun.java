package com.nguyentiennam.Main;

import com.nguyentiennam.service.DriverBusManagementService;
import com.nguyentiennam.service.DriverService;
import com.nguyentiennam.service.RouteService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainRun {
    public static final DriverService driverService = new DriverService();
    public static final RouteService routeService = new RouteService();
    public static final DriverBusManagementService driverBusManagementService = new DriverBusManagementService();

    public static void main(String[] args) {
        init();
        menu();
    }

    private static void init() {
        driverService.init();
        routeService.init();
        driverBusManagementService.init();
    }

    private static void menu() {
        while (true) {
            showMenu();
            int functionChoice = functionChoice();
            switch (functionChoice) {
                case 1:
                    driverService.createNew();
                    break;
                case 2:
                    driverService.showAll();
                    break;
                case 3:
                    routeService.createNew();
                    break;
                case 4:
                    routeService.showAll();
                    break;
                case 5:
                    driverBusManagementService.createNew();
                    break;
                case 6:
                    driverBusManagementService.showAll();
                    break;
                case 8:
                    driverBusManagementService.showAll();
                    break;
                case 7:
                    driverBusManagementService.sort();
                    break;
                case 9:
                    return;
            }
        }
    }

    private static int functionChoice() {
        System.out.print("Xin mời nhập lựa chọn: ");
        int choice = -1;
        do {
            try {
                choice = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Giá trị cần nhập là một số nguyên, vui lòng nhập lại: ");
                continue;
            }
            if (choice >= 1 && choice <= 9) {
                return choice;
            }
            System.out.print("Giá trị lựa chọn không tồn tại, vui lòng nhập lại: ");
        } while (true);
    }

    private static void showMenu() {
        System.out.println("\n\n\n-------------PHẦN MỀM QUẢN LÝ PHÂN CÔNG LÁI XE BUÝT-------------");
        System.out.println("1. Nhập danh sách lái xe mới.");
        System.out.println("2. Hiển thị danh sách lái xe.");
        System.out.println("3. Nhập danh sách tuyến xe mới.");
        System.out.println("4. Hiển thị danh sách tuyến xe.");
        System.out.println("5. Lập bảng phân công lái xe");
        System.out.println("6. Hiển thị bảng phân công lái xe");
        System.out.println("7. Sắp xếp danh sách phân công");
        System.out.println("8. Lập bảng thống kê khoảng cách chạy xe trong ngày");
        System.out.println("9. Kết thúc chương trình");
    }


}
