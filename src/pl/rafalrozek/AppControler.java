package pl.rafalrozek;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import java.util.ArrayList;
import java.util.List;

public class AppControler {
    @FXML
    private ComboBox deviceList;

    @FXML
    public Button sniffBtn;

    public void fillDeviceList(){
        Platform.runLater(() ->{
            List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
            StringBuilder errbuf = new StringBuilder(); // For any error msgs
            int r = Pcap.findAllDevs(alldevs, errbuf);
            if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
                System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
                return;
            }
            //fill combobox
            for (PcapIf device : alldevs) {
                this.deviceList.getItems().add(device.getName());
            }
        });

    }

    public String getdeviceListValue() {
        return this.deviceList.getValue().toString();
    }
}
