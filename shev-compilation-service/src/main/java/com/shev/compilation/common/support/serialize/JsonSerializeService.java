package com.shev.compilation.common.support.serialize;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class JsonSerializeService implements SerializeService
{
    private ObjectMapper mapper = new ObjectMapper();
    
    public byte[] serialize(Object object) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.mapper.writeValue(out, object);
        return out.toByteArray();
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) throws IOException
    {
        if (data == null)
            return null;
        JavaType type = this.mapper.getTypeFactory().constructType(clazz);
        return this.mapper.readValue(data, type);
    }
    
    public <T, K> T deserialize(byte[] data, Class<T> parametrized, Class<K> parameterClasses) throws IOException
    {
        JavaType type = this.mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
        return this.mapper.readValue(data, type);
    }
    
    protected ObjectMapper getMapper()
    {
        return mapper;
    }

}
