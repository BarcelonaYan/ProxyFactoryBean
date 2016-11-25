# BeanFactory类实现，先获取配置文件中需要创建的类的名字，然后先调用不带参数的构造方法创建一个实例对象（配置中的要求创建的类必须是JavaBean，
否则不一定具有不带参数的构造方法），通过实例对象判断是否是ProxyFactoryBean对象，如果是，则转换成ProxyFactoryBean对象，
调用ProxyFactoryBean类中方法getProxy来创建代理对象。

package Aopproxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BeanFactory {

    Properties props = new Properties();
    public BeanFactory(InputStream ips){
        try {
            props.load(ips);//读入配置文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String key){
        //这个方法的用途是：获得一个key所指的类对象，
        //如果这个类是ProxyFactoryBean类的，即代理类，则创建代理对象并返回
        //如果不是则直接调用不带参数的构造方法创建对象返回
        String clazzname = props.getProperty(key);
        Object bean = null;
        try {
            Class clazz = Class.forName(clazzname);
            bean = clazz.newInstance();//对于javabean必须有不带参数的构造方法
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(bean instanceof ProxyFactoryBean)
        {
            //转换成ProxyFactoryBean对象，方便使用getProxy方法和set方法
            ProxyFactoryBean proxyFactoryBean = (ProxyFactoryBean)bean;
            Object proxy = null;
            try {
                //取得所想建立的对象目标和Advice类
                Object target = Class.forName(props.getProperty(key+".target")).newInstance();
                Advice ad = (Advice)Class.forName(props.getProperty(key+".advice")).newInstance();
                proxyFactoryBean.setTarget(target);
                proxyFactoryBean.setAd(ad);

                proxy = proxyFactoryBean.getProxy();
            } catch (Exception ex) {
                ex.printStackTrace();
            }   
            return proxy;
        }
        return bean;
    }
}
