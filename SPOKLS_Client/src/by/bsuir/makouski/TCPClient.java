package by.bsuir.makouski;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Stanislau_Makouski on 10/12/2016.
 */
public class TCPClient {

    public static void main(String[] args) {
        TCPClient tcpClient = new TCPClient();
        System.out.println("Please specify port");
        Scanner in = new Scanner(System.in);
        int port = in.nextInt();
        System.out.println("Please specify ip");
        String ip = in.nextLine();
        tcpClient.start(port, "localhost");
    }

    private void start(int port, String ip) {
        Socket socket = null;
        try {
            System.out.println("Please specify path to file to send");
            Scanner in = new Scanner(System.in);
            String fileToSend = in.nextLine();
            File file = new File(fileToSend);
            byte[] byteArray = new byte[(int) file.length()];
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(byteArray, 0, byteArray.length);
            socket = new Socket(ip, port);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            System.out.println("Length" + byteArray.length);
            output.writeInt(byteArray.length);
            output.writeBytes(byteArray.toString());
            int nb = input.readInt();
            byte[] digit = new byte[nb];
            for (int i = 0; i < nb; i++) {
                digit[i] = input.readByte();
            }
            String st = new String(digit);
            System.out.println("Received: " + st);


        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getMessage());
        }
        finally {
            if(socket!=null){
                try{
                    socket.close();
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
