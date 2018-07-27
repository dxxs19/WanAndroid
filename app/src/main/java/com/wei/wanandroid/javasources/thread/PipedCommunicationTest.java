package com.wei.wanandroid.javasources.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 通常没有必要使用管道流来控制两个线程之间的通信，因为两个线程属于同一个进程，它们可以非常
 * 方便地共享数据，这种方式才是线程之间进行信息交换的最好方式，而不是使用管道流。
 * @author: WEI
 * @date: 2018/7/24
 */
public class PipedCommunicationTest
{
    public static void main(String[] args) {
        PipedWriter pipedWriter = null;
        PipedReader pipedReader = null;
        try {
            // 分别创建两个独立的管道输出流、输入流
            pipedWriter = new PipedWriter();
            pipedReader = new PipedReader();
            // 连接管道输出流、输入流
            pipedWriter.connect(pipedReader);
            // 将连接好的管道流分别传入两个线程，就可以让两个线程通过管道流进行通信
            new WriterThread(pipedWriter).start();
            new ReaderThread(pipedReader).start();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class ReaderThread extends Thread
{
    private PipedReader mPipedReader;
    // 用于包装管道流的 BufferedReader 对象
    private BufferedReader mBufferedReader;
    public ReaderThread(PipedReader pipedReader)
    {
        mPipedReader = pipedReader;
        mBufferedReader = new BufferedReader(mPipedReader);
    }

    @Override
    public void run() {
        String buf;
        try {
            // 逐行读取管道输入流中的内容
            while ((buf = mBufferedReader.readLine()) != null)
            {
                System.out.println(buf);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            try {
                if (mBufferedReader != null)
                {
                    mBufferedReader.close();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

class WriterThread extends Thread
{
    String[] books = {"Android开发艺术探索", "Android核心分析", "Android权威指南", "Android高级进阶"};
    private PipedWriter mPipedWriter;

    public WriterThread(PipedWriter pipedWriter)
    {
        mPipedWriter = pipedWriter;
    }

    @Override
    public void run() {
        try {
            // 循环100次，向管道输出流中写入100组字符串
            for (int i = 0; i < 100; i ++)
            {
                mPipedWriter.write(books[i % 4] + "\n");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            try {
                if (mPipedWriter != null)
                {
                    mPipedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
