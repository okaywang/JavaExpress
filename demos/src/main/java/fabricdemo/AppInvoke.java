package fabricdemo;

import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by wangguojun01 on 2018/6/25.
 */
public class AppInvoke {
    public static void main(String[] args) throws InvalidArgumentException, ProposalException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException, TransactionException, InterruptedException, ExecutionException, TimeoutException {
        Enrollment enrollment = ConfigHelper.getEnrollment();
        User user1 = new ClientUser("Admin", enrollment, "Org2MSP");

        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        client.setUserContext(user1);
        Channel channel = ConfigHelper.getChannel("mychannel", client);

        for (int i = 0; i < 35; i++) {
            invokeMethod(channel, user1, client);
        }


    }

    private static void invokeMethod(Channel channel, User user1, HFClient client) throws InvalidArgumentException, TransactionException, ProposalException, InterruptedException, ExecutionException, TimeoutException {
        ChaincodeID.Builder chaincodeIDBuilder = ChaincodeID.newBuilder().setName("mycc")
                .setVersion("5.1");
        //.setPath("github.com/hyperledger/fabric/examples/chaincode/go/chaincode_example02");

        ChaincodeID chaincodeID = chaincodeIDBuilder.build();

        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chaincodeID);
        transactionProposalRequest.setChaincodeLanguage(TransactionRequest.Type.GO_LANG);
        //transactionProposalRequest.setFcn("invoke");
        //transactionProposalRequest.setFcn("saveWealthAndPower");
        transactionProposalRequest.setFcn("move");
        //transactionProposalRequest.setProposalWaitTime(testConfig.getProposalWaitTime());
        transactionProposalRequest.setArgs("a", "b", "1");


        Map<String, byte[]> tm2 = ConfigHelper.getTransientMap();
        transactionProposalRequest.setTransientMap(tm2);

        System.out.println("sending transactionProposal to all peers with arguments: move(a,b,100)");


        transactionProposalRequest.setUserContext(user1);
        Collection<ProposalResponse> transactionPropResp = channel.sendTransactionProposal(transactionProposalRequest, channel.getPeers());
        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();

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

        //BlockEvent.TransactionEvent txEvent = transactionEventCompletableFuture.get(1000, TimeUnit.SECONDS);

        //out("Finished transaction with transaction id %s", txEvent.getTransactionID());
    }

    static void out(String format, Object... args) {

        System.err.flush();
        System.out.flush();

        System.out.println(format(format, args));
        System.err.flush();
        System.out.flush();

    }
}
