package com.AutoTrading.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class AutoTradingServer implements CommandLineRunner {

    @Autowired
    private AutoTrading_Classify autoTradingClassify;

    @Override
    public void run(String... args) throws Exception { start(); }

    public static void main(String[] args) { SpringApplication.run(AutoTradingServer.class, args); }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(7777);
            while (true) {
                System.out.println("[클라이언트 연결대기중]");
                socket = serverSocket.accept();
                ReceiveThread receiveThread = new ReceiveThread(socket,autoTradingClassify);
                receiveThread.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    System.out.println("[서버종료]");
                } catch (IOException e) {
                    System.out.println("[서버소켓통신에러]");
                }
            }
        }
    }


}
