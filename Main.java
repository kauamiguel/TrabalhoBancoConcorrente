import Hotel.Hotel;
import java.util.ArrayList;
import java.util.List;

import Hotel.Hospede;

import Funcionarios.Camareira;
import Funcionarios.Recepcionista;

public class Main {
    public static void main(String[] args) {

       
         Hotel hotel = new Hotel(5, 10, 10);
        for (Recepcionista recepcionista : hotel.recepcionistas) {
            Thread recepcionistaThread = new Thread(recepcionista);
            recepcionistaThread.start();
        //    recepcionistaThreads.add(recepcionistaThread);
        }

        Hospede hospede = new Hospede("Alex", 3, "0000-00", hotel);
        Thread hospedeThread = new Thread(hospede);
        hospedeThread.start();



    }
}