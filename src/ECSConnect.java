/**
 * Created by emkasun on 3/2/2016.
 */

import netscape.ldap.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ECSConnect {

    String ldapServer = "";
    int ldapPort = 0;
    String baseDN = "";
    String password = "";
    String baseObject = "";
    String request = "";
    String[] myAttrs = {"res", "rd"};

    public static void main(String[] args) {
        ECSConnect e1 = new ECSConnect();
        e1.setPara();
        e1.ldapSearch();

    }
    public void ldapSearch(){

        LDAPConnection ld = new LDAPConnection();
        System.out.println("ldap initialized .....");
        try {

            ld.connect(ldapServer, ldapPort);
            System.out.println("connected");
            ld.authenticate(baseDN, password);
            System.out.println("authenticated");
            LDAPSearchResults results = ld.search(baseObject, 1, request, myAttrs, false);
            System.out.println("search complete.");
            while (results.hasMoreElements()) {
                LDAPEntry entry = null;
                try {
                    entry = results.next();
                    System.out.println(entry.getDN());
                    System.out.println(entry.getAttributeSet().toString());
                } catch (LDAPReferralException e) {
                    System.out.println("Search references: ");
                    LDAPUrl refUrls[] = e.getURLs();
                    for (int i = 0; i < refUrls.length; i++) {
                        System.out.println("\t" + refUrls[i].getUrl());
                    }
                    continue;
                } catch (LDAPException e) {
                    System.out.println("Error: " + e.toString());
                    continue;
                }
            }
        } catch (LDAPException e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
        try {
            System.out.println("disconnecting .....");
            ld.disconnect();
        } catch (LDAPException e) {
            System.out.println("Error: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }

    public void setPara() {

        System.out.println("main configurations loading ");
        Properties prop = new Properties();

        try {
            //load a properties file
            prop.load(new FileInputStream("conf"+File.separator+"config"));

            //get the property value and print it out
            ldapServer = prop.getProperty("ldap.host");
            ldapPort = Integer.parseInt(prop.getProperty("ldap.port"));
            baseDN = prop.getProperty("base.dn");
            password = prop.getProperty("password");
            baseObject = prop.getProperty("base.object");
            request = prop.getProperty("request");

        } catch (IOException ex) {
            System.out.println("Error: " + ex.toString());
        }

    }

}