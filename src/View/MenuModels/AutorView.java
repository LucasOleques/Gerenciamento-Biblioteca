package View.MenuModels;

import Controller.*;
import Model.*;
import static Utils.Icones.Icones.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AutorView {

    private final AutorController autorController;
    private final JPanel painel;
    private final JTextField campoNome = new JTextField();
    private final JTextField campoIdade = new JTextField();
    private final JTextArea areaLista = new JTextArea();

    private Timer limparTextoTimer;

    public AutorView(AutorController autorController) {
        this.autorController = autorController;

        painel = new JPanel(new BorderLayout(10, 10));
        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Gerenciamento de Autor");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        painelTopo.add(titulo, BorderLayout.NORTH);
        painel.add(painelTopo, BorderLayout.NORTH);

        // Formulário
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));
        form.add(new JLabel("Nome:"));
        form.add(campoNome);
        form.add(new JLabel("Idade:"));
        form.add(campoIdade);

        JButton btnSalvar = new JButton("Salvar Autor");
        btnSalvar.addActionListener(this::salvarAutor);
        JButton btnListar = new JButton("Listar Autores");
        btnListar.addActionListener(this::listarAutores);
        JButton btnEditar = new JButton("Editar Autor");
        btnEditar.addActionListener(this::editarAutor);
        JButton btnRemover = new JButton("Remover Autor");
        btnRemover.addActionListener(this::removerAutor);
        form.add(btnSalvar);
        form.add(btnListar);
        form.add(btnEditar);
        form.add(btnRemover);
        painel.add(form, BorderLayout.CENTER);

        // Área de lista
        areaLista.setEditable(false);
        areaLista.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaLista);
        scroll.setPreferredSize(new Dimension(100, 100));
        areaLista.setPreferredSize(new Dimension(100, 100));

        painel.add(scroll, BorderLayout.SOUTH);
    }

    private void salvarAutor(ActionEvent e) {
        String nome = campoNome.getText().trim();
        String textoidade = campoIdade.getText().trim();

        if (nome.isEmpty() || textoidade.isEmpty()) {
            JOptionPane.showMessageDialog(painel, "Preencha todos os campos! ", "Aviso " + AVISO, JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(textoidade);
            if (idade < 1) {
                JOptionPane.showMessageDialog(painel, "Idade deve ser um número positivo!", "Aviso " + AVISO, JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(painel, "Idade inválida! Digite apenas números.", "Aviso " + AVISO, JOptionPane.WARNING_MESSAGE);
            return;
        }

        Autor autor = new Autor(nome, idade);
        autorController.cadastrarAutor(autor);

        JOptionPane.showMessageDialog(painel, "Autor cadastrado com sucesso! ", "Sucesso " + SUCESSO, JOptionPane.INFORMATION_MESSAGE);
        campoNome.setText("");
        campoIdade.setText("");
    }

    private void listarAutores(ActionEvent e) {
        List<Autor> autores = autorController.listarAutor();

        if (autores.isEmpty()) {
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

        // Se houver autores, cancela qualquer timer que limpe o texto
        if (limparTextoTimer != null && limparTextoTimer.isRunning()) {
            limparTextoTimer.stop();
        }
        StringBuilder sb = new StringBuilder();

        sb.append("Lista de Autores:\n");
        for (Autor autor : autores) {
            sb.append(PESSOA + " ").append(autor.getNomeautor())
                    .append(" | " + CALENDARIO + "Idade: ").append(autor.getIdadeautor())
                    .append("\n");
        }
        areaLista.setText(sb.toString());
    }

    private void editarAutor(ActionEvent actionEvent) {
        String nome = JOptionPane.showInputDialog(null, "Nome atual do autor:", "Autor", JOptionPane.PLAIN_MESSAGE);
        if (nome == null || nome.trim().isEmpty()) {
            return;
        }
        Autor autor = autorController.buscarPorNomeAutor(nome);

        if (autor == null) {
            JOptionPane.showMessageDialog(null, "Autor não encontrado.", "Autor", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        String novoNome = JOptionPane.showInputDialog(null, "Novo nome:", "Autor", JOptionPane.PLAIN_MESSAGE);
        int novaIdade = Integer.parseInt(JOptionPane.showInputDialog(null, "Nova idade:", "Autor", JOptionPane.PLAIN_MESSAGE));

        autor.atualizarInformacao(novoNome, novaIdade);
        JOptionPane.showMessageDialog(null, "Autor atualizado com sucesso! " + SUCESSO, "Autor", JOptionPane.PLAIN_MESSAGE);
    }

    private void removerAutor(ActionEvent e) {
        String nome = JOptionPane.showInputDialog(null, "Autor a ser removido:", "Autor", JOptionPane.PLAIN_MESSAGE);
        Autor autor = autorController.buscarPorNomeAutor(nome);

        if (autor == null) {
            JOptionPane.showMessageDialog(null, "Autor não encontrado.", "Autor", JOptionPane.PLAIN_MESSAGE);
        }
        autorController.removerAutor(autor);
        JOptionPane.showMessageDialog(null, "Autor removido com sucesso! " + SUCESSO, "Autor", JOptionPane.PLAIN_MESSAGE);
    }

    public JPanel getPainel() {
        return painel;
    }
}
