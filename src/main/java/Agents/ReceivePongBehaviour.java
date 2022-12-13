package Agents;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceivePongBehaviour extends Behaviour {
    private MessageTemplate mt;
    @Override
    public void action() {
        mt = MessageTemplate.MatchPerformative(ACLMessage.CONFIRM);
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null){
            ACLMessage m = new ACLMessage(ACLMessage.REQUEST);
            AID n = new AID(msg.getSender().getLocalName(), false);
            m.addReceiver(n);
            m.setContent(myAgent.getLocalName()+" received pong and "+" send to "+msg.getSender().getLocalName()+" ping");
            log.info("{}", m.getContent());
            myAgent.send(m);
            myAgent.removeBehaviour(this);
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
