package TP4;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static byte buffer[] = new byte[1024];
    static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        System.out.println("lancement du serveur");
        while (true){
            List<DatagramPacket> list = new ArrayList<DatagramPacket>();
            DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
            datagramSocket.receive(datagramPacket);
            list.add(datagramPacket);
            int port = datagramPacket.getPort();
            for(int i=0;i<list.toArray().length;i++){
                list.get(i).getPort()!=port {
                    datagramSocket.send(datagramPacket);
                }
            }
            int taille = datagramPacket.getLength();
            String donnees = new String(datagramPacket.getData(),0,taille);
            System.out.println("Donnees reçues = "+donnees);

        }
    }
}