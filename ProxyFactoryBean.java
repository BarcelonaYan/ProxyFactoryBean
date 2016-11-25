#ProxyFactoryBean类实现

package Aopproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;

public class ProxyFactoryBean {

    private Object target;
    private Advice ad;

    public Object getProxy() {
        //利用Proxy类的静态方法newProxyInstance创建代理对象
        Object proxyobj = Proxy.newProxyInstance(
        target.getClass().getClassLoader(),
        target.getClass().getInterfaces(),
        new InvocationHandler(){

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ad.beforeMethod(method);
                Object obj = method.invoke(target, args);
                ad.afterMethod(method);
                return obj;
            }
        }           
        );
        return proxyobj;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Advice getAd() {
        return ad;
    }

    public void setAd(Advice ad) {
        this.ad = ad;
    }
}
