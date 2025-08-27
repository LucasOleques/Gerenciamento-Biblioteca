package View.MenuModels;

import Controller.*;
import Model.*;
import static Utils.Icones.Icones.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class LivroView {

    private final LivroController livroController;
    private final JPanel painel;
    private final JTextField campoTitulo = new JTextField();
    private final JTextField campoPaginas = new JTextField();
    private final JTextField campoAutor = new JTextField();
    private final JTextArea areaLista = new JTextArea();

    private Timer limparTextoTimer;

    public LivroView(LivroController livroController) {
        this.livroController = livroController;

        painel = new JPanel(new BorderLayout(10, 10));
        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Gerenciamento de Livro");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        painelTopo.add(titulo, BorderLayout.NORTH);
        painel.add(painelTopo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));
        form.add(new JLabel("Título:"));
        form.add(campoTitulo);
        form.add(new JLabel("Páginas:"));
        form.add(campoPaginas);
        form.add(new JLabel("Nome do Autor:"));
        form.add(campoAutor);

        JButton btnSalvar = new JButton("Salvar Livro");
        btnSalvar.addActionListener(this::salvarLivro);
        JButton btnListar = new JButton("Listar Livros");
        btnListar.addActionListener(this::listarLivros);
        JButton btnEditar = new JButton("Editar Livro");
        btnEditar.addActionListener(this::editarLivro);
        JButton btnRemover = new JButton("Remover Livro");
        btnRemover.addActionListener(this::removerLivro);
        form.add(btnSalvar);
        form.add(btnListar);
        form.add(btnEditar);
        form.add(btnRemover);
        painel.add(form, BorderLayout.CENTER);

        areaLista.setEditable(false);
        areaLista.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaLista);
        scroll.setPreferredSize(new Dimension(100, 100));
        areaLista.setPreferredSize(new Dimension(100, 100));

        painel.add(scroll, BorderLayout.SOUTH);
    }

    private void salvarLivro(ActionEvent e) {
        String titulo = campoTitulo.getText().trim();
        String paginas = campoPaginas.getText().trim();
        String nomeAutor = campoAutor.getText().trim();

        if (titulo.isEmpty() || paginas.isEmpty() || nomeAutor.isEmpty()) {
            JOptionPane.showMessageDialog(painel, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        AutorController autorController = new AutorController();
        Autor autor = autorController.buscarPorNomeAutor(nomeAutor);
        if (autor == null) {
            JOptionPane.showMessageDialog(painel, "Autor não encontrado! Cadastre o autor primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Livro livro = new Livro(titulo, paginas, autor);
        livroController.cadastrarLivro(livro);
        JOptionPane.showMessageDialog(painel, "Livro cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        campoTitulo.setText("");
        campoPaginas.setText("");
        campoAutor.setText("");
    }

    private void listarLivros(ActionEvent e) {
        List<Livro> livros = livroController.listarLivros();

        if (livros.isEmpty()) {
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
        areaLista.setText("Lista de Livros:\n");
        for (Livro livro : livros) {
            sb.append("📚 ").append(livro.getTitulo())
                    .append(" | Páginas: ").append(livro.getPaginaslivro())
                    .append(" | Autor: ").append(livro.getAutorlivro().getNomeautor())
                    .append("\n");
        }
        areaLista.setText(sb.toString());
    }

    private void editarLivro(ActionEvent e){
        String tituloOriginal = JOptionPane.showInputDialog(null,"Título atual do livro:","Livro", JOptionPane.PLAIN_MESSAGE);
        if (tituloOriginal == null || tituloOriginal.trim().isEmpty()) {
            return;
        }

        Livro livro = livroController.buscarPorTitulo(tituloOriginal);

        if (livro == null) {
            JOptionPane.showMessageDialog(null, "Livro não encontrado.","Livro", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        String novoTitulo = JOptionPane.showInputDialog(null,"Novo título:","Livro", JOptionPane.PLAIN_MESSAGE);
        String novasPaginas = JOptionPane.showInputDialog(null,"Novo número de páginas:","Livro", JOptionPane.PLAIN_MESSAGE);
        String novoAutorNome = JOptionPane.showInputDialog(null,"Novo autor:","Livro", JOptionPane.PLAIN_MESSAGE);

        AutorController autorController = new AutorController();
        Autor novoAutor = autorController.buscarPorNomeAutor(novoAutorNome);
        if (novoAutor == null) {
            JOptionPane.showMessageDialog(null, "Autor não encontrado.","Autor", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        livro.atualizarInformacao(novoTitulo, novasPaginas, novoAutor);
        JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!","Autor", JOptionPane.PLAIN_MESSAGE);
    }

    private void removerLivro(ActionEvent e) {
        String livrotitulo = JOptionPane.showInputDialog(null,"Livro a ser removido:","Livro", JOptionPane.PLAIN_MESSAGE);
        Livro livro = livroController.buscarPorTitulo(livrotitulo);

        if (livro == null) {
            JOptionPane.showMessageDialog(null, "Livro não encontrado.","Livro", JOptionPane.PLAIN_MESSAGE);
        }
        livroController.removerLivro(livro);
        JOptionPane.showMessageDialog(null, "Livro removido com sucesso! " + SUCESSO ,"Livro", JOptionPane.PLAIN_MESSAGE);
    }

    public JPanel getPainel() {
        return painel;
    }
}
