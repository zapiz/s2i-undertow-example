import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bild {
    
    static final int responseCode_OK = 200;

    public static void main(String[] args) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
            httpServer.createContext("/", new MyHttpHandler());
            httpServer.createContext("/image", new GetHttpHandler());
            httpServer.setExecutor(null);
            httpServer.start();
        } catch (IOException ex) {
            
        }
    }
    
    static class MyHttpHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange he) throws IOException {
            
            String response = "Hello everybody out there :)";
            he.sendResponseHeaders(responseCode_OK, response.length());
            
            OutputStream outputStream = he.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
    
    static class GetHttpHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange he) throws IOException {

            Headers headers = he.getResponseHeaders();
            headers.add("Content-Type", "image/jpeg");
            
            File file = new File ("cool.jpg");
            byte[] bytes  = new byte [(int)file.length()];
            System.out.println(file.getAbsolutePath());
            System.out.println("length:" + file.length());
            
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedInputStream.read(bytes, 0, bytes.length);

            he.sendResponseHeaders(responseCode_OK, file.length());
            OutputStream outputStream = he.getResponseBody();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.close();
        }
    }
    
}

