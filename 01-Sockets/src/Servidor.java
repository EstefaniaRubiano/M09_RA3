import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final int PORT = 7777;
    private static final String HOST = "localhost";
    private ServerSocket srvSocket;
    private Socket clientSocket;

    public void connecta() throws IOException {
        srvSocket = new ServerSocket(PORT);
        System.out.println("Servidor en marxa a " + HOST + ":" + PORT);
        System.out.println("Esperant connexions a " + HOST + ":" + PORT);
        clientSocket = srvSocket.accept();
        System.out.println("Client connectat: " + clientSocket.getInetAddress());
    }

    public void repDades() throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
        )) {
            String linia;
            while ((linia = br.readLine()) != null) {
                System.out.println("Rebut: " + linia);
            }
        }
    }

    public void tanca() throws IOException {
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
        }
        if (srvSocket != null && !srvSocket.isClosed()) {
            srvSocket.close();
        }
        System.out.println("Servidor tancat.");
    }

    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        servidor.connecta();
        servidor.repDades();
        servidor.tanca();
    }
}
