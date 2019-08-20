/*
package com.shev.compilation.common.datasource.page;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PageHelperConfig
{
    @Value("${pagehelper.helperDialect}")
    private String helperDialect;

    @Value("${pagehelper.reasonable}")
    private String reasonable;

    @Value("${pagehelper.supportMethodsArguments}")
    private String supportMethodsArguments;

    @Value("${pagehelper.params}")
    private String params;

    @Value("${pagehelper.rowBoundsWithCount}")
    private String rowBoundsWithCount;

    @Value("${pagehelper.offset-as-page-num}")
    private String offsetAsPageNum;

    @Bean
    @ConfigurationProperties(prefix = "pagehelper")
    public PageHelper pageHelper()
    {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", offsetAsPageNum);
        properties.setProperty("rowBoundsWithCount", rowBoundsWithCount);
        properties.setProperty("reasonable", reasonable);
        properties.setProperty("dialect", helperDialect);
        properties.setProperty("params", params);
        properties.setProperty("supportMethodsArguments", supportMethodsArguments);
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
*/
