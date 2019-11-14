package com.shev.itembank.common.datasource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.shev.itembank.common.Enum.DataServiceEnum;
import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.datasource.Enum.DataSourceEnum;
import com.shev.itembank.common.zookeeper.ZkClient;

@MapperScan({
        "com.shev.itembank.edumeta.mapper",
        "com.shev.itembank.edumeta.custom",
        "com.shev.itembank.paper.mapper",
        "com.shev.itembank.paper.custom",
        "com.shev.itembank.task.mapper",
        "com.shev.itembank.task.custom",
        "com.shev.itembank.resource.mapper",
        "com.shev.itembank.resource.custom",
        "com.shev.itembank.exercise.mapper",
        "com.shev.itembank.exercise.custom",
        "com.shev.itembank.system.mapper",
        "com.shev.itembank.system.custom"
})
@Configuration
@EnableTransactionManagement
public class LocalDatabaseConfig
{
    private static final Logger logger = LoggerFactory.getLogger(LocalDatabaseConfig.class);

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private Environment environment;

    @Value("${spring.datasource.write}")
    private String master;

    @Value("${spring.datasource.read}")
    private String slave;

    @Value("${spring.datasource.druid.filters}")
    private String filters;

    @Value("${spring.datasource.druid.initial-size}")
    private Integer initialSize;

    @Value("${spring.datasource.druid.min-idle}")
    private Integer minIdle;

    @Value("${spring.datasource.druid.max-active}")
    private Integer maxActive;

    @Value("${spring.datasource.druid.max-wait}")
    private Integer maxWait;

