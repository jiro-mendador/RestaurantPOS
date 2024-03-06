package com.restaurantpos.HomePOS;

import static com.restaurantpos.HomePOS.HomePOS.dark;
import static com.restaurantpos.HomePOS.HomePOS.red;
import static com.restaurantpos.HomePOS.HomePOS.violet;
import static com.restaurantpos.HomePOS.Products.big;
import static com.restaurantpos.HomePOS.Products.med;
import com.restaurantpos.LoginAndSignUp.DataBaseConnection;
import static com.restaurantpos.LoginAndSignUp.LoginSignUpGui.lightest;
import com.restaurantpos.LoginAndSignUp.SignUpNewAccount;
import com.restaurantpos.LoginAndSignUp.User;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

/**
 *
 * @author Jiro Mendador
 */
public class User_Accounts {

    static JPanel accountsMainPanel;
    JPanel midPanel, actionPanel;
    JLabel accountsTitleLbl, firstNameLbl, lastNameLbl,
            userLevelLbl, usernameLbl, passwordLbl;
    JTextField firstNameTf, lastNameTf, usernameTf, passwordTf;
    JComboBox<String> userLevelCombo;
    JButton registerBtn, updateBtn, removeBtn;
    JTable accountsTbl;
    DefaultTableModel accountsModel;
    JScrollPane accountsScroll;

    int account_id = 0;
    String firstName = "";
    String lastName = "";
    
    public User_Accounts() {
        accountsMainPanel = new JPanel();
        midPanel = new JPanel();
        actionPanel = new JPanel();
        accountsTitleLbl = new JLabel();
        firstNameLbl = new JLabel();
        lastNameLbl = new JLabel();
        userLevelLbl = new JLabel();
        usernameLbl = new JLabel();
        passwordLbl = new JLabel();
        registerBtn = new JButton();
        updateBtn = new JButton();
        removeBtn = new JButton();
        accountsTbl = new JTable();
        accountsModel = new DefaultTableModel();
        firstNameTf = new JTextField();
        lastNameTf = new JTextField();
        usernameTf = new JTextField();
        passwordTf = new JTextField();
        userLevelCombo = new JComboBox<>();
    }

