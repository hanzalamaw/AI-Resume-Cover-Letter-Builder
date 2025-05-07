package com.example.Backend.util;

import com.example.Backend.model.UserData;
import com.example.Backend.model.Resume;
import com.example.Backend.model.CoverLetter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class aiTextGenerator {

    // Generates a Resume from UserData
    public static Resume generateResume(UserData details) {
        String prompt = "Create a professional resume in plain text format. Include the following five sections clearly marked by unique tags. " +
                "Begin each section with [START_SUMMARY], [START_EDUCATION], [START_EXPERIENCE], [START_AWARDS], [START_PROJECTS], and end them with their corresponding [END_] tags. " +
                "Each section should be written in a short, clear paragraph using the provided details. Fill in missing details appropriately and professionally. Do not use other formatting. Information: " +
                formatUserData(details);

        String response = sendRequest(prompt);
        Resume resume = new Resume();
        resume.setSummary(extractSection(response, "SUMMARY"));
        resume.setEducation(extractSection(response, "EDUCATION"));
        resume.setExperience(extractSection(response, "EXPERIENCE"));
        resume.setAwards(extractSection(response, "AWARDS"));
        resume.setProject(extractSection(response, "PROJECTS"));
        return resume;
    }

    // Generates a Cover Letter from UserData
    // public static CoverLetter generateCoverLetter(UserData details) {
    //     String prompt = "Create a professional cover letter in plain text format. Include the following six sections clearly marked by unique tags. " +
    //             "Begin each section with [START_SUMMARY], [START_EDUCATION], [START_EXPERIENCE], [START_AWARDS], [START_PROJECTS], [START_RECRUITER], and end them with their corresponding [END_] tags. " +
    //             "Each section should be written in a short, clear paragraph using the provided details. Fill in missing details appropriately and professionally. Do not use other formatting. Information: " +
    //             formatUserData(details);

    //     String response = sendRequest(prompt);
    //     CoverLetter letter = new CoverLetter();
    //     letter.setSummary(extractSection(response, "SUMMARY"));
    //     letter.setEducation(extractSection(response, "EDUCATION"));
    //     letter.setExperience(extractSection(response, "EXPERIENCE"));
    //     letter.setAwards(extractSection(response, "AWARDS"));
    //     letter.setProjects(extractSection(response, "PROJECTS"));
    //     letter.setRecruiter(extractSection(response, "RECRUITER"));
    //     return letter;
    // }
    public static String generateCoverLetterText(UserData details) {
        String prompt = "Create a professional cover letter in plain text format using the information provided. The letter should be formal, properly structured with a greeting, body, and closing, and it should flow as a single paragraph-style letter. Address the recruiter appropriately. Information: " +
                formatUserData(details);

        String response = sendRequest(prompt);
        return response;
    }

    // Sends request to Gemini API
    private static String sendRequest(String message) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=YOUR_API_KEY";
        int retries = 3;
        int attempt = 0;

        while (attempt < retries) {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                String body = "{ \"contents\": [{ \"parts\": [{\"text\": \"" + message + "\"}] }] }";
                try (OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream())) {
                    writer.write(body);
                }

                int responseCode = con.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        responseCode == 200 ? con.getInputStream() : con.getErrorStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();

                if (responseCode != 200) {
                    if (responseCode == 503 && ++attempt < retries) {
                        Thread.sleep(3000);
                        continue;
                    }
                    return "Error: " + responseCode + " - " + response.toString();
                }

                return extractText(response.toString());

            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        return "Failed after multiple attempts.";
    }

    // Extracts plain text from JSON response
    private static String extractText(String response) {
        int start = response.indexOf("\"text\": \"") + 9;
        int end = response.indexOf("\"", start);
        if (start != -1 && end != -1) {
            return response.substring(start, end).replace("\\n", " ").trim();
        }
        return "No text found";
    }

    // Extracts a section between tags
    private static String extractSection(String response, String sectionName) {
        String startTag = "[START_" + sectionName + "]";
        String endTag = "[END_" + sectionName + "]";
        int start = response.indexOf(startTag);
        int end = response.indexOf(endTag);
        if (start != -1 && end != -1 && end > start) {
            return response.substring(start + startTag.length(), end).trim();
        }
        return "Not available";
    }

    // Formats UserData to a string
    private static String formatUserData(UserData u) {
        return u.getFirstName() + " " + u.getLastName() + ", Contact: " + u.getContactNo() +
                ", Address: " + u.getAddress() + ", Job Title: " + u.getJobTitle() +
                ", Email: " + u.getEmail() + ", Education: " + u.getEducation() +
                ", Experience: " + u.getExperience() + ", Awards: " + u.getAwards() +
                ", Projects: " + u.getProjects();
    }
}
