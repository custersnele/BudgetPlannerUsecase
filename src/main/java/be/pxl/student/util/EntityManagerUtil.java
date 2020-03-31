package be.pxl.student.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Enumeration;


@WebListener
public class EntityManagerUtil implements ServletContextListener {
	private static final Logger LOGGER = LogManager.getLogger(EntityManagerUtil.class);

	private static EntityManagerFactory emf;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		emf = Persistence.createEntityManagerFactory("musicdb_pu");
		LOGGER.debug("*** Persistence started at " + LocalDateTime.now());
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// This manually deregisters JDBC driver, which prevents TomEE from complaining about memory leaks
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				LOGGER.info(String.format("deregistering jdbc driver: %s", driver));
			} catch (SQLException e) {
				LOGGER.fatal(String.format("Error deregistering driver %s", driver), e);
			}
		}
		if (emf != null) {
			emf.close();
			LOGGER.info("*** Persistence finished at " + LocalDateTime.now());
		}
	}

	public static EntityManager createEntityManager() {
		if (emf != null) {
			return emf.createEntityManager();
		}
		return null;
	}

}
