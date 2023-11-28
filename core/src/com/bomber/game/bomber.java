package com.bomber.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class bomber extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private Texture animationSheet;
	private TextureRegion[][] frames;
	private Array<TextureRegion> animationFrames;
	private float stateTime;
	private static final float FRAME_DURATION = .5f; // Defina a duração de cada frame da animação
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		animationSheet = new Texture(Gdx.files.internal("bomber.png"));
		frames = TextureRegion.split(animationSheet, animationSheet.getWidth() / 4, animationSheet.getHeight() / 5);
		animationFrames = new Array<>();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				animationFrames.add(frames[i][j]);
			}
		}

		stateTime = 0f;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stateTime += Gdx.graphics.getDeltaTime();

		TextureRegion currentFrame = animationFrames.get((int) (stateTime / FRAME_DURATION) % animationFrames.size);

		// Desenhar o frame atual
		// (Lembre-se de substituir os valores adequados para a posição e escala)
		 batch.begin();
		 batch.draw(currentFrame, 300, 300);
		 batch.end();
	}
	
	@Override
	public void dispose () {
		animationSheet.dispose();
	}
}
