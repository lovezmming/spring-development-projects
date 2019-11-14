package com.shev.itembank.common.base.valid;

import com.shev.itembank.common.base.exception.ValidationException;
import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RequestValidationAspect
{
    private static final Logger logger = LoggerFactory.getLogger(RequestValidationAspect.class);

    @Value("${request.valid}")
    private String valid;

    @Value("${request.timePeriod}")
    private String timePeriod;

    @Before("@annotation(RequestValidate)")
    public void checkBeforeRequest(JoinPoint point) throws ValidationException
    {
        logger.info("request valid!");
        if ("1".equals(valid))
        {
            Object[] args = point.getArgs();
            if (args != null && args.length > 0 && (args[0] instanceof HttpServletBasicRequest))
            {
                HttpServletBasicRequest request = (HttpServletBasicRequest) args[0];
                RequestValidate requestValidate = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(RequestValidate.class);
                if (requestValidate.timestampValidate())
                {
                    long diff = Math.abs(System.currentTimeMillis() - request.getTimestamp());
                    if (diff > (Integer.valueOf(timePeriod) * 1000))
                    {
                        //   throw new ValidationException("Invalid.timestamp", "timestamp invalid");
                        logger.info("timestamp is error!");
                    }
                }
                if (request.getPageNumber() == null)
                {
                    request.setPageNumber(0);
                }
                if (request.getPageSize() == null)
                {
                    request.setPageSize(20);
                }
                if (request.getStart() == null)
                {
                    request.setStart(request.getPageNumber() * request.getPageSize());
                }
                logger.info("request valid success!");
            }
        }
    }

}
