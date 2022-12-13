package Factory;

import Helpers.PacketHelper;

public class Factory {
    public static byte[] createPackage (String data, int port, String communicationInterface){
        PacketHelper ph = new PacketHelper(communicationInterface);
        return (ph.collectPacket(data, port));
    }
}
