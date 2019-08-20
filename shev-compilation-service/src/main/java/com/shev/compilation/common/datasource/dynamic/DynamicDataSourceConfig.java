package com.shev.compilation.common.datasource.dynamic;

import com.alibaba.druid.pool.DruidDataSource;
import com.shev.compilation.common.datasource.dynamic.Enum.DataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@MapperScan({
        "com.shev.compilation.user.dao.mapper",
        "com.shev.compilation.user.dao.custom",
        "com.shev.compilation.edumeta.dao.mapper",
        "com.shev.compilation.edumeta.dao.custom"})
@Configuration
public class DynamicDataSourceConfig
{
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceConfig.class);

    @Autowired
    private DataSourceProperties properties;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

//    @Value("${mybatis.config-location}")
//    private String configLocation;

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

    @Bean("accountMasterDatasource")
    @Qualifier("accountMasterDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.account.master")
    public DataSource accountMasterDatasource()
    {
        logger.info("Init dataSourceConfig accountMasterDatasource");
        return DataSourceBuilder.create().build();
    }

    @Bean("accountSlave1Datasource")
    @Qualifier("accountSlave1Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.account.slave")
    public DataSource accountSlave1Datasource() throws SQLException
    {
        logger.info("Init dataSourceConfig accountSlave1Datasource");
        return getDataSource();
    }

    private DataSource getDataSource() throws SQLException
    {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setFilters(filters);
        dataSource.setUrl(properties.getUrl());
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
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

    @Bean("edumetaMaster1Datasource")
    @Qualifier("edumetaMaster1Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.edumeta.master1")
    public DataSource edumetaMaster1Datasource()
    {
        logger.info("Init dataSourceConfig edumetaMaster1Datasource");
        return DataSourceBuilder.create().build();
    }

    @Bean("edumetaMaster2Datasource")
    @Qualifier("edumetaMaster2Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.edumeta.master2")
    public DataSource edumetaMaster2Datasource()
    {
        logger.info("Init dataSourceConfig edumetaMaster2Datasource");
        return DataSourceBuilder.create().build();
    }

    @Bean("edumetaMaster3Datasource")
    @Qualifier("edumetaMaster3Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.edumeta.master3")
    public DataSource edumetaMaster3Datasource()
    {
        logger.info("Init dataSourceConfig edumetaMaster3Datasource");
        return DataSourceBuilder.create().build();
    }

    @Bean("edumetaMaster4Datasource")
    @Qualifier("edumetaMaster4Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.edumeta.master4")
    public DataSource edumetaMaster4Datasource()
    {
        logger.info("Init dataSourceConfig edumetaMaster4Datasource");
        return DataSourceBuilder.create().build();
    }

    @Bean("edumetaSlave1Datasource")
    @Qualifier("edumetaSlave1Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.edumeta.slave1")
    public DataSource edumetaSlave1Datasource() throws SQLException
    {
        logger.info("Init dataSourceConfig edumetaSlave1Datasource");
        return getDataSource();
    }

    @Bean("edumetaSlave2Datasource")
    @Qualifier("edumetaSlave2Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.edumeta.slave2")
    public DataSource edumetaSlave2Datasource() throws SQLException
    {
        logger.info("Init dataSourceConfig edumetaSlave2Datasource");
        return getDataSource();
    }

    @Bean("edumetaSlave3Datasource")
    @Qualifier("edumetaSlave3Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.edumeta.slave3")
    public DataSource edumetaSlave3Datasource() throws SQLException
    {
        logger.info("Init dataSourceConfig edumetaSlave3Datasource");
        return getDataSource();
    }

    @Bean("edumetaSlave4Datasource")
    @Qualifier("edumetaSlave4Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.edumeta.slave4")
    public DataSource edumetaSlave4Datasource() throws SQLException
    {
        logger.info("Init dataSourceConfig edumetaSlave4Datasource");
        return getDataSource();
    }

    @Primary
    @Bean("commonDatasource")
    public DynamicDataSource commonDatasource(
            // account
            @Qualifier("accountMasterDatasource") DataSource accountMasterDatasource,
            @Qualifier("accountSlave1Datasource") DataSource accountSlave1Datasource,
            // edumeta
            @Qualifier("edumetaMaster1Datasource") DataSource edumetaMaster1Datasource,
            @Qualifier("edumetaMaster2Datasource") DataSource edumetaMaster2Datasource,
            @Qualifier("edumetaMaster3Datasource") DataSource edumetaMaster3Datasource,
            @Qualifier("edumetaMaster4Datasource") DataSource edumetaMaster4Datasource,
            @Qualifier("edumetaSlave1Datasource") DataSource edumetaSlave1Datasource,
            @Qualifier("edumetaSlave2Datasource") DataSource edumetaSlave2Datasource,
            @Qualifier("edumetaSlave3Datasource") DataSource edumetaSlave3Datasource,
            @Qualifier("edumetaSlave4Datasource") DataSource edumetaSlave4Datasource
    )
    {
        DynamicDataSource dataSource = new DynamicDataSource();

        Map<Object, Object> targetDataSources = new ConcurrentHashMap<>();
        targetDataSources.put(DataSourceEnum.ACCOUNT_MASTER, accountMasterDatasource);
        targetDataSources.put(DataSourceEnum.ACCOUNT_SLAVE1, accountSlave1Datasource);
        targetDataSources.put(DataSourceEnum.EDUEMTA_MASTER1, edumetaMaster1Datasource);
        targetDataSources.put(DataSourceEnum.EDUEMTA_MASTER2, edumetaMaster2Datasource);
        targetDataSources.put(DataSourceEnum.EDUEMTA_MASTER3, edumetaMaster3Datasource);
        targetDataSources.put(DataSourceEnum.EDUEMTA_MASTER4, edumetaMaster4Datasource);
        targetDataSources.put(DataSourceEnum.EDUMETA_SLAVE1, edumetaSlave1Datasource);
        targetDataSources.put(DataSourceEnum.EDUMETA_SLAVE2, edumetaSlave2Datasource);
        targetDataSources.put(DataSourceEnum.EDUMETA_SLAVE3, edumetaSlave3Datasource);
        targetDataSources.put(DataSourceEnum.EDUMETA_SLAVE4, edumetaSlave4Datasource);
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(accountMasterDatasource);

        dataSource.setMethodType(DataSourceEnum.ACCOUNT_MASTER, master);
        dataSource.setMethodType(DataSourceEnum.ACCOUNT_SLAVE1, slave);
        dataSource.setMethodType(DataSourceEnum.EDUEMTA_MASTER1, master);
        dataSource.setMethodType(DataSourceEnum.EDUEMTA_MASTER2, master);
        dataSource.setMethodType(DataSourceEnum.EDUEMTA_MASTER3, master);
        dataSource.setMethodType(DataSourceEnum.EDUEMTA_MASTER4, master);
        dataSource.setMethodType(DataSourceEnum.EDUMETA_SLAVE1, slave);
        dataSource.setMethodType(DataSourceEnum.EDUMETA_SLAVE2, slave);
        dataSource.setMethodType(DataSourceEnum.EDUMETA_SLAVE3, slave);
        dataSource.setMethodType(DataSourceEnum.EDUMETA_SLAVE4, slave);

        return dataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            // account
            @Qualifier("accountMasterDatasource") DataSource accountMasterDatasource,
            @Qualifier("accountSlave1Datasource") DataSource accountSlave1Datasource,
            // edumeta
            @Qualifier("edumetaMaster1Datasource") DataSource edumetaMaster1Datasource,
            @Qualifier("edumetaMaster2Datasource") DataSource edumetaMaster2Datasource,
            @Qualifier("edumetaMaster3Datasource") DataSource edumetaMaster3Datasource,
            @Qualifier("edumetaMaster4Datasource") DataSource edumetaMaster4Datasource,
            @Qualifier("edumetaSlave1Datasource") DataSource edumetaSlave1Datasource,
            @Qualifier("edumetaSlave2Datasource") DataSource edumetaSlave2Datasource,
            @Qualifier("edumetaSlave3Datasource") DataSource edumetaSlave3Datasource,
            @Qualifier("edumetaSlave4Datasource") DataSource edumetaSlave4Datasource
    ) throws Exception
    {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(
                this.commonDatasource(
                        accountMasterDatasource,
                        accountSlave1Datasource,
                        edumetaMaster1Datasource,
                        edumetaMaster2Datasource,
                        edumetaMaster3Datasource,
                        edumetaMaster4Datasource,
                        edumetaSlave1Datasource,
                        edumetaSlave2Datasource,
                        edumetaSlave3Datasource,
                        edumetaSlave4Datasource
        ));
//        fb.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
//        fb.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        return fb.getObject();
    }

    @Bean("transactionManager")
    public DataSourceTransactionManager transactionManager(DynamicDataSource dynamicDataSource)
    {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

}
