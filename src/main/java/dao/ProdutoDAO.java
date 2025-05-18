package dao;

import model.Produto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private static final String ARQUIVO = "produtos.csv";
    private int ultimoId = 0;

    public void salvar(Produto produto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            writer.write(produto.getId() + ";" + produto.getNome() + ";" +
                    produto.getPreco() + ";" + produto.getEstoque());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar produto: " + e.getMessage());
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                Produto p = new Produto(
                        Integer.parseInt(dados[0]),
                        dados[1],
                        Double.parseDouble(dados[2]),
                        Integer.parseInt(dados[3])
                );
                produtos.add(p);
                if (p.getId() > ultimoId) {
                    ultimoId = p.getId();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler produtos: " + e.getMessage());
        }
        return produtos;
    }

    public int proximoId() {
        return ultimoId + 1;
    }
}