import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start {

    public static void main(String[] args) throws Exception {

        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;

        Server server = new Server();
        SocketConnector connector = new SocketConnector();

        // Set some timeout options to make debugging easier.
        connector.setMaxIdleTime(1000 * 60 * 60);
        connector.setSoLingerTime(-1);
        connector.setPort(port);
        server.setConnectors(new Connector[] { connector });

        WebAppContext context = new WebAppContext();
        context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        context.setServer(server);
        context.setContextPath("/");

        ProtectionDomain protectionDomain = Start.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        context.setWar(location.toExternalForm());

        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

}
