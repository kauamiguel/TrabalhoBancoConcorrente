package Hotel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Funcionarios.Camareira;
import Funcionarios.Recepcionista;

public class Hotel {
    public List<Recepcionista> recepcionistas;
    private List<Camareira> camareiras;
    private List<Quarto> quartos;
    private Lock lock;
    public String hotelNome = "Hotel 5 estrelas";

    public Hotel(int numRecepcionistas, int numCamareiras, int numQuartos){
        recepcionistas = new ArrayList<>();
        camareiras = new ArrayList<>();
        quartos = new ArrayList<>();
        lock = new ReentrantLock(); 
        addRecepcionista(numRecepcionistas);
        addCamareiras(numCamareiras);
        addQuartos(10);
    
    }

    void addQuartos(int numQuartos) {
        for (int i = 0; i < numQuartos; i++) {
            quartos.add(new Quarto(i)); 
        }
    }

    void addRecepcionista(int numRecepcionistas){
        String[] names = {"Ana", "Carlos", "Maria", "Joana", "Tais"};
        Integer[] idades = {40, 20, 25, 27, 21};
        String[] cpf = {"111-111-111-11", "222-222-222-22", "333-333-333-33", "444-444-444-44", "555-555-555-55"};
    
        for (int i = 0; i < numRecepcionistas && i < names.length; i++){
            this.recepcionistas.add(new Recepcionista(names[i], idades[i], cpf[i], this));    
        }
    }    

    void addCamareiras(int numCamareiras){

        String[] names = {"Dona Maria", "Alceu", "Ana", "Alcileia", "Joana", "Gabriela", "Zendaia", "Duda", "roberto" ,"Marcos"};
        Integer[] idades = {41, 59, 31, 40, 32, 19, 23, 24, 29, 50};
        String[] cpf = {"121-121-121-12", "232-232-232-23", "343-343-343-34", "555-555-555-55", "666-666-666-66", "777-777-777-77", "888-888-888-88", "999-999-999-99", "101-101-101-11", "222-222-222-22"};

        for (int i = 0; i < numCamareiras; i++){
            this.camareiras.add(new Camareira(names[i], idades[i], cpf[i]));
        }
    }

    //Retonra a recepcionista que estiver livre
    public Recepcionista recepcionistaDisponivel(){
        for (Recepcionista recepcionista : recepcionistas) {
            if (recepcionista.estaDisponivel()){
                return recepcionista;
            }
        }
        return null;
    }

    public void tentarAlugarQuarto(Hospede hospede){
        
    }

    public Quarto obterQuartoDisponivel() {
        lock.lock();
        try {
            for (Quarto quarto : quartos) {
                if (quarto.getDisponivel()) {
                    return quarto;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }   
}
