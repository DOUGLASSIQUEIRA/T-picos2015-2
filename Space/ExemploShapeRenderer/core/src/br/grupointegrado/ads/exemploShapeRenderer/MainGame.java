package br.grupointegrado.ads.exemploShapeRenderer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class MainGame extends ApplicationAdapter {

    private ShapeRenderer renderer;

    @Override
    public void create() {
        renderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        // limpa a tela com a cor PRETA
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // prepara o ShapeRenderer para desenhar com linhas
        renderer.begin(ShapeRenderer.ShapeType.Line);

        // altera a cor para VERMELHO
        renderer.setColor(1, 0, 0, 1);

        Vector2 pontoA = new Vector2(150, 300); // define um ponto X e Y na tela
        Vector2 pontoB = new Vector2(150, 150); // define um ponto X e Y na tela

        Vector2 pontoD = new Vector2(200, 300); // define um ponto X e Y na tela
        Vector2 pontoE = new Vector2(200, 150); // define um ponto X e Y na tela

        Vector2 pontoF = new Vector2(250, 250); // define um ponto X e Y na tela
        Vector2 pontoG = new Vector2(100, 250); // define um ponto X e Y na tela


        Vector2 pontoH = new Vector2(250, 200); // define um ponto X e Y na tela
        Vector2 pontoI = new Vector2(100, 200); // define um ponto X e Y na tela

        Vector2 xisa = new Vector2(155, 295); // define um ponto X e Y na tela
        Vector2 xisb = new Vector2(195, 255); // define um ponto X e Y na tela
        Vector2 xisc = new Vector2(195, 295); // define um ponto X e Y na tela
        Vector2 xisd = new Vector2(155, 255); // define um ponto X e Y na tela

        Vector2 xise = new Vector2(205, 295); // define um ponto X e Y na tela
        Vector2 xisf = new Vector2(245, 255); // define um ponto X e Y na tela
        Vector2 xisg = new Vector2(245, 295); // define um ponto X e Y na tela
        Vector2 xish = new Vector2(205, 255); // define um ponto X e Y na tela

        Vector2 xisi = new Vector2(145, 205); // define um ponto X e Y na tela
        Vector2 xisj = new Vector2(105, 245); // define um ponto X e Y na tela
        Vector2 xisk = new Vector2(105, 205); // define um ponto X e Y na tela
        Vector2 xisl = new Vector2(145, 245); // define um ponto X e Y na tela

        Vector2 xism = new Vector2(145, 155); // define um ponto X e Y na tela
        Vector2 xisn = new Vector2(105, 195); // define um ponto X e Y na tela
        Vector2 xiso = new Vector2(105, 155); // define um ponto X e Y na tela
        Vector2 xisp = new Vector2(145, 195); // define um ponto X e Y na tela


        // traça uma linha para do ponto A ao B
        renderer.line(pontoA, pontoB);
        renderer.line(pontoD, pontoE);
        renderer.line(pontoF, pontoG);
        renderer.line(pontoH, pontoI);


        //xis
        renderer.line(xisa, xisb);
        renderer.line(xisc, xisd);

        renderer.line(xise, xisf);
        renderer.line(xisg, xish);

        renderer.line(xisi, xisj);
        renderer.line(xisk, xisl);

        renderer.line(xism, xisn);
        renderer.line(xiso, xisp);


        // altera a cor para VERDE
        renderer.setColor(0, 1, 0, 1);

        Vector2 circuloa = new Vector2(120, 280); // define um ponto X e Y na tela
        Vector2 circulob = new Vector2(175, 225); // define um ponto X e Y na tela
        Vector2 circuloc = new Vector2(225, 175); // define um ponto X e Y na tela
        Vector2 circulod = new Vector2(175, 175); // define um ponto X e Y na tela
        Vector2 circuloe = new Vector2(225, 225); // define um ponto X e Y na tela
        float raio = 15;

        // desenha um círculo no ponto C
        renderer.circle(circuloa.x, circuloa.y, raio);
        renderer.circle(circulob.x, circulob.y, raio);
        renderer.circle(circuloc.x, circuloc.y, raio);
        renderer.circle(circulod.x, circulod.y, raio);
        renderer.circle(circuloe.x, circuloe.y, raio);

        // encerra o desenho
        renderer.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        renderer.dispose();
    }
}
