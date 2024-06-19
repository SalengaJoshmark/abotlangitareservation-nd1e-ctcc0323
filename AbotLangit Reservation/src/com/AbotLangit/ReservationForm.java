package com.AbotLangit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ReservationForm class for creating the reservation form GUI.
 * This is where the transaction goes
 * This class handles the user inputs for making room reservations.
 */
public class ReservationForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nameField; // New  field for name
    private JTextField contactField; // New field for contact number
    private JComboBox<String> roomTypeComboBox;
    private JTextField checkInField;
    private JTextField durationField; // Field for duration in days
    private JTextField guestsField;
    private JLabel priceLabel;
    private JButton reserveButton;
    private JButton checkAvailabilityButton;
    private RoomManager roomManager;

    /**
     * Constructor to initialize the ReservationForm with a RoomManager instance.
     * @param roomManager an instance of RoomManager to manage room reservations.
     */
    public ReservationForm(RoomManager roomManager) {
        this.roomManager = roomManager;

        setTitle("Reservation Form");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load and set the background image from the resource folder with the help of background panel class
        BackgroundPanel panel = new BackgroundPanel("/image/apartment.jpg"); // Change "apartment.jpg" to your background image path
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Functions to customize labels
        Font labelFont = new Font("Calibri", Font.PLAIN, 20);
        Color labelColor = Color.WHITE; // CYAN text for labels

        // Panels to contain all fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);

        // Add the Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(customizeLabel(new JLabel("Name:"), labelFont, labelColor), gbc);
        gbc.gridx = 1;
        nameField = new JTextField();
        fieldsPanel.add(customizeTextField(nameField), gbc);

        // Add the Contact Number field
        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(customizeLabel(new JLabel("Contact Number (11 Digits Only):"), labelFont, labelColor), gbc);
        gbc.gridx = 1;
        contactField = new JTextField();
        fieldsPanel.add(customizeTextField(contactField), gbc);

        // Add Room Type field
        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldsPanel.add(customizeLabel(new JLabel("Room Type:"), labelFont, labelColor), gbc);
        gbc.gridx = 1;
        roomTypeComboBox = new JComboBox<>();
        roomTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePrice();
            }
        });
        fieldsPanel.add(customizeComboBox(roomTypeComboBox), gbc);

        // Add Price Per Day field
        gbc.gridx = 0;
        gbc.gridy = 3;
        fieldsPanel.add(customizeLabel(new JLabel("Price Per Day:"), labelFont, labelColor), gbc);
        gbc.gridx = 1;
        priceLabel = new JLabel();
        fieldsPanel.add(customizeLabel(priceLabel, labelFont, labelColor), gbc);

        // Add Check-in Date field
        gbc.gridx = 0;
        gbc.gridy = 4;
        fieldsPanel.add(customizeLabel(new JLabel("Check-in Date (yyyy-MM-dd):"), labelFont, labelColor), gbc);
        gbc.gridx = 1;
        checkInField = new JTextField();
        fieldsPanel.add(customizeTextField(checkInField), gbc);

        // Add Duration of Stay field
        gbc.gridx = 0;
        gbc.gridy = 5;
        fieldsPanel.add(customizeLabel(new JLabel("Duration of Stay (days) (0-9):"), labelFont, labelColor), gbc);
        gbc.gridx = 1;
        durationField = new JTextField();
        fieldsPanel.add(customizeTextField(durationField), gbc);

        // Add Number of Guests field
        gbc.gridx = 0;
        gbc.gridy = 6;
        fieldsPanel.add(customizeLabel(new JLabel("Number of Guests (0-9):"), labelFont, labelColor), gbc);
        gbc.gridx = 1;
        guestsField = new JTextField();
        fieldsPanel.add(customizeTextField(guestsField), gbc);

        // Add Reserve button
        gbc.gridx = 0;
        gbc.gridy = 7;
        reserveButton = new JButton("Make Reservation");
        reserveButton.setBackground(new Color(50, 122, 74)); // Green
        reserveButton.setFont(new Font("Castellar", Font.BOLD, 14));
        reserveButton.setForeground(Color.WHITE); // White text
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeReservation();
            }
        });
        fieldsPanel.add(reserveButton, gbc);

        // Add Check Availability button
        gbc.gridx = 1;
        checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.setBackground(new Color(206, 17, 38)); // Red
        checkAvailabilityButton.setFont(new Font("Castellar", Font.BOLD, 14));
        checkAvailabilityButton.setForeground(Color.WHITE); // White text
        checkAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAvailability();
            }
        });
        fieldsPanel.add(checkAvailabilityButton, gbc);

        // Add fields panel to main panel
        gbc.gridx = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(fieldsPanel, gbc);

        getContentPane().add(panel);
        setVisible(true);
        refreshRoomTypes();
    }

    /**
     * Customize label appearance.
     * @param label the JLabel to customize.
     * @param font the Font to set on the label.
     * @param color the Color to set on the label text.
     * @return the customized JLabel.
     */
    private JLabel customizeLabel(JLabel label, Font font, Color color) {
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    /**
     * Customize text field appearance.
     * @param textField the JTextField to customize.
     * @return the customized JTextField.
     */
    private JTextField customizeTextField(JTextField textField) {
        textField.setFont(new Font("Palatino Linotype", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                textField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return textField;
    }

    /**
     * Customize combo box appearance.
     * @param comboBox the JComboBox to customize.
     * @return the customized JComboBox.
     */
    private JComboBox<String> customizeComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Georgia", Font.BOLD, 12));
        comboBox.setForeground(Color.BLACK);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                comboBox.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return comboBox;
    }

    /**
     * Refresh the list of room types in the combo box based on available rooms.
     * This code function is the responsible for automatically removing the unavailable room in the combo box
     */
    private void refreshRoomTypes() {
        roomTypeComboBox.removeAllItems();
        roomTypeComboBox.addItem("Select a room type"); // Add an empty choice first
        List<Room> availableRooms = roomManager.getAvailableRooms();
        for (Room room : availableRooms) {
            roomTypeComboBox.addItem(room.getType() + " - Room " + room.getNumber());
        }
        if (availableRooms.isEmpty()) {
            priceLabel.setText("No rooms available"); // Will only appear if all the rooms are not available
        }
    }

    /**
     * Update the price labels based on the room type that was selected.
     */
    private void updatePrice() {
        String roomInfo = (String) roomTypeComboBox.getSelectedItem();
        if (roomInfo == null || roomInfo.equals("Select a room type")) {
            priceLabel.setText("");
            return;
        }
        String[] selectedRoomInfo = roomInfo.split(" - ");
        String roomType = selectedRoomInfo[0];
        int roomNumber = Integer.parseInt(selectedRoomInfo[1].substring(selectedRoomInfo[1].indexOf(" ") + 1));
        List<Room> rooms = roomManager.getRooms();
        for (Room room : rooms) {
            if (room.getType().equals(roomType) && room.getNumber() == roomNumber) {
                String priceInfo = String.format("Php %.2f per days", room.getPrice());
                priceLabel.setText(priceInfo);
                return;
            }
        }
        priceLabel.setText("");
    }

    /**
     * As you click the Check Availability button
     *To Check the availability of rooms it will open the AvailabilityFrame to showcase the status of each room.
     */
    private void checkAvailability() {
        new AvailabilityFrame(roomManager);
    }

    /**
     * The codes that create make the reservation button
     * Handle reservation creation when the reserve button is clicked.
     * The makeReservation also ensure if the user input still doesnt choose a room a error message will occur
     */
    private void makeReservation() {
        String name = nameField.getText();
        String contactNumber = contactField.getText().trim();
        String roomInfo = (String) roomTypeComboBox.getSelectedItem();
        if (roomInfo == null || roomInfo.equals("Select a room type")) {
            showMessage("Please select a room type.");
            return;
        }
        String[] selectedRoomInfo = roomInfo.split(" - ");
        if (selectedRoomInfo.length < 2) {
            showMessage("Invalid room selection.");
            return;
        }

        String roomType = selectedRoomInfo[0];
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(selectedRoomInfo[1].substring(selectedRoomInfo[1].indexOf(" ") + 1));
        } catch (NumberFormatException e) {
            showMessage("Invalid room number.");
            return;
        }

        String checkInDateStr = checkInField.getText();
        String durationStr = durationField.getText();
        String guestsStr = guestsField.getText();
        
        if (name.isEmpty() || contactNumber.isEmpty() || checkInDateStr.isEmpty() || durationStr.isEmpty() || guestsStr.isEmpty()) {
            showMessage("Please fill in all fields."); // Message noticed when the user didnt put any inputs in the form
            return;
        }
        if (!contactNumber.matches("\\d{11}")) {
            showMessage("Contact number must be exactly 11 digits."); // Warning the user if the input on the contact # is either lesser or exceded the given number of digit
            return;
        }
        int guests, duration;
        try {
            guests = Integer.parseInt(guestsStr);
            if (guests <= 0) {
                throw new NumberFormatException();
            }
            duration = Integer.parseInt(durationStr);
            if (duration <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid number of guests or duration."); //THe noticed only appeared when the user put string variable on the said panels
            return;
        }

        // Parse check-in date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);  // Enforce strict parsing to avoid any errors like exceding numbers like 13 months or so
        Date checkInDate;
        try {
            checkInDate = dateFormat.parse(checkInDateStr);       
        } catch (ParseException e) {
            showMessage("Invalid date format. Please use yyyy-MM-dd."); //Message noticed when the user failed to follow the given format of date
            return;
        }

        // Calculate the check-out date based on the Check-in date and duration of stay input
        Date checkOutDate = new Date(checkInDate.getTime() + TimeUnit.DAYS.toMillis(duration));
        java.util.Date CheckInDate = new java.util.Date(checkInDate.getTime());
        java.util.Date CheckOutDate = new java.util.Date(checkOutDate.getTime());

        // Check room availability and make reservation if possible
        List<Room> rooms = roomManager.getRooms();
        boolean isAvailable = false;
        for (Room room : rooms) {
            if (room.getType().equals(roomType) && room.getNumber() == roomNumber && room.isAvailable()) {
                isAvailable = true;
                double totalPrice = room.calculateTotalPrice(duration, guests);
                roomManager.reserveRoom(room, name, contactNumber, CheckInDate, CheckOutDate, guests); // Pass every variable here
                //Confirmation notice if the submittion of reservations are done succesfully
                showMessage(String.format("Reservation made successfully for %s.\nRoom: %s - %d\nCheck-in: %s\nCheck-out: %s\nDuration of Stay: %d day(s)\nNumber of Guests: %d \nTotal Price: Php %.2f",
                        name, room.getType(), room.getNumber(), dateFormat.format(checkInDate), dateFormat.format(checkOutDate), duration, guests, totalPrice));
                clearFields();
                refreshRoomTypes();
                break;
            }
        }
        if (!isAvailable) {
            showMessage("Sorry, no rooms available for the selected date range or type.");
        }
    }

    /**
     * Display a message to the user.
     * @param message the message to display.
     */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Clear all input fields.
     */
    private void clearFields() {
        nameField.setText("");
        contactField.setText("");
        checkInField.setText("");
        durationField.setText("");
        guestsField.setText("");
    }

    /**
     * Main method to start the application.
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        new WelcomeFrame();
    }
}
