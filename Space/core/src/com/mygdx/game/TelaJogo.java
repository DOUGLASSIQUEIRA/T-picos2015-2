package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
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
    private Image jogador;
    private Texture texturajogador;
    private Texture texturajogadorDireita;
    private Texture texturajogadorEsquerda;
    private boolean IndoDireita;
    private boolean IndoEsquerda;
    private boolean atirando;
    private Texture texturameteoro1;
    private Texture texturameteoro2;

    private Array<Image> tiros = new Array<Image>();
    private Texture texturatiro;

    private Array<Image> meteoros1 = new Array<Image>();
    private Array<Image> meteoros2 = new Array<Image>();

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

        initTexturas();
        initFonte();
        initInformacoes();
        initJogador();

    }

    private void initTexturas() {
        texturatiro = new Texture("sprites/shot.png");
        texturameteoro1 = new Texture("sprites/enemie-1.png");
        texturameteoro2 = new Texture("sprites/enemie-2.png");
    }

    /**
     * Instancia o jogador e a textura
     */
    private void initJogador(){
        texturajogador = new Texture("sprites/player.png");
        texturajogadorDireita = new Texture("sprites/player-right.png");
        texturajogadorEsquerda = new Texture("sprites/player-left.png");

        jogador = new Image(texturajogador);

        float x =camera.viewportWidth/2- jogador.getWidth()/2;
        float y = 15;
        jogador.setPosition(x,y);
        palco.addActor(jogador);




    }

    /**
     * instancia as informações escritas na tela
     */
    private void initInformacoes() {
        Label.LabelStyle lbEstilo = new Label.LabelStyle();
        lbEstilo.font = fonte;
        lbEstilo.fontColor = Color.WHITE;

        lbpontuacao = new Label("0 pontos", lbEstilo);
        palco.addActor(lbpontuacao);

    }

    /**
     * instancia os objetos da fonte
     */
    private void initFonte(){
        fonte = new BitmapFont();


    }

    /**
     * chamado a todo quadro de atualização do jogo ou famoso fps
     * @param delta tempo entre um quadro e utro em segundos
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( .15f, .15f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lbpontuacao.setPosition(10,camera.viewportHeight -20);

        CapituraTeclas();
        atualizaJogador(delta);
        atualizarTiros(delta);
        atualizarMeteoros(delta);

        //atualiza situação do palco
        palco.act(delta);
        //desenha o palco na tela
        palco.draw();


    }

    private void atualizarMeteoros(float delta) {
        int tipo = MathUtils.random(1,3);

        if (tipo == 1){
            //cria meteoro1
            Image meteoro = new Image(texturameteoro1);
            float x = MathUtils.random(0, camera.viewportWidth - meteoro.getImageWidth());
            float y = MathUtils.random(camera.viewportHeight, camera.viewportHeight * 2);
            meteoro.setPosition(x,y);
            meteoros1.add(meteoro);
            palco.addActor(meteoro);

        }else{
            //cria meteoro 2
        }
        float velocidade = 200;
        for (Image meteoro : meteoros1){
            float x = meteoro.getX();
            float y = meteoro.getY() - velocidade * delta;
            meteoro.setPosition(x,y);

        }

    }


    private final float maxIntervaloTiros = 0.4f;//minimo de tempo entre tiros
    private float IntervaloTiros = 0;//tempo acumulado entre os tiros



    private void atualizarTiros(float delta) {
        IntervaloTiros = IntervaloTiros +  delta;//acumula o tempo percorrido
        if(atirando){
//verifica se o tempo minimo foi atualizado
            if (IntervaloTiros >= maxIntervaloTiros) {
                Image tiro = new Image(texturatiro);

                float x = jogador.getX() + jogador.getWidth() / 2 - tiro.getWidth()/2;
                float y = jogador.getY() + jogador.getHeight();
                tiro.setPosition(x, y);
                tiros.add(tiro);
                palco.addActor(tiro);
                IntervaloTiros = 0;

            }

        }
        float velocidade = 250;//veocidade do tiro
        //percorre todos tiros existentes
        for(Image tiro:tiros){
            //movimenta os tiros em direção ao topo
            float x = tiro.getX();
            float y = tiro.getY() + velocidade * delta;
            tiro.setPosition(x,y);
            //removem os tiros que sairam da tela
            if (tiro.getY() > camera.viewportHeight){
               tiros.removeValue(tiro, true);//remove da lista
                tiro.remove();//remove do palco
            }
        }

    }

    private void atualizaJogador(float delta) {
        //atualiza a osiçaõ do jogador a velocidade determinada
      float velocidade = 500;
        if(IndoDireita){
            //verifica se o jogador esta dentro da tela
            if (jogador.getX() < camera.viewportWidth - jogador.getImageWidth()) {


                float x = jogador.getX() + velocidade * delta;
                float y = jogador.getY();
                jogador.setPosition(x, y);
            }
        }

        if(IndoEsquerda){
            //verifica se o jogador esta dentro da tela
            if(jogador.getX() > 0) {
                float x = jogador.getX() - velocidade * delta;
                float y = jogador.getY();
                jogador.setPosition(x, y);

            }
        }

        if (IndoDireita){
            //trocar imagem direita
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturajogadorDireita)));
        }else if(IndoEsquerda){
            //trocar imagem direita
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturajogadorEsquerda)));
        }else{
            //trocar imagem centro
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturajogador)));

        }

    }

    private void CapituraTeclas() {
        IndoDireita = false;
        IndoEsquerda = false;
        atirando = false;

      //verifica tecla esquerda
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            IndoEsquerda= true;

        }
//verifica tecla direita
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            IndoDireita= true;

        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
           atirando = true;

        }

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
        texturajogadorDireita.dispose();;
        texturajogadorEsquerda.dispose();;
        texturajogador.dispose();
        texturatiro.dispose();
        texturameteoro1.dispose();
        texturameteoro2.dispose();

    }
}
