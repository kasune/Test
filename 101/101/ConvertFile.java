import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;

import java.text.*;
import java.util.*;


public class ConvertFile extends DefaultHandler{
	
	private boolean endLine    = false;
	private boolean endSummary = false;
	private boolean isTitle    = false;
	private boolean isNumber   = false;
	private boolean isDate     = false;
	private boolean isEndCell  = false;
	private Calendar last      = null;
	
	private StringBuffer buffer= null;
	private PrintWriter out    = null;
	
	
	private String rLine = null;
	private int columnIndex=0;
	private String countryName = null;


	private final String BASE_DIRECTORY   = "D:\\git\\Test\\101\\";
	private final String SOURCE_DIRECTORY = "excel";
	private final String DEST_DIRECTORY   = "reports";
	private final String BACKUP_DIRECTORY = "archive";
	private final String DELIMITER        = "_";

	
	public ConvertFile() {
		// default constructor
	}
	
	public static void main(String args[]){
		ConvertFile cf = new ConvertFile();
		cf.start();
	}
	
	public void start(){
		File srcDir           = null;
		File files[]          = null;
		FilenameFilter filter = null;
		String sLast          = null;
		
		SAXParserFactory factory = null;
		SAXParser saxParser      = null;
		
		Calendar calendar     = null;
		String fHour          = null;
		String fMinute        = null;
		String fSecond        = null;
		String fDay           = null;
		String fMonth         = null;
		String fYear          = null;


		srcDir = new File(BASE_DIRECTORY);
		if (!srcDir.isDirectory()){
			System.out.println("Warning: Directory <" + srcDir.getName() + "> does not exist");
			System.exit(10);
		}

		filter = new FilenameFilter() {
			public boolean accept(File file, String name) {
				boolean doProcess = false;
				doProcess = (name.toUpperCase().endsWith(".XLS") && (name.toUpperCase().indexOf("GLR101") > -1));
				return doProcess;
			}
		};
		
		files = srcDir.listFiles(filter);
		
		if ((files != null) && (files.length>0)){
			System.out.println("INFO: Found <" + files.length + "> files in the directory");
		}else {
			System.out.println("Warning: No files found in the <" + SOURCE_DIRECTORY + "> directory");
			System.exit(10);
		}

		try {


			out = new PrintWriter(new BufferedWriter(new FileWriter(BASE_DIRECTORY + "report.csv")));
			
			out.println("Date,"	+
						"Network," +
						"GSM Register Attempt," +
						"GPRS Register Attempt," +
						"GSM Register Success," +
						"GPRS Register Success," +
						"GSM Success Ratio (%)," +
						"GPRS Success Ratio (%)," +
						"GSM Avg Register Roamers," +
						"GPRS Avg Register Roamers," +
						"Total Avg Register Roamers," +
						"GSM Out of Total Roamer(%)," +
						"GPRS Out of Total Roamer(%)," +
						"Country");

			for (int index=0; index<files.length;index++){
				endSummary=false;
				factory = SAXParserFactory.newInstance();
				saxParser = factory.newSAXParser();

				System.out.println("Analyzing file >>>>> " + files[index].getName());

				saxParser.parse(files[index], this);

				Thread.sleep(1000);
				files[index].renameTo(new File(BASE_DIRECTORY+BACKUP_DIRECTORY+"/"+files[index].getName()));

			}
			
			out.close();
			
			if (last==null){
				System.out.println("last is null");
				System.exit(10);
			}

			calendar = Calendar.getInstance();
			fHour    = (calendar.get(Calendar.HOUR_OF_DAY)<10)? ("0"+calendar.get(Calendar.HOUR_OF_DAY)): (""+calendar.get(Calendar.HOUR_OF_DAY));
			fMinute  = (calendar.get(Calendar.MINUTE)<10)? ("0"+calendar.get(Calendar.MINUTE)): (""+calendar.get(Calendar.MINUTE));
			fSecond  = (calendar.get(Calendar.SECOND)<10)? ("0"+calendar.get(Calendar.SECOND)): (""+calendar.get(Calendar.SECOND));
			fMonth   = ((last.get(Calendar.MONTH)+1)<10)? ("0"+(last.get(Calendar.MONTH)+1)): (""+(last.get(Calendar.MONTH)+1));
			fDay     = (last.get(Calendar.DAY_OF_MONTH)<10)? ("0"+last.get(Calendar.DAY_OF_MONTH)): (""+last.get(Calendar.DAY_OF_MONTH));
			
			sLast = ("GLR101" + DELIMITER + 
			         last.get(Calendar.YEAR) + fMonth + fDay + DELIMITER +
			         fHour + fMinute + fSecond);
			new File(BASE_DIRECTORY + "report.csv").renameTo(new File(BASE_DIRECTORY+sLast+".csv") );


		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	
	public void startElement(String uri, String localName, String qName, Attributes attributes){
		
		if(qName.equalsIgnoreCase("Row")){
			endLine = false;
			rLine="";
			columnIndex=0;
			isTitle=true;
		}
	
		if(qName.equalsIgnoreCase("Data")){
			if (attributes!=null){
				if (attributes.getValue(0).equals("DateTime")) {
					isTitle=false;
					isDate=true;
				}else {
					isDate=false;
				}
				
				if (attributes.getValue(0).equals("Number")){
					isNumber=true;
				}else{
					isNumber=false;
				}
			}
		}
		
	}
	
	
	public void characters(char[] ch, int start, int length){
		
		String data = null;
		NumberFormat nf = null;
		DateFormat df = null;
		buffer = new StringBuffer();

		Calendar calendar = Calendar.getInstance();
		String month      = null;
		String date       = null;
		int year          = 0;
		
		
		buffer.append(ch, start, length);
		data = buffer.toString().trim();
		columnIndex++;
		
		if (data.equalsIgnoreCase("Detailed Report")){
			endSummary = true;
		}
		
		if ((data.length()>0) && (endSummary)){
			
			if (isNumber) {
				//Conlumn 5 or 7
				if ((columnIndex==19) || (columnIndex==27)){
					nf = NumberFormat.getInstance();
					nf.setMaximumFractionDigits(2);
					try{
						data = ( nf.format(nf.parse(data).doubleValue()*100) );
						rLine+=(data+",");
					}catch(Exception ex){
						rLine+=(data+",");
					}
				}else {
					rLine+=(data+",");
				}

			}else if (isDate){
				df = DateFormat.getDateInstance();
				try{
					//data = data.substring(0, data.indexOf("T"));
					df = new SimpleDateFormat("yyyy-MM-dd");
					calendar.setTimeInMillis(df.parse(data).getTime());
					year  = calendar.get(Calendar.YEAR);
					month = ((calendar.get(Calendar.MONTH)+1) < 10) ? ("0"+(calendar.get(Calendar.MONTH)+1)) : (""+(calendar.get(Calendar.MONTH)+1));
					date  = (calendar.get(Calendar.DAY_OF_MONTH) <10) ? ("0"+calendar.get(Calendar.DAY_OF_MONTH)) : (""+calendar.get(Calendar.DAY_OF_MONTH)) ;

					rLine += (year + "-" + month + "-" + date + ",");
					
					last = (last==null)? calendar:getLast(calendar);
					
				}catch(Exception date_ex){
					rLine+=(data+",");
					//date_ex.printStackTrace();
					System.out.println("Warning: Days > "+date_ex.getMessage());
				}
			}else {
				isEndCell=false;
				rLine+=(data);
			}
			//rLine+=(data+",");
		}
		
	}

	private Calendar getLast(Calendar nCal){
		Calendar result = null;
		result = (nCal.after(last))?nCal:last;
		return result;
	}
	
	public void endElement(String uri, String localName, String qName) {
		if(qName.equalsIgnoreCase("Row")){
			endLine=true;
			if ((endSummary) && (!isTitle)){
				out.println(rLine+countryName);
			}
			
			if ((endSummary) && (columnIndex==5)){
				if (rLine.indexOf("Detailed")<0){
					countryName=rLine;
				}
			}
		}
		
		if((qName.equalsIgnoreCase("Data")) && (!isEndCell)){
			isEndCell=true;
			rLine+=",";
		}
	}
}