package com.game.View.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.game.controller.GameManager;
import com.game.controller.InputManager;
import com.game.model.controls.ControlAdapter;
import com.game.model.controls.MenuControl;
import com.game.util.UniversalUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChoiceCharacterScreen extends ScreenAdapter implements Screen {
    final GameManager gameManager;
    private Stage stage;
    private Texture textureSelectCaracter;
    Map<Image, Pair<Integer, Integer>> imagePositions;
    private Actor selectedCharacter;
    private ShapeRenderer shapeRenderer;

    /**
     * Statics Values
     */
    private static final String CHARACTER = "select-cowboy.png";


    public ChoiceCharacterScreen(final GameManager gameManager) {
        this.gameManager = gameManager;
        stage = new Stage();
        this.gameManager.setInputManager(new InputManager(getControler(), this));
        this.gameManager.setInput();
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        imagePositions = new HashMap<>();
        textureSelectCaracter = new Texture(Gdx.files.internal(CHARACTER));
        float stageCenterX = stage.getWidth() / 2f;
        float stageCenterY = stage.getHeight() / 2f;
        float linha = 1f;
        float coluna = 1f;


        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Image image = new Image(textureSelectCaracter);
                image.setPosition(stageCenterX + coluna * ( - image.getWidth() - 15f), stageCenterY + (linha > 0 ? 0 : - image.getWidth() - 60f));
                imagePositions.put(image, new Pair<>(i,j));
                stage.addActor(image);
                coluna = coluna * (-1f);
            }
            linha = linha * (-1f);
        }

        selectedCharacter = imagePositions.keySet().iterator().next();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        UniversalUtil.drawSelectionBorder(selectedCharacter, shapeRenderer);
    }

    @Override
    public void resize(int width, int height) {

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
        textureSelectCaracter.dispose();
    }

    @Override
    public ControlAdapter getControler() {
        return new MenuControl();
    }

    @Override
    public boolean pressUp(boolean isTypeKeyPressDOWN) {
        Pair<Integer, Integer> position = imagePositions.get(selectedCharacter);
        int currentRow = position.getFirst();
        int currentColumn = position.getSecond();

        int newRow = (currentRow - 1 + 2) % 2; // totalRows é o número total de linhas na matriz
        Pair<Integer, Integer> newSelectedPosition = new Pair<>(newRow, currentColumn);

        // Encontrar o novo elemento selecionado com base na nova posição na matriz
        for (Map.Entry<Image, Pair<Integer, Integer>> entry : imagePositions.entrySet()) {
            if (entry.getValue().equals(newSelectedPosition)) {
                selectedCharacter = entry.getKey(); // Atualiza o elemento selecionado
                break;
            }
        }

        return true;
    }

    @Override
    public boolean pressDown(boolean isTypeKeyPressDOWN) {
        Pair<Integer, Integer> position = imagePositions.get(selectedCharacter);
        int currentRow = position.getFirst();
        int currentColumn = position.getSecond();

        int newRow = (currentRow + 1 + 2) % 2; // totalRows é o número total de linhas na matriz
        Pair<Integer, Integer> newSelectedPosition = new Pair<>(newRow, currentColumn);

        // Encontrar o novo elemento selecionado com base na nova posição na matriz
        for (Map.Entry<Image, Pair<Integer, Integer>> entry : imagePositions.entrySet()) {
            if (entry.getValue().equals(newSelectedPosition)) {
                selectedCharacter = entry.getKey(); // Atualiza o elemento selecionado
                break;
            }
        }

        return true;
    }

    @Override
    public boolean pressLeft(boolean isTypeKeyPressDOWN) {
        Pair<Integer, Integer> position = imagePositions.get(selectedCharacter);
        int currentRow = position.getFirst();
        int currentColumn = position.getSecond();

        int newColumn = (currentColumn - 1 + 2) % 2; // totalRows é o número total de linhas na matriz
        Pair<Integer, Integer> newSelectedPosition = new Pair<>(currentRow, newColumn);

        // Encontrar o novo elemento selecionado com base na nova posição na matriz
        for (Map.Entry<Image, Pair<Integer, Integer>> entry : imagePositions.entrySet()) {
            if (entry.getValue().equals(newSelectedPosition)) {
                selectedCharacter = entry.getKey(); // Atualiza o elemento selecionado
                break;
            }
        }

        return true;
    }

    @Override
    public boolean pressRight(boolean isTypeKeyPressDOWN) {
        Pair<Integer, Integer> position = imagePositions.get(selectedCharacter);
        int currentRow = position.getFirst();
        int currentColumn = position.getSecond();

        int newColumn = (currentColumn + 1 + 2) % 2; // totalRows é o número total de linhas na matriz
        Pair<Integer, Integer> newSelectedPosition = new Pair<>(currentRow, newColumn);

        // Encontrar o novo elemento selecionado com base na nova posição na matriz
        for (Map.Entry<Image, Pair<Integer, Integer>> entry : imagePositions.entrySet()) {
            if (entry.getValue().equals(newSelectedPosition)) {
                selectedCharacter = entry.getKey(); // Atualiza o elemento selecionado
                break;
            }
        }

        return true;
    }

    @Override
    public boolean pressActionA(boolean isTypeKeyPressDOWN) {
        return false;
    }

    @Override
    public boolean pressActionY(boolean isTypeKeyPressDOWN) {
        dispose();
        gameManager.setScreen(new MenuScreen(gameManager));
        return true;
    }

    @Override
    public boolean pressStart(boolean isTypeKeyPressDOWN) {
        return false;
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
