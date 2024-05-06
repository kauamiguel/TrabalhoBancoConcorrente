package Hotel;

public class Chave {
    private Quarto quarto;
    private int numeroChave;

    public Chave(Quarto quarto) {
        this.quarto = quarto;
        this.numeroChave = quarto.getNumero();
    }

    public Integer getNumeroChave() {
        return numeroChave;
    }
}
