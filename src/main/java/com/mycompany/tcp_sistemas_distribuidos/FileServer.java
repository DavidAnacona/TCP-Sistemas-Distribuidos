package com.mycompany.tcp_sistemas_distribuidos;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    private static final int PORT = 12345;
    private static final String SAVE_DIR = "C:/ArchivosRecibidos/";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor esperando archivos...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream dis = new DataInputStream(socket.getInputStream());
                     FileOutputStream fos = new FileOutputStream(SAVE_DIR + dis.readUTF())) {

                    String senderName = dis.readUTF();
                    String fileName = dis.readUTF();
                    String newFileName = senderName + "_" + fileName;

                    File file = new File(SAVE_DIR + newFileName);
                    fos.write(dis.readAllBytes());
                    System.out.println("Archivo " + newFileName + " recibido y guardado.");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
