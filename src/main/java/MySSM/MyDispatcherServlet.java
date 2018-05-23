package MySSM;

import MySSM.annocation.MyController;
import MySSM.annocation.MyRequestMapping;
import MySSM.helper.BeanHelper;
import MySSM.helper.iocHelper;
import MySSM.utils.ClassUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class MyDispatcherServlet extends HttpServlet{
    private Map<String,Method> handlerMapping = new HashMap<>();

    private String basePackage = Constant.basePackage;

    private Map<String,Object> controllerMap = new HashMap<>();

    private static final List<String> classNames = new ArrayList<>(30);
    @Override
    public void init(ServletConfig config) throws ServletException {
        //扫描所有类的全路径名
        ClassUtils.doScanner(basePackage);
        //通过反射获取类的Class对象
        ClassUtils.doClass();
        //实例化对象
        BeanHelper.doInstance();
        //进行依赖注入
        iocHelper.ioc();
        //初始化handler映射
        initHandlerMapping();
    }

    private void initHandlerMapping() {
        Map<String,Object> beanMap = BeanHelper.getBeanMap();
        if (beanMap.isEmpty()){
            return;
        }
        try {
            for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
                Class<? extends Object> clazz = entry.getValue().getClass();
                if (!clazz.isAnnotationPresent(MyController.class))
                    continue;
                String baseUrl = "";
                if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
                    MyRequestMapping annotation = clazz.getAnnotation(MyRequestMapping.class);
                    baseUrl = annotation.value();
                }
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(MyRequestMapping.class))
                        continue;
                    MyRequestMapping annotation = method.getAnnotation(MyRequestMapping.class);
                    String url = annotation.value();
                    url = (baseUrl + "/" + url).replaceAll("/+", "/");
                    handlerMapping.put(url, method);
                    controllerMap.put(url, entry.getValue());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        }catch (Exception e){
            resp.getWriter().write("500!!!Server Exception");
        }
    }

    private void doDispatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
        if (handlerMapping.isEmpty())
            return;
        String uri = request.getRequestURI();
        String contentPath = request.getContextPath();
        uri = uri.replace(contentPath,"").replaceAll("/+","/");
        if (!handlerMapping.containsKey(uri)){
            response.getWriter().write("404 Not Found");
            return;
        }
        Method method = handlerMapping.get(uri);
        //获取方法参数列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        //获取请求的参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //保存参数值
        Object[] paramValues = new Object[parameterTypes.length];
        for (int i=0;i<parameterTypes.length;i++){
            //根据参数名，进行处理
            String requestParam = parameterTypes[i].getSimpleName();
            if (requestParam.equals("HttpServletRequest")){
                paramValues[i]=request;
                continue;
            }
            if (requestParam.equals("HttpServletResponse")){
                paramValues[i] = response;
                continue;
            }
            if (requestParam.equals("String")){
                for (Map.Entry<String,String[]> param : parameterMap.entrySet()){
                    String value = Arrays.toString(param.getValue())
                            .replaceAll("\\[|\\]","")
                            .replaceAll(",\\s",",");
                    paramValues[i] = value;
                }
            }
        }
        //利用反射调用方法
        try {
            method.invoke(controllerMap.get(uri),paramValues);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
