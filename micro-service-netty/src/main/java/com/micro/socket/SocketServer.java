package com.micro.socket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
 

@Slf4j
@Data
public class SocketServer implements CommandLineRunner {

    @Value("${socket.port:9999}")
    private Integer port;

    public void start(Integer port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket client = serverSocket.accept();
                new HandlerThread(client);
            }
        } catch (Exception e) {
            log.info("socket服务器启动异常: " + e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        start(port);
    }

    private class HandlerThread implements Runnable {
        private Socket socket;
        BufferedReader reader;
        BufferedWriter writer;

        public HandlerThread(Socket client) {
            socket = client;
            new Thread(this).start();
        }

        public void run() {
            try {
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//读取客户端消息  
                    writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//向客户端写消息
                    String lineString="";
                    while(!(lineString=reader.readLine()).equals("exit")){
                    log.info("client" + lineString);
                    writer.write("服务器返回："+lineString+"\n");
                    writer.flush();
                }
            } catch (Exception e) {
                log.info("服务器运行异常: ", e);
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                }catch (Exception e) {
                        socket = null;
                    }
                }
            }
        }
    }
