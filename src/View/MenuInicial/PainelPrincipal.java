package View.MenuInicial;

import Utils.AdicionarImagemNaTela;
import View.MenuModels.*;
import View.MenuInicialBiblioteca.*;

import javax.swing.*;
import java.awt.*;

public class PainelPrincipal extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel painelPrincipal;
    private final JPanel telaMenuPrincipal;
    private LoginUsuarioView loginUsuarioView;

    public PainelPrincipal(AutorView autorView, LivroView livroView, EmprestimoView emprestimoView,
                           ClienteView clienteView, UsuarioView usuarioView) {

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        setContentPane(painel);

        painel.add(
                AdicionarImagemNaTela.criarAdicionarImagemNaTela(" ", "/Utils/Image/IconeJava.png", 80, 80),
                BorderLayout.NORTH
        );

        setTitle("Sistema de Gerenciamento de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);


        // Painel inicial
        telaMenuPrincipal = criarTelaComBotao("Bem-vindo ao Sistema de Gerenciamento de Biblioteca");
        telaMenuPrincipal.setFont(new Font("Arial", Font.BOLD, 25));

        // Painel de login, com callback para abrir a biblioteca
        loginUsuarioView = new LoginUsuarioView(() -> {
            dispose();
            new BibliotecaView(autorView, livroView, emprestimoView, clienteView, usuarioView, loginUsuarioView);
        });

        painelPrincipal.add(telaMenuPrincipal, "principal");
        painelPrincipal.add(loginUsuarioView.getPainelConteudo(), "login");

        setJMenuBar(criarMenu());
        add(painelPrincipal);

        setVisible(true);
    }

    private JMenuBar criarMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem itemTelaLogin = new JMenuItem("Tela de Login");
        JMenuItem itemMenuPrincipal = new JMenuItem("Menu Principal");
        JMenuItem itemSair = new JMenuItem("Sair");

        itemTelaLogin.addActionListener(e -> cardLayout.show(painelPrincipal, "login"));
        itemMenuPrincipal.addActionListener(e -> cardLayout.show(painelPrincipal, "principal"));
        itemSair.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(null,
                    "Deseja realmente sair?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        menu.add(itemTelaLogin);
        menu.add(itemMenuPrincipal);
        menu.add(itemSair);
        menuBar.add(menu);
        return menuBar;
    }

    private JPanel criarTelaComBotao(String nomeTela) {
        JPanel painel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(nomeTela, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 25));
        painel.add(label, BorderLayout.NORTH);

        JButton botao = new JButton("Realizar Login");
        botao.setPreferredSize(new Dimension(150, 40));
        botao.addActionListener(e -> cardLayout.show(painelPrincipal, "login"));

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.add(botao);

        painel.add(painelBotao, BorderLayout.CENTER);
        return painel;
    }
}