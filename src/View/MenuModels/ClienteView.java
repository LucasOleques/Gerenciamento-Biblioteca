package View.MenuModels;

import Controller.*;
import Model.*;
import static Utils.Icones.Icones.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ClienteView {

    private final ClienteController clienteController;
    private final JPanel painel;
    private final JTextField campoNome = new JTextField();
    private final JTextField campoTelefone = new JTextField();
    private final JTextField campoEmail = new JTextField();
    private final JTextArea areaLista = new JTextArea();

    private Timer limparTextoTimer;

    public ClienteView(ClienteController clienteController) {
        this.clienteController = clienteController;

        painel = new JPanel(new BorderLayout(10, 10));
        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Gerenciamento de Cliente");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        painelTopo.add(titulo, BorderLayout.NORTH);
        painel.add(painelTopo, BorderLayout.NORTH);

        // Formulário
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));
        form.add(new JLabel("Nome Cliente:"));
        form.add(campoNome);
        form.add(new JLabel("Telefone:"));
        form.add(campoTelefone);
        form.add(new JLabel("Email:"));
        form.add(campoEmail);

        JButton btnSalvar = new JButton("Salvar Cliente");
        btnSalvar.addActionListener(this::salvarCliente);
        JButton btnListar = new JButton("Listar Clientes");
        btnListar.addActionListener(this::listarClientes);
        JButton btnEditar = new JButton("Editar Cliente");
        btnEditar.addActionListener(this::editarCliente);
        JButton btnRemover = new JButton("Remover Cliente");
        btnRemover.addActionListener(this::removerCliente);
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

    private void salvarCliente(ActionEvent actionEvent) {
        String nome = campoNome.getText().trim();
        String telefoneTexto = campoTelefone.getText().trim();
        String email = campoEmail.getText().trim();

        if (nome.isEmpty() || telefoneTexto.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(painel, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int telefone;
        try {
            telefone = Integer.parseInt(telefoneTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(painel, "Telefone inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(nome, telefone, email);
        clienteController.cadastrarCliente(cliente);

        JOptionPane.showMessageDialog(painel, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        campoNome.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");
    }

    private void listarClientes(ActionEvent actionEvent) {
        List<Cliente> clientes = clienteController.listarCliente();

        if (clientes.isEmpty()) {
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
        areaLista.setText("Lista de Clientes:\n");
        for (Cliente cliente : clientes) {
            sb.append("👤 ").append(cliente.getNome())
                    .append(" | ").append(TELEFONE).append(" ").append(cliente.getTelefone())
                    .append(" | ").append(EMAIL).append(" ").append(cliente.getEmail())
                    .append("\n");
        }
        areaLista.setText(sb.toString());
    }

    private void editarCliente(ActionEvent actionEvent) {
        String nome = JOptionPane.showInputDialog(null, "Nome atual do cliente:", "Cliente", JOptionPane.PLAIN_MESSAGE);
        if (nome == null || nome.trim().isEmpty()) {
            return;
        }
        Cliente cliente = clienteController.buscarPorNomeCliente(nome);

        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Cliente", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        String novoNome = JOptionPane.showInputDialog(null, "Novo nome:", "Cliente", JOptionPane.PLAIN_MESSAGE);
        int novoTelefone = Integer.parseInt(JOptionPane.showInputDialog(null, "Novo telefone:", "Cliente", JOptionPane.PLAIN_MESSAGE));
        String novoEmail = JOptionPane.showInputDialog(null, "Novo email:", "Cliente", JOptionPane.PLAIN_MESSAGE);

        cliente.atualizarInformacao(novoNome, novoTelefone, novoEmail);
        JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!", "Cliente", JOptionPane.PLAIN_MESSAGE);
    }

    private void removerCliente(ActionEvent actionEvent) {
        String nome = JOptionPane.showInputDialog(null, "Cliente a ser removido:", "Cliente", JOptionPane.PLAIN_MESSAGE);
        Cliente cliente = clienteController.buscarPorNomeCliente(nome);

        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Cliente", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        clienteController.removerCliente(cliente);
        JOptionPane.showMessageDialog(null, "Cliente removido com sucesso! " + SUCESSO, "Cliente", JOptionPane.PLAIN_MESSAGE);
    }

    public JPanel getPainel() {
        return painel;
    }
}
