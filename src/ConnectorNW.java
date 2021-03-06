import org.apache.commons.net.telnet.TelnetClient;

import java.io.InputStream;
import java.io.PrintStream;
/**
 * Created by emkasun on 10/3/2016.
 */
public class ConnectorNW {
    private TelnetClient telnet = new TelnetClient();
    private InputStream in;
    private PrintStream out;
    private String prompt = "%";

    public ConnectorNW(String server, String user, String password) {
        try {
            // Connect to the specified server
            telnet.connect(server, 9090);

            // Get input and output stream references
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());

            // Log the user on
            //readUntil("login: ");
            Thread.sleep(1000);
            write(user);
            //readUntil("Password: ");
            Thread.sleep(1000);
            write(password);

            // Advance to a prompt
            //readUntil(prompt + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void su(String password) {
        try {
            write("su");
            readUntil("Password: ");
            write(password);
            prompt = "#";
            //readUntil(prompt + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            boolean found = false;
            char ch = (char) in.read();
            int count = 0;
            while (count<2500) {
                System.out.print(ch);
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
                count++;
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String value) {
        try {
            out.println(value);
            out.flush();
            //System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendCommand(String command) {
        try {
            write(command);
            return readUntil(prompt + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        try {
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ConnectorNW telnet = new ConnectorNW(
                    "172.30.51.36", "kasun", "kasun");
            System.out.println("Got Connection...");
            telnet.sendCommand("ZMIO:MSISDN=6586918002;");
            Thread.sleep(1000);
            //telnet.sendCommand("Z; ");
            telnet.sendCommand("ZMIO:MSISDN=6586914008;");
            Thread.sleep(1000);
            telnet.sendCommand("Z; ");
            Thread.sleep(1000);
            //telnet.disconnect();
            System.out.println("DONE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
