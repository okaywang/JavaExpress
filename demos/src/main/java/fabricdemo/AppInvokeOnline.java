package fabricdemo;

import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import sun.security.util.HostnameChecker;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;

/**
 * Created by wangguojun01 on 2018/6/25.
 */
public class AppInvokeOnline {

    public static void main(String[] args) throws InvalidArgumentException, ProposalException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException, TransactionException, InterruptedException, ExecutionException, TimeoutException, URISyntaxException, IOException {
        Enrollment enrollment = ConfigHelperOnline.getEnrollment();
        User user1 = new ClientUser("User1", enrollment, "org1MSP");

        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        client.setUserContext(user1);
        Channel channel = ConfigHelperOnline.getChannel("channelx", client, enrollment);

        invokeMethod(channel, user1, client);
    }

    private static void invokeMethod(Channel channel, User user1, HFClient client) throws InvalidArgumentException, TransactionException, ProposalException, InterruptedException, ExecutionException, TimeoutException {
        ChaincodeID.Builder chaincodeIDBuilder = ChaincodeID.newBuilder().setName("c5")
                .setVersion("v1");

        ChaincodeID chaincodeID = chaincodeIDBuilder.build();

        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chaincodeID);
        transactionProposalRequest.setChaincodeLanguage(TransactionRequest.Type.GO_LANG);
        //transactionProposalRequest.setFcn("invoke");
        //transactionProposalRequest.setFcn("saveWealthAndPower");
        transactionProposalRequest.setFcn("invoke");
        //transactionProposalRequest.setProposalWaitTime(testConfig.getProposalWaitTime());
        transactionProposalRequest.setArgs("a", "b", "10");
        //String v = randomString(1 * 800);
        //transactionProposalRequest.setArgs("a", v);


        Map<String, byte[]> tm2 = ConfigHelper.getTransientMap();
        transactionProposalRequest.setTransientMap(tm2);

        System.out.println("sending transactionProposal to all peers with arguments: move(a,b,100)");


        transactionProposalRequest.setUserContext(user1);
        Collection<ProposalResponse> transactionPropResp = channel.sendTransactionProposal(transactionProposalRequest, channel.getPeers());
        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();
        HostnameChecker aa;
        for (ProposalResponse response : transactionPropResp) {
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                out("Successful transaction proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName());
                successful.add(response);
            } else {
                failed.add(response);
            }
        }
        Collection<Set<ProposalResponse>> proposalConsistencySets = SDKUtils.getProposalConsistencySets(transactionPropResp);
        if (proposalConsistencySets.size() != 1) {
            out(format("Expected only one set of consistent proposal responses but got %d", proposalConsistencySets.size()));
            //return;
        }

        out("Received %d install proposal responses. Successful+verified: %d . Failed: %d", 1, successful.size(), failed.size());

        if (failed.size() > 0) {
            return;
        }
        out("Successfully received transaction proposal responses.");

        ProposalResponse resp = successful.iterator().next();
        byte[] x = resp.getChaincodeActionResponsePayload();
        out(new String(x));

        TxReadWriteSetInfo readWriteSetInfo = resp.getChaincodeActionResponseReadWriteSetInfo();
        System.out.println(readWriteSetInfo.getNsRwsetCount());
        System.out.println(resp.getChaincodeID());

        CompletableFuture<BlockEvent.TransactionEvent> transactionEventCompletableFuture = channel.sendTransaction(successful);


        BlockEvent.TransactionEvent txEvent = transactionEventCompletableFuture.get(1000, TimeUnit.SECONDS);

        out("Finished transaction with transaction id %s", txEvent.getTransactionID());
    }

    static String randomString(final int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }

    static void out(String format, Object... args) {

        System.err.flush();
        System.out.flush();

        System.out.println(format(format, args));
        System.err.flush();
        System.out.flush();

    }
}
