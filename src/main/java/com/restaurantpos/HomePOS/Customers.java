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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jiro Mendador
 */
public class Customers {

    static JPanel customersMainPanel, midPanel, actionPanel;
    JLabel customersPanelLbl, filterLbl, itemOrderedlbl, idLbl, date1Lbl, date2Lbl;
    JTextField searchTf, date1Tf, date2Tf;
    JButton searchCustomerBtn;
    JTable customersTbl, orderListTbl;
    DefaultTableModel customersModel, orderModel;
    JScrollPane customersScroll, orderScroll;

    public Customers() {
        customersMainPanel = new JPanel();
        midPanel = new JPanel();
        actionPanel = new JPanel();
        customersPanelLbl = new JLabel();
        filterLbl = new JLabel();
        idLbl = new JLabel();
        date1Lbl = new JLabel();
        date2Lbl = new JLabel();
        itemOrderedlbl = new JLabel();
        searchTf = new JTextField();
        date1Tf = new JTextField();
        date2Tf = new JTextField();;
        searchCustomerBtn = new JButton();
        customersModel = new DefaultTableModel();
        orderModel = new DefaultTableModel();
    }

    public void setCustomersGui() {
        //label        
        customersPanelLbl.setText("Customers");
        customersPanelLbl.setBounds(300, 30, 200, 40);
        customersPanelLbl.setForeground(lightest);
        customersPanelLbl.setFont(big);

        itemOrderedlbl.setText("Items Ordered");
        itemOrderedlbl.setBounds(145, 280, 200, 40);
        itemOrderedlbl.setForeground(lightest);
        itemOrderedlbl.setFont(med);

        filterLbl.setText("Filters");
        filterLbl.setBounds(550, 280, 200, 40);
        filterLbl.setForeground(lightest);
        filterLbl.setFont(med);

        idLbl.setText("ID / NAME / NO.");
        idLbl.setBounds(420, 320, 200, 40);
        idLbl.setForeground(lightest);
        idLbl.setFont(med);

        date1Lbl.setText("DATE START");
        date1Lbl.setBounds(420, 365, 200, 40);
        date1Lbl.setForeground(lightest);
        date1Lbl.setFont(med);

        date2Lbl.setText("DATE END");
        date2Lbl.setBounds(420, 405, 200, 40);
        date2Lbl.setForeground(lightest);
        date2Lbl.setFont(med);

        //textfield
        searchTf.setText("");
        searchTf.setFont(med);
        searchTf.setBounds(545, 325, 170, 30);
        searchTf.setBackground(lightest);
        searchTf.setForeground(violet);
        searchTf.setMargin(new Insets(0, 10, 0, 10));

        date1Tf.setText("");
        date1Tf.setFont(med);
        date1Tf.setBounds(545, 370, 170, 30);
        date1Tf.setBackground(lightest);
        date1Tf.setForeground(violet);
        date1Tf.setMargin(new Insets(0, 10, 0, 10));

        date2Tf.setText("");
        date2Tf.setFont(med);
        date2Tf.setBounds(545, 410, 170, 30);
        date2Tf.setBackground(lightest);
        date2Tf.setForeground(violet);
        date2Tf.setMargin(new Insets(0, 10, 0, 10));

        //button
        ImageIcon searchIcon = new ImageIcon("src/main/java/images/search.png");
        searchCustomerBtn.setText("SEARCH");
        searchCustomerBtn.setBounds(10, 10, 720, 60);
        searchCustomerBtn.setBackground(red);
        searchCustomerBtn.setForeground(lightest);
        searchCustomerBtn.setFont(med);
        searchCustomerBtn.setIcon(searchIcon);
        searchCustomerBtn.setIconTextGap(10);
        searchCustomerBtn.setFocusable(false);
        searchCustomerBtn.setBorderPainted(false);
        searchCustomerBtn.addActionListener(new CustomActionListener());

        //table
        String[] orderListColumnNames = {"ITEM NAME","ITEM SIZE"," ORDER QUANTITY"};
        orderModel.setColumnIdentifiers(orderListColumnNames);
        //orderModel.addRow(new Object[]{"Burger","100"});
        orderListTbl = new JTable(orderModel);
        orderListTbl.setFont(new Font("Dialog", Font.PLAIN, 15));
        orderListTbl.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 11));
        orderListTbl.getTableHeader().setForeground(dark);
        orderListTbl.setDefaultEditor(Object.class, null);
        orderListTbl.getTableHeader().setReorderingAllowed(false);
        orderListTbl.setForeground(dark);
        orderListTbl.setRowHeight(orderListTbl.getRowHeight() + 5);
        orderScroll = new JScrollPane(orderListTbl);
        orderScroll.getVerticalScrollBar().setBackground(violet);
        orderScroll.setBounds(20, 320, 370, 120);

        String[] customerColumnNames = {"ID", "NAME", "ADDRESS", "NUMBER", "TOTAL", "DISC", "DATE"};
        customersModel.setColumnIdentifiers(customerColumnNames);
        customersTbl = new JTable(customersModel);
        customersTbl.setFont(new Font("Dialog", Font.PLAIN, 12));
        customersTbl.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
        customersTbl.getTableHeader().setForeground(dark);
        customersTbl.getColumnModel().getColumn(0).setPreferredWidth(35);
        customersTbl.getColumnModel().getColumn(5).setPreferredWidth(50);
        customersTbl.getColumnModel().getColumn(2).setPreferredWidth(250);
        customersTbl.setDefaultEditor(Object.class, null);
        customersTbl.getTableHeader().setReorderingAllowed(false);
        customersTbl.setForeground(dark);
        customersTbl.setRowHeight(customersTbl.getRowHeight() + 5);
        customersScroll = new JScrollPane(customersTbl);
        customersScroll.getVerticalScrollBar().setBackground(violet);
        customersScroll.setBounds(20, 20, 700, 250);
        customersTbl.addMouseListener(new CustomMouseListener());

        //adding
        customersMainPanel.add(customersPanelLbl);
        customersMainPanel.add(midPanel);
        customersMainPanel.add(actionPanel);

        midPanel.add(date2Tf);
        midPanel.add(date1Tf);
        midPanel.add(searchTf);
        midPanel.add(date1Lbl);
        midPanel.add(date2Lbl);
        midPanel.add(idLbl);
        midPanel.add(filterLbl);
        midPanel.add(itemOrderedlbl);
        midPanel.add(customersScroll);
        midPanel.add(orderScroll);

        actionPanel.add(searchCustomerBtn);

        //panel
        actionPanel.setLayout(null);
        actionPanel.setBounds(20, 570, 740, 80);
        actionPanel.setBackground(violet);

        midPanel.setLayout(null);
        midPanel.setBounds(20, 100, 740, 455);
        midPanel.setBackground(violet);

        customersMainPanel.setLayout(null);
        customersMainPanel.setBounds(200, 0, 800, 700);
        customersMainPanel.setBackground(dark);
        customersMainPanel.setVisible(false);
        customersMainPanel.addMouseListener(new CustomMouseListener());

        retrieveCustomersData();
    }

    public void retrieveCustomersData() {
        try {
            customersModel.setRowCount(0);
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM products.customers");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("customer_id");
                String name = rs.getString("customer_name");
                String address = rs.getString("customer_address");
                String number = rs.getString("customer_number");
                double total = rs.getDouble("total_ordered_price");
                double discount = rs.getDouble("discount_added");
                String date = rs.getString("date_ordered");
                customersModel.addRow(new Object[]{id, name, address, number, total, discount, date});
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void orderItemNameSizeQuantity(int id) {
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT item_ordered_name, item_ordered_sizes, item_ordered_quantities FROM products.orders WHERE customer_id = ?;");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String item = rs.getString(1);
                String size = rs.getString(2);
                String quantity= rs.getString(3);
                orderModel.addRow(new Object[]{item,size,quantity});
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class CustomActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == searchCustomerBtn) {
                try {
                    customersModel.setRowCount(0);
                    boolean search = !searchTf.getText().isBlank();
                    boolean date1 = !date1Tf.getText().isBlank();
                    boolean date2 = !date2Tf.getText().isBlank();
                    boolean hasDate = false;

                    if (search || date1) {
                        System.out.println("has search OR date1");
                        Connection con = DataBaseConnection.getConnection();
                        PreparedStatement statement = null;
                        String filterStart = "";
                        String filterEnd = "";
                        if (search && (!date1 || !date2)) {
                            System.out.println("has search no date1 or date2");
                            statement = statement = con.prepareStatement("SELECT * FROM products.customers "
                                    + "WHERE customer_id = ? || customer_name LIKE ? || customer_number = ?;");
                            statement.setString(1, searchTf.getText());
                            statement.setString(2, "%" + searchTf.getText() + "%");
                            statement.setString(3, searchTf.getText());
                            date2Tf.setText("");
                        } else if (date1) {
                            hasDate = true;
                            System.out.println("has date1");
                            if (date1 && search) {
                                statement = con.prepareStatement("SELECT * FROM products.customers WHERE date_ordered BETWEEN ? AND ? "
                                        + "AND customer_id = ? OR customer_name LIKE ? OR customer_number = ?;");
                                statement.setString(3, searchTf.getText());
                                statement.setString(4, "%" + searchTf.getText() + "%");
                                statement.setString(5, searchTf.getText());
                            } else {
                                statement = con.prepareStatement("SELECT * FROM products.customers "
                                        + " WHERE date_ordered BETWEEN ? AND ?;");
                            }
                            filterStart = date1Tf.getText();
                            if (!date2) {
                                filterEnd = HomePOS.getDateToday();
                            } else {
                                filterEnd = date2Tf.getText();
                            }
                            statement.setString(1, filterStart);
                            statement.setString(2, filterEnd);
                        }
                        if (!hasDate && search || hasDate && validDate(filterStart) && validDate(filterEnd)
                                || hasDate && validDate(filterStart) && validDate(filterEnd)) {
                            ResultSet rs = statement.executeQuery();
                            while (rs.next()) {
                                int id = rs.getInt("customer_id");
                                String name = rs.getString("customer_name");
                                String addrs = rs.getString("customer_address");
                                String num = rs.getString("customer_number");
                                double total = rs.getDouble("total_ordered_price");
                                double discount = rs.getDouble("discount_added");
                                String date = rs.getString("date_ordered");
                                customersModel.addRow(new Object[]{id, name, addrs, num, total, discount, date});
                            }
                        } else {
                            JOptionPane.showMessageDialog(customersMainPanel, "Enter A Valid Date Format...\n"
                                    + "yyyy-MM-dd");
                            date1Tf.setText("");
                            date2Tf.setText("");
                        }
                        statement.close();
                    } else {
                        JOptionPane.showMessageDialog(customersMainPanel, "Enter An ID, NAME or NUMBER to SEARCH\n"
                                + "or STARTING DATE for FILTERING RESULTS");
                        date1Tf.setText("");
                        date2Tf.setText("");
                        searchTf.setText("");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
                }
                orderModel.setRowCount(0);
                customersTbl.getSelectionModel().clearSelection();
            }
        }
    }

    class CustomMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == customersTbl) {
                orderModel.setRowCount(0);
                System.out.println("Customer Table Clicked!");
                orderItemNameSizeQuantity(Integer.valueOf(customersModel.getValueAt(customersTbl.getSelectedRow(), 0).toString()));
            } else if (e.getSource() == customersMainPanel) {
                orderModel.setRowCount(0);
                customersTbl.getSelectionModel().clearSelection();
                retrieveCustomersData();
                date1Tf.setText("");
                date2Tf.setText("");
                searchTf.setText("");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    public static boolean validDate(String strDate) {
        if (strDate.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
            sdfrmt.setLenient(false);
            try {
                java.util.Date javaDate = sdfrmt.parse(strDate);
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
    }
}
