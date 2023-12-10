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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.controller.GameManager;
import com.game.controller.InputManager;
import com.game.model.controls.GameControl;
import com.game.util.UniversalUtil;

public class MenuScreen extends ScreenAdapter implements Screen {

    private Stage stage;
    private Label choiceCharacterLabel;
    private Label exitLabel;
    private BitmapFont font;
    private Actor selectedOption; // Índice da opção selecionada
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;

    public MenuScreen(final GameManager gameManager) {
        super(gameManager);
        setInputManager(new InputManager(new GameControl(), this));
        getGameManager().addInput(getInputManager());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        font = new BitmapFont(); // Você pode configurar a fonte aqui
        shapeRenderer = new ShapeRenderer();
        LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);

        choiceCharacterLabel = new Label("Adventure Mode", labelStyle);
        exitLabel = new Label("Sair", labelStyle);

        float stageCenterX = stage.getViewport().getWorldWidth() / 2f;
        float stageCenterY = stage.getViewport().getWorldHeight() / 2f;

        choiceCharacterLabel.setPosition(stageCenterX - choiceCharacterLabel.getWidth() / 2, stageCenterY);
        exitLabel.setPosition(stageCenterX - exitLabel.getWidth() / 2, stageCenterY - 50f); // Ajuste a posição vertical como desejado

        stage.addActor(choiceCharacterLabel);
        stage.addActor(exitLabel);
        selectedOption = UniversalUtil.filterActorsByType(Label.class, stage.getActors()).first();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        UniversalUtil.drawSelectionBorder(selectedOption, shapeRenderer);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        font.dispose();
    }

    @Override
    public void pressUp(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN)
            selectedOption = getNextLabelActor(stage.getActors().indexOf(selectedOption, false), true);
    }

    @Override
    public void pressDown(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN)
            selectedOption = getNextLabelActor(stage.getActors().indexOf(selectedOption, false), false);
    }

    @Override
    public void pressLeft(boolean isTypeKeyPressDOWN) {
    }

    @Override
    public void pressRight(boolean isTypeKeyPressDOWN) {
    }

    @Override
    public void pressActionA(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            if (getSelectedOption().equals(choiceCharacterLabel)) {
                dispose();
                getGameManager().removeInput(getInputManager());
                getGameManager().setScreen(new ChoiceCharacterScreen(getGameManager()));
            } else if (getSelectedOption().equals(exitLabel)) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void pressActionY(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            if (getSelectedOption().equals(choiceCharacterLabel)) {
                selectedOption = stage.getActors().get(stage.getActors().indexOf(exitLabel, false));
            }
        }
    }

    @Override
    public void pressStart(boolean isTypeKeyPressDOWN) {
        if (isTypeKeyPressDOWN) {
            if (getSelectedOption().equals(choiceCharacterLabel)) {
                dispose();
                getGameManager().removeInput(getInputManager());
                getGameManager().setScreen(new ChoiceCharacterScreen(getGameManager()));
            } else if (getSelectedOption().equals(exitLabel)) {
                Gdx.app.exit();
            }
        }
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
