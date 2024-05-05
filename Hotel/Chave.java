package Hotel;

public class Chave {
    private Quarto quarto;
    private int numeroChave;

    public Chave(Quarto quarto) {
        this.quarto = quarto;
        this.numeroChave = quarto.numero;
    }

    public Integer getNumeroChave() {
        return numeroChave;
    }
}
