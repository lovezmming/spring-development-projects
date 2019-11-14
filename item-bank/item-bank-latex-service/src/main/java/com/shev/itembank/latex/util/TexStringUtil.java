package com.shev.itembank.latex.util;

import java.util.List;

public class TexStringUtil
{
    private static final String head = "\\batchmode\r\n" + "\\documentclass[30pt]{article}\r\n" + "\\usepackage{mathabx}\r\n" + "\\usepackage{ctex}\r\n" + "\\usepackage{CJK}\r\n" + "\\pagestyle{empty}\r\n" + "\\usepackage{tikz}\r\n" + "\\newcounter{fofo}\r\n" + "\\usepackage{amsmath}\r\n"
            + "\\usepackage{amssymb}\r\n" + "\\usepackage{latexsym}\r\n" + "\\usepackage{wasysym}\r\n" + "\\usepackage{bbding}\r\n" + "\\usepackage{stmaryrd}\r\n" + "\\usepackage{color}\r\n" + "\\usepackage{extarrows}\r\n" + "\\usepackage{chemarrow}\r\n" + "\\usepackage{pifont}\r\n"
            + "\\usepackage{fourier}\r\n" + "\\DeclareMathOperator{\\arccot }{arccot}\r\n" + "\\DeclareMathOperator{\\arccot }{arcsec}\r\n" + "\\DeclareMathOperator{\\arccot }{arccsc}\r\n" + "\\begin{document}\r\n";

    private static String end = "\\end{document}";

    // 生成TEX文件
    public static String create(List<String> vecIn, String s)
    {
        String strColor = s + "\r\n";
        String strOut = head + strColor + "\\[\\[" + vecIn.get(0) + "\\]";
        for (int i = 1; i < vecIn.size(); i++)
        {
            strOut = strOut + "\r\n\\newpage\\[" + vecIn.get(i) + "\\]";
        }
        strOut = strOut + "\\]\r\n" + end;
        return strOut;
    }
}
