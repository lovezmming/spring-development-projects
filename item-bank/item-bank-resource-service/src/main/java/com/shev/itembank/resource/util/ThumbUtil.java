package com.shev.itembank.resource.util;

import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.shev.itembank.resource.entity.Thumb;

public class ThumbUtil
{

    public static String V_FRAME = "vframe";
    public static String LEFT_DIAGONAL = "/";
    public static String IMAGE_TYPE_JPG = "jpg";

    public static String OFFSET = "offset";
    public static String W_LOWWER = "w";
    public static String H_LOWWER = "h";

    public static String VERTICAL_LINE = "|";
    public static String SAVE_AS = "saveas";

    public static String FORCE = "force";
    public static Integer ONE = 1;
    public static Boolean TRUE_BOOLEAN = true;
    public static String PIPE_LINE = "pipeline";

    public static String createPfops(Thumb t)
    {
        String urlbase64 = UrlSafeBase64.encodeToString(t.getBucket() + ":" + t.getDestThumb());

        StringBuffer temp = new StringBuffer();
        temp.append(V_FRAME);
        temp.append(LEFT_DIAGONAL);
        temp.append(IMAGE_TYPE_JPG);
        temp.append(LEFT_DIAGONAL);
        temp.append(OFFSET);
        temp.append(LEFT_DIAGONAL);
        temp.append(t.getFrap());
        temp.append(LEFT_DIAGONAL);
        temp.append(W_LOWWER);
        temp.append(LEFT_DIAGONAL);
        temp.append(t.getWidth());
        temp.append(LEFT_DIAGONAL);
        temp.append(H_LOWWER);
        temp.append(LEFT_DIAGONAL);
        temp.append(t.getHeight());
        temp.append(LEFT_DIAGONAL);
        temp.append(VERTICAL_LINE);
        temp.append(SAVE_AS);
        temp.append(LEFT_DIAGONAL);
        temp.append(urlbase64);

        return temp.toString();
    }

    public static String createPfopsOrignSize(Thumb t)
    {
        String urlbase64 = UrlSafeBase64.encodeToString(t.getBucket() + ":" + t.getDestThumb());

        StringBuffer temp = new StringBuffer();
        temp.append(V_FRAME);
        temp.append(LEFT_DIAGONAL);
        temp.append(IMAGE_TYPE_JPG);
        temp.append(LEFT_DIAGONAL);
        temp.append(OFFSET);
        temp.append(LEFT_DIAGONAL);
        temp.append(t.getFrap());
        temp.append(LEFT_DIAGONAL);
        temp.append(VERTICAL_LINE);
        temp.append(SAVE_AS);
        temp.append(LEFT_DIAGONAL);
        temp.append(urlbase64);

        return temp.toString();
    }


    public static StringMap createParam(Thumb t)
    {
        StringMap params = new StringMap().putWhen(FORCE, ONE, TRUE_BOOLEAN).putNotEmpty(PIPE_LINE, t.getPipeline());
        return params;
    }
    
}