package Hotel;

import Funcionarios.Recepcionista;
import Pessoas.Pessoa;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class Hospede extends Pessoa implements Runnable{
    private List<Pessoa> companheirosDeQuartos;
    private boolean estaNoQuarto;
    private Hotel hotel;
    private Quarto quarto;
    private int qtdCompanheiros;
    private int tentativas = 2;
    private Chave chave;


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

    public void entregarChave() {

    }

    public int getTentativas() {
        return tentativas;
    }

    public void decrementarTentativas() {
        tentativas--;
    }

    public void fazerReclamacao() {
        System.out.println(this.getNome() + ": O hotel é muito ruim e não tem quartos disponiveis.");
        // Mata a thread pois o hospede vai embora
        Thread.currentThread().interrupt();
    }

    public  void  sairPassearFila() {
        System.out.println(this.getNome() + " saiu para passear na cidade!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        hotel.addFilaEspera(this);
    }

    public void sairPassearQuarto() {
        deixarChaveRecepcao();
        System.out.println(this.getNome() + " saiu para passear e deixou a chave na recepcao");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deixarChaveRecepcao(){
        hotel.addChave(chave);
        // perde a referenciua da chave
        this.chave = null;
        System.out.println(chave);
    }

    public void setChave(Chave chave) {
        this.chave = chave;
    }

    @Override
    public void run() {
        Recepcionista recepcionista = hotel.recepcionistaDisponivel();

        if (recepcionista != null && tentativas > 0){
            recepcionista.alugarQuarto(this);
            // se eu tenho a chave eu posso sair para passear
            if (this.chave != null) {
                try {
                    Thread.sleep(4000);
                    sairPassearQuarto();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
