# dbutil
    自动同步两个库中的表结构和数据


# 使用说明
    在 application.yml 中将  db-settings 数据源进行修改
    FROM: 源数据库
    TO:   目标数据库

# 运行
    在 com.example.dbutil.TestSync 中直接运行单元测试即可
    syncDdl:同步表结构  (源数据库中的表和目标数据库中的表进行比对,没有就新增,表结构有变动就修改表)
    syncData:同步数据   (从源数据库中复制数据到目标数据库中,注意两个库中的表结构必须完全一致)
    
