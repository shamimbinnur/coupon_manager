import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
public class Test {
    public static void main(String[] args) throws IOException {
        Validation validation = new Validation();
        System.out.println(validation.isMacVerified());
        System.out.println(validation.storeMac());
        System.out.println(validation.isMacVerified());


    }
}