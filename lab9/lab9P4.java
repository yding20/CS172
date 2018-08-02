import java.util.PriorityQueue;

public class lab9P4 {
	PriorityQueue<Integer> pq;
	public lab9P4() {
		pq = new PriorityQueue<Integer>();
	}
	public int Jesse_cookies(int[] cookies, int k) {
		int count = 0;
		for (int i = 0; i < cookies.length; i++)
			pq.add(cookies[i]);

		while (pq.peek() < k && pq.size() > 1) {
			int cookie1 = pq.poll();
			int cookie2 = pq.poll();
			pq.add(cookie1+2*cookie2);
//			System.out.println(pq.peek());
			count++;
		}
		if (pq.peek() >= k)	return count;
		else				return -1;
	}

	public static void main(String args[]) {
		lab9P4 test = new lab9P4();
		int[] arr = {1,2,3,4,5,6,7,8,9,10};
		System.out.println(test.Jesse_cookies(arr, 4));
	}
}