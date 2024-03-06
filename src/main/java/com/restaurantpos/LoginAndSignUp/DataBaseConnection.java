package com.restaurantpos.LoginAndSignUp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {

    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306";
            String rootUname = "root";
            String rootPw = "";
            Class.forName(driver);

            Connection con = DriverManager.getConnection(url, rootUname, rootPw);
            System.out.println("CONNECTION SUCCESS!");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void createUsersDB() {
        try {
            Connection con = getConnection();
            ArrayList<String> rpos_account = new ArrayList<>();
            rpos_account.add("CREATE DATABASE IF NOT EXISTS accounts;");
            rpos_account.add("USE accounts;");
            rpos_account.add("SET NAMES utf8;");
            rpos_account.add("SET character_set_client = utf8mb4;");
            rpos_account.add("CREATE TABLE IF NOT EXISTS accounts.accounts_signed_up (account_id INT NOT NULL AUTO_INCREMENT, username VARCHAR(45) NOT NULL, password VARCHAR(45) NOT NULL, first_name VARCHAR(45) NOT NULL, last_name VARCHAR(45) NOT NULL, user_level VARCHAR(45) NOT NULL, PRIMARY KEY (account_id));");
            for (int i = 0; i < rpos_account.size(); i++) {
                PreparedStatement statement = con.prepareStatement(rpos_account.get(i));
                statement.executeUpdate();
                statement.close();
            }
            if(!hasAdminAccount()) {
                createAdminAccount();
            }
        } catch (Exception ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createProductsDB() {
        try {
            Connection con = getConnection();
            ArrayList<String> products = new ArrayList<>();
            products.add("CREATE DATABASE IF NOT EXISTS products;");
            products.add("USE products;");
            products.add("SET NAMES utf8;");
            products.add("SET character_set_client = utf8mb4;");
            products.add("CREATE TABLE IF NOT EXISTS products.categories(category_id INT NOT NULL AUTO_INCREMENT, "
                    + "category_name VARCHAR(45) NOT NULL, "
                    + "PRIMARY KEY (category_id));");
            products.add("CREATE TABLE IF NOT EXISTS products.category_items(item_id INT NOT NULL AUTO_INCREMENT, "
                    + "category_id INT NOT NULL, "
                    + "item_name VARCHAR(45) NOT NULL, "
                    + "regular_price DOUBLE NOT NULL, "
                    + "medium_price DOUBLE, large_price DOUBLE, "
                    + "PRIMARY KEY (item_id),FOREIGN KEY (category_id) REFERENCES categories(category_id));");
            products.add("CREATE TABLE IF NOT EXISTS products.items_stocks(stock_id INT NOT NULL AUTO_INCREMENT, "
                    + "category_id INT NOT NULL, "
                    + "item_id INT NOT NULL, "
                    + "stocks_count INT NOT NULL, "
                    + "regular_deduction INT NOT NULL, "
                    + "medium_deduction INT, "
                    + "large_deduction INT, "
                    + "stocks_unit VARCHAR(45) NOT NULL, "
                    + "PRIMARY KEY (stock_id),FOREIGN KEY (category_id) REFERENCES categories(category_id),"
                    + "FOREIGN KEY (item_id) REFERENCES category_items(item_id));");
            products.add("CREATE TABLE IF NOT EXISTS products.customers(customer_id INT NOT NULL AUTO_INCREMENT, "
                    + "customer_name VARCHAR(150) NOT NULL, "
                    + "customer_address VARCHAR(150), "
                    + "customer_number VARCHAR(11), "
                    + "total_ordered_price DOUBLE NOT NULL, "
                    + "discount_added DOUBLE NOT NULL, "
                    + "date_ordered DATE NOT NULL,"
                    + "PRIMARY KEY (customer_id));");
            products.add("CREATE TABLE IF NOT EXISTS products.orders(order_id INT NOT NULL AUTO_INCREMENT, "
                    + "customer_id INT NOT NULL, "
                    + "item_ordered_name VARCHAR(50) NOT NULL,"
                    + "item_ordered_quantities VARCHAR(50) NOT NULL,"
                    + "item_ordered_sizes VARCHAR(50) NOT NULL, "
                    + "PRIMARY KEY (order_id), "
                    + "FOREIGN KEY (customer_id) REFERENCES products.customers(customer_id));");
            products.add("CREATE TABLE IF NOT EXISTS products.restaurant("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "restaurant_name VARCHAR(45) NOT NULL, "
                    + "PRIMARY KEY (id));");
            for (int i = 0; i < products.size(); i++) {
                PreparedStatement statement = con.prepareStatement(products.get(i));
                statement.executeUpdate();
                statement.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getRestaurantName() {
        String name = "";
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT restaurant_name FROM products.restaurant;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                name = rs.getString(1);
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        name = name.isBlank() ? "Restaurant's Name" : name;
        return name;
    }

    public void createAdminAccount() {
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO accounts.accounts_signed_up "
                    + "VALUE(default,'mainAdminUser', 'mainAdminPassword', 'Main', 'Admin', 'Manager');");
            statement.executeUpdate();
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean hasAdminAccount() {
        boolean hasAdmin = false;
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * "
                    + "FROM accounts.accounts_signed_up "
                    + "WHERE account_id = 1;");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                hasAdmin = true;
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasAdmin;
    }
}
