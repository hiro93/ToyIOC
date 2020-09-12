package net.sk32.toy.boot.autoconfigure;

import net.sk32.toy.boot.annotation.TyBootApplication;
import net.sk32.toy.boot.annotation.TyComponent;
import net.sk32.toy.boot.annotation.TyResource;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TyApplicationContext {
    private Map<Class<?>, Object> beans = new ConcurrentHashMap<>();
    private Class<?> primarySource;

    private TyApplicationContext() {}

    private static class SingletonClassInstance {
        private static final TyApplicationContext instance = new TyApplicationContext();
    }

    public static TyApplicationContext getInstance() {
        return SingletonClassInstance.instance;
    }

    public void loadBeans(Class<?> primarySource) {
        this.primarySource = primarySource;
        for (Class<?> aClass : scan(primarySource)) {
            System.out.printf("[SCAN CLASS] %s\n", aClass.toString());

            // TyComponent 注解自动实例化
            if (aClass.isAnnotationPresent(TyComponent.class)) {
                // 这里存在循环依赖的问题
                Object bean = createBean(aClass);
                beans.put(aClass, bean);
            }
        }
    }

    /**
     * 实例化 Bean
     */
    private Object createBean(Class<?> aClass) {
        try {
            // 初始化 Bean
            Object bean = aClass.getDeclaredConstructor().newInstance();
            // 填充属性
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                // 找到所有的 TyResource 注解表示为待填充的属性
                if (field.isAnnotationPresent(TyResource.class)) {
                    Object newBean = getBean(field.getType().getName());
                    field.setAccessible(true);
                    field.set(bean, newBean);
                }
            }
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String name) {
        try {
            Class<?> aClass = Class.forName(name);
            return beans.get(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getBean(Class<T> type) {
        return (T) getBean(type.getName());
    }

    private List<Class<?>> scan(Class<?> entry) {
        ArrayList<Class<?>> array = new ArrayList<>();
        // 扫描入口
        if (entry.isAnnotationPresent(TyBootApplication.class)) {
            ClassLoader classLoader = getClass().getClassLoader();

            // 包名，TyBootApplication 注解必须在程序入口
            String packageName = entry.getPackageName();

            // 找到根文件
            URL url = classLoader.getResource(packageName.replace(".", "/"));
            File rootFile = new File(Objects.requireNonNull(url).getFile());

            array.addAll(scan(rootFile));
        }
        return array;
    }

    private List<Class<?>> scan(File file) {
        ArrayList<Class<?>> array = new ArrayList<>();
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                array.addAll(scan(f));
            }
        } else {
            try {
                String packageName = primarySource.getPackageName().replace(".", "/");
                String fileName = file.getAbsolutePath();
                String classPath = fileName.substring(fileName.indexOf(packageName), fileName.indexOf(".class"));
                classPath = classPath.replace("/", ".");
                Class<?> aClass = getClass().getClassLoader().loadClass(classPath);
                array.add(aClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

}
