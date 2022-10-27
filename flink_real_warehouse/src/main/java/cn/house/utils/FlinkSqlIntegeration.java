package cn.house.utils;

public class FlinkSqlIntegeration {
    /**
     * 映射kafka当中的json格式的数据，解析成为一张表
     */
    public static String dwd_user_appAd = "CREATE TABLE  if not exists  dwd_user_appAd(\n" +
            " `action`  STRING, \n" +
            " `activityId`   STRING, \n" +
            " `ar` STRING, \n" +
            " `ba` STRING, \n" +
            " `contentType`  STRING, \n" +
            " `displayMills`  STRING, \n" +
            " `en` STRING, \n" +
            " `entry`  STRING, \n" +
            " `ett`STRING, \n" +
            " `g`  STRING, \n" +
            " `hw` STRING, \n" +
            " `itemId`  INT, \n" +
            " `l`  STRING, \n" +
            " `md` STRING, \n" +
            " `mid`STRING, \n" +
            " `os` STRING, \n" +
            " `sr` STRING, \n" +
            " `sv` STRING, \n" +
            " `t`  STRING, \n" +
            " `timeStamp`  STRING, \n" +
            " `uid`INT, \n" +
            " `vc` STRING, \n" +
            " `vn` STRING \n" +
            " ) WITH( \n" +
            " 'connector' = 'kafka', \n" +
            " 'topic'='dwd_user_appAd', \n" +
            " 'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092', \n" +
            " 'properties.group.id' = 'dwd_user_appAd_kafka2hudi', \n" +
            " 'scan.startup.mode' = 'earliest-offset', \n" +
            " 'format' = 'json', \n" +
            " 'json.fail-on-missing-field' = 'false', \n" +
            " 'json.ignore-parse-errors' = 'true' \n" +
            " )";


    /**
     * 将解析kafka出来之后的json表数据，插入到hudi里面去
     */
    public static String dwd_user_appAd_hudi_sink ="CREATE TABLE  if not exists dwd_user_appAd_hudi_sink( \n" +
            " `action`  STRING, \n" +
            " `activityId`   STRING, \n" +
            " `ar` STRING, \n" +
            " `ba` STRING, \n" +
            " `contentType`  STRING, \n" +
            " `displayMills`  STRING, \n" +
            " `en` STRING, \n" +
            " `entry`  STRING, \n" +
            " `ett`STRING PRIMARY KEY NOT ENFORCED,\n" +
            " `g`  STRING, \n" +
            " `hw` STRING, \n" +
            " `itemId`  INT, \n" +
            " `l`  STRING, \n" +
            " `md` STRING, \n" +
            " `mid`STRING, \n" +
            " `os` STRING, \n" +
            " `sr` STRING, \n" +
            " `sv` STRING, \n" +
            " `t`  STRING, \n" +
            " `timeStamp`  STRING, \n" +
            " `uid`INT, \n" +
            " `vc` STRING, \n" +
            " `vn` STRING ,\n" +
            "  `ts` STRING,\n" +
            "  `partition_day` STRING \n" +
            ") \n" +
            "PARTITIONED BY (partition_day) \n" +
            "WITH( \n" +
            "'connector' = 'hudi', \n" +
            "'path'='hdfs://bigdata01:8020/hudi-warehouse/dwd_user_appAd_hudi_sink', \n" +
            "'table.type' = 'MERGE_ON_READ', \n" +
            "'write.operation' = 'upsert', \n" +
            "'hoodie.datasource.write.recordkey.field' = 'ett', \n" +
            "'write.precombine.field' = 'ts', \n" +
            "'write.tasks' = '1' \n" +
            ")";

   /* public static String dwd_user_appAd_hudi_sink = " CREATE TABLE dwd_user_appAd_hudi_sink(\n" +
            " `action`  STRING, \n" +
            " `activityId`   STRING, \n" +
            " `ar` STRING, \n" +
            " `ba` STRING, \n" +
            " `contentType`  STRING, \n" +
            " `displayMills`  STRING, \n" +
            " `en` STRING, \n" +
            " `entry`  STRING, \n" +
            " `ett`STRING PRIMARY KEY NOT ENFORCED,\n" +
            " `g`  STRING, \n" +
            " `hw` STRING, \n" +
            " `itemId`  INT, \n" +
            " `l`  STRING, \n" +
            " `md` STRING, \n" +
            " `mid`STRING, \n" +
            " `os` STRING, \n" +
            " `sr` STRING, \n" +
            " `sv` STRING, \n" +
            " `t`  STRING, \n" +
            " `timeStamp`  STRING, \n" +
            " `uid`INT, \n" +
            " `vc` STRING, \n" +
            " `vn` STRING ,\n" +
            "  `ts` STRING,\n" +
            "  `partition_day` STRING \n" +
            ") \n" +
            "PARTITIONED BY (partition_day) \n" +
            "WITH( \n" +
            "'connector' = 'hudi', \n" +
            "'path'='hdfs://bigdata01:8020/hudi-warehouse/dwd_user_appAd_hudi_sink', \n" +
            "'table.type' = 'MERGE_ON_READ', \n" +
            "'write.operation' = 'upsert', \n" +
            "'hoodie.datasource.write.recordkey.field' = 'ett', \n" +
            "'write.precombine.field' = 'ts', \n" +
            "'write.tasks' = '1' ,\n" +
            "'hive_sync.enable' = 'true',\n" +
            "'hive_sync.mode' = 'hms',\n" +
            "'hive_sync.metastore.uris' = 'thrift://bigdata03:9083',\n" +
            "'hive_sync.jdbc_url' = 'jdbc:hive2://bigdata03:10000',\n" +
            "'hive_sync.table' = 'dwd_user_appAd',\n" +
            "'hive_sync.db' = 'default',\n" +
            "'hive_sync.username' = 'hadoop',\n" +
            "'hive_sync.password' = '123456' \n" +
            ")";*/



