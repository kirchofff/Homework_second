package Helpers;

import Factory.Observer;
import jade.core.AID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Slf4j
public class Listener implements PacketListener {
    private Map<AID, Long> bookWithData;
//    private List <String> bookWithAgents;
//    private List <String> timeOfMessage;
    private AID aid;
    private String data;
    private Observer observer = new Observer();
    private List <String> listenersName= new ArrayList<>();
    private List <Listener> listeners;
    private static final String communicationInterface = "\\Device\\NPF_Loopback";
    private PacketHelper packetHelper = new PacketHelper(communicationInterface);
    public Listener (AID aid, List <Listener> listeners, Map<AID, Long> bookWithData){
        this.listeners = listeners;
        this.aid = aid;
        this.bookWithData = bookWithData;
    }
    @Override
    public void gotPacket(Packet packet) {
        data = packetHelper.parse(packet.getRawData());
        AID packetAgent = new AID(data.split(" ")[0]+"@"+data.split(" ")[1], true);
        data += " " + System.currentTimeMillis();
        log.debug("packet received {} with time {}", data.split(" ")[0]+" "+data.split(" ")[1]+" "+data.split(" ")[2], data.split(" ")[3]);
//        bookWithData.putIfAbsent(aid, System.currentTimeMillis());
        if (!aid.equals(packetAgent)) {
            bookWithData.put(packetAgent, System.currentTimeMillis());
        }
        for (int i = 0; i < listeners.size(); i++){
            observer.addListeners(listeners.get(i));
        }

    }
}
