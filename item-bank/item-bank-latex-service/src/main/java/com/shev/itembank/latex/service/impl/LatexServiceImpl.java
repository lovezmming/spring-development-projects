package com.shev.itembank.latex.service.impl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shev.itembank.latex.entity.ElementWithPngMessage;
import com.shev.itembank.latex.service.LatexService;
import com.shev.itembank.latex.util.*;
import com.shev.itembank.resource.service.UploadService;
import com.shev.itembank.system.custom.PartnerCustomMapper;
import com.shev.itembank.system.entity.Partner;

@Service
public class LatexServiceImpl implements LatexService
{

    @Autowired
    private UploadService uploadService;

    @Autowired
    private PartnerCustomMapper partnerCustomMapper;

    @Value("${CTEX_BIN_PATH:C:/CTEX/MiKTeX/miktex/bin/}")
    private String ctexBinPath ;

    @Value("${TEX_PIC_PATH:C:/tmp/}")
    private String rootFolder;

    @Value("${tenant.default}")
    private String defaultTenantId;

    public static final String SERVERNAME = "$$path$$";

    @Override
    public Map<String, Object> latex2Pic(String tenantId, int type, String xmlContent) throws Exception
    {
        Document document = DocumentHelper.parseText(xmlContent);
        Vector<String> vector = getTexVector(document);
        Map<String, Object> map = generateTex(tenantId, document, vector);
        return map;
    }

    private Map<String, Object> generateTex(String tenantId, Document document, Vector<String> vecLatex) throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (TextUtil.isEmpty(tenantId))
            tenantId = defaultTenantId;

