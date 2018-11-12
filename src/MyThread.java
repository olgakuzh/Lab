import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MyThread extends Thread {
    String lang;
    String input;
    StringBuffer output;

    public MyThread (String initLang, String initInput) {
        lang = initLang;
        input = initInput;
        output = new StringBuffer();
    }

    public StringBuffer getOutput() {
        return output;
    }

    public void run() {
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate";
            try {
                URL urlObject = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                Map<String, String> parameters = new HashMap<>();
                parameters.put("key", "trnsl.1.1.20181102T210526Z.8e7497d78f6f0301.f0f6dfaeb800e80d740a4a29329bea12f75264e7");
                parameters.put("text", URLEncoder.encode(input, "UTF-8"));
                parameters.put("lang", URLEncoder.encode(lang, "UTF-8"));

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();



                int status = con.getResponseCode();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;

                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                JSONObject myObject = new JSONObject(content.toString());

                output.append(myObject.getJSONArray("text").get(0));

                in.close();
                con.disconnect();

            }
            catch (IOException e) {
                System.out.println(e);
            }
            catch (JSONException je) {
                System.out.println(je);
            }

}

    private static class ParameterStringBuilder {
        public static String getParamsString(Map<String, String> params)
                throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }

    }

    }

