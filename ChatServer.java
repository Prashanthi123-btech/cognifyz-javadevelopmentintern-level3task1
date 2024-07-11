import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try 
{
            serverSocket = new ServerSocket(4444); // Port number 4444
            System.out.println("Server started. Waiting for clients to connect...");

            while (true) {
                clientSocket = serverSocket.accept(); 
                System.out.println("Client connected: " + clientSocket);

                
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } 
catch (IOException e)
 {
            e.printStackTrace();
        } 
finally
 {
            try 
{
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String inputLine;

        try {
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received message from client: " + inputLine);
                // Echo message back to the client
                out.println("Server echo: " + inputLine);

                
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
