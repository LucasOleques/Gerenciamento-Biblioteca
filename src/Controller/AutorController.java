package Controller;

import Model.Autor;

import java.util.ArrayList;
import java.util.List;

public class AutorController {
    public static List<Autor> autores = new ArrayList<>();

    public void cadastrarAutor(Autor autor){
        autores.add(autor);
    }
    public void removerAutor(Autor autor){
        autores.remove(autor);
    }
    public List<Autor> listarAutor() {
        return autores;
    }
    public Autor buscarPorNomeAutor(String nome){
        for( Autor autor : autores){
            if(autor.getNomeautor().equalsIgnoreCase(nome)){
                return autor;
            }
        }
        return null;
    }
}
