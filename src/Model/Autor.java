package Model;

public class Autor {

    private String nomeautor;
    private int idadeautor;

    public Autor(String nomeautor, int idadeautor) {
        this.nomeautor = nomeautor;
        this.idadeautor = idadeautor;
    }
    public String getNomeautor() {
        return nomeautor;
    }
    public int getIdadeautor() {
        return idadeautor;
    }
    public void atualizarInformacao(String novoNome,int novaIdade) {
        if (novoNome != null && !novoNome.isEmpty()) {
            this.nomeautor = novoNome;
        }
        if (novaIdade > 0 ) {
            this.idadeautor = novaIdade;
        }
    }
}
