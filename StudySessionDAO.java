import java.sql.*;

public class StudySessionDAO {

    public void addSession(String subject, String topic, int duration, String date) throws Exception {

        Connection conn = Database.connect();

        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO sessions(subject, topic, duration, date) VALUES (?, ?, ?, ?)"
        );

        ps.setString(1, subject);
        ps.setString(2, topic);
        ps.setInt(3, duration);
        ps.setString(4, date);

        ps.executeUpdate();

        System.out.println("Session added successfully!");
    }

    public void viewSessions() throws Exception {

        Connection conn = Database.connect();

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM sessions");

        System.out.println("\nStudy Sessions:");

        while (rs.next()) {

            System.out.println(
                rs.getInt("id") + " | " +
                rs.getString("subject") + " | " +
                rs.getString("topic") + " | " +
                rs.getInt("duration") + " hrs | " +
                rs.getString("date")
            );
        }
    }

    public void deleteSession(int id) throws Exception {

        Connection conn = Database.connect();

        PreparedStatement ps = conn.prepareStatement(
            "DELETE FROM sessions WHERE id = ?"
        );

        ps.setInt(1, id);

        ps.executeUpdate();

        System.out.println("Session deleted!");
    }
}