package render;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT1;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;

import static org.lwjgl.opengl.GL30.*;

import model.RawModel;
import shaders.BloomShader;
import shaders.HBlurShader;
import shaders.VBlurShader;

public class PostProcessing {

	private VBlurShader vBlur;
	private HBlurShader hBlur;
	private BloomShader bloomShader;
	
	private FBO fbo1, fbo2, fbo3;
	
	private RawModel quad;

	private final float pos[] = {-1, 1, 0,
								 -1, -1, 0,
								 1, -1, 0,
								 1, 1, 0};
	
	private final float tex[] = {0, 0,
								 0, 1,
								 1, 1,
								 1, 0};
	
	private final int ind[] = {0, 1, 3,
							   3, 1, 2};
	
	public PostProcessing(Loader loader) {
		quad = loader.loadToVAO(pos, tex, ind);
		
		vBlur = new VBlurShader();
		hBlur = new HBlurShader();
		bloomShader = new BloomShader();
		
		bloomShader.start();
		bloomShader.loadSceneTexture(0);
		bloomShader.loadBloomTexture(1);
		bloomShader.stop();
		
		fbo1 = FBO.create(DisplayManager.getWidth() / 8, DisplayManager.getHeight() / 8)
				.bind()
				.createAttachment(GL_RGB, GL_RGB, GL_UNSIGNED_BYTE, GL_COLOR_ATTACHMENT0, 1, true)
				.bindAttachments()
				.unbind();
		
		fbo2 = FBO.create(DisplayManager.getWidth() / 8, DisplayManager.getHeight() / 8)
				.bind()
				.createAttachment(GL_RGB, GL_RGB, GL_UNSIGNED_BYTE, GL_COLOR_ATTACHMENT0, 1, true)
				.bindAttachments()
				.unbind();
		
		fbo3 = FBO.create(DisplayManager.getWidth(), DisplayManager.getHeight())
				.bind()
				.createAttachment(GL_RGB, GL_RGB, GL_UNSIGNED_BYTE, GL_COLOR_ATTACHMENT0, 1, true)
				.bindAttachments()
				.unbind();
		
	}
	
	public void run(int sceneTextureID, int bloomTextureID) {
		vBlur.start();
		vBlur.loadTargetHeight(fbo1.getHeight());
		
		hBlur.start();
		hBlur.loadTargetWidth(fbo1.getWidth());
		
		fbo1.bind();
		
		glBindVertexArray(quad.getVaoID());
		glEnableVertexAttribArray(0);
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, bloomTextureID);
		glDrawElements(GL_TRIANGLES, quad.getVertexCount(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		
		fbo2.bind();
		vBlur.start();
		
		glBindVertexArray(quad.getVaoID());
		glEnableVertexAttribArray(0);
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, fbo1.getAttachments().get(0).id);
		glDrawElements(GL_TRIANGLES, quad.getVertexCount(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		
		vBlur.stop();
		fbo3.bind();
		
		bloomShader.start();
		
		glBindVertexArray(quad.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, sceneTextureID);
		
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, fbo2.getAttachments().get(0).id);
		glDrawElements(GL_TRIANGLES, quad.getVertexCount(), GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
		
		bloomShader.stop();
		
		fbo3.resolveToDisplay(GL_COLOR_ATTACHMENT0, GL_NEAREST);
		fbo3.unbind();
	}
	
}