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

/**
 * Created by Tomov on 4.9.2017 г..
 */
public class ImageURLDeliverer {
    public static List<String> getImageURLs (String albumHash) {
        List<String> imageURLsList = new ArrayList<>();
        try {
            HttpClient client = HttpClientBuilder.create().build();
            String getURL = "https://api.imgur.com/3/album/"+albumHash+"/images";
            HttpGet get = new HttpGet(getURL);
            get.setHeader("authorization", "Client-ID ********"); // app client id
            HttpResponse responseGet = client.execute(get);

            System.out.println(responseGet.getStatusLine().toString());

            HttpEntity resEntityGet = responseGet.getEntity();
            StringWriter writer = new StringWriter();
            if (resEntityGet != null) {
                InputStream inputStream = resEntityGet.getContent();
                java.util.Scanner scanner = new Scanner(inputStream).useDelimiter("\\n");
                String imageObjects = scanner.next();
                Matcher m = Pattern.compile("i.imgur.com.{9}\\.jpg").matcher(imageObjects);
                List<String> imageURLs = new ArrayList<>();
                while(m.find()) {
                    imageURLs.add(m.group());
                }
                int i = 0;
                for(String url : imageURLs) {
                    imageURLsList.add("https://" + url.substring(0,11) + url.substring(12));
                }
                inputStream.close();
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageURLsList;
    }
}
