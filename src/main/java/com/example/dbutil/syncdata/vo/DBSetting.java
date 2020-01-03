package com.example.dbutil.syncdata.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DBSetting {

    private String name;

    private String host;

    private String port;

    private String user;

    private String password;

    private String charset;

    private String  dbName;

    public DBSetting(String host, String user, String password,String charset,String dbName) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.charset = charset;
        this.dbName = dbName;
    }
}
