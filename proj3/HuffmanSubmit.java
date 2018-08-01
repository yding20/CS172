import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.PriorityQueue;


public class HuffmanSubmit implements Huffman {
    HuffNode root;
    PriorityQueue<HuffNode> pq;

	private class HuffNode implements Comparable<HuffNode>{
		private char element;
		private int freq;
	    private HuffNode left;
	    private HuffNode right;
	    
	    HuffNode(char c, int count, HuffNode left, HuffNode right) {
	    	element = c;
	    	freq = count;
	        this.left = left;
	        this.right = right;
	    }

	    private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

	    public int compareTo(HuffNode that) {
	    	int cmp = this.freq - that.freq;
	    	if (cmp <= 0)	return -1;
            else return 1;
        }
	}

	public HuffmanSubmit() {
        pq = new PriorityQueue<HuffNode>();
	}
  
   	public void encode(String inputFile, String outputFile, String freqFile) {
   		HashMap<Character,Integer> hmfre = new HashMap<Character, Integer>();
   		BinaryIn  in  = new BinaryIn(inputFile);
   		while (!in.isEmpty()) {
           	char c = in.readChar();
           	if (hmfre.containsKey(c))
           		hmfre.put(c, hmfre.get(c)+1);
           	else 
           		hmfre.put(c, 1);
       	}
        // buildtree using hmfre
        buildtree(hmfre);
//		preorder(root);
        // build symbol table that store<char, huffcode> pair
		HashMap<Character,String> newcode = new HashMap<Character, String>();
       	buildcode(newcode, root, "");
//		showTable(newcode);
        // write out freq file in hmfre
		try {
            Set< Map.Entry<Character,Integer> > st = hmfre.entrySet(); 
			PrintWriter printWriter = new PrintWriter(freqFile); 
       		for (Map.Entry<Character,Integer> me:st) {
       			String binary = Integer.toBinaryString(me.getKey());
           		printWriter.printf("%11s", binary +" : ");
           		printWriter.println("          " + me.getValue());
       		}
        	printWriter.close (); 
		} catch (FileNotFoundException e) {	} 
        // re-read in the file, look up of every char from Hashmap<char, huffcode> newcode.
		BinaryOut out = new BinaryOut(outputFile);
		BinaryIn  in2  = new BinaryIn(inputFile);
		while (!in2.isEmpty()) {
           	char c = in2.readChar();
           	String huffcode = newcode.get(c);
           	for (int i = 0; i < huffcode.length(); i++) {
           		if (huffcode.charAt(i) == '0')	 out.write(false);
           		else 							               out.write(true);
           	}
       	}
       	out.flush();
   	}

   	public void decode(String inputFile, String outputFile, String freqFile) {
   		
        // Read freq file and insert into priority queue
        HashMap<Character,Integer> hmfre = new HashMap<Character, Integer>();
   		try {
   			File file = new File(freqFile);
			Scanner sc = new Scanner(file);
 
  			while (sc.hasNextLine()) {
  				String line = sc.nextLine();
  				line = line.replace(" ", "");
    			String binaryelement = line.split(":")[0];
    			String freqs = line.split(":")[1];

    			char c  = (char) Integer.parseInt(binaryelement, 2);
    			int freqint = Integer.parseInt(freqs);
    			hmfre.put(c, freqint);
	  			}
   		} catch (FileNotFoundException e) {	} 

        // buildtree using hmfre
        buildtree(hmfre);

       	HuffNode rt = root;

        // Read the Huffman ecoded file and decode it into original
       	BinaryIn  in  = new BinaryIn(inputFile);
        BinaryOut out = new BinaryOut(outputFile);
		      while (!in.isEmpty()) {
           	boolean c = in.readBoolean();
           		if (c == false)	rt = rt.left;
           		else 			rt = rt.right;
           		if (rt.isLeaf())	{
           			out.write(rt.element);
                rt = root;
           		}
       	}
       	out.flush();

   	}

    private void preorder(HuffNode rt) {
        if (rt == null) return;
        System.out.println(rt.freq + "*****" +Integer.toBinaryString(rt.element));
        preorder(rt.left);
        preorder(rt.right);
    }

    private void buildtree(HashMap<Character,Integer> hmfre) {
        Set< Map.Entry<Character,Integer> > st = hmfre.entrySet(); 
        for (Map.Entry<Character,Integer> me:st) {
            pq.add(new HuffNode(me.getKey(), me.getValue(), null, null));
        }

        while (pq.size() > 1) {
            HuffNode left = pq.remove();
            HuffNode right = pq.remove();
            HuffNode parent = new HuffNode('\0', left.freq + right.freq, left, right);
            pq.add(parent);
        }
        root = pq.remove();
    }

    private void buildcode(HashMap<Character,String> newcode, HuffNode rt, String s) {
        if (!rt.isLeaf()) {
            buildcode(newcode, rt.left, s+'0');
            buildcode(newcode, rt.right, s+'1');
        } else
            newcode.put(rt.element, s);
    }

    private void showTable(HashMap<Character,String> newcode) {
        Set< Map.Entry<Character,String> > newcodetable = newcode.entrySet(); 
        for (Map.Entry<Character,String> instan :newcodetable) {
            System.out.println(Integer.toBinaryString(instan.getKey()) + "****" + instan.getValue());
        }
    }


   	public static void main(String[] args) {
      	Huffman  huffman = new HuffmanSubmit();
//		Scanner scan = new Scanner(System.in);
//
//		System.out.println("Input the file :   ");
//		String inputString = scan.nextLine();
//
//		System.out.println("Output the file :   ");
//		String outputString = scan.nextLine();

        huffman.encode("alice30.txt", "alice30.enc", "alicefreq.txt");
        huffman.decode("alice30.enc", "alice30_dec.txt", "alicefreq.txt");

        huffman.encode("ur.jpg", "ur.enc", "urfreq.txt");
        huffman.decode("ur.enc", "ur_dec.jpg", "urfreq.txt");
   	}

}










