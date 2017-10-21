package com.story.classloadertraining;

/**
 * Created by tuyoo on 2017/10/21.
 */

public abstract class ClassLoader {

    private final ClassLoader parent;

    protected final Class<?> findLoadedClass(String name) {
        ClassLoader loader;
        if (this == BootClassLoader.getInstance())
            loader = null;
        else
            loader = this;
        return VMClassLoader.findLoadedClass(loader, name);
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    /**
     * 第一个重要的方法--双亲委派模式
     * 加载指定名称（包括包名）的二进制类型
     * loadClass()方法是ClassLoader类自己实现的，该方法中的逻辑就是双亲委派模式的实现
     * @param name
     * @param resolve 是否生成class对象的同时进行解析相关操作
     * @throws ClassNotFoundException
     */
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // First, check if the class has already been loaded
        // 先从缓存查找该class对象，找到就不用重新加载
        Class<?> c = findLoadedClass(name);
        if (c == null) {
            try {
                if (parent != null) {
                    // 如果找不到，则委托给父类加载器去加载
                    c = parent.loadClass(name, false);
                } else {
                    // 如果没有父类，则委托给启动加载器去加载
                    c = findBootstrapClassOrNull(name);
                }
            } catch (ClassNotFoundException e) {
                // ClassNotFoundException thrown if class not found
                // from the non-null parent class loader
            }

            if (c == null) {
                // If still not found, then invoke findClass in order
                // to find the class.
                // 如果都没有找到，则通过自定义实现的findClass去查找并加载
                c = findClass(name);
            }
        }

        // 是否需要在加载时进行解析
        if (resolve) {
            resolveClass(c);
        }

        return c;
    }

    private Class<?> findBootstrapClassOrNull(String name) {
        return null;
    }

    /**
     * 第二个重要的方法
     * ClassLoader类中并没有实现findClass()方法的具体代码逻辑，取而代之的是抛出ClassNotFoundException异常
     * findClass方法通常是和defineClass方法一起使用的
     * @param name
     * @throws ClassNotFoundException
     */
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        throw new ClassNotFoundException(name);
    }

    /**
     * 第三个重要的方法
     * 用来将byte字节流解析成JVM能够识别的Class对象
     * defineClass()方法通常与findClass()方法一起使用
     * 一般情况下，在自定义类加载器时，会直接覆盖ClassLoader的findClass()方法并编写加载规则，取得要加载类的字节码后转换成流，然后调用defineClass()方法生成类的Class对象
     * @param name
     * @param b
     * @param off
     * @param len
     * @return
     * @throws ClassFormatError
     */
    protected final Class<?> defineClass(String name, byte[] b, int off, int len)
            throws ClassFormatError {
        throw new UnsupportedOperationException("can't load this type of class file");
    }

    /**
     * 可以使类的Class对象创建完成也同时被解析
     * @param c
     */
    protected final void resolveClass(Class<?> c) {

    }
}