    public static String dwd_user_appAd_hudi_sink_insert = "insert into dwd_user_appAd_hudi_sink(\n" +
            " select  \n" +
            "`action`       ,\n" +
            " `activityId`  ,\n" +
            " `ar`          ,\n" +
            " `ba`          ,\n" +
            " `contentType` ,\n" +
            " `displayMills`,\n" +
            " `en`          ,\n" +
            " `entry`       ,\n" +
            " `ett`         ,\n" +
            " `g`           ,\n" +
            " `hw`          ,\n" +
            " `itemId`      ,\n" +
            " `l`           ,\n" +
            " `md`          ,\n" +
            " `mid`         ,\n" +
            " `os`          ,\n" +
            " `sr`          ,\n" +
            " `sv`          ,\n" +
            " `t`           ,\n" +
            " `timeStamp`   ,\n" +
            " `uid`         ,\n" +
            " `vc`          ,\n" +
            " `vn`          ,\n" +
            " `ett` as `ts` ,  \n" +
            " SUBSTRING( cast ( TO_TIMESTAMP(FROM_UNIXTIME(cast(ett as BIGINT) / 1000, 'yyyy-MM-dd')) as STRING ),0,10) as `partition_day`   \n" +
            " from dwd_user_appAd \n" +
            " )";





    public static String dwd_user_appCar = "CREATE TABLE  if not exists dwd_user_appCar(\n" +
            "`addTime`    STRING,\n" +
            "`ar`         STRING,\n" +
            "`ba`         STRING,\n" +
            "`en`         STRING,\n" +
            "`ett`        STRING ,\n" +
            "`g`          STRING,\n" +
            "`goodsId`    INT,   \n" +
            "`hw`         STRING,\n" +
            "`l`          STRING,\n" +
            "`md`         STRING,\n" +
            "`mid`        STRING,\n" +
            "`num`        INT,   \n" +
            "`os`         STRING,\n" +
            "`skuId`      INT,   \n" +
            "`sr`         STRING,\n" +
            "`sv`         STRING,\n" +
            "`t`          STRING,\n" +
            "`timeStamp`  STRING,\n" +
            "`uid`        INT,   \n" +
            "`userId`     INT,   \n" +
            "`vc`         STRING,\n" +
            "`vn`         STRING \n" +
            ")\n" +
            "WITH(\n" +
            "'connector' = 'kafka',\n" +
            "'topic'='dwd_user_appCar',\n" +
            "'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092',\n" +
            "'properties.group.id' = 'dwd_user_appCar_kafka2hudi',\n" +
            "'scan.startup.mode' = 'earliest-offset',\n" +
            "'format' = 'json',\n" +
            "'json.fail-on-missing-field' = 'false',\n" +
            "'json.ignore-parse-errors' = 'true'\n" +
            ")";

    public static String dwd_user_appCar_hudi_sink = " CREATE TABLE if not exists dwd_user_appCar_hudi_sink( \n" +
            " `addTime`    STRING,\n" +
            "`ar`         STRING,\n" +
            "`ba`         STRING,\n" +
            "`en`         STRING,\n" +
            "`ett`        STRING PRIMARY KEY NOT ENFORCED,\n" +
            "`g`          STRING,\n" +
            "`goodsId`    INT,   \n" +
            "`hw`         STRING,\n" +
            "`l`          STRING,\n" +
            "`md`         STRING,\n" +
            "`mid`        STRING,\n" +
            "`num`        INT,   \n" +
            "`os`         STRING,\n" +
            "`skuId`      INT,   \n" +
            "`sr`         STRING,\n" +
            "`sv`         STRING,\n" +
            "`t`          STRING,\n" +
            "`timeStamp`  STRING,\n" +
            "`uid`        INT,   \n" +
            "`userId`     INT,   \n" +
            "`vc`         STRING,\n" +
            "`vn`         STRING ,\n" +
            "`ts` STRING,\n" +
            "`partition_day` STRING \n" +
            ") \n" +
            "PARTITIONED BY (partition_day) \n" +
            "WITH( \n" +
            "'connector' = 'hudi', \n" +
            "'path'='hdfs://bigdata01:8020/hudi-warehouse/dwd_user_appCar_hudi_sink', \n" +
            "'table.type' = 'MERGE_ON_READ', \n" +
            "'write.operation' = 'upsert', \n" +
            "'hoodie.datasource.write.recordkey.field' = 'ett', \n" +
            "'write.precombine.field' = 'ts', \n" +
            "'write.tasks' = '1' \n" +
            ")";

