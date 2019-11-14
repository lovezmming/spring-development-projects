package com.shev.itembank.latex.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PicColorUtil
{
	
	public static void changeColor(String strIn) throws Exception
	{
	    int[] rgb = new int[4];
	    BufferedImage bufferedImage = null;
	    BufferedImage targetImage = null;
		File file = new File(strIn);
		
		bufferedImage = ImageIO.read(file);
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		int pixel;
		targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				pixel =bufferedImage.getRGB(i, j);
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
				rgb[3] = pixel - (pixel & 0xff0000) - (pixel & 0xff00)-(pixel & 0xff);
				if(rgb[1]==0&&rgb[2]==0&&rgb[3]==0)
				{
					rgb[0] = 255;
					rgb[1] = 255;
					rgb[2] = 255;
				} else
					{
					rgb[0] =255;
				}
				pixel = rgb[3]+((rgb[0]& 0xffffffff)<<16) | ((rgb[1]& 0xffffffff)<<8) | ((rgb[2]& 0xffffffff)<<0); 
				if(rgb[1]!=255||rgb[1]!=255||rgb[0]!=255)
					targetImage.setRGB(i, j, pixel);
			}
		}
		File outFile = new File(strIn.replace(".png", "_r.png"));//生成输出的文件，但这时候没有输出图像信息
		ImageIO.write( targetImage, "PNG",outFile);
		targetImage.flush();
		bufferedImage.flush();
	}

}