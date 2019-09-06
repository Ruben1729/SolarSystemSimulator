package camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import static java.lang.Math.*;

public abstract class Camera {
	
	protected Vector3f pos, rot, vel;
	
	protected double dx, dy;
	
	public Camera() {
		this.pos = new Vector3f(0);
		this.rot = new Vector3f(0);
		this.vel = new Vector3f(0);
	}
	
	public abstract void tick();
	
	public void setRotation(Vector3f rot) {
		this.rot = new Vector3f(rot);
	}
	
	public void setPosition(Vector3f pos) {
		this.pos = new Vector3f(pos);
	}
	
	public Vector3f getPosition() {
		return this.pos;
	}
	
	public Vector3f getRotation() {
		return this.rot;
	}
	
	public float getPitch() {
		return this.rot.x;
	}
	
	public float getYaw() {
		return this.rot.y;
	}
	
	public Vector3f getVelocity() {
		return this.vel;
	}
	
	public void lookAt(Vector3f point) {
		
		// Direction relative to camera.
		Vector3f dir = new Vector3f();
		point.sub(pos, dir);
		
		// Calculate yaw.
		float yaw = (float) toDegrees(atan2(dir.z, dir.x)) + 90.0f;
		
		// Calculate pitch.
		float horizLength = (float) sqrt(pow(dir.x, 2) + pow(dir.z, 2));
		float pitch = (float) toDegrees(atan(dir.y / horizLength));
		
		// Apply the rotation to the camera.
		this.rot = new Vector3f(-pitch, yaw, 0.0f);
		
	}
	
	public void rotate(Vector3f rotation) {
		rot.add(rotation);
	}
	
	public void translate(Vector3f translation) {
		pos.add(translation);
	}
	
	public void invertPitch() {
		this.rot.x = -this.rot.x;
	}
	
	public Vector3f getDirection() {
		return new Vector3f((float)(Math.sin(Math.toRadians(rot.z))), 0.0f, (float)(Math.cos(Math.toRadians(rot.z))));
	}
	
	public void accelerate(Vector3f dir) {
		this.vel.add(dir, vel);
	}
	
	public void stop() {
		this.vel = new Vector3f(0);
	}
	
	public void setVelocity(Vector3f vel) {
		this.vel = vel;
	}
	
	public void setDX(double dx) {
		this.dx = dx;
	}
	
	public void setDY(double dy) {
		this.dy = dy;
	}
	
	public Matrix4f getYawTransform() {

        // Create the matrix and set it to identity.
        Matrix4f mat = new Matrix4f().identity();

        // Rotation.
        mat.rotate((float)toRadians(rot.y), new Vector3f(0, 1, 0));     // yaw`

        // Return the transformation matrix.
        return mat;

    }
	
}
