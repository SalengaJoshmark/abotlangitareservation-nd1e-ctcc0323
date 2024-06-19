package com.AbotLangit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

/*
 * The class that gives the Wekcome message for the system
 */
public class WelcomeFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private RoomManager roomManager;

    public WelcomeFrame() {
        roomManager = new RoomManager();

        setTitle("Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);

        // Set layout and custom the background panel
        setContentPane(new BackgroundPanel("/image/background.jpg")); // Loading the background.jpg is in the "images" folder
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("<html><font color='black'>Welcome to Abot Langit<br>Hotel Reservation System</font></html>", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Star Jedi Outline", Font.BOLD, 26)); // Changed font style and size
        add(welcomeLabel, BorderLayout.CENTER);
        
        //The code responsible for the continue button
        JButton continueButton = new JButton("Continue");
        styleContinueButton(continueButton);
        add(continueButton, BorderLayout.SOUTH);

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ReservationForm(roomManager);
            }
        });

        // Animate the welcome label by gradually changing its text color to gold
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int colorIndex = 0;
            Color[] colors = {Color.black, Color.decode("#FFD700")}; // Black and gold colors

            @Override
            public void run() {
                welcomeLabel.setForeground(colors[colorIndex]);
                colorIndex = (colorIndex + 1) % colors.length;
            }
        }, 10, 1000); // Change color every second

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // The customization of the continue button
    private void styleContinueButton(JButton button) {
        button.setFont(new Font("Star Jedi Outline", Font.BOLD, 20));
        button.setBackground(Color.decode("#FFD700"));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);

        // responsible for the color change effect if you click the continue button
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.BLACK);
                button.setForeground(Color.decode("#FFD700"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.decode("#FFD700"));
                button.setForeground(Color.BLACK);
            }
        });
    }

    public static void main(String[] args) {
        new WelcomeFrame();
    }
}
