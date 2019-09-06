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
	
	public void loadPlanets(Loader loader) {
		Planet mercury = new Planet(loader, 0.326f, "mercury",  307 + (sun.getScale() * 10), 466 + (sun.getScale() * 10), 330, sun);
		planets.add(mercury);
		
		Planet venus = new Planet(loader, 0.809f, "venus", 718 + (sun.getScale() * 10), 728 + (sun.getScale() * 10), 330, sun);
		planets.add(venus);
		
		Planet earth = new Planet(loader, 0.852f, "earth", 980 + (sun.getScale() * 10), 1010 + (sun.getScale() * 10), 330, sun);
		planets.add(earth);
				
		Planet mars = new Planet(loader, 0.4536f, "mars", 1380 + (sun.getScale() * 10), 1660 + (sun.getScale() * 10), 330, sun);
		planets.add(mars);
				
		Planet jupiter = new Planet(loader, 9.54f, "jupiter", 2950 + (sun.getScale() * 10), 3460 + (sun.getScale() * 10), 330, sun);
		planets.add(jupiter);
				
		Planet saturn = new Planet(loader, 8.06f, "saturn", 4005 + (sun.getScale() * 10), 5120 + (sun.getScale() * 10), 330, sun);
		planets.add(saturn);
				
		Planet uranus = new Planet(loader, 3.41f, "uranus", 8400 + (sun.getScale() * 10), 10100 + (sun.getScale() * 10), 330, sun);
		planets.add(uranus);
				
		Planet neptune = new Planet(loader, 3.24f, "neptune", 14800 + (sun.getScale() * 10), 15400 + (sun.getScale() * 10), 330, sun);
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