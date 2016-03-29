import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SendHTTP {

    public static void main(String[] args) throws Exception {
        String spmlData = readFile();
        System.out.println(spmlData);
        //String url = "http://172.17.220.102:8081//ProvisioningGateway/services/SPMLHlrEpsSubscriber50Service";
        String url = "https://nds1pgwt01/ProvisioningGateway/services/SPMLSubscriber10Service";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // add header
        post.removeHeaders("Content-Type");
        post.setHeader("SOAPAction", "urn:siemens:names:prov:gw:SPML:2:0/searchRequest");
        post.setHeader("Content-Type", "text/xml; charset=utf-8");
        post.setHeader("Encoding", "gzip,deflate");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("Content", spmlData));
        post.setEntity(new StringEntity(spmlData));

        HttpResponse response = client.execute(post);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
            System.out.println(line);
        }
        //stringToXML(line);
        //System.out.println(result);
    }

    public static String readFile(){
        BufferedReader br =null;
        String everything = "";
        try {
        br = new BufferedReader(new FileReader("D:\\git\\Test\\conf\\data.xml"));

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();

        } catch(Exception ex){
            System.out.println(ex.toString());
        }finally {
            try {
                br.close();
            }catch (IOException ioe){
                System.out.println(ioe.toString());
            }
        }
        return everything;
    }
}