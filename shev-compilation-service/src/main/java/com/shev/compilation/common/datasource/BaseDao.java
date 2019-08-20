package com.shev.compilation.common.datasource;

import com.shev.compilation.common.datasource.Enum.DataSourceName;
import com.shev.compilation.common.datasource.dynamic.DynamicDataSourceContextHolder;
import com.shev.compilation.common.datasource.dynamic.Enum.DataSourceEnum;
import com.shev.compilation.common.util.SpringApplicationUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;

@Service
public class BaseDao
{

    private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

    public List<Object> query(Class clasz, String methodName, Object params, Comparator<Object> comparator)
    {
        logger.info("object:{},method:{},params:{}", clasz, methodName, params.getClass().getName());
        //        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
//        String fromMethodName = elements[2].getMethodName();//调用的类
//        String fromClassName = elements[2].getClassName();//调用的类的方法
        List<Object> resList = new ArrayList<>();
//        Class<?> fromClazz = Class.forName(fromClassName);
        try
        {
            Class<?> clazz = Class.forName(clasz.getName());
            Method method = null;
            if (params.getClass().getName().contains("java.util.HashMap"))
            {
                method = clazz.getMethod(methodName, Map.class);
            } else
            {
                method = clazz.getMethod(methodName, params.getClass());
            }
            method.setAccessible(true);
            DataSourceName dataSource = DataSourceName.ACCOUNT_QUERY;
            List<DataSourceName> dataSourceNames = DataSourceName.getEnums("slave");
            for (DataSourceName name : dataSourceNames)
            {
                if (clazz.getName().contains(name.getName()))
                {
                    dataSource = name;
                    break;
                }
            }
            SqlSession sqlSession = SpringApplicationUtil.getBean(SqlSession.class);
            Object object;
            List<DataSourceEnum> enums = DataSourceEnum.getEnumsByName(dataSource.getName(), "slave");
            for (DataSourceEnum curr_enum : enums)
            {
                logger.info("className:{}, method:{}, args:{}, datasource:{} ", clasz.getName(), methodName, params, curr_enum.getKeyName());
                DynamicDataSourceContextHolder.setDataSourceType(curr_enum);
                object = method.invoke(sqlSession.getMapper(clazz), params);
                if (object instanceof ArrayList)
                {
                    resList.addAll((Collection<?>) object);
                } else
                {
                    resList.add(object);
                }
            }
            if (comparator != null)
            {
                Collections.sort(resList, comparator);
            }
            DynamicDataSourceContextHolder.clearDataSourceType();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return resList;
    }

    public void update(Class currClass, String id, Object updateObj)
    {
        String selectMethodName = "selectByPrimaryKey";
        String updateMethodName = "updateByPrimaryKey";
        try
        {
            Class<?> clazz = Class.forName(currClass.getName());
            Method selectMethod = clazz.getMethod(selectMethodName, String.class);
            Method updateMethod = clazz.getMethod(updateMethodName, updateObj.getClass());
            DataSourceName dataSource = DataSourceName.ACCOUNT_UPDATE;
            List<DataSourceName> dataSourceNames = DataSourceName.getEnums("master");
            for (DataSourceName name : dataSourceNames)
            {
                if (clazz.getName().contains(name.getName()))
                {
                    dataSource = name;
                    break;
                }
            }
            SqlSession sqlSession = SpringApplicationUtil.getBean(SqlSession.class);
            Object object;
            List<DataSourceEnum> enums = DataSourceEnum.getEnumsByName(dataSource.getName(), "master");
            for (DataSourceEnum curr_enum : enums)
            {
                logger.info("update : className:{}, datasource:{} ", currClass.getName(), curr_enum.getKeyName());
                DynamicDataSourceContextHolder.setDataSourceType(curr_enum);
                object = selectMethod.invoke(sqlSession.getMapper(clazz), id);
                if (object != null)
                {
                    updateMethod.invoke(sqlSession.getMapper(clazz), updateObj);
                    break;
                }
            }
            DynamicDataSourceContextHolder.clearDataSourceType();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void delete(Class currClass, String id)
    {
        String selectMethodName = "selectByPrimaryKey";
        String deleteMethodName = "deleteByPrimaryKey";
        try
        {
            Class<?> clazz = Class.forName(currClass.getName());
            Method selectMethod = clazz.getMethod(selectMethodName, String.class);
            Method deleteMethod = clazz.getMethod(deleteMethodName, String.class);
            DataSourceName dataSource = DataSourceName.ACCOUNT_UPDATE;
            List<DataSourceName> dataSourceNames = DataSourceName.getEnums("master");
            for (DataSourceName name : dataSourceNames)
            {
                if (clazz.getName().contains(name.getName()))
                {
                    dataSource = name;
                    break;
                }
            }
            SqlSession sqlSession = SpringApplicationUtil.getBean(SqlSession.class);
            Object object;
            List<DataSourceEnum> enums = DataSourceEnum.getEnumsByName(dataSource.getName(), "master");
            for (DataSourceEnum curr_enum : enums)
            {
                logger.info("delete : className:{}, datasource:{} ", currClass.getName(), curr_enum.getKeyName());
                DynamicDataSourceContextHolder.setDataSourceType(curr_enum);
                object = selectMethod.invoke(sqlSession.getMapper(clazz), id);
                if (object != null)
                {
                    deleteMethod.invoke(sqlSession.getMapper(clazz), id);
                    break;
                }
            }
            DynamicDataSourceContextHolder.clearDataSourceType();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Object queryById(Class currClass, String id)
    {
        Object object = null;
        String selectMethodName = "selectByPrimaryKey";
        try
        {
            Class<?> clazz = Class.forName(currClass.getName());
            Method selectMethod = clazz.getMethod(selectMethodName, String.class);
            DataSourceName dataSource = DataSourceName.ACCOUNT_QUERY;
            List<DataSourceName> dataSourceNames = DataSourceName.getEnums("slave");
            for (DataSourceName name : dataSourceNames)
            {
                if (clazz.getName().contains(name.getName()))
                {
                    dataSource = name;
                    break;
                }
            }
            SqlSession sqlSession = SpringApplicationUtil.getBean(SqlSession.class);
            List<DataSourceEnum> enums = DataSourceEnum.getEnumsByName(dataSource.getName(), "slave");
            for (DataSourceEnum curr_enum : enums)
            {
                logger.info("delete : className:{}, datasource:{} ", currClass.getName(), curr_enum.getKeyName());
                DynamicDataSourceContextHolder.setDataSourceType(curr_enum);
                object = selectMethod.invoke(sqlSession.getMapper(clazz), id);
                if (object != null)
                {
                    break;
                }
            }
            DynamicDataSourceContextHolder.clearDataSourceType();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return object;
    }

}
