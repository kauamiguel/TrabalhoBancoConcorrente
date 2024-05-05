package Funcionarios;

import Pessoas.Pessoa;

public class Camareira extends Pessoa implements Runnable{

    public Camareira(String nome, int idade, String cpf) {
        super(nome, idade, cpf);
    }

    @Override
    public void run() {

    }
}

