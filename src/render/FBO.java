package render;

import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.*;

import org.lwjgl.BufferUtils;

public class FBO {
	
	private int id; 
	private int width, height;
	private List<FBOAttachment> attachments = new ArrayList<FBOAttachment>();
	
	private static class FBOAttachment {
		public int id;
		public int glAttachment;
		public boolean texture;
		
		protected FBOAttachment(int width, int height, int internalFormat, int pixelFormat, int dataType, int glAttachment, int numSamples, boolean texture) {
			if(texture) {
				id = glGenTextures();
				glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, id);
				glTexImage2DMultisample(GL_TEXTURE_2D_MULTISAMPLE, numSamples, internalFormat, width, height, false);
				glFramebufferTexture2D(GL_FRAMEBUFFER, glAttachment, GL_TEXTURE_2D_MULTISAMPLE, id, 0);
				glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, 0);
			} else {
				id = glGenRenderbuffers();
				glBindRenderbuffer(GL_RENDERBUFFER, id);
				glRenderbufferStorageMultisample(GL_RENDERBUFFER, numSamples, internalFormat, width, height);
				glFramebufferRenderbuffer(GL_FRAMEBUFFER, glAttachment, GL_RENDERBUFFER, id);
				glBindRenderbuffer(GL_RENDERBUFFER, 0);
			}
		}
		
		protected FBOAttachment(int width, int height, int internalFormat, int pixelFormat, int dataType, int glAttachment, boolean texture) {
			if(texture) {
				id = glGenTextures();
				glBindTexture(GL_TEXTURE_2D, id);
				glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, pixelFormat, dataType, (ByteBuffer) null);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
				glFramebufferTexture2D(GL_FRAMEBUFFER, glAttachment, GL_TEXTURE_2D, id, 0);
				glBindTexture(GL_TEXTURE_2D, 0);
			} else {
				id = glGenRenderbuffers();
				glBindRenderbuffer(GL_RENDERBUFFER, id);
				glRenderbufferStorage(GL_RENDERBUFFER, internalFormat, width, height);
				glFramebufferRenderbuffer(GL_FRAMEBUFFER, glAttachment, GL_RENDERBUFFER, id);
				glBindRenderbuffer(GL_RENDERBUFFER, 0);
			}
		}
	}
	
	private FBO(int id, int width, int height) {
		this.width = width;
		this.height = height;
		this.id = id;
	}
	
	public FBO createAttachment(int internalFormat, int pixelFormat, int dataType, int glAttachment, int numSamples, boolean texture) {
		FBOAttachment attachment;
		if(numSamples > 1) {
			attachment = new FBOAttachment(width, height, internalFormat, pixelFormat, dataType, glAttachment, numSamples, texture);
		} else {
			attachment = new FBOAttachment(width, height, internalFormat, pixelFormat, dataType, glAttachment, texture);
		}
		attachments.add(attachment);
		return this;
	}
	
	public FBO bindAttachments() {
		List<Integer> bufferArray = new ArrayList<Integer>();
		for(FBOAttachment a : attachments) {
			if(a.glAttachment >= GL_COLOR_ATTACHMENT0 && a.glAttachment <= GL_COLOR_ATTACHMENT31) {
				bufferArray.add(a.glAttachment);
			}
		}
		IntBuffer buffer = BufferUtils.createIntBuffer(bufferArray.size());
		for(int i : bufferArray) {
			buffer.put(i);
		}
		buffer.flip();
		glDrawBuffers(buffer);
		return this;
	}
	
	public FBO unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		return this;
	}
	
	public FBO bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		return this;
	}
	
	public FBO resolve(FBO dest, int sourceAttachment, int destAttachment) {
		glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
		glReadBuffer(sourceAttachment);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, dest.id);
		glDrawBuffer(destAttachment);
		glBlitFramebuffer(0, 0, width, height, 0, 0, dest.width, dest.height, GL_COLOR_BUFFER_BIT, GL_NEAREST);
		return this;
	}
	
	public FBO resolveToDisplay(int sourceAttachment) {
		glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
		glReadBuffer(sourceAttachment);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
		glDrawBuffer(GL_COLOR_ATTACHMENT0);
		glBlitFramebuffer(0, 0, width, height, 0, 0, DisplayManager.getWidth(), DisplayManager.getHeight(), GL_COLOR_BUFFER_BIT, GL_NEAREST);
		return this;
	}
	
	public boolean checkFunctional() {
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		return glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE;
	}
	
	public static FBO create(int width, int height) {
		int id = glGenFramebuffers();
		return new FBO(id, width, height);
	}
}
