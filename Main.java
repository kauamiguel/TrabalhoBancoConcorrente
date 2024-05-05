import Hotel.Hotel;

import Hotel.Hospede;

import Funcionarios.Recepcionista;

public class Main {
    public static void main(String[] args) {

        Hotel hotel = new Hotel(5, 10, 2);
        for (Recepcionista recepcionista : hotel.recepcionistas) {
            Thread recepcionistaThread = new Thread(recepcionista);
            recepcionistaThread.start();
        //    recepcionistaThreads.add(recepcionistaThread);
        }
        for (int i = 0; i < 2; i++) {
            // TODO: Gerar numero de companheiros aleatoramente
            Hospede hospede = new Hospede("Alex", 3, "0000-00", hotel, 3);
            Thread hospedeThread1 = new Thread(hospede);
            hospedeThread1.start();
        }






    }
}