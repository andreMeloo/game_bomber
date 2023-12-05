package com.game.View.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.controller.GameManager;
import com.game.controller.InputManager;
import com.game.model.controls.ControlAdapter;
import com.game.model.controls.MenuControl;
import com.game.util.UniversalUtil;

public class MenuScreen extends ScreenAdapter implements Screen {
    final GameManager gameManager;
    private Stage stage;
    private Label choiceCharacterLabel;
    private Label exitLabel;
    private BitmapFont font;
    private Actor selectedOption; // Índice da opção selecionada
    private ShapeRenderer shapeRenderer;

    public MenuScreen(final GameManager gameManager) {
        this.gameManager = gameManager;
        stage = new Stage();
        this.gameManager.setInputManager(new InputManager(getControler(), this));
        this.gameManager.setInput();
    }

    @Override
    public void show() {
        font = new BitmapFont(); // Você pode configurar a fonte aqui
        shapeRenderer = new ShapeRenderer();
        LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);

        choiceCharacterLabel = new Label("Adventure Mode", labelStyle);
        exitLabel = new Label("Sair", labelStyle);

        float stageCenterX = stage.getWidth() / 2f;
        float stageCenterY = stage.getHeight() / 2f;

        choiceCharacterLabel.setPosition(stageCenterX - choiceCharacterLabel.getWidth() / 2, stageCenterY);
        exitLabel.setPosition(stageCenterX - exitLabel.getWidth() / 2, stageCenterY - 50f); // Ajuste a posição vertical como desejado

        stage.addActor(choiceCharacterLabel);
        stage.addActor(exitLabel);
        selectedOption = UniversalUtil.filterActorsByType(Label.class, stage.getActors()).first();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        UniversalUtil.drawSelectionBorder(selectedOption, shapeRenderer);
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
        font.dispose();
    }

    @Override
    public ControlAdapter getControler() {
        return new MenuControl();
    }

    @Override
    public boolean pressUp(boolean isTypeKeyPressDOWN) {
        selectedOption = getNextLabelActor(stage.getActors().indexOf(selectedOption, false), true);

        return true;
    }

    @Override
    public boolean pressDown(boolean isTypeKeyPressDOWN) {
        selectedOption = getNextLabelActor(stage.getActors().indexOf(selectedOption, false), false);

        return true;
    }

    @Override
    public boolean pressLeft(boolean isTypeKeyPressDOWN) {
        return false;
    }

    @Override
    public boolean pressRight(boolean isTypeKeyPressDOWN) {
        return false;
    }

    @Override
    public boolean pressActionA(boolean isTypeKeyPressDOWN) {
        if (getSelectedOption().equals(choiceCharacterLabel)) {
            dispose();
            gameManager.setScreen(new ChoiceCharacterScreen(gameManager));
        } else if (getSelectedOption().equals(exitLabel)) {
            Gdx.app.exit();
        }

        return true;
    }

    @Override
    public boolean pressActionY(boolean isTypeKeyPressDOWN) {
        if (getSelectedOption().equals(choiceCharacterLabel)) {
            selectedOption = stage.getActors().get(stage.getActors().indexOf(exitLabel, false));
        } else {
            return false;
        }

        return true;
    }

    @Override
    public boolean pressStart(boolean isTypeKeyPressDOWN) {
        if (getSelectedOption().equals(choiceCharacterLabel)) {
            dispose();
            gameManager.setScreen(new ChoiceCharacterScreen(gameManager));
        } else if (getSelectedOption().equals(exitLabel)) {
            Gdx.app.exit();
        }

        return true;
    }

    private Actor getNextLabelActor(int currentIndex, boolean upDirection) {
        Array<Label> labelActors = UniversalUtil.filterActorsByType(Label.class, stage.getActors());

        int nextIndex;

        if (upDirection) {
            nextIndex = currentIndex - 1;
            if (nextIndex < 0) {
                nextIndex = labelActors.size - 1; // Vai para o último elemento se estiver no primeiro
            }
        } else {
            nextIndex = (currentIndex + 1) % labelActors.size; // Vai para o primeiro se estiver no último
        }

        return stage.getActors().get(nextIndex);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Label getChoiceCharacterLabel() {
        return choiceCharacterLabel;
    }

    public void setChoiceCharacterLabel(Label choiceCharacterLabel) {
        this.choiceCharacterLabel = choiceCharacterLabel;
    }

    public Label getExitLabel() {
        return exitLabel;
    }

    public void setExitLabel(Label exitLabel) {
        this.exitLabel = exitLabel;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public Actor getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Actor selectedOption) {
        this.selectedOption = selectedOption;
    }
}
