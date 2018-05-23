package MySSM.Dao;

import MySSM.Bean.Employee;

public interface EmployeeMapper {
    public Employee findById(String id);
}
