package com.shev.compilation.common.support.serialize;

import java.io.IOException;

public interface SerializeService
{
    public byte[] serialize(Object object) throws IOException;

    public <T> T deserialize(byte[] data, Class<T> parametrized) throws IOException;
    
    public <T, K> T deserialize(byte[] data, Class<T> parametrized, Class<K> parameterClasses) throws IOException;
}
