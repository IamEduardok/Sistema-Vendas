import model.Produto;
import model.Cliente;
import dao.ProdutoDAO;
import dao.ClienteDAO;
import service.VendaService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProdutoDAO produtoDao = new ProdutoDAO();
        ClienteDAO clienteDao = new ClienteDAO();
        VendaService vendaService = new VendaService();

        int opcao;

        do {
            System.out.println("\n SISTEMA DE GESTÃO DE VENDAS");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Cadastrar Cliente");
            System.out.println("3. Registrar Venda");
            System.out.println("4. Listar Produtos");
            System.out.println("5. Listar Clientes");
            System.out.println("6. Listar Vendas");
            System.out.println("0. Sair");
            System.out.print("Opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarProduto(scanner, produtoDao);
                    break;
                case 2:
                    cadastrarCliente(scanner, clienteDao);
                    break;
                case 3:
                    vendaService.registrarVenda();
                    break;
                case 4:
                    System.out.println("\n PRODUTOS CADASTRADOS ");
                    produtoDao.listarTodos().forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("\n CLIENTES CADASTRADOS ");
                    clienteDao.listarTodos().forEach(System.out::println);
                    break;
                case 6:
                    System.out.println("\n VENDAS REGISTRADAS ");
                    vendaService.listarVendas();
                    break;
            }
        } while (opcao != 0);

        scanner.close();
        System.out.println("Sistema encerrado.");
    }

    private static void cadastrarProduto(Scanner scanner, ProdutoDAO dao) {
        System.out.println("\n CADASTRAR PRODUTO ");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Preço: ");
        double preco = scanner.nextDouble();

        System.out.print("Estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        Produto produto = new Produto(
                dao.proximoId(),
                nome,
                preco,
                estoque
        );

        dao.salvar(produto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    private static void cadastrarCliente(Scanner scanner, ClienteDAO dao) {
        System.out.println("\n CADASTRAR CLIENTE ");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        Cliente cliente = new Cliente(
                dao.proximoId(),
                nome,
                cpf
        );

        dao.salvar(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }
}