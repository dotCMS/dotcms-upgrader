package com.dotcms.upgrade;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import com.dotmarketing.exception.DotRuntimeException;

/**
 * Sets up the web environment needed to execute integration
 */
public class InitService {



    private static final String PROPERTY_FILE_NAME = "upgrade.properties";


    public static void init() {
        try {
            new InitService();
        } catch (Exception e) {
            throw new DotRuntimeException(e);
        }
    }

    public InitService() throws Exception {
        ConfigTestHelper._setupFakeTestingContext();
        
        Properties properties = new Properties();

        File propFile = new File(PROPERTY_FILE_NAME);

        if (!propFile.exists()) {
            System.out.println("Cannot find            :" + propFile.getAbsolutePath());
            propFile = new File(new File(InitService.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                            .getParentFile(), PROPERTY_FILE_NAME);
            System.out.println("Trying                 :" + propFile.getAbsolutePath());
        }
        if (propFile.exists()) {
            System.out.println("Loading props from     :" + propFile.getAbsolutePath());
            try (InputStream in = new FileInputStream(propFile)) {
                properties.load(in);
            }

        } else {
            System.out.println(" Falling back to default:" + PROPERTY_FILE_NAME);
            try (InputStream in = InitService.class.getResourceAsStream(PROPERTY_FILE_NAME)) {
                properties.load(in);
            }
        }
        System.out.println("");

        for(Object key : properties.keySet()) {
            System.setProperty((String) key, properties.getProperty((String) key));
        }
    }



    public static String getProperty(String key, String defaultValue) {
        String x = System.getProperty(key);
        return (x == null) ? defaultValue : x;
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String x = System.getProperty(key);
        return (x == null) ? defaultValue : Boolean.parseBoolean(x);
    }

}
