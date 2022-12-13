package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceivePingBehaviour extends Behaviour {
    private MessageTemplate mt;
    @Override
    public void action() {
        mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null){
            ACLMessage m = new ACLMessage(ACLMessage.CONFIRM);
            AID n = new AID(msg.getSender().getLocalName(), false);
            m.addReceiver(n);
            m.setContent(myAgent.getLocalName()+" received ping from "+msg.getSender().getLocalName() + " and send him pong");
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
