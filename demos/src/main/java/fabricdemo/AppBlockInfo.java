package fabricdemo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;

/**
 * Created by wangguojun01 on 2018/6/25.
 */
public class AppBlockInfo {
    public static void main(String[] args) throws InvalidArgumentException, ProposalException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException, TransactionException, InterruptedException, ExecutionException, TimeoutException, IOException {
        Enrollment enrollment = ConfigHelper.getEnrollment();
        User user1 = new ClientUser("Admin", enrollment, "Org1MSP");

        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        client.setUserContext(user1);

        Channel channel = ConfigHelper.getChannel("mychannel", client);

        BlockchainInfo blockchainInfo = channel.queryBlockchainInfo();
        long height = blockchainInfo.getHeight();

        for (int i = 0; i < 5; i++) {
            BlockInfo blockInfo = channel.queryBlockByNumber(height - i - 1);
            System.out.println(blockInfo.getBlockNumber());
            System.out.println(blockInfo.getEnvelopeCount());
            System.out.println(blockInfo.getBlock().getData().getSerializedSize());
            System.out.println(blockInfo.getBlock().getData().getSerializedSize() / blockInfo.getEnvelopeCount());
            System.out.println("-----------------------------------------------------");
        }
    }

    static void out(String format, Object... args) {
        System.err.flush();
        System.out.flush();

        System.out.println(format(format, args));
        System.err.flush();
        System.out.flush();
    }
}
