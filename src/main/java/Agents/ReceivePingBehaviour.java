package Agents;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReceivePingBehaviour extends Behaviour {
    private MessageTemplate mt;
    private List <String> answeredAgents;

    @Override
    public void onStart() {
        answeredAgents = new ArrayList<>();
    }

    @Override
    public void action() {
        mt = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE)) ;
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null){
                if (!answeredAgents.contains(msg.getSender().getLocalName())){
                    answeredAgents.add(msg.getSender().getLocalName());
                    ACLMessage m = new ACLMessage(ACLMessage.CONFIRM);
                    AID n = new AID(msg.getSender().getLocalName(), false);
                    m.addReceiver(n);
                    m.setContent(myAgent.getLocalName()+" received ping from "+msg.getSender().getLocalName() + " and send him pong");
                    log.info("{}", m.getContent());
                    myAgent.send(m);
                }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
