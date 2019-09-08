package shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import camera.Camera;
import tools.GLMath;

public class VBlurShader extends ShaderProgram{
	private static final String VERTEX_FILE = "./src/shaders/vBlurVertexShader.txt";
	private static final String FRAGMENT_FILE = "./src/shaders/vBlurFragmentShader.txt";
	
	private int location_tex, location_targetHeight;
	
	public VBlurShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		location_tex = getUniformLocation("tex");
		location_targetHeight = getUniformLocation("targetHeight");
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		bindAttribute(0, "pos");
	}
	
	public void loadTargetHeight(float height) {
		loadFloat(location_targetHeight, height);
	}
}
