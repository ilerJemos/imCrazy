package como.demo;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.logging.Logger;

public class bufferDemo {
    private static Logger logger = Logger.getGlobal();
    static IntBuffer intBuffer = null;

    public static void printBufferInfo(Buffer intBuffer){
        logger.info("position: "+intBuffer.position());
        logger.info("limit: "+intBuffer.limit());
        logger.info("capacity: "+intBuffer.capacity());
    }
    public static void allocateTest(){
        intBuffer = IntBuffer.allocate(20);
        printBufferInfo(intBuffer);
    }
    public static void putTest(){
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        logger.info("after put...");
        printBufferInfo(intBuffer);
    }

    public static void flipTest(){
        intBuffer.flip();
        logger.info("after flip...");
        printBufferInfo(intBuffer);
    }

    public static void getTest(){
        for (int i = 0; i < 2; i++) {
            int j = intBuffer.get();
            System.out.print(j+",");
            logger.info("after get...");
            printBufferInfo(intBuffer);
        }
    }

    public static void main(String[] args) {
        allocateTest();
        putTest();
        flipTest();
        getTest();
    }
}
