package com.restaurantpos.LoginAndSignUp;

import com.restaurantpos.HomePOS.Customers;
import com.restaurantpos.HomePOS.Dashboard;
import com.restaurantpos.HomePOS.HomePOS;
import com.restaurantpos.HomePOS.Products;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Jiro Mendador
 */
public class LoginSignUpGui {

    JFrame loginFrame;
    JPanel loginPanel1, loginPanel2;
    JTextField usernameTf;
    JPasswordField passwordTf;
    JButton loginBtn;
    JLabel loginLogFrameLbl;

    public static Color dark, light, lightest;
    public static Font big, small, smallBold, med;

    public LoginSignUpGui() {
        loginFrame = new JFrame();
        loginPanel1 = new JPanel();
        loginPanel2 = new JPanel();
        usernameTf = new JTextField();
        passwordTf = new JPasswordField();
        loginBtn = new JButton();
        loginLogFrameLbl = new JLabel();

        dark = new Color(30, 27, 46);
        light = new Color(52, 58, 78);
        lightest = Color.white;

        big = new Font("Dialog", Font.BOLD, 35);
        med = new Font("Dialog", Font.BOLD, 15);
        small = new Font("Monospaced", Font.PLAIN, 15);
        smallBold = new Font("Dialog", Font.BOLD, 13);
    }

    public void setLoginAndSignUp() {
        setLoginGui();
        addFocuses();
        addClicks();
        addActions();
        DataBaseConnection con = new DataBaseConnection();
        con.createUsersDB();
        con.createProductsDB();
        /*HomePOS home = new HomePOS();
        home.setHomePOSGui();
        Dashboard dashboard = new Dashboard();
        dashboard.setDashboardGui();
        dashboard.addClicks();*/
 /*Products products = new Products();
        products.setProductsGui();
        products.addActions();
        products.addClicks();
        Customers customer = new Customers();
        customer.setCustomersGui();*/
    }

    public void setLoginGui() {
        //labels
        loginLogFrameLbl.setText("LOGIN");
        loginLogFrameLbl.setBounds(230, 0, 150, 100);
        loginLogFrameLbl.setFont(big);
        loginLogFrameLbl.setForeground(Color.white);

        //textfields
        usernameTf.setText("Username");
        usernameTf.setBounds(230, 100, 300, 40);
        usernameTf.setFont(smallBold);
        usernameTf.setBackground(dark);
        usernameTf.setForeground(Color.white);
        usernameTf.setMargin(new Insets(0, 10, 0, 0));

        passwordTf.setText("Password");
        passwordTf.setBounds(230, 150, 300, 40);
        passwordTf.setFont(smallBold);
        passwordTf.setEchoChar((char) 0);
        passwordTf.setBackground(dark);
        passwordTf.setForeground(Color.white);
        passwordTf.setMargin(new Insets(0, 10, 0, 0));

        //buttons
        loginBtn.setText("L O G I N");
        loginBtn.setBounds(230, 200, 300, 40);
        loginBtn.setFont(new Font("Dialog", Font.BOLD, 15));
        loginBtn.setBackground(dark);
        loginBtn.setForeground(lightest);
        loginBtn.setFocusable(false);

        //panels
        loginPanel2.setBounds(0, 0, 600, 400);
        loginPanel2.setLayout(null);
        loginPanel2.setBackground(dark);
        loginPanel2.setFocusable(true);

        loginPanel1.setBounds(0, 0, 200, 400);
        loginPanel1.setLayout(null);
        loginPanel1.setBackground(light);

        //adding components
        loginPanel2.add(loginLogFrameLbl);
        loginPanel2.add(usernameTf);
        loginPanel2.add(passwordTf);
        loginPanel2.add(loginBtn);
        loginFrame.add(loginPanel1);
        loginFrame.add(loginPanel2);

        //frames
        loginFrame.setLayout(null);
        loginFrame.setResizable(false);
        loginFrame.setTitle("Login");
        loginFrame.setSize(600, 400);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    public void addActions() {
        loginBtn.addActionListener(new CustomActionListener());
    }

    public void addFocuses() {
        loginPanel2.requestFocusInWindow();
        usernameTf.addFocusListener(new CustomFocusListener());
        passwordTf.addFocusListener(new CustomFocusListener());
    }

    public void addClicks() {
        loginPanel2.addMouseListener(new CustomMouseListener());
    }

    class CustomFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            if (e.getSource() == usernameTf) {
                if (usernameTf.getText().equals("Username")) {
                    usernameTf.setText("");
                }
            } else if (e.getSource() == passwordTf) {
                String prePassword = new String(passwordTf.getPassword());
                if (prePassword.equals("Password")) {
                    passwordTf.setText("");
                    passwordTf.setEchoChar('*');
                }
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (e.getSource() == usernameTf) {
                if (usernameTf.getText().equals("")) {
                    usernameTf.setText("Username");
                }
            } else if (e.getSource() == passwordTf) {
                String prePassword = new String(passwordTf.getPassword());
                if (prePassword.equals("")) {
                    passwordTf.setText("Password");
                    passwordTf.setEchoChar((char) 0);
                }
            }
        }
    }

    class CustomMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == loginPanel2) {
                loginPanel2.requestFocusInWindow();
                if (usernameTf.getText().equals("")) {
                    usernameTf.setText("Username");
                } else if (passwordTf.getPassword().equals("")) {
                    passwordTf.setEchoChar((char) 0);
                    passwordTf.setText("Password");
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

    class CustomActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* SignUpNewAccount sign = new SignUpNewAccount();
            if (e.getSource() == loginBtn) {
                if (usernameTf.getText().equals("Username") || new String(passwordTf.getPassword()).equals("Password")) {
                    JOptionPane.showMessageDialog(loginFrame, "Please Enter A Username and Password");
                } else {
                    try {
                        Verify account = new Verify();
                        if (account.verify(usernameTf.getText(), new String(passwordTf.getPassword())) == true) {
                            //sets and gets user fullname
                            User currentUser = new User();
                            currentUser.setUserFname(usernameTf.getText());
                            currentUser.setUserLname(usernameTf.getText());
                            
                            JOptionPane.showMessageDialog(loginFrame, "LOGIN SUCCESS!");
                            loginFrame.setVisible(false);
                            HomePOS home = new HomePOS();
                            home.setHomePOSGui();
                        } else {
                            JOptionPane.showMessageDialog(loginFrame, "Incorrect Username or Password...", "INCORRECT", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(LoginSignUpGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }*/
            loginFrame.setVisible(false);
            HomePOS home = new HomePOS();
            home.setHomePOSGui();
        }
    }
}
