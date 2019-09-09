package tools;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import entities.Planet;

public class Orbit {

	private float a, b, c;
	private Vector3f pos;
	
	private float apo, peri;
	
	private double alpha;
	
	private Vector3f center;
	private Vector3f focus;
	private Vector3f focus2;
	
	private double mass;
	
	private final double G = 6.67529;
	
	public Orbit(double peri, double apo, double mass, Vector3f focus) {
		this.a = (float)(apo + peri)/ 2;
		
		this.focus2 = new Vector3f(0);
		this.focus2.x = (float)((focus.x + apo) - peri);
		this.focus2.z = focus.z;
		
		this.c = (focus2.x - focus.x)/2;
		
		this.b = (float)Math.sqrt((Math.pow(a, 2) - Math.pow(c, 2)));
		
		this.center = new Vector3f(0, 0, 0);
		
		this.center.x = (float)(focus.x + c);
		this.center.z = focus.z;
		
		this.apo = (float) apo;
		this.peri = (float) peri;
		
		this.focus = focus;
		
		this.pos = new Vector3f((float)(this.center.x - a), 0.0f, (float)this.center.z);
		
		this.mass = mass;
		
		this.alpha = 0;
	}
	
	public void tick(float delta) {
		center = new Vector3f(focus.x + c, focus.z, 0);
		
		float dist = distance(pos, center);
		float deltaAlpha = (float)Math.asin(getVel()/dist);
		
		pos = new Vector3f((float)((center.x + a) * Math.cos(alpha)), 0, (float)((center.z + b) * Math.sin(alpha)));

		alpha += (deltaAlpha * 0.01);
		
	}
	
	public List getAllPoints() {
		
		center = new Vector3f(focus.x + c, focus.z, 0);
		List<Vector3f> points = new ArrayList<Vector3f>();
		
		for(float beta = 0; beta < 360; beta += 0.01 ) {
			pos = new Vector3f((float)((center.x + a) * Math.cos(Math.toRadians(beta))), 0, (float)((center.z + b) * Math.sin(Math.toRadians(beta))));
			points.add(pos);
		}
		return points;
	}
	
	public float getZGivenX(float x) {
		return (float)(this.center.z + (b * (Math.sqrt(1 - ((Math.pow(x, 2) - Math.pow(center.x, 2)/Math.pow(a, 2)))))));
	}
	public float getNegZGivenX(float x) {
		return (float)(this.center.z - (b * (Math.sqrt(1 - ((Math.pow(x, 2) - Math.pow(center.x, 2)/Math.pow(a, 2)))))));
	}
	
	public double getVel() {
		return Math.sqrt((G*(mass*1e4))*(Math.abs(2/distanceFromOrbit()) - (1/a)));
	}
	
	public double distanceFromOrbit() {
		return distance(pos, focus);
	}
	
	public float distance(Vector3f pos1, Vector3f pos2) {
		return (float)Math.sqrt(Math.pow((pos1.x - pos2.x), 2) + Math.pow((pos1.z - pos2.z), 2));
	}

	public double getX() {
		return this.pos.x;
	}
	
	public double getZ() {
		return this.pos.z;
	}
	
	public float equation() {
		return (float) ((Math.pow((this.pos.x - this.center.x), 2)/Math.pow(a, 2)) + (Math.pow((this.pos.z - this.center.z), 2)/Math.pow(b, 2)));
	}
	
	public float getA() {
		return (float) a;
	}
	
	public float getB() {
		return (float) b;
	}
}
