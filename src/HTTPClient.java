/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 9/15/14
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
import sun.net.www.http.HttpClient;

import java.net.*;
import java.io.*;

public class HTTPClient {
    public static void main(String[] args) throws Exception {
        URL oracle = new URL("http://oracle.com");
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }


}