package com.wei.wanandroid.bean;

import javax.inject.Inject;

import dagger.Module;

@Module
public class BeautyBean
{
    private String filePath;
    private String fileName;

    @Inject
    public BeautyBean(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "BeautyBean{" +
                "filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
