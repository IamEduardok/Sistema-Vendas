package dao;

import model.Venda;
import model.Cliente;
import model.Produto;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    private static final String ARQUIVO = "vendas.csv";
    private int ultimoId = 0;

    public void salvar(Venda venda) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(venda.getId()).append(";")
                    .append(venda.getCliente().getId()).append(";")
                    .append(venda.getData()).append(";")
                    .append(venda.getTotal()).append(";");

            // Adiciona IDs dos produtos separados por vírgula
            for (Produto p : venda.getProdutos()) {
                sb.append(p.getId()).append(",");
            }

            writer.write(sb.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar venda: " + e.getMessage());
        }
    }

    public List<Venda> listarTodos() {
        List<Venda> vendas = new ArrayList<>();
        ClienteDAO clienteDao = new ClienteDAO();
        ProdutoDAO produtoDao = new ProdutoDAO();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                int id = Integer.parseInt(dados[0]);
                Cliente cliente = clienteDao.listarTodos().stream()
                        .filter(c -> c.getId() == Integer.parseInt(dados[1]))
                        .findFirst().orElse(null);

                LocalDate data = LocalDate.parse(dados[2]);
                double total = Double.parseDouble(dados[3]);

                List<Produto> produtos = new ArrayList<>();
                for (String produtoId : dados[4].split(",")) {
                    if (!produtoId.isEmpty()) {
                        produtoDao.listarTodos().stream()
                                .filter(p -> p.getId() == Integer.parseInt(produtoId))
                                .findFirst().ifPresent(produtos::add);
                    }
                }

                Venda v = new Venda(id, cliente, produtos, data);
                vendas.add(v);
                if (v.getId() > ultimoId) {
                    ultimoId = v.getId();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler vendas: " + e.getMessage());
        }
        return vendas;
    }

    public int proximoId() {
        return ultimoId + 1;
    }
}