package gr.aueb.cf.schoolapp.service.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {

    private final static BasicDataSource ds = new BasicDataSource();
    private static Connection conn;

    /**
     * No instances of this class should be available
     */
    private DBUtil(){ }

    static {
        /**
         * thelei allagi
         */
        ds.setUrl("jdbc:mysql://localhost:3306/school?serverTimeZone=UTC");
        ds.setUsername("user1");
        ds.setPassword(System.getenv("SCHOOL_ADMIN_PASSWORD"));
        ds.setInitialSize(8);
        ds.setMaxTotal(32);
        ds.setMinIdle(8);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        /**
         * thelei allagi
         */
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = ds.getConnection();
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
