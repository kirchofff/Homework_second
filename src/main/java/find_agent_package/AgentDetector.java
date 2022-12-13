package find_agent_package;

import Factory.Factory;
import Helpers.Listener;
import Helpers.PacketHelper;
import Helpers.PcapHelper;
import JSON.FromJSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

import jade.core.AID;

@Slf4j
public class AgentDetector{
//    private List <String> bookWithAgents = new ArrayList<>();
//    private List <String> bookWithTimeOfMessage = new ArrayList<>();
    private Map<AID, Long> bookWithData = new ConcurrentHashMap<>();
    private AID aid;
    private String communicationInterface;
    private int t;
    private boolean discovering = false, sending = false;
    private final PacketHelper packetHelper;
    private final PcapHelper pcapHelper;
    private byte [] packet;
    private ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);
    private ScheduledFuture <?> sendingTask, discoveringTask;
    private int port;
    private List <Listener> listeners;
    private FromJSON jsonData = new FromJSON();
    public AgentDetector (String communicationInterface, int t, int port, AID aid){
        this.communicationInterface = communicationInterface;
        this.t = t;
        this.port = port;
        this.aid = aid;
        packetHelper = new PacketHelper(communicationInterface);
        pcapHelper= new PcapHelper(communicationInterface, t);
        listeners = new ArrayList<>();
        packet = Factory.createPackage(jsonData.parse(aid).getAgentName()+" "
                +jsonData.parse(aid).getNameOfPlatform()+" "+jsonData.parse(aid).getAdressOfPlatform(),port, communicationInterface);
    }
    public void startDiscovering(){
        if (!discovering){
            discoveringTask = pcapHelper.startPacketsCapturing(port, new Listener(aid, listeners, bookWithData), ses);
        }
        discovering = true;
    }
    public void startSending(){
        if (sending){
            return;
        }
        sendingTask = ses.scheduleAtFixedRate(()-> pcapHelper.sendPacket(packet), t, t, TimeUnit.MILLISECONDS);
        sending = true;
    }
    public void deadAgentRemoving (){
        long currentTime = System.currentTimeMillis();
        List <Long> timeOfData = new ArrayList<>(bookWithData.values().stream().toList());
        List <AID> aidOfData = new ArrayList<>(bookWithData.keySet());
        for (int i = 0; i < timeOfData.size(); i++){
            if (currentTime - timeOfData.get(i) > 1200){
                bookWithData.remove(aidOfData.get(i));
            }
        }
    }
    @SneakyThrows
    public List<AID> getActiveAgents(){
        deadAgentRemoving();
        List <AID> activeAgent = new ArrayList<>(bookWithData.keySet());
        return activeAgent;
    }
    public void subscribeOnChange(Listener l){
        listeners.add(l);
    }
    public void stopSending(){
        sendingTask.cancel(true);
    }
}
