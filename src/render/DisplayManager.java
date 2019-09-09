package render;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import input.InputManager;
import input.KeyboardInputManager;
import input.MouseInputManager;

public class DisplayManager {

	private static final int WIDTH = 1280, HEIGHT = 720;
	private static final String TITLE = "Solar System Simulator";
	private long window;
	
	public InputManager input;
	
	public DisplayManager() {
		input = new InputManager();
	}
	
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
		input.create(window);
		
		if(window == 0) { 
			System.err.println("ERROR: Window could not be created");
			System.exit(1);
		}
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		
		GL.createCapabilities();
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2);
		glfwShowWindow(window);
		
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
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
	
	public long getWindow() {
		return this.window;
	}
	
	public static int getWidth() {
		return WIDTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}
}
