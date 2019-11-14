package com.shev.itembank.common.base.util;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import java.io.InputStream;
import java.util.Map;

public class XMLUtil
{
    public static String XML2JSON(String xml)
    {
        return new XMLSerializer().read(xml).toString();
    }

    @SuppressWarnings("unchecked")
    public static Map<String,Object> XML2Map(String xml)
    {
        JSONObject json = (JSONObject) new XMLSerializer().read(xml);
        return (Map<String,Object>) json;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> XML2Map(InputStream inputStream)
    {
        JSONObject json = (JSONObject) new XMLSerializer().readFromStream(inputStream);
        return (Map<String,Object>) json;
    }

    public static String Json2XML(String json)
    {
        JSONObject jobj = JSONObject.fromObject(json);
        String xml =  new XMLSerializer().write(jobj);
        return xml;
    }

}