    public static String dwd_user_appCar_hudi_sink_insert = "insert into dwd_user_appCar_hudi_sink(\n" +
            " select \n" +
            " `addTime`  ,\n" +
            "`ar`       ,\n" +
            "`ba`       ,\n" +
            "`en`       ,\n" +
            "`ett`      ,\n" +
            "`g`        ,\n" +
            "`goodsId`  ,\n" +
            "`hw`       ,\n" +
            "`l`        ,\n" +
            "`md`       ,\n" +
            "`mid`      ,\n" +
            "`num`      ,\n" +
            "`os`       ,\n" +
            "`skuId`    ,\n" +
            "`sr`       ,\n" +
            "`sv`       ,\n" +
            "`t`        ,\n" +
            "`timeStamp`,\n" +
            "`uid`      ,\n" +
            "`userId`   ,\n" +
            "`vc`       ,\n" +
            "`vn`       ,\n" +
            " `ett` as `ts` ,  \n" +
            " SUBSTRING( cast ( TO_TIMESTAMP(FROM_UNIXTIME(cast(ett as BIGINT) / 1000, 'yyyy-MM-dd')) as STRING ),0,10) as  `partition_day`   \n" +
            " from dwd_user_appCar \n" +
            " )";



//
//
    public static String dwd_user_appComment = "CREATE TABLE if not exists dwd_user_appComment(\n" +
        "`addTime`        STRING, \n" +
        "`ar`             STRING, \n" +
        "`ba`             STRING, \n" +
        "`content`        STRING, \n" +
        "`en`             STRING, \n" +
        "`ett`            STRING , \n" +
        "`g`              STRING, \n" +
        "`hw`             STRING, \n" +
        "`l`              STRING, \n" +
        "`md`             STRING, \n" +
        "`mid`            STRING, \n" +
        "`os`             STRING, \n" +
        "`sr`             STRING, \n" +
        "`star`           INT,    \n" +
        "`sv`             STRING, \n" +
        "`t`              STRING, \n" +
        "`timeStamp`      STRING, \n" +
        "`type`           INT,    \n" +
        "`uid`            INT,    \n" +
        "`userId`         INT,    \n" +
        "`valueId`        INT,    \n" +
        "`vc`             STRING, \n" +
        "`vn`             STRING  \n" +
        ")\n" +
        "WITH(\n" +
        "'connector' = 'kafka',\n" +
        "'topic'='dwd_user_appComment',\n" +
        "'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092',\n" +
        "'properties.group.id' = 'dwd_user_appComment_kafka2hudi',\n" +
        "'scan.startup.mode' = 'earliest-offset',\n" +
        "'format' = 'json',\n" +
        "'json.fail-on-missing-field' = 'false',\n" +
        "'json.ignore-parse-errors' = 'true'\n" +
        ")";

    public static String dwd_user_appComment_hudi_sink = "CREATE TABLE if not exists dwd_user_appComment_hudi_sink( \n" +
            "`addTime`        STRING, \n" +
            "`ar`             STRING, \n" +
            "`ba`             STRING, \n" +
            "`content`        STRING, \n" +
            "`en`             STRING, \n" +
            "`ett`            STRING PRIMARY KEY NOT ENFORCED,\n" +
            "`g`              STRING, \n" +
            "`hw`             STRING, \n" +
            "`l`              STRING, \n" +
            "`md`             STRING, \n" +
            "`mid`            STRING, \n" +
            "`os`             STRING, \n" +
            "`sr`             STRING, \n" +
            "`star`           INT,    \n" +
            "`sv`             STRING, \n" +
            "`t`              STRING, \n" +
            "`timeStamp`      STRING, \n" +
            "`type`           INT,    \n" +
            "`uid`            INT,    \n" +
            "`userId`         INT,    \n" +
            "`valueId`        INT,    \n" +
            "`vc`             STRING, \n" +
            "`vn`             STRING  ,\n" +
            "`ts` STRING,\n" +
            "`partition_day` STRING \n" +
            ") \n" +
            "PARTITIONED BY (partition_day) \n" +
            "WITH( \n" +
            "'connector' = 'hudi', \n" +
            "'path'='hdfs://bigdata01:8020/hudi-warehouse/dwd_user_appComment_hudi_sink', \n" +
            "'table.type' = 'MERGE_ON_READ', \n" +
            "'write.operation' = 'upsert', \n" +
            "'hoodie.datasource.write.recordkey.field' = 'ett', \n" +
            "'write.precombine.field' = 'ts', \n" +
            "'write.tasks' = '1' \n" +
            ")";


