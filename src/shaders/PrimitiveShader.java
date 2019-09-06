package shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import camera.Camera;
import entities.Light;
import tools.GLMath;

public class PrimitiveShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "./src/shaders/primitiveVertexShader.txt";
	private static final String FRAGMENT_FILE = "./src/shaders/primitiveFragmentShader.txt";
	
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	
	public int locationColor;
	
	public PrimitiveShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		locationColor = getUniformLocation("color");
		locationProjectionMatrix = getUniformLocation("projectionMatrix");
		locationViewMatrix = getUniformLocation("viewMatrix");
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		bindAttribute(0, "pos");
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = GLMath.createViewMatrix(camera);
		loadMatrix(locationViewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(locationProjectionMatrix, projection);
	}
	
	public void loadColor(Vector3f color) {
		loadVector(locationColor, color);
	}

}