    @Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
    private Long timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.min-evictable-idle-time-millis}")
    private Long minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.druid.test-while-idle}")
    private Boolean testWhileIdle;

    @Value("${spring.datasource.druid.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.test-on-return}")
    private boolean testOnReturn;

    @Value("${spring.datasource.druid.pool-prepared-statements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size}")
    private Integer maxPoolPreparedStatementPerConnectionSize;

    @Bean(name = "masterDataSource")
    public Map<String, DataSource> masterDataSource()
    {
        DataSourceBuilder<?> dataSource = null;
        Map<String, DataSource> masterDatasourceMap = new HashMap<>();
        for (Map.Entry<String, String> databaseMaps : LocalDatabaseHolder.masterDatabaseMap.entrySet())
        {
            String databaseJson = databaseMaps.getValue();
            Map<String, String> databaseMap = (Map<String, String>) JSONObject.parse(databaseJson);
            dataSource = DataSourceBuilder.create();
            dataSource.password(databaseMap.get("password"));
            dataSource.username(databaseMap.get("username"));
            dataSource.url(databaseMap.get("url"));
            dataSource.driverClassName(databaseMap.get("classname"));
            masterDatasourceMap.put(databaseMaps.getKey(), dataSource.build());
        }
        return masterDatasourceMap;
    }

    @Bean(name = "slaveDataSource")
    public Map<String, DataSource> slaveDataSource()
    {
        Map<String, DataSource> slaveDatasourceMap = new HashMap<>();
        for (Map.Entry<String, String> databaseMaps : LocalDatabaseHolder.slaveDatabaseMap.entrySet())
        {
            String databaseJson = databaseMaps.getValue();
            Map<String, String> databaseMap = (Map<String, String>) JSONObject.parse(databaseJson);
            slaveDatasourceMap.put(databaseMaps.getKey(), getDataSource(databaseMap));
        }
        return slaveDatasourceMap;
    }

    private DataSource getDataSource(Map<String, String> databaseMap)
    {
        DruidDataSource dataSource = new DruidDataSource();
        try
        {
            dataSource.setFilters(filters);
        } catch (SQLException e)
        {
            logger.error("datasource filter error!");
        }
        dataSource.setUrl(databaseMap.get("url"));
        dataSource.setDriverClassName(databaseMap.get("classname"));
        dataSource.setUsername(databaseMap.get("username"));
        dataSource.setPassword(databaseMap.get("password"));
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        return dataSource;
    }

    @Bean
    @Primary
    public DataSource multipleDataSource(
            @Qualifier("masterDataSource") Map<String, DataSource> masterDataSources,
            @Qualifier("slaveDataSource") Map<String, DataSource> slaveDataSources)
    {
        LocalDatabaseHolder dataSource = new LocalDatabaseHolder();
        Map<Object, Object> targetDataSources = new ConcurrentHashMap<>();
        DataSource defaultDatasource = null;
        for (Map.Entry<String, DataSource> masterDataSourceMap : masterDataSources.entrySet())
        {
            defaultDatasource = masterDataSourceMap.getValue();
            targetDataSources.put(masterDataSourceMap.getKey(), defaultDatasource);
            logger.info("init database:{}", masterDataSourceMap.getKey());
        }
        for (Map.Entry<String, DataSource> slaveDataSourceMap : slaveDataSources.entrySet())
        {
            targetDataSources.put(slaveDataSourceMap.getKey(), slaveDataSourceMap.getValue());
            logger.info("init database:{}", slaveDataSourceMap.getKey());
        }
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(defaultDatasource);

        dataSource.setMethodType(DataSourceEnum.MASTER, master);
        dataSource.setMethodType(DataSourceEnum.SLAVE, slave);
        return dataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception
    {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(masterDataSource(), slaveDataSource()));
        return sqlSessionFactory.getObject();
    }

    @Bean("transactionManager")
    public DataSourceTransactionManager transactionManager(LocalDatabaseHolder localDatabaseHolder)
    {
        return new DataSourceTransactionManager(localDatabaseHolder);
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @PostConstruct
    private void initDataConfig()
    {
        if (zkClient.ENABLE)
        {
            zkData();
        } else
        {
            propertyData();
        }
    }

    private void zkData()
    {
        if (zkClient.isExistNode("/database"))
        {
            // znode节点：/database/edu/edumeta/master(slave)
            List<String> parentNodes = zkClient.getChildren("/database");
            if (!TextUtil.isEmpty(parentNodes))
            {
                List<String> databaseNodes = zkClient.getChildren("/database/edu");
                if (!TextUtil.isEmpty(databaseNodes))
                {
                    List<String> subNodes = null;
                    for (String node : databaseNodes)
                    {
                        subNodes = zkClient.getChildren("/database/edu/" + node);
                        if (!TextUtil.isEmpty(subNodes))
                        {
                            LocalDatabaseHolder.masterDatabaseMap.put("/" + node + "/master", new String(zkClient.getNodeData("/database/edu/" + node + "/" + "/master")));
                            LocalDatabaseHolder.slaveDatabaseMap.put("/" + node + "/slave", new String(zkClient.getNodeData("/database/edu/" + node + "/" + "/slave")));
                        }
                    }
                }
            }
        } else
        {
            logger.error("zookeeper database init fail!");
            // throw new ConfigInitException("Zk.init.database.fail", "zookeeper database init fail!");
        }
    }

    private void propertyData()
    {
        String url = null;
        for (DataServiceEnum dataServiceEnum : DataServiceEnum.values())
        {
            url = environment.getProperty("spring.datasource." + dataServiceEnum.getName() + ".master.url");
            if (!TextUtil.isEmpty(url))
            {
                Map<String, String> envMasterMap = new HashMap<>();
                envMasterMap.put("username", environment.getProperty("spring.datasource." + dataServiceEnum.getName() + ".master.data-username"));
                envMasterMap.put("password", environment.getProperty("spring.datasource." + dataServiceEnum.getName() + ".master.data-password"));
                envMasterMap.put("classname", environment.getProperty("spring.datasource." + dataServiceEnum.getName() + ".master.driver-class-name"));
                envMasterMap.put("url", url);
                String envMasterJson = JSONObject.toJSONString(envMasterMap);
                Map<String, String> envSlaveMap = new HashMap<>();
                envSlaveMap.put("username", environment.getProperty("spring.datasource." + dataServiceEnum.getName() + ".slave.data-username"));
                envSlaveMap.put("password", environment.getProperty("spring.datasource." + dataServiceEnum.getName() + ".slave.data-password"));
                envSlaveMap.put("classname", environment.getProperty("spring.datasource." + dataServiceEnum.getName() + ".slave.driver-class-name"));
                envSlaveMap.put("url", environment.getProperty("spring.datasource." + dataServiceEnum.getName() + ".slave.url"));
                String envSlaveJson = JSONObject.toJSONString(envSlaveMap);
                LocalDatabaseHolder.masterDatabaseMap.put("/" + dataServiceEnum.getName() + "/master", envMasterJson);
                LocalDatabaseHolder.slaveDatabaseMap.put("/" + dataServiceEnum.getName() + "/slave", envSlaveJson);
            }
        }
    }

}
