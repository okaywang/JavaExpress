package fabricdemo;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by wangguojun01 on 2018/6/25.
 */
public class PrivateKeyUtil {

    public PrivateKeyUtil() {
    }

    public static PrivateKey getPrivateKeyFromBytes(byte[] data) throws IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        Reader pemReader = new StringReader(new String(data));
        PEMParser pemParser = new PEMParser(pemReader);
        Throwable var4 = null;

        PrivateKeyInfo pemPair;
        try {
            pemPair = (PrivateKeyInfo) pemParser.readObject();
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            if (pemParser != null) {
                if (var4 != null) {
                    try {
                        pemParser.close();
                    } catch (Throwable var12) {
                        var4.addSuppressed(var12);
                    }
                } else {
                    pemParser.close();
                }
            }

        }

        PrivateKey privateKey = (new JcaPEMKeyConverter()).setProvider("BC").getPrivateKey(pemPair);
        return privateKey;
    }

    public static String getPEMStringFromPrivateKey(PrivateKey privateKey) throws IOException {
        StringWriter pemStrWriter = new StringWriter();
        PEMWriter pemWriter = new PEMWriter(pemStrWriter);
        pemWriter.writeObject(privateKey);
        pemWriter.close();
        return pemStrWriter.toString();
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}

