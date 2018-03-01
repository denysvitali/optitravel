package ch.supsi.dti.i2b.shrug.optitravel;

import ch.supsi.dti.i2b.shrug.optitravel.api.MapQuest.MapQuestWrapper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("OptiTravel");
        stage.setScene(scene);
        stage.show();

        MapQuestWrapper mapQuestWrapper = new MapQuestWrapper();
        Image mapImage = mapQuestWrapper.getMapImage(46.0037, 8.9511, 7);

        ImageView imageView = new ImageView();
        imageView.setImage(mapImage);

        root.getChildren().add(imageView);
    }
}
