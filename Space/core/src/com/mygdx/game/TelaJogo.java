package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.TelaBase;
import com.badlogic.gdx.scenes.scene2d.Stage;

import javafx.application.Application;


/**
 * Created by DOUGLAS on 03/08/2015.
 */


public class TelaJogo extends TelaBase {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage palco;
    private BitmapFont fonte;
    private Label lbpontuacao;
    private Label lbgameOver;
    private Image jogador;
    private Texture texturajogador;
    private Texture texturajogadorDireita;
    private Texture texturajogadorEsquerda;
    private boolean IndoDireita;
    private boolean IndoEsquerda;
    private boolean atirando;
    private Texture texturameteoro1;
    private Texture texturameteoro2;
    private Stage PalcoInformacoes;

    private Array<Image> tiros = new Array<Image>();
    private Texture texturatiro;

    private Array<Image> meteoros1 = new Array<Image>();
    private Array<Image> meteoros2 = new Array<Image>();

    private Array<Texture> texturaExplosoes = new Array<Texture>();
    private Array<Explosao> explosoes = new Array<Explosao>();

    private Sound somTiro;
    private Sound somExplosao;
    private Music SomGameOver;
    private Music musicaFundo;
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
        PalcoInformacoes = new Stage(new FillViewport(camera.viewportWidth,camera.viewportHeight,camera));

