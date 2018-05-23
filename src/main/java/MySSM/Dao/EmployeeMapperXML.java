package MySSM.Dao;

import java.util.HashMap;
import java.util.Map;

public class EmployeeMapperXML {
    public static final String namespace = "MySSM.Dao.EmployeeMapper";

    public static Map<String,String> methodSqlMap = new HashMap<>();

    public static String getMethodSql(String method){
        return methodSqlMap.get(method);
    }
}
