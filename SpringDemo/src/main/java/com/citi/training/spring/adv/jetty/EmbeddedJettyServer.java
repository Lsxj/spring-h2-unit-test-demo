package com.citi.training.spring.adv.jetty;

import com.citi.training.spring.adv.db.DatabaseUtils;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.lang.management.ManagementFactory;

public class EmbeddedJettyServer {
	private static final int HTTPPORT = 8080;
	private static Server server;
	private static final String CONTEXT = "/";

	public static void main(String[] args) throws Exception {
		server = new Server(HTTPPORT);

		MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
		server.addBean(mbContainer);

		WebAppContext wac = new WebAppContext();
		wac.setServer(server);
		wac.setContextPath(CONTEXT);
		File warfile = new File("web");
		wac.setWar(warfile.getAbsolutePath());
		wac.setExtractWAR(true);

		Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);

		classlist.addAfter(FragmentConfiguration.class.getName(),
		EnvConfiguration.class.getName(),
		PlusConfiguration.class.getName());

		classlist.addBefore(JettyWebXmlConfiguration.XML_CONFIGURATION, AnnotationConfiguration.class.getName());

		new Resource(wac, "jdbc/mydatasource", DatabaseUtils.getDataSource("database"));
		//need to delete web.xml <resource-ref> or it will throw exception
		//then can use @autowire JdbcTemplate

		server.setHandler(wac);
		server.setStopAtShutdown(true);

		try {
			server.start();
//			server.dumpStdErr();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
