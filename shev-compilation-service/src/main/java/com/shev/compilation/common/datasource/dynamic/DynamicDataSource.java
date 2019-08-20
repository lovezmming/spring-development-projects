package com.shev.compilation.common.datasource.dynamic;

import com.shev.compilation.common.datasource.dynamic.Enum.DataSourceEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DynamicDataSource extends AbstractRoutingDataSource
{
    static final Map<DataSourceEnum, List<String>> datasourceMap = new HashMap<>();
    static final Map<DataSourceEnum, String> datasourcesMap = new HashMap<>();

    private AtomicInteger count = new AtomicInteger(0);

    @Value("${spring.datasource.write}")
    private String master;

    @Value("${spring.datasource.read}")
    private String slave;

    @Nullable
    @Override
    protected Object determineCurrentLookupKey()
    {
        DataSourceEnum type = DynamicDataSourceContextHolder.getDataSourceType();
        if (type == null)
        {
            List<DataSourceEnum> types = DynamicDataSourceContextHolder.getDataSourceTypes();
            return types;
        }
        return type;
    }

    void setMethodType(DataSourceEnum type, String content)
    {
        List<String> list = Arrays.asList(content.split(","));
        datasourceMap.put(type, list);
        datasourcesMap.put(type, content);
    }
}
