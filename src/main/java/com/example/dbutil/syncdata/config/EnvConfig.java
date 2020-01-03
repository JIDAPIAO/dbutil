package com.example.dbutil.syncdata.config;


import com.example.dbutil.syncdata.vo.DBSetting;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "env")
public class EnvConfig {


    private List<String> fromDbs;

    private List<String> toDbs;

    private List<DBSetting> dbSettings;

    private String entryPoint;

    private String autoExecute;


    public DBSetting getDBConfig(String name) {
        if (StringUtils.isBlank(name)) return null;
        return dbSettings.stream().filter(dbSetting -> dbSetting.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
