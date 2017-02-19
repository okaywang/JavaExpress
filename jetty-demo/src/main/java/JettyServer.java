import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * foo...Created by wgj on 2017/2/19.
 */
public class JettyServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8030);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // http://localhost:8030/hello
        context.addServlet(new ServletHolder(new FirstServlet()), "/hello");
        // http://localhost:8030/hello/web
        context.addServlet(new ServletHolder(new FirstServlet("this is my web")), "/hello/web");

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }
}