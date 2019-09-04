package input;

import org.lwjgl.glfw.GLFWScrollCallback;

public class ScrollInputManager extends GLFWScrollCallback {

	@Override
	public void invoke(long window, double xOffset, double yOffset) {
		// TODO Auto-generated method stub
		System.out.println(xOffset + " " + yOffset);
	}

}
