public class lab6_1 {

   private static int makeChange(int total, int denom, int level) {
    	int nextCoin = 0;
    	switch (denom) {
    	case 25:
    		level = 1;
    		nextCoin = 10;
    		break;
    	case 10:
    		level = 2;
    		nextCoin = 5;
    		break;
    	case 5:
    		level = 3;
    		nextCoin = 1;
    		break;
    	case 1:
    		return 1; 
    	default:   	
    		return 0;
    	}
    	
    	int ways = 0;
    	for (int count = 0; count*denom <= total; ++count) {
    		if (level == 1)
    			System.out.println("The following combination include" + count + "个" + "25");

    		if (level == 2) {
    			System.out.println("    The following combination include" + count + "个" + "10");  			
    		}

    		int n = makeChange(total-count*denom, nextCoin, level);
    		ways += n;
    		if (n == 1) {
    			int m = total-count*denom;
    			if (level == 3) {
    				System.out.print(ways + " : " + "        " + m + "个" + "1");
    				System.out.print(" + " + count + "个" + "5");
    			}
    		}
    		if (level == 3)
    			System.out.print("\n");

    	}
    	return ways;
    }
    
    public static int makeChange(int total) {
    	if (total < 0) return 0;
    	// Select coins with the biggest denomination first.
    	return makeChange(total, 25, 0);
    }

	public static void main(String args[]) {
		int i = makeChange(45);
		System.out.print("Total conbination is : " + i);
		System.out.print("\n");
	}
}

// public static int makeChange(int n, int denom) {
//     int next_denom = 0;
//     switch (denom) {
//     case 25:
//         next_denom = 10;
//         break;
//     case 10:
//         next_denom = 5;
//         break;
//     case 5:
//         next_denom = 1;
//         break;
//     case 1:
//         return 1;
//     }
//     int ways = 0;
//     for (int i = 0; i * denom <= n; i++) {
//         ways += makeChange(n - i * denom, next_denom);
//     }
//     return ways;
// }
