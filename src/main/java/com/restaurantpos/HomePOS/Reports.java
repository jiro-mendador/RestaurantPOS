/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurantpos.HomePOS;

import static com.restaurantpos.HomePOS.HomePOS.dark;
import static com.restaurantpos.HomePOS.HomePOS.red;
import static com.restaurantpos.HomePOS.HomePOS.violet;
import static com.restaurantpos.HomePOS.Products.big;
import static com.restaurantpos.HomePOS.Products.med;
import com.restaurantpos.LoginAndSignUp.DataBaseConnection;
import static com.restaurantpos.LoginAndSignUp.LoginSignUpGui.lightest;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jiro Mendador
 */
public class Reports {

    static JPanel reportsMainPanel, midPanel, actionPanel;
    JLabel reportsTitleLbl, grossSalesLbl, netSalesLbl, totalDiscountsLbl,
            mostItemSoldLbl, filterDate, dateStartLbl, dateEndLbl;
    JTextField grossSalesTf, netSalesTf, totalDiscountsTf,
            mostItemSoldTf, mostItemQuantityTf, dateStartTf, dateEndTf;
    JTextArea reportTa;
    JButton printBtn, applyFilterBtn;
    JTable salesReportTbl;
    DefaultTableModel salesReportModel;
    JScrollPane salesReportScroll;
    DecimalFormat df = new DecimalFormat("#.##");
    public Reports() {
        reportsMainPanel = new JPanel();
        midPanel = new JPanel();
        actionPanel = new JPanel();
        reportsTitleLbl = new JLabel();
        grossSalesLbl = new JLabel();
        netSalesLbl = new JLabel();
        grossSalesTf = new JTextField();
        netSalesTf = new JTextField();
        salesReportModel = new DefaultTableModel();
        mostItemSoldLbl = new JLabel();
        mostItemSoldTf = new JTextField();
        filterDate = new JLabel();
        mostItemQuantityTf = new JTextField();
        totalDiscountsLbl = new JLabel();
        totalDiscountsTf = new JTextField();
        dateStartLbl = new JLabel();
        dateStartTf = new JTextField();
        dateEndLbl = new JLabel();
        dateEndTf = new JTextField();
        printBtn = new JButton();
        applyFilterBtn = new JButton();
        reportTa = new JTextArea();
    }

