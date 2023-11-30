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
    private static final float FRAME_DURATION = .15f; // Defina a duração de cada frame da animação

    @Override
    public void create() {
        batch = new SpriteBatch();
        animationSheet = new Texture(Gdx.files.internal("cowboy-front.png"));
        frames = TextureRegion.split(animationSheet, animationSheet.getWidth() / 2, animationSheet.getHeight() / 2);
        animationFrames = new Array<>();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                animationFrames.add(frames[i][j]);
            }
        }

        stateTime = 0f;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();
        // Define o número total de quadros da animação
        int totalFrames = animationFrames.size;
        // Calcula o índice do próximo quadro
        int nextFrame = (int) (stateTime / FRAME_DURATION) % totalFrames;

		TextureRegion cf = animationFrames.get(nextFrame);

		// Desenhar o frame atual
		// (Lembre-se de substituir os valores adequados para a posição e escala)
		 batch.begin();
		 batch.draw(cf, 300, 300);
         // Se estiver próximo do último quadro, desenha-o novamente para criar a transição suave
         if (nextFrame == totalFrames - 1) {
             batch.draw(animationFrames.get(3), 300, 300);
         }
		 batch.end();
	}
	
	@Override
	public void dispose () {
		animationSheet.dispose();
	}
}
