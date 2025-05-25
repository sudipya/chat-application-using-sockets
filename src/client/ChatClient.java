
package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        new ReadThread(socket).start();
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String message = scanner.nextLine();
            out.println(message);
        }
    }

    static class ReadThread extends Thread {
        private BufferedReader in;

        public ReadThread(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println("Received: " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
