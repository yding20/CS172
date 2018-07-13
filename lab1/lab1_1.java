import java.util.*;

public class lab1_1 {

	private String s1;
	private String s2;

	public lab1_1(String s1, String s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	public boolean isAnagram() {
		String[] s1arr = new String[s1.length()];
		String[] s2arr = new String[s2.length()];
		s1arr = s1.split("");
		s2arr = s2.split("");
			
		Arrays.sort(s1arr);	
		Arrays.sort(s2arr);

		if (s1.length() != s2.length()) {
			return false;
		} else {
			// Following two compare method, choose either one
			//if (!Arrays.equals(s1arr, s2arr)) return false;
			for (int i = 0; i < s1.length(); i++ ) {
				if (!s1arr[i].equals(s2arr[i])) return false;
			}
		}
		return true;
	}

	public boolean isRotation () {
		String[] s1arr = new String[s1.length()];
		String[] s2arr = new String[s2.length()];
		s1arr = s1.split("");
		s2arr = s2.split("");

		for (int j = 0; j < s1.length(); j++) {

			String[] s1arrcopy = Arrays.copyOf(s1arr, s1.length());
			for (int i = 0; i <s1.length(); i++ ) {
				if (i == 0)    s1arr[0] = s1arrcopy[s1.length() - 1 ];
				else           s1arr[i] = s1arrcopy[i-1];
			}

			if (Arrays.equals(s1arr, s2arr)) 	return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String s1 = args[0];
		String s2 = args[1];
		System.out.println(s1 + "******" + s2);

		lab1_1 test = new lab1_1(s1, s2);
		System.out.println("isAnagram :  " + test.isAnagram());

		System.out.println("isRotation : " + test.isRotation());

	}
}

