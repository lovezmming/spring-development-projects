package com.shev.itembank.common.datasource;

import com.shev.itembank.common.datasource.Enum.DataSourceEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LocalDatabaseAspect
{
    private static final Logger logger = LoggerFactory.getLogger(LocalDatabaseAspect.class);

    @Pointcut("execution(* com.shev.itembank.*.*.*Mapper.*(..))")
    public void dataOperate()
    {
    }

    @Before("dataOperate()")
    public void beforeAspect(JoinPoint point)
    {
        Signature signature = point.getSignature();
        String methodName = signature.getName();
        String args = StringUtils.join(point.getArgs(), ",");
        try
        {
            String[] classPathNames = null;
            for (DataSourceEnum type : DataSourceEnum.values())
            {
                List<String> values = LocalDatabaseHolder.methods.get(type);
                for (String key : values)
                {
                    if (methodName.startsWith(key))
                    {
                        classPathNames = signature.getDeclaringTypeName().split("\\.");
                        LocalDatabaseHolder.setType("/" + classPathNames[3] + "/" + type.getName());
                        logger.debug("method:{}, params:{}, type:{}", signature.getDeclaringTypeName() + "." + methodName, args, LocalDatabaseHolder.getType());
                    }
                }
            }
        } catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
}
