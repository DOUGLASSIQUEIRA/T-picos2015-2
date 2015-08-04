package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.TelaBase;
import com.badlogic.gdx.scenes.scene2d.Stage;



/**
 * Created by DOUGLAS on 03/08/2015.
 */


public class TelaJogo extends TelaBase {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage palco;
    private BitmapFont fonte;
    private Label lbpontuacao;

    /**
     * referenciando a tela base com o construtor
     * @param game
     */
    public TelaJogo(MyGdxGame game) {
        super(game);
    }

    /**
     * chamado quando a tela é exibida
     */
    @Override
    public void show() {

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch= new SpriteBatch();
        palco = new Stage(new FillViewport(camera.viewportWidth,camera.viewportHeight,camera));

        initFonte();
        initInformacoes();

    }

    private void initInformacoes() {
        Label.LabelStyle lbEstilo = new Label.LabelStyle();
        lbEstilo.font = fonte;
        lbEstilo.fontColor = Color.WHITE;

        lbpontuacao = new Label("0 pontos", lbEstilo);
        palco.addActor(lbpontuacao);

    }

    private void initFonte(){
        fonte = new BitmapFont();


    }

    /**
     * chamado a todo uadro de atualização do jogo ou famoso fps
     * @param delta tempo entre um quadro e utro em segundos
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( .15f, .15f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lbpontuacao.setPosition(10,camera.viewportHeight -20);

        palco.act(delta);
        palco.draw();


    }

    /**
     * é chamado sempre quando há uma alteração no tamanho da tela
     * @param width novo valor de largura da tela
     * @param height novo valor de altura da tela
     */
    @Override
    public void resize(int width, int height) {

        camera.setToOrtho(false,width,height);
        camera.update();
    }

    /**
     * é chamado sempre que o jogo for minimizado
     */
    @Override
    public void pause() {

    }

    /**
     * é chamado sempre que o jogo voltar para o primeiro plano
     */
    @Override
    public void resume() {

    }

    /**
     * é chamado quando nossa tela for destruida
     */
    @Override
    public void dispose() {

        batch.dispose();
        palco.dispose();
        fonte.dispose();
    }
}
