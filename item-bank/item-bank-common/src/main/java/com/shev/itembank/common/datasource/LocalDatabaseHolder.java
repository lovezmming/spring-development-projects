package com.shev.itembank.common.datasource;

import com.shev.itembank.common.datasource.Enum.DataSourceEnum;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalDatabaseHolder extends AbstractRoutingDataSource
{
    public static final Map<String, String> masterDatabaseMap = new ConcurrentHashMap<>();

    public static final Map<String, String> slaveDatabaseMap = new ConcurrentHashMap<>();

    public static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static final Map<DataSourceEnum, List<String>> methods = new HashMap<>();

    @Override
    protected Object determineCurrentLookupKey()
    {
        return getType();
    }

    void setMethodType(DataSourceEnum type, String content)
    {
        List<String> list = Arrays.asList(content.split(","));
        methods.put(type, list);
    }

    public static void setType(String type)
    {
        contextHolder.set(type);
    }

    public static String getType()
    {
        return contextHolder.get();
    }

    public static void clearType()
    {
        contextHolder.remove();
    }
}
