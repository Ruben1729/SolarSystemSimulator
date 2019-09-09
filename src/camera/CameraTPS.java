package camera;

import org.joml.Vector3f;

import entities.Entity;

public class CameraTPS extends Camera{
	
	private float distanceFromTarget = 20;
	private float angleAroundTarget = 0;
	private float pitch = 20;
	
	private Entity target;
	
	public CameraTPS(Entity target) {
		this.target = target;
	}
	
	public void tick(float delta) {
		// TODO Auto-generated method stub
		float pitchChange = (float) (dy * 0.1f);
		pitch -= pitchChange;
		
		float angleChange = (float) (dx * 0.1f);
		angleAroundTarget += angleChange;
		
		float horDis = distanceFromTarget * (float) Math.cos(Math.toRadians(pitch));
		float verDis = distanceFromTarget * (float) Math.sin(Math.toRadians(pitch));
		
		float theta = angleAroundTarget;
		
		float offsetX = (float) (horDis * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horDis * Math.cos(Math.toRadians(theta)));
		
		setPosition(new Vector3f(target.getPosX() - offsetX, target.getPosY() + verDis, target.getPosZ() - offsetZ));
		
		lookAt(target.getPosition());
		
		if(!target.getVelocity().equals(new Vector3f(0)))
			target.setRotation(new Vector3f(target.getRotation().x, target.getRotation().y, (-rot.y + 180 + angleAroundTarget)));
	}
	
	public Entity getTarget() {
		return target;
	}
	
	public void setTarget(Entity target) {
		this.target = target;
	}
	
	public void setDistanceFromTarget(float distance) {
		this.distanceFromTarget = distance;
	}
	
	public void changeDistanceFromTarget(float diff) {
		if(this.distanceFromTarget - diff > 0)
			this.distanceFromTarget -= (diff);
	}

}
