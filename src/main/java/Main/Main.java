package Main;

import Services.AdminService;
import Services.CustomerService;
import Services.PackageService;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        CustomerService customerService = new CustomerService();
        AdminService adminService = new AdminService();
        PackageService packageService = new PackageService();
        int option;
        int adminOption;
        try {
            do {
                Scanner scanner = new Scanner(System.in);
                LOGGER.info("Welcome to FastDelivery!");
                LOGGER.info("In order to continue..\n Press 1 to create an account. \n Press 2 to add a package. \n Press 3 to see your packages. \n Press 4 to delete an order.  \n Press 5 to delete your account. \n Press 6 if you are an admin. \n Press 7 to exit.");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        customerService.createAccount();
                        break;
                    case 2:
                        packageService.addPackage();
                        break;
                    case 3:
                        customerService.seeMyPackages();
                        break;
                    case 4:
                        customerService.deleteOrder();
                        break;
                    case 5:
                        customerService.deleteAccount();
                        break;
                    case 10: {
                        if (adminService.checkAdmin()) {
                            LOGGER.info("Welcome Admin! ");
                            LOGGER.info("Please enter 1 to update the status of a package. \n  Press 2 to see all customers. \n Press 3 to block a customer. \n Press 4 to see all blocked customers. \n Press 5 to delete an account. \n Press 6 to create a new employee account. \n Press 0 to exit.");
                            adminOption = scanner.nextInt();
                            do {
                                switch (adminOption) {
                                    case 1:
                                        adminService.updatePackageStatus();
                                        break;
                                    case 2:
                                        adminService.seeAllCustomers();
                                        break;
                                    case 3:
                                        adminService.blockCustomer();
                                        break;
                                    case 4:
                                        adminService.seeAllBlockedCustomers();
                                        break;
                                    case 5:
                                        adminService.deleteAccount();
                                        break;
                                    case 6:
                                        adminService.createEmployee();
                                        break;
                                }

                            } while (adminOption != 0);
                        }
                    }
                    break;
                    case 7:
                        option = 0;
                        break;
                }
            } while (option != 0);
        } catch (InputMismatchException e) {
            LOGGER.info("Please choose one of the options, inserting a number.");
        }
    }
}
