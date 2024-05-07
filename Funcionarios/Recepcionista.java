package Funcionarios;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Hotel.Hospede;
import Hotel.Hotel;
import Hotel.Quarto;
import Pessoas.Pessoa;
import Hotel.Chave;

public class Recepcionista extends Pessoa implements Runnable{
    private Hotel hotel;
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
                    System.out.println("Quantidade de pessoas no quarto : " + hospede.getQtdCompanheiros());
                    quarto.definirHospede(hospede);
                    hospede.setChave(quarto.getChave());
                    hospede.setQuarto(quarto);
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
                    System.out.println("Quantidade de pessoas em dois quartos : " + hospede.getQtdCompanheiros());
                    int qtdPessoas = hospede.getQtdCompanheiros() - 4;
                    List<Pessoa> pessoasSeparadas = hospede.separarPessoas(qtdPessoas);
                    hospede.removerPessoas(qtdPessoas);
                    Hospede novoHospede = new Hospede("novo hospede", 20, "123",
                            this.hotel, pessoasSeparadas.size());
                    quarto1.definirHospede(hospede);
                    hospede.setChave(quarto1.getChave());
                    hospede.setQuarto(quarto1);

                    quarto2.definirHospede(novoHospede);
                    novoHospede.setChave(quarto2.getChave());
                    novoHospede.setQuarto(quarto2);
                    System.out.println("Hospede " + hospede.getNome() + " alugou o quarto " + quarto1.getNumero());
                    System.out.println("Hospede " + novoHospede.getNome() + " alugou o quarto " + quarto2.getNumero());
                } else {
                    hospede.decrementarTentativas();
                    hotel.addFilaEspera(hospede);
                    System.out.println("Não há quartos disponíveis para as duas familias.");
                }
            }
            this.lock.unlock();
        }
    }

    public void chamarFilaEspera() {
        try {
            boolean acessoLock = lock.tryLock(5, TimeUnit.SECONDS);
    
            if (acessoLock) {
                Hospede hospede = hotel.getFilaEspera().poll();
                if (hospede != null) {
                    alugarQuarto(hospede);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }    

    public void addChave(Chave chave) {
        hotel.getChaves().add(chave);
    }

    public Chave devolverChave(int numQuarto) {
        List<Chave> chaves = hotel.getChaves();
        for (int i = 0; i < chaves.size(); i++) {
            Chave chave = chaves.get(i);
            if (chave.getNumeroChave().equals(numQuarto)) {
                chaves.remove(i); 
                return chave;
            }
        }
        return null;
    }
    
    
    @Override
    public void run() {
        try {
            long startTime = System.nanoTime();
            while(true) {
                long endTime = System.nanoTime();
                long durationInMillis = endTime - startTime;
                if (durationInMillis > 40000000000L) break;
                Thread.sleep(5000); // Espera 5 segundos antes de verificar a fila de espera
                if (!hotel.getFilaEspera().isEmpty()) {
                    chamarFilaEspera(); // Chama a fila de espera se não estiver vazia
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
