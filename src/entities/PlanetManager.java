package entities;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import model.Primitive;
import render.Loader;

public class PlanetManager {
	
	private List<Planet> planets = new ArrayList<Planet>();
	private Planet currentTarget;
	private Sun sun;
	
	public PlanetManager(Sun sun) {
		this.sun = sun;
	}
	
	public void push(Planet planet) {
		planets.add(planet);
	}
	
	public void updateTarget() {
		currentTarget = planets.get((planets.indexOf(currentTarget) + 1) % planets.size());
	}
	
	public void updateTarget(int dir) {
		currentTarget = planets.get(Math.abs(planets.indexOf(currentTarget) + dir) % planets.size());
	}
	
	public void loadPlanets(Loader loader) {
		Planet mercury = new Planet(loader, 0.4016f, "mercury", 300.7f + (0.4016f * 0.00812f) + (sun.getScale() * 0.0737f), 460.6f + (0.4016f * 0.00812f) + (sun.getScale() * 0.00737f), 3.3f, new Vector3f(0.03f, 0, 0), sun);
		mercury.setDayLength(1407.5f);
		planets.add(mercury);
		
		Planet venus = new Planet(loader, 1.005f, "venus", 710.8f + (1.005f * 0.00805f) + (sun.getScale() * 0.00737f), 720.8f + (1.005f * 0.00805f) + (sun.getScale() * 0.00737f), 48.7f, new Vector3f(177.4f, 0, 0), sun);
		venus.setDayLength(5832.4333333333f);
		planets.add(venus);
		
		Planet earth = new Planet(loader, 1.1278f, "earth", 980f + (1.1278f * 0.00756f) + (sun.getScale() * 0.00737f), 1010f + (1.1278f * 0.00756f) + (sun.getScale() * 0.00737f), 59.7f, new Vector3f(23.4f, 0, 0), sun);
		earth.setDayLength(23.9333333f);
		planets.add(earth);
				
		Planet mars = new Planet(loader, 0.5566f, "mars", 1380f + (0.5566f * 0.00814f) + (sun.getScale() * 0.00737f), 1660f + (0.5566f * 0.00814f) + (sun.getScale() * 0.00737f), 6.42f, new Vector3f(25.2f, 0, 0), sun);
		mars.setDayLength(24.6f);
		planets.add(mars);
				
		Planet jupiter = new Planet(loader, 11.8437f, "jupiter", 4950f + (11.8437f * 0.00807f) + (sun.getScale() * 0.00737f), 5460f + (11.8437f * 0.00807f) + (sun.getScale() * 0.00737f), 1898, new Vector3f(3.1f, 0, 0), sun);
		jupiter.setDayLength(9.9166667f);
		planets.add(jupiter);
				
		Planet saturn = new Planet(loader, 9.9596f, "saturn", 9050f + (9.9596f * 0.00809f) + (sun.getScale() * 0.00737f), 10120f + (9.9596f * 0.00809f) + (sun.getScale() * 0.00737f), 568, new Vector3f(26.7f, 0, 0), sun);
		saturn.setDayLength(10.55f);
		planets.add(saturn);
				
		Planet uranus = new Planet(loader, 4.2172f, "uranus", 18400f + (4.2172f * 0.00804f) + (sun.getScale() * 0.00737f), 20100f + (4.2172f * 0.00804f) + (sun.getScale() * 0.00737f), 86.8f, new Vector3f(-97.8f, 0, 0), sun);
		uranus.setDayLength(17.2333333f);
		planets.add(uranus);
				
		Planet neptune = new Planet(loader, 4.0389f, "neptune", 29800f + (4.0389f * 0.00815f) + (sun.getScale() * 0.00737f), 30400f + (4.0389f * 0.00815f) + (sun.getScale() * 0.00737f), 102, new Vector3f(28.3f, 0, 0), sun);
		neptune.setDayLength(16f);
		planets.add(neptune);
			
		currentTarget = planets.get(0);
	}
	
	public ArrayList<Primitive> getDrawnOrbits(){
		ArrayList<Primitive> lines = new ArrayList<Primitive>();
		for(Planet p : planets) {
			lines.add(new Primitive(GL_LINE_LOOP, p.getOrbit().getAllPoints(), new Vector3f(0.5f, 0.5f, 0.5f)));
		}
		
		return lines;
	}
	
	public List<Planet> getPlanets() {
		return planets;
	}
	
	public Planet getCurrentTarget() {
		return currentTarget;
	}
	
	public Vector3f getCurrentTargetPosition() {
		return currentTarget.getPosition();
	}
}