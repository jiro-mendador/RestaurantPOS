package com.restaurantpos.HomePOS;

import com.restaurantpos.LoginAndSignUp.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryOperations {

    String category;
    DataBaseConnection connect = new DataBaseConnection();

    public CategoryOperations() {
        category = "";
    }

    public CategoryOperations(String categoryName) {
        this.category = categoryName;
    }

    public String getCategoryName(int id) {
        try {
            String category_name = "";
            Connection con = connect.getConnection();
            String addCategory = "SELECT * FROM products.categories WHERE category_id = ?;";
            PreparedStatement statement = con.prepareStatement(addCategory);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                category_name = rs.getString("category_name");
            }
            statement.close();
            return category_name;
        } catch (Exception ex) {
            Logger.getLogger(CategoryOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void addCategory() {
        try {
            Connection con = connect.getConnection();
            String addCategory = "INSERT INTO products.categories(category_name) VALUES(?);";
            PreparedStatement statement = con.prepareStatement(addCategory);
            statement.setString(1, category);
            statement.executeUpdate();
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateCategory(int id) {
        try {
            Connection con = connect.getConnection();
            String updateCategory = "UPDATE products.categories SET category_name = ? WHERE category_id = ?;";
            PreparedStatement statement = con.prepareStatement(updateCategory);
            statement.setString(1, category);
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteCategory(int id) {
        try {
            Connection con = connect.getConnection();
            String deleteCategory = "DELETE FROM products.items_stocks WHERE category_id = ?;";
            PreparedStatement statement = con.prepareStatement(deleteCategory);
            statement.setInt(1, id);
            statement.executeUpdate();
            deleteCategory = "DELETE FROM products.category_items WHERE category_id = ?;";
            statement = con.prepareStatement(deleteCategory);
            statement.setInt(1, id);
            statement.executeUpdate();
            deleteCategory = "DELETE FROM products.categories WHERE category_id = ?;";
            statement = con.prepareStatement(deleteCategory);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkDuplication() {
        try {
            Connection con = connect.getConnection();
            String checkDuplication = "SELECT * FROM products.categories WHERE category_name = ? ;";
            PreparedStatement statement = con.prepareStatement(checkDuplication);
            statement.setString(1, category);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
