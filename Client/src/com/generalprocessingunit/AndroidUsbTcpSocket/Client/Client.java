package com.generalprocessingunit.AndroidUsbTcpSocket.Client;

import com.generalprocessingunit.AndroidUsbTcpSocket.Settings;
import com.generalprocessingunit.AndroidUsbTcpSocket.UsbListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public abstract class Client<T> implements UsbListener<T>{
    private Gson gson = new Gson();
    private Type msgObjType;

    private Socket echoSocket = null;
    private PrintStream out = null;
    private BufferedReader in = null;

    Client(Type msgObjType){
        System.out.println("Initializing Android USB Client");

        this.msgObjType = msgObjType;

        execAdb();

        try {
            echoSocket = new Socket("localhost", Settings.PORT);
            out = new PrintStream(echoSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.err.println(e);
            System.exit(1);
        }

        System.out.println("connected!!");
        new Thread(clientThread).start();
    }


    private Client self = this;

    private Runnable clientThread = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    if(in.ready()){
                        self.msgReceived(gson.fromJson(in.readLine(), msgObjType));
                    }
                } catch(Exception e) {
                    System.err.println(e);
                }
            }
        }
    };

    public void sendMsg(Object msg) {
        if (out != null) {
            out.println(gson.toJson(msg));
            out.flush();
        }
    }

    private static void execAdb() {
        try {
            String cmd = String.format(Settings.ADB_CMD, Settings.PORT, Settings.PORT);
            Process p = Runtime.getRuntime().exec(cmd);
            Scanner sc = new Scanner(p.getErrorStream());
            if (sc.hasNext()) {
                while (sc.hasNext()) System.out.println(sc.next());
                System.out.println("Cannot start the Android debug bridge");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
