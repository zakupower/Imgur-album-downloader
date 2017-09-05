import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    private VBox vBox;
    private int imageCounter = 0;
    private Button nextBtn = new Button("NEXT");
    private Button downloadBtn = new Button("DOWNLOAD");
    private Button selectAllBtn = new Button("SELECT ALL");
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private List<String> imageURLs;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Imgur Album Downloader");
        Group group = new Group();
        PannableCanvas canvas = new PannableCanvas();
        canvas.setTranslateX(100);
        canvas.setTranslateY(100);
        group.getChildren().add(canvas);

        imageURLs = ImageURLDeliverer.getImageURLs("yMVS3"); // album hash

        loadFirstImageBatch();

        addButton(nextBtn);
        addButton(downloadBtn);
        addButton(selectAllBtn);
        nextBtn.setOnAction(new NextHandler());
        downloadBtn.setOnAction(new DownloadHandler());
        selectAllBtn.setOnAction(new SelectHandler());

        canvas.getChildren().add(vBox);

        // create scene which can be dragged and zoomed
        stage.setScene(createDraggableZoomableScene(group,canvas));
        stage.show();
    }

    private Scene createDraggableZoomableScene(Group group, PannableCanvas canvas){
        Scene scene = new Scene(group, 1024, 768);
        SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        return scene;
    }

    private void addButton(Button btn){
        btn.setMinSize(1700,200);
        btn.setFont(Font.font(100));
        vBox.getChildren().add(btn);
    }

    private void loadFirstImageBatch(){
        vBox = new VBox();
        vBox.setSpacing(40);
        HBox hBox = new HBox();
        hBox.setSpacing(40);
        int j = 0;
        for(; imageCounter < imageURLs.size(); imageCounter++,j++) {
            if(j==5){
                j=0;
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.setSpacing(40);
            }
            String imageURL = imageURLs.get(imageCounter);
            System.out.println(imageCounter + " " + imageURL);
            VBox imageVBox = new VBox();
            imageVBox.setSpacing(10);
            imageVBox.setAlignment(Pos.CENTER);
            CheckBox checkBox = new CheckBox();
            checkBox.setId(imageURL.substring(20,27));
            checkBoxes.add(checkBox);
            ImageView image = new ImageView(imageURL);
            image.setFitHeight(300);
            image.setFitWidth(300);
            image.setSmooth(false);
            imageVBox.getChildren().add(image);
            imageVBox.getChildren().add(checkBox);

            hBox.getChildren().add(imageVBox);
            if(imageCounter%50==0 && imageCounter!=0) { //brake for bigger albums future next button switch
                break;
            }
        }
        //add last row if imageURls.size() not multiple of 5
        if(j!=0) {
            vBox.getChildren().add(hBox);
        }
    }

    private void saveImage(String imageHash) {
        System.out.println(imageHash);
        File outputFile = new File("images/"+imageHash+".jpg");
        BufferedImage bImage = SwingFXUtils.fromFXImage(new Image("https://i.imgur.com/"+imageHash+".jpg"), null);
        try {
            ImageIO.write(bImage, "jpg", outputFile);
            System.out.println("image saved" + outputFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class DownloadHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            for(CheckBox checkbox : checkBoxes) {
                if(checkbox.isSelected()) {
                    saveImage(checkbox.getId());
                }
            }
            System.out.println("Done downloading.");
        }
    }

    class SelectHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            for(CheckBox checkBox : checkBoxes) {
                if(!checkBox.isSelected()) checkBox.setSelected(true);
            }
        }
    }

    class NextHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            vBox.getChildren().clear();
            HBox hBox = new HBox();
            hBox.setSpacing(40);
            int j=0;
            imageCounter+=1;
            for(; imageCounter < imageURLs.size();imageCounter++,j++){
                if(j==5){
                    j=0;
                    vBox.getChildren().add(hBox);
                    hBox = new HBox();
                    hBox.setSpacing(40);
                }
                String imageURL = imageURLs.get(imageCounter);
                System.out.println(imageCounter + " " + imageURL);
                VBox imageVBox = new VBox();
                imageVBox.setSpacing(10);
                imageVBox.setAlignment(Pos.CENTER);
                CheckBox checkBox = new CheckBox();
                checkBox.setMinSize(50,50);
                checkBox.setId(imageURL.substring(20,27));
                checkBoxes.add(checkBox);
                ImageView image = new ImageView(imageURL);
                image.setFitHeight(300);
                image.setFitWidth(300);
                image.setSmooth(false);
                imageVBox.getChildren().add(image);
                imageVBox.getChildren().add(checkBox);

                hBox.getChildren().add(imageVBox);
                if(imageCounter%50==0 && imageCounter!=0) { //brake for bigger albums future next button switch
                    break;
                }
            }
            vBox.getChildren().add(nextBtn);
            vBox.getChildren().add(downloadBtn);
            vBox.getChildren().add(selectAllBtn);
        }
    }
}
