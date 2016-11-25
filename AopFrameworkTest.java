#测试类
package Aopproxy;

import java.io.InputStream;
import java.util.Collection;

public class AopFrameworkTest {

    public static void main(String[] args) {
        InputStream ips = AopFrameworkTest.class.getResourceAsStream("config.properties");
        //创建工厂
        BeanFactory beanFactory = new BeanFactory(ips);
        //获取对象并打印对应字节码名字
        Object bean = beanFactory.getBean("bean");
        System.out.println(bean.getClass().getName());
        //下面语句可以测试Advice类是否有效
        //((Collection)bean).clear();
    }

}
