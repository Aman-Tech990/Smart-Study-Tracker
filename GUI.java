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

    JLabel totalSessionsLabel, totalHoursLabel, topSubjectLabel;
    BarChartPanel chartPanel;

    public GUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Smart Study & DSA Tracker - Analytics Edition");
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel mainContainer = new JPanel(new BorderLayout(15, 15));
        mainContainer.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainContainer.setBackground(new Color(245, 247, 250));

        mainContainer.add(createFormPanel(), BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout(15, 15));
        rightPanel.setOpaque(false);
        
        JPanel topAnalyticsPanel = new JPanel(new BorderLayout(0, 15));
        topAnalyticsPanel.setOpaque(false);
        topAnalyticsPanel.add(createStatsPanel(), BorderLayout.NORTH);
        
        chartPanel = new BarChartPanel();
        chartPanel.setPreferredSize(new Dimension(600, 200));
        topAnalyticsPanel.add(chartPanel, BorderLayout.CENTER);

        rightPanel.add(topAnalyticsPanel, BorderLayout.NORTH);
        
        rightPanel.add(createTablePanel(), BorderLayout.CENTER);

        mainContainer.add(rightPanel, BorderLayout.CENTER);
        add(mainContainer);

        loadTable();

        setVisible(true);
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatCard("Total Sessions", totalSessionsLabel = new JLabel("0")));
        statsPanel.add(createStatCard("Total Hours", totalHoursLabel = new JLabel("0 hrs")));
        statsPanel.add(createStatCard("Top Subject", topSubjectLabel = new JLabel("-")));

        return statsPanel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 232), 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(100, 110, 120));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(new Color(41, 128, 185)); 

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(280, 0));
        
        TitledBorder border = BorderFactory.createTitledBorder("Log New Session");
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                border,
                new EmptyBorder(10, 15, 15, 15)
        ));
        
        formPanel.add(createInputRow("Subject (e.g., Java, DSA):", subjectField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createInputRow("Topic:", topicField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createInputRow("Duration (hrs):", durationField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createInputRow("Date (YYYY-MM-DD):", dateField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(20));

        JButton addBtn = createStyledButton("Add Session", new Color(46, 204, 113));
        addBtn.addActionListener(e -> handleAddSession());
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(addBtn);
        
        formPanel.add(btnPanel);
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
        row.setMaximumSize(new Dimension(250, 60));
        return row;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
        tablePanel.setOpaque(false);

        model = new DefaultTableModel(new String[]{"ID", "Subject", "Topic", "Duration", "Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(245, 247, 250));
        header.setPreferredSize(new Dimension(100, 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 232)));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JButton deleteBtn = createStyledButton("Delete Selected", new Color(231, 76, 60));
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
        btn.setPreferredSize(new Dimension(150, 35));
        return btn;
    }

    private void handleAddSession() {
        try {
            String subject = subjectField.getText().trim();
            String topic = topicField.getText().trim();
            int duration = Integer.parseInt(durationField.getText().trim());
            String date = dateField.getText().trim();

            if(subject.isEmpty() || topic.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields");
                return;
            }

            dao.addSession(subject, topic, duration, date);
            loadTable();
            subjectField.setText("");
            topicField.setText("");
            durationField.setText("");
            dateField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Duration must be a valid number");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleDeleteSession() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) return;
            int id = (int) model.getValueAt(row, 0);
            dao.deleteSession(id);
            loadTable();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            updateAnalytics(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAnalytics() {
        int totalHours = 0;
        int totalSessions = model.getRowCount();
        Map<String, Integer> subjectHours = new HashMap<>();
        Map<String, Integer> subjectCounts = new HashMap<>();

        for (int i = 0; i < totalSessions; i++) {
            String subject = (String) model.getValueAt(i, 1);
            int hours = (int) model.getValueAt(i, 3);
            
            totalHours += hours;
            subjectHours.put(subject, subjectHours.getOrDefault(subject, 0) + hours);
            subjectCounts.put(subject, subjectCounts.getOrDefault(subject, 0) + 1);
        }

        String topSubject = "-";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : subjectCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                topSubject = entry.getKey();
            }
        }

        totalSessionsLabel.setText(String.valueOf(totalSessions));
        totalHoursLabel.setText(totalHours + " hrs");
        topSubjectLabel.setText(topSubject);

        chartPanel.setData(subjectHours);
    }

    class BarChartPanel extends JPanel {
        private Map<String, Integer> data = new HashMap<>();
        private int padding = 35;

        public BarChartPanel() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(220, 224, 232)));
        }

        public void setData(Map<String, Integer> data) {
            this.data = data;
            repaint(); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.isEmpty()) {
                g.drawString("Add sessions to see analytics...", getWidth() / 2 - 80, getHeight() / 2);
                return;
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            g2.drawString("Total Hours Studied per Subject", padding, 20);

            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(padding, height - padding, width - padding, height - padding); 
            g2.drawLine(padding, padding, padding, height - padding); 

            int maxHours = data.values().stream().max(Integer::compareTo).orElse(1);
            int barAreaWidth = width - (2 * padding);
            int barAreaHeight = height - (2 * padding);
            
            int numberOfBars = data.size();
            int spaceBetweenBars = 20;
            int barWidth = (barAreaWidth - (spaceBetweenBars * (numberOfBars + 1))) / numberOfBars;
            barWidth = Math.min(barWidth, 80); 

            int xOffset = padding + spaceBetweenBars;

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                String subject = entry.getKey();
                int hours = entry.getValue();

                int barHeight = (int) (((double) hours / maxHours) * (barAreaHeight - 20));

                g2.setColor(new Color(52, 152, 219)); 
                g2.fillRect(xOffset, height - padding - barHeight, barWidth, barHeight);

                g2.setColor(Color.BLACK);
                String valStr = hours + "h";
                int valWidth = g2.getFontMetrics().stringWidth(valStr);
                g2.drawString(valStr, xOffset + (barWidth / 2) - (valWidth / 2), height - padding - barHeight - 5);

                String subStr = subject.length() > 10 ? subject.substring(0, 8) + ".." : subject;
                int subWidth = g2.getFontMetrics().stringWidth(subStr);
                g2.drawString(subStr, xOffset + (barWidth / 2) - (subWidth / 2), height - padding + 15);

                xOffset += barWidth + spaceBetweenBars;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }
}