package MySSM.Controller;

import MySSM.Bean.Employee;
import MySSM.Service.EmployeeService;
import MySSM.annocation.MyAutowried;
import MySSM.annocation.MyController;
import MySSM.annocation.MyRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MyController
@MyRequestMapping("/test")
public class EmployeeController {

    @MyAutowried
    private EmployeeService employeeService;

    @MyRequestMapping("/doTest")
    public void test1(HttpServletRequest request, HttpServletResponse response,
                      String param){
        System.out.println(param);
        try {
            Employee byId = employeeService.findById(param);
            response.getWriter().write(byId.toString());
            //response.getWriter().write("doTest method success : param:"+param);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @MyRequestMapping("/doTest2")
    public void test2(HttpServletRequest request,HttpServletResponse response){
        try {
            response.getWriter().println("doTest2 success");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
