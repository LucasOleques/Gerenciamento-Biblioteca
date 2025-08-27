package Model;

public class Cliente {

    private String nome;
    private int telefone;
    private String email;

    public Cliente(String nome, int telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }
    public String getNome() {
        return nome;
    }
    public int getTelefone() {
        return telefone;
    }
    public String getEmail() {
        return email;
    }

    public void atualizarInformacao(String novoNome, int novoTelefone, String novoEmail) {
        if (novoNome != null && !novoNome.isEmpty()) {
            this.nome = novoNome;
        }
        if (novoTelefone != 0) {
            this.telefone = novoTelefone;
        }
        if ( novoEmail != null && !novoEmail.isEmpty()) {
            this.email = novoEmail;
        }
    }


}
