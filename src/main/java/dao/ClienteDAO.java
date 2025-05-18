package dao;

import model.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private static final String ARQUIVO = "clientes.csv";
    private int ultimoId = 0;

    public void salvar(Cliente cliente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            writer.write(cliente.getId() + ";" + cliente.getNome() + ";" + cliente.getCpf());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                Cliente c = new Cliente(
                        Integer.parseInt(dados[0]),
                        dados[1],
                        dados[2]
                );
                clientes.add(c);
                if (c.getId() > ultimoId) {
                    ultimoId = c.getId();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler clientes: " + e.getMessage());
        }
        return clientes;
    }

    public int proximoId() {
        return ultimoId + 1;
    }
}