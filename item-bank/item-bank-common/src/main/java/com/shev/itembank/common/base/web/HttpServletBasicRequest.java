package com.shev.itembank.common.base.web;

import com.shev.itembank.common.Enum.ConstantEnum;
import com.shev.itembank.common.base.exception.ValidationException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@Data
public class HttpServletBasicRequest implements Serializable
{
    private static final long serialVersionUID = -7025331035740789979L;

    private static final Logger logger = LoggerFactory.getLogger(HttpServletBasicRequest.class);

/*
    @NotNull
    private String appAccessKey;

    @NotNull
    private String longTicket;

    @NotNull
    private String sign;

    @NotNull
    private String requestId;
*/

    @NotNull
    private long timestamp;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer start;

    /*
     * will be set after validation
     */
    private String currentUserTenantId;

    /*
     * will be set after validation
     */
    private String currentUserId;

/*
    public Integer getStart()
    {
        return start == null ? pageNumber * pageSize : start;
    }
*/

    public Integer getMax()
    {
        return getPageSize();
    }

    public String getCurrentUserTenantId()
    {
        return currentUserTenantId == null ? ConstantEnum.DEFAULT_TENANT_ID.getValue() : currentUserTenantId;
    }
    public String getCurrentUserId()
    {
        return currentUserId == null ? ConstantEnum.DEFAULT_USER_ID.getValue() : currentUserId;
    }

    public Map<String, Object> getParamsMap() throws ValidationException
    {
        try
        {
            List<Field> fields = this.getBasicFields(getClass());
            Map<String, Object> result = new HashMap<String, Object>();
            String[] types = { "java.lang.Integer", "java.lang.Double", "java.lang.Float", "java.lang.Long", "java.lang.Short", "java.lang.Byte",
                    "java.lang.Boolean", "java.lang.Character", "java.lang.String", "int", "double", "long", "short", "byte", "boolean", "char", "float","java.util.Date"};
            List<String> list = Arrays.asList(types);
            if (fields != null)
            {
                for (Field field : fields)
                {
                    if (Modifier.isStatic(field.getModifiers()) || "sign".equalsIgnoreCase(field.getName()))
                        continue;
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    if ((list.contains(field.getType().getName())))
                    {
                        Object value = field.get(this); // 调用getter方法获取属性值
                        if (value != null)
                            result.put(fieldName, value);
                    }
                }
            }

            return result;
        } catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new ValidationException();
        }
    }

    private List<Field> getBasicFields(Class<?> clazz)
    {
        List<Field> result = new ArrayList<Field>();

        Field[] declaredFields = clazz.getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0)
        {
            for (Field field : declaredFields)
                result.add(field);
        }
        if (clazz.getSuperclass() != null)
            result.addAll(getBasicFields(clazz.getSuperclass()));
        return result;
    }

}
