package service;

import dao.ClienteDAO;
import dao.ProdutoDAO;
import dao.VendaDAO;
import model.Venda;
import model.Cliente;
import model.Produto;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class VendaService {
    private final ProdutoDAO produtoDao = new ProdutoDAO();
    private final ClienteDAO clienteDao = new ClienteDAO();
    private final VendaDAO vendaDao = new VendaDAO();
    private final Scanner scanner = new Scanner(System.in);

    public void registrarVenda() {
        System.out.println("\n=== REGISTRAR VENDA ===");

        // Listar clientes
        System.out.println("\nClientes disponíveis:");
        clienteDao.listarTodos().forEach(System.out::println);

        System.out.print("\nDigite o ID do cliente: ");
        int clienteId = scanner.nextInt();
        Cliente cliente = clienteDao.listarTodos().stream()
                .filter(c -> c.getId() == clienteId)
                .findFirst().orElse(null);

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        // Listar produtos
        System.out.println("\nProdutos disponíveis:");
        produtoDao.listarTodos().forEach(System.out::println);

        List<Produto> produtos = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.print("\nDigite o ID do produto (0 para finalizar): ");
            int produtoId = scanner.nextInt();

            if (produtoId == 0) {
                continuar = false;
            } else {
                Produto produto = produtoDao.listarTodos().stream()
                        .filter(p -> p.getId() == produtoId)
                        .findFirst().orElse(null);

                if (produto != null) {
                    if (produto.getEstoque() > 0) {
                        produtos.add(produto);
                        System.out.println("Produto adicionado: " + produto.getNome());
                    } else {
                        System.out.println("Produto sem estoque disponível!");
                    }
                } else {
                    System.out.println("Produto não encontrado!");
                }
            }
        }

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto selecionado. Venda cancelada.");
            return;
        }


        Venda venda = new Venda(
                vendaDao.proximoId(),
                cliente,
                produtos,
                LocalDate.now()
        );

        vendaDao.salvar(venda);


        produtos.forEach(p -> {
            p.setEstoque(p.getEstoque() - 1);

        });

        System.out.println("\nVenda registrada com sucesso!");
        System.out.println(venda);
    }

    public void listarVendas() {
        vendaDao.listarTodos().forEach(System.out::println);
    }
}