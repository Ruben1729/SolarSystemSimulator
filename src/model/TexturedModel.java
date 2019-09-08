package model;

import textures.ModelTexture;

public class TexturedModel {
	
	private RawModel model;
	private ModelTexture texture;
	
	private boolean applyBloom = false;
	
	public TexturedModel(RawModel model, ModelTexture texture) {
		this.model = model;
		this.texture = texture;
	}
	
	public RawModel getModel() {
		return model;
	}
	
	public ModelTexture getTexture() {
		return texture;
	}
	
	public void setApplyBloom(boolean a) {
		this.applyBloom = a;
	}
	
	public boolean getApplyBloom() {
		return applyBloom;
	}
}
