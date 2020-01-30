package Services;


/**
 * @author Ionut Butnaru
 * version 1.0
 * @see Entities.Employee
 */


import Connection.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class AdminService {

    Logger LOGGER = Logger.getLogger(AdminService.class.getName());

    /**
     * Takes input using Scanner.
     * Accesses the DB, finds the user, is inserting a copy in a new table and deletes the one from the initial table.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void blockCustomer() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Scanner scanner = new Scanner(System.in);

        LOGGER.info("Please enter the email address of the person your want to block.");
        String email = scanner.nextLine();

        Connection connection = MyConnection.getConnection();

        String findEmail = "Select * from customer where email = " + "'" + email + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(findEmail);
        if (resultSet.next()) {
            String addToBlock = "Insert into blocked_customer select * from customer where email = " + "'" + email + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(addToBlock);
            preparedStatement.executeUpdate();
            String deleteCustomer = "Delete from customer where email = " + "'" + email + "'";
            PreparedStatement pstmt = connection.prepareStatement(deleteCustomer);
            pstmt.executeUpdate();
        } else {
            LOGGER.info("The email could not be found.");
        }
    }

    /**
     * Takes input using Scanner.
     * Checks if the taken input matches with the String 'admin'.
     * @return true/false
     */

    public boolean checkAdmin() {
        Scanner scanner = new Scanner(System.in);
        LOGGER.info("Please enter your email address.");
        String email = scanner.nextLine();
        LOGGER.info("Please enter your password.");
        String password = scanner.nextLine();
        if (email.contentEquals("admin") && password.contentEquals("admin")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * Connects with DB and tries to find the given String in the table.
     * @param email String
     * @return true/false
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public boolean checkBlockedCustomer(String email) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();
        Statement statement = connection.createStatement();
        String checkCustomer = "select * from blocked_customer where email = " + "'" + email + "'";
        ResultSet resultSet = statement.executeQuery(checkCustomer);
        if (!resultSet.next()) {
            return false;
        } else return true;
    }

    /**
     * Connects with DB and tries to find the given String in the table.
     * @param email String
     * @return true/false
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public boolean checkCustomer(String email) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();
        Statement statement = connection.createStatement();
        String checkCustomer = "select * from customer where email = " + "'" + email + "'";
        ResultSet resultSet = statement.executeQuery(checkCustomer);
        if (!resultSet.next()) {
            return false;
        } else return true;
    }

    /**
     * Takes input using Scanner.
     * Updates the status of the package in the table.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void updatePackageStatus() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection connection = MyConnection.getConnection();

        Scanner scanner = new Scanner(System.in);
        LOGGER.info("Please write the new status of the package.");
        String status = scanner.nextLine();
        LOGGER.info("Please enter the id of the package you want to update status for.");
        int id = scanner.nextInt();
        String updateStatus = "update package set status= ?" + " where id= ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(updateStatus);
        preparedStatement.setString(1, status);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
        LOGGER.info("The status has been updated.");
        preparedStatement.close();
        connection.close();
    }

    /**
     * Shows all data from customer table.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void seeAllCustomers() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();

        Statement statement = connection.createStatement();
        String query = "Select * from customer";
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        ArrayList<Object> values = new ArrayList<Object>();
        while (resultSet.next()) {
            values.add(" ID - " + resultSet.getInt("id") + " First Name - " + resultSet.getString("firstname") + " Last Name - " + resultSet.getString("lastname") + " Email - " + resultSet.getString("email") + " City - " + resultSet.getString("city") + " Address - " + resultSet.getString("address") + " Phone - " + resultSet.getInt("phone") + "\n");
        }
        LOGGER.info("Customers: \n" + values);
    }

    /**
     * Shows all data from blocked_customer table.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void seeAllBlockedCustomers() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();

        Statement statement = connection.createStatement();
        String query = "Select * from blocked_customer";
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        ArrayList<Object> values = new ArrayList<Object>();
        while (resultSet.next()) {
            values.add(" ID - " + resultSet.getInt("id") + " First Name - " + resultSet.getString("firstname") + " Last Name - " + resultSet.getString("lastname") + " Email - " + resultSet.getString("email") + " City - " + resultSet.getString("city") + " Address - " + resultSet.getString("address") + " Phone - " + resultSet.getInt("phone") + "\n");
        }

        LOGGER.info("Blocked Customers: \n" + values);
    }

    /**
     * Shows all data from package table.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void seeAllPackages() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();

        Statement statement = connection.createStatement();
        String query = "Select * from package";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<Object> values = new ArrayList<Object>();
        while (resultSet.next()) {

            LOGGER.info("ID - " + resultSet.getInt("id") + " Fragile - " + resultSet.getBoolean("fragile") + " Delivery Type - " + resultSet.getInt("delivery_type") + " Status - " + resultSet.getString("status") + " Customer`s id - " + resultSet.getInt("customer_id"));
        }

        LOGGER.info("Packages: \n" + String.valueOf(values));
    }

    /**
     * Takes input using Scanner.
     * Deletes the data that is found in the DB.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void deleteAccount() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        LOGGER.info("Please enter your email of the account you want to delete:");
        String email = scanner.nextLine();

        Statement stmt = connection.createStatement();

        String findId = "select id from customer where email = " + "'" + email + "'";
        ResultSet resultSet = stmt.executeQuery(findId);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();

        int customer_id = 0;

        if (!resultSet.isBeforeFirst()) {
            LOGGER.info("The entered address could not be found.");
        } else {

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    customer_id = resultSet.getInt(i);
                }
            }

            String deleteOrders = "DELETE FROM package WHERE customer_id = " + customer_id;
            PreparedStatement preparedStatement = connection.prepareStatement(deleteOrders);
            preparedStatement.execute();
            String deleteCustomer = "DELETE FROM customer WHERE email = " + "'" + email + "'";
            preparedStatement = connection.prepareStatement(deleteCustomer);
            LOGGER.info("The account has been deleted");
            preparedStatement.execute();
            resultSet.close();
            connection.close();
        }
    }

    /**
     * Takes input using Scanner.
     * Inserts new employee in DB using the input.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void createEmployee() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();

        Scanner scanner = new Scanner(System.in);

        LOGGER.info("Please enter the employee`s first name:");
        String firstName = scanner.nextLine();
        LOGGER.info("Please enter the employee`s last name");
        String lastName = scanner.nextLine();
        LOGGER.info("Please enter the employee`s position:");
        String position = scanner.nextLine();
        LOGGER.info("Please enter the employee`s gender (M/F):");
        String gender = scanner.nextLine();

        if (gender.length() > 1) {
            LOGGER.info("Please enter for gender 'M' for male and 'F' for female");
            System.exit(0);
        }

        String insertQuery = "INSERT INTO employee (firstname, lastname, position, gender) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, position);
        preparedStatement.setString(4, gender);
        preparedStatement.execute();
        LOGGER.info("The employee has been added.");
        preparedStatement.close();
        connection.close();
    }
}
