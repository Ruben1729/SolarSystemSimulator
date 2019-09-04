package entities;

import org.joml.Vector3f;

import model.TexturedModel;
import tools.Orbit;

public class Planet extends Entity{

	private Orbit orbit;
	
	public Planet(TexturedModel model, Vector3f position, Vector3f rotation, float scale, Orbit orbit) {
		super(model, position, rotation, scale);
		this.orbit = orbit;
		// TODO Auto-generated constructor stub
	}

	private float speed;
	
	
	public void tick() {
		
		orbit.tick();
		this.setPosition(new Vector3f((float) orbit.getX(), (float) orbit.getY(), 0));
		// updateVelocity();
		// translate(getVelocity());
		
	}
	
	/**
	 * Method that updates the velocity of the player with the given speed.
	 */
	public void updateVelocity() {
		
		Vector3f direction = new Vector3f(0);
		
//		if(KeyboardInput.isKeyDown(Keybinds.PLYR_FORWARD))
//			direction.add(new Vector3f(0, 0, -1));
//		
//		if(KeyboardInput.isKeyDown(Keybinds.PLYR_BACKWARD))
//			direction.add(new Vector3f(0, 0, 1));
//
//		
//		if(KeyboardInput.isKeyDown(Keybinds.PLYR_LEFT))
//			direction.add(new Vector3f(-1, 0, 0));
//
//		
//		if(KeyboardInput.isKeyDown(Keybinds.PLYR_RIGHT))
//			direction.add(new Vector3f(1, 0, 0));
		
//		direction.mul(speed);
//		setVelocity(direction.mulTransposePosition(Game.getCamera().getYawTransform()));
		
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
