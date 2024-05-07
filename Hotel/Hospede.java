package Hotel;

import Funcionarios.Camareira;
import Funcionarios.Recepcionista;
import Pessoas.Pessoa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.CheckedOutputStream;

public class Hospede extends Pessoa implements Runnable{
    private List<Pessoa> companheirosDeQuartos;
    private boolean estaNoQuarto;
    private Hotel hotel;
    private Quarto quarto;
    private int qtdCompanheiros;
    private int tentativas = 2;
    private Chave chave;
    private Thread thread;
    private Lock lock;


    public Hospede(String nome, Integer idade, String cpf, Hotel hotel, int qtdCompanheiros){
        super(nome, idade, cpf);
        this.estaNoQuarto = false;
        this.quarto = null;
        this.hotel = hotel;
        this.qtdCompanheiros = qtdCompanheiros;
        this.thread = new Thread(this);
        this.lock = new ReentrantLock();
    }

    public Thread getThread() {
        return thread;
    }

    public boolean isAlive() {
        return thread.isInterrupted();
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
        Thread.currentThread().interrupt();
    }

    public  void  sairPassearFila() {
        System.out.println(this.getNome() + " saiu para passear na cidade!");
        try {
            Thread.sleep(13000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        hotel.addFilaEspera(this);
    }

    public void sairPassearQuarto(Recepcionista recepcionista) {
        deixarChaveRecepcao(recepcionista);
        System.out.println(this.getNome() + " saiu para passear e deixou a chave na recepcao");
        Camareira camareira = hotel.camareiraDisponivel();
        camareira.limparQuarto();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void deixarChaveRecepcao(Recepcionista recepcionista){
        recepcionista.addChave(chave);
        // perde a referenciua da chave
        this.chave = null;
        // a chave foi para a recepcao
        quarto.setEstaComChave(false);;
    }

    public void voltarDoPasseio(Chave chave) {
        this.chave = chave;
        System.out.println(this.getNome() + " voltando do passeio");
    }

    public void setChave(Chave chave) {
        this.chave = chave;
    }

    public void terminarEstadia(Recepcionista recepcionista) {
        deixarChaveRecepcao(recepcionista);
        hotel.removeHospede(this);
        System.out.println(this.getNome() + " terminou o sua estadia no hotel");
        recepcionista.chamarFilaEspera(); // Chamar a lista de espera após terminar a estadia
    }
    

    @Override
public void run() {
    Recepcionista recepcionista = hotel.recepcionistaDisponivel();
    if (recepcionista != null && tentativas > 0){
        while (tentativas > 0) {
            recepcionista.alugarQuarto(this);
            if (this.chave != null) {
                try {
                    sairPassearQuarto(recepcionista);
                    Thread.sleep(4000);
                    if (!quarto.getEstaLimpando()) {
                        voltarDoPasseio(recepcionista.devolverChave(quarto.getNumero()));
                    }
                    Random random = new Random();
                    Thread.sleep(1000 * random.nextInt(6));
                    terminarEstadia(recepcionista);
                    break; // Se a estadia for terminada com sucesso, sair do loop
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                tentativas--; // Decrementar as tentativas se a primeira tentativa falhar
            }
        }
        if (tentativas == 0) {
            fazerReclamacao(); // Fazer uma reclamação se todas as tentativas falharem
        }
    }    
}

}
