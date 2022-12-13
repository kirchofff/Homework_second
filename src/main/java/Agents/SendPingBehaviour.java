package Agents;

import AgentDetector.AgentDetector;
import jade.core.AID;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SendPingBehaviour extends ParallelBehaviour {
    private AgentDetector ad;
    public SendPingBehaviour(AgentDetector ad){
        this.ad = ad;
        addSubBehaviour(new TickerBehaviour(myAgent, 5000) {
            @Override
            protected void onTick() {
                List <AID> receivers = new ArrayList<>(ad.getActiveAgents());
                for (int i = 0; i < receivers.size();i++) {
                    ACLMessage m = new ACLMessage(ACLMessage.REQUEST);
                    AID n = new AID(receivers.get(i).getLocalName(), false);
                    m.addReceiver(n);
                    m.setContent(myAgent.getLocalName()+" wake and "+" send to "+receivers.get(i).getLocalName()+" ping");
                    log.info("{}", m.getContent());
                    myAgent.send(m);
                    myAgent.addBehaviour(new ReceivePingBehaviour());
                    myAgent.addBehaviour(new ReceivePongBehaviour());
                }
            }
        });
    }
}
