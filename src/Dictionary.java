/**
 * @author JordanGould jordangould@gmail.com
 */

import java.util.LinkedList;
import java.util.List;

public class Dictionary implements DictionaryADT{
	
	private int dictSize;
	private int numElements;
	@SuppressWarnings("rawtypes")
	private final List[] table;
	
	/**
	 * Constructor for dictionary
	 * @param size 
	 */
    public Dictionary(int size) {
		
		this.dictSize = size;
		
		this.table = new LinkedList[dictSize];

    }
    
    
    /**
     * Insert, inserts DictEntry into Dictionary
     * @param DictEntry DictEntry object to be inserted into dictionary
     */
    @SuppressWarnings("unchecked")
    public int insert(DictEntry pair) throws DictionaryException {
    	
    	//Generate hash Value
    	int hashValue = hashFunction(pair.getKey());
    	
    	//If there is no list at hash address create one and add the dictEntry
    	//else just add the dictEntry to the end of the list
    	if(table[hashValue]==null){
    		
    		table[hashValue] = new LinkedList<DictEntry>();
    		
    		table[hashValue].add(pair);
    		
    		numElements++;
    				
    		return 0;
    		
    	}else if(table[hashValue] != null) {
    		
    		//Check to see if the DictEntry is already in the dictionary, if not add it if it is throw exception		
    		if(find(pair.getKey())==null){
    			
    			table[hashValue].add(pair);
        		
        		return 1;
    			
    		}else{
    			
    			throw new DictionaryException("DictEntry is already in Dictionary");
    			
    		}
    		
        }
        	
        return 0;	
        
    	
    }
    
    /**
     * Remove, removes the dictentry with the supplied key from the dictionary 
     * @param String Key
     */
    public void remove(String key) throws DictionaryException {
    	
    	//generate hash value for the key
    	int hashValue = hashFunction(key);
    	
    	//if there is no list at that address the entry isnt in the dictionary, throw exception
    	if(table[hashValue] == null){
    		
    		throw new DictionaryException("The item is not in the dictionary");
    		
    	}else{		
    		
    		//loop through the lsit to find if the dict entry is there, if it is remove it
    		boolean keyNotFound = true;
    		int i= 0;
    		while ( i < table[hashValue].size()) {
    			
    			DictEntry DE = (DictEntry) table[hashValue].get(i);
    			
    			if(DE.getKey() == key){
    				
    				table[hashValue].remove(i);
    				
    				keyNotFound = false;
    				
    				break;
    				
    			}
    			
    			keyNotFound = true;
    			
    		}
    		//throw exception if the key is not found
    		if(keyNotFound){
    			
    			throw new DictionaryException("Key Not Foundy");
    			
    		}
    	
    		
    		numElements--;
    		
    	}
    	
    	
    }
    
    /**
     * Dictionary Find
     * @param String key
     * @return DictionaryEntry with the key
     */
    public DictEntry find(String key){
    	
    	int hashValue = hashFunction(key);
    	
    	//Find the list at the hashvalue and loop through it if there is a dict entry with the key return it, else return null
    	if(table[hashValue] != null){
    		
    		int listSize = table[hashValue].size();
    		
    		int i= 0;
    		while ( i < listSize) {
    			
    			DictEntry ListDictEntry = (DictEntry) table[hashValue].get(i);
    			
    			String dictEntryKey = ListDictEntry.getKey();
    			
    			if( dictEntryKey.equals(key)){
    				
   				return ListDictEntry;
    								
    			}
    				
    			i++;
    		}
    		
    		return null;
    		
    	}else{		
    		
    		return null;
  			
    	}
    		
    }
    
    /**
     * numElements
     * @return int the number of elements in the dictionary
     */
    public int numElements(){
    	
    	return this.numElements;
    	
    }
     
    /**
     * hashFunction
     * @param key
     * @return int hash value for the supplied key
     */
    public int hashFunction(String key){
    	
    	char[] stringArray = key.toCharArray();
    	
    	double hash = 0;
    	
    	
    	//Use MAD compression to avoid collisions 
     	for(int i=0; i<stringArray.length; i++){
    		
     		//Cast the char value as an int and calculate
    		int  charInt = (int) stringArray[i]; 	
   
    		hash  = hash + (charInt*(Math.pow(37,i)));
	
    	}
     	
     	//Use MAD to avoid collisions
     	//hash =(7*hash+3) % dictSize;
     	hash =(hash) % dictSize;
     	
    	return (int) hash;
    	
    }
    
}
