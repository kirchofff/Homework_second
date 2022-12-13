package Agents;

import find_agent_package.AgentDetector;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import lombok.SneakyThrows;

public class StartAgent extends Agent{


    private static final String communicationInterface = "\\Device\\NPF_Loopback";
    private int port = 2228;
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
