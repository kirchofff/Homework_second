package Agents;

import AgentDetector.AgentDetector;
import jade.core.Agent;
import lombok.SneakyThrows;

public class StartAgent extends Agent{


    private static final String communicationInterface = "\\Device\\NPF_Loopback";
    private int port = 5000;
    private int t = 1000;
    private AgentDetector ad;
    @SneakyThrows
    @Override
    protected void setup() {
        ad = new AgentDetector(communicationInterface, t, port, getAID());
        ad.startSending();
        ad.startDiscovering();
        addBehaviour(new SendPingBehaviour(ad));
    }
    @Override
    public void doDelete() {
        ad.stopSending();
        super.doDelete();
    }
}
