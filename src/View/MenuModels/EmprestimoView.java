package View.MenuModels;

import Controller.*;
import Model.*;
import static Utils.Icones.Icones.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class EmprestimoView {

    private final EmprestimoController emprestimoController;
    private final JPanel painel;
    private final JTextField campoNome = new JTextField();
    private final JTextField campoTelefone = new JTextField();
    private final JTextField campoEmail = new JTextField();
    private final JTextField campoLivro = new JTextField();
    private final JTextArea areaLista = new JTextArea();

    private Timer limparTextoTimer;

    public EmprestimoView(EmprestimoController emprestimoController) {
        this.emprestimoController = emprestimoController;

        painel = new JPanel(new BorderLayout(10, 10));
        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Gerenciamento de Empréstimo");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        painelTopo.add(titulo, BorderLayout.NORTH);
        painel.add(painelTopo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));
        form.add(new JLabel("Nome do Usuário:"));
        form.add(campoNome);
        form.add(new JLabel("Telefone:"));
        form.add(campoTelefone);
        form.add(new JLabel("Email:"));
        form.add(campoEmail);
        form.add(new JLabel("Livro (título):"));
        form.add(campoLivro);

        JButton btnSalvar = new JButton("Salvar Empréstimos");
        btnSalvar.addActionListener(this::salvarEmprestimo);
        JButton btnListar = new JButton("Listar Empréstimos");
        btnListar.addActionListener(this::listarEmprestimos);
        JButton btnEditar = new JButton("Editar Empréstimo");
        btnEditar.addActionListener(this::editarEmprestimo);
        JButton btnRemover = new JButton("Remover Empréstimo");
        btnRemover.addActionListener(this::removerEmprestimo);
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

    private void salvarEmprestimo(ActionEvent e) {
        String nome = campoNome.getText().trim();
        int telefone = Integer.parseInt(campoTelefone.getText().trim());
        String email = campoEmail.getText().trim();
        String tituloLivro = campoLivro.getText().trim();

        if (nome.isEmpty() || telefone != 0 || email.isEmpty() || tituloLivro.isEmpty()) {
            JOptionPane.showMessageDialog(painel, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LivroController livroController = new LivroController();
        Livro livro = livroController.buscarPorTitulo(tituloLivro);
        if (livro == null) {
            JOptionPane.showMessageDialog(painel, "Livro não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(nome, telefone, email);
        LocalDate dataEmprestimo = LocalDate.now();
        emprestimoController.cadastrarEmprestimo(cliente, livro, dataEmprestimo);

        JOptionPane.showMessageDialog(painel, "Empréstimo registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        campoNome.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");
        campoLivro.setText("");
    }

    private void listarEmprestimos(ActionEvent e) {
        List<Emprestimo> emprestimos = emprestimoController.listarEmprestimo();

        if (emprestimos.isEmpty()) {
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
        areaLista.setText("Lista de Empréstimos:\n");
        for (Emprestimo emp : emprestimos) {
            sb.append(PESSOA + " ").append(emp.getCliente().getNome())
                    .append(" | ").append(emp.getLivro().getTitulo())
                    .append(" | " + CALENDARIO + " " ).append(emp.getDataEmprestimo())
                    .append("\n");
        }

        areaLista.setText(sb.toString());
    }

    private void editarEmprestimo(ActionEvent actionEvent) {
        String nomecliente = JOptionPane.showInputDialog(null,"Nome do cliente:","Empréstimo", JOptionPane.PLAIN_MESSAGE);
        String titulolivro = JOptionPane.showInputDialog(null,"Nome titulo do livro:","Empréstimo", JOptionPane.PLAIN_MESSAGE);
        if (nomecliente == null || nomecliente.trim().isEmpty() || titulolivro == null || titulolivro.trim().isEmpty()) {
            return;
        }
        ClienteController clienteController = new ClienteController();
        Cliente cliente = clienteController.buscarPorNomeCliente(nomecliente);
        LivroController livroController = new LivroController();
        Livro livro = livroController.buscarPorTitulo(titulolivro);

        Emprestimo emprestimo = emprestimoController.buscarPorNomeUsuarioLivro(cliente, livro);

        if (emprestimo == null) {
            JOptionPane.showMessageDialog(null, "Empréstimo não encontrado.","Empréstimo", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        String novocliente = JOptionPane.showInputDialog(null,"Novo cliente:","Empréstimo", JOptionPane.PLAIN_MESSAGE);
        String novolivro = JOptionPane.showInputDialog(null,"Novo livro:","Empréstimo", JOptionPane.PLAIN_MESSAGE);
        Cliente cliente2 = clienteController.buscarPorNomeCliente(novocliente);
        Livro livro2 = livroController.buscarPorTitulo(novolivro);
        LocalDate dataemprestimo = emprestimo.getDataEmprestimo();

        emprestimo.atualizarInformacao(cliente2,livro2,dataemprestimo);
        JOptionPane.showMessageDialog(null, "Empréstimo atualizado com sucesso! " + SUCESSO ,"Empréstimo", JOptionPane.PLAIN_MESSAGE);
    }

    private void removerEmprestimo(ActionEvent actionEvent) {
        String nomecliente = JOptionPane.showInputDialog(null,"Cliente que o empréstimo vai ser removido:","Empréstimo", JOptionPane.PLAIN_MESSAGE);
        String titulolivro = JOptionPane.showInputDialog(null,"Livro que o empréstimo vai ser removido:","Empréstimo", JOptionPane.PLAIN_MESSAGE);

        ClienteController clienteController = new ClienteController();
        Cliente cliente = clienteController.buscarPorNomeCliente(nomecliente);
        LivroController livroController = new LivroController();
        Livro livro = livroController.buscarPorTitulo(titulolivro);
        Emprestimo emprestimo = emprestimoController.buscarPorNomeUsuarioLivro(cliente, livro);

        if (emprestimo == null) {
            JOptionPane.showMessageDialog(null, "Empréstimo não encontrado.","Empréstimo", JOptionPane.PLAIN_MESSAGE);
        }
        emprestimoController.removerEmprestimo(emprestimo);
        JOptionPane.showMessageDialog(null, "Empréstimo removido com sucesso! " + SUCESSO ,"Empréstimo", JOptionPane.PLAIN_MESSAGE);
    }

    public JPanel getPainel() {
        return painel;
    }
}
