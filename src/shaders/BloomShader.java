package shaders;

public class BloomShader extends ShaderProgram{
	private static final String VERTEX_FILE = "./src/shaders/bloomVertexShader.txt";
	private static final String FRAGMENT_FILE = "./src/shaders/bloomFragmentShader.txt";
	
	public int location_sceneTexture;
	public int location_bloomTexture;
	
	public BloomShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		location_sceneTexture = getUniformLocation("sceneTexture");
		location_bloomTexture = getUniformLocation("bloomTexture");
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		bindAttribute(0, "pos");
		bindAttribute(1, "uv");
	}

	public void loadSceneTexture(int i) {
		loadInt(location_sceneTexture, i);
	}

	public void loadBloomTexture(int i) {
		loadInt(location_bloomTexture, i);
	}
}
