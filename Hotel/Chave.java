package Hotel;

public class Chave {
    private Quarto quarto;
    private Integer numeroChave;

    public Chave(Quarto quarto) {
        this.quarto = quarto;
        this.numeroChave = quarto.numero;
    }
}
