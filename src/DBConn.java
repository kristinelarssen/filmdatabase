import java.sql.*;
import java.util.Properties;
public abstract class DBConn {

    protected static Connection conn;

    public DBConn() {
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "nidarosdomen123");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kriss", p);
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}
