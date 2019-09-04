package tools;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import camera.Camera;

public class GLMath {
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, float scale) {
		
		Matrix4f matrix = new Matrix4f();
		
		matrix.identity();
		matrix.translate(translation, matrix);
		matrix.rotateXYZ((float) Math.toRadians(rotation.x), (float) Math.toRadians(rotation.y), (float) Math.toRadians(rotation.z), matrix);
		
		matrix.scale(scale);
		
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		
		Vector3f cameraRot = camera.getRotation();
		
		matrix.rotate((float) Math.toRadians(cameraRot.x), new Vector3f(1, 0, 0), matrix);
		matrix.rotate((float) Math.toRadians(cameraRot.y), new Vector3f(0, 1, 0), matrix);
		matrix.rotate((float) Math.toRadians(cameraRot.z), new Vector3f(0, 0, 1), matrix);
		
		Vector3f cameraPos = camera.getPosition();
		
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		matrix.translate(negativeCameraPos, matrix);
		
		return matrix;
	}
}
