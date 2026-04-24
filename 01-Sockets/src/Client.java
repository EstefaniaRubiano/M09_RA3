import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final int PORT = 7777;
    private static final String HOST = "localhost";
    private Socket socket;
    private PrintWriter out;

    public void connecta() throws IOException {
        socket = new Socket(HOST, PORT);
        out = new PrintWriter(socket.getOutputStream());
        System.out.println("Connectat a servidor en " + HOST + ":" + PORT);
    }

    public void envia(String missatge) {
        out.println(missatge);
        System.out.println("Enviant al servidor: " + missatge);
    }

    public void tanca() throws IOException {
        if (out != null) {
            out.close();
        }
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        System.out.println("Client tancat");
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connecta();
        client.envia("Prova d'enviament 1");
        client.envia("Prova d'enviament 2");
        client.envia("Adeu!");
        System.out.println("Prem Enter per tancar el client...");
        System.in.read();
        client.tanca();
    }
}
