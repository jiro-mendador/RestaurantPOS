package com.restaurantpos;

import com.restaurantpos.LoginAndSignUp.LoginSignUpGui;

public class RestaurantPOS {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        runnables();
    }
    
    public static void runnables() {
        LoginSignUpGui logSign = new LoginSignUpGui();
        logSign.setLoginAndSignUp();
    }
}
