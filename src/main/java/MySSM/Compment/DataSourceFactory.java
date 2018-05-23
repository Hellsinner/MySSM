package MySSM.Compment;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceFactory {
    private static DruidDataSource dataSource = null;
    private static final String Driver = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/ttms";
    private static final String USER = "root";
    private static final String PASS = "966815";
    static {
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(Driver);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
