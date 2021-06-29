package como.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NioReceiveServer {
    private Charset charset = StandardCharsets.UTF_8;
    static class  Client {
        String fileName;
        long fileLength;
        long startTime;
        InetSocketAddress remoteAddreass;
        FileChannel outChannel;
    }

    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    Map<SelectableChannel, Client> clientMap = new HashMap<>();

    public void startServer() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocketChannel.configureBlocking(false);
        serverSocket.bind(new InetSocketAddress(9999));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0) {
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    Client client = new Client();
                    client.remoteAddreass = (InetSocketAddress) socketChannel.getRemoteAddress();
                    clientMap.put(socketChannel, client);
                } else if (key.isReadable()) {
                    processData(key);
                }
                it.remove();

            }
        }
    }

    private void processData(SelectionKey key) {
        Client client = clientMap.get(key.channel());
    }
}
