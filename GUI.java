import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {

    JTextField subjectField, topicField, durationField, dateField;
    JTable table;
    DefaultTableModel model;
    StudySessionDAO dao = new StudySessionDAO();

    public GUI() {
        setTitle("Smart Study Tracker");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel (Form)
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Subject:"));
        subjectField = new JTextField();
        panel.add(subjectField);

        panel.add(new JLabel("Topic:"));
        topicField = new JTextField();
        panel.add(topicField);

        panel.add(new JLabel("Duration (hrs):"));
        durationField = new JTextField();
        panel.add(durationField);

        panel.add(new JLabel("Date:"));
        dateField = new JTextField();
        panel.add(dateField);

        JButton addBtn = new JButton("Add Session");
        panel.add(addBtn);

        JButton deleteBtn = new JButton("Delete Selected");
        panel.add(deleteBtn);

        add(panel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Subject", "Topic", "Duration", "Date"}, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Loading Data
        loadTable();

        addBtn.addActionListener(e -> {
            try {
                String subject = subjectField.getText();
                String topic = topicField.getText();
                int duration = Integer.parseInt(durationField.getText());
                String date = dateField.getText();

                dao.addSession(subject, topic, duration, date);

                loadTable();

                subjectField.setText("");
                topicField.setText("");
                durationField.setText("");
                dateField.setText("");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding session");
            }
        });
        
        deleteBtn.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Select a row first");
                    return;
                }

                int id = (int) model.getValueAt(row, 0);

                dao.deleteSession(id);

                loadTable();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting session");
            }
        });

        setVisible(true);
    }

    // Loading Data into Table
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}