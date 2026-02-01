package com.example.translator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class LLMtranslator {

    // Token Hugging Face
    private static final String API_KEY = System.getenv("HUGGINGFACE_API_KEY");
    private static final String API_URL = "https://router.huggingface.co/v1/chat/completions";

    public static String translateToDarija(String text) {
        try {

            // Prompt clair et strict
            String prompt =
                    """
                    Translate the following English text to Moroccan Darija.
                    Rules:
                    - Use Moroccan Arabic dialect (Darija)
                    - Write ONLY in Arabic script
                    - Do NOT use French
                    - Do NOT explain
                    
                    Text:
                    """ + text;

            JSONObject message = new JSONObject()
                    .put("role", "user")
                    .put("content", prompt);

            JSONArray messages = new JSONArray().put(message);

            JSONObject requestBody = new JSONObject()
                    .put("model", "meta-llama/Llama-3.1-8B-Instruct")
                    .put("messages", messages)
                    .put("temperature", 0.2); // plus précis

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray choices = jsonResponse.getJSONArray("choices");

            return choices
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la traduction : " + e.getMessage();
        }
    }
}