    public void setReportsGui() {
        //labels
        reportsTitleLbl.setText("Reports");
        reportsTitleLbl.setBounds(300, 30, 200, 40);
        reportsTitleLbl.setForeground(lightest);
        reportsTitleLbl.setFont(big);

        grossSalesLbl.setText("GROSS SALES ");
        grossSalesLbl.setBounds(440, 165, 200, 40);
        grossSalesLbl.setForeground(lightest);
        grossSalesLbl.setFont(med);

        netSalesLbl.setText("NET SALES ");
        netSalesLbl.setBounds(440, 245, 200, 40);
        netSalesLbl.setForeground(lightest);
        netSalesLbl.setFont(med);

        mostItemSoldLbl.setText("MOST ITEM SOLD"
                + "                       QTY");
        mostItemSoldLbl.setBounds(440, 10, 400, 40);
        mostItemSoldLbl.setForeground(lightest);
        mostItemSoldLbl.setFont(med);

        totalDiscountsLbl.setText("TOTAL DISCOUNTS ");
        totalDiscountsLbl.setBounds(440, 85, 200, 40);
        totalDiscountsLbl.setForeground(lightest);
        totalDiscountsLbl.setFont(med);

        filterDate.setText("FILTER DATE");
        filterDate.setBounds(540, 320, 200, 40);
        filterDate.setForeground(lightest);
        filterDate.setFont(med);

        dateStartLbl.setText("START");
        dateStartLbl.setBounds(440, 360, 200, 40);
        dateStartLbl.setForeground(lightest);
        dateStartLbl.setFont(med);

        dateEndLbl.setText("END");
        dateEndLbl.setBounds(440, 400, 200, 40);
        dateEndLbl.setForeground(lightest);
        dateEndLbl.setFont(med);

        //textfield
        grossSalesTf.setText("");
        grossSalesTf.setBackground(dark);
        grossSalesTf.setBounds(440, 200, 280, 30);
        grossSalesTf.setForeground(lightest);
        grossSalesTf.setFont(med);
        grossSalesTf.setEditable(false);
        grossSalesTf.setMargin(new Insets(5, 5, 5, 5));

        netSalesTf.setText("");
        netSalesTf.setBackground(dark);
        netSalesTf.setBounds(440, 280, 280, 30);
        netSalesTf.setForeground(lightest);
        netSalesTf.setFont(med);
        netSalesTf.setEditable(false);
        netSalesTf.setMargin(new Insets(5, 5, 5, 5));

        mostItemSoldTf.setText("");
        mostItemSoldTf.setBackground(dark);
        mostItemSoldTf.setBounds(440, 45, 200, 30);
        mostItemSoldTf.setForeground(lightest);
        mostItemSoldTf.setFont(med);
        mostItemSoldTf.setEditable(false);
        mostItemSoldTf.setMargin(new Insets(5, 5, 5, 5));

        mostItemQuantityTf.setText("");
        mostItemQuantityTf.setBackground(dark);
        mostItemQuantityTf.setBounds(651, 45, 70, 30);
        mostItemQuantityTf.setForeground(lightest);
        mostItemQuantityTf.setFont(med);
        mostItemQuantityTf.setEditable(false);
        mostItemQuantityTf.setMargin(new Insets(5, 5, 5, 5));

        totalDiscountsTf.setText("");
        totalDiscountsTf.setBackground(dark);
        totalDiscountsTf.setBounds(440, 120, 280, 30);
        totalDiscountsTf.setForeground(lightest);
        totalDiscountsTf.setFont(med);
        totalDiscountsTf.setEditable(false);
        totalDiscountsTf.setMargin(new Insets(5, 5, 5, 5));

        dateStartTf.setText("");
        dateStartTf.setBackground(violet);
        dateStartTf.setBounds(520, 368, 200, 25);
        dateStartTf.setForeground(lightest);
        dateStartTf.setFont(med);
        dateStartTf.setMargin(new Insets(2, 2, 2, 2));

        dateEndTf.setText("");
        dateEndTf.setBackground(violet);
        dateEndTf.setBounds(520, 408, 200, 25);
        dateEndTf.setForeground(lightest);
        dateEndTf.setFont(med);
        dateEndTf.setMargin(new Insets(2, 2, 2, 2));

        reportTa.setEditable(false);
        reportTa.setMargin(new Insets(50, 50, 50, 50));

        //button
        ImageIcon printIcon = new ImageIcon("src/main/java/images/print.png");
        printBtn.setText("PRINT");
        printBtn.setBounds(10, 10, 355, 60);
        printBtn.setBackground(red);
        printBtn.setForeground(lightest);
        printBtn.setFont(med);
        printBtn.setIcon(printIcon);
        printBtn.setIconTextGap(10);
        printBtn.setFocusable(false);
        printBtn.setBorderPainted(false);
        printBtn.addActionListener(new CustomActionListener());

        ImageIcon filterIcon = new ImageIcon("src/main/java/images/filter.png");
        applyFilterBtn.setText("APPLY DATE FILTER");
        applyFilterBtn.setBounds(375, 10, 355, 60);
        applyFilterBtn.setBackground(red);
        applyFilterBtn.setForeground(lightest);
        applyFilterBtn.setFont(med);
        applyFilterBtn.setIcon(filterIcon);
        applyFilterBtn.setIconTextGap(10);
        applyFilterBtn.setFocusable(false);
        applyFilterBtn.setBorderPainted(false);
        applyFilterBtn.addActionListener(new CustomActionListener());

        //tables
        String[] salesReportsColumnNames = {/*"ITEM ID", */"ITEM NAME", "QTY. SOLD"};
        salesReportModel.setColumnIdentifiers(salesReportsColumnNames);
        salesReportTbl = new JTable(salesReportModel);
        salesReportTbl.setFont(new Font("Dialog", Font.PLAIN, 13));
        salesReportTbl.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
        salesReportTbl.getTableHeader().setForeground(dark);
        salesReportTbl.setDefaultEditor(Object.class, null);
        salesReportTbl.getTableHeader().setReorderingAllowed(false);
        //salesReportTbl.getColumnModel().getColumn(0).setPreferredWidth(35);
        salesReportTbl.getColumnModel().getColumn(0).setPreferredWidth(120);
        salesReportTbl.getColumnModel().getColumn(1).setPreferredWidth(60);
        salesReportTbl.setForeground(dark);
        salesReportTbl.setRowHeight(salesReportTbl.getRowHeight() + 5);
        salesReportScroll = new JScrollPane(salesReportTbl);
        salesReportScroll.getVerticalScrollBar().setBackground(violet);
        salesReportScroll.setBounds(20, 20, 400, 415);

        //adding components
        reportsMainPanel.add(reportsTitleLbl);
        reportsMainPanel.add(actionPanel);
        reportsMainPanel.add(midPanel);

        midPanel.add(dateStartTf);
        midPanel.add(dateEndTf);
        midPanel.add(dateStartLbl);
        midPanel.add(dateEndLbl);
        midPanel.add(filterDate);
        midPanel.add(totalDiscountsLbl);
        midPanel.add(totalDiscountsTf);
        midPanel.add(mostItemQuantityTf);
        midPanel.add(mostItemSoldLbl);
        midPanel.add(mostItemSoldTf);
        midPanel.add(netSalesLbl);
        midPanel.add(netSalesTf);
        midPanel.add(grossSalesLbl);
        midPanel.add(grossSalesTf);
        midPanel.add(salesReportScroll);

        actionPanel.add(printBtn);
        actionPanel.add(applyFilterBtn);

        //panels
        actionPanel.setLayout(null);
        actionPanel.setBounds(20, 570, 740, 80);
        actionPanel.setBackground(violet);

        midPanel.setLayout(null);
        midPanel.setBounds(20, 100, 740, 455);
        midPanel.setBackground(violet);

        reportsMainPanel.setLayout(null);
        reportsMainPanel.setBounds(200, 0, 800, 700);
        reportsMainPanel.setBackground(dark);
        reportsMainPanel.setVisible(false);
        //reportsMainPanel.addMouseListener(new CustomMouseListener());
        retrieveSalesReport();
    }

