package pl.rafalrozek.Sniff;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.pcap4j.core.NotOpenException;



public class SniffWindow {
    String deviceName;

    public SniffWindow(String deviceName){
        this.deviceName = deviceName;

        Parent root;
        try {
            //create window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/sniff.fxml"));
            root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Sniffing " + deviceName);
            stage.setScene(new Scene(root, 600, 400));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../hb.png")));
            stage.show();

            SniffControler ctrl = loader.getController();


            //thread to handle sniff loop
            SniffThread st = new SniffThread(deviceName, ctrl);
            st.join();

            //on window close, break thread loop
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    System.out.println("Closing " + deviceName);
                    try {
                        st.handle.breakLoop();

                    } catch (NotOpenException e) {
                        e.printStackTrace();
                    }

                    st.handle.close();
                    st.interrupt();

                }
            });

            st.start();



        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
