package com.dotcms.upgrade;

import javax.naming.spi.NamingManager;

public class LocalContextFactory {

    private LocalContextFactory() {}

    public static LocalContext createLocalContext() throws Exception {

        LocalContext ctx = new LocalContext();
        NamingManager.setInitialContextFactoryBuilder(ctx);
        return ctx;
    }
}
