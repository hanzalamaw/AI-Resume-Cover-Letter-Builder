import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAITest {

    public static void main(String[] args) {
        String message = "Hello, OpenAI!";
        String response = sendRequest(message);
        System.out.println("Response: " + response);
    }

    public static String sendRequest(String message) {
        String apiKey = "API_KEY";
        String url = "https://api.openai.com/v1/chat/completions"; 
        String model = "gpt-3.5-turbo"; 

        try {
            // Set up the connection
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Prepare the body with the input message
            String body = "{\n" +
                          "  \"model\": \"" + model + "\",\n" +
                          "  \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]\n" +
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

        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

    // Extract content from the response
    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content") + 11; // Find start of the text field
        int endMarker = response.indexOf("\"", startMarker); // Find the end of the text field
        return response.substring(startMarker, endMarker); // Return the text part of the response
    }
}
