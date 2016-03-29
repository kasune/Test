import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 11/11/14
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class HLRView {

    public void hlrObjectMap(Document in){

        //{"auc","hlr","pdpContext","vlrMobData","sgsnMobData","msgWaitData","mssMultiSim"};

        Hashtable ht = new Hashtable();
        ht.put(1,"hlr");
        ht.put(2,"auc");
        ht.put(3,"pdpContext");
        ht.put(4,"vlrMobData");
        ht.put(5,"sgsnMobData")  ;
        ht.put(6,"msgWaitData");
        ht.put(7,"mssMultiSim");
        ht.put(8,"eps");
        ht.put(9,"epsPdnContext");

        //search results
        NamedNodeMap SStatus = in.getElementsByTagName("spml:searchResponse").item(0).getAttributes();
        HLRView.searchStatus(SStatus);
        //imsi
        //hlr may have attributes, multisim
        printAttr(in,"hlr");
        for(int a=1;a<=ht.size();a++){
            printValue(ht.get(a).toString(),in,ht);
        }

        /*String Imsi = in.getElementsByTagName("ntype").item(0).getTextContent();
        System.out.println("HLR NType : "+Imsi);
        try{
        String UMTS = in.getElementsByTagName("umtsSubscriber").item(0).getFirstChild().getNodeName();
        System.out.println("UMTS : "+UMTS);
        }catch(NullPointerException ne){
            System.out.println("no UMTS");
        }*/
        //System.out.println("HLR : "+hlr);
    }

    private synchronized static void printValue(String paraName,Document in,Hashtable ht){
        try{
            for(int i=0;i<in.getElementsByTagName(paraName).getLength();i++){
            //String NType = in.getElementsByTagName(paraName).item(i).getTextContent();
                System.out.println("----"+paraName+"----");
                for(int j=0;j<in.getElementsByTagName(paraName).item(i).getChildNodes().getLength();j++ ){
                    String value = in.getElementsByTagName(paraName).item(i).getChildNodes().item(j).getTextContent();
                    String name  = in.getElementsByTagName(paraName).item(i).getChildNodes().item(j).getNodeName();

                        if(!ht.contains(name)){
                            System.out.println(name+" : "+value);
                        }

                }

            }
        }catch(Exception e){
            System.out.println(paraName + " is not found");
        }

    }

   private static void printAttr(Document in, String paraName){
       NamedNodeMap NMap = in.getElementsByTagName(paraName).item(0).getAttributes();
        System.out.println(paraName+" attributes");
        for(int i=0;i<NMap.getLength();i++){
            System.out.println(NMap.item(i).toString());
        }

   }

    private static void searchStatus(NamedNodeMap in){
        int state=0;
        for(int i=0;i<in.getLength();i++){
            if(in.item(i).toString().trim().contains("result=\"failure\"")){
            System.out.println("search "+in.item(i).toString());
            state=1;
            }
        }
        if(state==1){
            System.exit(1);
        }   else{
            System.out.println("search result=success");
        }
    }
}
