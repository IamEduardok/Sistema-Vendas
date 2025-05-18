package model;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    private int id;
    private Cliente cliente;
    private List<Produto> produtos;
    private LocalDate data;
    private double total;

    public Venda(int id, Cliente cliente, List<Produto> produtos, LocalDate data) {
        this.id = id;
        this.cliente = cliente;
        this.produtos = produtos;
        this.data = data;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        return produtos.stream().mapToDouble(Produto::getPreco).sum();
    }

    // Getters
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<Produto> getProdutos() { return produtos; }
    public LocalDate getData() { return data; }
    public double getTotal() { return total; }

    @Override
    public String toString() {
        return "Venda #" + id + " - Cliente: " + cliente.getNome() +
                " - Total: R$ " + total + " - Data: " + data;
    }
}