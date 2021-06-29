package como.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class NioDiscardClient {
    public static void startClient() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9999));
        socketChannel.configureBlocking(false);
        while (!socketChannel.isConnected()) {
            Logger.getGlobal().info("waitting...");
        }
        Logger.getGlobal().info("Connected to server");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("hello world".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        socketChannel.write(buffer);
        socketChannel.shutdownOutput();
        socketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        startClient();
    }
}
