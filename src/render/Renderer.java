package render;

import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import camera.Camera;
import entities.Entity;
import entities.Light;
import model.RawModel;
import model.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;
import tools.GLMath;

public class Renderer {
	
	private StaticShader shader;
	
	public Renderer(Matrix4f projectionMatrix) {
		this.shader = new StaticShader();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Map<TexturedModel, List<Entity>> entities) {
		for(TexturedModel model: entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity: batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
			
	}
	
	private void prepareTexturedModel(TexturedModel model) {
		RawModel rawModel = model.getModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		ModelTexture texture = model.getTexture();
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}
	
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = GLMath.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
	public void render(Light sun, Camera camera, Map<TexturedModel, List<Entity>> entities) {
		prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		
		render(entities);
		
		shader.stop();
		entities.clear();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}

}