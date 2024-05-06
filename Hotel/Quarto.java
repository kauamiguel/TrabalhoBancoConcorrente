package Hotel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Quarto {
    private int numero;
    private boolean disponivel = true;
    private Hospede hospede;
    private boolean estaComChave = false;
    private  Chave chave;
    private boolean estaLimpando = false;

    public Quarto(int numero){
        this.numero = numero;
        chave = new Chave(this);
    }

    boolean estaComChave(){
        return this.estaComChave;
    }

    void setEstaComChave(boolean estado){
        this.estaComChave = estado;
    }

    public boolean getEstaLimpando() {
        return estaLimpando;
    }

    public void setEstaLimpando(boolean estaLimpando) {
        this.estaLimpando = estaLimpando;
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

    public Chave getChave() {
        return chave;
    }

    public void definirHospede(Hospede hospede){
        //Atribui um hospede e define que o quarto nao esta disponivel
        this.hospede = hospede;
        this.setQuarto();
       // System.out.println("O hospede " + hospede.getNome() + "alugou o quarto com numero " + this.numero + ".");
        estaComChave = true;
    }

}
