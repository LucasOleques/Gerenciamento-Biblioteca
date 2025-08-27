package Model;

public class UsuarioLogin {
    private int id;
    private String login;
    private String senha;

    public UsuarioLogin(int id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getSenha() {
        return senha;
    }

    public void atualizarInformacao(int id, String novoLogin, String novaSenha) {
        if (id != 0 && id > 0) {
            this.id = id > 0 ? id : this.id;
        }
        if (novoLogin != null && !novoLogin.isEmpty()) {
            this.login = novoLogin;
        }
        if ( novaSenha != null && !novaSenha.isEmpty()) {
            this.senha = novaSenha;
        }
    }
}
