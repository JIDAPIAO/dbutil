/*
 * Copyright (C) 2016 alchemystar, Inc. All Rights Reserved.
 */
package com.example.dbutil.syncddl.runner;


import com.example.dbutil.syncdata.config.EnvConfig;
import com.example.dbutil.syncdata.vo.DBSetting;
import com.example.dbutil.syncddl.compare.CompareUnits;
import com.example.dbutil.syncddl.meta.MetaData;
import com.example.dbutil.syncddl.util.SqlUtil;
import com.mysql.jdbc.StringUtils;


public class Syncer {

    public static void sync(EnvConfig envConfig, String from, String to) {
        DBSetting fromDbSetting = envConfig.getDBConfig(from);
        DBSetting toDbSetting = envConfig.getDBConfig(to);

        String sourceHost = fromDbSetting.getHost() + ":" + fromDbSetting.getPort();
        if (StringUtils.isEmptyOrWhitespaceOnly(sourceHost)) {
            System.out.println("sourceHost is miss");
            System.exit(-1);
        }
        String sourceUser = fromDbSetting.getUser();
        if (StringUtils.isEmptyOrWhitespaceOnly(sourceUser)) {
            System.out.println("sourceUser is miss");
            System.exit(-1);
        }
        String sourcePass = fromDbSetting.getPassword();
        if (StringUtils.isEmptyOrWhitespaceOnly(sourcePass)) {
            System.out.println("sourcePass is miss");
            System.exit(-1);
        }
        String sourceSchema = fromDbSetting.getDbName();
        if (StringUtils.isEmptyOrWhitespaceOnly(sourceSchema)) {
            System.out.println("sourceSchema is miss");
            System.exit(-1);
        }
        String sourceCharset = fromDbSetting.getCharset();
        if (StringUtils.isEmptyOrWhitespaceOnly(sourceCharset)) {
            System.out.println("sourceCharset is miss");
            System.exit(-1);
        }
        String targetHost = toDbSetting.getHost() + ":" + toDbSetting.getPort();
        if (StringUtils.isEmptyOrWhitespaceOnly(targetHost)) {
            System.out.println("targetHost is miss");
            System.exit(-1);
        }
        String targetUser = toDbSetting.getUser();
        if (StringUtils.isEmptyOrWhitespaceOnly(targetUser)) {
            System.out.println("targetUser is miss");
            System.exit(-1);
        }
        String targetPass = toDbSetting.getPassword();
        if (StringUtils.isEmptyOrWhitespaceOnly(targetPass)) {
            System.out.println("targetPass is miss");
            System.exit(-1);
        }
        String targetSchema = toDbSetting.getDbName();
        if (StringUtils.isEmptyOrWhitespaceOnly(targetSchema)) {
            System.out.println("targetSchema is miss");
            System.exit(-1);
        }
        String targetCharset = toDbSetting.getCharset();
        if (StringUtils.isEmptyOrWhitespaceOnly(targetCharset)) {
            System.out.println("targetCharset is miss");
            System.exit(-1);
        }

        String autoExecute = envConfig.getAutoExecute();
        if (StringUtils.isEmptyOrWhitespaceOnly(autoExecute)) {
            autoExecute = "FALSE";
        }

        if (!sourceCharset.equals(targetCharset)) {
            System.out.println("source target charset not equal");
            System.out.println(-1);
        }

        MetaData source = new MetaData();
        source.setJdbcUrl("jdbc:mysql://" + sourceHost + "/" + sourceSchema + "?characterEncoding=" + sourceCharset);
        source.setUser(sourceUser);
        source.setPassword(sourcePass);
        source.setSchema(sourceSchema);
        source.init();

        MetaData target = new MetaData();
        target.setJdbcUrl("jdbc:mysql://" + targetHost + "/" + targetSchema + "?characterEncoding=" + targetCharset);
        target.setUser(targetUser);
        target.setPassword(targetPass);
        target.setSchema(targetSchema);
        target.init();

        CompareUnits units = new CompareUnits(source, target);
        units.compare();

        for (int i = 0; i < units.getChangeSql().size(); i++) {
            System.out.println(units.getChangeSql().get(i));
            if (autoExecute.equals("YES")) {
                SqlUtil.ddl(target.getConn(), units.getChangeSql().get(i));
            }
        }
    }
}

