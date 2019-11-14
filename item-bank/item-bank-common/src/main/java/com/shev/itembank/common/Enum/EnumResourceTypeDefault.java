package com.shev.itembank.common.Enum;

public enum EnumResourceTypeDefault
{
    Other((byte) 0, "Other"), PDF((byte) 1, "PDF"), Word((byte) 2, "Word"), Excel((byte) 3, "Excel"), PPT((byte) 4, "PPT"), Audio((byte) 5, "Audio"), Video((byte) 6, "Video"), Image((byte) 7, "Image"), TGBD((byte) 8, "TGBD"), Game((byte) 9, "Game");

    private byte _id;
    private String _name;

    private EnumResourceTypeDefault(byte id, String name)
    {
        _id = id;
        _name = name;
    }

    public byte getId()
    {
        return _id;
    }

    public String getName()
    {
        return _name;
    }

    public final static EnumResourceTypeDefault getByResourceSuffix(String suffix)
    {
        switch (suffix.toLowerCase())
        {
        case ".pdf":
            return PDF;
        case ".doc":
        case ".docx":
            return Word;
        case ".xls":
        case ".xlsx":
            return Excel;
        case ".ppt":
        case ".pptx":
            return PPT;
        case ".mp3":
            return Audio;
        case ".mp4":
            return Video;
        case ".jpg":
        case ".png":
            return Image;
        default:
            return Other;
        }
    }
}