    public void setUserAccountsGui() {
        //labels
        accountsTitleLbl.setText("User Accounts");
        accountsTitleLbl.setBounds(280, 30, 300, 40);
        accountsTitleLbl.setForeground(lightest);
        accountsTitleLbl.setFont(big);

        firstNameLbl.setText("First Name");
        firstNameLbl.setBounds(25, 300, 200, 40);
        firstNameLbl.setForeground(lightest);
        firstNameLbl.setFont(med);

        lastNameLbl.setText("Last Name");
        lastNameLbl.setBounds(300, 300, 200, 40);
        lastNameLbl.setForeground(lightest);
        lastNameLbl.setFont(med);

        userLevelLbl.setText("User-Level");
        userLevelLbl.setBounds(550, 300, 200, 40);
        userLevelLbl.setForeground(lightest);
        userLevelLbl.setFont(med);

        usernameLbl.setText("Username");
        usernameLbl.setBounds(25, 375, 200, 40);
        usernameLbl.setForeground(lightest);
        usernameLbl.setFont(med);

        passwordLbl.setText("Password");
        passwordLbl.setBounds(300, 375, 200, 40);
        passwordLbl.setForeground(lightest);
        passwordLbl.setFont(med);

        //textfield
        firstNameTf.setBackground(dark);
        firstNameTf.setBounds(25, 335, 200, 30);
        firstNameTf.setForeground(lightest);
        firstNameTf.setFont(med);
        firstNameTf.setMargin(new Insets(5, 5, 5, 5));

        lastNameTf.setBackground(dark);
        lastNameTf.setBounds(300, 335, 200, 30);
        lastNameTf.setForeground(lightest);
        lastNameTf.setFont(med);
        lastNameTf.setMargin(new Insets(5, 5, 5, 5));

        userLevelCombo.setBounds(550, 335, 170, 30);
        userLevelCombo.addItem("Staff");
        userLevelCombo.addItem("Manager");
        userLevelCombo.setFocusable(false);
        userLevelCombo.setBackground(dark);
        userLevelCombo.setForeground(lightest);
        userLevelCombo.setFont(med);

        usernameTf.setBackground(dark);
        usernameTf.setBounds(25, 410, 200, 30);
        usernameTf.setForeground(lightest);
        usernameTf.setFont(med);
        usernameTf.setMargin(new Insets(5, 5, 5, 5));

        passwordTf.setBackground(dark);
        passwordTf.setBounds(300, 410, 200, 30);
        passwordTf.setForeground(lightest);
        passwordTf.setFont(med);
        passwordTf.setMargin(new Insets(5, 5, 5, 5));

        //button
        ImageIcon registerIcon = new ImageIcon("src/main/java/images/add-user.png");
        registerBtn.setText("REGISTER");
        registerBtn.setBounds(10, 10, 230, 60);
        registerBtn.setBackground(red);
        registerBtn.setForeground(lightest);
        registerBtn.setFont(med);
        registerBtn.setIcon(registerIcon);
        registerBtn.setIconTextGap(10);
        registerBtn.setFocusable(false);
        registerBtn.setBorderPainted(false);
        registerBtn.addActionListener(new CustomActionListener());

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
        updateBtn.addActionListener(new CustomActionListener());

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
        removeBtn.addActionListener(new CustomActionListener());

        //table
        String[] accountsTableColumnNames = {"ID", "First Name", "Last Name", "User-Level", "Username", "Password"};
        accountsModel.setColumnIdentifiers(accountsTableColumnNames);
        accountsTbl = new JTable(accountsModel);
        accountsTbl.setFont(new Font("Dialog", Font.PLAIN, 13));
        accountsTbl.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
        accountsTbl.getTableHeader().setForeground(dark);
        accountsTbl.setDefaultEditor(Object.class, null);
        accountsTbl.getTableHeader().setReorderingAllowed(false);
        accountsTbl.getColumnModel().getColumn(0).setPreferredWidth(35);
        accountsTbl.getColumnModel().getColumn(1).setPreferredWidth(80);
        accountsTbl.getColumnModel().getColumn(2).setPreferredWidth(80);
        accountsTbl.getColumnModel().getColumn(3).setPreferredWidth(50);
        accountsTbl.setForeground(dark);
        accountsTbl.setRowHeight(accountsTbl.getRowHeight() + 5);
        accountsScroll = new JScrollPane(accountsTbl);
        accountsScroll.getVerticalScrollBar().setBackground(violet);
        accountsScroll.setBounds(20, 20, 700, 275);
        accountsTbl.addMouseListener(new CustomMouseListener());

        //adding components
        actionPanel.add(registerBtn);
        actionPanel.add(updateBtn);
        actionPanel.add(removeBtn);

        midPanel.add(firstNameTf);
        midPanel.add(lastNameTf);
        midPanel.add(userLevelCombo);
        midPanel.add(usernameTf);
        midPanel.add(passwordTf);
        midPanel.add(firstNameLbl);
        midPanel.add(lastNameLbl);
        midPanel.add(userLevelLbl);
        midPanel.add(usernameLbl);
        midPanel.add(passwordLbl);
        midPanel.add(accountsScroll);

        accountsMainPanel.add(accountsTitleLbl);
        accountsMainPanel.add(midPanel);
        accountsMainPanel.add(actionPanel);

        //panels
        actionPanel.setLayout(null);
        actionPanel.setBounds(20, 570, 740, 80);
        actionPanel.setBackground(violet);

        midPanel.setLayout(null);
        midPanel.setBounds(20, 100, 740, 455);
        midPanel.setBackground(violet);

        accountsMainPanel.setLayout(null);
        accountsMainPanel.setBounds(200, 0, 800, 700);
        accountsMainPanel.setBackground(dark);
        accountsMainPanel.setVisible(false);
        accountsMainPanel.addMouseListener(new CustomMouseListener());

        retrieveUsersInfo();
    }

