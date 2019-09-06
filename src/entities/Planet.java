package entities;

import org.joml.Vector3f;

import model.TexturedModel;
import render.Loader;
import render.OBJLoader;
import tools.Orbit;

public class Planet extends Entity{

	private Orbit orbit;
	
	public Planet(Loader loader, float scale, String planetName, float peri, float apo, float mass, Entity target) {
		super(getTexturedModel("/" + planetName + "/" + planetName, loader, planetName + ".png"), new Vector3f(), new Vector3f(), scale);
		
		Orbit orbit = new Orbit(peri, apo, mass, target.getPosition());
		
		setPosition(new Vector3f((float)orbit.getX(), 0, (float)orbit.getZ()));
		
		this.orbit = orbit;
		// TODO Auto-generated constructor stub
	}

	private float speed;
	
	
	public void tick() {
		
		orbit.tick();
		this.setPosition(new Vector3f((float) orbit.getX(), 0, (float) orbit.getZ()));
		// updateVelocity();
		// translate(getVelocity());
		
	}
	
	/**
	 * Method that updates the velocity of the player with the given speed.
	 */
	public void updateVelocity() {
		
		Vector3f direction = new Vector3f(0);
			
	}
	
	private static TexturedModel getTexturedModel(String path, Loader loader, String textureName) {
		return new TexturedModel(OBJLoader.loadObjModel(path, loader), loader.loadTexture(textureName, true));
	}
	
	//GETTERS AND SETTERS
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public Orbit getOrbit() {
		return orbit;
	}
	
}
