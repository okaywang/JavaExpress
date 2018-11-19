package fabricdemo;

import org.bouncycastle.openssl.PEMWriter;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by wangguojun01 on 2018/6/26.
 */
public class ConfigHelperOnline {

    private static long appId = 90095;
    private static String orgName = "org1";
    private static String appName = "wjnetwell";
    private static String domain = orgName + "-" + appName;
    private static String user = "User1";
    private static String ordererHostname = "orderer.wjnetwell";
    private static String ordererGrpcUrl = "grpcs://10.132.22.9:36444";


    private static String skName = "bc6f6f680d1b112503154671773ffb092695e54c8bcb02e5f219308952a158c8_sk";

    public static Enrollment getEnrollment() {
        Enrollment enrollment = new Enrollment() {
            @Override
            public PrivateKey getKey() {
//                String path = "";

//                String key = "-----BEGIN PRIVATE KEY-----\n" +
//                        "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgyEHu6B4/pH6liM3k\n" +
//                        "+Rn7rAvJNG1BNsTsVMWd7i621cmhRANCAARgDdoKuBV2PnsVGlI9JfdSLNBzFgc4\n" +
//                        "sdn9HgVRGDpM8OtmA9aEc1r3RSxjOjf1mq3OvIRr5hkxnHqI2O670U5W\n" +
//                        "-----END PRIVATE KEY-----";
                PrivateKey privateKey = null;
                try {
                    String path = "/" + String.valueOf(appId) + "/crypto-config/peerOrganizations/" + domain + "/users/" + user + "@" + domain + "/msp/keystore/" + skName;
                    System.out.println(path);
                    //byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getResource("/132/crypto-config/peerOrganizations/org1-wangjun1/users/User1@org1-wangjun1/msp/keystore/f6983b5d2768a4e25914d49c21c6fee7dd8af4e6af59892cca8faec04ec4282c_sk").toURI()));
                    byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getResource(path).toURI()));
                    //byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getResource("/132/crypto-config/peerOrganizations/org1-wangjun1/users/Admin@org1.example.com/msp/keystore/15a9e7b7af2f72e58af330d94d7fdefc9e0b8c2e2f5f9a557fdc9a9cb3abeff9_sk").toURI()));
                    privateKey = PrivateKeyUtil.getPrivateKeyFromBytes(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                return privateKey;
            }

            //github.com/chaincode/chaincode_example02/go
            @Override
            public String getCert() {
//                return "-----BEGIN CERTIFICATE-----\n" +
//                        "MIICBTCCAaugAwIBAgIRANl9xGNWiA5qe1dZT3apzEYwCgYIKoZIzj0EAwIwZTEL\n" +
//                        "MAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDVNhbiBG\n" +
//                        "cmFuY2lzY28xEjAQBgNVBAoTCXRlc3QtYWFhMTEVMBMGA1UEAxMMY2EudGVzdC1h\n" +
//                        "YWExMB4XDTE4MDkxMDAzNDQwNVoXDTI4MDkwNzAzNDQwNVowVDELMAkGA1UEBhMC\n" +
//                        "VVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDVNhbiBGcmFuY2lzY28x\n" +
//                        "GDAWBgNVBAMMD0FkbWluQHRlc3QtYWFhMTBZMBMGByqGSM49AgEGCCqGSM49AwEH\n" +
//                        "A0IABGAN2gq4FXY+exUaUj0l91Is0HMWBzix2f0eBVEYOkzw62YD1oRzWvdFLGM6\n" +
//                        "N/Warc68hGvmGTGceojY7rvRTlajTTBLMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMB\n" +
//                        "Af8EAjAAMCsGA1UdIwQkMCKAICF3wbnlFRtKoyXfY9QpsqDOiwqPERjwt6wMrLYO\n" +
//                        "kbL2MAoGCCqGSM49BAMCA0gAMEUCIQDjL+ZWeOx+VfWrX8u/5boXBot9520gXN3V\n" +
//                        "+x+0b4KUDwIgKHLHfs53BuLxePpij/f9+DJMZBWsI9qF14MtM2MtHxU=\n" +
//                        "-----END CERTIFICATE-----";

                try {
                    String path = "/" + appId + "/crypto-config/peerOrganizations/" + domain + "/users/" + user + "@" + domain + "/msp/signcerts/" + user + "@" + domain + "-cert.pem";
                    System.out.println(path);
                    //byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getResource("/132/crypto-config/peerOrganizations/org1-wangjun1/users/User1@org1-wangjun1/msp/signcerts/User1@org1-wangjun1-cert.pem").toURI()));
                    byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getResource(path).toURI()));
                    //byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getResource("/fabric/crypto-config/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/signcerts/Admin@org2.example.com-cert.pem").toURI()));
                    return new String(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                return "";
            }
        };

        return enrollment;
    }


