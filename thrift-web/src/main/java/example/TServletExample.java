package example;


import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServlet;

public class TServletExample extends TServlet {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public TServletExample() {
        super(new ServiceExample.Processor(new ServiceExampleImpl()), new TCompactProtocol.Factory());
    }
}
