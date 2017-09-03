//import javafx.application.Application;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.Scene;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;
//
//import java.io.InputStream;
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
////
////public class Main extends Application {
////    public static void main(String[] args) {
////        launch(args);
////    }
////
////    @Override
////    public void start(Stage primaryStage) {
////        primaryStage.setTitle("Hello World!");
////        primaryStage.setHeight(700);
////        primaryStage.setWidth(700);
////        StackPane root = new StackPane();
////        primaryStage.setScene(new Scene(root));
////        primaryStage.show();
////        VBox vBox = new VBox();
////        HBox hBox = new HBox();
////        ArrayList<HBox> hBoxList = new ArrayList<>();
////        List<String> imageURLs = getImageURLs("Mmbjh"); // album hash
////        int j = 0;
////        for(int i = 0; i < imageURLs.size(); i++,j++) {
////           if(j==5){
////               j=0;
////               hBoxList.add(hBox);
////               hBox = new HBox();
////           }
////           System.out.println(imageURLs.get(i));
////           ImageView image = new ImageView(imageURLs.get(i));
////           image.setFitHeight(300);
////           image.setFitWidth(300);
////           image.setSmooth(true);
////           hBox.getChildren().add(image);
////        }
////        if(j!=0) {
////            hBoxList.add(hBox);
////        }
////        for(HBox someBox : hBoxList) {
////            vBox.getChildren().add(someBox);
////        }
////        ScrollPane scPane = new ScrollPane();
////        scPane.setContent(vBox);
////        root.getChildren().add(scPane);
////    }
////
////    private List<String> getImageURLs (String albumHash) {
////        List<String> imageURLsList = new ArrayList<>();
////        try {
////            HttpClient client = HttpClientBuilder.create().build();
////            String getURL = "https://api.imgur.com/3/album/Mmbjh/images";
////            HttpGet get = new HttpGet(getURL);
////            get.setHeader("authorization", "Client-ID 27fcf6e736ad63d");
////            HttpResponse responseGet = client.execute(get);
////            System.out.println(responseGet.getStatusLine().toString());
////            HttpEntity resEntityGet = responseGet.getEntity();
////            StringWriter writer = new StringWriter();
////            if (resEntityGet != null) {
////                InputStream inputStream = resEntityGet.getContent();
////                java.util.Scanner s = new Scanner(inputStream).useDelimiter("\\n");
////                String imageObjects = s.next();
////                Matcher m = Pattern.compile("i.imgur.com.{9}\\.jpg").matcher(imageObjects);
////                List<String> imageURLs = new ArrayList<>();
////                while(m.find()) {
////                    imageURLs.add(m.group());
////                }
////                for(String url : imageURLs) {
////                    imageURLsList.add("https://" + url.substring(0,11) + url.substring(12));
////                }
////
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return imageURLsList;
////    }
////}
////
//
//import javafx.application.Application;
//import javafx.beans.property.DoubleProperty;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.input.ScrollEvent;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.CornerRadii;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.Stage;
//
//class PannableCanvas extends Pane {
//
//    DoubleProperty myScale = new SimpleDoubleProperty(1.0);
//
//    public PannableCanvas() {
//        setPrefSize(600, 600);
//
//        // add scale transform
//        scaleXProperty().bind(myScale);
//        scaleYProperty().bind(myScale);
//    }
//
//
//
//    public double getScale() {
//        return myScale.get();
//    }
//
//    public void setScale( double scale) {
//        myScale.set(scale);
//    }
//
//    public void setPivot( double x, double y) {
//        setTranslateX(getTranslateX()-x);
//        setTranslateY(getTranslateY()-y);
//    }
//}
//
//
///**
// * Mouse drag context used for scene and nodes.
// */
//class DragContext {
//
//    double mouseAnchorX;
//    double mouseAnchorY;
//
//    double translateAnchorX;
//    double translateAnchorY;
//
//}
//
///**
// * Listeners for making the scene's canvas draggable and zoomable
// */
//class SceneGestures {
//
//    private static final double MAX_SCALE = 10.0d;
//    private static final double MIN_SCALE = .1d;
//
//    private DragContext sceneDragContext = new DragContext();
//
//    PannableCanvas canvas;
//
//    public SceneGestures( PannableCanvas canvas) {
//        this.canvas = canvas;
//    }
//
//    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
//        return onMousePressedEventHandler;
//    }
//
//    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
//        return onMouseDraggedEventHandler;
//    }
//
//    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
//        return onScrollEventHandler;
//    }
//
//    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
//
//        public void handle(MouseEvent event) {
//
//            // right mouse button => panning
//            if( !event.isSecondaryButtonDown())
//                return;
//
//            sceneDragContext.mouseAnchorX = event.getSceneX();
//            sceneDragContext.mouseAnchorY = event.getSceneY();
//
//            sceneDragContext.translateAnchorX = canvas.getTranslateX();
//            sceneDragContext.translateAnchorY = canvas.getTranslateY();
//
//        }
//
//    };
//
//    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
//        public void handle(MouseEvent event) {
//
//            // right mouse button => panning
//            if( !event.isSecondaryButtonDown())
//                return;
//
//            canvas.setTranslateX(sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX);
//            canvas.setTranslateY(sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY);
//
//            event.consume();
//        }
//    };
//
//    /**
//     * Mouse wheel handler: zoom to pivot point
//     */
//    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {
//
//        @Override
//        public void handle(ScrollEvent event) {
//
//            double delta = 1.2;
//
//            double scale = canvas.getScale(); // currently we only use Y, same value is used for X
//            double oldScale = scale;
//
//            if (event.getDeltaY() < 0)
//                scale /= delta;
//            else
//                scale *= delta;
//
//            scale = clamp( scale, MIN_SCALE, MAX_SCALE);
//
//            double f = (scale / oldScale)-1;
//
//            double dx = (event.getSceneX() - (canvas.getBoundsInParent().getWidth()/2 + canvas.getBoundsInParent().getMinX()));
//            double dy = (event.getSceneY() - (canvas.getBoundsInParent().getHeight()/2 + canvas.getBoundsInParent().getMinY()));
//
//            canvas.setScale( scale);
//
//            // note: pivot value must be untransformed, i. e. without scaling
//            canvas.setPivot(f*dx, f*dy);
//
//            event.consume();
//
//        }
//
//    };
//
//
//    public static double clamp( double value, double min, double max) {
//
//        if( Double.compare(value, min) < 0)
//            return min;
//
//        if( Double.compare(value, max) > 0)
//            return max;
//
//        return value;
//    }
//}
//
//
//
///**
// * An application with a zoomable and pannable canvas.
// */
//public class Main extends Application {
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) {
//
//        Group group = new Group();
//
//        // create canvas
//        PannableCanvas canvas = new PannableCanvas();
//
//        // we don't want the canvas on the top/left in this example => just
//        // translate it a bit
//        canvas.setTranslateX(100);
//        canvas.setTranslateY(100);
//
//        group.getChildren().add(canvas);
//
//                VBox vBox = new VBox();
//        HBox hBox = new HBox();
//        ArrayList<HBox> hBoxList = new ArrayList<>();
//        List<String> imageURLs = getImageURLs("Mmbjh"); // album hash
//        int j = 0;
//        for(int i = 0; i < imageURLs.size(); i++,j++) {
//           if(j==5){
//               j=0;
//               hBoxList.add(hBox);
//               hBox = new HBox();
//           }
//           System.out.println(imageURLs.get(i));
//           ImageView image = new ImageView(imageURLs.get(i));
//           image.setFitHeight(300);
//           image.setFitWidth(300);
//           image.setSmooth(true);
//           hBox.getChildren().add(image);
//        }
//        if(j!=0) {
//            hBoxList.add(hBox);
//        }
//        for(HBox someBox : hBoxList) {
//            vBox.getChildren().add(someBox);
//        }
//        ScrollPane scPane = new ScrollPane();
//        scPane.setContent(vBox);
//        canvas.getChildren().add(scPane);
//
//        // create scene which can be dragged and zoomed
//        Scene scene = new Scene(group, 1024, 768);
//
//        SceneGestures sceneGestures = new SceneGestures(canvas);
//        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
//        scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
//        scene.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
//
//        stage.setScene(scene);
//        stage.show();
//
//
//    }
//    private List<String> getImageURLs (String albumHash) {
//        List<String> imageURLsList = new ArrayList<>();
//        try {
//            HttpClient client = HttpClientBuilder.create().build();
//            String getURL = "https://api.imgur.com/3/album/Mmbjh/images";
//            HttpGet get = new HttpGet(getURL);
//            get.setHeader("authorization", "Client-ID 27fcf6e736ad63d");
//            HttpResponse responseGet = client.execute(get);
//            System.out.println(responseGet.getStatusLine().toString());
//            HttpEntity resEntityGet = responseGet.getEntity();
//            StringWriter writer = new StringWriter();
//            if (resEntityGet != null) {
//                InputStream inputStream = resEntityGet.getContent();
//                java.util.Scanner s = new Scanner(inputStream).useDelimiter("\\n");
//                String imageObjects = s.next();
//                Matcher m = Pattern.compile("i.imgur.com.{9}\\.jpg").matcher(imageObjects);
//                List<String> imageURLs = new ArrayList<>();
//                while(m.find()) {
//                    imageURLs.add(m.group());
//                }
//                for(String url : imageURLs) {
//                    imageURLsList.add("https://" + url.substring(0,11) + url.substring(12));
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return imageURLsList;
//    }
//}