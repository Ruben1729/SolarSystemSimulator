package entities;
import org.joml.Vector3f;

import model.TexturedModel;

public class Sun extends Entity{

	private int radius = 10;
	private float speed;
	
	public Sun(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
		super(model, position, rotation, scale);
		// TODO Auto-generated constructor stub
	}
	
	public void tick(float delta) {
		
	}
	
	//GETTERS AND SETTERS
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public float getRadius() {
		return radius * scale;
	}
	
}
