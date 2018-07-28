public class BinaryCounter {
	private int N;
 	private int[] a; // bits (0 or 1)

 	public BinaryCounter(int N) {
 		this.N = N;
 		a = new int[N];
// 		for (int i = 0; i < N; i++)
// 			a[i] = i;
 			enumerate(0);
 	}

 	 private void process() {
 		for (int i = 0; i < N; i++)
 		System.out.print(a[i] + " ");
 		System.out.println();
 	}

	private void enumerate(int k) {
		//for (int i: a)
		//	System.out.print(i);
		//System.out.println("input" + k);

 		if (k == N) { 
 			process(); 
 			return; 
 		}
 		enumerate(k+1);
 		a[k] = 1;
 		enumerate(k+1);
 		a[k] = 0;
	}

	public static void main(String[] args) {
 		int N = Integer.parseInt(args[0]);
 		BinaryCounter test = new BinaryCounter(N);
 	}
}