package pl.rafalrozek.Sniff;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;

public class SniffThread extends Thread{
    String deviceName;
    public Pcap pcap;
    public SniffThread(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public void run() {
        StringBuilder errbuf = new StringBuilder(); // For any error msgs
        int snaplen = 64 * 1024;                    // Capture all packets, no trucation
        int flags = Pcap.MODE_PROMISCUOUS;          // capture all packets
        int timeout = 0;                            // 10 seconds in millis
        pcap = Pcap.openLive(deviceName, snaplen, flags, timeout, errbuf);

        if (pcap == null) {
            System.err.printf("Error while opening device for capture: ");
            return;
        }
        System.out.println("Started device " + deviceName);
        PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {
            public void nextPacket(PcapPacket packet, String user) {
                //System.out.println("NEW: "+packet.getState().toDebugString()); //debug
                Ip4 ip = new Ip4();
                byte[] sIP = new byte[4];
                byte[] dIP = new byte[4];
                String sourceIP = "";
                String destIP = "";
                if(packet.hasHeader(ip)){
                    sIP = packet.getHeader(ip).source();
                    sourceIP = org.jnetpcap.packet.format.FormatUtils.ip(sIP);
                    dIP = packet.getHeader(ip).destination();
                    destIP = org.jnetpcap.packet.format.FormatUtils.ip(dIP);
                    /*
                    TODO:
                        print out to listview?
                     */
                    System.out.println(sourceIP + "->" + destIP);

                }
            }
        };
        //0 -> infinite loop
        pcap.loop(0, jpacketHandler, "jNetPcap not rocks!");


    }
}
