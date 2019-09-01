package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class ShaderProgram {
	private int vertexShaderID, fragmentShaderID, programID;
	
	public ShaderProgram(String vertexFile, String fragmentFile) {
		vertexShaderID = load(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = load(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
	}
	
	private static int load(String file, int type) {
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, readFile(file));
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println(GL20.glGetShaderInfoLog(shaderID));
		}
		
		return shaderID;
	}
	
	protected abstract void bindAttributes();
	public void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	public void start() {
		GL20.glUseProgram(programID);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void cleanUp() {
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
		
	}
	
	private static String readFile(String file) {
		BufferedReader reader = null;
		StringBuilder string = new StringBuilder();
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				string.append(line).append("\n");
			}
			
		} catch (IOException e) {
			System.err.println(e);
		}
		
		return string.toString();
	}
}
