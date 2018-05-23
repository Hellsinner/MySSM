package MySSM.Dao.impl;

import MySSM.Bean.Employee;
import MySSM.Compment.Mapping;
import MySSM.Compment.MyDefalutSqlSession;
import MySSM.Compment.MySqlSession;
import MySSM.Dao.EmployeeMapper;
import MySSM.Dao.EmployeeMapperXML;
import MySSM.annocation.MyDao;

@MyDao
public class EmployeeImpl{
    private MySqlSession sqlSession = new MyDefalutSqlSession();

    public Employee findById(String id) {
        String sql = "select * from employee where emp_id = %s";
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        EmployeeMapperXML.methodSqlMap.put("findById",sql);
        Mapping.nameMapping.put(EmployeeMapperXML.namespace,EmployeeMapperXML.methodSqlMap);
        Employee employee = mapper.findById(id);
        return employee;
    }


}
