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
