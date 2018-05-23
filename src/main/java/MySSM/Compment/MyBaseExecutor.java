package MySSM.Compment;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyBaseExecutor implements MyExecutor{

    @Override
    public <T> T query(String sql, Class<?> returnType,String... statement)throws SQLException {
        Connection connection = DataSourceFactory.getConnection();
        //sql = replaceSql(sql);
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        ResultSet resultSet = prepareStatement.executeQuery();

        Object instance = null;
        try {
            if (resultSet.next()){
                instance = returnType.newInstance();
                Field[] fields = returnType.getDeclaredFields();
                for (Field field : fields){
                    field.setAccessible(true);
                    field.set(instance,resultSet.getObject(field.getName()));
                }
            }
            return (T) instance;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String replaceSql(String sql,String... param){
        for (int i=0;i<param.length;i++){
            sql = sql.replace("%s",param[i]);
        }
        return sql;
    }
}
