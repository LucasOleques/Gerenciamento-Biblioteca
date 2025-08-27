import Controller.*;
import Logging.*;
import View.MenuInicial.*;
import View.MenuInicialBiblioteca.*;
import View.MenuModels.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Logging logging = new Logging();

        AutorController autorController = new AutorController();
        LivroController livroController = new LivroController();
        EmprestimoController emprestimoController = new EmprestimoController();
        ClienteController clienteController = new ClienteController();
        UsuarioLoginController usuarioLoginController = new UsuarioLoginController();

        AutorView autorView = new AutorView(autorController);
        LivroView livroView = new LivroView(livroController);
        EmprestimoView emprestimoView = new EmprestimoView(emprestimoController);
        ClienteView clienteView = new ClienteView(clienteController);
        UsuarioView usuarioView = new UsuarioView(usuarioLoginController);

        // Altere para false para fluxo normal || Altere para true para fluxo de teste
        boolean modoTeste = false;
        logging.logModoTeste(modoTeste);

        SwingUtilities.invokeLater(() -> {
            if (modoTeste) {
                // Cria um LoginUsuarioView "fake" só para passar no construtor
                LoginUsuarioView loginUsuarioView = new LoginUsuarioView(() -> {});
                new BibliotecaView(
                        autorView, livroView, emprestimoView, clienteView, usuarioView, loginUsuarioView
                );
            } else {
                new PainelPrincipal(autorView, livroView, emprestimoView, clienteView, usuarioView);
            }
        });
    }
}