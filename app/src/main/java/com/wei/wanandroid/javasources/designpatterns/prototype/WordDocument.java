package com.wei.wanandroid.javasources.designpatterns.prototype;

import java.util.ArrayList;

/**
 * 实现Cloneable接口表示这个类是可拷贝的。如果没有实现Cloneable接口却调用了clone函数将抛出异常。
 */
public class WordDocument implements Cloneable{
    private String mText;
    private ArrayList<String> mImages = new ArrayList<>();

    public WordDocument()
    {
        System.out.println("--- WordDocument 构造函数 ---");
    }

    @Override
    protected WordDocument clone() {
        WordDocument wordDocument = null;
        try {
            wordDocument = (WordDocument) super.clone();
            wordDocument.mText = this.mText;
            // wordDocument.mImages = this.mImages;这种形式为浅拷贝，副本变动时，原本也会跟着变化。下面形式是深拷贝，副本变化时，原本不受影响
            wordDocument.mImages = (ArrayList<String>) this.mImages.clone();
            return wordDocument;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public ArrayList<String> getmImages() {
        return mImages;
    }

    public void addImage(String img) {
        this.mImages.add(img);
    }

    public void showDocument()
    {
        System.out.println("--- Word Content Start ---");
        System.out.println("Text : " + mText);
        System.out.println("Images List : ");
        for (String imgName:mImages) {
            System.out.println("image name : " + imgName);
        }
        System.out.println("--- Word Content End ---");
    }
}
