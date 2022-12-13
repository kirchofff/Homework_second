package Agents;


import AgentDetector.AgentDetector;
import jade.core.AID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


class Tests {
    private static final String communicationInterface = "\\Device\\NPF_Loopback";
    private int port = 5000;
    private int t = 1000;
    private JadeStartKit jadeStartKit = new JadeStartKit();
    @SneakyThrows
    @BeforeEach
    void startJade (){
        jadeStartKit.startJade();
        Thread.sleep(5000);
    }
    @SneakyThrows
    @Test
    void twoAgentDetector (){
        EmptyBeh emptyBeh = new EmptyBeh();
        AID agent1 = new AID(jadeStartKit.createAgent("Agent1", emptyBeh),true);
        AID agent2 = new AID(jadeStartKit.createAgent("Agent2", emptyBeh), true);
        AgentDetector firstAgentDetector = new AgentDetector(communicationInterface, t, port, agent1);
        AgentDetector secondAgentDetector = new AgentDetector(communicationInterface, t, port, agent2);
        firstAgentDetector.startSending();
        secondAgentDetector.startSending();
        firstAgentDetector.startDiscovering();
        secondAgentDetector.startDiscovering();
        Thread.sleep(3000);
        assertEquals(1, firstAgentDetector.getActiveAgents().size());
        firstAgentDetector.stopSending();
        secondAgentDetector.stopSending();
    }
    @SneakyThrows
    @Test
    void removeOneAgentDetector (){
        EmptyBeh emptyBeh = new EmptyBeh();
        AID agent1 = new AID(jadeStartKit.createAgent("Agent1", emptyBeh),true);
        AID agent2 = new AID(jadeStartKit.createAgent("Agent2", emptyBeh), true);
        AgentDetector firstAgentDetector = new AgentDetector(communicationInterface, t, port, agent1);
        AgentDetector secondAgentDetector = new AgentDetector(communicationInterface, t, port, agent2);
        firstAgentDetector.startSending();
        secondAgentDetector.startSending();
        firstAgentDetector.startDiscovering();
        secondAgentDetector.startDiscovering();
        Thread.sleep(3000);
        secondAgentDetector.stopSending();
        Thread.sleep(3000);
        System.out.println(firstAgentDetector.getActiveAgents().toString());
        assertEquals(0, firstAgentDetector.getActiveAgents().size());
        assertEquals(1, secondAgentDetector.getActiveAgents().size());
        firstAgentDetector.stopSending();
        secondAgentDetector.stopSending();
    }
    @SneakyThrows
    @Test
    void fourthAgentDetector (){
        EmptyBeh emptyBeh = new EmptyBeh();
        AID agent1 = new AID(jadeStartKit.createAgent("Agent1", emptyBeh),true);
        AID agent2 = new AID(jadeStartKit.createAgent("Agent2", emptyBeh), true);
        AID agent3 = new AID(jadeStartKit.createAgent("Agent3", emptyBeh),true);
        AID agent4 = new AID(jadeStartKit.createAgent("Agent4", emptyBeh), true);
        AgentDetector firstAgentDetector = new AgentDetector(communicationInterface, t, port, agent1);
        AgentDetector secondAgentDetector = new AgentDetector(communicationInterface, t, port, agent2);
        AgentDetector thirdAgentDetector = new AgentDetector(communicationInterface, t, port, agent3);
        AgentDetector fourthAgentDetector = new AgentDetector(communicationInterface, t, port, agent4);
        firstAgentDetector.startSending();
        secondAgentDetector.startSending();
        thirdAgentDetector.startSending();
        fourthAgentDetector.startSending();
        firstAgentDetector.startDiscovering();
        secondAgentDetector.startDiscovering();
        thirdAgentDetector.startDiscovering();
        fourthAgentDetector.startDiscovering();
        Thread.sleep(3000);
        assertEquals(3, firstAgentDetector.getActiveAgents().size());
        assertEquals(3, secondAgentDetector.getActiveAgents().size());
        assertEquals(3, thirdAgentDetector.getActiveAgents().size());
        assertEquals(3, fourthAgentDetector.getActiveAgents().size());
        firstAgentDetector.stopSending();
        secondAgentDetector.stopSending();
        thirdAgentDetector.stopSending();
        fourthAgentDetector.stopSending();
    }
}