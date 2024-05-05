package Funcionarios;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

    public void alugarQuarto(Hospede hospede) {
        this.lock.lock();
        if (hospede.getTentativas() > 0) {
            if (hospede.getQtdCompanheiros() < 4) {
                //Procurar um quarto dispinivel
                Quarto quarto = hotel.obterQuartoDisponivel(null);
                if (quarto != null) {
                    quarto.definirHospede(hospede);
                    hospede.setChave(quarto.getChave());
                    System.out.println("Hospede " + hospede.getNome() + " alugou o quarto " + quarto.getNumero());
                } else {
                    hospede.decrementarTentativas();
                    hotel.addFilaEspera(hospede);
                    System.out.println("Não há quartos disponíveis.");
                }
            } else {
                Quarto quarto1 = hotel.obterQuartoDisponivel(null);
                Quarto quarto2 = hotel.obterQuartoDisponivel(quarto1);

                if (quarto1 != null && quarto2 != null) {
                    int qtdPessoas = hospede.getQtdCompanheiros() - 4;
                    List<Pessoa> pessoasSeparadas = hospede.separarPessoas(qtdPessoas);
                    hospede.removerPessoas(qtdPessoas);
                    Hospede novoHospede = new Hospede("novo hospede", 20, "123",
                            this.hotel, pessoasSeparadas.size());
                    quarto1.definirHospede(hospede);
                    hospede.setChave(quarto1.getChave());

                    quarto2.definirHospede(novoHospede);
                    novoHospede.setChave(quarto2.getChave());
                    System.out.println("Hospede " + hospede.getNome() + " alugou o quarto " + quarto1.getNumero());
                    System.out.println("Hospede " + novoHospede.getNome() + " alugou o quarto " + quarto2.getNumero());
                } else {
                    hospede.decrementarTentativas();
                    hotel.addFilaEspera(hospede);
                    System.out.println("Não há quartos disponíveis para as duas familias.");
//                colocar reclamação da familia
                }
            }
            this.lock.unlock();
        }
    }

    public void chamarFilaEspera() {
        try {
            boolean acessoLock = lock.tryLock(5, TimeUnit.SECONDS);

            if (acessoLock && !hotel.filaEspera.isEmpty()) {
                     Hospede hospede = hotel.filaEspera.poll();
                     alugarQuarto(hospede);
            } else {
                Hospede hospede = hotel.filaEspera.poll();
                hospede.sairPassearFila();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
//        try {
//            Thread.sleep(5000);
//            if (!hotel.filaEspera.isEmpty()) {
//                chamarFilaEspera();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
