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
		this.weight = weight;
		this.vx = vx;
		this.vy = vy;
		this.wx = wx;
		this.wy = wy;
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

	public int compareTo(Edge that) {
		return Double.compare(this.weight, that.weight);
	}
}