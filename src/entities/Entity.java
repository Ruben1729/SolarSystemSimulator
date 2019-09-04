package entities;

import org.joml.Vector3f;

import model.TexturedModel;

public class Entity {
	
	private TexturedModel model;
	protected Vector3f position, velocity, rotation;
	private float scale;
	
	public Entity(TexturedModel model, Vector3f position, Vector3f rotation,
			float scale) {
		super();
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.velocity = new Vector3f(0);
	}
	
	public void increasePosition(Vector3f d) {
		this.position.add(d);
	}
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.add(new Vector3f(dx, dy, dz));
	}
	
	public void increaseRotation(Vector3f r) {
		this.rotation.add(r);
	}
	
	public void increaseRotation(float rx, float ry, float rz) {
		this.rotation.add(new Vector3f(rx, ry, rz));
	}
	
	public void translate(Vector3f translation) {
		position.add(translation);
	}
	
	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rot) {
		this.rotation = rot;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public float getPosX() {
		return position.x;
	}
	
	public float getPosY() {
		return position.y;
	}
	
	public float getPosZ() {
		return position.z;
	}
	
}
