package com.shev.itembank.latex.entity;

import java.awt.image.BufferedImage;

public class PngMessage
{
	public String strForlder;
	public int width;
	public int height;
	public BufferedImage bufferedImage;
	public String imageStr;
	public PngMessage()
	{
	}
	
	public void clear()
	{
		bufferedImage.flush();
		height = 0;
		width = 0;
		strForlder = "";
	}
}
