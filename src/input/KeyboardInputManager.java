package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.HashMap;

public class KeyboardInputManager extends GLFWKeyCallback{
	
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	
	public HashMap<Integer, Runnable> keyPressed = new HashMap<Integer, Runnable>();
	public HashMap<Integer, Runnable> keyReleased = new HashMap<Integer, Runnable>();
	
	public boolean isKeyDown(int keyCode) {
		return keys[keyCode];
	}

	@Override
	public void invoke(long window, int keyCode, int scanCode, int action, int mods) {
		// TODO Auto-generated method stub
		keys[keyCode] = action != GLFW_RELEASE;
		
		if(action == GLFW_RELEASE) {
			try { 
				keyReleased.get(keyCode).run();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try { 
				keyPressed.get(keyCode).run();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
