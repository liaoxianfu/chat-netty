package com.liao.chat.im.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Scanner;

/**
 * 测试用的命令行客户端
 */
public class TCPClient {


    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        Runnable readFun = () ->
        {
            byte[] bs = new byte[1024];
            int n;
            while (true) {
                try {
                    while ((n = in.read(bs)) != -1) {
                        System.out.println("\n接收到的数据为" + new String(bs, 0, n));
                        System.out.println();
                        System.out.print("请输入数据:");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable writeFun = () -> {
            System.out.println("请输入数据:");
            Scanner sc = new Scanner(System.in);
            try {
                while (true) {
                    String s = sc.nextLine();
                    if (s.toLowerCase(Locale.CHINA).equals("q")) {
                        break;
                    }
                    out.write(s.getBytes(Charset.defaultCharset()));

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        new Thread(readFun).start();
        new Thread(writeFun).start();
    }
}
