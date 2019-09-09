package entities;

import org.joml.Vector3f;

import core.MainLoop;
import model.TexturedModel;
import render.Loader;
import render.OBJLoader;
import tools.Orbit;

public class Planet extends Entity{

	private Orbit orbit;
	private float rotationSpeed = 100.0f;
	private float dayLength = 24.0f;
	private float speed;
	
	private float radius = 10;
	
	public Planet(Loader loader, float scale, String planetName, float peri, float apo, float mass, Vector3f rot, Sun target) {
		super(getTexturedModel("/" + planetName + "/" + planetName, loader, planetName + ".png"), new Vector3f(), rot, scale);
		
		Orbit orbit = new Orbit(peri + target.getRadius() + getRadius(), apo + getRadius() + target.getRadius(), mass, target.getPosition());
		
		setPosition(new Vector3f((float)orbit.getX(), 0, (float)orbit.getZ()));
		
		this.orbit = orbit;
		// TODO Auto-generated constructor stub
	}
	
	public void tick(float delta) {
		
		orbit.tick(delta);
		this.setPosition(new Vector3f((float) orbit.getX(), 0, (float) orbit.getZ()));
		
		// Rotate the planet
		increaseRotation(new Vector3f(0, (rotationSpeed * delta * MainLoop.daySpeed/dayLength), 0));
		
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
	
	public void setRotationSpeed(float speed) {
		this.rotationSpeed = speed;
	}
	
	public void setDayLength(float length) {
		this.dayLength = length;
	}
	
	public float getRadius() {
		return radius * scale;
	}
	
}
