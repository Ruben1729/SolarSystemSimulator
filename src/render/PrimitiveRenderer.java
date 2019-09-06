package render;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL15;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;

import camera.Camera;
import model.Primitive;
import shaders.PrimitiveShader;

public class PrimitiveRenderer {

	private PrimitiveShader shader;
	private int vaoID, vboID;
	
	public PrimitiveRenderer(Matrix4f projectionMatrix) {
		shader = new PrimitiveShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		
		vaoID = glGenVertexArrays();
		vboID = glGenBuffers();
		
		glBindVertexArray(vaoID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	}
	
	public void render(Camera cam, Primitive p) {
		shader.start();
		shader.loadViewMatrix(cam);
		shader.loadColor(p.getColor());
		glBindVertexArray(vaoID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, p.getVertices(), GL15.GL_DYNAMIC_DRAW);
		glEnableVertexAttribArray(0);
		glDrawArrays(p.getType(), 0, p.getVertices().capacity() / 3);
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
}
