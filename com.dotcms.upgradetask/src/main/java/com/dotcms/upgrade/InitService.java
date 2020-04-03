package com.dotcms.upgrade;

import com.dotcms.config.DotInitializationService;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.CacheLocator;
import com.dotmarketing.business.FactoryLocator;
import com.dotmarketing.util.Config;
import com.dotmarketing.util.Logger;
import com.liferay.util.SystemProperties;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Sets up the web environment needed to execute integration
 */
public class InitService {

    private static InitService service = new InitService();

    private static final AtomicBoolean initCompleted = new AtomicBoolean(false);

    static {
        SystemProperties.getProperties();
    }

    private InitService() {
    }

    public static InitService getInstance() {
        return service;
    }

    public void init() throws Exception {
        try {
            if (initCompleted.compareAndSet(false, true)) {
                ConfigTestHelper._setupFakeTestingContext();

                CacheLocator.init();
                FactoryLocator.init();
                APILocator.init();
                Config.initializeConfig();
                //For these tests fire the reindex immediately
                Config.setProperty("ASYNC_REINDEX_COMMIT_LISTENERS", false);
                Config.setProperty("ASYNC_COMMIT_LISTENERS", false);

                Config.setProperty("NETWORK_CACHE_FLUSH_DELAY", (long) 0);
                // Init other dotCMS services.
                DotInitializationService.getInstance().initialize();
            }
        } catch(Exception e) {
            Logger.error(this, "Error initializing Integration Test Init Service", e);
        }
    }

}