        initTexturas();
        initFonte();
        initInformacoes();
        initJogador();
        initSons();

    }

    private void initSons() {
        somTiro = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
        somExplosao = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.mp3"));
        SomGameOver = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameover.mp3"));
        musicaFundo = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.mp3"));
        musicaFundo.setLooping(true);
    }

    private void initTexturas() {
        texturatiro = new Texture("sprites/shot.png");
        texturameteoro1 = new Texture("sprites/enemie-1.png");
        texturameteoro2 = new Texture("sprites/enemie-2.png");
        for (int i = 1; i <= 17; i++){
            Texture text = new Texture("sprites/explosion-" + i+".png");
            texturaExplosoes.add(text);
        }
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
        PalcoInformacoes.addActor(lbpontuacao);

        lbgameOver = new Label("Game Over", lbEstilo);
        PalcoInformacoes.addActor(lbgameOver);

    }

    /**
     * instancia os objetos da fonte
     */
    private void initFonte(){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.color = Color.WHITE;
        param.size = 24;
        param.shadowOffsetX = 2;
        param.shadowColor= Color.BLUE;

        fonte = generator.generateFont(param);
        generator.dispose();

    }

    /**
     * chamado a todo quadro de atualização do jogo ou famoso fps
     * @param delta tempo entre um quadro e utro em segundos
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( .15f, .15f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lbpontuacao.setPosition(10,camera.viewportHeight -lbpontuacao.getPrefHeight() - 20);
        lbpontuacao.setText(pontuacao + " Pontos");

        lbgameOver.setPosition(camera.viewportWidth/2-lbgameOver.getWidth()/2, camera.viewportHeight/2);
        lbgameOver.setVisible(gameOver == true);

        atualizarExplosoes(delta);

if (gameOver == false) {
    if (!musicaFundo.isPlaying())
        musicaFundo.play();
    CapituraTeclas();
    atualizaJogador(delta);
    atualizarTiros(delta);
    atualizarMeteoros(delta);
    detectarColisoes(meteoros1, 5);
    detectarColisoes(meteoros2, 15);
}else {
   if (musicaFundo.isPlaying())
       musicaFundo.stop();
    reiniciarJogo();
}

        //atualiza situação do palco
        palco.act(delta);
        //desenha o palco na tela
        palco.draw();

        PalcoInformacoes.act(delta);
        PalcoInformacoes.draw();


    }

    private void reiniciarJogo() {
        if (Gdx.input.isKeyJustPressed((Input.Keys.ENTER))){
            Preferences preferencias = Gdx.app.getPreferences("SpaceInvaders");
            int pontuacaoMaxima = preferencias.getInteger("pontuacao_maxima",0);
            if (pontuacao > pontuacaoMaxima){
                preferencias.putInteger("pontuacao_maxima",pontuacao);
                preferencias.flush();
            }
            game.setScreen((new TelaMenu(game)));
        }
    }

    private void atualizarExplosoes(float delta) {
        for (Explosao explosao : explosoes){
            if (explosao.getEstagio() >=16){
                explosoes.removeValue(explosao, true);
                explosao.getAtor().remove();
            }else {
                explosao.atualizar(delta);
            }
        }
    }

    Rectangle recJogador = new Rectangle();
    Rectangle recMeteoro= new Rectangle();
    Rectangle recTiro = new Rectangle();

    private int pontuacao = 0;
    private boolean gameOver;

    private void detectarColisoes(Array<Image> meteoros, int valePonto) {
        recJogador.set(jogador.getX(), jogador.getY(), jogador.getWidth(), jogador.getHeight());
          for (Image meteoro : meteoros){
              recMeteoro.set(meteoro.getX(), meteoro.getY(),meteoro.getWidth(), meteoro.getHeight());

              //detecta colisões com o tiro
              for (Image tiro: tiros){
                 recTiro.set(tiro.getX(), tiro.getY(), tiro.getWidth(), tiro.getHeight());
                if (recMeteoro.overlaps(recTiro)){
                    //aqui ocorre uma colisão do tiro com o meteoro 1
                    pontuacao += valePonto;
                    tiro.remove();
                    tiros.removeValue(tiro, true);
                    meteoro.remove();//remove do palco
                    meteoros.removeValue(meteoro, true);//remove da lista
                    criarExplosao(meteoro.getX() + meteoro.getWidth()/2, meteoro.getY()+meteoro.getHeight()/2);
                }
              }
              if (recJogador.overlaps(recMeteoro)){
                  //ocorre a colisão do jogador com o meteoro
                  gameOver = true;
                  SomGameOver.play();;

              }
          }


    }

    /**
     * cria a explosão na posição x e y
     * @param x
     * @param y
     */
    private void criarExplosao(float x, float y) {

        Image ator = new Image(texturaExplosoes.get(0));
        ator.setPosition(x-ator.getWidth()/2,y-ator.getHeight()/2);
        palco.addActor(ator);

        Explosao explosao = new Explosao(ator, texturaExplosoes);
        explosoes.add(explosao);
        somExplosao.play();
    }

    private void atualizarMeteoros(float delta) {
        int qtdMeteoros = meteoros1.size + meteoros2.size;//contando a quantidade de meteoros

        if (qtdMeteoros < 10) {
            int tipo = MathUtils.random(1, 4);//retorna valor 1 ou 2 excluindo o 3

            if (tipo == 1) {
                //cria meteoro1
                Image meteoro = new Image(texturameteoro1);
                float x = MathUtils.random(0, camera.viewportWidth - meteoro.getImageWidth());
                float y = MathUtils.random(camera.viewportHeight, camera.viewportHeight * 2);
                meteoro.setPosition(x, y);
                meteoros1.add(meteoro);
                palco.addActor(meteoro);

            } else if (tipo ==2){
                //cria meteoro 2
                Image meteoro = new Image(texturameteoro2);
                float x = MathUtils.random(0, camera.viewportWidth - meteoro.getImageWidth());
                float y = MathUtils.random(camera.viewportHeight, camera.viewportHeight * 2);
                meteoro.setPosition(x, y);
                meteoros2.add(meteoro);
                palco.addActor(meteoro);

            }
        }
        float velocidade1 = 100;//200pixels por segundo
        for (Image meteoro : meteoros1) {
            float x = meteoro.getX();
            float y = meteoro.getY() - velocidade1 * delta;
            meteoro.setPosition(x, y);//atualiza a posição do meteoro

            if (meteoro.getY() + meteoro.getHeight() < 0) {
                meteoro.remove();//remove do palco
                meteoros1.removeValue(meteoro, true); //remove da lista
                pontuacao = pontuacao - 30;
            }
        }


        float velocidade2 = 150;//250pixels por segundo
        for (Image meteoro : meteoros2) {
            float x = meteoro.getX();
            float y = meteoro.getY() - velocidade2 * delta;
            meteoro.setPosition(x, y);//atualiza a posição do meteoro

            if (meteoro.getY() + meteoro.getHeight() < 0) {
                meteoro.remove();//remove do palco
                meteoros2.removeValue(meteoro, true); //remove da lista
                pontuacao = pontuacao - 60;
            }
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
                somTiro.play();

            }

        }
        float velocidade = 500;//veocidade do tiro
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
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || clicouEsquerda()){
            IndoEsquerda= true;

        }
//verifica tecla direita
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || clicouDireita()){
            IndoDireita= true;

        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)|| Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.Android){
           atirando = true;

        }

    }

    private boolean clicouEsquerda() {

        if(Gdx.input.isTouched()) {
            Vector3 posicao = new Vector3();
            posicao.x = Gdx.input.getX();
            posicao.y = Gdx.input.getY();

            posicao = camera.unproject(posicao);
            float meio = camera.viewportWidth / 2;

            if (posicao.x < meio) {
                return true;
            }
        }
        return false;
    }

    private boolean clicouDireita() {
        if(Gdx.input.isTouched()) {
            Vector3 posicao = new Vector3();
            posicao.x = Gdx.input.getX();
            posicao.y = Gdx.input.getY();

            posicao = camera.unproject(posicao);
            float meio = camera.viewportWidth / 2;

            if (posicao.x > meio) {
                return true;
            }
        }
        return false;
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
        for (Texture text:texturaExplosoes){
            text.dispose();
        }

        PalcoInformacoes.dispose();
        somTiro.dispose();
        somExplosao.dispose();
        SomGameOver.dispose();
        musicaFundo.dispose();
    }
}
