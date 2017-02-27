package example;

import org.apache.http.HttpVersion;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransportException;

public class ClientTest {
    private static BasicHttpParams params;
    private static ThreadSafeClientConnManager cm;


    public static void main(String[] args) throws TTransportException, TException {

        // Set up Thrift HTTP client connection parameters
        params = new BasicHttpParams();
        params.setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        params.setParameter("http.protocol.content-charset", "UTF-8");
        // Disable Expect-Continue
        params.setParameter("http.protocol.expect-continue", false);
        // Enable staleness check
        params.setParameter("http.connection.stalecheck", true);
        HttpConnectionParams.setSoTimeout(params, 10000); // 10 secondes
        HttpConnectionParams.setConnectionTimeout(params, 10000); // 10 secondes
        ConnManagerParams.setMaxTotalConnections(params, 20);
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(20);
        ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 8080));
        cm = new ThreadSafeClientConnManager(params, schemeRegistry);

        String servletUrl = "http://localhost:8090/first";

        THttpClient thc = new THttpClient(servletUrl, new DefaultHttpClient(cm, params));
        TProtocol loPFactory = new TCompactProtocol(thc);
        ServiceExample.Client client = new ServiceExample.Client(loPFactory);

        BeanExample bean = client.getBean(1, "string");
        //Assert.assertEquals("OK", bean.getStringObject());
        System.out.println(bean.getStringObject());
    }
}
