package com.shev.itembank.latex.util;

import com.shev.itembank.latex.entity.ElementWithPngMessage;
import com.shev.itembank.latex.entity.PngMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class RewritePngMsgUtil
{

	public static ElementWithPngMessage process(Document docIn,String strMaxFolderIn,String rootFolder) throws IOException, DocumentException{
	    ElementWithPngMessage elementWithPngMessage = new ElementWithPngMessage();
	    String strMaxFolder = strMaxFolderIn;
		elementWithPngMessage.clear();
		Document docOut = docIn;
		Element rootElement = docOut.getRootElement();
		if(rootElement.getName().equals("statement"))
		{
		    if(rootElement.elements("p")!=null){
	            for (Iterator<?> i1 = rootElement.elementIterator("p"); i1.hasNext();) {
	                Element thisPTag =(Element) i1.next();
	                getTex(1, thisPTag, elementWithPngMessage, rootFolder);
	            }
	        }
	        if(rootElement.element("choices")!=null){
	            for (Iterator<?> i1 = rootElement.element("choices").elementIterator("choice"); i1.hasNext();) {
	                Element eleChoice = (Element) i1.next();
	                for ( Iterator<?> i2 = eleChoice.elementIterator("p"); i2.hasNext();) {
	                    Element thisPTag =(Element) i2.next();
	                    getTex(1 ,thisPTag, elementWithPngMessage, rootFolder);
	                }
	            }
	        }
	        if(rootElement.elements("table")!=null){
	            for (Iterator<?> i1 = rootElement.elementIterator("table"); i1.hasNext();) {
	                Element eleTable =(Element) i1.next();
	                for(Iterator<?> i2 = eleTable.elementIterator("tr"); i2.hasNext();) {
	                    Element eleTr =(Element) i2.next();
	                    for(Iterator<?> i3 = eleTr.elementIterator("td"); i3.hasNext();) {
	                        Element eleTd =(Element) i3.next();
	                        for(Iterator<?> i4 = eleTd.elementIterator("p"); i4.hasNext();) {
	                            Element thisPTag =(Element) i4.next();
	                            getTex(1, thisPTag,elementWithPngMessage, rootFolder);
	                        }
	                    }
	                }
	            }
	        }
		}
		else if(rootElement.getName().equals("solutions"))
		{
		    for ( Iterator<?> i1 = rootElement.elementIterator("solution"); i1.hasNext(); ) {
	            Element eleSolution = (Element) i1.next();
	            if (eleSolution.element("analyse").elements("p")!=null){
	                for ( Iterator<?> i2 = eleSolution.element("analyse").elementIterator("p");i2.hasNext();){
	                    Element thisPTag =(Element) i2.next();
	                    getTex(2, thisPTag,elementWithPngMessage, rootFolder);
	                }
	            }
	            if(eleSolution.element("analyse").elements("table")!=null){
	                for ( Iterator<?> i2 = eleSolution.element("analyse").elementIterator("table"); i2.hasNext(); ) {
	                    Element eleTable =(Element) i2.next();
	                    for(Iterator<?> i3 = eleTable.elementIterator("tr"); i3.hasNext(); ) {
	                        Element eleTr =(Element) i3.next();
	                        for(Iterator<?> i4 = eleTr.elementIterator("td"); i4.hasNext(); ) {
	                            Element eleTd =(Element) i4.next();
	                            for(Iterator<?> i5 = eleTd.elementIterator("p"); i5.hasNext(); ) {
	                                Element thisPTag =(Element) i5.next();
	                                getTex(2, thisPTag,elementWithPngMessage, rootFolder);
	                            }
	                        }
	                    }
	                }
	            }
	            if (eleSolution.element("answer").elements("p")!=null){
	                for ( Iterator<?> i2 = eleSolution.element("answer").elementIterator("p");i2.hasNext();){
	                    Element thisPTag=(Element) i2.next();
	                    getTex(2, thisPTag,elementWithPngMessage, rootFolder);
	                }
	            }
	            if(eleSolution.element("answer").elements("table")!=null){
	                for ( Iterator<?> i2 = eleSolution.element("answer").elementIterator("table"); i2.hasNext(); ) {
	                    Element eleTable =(Element) i2.next();
	                    for(Iterator<?> i3 = eleTable.elementIterator("tr"); i3.hasNext(); ) {
	                        Element eleTr =(Element) i3.next();
	                        for(Iterator<?> i4 = eleTr.elementIterator("td"); i4.hasNext(); ) {
	                            Element eleTd =(Element) i4.next();
	                            for(Iterator<?> i5 = eleTd.elementIterator("p"); i5.hasNext(); ) {
	                                Element thisPTag =(Element) i5.next();
	                                getTex(2, thisPTag,elementWithPngMessage, rootFolder);
	                            }
	                        }
	                    }

	                }
	            }
	            if (eleSolution.element("comment").elements("p")!=null){
	                for ( Iterator<?> i2 = eleSolution.element("comment").elementIterator("p");i2.hasNext();){
	                    Element thisPTag =(Element) i2.next();
	                    getTex(2, thisPTag,elementWithPngMessage, rootFolder);
	                }

	            }
	            if(eleSolution.element("comment").elements("table")!=null){
	                for ( Iterator<?> i2 = eleSolution.element("comment").elementIterator("table"); i2.hasNext(); ) {
	                    Element eleTable =(Element) i2.next();
	                    for(Iterator<?> i3 = eleTable.elementIterator("tr"); i3.hasNext(); ) {
	                        Element eleTr =(Element) i3.next();
	                        for(Iterator<?> i4 = eleTr.elementIterator("td"); i4.hasNext(); ) {
	                            Element eleTd =(Element) i4.next();
	                            for(Iterator<?> i5 = eleTd.elementIterator("p"); i5.hasNext(); ) {
	                                Element thisPTag =(Element) i5.next();
	                                getTex(2, thisPTag,elementWithPngMessage, rootFolder);
	                            }
	                        }
	                    }

	                }
	            }
	        }
		}

		elementWithPngMessage.document = docOut;
		if(elementWithPngMessage.vecPngMessage1.size()>0)
			combienPng1(strMaxFolder, elementWithPngMessage);
		if(elementWithPngMessage.vecPngMessage2.size()>0)
			combienPng2(strMaxFolder, elementWithPngMessage);
		return elementWithPngMessage;
	}
	
	private static void getTex(int type, Element thisPTag ,ElementWithPngMessage elementWithPngMessage, String rootFolder) throws DocumentException, IOException{
		if(thisPTag.elements("formula")!=null||thisPTag.elements("connectionformula")!=null||thisPTag.elements("blank")!=null){
			for ( Iterator<?> i1 = thisPTag.elementIterator();i1.hasNext();){
				Element TTag =(Element) i1.next();
				if(TTag.getName().equals("formula")||TTag.getName().equals("connectionformula")){
					String tmp = rootFolder + "TexPicTmp/tex"+(elementWithPngMessage.vecPngMessage1.size()+elementWithPngMessage.vecPngMessage2.size()+1)+"_s1.png";
					PngMessage pngMessage = new PngMessage();
					pngMessage.strForlder = tmp;
					File file = new File(tmp);
					BufferedImage ImageOne = ImageIO.read(file);
					pngMessage.width = ImageOne.getWidth();
					pngMessage.height = ImageOne.getHeight();
					ImageOne.flush();
					pngMessage.bufferedImage = ImageOne;
					pngMessage.imageStr = tmp;
					if(type==1){
						elementWithPngMessage.vecPngMessage1.add(pngMessage);
						TTag.addAttribute("x",elementWithPngMessage.totalWidth1+"");
						TTag.addAttribute("y",0+"");
						TTag.addAttribute("width",pngMessage.width+"");
						TTag.addAttribute("height",pngMessage.height+"");
						if(elementWithPngMessage.maxHeight1<pngMessage.height)
							elementWithPngMessage.maxHeight1 = pngMessage.height;
						elementWithPngMessage.totalWidth1 = elementWithPngMessage.totalWidth1 + pngMessage.width;
					}else if(type==2){
						elementWithPngMessage.vecPngMessage2.add(pngMessage);
						TTag.addAttribute("x",elementWithPngMessage.totalWidth2+"");
						TTag.addAttribute("y",0+"");
						TTag.addAttribute("width",pngMessage.width+"");
						TTag.addAttribute("height",pngMessage.height+"");
						if(elementWithPngMessage.maxHeight2<pngMessage.height)
							elementWithPngMessage.maxHeight2 = pngMessage.height;
						elementWithPngMessage.totalWidth2 = elementWithPngMessage.totalWidth2 + pngMessage.width;
					}

				}else if(TTag.getName().equals("blank")){
					for(Iterator<?> i2 = TTag.elementIterator();i2.hasNext();){
						Element elePTag2 =(Element) i2.next();
						for(Iterator<?> i3 = elePTag2.elementIterator();i3.hasNext();){
							Element elePTag3 =(Element) i3.next();
							if(elePTag3.getName().equals("formula")||elePTag3.getName().equals("connectionformula")){
								String tmp = rootFolder + "TexPicTmp/tex"+(elementWithPngMessage.vecPngMessage1.size()+elementWithPngMessage.vecPngMessage2.size()+1)+"_s1.png";
								PngMessage pngMessage = new PngMessage();
								pngMessage.strForlder = tmp;
								//System.out.println(tmp);
								File file = new File(tmp);
								BufferedImage ImageOne = ImageIO.read(file);
								pngMessage.width = ImageOne.getWidth();
								pngMessage.height = ImageOne.getHeight();
								ImageOne.flush();
								pngMessage.bufferedImage = ImageOne;
								pngMessage.imageStr = tmp;
								if(type==1){
									elementWithPngMessage.vecPngMessage1.add(pngMessage);
									elePTag3.addAttribute("x",elementWithPngMessage.totalWidth1+"");
									elePTag3.addAttribute("y",0+"");
									elePTag3.addAttribute("width",pngMessage.width+"");
									elePTag3.addAttribute("height",pngMessage.height+"");
									if(elementWithPngMessage.maxHeight1<pngMessage.height)
										elementWithPngMessage.maxHeight1 = pngMessage.height;
									elementWithPngMessage.totalWidth1 = elementWithPngMessage.totalWidth1 + pngMessage.width;
								}else if(type==2){
									elementWithPngMessage.vecPngMessage2.add(pngMessage);
									elePTag3.addAttribute("x",elementWithPngMessage.totalWidth2+"");
									elePTag3.addAttribute("y",0+"");
									elePTag3.addAttribute("width",pngMessage.width+"");
									elePTag3.addAttribute("height",pngMessage.height+"");
									if(elementWithPngMessage.maxHeight2<pngMessage.height)
										elementWithPngMessage.maxHeight2 = pngMessage.height;
									elementWithPngMessage.totalWidth2 = elementWithPngMessage.totalWidth2 + pngMessage.width;
									
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	
	private static void combienPng1(String strMaxFolder ,ElementWithPngMessage elementWithPngMessage) throws IOException{
		BufferedImage ImageNew = new BufferedImage(elementWithPngMessage.totalWidth1*2,elementWithPngMessage.maxHeight1*2,BufferedImage.TYPE_4BYTE_ABGR);
		int currentWidth = 0;
		for(int i=0;i<elementWithPngMessage.vecPngMessage1.size();i++){
			elementWithPngMessage.vecPngMessage1.get(i).bufferedImage = ImageIO.read(new File(elementWithPngMessage.vecPngMessage1.get(i).imageStr.replace("_s1.png", "_s2.png")));
			int[] ImageArray = new int[elementWithPngMessage.vecPngMessage1.get(i).width*2*elementWithPngMessage.vecPngMessage1.get(i).height*2];
			ImageArray = elementWithPngMessage.vecPngMessage1.get(i).bufferedImage.getRGB(0, 0, elementWithPngMessage.vecPngMessage1.get(i).width*2, elementWithPngMessage.vecPngMessage1.get(i).height*2,ImageArray,0, elementWithPngMessage.vecPngMessage1.get(i).width*2);
			ImageNew.setRGB(currentWidth, 0, elementWithPngMessage.vecPngMessage1.get(i).width*2,elementWithPngMessage.vecPngMessage1.get(i).height*2,ImageArray, 0, elementWithPngMessage.vecPngMessage1.get(i).width*2);
			currentWidth = currentWidth+elementWithPngMessage.vecPngMessage1.get(i).width*2;
			elementWithPngMessage.vecPngMessage1.get(i).bufferedImage.flush();
		}//改动
		//File outFile = new File(strMaxFolder.replace(".png", "_r.png"));
		File outFile = new File(strMaxFolder.replace(".png", "_s_b.png"));
		if (outFile.exists()) {
			//System.out.println("The folder exists.");
		} else {
			outFile.mkdirs();
		}
		ImageIO.write(ImageNew, "png", outFile);// 写图片
		BufferedImage ImageNew2 = new BufferedImage(elementWithPngMessage.totalWidth1,elementWithPngMessage.maxHeight1,BufferedImage.TYPE_4BYTE_ABGR);
		int currentWidth2 = 0;
		for(int i=0;i<elementWithPngMessage.vecPngMessage1.size();i++){
			elementWithPngMessage.vecPngMessage1.get(i).bufferedImage = ImageIO.read(new File(elementWithPngMessage.vecPngMessage1.get(i).imageStr));
			int[] ImageArray = new int[elementWithPngMessage.vecPngMessage1.get(i).width*elementWithPngMessage.vecPngMessage1.get(i).height];
			ImageArray = elementWithPngMessage.vecPngMessage1.get(i).bufferedImage.getRGB(0, 0, elementWithPngMessage.vecPngMessage1.get(i).width, elementWithPngMessage.vecPngMessage1.get(i).height,ImageArray,0, elementWithPngMessage.vecPngMessage1.get(i).width);
			ImageNew2.setRGB(currentWidth2, 0, elementWithPngMessage.vecPngMessage1.get(i).width,elementWithPngMessage.vecPngMessage1.get(i).height,ImageArray, 0, elementWithPngMessage.vecPngMessage1.get(i).width);
			currentWidth2 = currentWidth2+elementWithPngMessage.vecPngMessage1.get(i).width;
			elementWithPngMessage.vecPngMessage1.get(i).bufferedImage.flush();
		}//改动
		//File outFile = new File(strMaxFolder.replace(".png", "_r.png"));
		File outFile2 = new File(strMaxFolder.replace(".png", "_s.png"));
		if (outFile.exists()) {
			//System.out.println("The folder exists.");
		} else {
			outFile.mkdirs();
		}
		ImageIO.write(ImageNew2, "png", outFile2);// 写图片
		
	}
	private static void combienPng2(String strMaxFolder,ElementWithPngMessage elementWithPngMessage) throws IOException{
		BufferedImage ImageNew = new BufferedImage(elementWithPngMessage.totalWidth2*2,elementWithPngMessage.maxHeight2*2,BufferedImage.TYPE_4BYTE_ABGR);
		int currentWidth = 0;
		for(int i=0;i<elementWithPngMessage.vecPngMessage2.size();i++){
			elementWithPngMessage.vecPngMessage2.get(i).bufferedImage = ImageIO.read(new File(elementWithPngMessage.vecPngMessage2.get(i).imageStr.replace("_s1.png", "_s2.png")));
			int[] ImageArray = new int[elementWithPngMessage.vecPngMessage2.get(i).width*2*elementWithPngMessage.vecPngMessage2.get(i).height*2];
			ImageArray = elementWithPngMessage.vecPngMessage2.get(i).bufferedImage.getRGB(0, 0, elementWithPngMessage.vecPngMessage2.get(i).width*2, elementWithPngMessage.vecPngMessage2.get(i).height*2,ImageArray,0, elementWithPngMessage.vecPngMessage2.get(i).width*2);
			ImageNew.setRGB(currentWidth, 0, elementWithPngMessage.vecPngMessage2.get(i).width*2,elementWithPngMessage.vecPngMessage2.get(i).height*2,ImageArray, 0, elementWithPngMessage.vecPngMessage2.get(i).width*2);
			currentWidth = currentWidth+elementWithPngMessage.vecPngMessage2.get(i).width*2;
			elementWithPngMessage.vecPngMessage2.get(i).bufferedImage.flush();
			
		}//改动
		//File outFile = new File(strMaxFolder.replace(".png", "_r.png"));
		File outFile = new File(strMaxFolder.replace(".png", "_a_b.png"));
		if (outFile.exists()) {
			//System.out.println("The folder exists.");
		} else {
			outFile.mkdirs();
		}
		ImageIO.write(ImageNew, "png", outFile);// 写图片
		BufferedImage ImageNew2 = new BufferedImage(elementWithPngMessage.totalWidth2,elementWithPngMessage.maxHeight2,BufferedImage.TYPE_4BYTE_ABGR);
		int currentWidth2 = 0;
		for(int i=0;i<elementWithPngMessage.vecPngMessage2.size();i++){
			elementWithPngMessage.vecPngMessage2.get(i).bufferedImage = ImageIO.read(new File(elementWithPngMessage.vecPngMessage2.get(i).imageStr));
			int[] ImageArray = new int[elementWithPngMessage.vecPngMessage2.get(i).width*elementWithPngMessage.vecPngMessage2.get(i).height];
			ImageArray = elementWithPngMessage.vecPngMessage2.get(i).bufferedImage.getRGB(0, 0, elementWithPngMessage.vecPngMessage2.get(i).width, elementWithPngMessage.vecPngMessage2.get(i).height,ImageArray,0, elementWithPngMessage.vecPngMessage2.get(i).width);
			ImageNew2.setRGB(currentWidth2, 0, elementWithPngMessage.vecPngMessage2.get(i).width,elementWithPngMessage.vecPngMessage2.get(i).height,ImageArray, 0, elementWithPngMessage.vecPngMessage2.get(i).width);
			currentWidth2 = currentWidth2+elementWithPngMessage.vecPngMessage2.get(i).width;
			elementWithPngMessage.vecPngMessage2.get(i).bufferedImage.flush();
		}//改动
		//File outFile = new File(strMaxFolder.replace(".png", "_r.png"));
		File outFile2 = new File(strMaxFolder.replace(".png", "_a.png"));
		if (outFile.exists()) {
			//System.out.println("The folder exists.");
		} else {
			outFile.mkdirs();
		}
		ImageIO.write(ImageNew2, "png", outFile2);// 写图片
	}
}
