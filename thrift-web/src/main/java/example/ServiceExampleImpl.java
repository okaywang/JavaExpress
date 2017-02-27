package example;

import java.nio.ByteBuffer;

import org.apache.thrift.TException;

public class ServiceExampleImpl implements ServiceExample.Iface {
    @Override
    public BeanExample getBean(int anArg, String anOther) throws TException {
        System.out.println(anOther);
        return new BeanExample(true, (byte) 2, (short) 3, anArg, 5, 6.0, anOther + "halou", ByteBuffer.wrap(new byte[]{3, 1, 4}));
    }
}