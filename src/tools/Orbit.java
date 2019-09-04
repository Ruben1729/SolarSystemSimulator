package tools;

import org.joml.Vector3f;

public class Orbit {

	private double a, b;
	private double x, y;
	
	private Vector3f center;
	private Vector3f foci;
	private Vector3f foci2;
	
	private double mass;
	
	private final double G = 6.67529;
	
	public Orbit(double peri, double apo, double mass, Vector3f foci) {
		this.a = (double)(apo + peri)/ 2;
		
		this.foci2 = new Vector3f(0);
		this.foci2.x = (float)((foci.x + apo) - peri);
		this.foci2.y = foci.y;
		
		double c = (foci2.x - foci.x)/2;
		
		this.b = Math.sqrt((Math.pow(a, 2) - Math.pow(c, 2)));
		
		this.center = new Vector3f(0, 0, 0);
		
		this.center.x = (float)(foci.x + c);
		this.center.y = foci.y;
		
		this.foci = foci;
		
		this.x = this.center.x - a;
		this.y = this.center.y;
		
		this.mass = mass;
	}
	
	public void tick() {
		
		if(this.x == this.center.x + a || this.y > this.center.y) {
			this.x -= 10;
			this.y = (Math.sqrt(1-(Math.pow(x - center.x, 2)/Math.pow(a, 2))) * b) + center.y;
		} else {
			this.x += 10;
			this.y = center.y - (Math.sqrt(1-(Math.pow(x - center.x, 2)/Math.pow(a, 2))) * b);
		}
		
	}
	
	public double getVel() {
		return Math.sqrt((G*mass)*((2/distanceFromOrbit()) - (1/a)));
	}
	
	public double distanceFromOrbit() {
		return Math.sqrt(Math.pow((x - foci.x), 2) - Math.pow((y - foci.y), 2));
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	
	public float equation() {
		return (float) ((Math.pow((x - this.center.x), 2)/Math.pow(a, 2)) + (Math.pow((y - this.center.y), 2)/Math.pow(b, 2)));
	}
	
	public float getA() {
		return (float) a;
	}
	
	public float getB() {
		return (float) b;
	}
}
