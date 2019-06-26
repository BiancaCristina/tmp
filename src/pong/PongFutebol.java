package pong;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PongFutebol extends Pong {

    public void paintComponent(Graphics g) {
        BufferedImage bufferedImage = new BufferedImage(getLargura(), getAltura(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getLargura(), getAltura());

        if (isAcabou() && isTipoJogo()) {
            // Apenas encerra a partida caso não seja modo treino
            g2d.setColor(Color.RED);
            g2d.drawString("Fim de jogo!", 350, 250);
        }

        else {
            // Caso o jogo não tenha acabado ainda
            if (getBola().getX() < -10) {
                atualizarPontuacao(getJogadorDir());
            }

            else if (getBola().getX() > 810) {
                atualizarPontuacao(getJogadorEsq());
            }

            else {
                getJogadorEsq().desenhar(g2d);
                getJogadorDir().desenhar(g2d);
                getBola().desenhar(g2d);
                getLinhaObstaculoEsq().desenhar(g2d);
                getLinhaObstaculoDir().desenhar(g2d);
                getBordaObstaculo().desenhar(g2d);
            }
        }

        Graphics2D g2dComponent = (Graphics2D)g;
        g2dComponent.drawImage(bufferedImage, null, 0,0);
    }

    @Override
    public void iniciarPong() {
        while(!isAcabou()) {
            getJogadorEsq().mover(getAltura() - getBordaObstaculo().getBordaSup().getAltura());
            getJogadorDir().mover(getAltura() - getBordaObstaculo().getBordaSup().getAltura());
            getBola().mover(getAltura());
            getBola().verificaColisao(getJogadorEsq(), getJogadorDir(), getLargura());
            getBola().verificaColisao(getBordaObstaculo(), getAltura(), getLargura());
            getBola().verificaColisao(getLinhaObstaculoEsq());
            getBola().verificaColisao(getLinhaObstaculoDir());
            getLinhaObstaculoEsq().mover(getAltura() - getBordaObstaculo().getBordaSup().getAltura());
            getLinhaObstaculoDir().mover(getAltura() - getBordaObstaculo().getBordaSup().getAltura());
            repaint();

            try {
                Thread.sleep(10);
            }

            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Printa que o jogo acabou
        repaint();
    }
}
