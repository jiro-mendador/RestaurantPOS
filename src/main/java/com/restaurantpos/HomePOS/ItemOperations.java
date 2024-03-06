package com.restaurantpos.HomePOS;

import com.restaurantpos.LoginAndSignUp.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemOperations {

    String item, regPrice, medPrice, lrgPrice, categoryName, prevItemName;
    DataBaseConnection connect = new DataBaseConnection();
    boolean hasDuplicate = false;

    public ItemOperations() {
        item = "";
        categoryName = "";
        regPrice = "";
        medPrice = "";
        lrgPrice = "";
        prevItemName = "";
    }

    public ItemOperations(String iTname, String catName, String reg, String med, String lrg, String prevItemName) {
        this.item = iTname;
        this.categoryName = catName;
        this.regPrice = reg;
        this.medPrice = med;
        this.lrgPrice = lrg;
        this.prevItemName = prevItemName;
    }

    public void addItems() {
        try {
            Connection con = connect.getConnection();
            String addItem = "INSERT INTO products.category_items(category_id,item_name,regular_price,medium_price,large_price) VALUES(?,?,?,?,?);";
            PreparedStatement statement = con.prepareStatement(addItem);
            statement.setInt(1, getItemCategoryId(categoryName, "category"));
            statement.setString(2, item);
            statement.setString(3, regPrice);
            statement.setString(4, checkForEmptyField(medPrice));
            statement.setString(5, checkForEmptyField(lrgPrice));
            statement.executeUpdate();
            
            String addStocks = "INSERT INTO products.items_stocks(category_id, item_id, stocks_count, regular_deduction, medium_deduction, large_deduction, stocks_unit) VALUES(?,?,?,?,?,?,?);";
            statement = con.prepareStatement(addStocks);
            statement.setInt(1, getItemCategoryId(categoryName, "category"));
            statement.setInt(2, getItemCategoryId(item, "item"));
            statement.setInt(3, 0);
            statement.setInt(4, 1);
            
            int mDed = 0, lDed = 0;
            if(!checkForEmptyField(medPrice).equals("0")) {
                mDed = 1;
            }
            if(!checkForEmptyField(lrgPrice).equals("0")) {
                lDed = 1;
            }
            
            statement.setInt(5,mDed);
            statement.setInt(6,lDed);
            statement.setString(7, "pc");
            statement.execute();
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateitems(int itemId) {
        try {
            Connection con = connect.getConnection();
            String updateItem = "UPDATE products.category_items SET category_id = ?, item_name = ?, regular_price = ?, medium_price = ?, large_price = ? WHERE item_id = ?;";
            PreparedStatement statement = con.prepareStatement(updateItem);
            statement.setInt(1, getItemCategoryId(categoryName, "category"));
            statement.setString(2, item);
            statement.setDouble(3, Double.valueOf(checkForEmptyField(regPrice)));
            statement.setDouble(4, Double.valueOf(checkForEmptyField(medPrice)));
            statement.setDouble(5, Double.valueOf(checkForEmptyField(lrgPrice)));
            statement.setInt(6, itemId);
            statement.executeUpdate();
            statement.close();
            System.out.println(medPrice+" ano na "+lrgPrice);
            
            int mDed = 1, lDed = 1;
            if(checkForEmptyField(medPrice).equals("0")) {
                mDed = 0;
            }
            if(checkForEmptyField(lrgPrice).equals("0")) {
                lDed = 0;
            }
            
            String updateStock = "UPDATE products.items_stocks SET medium_deduction = ?, large_deduction = ? WHERE item_id = ?;";
            PreparedStatement update = con.prepareStatement(updateStock);
            update.setInt(1,mDed);
            update.setInt(2,lDed);
            update.setInt(3,itemId);
            update.executeUpdate();
            update.close();
        } catch (Exception ex) {
            Logger.getLogger(ItemOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteItems(int itemId) {
        try {
            Connection con = connect.getConnection();
            String deleteItem = "DELETE FROM products.items_stocks WHERE item_id = ?;";
            PreparedStatement statement = con.prepareStatement(deleteItem);
            statement.setInt(1, itemId);
            statement.executeUpdate();
            deleteItem = "DELETE FROM products.category_items WHERE item_id = ?;";
            statement = con.prepareStatement(deleteItem);
            statement.setInt(1, itemId);
            statement.executeUpdate();
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(ItemOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkItemNameDuplication() {
        try {
            Connection con = connect.getConnection();
            String checkDuplication = "SELECT item_name FROM products.category_items WHERE item_name = ? ;";
            PreparedStatement statement = con.prepareStatement(checkDuplication);
            statement.setString(1, item);
            ResultSet rs = statement.executeQuery();
            if (prevItemName.equals(item)) {
                hasDuplicate = false;
            } else {
                if (rs.next()) {
                    hasDuplicate = true;
                }
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasDuplicate;
    }

    public String checkForEmptyField(String field) {
        if (field.isBlank() || Double.parseDouble(field) <= 0) {
            field = "0";
        }
        return field;
    }

    public String getPrevItemName(String prevItemName) {
        return prevItemName;
    }

    public int getItemCategoryId(String name, String action) {
        try {
            int id = 0;
            Connection con = connect.getConnection();
            String getId = "";
            String colName = "";
            if (action.equals("item")) {
                getId = "SELECT item_id FROM products.category_items WHERE item_name = ?;";
            } else if (action.equals("category")) {
                getId = "SELECT category_id FROM products.categories WHERE category_name = ?;";
            }
            PreparedStatement statement = con.prepareStatement(getId);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getInt(action + "_id");
            }
            statement.close();
            return id;
        } catch (Exception ex) {
            Logger.getLogger(ItemOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public boolean hasPriceAvailable(String itemName, String priceOf) {
        try {
            double medPrice = 0, lrgPrice = 0;
            Connection con = connect.getConnection();
            String checkDuplication = "SELECT * FROM products.category_items WHERE item_name = ?;";
            PreparedStatement statement = con.prepareStatement(checkDuplication);
            statement.setString(1, itemName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (priceOf.equals("medium")) {
                    medPrice = rs.getDouble("medium_price");
                } else if (priceOf.equals("large")) {
                    lrgPrice = rs.getDouble("large_price");
                }
            }
            statement.close();
            if (medPrice > 0 || lrgPrice > 0) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ItemOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
