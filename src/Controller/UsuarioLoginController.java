package Controller;

import Model.UsuarioLogin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioLoginController {

    private List<UsuarioLogin> usuarioLogins;

    public UsuarioLoginController(){
        this.usuarioLogins = new ArrayList<>();
    }

    public void cadastrarUsuario(int id, String login, String senha) {
        if (id < 0) {
            throw new IllegalArgumentException("ID não pode ser negativo.");
        }
        if (login == null || login.isBlank() || senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Login e senha não podem ser nulos ou vazios.");
        }
        if( senha.length() < 4) {
            throw new IllegalArgumentException("Senha deve ter 4 caracteres ou mais.");
        }

        id = usuarioLogins.size() + 1;
        UsuarioLogin novoUsuarioLogin = new UsuarioLogin(id, login, senha);
        usuarioLogins.add(novoUsuarioLogin);
    }
    public void removerUsuario(UsuarioLogin usuarioLogin){
        if( !usuarioLogins.contains(usuarioLogin)) {
            throw new IllegalArgumentException("Usuário não encontrado para remoção.");
        }
        usuarioLogins.remove(usuarioLogin);
    }

    public UsuarioLogin buscarPorLogin(String login){
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login não pode ser nulo ou vazio.");
        }
        for( UsuarioLogin usuarioLogin : usuarioLogins) {
            if (usuarioLogin.getLogin().equalsIgnoreCase(login)){
                return usuarioLogin;
            }
        }
        return null;
    }

    public boolean login(String login, String senha) {
        for (UsuarioLogin usuarioLogin : usuarioLogins) {
            if (usuarioLogin.getLogin().equals(login) && usuarioLogin.getSenha().equals(senha)) {
                return true;
            } else if (usuarioLogin.getLogin().equals(login) && !usuarioLogin.getSenha().equals(senha)) {
                throw new IllegalArgumentException("Senha incorreta para o usuário: " + login);
            } else if (!usuarioLogin.getLogin().equals(login)) {
                throw new IllegalArgumentException("Tentativa de login com usuário não cadastrado: " + login);
            } else {
                throw new IllegalArgumentException("Usuário não encontrado: " + login);
            }
        }
        return false;
    }

    public List<UsuarioLogin> listarUsuarios() {
        return Collections.unmodifiableList(usuarioLogins);
    }
}