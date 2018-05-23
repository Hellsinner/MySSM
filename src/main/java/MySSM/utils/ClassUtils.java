package MySSM.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ClassUtils {
    private static final List<String> classNames = new ArrayList<>(30);
    private static final Set<Class<?>> CLASS_SET = new HashSet<>();

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static List<String> getClassNames() {
        return classNames;
    }
    //扫描所有的包,获取他们的类的全路径名
    public static void doScanner(String basePackage){
        URL url = Thread.currentThread().getContextClassLoader()
                .getResource("/"+basePackage.replaceAll("\\.","/"));

        File dir = new File(url.getFile());

        for (File file : dir.listFiles()){
            if (file.isDirectory()){
                doScanner(basePackage+"."+file.getName());
            }else {
                String className = basePackage+"."+file.getName().replace(".class","");
                classNames.add(className);
            }
        }
    }

    //构建所有类的Class对象
    public static void doClass(){
        if (classNames.isEmpty()){
            return;
        }
        for (String name : classNames){
            try {
                Class<?> clazz = Class.forName(name);
                CLASS_SET.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
