package Controller;

import Model.Cliente;
import Model.Emprestimo;
import Model.Livro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoController {
    public List<Emprestimo> emprestimos = new ArrayList<>();

    public void cadastrarEmprestimo(Cliente cliente, Livro livro, LocalDate dataemprestimo) {
        Emprestimo emprestimo = new Emprestimo(cliente, livro, dataemprestimo);
        emprestimos.add(emprestimo);
    }
    public void removerEmprestimo(Emprestimo emprestimo) {
        emprestimos.remove(emprestimo);
    }
    public List<Emprestimo> listarEmprestimo() {
        return emprestimos;
    }
    public Emprestimo buscarPorNomeUsuarioLivro(Cliente nomeCliente, Livro nomeLivro) {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getCliente().getNome().equalsIgnoreCase(nomeCliente.getNome())
                    && emprestimo.getLivro().getTitulo().equalsIgnoreCase(nomeLivro.getTitulo())) {
                    return emprestimo;
            }
        }
        return null;
    }
}
