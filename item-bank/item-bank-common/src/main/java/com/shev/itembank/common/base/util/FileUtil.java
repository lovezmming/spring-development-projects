package com.shev.itembank.common.base.util;

import java.io.*;

public class FileUtil
{
    public static String getFileExtention(String fileName)
    {
        if (TextUtil.isEmpty(fileName))
            return null;
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    public static void deleteFile(String filePath)
    {
        File file = new File(filePath);
        if (file.exists())
            file.delete();
    }
    
    public static byte[] file2byte(String filePath)
    {
        return file2byte(new File(filePath));
    }
    
    public static byte[] file2byte(File file)
    {
        byte[] buffer = null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try
        {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } 
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (fis != null)
                {
                    fis.close();
                    fis = null;
                }
                if (bos != null)
                {
                    bos.close();
                    bos = null;
                }
            } catch (Exception e2) {}
        }
        return buffer;
    }

    public static void byte2File(byte[] buf, String dest, String name)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(dest);
            if (!dir.exists())
                dir.mkdirs();
            file = new File(dest + File.separator + name);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                if (bos != null)
                {
                    bos.close();
                    bos = null;
                }
                if (fos != null)
                {
                    fos.close();
                    fos = null;
                }
            } catch (IOException e)
            {
            }
        }
    }
}
