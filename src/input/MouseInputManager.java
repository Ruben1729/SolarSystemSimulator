package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import camera.Camera;
import camera.CameraTPS;
import core.MainLoop;
import render.DisplayManager;

public class MouseInputManager extends GLFWCursorPosCallback{

	private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	private double xpos, ypos;
	private int dx = 0, dy = 0;
	private Camera cam;
	
	public MouseInputManager() {
		xpos = 0;
		ypos = 0;
	}
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		// TODO Auto-generated method stub
		
		this.xpos = xpos;
		this.ypos = ypos;
		
		dx = (int) ((DisplayManager.getWidth() / 2) - xpos);
		dy = (int) ((DisplayManager.getHeight() / 2) - ypos);
		
		cam = MainLoop.getCamera();
		cam.setDX(dx);
		cam.setDY(dy);
	}
	
	public double getXpos() {
		return xpos;
	}

	public void setXpos(double xpos) {
		this.xpos = xpos;
	}

	public double getYpos() {
		return ypos;
	}

	public void setYpos(double ypos) {
		this.ypos = ypos;
	}
	
}
