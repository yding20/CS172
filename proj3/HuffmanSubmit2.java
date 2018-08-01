import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.PriorityQueue;


public class HuffmanSubmit implements Huffman {

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

	}
  
	// Feel free to add more methods and variables as required. 
   	public void encode(String inputFile, String outputFile, String freqFile) {
   		HashMap<Character,Integer> hmfre = new HashMap<Character, Integer>();
		PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>();
   		BinaryIn  in  = new BinaryIn(inputFile);
   		while (!in.isEmpty()) {
           	char c = in.readChar();
           	if (hmfre.containsKey(c))
           		hmfre.put(c, hmfre.get(c)+1);
           	else 
           		hmfre.put(c, 1);
       	}

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
       	HuffNode root = pq.remove();
//		preorder(root);

		HashMap<Character,String> newcode = new HashMap<Character, String>();
       	buildcode(newcode, root, "");
		showTable(newcode);

		try {
			PrintWriter printWriter = new PrintWriter(freqFile); 
       		for (Map.Entry<Character,Integer> me:st) {
       			String binary = Integer.toBinaryString(me.getKey());
           		printWriter.printf("%11s", binary +" : ");
           		printWriter.println("          " + me.getValue());
       		}
        	printWriter.close (); 
		} catch (FileNotFoundException e) {	} 

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

   	private void preorder(HuffNode rt) {
   		if (rt == null)	return;
   		System.out.println(rt.freq + "*****" +Integer.toBinaryString(rt.element));
   		preorder(rt.left);
   		preorder(rt.right);
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

   	public void decode(String inputFile, String outputFile, String freqFile) {
   		HashMap<Character,Integer> hmfre = new HashMap<Character, Integer>();
   		PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>();
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
//    			System.out.println(c + "***" + freqint);
	  			}
   		} catch (FileNotFoundException e) {	} 

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
       	HuffNode root = pq.remove();
       	HuffNode rt = root;
//		    preorder(root);

        HashMap<Character,String> newcode = new HashMap<Character, String>();
        buildcode(newcode, root, "");
        System.out.println("Decode");
        showTable(newcode);

       	BinaryIn  in  = new BinaryIn(inputFile);
        BinaryOut out = new BinaryOut(outputFile);
		      while (!in.isEmpty()) {
           	boolean c = in.readBoolean();
           		if (c == false)	rt = rt.left;
           		else 			rt = rt.right;
           		if (rt.isLeaf())	{
                //System.out.print(rt.element);
           			out.write(rt.element);
                rt = root;
           		}
       	}
       	out.flush();

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

    huffman.encode("test.txt", "test.enc", "freq.txt");
    huffman.decode("test.enc", "test_dec.txt", "freq.txt");


		// After decoding, both ur.jpg and ur_dec.jpg should be the same. 
		// On linux and mac, you can use `diff' command to check if they are the same. 
   	}

}










