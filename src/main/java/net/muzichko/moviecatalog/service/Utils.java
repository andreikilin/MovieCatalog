package net.muzichko.moviecatalog.service;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;

public class Utils {

    public static Connection getConnection(DataSource dataSource, Logger log) {

        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            log.error("Can't get DB connection. " + e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
