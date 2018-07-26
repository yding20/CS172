public class Rooks {
	private int N;
 	private int[] a; // bits (0 or 1)

 	public Rooks(int N) {
 		this.N = N;
 		a = new int[N];
 		for (int i = 0; i < N; i++)
 			a[i] = i;
 			enumerate(0);
 	}

 	 private void process() {
 		for (int i = 0; i < N; i++)
 		System.out.print(a[i] + " ");
 		System.out.println();
 	}

	private void enumerate(int k) {
 		if (k == N){ 
 			process(); return; 
 		}
 		for (int i = k; i < N; i++)	{
 			exch(k, i);
 			enumerate(k+1);
 			exch(i, k);
 		}
	}

 	private void exch(int i, int j) { 
 		int t = a[i];
 		a[i] = a[j];
 		a[j] = t; 
 	}

	public static void main(String[] args) {
 		int N = Integer.parseInt(args[0]);
 		Rooks test = new Rooks(N);
 	}
}