package pl.rafalrozek.Sniff;


import org.pcap4j.core.*;
import org.pcap4j.packet.IpV4Packet;

import java.net.Inet4Address;

public class SniffThread extends Thread{
    String deviceName;
    SniffControler ctrl;
    public PcapHandle handle;

    public SniffThread(String deviceName, SniffControler ctrl) {
        this.deviceName = deviceName;
        this.ctrl = ctrl;
    }

    @Override
    public void run() {
        PcapNetworkInterface nif = null;
        try {
            nif = Pcaps.getDevByName(deviceName);

        int snapLen = 65536;
        PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
        int timeout = 100;
        handle = nif.openLive(snapLen, mode, timeout);


        handle.loop(0, new PacketListener() {
            @Override
            public void gotPacket(PcapPacket packet) {
                IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
                if(ipV4Packet != null){
                    Inet4Address srcAddr = ipV4Packet.getHeader().getSrcAddr();
                    Inet4Address destAddr = ipV4Packet.getHeader().getDstAddr();

                    ctrl.addPacket(srcAddr.toString(), destAddr.toString());
                }

            }
        });
        handle.close();

        } catch (InterruptedException ex) {
            //everything is ok..
        }
        catch (Exception ex){
            ex.printStackTrace();
        }



    }
}
