package at.qe.skeleton.bleclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;


public class RESTClient {

    private final String url;
    private String apiKey;

    private final String ENDPOINT_UPDATE = "/api/update";
    private final String ENDPOINT_KEY = "/api/apikey";

    public RESTClient(String url, String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    public RESTClient(String url) {
        this.url = url;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private void sendMessageToBackend(String data) {

        HttpResponse<JsonNode> response;
        try {
            response = Unirest.patch(url + ENDPOINT_UPDATE)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .body(data).asJson();
            System.out.println(response.getStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getApiKeyFromBackend(String data) {

        HttpResponse<String> response;
        try {
            response = Unirest.get(url + ENDPOINT_KEY)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .basicAuth("admin", "passwd")
                    .queryString("ipAddress", data).asString();

            System.out.println(response.getStatus());
            return response.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public void updateTimeflip(PiRequest data) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mmX").create();
        String json = gson.toJson(data);
        sendMessageToBackend(json);
    }

    public String getApiKey(String ipAddress) {
        return getApiKeyFromBackend(ipAddress);
    }
}
