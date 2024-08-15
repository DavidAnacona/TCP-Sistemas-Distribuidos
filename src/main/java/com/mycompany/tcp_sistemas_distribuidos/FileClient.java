package com.mycompany.tcp_sistemas_distribuidos;

import java.io.*;
import java.net.Socket;

public class FileClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        String filePath = "C:/ProgesDoc/Logs/2024/agosto.log";
        String senderName = "Juan";

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             FileInputStream fis = new FileInputStream(filePath);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            String fileName = new File(filePath).getName();

            dos.writeUTF(senderName);
            dos.writeUTF(fileName);
            dos.write(fis.readAllBytes());
            System.out.println("Archivo enviado: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