    public static Enrollment getOrderEnrollment() {
        Enrollment enrollment = new Enrollment() {
            @Override
            public PrivateKey getKey() {
                String key = "-----BEGIN PRIVATE KEY-----\n" +
                        "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgdSxBMRxtitNqlE1G\n" +
                        "YVyq23kxjKDeyensOZfUbaQN4+ehRANCAAQ4HBp17R6TDDxU50zFcimtdeaLc85I\n" +
                        "8pEvpslg4O1XIh7lD5ZYP9oSV8rR4el+JnkX0qlOpdfxjomc0p9i56Wt\n" +
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

            //github.com/chaincode/chaincode_example02/go
            @Override
            public String getCert() {
                return "-----BEGIN CERTIFICATE-----\n" +
                        "MIICCjCCAbGgAwIBAgIRANQQdn/oVIkBi2KHk8ENNg0wCgYIKoZIzj0EAwIwaTEL\n" +
                        "MAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDVNhbiBG\n" +
                        "cmFuY2lzY28xFDASBgNVBAoTC2V4YW1wbGUuY29tMRcwFQYDVQQDEw5jYS5leGFt\n" +
                        "cGxlLmNvbTAeFw0xODA3MDYwNDA1NTlaFw0yODA3MDMwNDA1NTlaMFYxCzAJBgNV\n" +
                        "BAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQHEw1TYW4gRnJhbmNp\n" +
                        "c2NvMRowGAYDVQQDDBFBZG1pbkBleGFtcGxlLmNvbTBZMBMGByqGSM49AgEGCCqG\n" +
                        "SM49AwEHA0IABDgcGnXtHpMMPFTnTMVyKa115otzzkjykS+myWDg7VciHuUPllg/\n" +
                        "2hJXytHh6X4meRfSqU6l1/GOiZzSn2Lnpa2jTTBLMA4GA1UdDwEB/wQEAwIHgDAM\n" +
                        "BgNVHRMBAf8EAjAAMCsGA1UdIwQkMCKAIOKxRrVpspqEqis64y9HVl/+fA+usAtV\n" +
                        "O3/vqMe6Vdi5MAoGCCqGSM49BAMCA0cAMEQCID82T8AB/3/++e/dEqPNihEfC7rg\n" +
                        "PDzgGQ2XhtvGyoAfAiB3CliAJJwH4DYHmHXa4+mAYbgqXQbmfkUfYUlx2StMaQ==\n" +
                        "-----END CERTIFICATE-----";
            }
        };

        return enrollment;
    }


    public static Channel getChannel(String channelName, HFClient client, Enrollment enrollment) throws InvalidArgumentException, TransactionException {
        //client.setUserContext(userContext);

        Channel channel = client.newChannel(channelName);
        Properties orderPeerProperties = new Properties();
        try {
            orderPeerProperties.put("pemBytes", Files.readAllBytes(Paths.get("D:\\Projects\\JavaExpress\\demos\\src\\main\\resources\\90095\\crypto-config\\ordererOrganizations\\wjnetwell\\orderers\\orderer.wjnetwell\\tls\\server.crt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        orderPeerProperties.setProperty("hostnameOverride", ordererHostname);
        orderPeerProperties.setProperty("sslProvider", "openSSL");
        orderPeerProperties.setProperty("negotiationType", "TLS");

        channel.addOrderer(client.newOrderer(ordererHostname, ordererGrpcUrl, orderPeerProperties));

        Properties peerProperties = new Properties();
        try {
            peerProperties.put("pemBytes", Files.readAllBytes(Paths.get("D:\\Projects\\JavaExpress\\demos\\src\\main\\resources\\90095\\crypto-config\\peerOrganizations\\org1-wjnetwell\\peers\\peer0.org1-wjnetwell\\tls\\server.crt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        peerProperties.setProperty("hostnameOverride", "peer0." + domain);
        peerProperties.setProperty("sslProvider", "openSSL");
        peerProperties.setProperty("negotiationType", "TLS");


//        try {
//            peerProperties.put("clientKeyBytes", getPEMStringFromPrivateKey(enrollment.getKey()).getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        peerProperties.put("clientCertBytes", enrollment.getCert().getBytes());
        //peerProperties.put("peer0.test-aaa1", tlsProperties);
        peerProperties.put("grpc.NettyChannelBuilderOption.maxInboundMessageSize", 9000000);
        channel.addPeer(client.newPeer("peer0." + domain, "grpcs://10.132.22.9:31087", peerProperties));
        //channel.addPeer(client.newPeer("peer0", "grpc://111.230.147.33:9051"));
        channel.initialize();

        return channel;
    }

    static String getPEMStringFromPrivateKey(PrivateKey privateKey) throws IOException {
        StringWriter pemStrWriter = new StringWriter();
        PEMWriter pemWriter = new PEMWriter(pemStrWriter);

        pemWriter.writeObject(privateKey);

        pemWriter.close();

        return pemStrWriter.toString();
    }

    public static Map<String, byte[]> getTransientMap() {
        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); //Just some extra junk in transient map
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
        tm2.put("result", ":)".getBytes(UTF_8));  // This should be returned see chaincode why.
        tm2.put("event", "!".getBytes(UTF_8));  //This should trigger an event see chaincode why.
        return tm2;
    }
}
