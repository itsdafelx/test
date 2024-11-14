package edu.bbte.idde.boim2218.backend.dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public final class ConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

    private ConnectionPool() {
    }

    private static final class ConnectionPoolHolder {
        private static final HikariDataSource DATA_SOURCE;

        static {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost/CalendarEvents");
            config.setUsername("root");
            config.setPassword("password");
            config.setMaximumPoolSize(10);
            DATA_SOURCE = new HikariDataSource(config);
            logger.info("DataSource successfully initialized.");
        }
    }

    public static DataSource getDataSource() {
        return ConnectionPoolHolder.DATA_SOURCE;
    }
}
