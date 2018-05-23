package MySSM.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanHelper {
    private static final Map<String,Object> BEAN_MAP = new HashMap<>();

    public static Map<String, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static void doInstance(){
        Set<Class<?>> classSet = ClassHelper.getAnnoClassSet();
        if (classSet.isEmpty()){
            return;
        }
        for (Class<?> clazz : classSet){
            try {
                Object instance = clazz.newInstance();
                BEAN_MAP.put(clazz.getName(),instance);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //System.out.println(BEAN_MAP);
    }
}
