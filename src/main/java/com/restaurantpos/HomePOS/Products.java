package com.restaurantpos.HomePOS;

import static com.restaurantpos.HomePOS.HomePOS.dark;
import static com.restaurantpos.HomePOS.HomePOS.red;
import static com.restaurantpos.HomePOS.HomePOS.violet;
import com.restaurantpos.LoginAndSignUp.DataBaseConnection;
import static com.restaurantpos.LoginAndSignUp.LoginSignUpGui.lightest;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Products {

    static JPanel productsMainPanel/*, pan1, pan2, pan3*/;
    JPanel categoryPanel, actionsPanel, itemPanel, stockPanel;
    JButton addBtn, updateBtn, removeBtn, categoryBtn, stocksBtn, itemsBtn;
    JLabel productsLbl, categoryLbl, itemNameLbl, regPriceLbl, medPriceLbl, lrgPriceLbl, stockCountLbl, stockUnitLbl, regDeductionLbl, medDeductionLbl, lrgDeductionLbl;
    JTextField categoryTf, itemNameTf, regPriceTf, medPriceTf, lrgPriceTf, stockCountTf, stockUnitTf, regDeductionTf, medDeductionTf, lrgDeductionTf, stockItemNameTf;

    JTable categoryTable, itemTable, stockTable;
    DefaultTableModel catModel, itemModel;
    static DefaultTableModel stockModel;
    JScrollPane catScroll, itemScroll, stockScroll;

    JComboBox<String> categoryNameCombo;
    JLabel catNameCBLbl, itemNameCBlbl;

    static Font big = new Font("Dialog", Font.BOLD, 30);
    static Font med = new Font("Dialog", Font.BOLD, 15);
    static Font mid = new Font("Dialog", Font.BOLD, 20);

    DataBaseConnection connect = new DataBaseConnection();
    ArrayList<JTextField> fieldsForNumbers = new ArrayList<>();
    ArrayList<JTextField> fieldsForWords = new ArrayList<>();

    //categoryPanel
    int categoryId = 0;
    //itemPanel
    int itemId = 0;
    String prevItemName = "";
    //stock panel
    int stockId = 0;

    public Products() {
        productsMainPanel = new JPanel();
        /*pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();*/
        categoryPanel = new JPanel();
        itemPanel = new JPanel();
        stockPanel = new JPanel();
        actionsPanel = new JPanel();

        addBtn = new JButton();
        updateBtn = new JButton();
        removeBtn = new JButton();
        categoryBtn = new JButton();
        stocksBtn = new JButton();
        itemsBtn = new JButton();

        productsLbl = new JLabel();
        categoryLbl = new JLabel();
        itemNameLbl = new JLabel();
        regPriceLbl = new JLabel();
        medPriceLbl = new JLabel();
        lrgPriceLbl = new JLabel();
        regDeductionLbl = new JLabel();
        medDeductionLbl = new JLabel();
        lrgDeductionLbl = new JLabel();
        stockCountLbl = new JLabel();
        stockUnitLbl = new JLabel();

        categoryTf = new JTextField();
        itemNameTf = new JTextField();
        regPriceTf = new JTextField();
        medPriceTf = new JTextField();
        lrgPriceTf = new JTextField();
        regDeductionTf = new JTextField();
        medDeductionTf = new JTextField();
        lrgDeductionTf = new JTextField();
        stockCountTf = new JTextField();
        stockUnitTf = new JTextField();
        stockItemNameTf = new JTextField();

        catModel = new DefaultTableModel();
        itemModel = new DefaultTableModel();
        stockModel = new DefaultTableModel();

        categoryNameCombo = new JComboBox<>();
        catNameCBLbl = new JLabel();
        itemNameCBlbl = new JLabel();
    }

    public void setProductsGui() {
        fieldsToArray(new JTextField[]{stockCountTf, regDeductionTf, medDeductionTf, lrgDeductionTf}, "number");
        fieldsToArray(new JTextField[]{categoryTf, itemNameTf, stockUnitTf}, "word");
        //tables
        String[] catColumnNames = {"CATEGORY_ID", "CATEGORY_NAME"};
        catModel.setColumnIdentifiers(catColumnNames);
        categoryTable = new JTable(catModel);
        categoryTable.setFont(new Font("Dialog", Font.PLAIN, 15));
        
        categoryTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 15));
        categoryTable.getTableHeader().setForeground(dark);
        categoryTable.getTableHeader().setReorderingAllowed(false);
        
        categoryTable.setDefaultEditor(Object.class, null);
        categoryTable.getTableHeader().setReorderingAllowed(false);
        categoryTable.setForeground(dark);
        categoryTable.setRowHeight(categoryTable.getRowHeight() + 5);
        catScroll = new JScrollPane(categoryTable);
        catScroll.getVerticalScrollBar().setBackground(violet);
        catScroll.setBounds(20, 20, 700, 220);

        String[] itemColumnNames = {"ITEM_ID", "CATEGORY_ID", "ITEM_NAME", "REG_PRICE", "MED_PRICE", "LRG_PRICE"};
        itemModel.setColumnIdentifiers(itemColumnNames);
        itemTable = new JTable(itemModel);
        itemTable.setFont(new Font("Dialog", Font.PLAIN, 15));
        itemTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 15));
        itemTable.getTableHeader().setForeground(dark);
        itemTable.setDefaultEditor(Object.class, null);
        itemTable.getTableHeader().setReorderingAllowed(false);
        itemTable.setForeground(dark);
        itemTable.setRowHeight(itemTable.getRowHeight() + 5);
        itemScroll = new JScrollPane(itemTable);
        itemScroll.getVerticalScrollBar().setBackground(violet);
        itemScroll.setBounds(20, 20, 700, 220);

        String[] stockColumnNames = {"stock_id", "item_name", "stock_count", "reg_deduction", "med_deduction", "lrg_deduction", "stock_unit"};
        stockModel.setColumnIdentifiers(stockColumnNames);
        stockTable = new JTable(stockModel);
        stockTable.setFont(new Font("Dialog", Font.PLAIN, 15));
        stockTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
        stockTable.getTableHeader().setForeground(dark);
        stockTable.setDefaultEditor(Object.class, null);
        stockTable.getTableHeader().setReorderingAllowed(false);
        stockTable.setForeground(dark);
        stockTable.setRowHeight(stockTable.getRowHeight() + 5);
        stockScroll = new JScrollPane(stockTable);
        stockScroll.getVerticalScrollBar().setBackground(violet);
        stockScroll.setBounds(20, 20, 700, 220);

        //combobox
        categoryNameCombo.setBounds(450, 265, 180, 25);
        categoryNameCombo.addItem("Meals");
        categoryNameCombo.addItem("Drinks");
        categoryNameCombo.setFocusable(false);
        categoryNameCombo.setBackground(lightest);
        categoryNameCombo.setForeground(violet);
        categoryNameCombo.setFont(med);

        stockItemNameTf.setBounds(355, 260, 180, 30);
        stockItemNameTf.setBackground(lightest);
        stockItemNameTf.setForeground(violet);
        stockItemNameTf.setFont(med);
        stockItemNameTf.setEditable(false);

        //textfield
        lrgDeductionTf.setText("");
        lrgDeductionTf.setBounds(620, 305, 100, 30);
        lrgDeductionTf.setForeground(violet);
        lrgDeductionTf.setFont(med);
        lrgDeductionTf.setMargin(new Insets(0, 10, 0, 10));
        lrgDeductionTf.setEditable(false);

        medDeductionTf.setText("");
        medDeductionTf.setBounds(380, 305, 100, 30);
        medDeductionTf.setForeground(violet);
        medDeductionTf.setFont(med);
        medDeductionTf.setMargin(new Insets(0, 10, 0, 10));
        medDeductionTf.setEditable(false);

        regDeductionTf.setText("");
        regDeductionTf.setBounds(140, 305, 100, 30);
        regDeductionTf.setForeground(violet);
        regDeductionTf.setFont(med);
        regDeductionTf.setMargin(new Insets(0, 10, 0, 10));

        stockUnitTf.setText("");
        stockUnitTf.setBounds(650, 260, 70, 30);
        stockUnitTf.setForeground(violet);
        stockUnitTf.setFont(med);
        stockUnitTf.setMargin(new Insets(0, 10, 0, 10));

        stockCountTf.setText("");
        stockCountTf.setBounds(120, 260, 120, 30);
        stockCountTf.setForeground(violet);
        stockCountTf.setFont(med);
        stockCountTf.setMargin(new Insets(0, 10, 0, 10));

        lrgPriceTf.setText("");
        lrgPriceTf.setFont(med);
        lrgPriceTf.setBounds(620, 308, 100, 25);
        lrgPriceTf.setBackground(lightest);
        lrgPriceTf.setForeground(violet);
        lrgPriceTf.setMargin(new Insets(0, 10, 0, 10));

        medPriceTf.setText("");
        medPriceTf.setFont(med);
        medPriceTf.setBounds(385, 308, 100, 25);
        medPriceTf.setBackground(lightest);
        medPriceTf.setForeground(violet);
        medPriceTf.setMargin(new Insets(0, 10, 0, 10));

        regPriceTf.setText("");
        regPriceTf.setFont(med);
        regPriceTf.setBounds(135, 308, 100, 25);
        regPriceTf.setBackground(lightest);
        regPriceTf.setForeground(violet);
        regPriceTf.setMargin(new Insets(0, 10, 0, 10));

        itemNameTf.setText("");
        itemNameTf.setFont(med);
        itemNameTf.setBounds(110, 265, 180, 25);
        itemNameTf.setBackground(lightest);
        itemNameTf.setForeground(violet);
        itemNameTf.setMargin(new Insets(0, 10, 0, 10));

        categoryTf.setText("");
        categoryTf.setFont(med);
        categoryTf.setBounds(20, 300, 250, 30);
        categoryTf.setBackground(lightest);
        categoryTf.setForeground(violet);
        categoryTf.setMargin(new Insets(0, 10, 0, 10));

        //labels        
        medDeductionLbl.setText("Med_Deduction");
        medDeductionLbl.setBounds(260, 300, 150, 40);
        medDeductionLbl.setForeground(lightest);
        medDeductionLbl.setFont(med);

        lrgDeductionLbl.setText("Lrg_Deduction");
        lrgDeductionLbl.setBounds(505, 300, 150, 40);
        lrgDeductionLbl.setForeground(lightest);
        lrgDeductionLbl.setFont(med);

        regDeductionLbl.setText("Reg_Deduction");
        regDeductionLbl.setBounds(20, 300, 150, 40);
        regDeductionLbl.setForeground(lightest);
        regDeductionLbl.setFont(med);

        stockUnitLbl.setText("Stock Unit");
        stockUnitLbl.setBounds(565, 255, 150, 40);
        stockUnitLbl.setForeground(lightest);
        stockUnitLbl.setFont(med);

        itemNameCBlbl.setText("Item Name");
        itemNameCBlbl.setBounds(270, 255, 150, 40);
        itemNameCBlbl.setForeground(lightest);
        itemNameCBlbl.setFont(med);

        stockCountLbl.setText("Stock Count");
        stockCountLbl.setBounds(20, 255, 150, 40);
        stockCountLbl.setForeground(lightest);
        stockCountLbl.setFont(med);

        regPriceLbl.setText("Regular Price");
        regPriceLbl.setBounds(25, 300, 150, 40);
        regPriceLbl.setForeground(lightest);
        regPriceLbl.setFont(med);

        medPriceLbl.setText("Medium Price");
        medPriceLbl.setBounds(275, 300, 150, 40);
        medPriceLbl.setForeground(lightest);
        medPriceLbl.setFont(med);

        lrgPriceLbl.setText("Large Price");
        lrgPriceLbl.setBounds(525, 300, 150, 40);
        lrgPriceLbl.setForeground(lightest);
        lrgPriceLbl.setFont(med);

        catNameCBLbl.setText("Category Name");
        catNameCBLbl.setBounds(325, 255, 150, 40);
        catNameCBLbl.setForeground(lightest);
        catNameCBLbl.setFont(med);

        itemNameLbl.setText("Item Name");
        itemNameLbl.setBounds(25, 255, 150, 40);
        itemNameLbl.setForeground(lightest);
        itemNameLbl.setFont(med);

        categoryLbl.setText("Category Name");
        categoryLbl.setBounds(20, 255, 150, 40);
        categoryLbl.setForeground(lightest);
        categoryLbl.setFont(mid);

        productsLbl.setText("Products");
        productsLbl.setBounds(300, 30, 150, 40);
        productsLbl.setForeground(lightest);
        productsLbl.setFont(big);

        //buttons
        ImageIcon addIcon = new ImageIcon("src/main/java/images/add.png");
        addBtn.setText("ADD");
        addBtn.setBounds(10, 10, 230, 60);
        addBtn.setBackground(red);
        addBtn.setForeground(lightest);
        addBtn.setFont(med);
        addBtn.setIcon(addIcon);
        addBtn.setIconTextGap(10);
        addBtn.setFocusable(false);
        addBtn.setBorderPainted(false);

        ImageIcon updateIcon = new ImageIcon("src/main/java/images/update.png");
        updateBtn.setText("UPDATE");
        updateBtn.setBounds(255, 10, 230, 60);
        updateBtn.setBackground(red);
        updateBtn.setForeground(lightest);
        updateBtn.setFont(med);
        updateBtn.setIcon(updateIcon);
        updateBtn.setIconTextGap(10);
        updateBtn.setFocusable(false);
        updateBtn.setBorderPainted(false);

        ImageIcon removeIcon = new ImageIcon("src/main/java/images/remove.png");
        removeBtn.setText("REMOVE");
        removeBtn.setBounds(500, 10, 230, 60);
        removeBtn.setBackground(red);
        removeBtn.setForeground(lightest);
        removeBtn.setFont(med);
        removeBtn.setIcon(removeIcon);
        removeBtn.setIconTextGap(10);
        removeBtn.setFocusable(false);
        removeBtn.setBorderPainted(false);

        ImageIcon categoryIcon = new ImageIcon("src/main/java/images/category.png");
        categoryBtn.setText("CATEGORIES");
        categoryBtn.setBounds(20, 100, 240, 100);
        categoryBtn.setBackground(violet);
        categoryBtn.setForeground(lightest);
        categoryBtn.setFont(med);
        categoryBtn.setIcon(categoryIcon);
        categoryBtn.setIconTextGap(10);
        categoryBtn.setFocusable(false);
        categoryBtn.setBorderPainted(false);

        ImageIcon itemsIcon = new ImageIcon("src/main/java/images/items.png");
        itemsBtn.setText("ITEMS");
        itemsBtn.setBounds(270, 100, 240, 100);
        itemsBtn.setBackground(violet);
        itemsBtn.setForeground(lightest);
        itemsBtn.setFont(med);
        itemsBtn.setIcon(itemsIcon);
        itemsBtn.setIconTextGap(10);
        itemsBtn.setFocusable(false);
        itemsBtn.setBorderPainted(false);

        ImageIcon stocksIcon = new ImageIcon("src/main/java/images/stocks.png");
        stocksBtn.setText("STOCKS");
        stocksBtn.setBounds(520, 100, 240, 100);
        stocksBtn.setBackground(violet);
        stocksBtn.setForeground(lightest);
        stocksBtn.setFont(med);
        stocksBtn.setIcon(stocksIcon);
        stocksBtn.setIconTextGap(10);
        stocksBtn.setFocusable(false);
        stocksBtn.setBorderPainted(false);

        //adding in panels          
        stockPanel.add(itemNameCBlbl);
        stockPanel.add(stockItemNameTf);
        stockPanel.add(lrgDeductionLbl);
        stockPanel.add(lrgDeductionTf);
        stockPanel.add(medDeductionTf);
        stockPanel.add(medDeductionLbl);
        stockPanel.add(regDeductionTf);
        stockPanel.add(regDeductionLbl);
        stockPanel.add(stockCountTf);
        stockPanel.add(stockUnitTf);
        stockPanel.add(stockUnitLbl);
        stockPanel.add(stockCountLbl);
        stockPanel.add(stockScroll);

        itemPanel.add(catNameCBLbl);
        itemPanel.add(categoryNameCombo);
        itemPanel.add(lrgPriceTf);
        itemPanel.add(medPriceTf);
        itemPanel.add(regPriceTf);
        itemPanel.add(itemNameTf);
        itemPanel.add(lrgPriceLbl);
        itemPanel.add(medPriceLbl);
        itemPanel.add(regPriceLbl);
        itemPanel.add(itemNameLbl);
        itemPanel.add(itemScroll);

        actionsPanel.add(addBtn);
        actionsPanel.add(updateBtn);
        actionsPanel.add(removeBtn);

        categoryPanel.add(categoryLbl);
        categoryPanel.add(categoryTf);
        categoryPanel.add(catScroll);

        productsMainPanel.add(actionsPanel);
        productsMainPanel.add(stockPanel);
        productsMainPanel.add(itemPanel);
        productsMainPanel.add(categoryPanel);
        productsMainPanel.add(stocksBtn);
        productsMainPanel.add(itemsBtn);
        productsMainPanel.add(categoryBtn);
        productsMainPanel.add(productsLbl);

        //panels
        stockPanel.setLayout(null);
        stockPanel.setBounds(20, 210, 740, 350);
        stockPanel.setBackground(violet);
        stockPanel.setVisible(false);

        itemPanel.setLayout(null);
        itemPanel.setBounds(20, 210, 740, 350);
        itemPanel.setBackground(violet);
        itemPanel.setVisible(false);

        actionsPanel.setLayout(null);
        actionsPanel.setBounds(20, 570, 740, 80);
        actionsPanel.setBackground(violet);

        categoryPanel.setLayout(null);
        categoryPanel.setBounds(20, 210, 740, 350);
        categoryPanel.setBackground(violet);
        categoryPanel.setVisible(false);

        productsMainPanel.setLayout(null);
        productsMainPanel.setBounds(200, 0, 800, 700);
        productsMainPanel.setBackground(dark);
        productsMainPanel.setVisible(false);

        hideShowStockTextFields(false);
        retrieveCategoryData();
        retrieveItemData();
        retrieveStockData();
    }

    public void addActions() {
        categoryBtn.addActionListener(new CustomActionListener());
        itemsBtn.addActionListener(new CustomActionListener());
        stocksBtn.addActionListener(new CustomActionListener());
        addBtn.addActionListener(new CustomActionListener());
        updateBtn.addActionListener(new CustomActionListener());
        removeBtn.addActionListener(new CustomActionListener());
    }

    public void addClicks() {
        categoryTable.addMouseListener(new CustomMouseListener());
        itemTable.addMouseListener(new CustomMouseListener());
        stockTable.addMouseListener(new CustomMouseListener());
        Dashboard.dashboardFrame.addMouseListener(new CustomMouseListener());
    }

    class CustomActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == categoryBtn) {
                hideShowPanels(itemPanel, stockPanel, categoryPanel);
                showAddRemoveButton();
            } else if (e.getSource() == itemsBtn) {
                hideShowPanels(categoryPanel, stockPanel, itemPanel);
                showAddRemoveButton();
            } else if (e.getSource() == stocksBtn) {
                hideShowPanels(itemPanel, categoryPanel, stockPanel);
                hideAddRemoveButton();
            } else if (e.getSource() == addBtn) {
                addingDatas();
            } else if (e.getSource() == updateBtn) {
                updatingDatas();
            } else if (e.getSource() == removeBtn) {
                deletingDatas();
            }
        }

    }

    class CustomMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == categoryTable && e.getClickCount() == 2) {
                categoryId = Integer.parseInt(catModel.getValueAt(categoryTable.getSelectedRow(), 0).toString());
                categoryTf.setText(catModel.getValueAt(categoryTable.getSelectedRow(), 1).toString());
            } else if (e.getSource() == itemTable && e.getClickCount() == 2) {
                CategoryOperations cat = new CategoryOperations();
                itemId = Integer.parseInt(itemModel.getValueAt(itemTable.getSelectedRow(), 0).toString());
                itemNameTf.setText(itemModel.getValueAt(itemTable.getSelectedRow(), 2).toString());
                regPriceTf.setText(itemModel.getValueAt(itemTable.getSelectedRow(), 3).toString());
                medPriceTf.setText(itemModel.getValueAt(itemTable.getSelectedRow(), 4).toString());
                lrgPriceTf.setText(itemModel.getValueAt(itemTable.getSelectedRow(), 5).toString());
                categoryNameCombo.setSelectedItem(cat.getCategoryName(Integer.parseInt(itemModel.getValueAt(
                        itemTable.getSelectedRow(), 1).toString())));
                prevItemName = itemNameTf.getText();
            } else if (e.getSource() == stockTable && e.getClickCount() == 2) {
                ItemOperations items = new ItemOperations();
                hideShowStockTextFields(true);
                stockId = Integer.parseInt(stockModel.getValueAt(stockTable.getSelectedRow(), 0).toString());
                stockItemNameTf.setText(stockModel.getValueAt(stockTable.getSelectedRow(), 1).toString());
                stockCountTf.setText(stockModel.getValueAt(stockTable.getSelectedRow(), 2).toString());
                regDeductionTf.setText(stockModel.getValueAt(stockTable.getSelectedRow(), 3).toString());
                medDeductionTf.setText(stockModel.getValueAt(stockTable.getSelectedRow(), 4).toString());
                lrgDeductionTf.setText(stockModel.getValueAt(stockTable.getSelectedRow(), 5).toString());
                stockUnitTf.setText(stockModel.getValueAt(stockTable.getSelectedRow(), 6).toString());

                if (items.hasPriceAvailable(stockItemNameTf.getText(), "medium") == true) {
                    medDeductionTf.setEditable(true);
                } else {
                    medDeductionTf.setEditable(false);
                }

                if (items.hasPriceAvailable(stockItemNameTf.getText(), "large") == true) {
                    lrgDeductionTf.setEditable(true);
                } else {
                    lrgDeductionTf.setEditable(false);
                }

            } else if (e.getSource() == Dashboard.dashboardFrame) {
                if (categoryId != 0 || itemId != 0 || stockId != 0) {
                    categoryId = 0;
                    categoryTf.setText("");
                    categoryTable.getSelectionModel().clearSelection();
                    itemId = 0;
                    itemNameTf.setText("");
                    regPriceTf.setText("");
                    medPriceTf.setText("");
                    lrgPriceTf.setText("");
                    if (categoryNameCombo.getItemCount() != 0) {
                        categoryNameCombo.setSelectedIndex(0);
                    }
                    itemTable.getSelectionModel().clearSelection();
                    stockTable.getSelectionModel().clearSelection();
                    hideShowStockTextFields(false);
                }
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

    public void addingDatas() {
        if (checkActivePanel() == categoryPanel) {
            if (categoryId == 0) {
                if (!categoryTf.getText().isBlank()) {
                    CategoryOperations catOper = new CategoryOperations(categoryTf.getText());
                    if (catOper.checkDuplication() == false) {
                        catOper.addCategory();
                        categoryTf.setText("");
                        retrieveCategoryData();
                        HomePOS.putCategoryAndItemToButtons();
                    } else {
                        JOptionPane.showMessageDialog(categoryPanel, "Duplication Of Data Are Not Allowed",
                                "Duplication", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(categoryPanel, "Category Name Is Empty...",
                            "Empty Field", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(categoryPanel, "Please Deselect The Row First",
                        "Active Selection", JOptionPane.ERROR_MESSAGE);
            }
        } else if (checkActivePanel() == itemPanel) {
            if (itemId == 0) {
                if (categoryNameCombo.getItemCount() != 0) {
                    if (!itemNameTf.getText().isBlank() && !regPriceTf.getText().isBlank()) {
                        ItemOperations itemOper = new ItemOperations(itemNameTf.getText(), categoryNameCombo.getSelectedItem().toString(),
                                regPriceTf.getText(), medPriceTf.getText(), lrgPriceTf.getText(), prevItemName);
                        try {
                            //just to catch invalid inputs
                            String mPrc = setToZeroIfEmpty(medPriceTf);
                            String lPrc = setToZeroIfEmpty(lrgPriceTf);
                            double reg = Double.parseDouble(regPriceTf.getText());
                            double med = Double.parseDouble(mPrc);
                            double lrg = Double.parseDouble(lPrc);
                            if (itemOper.checkItemNameDuplication() == false) {
                                itemOper.addItems();
                                itemNameTf.setText("");
                                regPriceTf.setText("");
                                medPriceTf.setText("");
                                lrgPriceTf.setText("");
                                categoryNameCombo.setSelectedIndex(0);
                                retrieveItemData();
                                retrieveStockData();
                                HomePOS.putCategoryAndItemToButtons();
                            } else {
                                JOptionPane.showMessageDialog(itemPanel, "Duplication Of Data Are Not Allowed",
                                        "Duplication", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(itemPanel, "Price Field Can Only Accepts Numbers...\n"
                                    + "Ex. 99.99",
                                    "ITEM PRICE", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(itemPanel, "Please Put An Item Name And Atleast Regular Price",
                                "Empty Fields", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(itemPanel, "Please Add A Category First In Category Panel",
                            "No Category Available For Item", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(itemPanel, "Please Deselect The Row First",
                        "Active Selection", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void updatingDatas() {
        if (checkActivePanel() == categoryPanel) {
            if (!categoryTf.getText().isBlank() && categoryId > 0) {
                CategoryOperations catOper = new CategoryOperations(categoryTf.getText());
                if (catOper.checkDuplication() == false) {
                    catOper.updateCategory(categoryId);
                    categoryTf.setText("");
                    retrieveCategoryData();
                    retrieveItemData();
                    retrieveStockData();
                    categoryId = 0;
                    categoryTable.getSelectionModel().clearSelection();
                    HomePOS.putCategoryAndItemToButtons();
                } else {
                    JOptionPane.showMessageDialog(categoryPanel, "Duplicated Values Are Not Allowed",
                            "Duplication", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(categoryPanel, "Please Select A Row To Update",
                        "UPDATING CATEGORY", JOptionPane.ERROR_MESSAGE);
            }
        } else if (checkActivePanel() == itemPanel) {
            if (itemId > 0) {
                ItemOperations itemOper = new ItemOperations(itemNameTf.getText(), categoryNameCombo.getSelectedItem().toString(),
                        regPriceTf.getText(), medPriceTf.getText(), lrgPriceTf.getText(), prevItemName);
                if (itemOper.checkItemNameDuplication() == false) {
                    try {
                        //just to catch invalid inputs
                        String mPrc = setToZeroIfEmpty(medPriceTf);
                        String lPrc = setToZeroIfEmpty(lrgPriceTf);
                        double reg = Double.parseDouble(regPriceTf.getText());
                        double med = Double.parseDouble(mPrc);
                        double lrg = Double.parseDouble(lPrc);

                        if (!itemNameTf.getText().isBlank() && !regPriceTf.getText().isBlank()
                                && Double.parseDouble(regPriceTf.getText()) > 0) {
                            itemOper.updateitems(itemId);
                            itemNameTf.setText("");
                            regPriceTf.setText("");
                            medPriceTf.setText("");
                            lrgPriceTf.setText("");
                            categoryNameCombo.setSelectedIndex(0);
                            retrieveItemData();
                            retrieveStockData();
                            itemId = 0;
                            itemTable.getSelectionModel().clearSelection();
                            HomePOS.putCategoryAndItemToButtons();
                        } else {
                            JOptionPane.showMessageDialog(itemPanel, "An Item Should Have A Name And Atleast Regular Price...",
                                    "ITEM PRICE", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(itemPanel, "Price Field Can Only Accepts Numbers...\n"
                                + "Ex. 99.99",
                                "ITEM PRICE", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(itemPanel, "Duplication Of Data Are Not Allowed",
                            "Duplication", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(itemPanel, "Please Select A Row To Update", "", JOptionPane.ERROR_MESSAGE);
            }
        } else if (checkActivePanel() == stockPanel) {
            if (!stockCountTf.getText().isBlank() && !regDeductionTf.getText().isBlank()
                    && !stockUnitTf.getText().isBlank() && stockId != 0) {
                if(Integer.parseInt(stockCountTf.getText()) <= 5000) {
                ItemOperations itemOper = new ItemOperations();
                int rDed = Integer.parseInt(regDeductionTf.getText());
                int mDed = Integer.parseInt(medDeductionTf.getText());
                int lDed = Integer.parseInt(lrgDeductionTf.getText());

                boolean mHasPrice = itemOper.hasPriceAvailable(stockItemNameTf.getText(), "medium");
                boolean lHasPrice = itemOper.hasPriceAvailable(stockItemNameTf.getText(), "large");

                System.out.println("item name is " + stockItemNameTf.getText());
                System.out.println("medium " + mHasPrice);
                System.out.println("large " + lHasPrice);

                if ((mHasPrice == true && mDed > 0 || mHasPrice == false && mDed <= 0)
                        && (lHasPrice == true && lDed > 0 || lHasPrice == false && lDed <= 0)
                        && (rDed > 0)) {
                    StockOperations stocks = new StockOperations(stockCountTf.getText(), itemNameTf.getText(),
                            regDeductionTf.getText(), medDeductionTf.getText(), lrgDeductionTf.getText(), stockUnitTf.getText());
                    stocks.updateStocks(stockId);
                    hideShowStockTextFields(false);
                    stockCountTf.setText("");
                    regDeductionTf.setText("");
                    medDeductionTf.setText("");
                    lrgDeductionTf.setText("");
                    stockUnitTf.setText("");
                    itemNameTf.setText("");
                    retrieveStockData();
                    HomePOS.putCategoryAndItemToButtons();
                    Dashboard.updateOverview();
                } else {
                    JOptionPane.showMessageDialog(stockPanel, "Cannot Set Deduction To Zero If An Item Has A Price For This Size",
                            "Stock Deduction", JOptionPane.ERROR_MESSAGE);
                }
                 } else {
                    JOptionPane.showMessageDialog(stockPanel,"MAXIMUM STOCK ALLOWED IS 5000...","STOCKS",0);
                }
            } else {
                JOptionPane.showMessageDialog(stockPanel, "Please Select A Row To Update Stock",
                        "No Active Selection", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deletingDatas() {
        if (checkActivePanel() == categoryPanel) {
            if (!categoryTf.getText().isBlank() && categoryId > 0) {
                CategoryOperations catOper = new CategoryOperations(categoryTf.getText());
                catOper.deleteCategory(categoryId);
                categoryTf.setText("");
                retrieveCategoryData();
                retrieveItemData();
                retrieveStockData();
                categoryId = 0;
                HomePOS.putCategoryAndItemToButtons();
                HomePOS.refreshFrame(HomePOS.homePOSmainframe);
            } else {
                JOptionPane.showMessageDialog(categoryPanel, "Please Select A Row To Delete",
                        "DELETING CATEGORY", JOptionPane.ERROR_MESSAGE);
            }
        } else if (checkActivePanel() == itemPanel) {
            if (!(itemNameTf.getText().isBlank()) && itemId > 0) {
                ItemOperations itemOper = new ItemOperations(itemNameTf.getText(), categoryNameCombo.getSelectedItem().toString(),
                        regPriceTf.getText(), medPriceTf.getText(), lrgPriceTf.getText(), prevItemName);
                itemOper.deleteItems(itemId);
                itemNameTf.setText("");
                regPriceTf.setText("");
                medPriceTf.setText("");
                lrgPriceTf.setText("");
                categoryNameCombo.setSelectedIndex(0);
                itemId = 0;
                itemTable.getSelectionModel().clearSelection();
                retrieveItemData();
                retrieveStockData();
                HomePOS.putCategoryAndItemToButtons();
            } else {
                JOptionPane.showMessageDialog(itemPanel, "Please Select A Row To Delete", "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void hideAddRemoveButton() {
        addBtn.setVisible(false);
        removeBtn.setVisible(false);
        updateBtn.setBounds(10, 10, 720, 60);
    }

    public void showAddRemoveButton() {
        addBtn.setVisible(true);
        removeBtn.setVisible(true);
        removeBtn.setBounds(500, 10, 230, 60);
        updateBtn.setBounds(255, 10, 230, 60);
    }

    public void hideShowPanels(JPanel p1, JPanel p2, JPanel panelShow) {
        ArrayList<JPanel> panelList = new ArrayList<>();
        panelList.add(p1);
        panelList.add(p2);
        for (int i = 0; i < panelList.size(); i++) {
            panelList.get(i).setVisible(false);
        }
        panelShow.setVisible(true);
    }

    public JPanel checkActivePanel() {
        if (categoryPanel.isVisible()) {
            return categoryPanel;
        } else if (itemPanel.isVisible()) {
            return itemPanel;
        } else if (stockPanel.isVisible()) {
            return stockPanel;
        }
        return null;
    }

    public void retrieveCategoryData() {
        try {
            catModel.setRowCount(0);
            categoryNameCombo.removeAllItems();
            Connection con = connect.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM products.categories");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int cat_id = rs.getInt("category_id");
                String cat_name = rs.getString("category_name");
                catModel.addRow(new Object[]{cat_id, cat_name});
                categoryNameCombo.addItem(cat_name);
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void retrieveItemData() {
        try {
            itemModel.setRowCount(0);
            Connection con = connect.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM products.category_items");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                int category_id = rs.getInt("category_id");
                String item_name = rs.getString("item_name");
                double reg_price = rs.getDouble("regular_price");
                double med_price = rs.getDouble("medium_price");
                double lrg_price = rs.getDouble("large_price");
                itemModel.addRow(new Object[]{item_id, category_id, item_name, reg_price, med_price, lrg_price});
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void retrieveStockData() {
        try {
            stockModel.setRowCount(0);
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT stock_id, items.item_name, "
                    + "stocks_count,regular_deduction, medium_deduction,large_deduction,"
                    + "stocks_unit FROM products.items_stocks stocks JOIN products.category_items "
                    + "items WHERE stocks.item_id = items.item_id;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int stock_id = rs.getInt("stock_id");
                String item_name = rs.getString("item_name");
                int stock_count = rs.getInt("stocks_count");
                int reg_ded = rs.getInt("regular_deduction");
                int med_ded = rs.getInt("medium_deduction");
                int lrg_ded = rs.getInt("large_deduction");
                String stock_unit = rs.getString("stocks_unit");
                stockModel.addRow(new Object[]{stock_id, item_name, stock_count, reg_ded, med_ded, lrg_ded, stock_unit});
            }
            statement.close();
        } catch (Exception ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hideShowStockTextFields(boolean action) {
        stockCountTf.setVisible(action);
        regDeductionTf.setVisible(action);
        medDeductionTf.setVisible(action);
        lrgDeductionTf.setVisible(action);
        stockItemNameTf.setVisible(action);
        stockUnitTf.setVisible(action);
    }

    public void fieldsToArray(JTextField[] textfields, String action) {
        for (JTextField iterator : textfields) {
            if (action.equals("number")) {
                fieldsForNumbers.add(iterator);
            } else if (action.equals("word")) {
                fieldsForWords.add(iterator);
            }
            iterator.addKeyListener(new CustomKeyListener());
        }
    }

    private class CustomKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            for (JTextField iterator : fieldsForNumbers) {
                if (e.getSource() == iterator) {
                    if (!(Character.isDigit(e.getKeyChar()))) {
                        e.consume();
                    } else if (iterator.getText().length() == 7
                            || e.getKeyChar() == java.awt.event.KeyEvent.VK_BACK_SPACE) {
                        e.consume();
                    }
                }
            }

            for (JTextField iterator : fieldsForWords) {
                if (e.getSource() == iterator) {
                    if (Character.isDigit(e.getKeyChar())) {
                        e.consume();
                    } else if (iterator.getText().length() == 15
                            || e.getKeyChar() == java.awt.event.KeyEvent.VK_BACK_SPACE) {
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

    public String setToZeroIfEmpty(JTextField field) {
        if (field.getText().isBlank() || field.getText().isEmpty()) {
            field.setText("0");
        }
        return field.getText();
    }
}
