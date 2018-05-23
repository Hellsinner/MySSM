package MySSM.Service;

import MySSM.Bean.Employee;
import MySSM.Dao.impl.EmployeeImpl;
import MySSM.annocation.MyAutowried;
import MySSM.annocation.MyController;

@MyController
public class EmployeeService {
    @MyAutowried
    private EmployeeImpl employeeImpl;

    public Employee findById(String id){
        return employeeImpl.findById(id);
    }
}
