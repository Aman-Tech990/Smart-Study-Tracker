import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

    public static Connection connect() throws Exception {

        Class.forName("org.sqlite.JDBC");

        String url = "jdbc:sqlite:studytracker.db";

        Connection conn = DriverManager.getConnection(url);

        Statement stmt = conn.createStatement();

        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS sessions (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "subject TEXT," +
            "topic TEXT," +
            "duration INTEGER," +
            "date TEXT)"
        );

        return conn;
    }
}