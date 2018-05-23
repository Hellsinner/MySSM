package MySSM.Compment;

import java.lang.reflect.Proxy;
import java.sql.SQLException;

public class MyDefalutSqlSession implements MySqlSession{

    private MyExecutor executor = new MyBaseExecutor();
    @Override
    public <T> T selectOne(String sql,Class<?> returnType,String... param) throws SQLException {
        return executor.query(sql,returnType,param);
    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new MyMapperProxy(this));
    }
}
