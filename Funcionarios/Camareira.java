package Funcionarios;

import Pessoas.Pessoa;
import Hotel.Hotel;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Hotel.Chave;
import Hotel.Quarto;

public class Camareira extends Pessoa implements Runnable{
    private  Hotel hotel;
    private Quarto quartoLimpando;
    private boolean estaDisponivel = true;
    private Lock lock;

    public Camareira(String nome, int idade, String cpf, Hotel hotel) {
        super(nome, idade, cpf);
        this.hotel = hotel;
        this.lock = new ReentrantLock();
    }

    public void killThread(){
        Thread.currentThread().interrupt();
    }

    public boolean estaDisponivel(){
        return this.estaDisponivel;
    }

    public void setDisponivel(){
        this.estaDisponivel = !estaDisponivel;
    }

    public void limparQuarto() {
       lock.lock();
       setDisponivel();
       this.quartoLimpando = hotel.obterQuartoSujo();
        try {
            if (quartoLimpando != null) {
                quartoLimpando.setEstaLimpando(true);
                System.out.println(getNome() + ": Iniciando limpeza do quarto " + quartoLimpando.getNumero());
                Thread.sleep(5000);
                System.out.println("Quarto " + quartoLimpando.getNumero() + " limpo.");
                quartoLimpando.setEstaLimpando(false);
                setDisponivel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }

    @Override
    public void run() {
       long startTime = System.nanoTime();
       while (true) {
           long endTime = System.nanoTime();
           long durationInMillis = endTime - startTime;
           if (durationInMillis > 40000000000L) break;

           System.out.println("Camareira tentando limpar quarto");
           try {
            Thread.sleep(8000);
            limparQuarto();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       }
    }
}

