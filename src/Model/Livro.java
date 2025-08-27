package Model;

public class Livro {
    private String titulo;
    private String paginaslivro;
    private Autor autorlivro;

    public Livro(String titulo, String paginaslivro, Autor autorlivro) {
        this.titulo = titulo;
        this.paginaslivro = paginaslivro;
        this.autorlivro = autorlivro;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getPaginaslivro() {
        return paginaslivro;
    }
    public Autor getAutorlivro() {
        return autorlivro;
    }


    public void atualizarInformacao(String novoTitulo,String novasPaginas, Autor novoAutor) {
        if (novoTitulo != null && !novoTitulo.isEmpty()) {
            this.titulo = novoTitulo;
        }
        if (novasPaginas != null && !novasPaginas.isEmpty()) {
            this.paginaslivro = novasPaginas;
        }
        if (novoAutor != null && !novoAutor.getNomeautor().isEmpty()) {
            this.autorlivro = novoAutor;
        }
    }
}
