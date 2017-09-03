import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main extends Application {
    public VBox vBox;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Imgur Album Downloader");
        Group group = new Group();
        PannableCanvas canvas = new PannableCanvas();

        // we don't want the canvas on the top/left in this example => just
        // translate it a bit
        canvas.setTranslateX(100);
        canvas.setTranslateY(100);
        group.getChildren().add(canvas);

        vBox = new VBox();
        vBox.setSpacing(40);
        HBox hBox = new HBox();
        hBox.setSpacing(40);

        List<String> imageURLs = ImageURLDeliverer.getImageURLs("qr7io"); // album hash

        int j = 0;
        for(int i = 0; i < imageURLs.size(); i++,j++) {
            if(j==5){
                j=0;
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setSpacing(40);
            }
            System.out.println(i + " " + imageURLs.get(i));
            VBox imageVBox = new VBox();
            imageVBox.setSpacing(10);
            imageVBox.setAlignment(Pos.CENTER);
            CheckBox checkBox = new CheckBox();
            checkBox.setId(imageURLs.get(i).substring(21,27));

            ImageView image = new ImageView(imageURLs.get(i));
            image.setFitHeight(300);
            image.setFitWidth(300);
            image.setSmooth(false);
            imageVBox.getChildren().add(image);
            imageVBox.getChildren().add(checkBox);

            hBox.getChildren().add(imageVBox);
//            if(i==50) { brake for bigger albums future next button switch
//                break;
//            }
        }

        //add last row if imageURls.size() not multiple of 5
        if(j!=0) {
            vBox.getChildren().add(hBox);
        }
        // Button for more pages - to try and save heap space
        Button btn = new Button("NEXT");
        btn.setMinSize(1700,200);
        btn.setFont(Font.font(100));

        vBox.getChildren().add(btn);

//        ScrollPane scPane = new ScrollPane();
//        scPane.setContent(vBox);
        canvas.getChildren().add(vBox);

        // create scene which can be dragged and zoomed
        Scene scene = new Scene(group, 1024, 768);

        SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        stage.setScene(scene);
        stage.show();


    }
}