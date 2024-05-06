package Funcionarios;

import Pessoas.Pessoa;
import Hotel.Hotel;
import Hotel.Chave;
import Hotel.Quarto;

public class Camareira extends Pessoa implements Runnable{
    private  Hotel hotel;
    private Quarto quartoLimpando;
    private boolean estaDisponivel = true;

    public Camareira(String nome, int idade, String cpf, Hotel hotel) {
        super(nome, idade, cpf);
        this.hotel = hotel;
    }

    public boolean estaDisponivel(){
        return this.estaDisponivel;
    }

    public void setDisponivel(){
        this.estaDisponivel = !estaDisponivel;
    }

    public void limparQuarto() {
        setDisponivel();
       this.quartoLimpando = hotel.obterQuartoSujo();
        try {
            quartoLimpando.setEstaLimpando(true);
            System.out.println(getNome() + ": Iniciando limpeza do quarto " + quartoLimpando.getNumero());
            Thread.sleep(2000);
            System.out.println("Quarto " + quartoLimpando.getNumero() + " limpo.");
            quartoLimpando.setEstaLimpando(false);
            setDisponivel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}