    public static String dwd_user_appComment_hudi_sink_insert = " insert into dwd_user_appComment_hudi_sink(\n" +
            " select \n" +
            " `addTime`       ,\n" +
            "`ar`            ,\n" +
            "`ba`            ,\n" +
            "`content`       ,\n" +
            "`en`            ,\n" +
            "`ett`           ,\n" +
            "`g`             ,\n" +
            "`hw`            ,\n" +
            "`l`             ,\n" +
            "`md`            ,\n" +
            "`mid`           ,\n" +
            "`os`            ,\n" +
            "`sr`            ,\n" +
            "`star`          ,\n" +
            "`sv`            ,\n" +
            "`t`             ,\n" +
            "`timeStamp`     ,\n" +
            "`type`          ,\n" +
            "`uid`           ,\n" +
            "`userId`        ,\n" +
            "`valueId`       ,\n" +
            "`vc`            ,\n" +
            "`vn`            ,\n" +
            " `ett` as `ts` ,  \n" +
            " SUBSTRING( cast ( TO_TIMESTAMP(FROM_UNIXTIME(cast(ett as BIGINT) / 1000, 'yyyy-MM-dd')) as STRING ),0,10) as  `partition_day`  \n" +
            " from dwd_user_appComment  \n" +
            " )";









//
//
    public static String dwd_user_appDisplay = "CREATE TABLE if not exists   dwd_user_appDisplay(\n" +
        "`action`      STRING,\n" +
        "`ar`          STRING,\n" +
        "`ba`          STRING,\n" +
        "`category`    STRING,\n" +
        "`en`          STRING,\n" +
        "`ett`         STRING,\n" +
        "`extend1`     STRING,\n" +
        "`g`           STRING,\n" +
        "`goodsId`     INT,\n" +
        "`hw`          STRING,\n" +
        "`l`           STRING,\n" +
        "`md`          STRING,\n" +
        "`mid`         STRING,\n" +
        "`os`          STRING,\n" +
        "`place`       STRING,\n" +
        "`sr`          STRING,\n" +
        "`sv`          STRING,\n" +
        "`t`           STRING,\n" +
        "`timeStamp`   STRING,\n" +
        "`uid`         INT,\n" +
        "`vc`          STRING,\n" +
        "`vn`          STRING\n" +
        ")\n" +
        "WITH(\n" +
        "'connector' = 'kafka',\n" +
        "'topic'='dwd_user_appDisplay',\n" +
        "'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092',\n" +
        "'properties.group.id' = 'dwd_user_appDisplay_kafka2hudi',\n" +
        "'scan.startup.mode' = 'earliest-offset',\n" +
        "'format' = 'json',\n" +
        "'json.fail-on-missing-field' = 'false',\n" +
        "'json.ignore-parse-errors' = 'true'\n" +
        ")";

    public static String dwd_user_appDisplay_hudi_sink = "CREATE TABLE if not exists  dwd_user_appDisplay_hudi_sink( \n" +
            "`action`      STRING,\n" +
            "`ar`          STRING,\n" +
            "`ba`          STRING,\n" +
            "`category`    STRING,\n" +
            "`en`          STRING,\n" +
            "`ett`         STRING PRIMARY KEY NOT ENFORCED,\n" +
            "`extend1`     STRING,\n" +
            "`g`           STRING,\n" +
            "`goodsId`     INT,\n" +
            "`hw`          STRING,\n" +
            "`l`           STRING,\n" +
            "`md`          STRING,\n" +
            "`mid`         STRING,\n" +
            "`os`          STRING,\n" +
            "`place`       STRING,\n" +
            "`sr`          STRING,\n" +
            "`sv`          STRING,\n" +
            "`t`           STRING,\n" +
            "`timeStamp`   STRING,\n" +
            "`uid`         INT,\n" +
            "`vc`          STRING,\n" +
            "`vn`          STRING,\n" +
            "`ts` STRING,\n" +
            "`partition_day` STRING \n" +
            ") \n" +
            "PARTITIONED BY (partition_day) \n" +
            "WITH( \n" +
            "'connector' = 'hudi', \n" +
            "'path'='hdfs://bigdata01:8020/hudi-warehouse/dwd_user_appDisplay_hudi_sink', \n" +
            "'table.type' = 'MERGE_ON_READ', \n" +
            "'write.operation' = 'upsert', \n" +
            "'hoodie.datasource.write.recordkey.field' = 'ett', \n" +
            "'write.precombine.field' = 'ts', \n" +
            "'write.tasks' = '1' \n" +
            ")";

