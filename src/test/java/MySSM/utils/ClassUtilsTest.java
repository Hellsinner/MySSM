package MySSM.utils;

import org.junit.Test;

public class ClassUtilsTest {
    @Test
    public void getClassMap() throws Exception{
        Class<?> clazz = Class.forName("MySSM.Service.EmployeeService");
        System.out.println(clazz.getName());
        System.out.println(clazz.getSimpleName());
    }
}