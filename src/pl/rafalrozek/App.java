package pl.rafalrozek;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import pl.rafalrozek.Sniff.SniffWindow;


public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        //create window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/main.fxml"));
        Parent root = loader.load();

        AppControler ctrl = loader.getController();
        Platform.setImplicitExit(false);


        primaryStage.setTitle("Sniffer");
        primaryStage.setScene(new Scene(root, 250, 100));
        primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("hb.png")));
        primaryStage.setResizable(false);
        primaryStage.show();

        //read all devices
        ctrl.fillDeviceList();

        //on 'Sniff' button click
        ctrl.sniffBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String deviceListValue = ctrl.getdeviceListValue();
                if(deviceListValue.compareTo("") != 0)
                    new SniffWindow(deviceListValue);
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
