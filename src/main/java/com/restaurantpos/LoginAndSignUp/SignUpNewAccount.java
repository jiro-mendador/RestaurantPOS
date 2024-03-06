package com.restaurantpos.LoginAndSignUp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpNewAccount {

    DataBaseConnection con = new DataBaseConnection();
    Verify verify = new Verify();
    static String prevUname = "";
    static String prevFname = "";
    static String prevLname = "";

    public void setPreviousUsername(String un) {
        this.prevUname = un;
    }

    public void setPreviousFirstName(String fn) {
        this.prevFname = fn;
    }

    public void setPreviousLastName(String ln) {
        this.prevLname = ln;
    }

    public boolean saveAccount(String username, String password, String fName, String lName, String uLevel) throws Exception {
        if (checkUsername(username) == false) {
            Connection connect = con.getConnection();
            PreparedStatement statement = connect.prepareStatement("INSERT INTO accounts.accounts_signed_up(username,password,first_name,last_name,user_level) VALUES(?,?,?,?,?);");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fName);
            statement.setString(4, lName);
            statement.setString(5, uLevel);
            statement.executeUpdate();
            statement.close();
            return true;
        }
        return false;
    }

    public static boolean updateAccount(String username, String password, String fName,
            String lName, String uLevel, int id) throws Exception {
        if (checkUsername(username) == false) {
            Connection connect = DataBaseConnection.getConnection();
            PreparedStatement statement = connect.prepareStatement("UPDATE accounts.accounts_signed_up SET "
                    + "username = ?, password = ?,"
                    + "first_name = ?, last_name = ?, user_level = ? "
                    + "WHERE account_id = ?;");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fName);
            statement.setString(4, lName);
            statement.setString(5, uLevel);
            statement.setInt(6, id);
            statement.executeUpdate();
            statement.close();
            return true;
        }
        return false;
    }
    
    public void removeAccount(int id) {
        try {
            Connection con = DataBaseConnection.getConnection();
            String deleteAcc = "DELETE FROM accounts.accounts_signed_up WHERE account_id = ?;";
            PreparedStatement statement = con.prepareStatement(deleteAcc);
            statement.setInt(1,id);
            statement.executeUpdate();
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(SignUpNewAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean checkUsername(String username) throws Exception {
        Connection connect = DataBaseConnection.getConnection();
        PreparedStatement statement = connect.prepareStatement("SELECT * FROM accounts.accounts_signed_up WHERE username = ?;");
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();
        if (prevUname.equals(username)) {
            return false;
        } else {
            if (result.next()) {
                return true;
            }
        }
        statement.close();
        return false;
    }

    public static boolean checkFirstLastName(String fName, String lName) throws Exception {
        Connection connect = DataBaseConnection.getConnection();
        PreparedStatement statement = connect.prepareStatement("SELECT * FROM accounts.accounts_signed_up WHERE first_name = ? AND last_name = ?;");
        statement.setString(1, fName);
        statement.setString(2, lName);
        ResultSet result = statement.executeQuery();
        if (prevFname.equals(fName) && prevLname.equals(lName)) {
            return false;
        } else {
            if (result.next()) {
                return true;
            }
        }
        statement.close();
        return false;
    }

    public static int checkAdminAccountCount() throws Exception {
        Connection connect = DataBaseConnection.getConnection();
        PreparedStatement statement = connect.prepareStatement("SELECT COUNT(*) FROM accounts.accounts_signed_up WHERE user_level = 'Manager';");
        ResultSet result = statement.executeQuery();

        int adminCount = 0;
        while (result.next()) {
            adminCount = result.getInt("COUNT(*)");
        }
        statement.close();
        return adminCount;
    }
}
