package input;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {
	
	public KeyboardInputManager keyIn;
	public ScrollInputManager scrollIn;
	public MouseInputManager mouseIn;
	
	public InputManager() {
		keyIn = new KeyboardInputManager();
		mouseIn = new MouseInputManager();
		scrollIn = new ScrollInputManager();
	}
	
	public void create(long window) {
		glfwSetKeyCallback(window, keyIn);
		glfwSetCursorPosCallback(window, mouseIn);
		glfwSetScrollCallback(window, scrollIn);
	}
	
	public void registerKeyUp(int key, Runnable action) {
		keyIn.keyReleased.put(key, action);
	}
	
	public void registerKeyDown(int key, Runnable action) {
		keyIn.keyPressed.put(key, action);
	}
}
