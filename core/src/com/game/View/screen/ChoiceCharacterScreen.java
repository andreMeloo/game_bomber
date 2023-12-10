package com.game.View.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.controller.GameManager;
import com.game.controller.InputManager;
import com.game.model.controls.GameControl;
import com.game.util.UniversalUtil;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ChoiceCharacterScreen extends ScreenAdapter implements Screen {
    final GameManager gameManager;
    private InputManager inputManager;

    private Stage stage;
    private Texture textureSelectCaracter;
    Map<Image, Pair<Integer, Integer>> imagePositions;
    private Actor selectedCharacter;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;

    /**
     * Statics Values
     */
    private static final String CHARACTER = "select-cowboy.png";
    private static final Float SPACE_BETWEEN_CHARACTERS = 20f;
    private static final int COLUMNS_CHARACTERS = 5;
    private static final int ROWS_CHARACTERS = 3;


    public ChoiceCharacterScreen(final GameManager gameManager) {
        this.gameManager = gameManager;
        viewport =  new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        this.inputManager = new InputManager(new GameControl(), this);
        this.gameManager.addInput(inputManager);
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        imagePositions = new LinkedHashMap<>();
        textureSelectCaracter = new Texture(Gdx.files.internal(CHARACTER));
        createCharactersSelection();
    }

    private void createCharactersSelection() {
        for (int i = 0; i < ROWS_CHARACTERS; i++) {
            for (int j = 0; j < COLUMNS_CHARACTERS; j++) {
                Image image = new Image(textureSelectCaracter);
                image.setWidth(image.getWidth() * 2.5f);
                image.setHeight(image.getHeight() * 2.5f);
                image.setPosition(
                        (getWidthCenterStage() - ((image.getWidth() * COLUMNS_CHARACTERS) + ((SPACE_BETWEEN_CHARACTERS * 5) * (COLUMNS_CHARACTERS - 1))) / 2f) + ((image.getWidth() + (SPACE_BETWEEN_CHARACTERS * 5)) * j),
                        ((getHeightCenterStage() - (image.getHeight()*3)) + ((image.getHeight() * (ROWS_CHARACTERS - 1)) + ((SPACE_BETWEEN_CHARACTERS * 2) * (ROWS_CHARACTERS - 1))) / 2f) - ((image.getHeight() + (SPACE_BETWEEN_CHARACTERS * 2)) * i)
                );
                imagePositions.put(image, new Pair<>(i,j));
                stage.addActor(image);
            }
        }

        selectedCharacter = imagePositions.keySet().iterator().next();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        UniversalUtil.drawSelectionBorder(selectedCharacter, shapeRenderer);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
        textureSelectCaracter.dispose();
    }

    @Override
    public void pressUp(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            Pair<Integer, Integer> position = imagePositions.get(selectedCharacter);
            int currentRow = position.getFirst();
            int currentColumn = position.getSecond();

            int newRow = (currentRow - 1 + ROWS_CHARACTERS) % ROWS_CHARACTERS; // totalRows é o número total de linhas na matriz
            Pair<Integer, Integer> newSelectedPosition = new Pair<>(newRow, currentColumn);

            // Encontrar o novo elemento selecionado com base na nova posição na matriz
            for (Map.Entry<Image, Pair<Integer, Integer>> entry : imagePositions.entrySet()) {
                if (entry.getValue().equals(newSelectedPosition)) {
                    selectedCharacter = entry.getKey(); // Atualiza o elemento selecionado
                    break;
                }
            }
        }
    }

    @Override
    public void pressDown(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            Pair<Integer, Integer> position = imagePositions.get(selectedCharacter);
            int currentRow = position.getFirst();
            int currentColumn = position.getSecond();

            int newRow = (currentRow + 1 + ROWS_CHARACTERS) % ROWS_CHARACTERS; // totalRows é o número total de linhas na matriz
            Pair<Integer, Integer> newSelectedPosition = new Pair<>(newRow, currentColumn);

            // Encontrar o novo elemento selecionado com base na nova posição na matriz
            for (Map.Entry<Image, Pair<Integer, Integer>> entry : imagePositions.entrySet()) {
                if (entry.getValue().equals(newSelectedPosition)) {
                    selectedCharacter = entry.getKey(); // Atualiza o elemento selecionado
                    break;
                }
            }
        }
    }

    @Override
    public void pressLeft(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            Pair<Integer, Integer> position = imagePositions.get(selectedCharacter);
            int currentRow = position.getFirst();
            int currentColumn = position.getSecond();

            int newColumn = (currentColumn - 1 + COLUMNS_CHARACTERS) % COLUMNS_CHARACTERS; // totalRows é o número total de linhas na matriz
            Pair<Integer, Integer> newSelectedPosition = new Pair<>(currentRow, newColumn);

            // Encontrar o novo elemento selecionado com base na nova posição na matriz
            for (Map.Entry<Image, Pair<Integer, Integer>> entry : imagePositions.entrySet()) {
                if (entry.getValue().equals(newSelectedPosition)) {
                    selectedCharacter = entry.getKey(); // Atualiza o elemento selecionado
                    break;
                }
            }
        }
    }

    @Override
    public void pressRight(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            Pair<Integer, Integer> position = imagePositions.get(selectedCharacter);
            int currentRow = position.getFirst();
            int currentColumn = position.getSecond();

            int newColumn = (currentColumn + 1 + 5) % 5; // totalRows é o número total de linhas na matriz
            Pair<Integer, Integer> newSelectedPosition = new Pair<>(currentRow, newColumn);

            // Encontrar o novo elemento selecionado com base na nova posição na matriz
            for (Map.Entry<Image, Pair<Integer, Integer>> entry : imagePositions.entrySet()) {
                if (entry.getValue().equals(newSelectedPosition)) {
                    selectedCharacter = entry.getKey(); // Atualiza o elemento selecionado
                    break;
                }
            }
        }
    }

    @Override
    public void pressActionA(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            dispose();
            gameManager.removeInput(inputManager);
            gameManager.setScreen(new PlayGameScreen(gameManager));
        }
    }

    @Override
    public void pressActionY(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            dispose();
            gameManager.removeInput(inputManager);
            gameManager.setScreen(new MenuScreen(gameManager));
        }
    }

    @Override
    public void pressStart(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            dispose();
            gameManager.removeInput(inputManager);
            gameManager.setScreen(new PlayGameScreen(gameManager));
        }
    }

    private float getWidthCenterStage() {
        return stage.getWidth() / 2f;
    }

    private float getHeightCenterStage() {
        return stage.getHeight() / 2f;
    }

    private class Pair<T, U> {
        private final T first;
        private final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(first, pair.first) &&
                    Objects.equals(second, pair.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }
}
