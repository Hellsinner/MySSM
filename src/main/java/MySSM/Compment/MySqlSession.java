package MySSM.Compment;

import java.sql.SQLException;

public interface MySqlSession {
    <T> T selectOne(String sql,Class<?> returnType,String... param) throws SQLException;
    <T> T getMapper(Class<T> clazz);
}
