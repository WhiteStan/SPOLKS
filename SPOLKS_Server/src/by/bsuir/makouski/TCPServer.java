package by.bsuir.makouski;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Stanislau_Makouski on 10/12/2016.
 */
public class TCPServer {
    public static void main(String args[]) {
        TCPServer tcpServer = new TCPServer();
        System.out.println("Please specify port");
        Scanner in = new Scanner(System.in);
        int port = in.nextInt();
        tcpServer.start(port);
    }

    private void start(int port) {
        try {
            ServerSocket socket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = socket.accept();
                this.run(clientSocket);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void run(Socket socket) {
        DataInputStream input;
        DataOutputStream output;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            FileWriter out = new FileWriter("test.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(out);

            int nb = input.readInt();
            System.out.println("Length: " + nb);
            byte[] digit = new byte[nb];
            for (int i = 0; i < nb-1; i++) {
                digit[i] = input.readByte();
            }
            String st = new String(digit);
            bufferedWriter.append(st);
            bufferedWriter.close();
            System.out.println("received from: " +
            socket.getInetAddress() + ":" +
            socket.getPort() + "message - " + st);
            output.writeInt(st.length());
            output.writeBytes(st);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
