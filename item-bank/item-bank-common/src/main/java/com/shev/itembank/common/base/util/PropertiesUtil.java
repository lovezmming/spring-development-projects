package com.shev.itembank.common.base.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil
{

    public static String readValue(String filePath,String key) {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            if (in != null) {
                prop.load(in);
            }
            return prop.getProperty(key);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public static Properties getProperties(String filePath) {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            File file = new File(filePath);
//          直接读取文件
            if (file.canRead()) {
                in = new BufferedInputStream(new FileInputStream(file));
//          从当前路径中获取文件流
            } else {
                in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
            }
            if (in != null) {
                prop.load(in);
            }
        } catch (IOException e) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
        return prop;
    }

}
