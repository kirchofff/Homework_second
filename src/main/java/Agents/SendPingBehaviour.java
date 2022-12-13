package Agents;

import AgentDetector.AgentDetector;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SendPingBehaviour extends ParallelBehaviour {
    private AgentDetector ad;
    public SendPingBehaviour(Agent myAgent, AgentDetector ad){
        this.ad = ad;
        this.myAgent = myAgent;
        addSubBehaviour(new TickerBehaviour(myAgent, 6000) {
            @Override
            @SneakyThrows
            protected void onTick() {
                myAgent.removeBehaviour(new ReceivePingBehaviour());
                myAgent.removeBehaviour(new ReceivePongBehaviour());
                myAgent.addBehaviour(new ReceivePingBehaviour());
                myAgent.addBehaviour(new ReceivePongBehaviour());
                List <AID> receivers = new ArrayList<>(ad.getActiveAgents());
                for (int i = 0; i < receivers.size();i++) {
                    ACLMessage m = new ACLMessage(ACLMessage.PROPAGATE);
                    AID n = new AID(receivers.get(i).getLocalName(), false);
                    m.addReceiver(n);
                    m.setContent(myAgent.getLocalName()+" wake and "+" send to "+receivers.get(i).getLocalName()+" ping");
                    log.info("{}", m.getContent());
                    myAgent.send(m);
                    Thread.sleep(500);
                }
            }
        });
    }
}
