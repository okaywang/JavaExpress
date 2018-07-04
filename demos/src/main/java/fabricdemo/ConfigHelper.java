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
                        "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgpnDbEvHlh2ZGvcuW\n" +
                        "+9h7RDWTKYIfyjAb6eNOYTJV6OuhRANCAASm9v5ZU9GAFyTVKGDacZl/M77nYKZS\n" +
                        "dTp4gCRT4IHXgfI2U0YZ9SjezpSYY+eEFvYEUCFTkpZHsE196ozh5sH0\n" +
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
                        "MIICKjCCAdGgAwIBAgIRALmsOVUFJuQYakEfxMR6f1MwCgYIKoZIzj0EAwIwczEL\n" +
                        "MAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDVNhbiBG\n" +
                        "cmFuY2lzY28xGTAXBgNVBAoTEG9yZzIuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2Nh\n" +
                        "Lm9yZzIuZXhhbXBsZS5jb20wHhcNMTgwNzA0MTI1NzU5WhcNMjgwNzAxMTI1NzU5\n" +
                        "WjBsMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMN\n" +
                        "U2FuIEZyYW5jaXNjbzEPMA0GA1UECxMGY2xpZW50MR8wHQYDVQQDDBZBZG1pbkBv\n" +
                        "cmcyLmV4YW1wbGUuY29tMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEpvb+WVPR\n" +
                        "gBck1Shg2nGZfzO+52CmUnU6eIAkU+CB14HyNlNGGfUo3s6UmGPnhBb2BFAhU5KW\n" +
                        "R7BNfeqM4ebB9KNNMEswDgYDVR0PAQH/BAQDAgeAMAwGA1UdEwEB/wQCMAAwKwYD\n" +
                        "VR0jBCQwIoAgz6bWq8RVtKeyDCwK9KyIJkFrTipTpH0tE4gHtf8CyOIwCgYIKoZI\n" +
                        "zj0EAwIDRwAwRAIgMVvo1wz2UtbbQJ55mMSoUFJPDEp3TMypPBRjR1Cft6cCIDe0\n" +
                        "+LqI1K93f2lm6ufxU//Wb2U7eWO4SVy+9GCgCksW\n" +
                        "-----END CERTIFICATE-----";
            }
        };

        return enrollment;
    }


    public static Enrollment getOrderEnrollment() {
        Enrollment enrollment = new Enrollment() {
            @Override
            public PrivateKey getKey() {
                String key = "-----BEGIN PRIVATE KEY-----\n" +
                        "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgcJNbPbRPPH01sbKn\n" +
                        "yVUieNmbG7+WVpBz7lviOkS8mlmhRANCAARZpuT07zjVsSor/lVl+M0AxqKrOBSx\n" +
                        "W6XmJGp5CPd5y7JFcO24PhfnzK5dF1quw0CmuBCjaHqr30Myw0isiScr\n" +
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
                        "MIICCzCCAbGgAwIBAgIRAMYT+1h9p2VUmCMEcux7CTUwCgYIKoZIzj0EAwIwaTEL\n" +
                        "MAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDVNhbiBG\n" +
                        "cmFuY2lzY28xFDASBgNVBAoTC2V4YW1wbGUuY29tMRcwFQYDVQQDEw5jYS5leGFt\n" +
                        "cGxlLmNvbTAeFw0xODA3MDQxMjU3NTlaFw0yODA3MDExMjU3NTlaMFYxCzAJBgNV\n" +
                        "BAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQHEw1TYW4gRnJhbmNp\n" +
                        "c2NvMRowGAYDVQQDDBFBZG1pbkBleGFtcGxlLmNvbTBZMBMGByqGSM49AgEGCCqG\n" +
                        "SM49AwEHA0IABFmm5PTvONWxKiv+VWX4zQDGoqs4FLFbpeYkankI93nLskVw7bg+\n" +
                        "F+fMrl0XWq7DQKa4EKNoeqvfQzLDSKyJJyujTTBLMA4GA1UdDwEB/wQEAwIHgDAM\n" +
                        "BgNVHRMBAf8EAjAAMCsGA1UdIwQkMCKAIKWE0nkwrnW02iz17VdpmCGldXNIvuOo\n" +
                        "DLm0IELAtVh/MAoGCCqGSM49BAMCA0gAMEUCIQD1h88UlVxAielxNcLz5MnAVHoo\n" +
                        "vcLI+VUD2dLLW6U9CQIgLOGjaQP2wTYhPXVTZyXHUzQGCT6+NZhxHznrcadtA9M=\n" +
                        "-----END CERTIFICATE-----";
            }
        };

        return enrollment;
    }


    public static Channel getChannel(String channelName, HFClient client) throws InvalidArgumentException, TransactionException {
        //client.setUserContext(userContext);

        Channel channel =  client.newChannel(channelName);
        channel.addOrderer(client.newOrderer("orderer0","grpc://111.230.147.33:7050"));
//        channel.addPeer(client.newPeer("peer0", "grpc://111.230.147.33:7051"));
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
