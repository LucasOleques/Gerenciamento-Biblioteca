package Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AdicionarImagemNaTela {

    /**
     * Cria um painel com uma imagem e um título centralizado.
     *
     * @param tituloTexto    Texto do título a ser exibido abaixo da imagem.
     * @param caminhoImagem  Caminho do recurso da imagem (ex: "/Utils/Image/IconeJava.png").
     * @param larguraImagem  Largura da imagem em pixels.
     * @param alturaImagem   Altura da imagem em pixels.
     * @return JPanel pronto para ser adicionado ao layout (tipicamente em BorderLayout.NORTH).
     */
    public static JPanel criarAdicionarImagemNaTela(String tituloTexto, String caminhoImagem, int larguraImagem, int alturaImagem) {
        JPanel painelTopo = new JPanel(new BorderLayout());

        try {
            ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(AdicionarImagemNaTela.class.getResource(caminhoImagem)));
            Image imagemRedimensionada = imageIcon.getImage().getScaledInstance(larguraImagem, alturaImagem, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(imagemRedimensionada));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            painelTopo.add(logoLabel, BorderLayout.NORTH);
        } catch (NullPointerException e) {
            System.err.println("Imagem não encontrada: " + caminhoImagem);
        }

        JLabel titulo = new JLabel(tituloTexto);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        painelTopo.add(titulo, BorderLayout.SOUTH);

        return painelTopo;
    }
}

