package MySSM.Compment;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyMapperProxy implements InvocationHandler{
    private MySqlSession sqlSession;
    public MyMapperProxy(){}
    public MyMapperProxy(MySqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperClass = method.getDeclaringClass().getName();
        if (Mapping.nameMapping.containsKey(mapperClass)){
            String methodName = method.getName();
            String sql = Mapping.nameMapping.get(mapperClass).get(methodName);
            sql = replaceSql(sql,args);
            Class<?> returnType = method.getReturnType();
            return sqlSession.selectOne(sql,returnType);
        }
        return null;
    }

    public String replaceSql(String sql,Object[] param){
        for (int i=0;i<param.length;i++){
            sql = sql.replace("%s",param[i]+"");
        }
        return sql;
    }
}
