/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurantpos.CustomerAndOrder;

import com.restaurantpos.HomePOS.HomePOS;
import com.restaurantpos.LoginAndSignUp.DataBaseConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jiro Mendador
 */
public class CustomerAndOrder {

    String name, address, number, itemOrdered, itemQuantitiesOrdered, itemSizes, dateOrdered;
    double totalPrice, discount;

    public CustomerAndOrder() {
        name = "";
        address = "";
        number = "";
        itemOrdered = "";
        itemQuantitiesOrdered = "";
        itemSizes = "";
        dateOrdered = "";
        totalPrice = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setItemOrdered(String items) {
        this.itemOrdered = items;
    }

    public void setItemQuantities(String quantity) {
        this.itemQuantitiesOrdered = quantity;
    }
    
    public void setItemSizes(String sizes) {
        this.itemSizes = sizes;
    }

    public void setDate(String date) {
        this.dateOrdered = date;
    }

    public void setTotalPrice(double total) {
        this.totalPrice = total;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public String getItemOrdered() {
        return itemOrdered;
    }
    
    public String getItemSizes() {
        return itemSizes;
    }

    public String getItemQuantities() {
        return itemQuantitiesOrdered;
    }

    public Date getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = dateFormat.parse(dateOrdered);
        } catch (ParseException ex) {
            Logger.getLogger(CustomerAndOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    
    public double getDiscount() {
        return discount;
    }
    
    public void saveCustomer() {
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement saveCustomer = con.prepareStatement("INSERT INTO products.customers VALUE(default,?,?,?,?,?,?);");
            saveCustomer.setString(1,getName());
            saveCustomer.setString(2,getAddress());
            saveCustomer.setString(3,getNumber());
            saveCustomer.setDouble(4,getTotalPrice());
            saveCustomer.setDouble(5,getDiscount());
            saveCustomer.setDate(6,getDate());
            saveCustomer.executeUpdate();
            saveCustomer.close();
        } catch (Exception ex) {
            Logger.getLogger(HomePOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveOrder() {
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement saveOrder = con.prepareStatement("INSERT INTO products.orders VALUE(default,?,?,?,?);");
            saveOrder.setInt(1,getCustomerId());
            saveOrder.setString(2,getItemOrdered());
            saveOrder.setString(3,getItemQuantities());
            saveOrder.setString(4,getItemSizes());
            saveOrder.executeUpdate();
            saveOrder.close();
        } catch (Exception ex) {
            Logger.getLogger(HomePOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getCustomerId() {
        try {
            int id = 0;
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement getId = con.prepareStatement("SELECT customer_id FROM products.customers WHERE customer_name = ?;");
            getId.setString(1,getName());
            ResultSet rs = getId.executeQuery();
            while(rs.next()) {
                id = rs.getInt(1);
            }
            getId.close();
            return id;
        } catch (Exception ex) {
            Logger.getLogger(CustomerAndOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
