package com.example.Backend.controller;

import com.example.Backend.model.Resume;
import com.example.Backend.model.UserData;
import com.example.Backend.util.aiTextGenerator;
import com.example.Backend.util.pdfGenerator;
import org.json.JSONObject;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;

public class AiTextGeneratorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            System.out.println("✅ Servlet hit: AiTextGeneratorServlet");

            // Read JSON input from request
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            String jsonString = sb.toString();
            System.out.println("📥 Incoming JSON: " + jsonString);

            // Parse JSON
            JSONObject data = new JSONObject(jsonString);
            JSONObject personal = data.getJSONObject("personal");

            UserData details = new UserData(
                personal.optString("firstName"),
                personal.optString("lastName"),
                personal.optString("contactNo"),
                personal.optString("address"),
                personal.optString("profession"),
                personal.optString("email"),
                data.optString("education"),
                data.optString("experience"),
                data.optString("awards"),
                data.optString("projects")
            );

            String message = "Create a professional resume in plain text format. Include the following five sections clearly marked by unique tags. Begin each section with [START_SUMMARY], [START_EDUCATION], [START_EXPERIENCE], [START_AWARDS], and [START_PROJECTS], and end them with their corresponding [END_] tags. Each section should be written in a short, clear paragraph using the provided details. If any detail is missing, fill it in appropriately and professionally. Do not use any other formatting. Information: "
                + details.getFirstName() + " " + details.getLastName()
                + ", Contact: " + details.getContactNo()
                + ", Address: " + details.getAddress()
                + ", Job Title: " + details.getJobTitle()
                + ", Email: " + details.getEmail()
                + ", Education: " + details.getEducation()
                + ", Experience: " + details.getExperience()
                + ", Awards: " + details.getAwards()
                + ", Projects: " + details.getProjects();

            System.out.println("🧠 Prompt to AI: " + message);

            // Send to AI and get response
            String aiResponse = aiTextGenerator.sendRequest(message);
            if (aiResponse.startsWith("Error")) {
                System.out.println("❌ AI API Error: " + aiResponse);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json");
                response.getWriter().write("{\"status\": \"error\", \"message\": \"" + aiResponse + "\"}");
                return;
            }

            System.out.println("🤖 AI Response: " + aiResponse);

            // Parse and generate PDF
            Resume resume = new Resume();
            resume.setSummary(aiTextGenerator.extractSection(aiResponse, "SUMMARY"));
            resume.setEducation(aiTextGenerator.extractSection(aiResponse, "EDUCATION"));
            resume.setExperience(aiTextGenerator.extractSection(aiResponse, "EXPERIENCE"));
            resume.setAwards(aiTextGenerator.extractSection(aiResponse, "AWARDS"));
            resume.setProject(aiTextGenerator.extractSection(aiResponse, "PROJECTS"));

            pdfGenerator.generatePdf(
                resume.getSummary(), resume.getEducation(), resume.getExperience(), resume.getAwards(), resume.getProject(),
                details.getFirstName() + " " + details.getLastName(),
                details.getContactNo(),
                details.getEmail(),
                details.getAddress()
            );

            // Response back to frontend
            response.getWriter().write("{\"status\": \"success\"}");

        } catch (Exception e) {
            e.printStackTrace();
            String errorJson = "{\"status\":\"error\", \"message\": \"" + e.getMessage().replace("\"", "'") + "\"}";
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(errorJson);
        }
    }
}
