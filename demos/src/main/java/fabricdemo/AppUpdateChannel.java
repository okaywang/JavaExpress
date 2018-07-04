package fabricdemo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
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
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;

/**
 * Created by wangguojun01 on 2018/6/25.
 */
public class AppUpdateChannel {
//    private static final String ORIGINAL_BATCH_TIMEOUT = "\"timeout\": \"5s\""; // Batch time out in configtx.yaml
//    private static final String UPDATED_BATCH_TIMEOUT = "\"timeout\": \"2s\"";  // What we want to change it to.


    private static final String ORIGINAL_BATCH_TIMEOUT = "\"max_message_count\": 10"; // Batch time out in configtx.yaml
    private static final String UPDATED_BATCH_TIMEOUT = "\"max_message_count\": 3";  // What we want to change it to.

    private static final String CONFIGTXLATOR_LOCATION = "http://111.230.147.33:7059";

    public static void main(String[] args) throws InvalidArgumentException, ProposalException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException, TransactionException, InterruptedException, ExecutionException, TimeoutException, IOException {
        Enrollment enrollment = ConfigHelper.getEnrollment();
        User user1 = new ClientUser("Admin", enrollment, "Org2MSP");

        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        client.setUserContext(user1);

        Channel channel = ConfigHelper.getChannel("mychannel", client);

        final byte[] channelConfigurationBytes = channel.getChannelConfigurationBytes();

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://111.230.147.33:7059" + "/protolator/decode/common.Config");
        httppost.setEntity(new ByteArrayEntity(channelConfigurationBytes));

        HttpResponse response = httpclient.execute(httppost);
        int statuscode = response.getStatusLine().getStatusCode();
        out("Got %s status for decoding current channel config bytes", statuscode);
        System.out.println(statuscode);

        String responseAsString = EntityUtils.toString(response.getEntity());
        System.out.println(responseAsString);

        String updateString = responseAsString.replace(ORIGINAL_BATCH_TIMEOUT, UPDATED_BATCH_TIMEOUT);


        httppost = new HttpPost(CONFIGTXLATOR_LOCATION + "/protolator/encode/common.Config");
        httppost.setEntity(new StringEntity(updateString));

        response = httpclient.execute(httppost);
        statuscode = response.getStatusLine().getStatusCode();
        out("Got %s status for encoding the new desired channel config bytes", statuscode);
        System.out.println(statuscode);
        byte[] newConfigBytes = EntityUtils.toByteArray(response.getEntity());

        // Now send to configtxlator multipart form post with original config bytes, updated config bytes and channel name.
        httppost = new HttpPost(CONFIGTXLATOR_LOCATION + "/configtxlator/compute/update-from-configs");

        HttpEntity multipartEntity = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("original", channelConfigurationBytes, ContentType.APPLICATION_OCTET_STREAM, "originalFakeFilename")
                .addBinaryBody("updated", newConfigBytes, ContentType.APPLICATION_OCTET_STREAM, "updatedFakeFilename")
                .addBinaryBody("channel", channel.getName().getBytes()).build();

        httppost.setEntity(multipartEntity);

        response = httpclient.execute(httppost);
        statuscode = response.getStatusLine().getStatusCode();
        out("Got %s status for updated config bytes needed for updateChannelConfiguration ", statuscode);
        System.out.println(statuscode);

        byte[] updateBytes = EntityUtils.toByteArray(response.getEntity());

        UpdateChannelConfiguration updateChannelConfiguration = new UpdateChannelConfiguration(updateBytes);


        Enrollment orderEnrollment = ConfigHelper.getOrderEnrollment();
        User ordererAdmin = new ClientUser("Admin", orderEnrollment, "OrdererMSP");

        client.setUserContext(ordererAdmin);
        channel.updateChannelConfiguration(updateChannelConfiguration, client.getUpdateChannelConfigurationSignature(updateChannelConfiguration, ordererAdmin));

    }

    static void out(String format, Object... args) {

        System.err.flush();
        System.out.flush();

        System.out.println(format(format, args));
        System.err.flush();
        System.out.flush();

    }
}
