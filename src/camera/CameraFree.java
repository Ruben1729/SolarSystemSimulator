package camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class CameraFree extends Camera {
	
	private boolean forward = false, backward = false, left = false,  right = false;
	
	public CameraFree() {
		
	}
	
	public void tick() {
		rotate(new Vector3f((float) (-dy * 0.1f), (float) (-dx * 0.1f), 0));
		
		updateVelocity();
		translate(getVelocity());
	}
	
	private void updateVelocity() {
		Vector3f direction = new Vector3f(0);
		
		if(forward)
			direction.add(forward());
		
		if(backward)
			direction.add(forward().mul(-1));

		if(left)
			direction.add(right().mul(-1));

		if(right)
			direction.add(right());
		
		// speed
		direction.mul(200);
		setVelocity(direction);
	}
	
	private Vector3f right() {
		return forward().cross(new Vector3f(0, 1, 0));
	}
	
	private Vector3f forward() {
		Matrix4f transform = new Matrix4f();
		transform.identity();
		
	    transform.rotate((float) ((rot.y - 90.0f) * Math.PI / 180), new Vector3f(0, 1, 0));
	    transform.rotate((float) (-rot.x * Math.PI / 180), new Vector3f(0, 0, 1));

	    Vector4f vec = transform.transform(new Vector4f(1, 0, 0, 1));
	    
	    Vector3f temp = new Vector3f(vec.x, vec.y, -vec.z);
	    return  temp.div(vec.w);
	}

	public void setForward(boolean forward) {
		this.forward = forward;
	}

	public void setBackward(boolean backward) {
		this.backward = backward;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
}
