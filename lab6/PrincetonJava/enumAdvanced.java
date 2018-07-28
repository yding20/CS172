public class enumAdvanced {
	private int N;
 	private int[] a; // bits (0 or 1)

 	public enumAdvanced(int N) {
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
 		for (int r = 0; r < 2; r++) {
 			a[k] = r;
			enumerate(k+1);
		}
		
	}

	public static void main(String[] args) {
 		int N = Integer.parseInt(args[0]);
 		enumAdvanced test = new enumAdvanced(N);
 	}
}