
package server;
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {
    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started...");
        while (true) {
            Socket socket = serverSocket.accept();
            clients.add(socket);
            new ClientHandler(socket).start();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        public void run() {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println("Received: " + msg);
                    for (Socket client : clients) {
                        if (client != socket) {
                            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                            writer.println(msg);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
