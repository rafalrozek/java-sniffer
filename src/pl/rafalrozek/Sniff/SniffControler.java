package pl.rafalrozek.Sniff;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SniffControler {
    @FXML
    private ListView packetsList;

    public void addPacket(String src, String dst){
        Platform.runLater(() -> {
            final ImageView selectedImage = new ImageView();
            selectedImage.setFitHeight(22);
            selectedImage.setFitWidth(22);
            Image image1 = new Image(SniffControler.class.getResourceAsStream("icons/computer.png"));
            selectedImage.setImage(image1);

            Text t = new Text(src);

            final ImageView arrow = new ImageView();
            arrow.setFitHeight(22);
            arrow.setFitWidth(22);
            Image image3 = new Image(SniffControler.class.getResourceAsStream("icons/right-arrow.png"));
            arrow.setImage(image3);

            Text t2 = new Text(dst);

            final ImageView compDest = new ImageView();
            compDest.setFitHeight(22);
            compDest.setFitWidth(22);
            Image image2 = new Image(SniffControler.class.getResourceAsStream("icons/computer.png"));
            compDest.setImage(image2);

            HBox hbox = new HBox();
            hbox.getChildren().addAll(selectedImage, t, arrow, t2, compDest);
            this.packetsList.getItems().add(hbox);
        });
    }

}
