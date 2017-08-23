package com.application.robat.postage;

/**
 * Created by domin on 19.08.2017.
 */
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.net.*;
import java.io.*;

public class URLConnectionReader {

    public static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return response.toString();
    }

    public static String getUrlAsString(String url)
    {
        try
        {
            URL urlObj = new URL(url);
            URLConnection con = urlObj.openConnection();

            con.setDoOutput(true); // we want the response
            con.setRequestProperty("Cookie", "myCookie=test123");
            con.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            String newLine = System.getProperty("line.separator");
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine + newLine);
            }

            in.close();

            return response.toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }




    public static void main() throws Exception {
       //String content = URLConnectionReader.getText(args[0]);
       // System.out.println(content);

    }

}
