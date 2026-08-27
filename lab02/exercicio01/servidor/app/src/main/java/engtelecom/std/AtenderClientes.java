package engtelecom.std;
import module java.base;
import java.net.Socket;

public record AtenderClientes(Socket clientSocket) implements Runnable{


    @Override
    public void run() {
        try {
            var enderecoCliente = clientSocket.getInetAddress().getHostAddress();
            var portaCliente = clientSocket.getPort();

            System.out.printf("Cliente conectado: %s:%d%n", enderecoCliente, portaCliente);

            // Estabelecimento de fluxos de entrada e saída
            var reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8)
            );
            var writer = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8)
            );

            // Protocolo de comunicação
            String mensagem = "";

            while (!mensagem.equalsIgnoreCase("sair")) {
                mensagem = reader.readLine(); //Servidor lê a primeira mensagem encaminhada pelo cliente
                if (mensagem == null){break;}
                System.out.printf("[%s:%d] -> %s%n", enderecoCliente, portaCliente, mensagem);
                writer.write(mensagem.toUpperCase());
                writer.newLine();
                writer.flush();
            }

            System.out.printf("Cliente desconectado: %s:%d%n", enderecoCliente, portaCliente);
            reader.close();
            writer.close();

        }catch (Exception e){
            System.err.println("Error " + e);
        }
    }
}