    public static String dwd_user_appDisplay_hudi_sink_insert = "insert into dwd_user_appDisplay_hudi_sink(\n" +
            " select \n" +
            " `action`    ,\n" +
            "`ar`        ,\n" +
            "`ba`        ,\n" +
            "`category`  ,\n" +
            "`en`        ,\n" +
            "`ett`       ,\n" +
            "`extend1`   ,\n" +
            "`g`         ,\n" +
            "`goodsId`   ,\n" +
            "`hw`        ,\n" +
            "`l`         ,\n" +
            "`md`        ,\n" +
            "`mid`       ,\n" +
            "`os`        ,\n" +
            "`place`     ,\n" +
            "`sr`        ,\n" +
            "`sv`        ,\n" +
            "`t`         ,\n" +
            "`timeStamp` ,\n" +
            "`uid`       ,\n" +
            "`vc`        ,\n" +
            "`vn`        ,\n" +
            " `ett` as `ts` ,  \n" +
            " SUBSTRING( cast ( TO_TIMESTAMP(FROM_UNIXTIME(cast(ett as BIGINT) / 1000, 'yyyy-MM-dd')) as STRING ),0,10) as  `partition_day`  \n" +
            " from dwd_user_appDisplay  \n" +
            " )";

//
//
    public static String dwd_user_appFavorites = "CREATE TABLE if not exists dwd_user_appFavorites(\n" +
        "`addTime`          STRING,\n" +
        "`ar`               STRING,\n" +
        "`ba`               STRING,\n" +
        "`courseId`         INT,\n" +
        "`en`               STRING,\n" +
        "`ett`              STRING,\n" +
        "`g`                STRING,\n" +
        "`hw`               STRING,\n" +
        "`id`               INT,\n" +
        "`l`                STRING,\n" +
        "`md`               STRING,\n" +
        "`mid`              STRING,\n" +
        "`os`               STRING,\n" +
        "`sr`               STRING,\n" +
        "`sv`               STRING,\n" +
        "`t`                STRING,\n" +
        "`timeStamp`        STRING,\n" +
        "`uid`              INT,\n" +
        "`userId`           INT,\n" +
        "`vc`               STRING,\n" +
        "`vn`               STRING\n" +
        ")\n" +
        "WITH(\n" +
        "'connector' = 'kafka',\n" +
        "'topic'='dwd_user_appFavorites',\n" +
        "'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092',\n" +
        "'properties.group.id' = 'dwd_user_appFavorites_kafka2hudi',\n" +
        "'scan.startup.mode' = 'earliest-offset',\n" +
        "'format' = 'json',\n" +
        "'json.fail-on-missing-field' = 'false',\n" +
        "'json.ignore-parse-errors' = 'true'\n" +
        ")";

    public static String dwd_user_appFavorites_hudi_sink = "CREATE TABLE if not exists dwd_dwd_user_appFavorites_hudi_sink( \n" +
            "`addTime`          STRING,\n" +
            "`ar`               STRING,\n" +
            "`ba`               STRING,\n" +
            "`courseId`         INT,\n" +
            "`en`               STRING,\n" +
            "`ett`              STRING PRIMARY KEY NOT ENFORCED,\n" +
            "`g`                STRING,\n" +
            "`hw`               STRING,\n" +
            "`id`               INT,\n" +
            "`l`                STRING,\n" +
            "`md`               STRING,\n" +
            "`mid`              STRING,\n" +
            "`os`               STRING,\n" +
            "`sr`               STRING,\n" +
            "`sv`               STRING,\n" +
            "`t`                STRING,\n" +
            "`timeStamp`        STRING,\n" +
            "`uid`              INT,\n" +
            "`userId`           INT,\n" +
            "`vc`               STRING,\n" +
            "`vn`               STRING,\n" +
            "`ts` STRING,\n" +
            "`partition_day` STRING \n" +
            ") \n" +
            "PARTITIONED BY (partition_day) \n" +
            "WITH( \n" +
            "'connector' = 'hudi', \n" +
            "'path'='hdfs://bigdata01:8020/hudi-warehouse/dwd_user_appFavorites_hudi_sink', \n" +
            "'table.type' = 'MERGE_ON_READ', \n" +
            "'write.operation' = 'upsert', \n" +
            "'hoodie.datasource.write.recordkey.field' = 'ett', \n" +
            "'write.precombine.field' = 'ts', \n" +
            "'write.tasks' = '1' \n" +
            ")";

