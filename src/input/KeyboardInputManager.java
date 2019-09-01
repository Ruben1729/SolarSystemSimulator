package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardInputManager extends GLFWKeyCallback{
	
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	public boolean isKeyDown(int keyCode) {
		return keys[keyCode];
	}

	@Override
	public void invoke(long window, int keyCode, int scanCode, int action, int mods) {
		// TODO Auto-generated method stub
		keys[keyCode] = action != GLFW_RELEASE;
		
		if(action == GLFW_RELEASE) {
			// key is released event
		} else {
			//key is pressed event
		}
	}
}
