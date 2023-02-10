package com.edu.mvc3.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {
//서버가 가동되고 멈출때 낚아챔!!

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		String dataPath=application.getInitParameter("dataPath"); //param-name
		String path=application.getRealPath(dataPath);
		application.setAttribute("dataPath", path); //path= 풀경로
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
