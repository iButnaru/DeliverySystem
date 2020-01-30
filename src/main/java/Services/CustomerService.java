package Services;

import Connection.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class CustomerService {

    Logger LOGGER = Logger.getLogger(CustomerService.class.getName());

    /**
     * Takes input using Scanner.
     * If customer is not found in the customer and blocked_customer tables, object will be inserted in DB.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void createAccount() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        try {
            Scanner scanner = new Scanner(System.in);
            LOGGER.info("Welcome to FastDelivery Limited! \n");
            LOGGER.info("Please enter your first name");
            String firstName = scanner.nextLine();
            LOGGER.info("Please enter your last name");
            String lastName = scanner.nextLine();
            LOGGER.info("Please enter your email address");
            String email = scanner.nextLine();
            LOGGER.info("Please enter a password");
            String password = scanner.nextLine();
            LOGGER.info("Please enter your city");
            String city = scanner.nextLine();
            LOGGER.info("Please enter your address where future packages might be delivered");
            String address = scanner.nextLine();
            LOGGER.info("Please enter your phone number");
            Long phone = scanner.nextLong();


            AdminService adminService = new AdminService();
            if (adminService.checkBlockedCustomer(email)) {
                LOGGER.info("The account could not be created. You are on the blocked customers list.");
                System.exit(0);
            }

            if (adminService.checkCustomer(email)) {
                LOGGER.info("There is already an account with this email address. Please try again.");
                System.exit(0);
            }

            Connection connection = null;
            connection = MyConnection.getConnection();
            String query = " insert into customer (firstname, lastname, email, password, city, address, phone) " + "values (?, ?, ?, ?, ?, ?, ?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, address);
            preparedStatement.setLong(7, phone);

            preparedStatement.execute();
            LOGGER.info("The account has been created!");
            preparedStatement.close();
            connection.close();
        } catch (InputMismatchException e) {
            LOGGER.info("Please enter the phone number wihtout letters.");
        }
    }

    /**
     * Takes input using Scanner.
     * If customer found in DB will show all data from package table.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void seeMyPackages() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();
        Scanner scanner = new Scanner(System.in);
        LOGGER.info("Please enter your email address:");
        String email = scanner.nextLine();
        LOGGER.info("Please enter your password:");
        String password = scanner.nextLine();
        Statement stmt = connection.createStatement();
        String findId = "select id from customer where email = " + "'" + email + "'" + " and " + " password = " + "'" + password + "'";
        ResultSet resultSet = stmt.executeQuery(findId);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        int customer_id = 0;
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                customer_id = resultSet.getInt(i);
            }
        }
        String query = "Select * from package where customer_id = " + customer_id;
        resultSet = stmt.executeQuery(query);
        ArrayList<Object> values = new ArrayList<Object>();

        if (!resultSet.isBeforeFirst()) {
            LOGGER.info("You do not have any package in our database. ");
        }
        while (resultSet.next()) {

            values.add("ID - " + resultSet.getInt("id") + " Fragile - " + resultSet.getBoolean("fragile") + " Delivery Type - " + resultSet.getInt("delivery_type") + " Status - " + resultSet.getString("status") + " Customer`s id - " + resultSet.getInt("customer_id"));
        }
        LOGGER.info("Packages: \n" + String.valueOf(values));
        resultSet.close();
        stmt.close();
        connection.close();
    }

    /**
     * Takes input using Scanner.
     * If customer found in DB, their account will be deleted.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void deleteAccount() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        LOGGER.info("Please enter your email: ");
        String email = scanner.nextLine();
        LOGGER.info("Please enter your password: ");
        String password = scanner.nextLine();

        Statement stmt = connection.createStatement();

        String findId = "select id from customer where email = " + "'" + email + "'" + " AND " + " password = " + "'" + password + "'";
        ResultSet resultSet = stmt.executeQuery(findId);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();

        int customer_id = 0;

        if (!resultSet.isBeforeFirst()) {
            LOGGER.info("The entered address could not be found.");
        }

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                customer_id = resultSet.getInt(i);
            }
        }
        String deleteOrders = "DELETE FROM package WHERE customer_id = " + customer_id;
        PreparedStatement preparedStatement = connection.prepareStatement(deleteOrders);
        preparedStatement.execute();
        String deleteCustomer = "DELETE FROM customer WHERE email = " + "'" + email + "'" + " AND " + "password = " + "'" + password + "'";
        preparedStatement = connection.prepareStatement(deleteCustomer);
        preparedStatement.execute();
        LOGGER.info("The account has been now deleted!");
        resultSet.close();
        connection.close();
    }

    /**
     * Takes input using Scanner.
     * If package is found, will be deleteD from DB.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void deleteOrder() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        LOGGER.info("Please write the id number of the package you want to delete: ");
        int id = scanner.nextInt();

        Statement statement = connection.createStatement();
        String checkId = "Select * from package where id = " + id;
        ResultSet resultSet = statement.executeQuery(checkId);

        if (!resultSet.isBeforeFirst()) {
            LOGGER.info("The entered id could not be found. Please try again.");
        } else {

            String deletePackage = "DELETE FROM package WHERE id = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(deletePackage);
            LOGGER.info("The package has been deleted!");
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        }
    }
}
