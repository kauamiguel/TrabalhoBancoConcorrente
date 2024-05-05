package Hotel;

import Funcionarios.Recepcionista;
import Pessoas.Pessoa;

public class Hospede extends Pessoa implements Runnable{

    private boolean estaNoQuarto;
    Hotel hotel;
    Quarto quarto;

    public Hospede(String nome, Integer idade, String cpf, Hotel hotel){
        super(nome, idade, cpf);
        this.estaNoQuarto = false;
        this.quarto = null;
        this.hotel = hotel;
    }

    public void setQuarto(Quarto quarto){
        this.quarto = quarto;
    }

    @Override
    public void run() {
        Recepcionista recepcionista = hotel.recepcionistaDisponivel();

        if (recepcionista != null){
            recepcionista.alugarQuarto(this);
        }
    }
}
