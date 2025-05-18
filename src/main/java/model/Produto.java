package model;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private int estoque;

    public Produto(int id, String nome, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }

    public void setEstoque(int estoque) { this.estoque = estoque; }

    @Override
    public String toString() {
        return id + " - " + nome + " (R$ " + preco + ") - Estoque: " + estoque;
    }
}