package MySSM.Compment;

import java.sql.SQLException;

public interface MyExecutor {
    public <T> T query(String sql,Class<?> returnType,String... statement) throws SQLException;
}
