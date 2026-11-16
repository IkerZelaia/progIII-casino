package table;

public class PartidaBlackjack {
    private int ronda;
    private int apuesta;
    private int puntosJugador;
    private int puntosCrupier;
    private String resultado;
    private int ganancia;

    public PartidaBlackjack(int ronda, int apuesta, int puntosJugador, int puntosCrupier, String resultado, int ganancia) {
        this.ronda = ronda;
        this.apuesta = apuesta;
        this.puntosJugador = puntosJugador;
        this.puntosCrupier = puntosCrupier;
        this.resultado = resultado;
        this.ganancia = ganancia;
    }

    public int getRonda() { return ronda; }
    public int getApuesta() { return apuesta; }
    public int getPuntosJugador() { return puntosJugador; }
    public int getPuntosCrupier() { return puntosCrupier; }
    public String getResultado() { return resultado; }
    public int getGanancia() { return ganancia; }
}