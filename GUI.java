import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class GUI extends JFrame {

    JTextField subjectField, topicField, durationField, dateField;
    JTable table;
    DefaultTableModel model;
    StudySessionDAO dao = new StudySessionDAO();

    // Analytics Labels
    JLabel totalSessionsLabel, totalHoursLabel, topSubjectLabel;

    public GUI() {
        // 1. Set System Look and Feel for a modern UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Smart Study & DSA Tracker");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout(10, 10)); // Add gaps between regions

        // Main container with padding
        JPanel mainContainer = new JPanel(new BorderLayout(15, 15));
        mainContainer.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainContainer.setBackground(new Color(245, 247, 250));

        // --- DASHBOARD PANEL (Analytics) ---
        mainContainer.add(createDashboardPanel(), BorderLayout.NORTH);

        // --- CENTER PANEL (Split into Form and Table) ---
        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setOpaque(false);
        
        centerPanel.add(createFormPanel(), BorderLayout.WEST);
        centerPanel.add(createTablePanel(), BorderLayout.CENTER);

        mainContainer.add(centerPanel, BorderLayout.CENTER);
        add(mainContainer);

        // Initial Data Load
        loadTable();

        setVisible(true);
    }

    private JPanel createDashboardPanel() {
        JPanel dashboard = new JPanel(new GridLayout(1, 3, 15, 0));
        dashboard.setOpaque(false);

        // Create Stat Cards
        JPanel sessionsCard = createStatCard("Total Sessions", totalSessionsLabel = new JLabel("0"));
        JPanel hoursCard = createStatCard("Total Hours Studied", totalHoursLabel = new JLabel("0 hrs"));
        JPanel subjectCard = createStatCard("Top Subject", topSubjectLabel = new JLabel("-"));

        dashboard.add(sessionsCard);
        dashboard.add(hoursCard);
        dashboard.add(subjectCard);

        return dashboard;
    }

    private JPanel createStatCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 232), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(100, 110, 120));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(new Color(41, 128, 185)); // Professional Blue

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        
        TitledBorder border = BorderFactory.createTitledBorder("Log New Session");
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                border,
                new EmptyBorder(10, 15, 15, 15)
        ));

        // Form fields with standard sizing
        Dimension fieldSize = new Dimension(200, 30);
        
        formPanel.add(createInputRow("Subject:", subjectField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createInputRow("Topic:", topicField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createInputRow("Duration (hrs):", durationField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createInputRow("Date (YYYY-MM-DD):", dateField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(20));

        // Buttons
        JButton addBtn = createStyledButton("Add Session", new Color(46, 204, 113));
        addBtn.addActionListener(e -> handleAddSession());
        
        JButton clearBtn = createStyledButton("Clear Fields", new Color(149, 165, 166));
        clearBtn.addActionListener(e -> clearFields());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(addBtn);
        btnPanel.add(clearBtn);
        
        formPanel.add(btnPanel);
        
        // Push everything to the top
        formPanel.add(Box.createVerticalGlue());
        
        return formPanel;
    }

    private JPanel createInputRow(String labelText, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(5, 5));
        row.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setPreferredSize(new Dimension(220, 30));
        row.add(label, BorderLayout.NORTH);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
        tablePanel.setOpaque(false);

        model = new DefaultTableModel(new String[]{"ID", "Subject", "Topic", "Duration", "Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent accidental edits in UI
            }
        };
        table = new JTable(model);
        
        // Style the table
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(Color.BLACK);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));

        // Style the header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(245, 247, 250));
        header.setPreferredSize(new Dimension(100, 35));

        // Center align table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 232)));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Delete Button
        JButton deleteBtn = createStyledButton("Delete Selected Session", new Color(231, 76, 60));
        deleteBtn.addActionListener(e -> handleDeleteSession());
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(deleteBtn);
        tablePanel.add(bottomPanel, BorderLayout.SOUTH);

        return tablePanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 20, 35));
        return btn;
    }

    private void handleAddSession() {
        try {
            String subject = subjectField.getText().trim();
            String topic = topicField.getText().trim();
            int duration = Integer.parseInt(durationField.getText().trim());
            String date = dateField.getText().trim();

            if(subject.isEmpty() || topic.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            dao.addSession(subject, topic, duration, date);
            loadTable();
            clearFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Duration must be a valid number", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding session", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteSession() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first to delete", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = (int) model.getValueAt(row, 0);
            
            // Add a confirmation dialog
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this session?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.deleteSession(id);
                loadTable();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting session", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        subjectField.setText("");
        topicField.setText("");
        durationField.setText("");
        dateField.setText("");
    }

    private void loadTable() {
        try {
            model.setRowCount(0);
            Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sessions");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("subject"),
                        rs.getString("topic"),
                        rs.getInt("duration"),
                        rs.getString("date")
                });
            }
            
            updateAnalytics(); // Call analytics updater after loading data

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- ANALYTICS ENGINE ---
    private void updateAnalytics() {
        int totalHours = 0;
        int totalSessions = model.getRowCount();
        Map<String, Integer> subjectCounts = new HashMap<>();

        for (int i = 0; i < totalSessions; i++) {
            // Calculate Hours
            totalHours += (int) model.getValueAt(i, 3);
            
            // Calculate Top Subject
            String subject = (String) model.getValueAt(i, 1);
            subjectCounts.put(subject, subjectCounts.getOrDefault(subject, 0) + 1);
        }

        // Find the most frequent subject
        String topSubject = "-";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : subjectCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                topSubject = entry.getKey();
            }
        }

        // Update UI Labels
        totalSessionsLabel.setText(String.valueOf(totalSessions));
        totalHoursLabel.setText(totalHours + " hrs");
        topSubjectLabel.setText(topSubject);
    }

    public static void main(String[] args) {
        // Run on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> new GUI());
    }
}