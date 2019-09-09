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
		Planet mercury = new Planet(loader, 0.0063f, "mercury", 307f, 466f, 0.33f, new Vector3f(0.03f, 0, 0), sun);
		mercury.setDayLength(1407.5f);
		planets.add(mercury);
		
		Planet venus = new Planet(loader, 0.0405f, "venus", 718f, 728f, 4.87f, new Vector3f(177.4f, 0, 0), sun);
		venus.setDayLength(5832.4333333333f);
		planets.add(venus);
		
		Planet earth = new Planet(loader, 0.0425f, "earth", 980f, 1010f, 5.97f, new Vector3f(23.4f, 0, 0), sun);
		earth.setDayLength(23.9333333f);
		planets.add(earth);
				
		Planet mars = new Planet(loader, 0.0226f, "mars", 1380f, 1660, 0.642f, new Vector3f(25.2f, 0, 0), sun);
		mars.setDayLength(24.6f);
		planets.add(mars);
				
		Planet jupiter = new Planet(loader, 0.04673f, "jupiter", 4950, 5460, 1898, new Vector3f(3.1f, 0, 0), sun);
		jupiter.setDayLength(9.9166667f);
		planets.add(jupiter);
				
		Planet saturn = new Planet(loader, 0.03892f, "saturn", 9050, 10120, 568, new Vector3f(26.7f, 0, 0), sun);
		saturn.setDayLength(10.55f);
		planets.add(saturn);
				
		Planet uranus = new Planet(loader, 0.01695f, "uranus", 18400, 20100, 86.8f, new Vector3f(-97.8f, 0, 0), sun);
		uranus.setDayLength(17.2333333f);
		planets.add(uranus);
				
		Planet neptune = new Planet(loader, 0.01645f, "neptune", 29800, 30400, 102, new Vector3f(28.3f, 0, 0), sun);
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