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

public class Main {

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            String getURL = "https://api.imgur.com/3/album/Mmbjh/images";
            HttpGet get = new HttpGet(getURL);
            get.setHeader("authorization", "Client-ID 27fcf6e736ad63d");
            HttpResponse responseGet = client.execute(get);
            System.out.println(responseGet.getStatusLine().toString());
            HttpEntity resEntityGet = responseGet.getEntity();
            StringWriter writer = new StringWriter();
            if (resEntityGet != null) {
                InputStream inputStream = resEntityGet.getContent();
                java.util.Scanner s = new Scanner(inputStream).useDelimiter("\\n");
                String imageObjects = s.next();

//                ArrayList<String> imageObjectsList = new ArrayList<>(Arrays.asList(imageObjects.split("},")));
//                for(String imageObject : imageObjectsList) {
//                    System.out.println(imageObject);
//                }
                Matcher m = Pattern.compile("i.imgur.com.{9}\\.jpg").matcher(imageObjects);
                List<String> imageURLs = new ArrayList<>();
                while(m.find()) {
                    imageURLs.add(m.group());
                }
                int i = 1;
                for(String url : imageURLs) {
                    System.out.println(i++ + url + "   " + url.substring(13));
                }

//                imageObjectsList = deleteNotNeeded(imageObjectsList);
//                int i =0;
//                for(String imageObject : imageObjectsList) {
//                    System.out.println(i++ + imageObject);
//                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        primaryStage.setHeight(700);
        primaryStage.setWidth(700);
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

        ImageView img = new ImageView("http://i.imgur.com/02qqN5T.jpg");
        img.setFitWidth(200);
        img.setFitHeight(200);
        ImageView img1 = new ImageView("http://i.imgur.com/02qqN5T.jpg");
        img1.setFitWidth(200);
        img1.setFitHeight(200);
        ImageView img2 = new ImageView("http://i.imgur.com/02qqN5T.jpg");
        img1.setFitWidth(200);
        img1.setFitHeight(200);
        ImageView img3 = new ImageView("http://i.imgur.com/02qqN5T.jpg");
        img1.setFitWidth(200);
        img1.setFitHeight(200);
        ImageView img4 = new ImageView("http://i.imgur.com/02qqN5T.jpg");
        img1.setFitWidth(200);
        img1.setFitHeight(200);
        ImageView img5 = new ImageView("http://i.imgur.com/02qqN5T.jpg");
        img1.setFitWidth(200);
        img1.setFitHeight(200);
//        root.getChildren().add(img);
        VBox vbox = new VBox();
        vbox.getChildren().add(img);
        vbox.getChildren().add(img1);
        vbox.getChildren().add(img2);
        vbox.getChildren().add(img3);
        vbox.getChildren().add(img4);
        vbox.getChildren().add(img5);
        ScrollPane scpane = new ScrollPane();
        scpane.setContent(vbox);
        root.getChildren().add(scpane);

    }
}