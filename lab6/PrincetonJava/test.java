public class test {

	public static void add(int n) {
		if (n==0)	return;
			System.out.print("+" + n);
			add(n-1);

// while never gonna to work, there is dead loop at the one before last loop, at that loop
// while is good, we will never escape! we need if and return

//		while(n != 0){
//			System.out.print("+" + n);
//			add(n-1);
//		}

	}

	public static void main(String args[]) {
		add(10);
		System.out.print("\n");

	}
}