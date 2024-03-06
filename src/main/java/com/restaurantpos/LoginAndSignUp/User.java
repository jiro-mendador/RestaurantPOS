/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurantpos.LoginAndSignUp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jiro Mendador
 */
public class User {
    public static String userFirstName = "Default";
    public static String userLastName = "User";
    
    public User() {
        userFirstName = "";
        userLastName = "";
    }
    
    public void setUserFname(String username) {
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT first_name FROM accounts.accounts_signed_up WHERE username = ?;");
            statement.setString(1,username);
            ResultSet result =  statement.executeQuery();
            while(result.next()) {
                this.userFirstName = result.getString("first_name");
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setUserLname(String username) {
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT last_name FROM accounts.accounts_signed_up WHERE username = ?;");
            statement.setString(1,username);
            ResultSet result =  statement.executeQuery();
            while(result.next()) {
                this.userLastName = result.getString("last_name");
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getUserFullName() {
        return userFirstName+" "+userLastName;
    }
    
    public static String getMainAdminAccount() {
        String fName = ""; 
        String lName = "";
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT first_name,last_name "
                    + "FROM accounts.accounts_signed_up WHERE account_id = 1;");
            ResultSet result =  statement.executeQuery();
            while(result.next()) {
                fName = result.getString(1);
                lName = result.getString(2);
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fName+" "+lName;
    }
    
}
