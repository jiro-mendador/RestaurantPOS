package com.restaurantpos.HomePOS;

import static com.restaurantpos.HomePOS.Customers.customersMainPanel;
import static com.restaurantpos.HomePOS.HomePOS.big;
import static com.restaurantpos.HomePOS.HomePOS.dark;
import static com.restaurantpos.HomePOS.HomePOS.homePOSmainframe;
import static com.restaurantpos.HomePOS.HomePOS.med;
import static com.restaurantpos.HomePOS.HomePOS.red;
import static com.restaurantpos.HomePOS.HomePOS.violet;
import static com.restaurantpos.HomePOS.Products.productsMainPanel;
import static com.restaurantpos.HomePOS.Reports.reportsMainPanel;
import static com.restaurantpos.HomePOS.User_Accounts.accountsMainPanel;
import com.restaurantpos.LoginAndSignUp.DataBaseConnection;
import static com.restaurantpos.LoginAndSignUp.LoginSignUpGui.light;
import static com.restaurantpos.LoginAndSignUp.LoginSignUpGui.lightest;
import com.restaurantpos.LoginAndSignUp.User;
import com.restaurantpos.RestaurantPOS;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class Dashboard {

    public static JFrame dashboardFrame;
    JPanel dashboardPanel, sidePanel, netSalesPanel, grossSalesPanel,
            transactionCountPanel, itemsSoldPanel, stocksPercentPanel;
    JLabel productsSideLbl, customersSideLbl, overviewSideLbl,
            reportsSideLbl, usersSideLbl, restaurantsNameLbl;
    static JLabel stocksPercentLbl, stocksPercent,
            itemsSoldLbl, itemsSold,
            transactionCountLbl, transactionCount,
            netSalesLbl, netSales,
            grossSalesLbl, grossSales;
    JButton editRestaurantNameBtn, logoutAdminBtn;
    
    static DecimalFormat df = new DecimalFormat("#.##");
    public Dashboard() {
        dashboardFrame = new JFrame();
        dashboardPanel = new JPanel();
        sidePanel = new JPanel();
        netSalesPanel = new JPanel();
        grossSalesPanel = new JPanel();
        transactionCountPanel = new JPanel();
        itemsSoldPanel = new JPanel();
        stocksPercentPanel = new JPanel();

        restaurantsNameLbl = new JLabel();
        productsSideLbl = new JLabel();
        customersSideLbl = new JLabel();
        overviewSideLbl = new JLabel();
        reportsSideLbl = new JLabel();
        usersSideLbl = new JLabel();
        stocksPercentLbl = new JLabel();
        stocksPercent = new JLabel();
        itemsSoldLbl = new JLabel();
        itemsSold = new JLabel();
        transactionCountLbl = new JLabel();
        transactionCount = new JLabel();
        netSalesLbl = new JLabel();
        netSales = new JLabel();
        grossSalesLbl = new JLabel();
        grossSales = new JLabel();
        editRestaurantNameBtn = new JButton();
        logoutAdminBtn = new JButton();

        Products products = new Products();
        products.setProductsGui();
        products.addActions();
        products.addClicks();

        Customers customers = new Customers();
        customers.setCustomersGui();
        HomePOS.putCategoryAndItemToButtons();

        Reports report = new Reports();
        report.setReportsGui();

        User_Accounts accounts = new User_Accounts();
        accounts.setUserAccountsGui();

    }

    public void setDashboardGui() {
        //button
        ImageIcon logoutIcon = new ImageIcon("src/main/java/images/logout.png");
        logoutAdminBtn.setText("Logout");
        logoutAdminBtn.setBounds(-30, 355, 230, 40);
        logoutAdminBtn.setBackground(violet);
        logoutAdminBtn.setForeground(lightest);
        logoutAdminBtn.setFont(big);
        logoutAdminBtn.setIcon(logoutIcon);
        logoutAdminBtn.setIconTextGap(20);
        logoutAdminBtn.setFocusable(false);
        logoutAdminBtn.setBorderPainted(false);
        logoutAdminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePOSmainframe.setVisible(false);
                dashboardFrame.setVisible(false);
                new RestaurantPOS().runnables();
            }
        });

        ImageIcon editIcon = new ImageIcon("src/main/java/images/edit.png");
        editRestaurantNameBtn.setText("");
        editRestaurantNameBtn.setBounds(10, 36, 30, 30);
        editRestaurantNameBtn.setBackground(red);
        editRestaurantNameBtn.setForeground(lightest);
        editRestaurantNameBtn.setFont(med);
        editRestaurantNameBtn.setIcon(editIcon);
        editRestaurantNameBtn.setIconTextGap(10);
        editRestaurantNameBtn.setFocusable(false);
        editRestaurantNameBtn.setBorderPainted(false);
        editRestaurantNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Object[] options = {"SAVE", "CANCEL"};

                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Enter Restaurant's Name : "));
                    JTextField rName = new JTextField(30);
                    panel.add(rName);

                    int result = JOptionPane.showOptionDialog(null, panel, "Restaurant Name",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                            options, null);
                    String restaurantName = rName.getText();
                    if (result == JOptionPane.YES_OPTION) {
                        if (!restaurantName.isBlank()) {
                            int rowCount = 0;
                            Connection con = DataBaseConnection.getConnection();
                            PreparedStatement statement = con.prepareStatement("SELECT COUNT(*) FROM products.restaurant;");
                            ResultSet rs = statement.executeQuery();
                            while (rs.next()) {
                                rowCount = rs.getInt(1);
                            }
                            if (rowCount == 0) {
                                statement = con.prepareStatement("INSERT INTO products.restaurant VALUE(default,?);");
                                statement.setString(1, restaurantName);
                                statement.executeUpdate();
                            } else {
                                statement = con.prepareStatement("UPDATE products.restaurant SET restaurant_name = ? WHERE id = 1;");
                                statement.setString(1, restaurantName);
                                statement.executeUpdate();
                            }
                            statement.close();
                            restaurantsNameLbl.setText(DataBaseConnection.getRestaurantName());
                            HomePOS.restaurantNameLbl.setText(DataBaseConnection.getRestaurantName());
                        } else {
                            JOptionPane.showMessageDialog(dashboardFrame, "Cannot Proceed With Empty Field...", "Restaurant Name", 0);
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        //labels
        stocksPercentLbl.setText("STOCKS PERCENTAGE");
        stocksPercentLbl.setBounds(20, 20, 250, 40);
        stocksPercentLbl.setForeground(lightest);
        stocksPercentLbl.setFont(new Font("Dialog", Font.BOLD, 17));
        
        stocksPercent.setBounds(50, 100, 250, 75);
        stocksPercent.setForeground(lightest);
        stocksPercent.setFont(new Font("Dialog", Font.BOLD, 50));

        itemsSoldLbl.setText("ITEMS SOLD");
        itemsSoldLbl.setBounds(50, 20, 250, 40);
        itemsSoldLbl.setForeground(lightest);
        itemsSoldLbl.setFont(new Font("Dialog", Font.BOLD, 20));
        
        itemsSold.setBounds(35, 100, 250, 75);
        itemsSold.setForeground(lightest);
        itemsSold.setFont(new Font("Dialog", Font.BOLD, 35));

        transactionCountLbl.setText("TRANSACTIONS");
        transactionCountLbl.setBounds(35, 20, 250, 40);
        transactionCountLbl.setForeground(lightest);
        transactionCountLbl.setFont(new Font("Dialog", Font.BOLD, 20));
        
        transactionCount.setBounds(45, 100, 250, 75);
        transactionCount.setForeground(lightest);
        transactionCount.setFont(new Font("Dialog", Font.BOLD, 35));

        netSalesLbl.setText("NET SALES");
        netSalesLbl.setBounds(20, 20, 250, 40);
        netSalesLbl.setForeground(lightest);
        netSalesLbl.setFont(new Font("Dialog", Font.BOLD, 30));
        
        netSales.setBounds(40, 100, 250, 75);
        netSales.setForeground(lightest);
        netSales.setFont(new Font("Dialog", Font.BOLD, 40));

        grossSalesLbl.setText("GROSS SALES");
        grossSalesLbl.setBounds(20, 20, 250, 40);
        grossSalesLbl.setForeground(lightest);
        grossSalesLbl.setFont(new Font("Dialog", Font.BOLD, 30));
        
        grossSales.setBounds(40, 100, 250, 75);
        grossSales.setForeground(lightest);
        grossSales.setFont(new Font("Dialog", Font.BOLD, 40));

        restaurantsNameLbl.setText(DataBaseConnection.getRestaurantName());
        restaurantsNameLbl.setBounds(50, 30, 800, 40);
        restaurantsNameLbl.setForeground(lightest);
        restaurantsNameLbl.setFont(new Font("Dialog", Font.BOLD, 30));

        ImageIcon overviewIcon = new ImageIcon("src/main/java/images/overview.png");
        overviewSideLbl.setText("Overview");
        overviewSideLbl.setBounds(0, 100, 200, 50);
        overviewSideLbl.setBackground(dark);
        overviewSideLbl.setForeground(lightest);
        overviewSideLbl.setFont(big);
        overviewSideLbl.setIcon(overviewIcon);
        overviewSideLbl.setIconTextGap(20);
        overviewSideLbl.setOpaque(true);
        overviewSideLbl.setBorder(new CompoundBorder(overviewSideLbl.getBorder(),
                new EmptyBorder(20, 30, 20, 20)));

        ImageIcon productsIcon = new ImageIcon("src/main/java/images/products.png");
        productsSideLbl.setText("Products");
        productsSideLbl.setBounds(0, 150, 200, 50);
        productsSideLbl.setForeground(lightest);
        productsSideLbl.setFont(big);
        productsSideLbl.setIcon(productsIcon);
        productsSideLbl.setIconTextGap(20);
        productsSideLbl.setBorder(new CompoundBorder(productsSideLbl.getBorder(),
                new EmptyBorder(20, 30, 20, 20)));

        ImageIcon customersIcon = new ImageIcon("src/main/java/images/customers.png");
        customersSideLbl.setText("Customers");
        customersSideLbl.setBounds(0, 200, 200, 50);
        customersSideLbl.setForeground(lightest);
        customersSideLbl.setFont(big);
        customersSideLbl.setIcon(customersIcon);
        customersSideLbl.setIconTextGap(20);
        customersSideLbl.setBorder(new CompoundBorder(customersSideLbl.getBorder(),
                new EmptyBorder(20, 30, 20, 20)));

        ImageIcon reportsIcon = new ImageIcon("src/main/java/images/reports.png");
        reportsSideLbl.setText("Reports");
        reportsSideLbl.setBounds(0, 250, 200, 50);
        reportsSideLbl.setForeground(lightest);
        reportsSideLbl.setFont(big);
        reportsSideLbl.setIcon(reportsIcon);
        reportsSideLbl.setIconTextGap(20);
        reportsSideLbl.setBorder(new CompoundBorder(reportsSideLbl.getBorder(),
                new EmptyBorder(20, 30, 20, 20)));

        ImageIcon usersIcon = new ImageIcon("src/main/java/images/users.png");
        usersSideLbl.setText("Users");
        usersSideLbl.setBounds(5, 300, 205, 50);
        usersSideLbl.setForeground(lightest);
        usersSideLbl.setFont(big);
        usersSideLbl.setIcon(usersIcon);
        usersSideLbl.setIconTextGap(20);
        usersSideLbl.setBorder(new CompoundBorder(usersSideLbl.getBorder(),
                new EmptyBorder(20, 30, 20, 20)));

        //panels
        stocksPercentPanel.setLayout(null);
        stocksPercentPanel.setBounds(510, 370, 220, 250);
        stocksPercentPanel.setBackground(new Color(23,191,107));

        itemsSoldPanel.setLayout(null);
        itemsSoldPanel.setBounds(280, 370, 220, 250);
        itemsSoldPanel.setBackground(new Color(72,117,180));

        transactionCountPanel.setLayout(null);
        transactionCountPanel.setBounds(50, 370, 220, 250);
        transactionCountPanel.setBackground(new Color(255,104,105));

        grossSalesPanel.setLayout(null);
        grossSalesPanel.setBounds(400, 100, 330, 250);
        grossSalesPanel.setBackground(new Color(245,162,47));

        netSalesPanel.setLayout(null);
        netSalesPanel.setBounds(50, 100, 330, 250);
        netSalesPanel.setBackground(new Color(105,158,255));
        
        sidePanel.setLayout(null);
        sidePanel.setBounds(0, 0, 200, 700);
        sidePanel.setBackground(violet);
        sidePanel.setVisible(true);

        dashboardPanel.setLayout(null);
        dashboardPanel.setBounds(200, 0, 800, 700);
        dashboardPanel.setBackground(dark);
        dashboardPanel.setVisible(true);

        //add components
        stocksPercentPanel.add(stocksPercent);
        stocksPercentPanel.add(stocksPercentLbl);

        itemsSoldPanel.add(itemsSold);
        itemsSoldPanel.add(itemsSoldLbl);

        transactionCountPanel.add(transactionCount);
        transactionCountPanel.add(transactionCountLbl);

        netSalesPanel.add(netSales);
        netSalesPanel.add(netSalesLbl);

        grossSalesPanel.add(grossSales);
        grossSalesPanel.add(grossSalesLbl);

        sidePanel.add(logoutAdminBtn);
        sidePanel.add(overviewSideLbl);
        sidePanel.add(productsSideLbl);
        sidePanel.add(customersSideLbl);
        sidePanel.add(reportsSideLbl);
        sidePanel.add(usersSideLbl);

        dashboardPanel.add(editRestaurantNameBtn);
        dashboardPanel.add(restaurantsNameLbl);
        dashboardPanel.add(stocksPercentPanel);
        dashboardPanel.add(itemsSoldPanel);
        dashboardPanel.add(transactionCountPanel);
        dashboardPanel.add(grossSalesPanel);
        dashboardPanel.add(netSalesPanel);

        dashboardFrame.add(accountsMainPanel);
        dashboardFrame.add(reportsMainPanel);
        dashboardFrame.add(customersMainPanel);
        dashboardFrame.add(productsMainPanel);
        dashboardFrame.add(sidePanel);
        dashboardFrame.add(dashboardPanel);

        //frames
        dashboardFrame.setTitle("Dashboard");
        dashboardFrame.setSize(1000, 700);
        dashboardFrame.setLayout(null);
        dashboardFrame.setResizable(false);
        dashboardFrame.setLocationRelativeTo(null);
        //dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//
        dashboardFrame.setVisible(false);
        
        updateOverview();
    }

    public void addClicks() {
        overviewSideLbl.addMouseListener(new CustomMouseListener());
        productsSideLbl.addMouseListener(new CustomMouseListener());
        customersSideLbl.addMouseListener(new CustomMouseListener());
        reportsSideLbl.addMouseListener(new CustomMouseListener());
        usersSideLbl.addMouseListener(new CustomMouseListener());
    }

    class CustomMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == overviewSideLbl) {
                changeBackgroundColor(new JLabel[]{productsSideLbl, customersSideLbl,
                    reportsSideLbl, usersSideLbl}, overviewSideLbl);
                hideShowPanels(new JPanel[]{productsMainPanel, customersMainPanel,
                    reportsMainPanel, accountsMainPanel}, dashboardPanel);
            } else if (e.getSource() == productsSideLbl) {
                changeBackgroundColor(new JLabel[]{overviewSideLbl, productsSideLbl, customersSideLbl,
                    reportsSideLbl, usersSideLbl}, productsSideLbl);
                hideShowPanels(new JPanel[]{dashboardPanel, customersMainPanel,
                    reportsMainPanel, accountsMainPanel}, productsMainPanel);
            } else if (e.getSource() == customersSideLbl) {
                changeBackgroundColor(new JLabel[]{productsSideLbl, overviewSideLbl,
                    reportsSideLbl, usersSideLbl}, customersSideLbl);
                hideShowPanels(new JPanel[]{dashboardPanel, productsMainPanel,
                    reportsMainPanel, accountsMainPanel}, customersMainPanel);
            } else if (e.getSource() == reportsSideLbl) {
                changeBackgroundColor(new JLabel[]{productsSideLbl, customersSideLbl,
                    overviewSideLbl, usersSideLbl}, reportsSideLbl);
                hideShowPanels(new JPanel[]{dashboardPanel, productsMainPanel,
                    customersMainPanel, accountsMainPanel}, reportsMainPanel);
            } else if (e.getSource() == usersSideLbl) {
                changeBackgroundColor(new JLabel[]{productsSideLbl, customersSideLbl,
                    overviewSideLbl, reportsSideLbl}, usersSideLbl);
                hideShowPanels(new JPanel[]{dashboardPanel, productsMainPanel,
                    customersMainPanel, reportsMainPanel}, accountsMainPanel);
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
    
    public static void updateOverview() {
        transactionCount.setText(String.valueOf(getTransactionCountToday()));
        netSales.setText(df.format(getNetSalesToday()));
        grossSales.setText(df.format(getGrossSalesToday()));
        itemsSold.setText(String.valueOf(getItemsSoldToday()));
        stocksPercent.setText(String.valueOf(getStockPercentage())+"%");
    }
    
    //utils method
    public void hideShowPanels(JPanel[] panels, JPanel panelshow) {
        for (JPanel i : panels) {
            i.setVisible(false);
        }
        panelshow.setVisible(true);
    }

    public void changeBackgroundColor(JLabel[] labelsLight, JLabel labelDark) {
        for (JLabel i : labelsLight) {
            i.setBackground(violet);
            i.setOpaque(false);
        }
        labelDark.setBackground(dark);
        labelDark.setOpaque(true);
    }
    
    public static double getNetSalesToday() {
        double net = 0;
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT sum(total_ordered_price) FROM products.customers WHERE date_ordered = ?;");
            statement.setString(1,HomePOS.getDateToday());
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                net = rs.getDouble(1);
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return net;
    }
    
     public static double getGrossSalesToday() {
        double gross = 0;
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT sum(total_ordered_price) - sum(discount_added) FROM products.customers WHERE date_ordered = ?;");
            statement.setString(1,HomePOS.getDateToday());
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                gross = rs.getDouble(1);
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gross;
    }
     
    public static int getTransactionCountToday() {
        int count = 0;
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT COUNT(*) FROM products.customers WHERE date_ordered = ?;");
            statement.setString(1,HomePOS.getDateToday());
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                count = rs.getInt(1);
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    
    public static int getItemsSoldToday() {
        int sold = 0;
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT sum(o.item_ordered_quantities) "
                    + "FROM products.orders o "
                    + "JOIN products.customers c "
                    + "ON c.customer_id = o.customer_id "
                    + "WHERE c.date_ordered = ?;");
            statement.setString(1,HomePOS.getDateToday());
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                sold = rs.getInt(1);
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sold;
    }
    
    public static int getStockPercentage() {
        int percentage = 0;
        try {
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT "
                    + "(SUM(stocks_count)/5000)*100 / COUNT(*) "
                    + "FROM products.items_stocks;");
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                percentage = rs.getInt(1);
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return percentage;
    }
}
