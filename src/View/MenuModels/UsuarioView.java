package View.MenuModels;

import Controller.UsuarioLoginController;
import Model.*;
import static Utils.Icones.Icones.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class UsuarioView {

    private final UsuarioLoginController usuarioLoginController;
    private final JPanel painel;
    private final JTextField campoLogin = new JTextField();
    private final JTextField campoSenha = new JTextField();
    private final JTextArea areaLista = new JTextArea();

    private Timer limparTextoTimer;

    public UsuarioView(UsuarioLoginController usuarioLoginController) {
        this.usuarioLoginController = usuarioLoginController;

        painel = new JPanel(new BorderLayout(10,10));
        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Gerenciamento de Usuário");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        painelTopo.add(titulo, BorderLayout.NORTH);
        painel.add(painelTopo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));
        form.add(new JLabel("Login:"));
        form.add(campoLogin);
        form.add(new JLabel("Senha:"));
        form.add(campoSenha);

        JButton botaoSalvar = new JButton("Salvar Usuário");
        botaoSalvar.addActionListener(this::salvarUsuario);
        JButton botaoListar = new JButton("Listar Usuários");
        botaoListar.addActionListener(this::listarUsuarios);
        JButton botaoEditar = new JButton("Editar Usuário");
        botaoEditar.addActionListener(this::editarUsuario);
        JButton botaoRemover = new JButton("Remover Usuário");
        botaoRemover.addActionListener(this::removerUsuario);
        form.add(botaoSalvar);
        form.add(botaoListar);
        form.add(botaoEditar);
        form.add(botaoRemover);
        painel.add(form, BorderLayout.CENTER);

        areaLista.setEditable(false);
        areaLista.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaLista);
        scroll.setPreferredSize(new Dimension(100, 100));
        areaLista.setPreferredSize(new Dimension(100, 100));

        painel.add(scroll, BorderLayout.SOUTH);

    }

    private void salvarUsuario(ActionEvent e) {
        String login = campoLogin.getText().trim();
        String senha = campoSenha.getText().trim();

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(painel, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (senha.length() < 4) {
            JOptionPane.showMessageDialog(painel, "A senha deve ter pelo menos 4 caracteres!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (usuarioLoginController.buscarPorLogin(login) != null) {
            JOptionPane.showMessageDialog(painel, "Usuário já cadastrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        usuarioLoginController.cadastrarUsuario(0, login, senha);

        JOptionPane.showMessageDialog(painel, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        campoLogin.setText("");
        campoSenha.setText("");
    }

    private void listarUsuarios(ActionEvent e) {
        List<UsuarioLogin> usuarios = usuarioLoginController.listarUsuarios();

        if (usuarios.isEmpty()) {
            areaLista.setText("Nenhum autor cadastrado.");

            // Cancela o timer anterior se existir
            if (limparTextoTimer != null && limparTextoTimer.isRunning()) {
                limparTextoTimer.stop();
            }

            // Cria novo timer para limpar em 5 segundos
            limparTextoTimer = new Timer(5000, _ -> areaLista.setText(""));
            limparTextoTimer.setRepeats(false); // executa só uma vez
            limparTextoTimer.start();
            return;
        }

        StringBuilder sb = new StringBuilder();
        areaLista.setText("Lista de Usuários:\n");
        for (UsuarioLogin usuarioLogin : usuarios) {
            sb.append("👤 ").append(usuarioLogin.getId())
                    .append(" | " + PESSOA + " ").append(usuarioLogin.getLogin())
                    .append(" | " + ALERTA + " ").append(usuarioLogin.getSenha().length())
                    .append("\n");
        }
        areaLista.setText(sb.toString());
    }

    private void editarUsuario(ActionEvent e) {
        String nome = JOptionPane.showInputDialog(null,"Login atual do usuário:","Usuário", JOptionPane.PLAIN_MESSAGE);
        if (nome == null || nome.trim().isEmpty()) {
            return;
        }
            UsuarioLogin usuarioLogin = usuarioLoginController.buscarPorLogin(nome);
        if (usuarioLogin == null) {
            JOptionPane.showMessageDialog(null, "Usuário não encontrado.","Usuário", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        int novoId = usuarioLogin.getId();
        String novoLogin = JOptionPane.showInputDialog(null,"Novo login:","Usuário", JOptionPane.PLAIN_MESSAGE);
        String novaSenha = JOptionPane.showInputDialog(null,"Nova senha:","Usuário", JOptionPane.PLAIN_MESSAGE);


        usuarioLogin.atualizarInformacao(novoId, novoLogin,novaSenha);
        JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!","Usuário", JOptionPane.PLAIN_MESSAGE);
    }

    private void removerUsuario(ActionEvent e) {
        String nome = JOptionPane.showInputDialog(null,"Usuário a ser removido:","Usuário", JOptionPane.PLAIN_MESSAGE);
        UsuarioLogin usuarioLogin = usuarioLoginController.buscarPorLogin(nome);

        if (usuarioLogin == null) {
            JOptionPane.showMessageDialog(null, "Usuário não encontrado.","Usuário", JOptionPane.PLAIN_MESSAGE);
        }
        usuarioLoginController.removerUsuario(usuarioLogin);
        JOptionPane.showMessageDialog(null, "Usuário removido com sucesso! " + SUCESSO ,"Usuário", JOptionPane.PLAIN_MESSAGE);
    }

    public JPanel getPainel() {
        return painel;
    }
}
