import javax.xml.parsers.DocumentBuilder;
import javax.xml.soap.*;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 11/7/14
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class SOAPClient {

        public static void main(String args[]) throws Exception {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            //String url = "http://172.30.50.134:8081/ProvisioningGateway/services/SPMLHlrEpsSubscriber50Service";
            String url = "http://172.17.220.102:8081/ProvisioningGateway/services/SPMLHlrEpsSubscriber50Service";
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

            // print SOAP Response
            //System.out.print("Response SOAP Message:");

            //soapResponse.writeTo(System.out);
            //System.out.println();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapResponse.writeTo(out);
            String xml=new String(out.toByteArray());

            System.out.println(xml);
            //String m = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><spml:searchResponse executionTime=\"18\" requestID=\"-16fb6d97:1495a776cd9:2d\" result=\"success\" searchStatus=\"completeResult\" xmlns:spml=\"urn:siemens:names:prov:gw:SPML:2:0\" xmlns:subscriber=\"urn:siemens:names:prov:gw:HLR_EPS_SUBSCRIBER:5:0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><version>HLR_EPS_SUBSCRIBER_v50</version><objects xmlns:ns2=\"urn:siemens:names:prov:gw:HLR_EPS_SUBSCRIBER:5:0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ns2:Subscriber\"><auc><imsi>525038989898989</imsi></auc><hlr actIMSIGprs=\"true\" clip=\"true\" clipOverride=\"false\" colpOverride=\"false\" commonMSISDN=\"6586914005\" hold=\"true\" isActiveIMSI=\"true\" mobileSubscriberType=\"genericSubscriber\" mpty=\"true\" mscat=\"10\" ndcLac=\"8\" ntype=\"single\" nwa=\"3\" obGprs=\"0\" odbgprs=\"0\" odbic=\"0\" odboc=\"0\" odboprc=\"0\" odbr=\"0\" odbsci=\"0\" odbssm=\"0\" optimalRouting=\"true\" overrideCommonCLI=\"true\" profileType=\"mssMultiSim\" rr=\"M1Roam\" sr=\"1\" vlrid=\"true\" wllSubscriber=\"false\">\n" +
            //        "      <ntype>single</ntype><imsiActive>true</imsiActive><mobileSubscriberType>genericSubscriber</mobileSubscriberType><umtsSubscriber><accTypeGSM>true</accTypeGSM></umtsSubscriber><wllSubscriber>false</wllSubscriber><mscat>10</mscat><odboc>0</odboc><odbic>0</odbic><odbr>0</odbr><odboprc>0</odboprc><odbssm>0</odbssm><clip>true</clip><clipOverride>false</clipOverride><clir>1</clir><colpOverride>false</colpOverride><hold>true</hold><nwa>3</nwa><odbgprs>0</odbgprs><sr>9</sr><odbsci>0</odbsci><ts11><msisdn>6596960141</msisdn></ts11><ts21><msisdn>6596960142</msisdn></ts21><ts22><msisdn>6596960142</msisdn></ts22><bs20genr><msisdn>6596960142</msisdn></bs20genr><bs30genr><msisdn>6596960142</msisdn></bs30genr><bs40genr><msisdn>6596960142</msisdn></bs40genr><gprs><msisdn>6596960142</msisdn></gprs><cfu><basicServiceGroup>TS10-telephony</basicServiceGroup><status>4</status></cfu><cfu><basicServiceGroup>BS40-padAccess</basicServiceGroup><status>4</status></cfu><cfu><basicServiceGroup>BS30-dataSync</basicServiceGroup><status>4</status></cfu><cfu><basicServiceGroup>BS20-dataAsync</basicServiceGroup><status>4</status></cfu><caw><basicServiceGroup>TS10-telephony</basicServiceGroup><status>4</status></caw><caw><basicServiceGroup>BS40-padAccess</basicServiceGroup><status>4</status></caw><caw><basicServiceGroup>BS30-dataSync</basicServiceGroup><status>4</status></caw><caw><basicServiceGroup>BS20-dataAsync</basicServiceGroup><status>4</status></caw><cug><cugIndex>1</cugIndex><cugInterlockCode>2</cugInterlockCode><dataNetworkIdentityCode>1065</dataNetworkIdentityCode><intraCUGRestriction>0</intraCUGRestriction><basicServiceGroup>TS10-telephony</basicServiceGroup></cug><cugbsg><basicServiceGroup>TS10-telephony</basicServiceGroup><interCUGAccessType>0</interCUGAccessType></cugbsg><isActiveIMSI>false</isActiveIMSI><actIMSIGprs>false</actIMSIGprs><obGprs>0</obGprs><optimalRouting>true</optimalRouting><ndcLac>8</ndcLac><generalChargingCharacteristics><chargingCharacteristics>hotBilling</chargingCharacteristics><chargingCharacteristicsProfile>1</chargingCharacteristicsProfile><chargingCharacteristicsBehavior>1</chargingCharacteristicsBehavior></generalChargingCharacteristics><pdpContext><id>1</id><type>4</type><qosProfile>PPU7200</qosProfile><apn>TestAPN</apn><apnArea>HPLMN</apnArea></pdpContext><pdpContext><id>2</id><type>4</type><qosProfile>DMAX1000</qosProfile><apn>*</apn><apnArea>ALLPLMN</apnArea></pdpContext><pdpContext><id>3</id><type>2</type><qosProfile>PPU7200</qosProfile><apn>CORPORATE.COM</apn><apnArea>HPLMN</apnArea></pdpContext><pdpContext><id>4</id><type>2</type><qosProfile>PPU7200</qosProfile><apn>7CONNECT</apn><apnArea>HPLMN</apnArea></pdpContext><ocsi><operatorServiceName>M1_OCSI_C</operatorServiceName><csiState>1</csiState><csiNotify>1</csiNotify></ocsi><vlrMobData><vlrIdValid>false</vlrIdValid><mobileTerminatingCallPossible>true</mobileTerminatingCallPossible><plmnAllowed>true</plmnAllowed><roamingAreaAllowed>true</roamingAreaAllowed><mscAreaRestrictedReceived>false</mscAreaRestrictedReceived><msPurged>false</msPurged><supportedCAMELPhaseByVLR>1</supportedCAMELPhaseByVLR><supportedMAPVersionForLUP>3</supportedMAPVersionForLUP><featuresNotSupportedByVLR>extCamel</featuresNotSupportedByVLR><featuresNotSupportedByVLR>oICK</featuresNotSupportedByVLR><prohFtnoUpdInVlrFail>false</prohFtnoUpdInVlrFail><ts10BarrByCb>0</ts10BarrByCb><ts20BarrByCb>0</ts20BarrByCb><ts60BarrByCb>0</ts60BarrByCb><bs20BarrByCb>0</bs20BarrByCb><bs30BarrByCb>0</bs30BarrByCb><bs40BarrByCb>0</bs40BarrByCb><vlrSupportsLongFtno>false</vlrSupportsLongFtno><ssetSubst>0</ssetSubst></vlrMobData><sgsnMobData><sgsnIdValid>false</sgsnIdValid><plmnAllowed>true</plmnAllowed><roamingAreaAllowed>true</roamingAreaAllowed><gprsAllowed>true</gprsAllowed><supportedCAMELPhaseBySGSN>1</supportedCAMELPhaseBySGSN><supportedMAPVersionForLUP>3</supportedMAPVersionForLUP><featuresNotSupportedBySGSN>extCamel</featuresNotSupportedBySGSN><sgsnCamelNot>false</sgsnCamelNot><sgsnExtQos>false</sgsnExtQos><msPurged>false</msPurged><sgsnAreaRestRcvd>false</sgsnAreaRestRcvd></sgsnMobData><arc><redirInd>2</redirInd></arc><eps><defaultPdnContextId>43</defaultPdnContextId><maxBandwidthUp>1024</maxBandwidthUp><maxBandwidthDown>2048</maxBandwidthDown><msPurgedEps>false</msPurgedEps><apnOIReplacement>2</apnOIReplacement><sessionTransferNumber>1</sessionTransferNumber><mmeUpdateSucceeded>false</mmeUpdateSucceeded></eps><epsPdnContext><apn>EPS_TestK</apn><contextId>43</contextId><type>both</type></epsPdnContext><epsRoamAreaName>LTE_Roaming</epsRoamAreaName><epsPsRoamAreaMmeName>M1</epsPsRoamAreaMmeName></hlr></objects></spml:searchResponse></soapenv:Body></soapenv:Envelope>";

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(xml));

            Document doc = builder.parse(src);

            HLRView hv = new HLRView();
            hv.hlrObjectMap(doc);
//            String name = doc.getElementsByTagName("name").item(0).getTextContent();
        }

    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        private static SOAPMessage createSOAPRequest() throws Exception {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();

            String urnValue = "urn:siemens:names:prov:gw:SPML:2:0";
            // SOAP Envelope
            SOAPEnvelope envelope = soapPart.getEnvelope();
            //envelope.addNamespaceDeclaration("example", serverURI);
            envelope.addNamespaceDeclaration("urn",urnValue);//("urn",UrnValue);

            // SOAP Body
            SOAPBody soapBody = envelope.getBody();
            SOAPElement soapBodyElem = soapBody.addChildElement("searchRequest", "urn");
            Name Nspml = envelope.createName("xmlns:spml");
            soapBodyElem.addAttribute(Nspml,"urn:siemens:names:prov:gw:SPML:2:0");
            Name Nsubscriber = envelope.createName("xmlns:subscriber");
            soapBodyElem.addAttribute(Nsubscriber,"urn:siemens:names:prov:gw:HLR_EPS_SUBSCRIBER:5:0");
            Name Nxsi = envelope.createName("xmlns:xsi");
            soapBodyElem.addAttribute(Nxsi, "http://www.w3.org/2001/XMLSchema-instance");

            SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("version");
            soapBodyElem1.addTextNode("HLR_EPS_SUBSCRIBER_v50");
            SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("base");
            SOAPElement soapBodyElem3 = soapBodyElem2.addChildElement("objectclass");
            soapBodyElem3.addTextNode("Subscriber");
            SOAPElement soapBodyElem4 = soapBodyElem2.addChildElement("alias");
            Name Nname = envelope.createName("name");
            soapBodyElem4.addAttribute(Nname, "msisdn");
            Name Nvalue = envelope.createName("value");
            soapBodyElem4.addAttribute(Nvalue, "6586914007");

            SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("returnAttribute");
            soapBodyElem5.addTextNode("hlr");
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction","urn:siemens:names:prov:gw:SPML:2:0/searchRequest");

            soapMessage.saveChanges();

        /* Print the request message */
            //System.out.print("Request SOAP Message:");
            soapMessage.writeTo(System.out);
            System.out.println();

            return soapMessage;
        }

    public String format(String xml) {

        try {
            final InputSource src = new InputSource(new StringReader(xml));
            final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            final Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));

            //May need this:
            System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");

            final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            final LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.

            return writer.writeToString(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
