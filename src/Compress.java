/**
 * @author JordanGould jordangould@gmail.com
 */
import java.io.*;
import java.util.ArrayList;

public class Compress {

	public static void main(String[] args) {

		//Initialise Dictionary
		Dictionary Dictionary = new Dictionary(15000);
		
		//Fill dictionary with assci codes from 0-255
		for(int i = 0; i <256; i++){
			
			try{				
				
				Dictionary.insert(new DictEntry(Character.toString((char) i),i));
				
			}catch (DictionaryException e) {
				
			      System.out.println("Dictionary Initilisation Failure");
			      e.printStackTrace();
			      
		    }
			
		}
		
		 //Declare file at location at arg1
		File inputFile = new File(args[0]);
		
		//open output and input streams
		String outputFileName = inputFile.getName();
		int pos = outputFileName.lastIndexOf(".");
		if (pos > 0) {
			outputFileName = outputFileName.substring(0, pos);
			outputFileName = outputFileName + ".zzz";
		}
		//Create compressed file in same directory
		File outputFile = new File (inputFile.getParent(), outputFileName);
		
		//Open output and input streams
		BufferedInputStream in;
		BufferedOutputStream out;
		MyOutput myOutput = new MyOutput();
		
		try{
			in = new BufferedInputStream(new FileInputStream(inputFile));
			out = new BufferedOutputStream(new FileOutputStream(outputFile));
		} catch (Exception e){
			
			System.out.println("File Input Error");
			e.printStackTrace();
			return;
		}
		
	try{
		
    	//Declare array list to store bytes from Pb
    	ArrayList<Integer> pb = new ArrayList<Integer>();
    	
    	//Add the first byte from the file
    	pb.add(in.read());
    	
    	//set nc to dictionary element size ~255
    	int nc = Dictionary.numElements();
	
		 // read until a single byte is available
        while(pb.get(pb.size()-1)!=-1)
        {
        	        	
        	pb.add(in.read());
        	
        	//Add bytes to pb until pbtostring is not located in the dictionary
        	while(Dictionary.find(pbtoString(pb)) != null){
        		
        		pb.add(in.read());
        		
        	}
        	
        	//Get dictEntry from the dictionary at key pbtostring-1
        	DictEntry Writecode = Dictionary.find(pbtoStringMinusLastChar(pb)) ;
			
        	//write the code to the compressed file
			myOutput.output(Writecode.getCode(), out);	
			
			//check to see if the file has ended, if so break 
			if(pb.get(pb.size()-1)==-1){
				
				break;
				
			}
			
        	//set the byte stored at the end of list pb to lastbyte
			int lastByte = pb.get(pb.size()-1);
			
			//Create a new dictionary entry so long as the dictionary contains less than
			if(nc<4096){
    		
				Dictionary.insert(new DictEntry(pbtoString(pb),nc));
			
				nc++;
				
			}
			
    		pb.clear();
    		
    		pb.add(lastByte);
        	    	
        }
             
     }catch(Exception e){
        
        e.printStackTrace();
        
     }finally{		
        // releases any system resources associated with the stream
      
    	 try{
 			in.close();
 			myOutput.flush(out);
 			out.close();
 		} catch (Exception e){
 			System.out.println("File Stream Close Error");
 			e.printStackTrace();
 			return;
 		}
    	 
     }
				
		
	}
	
	
	/**
	 * pbtoString
	 * @param ArrayList pb
	 * @return String of bytes in pb 
	 */
	private static String pbtoString(ArrayList<Integer> pb){
		
		String p = "";
		//Create a string from pb
		for (int i = 0; i<pb.size();i++){
			
		int j = pb.get(i);
		
		char stringletter = (char) j;
		
		p +=  stringletter;
				
		}
		
		return p;
	}
	/**
	 * pbtoStringMinusLastChar
	 * @param ArrayList pb
	 * @return String of bytes in pb excluding the last byte/char
	 */
	private static String pbtoStringMinusLastChar(ArrayList<Integer> pb){
		
		String p = "";
		//Create a string from pb, stop before the last node in pb
		for (int i = 0; i<pb.size()-1;i++){
			
		int j = pb.get(i);
		
		char stringletter = (char) j;
		
		p +=  stringletter;
				
		}
		
		return p;
	}
	
}
	


