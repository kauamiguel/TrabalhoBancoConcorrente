import Hotel.Hotel;

import Hotel.Hospede;

import Funcionarios.Recepcionista;

public class Main {
    public static void main(String[] args) {

       
        Hotel hotel = new Hotel(5, 10, 10);
        for (Recepcionista recepcionista : hotel.recepcionistas) {
            Thread recepcionistaThread = new Thread(recepcionista);
            recepcionistaThread.start();
        //    recepcionistaThreads.add(recepcionistaThread);
        }

        Hospede hospede1 = new Hospede("Alex", 3, "0000-00", hotel, 6);
        Thread hospedeThread1 = new Thread(hospede1);
        hospedeThread1.start();

    }
}