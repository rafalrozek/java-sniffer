package pl.rafalrozek.Sniff;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SniffWindow {
    String deviceName;

    public SniffWindow(String deviceName){
        this.deviceName = deviceName;

        Parent root;
        try {
            //create window
            root = FXMLLoader.load(getClass().getResource("../views/sniff.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Sniffing..");
            stage.setScene(new Scene(root, 200, 200));
            stage.show();

            //thread to handle sniff loop
            SniffThread st = new SniffThread(deviceName);
            st.join();

            //on window close, break thread loop
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    System.out.println("Closing..");
                    st.pcap.breakloop();
                    st.pcap.close();
                }
            });

            st.start();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
