package shaders;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "./src/shaders/vertexShader.txt", FRAGMENT_FILE = "./src/shaders/fragmentShader.txt";
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

}