    public void retrieveSalesReport() {
        try {
            if (dateStartTf.getText().isBlank() && dateEndTf.getText().isBlank()) {
                salesReportModel.setRowCount(0);
                Connection con = DataBaseConnection.getConnection();
                PreparedStatement statement = con.prepareStatement("SELECT o.item_ordered_name,"
                        + "	sum(item_ordered_quantities) AS 'Quantity_Sold'"
                        + "	FROM products.orders o"
                        + "    JOIN products.customers c"
                        + "    ON o.customer_id = c.customer_id"
                        + "    GROUP BY o.item_ordered_name;");
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    //int id = 0; //rs.getInt("item_id");
                    String itemName = rs.getString("item_ordered_name");
                    int quantitySold = rs.getInt("Quantity_Sold");
                    salesReportModel.addRow(new Object[]{/*id, */itemName, quantitySold});
                }

                statement = con.prepareStatement("SELECT o.item_ordered_name, "
                        + "	sum(item_ordered_quantities) AS 'Quantity_Sold'"
                        + "	FROM products.orders o"
                        + "    RIGHT JOIN products.customers c"
                        + "    ON o.customer_id = c.customer_id"
                        + "    GROUP BY item_ordered_name"
                        + "    ORDER BY Quantity_Sold desc limit 1;");
                rs = statement.executeQuery();

                while (rs.next()) {
                    mostItemSoldTf.setText(rs.getString("item_ordered_name"));
                    mostItemQuantityTf.setText(String.valueOf(rs.getInt("Quantity_Sold")));
                }

                statement = con.prepareStatement("SELECT sum(discount_added) AS 'TOTAL_DISC',"
                        + "	sum(total_ordered_price) AS 'Sub_Total',"
                        + "	sum(total_ordered_price) - sum(discount_added) AS 'Grand_Total'"
                        + "    FROM products.customers;");
                rs = statement.executeQuery();

                while (rs.next()) {
                    totalDiscountsTf.setText("P "+String.valueOf(df.format(rs.getDouble(1))));
                    grossSalesTf.setText("P "+String.valueOf(df.format(rs.getDouble(2))));
                    netSalesTf.setText("P "+String.valueOf(df.format(rs.getDouble(3))));
                }

                statement.close();
            } else {
                if (!dateStartTf.getText().isBlank() && !dateEndTf.getText().isBlank()) {
                    if (validDate(dateStartTf.getText(), dateEndTf.getText())) {
                        salesReportModel.setRowCount(0);
                        Connection con = DataBaseConnection.getConnection();
                        PreparedStatement statement = con.prepareStatement("SELECT o.item_ordered_name,"
                                + "	sum(item_ordered_quantities) AS 'Quantity_Sold'"
                                + "	FROM products.orders o"
                                + "    JOIN products.customers c"
                                + "    ON o.customer_id = c.customer_id"
                                + "    WHERE c.date_ordered BETWEEN ? AND ?"
                                + "    GROUP BY o.item_ordered_name;");
                        statement.setString(1, dateStartTf.getText());
                        statement.setString(2, dateEndTf.getText());
                        ResultSet rs = statement.executeQuery();

                        while (rs.next()) {
                            String itemName = rs.getString("item_ordered_name");
                            int quantitySold = rs.getInt("Quantity_Sold");
                            salesReportModel.addRow(new Object[]{itemName, quantitySold});
                        }

                        statement = con.prepareStatement("SELECT o.item_ordered_name, "
                                + "	sum(item_ordered_quantities) AS 'Quantity_Sold'"
                                + "	FROM products.orders o"
                                + "    RIGHT JOIN products.customers c"
                                + "    ON o.customer_id = c.customer_id"
                                + "    WHERE c.date_ordered BETWEEN ? AND ?"
                                + "    GROUP BY item_ordered_name"
                                + "    ORDER BY Quantity_Sold desc limit 1;");
                        statement.setString(1, dateStartTf.getText());
                        statement.setString(2, dateEndTf.getText());
                        rs = statement.executeQuery();

                        if (rs.next()) {
                            mostItemSoldTf.setText(rs.getString("item_ordered_name"));
                            mostItemQuantityTf.setText(String.valueOf(rs.getInt("Quantity_Sold")));
                        } else {
                            mostItemSoldTf.setText("");
                            mostItemQuantityTf.setText("");
                        }

                        statement = con.prepareStatement("SELECT sum(discount_added) AS 'TOTAL_DISC',"
                                + "	sum(total_ordered_price) AS 'Sub_Total',"
                                + "	sum(total_ordered_price) - sum(discount_added) AS 'Grand_Total'"
                                + "    FROM products.customers"
                                + " WHERE date_ordered BETWEEN ? AND ?;");
                        statement.setString(1, dateStartTf.getText());
                        statement.setString(2, dateEndTf.getText());
                        rs = statement.executeQuery();

                        while (rs.next()) {
                            totalDiscountsTf.setText("P "+String.valueOf(df.format(rs.getDouble(1))));
                            grossSalesTf.setText("P "+String.valueOf(df.format(rs.getDouble(2))));
                            netSalesTf.setText("P "+String.valueOf(df.format(rs.getDouble(3))));
                        }

                        statement.close();
                    } else {
                        JOptionPane.showMessageDialog(reportsMainPanel, "Enter A Valid Date Format (YYYY-MM-DD)\n"
                                + "Starting Date Should Come First Before End Date...", "Invalid Date", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(reportsMainPanel, "Enter An Exact Starting and End Date\n"
                            + "For Filtering Reports", "Empty Date Fields", 0);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean validDate(String date1, String date2) {
        if (date1.trim().equals("") && date2.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
            sdfrmt.setLenient(false);
            try {
                java.util.Date javaDate1 = sdfrmt.parse(date1);
                java.util.Date javaDate2 = sdfrmt.parse(date2);
                if (javaDate1.compareTo(javaDate2) > 0) {
                    return false;
                }
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
    }

    class CustomActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == applyFilterBtn) {
                retrieveSalesReport();
            } else if (e.getSource() == printBtn) {
                reportTa.setText("");
                String startD = dateStartTf.getText().isBlank() ? HomePOS.getDateToday() : dateStartTf.getText();
                String endD = dateEndTf.getText().isBlank() ? HomePOS.getDateToday() : dateEndTf.getText();
                reportTa.append("\tSALES REPORT\t\n"
                        + "\t"+DataBaseConnection.getRestaurantName()+"\t\n\n"
                        + "Report Date : " +startD+ " TO " + endD + "\n\n\n"
                        + "ITEMS SOLD\n"
                        + "NAME\tQTY\n");
                for(int i = 0; i < salesReportModel.getRowCount(); i++) {   
                    reportTa.append(salesReportModel.getValueAt(i,0)+"\t"+
                            salesReportModel.getValueAt(i,1)+"\n");
                }
                reportTa.append("\n\nDiscounts Total :\t"+totalDiscountsTf.getText()+"\t\n"
                        + "Most Ordered Item :\t"+mostItemSoldTf.getText()+"\n"
                        + "Most Ordered Item QTY :\t"+mostItemQuantityTf.getText()+"\n"
                        + "Gross Sales :\t\t"+grossSalesTf.getText()+"\n"
                        + "Net Sales :\t\t"+netSalesTf.getText()+"\n");
                JOptionPane.showMessageDialog(reportsMainPanel,reportTa);
                try {
                    reportTa.print();
                } catch (PrinterException ex) {
                    Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
