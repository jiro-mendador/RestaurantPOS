package com.restaurantpos.HomePOS;

import com.restaurantpos.CustomerAndOrder.CustomerAndOrder;
import com.restaurantpos.LoginAndSignUp.DataBaseConnection;
import com.restaurantpos.LoginAndSignUp.User;
import com.restaurantpos.LoginAndSignUp.Verify;
import com.restaurantpos.RestaurantPOS;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class HomePOS {

    static JFrame homePOSmainframe;
    static JPanel homePOSmainpanel, orderedPanel, operationsPanel, itemsPanel, customersInfoPanel, categoriesPanel;
    static JButton regularBtn, mediumBtn, largeBtn, dineInBtn,
            takeOutBtn, discountBtn, printReceiptBtn, voidBtn, adminBtn, logoutStaffBtn, deliveryBtn;
    static JLabel restaurantNameLbl, quantityLbl, paymentLbl, customerNameLbl, customerAddressLbl, customerNumberLbl;
    static JTextField quantityTf, totalTf, subTotalTf, paymentTf, customerNameTf, customerAddressTf, customerNumberTf;
    static JTable receiptTable;
    static DefaultTableModel receiptModel;
    static JScrollPane sp1, receiptScroll;
    static JTextArea receiptTa;

    static Font big, med;
    static Color dark, violet, red;

    static ArrayList<JPanel> panelList = new ArrayList<>();
    static ArrayList<JScrollPane> scrollPaneList = new ArrayList<>();
    static ArrayList<JButton> categoryButtonList = new ArrayList<>();
    static ArrayList<JButton> itemButtonList = new ArrayList<>();

    static ArrayList<JTextField> fields = new ArrayList<>();

    static ArrayList<Integer> stockToDeductList = new ArrayList<>();
    static ArrayList<Integer> itemIdList = new ArrayList<>();

    static ArrayList<String> itemNameOrdered = new ArrayList<>();
    static ArrayList<String> itemQuantityOrdered = new ArrayList<>();
    static ArrayList<String> itemSizeOrdered = new ArrayList<>();

    static int quantityCounter = 0;
    static String prevSize = "";
    static String itemSize = "";
    static int prevButtonNumber = 0;
    static boolean hasDiscount = false;
    static DecimalFormat df = new DecimalFormat("#.##");
    static String orderType = "";
    static String discountType = "None";
    static boolean stockInsufficient = false;
    static double change = 0;

    public HomePOS() {
        homePOSmainframe = new JFrame();
        homePOSmainpanel = new JPanel();

        orderedPanel = new JPanel();
        operationsPanel = new JPanel();
        itemsPanel = new JPanel();
        categoriesPanel = new JPanel();
        customersInfoPanel = new JPanel();

        regularBtn = new JButton();
        mediumBtn = new JButton();
        largeBtn = new JButton();
        dineInBtn = new JButton();
        takeOutBtn = new JButton();
        discountBtn = new JButton();
        printReceiptBtn = new JButton();
        voidBtn = new JButton();
        adminBtn = new JButton();
        logoutStaffBtn = new JButton();
        deliveryBtn = new JButton();

        quantityLbl = new JLabel();
        paymentLbl = new JLabel();
        restaurantNameLbl = new JLabel();
        customerNameLbl = new JLabel();
        customerAddressLbl = new JLabel();
        customerNumberLbl = new JLabel();

        quantityTf = new JTextField();
        totalTf = new JTextField();
        subTotalTf = new JTextField();
        paymentTf = new JTextField();
        customerNameTf = new JTextField();
        customerAddressTf = new JTextField();
        customerNumberTf = new JTextField();

        receiptTable = new JTable();
        receiptModel = new DefaultTableModel();
        receiptTa = new JTextArea();

        dark = new Color(30, 27, 46);
        violet = new Color(52, 58, 78);
        red = new Color(255, 84, 115);

        big = new Font("Dialog", Font.BOLD, 17);
        med = new Font("Dialog", Font.BOLD, 12);

        sp1 = new JScrollPane();

        Dashboard dashboard = new Dashboard();
    }

    public void setHomePOSGui() {
        fieldsToArray(new JTextField[]{quantityTf, customerNumberTf, paymentTf, customerNameTf, customerAddressTf});

        //tables       
        String[] catColumnNames = {"QTY", "ITEM", "SIZE", "PRICE"};
        receiptModel.setColumnIdentifiers(catColumnNames);

        receiptTable = new JTable(receiptModel);
        receiptTable.setShowVerticalLines(true);
        receiptTable.setShowHorizontalLines(false);
        receiptTable.setFont(new Font("Dialog", Font.PLAIN, 12));
        receiptTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 15));
        receiptTable.getTableHeader().setForeground(dark);
        receiptTable.setDefaultEditor(Object.class, null);
        receiptTable.getTableHeader().setReorderingAllowed(false);
        receiptTable.setForeground(dark);
        receiptTable.setRowHeight(receiptTable.getRowHeight() + 5);

        receiptScroll = new JScrollPane(receiptTable);
        receiptScroll.getVerticalScrollBar().setBackground(violet);
        receiptScroll.setBounds(10, 10, 280, 375);

        //textfields
        customerNameTf.setText("");
        customerNameTf.setFont(big);
        customerNameTf.setBounds(10, 45, 200, 30);
        customerNameTf.setBackground(Color.white);
        customerNameTf.setForeground(violet);
        customerNameTf.setMargin(new Insets(0, 10, 0, 10));

        customerAddressTf.setText("");
        customerAddressTf.setFont(big);
        customerAddressTf.setBounds(238, 45, 248, 30);
        customerAddressTf.setBackground(Color.white);
        customerAddressTf.setForeground(violet);
        customerAddressTf.setMargin(new Insets(0, 10, 0, 10));

        customerNumberTf.setText("");
        customerNumberTf.setFont(big);
        customerNumberTf.setBounds(513, 45, 198, 30);
        customerNumberTf.setBackground(Color.white);
        customerNumberTf.setForeground(violet);
        customerNumberTf.setMargin(new Insets(0, 10, 0, 10));

        quantityTf.setText("");
        quantityTf.setFont(new Font("Dialog", Font.BOLD, 25));
        quantityTf.setBounds(235, 55, 250, 40);
        quantityTf.setBackground(Color.white);
        quantityTf.setForeground(violet);
        quantityTf.setMargin(new Insets(0, 10, 0, 0));

        paymentTf.setText("");
        paymentTf.setFont(med);
        paymentTf.setBounds(10, 520, 280, 40);
        paymentTf.setForeground(dark);
        paymentTf.setMargin(new Insets(0, 10, 0, 0));

        subTotalTf.setText("SUB TOTAL : ");
        subTotalTf.setFont(med);
        subTotalTf.setBounds(10, 395, 280, 40);
        subTotalTf.setBackground(red);
        subTotalTf.setForeground(Color.white);
        subTotalTf.setMargin(new Insets(0, 10, 0, 0));
        subTotalTf.setEditable(false);

        totalTf.setText("TOTAL : ");
        totalTf.setFont(med);
        totalTf.setBounds(10, 445, 280, 40);
        totalTf.setBackground(red);
        totalTf.setForeground(Color.white);
        totalTf.setMargin(new Insets(0, 10, 0, 0));
        totalTf.setEditable(false);

        //labels   
        customerNameLbl.setText("Customer's Name");
        customerNameLbl.setBounds(15, 10, 150, 30);
        customerNameLbl.setForeground(Color.white);
        customerNameLbl.setFont(big);

        customerAddressLbl.setText("Customer's Address");
        customerAddressLbl.setBounds(240, 10, 200, 30);
        customerAddressLbl.setForeground(Color.white);
        customerAddressLbl.setFont(big);

        customerNumberLbl.setText("Customer's Number");
        customerNumberLbl.setBounds(515, 10, 200, 30);
        customerNumberLbl.setForeground(Color.white);
        customerNumberLbl.setFont(big);

        restaurantNameLbl.setText(DataBaseConnection.getRestaurantName());
        restaurantNameLbl.setBounds(20, 25, 1000, 40);
        restaurantNameLbl.setForeground(Color.white);
        restaurantNameLbl.setFont(new Font("Dialog", Font.BOLD, 30));

        quantityLbl.setText("MANUAL QUANTITY");
        quantityLbl.setBounds(280, 10, 200, 40);
        quantityLbl.setForeground(Color.white);
        quantityLbl.setFont(big);

        paymentLbl.setText("PAYMENT");
        paymentLbl.setBounds(10, 485, 100, 40);
        paymentLbl.setForeground(Color.white);
        paymentLbl.setFont(big);

        //buttons
        deliveryBtn.setText("DELIVERY");
        deliveryBtn.setBounds(510, 100, 200, 40);
        deliveryBtn.setBackground(red);
        deliveryBtn.setForeground(Color.white);
        deliveryBtn.setFont(med);
        deliveryBtn.addActionListener(new CustomActionListener());

        ImageIcon settingsIcon = new ImageIcon("src/main/java/images/settings.png");
        adminBtn.setText("Admin Dashboard");
        adminBtn.setBounds(490, 20, 250, 40);
        adminBtn.setBackground(violet);
        adminBtn.setForeground(Color.white);
        adminBtn.setFont(big);
        adminBtn.setIcon(settingsIcon);
        adminBtn.setIconTextGap(10);
        adminBtn.setFocusable(false);
        adminBtn.addActionListener(new CustomActionListener());
        adminBtn.setBorderPainted(false);

        ImageIcon logoutIcon = new ImageIcon("src/main/java/images/logout.png");
        logoutStaffBtn.setText("Logout");
        logoutStaffBtn.setBounds(490, 20, 250, 40);
        logoutStaffBtn.setBackground(violet);
        logoutStaffBtn.setForeground(Color.white);
        logoutStaffBtn.setFont(big);
        logoutStaffBtn.setIcon(logoutIcon);
        logoutStaffBtn.setIconTextGap(10);
        logoutStaffBtn.setFocusable(false);
        logoutStaffBtn.addActionListener(new CustomActionListener());
        logoutStaffBtn.setBorderPainted(false);

        /*Verify verify = new Verify();
        try {
            if (verify.isAnAdmin(User.userFirstName, User.userLastName)) {
                adminBtn.setVisible(true);
                logoutStaffBtn.setVisible(false);
            } else {
                adminBtn.setVisible(false);
                logoutStaffBtn.setVisible(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(HomePOS.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        adminBtn.setVisible(true);
        logoutStaffBtn.setVisible(false);

        voidBtn.setText("VOID");
        voidBtn.setBounds(10, 570, 280, 40);
        voidBtn.setBackground(red);
        voidBtn.setForeground(Color.white);
        voidBtn.setFont(med);
        voidBtn.addActionListener(new CustomActionListener());

        printReceiptBtn.setText("PRINT RECEIPT");
        printReceiptBtn.setBounds(10, 620, 280, 40);
        printReceiptBtn.setBackground(red);
        printReceiptBtn.setForeground(Color.white);
        printReceiptBtn.setFont(med);
        printReceiptBtn.addActionListener(new CustomActionListener());

        discountBtn.setText("DISCOUNT");
        discountBtn.setBounds(235, 100, 250, 40);
        discountBtn.setBackground(red);
        discountBtn.setForeground(Color.white);
        discountBtn.setFont(med);
        discountBtn.addActionListener(new CustomActionListener());

        dineInBtn.setText("DINE IN");
        dineInBtn.setBounds(510, 10, 200, 40);
        dineInBtn.setBackground(red);
        dineInBtn.setForeground(Color.white);
        dineInBtn.setFont(med);
        dineInBtn.addActionListener(new CustomActionListener());

        takeOutBtn.setText("TAKE OUT");
        takeOutBtn.setBounds(510, 55, 200, 40);
        takeOutBtn.setBackground(red);
        takeOutBtn.setForeground(Color.white);
        takeOutBtn.setFont(med);
        takeOutBtn.addActionListener(new CustomActionListener());

        regularBtn.setText("Regular");
        regularBtn.setBounds(10, 10, 200, 40);
        regularBtn.setBackground(red);
        regularBtn.setForeground(Color.white);
        regularBtn.setFont(med);
        regularBtn.addActionListener(new CustomActionListener());

        mediumBtn.setText("Medium");
        mediumBtn.setBounds(10, 55, 200, 40);
        mediumBtn.setBackground(red);
        mediumBtn.setForeground(Color.white);
        mediumBtn.setFont(med);
        mediumBtn.addActionListener(new CustomActionListener());

        largeBtn.setText("Large");
        largeBtn.setBounds(10, 100, 200, 40);
        largeBtn.setBackground(red);
        largeBtn.setForeground(Color.white);
        largeBtn.setFont(med);
        largeBtn.addActionListener(new CustomActionListener());

        //textareas
        receiptTa.setEditable(false);
        receiptTa.setMargin(new Insets(50, 50, 50, 50));

        //panels
        customersInfoPanel.setLayout(null);
        customersInfoPanel.setBackground(violet);
        customersInfoPanel.setBounds(20, 600, 720, 90);

        categoriesPanel.setLayout(new GridLayout(1, 100, 5, 0));
        categoriesPanel.setBackground(violet);

        itemsPanel.setLayout(null);
        itemsPanel.setBounds(20, 170, 720, 250);
        itemsPanel.setBackground(violet);

        operationsPanel.setLayout(null);
        operationsPanel.setBounds(20, 440, 720, 150);
        operationsPanel.setBackground(violet);

        orderedPanel.setLayout(null);
        orderedPanel.setBounds(760, 20, 300, 670);
        orderedPanel.setBackground(violet);

        homePOSmainpanel.setLayout(null);
        homePOSmainpanel.setBounds(0, 0, 1100, 750);
        homePOSmainpanel.setBackground(dark);

        //adding components
        customersInfoPanel.add(customerNumberTf);
        customersInfoPanel.add(customerNumberLbl);
        customersInfoPanel.add(customerAddressTf);
        customersInfoPanel.add(customerAddressLbl);
        customersInfoPanel.add(customerNameTf);
        customersInfoPanel.add(customerNameLbl);

        orderedPanel.add(paymentLbl);
        orderedPanel.add(paymentTf);
        orderedPanel.add(totalTf);
        orderedPanel.add(subTotalTf);
        orderedPanel.add(voidBtn);
        orderedPanel.add(printReceiptBtn);
        orderedPanel.add(receiptScroll);

        operationsPanel.add(discountBtn);
        operationsPanel.add(deliveryBtn);
        operationsPanel.add(takeOutBtn);
        operationsPanel.add(dineInBtn);
        operationsPanel.add(quantityTf);
        operationsPanel.add(quantityLbl);
        operationsPanel.add(regularBtn);
        operationsPanel.add(mediumBtn);
        operationsPanel.add(largeBtn);

        sp1 = new JScrollPane(categoriesPanel);
        sp1.setBounds(20, 70, 720, 80);

        homePOSmainpanel.add(logoutStaffBtn);
        homePOSmainpanel.add(adminBtn);
        homePOSmainpanel.add(restaurantNameLbl);
        homePOSmainpanel.add(customersInfoPanel);
        homePOSmainpanel.add(sp1);
        homePOSmainpanel.add(itemsPanel);
        homePOSmainpanel.add(operationsPanel);
        homePOSmainpanel.add(orderedPanel);
        homePOSmainframe.add(homePOSmainpanel);

        //frames
        homePOSmainframe.setTitle("MAIN POS");
        homePOSmainframe.setSize(1100, 750);
        homePOSmainframe.setLayout(null);
        homePOSmainframe.setResizable(false);
        homePOSmainframe.setLocationRelativeTo(null);
        homePOSmainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homePOSmainframe.setVisible(true);

        putCategoryAndItemToButtons();
    }

    private static class CustomActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == adminBtn) {
                setToDefault();
                Dashboard dashboard = new Dashboard();
                dashboard.setDashboardGui();
                dashboard.addClicks();
                dashboard.dashboardFrame.setVisible(true);
            } else if (e.getSource() == logoutStaffBtn) {
                homePOSmainframe.setVisible(false);
                new RestaurantPOS().runnables();
            } else if (e.getSource() == regularBtn) {
                itemSize = "regular";
                buttonColor(new JButton[]{mediumBtn, largeBtn}, regularBtn);
            } else if (e.getSource() == mediumBtn) {
                itemSize = "medium";
                buttonColor(new JButton[]{regularBtn, largeBtn}, mediumBtn);
            } else if (e.getSource() == largeBtn) {
                itemSize = "large";
                buttonColor(new JButton[]{regularBtn, mediumBtn}, largeBtn);
            } else if (e.getSource() == dineInBtn) {
                orderType = "dine-in";
                buttonColor(new JButton[]{deliveryBtn, takeOutBtn}, dineInBtn);
            } else if (e.getSource() == takeOutBtn) {
                orderType = "take-out";
                buttonColor(new JButton[]{dineInBtn, deliveryBtn}, takeOutBtn);
            } else if (e.getSource() == deliveryBtn) {
                orderType = "delivery";
                buttonColor(new JButton[]{dineInBtn, takeOutBtn}, deliveryBtn);
            } else if (e.getSource() == printReceiptBtn) {
                if (receiptModel.getRowCount() > 0) {
                    if (!orderType.equals("")) {
                        if (!customerNameTf.getText().isBlank()) {
                            if (!(orderType.equals("delivery") && customerAddressTf.getText().isBlank() && customerNumberTf.getText().isBlank()) == true) {
                                if (!paymentTf.getText().isBlank()) {
                                    computeChange();
                                    if (change >= 0) {
                                        receiptTa.setText("");
                                        receiptTa.append("==============================================\n\n"
                                                + "\t" + DataBaseConnection.getRestaurantName() + "\t\n\n\n"
                                                + "Staff Name : " + getStaffName() + "\t\n"
                                                + "Transaction Date : " + getDateToday() + "\t\n"
                                                + "==============================================\n\n"
                                                + "\t--ORDER DETAILS--\n");
                                        for (int i = 0; i < receiptModel.getRowCount(); i++) {
                                            int quantity = Integer.parseInt(receiptModel.getValueAt(i, 0).toString());
                                            String itemName = receiptModel.getValueAt(i, 1).toString();
                                            String size = receiptModel.getValueAt(i, 2).toString();
                                            double price = Double.parseDouble(receiptModel.getValueAt(i, 3).toString());
                                            checkStock(quantity, itemName, size);
                                            if (stockInsufficient == true) {
                                                setToDefault();
                                                break;
                                            }
                                        }
                                        if (stockInsufficient == false) {
                                            for (int i = 0; i < receiptModel.getRowCount(); i++) {
                                                int quantity = Integer.parseInt(receiptModel.getValueAt(i, 0).toString());
                                                String itemName = receiptModel.getValueAt(i, 1).toString();
                                                String size = receiptModel.getValueAt(i, 2).toString();
                                                double price = Double.parseDouble(receiptModel.getValueAt(i, 3).toString());
                                                receiptTa.append(quantity + " x " + size + " " + itemName + "\t\t : " + price + "\n");
                                            }
                                            deductStock();
                                            discountType = hasDiscount ? "20%" : "NONE";
                                            receiptTa.append("\n\n==============================================\n\n"
                                                    + "ORDER TYPE : " + orderType.toUpperCase() + "\n"
                                                    + subTotalTf.getText() + "\n"
                                                    + "DISCOUNT :\t" + discountType + "\n"
                                                    + totalTf.getText() + "\n"
                                                    + "PAYMENT :\t" + paymentTf.getText() + "\n"
                                                    + "CHANGE :\t" + df.format(change) + "\n\n"
                                                    + "==============================================\n\n"
                                                    + "\tCUSTOMER DETAILS\n"
                                                    + "NAME :\t" + customerNameTf.getText() + "\n"
                                                    + "ADDRESS :\t" + customerAddressTf.getText() + "\n"
                                                    + "NUMBER :\t" + customerNumberTf.getText() + "\n\n"
                                                    + "==============================================\n\n"
                                                    + "THANK YOU! PLEASE COME AGAIN.\n\n");
                                            JOptionPane.showMessageDialog(homePOSmainpanel, receiptTa);
                                            Dashboard.updateOverview();
                                            try {
                                                receiptTa.print();
                                                Products.retrieveStockData();
                                            } catch (PrinterException ex) {
                                                Logger.getLogger(HomePOS.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        setToDefault();
                                    } else {
                                        JOptionPane.showMessageDialog(homePOSmainpanel, "Cannot Proceed...\nPayment Insufficient", "PAYMENT", 0);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(homePOSmainpanel, "Enter Payment", "PAYMENT", 0);
                                }
                            } else {
                                JOptionPane.showMessageDialog(homePOSmainpanel, "Enter Customer ADDRESS and NUMBER", "Customer Details", 0);
                            }
                        } else {
                            JOptionPane.showMessageDialog(homePOSmainpanel, "Enter Customer Name", "Customer Details", 0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(homePOSmainpanel, "Choose From The Following Type Of Order First...\nDINE-IN\nTAKE-OUT\nDELIVERY", "ORDER TYPE", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(homePOSmainpanel, "Cannot Proceed....\nNo Item Ordered", "Customer Details", 0);
                }
            } else if (e.getSource() == voidBtn) {
                setToDefault();
                putCategoryAndItemToButtons();
            } else if (e.getSource() == discountBtn) {
                if (computeTotalPrice() != 0) {
                    if (hasDiscount == false) {
                        totalTf.setText("TOTAL :\t" + df.format(computeTotalPrice() - computeTotalPrice() * 0.20));
                        hasDiscount = true;
                        discountBtn.setBackground(dark);
                    } else if (hasDiscount == true) {
                        totalTf.setText("TOTAL :\t" + df.format(computeTotalPrice()));
                        hasDiscount = false;
                        discountBtn.setBackground(red);
                    }
                }
            }

            for (int i = 0; i < categoryButtonList.size(); i++) {
                if (e.getSource() == categoryButtonList.get(i)) {
                    System.out.println("its the " + e.getActionCommand() + " button " + i);
                    for (int j = 0; j < panelList.size(); j++) {
                        if (j == i) {
                            panelList.get(j).setVisible(true);
                        } else {
                            panelList.get(j).setVisible(false);
                        }
                    }
                }
            }

            //logic for getting the item pressed
            //loop to get possible actions on items
            for (int i = 0; i < itemButtonList.size(); i++) {
                boolean isAlreadyPresent = false;
                double price = 0;
                //getting only the items buttons actions
                if (e.getSource() == itemButtonList.get(i)) {
                    if (!itemSize.isBlank()) {
                        //knowing if the button pressed and its size is already present in table
                        for (int k = 0; k < receiptModel.getRowCount(); k++) {
                            if (receiptModel.getValueAt(k, 1).toString().equals(e.getActionCommand()) && receiptModel.getValueAt(k, 2).toString().equals(itemSize)) {
                                System.out.println("Already Present : " + e.getActionCommand() + " " + itemSize + " row : " + k);
                                int previousQuantity = Integer.parseInt(receiptModel.getValueAt(k, 0).toString());
                                if (!quantityTf.getText().isBlank()) {
                                    receiptModel.setValueAt(previousQuantity + Integer.parseInt(quantityTf.getText()), k, 0);
                                    quantityTf.setText("");
                                } else {
                                    receiptModel.setValueAt(previousQuantity + 1, k, 0);
                                }
                                receiptModel.setValueAt(e.getActionCommand(), k, 1);
                                receiptModel.setValueAt(itemSize, k, 2);
                                int currentQuantity = Integer.parseInt(receiptModel.getValueAt(k, 0).toString());
                                receiptModel.setValueAt(returnItemPrice(e.getActionCommand()) * currentQuantity, k, 3);
                                isAlreadyPresent = true;
                            }
                        }

                        //condition to know if the last button pressed is the same with the current button pressed
                        // condition to know if it is still the same item but different size then its another row
                        if (prevButtonNumber != i && !isAlreadyPresent || !itemSize.equals(prevSize) && !isAlreadyPresent) {
                            quantityCounter = 0;
                        }
                        quantityCounter++; //incrementing the quantity which is 0 by default

                        //condition if the quantity has now a value 
                        if (quantityCounter == 1 && !isAlreadyPresent) {
                            //it'll be added as a row on receipt table 
                            //with the details of quantity, item name, size, its price total based on quantity
                            price = returnItemPrice(e.getActionCommand());
                            if (price != 0) {
                                if (!quantityTf.getText().isBlank()) {
                                    receiptModel.addRow(new Object[]{Integer.parseInt(quantityTf.getText()), e.getActionCommand(),
                                        itemSize, price * Integer.parseInt(quantityTf.getText())});
                                    quantityTf.setText("");
                                } else {
                                    receiptModel.addRow(new Object[]{quantityCounter, e.getActionCommand(),
                                        itemSize, price * quantityCounter});
                                }
                            } else {
                                JOptionPane.showMessageDialog(homePOSmainpanel, itemSize + " size is NOT AVAILABLE for this item", "NOT AVAILABLE", 0);
                                quantityCounter = 0;
                            }
                        }

                        //passing the current value to another variable that will be used to check in the condition above
                        prevSize = itemSize;
                        prevButtonNumber = i;

                        //displaying the total in textfields
                        subTotalTf.setText("SUB TOTAL :\t" + df.format(computeSubTotalPrice()));
                        if (hasDiscount) {
                            totalTf.setText("TOTAL :\t" + df.format(computeTotalPrice() - computeTotalPrice() * 0.20));
                        } else {
                            totalTf.setText("TOTAL :\t" + df.format(computeTotalPrice()));
                        }
                        System.out.println("size : " + itemSize + " item : " + e.getActionCommand()
                                + " counter : " + quantityCounter + " is already present? " + isAlreadyPresent);
                    } else {
                        JOptionPane.showMessageDialog(homePOSmainpanel, "Please Select A Size First...", "SIZES", 0);
                    }
                }
            }
        }
    }

    private static class CustomKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            for (JTextField iterator : fields) {
                if (e.getSource() == iterator) {
                    if (iterator == customerNumberTf && customerNumberTf.getText().length() == 11
                            || e.getKeyChar() == java.awt.event.KeyEvent.VK_BACK_SPACE) {
                        e.consume();
                    } else if (iterator == quantityTf && quantityTf.getText().length() == 3
                            || e.getKeyChar() == java.awt.event.KeyEvent.VK_BACK_SPACE) {
                        e.consume();
                    } else if (iterator == customerNameTf && Character.isDigit(e.getKeyChar())) {
                        e.consume();
                    } else if (!(Character.isDigit(e.getKeyChar()))
                            && iterator != customerNameTf && iterator != customerAddressTf) {
                        e.consume();
                    }
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    public static String getDateToday() {
        return LocalDate.now().toString();
    }

    public static String getStaffName() {
        return User.getUserFullName();
    }

    public static String getRestaurantName() {
        return null;
    }

    public static double computeSubTotalPrice() {
        double subTotal = 0;
        for (int i = 0; i < receiptTable.getModel().getRowCount(); i++) {
            subTotal += Double.parseDouble(receiptTable.getModel().getValueAt(i, 3).toString());
        }
        return subTotal;
    }

    public static double computeTotalPrice() {
        double total = 0;
        for (int i = 0; i < receiptTable.getModel().getRowCount(); i++) {
            total += Double.parseDouble(receiptTable.getModel().getValueAt(i, 3).toString());
        }

        return total;
    }

    public static double returnItemPrice(String itemName) {
        try {
            DataBaseConnection db = new DataBaseConnection();
            Connection con = db.getConnection();
            String getPrice = "SELECT " + itemSize + "_price FROM products.category_items WHERE item_name = ?;";
            PreparedStatement statement = con.prepareStatement(getPrice);
            statement.setString(1, itemName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                double price = rs.getDouble(itemSize + "_price");
                return price;
            }
        } catch (Exception ex) {
            Logger.getLogger(HomePOS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static void putCategoryAndItemToButtons() {
        try {
            categoriesPanel.removeAll();
            itemsPanel.removeAll();

            panelList.clear();
            itemButtonList.clear();
            categoryButtonList.clear();

            DataBaseConnection db = new DataBaseConnection();
            String category_name = "";
            int catId = 0;
            //query to get the category names
            Connection con = db.getConnection();
            String getAllCategory = "SELECT * FROM products.categories";
            PreparedStatement statement = con.prepareStatement(getAllCategory);
            ResultSet rs = statement.executeQuery();

            String item_name = "";
            int category = 0;
            //query to get the item names
            String getAllItem = "SELECT * FROM products.category_items";
            //adding additional parameters in preparestatement to allow us to scroll again in resultset 
            PreparedStatement statementt = con.prepareStatement(getAllItem, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss = statementt.executeQuery();

            int j = 0;
            int i = 0;
            //loop to get the category name
            while (rs.next()) {
                category_name = rs.getString("category_name"); //storing category name on a variable
                catId = rs.getInt("category_id");
                System.out.println(catId + " " + category_name);
                categoryButtonList.add(new JButton(category_name)); //adding the category as a button in arrayList of buttons for categories
                //setting it's properties
                categoryButtonList.get(i).setBackground(violet);
                categoryButtonList.get(i).setForeground(Color.white);
                categoryButtonList.get(i).setFont(big);
                categoryButtonList.get(i).addActionListener(new CustomActionListener()); //adding actions to it
                //adding it on our panel 
                categoriesPanel.add(categoryButtonList.get(i));
                //creating a new panel for each category name
                //it'll be hidden or visible once they're pressed
                panelList.add(new JPanel(new GridLayout(4, 4, 5, 5)));
                panelList.get(i).setBounds(10, 10, 700, 230);
                panelList.get(i).setBackground(violet);
                if (i == 0) {
                    panelList.get(i).setVisible(true);
                } else {
                    panelList.get(i).setVisible(false);
                }
                //loop to get the item name
                rss.beforeFirst(); //scrolling from the top of resultset
                while (rss.next()) {
                    item_name = rss.getString("item_name"); //storing item name on a variable
                    category = rss.getInt("category_id");
                    System.out.println(category + " " + item_name);
                    itemButtonList.add(new JButton(item_name)); //adding the item as a button in our arrayList of buttons for items
                    //setting it's properties
                    itemButtonList.get(j).setBackground(Color.white);
                    itemButtonList.get(j).setForeground(dark);
                    itemButtonList.get(j).addActionListener(new CustomActionListener());
                    itemButtonList.get(j).setFont(new Font("Dialog", Font.BOLD, 15));
                    //this condition determines if the item is related to the category
                    if (catId == category) {
                        //if condition is true then the item will be added on the created panel
                        panelList.get(i).add(itemButtonList.get(j));
                    }
                    refreshFrame(homePOSmainframe); //refreshing frame
                    j++; //increment the counter variable in inner loop
                }
                //lastly, after iterating on all the items and getting all related items in the current category name above
                //in items panel we add the created panel that has the item related to category
                itemsPanel.add(panelList.get(i));

                refreshFrame(homePOSmainframe); //refreshing frame to get the updated positions of components
                i++; //increment the counter variable in outer loop
            }
            //closing statements
            statementt.close();
            statement.close();
            refreshComponent(categoriesPanel); //repaint revalidate the categories panel
        } catch (Exception ex) {
            Logger.getLogger(HomePOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void checkStock(int deductionCount, String itemName, String itemSize) {
        try {
            ItemOperations item = new ItemOperations();
            int itemId = item.getItemCategoryId(itemName, "item");

            Connection con = DataBaseConnection.getConnection();
            int deductionPerSize = 0;
            PreparedStatement stocks = con.prepareStatement("SELECT " + itemSize + "_deduction FROM products.items_stocks WHERE item_id = ?;");
            stocks.setInt(1, itemId);
            ResultSet result = stocks.executeQuery();
            while (result.next()) {
                deductionPerSize = result.getInt(itemSize + "_deduction");
            }

            int current_stock = 0;
            stocks = con.prepareStatement("SELECT stocks_count FROM products.items_stocks WHERE item_id = ?;");
            stocks.setInt(1, itemId);
            result = stocks.executeQuery();
            while (result.next()) {
                current_stock = result.getInt("stocks_count");
            }
            int finalDeduction = deductionCount * deductionPerSize;
            int updatedStock = current_stock - finalDeduction;
            System.out.println("deduc per size : " + deductionPerSize);
            System.out.println("deduct is : " + finalDeduction);
            System.out.println("updated is : " + updatedStock);
            if (updatedStock >= 0) {
                itemQuantityOrdered.add(String.valueOf(deductionCount));
                itemNameOrdered.add(itemName);
                itemSizeOrdered.add(itemSize);
                stockToDeductList.add(updatedStock);
                itemIdList.add(itemId);
                stockInsufficient = false;
            } else {
                JOptionPane.showMessageDialog(homePOSmainpanel, "Cannot Process Order..."
                        + "\nCurrent Stock For " + itemName + " : " + current_stock
                        + "\nInsufficient For " + deductionCount + " " + itemSize + " order(s)",
                        "INSUFFICIENT STOCK", 0);
                stockInsufficient = true;
            }
            stocks.close();
        } catch (Exception ex) {
            Logger.getLogger(HomePOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deductStock() {
        try {
            Connection con = DataBaseConnection.getConnection();
            for (int i = 0; i < itemIdList.size(); i++) {
                PreparedStatement stocks = con.prepareStatement("UPDATE products.items_stocks SET stocks_count = ? WHERE item_id = ?;");
                stocks.setInt(1, stockToDeductList.get(i));
                stocks.setInt(2, itemIdList.get(i));
                stocks.executeUpdate();
                stocks.close();
            }

            //saving customer and order details;
            CustomerAndOrder customerOrder = new CustomerAndOrder();
            customerOrder.setName(customerNameTf.getText());
            customerOrder.setAddress(customerAddressTf.getText());
            customerOrder.setNumber(customerNumberTf.getText());
            customerOrder.setTotalPrice(Double.parseDouble(totalTf.getText().replace("TOTAL :\t", "")));
            customerOrder.setDiscount(hasDiscount == true ? Double.valueOf(df.format(computeTotalPrice() * 0.20)) : 0);
            customerOrder.setDate(getDateToday());
            customerOrder.saveCustomer();

            for (int i = 0; i < itemNameOrdered.size(); i++) {
                customerOrder.setItemOrdered(itemNameOrdered.get(i));
                customerOrder.setItemQuantities(itemQuantityOrdered.get(i));
                customerOrder.setItemSizes(itemSizeOrdered.get(i));
                customerOrder.saveOrder();
            }

            itemNameOrdered.clear();
            itemQuantityOrdered.clear();
            itemIdList.clear();
            stockToDeductList.clear();
        } catch (Exception ex) {
            Logger.getLogger(HomePOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void computeChange() {
        String total = totalTf.getText();
        total = total.replace("TOTAL :\t", "");
        change = Double.parseDouble(paymentTf.getText()) - Double.parseDouble(total);
    }

    //utility functions
    public static void fieldsToArray(JTextField[] textfields) {
        for (JTextField iterator : textfields) {
            fields.add(iterator);
            iterator.addKeyListener(new CustomKeyListener());
        }
    }

    public static void refreshFrame(JFrame main) {
        main.revalidate();
        main.repaint();
    }

    public static void refreshComponent(JComponent component) {
        component.revalidate();
        component.repaint();
    }

    public static void buttonColor(JButton[] red, JButton dark) {
        for (JButton jButton : red) {
            jButton.setBackground(HomePOS.red);
        }
        dark.setBackground(HomePOS.dark);
    }

    public static void setToDefault() {
        for (JTextField i : fields) {
            i.setText("");
        }
        totalTf.setText("TOTAL :");
        subTotalTf.setText("SUB TOTAL :");
        receiptTa.setText("");
        orderType = "";
        itemSize = "";
        hasDiscount = false;
        receiptModel.setRowCount(0);
        buttonColor(new JButton[]{mediumBtn, largeBtn, regularBtn}, new JButton());
        buttonColor(new JButton[]{deliveryBtn, takeOutBtn, dineInBtn}, new JButton());
        discountBtn.setBackground(red);
        quantityCounter = 0;
    }
}
