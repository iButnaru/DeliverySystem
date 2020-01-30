package Services;

import Connection.MyConnection;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class PackageService {

    Logger LOGGER = Logger.getLogger(PackageService.class.getName());

    /**
     * Takes input using Scanner.
     * Finds the customer in DB and adds new package in the table.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void addPackage() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection connection = MyConnection.getConnection();

        Scanner scanner = new Scanner(System.in);
        LOGGER.info("Please enter the email address of your account:");
        String email = scanner.nextLine();
        LOGGER.info("Please enter your password:");
        String password = scanner.nextLine();

        Statement stmt = connection.createStatement();

        String findId = "SELECT id FROM customer WHERE email = " + "'" + email + "'" + " AND " + " password = " + "'" + password + "'";
        ResultSet resultSet = stmt.executeQuery(findId);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();

        int customer_id = 0;
        if (!resultSet.isBeforeFirst() ) {
            LOGGER.info("The entered address could not be found. If you already have an account, please enter again you email address.");
        }

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                customer_id = resultSet.getInt(i);
            }

            LOGGER.info("Should your package be included in the 'Fragile' category? \n Yes/No");
            String isFragile = scanner.nextLine();
            Boolean fragile = false;
            if (isFragile.contains("y")) {
                fragile = true;
            }

            int delivery_type = 1;

            try {
                LOGGER.info("Please select the delivery type: \n Type: \n 1 for normal delivery - package arrives in 3-5 wrking days.\n 2 for fast delivery - package arrives in 1-2 workng days.");
                delivery_type = scanner.nextInt();
                if (delivery_type != 2 && delivery_type != 1) {
                    delivery_type = 1;
                }

            } catch (InputMismatchException e) {
                LOGGER.info("Please type a number.");
            }

            String status = "Not delivered.";
            String query = "insert into package (fragile, delivery_type, status, customer_id)" + "values(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            connection.prepareStatement(query);
            preparedStatement.setBoolean(1, fragile);
            preparedStatement.setInt(2, delivery_type);
            preparedStatement.setString(3, status);
            preparedStatement.setInt(4, customer_id);
            preparedStatement.executeUpdate();
            LOGGER.info("The package has been added!");
            preparedStatement.close();
        }
        stmt.close();
        resultSet.close();
        connection.close();
    }
}
