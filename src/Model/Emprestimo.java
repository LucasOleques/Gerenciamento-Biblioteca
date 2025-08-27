package Model;

import java.time.LocalDate;

public class Emprestimo {

    private Cliente cliente;
    private Livro livro;
    private LocalDate dataemprestimo;

    public Emprestimo(Cliente cliente, Livro livro, LocalDate dataemprestimo) {
        this.cliente = cliente;
        this.livro = livro;
        this.dataemprestimo = dataemprestimo;
    }

    public Cliente getCliente(){
        return cliente;
    }
    public Livro getLivro() {
        return livro;
    }
    public LocalDate getDataEmprestimo() {
        return dataemprestimo;
    }

    public void atualizarInformacao(Cliente nomeCliente, Livro nomeLivro, LocalDate dataemprestimo) {

        if (nomeCliente != null && !nomeCliente.getNome().isEmpty()) {
            this.cliente = nomeCliente;
        }
        if (nomeLivro != null && !nomeLivro.getTitulo().isEmpty()) {
            this.livro = nomeLivro;
        }
        if (dataemprestimo != null) {
            this.dataemprestimo = dataemprestimo;
        }
    }
}
