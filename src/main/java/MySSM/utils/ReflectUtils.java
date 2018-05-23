package MySSM.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectUtils {
    private static final Map<String,Object> CLASS_MAP = new HashMap<>();

    public static void doInstance(){
        List<String> classNames = ClassUtils.getClassNames();
        for (String name : classNames){
            try {
                Class<?> clazz = Class.forName(name);
                CLASS_MAP.put(clazz.getName(),clazz);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void setField(Object instance, Field field,Object beanFiledInstance){
        try {
            field.setAccessible(true);
            field.set(instance,beanFiledInstance);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
