package com.example.Backend.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.Backend.model.*;
import com.example.Backend.controller.*;
import com.example.Backend.util.*;

public class aiTextGenerator {
    public static void main(String[] args) {
        UserData details = new UserData("Hanzala", "Wahab", "0325121", "sdahda", "Software Engineer", "hanzalamwa@gmail.com",
                "Bs Cs from Iobm", "Worked as a software engineer at ibex", "Best Employee", "Ai Resume maker");

        String message = "Create a professional resume in plain text format. Include the following five sections clearly marked by unique tags. Begin each section with [START_SUMMARY], [START_EDUCATION], [START_EXPERIENCE], [START_AWARDS], and [START_PROJECTS], and end them with their corresponding [END_] tags. Each section should be written in a short, clear paragraph using the provided details. If any detail is missing, fill it in appropriately and professionally. Do not use any other formatting. Information: " + details.getFirstName() + " " + details.getLastName() + ", Contact: " + details.getContactNo() + ", Address: " + details.getAddress() + ", Job Title: " + details.getJobTitle() + ", Email: " + details.getEmail() + ", Education: " + details.getEducation() + ", Experience: " + details.getExperience() + ", Awards: " + details.getAwards() + ", Projects: " + details.getProjects();

        String response  = sendRequest(message);
        response = response.replace("\\", " ");
        System.out.println("Full Response: " + response);

        // Create a Resume object and extract sections from the response
        Resume resume = new Resume();
        resume.setSummary(extractSection(response, "SUMMARY"));
        resume.setEducation(extractSection(response, "EDUCATION"));
        resume.setExperience(extractSection(response, "EXPERIENCE"));
        resume.setAwards(extractSection(response, "AWARDS"));
        resume.setProject(extractSection(response, "PROJECTS"));

        // Print each section of the resume
        System.out.println("Summary: " + resume.getSummary());
        System.out.println("Education: " + resume.getEducation());
        System.out.println("Experience: " + resume.getExperience());
        System.out.println("Awards: " + resume.getAwards());
        System.out.println("Projects: " + resume.getProject());

        String fullName = details.getFirstName() + details.getLastName();

        pdfGenerator.generatePdf(resume.getSummary(), resume.getEducation(), resume.getExperience(), resume.getAwards(), resume.getProject(), fullName, details.getContactNo(), details.getEmail(), details.getAddress());
    }

    public static String extractSection(String response, String sectionName) {
        String startTag = "[START_" + sectionName.toUpperCase() + "]";
        String endTag = "[END_" + sectionName.toUpperCase() + "]";
        int start = response.indexOf(startTag);
        int end = response.indexOf(endTag);
        if (start != -1 && end != -1 && end > start) {
            return response.substring(start + startTag.length(), end).trim();
        }
        return "Not available";
    }

    public static String sendRequest(String message) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=AIzaSyBq77RouZ3hR49ltOeHn4xk9yiEh1qncXs";
        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                // Set up the connection
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");

                // Prepare the body with the input message
                String body = "{\n" +
                        "  \"contents\": [{\n" +
                        "    \"parts\": [{\"text\": \"" + message + "\"}]\n" +
                        "  }]\n" +
                        "}";

                // Send the request
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();

                // Get the response code and handle errors
                int responseCode = con.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    String inputLine;
                    StringBuffer errorResponse = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        errorResponse.append(inputLine);
                    }
                    in.close();
                    if (responseCode == 503) {
                        // If 503, try again
                        retryCount++;
                        System.out.println("Retrying... Attempt " + retryCount);
                        Thread.sleep(5000); // Wait for 5 seconds before retry
                        continue;
                    }
                    return "Error: " + responseCode + " - " + errorResponse.toString();
                }

                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Extract and return the content from the response
                return extractContentFromResponse(response.toString());

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "Error occurred: " + e.getMessage();
            }
        }

        return "Failed after " + maxRetries + " attempts.";
    }

    public static String extractContentFromResponse(String response) {
        // Print the full response for debugging purposes
        System.out.println("Full Response: " + response);

        try {
            // Locate the "candidates" field
            int candidatesIndex = response.indexOf("\"candidates\": ");
            if (candidatesIndex == -1) {
                return "Error: No candidates found in response.";
            }

            // Locate the "content" field within the candidates
            int contentIndex = response.indexOf("\"content\": ", candidatesIndex);
            if (contentIndex == -1) {
                return "Error: No content field found in candidates.";
            }

            // Locate the "text" field within the content
            int startMarker = response.indexOf("\"text\": \"", contentIndex) + 9; // Skip over "\"text\": \""
            if (startMarker == -1) {
                return "Error: Text field not found.";
            }

            // Find the end of the "text" field
            int endMarker = response.indexOf("\"", startMarker);
            if (endMarker == -1) {
                return "Error: End of text not found.";
            }

            // Extract the text part
            String extractedText = response.substring(startMarker, endMarker);

            // Handle escape characters, specifically backslashes
            extractedText = extractedText.replace("\\n", " ");

            // Return the cleaned text
            return extractedText;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred during response parsing: " + e.getMessage();
        }
    }
}