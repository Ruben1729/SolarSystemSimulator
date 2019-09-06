package input;

import org.lwjgl.glfw.GLFWScrollCallback;

import camera.Camera;
import camera.CameraTPS;
import core.MainLoop;

public class ScrollInputManager extends GLFWScrollCallback {

	@Override
	public void invoke(long window, double xOffset, double yOffset) {
		// TODO Auto-generated method stub
		CameraTPS cam = (CameraTPS) MainLoop.getCamera();
		cam.changeDistanceFromTarget((float) (yOffset));
	}

}
