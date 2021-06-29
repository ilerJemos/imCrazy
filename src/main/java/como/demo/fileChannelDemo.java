package como.demo;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static como.demo.bufferDemo.printBufferInfo;

public class fileChannelDemo {

    public static void main(String[] args) throws IOException {

        FileInputStream fis = new FileInputStream("README.md");
        FileChannel inChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("README.tmp");
        FileChannel outChannel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(20);
        int len = -1;


        while ((len = inChannel.read(buffer)) != -1) {
            System.out.println(len);
            buffer.flip();
            int outlength = 0;

            while ((outlength = outChannel.write(buffer)) != 0) {
                System.out.println("写入的字节数：" + outlength);
            }
            buffer.clear();
        }
        outChannel.force(true);
        inChannel.close();
        outChannel.close();
        fis.close();
        fos.close();
    }
}
