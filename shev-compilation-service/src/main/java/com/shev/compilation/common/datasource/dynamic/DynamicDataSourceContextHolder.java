package com.shev.compilation.common.datasource.dynamic;

import com.shev.compilation.common.datasource.dynamic.Enum.DataSourceEnum;

import java.util.List;

public class DynamicDataSourceContextHolder
{
    private static final ThreadLocal<DataSourceEnum> contextHolder = new ThreadLocal();

    private static final ThreadLocal<List<DataSourceEnum>> contextHolders = new ThreadLocal();

    public static void setDataSourceType(DataSourceEnum dataSourceType)
    {
        contextHolder.set(dataSourceType);
    }

    public static DataSourceEnum getDataSourceType()
    {
        return contextHolder.get();
    }

    public static void clearDataSourceType()
    {
        contextHolder.remove();
    }

    public static void setDataSourceTypes(List<DataSourceEnum> dataSourceTypes)
    {
        contextHolders.set(dataSourceTypes);
    }

    public static List<DataSourceEnum> getDataSourceTypes()
    {
        return contextHolders.get();
    }

    public static void clearDataSourceTypes()
    {
        contextHolders.remove();
    }
}
