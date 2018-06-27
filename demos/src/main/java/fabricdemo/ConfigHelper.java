package fabricdemo;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by wangguojun01 on 2018/6/26.
 */
public class ConfigHelper {
    public static Enrollment getEnrollment() {
        Enrollment enrollment = new Enrollment() {
            @Override
            public PrivateKey getKey() {
                String key = "-----BEGIN PRIVATE KEY-----\n" +
                        "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgC4xWMlvj+7+tvg6/\n" +
                        "HKwVbv6QpJA+ckpxMRupJbN+KSChRANCAARSSLb2xYVe6slbwvVED4ef8XpQs2Iz\n" +
                        "E7R8M0nSOiD2IfxUexTEL+fhSsXCgZKCDwdzFIRyvYOs7c7gHJJswAs2\n" +
                        "-----END PRIVATE KEY-----";
                PrivateKey privateKey = null;
                try {
                    privateKey = PrivateKeyUtil.getPrivateKeyFromBytes(key.getBytes("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                return privateKey;
            }

            @Override
            public String getCert() {
                return "-----BEGIN CERTIFICATE-----\n" +
                        "MIICKTCCAdCgAwIBAgIQHBugv3C8se5A3BjSbLDnyjAKBggqhkjOPQQDAjBzMQsw\n" +
                        "CQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNU2FuIEZy\n" +
                        "YW5jaXNjbzEZMBcGA1UEChMQb3JnMS5leGFtcGxlLmNvbTEcMBoGA1UEAxMTY2Eu\n" +
                        "b3JnMS5leGFtcGxlLmNvbTAeFw0xODA2MjExMzA2MDlaFw0yODA2MTgxMzA2MDla\n" +
                        "MGwxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQHEw1T\n" +
                        "YW4gRnJhbmNpc2NvMQ8wDQYDVQQLEwZjbGllbnQxHzAdBgNVBAMMFkFkbWluQG9y\n" +
                        "ZzEuZXhhbXBsZS5jb20wWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAARSSLb2xYVe\n" +
                        "6slbwvVED4ef8XpQs2IzE7R8M0nSOiD2IfxUexTEL+fhSsXCgZKCDwdzFIRyvYOs\n" +
                        "7c7gHJJswAs2o00wSzAOBgNVHQ8BAf8EBAMCB4AwDAYDVR0TAQH/BAIwADArBgNV\n" +
                        "HSMEJDAigCCzwoQiWEEcAZ7Gztzw/pAuKGXpxhsW1RFMgve+DqpooTAKBggqhkjO\n" +
                        "PQQDAgNHADBEAiAsiVJ6um8pFryDWFCqR/E7/iyuveRtenK1IWclZ775ngIgIHLK\n" +
                        "+u6nxKCbsEcJM0SglZFAS0z0aHGMTX1Trwqm+gA=\n" +
                        "-----END CERTIFICATE-----";
            }
        };

        return enrollment;
    }

    public static Channel getChannel(String channelName, HFClient client) throws InvalidArgumentException, TransactionException {
        //client.setUserContext(userContext);

        Channel channel =  client.newChannel(channelName);
        channel.addOrderer(client.newOrderer("orderer0","grpc://111.230.147.33:7050"));
        channel.addPeer(client.newPeer("peer0", "grpc://111.230.147.33:7051"));
        channel.addPeer(client.newPeer("peer0", "grpc://111.230.147.33:9051"));
        channel.initialize();

        return channel;
    }

    public static Map<String, byte[]>  getTransientMap(){
        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); //Just some extra junk in transient map
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
        tm2.put("result", ":)".getBytes(UTF_8));  // This should be returned see chaincode why.
        tm2.put("event", "!".getBytes(UTF_8));  //This should trigger an event see chaincode why.
        return tm2;
    }
}
