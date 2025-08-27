package View.MenuInicial;

import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginUsuarioView {

    private final CardLayout layout;
    private final JPanel painelConteudo;
    private final Runnable onLoginSuccess;
    private final JTextField campoLogin;
    private final JPasswordField campoSenha;

    public LoginUsuarioView(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;

        layout = new CardLayout();
        painelConteudo = new JPanel(layout); // Esse painel controla as telas

        JPanel painelLogin = new JPanel(new BorderLayout(10, 10));
        JPanel painelTopo = new JPanel(new BorderLayout(10,10));

        JLabel titulo = new JLabel("Login Usuario", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        painelTopo.add(titulo, BorderLayout.SOUTH);
        painelLogin.add(painelTopo, BorderLayout.NORTH);


        // Painel com formulário de login
        JPanel form = new JPanel(new GridLayout(5, 3, 20, 20));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel labelUsuario = new JLabel("Usuário:");
        campoLogin = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        JButton botaoLogin = new JButton("Login");
        botaoLogin.setPreferredSize(new Dimension(100, 30));
        botaoLogin.addActionListener(this::autenticarUsuario);

        form.add(labelUsuario);
        form.add(campoLogin);
        form.add(labelSenha);
        form.add(campoSenha);
        form.add(new JLabel()); // espaço vazio
        form.add(botaoLogin);

        painelLogin.add(form, BorderLayout.CENTER);

        // Adiciona a tela de login no painel de navegação
        painelConteudo.add(painelLogin, "login");

        // Mostra a tela de login inicialmente
        layout.show(painelConteudo, "login");
    }

    private void loginSucesso() {
        onLoginSuccess.run(); // Chama a função que vai abrir a próxima tela
    }

    private void autenticarUsuario(ActionEvent e) {
        UsuarioLoginController usuarioLoginController = new UsuarioLoginController();
        usuarioLoginController.cadastrarUsuario(0, "admin", "oleques");

        String usuario = campoLogin.getText();
        String senha = new String(campoSenha.getPassword());

        if (usuarioLoginController.login(usuario, senha)) {
            JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
            campoLogin.setText("");
            campoSenha.setText("");
            loginSucesso();
        } else {
            JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getPainelConteudo() {
        return painelConteudo;
    }
}
