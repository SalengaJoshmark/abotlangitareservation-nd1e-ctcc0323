package com.AbotLangit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * AvailabilityFrame class displays room availability in a JFrame with a background image.
 */
public class AvailabilityFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private RoomManager roomManager;

    /**
     * Constructs the AvailabilityFrame.
     * @param roomManager The RoomManager instance managing room data.
     */
    public AvailabilityFrame(RoomManager roomManager) {
        this.roomManager = roomManager;

        setTitle("Room Availability");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load background image
        BufferedImage backgroundImage = loadImage("image/couple.jpg");
        JPanel mainPanel = new BackgroundPanel(backgroundImage);
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Header label
        JLabel headerLabel = new JLabel("Room Availability", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Star Jedi Outline", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Room Type", "Room Number", "Availability", "Price per day", "Customer Name", "Contact #", "Check-in Date", "Check-out Date", "Duration (days)", "Guest Count", "Total Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable availabilityTable = new JTable(tableModel);
        availabilityTable.setFillsViewportHeight(true);
        availabilityTable.setFont(new Font("Arial", Font.PLAIN, 12));
        availabilityTable.setRowHeight(40);

        // Cell renderer for centering and coloring
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        availabilityTable.setDefaultRenderer(Object.class, cellRenderer);

        // Table header customization
        JTableHeader tableHeader = availabilityTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 10));
        tableHeader.setBackground(new Color(36, 63, 96));
        tableHeader.setForeground(Color.WHITE);

        // Populate table model with initial data
        updateTableModel(tableModel);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(availabilityTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Updates the table model with current room availability data.
     * @param tableModel The DefaultTableModel to update.
     */
    private void updateTableModel(DefaultTableModel tableModel) {
        List<Room> rooms = roomManager.getRooms();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        for (Room room : rooms) {
            String availabilityStatus = room.isAvailable() ? "Available" : "Not Available";
            String priceStatus = String.format("Php %.2f", room.getPrice());

            if (!room.isAvailable()) {
                int duration = calculateDuration(room.getCheckInDate(), room.getCheckOutDate());
                double totalPrice = room.calculateTotalPrice(duration, room.getGuestCount());

                // Prepare row data for booked rooms
                Object[] rowData = {room.getType(), room.getNumber(), availabilityStatus, priceStatus, room.getCustomerName(), room.getContactNumber(), dateFormat.format(room.getCheckInDate()), dateFormat.format(room.getCheckOutDate()), duration, room.getGuestCount(), String.format("Php %.2f", totalPrice)};
                tableModel.addRow(rowData);
            } else {
                // Prepare row data for available rooms
                Object[] rowData = {room.getType(), room.getNumber(), availabilityStatus, priceStatus, "", "", "", "", "", "", ""};
                tableModel.addRow(rowData);
            }
        }
    }

    /**
     * Calculates the duration in days between two dates.
     * @param checkInDate The check-in date.
     * @param checkOutDate The check-out date.
     * @return The duration in days as an integer.
     */
    private int calculateDuration(Date checkInDate, Date checkOutDate) {
        if (checkInDate != null && checkOutDate != null) {
            long differenceInMillis = checkOutDate.getTime() - checkInDate.getTime();
            return (int) TimeUnit.MILLISECONDS.toDays(differenceInMillis);
        } else {
            return 0; // Handle the cases where dates are not set properly
        }
    }

    /**
     * Loads an image from the resources.
     * @param imagePath The path to the image file.
     * @return The BufferedImage loaded from the specified path.
     */
    private BufferedImage loadImage(String imagePath) {
        try {
            URL imageUrl = getClass().getClassLoader().getResource(imagePath);
            if (imageUrl != null) {
                return ImageIO.read(imageUrl);
            } else {
                System.err.println("Resource not found: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Entry point for the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager(); // Example RoomManager instance
        SwingUtilities.invokeLater(() -> new AvailabilityFrame(roomManager));
    }

    /**
     * JPanel with background image painting capability.
     */
    class BackgroundPanel extends JPanel {
        private static final long serialVersionUID = 8390420577469614622L;
        private BufferedImage backgroundImage;

        /**
         * Constructs a BackgroundPanel with a background image.
         * @param image The background image to display.
         */
        public BackgroundPanel(BufferedImage image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
