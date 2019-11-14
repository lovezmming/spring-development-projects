package com.shev.itembank.latex.entity;

import org.dom4j.Document;

import java.util.Vector;

public class ElementWithPngMessage
{
	public Document document;
	public int totalWidth1;
	public int maxHeight1;
	public int totalWidth2;
	public int maxHeight2;
	public Vector<PngMessage> vecPngMessage1;
	public Vector<PngMessage> vecPngMessage2;

	public ElementWithPngMessage()
	{
		vecPngMessage1 = new Vector<PngMessage>();
		vecPngMessage2 = new Vector<PngMessage>();
		totalWidth1 = 0;
		totalWidth2 = 0;
		maxHeight1 = 0;
		maxHeight2 = 0;
	}
	
	public void clear()
	{
		if(vecPngMessage1.size()>0){
			for(int i =0;i<vecPngMessage1.size();i++)
			{
				vecPngMessage1.get(i).clear();
			}
		}
		if(vecPngMessage2.size()>0)
		{
			for(int i =0;i<vecPngMessage2.size();i++)
			{
				vecPngMessage2.get(i).clear();
			}
		}
		totalWidth2 = 0;
		totalWidth1 = 0;
		maxHeight2 = 0;
		maxHeight1 = 0;
		vecPngMessage1.clear();
		vecPngMessage2.clear();
	}
}
