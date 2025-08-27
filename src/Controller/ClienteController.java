package Controller;

import Model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteController {
    public static List<Cliente> clientes = new ArrayList<>();

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }
    public void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
    }
    public List<Cliente> listarCliente() {
        return clientes;
    }
    public Cliente buscarPorNomeCliente(String nome) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nome)) {
                return cliente;
            }
        }
        return null;
    }
}