    public void retrieveUsersInfo() {
        try {
            accountsModel.setRowCount(0);
            Connection con = DataBaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM "
                    + "accounts.accounts_signed_up");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String uname = rs.getString(2);
                String pw = rs.getString(3);
                String fname = rs.getString(4);
                String lname = rs.getString(5);
                String ulevel = rs.getString(6);
                accountsModel.addRow(new Object[]{id, fname, lname, ulevel, uname, pw});
            }
        } catch (Exception ex) {
            Logger.getLogger(User_Accounts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkEmptyFields(JTextField[] fields) {
        for (JTextField i : fields) {
            if (i.getText().isBlank()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkInputLength(JTextField[] fields) {
        for (JTextField i : fields) {
            if (i.getText().length() <= 7) {
                return true;
            }
        }
        return false;
    }

    class CustomActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == registerBtn) {
                if(account_id == 0) {
                if (!checkEmptyFields(new JTextField[]{firstNameTf, lastNameTf,
                    usernameTf, passwordTf})) {
                    if (!checkInputLength(new JTextField[]{usernameTf, passwordTf})) {
                        try {
                            SignUpNewAccount register = new SignUpNewAccount();
                            if (!register.checkFirstLastName(firstNameTf.getText(), lastNameTf.getText())) {
                                if (userLevelCombo.getSelectedIndex() == 1 && register.checkAdminAccountCount() >= 3) {
                                    JOptionPane.showMessageDialog(accountsMainPanel, "The Creation Of Admin Account "
                                            + "Is Already On It's Limit", "LIMIT", 0);
                                } else {
                                    if (register.saveAccount(usernameTf.getText(), passwordTf.getText(),
                                            firstNameTf.getText(), lastNameTf.getText(),
                                            userLevelCombo.getSelectedItem().toString())) {
                                        JOptionPane.showMessageDialog(accountsMainPanel, "ACCOUNT REGISTERED SUCCESSFULLY!");
                                        retrieveUsersInfo();
                                        firstNameTf.setText("");
                                        lastNameTf.setText("");
                                        userLevelCombo.setSelectedIndex(0);
                                        usernameTf.setText("");
                                        passwordTf.setText("");
                                    } else {
                                        JOptionPane.showMessageDialog(accountsMainPanel, "USERNAME ALREADY EXISTS!", "USERNAME", 0);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(accountsMainPanel, "First Name And Last Name Is "
                                        + "Registered Already", "TAKEN", 0);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(User_Accounts.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(accountsMainPanel, "Username And Password Should "
                                + "Be Atleast 8 Characters", "REGISTER", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(accountsMainPanel, "All Input Fields Should Be Filled...", "REGISTER", 0);
                }
                } else {
                    JOptionPane.showMessageDialog(accountsMainPanel, "Deselect The Selected Account First...", "REGISTER", 0);
                }
            } else if (e.getSource() == updateBtn) {
                if (account_id != 0) {
                    if (!checkEmptyFields(new JTextField[]{firstNameTf, lastNameTf,
                        usernameTf, passwordTf})) {
                        if (!checkInputLength(new JTextField[]{usernameTf, passwordTf})) {
                            try {
                                if (!SignUpNewAccount.checkFirstLastName(firstNameTf.getText(), lastNameTf.getText())) {
                                    if (userLevelCombo.getSelectedIndex() == 1 && SignUpNewAccount.checkAdminAccountCount() >= 3) {
                                        JOptionPane.showMessageDialog(accountsMainPanel, "The Creation Of Admin Account "
                                                + "Is Already On It's Limit", "LIMIT", 0);
                                    } else if(User.getMainAdminAccount().equals(getCurrentSelectedUser()) 
                                                && !User.getUserFullName().equals(User.getMainAdminAccount())) {
                                        System.out.println("Selected "+getCurrentSelectedUser());
                                        System.out.println("Admin "+User.getMainAdminAccount());
                                        System.out.println("User "+User.getUserFullName());
                                            JOptionPane.showMessageDialog(accountsMainPanel, "Main Admin Account Cannot Be Modified...", "UPDATE", 0);
                                            firstNameTf.setText("");
                                            lastNameTf.setText("");
                                            userLevelCombo.setSelectedIndex(0);
                                            usernameTf.setText("");
                                            passwordTf.setText("");
                                            account_id = 0;
                                            accountsTbl.getSelectionModel().clearSelection();
                                    } else {
                                        if (SignUpNewAccount.updateAccount(usernameTf.getText(), passwordTf.getText(),
                                                firstNameTf.getText(), lastNameTf.getText(),
                                                userLevelCombo.getSelectedItem().toString(), account_id)) {
                                            User currentUser = new User();
                                            currentUser.setUserFname(usernameTf.getText());
                                            currentUser.setUserLname(usernameTf.getText());
                                            JOptionPane.showMessageDialog(accountsMainPanel, "ACCOUNT UPDATED SUCCESSFULLY!");
                                            retrieveUsersInfo();
                                            firstNameTf.setText("");
                                            lastNameTf.setText("");
                                            userLevelCombo.setSelectedIndex(0);
                                            usernameTf.setText("");
                                            passwordTf.setText("");
                                            account_id = 0;
                                        } else {
                                            JOptionPane.showMessageDialog(accountsMainPanel, "USERNAME ALREADY EXISTS!", "USERNAME", 0);
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(accountsMainPanel, "First Name And Last Name Is "
                                            + "Registered Already", "TAKEN", 0);
                                }
                            } catch (Exception ex) {
                                Logger.getLogger(User_Accounts.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(accountsMainPanel, "Username And Password Should "
                                    + "Be Atleast 8 Characters", "REGISTER", 0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(accountsMainPanel, "All Input Fields Should Be Filled...", "REGISTER", 0);
                    }

                } else {
                    JOptionPane.showMessageDialog(accountsMainPanel, "Please Select An Account To Update");
                }
            } else if (e.getSource() == removeBtn) {
                if (account_id != 0) {
                    if(!User.getUserFullName().equals(getCurrentSelectedUser())) {
                        if(!User.getMainAdminAccount().equals(getCurrentSelectedUser()) 
                            || User.getUserFullName().equals(User.getMainAdminAccount())) {
                            SignUpNewAccount remove = new SignUpNewAccount();
                            remove.removeAccount(account_id);
                            JOptionPane.showMessageDialog(accountsMainPanel, "ACCOUNT REMOVED!");
                            retrieveUsersInfo();
                            firstNameTf.setText("");
                            lastNameTf.setText("");
                            userLevelCombo.setSelectedIndex(0);
                            usernameTf.setText("");
                            passwordTf.setText("");
                            account_id = 0;
                        } else {
                           JOptionPane.showMessageDialog(accountsMainPanel, "Main Admin Account Cannot Be Deleted...", "DELETE", 0); 
                        }
                    } else {
                        JOptionPane.showMessageDialog(accountsMainPanel, "Deleting The Current IN USE ACCOUNT Is Invalid!", "DELETE", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(accountsMainPanel, "Please Select An Account To Delete");
                }
            }
        }

    }

    class CustomMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == accountsTbl && e.getClickCount() == 2) {
                account_id = Integer.parseInt(accountsModel.getValueAt(
                        accountsTbl.getSelectedRow(), 0).toString());
                firstNameTf.setText(accountsModel.getValueAt(
                        accountsTbl.getSelectedRow(), 1).toString());
                lastNameTf.setText(accountsModel.getValueAt(
                        accountsTbl.getSelectedRow(), 2).toString());
                userLevelCombo.setSelectedItem(accountsModel.getValueAt(
                        accountsTbl.getSelectedRow(), 3).toString());
                usernameTf.setText(accountsModel.getValueAt(
                        accountsTbl.getSelectedRow(), 4).toString());
                passwordTf.setText(accountsModel.getValueAt(
                        accountsTbl.getSelectedRow(), 5).toString());
                
                setCurrentSelectedUser(accountsModel.getValueAt(
                        accountsTbl.getSelectedRow(), 1).toString(),
                        accountsModel.getValueAt(
                        accountsTbl.getSelectedRow(), 2).toString());
                
                if(account_id == 1) {
                    userLevelCombo.setEnabled(false);
                } else {
                    userLevelCombo.setEnabled(true);
                }
                
                SignUpNewAccount sign = new SignUpNewAccount();
                sign.setPreviousFirstName(firstNameTf.getText());
                sign.setPreviousLastName(lastNameTf.getText());
                sign.setPreviousUsername(usernameTf.getText());
            } else if (e.getSource() == accountsMainPanel) {
                accountsTbl.getSelectionModel().clearSelection();
                firstNameTf.setText("");
                lastNameTf.setText("");
                userLevelCombo.setSelectedIndex(0);
                usernameTf.setText("");
                passwordTf.setText("");
                account_id = 0;
                userLevelCombo.setEnabled(true);
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
    
    public void setCurrentSelectedUser(String fN, String lN) {
        this.firstName = fN;
        this.lastName = lN;
    }
    
    public String getCurrentSelectedUser() {
        return this.firstName+" "+this.lastName; 
    }
}
