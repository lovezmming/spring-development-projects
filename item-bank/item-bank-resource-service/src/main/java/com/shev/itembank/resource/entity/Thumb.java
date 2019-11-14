package com.shev.itembank.resource.entity;

public class Thumb
{
    private String bucket;
    private String pipeline;
    private Integer width;
    private Integer height;
    private String soureVideo;
    private String destThumb;
    private Integer frap = 4 * 24;

    public String getBucket()
    {
        return bucket;
    }

    public void setBucket(String bucket)
    {
        this.bucket = bucket;
    }

    public String getPipeline()
    {
        return pipeline;
    }

    public void setPipeline(String pipeline)
    {
        this.pipeline = pipeline;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth(Integer width)
    {
        this.width = width;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight(Integer height)
    {
        this.height = height;
    }

    public String getSoureVideo()
    {
        return soureVideo;
    }

    public void setSoureVideo(String soureVideo)
    {
        this.soureVideo = soureVideo;
    }

    public String getDestThumb()
    {
        String fileName = this.getSoureVideo().substring(0, getSoureVideo().lastIndexOf("."));
        String fileType = ".jpg";
        StringBuffer destFile = new StringBuffer();

        destFile.append(fileName);
        destFile.append("-");
        destFile.append(this.getFrap());
        destFile.append(fileType);

        this.destThumb = destFile.toString();
        return destThumb;
    }

    public void setDestThumb(String destThumb)
    {
        this.destThumb = destThumb;
    }

    public Integer getFrap()
    {
        return frap;
    }

    public void setFrap(Integer frap)
    {
        this.frap = frap;
    }
}