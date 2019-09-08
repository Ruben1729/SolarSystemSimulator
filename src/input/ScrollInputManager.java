package input;

import org.lwjgl.glfw.GLFWScrollCallback;

import camera.Camera;
import camera.CameraFree;
import camera.CameraTPS;
import core.MainLoop;
import render.DisplayManager;

public class ScrollInputManager extends GLFWScrollCallback {

	@Override
	public void invoke(long window, double xOffset, double yOffset) {
		// TODO Auto-generated method stub
		if(MainLoop.tpsOn) {
			CameraTPS cam = (CameraTPS) MainLoop.getCamera();
			cam.changeDistanceFromTarget((float) (yOffset));
		} else {
			CameraFree cam = (CameraFree) MainLoop.getCamera();
			cam.increaseSpeed((int)(yOffset));
		}
	}

}
