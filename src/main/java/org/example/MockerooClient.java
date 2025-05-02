package org.example;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockerooClient {

    String filePath = "src/main/resources/Config.yaml";

    MockerooConfig mockerooConfig = YamlLoader.loadSection(filePath, "mockaroo", MockerooConfig.class);;

    private final String apiKey = mockerooConfig.getApiKey();
    private final String url = mockerooConfig.getUrl();

    public List<Map<String, Object>> fetchMockData(int recordCount) throws Exception {
        String apiUrl = String.format("%s?key=%s&count=%d&format=json", url, apiKey, recordCount);

        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // Read response
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // Parse the JSON response using Gson
            Gson gson = new Gson();
            return gson.fromJson(response.toString(), new TypeToken<List<Map<String, Object>>>() {}.getType());
        }
    }
}
