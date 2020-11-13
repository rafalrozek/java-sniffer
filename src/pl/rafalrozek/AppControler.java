package pl.rafalrozek;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import java.util.ArrayList;
import java.util.List;

public class AppControler {
    @FXML
    private ComboBox deviceList;

    @FXML
    public Button sniffBtn;

    public void fillDeviceList(){
        Platform.runLater(() ->{
            List<PcapNetworkInterface> allDev = null;
            try {
                allDev = Pcaps.findAllDevs();
            } catch (PcapNativeException e) {
                e.printStackTrace();
            }
            
            //fill combobox
            for (PcapNetworkInterface device : allDev) {
                this.deviceList.getItems().add(device.getName());
            }
        });

    }

    public String getdeviceListValue() {
        if (this.deviceList.getValue() == null) {
            return "";
        } else return this.deviceList.getValue().toString();
    }
}
