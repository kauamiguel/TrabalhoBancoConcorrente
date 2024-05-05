package Funcionarios;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Hotel.Hospede;
import Hotel.Hotel;
import Hotel.Quarto;
import Pessoas.Pessoa;

public class Recepcionista extends Pessoa implements Runnable{
    public Hotel hotel;
    private Lock lock;
    boolean estaDisponivel = true;

    public Recepcionista(String nome, int idade, String cpf, Hotel hotel) {
        super(nome, idade, cpf);
        this.hotel = hotel;
        this.lock = new ReentrantLock();
    }

    public boolean estaDisponivel(){
        return this.estaDisponivel;
    }

    public void setDisponivel(){
        this.estaDisponivel = !estaDisponivel;
    }

    public void alugarQuarto(Hospede hospede){
        //Procurar um quarto dispinivel
        Quarto quarto = hotel.obterQuartoDisponivel();
        if (quarto != null){
            quarto.definirHospede(hospede);
        }else{
            System.out.println("Não há quartos disponíveis.");
        }
    }

    
    @Override
    public void run() {
       
    }
    
}