    public static String dwd_dwd_user_appFavorites_hudi_sink_insert = "insert into dwd_dwd_user_appFavorites_hudi_sink(\n" +
            " select \n" +
            " `addTime`  ,\n" +
            "`ar`       ,\n" +
            "`ba`       ,\n" +
            "`courseId` ,\n" +
            "`en`       ,\n" +
            "`ett`      ,\n" +
            "`g`        ,\n" +
            "`hw`       ,\n" +
            "`id`       ,\n" +
            "`l`        ,\n" +
            "`md`       ,\n" +
            "`mid`      ,\n" +
            "`os`       ,\n" +
            "`sr`       ,\n" +
            "`sv`       ,\n" +
            "`t`        ,\n" +
            "`timeStamp`,\n" +
            "`uid`      ,\n" +
            "`userId`   ,\n" +
            "`vc`       ,\n" +
            "`vn`       ,\n" +
            " `ett` as `ts` ,  \n" +
            " SUBSTRING( cast ( TO_TIMESTAMP(FROM_UNIXTIME(cast(ett as BIGINT) / 1000, 'yyyy-MM-dd')) as STRING ),0,10) as  `partition_day`  \n" +
            " from dwd_user_appFavorites  \n" +
            " )";


//
    public static String dwd_user_appLoading = "CREATE TABLE if not exists dwd_user_appLoading(\n" +
        "`action`         STRING,\n" +
        "`ar`             STRING,\n" +
        "`ba`             STRING,\n" +
        "`en`             STRING,\n" +
        "`ett`            STRING,\n" +
        "`extend1`        STRING,\n" +
        "`extend2`        STRING,\n" +
        "`g`              STRING,\n" +
        "`hw`             STRING,\n" +
        "`l`              STRING,\n" +
        "`loadingTime`    STRING,\n" +
        "`loadingWay`     STRING,\n" +
        "`md`             STRING,\n" +
        "`mid`            STRING,\n" +
        "`os`             STRING,\n" +
        "`sr`             STRING,\n" +
        "`sv`             STRING,\n" +
        "`t`              STRING,\n" +
        "`timeStamp`      STRING,\n" +
        "`type`           STRING,\n" +
        "`type1`          STRING,\n" +
        "`uid`            INT,\n" +
        "`vc`             STRING,\n" +
        "`vn`             STRING\n" +
        ")\n" +
        "WITH(\n" +
        "'connector' = 'kafka',\n" +
        "'topic'='dwd_user_appLoading',\n" +
        "'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092',\n" +
        "'properties.group.id' = 'dwd_user_appLoading_kafka2hudi',\n" +
        "'scan.startup.mode' = 'earliest-offset',\n" +
        "'format' = 'json',\n" +
        "'json.fail-on-missing-field' = 'false',\n" +
        "'json.ignore-parse-errors' = 'true'\n" +
        ")";

    public static String dwd_user_appLoading_hudi_sink = "CREATE TABLE if not exists dwd_user_appLoading_hudi_sink( \n" +
            "`action`         STRING,\n" +
            "`ar`             STRING,\n" +
            "`ba`             STRING,\n" +
            "`en`             STRING,\n" +
            "`ett`            STRING PRIMARY KEY NOT ENFORCED,\n" +
            "`extend1`        STRING,\n" +
            "`extend2`        STRING,\n" +
            "`g`              STRING,\n" +
            "`hw`             STRING,\n" +
            "`l`              STRING,\n" +
            "`loadingTime`    STRING,\n" +
            "`loadingWay`     STRING,\n" +
            "`md`             STRING,\n" +
            "`mid`            STRING,\n" +
            "`os`             STRING,\n" +
            "`sr`             STRING,\n" +
            "`sv`             STRING,\n" +
            "`t`              STRING,\n" +
            "`timeStamp`      STRING,\n" +
            "`type`           STRING,\n" +
            "`type1`          STRING,\n" +
            "`uid`            INT,\n" +
            "`vc`             STRING,\n" +
            "`vn`             STRING,\n" +
            "`ts` STRING,\n" +
            "`partition_day` STRING \n" +
            ") \n" +
            "PARTITIONED BY (partition_day) \n" +
            "WITH( \n" +
            "'connector' = 'hudi', \n" +
            "'path'='hdfs://bigdata01:8020/hudi-warehouse/dwd_user_appLoading_hudi_sink', \n" +
            "'table.type' = 'MERGE_ON_READ', \n" +
            "'write.operation' = 'upsert', \n" +
            "'hoodie.datasource.write.recordkey.field' = 'ett', \n" +
            "'write.precombine.field' = 'ts', \n" +
            "'write.tasks' = '1' \n" +
            ")";

