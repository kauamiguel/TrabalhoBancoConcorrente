package Hotel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Quarto {
    int numero;
    boolean disponivel = true;
    Hospede hospede;
    Lock lock;

    public Quarto(int numero){
        this.lock = new ReentrantLock();
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

     public void setHospedes(Hospede hospede) {
      this.hospede = hospede;
    }

    public void setQuarto(){
        this.disponivel = !disponivel;
    }

    public boolean getDisponivel(){
        return this.disponivel;
    }

    public void definirHospede(Hospede hospede){
        //Atribui um hospede e define que o quarto nao esta disponivel
        this.hospede = hospede;
        this.setQuarto();
        System.out.println("O hospede " + hospede.getNome() + "alugou o quarto com numero " + this.numero + ".");
    }

}
