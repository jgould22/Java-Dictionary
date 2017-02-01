/**
 * Assignment 2 CS2210A 2013
 * @author jgould22 jgould22@uwo.ca
 *DictEntry Class 
 */
public class DictEntry {

    private String key;
    private int code;
    
    /**
     * Constructor for DictEntry
     * @param key
     * @param code
     */
    public DictEntry(String key, int code) {
        
        this.key = key;
        
        this.code = code;
    
               
    }

    /**
     * Get String 
     * @return String Key Value
     */
    public String getKey(){
        
        return this.key;
        
    }
    
    /**
     * Get Code
     * @return Int code Value
     */
   public int getCode(){
        
        return this.code;
        
    }
   
   
   
   
    

}
