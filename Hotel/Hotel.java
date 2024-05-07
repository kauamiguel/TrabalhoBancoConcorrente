package Hotel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Funcionarios.Camareira;
import Funcionarios.Recepcionista;

public class Hotel {
    private List<Recepcionista> recepcionistas;
    private List<Camareira> camareiras;
    private List<Quarto> quartos;
    private Queue<Hospede> filaEspera;
    private Lock lock;
    private String hotelNome = "Hotel 5 estrelas";
    private List<Chave> chaves;
    private List<Hospede> todosHospedes; // todos os hospedes que estao no projeto para usar de controle nos whiles
    private final Object lockObject = new Object();

    public Hotel(int numRecepcionistas, int numCamareiras, int numQuartos){
        recepcionistas = new ArrayList<>();
        camareiras = new ArrayList<>();
        quartos = new ArrayList<>();
        todosHospedes = new ArrayList<>();
        this.filaEspera = new LinkedList<>();
        chaves = new ArrayList<>();
        lock = new ReentrantLock(); 
        addRecepcionista(numRecepcionistas);
        addCamareiras(numCamareiras);
        addQuartos(numQuartos);
    }

    public List<Hospede> getHospedes(){
        return this.todosHospedes;
    }

    public Queue<Hospede> getFilaEspera() {
        return this.filaEspera;
    }

    public List<Chave> getChaves() {
        return chaves;
    }

    public List<Recepcionista> getRecepcionistas(){
        return this.recepcionistas;
    }

    public List<Camareira> getCamareiras(){
        return this.camareiras;
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
            this.camareiras.add(new Camareira(names[i], idades[i], cpf[i], this));
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

    //Retonra a camareira que estiver livre
    public Camareira camareiraDisponivel(){
        for (Camareira camareira : camareiras) {
            if (camareira.estaDisponivel()){
                return camareira;
            }
        }
        return null;
    }

    public Quarto obterQuartoDisponivel(Quarto quartoSegundo) {
        lock.lock();
        try {
            for (Quarto quarto : quartos) {
                if (quarto.getDisponivel() && quartoSegundo != quarto) {
                    return quarto;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    // verifica se o hospede esta passeando e se nao ja tem alguma camareira limpando - retorna um quarto sujo
    public Quarto obterQuartoSujo() {
        lock.lock();
        try {
            for (Quarto quarto : quartos) {
                if (!quarto.estaComChave() && !quarto.getEstaLimpando()) {
                    return quarto;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void addFilaEspera(Hospede hospede) {
        System.out.println(hospede.getNome() + " Adicionado a lista de espera");
        this.filaEspera.add(hospede);
    }

    public boolean aindaHaHospedes() {
        for (Hospede hospede : todosHospedes) {
           if(!hospede.isAlive()) {
               return true;
           }
        }
        return false;
    }


    public void addHospede(Hospede hospede) {
        lock.lock();
        if (this.todosHospedes.size() < 10){
            this.todosHospedes.add(hospede);
        }
        
        lock.unlock();
    }

    public void removeHospede(Hospede hospede) {
        this.todosHospedes.remove(hospede);
    }
}