    public static String dwd_user_appLoading_hudi_sink_insert = "insert into dwd_user_appLoading_hudi_sink(\n" +
            " select \n" +
            " `action`        ,\n" +
            "`ar`            ,\n" +
            "`ba`            ,\n" +
            "`en`            ,\n" +
            "`ett`           ,\n" +
            "`extend1`       ,\n" +
            "`extend2`       ,\n" +
            "`g`             ,\n" +
            "`hw`            ,\n" +
            "`l`             ,\n" +
            "`loadingTime`   ,\n" +
            "`loadingWay`    ,\n" +
            "`md`            ,\n" +
            "`mid`           ,\n" +
            "`os`            ,\n" +
            "`sr`            ,\n" +
            "`sv`            ,\n" +
            "`t`             ,\n" +
            "`timeStamp`     ,\n" +
            "`type`          ,\n" +
            "`type1`         ,\n" +
            "`uid`           ,\n" +
            "`vc`            ,\n" +
            "`vn`            ,\n" +
            " `ett` as `ts` ,  \n" +
            " SUBSTRING( cast ( TO_TIMESTAMP(FROM_UNIXTIME(cast(ett as BIGINT) / 1000, 'yyyy-MM-dd')) as STRING ),0,10) as  `partition_day`  \n" +
            " from dwd_user_appLoading  \n" +
            " )";

//
   public static String dwd_user_appPraise = "CREATE TABLE if not exists dwd_user_appPraise(\n" +
        "`addTime`      STRING,\n" +
        "`ar`           STRING,\n" +
        "`ba`           STRING,\n" +
        "`en`           STRING,\n" +
        "`ett`          STRING,\n" +
        "`g`            STRING,\n" +
        "`hw`           STRING,\n" +
        "`id`           STRING,\n" +
        "`l`            STRING,\n" +
        "`md`           STRING,\n" +
        "`mid`          STRING,\n" +
        "`os`           STRING,\n" +
        "`sr`           STRING,\n" +
        "`sv`           STRING,\n" +
        "`t`            STRING,\n" +
        "`targetId`     INT,   \n" +
        "`timeStamp`    STRING,\n" +
        "`type`         INT,   \n" +
        "`uid`          INT,   \n" +
        "`userId`       INT,   \n" +
        "`vc`           STRING,\n" +
        "`vn`           STRING\n" +
        ")\n" +
        "WITH(\n" +
        "'connector' = 'kafka',\n" +
        "'topic'='dwd_user_appPraise',\n" +
        "'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092',\n" +
        "'properties.group.id' = 'dwd_user_appPraise_kafka2hudi',\n" +
        "'scan.startup.mode' = 'earliest-offset',\n" +
        "'format' = 'json',\n" +
        "'json.fail-on-missing-field' = 'false',\n" +
        "'json.ignore-parse-errors' = 'true'\n" +
        ")";


   public static String  dwd_user_appPraise_hudi_sink = "CREATE TABLE if not exists dwd_user_appPraise_hudi_sink( \n" +
           "`addTime`      STRING,\n" +
           "`ar`           STRING,\n" +
           "`ba`           STRING,\n" +
           "`en`           STRING,\n" +
           "`ett`          STRING PRIMARY KEY NOT ENFORCED,\n" +
           "`g`            STRING,\n" +
           "`hw`           STRING,\n" +
           "`id`           STRING,\n" +
           "`l`            STRING,\n" +
           "`md`           STRING,\n" +
           "`mid`          STRING,\n" +
           "`os`           STRING,\n" +
           "`sr`           STRING,\n" +
           "`sv`           STRING,\n" +
           "`t`            STRING,\n" +
           "`targetId`     INT,   \n" +
           "`timeStamp`    STRING,\n" +
           "`type`         INT,   \n" +
           "`uid`          INT,   \n" +
           "`userId`       INT,   \n" +
           "`vc`           STRING,\n" +
           "`vn`           STRING,\n" +
           "`ts` STRING,\n" +
           "`partition_day` STRING \n" +
           ") \n" +
           "PARTITIONED BY (partition_day) \n" +
           "WITH( \n" +
           "'connector' = 'hudi', \n" +
           "'path'='hdfs://bigdata01:8020/hudi-warehouse/dwd_user_appPraise_hudi_sink', \n" +
           "'table.type' = 'MERGE_ON_READ', \n" +
           "'write.operation' = 'upsert', \n" +
           "'hoodie.datasource.write.recordkey.field' = 'ett', \n" +
           "'write.precombine.field' = 'ts', \n" +
           "'write.tasks' = '1' \n" +
           ")";


