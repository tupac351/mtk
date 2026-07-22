package onthi.libapp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnector {
    private static JdbcConnector instance;
    private Connection conn;

    private JdbcConnector() {}

    public static JdbcConnector getInstance() {
        if (instance == null) {
            instance = new JdbcConnector();
        }
        return instance;
    }

    public Connection connect() throws SQLException {
        // LƯU Ý: Đổi đường dẫn tuyệt đối tới file DB của bạn khi đi thi
        String url = "jdbc:sqlite:D:/libapp.db"; 
        conn = DriverManager.getConnection(url);
        return conn;
    }
}