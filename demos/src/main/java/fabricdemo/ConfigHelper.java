package fabricdemo;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import javax.annotation.Resources;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                String path = "";

                String key = "-----BEGIN PRIVATE KEY-----\n" +
                        "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQg7QJe/7CDxJGUzzqi\n" +
                        "2DiRuXPe7xqVMyWoKXZh2yM0PG+hRANCAAQvxXsZY0/Ma/3/2B9MHp5kI8i2pttD\n" +
                        "YIGtqpz0/FctkCowYoag1f4a1jasitYiswZ0DFofugArwc7xFJZcJkVZ\n" +
                        "-----END PRIVATE KEY-----";
                PrivateKey privateKey = null;
                try {
                    //byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getResource("/fabric/crypto-config/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/keystore/d038d0fc224ed43a8f4668c3f466534aeb599d66d37ef930ff9096339294f4c8_sk").toURI()));
                    byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getResource("/fabric/crypto-config/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore/15a9e7b7af2f72e58af330d94d7fdefc9e0b8c2e2f5f9a557fdc9a9cb3abeff9_sk").toURI()));
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
//                        "MIICKzCCAdGgAwIBAgIRAMzX5KBWGpcTXTK4NRAhQHswCgYIKoZIzj0EAwIwczEL\n" +
//                        "MAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDVNhbiBG\n" +
//                        "cmFuY2lzY28xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2Nh\n" +
//                        "Lm9yZzEuZXhhbXBsZS5jb20wHhcNMTgwNzA2MDQwNTU5WhcNMjgwNzAzMDQwNTU5\n" +
//                        "WjBsMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMN\n" +
//                        "U2FuIEZyYW5jaXNjbzEPMA0GA1UECxMGY2xpZW50MR8wHQYDVQQDDBZBZG1pbkBv\n" +
//                        "cmcxLmV4YW1wbGUuY29tMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEcY5Dw1YP\n" +
//                        "8d/e+0LFwqMNGrt/pouDGJ+FX42up68NlPDASAcH/YlM1m6m1HkmtNZ1Gv4hzXLe\n" +
//                        "MjEswYZG2NCSOqNNMEswDgYDVR0PAQH/BAQDAgeAMAwGA1UdEwEB/wQCMAAwKwYD\n" +
//                        "VR0jBCQwIoAgtX71wHITiV0qAzR3JPH1C9Gvg0TZ9C2L0IYuHXk0V/IwCgYIKoZI\n" +
//                        "zj0EAwIDSAAwRQIhAOONG0ZFtzxM7g3cyiWpJvrqHWX7462KaGI5YjI7LNVnAiBz\n" +
//                        "ZTMy93sjJlZmOChS/ZUe+FQ43uwLVLINHrRj5drBFQ==\n" +
//                        "-----END CERTIFICATE-----";

                try {
                    byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getResource("/fabric/crypto-config/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/signcerts/Admin@org1.example.com-cert.pem").toURI()));
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


    public static Channel getChannel(String channelName, HFClient client) throws InvalidArgumentException, TransactionException {
        //client.setUserContext(userContext);

        Channel channel =  client.newChannel(channelName);
        channel.addOrderer(client.newOrderer("orderer0","grpc://111.230.147.33:7050"));
        channel.addPeer(client.newPeer("peer0", "grpc://111.230.147.33:7051"));
        //channel.addPeer(client.newPeer("peer0", "grpc://111.230.147.33:9051"));
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
