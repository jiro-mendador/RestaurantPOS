package com.restaurantpos.HomePOS;

import com.restaurantpos.LoginAndSignUp.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockOperations {

    String stockCount, regDed, medDed, lrgDed, stockUnit, itemName;
    DataBaseConnection connect = new DataBaseConnection();
    ItemOperations item = new ItemOperations();

    public StockOperations(String stock_count, String item_name, String reg_deduc, String med_deduc, String lrg_deduc, String stock_unit) {
        this.stockCount = stock_count;
        this.itemName = item_name;
        this.regDed = reg_deduc;
        this.medDed = med_deduc;
        this.lrgDed = lrg_deduc;
        this.stockUnit = stock_unit;
    }

    public void updateStocks(int id) {
        try {
            Connection con = connect.getConnection();
            String updateStocks = "UPDATE products.items_stocks SET stocks_count = ?, regular_deduction = ?, medium_deduction = ?, large_deduction = ?, stocks_unit = ? WHERE stock_id = ?;";
            PreparedStatement statement = con.prepareStatement(updateStocks);
            statement.setInt(1, Integer.valueOf(stockCount));
            statement.setInt(2, Integer.valueOf(regDed));
            statement.setInt(3, Integer.valueOf(medDed));
            statement.setInt(4, Integer.valueOf(lrgDed));
            statement.setString(5, stockUnit);
            statement.setInt(6, id);
            statement.executeUpdate();
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(StockOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
