package com.example.dbutil;


import com.example.dbutil.syncdata.config.EnvConfig;
import com.example.dbutil.syncdata.service.KettleServiceImpl;
import com.example.dbutil.syncddl.runner.Syncer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSync {

    @Autowired
    private EnvConfig envConfig;
    @Autowired
    private KettleServiceImpl kettleService;

    private final String FROM = "FROM";

    private final String TO = "TO";

    /**
     * 同步表结构
     */
    @Test
    public void syncDdl() {
        Syncer.sync(envConfig, FROM, TO);
    }


    /**
     * 同步数据
     */
    @Test
    public void syncData() throws Exception {
        kettleService.sync(envConfig, FROM, TO);
    }


}
