package Funcionarios;
import java.util.List;
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
        this.lock.lock();
        if (hospede.getQtdCompanheiros() < 4) {
            //Procurar um quarto dispinivel
            Quarto quarto = hotel.obterQuartoDisponivel(null);
            if (quarto != null){
                quarto.definirHospede(hospede);
                System.out.println("Hospede " + hospede.getNome() + " alugou o quarto " + quarto.getNumero());
            }else{
                System.out.println("Não há quartos disponíveis.");
            }
        } else {
            Quarto quarto1 = hotel.obterQuartoDisponivel(null);
            Quarto quarto2 = hotel.obterQuartoDisponivel(quarto1);

            if (quarto1 != null && quarto2 != null){
                int qtdPessoas = hospede.getQtdCompanheiros() - 4;
                List<Pessoa> pessoasSeparadas =  hospede.separarPessoas(qtdPessoas);
                hospede.removerPessoas(qtdPessoas);
                Hospede novoHospede = new Hospede("novo hospede", 20, "123",
                        this.hotel, pessoasSeparadas.size());
                quarto1.definirHospede(hospede);


                quarto2.definirHospede(novoHospede);
                System.out.println("Hospede " + hospede.getNome() + " alugou o quarto " + quarto1.getNumero());
                System.out.println("Hospede " + novoHospede.getNome() + " alugou o quarto " + quarto2.getNumero());
            }else{
                System.out.println("Não há quartos disponíveis para as duas familias.");
//                colocar reclamação da familia
            }
        }
        this.lock.unlock();
    }
    
    @Override
    public void run() {
       
    }
    
}
