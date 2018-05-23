package MySSM.helper;

import MySSM.annocation.MyAutowried;
import MySSM.utils.ReflectUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class iocHelper {
    /**
     * 进行依赖注入
     */
    public static void ioc(){
        Map<String,Object> beanMap = BeanHelper.getBeanMap();
        if (!beanMap.isEmpty()){
            for (Map.Entry<String,Object> entry : beanMap.entrySet()){
                Class<?> beanClass = null;
                String className = entry.getKey();
                try {
                    beanClass = Class.forName(className);
                }catch(Exception e){
                    e.printStackTrace();
                }
                Object instance = entry.getValue();
                Field[] fields = beanClass.getDeclaredFields();
                if (fields!=null && fields.length>0){
                    for (Field field : fields){
                        if (field.isAnnotationPresent(MyAutowried.class)){
                            Class<?> cl = field.getType();
                            Object beanFieldInstance = beanMap.get(cl.getName());
                            if (beanFieldInstance!=null){
                                ReflectUtils.setField(instance,field,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
