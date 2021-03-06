package app;

import app.core.AmazonNotifier;
import app.core.Costume;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class FakeApiAmazonNotifier implements AmazonNotifier {

    @Value("${amazon.endpoint}")
    String endpointUrl;

    @Override
    public void notifyCostume(Costume costume) {
        HttpClient client = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("{\"costumeId\":\"" + costume.getCostumeId() + "\"}"))
                .header("Content-Type", "application/json")
                .uri(URI.create(endpointUrl + "/costume"))
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
