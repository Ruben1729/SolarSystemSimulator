package core;

import entities.Entity;
import entities.Light;
import entities.Planet;
import entities.PlanetManager;
import entities.Sun;
import model.Primitive;
import model.RawModel;
import model.TexturedModel;
import render.DisplayManager;
import render.FBO;
import render.Loader;
import render.MasterRenderer;
import render.OBJLoader;
import render.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import tools.Orbit;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;

import camera.Camera;
import camera.CameraFree;
import camera.CameraTPS;

public class MainLoop extends Thread{
	
	private static final double TICKS_PER_SEC = 60;
	private static Thread mainThread;
	private static DisplayManager display;
	private static volatile boolean running = false;
	
	private static CameraFree camera;
	private static CameraTPS tpsCamera;
	
	private static int targetIndex = 0;
	
	private static Sun sun;
	
	private static boolean renderOrbits = true;
	private static boolean tpsOn = false;
	
	private static PlanetManager pMan;
	
	private static List<Primitive> lines = new ArrayList<Primitive>();
	
	public static void main(String[] args) {
		MainLoop loop = new MainLoop();
		loop.start();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		display = new DisplayManager();
		display.create();
		
		FBO fbo = FBO.create(DisplayManager.getWidth(), DisplayManager.getHeight())
				.bind()
				.createAttachment(GL_RGB, GL_RGB, GL_UNSIGNED_BYTE, GL_COLOR_ATTACHMENT0, 8, false)
				.createAttachment(GL_DEPTH_COMPONENT32, GL_DEPTH_COMPONENT, GL_FLOAT, GL_DEPTH_ATTACHMENT, 8, false)
				.bindAttachments()
				.unbind();
		
		bindKeys();
		
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		
		System.out.println("OpenGL version " +  glGetString(GL_VERSION));
		// Models
		RawModel model = OBJLoader.loadObjModel("/sun/sun", loader);
		TexturedModel texturedModel = new TexturedModel(model, loader.loadTexture("sun.png", true));
		sun = new Sun(texturedModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 9.29f);
		
		pMan = new PlanetManager(sun);
		pMan.loadPlanets(loader);
		lines = pMan.getDrawnOrbits();
		
		Light light = new Light(new Vector3f(0, 0, 60000), new Vector3f(1, 1, 1));
		
		camera = new CameraFree();
		
		tpsCamera = new CameraTPS(pMan.getCurrentTarget());
		tpsCamera.lookAt(pMan.getCurrentTargetPosition());
		
		camera.setPosition(new Vector3f(0, 0, 20000));
		
		// Game Loop variables
		long lastFrame = System.nanoTime();
		long lastTime = System.nanoTime();
		long lastTick = System.nanoTime();
		int fps = 0;
		int ticks = 0;
		
		Camera renderCamera;
		
		// Game Loop
		while(!display.close()) {
			long currentTime = System.nanoTime();
			if(currentTime - lastTime >= 1000000000) {
				System.out.println("FPS: " + fps + " | TICKS: " + ticks);
				fps = 0;
				ticks = 0;
				lastTime = currentTime;
			}
			
			// TICK
			long currentTick = System.nanoTime();
			if(currentTick - lastTick >= 1000000000 / TICKS_PER_SEC) {
				tick();
				
				ticks ++;
				lastTick = currentTick;
			}
			
			long currentFrame = System.nanoTime();
			float deltaTime = ((float) (currentFrame - lastFrame) / 1000000000.0f);
			lastFrame = currentFrame;
			
			sun.increaseRotation(0, 1, 0);
			
			//How to add a new entity
			renderer.processEntity(sun);
			renderer.processEntities(pMan.getPlanets());
			
			if(tpsOn)
				renderCamera = tpsCamera;
			else
				renderCamera = camera;
			
			fbo.bind();
			
			renderer.render(light,  renderCamera);
			
			fbo.unbind();
			fbo.resolveToDisplay(GL_COLOR_ATTACHMENT0);
			
			if(renderOrbits)
				for(Primitive l : lines) {
					renderer.renderPrimitive(renderCamera, l);
				}
			
			// Render
			display.update();
			fps++;
		}
		
		renderer.cleanUp();
		loader.cleanUp();
	}
	
	private void tick() {
		//logic
		if(tpsOn)
			tpsCamera.tick();
		else
			camera.tick();
		sun.tick();

		for(Planet p : pMan.getPlanets()) {
			p.tick();
		}
		
		centerCursor();
	}
	
	private void bindKeys() {
		bindKeyDown(GLFW_KEY_ESCAPE, () -> {
			glfwSetWindowShouldClose(display.getWindow(), true);
		});
		
		bindKeyDown(GLFW_KEY_TAB, ()->{
			if(tpsOn) {
				pMan.updateTarget();
				tpsCamera.setTarget(pMan.getCurrentTarget());
				tpsCamera.lookAt(pMan.getCurrentTargetPosition());
			}
		});
		
		bindKeyRelease(GLFW_KEY_TAB, ()->{});
		
		bindKeyDown(GLFW_KEY_E, ()->{
			tpsOn = !tpsOn;
		});
		
		bindKeyRelease(GLFW_KEY_E, ()->{});
		
		bindKeyDown(GLFW_KEY_Q, ()->{
			renderOrbits = !renderOrbits;
		});
		
		bindKeyRelease(GLFW_KEY_Q, ()->{});
		
		bindKeyDown(GLFW_KEY_W, ()->{
			camera.setForward(true);
		});
		
		bindKeyRelease(GLFW_KEY_W, ()->{
			camera.setForward(false);
		});
		
		bindKeyDown(GLFW_KEY_S, ()->{
			camera.setBackward(true);
		});
		
		bindKeyRelease(GLFW_KEY_S, ()->{
			camera.setBackward(false);
		});
		
		bindKeyDown(GLFW_KEY_A, ()->{
			camera.setLeft(true);
		});
		
		bindKeyRelease(GLFW_KEY_A, ()->{
			camera.setLeft(false);
		});
		
		bindKeyDown(GLFW_KEY_D, ()->{
			camera.setRight(true);
		});
		
		bindKeyRelease(GLFW_KEY_D, ()->{
			camera.setRight(false);
		});
	}
	
	private void bindKeyDown(int code, Runnable callback) {
		display.input.registerKeyDown(code, callback);
	}
	
	private void bindKeyRelease(int code, Runnable callback) {
		display.input.registerKeyUp(code, callback);
	}
	
	public synchronized void start() {
		if(running)
			return;
		
		mainThread = new Thread(this);
		running = true;
		mainThread.start();
	}
	
	public synchronized void close() {
		if(!running)
			return;
		
		try {
			join();
			running = false;
			display.destroy();
			System.exit(0);
		} catch(InterruptedException e) {}
	}
	
	public static boolean isRunning() {
		return running;
	}
	
	public static Camera getCamera() {
		if(tpsOn)
			return tpsCamera;
		else
			return camera;
	}
	
	public void centerCursor() {
		glfwSetCursorPos(display.getWindow(), (DisplayManager.getWidth() / 2), (DisplayManager.getHeight() / 2));
		
		if(tpsOn){
			tpsCamera.setDX(0);
			tpsCamera.setDY(0);
		} else {
			camera.setDX(0);
			camera.setDY(0);
		}
	}
}
