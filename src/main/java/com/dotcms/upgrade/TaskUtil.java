package com.dotcms.upgrade;

import com.dotcms.business.CloseDB;
import com.dotmarketing.common.db.DotConnect;

public class TaskUtil {

    private String DB_VERSION = "SELECT max(db_version) AS db_version FROM db_version";
    
    @CloseDB
    public int getDBVersion() {
        return new DotConnect().setSQL(DB_VERSION).getInt("db_version");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
