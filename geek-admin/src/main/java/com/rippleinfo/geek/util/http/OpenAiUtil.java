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

