package MySSM.helper;

import MySSM.annocation.MyController;
import MySSM.annocation.MyDao;
import MySSM.annocation.MyService;
import MySSM.utils.ClassUtils;

import java.util.HashSet;
import java.util.Set;

public class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;
    static {
        CLASS_SET = ClassUtils.getClassSet();
    }

    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }
    //获取带注解的Class对象
    public static Set<Class<?>> getAnnoClassSet(){
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> clazz : CLASS_SET){
            if (clazz.isAnnotationPresent(MyController.class) ||
                    clazz.isAnnotationPresent(MyService.class) ||
                    clazz.isAnnotationPresent(MyDao.class)){
                set.add(clazz);
            }
        }
        //System.out.println(set);
        return set;
    }
}
