package fabricdemo;

import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.*;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hyperledger.fabric.sdk.Channel.NOfEvents.createNofEvents;
import static org.hyperledger.fabric.sdk.Channel.TransactionOptions.createTransactionOptions;

/**
 * Created by wangguojun01 on 2018/6/28.
 */
public class AppCCInstall {
    final static String CC_NAME = "mycc";
    //final static String CC_PATH = "github.com/chaincode/chaincode_example08/go";
    final static String CC_PATH = "aaabbb";

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException, TransactionException, ProposalException, IOException, ChaincodeEndorsementPolicyParseException {
        System.out.println("cc install");
        Enrollment enrollment = ConfigHelper.getEnrollment();
        User user1 = new ClientUser("Admin", enrollment, "Org2MSP");

        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        client.setUserContext(user1);

        Channel channel = ConfigHelper.getChannel("mychannel", client);

        InstallProposalRequest installProposalRequest = client.newInstallProposalRequest();
        ChaincodeID.Builder chaincodeIDBuilder = ChaincodeID.newBuilder().setName(CC_NAME)
                .setVersion("5.1")
                .setPath(CC_PATH);
        ChaincodeID chaincodeID = chaincodeIDBuilder.build();

        out("Creating install proposal");
        installProposalRequest.setChaincodeID(chaincodeID);
        //installProposalRequest.setChaincodeSourceLocation(Paths.get("D:\\workspace\\JavaExpress\\demos\\src\\main\\resources\\fabric\\gocc\\sample1").toFile());

        installProposalRequest.setChaincodeInputStream(Util.generateTarGzInputStream(
                (Paths.get("D:\\workspace\\JavaExpress\\demos\\src\\main\\resources\\fabric\\gocc\\sample1\\src\\github.com\\abcde").toFile()),
                Paths.get("src", CC_PATH).toString()));


        installProposalRequest.setChaincodeVersion("5.1");
        installProposalRequest.setChaincodeLanguage(TransactionRequest.Type.GO_LANG);
        out("Sending install proposal");

        int numInstallProposal = 0;
        Collection<Peer> peers = channel.getPeers();
        numInstallProposal = numInstallProposal + peers.size();
        Collection<ProposalResponse> responses = client.sendInstallProposal(installProposalRequest, peers);
        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();
        for (ProposalResponse response : responses) {
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                out("Successful install proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName());
                successful.add(response);
            } else {
                failed.add(response);
            }
        }

        out("Received %d install proposal responses. Successful+verified: %d . Failed: %d", numInstallProposal, successful.size(), failed.size());
        if (failed.size() > 0) {
            ProposalResponse first = failed.iterator().next();
            out("Not enough endorsers for install :" + successful.size() + ".  " + first.getMessage());
        }



        InstantiateProposalRequest instantiateProposalRequest = client.newInstantiationProposalRequest();
        //instantiateProposalRequest.setProposalWaitTime(testConfig.getProposalWaitTime());
        instantiateProposalRequest.setChaincodeID(chaincodeID);
        instantiateProposalRequest.setChaincodeLanguage(TransactionRequest.Type.GO_LANG);
        instantiateProposalRequest.setFcn("init");
        instantiateProposalRequest.setArgs(new String[]{"a", "500", "b", "600"});
        Map<String, byte[]> tm = new HashMap<>();
        tm.put("HyperLedgerFabric", "InstantiateProposalRequest:JavaSDK".getBytes(UTF_8));
        tm.put("method", "InstantiateProposalRequest".getBytes(UTF_8));
        instantiateProposalRequest.setTransientMap(tm);

        ChaincodeEndorsementPolicy chaincodeEndorsementPolicy = new ChaincodeEndorsementPolicy();
        chaincodeEndorsementPolicy.fromYamlFile(new File("D:\\workspace\\JavaExpress\\demos\\src\\main\\resources\\fabric\\chaincodeendorsementpolicy.yaml"));
        instantiateProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);


        out("Sending instantiateProposalRequest to all peers with arguments: a and b set to 100 and %s respectively", "" + (200));
        successful.clear();
        failed.clear();
        responses = channel.sendInstantiationProposal(instantiateProposalRequest);

        for (ProposalResponse response : responses) {
            if (response.isVerified() && response.getStatus() == ProposalResponse.Status.SUCCESS) {
                successful.add(response);
                out("Succesful instantiate proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName());
            } else {
                failed.add(response);
            }
        }
        out("Received %d instantiate proposal responses. Successful+verified: %d . Failed: %d", responses.size(), successful.size(), failed.size());

        if (failed.size() > 0) {
            for (ProposalResponse fail : failed) {
                out("Not enough endorsers for instantiate :" + successful.size() + "endorser failed with " + fail.getMessage() + ", on peer" + fail.getPeer());
            }
            ProposalResponse first = failed.iterator().next();
            out("Not enough endorsers for instantiate :" + successful.size() + "endorser failed with " + first.getMessage() + ". Was verified:" + first.isVerified());
        }

        ///////////////
        /// Send instantiate transaction to orderer
        out("Sending instantiateTransaction to orderer with a and b set to 100 and %s respectively", "" + (200));

        Channel.NOfEvents nOfEvents = createNofEvents();
        if (!channel.getPeers(EnumSet.of(Peer.PeerRole.EVENT_SOURCE)).isEmpty()) {
            nOfEvents.addPeers(channel.getPeers(EnumSet.of(Peer.PeerRole.EVENT_SOURCE)));
        }
        if (!channel.getEventHubs().isEmpty()) {
            nOfEvents.addEventHubs(channel.getEventHubs());
        }

        channel.sendTransaction(successful, createTransactionOptions() //Basically the default options but shows it's usage.
                .userContext(client.getUserContext()) //could be a different user context. this is the default.
                .shuffleOrders(false) // don't shuffle any orderers the default is true.
                .orderers(channel.getOrderers()) // specify the orderers we want to try this transaction. Fails once all Orderers are tried.
                .nOfEvents(nOfEvents) // The events to signal the completion of the interest in the transaction
        ).thenApply(transactionEvent -> {

            System.out.println("------------------------------------------------------------vvvv------------------");
            out(transactionEvent.isValid() + ""); // must be valid to be here.
            out(transactionEvent.getSignature() + ""); //musth have a signature.
            BlockEvent blockEvent = transactionEvent.getBlockEvent(); // This is the blockevent that has this transaction.
            out(blockEvent.getBlock().toString()); // Make sure the RAW Fabric block is returned.

            out("Finished instantiate transaction with transaction id %s", transactionEvent.getTransactionID());
            return null;
        });
    }


    static void out(String format, Object... args) {

        System.err.flush();
        System.out.flush();

        System.out.println(format(format, args));
        System.err.flush();
        System.out.flush();

    }
}
