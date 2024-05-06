import Funcionarios.Camareira;
import Hotel.Hotel;

import Hotel.Hospede;
import java.util.List;
import java.util.Arrays;
import Funcionarios.Recepcionista;

public class Main {

    public static void main(String[] args) {
        List<String> listaNomes = Arrays.asList(
                "João", "Maria", "Pedro", "Ana", "Carlos", "Mariana", "José", "Fernanda", "Paulo", "Patrícia",
                "Lucas", "Camila", "Gabriel", "Isabela", "Rafael", "Amanda", "Gustavo", "Laura", "Daniel", "Juliana",
                "Thiago", "Bruna", "Rodrigo", "Carolina", "Fábio", "Raquel", "Vinícius", "Renata", "Diego", "Natália",
                "Marcelo", "Letícia", "Felipe", "Tatiane", "Ricardo", "Débora", "Arthur", "Priscila", "Luiz", "Simone",
                "Eduardo", "Bianca", "Caio", "Vanessa", "Alexandre", "Larissa", "Fernando", "Aline", "Matheus", "Monique"
        );
        Hotel hotel = new Hotel(5, 10, 2);
        for (Recepcionista recepcionista : hotel.recepcionistas) {
            Thread recepcionistaThread = new Thread(recepcionista);
            recepcionistaThread.start();
        }

        for (int i = 0; i < 2; i++) {
            // TODO: Gerar numero de companheiros aleatoramente
            Hospede hospede = new Hospede(listaNomes.get(i), 3, "0000-00", hotel, 3);
            Thread hospedeThread1 = new Thread(hospede);
            hotel.addHospede(hospede);
            hospedeThread1.start();
        }

        for (Camareira camareira : hotel.camareiras) {
            Thread camareiraThread = new Thread(camareira);
            try {
                camareiraThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (Camareira camareira : hotel.camareiras) {
            Thread camareiraThread = new Thread(camareira);
            camareiraThread.start();
        }
    }
}