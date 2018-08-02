import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class lab9P3 {
	private HashMap<String,Integer> hmfre;
	public lab9P3() {
		hmfre = new HashMap<String, Integer>();
	}

	public void WordCount(String inputFile, String outputFile) {
   		try {
   			File file = new File(inputFile);
			Scanner sc = new Scanner(file);
 
  			while (sc.hasNextLine()) {
  				String line = sc.nextLine();
  				String[] stringarr = line.split("\\s+");
  				for (int i = 0; i < stringarr.length; i++) {
  					if (hmfre.containsKey(stringarr[i]))
           				hmfre.put(stringarr[i], hmfre.get(stringarr[i])+1);
           			else 
           			hmfre.put(stringarr[i], 1);
           		}
           	}
   		} catch (FileNotFoundException e) {	} 

   		try {
	        Set< Map.Entry<String,Integer> > st = hmfre.entrySet(); 
	        File file = new File(outputFile);
			PrintWriter printWriter = new PrintWriter(file); 
	      	for (Map.Entry<String,Integer> me:st) {
	      		String s = me.getKey();
	          	printWriter.printf("%11s", s +" : ");
	          	printWriter.println("          " + me.getValue());
	      	}
	    	printWriter.close (); 
		} catch (FileNotFoundException e) {	} 
	}



	public static void main(String args[]) {
		lab9P3 test = new lab9P3();
		test.WordCount("alice30.txt", "count.txt");
	}
}