package org.group1.back_end.response.skills;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class RasaAPI {
    private final String rasaServerUrl;

    public RasaAPI(String rasaServerUrl) {
        this.rasaServerUrl = rasaServerUrl;
    }

    public String sendMessageToRasa(String message) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(rasaServerUrl + "/webhooks/rest/webhook");
        StringEntity params = new StringEntity("{\"message\": \"" + message + "\"}");
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        String responseJson = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        String extractedText = extractTextFromResponse(responseJson);

        return extractedText;
    }

    private String extractTextFromResponse(String responseJson) {
        try {
            JSONArray jsonArray = new JSONArray(responseJson);
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                return jsonObject.getString("text");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
