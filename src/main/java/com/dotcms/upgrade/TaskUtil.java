package com.dotcms.upgrade;

import com.dotmarketing.exception.DotDataException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import static com.dotmarketing.util.Constants.DATABASE_DEFAULT_DATASOURCE;

public class TaskUtil {

    private static DataSource defaultDataSource = null;
    private String DB_VERSION = "SELECT max(db_version) AS db_version FROM db_version";

    public int getDBVersion() throws SQLException, DotDataException {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DB_VERSION);
                ResultSet resultSet = statement.executeQuery()) {

            return  resultSet.next()?
                    resultSet.getInt(1):0;
        }
    }

    public static Connection getConnection() throws SQLException {

        return getDataSource().getConnection();
    }

    public static DataSource getDataSource() {
        if (defaultDataSource == null) {
            synchronized (TaskUtil.class) {
                if (defaultDataSource == null) {

                    try {

                        defaultDataSource = DBPropertiesDataSourceStrategy.getInstance().apply();

                        LocalContext context = LocalContextFactory.createLocalContext();
                        context.addDataSource(DATABASE_DEFAULT_DATASOURCE, defaultDataSource);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return defaultDataSource;
    }


}
