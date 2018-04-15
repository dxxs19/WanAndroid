package com.wei.wanandroid.javasources.designpatterns.prototype;

/**
 * 原型模式：用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象。
 * 非常简单的一个模式,它的核心问题就是对原始对象进行拷贝,在这个模式的使用过程中需要注意的一点就是:深、浅
 * 拷贝的问题。在开发过程中，为了减少错误，在使用该模式时尽量使用深拷贝，避免操作副本时影响原始对象的问题。
 */
public class Client {
    public static void main(String[] args) {
        WordDocument originalDoc = new WordDocument();
        originalDoc.setText("这是一篇文档");
        originalDoc.addImage("图片 1");
        originalDoc.addImage("图片 2");
        originalDoc.addImage("图片 3");
        originalDoc.showDocument();

        // 通过clone拷贝对象时并不会执行构造函数
        WordDocument doc2 = originalDoc.clone();
        doc2.showDocument();
        // doc2 是一份拷贝，它修改后的内容不会影响原始对象的内容
        doc2.setText("这是修改过的Doc 2文本");
        doc2.addImage("doc2 新增的图片");
        doc2.showDocument();

        originalDoc.showDocument();
    }
}
