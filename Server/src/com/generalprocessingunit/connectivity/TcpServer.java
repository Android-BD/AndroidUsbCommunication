package com.generalprocessingunit.connectivity;

import android.util.Log;
import com.generalprocessingunit.AndroidUsbTcpSocket.Settings;
import com.generalprocessingunit.AndroidUsbTcpSocket.UsbListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public abstract class TcpServer<T> implements UsbListener<T> {
    private Gson gson = new Gson();
    private Type msgObjType;

    public static Scanner socketIn;
    public static PrintWriter socketOut;


    
    public TcpServer(Type msgObjType) {
        this.msgObjType = msgObjType;

        new Thread(serverThread).start();
    }

    public static final String TAG = "Connection";

    ServerSocket server = null;

    TcpServer thisTcpServer = this;

    private Runnable serverThread = new Thread() {
        public void run() {
            waitForConnection();
        }

        private void waitForConnection() {
            Socket client = null;

            try {
                server = new ServerSocket(Settings.PORT);

                Log.d(TAG, "server port " + server.getLocalPort());
                Log.d(TAG, "server socket address " + server.getLocalSocketAddress());
                Log.d(TAG, "server ip " + server.getInetAddress());

                // this blocks until a connection is established
                client = server.accept();

                socketIn = new Scanner(client.getInputStream());
                socketOut = new PrintWriter(client.getOutputStream(), true);

            } catch (Exception e) {
                Log.e(TAG, "" + e);
            } finally {
                // close the server socket
                try {
                    if (server != null)
                        server.close();
                } catch (IOException ec) {
                    Log.e(TAG, "Cannot close server socket" + ec);
                }
            }

            if (client != null) {
                Log.d(TAG, "connected!");

                while (socketIn.hasNext() && client.isConnected()) {
                    String msg = socketIn.next();
                    thisTcpServer.msgReceived(gson.fromJson(msg, msgObjType));
                }

                waitForConnection();
            }
        }
    };

    public void sendMsg(Object msg) {
        if (socketOut != null) {
            socketOut.println(gson.toJson(msg));
            socketOut.flush();
        }
    }

}
