import java.util.*;
import java.io.*;

public class URcalculator {

	private Stack<String> stack;
	private Queue<String> queue;
	Stack<String> Ostack;
	HashMap<String,Double> hm;
	private String[] inputarray;
	private int countP;
	private int countE;

	public URcalculator() {
		stack = new Stack<String>();
		queue = new LinkedList<String>();
		hm = new HashMap<String,Double>();
		Ostack = new Stack<String>();
	}

	public void infixTOposfix(String inputs) {
		inputs = inputs.replace(" ", "");
		inputarray = inputs.split("=");

		countP = 0;
		countE = 0;
		for (int i = 0; i < inputs.length(); i++) 
			if (inputs.charAt(i) == '=') {
				countP = i;
				countE++;
			}
		String s;
		if (countP != 0)
			s = new String(inputs.substring(countP+1));
		else 
			s = inputs;
		//System.out.println(s);

		int j = 0;
		char curr;
		char next;
		String e = new String();
		for (int i = 0; i < s.length(); i++) {

			if (i != s.length()-1 ) {
				curr = s.charAt(i);
				next = s.charAt(i+1);
			} else {
				curr = s.charAt(i);
				next = '@';				
			}

			if (!Character.isWhitespace(curr)) {
				if (Character.isDigit(curr) && !Character.isDigit(next) &&  next != '.') {
					e = s.substring(j,i+1);
					j = j+ e.length();
					queue.add(e);
					//System.out.println(e);
				} else if (Character.isAlphabetic(curr) && !Character.isAlphabetic(next) &&  next != '.') {
					e = s.substring(j,i+1);
					j = j+ e.length();
					queue.add(e);
					//System.out.println(e);
				}
	
				if (curr == '+' || curr == '-' || curr == '*' || curr == '/' || curr == '(' 
									|| curr == ')' || curr == '[' || curr == ']' || curr == '{' || curr == '}') {
					e = s.substring(i,i+1);
					j = j+ e.length();
					inTopos(e);
					//System.out.println(e);
				} 
			} else {
				j++;
			}
		}
		while(!stack.isEmpty())
			queue.add(stack.pop());
	}

	public void inTopos(String s) {
		if (s.equals("(")) {
			stack.push(s);
		} else if (s.equals(")")) {

			while (!stack.isEmpty() && !stack.peek().equals("(")) 
				queue.add(stack.pop());
			stack.pop();

		} else {

			while (!stack.isEmpty() && getSeq(s) <= getSeq(stack.peek()))
				queue.add(stack.pop());
			stack.push(s);
		}
	}

	public int getSeq(String s) {
        switch (s)
        {
        case "+":
        case "-":
            return 1;
      
        case "*":
        case "/":
            return 2;
      
        }
        return -1;
	}

	public Iterable<String> getStack() {
		return stack;
	}

	public Iterable<String> getQueue() {
		return queue;
	}

	public double URcalculate() {

		while (!queue.isEmpty()) {
			String s = queue.poll();
			if (s.equals("+")) {
				Double doubleOp1 = getOp1();
				Double doubleOp2 = getOp1();
				Ostack.push(Double.toString(doubleOp2 + doubleOp1));
			} else if (s.equals("-")) {
				Double doubleOp1 = getOp1();
				Double doubleOp2 = getOp1();
				Ostack.push(Double.toString(doubleOp2 - doubleOp1));
			} else if (s.equals("*")) {
				Double doubleOp1 = getOp1();
				Double doubleOp2 = getOp1();
				Ostack.push(Double.toString(doubleOp2 * doubleOp1));
			} else if (s.equals("/")) {
				Double doubleOp1 = getOp1();
				Double doubleOp2 = getOp1();
				Ostack.push(Double.toString(doubleOp2 / doubleOp1));
			} else if (s.equals("=")) {
				String operand1 = Ostack.pop();
				String operand2 = Ostack.peek();

				double doubleOp1;
				if (Character.isAlphabetic(operand1.charAt(0))) {
					if (hm.containsKey(operand1))
						doubleOp1 = hm.get(operand1);
					else throw new IllegalStateException(operand1 + "has not been given value");
				} else {
					doubleOp1 = Double.parseDouble(operand1);
				}
				hm.put(operand2, doubleOp1);

			} else if (Character.isAlphabetic(s.charAt(0))) {
				Ostack.push(s);
			} else {
				Ostack.push(s); 
			}
		}
		double output;
		if (Character.isAlphabetic(Ostack.peek().charAt(0)))
			output = hm.get(Ostack.pop());
		else 
			output = Double.parseDouble(Ostack.pop());

		for (int i = 0; i < countE; i++) {
			hm.put(inputarray[i], output);
			//System.out.println("The value of **" + inputarray[i] + "** is " + output);
		}

		stack = new Stack<String>();
		queue = new LinkedList<String>();
		Ostack = new Stack<String>();
		return  output;
	}

	private Double getOp1() {
		String operand1 = Ostack.pop();
		double doubleOp1;
		if (Character.isAlphabetic(operand1.charAt(0))) {
			doubleOp1 = hm.get(operand1);
		} else {
			doubleOp1 = Double.parseDouble(operand1);
		}
		return doubleOp1;
	}

	private Double getOp2() {
		String operand2 = Ostack.pop();
		double doubleOp2;
		if (Character.isAlphabetic(operand2.charAt(0))) {
			doubleOp2 = hm.get(operand2);
		} else {
			doubleOp2 = Double.parseDouble(operand2);
		}
		return doubleOp2;
	}


	public void showAll() {
		Set< Map.Entry<String,Double> > st = hm.entrySet();  

       	for (Map.Entry<String,Double> me:st) {
           	System.out.print("    " + me.getKey()+" : ");
           	System.out.println(me.getValue());
       	}
	}

	public void clearAll() {
		hm.clear();
	}

	public void clear(String s) {
		hm.remove(s);
	}


	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		URcalculator test = new URcalculator();

		for (int i = 0; i < 20; i++) {
			String inputString = scan.nextLine();

			if (inputString.substring(0, 4).equals("clea")) {
				String s = inputString.split(" ")[1];
				if (s.equals("all"))	test.clearAll();
				else 					test.clear(s);
			} else if (inputString.substring(0, 4).equals("show")) {
				test.showAll();
			} else if (inputString.substring(0, 4).equals("exit")) {
				break;
			} else {
				test.infixTOposfix(inputString);
				System.out.println("The results is : " + test.URcalculate());
			}
		}

		System.out.println("Thank you for choosing URcalculator ! ");


	}
}