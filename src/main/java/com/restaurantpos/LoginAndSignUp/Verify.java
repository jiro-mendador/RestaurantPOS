package com.restaurantpos.LoginAndSignUp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Verify {
    DataBaseConnection con = new DataBaseConnection();

    public boolean verify(String usernm, String passwrd) throws Exception {
        Connection connect = con.getConnection();
        PreparedStatement statement = connect.prepareStatement("SELECT * FROM accounts.accounts_signed_up WHERE username = ? AND password = ?;");
        statement.setString(1, usernm);
        statement.setString(2, passwrd);
        ResultSet result = statement.executeQuery();
        if(result.next()) {
            return true;
        }
        statement.close();
        return false;
    }
    
    public boolean isAnAdmin(String fname, String lname) throws Exception {
        Connection connect = con.getConnection();
        PreparedStatement statement = connect.prepareStatement("SELECT user_level FROM accounts.accounts_signed_up WHERE first_name = ? AND last_name = ?;");
        statement.setString(1, fname);
        statement.setString(2, lname);
        ResultSet result = statement.executeQuery();
        if(result.next()) {
           if(result.getString(1).equals("Manager")) {
               return true;
           }
        }
        statement.close();
        return false;
    }
}
