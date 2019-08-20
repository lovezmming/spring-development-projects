package com.shev.compilation.common.datasource.dynamic;

import com.shev.compilation.common.datasource.Enum.DataSourceName;
import com.shev.compilation.common.datasource.dynamic.Enum.DataSourceEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DynamicDataSourceAspect
{
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    private static final AtomicInteger counter = new AtomicInteger(-1);

    private static final Map<Integer, DataSourceEnum> existSlaveDatasouceHolder = new ConcurrentHashMap<>();
    private static final Map<Integer, DataSourceEnum> existMasterDatasouceHolder = new ConcurrentHashMap<>();

    @Before("@annotation(DataSourceUpdate)")
    public void beforeSwitchUpdate(JoinPoint point)
    {
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        String args = StringUtils.join(point.getArgs(), ",");
        try
        {
            Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
            Method method = className.getMethod(methodName, argClass);
            if (method.isAnnotationPresent(DataSourceUpdate.class))
            {
                DataSourceUpdate annotation = method.getAnnotation(DataSourceUpdate.class);
                DataSourceName dataSource = annotation.value();
                if (dataSource == null)
                {
                    List<DataSourceName> dataSourceNames = DataSourceName.getEnums("master");
                    for (DataSourceName name : dataSourceNames)
                    {
                        if (className.getName().contains(name.getName()))
                        {
                            dataSource = name;
                            break;
                        }
                    }
                }
                DataSourceEnum dataSourceEnum;
                if (existMasterDatasouceHolder != null)
                {
                    dataSourceEnum = existMasterDatasouceHolder.get(getMastKeyNum(existMasterDatasouceHolder));
                    DynamicDataSourceContextHolder.setDataSourceType(dataSourceEnum);
                    existMasterDatasouceHolder.clear();
                } else
                {
                    dataSourceEnum = polling(dataSource);
                    DynamicDataSourceContextHolder.setDataSourceType(dataSourceEnum);
                }
                logger.info("className:{}, method:{}, args:{}, datasource:{} ", className, methodName, args, dataSourceEnum.getKeyName());
            }
        } catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }

    @Before("@annotation(DataSourceMasterQuery)")
    public void beforeSwitchMasterQuery(JoinPoint point)
    {
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        String args = StringUtils.join(point.getArgs(), ",");
        try
        {
            Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
            Method method = className.getMethod(methodName, argClass);
            if (method.isAnnotationPresent(DataSourceMasterQuery.class))
            {
                DataSourceMasterQuery annotation = method.getAnnotation(DataSourceMasterQuery.class);
                DataSourceName dataSource = annotation.value();
                if (dataSource == null)
                {
                    List<DataSourceName> dataSourceNames = DataSourceName.getEnums("master");
                    for (DataSourceName name : dataSourceNames)
                    {
                        if (className.getName().contains(name.getName()))
                        {
                            dataSource = name;
                            break;
                        }
                    }
                }
                DataSourceEnum finalEnum = null;
                List<DataSourceEnum> enums = DataSourceEnum.getEnumsByName(dataSource.getName(), "master");
                for (DataSourceEnum curr_enum : enums)
                {
                    if (existMasterDatasouceHolder.containsKey(curr_enum))
                    {
                        continue;
                    }
                    DynamicDataSourceContextHolder.setDataSourceType(curr_enum);
                    finalEnum = curr_enum;
                }
                logger.info("className:{}, method:{}, args:{}, datasource:{} ", className, methodName, args, finalEnum.getKeyName());
                existMasterDatasouceHolder.put(getMastKeyNum(existMasterDatasouceHolder) + 1, finalEnum);
            }
        } catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }

    /*@Before("@annotation(DataSourceQuery)")
    public void beforeSwitchQuery(JoinPoint point)
    {
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        String args = StringUtils.join(point.getArgs(), ",");
        logger.info("className:{}, method:{}, args:{} ", className, methodName, args);
        try
        {
            Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
            DataSourceName dataSource = DataSourceName.ACCOUNT_QUERY;
            Method method = className.getMethod(methodName, argClass);
            if (method.isAnnotationPresent(DataSourceQuery.class))
            {
                List<DataSourceName> dataSourceNames = DataSourceName.getEnums("slave");
                for (DataSourceName name : dataSourceNames)
                {
                    if (className.getName().contains(name.getName()))
                    {
                        dataSource = name;
                        break;
                    }
                }
                List<DataSourceEnum> enums = DataSourceEnum.getEnumsByName(dataSource.getName(), "slave");
                for (DataSourceEnum curr_enum : enums)
                {
                    logger.info("query - 方法:{}使用的数据源为:{}", methodName, curr_enum.getKeyName());
                }
                DynamicDataSourceContextHolder.setDataSourceTypes(enums);
            }
        } catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }*/

    @Before("@annotation(DataSourceQuery)")
    public void beforeSwitchQuery(JoinPoint point)
    {
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        String args = StringUtils.join(point.getArgs(), ",");
        try
        {
            logger.info("className:{}", className);
            Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
            Method method = className.getMethod(methodName, argClass);
            if (method.isAnnotationPresent(DataSourceQuery.class))
            {
                DataSourceQuery annotation = method.getAnnotation(DataSourceQuery.class);
                DataSourceName dataSource = annotation.value();
                if (dataSource == null)
                {
                    List<DataSourceName> dataSourceNames = DataSourceName.getEnums("slave");
                    for (DataSourceName name : dataSourceNames)
                    {
                        if (className.getName().contains(name.getName()))
                        {
                            dataSource = name;
                            break;
                        }
                    }
                }
                logger.info("datasource:{},:{}", dataSource.getName(), dataSource.getValue());
                DataSourceEnum finalEnum = null;
                List<DataSourceEnum> enums = DataSourceEnum.getEnumsByName(dataSource.getName(), "slave");
                for (DataSourceEnum curr_enum : enums)
                {
                    if (existSlaveDatasouceHolder.containsKey(curr_enum))
                    {
                        continue;
                    }
                    DynamicDataSourceContextHolder.setDataSourceType(curr_enum);
                    finalEnum = curr_enum;
                }
                logger.info("className:{}, method:{}, args:{}, datasource:{} ", className, methodName, args, finalEnum.getKeyName());
                existSlaveDatasouceHolder.put(getMastKeyNum(existSlaveDatasouceHolder) + 1, finalEnum);
                if (existSlaveDatasouceHolder.size() - enums.size() == 0)
                {
                    existSlaveDatasouceHolder.clear();
                }
            }
        } catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }

    private Integer getMastKeyNum(Map<Integer, DataSourceEnum> datasouceHolder)
    {
        Integer key = 0;
        if (datasouceHolder.size() > 0)
        {
            for (Map.Entry<Integer, DataSourceEnum> dataSourceEnumEntry : datasouceHolder.entrySet())
            {
                if (key - dataSourceEnumEntry.getKey() < 0)
                {
                    key = dataSourceEnumEntry.getKey();
                }
            }
        }
        return key;
    }

    private DataSourceEnum polling(DataSourceName dataSource)
    {
        String datasourceName = null;
        String name = dataSource.getName();
        String value = dataSource.getValue();
        switch (name)
        {
            case "account":
                if (value.contains("master"))
                {
                    datasourceName = "accountMaster";
                } else
                {
                    datasourceName = "accountSlave" + getPolling();
                }
                break;
            case "edumeta":
                if (value.contains("master"))
                {
                    datasourceName = "edumetaMaster" + getPollingValue();
                } else
                {
                    datasourceName = "edumetaSlave" + getPollingValue();
                }
               break;
            default:
                if (value.contains("master"))
                {
                    datasourceName = "accountMaster";
                } else
                {
                    datasourceName = "accountSlave" + getPolling();
                }
                break;
        }
        return DataSourceEnum.getEnumByKeyName(datasourceName + "Datasource");
    }

    private String getPollingValue()
    {
        int index = counter.getAndIncrement() % 4;
        if (counter.get() > 9999)
        {
            counter.set(-1);
        }
        if (index == 0)
        {
            return "1";
        } else if (index == 1)
        {
            return "2";
        } else if (index == 2)
        {
            return "3";
        } else if (index == 3)
        {
            return "4";
        } else
        {
            return "1";
        }
    }

    private String getPolling()
    {
        int index = counter.getAndIncrement() % 2;
        if (counter.get() > 9999)
        {
            counter.set(-1);
        }
        if (index == 0)
        {
            return "1";
        } else if (index == 1)
        {
            return "2";
        } else
        {
            return "1";
        }
    }

    @After("@annotation(DataSourceUpdate)")
    public void afterSwitchUpdate(JoinPoint point)
    {
        DynamicDataSourceContextHolder.clearDataSourceType();
    }

    @After("@annotation(DataSourceQuery)")
    public void afterSwitchQuery(JoinPoint point)
    {
        DynamicDataSourceContextHolder.clearDataSourceTypes();
    }
}
