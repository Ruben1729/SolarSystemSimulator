package core;

import org.lwjgl.opengl.GL11;

import input.KeyboardInputManager;
import model.RawModel;
import model.TexturedModel;
import render.DisplayManager;
import render.Loader;
import render.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class MainLoop extends Thread{
	
	private static final double TICKS_PER_SEC = 60;
	private static Thread mainThread;
	private static DisplayManager display;
	private static volatile boolean running = false;
	
	public static void main(String[] args) {
		MainLoop loop = new MainLoop();
		loop.start();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		display = new DisplayManager();
		display.create();
		
		Renderer renderer = new Renderer();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		
		System.out.println("OpenGL version " +  glGetString(GL_VERSION));
		// Models
		float[] vertices = {
				-0.5f, 0.5f, 0.0f, // 0
				-0.5f, -0.5f, 0.0f,  // 1
				0.5f, -0.5f, 0.0f,// 2
				0.5f, 0.5f, 0.0f, // 3
				
		};
		
		int[] indices = {
			0, 1, 3,
			3, 1, 2
		};
		
		float[] textureCoords = {
				0, 0,
				0, 1,
				1, 1,
				1, 0
		};
		
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture = loader.loadTexture("dog.png", false);
		
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
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
			
			// Render
			
			display.setBackgroundColor(0.5f, 1.0f, 0.5f, 1.0f);
			display.clear();

			renderer.prepare();
			shader.start();
			
			renderer.render(texturedModel);
			shader.stop();
			
			display.update();
			fps++;
		}
		
		shader.cleanUp();
		loader.cleanUp();
	}
	
	private void tick() {
		//logic
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
}
