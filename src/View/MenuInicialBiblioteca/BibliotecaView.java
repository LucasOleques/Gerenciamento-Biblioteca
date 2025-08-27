package View.MenuInicialBiblioteca;

import Utils.AdicionarImagemNaTela;
import View.MenuInicial.*;
import View.MenuModels.*;
import static Utils.Icones.Icones.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BibliotecaView extends JFrame {

    private final CardLayout layout;
    private final JPanel painelConteudo;

    private final AutorView autorView;
    private final LivroView livroView;
    private final EmprestimoView emprestimoView;
    private final ClienteView clienteView;
    private final UsuarioView usuarioView;
    private final LoginUsuarioView loginUsuarioView;

    public BibliotecaView(AutorView autorView, LivroView livroView, EmprestimoView emprestimoView,
                          ClienteView clienteView, UsuarioView usuarioView, LoginUsuarioView loginUsuarioView) {
        super("Sistema de Biblioteca " + LIVRO_ABERTO);

        this.autorView = autorView;
        this.livroView = livroView;
        this.emprestimoView = emprestimoView;
        this.clienteView = clienteView;
        this.usuarioView = usuarioView;
        this.loginUsuarioView = loginUsuarioView;

        JPanel painelTopo = new JPanel(new BorderLayout());
        setContentPane(painelTopo);
        painelTopo.add(
                AdicionarImagemNaTela.criarAdicionarImagemNaTela(" ", "/Utils/Image/IconeJava.png", 80, 80),
                BorderLayout.NORTH
        );

        // Configuração da janela principal
        setTitle("Sistema de Gerenciamento de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        painelConteudo = new JPanel(layout);

        // Adiciona os painéis das views
        painelConteudo.add(autorView.getPainel(), "autor");
        painelConteudo.add(livroView.getPainel(), "livro");
        painelConteudo.add(emprestimoView.getPainel(), "emprestimo");
        painelConteudo.add(clienteView.getPainel(), "cliente");
        painelConteudo.add(usuarioView.getPainel(), "usuario");
        painelConteudo.add(loginUsuarioView.getPainelConteudo(), "login");

        // Cria a barra de menu
        setJMenuBar(criarMenu());

        // Painel com imagem no topo da pasta do sistema

        // Adiciona os painéis na janela
        add(painelConteudo, BorderLayout.CENTER);

        setVisible(true);
    }

    private JMenuBar criarMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu login = new JMenu("Login");
        JMenu cadastros = new JMenu("Cadastros");
        JMenu emprestimos = new JMenu("Empréstimos");

        JMenuItem itemAutor = new JMenuItem(new AbstractAction("Autor") {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(painelConteudo, "autor");
            }
        });
        JMenuItem itemLivro = new JMenuItem(new AbstractAction("Livro") {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(painelConteudo, "livro");
            }
        });
        JMenuItem itemEmprestimo = new JMenuItem(new AbstractAction("Empréstimo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(painelConteudo, "emprestimo");
            }
        });
        JMenuItem itemCliente = new JMenuItem(new AbstractAction("Cliente") {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(painelConteudo, "cliente");
            }
        });
        JMenuItem itemUsuario = new JMenuItem(new AbstractAction("Usuário") {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(painelConteudo, "usuario");
            }
        });
        JMenuItem itemLogin = new JMenuItem(new AbstractAction("Login") {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(painelConteudo, "login");
            }
        });


        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));

        login.add(itemLogin);
        login.add(itemUsuario);
        login.add(itemSair);
        cadastros.add(itemCliente);
        cadastros.add(itemAutor);
        cadastros.add(itemLivro);
        emprestimos.add(itemEmprestimo);

        menuBar.add(login);
        menuBar.add(cadastros);
        menuBar.add(emprestimos);
        return menuBar;
    }
}
