package com.AbotLangit;
// The beginning of the whole reservation system
public class HotelReservationSystem {
    public static void main(String[] args) {
        // Create and display the welcome message
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WelcomeFrame();
            }
        });
    }
}
