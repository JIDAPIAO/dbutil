package com.example.dbutil.syncdata.service;

import com.example.dbutil.syncdata.config.EnvConfig;
import com.example.dbutil.syncdata.vo.DBSetting;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class KettleServiceImpl {

    public boolean sync(EnvConfig envConfig, String from, String to) throws Exception {
        KettleEnvironment.init();

        final String sync = String.valueOf(System.currentTimeMillis());
        Resource resource = new ClassPathResource(envConfig.getEntryPoint());
        if (!resource.exists()) {
            return false;
        }
        JobMeta jobMeta = new JobMeta(resource.getFile().getAbsolutePath(), null);
        DBSetting fromDbSetting = envConfig.getDBConfig(from);
        DBSetting toDbSetting = envConfig.getDBConfig(to);
        if (fromDbSetting == null || toDbSetting == null) {
            return false;
        }
        if (jobMeta == null) {
            return false;
        }
        Job job = new Job(null, jobMeta);
        job.setVariable("sync", sync);
        job.setVariable("TO_HOST", toDbSetting.getHost());
        job.setVariable("TO_DB", toDbSetting.getDbName());
        job.setVariable("TO_USER", toDbSetting.getUser());
        job.setVariable("TO_PASSWORD", toDbSetting.getPassword());
        job.setVariable("TO_PORT", toDbSetting.getPort());

        job.setVariable("FROM_HOST", fromDbSetting.getHost());
        job.setVariable("FROM_DB", fromDbSetting.getDbName());
        job.setVariable("FROM_USER", fromDbSetting.getUser());
        job.setVariable("FROM_PASSWORD", fromDbSetting.getPassword());
        job.setVariable("FROM_PORT", fromDbSetting.getPort());
        job.start();
        job.waitUntilFinished();
        return true;
    }

}
