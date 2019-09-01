package math;

public class Vec3f {
	private double[] vector;
	
	public Vec3f() {
		vector = new double[3];
	}
	
	public Vec3f(double x) {
		vector = new double[] {x, x, x};
	}
	
	public Vec3f(Vec3f v) {
		vector = v.getVector();
	}
	
	public Vec3f(double[] array) {
		vector = array;
	}
	
	public Vec3f(double x, double y, double z) {
		vector = new double[] {x, y, z};	
	}
	
	public void add(Vec3f v) {
		for(int i = 0; i < 3; i ++) {
			vector[i] += v.getVector()[i];
		}
	}
	
	public void subtract(Vec3f v) {
		for(int i = 0; i < 3; i ++) {
			vector[i] -= v.getVector()[i];
		}
	}
	
	// Getters
	public double[] getVector() {
		return vector;
	}
	
}
