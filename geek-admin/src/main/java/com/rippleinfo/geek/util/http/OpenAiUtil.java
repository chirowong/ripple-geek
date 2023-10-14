package com.rippleinfo.geek.util.http;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.rippleinfo.geek.model.AiResponse;
import com.rippleinfo.geek.model.CompletionRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenAiUtil {

    public static String getTextByAi(String url,CompletionRequest completionRequest){
        completionRequest.setModel(completionRequest.getModel());
        completionRequest.setMessages(completionRequest.getMessages());
        // Convert the CompletionRequest to a JSON string
        String requestBody = convertCompletionRequestToJson(completionRequest);
        String responseBody = HttpUtil.post(url, requestBody);
        Gson gson = new Gson();
        AiResponse aiResponse = gson.fromJson(responseBody,AiResponse.class);
        return aiResponse.getChoices().get(0).getMessage().getContent();
        /*HttpClient httpClient = HttpClients.createDefault();

        try {

            // Create a POST request
            HttpPost httpPost = new HttpPost(url);
            // Create an instance of CompletionRequest and set its attributes
            completionRequest.setModel("baichuan");
            Messages messages = new Messages();
            messages.setRole("user");
            messages.setContent("你好");
            List<Messages> messagesList = new ArrayList<>();
            messagesList.add(messages);
            completionRequest.setMessages(messagesList);
            // Convert the CompletionRequest to a JSON string
            String requestBody = convertCompletionRequestToJson(completionRequest);
            System.out.println("requestBody "+requestBody);
            // Set the request body as a StringEntity
            StringEntity requestEntity = new StringEntity(requestBody);
            httpPost.setEntity(requestEntity);
            //HttpUtil


            // Set the Content-Type header to specify that the request body is in JSON format
            requestEntity.setContentType("application/json");

            // Execute the POST request
            HttpResponse response = httpClient.execute(httpPost);

            // Process the response
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Request was successful
                HttpEntity responseEntity = response.getEntity();
                String responseBody = EntityUtils.toString(responseEntity);
                System.out.println("Response: " + responseBody);
                Gson gson = new Gson();
                AiResponse aiResponse = gson.fromJson(responseBody,AiResponse.class);
                System.out.println("result: "+aiResponse.getChoices().get(0).getMessage().getContent());
                return aiResponse.getChoices().get(0).getMessage().getContent();
            } else {
                // Request failed
                System.err.println("HTTP POST request failed with status code: " + statusCode);
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }*/
    }

    public static void main(String[] args) {
        // Define the URL for the POST request
        String urlComp = "http://10.66.8.85:8860/v1/chat/completions";
        String urlEmb = "http://10.66.8.85:8860/v1/embeddings";
        getTextByAi(urlComp,new CompletionRequest());
        // Create an instance of HttpClient

    }

    private static String convertCompletionRequestToJson(CompletionRequest completionRequest) {
        // Serialize the CompletionRequest object to a JSON string using Gson
        Gson gson = new Gson();
        return gson.toJson(completionRequest);
    }
}

