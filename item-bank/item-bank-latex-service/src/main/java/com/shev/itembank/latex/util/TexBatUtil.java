package com.shev.itembank.latex.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

public class TexBatUtil
{
	public  static void process(String rootFolder, String ctexBinPath) throws Exception
	{
	    Vector<String> vFile = new Vector<String>();
		String strPContent="";
		listFile(vFile, new File(rootFolder+"TexPicTmp/"));
		String head = "@echo off\r\n";
		String end = "rem 处理完毕\r\n";
		strPContent = head;
		for(int i = 0;i < vFile.size();i++)
		{
			if(i == 0)
			{
				strPContent = strPContent 
				+ "cd "+rootFolder+"TexPicTmp\r\n"
				+ "for /f \"delims=\" %%i in (\'dir /b /a-d /s \"*.tex\"\') do "+ctexBinPath+"latex.exe  \"%%i\"\r\n"
				+ "for /f \"delims=\" %%i in (\'dir /b /a-d /s \"*.dvi\"\') do "+ctexBinPath+"dvipng.exe -T tight -Q 12 --palette* -bg transparent -D 480 --strict* \"%%i\" &del \"%%~ni.dvi\" &del \"%%~ni.log\"&del \"%%~ni.aux\"\r\n";
			}
			//第一个文件跑两遍
			strPContent = strPContent 
						+ "cd "+rootFolder+"TexPicTmp\r\n"
						+ "for /f \"delims=\" %%i in (\'dir /b /a-d /s \"*.tex\"\') do "+ctexBinPath+"latex.exe  \"%%i\"\r\n"
						+ "for /f \"delims=\" %%i in (\'dir /b /a-d /s \"*.dvi\"\') do "+ctexBinPath+"dvipng.exe -T tight -Q 12 --palette* -bg transparent -D 480 --strict* \"%%i\" &del \"%%~ni.dvi\" &del \"%%~ni.log\"&del \"%%~ni.aux\"\r\n";
		}
		strPContent = strPContent + end;
		File filePath = new File(rootFolder+"PProcess/");
		if (!filePath.exists())
		{
			filePath.mkdirs();
		}
		File[] files = filePath.listFiles();  
        for (int i = 0; i < files.length; i++)
        {
        	files[i].delete();      
        }
		File file = new File(rootFolder+"PProcess/tex.bat");
		if (!file.exists())
		{
			file.createNewFile();
		}
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"GB2312");
		write.append(strPContent);
		write.close(); 
	}

	private static void listFile(Vector<String> vFile ,File file) throws Exception
	{
		if (file.isFile())
		{
			if (file.toString().contains("tex.tex"))
			{
				vFile.add(file.getAbsolutePath());
			}
		} else
		{
			if(file.exists())
			{
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++)
				{
					listFile(vFile, files[i]);
				}
			}
		}
	}

}
