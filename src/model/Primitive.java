package model;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Primitive {
	
	private int type;
	private FloatBuffer vertices;
	private Vector3f color = new Vector3f(1, 1, 1);
	
	public Primitive(int type, List<Vector3f> vertices) {
		this.type = type;
		this.vertices = BufferUtils.createFloatBuffer(vertices.size() * 3);
		for(Vector3f v : vertices) {
			this.vertices .put(v.x);
			this.vertices .put(v.y);
			this.vertices .put(v.z);
		}
		this.vertices .flip();
		
	}
	
	public Primitive(int type, List<Vector3f> vertices, Vector3f color) {
		this.type = type;
		this.vertices = BufferUtils.createFloatBuffer(vertices.size() * 3);
		for(Vector3f v : vertices) {
			this.vertices .put(v.x);
			this.vertices .put(v.y);
			this.vertices .put(v.z);
		}
		this.vertices .flip();
		this.color = color;
	}
	
	public FloatBuffer getVertices() {
		return vertices;
	}
	
	public int getType() {
		return type;
	}
	
	public Vector3f getColor() {
		return color;
	}
}
