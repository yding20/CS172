public class Edge implements Comparable<Edge> {
	private int i;
	private int j;
	private double weight;
	double vx;
	double vy;
	double wx;
	double wy;

	public Edge(int i, int j, double weight, double vx, double vy, double wx, double wy) {
		this.i = i;
		this.j = j;
		this.vx = vx;
		this.vy = vy;
		this.wx = wx;
		this.wy = wy;
		this.weight = distance();
	}

    public int from() {
        return i;
    }

    public int to() {
		return j;
    }

	public double weight() {
        return weight;
    }

    public double getVX() {
    	return vx;
    }

    public double getVY() {
    	return vy;
    }

    public double getWX() {
    	return wx;
    }

    public double getWY() {
    	return wy;
    }

    private double distance () {

    	final int R = 6371; // Radius of the earth

    	double latDistance = Math.toRadians(wx - vx);
    	double lonDistance = Math.toRadians(wy - vy);
    	double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            	+ Math.cos(Math.toRadians(vy)) * Math.cos(Math.toRadians(wy))
            	* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    	double distance = R * c * 1000; // convert to meters

    	distance = Math.pow(distance, 2); 
    	return Math.sqrt(distance)*0.000621371; // to miles
}

	public int compareTo(Edge that) {
		return Double.compare(this.weight, that.weight);
	}
}