        Long timestamp = System.currentTimeMillis();
        // 生成tex文件
        if (vecLatex.size() > 0)
        {
            String strTex = TexStringUtil.create(vecLatex, "\\textcolor{black}");
            File filePath = new File(rootFolder + "/" + timestamp + "/TexPicTmp/");
            if (!filePath.exists())
            {
                filePath.mkdirs();
            }
            File[] files = filePath.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                files[i].delete();
            }
            File file = new File(filePath + "/" + "tex.tex");
            if (file.exists())
            {
                file.delete();
            }
            file.createNewFile();
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "GB2312");
            write.append(strTex);
            write.close();
        }

        // 生成bat文件
        TexBatUtil.process(rootFolder + "/" + timestamp + "/", ctexBinPath);
        // 运行bat文件,将每个latex公式生成图片，每个公式对应大小两个图片
        CommandUtil.executeCommand("cmd /c " + rootFolder + "/" + timestamp + "/PProcess/tex.bat");

        // 对图片重命名
        renameImageFile(rootFolder + "/" + timestamp + "/");

        // 将搜索公式的图片进行合成，生成四张图片，黑色两张，红色两张，每种颜色对应一大一小两张
        // 合成图片的文件名前缀，采用时间戳命名文件
        String fileStr = rootFolder + "/" + timestamp +  "/tmp/" + timestamp + ".png";
        combineImage(tenantId, document, fileStr, map, timestamp);

        // 对合成的图片生成红色图片，红色图片用于搜索时标识公式图片中的关键字
        List<String> pngPathList = new ArrayList<String>();
        String str = rootFolder + "/" + timestamp +  "/tmp/" ;
        listFile(pngPathList, new File(str.substring(0, str.lastIndexOf("/"))));
        for (int i = 0; i < pngPathList.size(); i++)
            PicColorUtil.changeColor(pngPathList.get(i));

        pngPathList.clear();
        listFile(pngPathList, new File(str.substring(0, str.lastIndexOf("/"))));

        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("type", "");
        List<Partner> partners = partnerCustomMapper.selectByParameter(params);
        String resourceServer = null;
        if (!TextUtil.isEmpty(partners))
        {
            Map<String, Object> comment = (Map<String, Object>) JSONObject.parse(partners.get(0).getComment());
            resourceServer = (String) comment.get("domainName");
        }

        // 将合成的图片上传到存储服务器
        String urlLocation = "";
        String prefix = timestamp.toString().substring(0, 4);
        for (String filePath : pngPathList)
        {
            File temp = new File(filePath);
            byte[] data = FileUtil.file2byte(temp);
            String url = uploadService.uploadResource(tenantId, data, "/latexpics/" + prefix, temp.getName());
            url = url.replace(resourceServer, SERVERNAME);
            if (url.endsWith("_s.png") || url.endsWith("_a.png"))
                urlLocation = url;
        }

        //删除临时文件
        FileUtils.deleteDirectory(new File(rootFolder + "/" + timestamp + "/"));
        map.put("urlLocation", urlLocation);
        return map;
    }

    // 公式图片处理：重命名并且放大外面
    private void renameImageFile(String rootFolder) throws Exception
    {
        File file = new File(rootFolder+ "TexPicTmp/");
        imageProcess(file);
    }

    // 合并图片并且将信息写入xml,将修正后的xml字符串返回
    private void combineImage(String tenantId, Document document, String fileStr, Map<String, Object> map, Long timestamp) throws IOException, DocumentException
    {
        ElementWithPngMessage elementWithPngMessage = new ElementWithPngMessage();
        elementWithPngMessage = RewritePngMsgUtil.process(document, fileStr ,rootFolder + "/" + timestamp +  "/" );
        Document newXMLDocument = elementWithPngMessage.document;
        String newXMLStr = newXMLDocument.asXML();
        map.put("formatXMLStr", newXMLStr);

        //题干中多个latex公式图片合成后的图片的宽度和高度
        map.put("formulaWidth_s", new Integer(elementWithPngMessage.totalWidth1));
        map.put("formulaHeight_s", new Integer(elementWithPngMessage.maxHeight1));

        //解法中多个latex公式图片合成后的图片的宽度和高度
        map.put("formulaWidth_a", new Integer(elementWithPngMessage.totalWidth2));
        map.put("formulaHeight_a", new Integer(elementWithPngMessage.maxHeight2));
    }

    // 公式图片处理：重命名并且放大
    private void imageProcess(File file) throws Exception
    {
        if (file.isFile())
        {
            if (file.toString().contains(".png"))
            {
                String strT = file.getAbsoluteFile().toString();
                scale(strT);
            }
        } else
        {
            File[] files = file.listFiles();
            if (files != null)
            {
                for (int i = 0; i < files.length; i++)
                {
                    imageProcess(files[i]);
                }
            }
        }
    }

    // 公式图片放大4倍图
    private void scale(String fileName) throws IOException
    {
        BufferedImage sourceImage = ImageIO.read(new File(fileName));
        // 缩放图像
        double width = (double) sourceImage.getWidth() / 2;
        double height = (double) sourceImage.getHeight() / 2;
        int width_ = 0, height_ = 0;
        if (width <= height)
        {
            width_ = (int) Math.floor(width);
            height_ = (int) Math.floor(height - height * (width - width_) / width);
            if (width_ % 2 == 1)
            {
                width_ = width_ + 1;
                double f = height_ + height_ * ((double) 1 / width_);
                height_ = (int) Math.ceil(f);
                if (height_ % 2 == 1)
                    height_ = height_ + 1;
            } else
            {
                if (height_ % 2 == 1)
                    height_ = height_ + 1;
            }
        } else
        {
            height_ = (int) Math.floor(height);
            width_ = (int) Math.floor(width - width * (height - height_) / height);
            if (height_ % 2 == 1)
            {
                height_ = height_ + 1;
                double f = width_ + width_ * ((double) 1 / height_);
                width_ = (int) Math.ceil(f);
                if (width_ % 2 == 1)
                    width_ = width_ + 1;
            } else
            {
                if (width_ % 2 == 1)
                    width_ = width_ + 1;
            }
        }
        if (width_ == 0 || height_ == 0)
        {
            width_ = 2;
            height_ = 2;
        }
        Image image = sourceImage.getScaledInstance(width_, height_, Image.SCALE_SMOOTH);
        // 大图
        BufferedImage bigImage = new BufferedImage(width_, height_, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bigImage.createGraphics();
        g.drawImage(image, 0, 0, null); // 绘制缩小后的
        g.dispose();
        OutputStream out = new FileOutputStream(fileName.replace(".png", "_s2.png"));
        ImageIO.write(bigImage, "png", out);// 输出
        out.close();
        bigImage.flush();
        image.flush();
        Image image2 = sourceImage.getScaledInstance(width_ / 2, height_ / 2, Image.SCALE_SMOOTH);
        // 小图
        BufferedImage bigImage2 = new BufferedImage(width_ / 2, height_ / 2, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = bigImage2.createGraphics();
        g2.drawImage(image2, 0, 0, null); // 绘制缩小后的
        g2.dispose();
        OutputStream out2 = new FileOutputStream(fileName.replace(".png", "_s1.png"));
        ImageIO.write(bigImage2, "png", out2);// 输出
        out2.close();
        bigImage2.flush();
        image2.flush();
        sourceImage.flush();
    }

    // 找所有的黑图
    private void listFile(List<String> vFile, File file) throws Exception
    {
        if (file.isFile())
        {
            if (file.toString().contains(".png"))
            {
                vFile.add(file.getAbsolutePath());
            }
        } else
        {
            File[] files = file.listFiles();
            if (files != null)
            {
                for (int i = 0; i < files.length; i++)
                {
                    listFile(vFile, files[i]);
                }
            }
        }
    }

    private Vector<String> getTexVector(Document docOut) throws DocumentException, IOException
    {
        Vector<String> vector = new Vector<String>();
        Element thisPTag;
        Element rootElement = docOut.getRootElement();
        if(rootElement.getName().equals("statement"))
        {
            if (docOut.getRootElement().elements("p") != null)
            {
                for (Iterator<?> i1 = docOut.getRootElement().elementIterator("p"); i1.hasNext();)
                {
                    thisPTag = (Element) i1.next();
                    getTex(thisPTag, vector);
                }
            }
            if (docOut.getRootElement().element("choices") != null)
            {
                for (Iterator<?> i1 = docOut.getRootElement().element("choices").elementIterator("choice"); i1.hasNext();)
                {
                    Element eleChoice = (Element) i1.next();
                    for (Iterator<?> i2 = eleChoice.elementIterator("p"); i2.hasNext();)
                    {
                        thisPTag = (Element) i2.next();
                        getTex(thisPTag, vector);
                    }
                }
            }
            if (docOut.getRootElement().elements("table") != null)
            {
                for (Iterator<?> i1 = docOut.getRootElement().elementIterator("table"); i1.hasNext();)
                {
                    Element eleTable = (Element) i1.next();
                    for (Iterator<?> i2 = eleTable.elementIterator("tr"); i2.hasNext();)
                    {
                        Element eleTr = (Element) i2.next();
                        for (Iterator<?> i3 = eleTr.elementIterator("td"); i3.hasNext();)
                        {
                            Element eleTd = (Element) i3.next();
                            for (Iterator<?> i4 = eleTd.elementIterator("p"); i4.hasNext();)
                            {
                                thisPTag = (Element) i4.next();
                                getTex(thisPTag, vector);
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
                        thisPTag =(Element) i2.next();
                        getTex(thisPTag, vector);
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
                                    thisPTag =(Element) i5.next();
                                    getTex(thisPTag, vector);
                                }
                            }
                        }
                    }
                }
                if (eleSolution.element("answer").elements("p")!=null){
                    for ( Iterator<?> i2 = eleSolution.element("answer").elementIterator("p");i2.hasNext();){
                        thisPTag=(Element) i2.next();
                        getTex(thisPTag, vector);
                    }
                }
                if(eleSolution.element("answer").elements("table")!=null){
                    for (Iterator<?> i2 = eleSolution.element("answer").elementIterator("table"); i2.hasNext(); ) {
                        Element eleTable =(Element) i2.next();
                        for(Iterator<?> i3 = eleTable.elementIterator("tr"); i3.hasNext(); ) {
                            Element eleTr =(Element) i3.next();
                            for(Iterator<?> i4 = eleTr.elementIterator("td"); i4.hasNext(); ) {
                                Element eleTd =(Element) i4.next();
                                for(Iterator<?> i5 = eleTd.elementIterator("p"); i5.hasNext(); ) {
                                    thisPTag =(Element) i5.next();
                                    getTex(thisPTag, vector);
                                }
                            }
                        }

                    }
                }
                if (eleSolution.element("comment").elements("p")!=null){
                    for ( Iterator<?> i2 = eleSolution.element("comment").elementIterator("p");i2.hasNext();){
                        thisPTag =(Element) i2.next();
                        getTex(thisPTag, vector);
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
                                    thisPTag =(Element) i5.next();
                                    getTex(thisPTag, vector);
                                }
                            }
                        }

                    }
                }
            }
        }
        return vector;
    }

    private void getTex(Element thisPTag, Vector<String> vector) throws DocumentException, IOException
    {
        if (thisPTag.elements("formula") != null || thisPTag.elements("connectionformula") != null || thisPTag.elements("blank") != null)
        {
            for (Iterator<?> i1 = thisPTag.elementIterator(); i1.hasNext();)
            {
                Element TTag = (Element) i1.next();
                if (TTag.getName().equals("formula") || TTag.getName().equals("connectionformula"))
                {
                    String tex = TTag.getText();
                    vector.add(tex);
                } else if (TTag.getName().equals("blank"))
                {
                    for (Iterator<?> i2 = TTag.elementIterator(); i2.hasNext();)
                    {
                        Element elePTag2 = (Element) i2.next();
                        for (Iterator<?> i3 = elePTag2.elementIterator(); i3.hasNext();)
                        {
                            Element elePTag3 = (Element) i3.next();
                            if (elePTag3.getName().equals("formula") || elePTag3.getName().equals("connectionformula"))
                            {
                                String tex = TTag.getText();
                                vector.add(tex);
                            }
                        }
                    }
                }
            }
        }
    }
}
