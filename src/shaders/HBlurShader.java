package shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import camera.Camera;
import tools.GLMath;

public class HBlurShader extends ShaderProgram{
	private static final String VERTEX_FILE = "./src/shaders/hBlurVertexShader.txt";
	private static final String FRAGMENT_FILE = "./src/shaders/hBlurFragmentShader.txt";
	
	private int location_tex, location_targetWidth;
	
	public HBlurShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		location_tex = getUniformLocation("tex");
		location_targetWidth = getUniformLocation("targetWidth");
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		bindAttribute(0, "pos");
	}
	
	public void loadTargetWidth(float width) {
		loadFloat(location_targetWidth, width);
	}
}
