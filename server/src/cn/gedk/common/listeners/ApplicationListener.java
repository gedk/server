package cn.gedk.common.listeners;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import cn.gedk.utils.Constant;

public class ApplicationListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0){}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Properties prop = new Properties();
		InputStream in = Object.class.getResourceAsStream("/project.properties");
		try {
			prop.load(in);
			Set<?> keyset = prop.keySet();
			for (Object object : keyset) {
				System.out.println("======================加载配置文件开始==========================");
				Constant.properties.put(object.toString(),prop.getProperty(object.toString()).toString());
				System.out.println(object.toString()+"="+prop.getProperty(object.toString()).toString());
				System.out.println("======================加载配置文件结束==========================");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
