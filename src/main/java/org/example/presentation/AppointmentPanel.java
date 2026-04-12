package org.example.presentation;

import org.example.domain.appointment.*;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.enums.AppointmentType;
import org.example.domain.valueobject.TimeSlot;
import org.example.service.AppointmentService;
import org.example.service.AuthService;
import org.example.service.ScheduleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthService authService;
    private final AppointmentService appointmentService;
    private final ScheduleService scheduleService;

    private DefaultListModel<String> slotModel;
    private JList<String> slotList;
    private List<TimeSlot> availableSlots;
    private AppointmentType selectedType = null;
    private JButton typeButton;
    private JSpinner participantsSpinner;
    private JLabel statusLabel;

    public AppointmentPanel(MainFrame mainFrame,
                            AuthService authService,
                            AppointmentService appointmentService,
                            ScheduleService scheduleService) {
        this.mainFrame = mainFrame;
        this.authService = authService;
        this.appointmentService = appointmentService;
        this.scheduleService = scheduleService;
        this.availableSlots = new ArrayList<>();
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 244, 248));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 244, 248));

        JLabel title = new JLabel("Book an Appointment");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(25, 80, 170));
        topBar.add(title, BorderLayout.WEST);
        
        JButton backButton = new JButton("← Back");
        styleButton(backButton, new Color(100, 100, 100));
        backButton.addActionListener(e -> mainFrame.showPanel(MainFrame.DASHBOARD_PANEL));
        topBar.add(backButton, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        slotModel = new DefaultListModel<>();
        slotList = new JList<>(slotModel);
        slotList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        slotList.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane slotScroll = new JScrollPane(slotList);
        slotScroll.setPreferredSize(new Dimension(320, 0));
        slotScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 230)),
                "Available Slots",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12),
                new Color(25, 80, 170)));
        add(slotScroll, BorderLayout.WEST);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 230)),
                        "Booking Details",
                        TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("SansSerif", Font.BOLD, 12),
                        new Color(25, 80, 170)),
                new EmptyBorder(10, 15, 10, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Appointment Type:"), gbc);
        typeButton = new JButton("Choose Type...");
        typeButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        typeButton.setBackground(new Color(230, 240, 255));
        typeButton.setForeground(new Color(25, 80, 170));
        typeButton.setFocusPainted(false);
        typeButton.setBorderPainted(true);
        typeButton.setOpaque(true);
        typeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        typeButton.addActionListener(e -> openTypeDialog());
        gbc.gridx = 1;
        formPanel.add(typeButton, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Participants:"), gbc);
        participantsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        participantsSpinner.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1;
        formPanel.add(participantsSpinner, gbc);

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actionsPanel.setBackground(Color.WHITE);

        JButton bookButton = new JButton("Book Selection");
        styleButton(bookButton, new Color(25, 140, 60));
        bookButton.addActionListener(e -> handleBooking());

        JButton customButton = new JButton("Custom Appointment");
        styleButton(customButton, new Color(20, 120, 100));
        customButton.addActionListener(e -> {
            mainFrame.getEditAppointmentPanel().clearFields();
            mainFrame.showPanel(MainFrame.EDIT_APPOINTMENT_PANEL);
        });

        actionsPanel.add(bookButton);
        actionsPanel.add(customButton);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(actionsPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void openTypeDialog() {
        AppointmentType[] types = AppointmentType.values();
        String[] typeNames = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            typeNames[i] = types[i].name().replace("_", " ");
        }

        JList<String> list = new JList<>(typeNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("SansSerif", Font.PLAIN, 14));
        list.setFixedCellHeight(30);
        if (selectedType != null) {
            for (int i = 0; i < types.length; i++) {
                if (types[i] == selectedType) { list.setSelectedIndex(i); break; }
            }
        }

        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(220, 220));

        int result = JOptionPane.showConfirmDialog(
                this, scroll, "Select Appointment Type",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION && list.getSelectedIndex() >= 0) {
            selectedType = types[list.getSelectedIndex()];
            typeButton.setText(typeNames[list.getSelectedIndex()]);
            typeButton.setForeground(new Color(20, 120, 50));
        }
    }

    public void loadAvailableSlots() {
        slotModel.clear();
        availableSlots = scheduleService.getAvailableSlots();
        if (availableSlots == null || availableSlots.isEmpty()) {
            slotModel.addElement("  No available slots.");
        } else {
            for (TimeSlot slot : availableSlots) {
                slotModel.addElement(String.format("  [%d]  %s  %s – %s",
                        slot.getId(),
                        slot.getDate(),
                        slot.getStartTime(),
                        slot.getEndTime()));
            }
        }
        statusLabel.setText(" ");
    }

    private void handleBooking() {
        int selectedIndex = slotList.getSelectedIndex();
        if (selectedIndex < 0 || availableSlots == null || availableSlots.isEmpty()) {
            setStatus("Please select a time slot.", false);
            return;
        }

        if (selectedType == null) {
            setStatus("Please choose an appointment type.", false);
            return;
        }

        TimeSlot selectedSlot = availableSlots.get(selectedIndex);
        int participants = (int) participantsSpinner.getValue();
        User currentUser = authService.getCurrentUser();

        Appointment appointment = createAppointment(0, currentUser, selectedSlot,
                selectedType, AppointmentStatus.CONFIRMED, participants);

        try {
            System.out.println("Processing booking for " + selectedType + "...");
            appointmentService.bookAppointment(appointment);
            setStatus("Appointment booked successfully!", true);
            selectedType = null;
            typeButton.setText("Choose Type...");
            typeButton.setForeground(new Color(25, 80, 170));
            loadAvailableSlots();
        } catch (Exception ex) {
            ex.printStackTrace();
            setStatus("Error: " + ex.getMessage(), false);
        }
    }

    private Appointment createAppointment(int id, User user, TimeSlot slot,
                                          AppointmentType type, AppointmentStatus status,
                                          int participants) {
        return switch (type) {
            case URGENT     -> new UrgentAppointment(id, user, slot, status, participants);
            case FOLLOW_UP  -> new FollowUpAppointment(id, user, slot, status, participants);
            case ASSESSMENT -> new AssessmentAppointment(id, user, slot, status, participants);
            case VIRTUAL    -> new VirtualAppointment(id, user, slot, status, participants);
            case IN_PERSON  -> new InPersonAppointment(id, user, slot, status, participants);
            case INDIVIDUAL -> new IndividualAppointment(id, user, slot, status, participants);
            case GROUP      -> new GroupAppointment(id, user, slot, status, participants);
            case DEFAULT    -> new DefaultAppointment(id, user, slot, status, participants);
        };
    }

    private void setStatus(String message, boolean success) {
        statusLabel.setText(message);
        statusLabel.setForeground(success ? new Color(30, 140, 60) : new Color(200, 40, 40));
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 35));
    }
}
