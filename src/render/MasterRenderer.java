package render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import camera.Camera;
import entities.Entity;
import entities.Light;
import entities.Planet;
import model.Primitive;
import model.TexturedModel;
import shaders.StaticShader;

import static org.lwjgl.opengl.GL30.*;

public class MasterRenderer {

	private Matrix4f projectionMatrix;
	private Renderer renderer;
	private PrimitiveRenderer pRenderer;
	
	private static final float FOV = 100;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 100000000;
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	
	public MasterRenderer() {
		createProjectionMatrix();
		renderer = new Renderer(projectionMatrix);
		pRenderer = new PrimitiveRenderer(projectionMatrix);
		glEnable(GL_LINE_SMOOTH);
	}
	
	public void renderPrimitive(Camera cam, Primitive p) {
		pRenderer.render(cam, p);
	}
	
	public void render(Light sun, Camera camera) {
		renderer.render(sun, camera, entities);
	}
	
	public void processEntities(List<Planet> entities) {
		for(Entity e : entities) {
			processEntity(e);
		}
	}
	
	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null)
			batch.add(entity);
		else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}

	}
	
	public void cleanUp() {
		renderer.cleanUp();
		pRenderer.cleanUp();
	}
	
	private void createProjectionMatrix() {
		float aspectRatio = (float) DisplayManager.getWidth() / (float) DisplayManager.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix.m33(0);
	}
}
