package como.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class serverSocketChannelDemo {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1",9999));
        SocketChannel socketChannel = serverChannel.accept();
        serverChannel.configureBlocking(false);

        ByteBuffer buf = ByteBuffer.allocate(1024);
        while (socketChannel.read(buf)!=-1){
            buf.flip();
            buf.clear();
        }

        buf.flip();
        socketChannel.write(buf);
        socketChannel.shutdownOutput();
        socketChannel.close();
    }
}
