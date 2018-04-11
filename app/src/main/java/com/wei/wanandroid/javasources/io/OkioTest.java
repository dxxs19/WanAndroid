package com.wei.wanandroid.javasources.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import okio.BufferedSource;
import okio.Okio;

/**
 * @author: WEI
 * @date: 2017/11/6
 */

public class OkioTest
{
    public static void main(String[] args) {
        File file = new File("build.gradle");
        try {
            readContext(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void readContext(FileInputStream fileInputStream) {
        BufferedSource source = Okio.buffer(Okio.source(fileInputStream));
        try {
            String s = source.readUtf8();
            System.out.println(s);
            source.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
