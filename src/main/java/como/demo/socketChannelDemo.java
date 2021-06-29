package como.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class socketChannelDemo {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        while (!socketChannel.finishConnect()) {
            //自旋
            System.out.println("hugging...");
        }
        System.out.println("connected to server...");
        String fileName = "README.md";
        File file = new File(fileName);
        FileChannel fileChannel  =  new FileInputStream(fileName).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteBuffer nameBuffer = ByteBuffer.wrap(fileName.getBytes(StandardCharsets.UTF_8));

        socketChannel.write(nameBuffer);
        buffer.putLong(file.length());
        buffer.flip();
        socketChannel.write(buffer);

        while (fileChannel.read(buffer)!=-1){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        fileChannel.close();
        socketChannel.shutdownOutput();
        socketChannel.close();
    }
}
