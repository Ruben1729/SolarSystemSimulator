package render;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import input.KeyboardInputManager;
import math.Vec3f;

public class DisplayManager {

	private static final int WIDTH = 1280, HEIGHT = 720;
	private static final String TITLE = "Solar System Simulator";
	private static long window;
	
	private static KeyboardInputManager keyIn = new KeyboardInputManager();
	
	public DisplayManager() {}
	
	public void create() {
		if(!glfwInit()) {
			System.out.println("ERROR: Could not initialize GLFW");
			System.exit(1);
		}
		
		glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);

	    // Compatibility profile.
	    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);

	    // Enable forward compatibility.
	    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_FALSE);
	    
		window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, 0, 0);
		
		// Keyboard input
		glfwSetKeyCallback(window, keyIn);
		
		if(window == 0) { 
			System.err.println("ERROR: Window could not be created");
			System.exit(1);
		}
		
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2);
		glfwShowWindow(window);
	}
	
	public void update() {
		glfwSwapBuffers(window);
		
		// Poll keyboard and mouse events
		glfwPollEvents();
	}
	
	public void destroy() {
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	public boolean close() {
		return glfwWindowShouldClose(window);
	}
	
	public void setBackgroundColor(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
	}
	
	public void clear() {
		glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public long getWindow() {
		return this.window;
	}
}
