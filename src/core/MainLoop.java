package core;

import entities.Entity;
import entities.Light;
import entities.Planet;
import entities.Sun;
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
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;

import camera.CameraFree;
import camera.CameraTPS;

public class MainLoop extends Thread{
	
	private static final double TICKS_PER_SEC = 60;
	private static Thread mainThread;
	private static DisplayManager display;
	private static volatile boolean running = false;
	
	private static CameraFree camera;
	private static Sun sun;
	
	private static List<Planet> planets = new ArrayList<Planet>();
	
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
		TexturedModel texturedModel = new TexturedModel(model, loader.loadTexture("sun_paint.png", true));
		sun = new Sun(texturedModel, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 50);
		
		loadPlanets(loader);
		
		Light light = new Light(new Vector3f(0, 0, -500), new Vector3f(1, 1, 1));
		
		camera = new CameraFree();
		camera.setPosition(new Vector3f(0, 0, 2000));
		
		// Game Loop variables
		long lastFrame = System.nanoTime();
		long lastTime = System.nanoTime();
		long lastTick = System.nanoTime();
		int fps = 0;
		int ticks = 0;
		
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
			
			// fbo.bind();
			
			//How to add a new entity
			renderer.processEntity(sun);
			for(Planet p : planets) {
				renderer.processEntity(p);
			}
			
			renderer.render(light,  camera);
			
			// fbo.unbind();
			// fbo.resolveToDisplay(GL_COLOR_ATTACHMENT0);
			// Render
			display.update();
			fps++;
		}
		
		renderer.cleanup();
		loader.cleanUp();
	}
	
	private void tick() {
		//logic
		camera.tick();
		sun.tick();

		for(Planet p : planets) {
			p.tick();
		}
		
		centerCursor();
	}
	
	private void loadPlanets(Loader loader) {
		Orbit earthOrbit = new Orbit(980 + sun.getScale(), 1000 + sun.getScale(), 100, sun.getPosition());
		Planet earth = new Planet(getTexturedModel("/earth/earth", loader, "Earth_2k.png"), new Vector3f((float)earthOrbit.getX(), (float)earthOrbit.getY(), 0), new Vector3f(0, 0, 0), 10, earthOrbit);
		planets.add(earth);
	}
	
	private TexturedModel getTexturedModel(String path, Loader loader, String textureName) {
		return new TexturedModel(OBJLoader.loadObjModel(path, loader), loader.loadTexture(textureName, true));
	}
	
	private void bindKeys() {
		bindKeyDown(GLFW_KEY_ESCAPE, () -> {
			glfwSetWindowShouldClose(display.getWindow(), true);
		});
		
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
	
	public static CameraFree getCamera() {
		return camera;
	}
	
	public void centerCursor() {
		glfwSetCursorPos(display.getWindow(), (DisplayManager.getWidth() / 2), (DisplayManager.getHeight() / 2));
		camera.setDX(0);
		camera.setDY(0);
	}
}
