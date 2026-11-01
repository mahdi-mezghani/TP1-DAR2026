package TP4;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static TP4.Server.buffer;

public class Client {
    static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        Scanner scanner = new Scanner(System.in);
        String ch = scanner.next();
        int length = ch.length();
        byte buffer[] = ch.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buffer,length, InetAddress.getByName("localhost"),1234);
        datagramSocket.send(datagramPacket);
    }
}