   public static String dwd_user_appPraise_hudi_sink_insert = "insert into dwd_user_appPraise_hudi_sink(\n" +
           " select \n" +
           "`addTime`    ,\n" +
           "`ar`         ,\n" +
           "`ba`         ,\n" +
           "`en`         ,\n" +
           "`ett`        ,\n" +
           "`g`          ,\n" +
           "`hw`         ,\n" +
           "`id`         ,\n" +
           "`l`          ,\n" +
           "`md`         ,\n" +
           "`mid`        ,\n" +
           "`os`         ,\n" +
           "`sr`         ,\n" +
           "`sv`         ,\n" +
           "`t`          ,\n" +
           "`targetId`   ,\n" +
           "`timeStamp`  ,\n" +
           "`type`       ,\n" +
           "`uid`        ,\n" +
           "`userId`     ,\n" +
           "`vc`         ,\n" +
           "`vn`         ,\n" +
           " `ett` as `ts` ,  \n" +
           " SUBSTRING( cast ( TO_TIMESTAMP(FROM_UNIXTIME(cast(ett as BIGINT) / 1000, 'yyyy-MM-dd')) as STRING ),0,10) as  `partition_day`  \n" +
           " from dwd_user_appPraise  \n" +
           " )";


    public static String dwd_user_start = "CREATE TABLE if not exists dwd_user_start(\n" +
            "`action`      STRING,\n" +
            "`ar`          STRING,\n" +
            "`ba`          STRING,\n" +
            "`detail`      STRING,\n" +
            "`en`          STRING,\n" +
            "`extend1`     STRING,\n" +
            "`g`           STRING,\n" +
            "`hw`          STRING,\n" +
            "`l`           STRING,\n" +
            "`loadingTime` STRING,\n" +
            "`md`          STRING,\n" +
            "`mid`         STRING,\n" +
            "`os`          STRING,\n" +
            "`sr`          STRING,\n" +
            "`sv`          STRING,\n" +
            "`t`           STRING,\n" +
            "`uid`         INT,\n" +
            "`vc`          STRING,\n" +
            "`vn`          STRING\n" +
            ")\n" +
            "WITH(\n" +
            "'connector' = 'kafka',\n" +
            "'topic'='dwd_user_start',\n" +
            "'properties.bootstrap.servers' = 'bigdata01:9092,bigdata02:9092,bigdata03:9092',\n" +
            "'properties.group.id' = 'dwd_user_start_kafka2hudi',\n" +
            "'scan.startup.mode' = 'earliest-offset',\n" +
            "'format' = 'json',\n" +
            "'json.fail-on-missing-field' = 'false',\n" +
            "'json.ignore-parse-errors' = 'true'\n" +
            ")";

    public static String dwd_user_start_hudi_sink = "CREATE TABLE if not exists dwd_user_start_hudi_sink( \n" +
            "`action`      STRING,\n" +
            "`ar`          STRING,\n" +
            "`ba`          STRING,\n" +
            "`detail`      STRING,\n" +
            "`en`          STRING,\n" +
            "`extend1`     STRING,\n" +
            "`g`           STRING,\n" +
            "`hw`          STRING,\n" +
            "`l`           STRING,\n" +
            "`loadingTime` STRING, \n" +
            "`md`          STRING,\n" +
            "`mid`         STRING,\n" +
            "`os`          STRING,\n" +
            "`sr`          STRING,\n" +
            "`sv`          STRING,\n" +
            "`t`           STRING PRIMARY KEY NOT ENFORCED,\n" +
            "`uid`         INT,\n" +
            "`vc`          STRING,\n" +
            "`vn`          STRING,\n" +
            "`ts` STRING,\n" +
            "`partition_day` STRING \n" +
            ") \n" +
            "PARTITIONED BY (partition_day) \n" +
            "WITH( \n" +
            "'connector' = 'hudi', \n" +
            "'path'='hdfs://bigdata01:8020/hudi-warehouse/dwd_user_start_hudi_sink', \n" +
            "'table.type' = 'MERGE_ON_READ', \n" +
            "'write.operation' = 'upsert', \n" +
            "'hoodie.datasource.write.recordkey.field' = 't', \n" +
            "'write.precombine.field' = 'ts', \n" +
            "'write.tasks' = '1' \n" +
            ")";

    public static String dwd_user_start_hudi_sink_insert = "insert into dwd_user_start_hudi_sink(\n" +
            " select \n" +
            "`action`     ,\n" +
            "`ar`         ,\n" +
            "`ba`         ,\n" +
            "`detail`     ,\n" +
            "`en`         ,\n" +
            "`extend1`    ,\n" +
            "`g`          ,\n" +
            "`hw`         ,\n" +
            "`l`          ,\n" +
            "`loadingTime`,\n" +
            "`md`         ,\n" +
            "`mid`        ,\n" +
            "`os`         ,\n" +
            "`sr`         ,\n" +
            "`sv`         ,\n" +
            "`t`          ,\n" +
            "`uid`        ,\n" +
            "`vc`         ,\n" +
            "`vn`         ,\n" +
            " `t` as `ts` ,  \n" +
            " SUBSTRING( cast ( TO_TIMESTAMP(FROM_UNIXTIME(cast(t as BIGINT) / 1000, 'yyyy-MM-dd')) as STRING ),0,10) as  `partition_day`  \n" +
            " from dwd_user_start  \n" +
            " )";

}
