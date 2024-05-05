package Hotel;

import Funcionarios.Recepcionista;
import Pessoas.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class Hospede extends Pessoa implements Runnable{
    private List<Pessoa> companheirosDeQuartos;
    private boolean estaNoQuarto;
    private Hotel hotel;
    private Quarto quarto;
    private int qtdCompanheiros;

    public Hospede(String nome, Integer idade, String cpf, Hotel hotel, int qtdCompanheiros){
        super(nome, idade, cpf);
        this.estaNoQuarto = false;
        this.quarto = null;
        this.hotel = hotel;
        this.qtdCompanheiros = qtdCompanheiros;
    }

    public void criarCompanheiros() {
        for (int i = 0; i < qtdCompanheiros; i++) {
            companheirosDeQuartos.add(new Pessoa("Pessoa " + i, 20, "000"));
        }
    }

    public int getQtdCompanheiros() {
        return this.qtdCompanheiros;
    }

    public void removerPessoas(int qtdPessoas) {
        for (int i = 4; i < qtdPessoas; i++) {
            companheirosDeQuartos.remove(i);
        }
    }

    public List<Pessoa> separarPessoas(int qtdPessoas) {
        List<Pessoa> novaLista = new ArrayList<>();
        for (int i = 4; i < qtdPessoas; i++) {
            novaLista.add(companheirosDeQuartos.get(i));
        }
        return novaLista;
    }

    public void setQuarto(Quarto quarto){
        this.quarto = quarto;
    }

    @Override
    public void run() {
        Recepcionista recepcionista = hotel.recepcionistaDisponivel();

        if (recepcionista != null){
            recepcionista.alugarQuarto(this);
        }
    }
}
