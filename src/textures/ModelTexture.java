package textures;

public class ModelTexture {
	
	private int textureID, type;
	
	public ModelTexture(int textureID, int type) {
		this.textureID = textureID;
		this.type = type;
	}
	
	public int getID() {
		return this.textureID;
	}
}
