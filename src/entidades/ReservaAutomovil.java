package entidades;

public class ReservaAutomovil {
    private int reservaId;
    private String placa;
    private double precioAlquiler;
    private double litrosInicial;

    public ReservaAutomovil(int reservaId, String placa, double precioAlquiler, double litrosInicial) {
        this.reservaId = reservaId;
        this.placa = placa;
        this.precioAlquiler = precioAlquiler;
        this.litrosInicial = litrosInicial;
    }

    // Getters
    public int getReservaId() {
        return reservaId;
    }

    public String getPlaca() {
        return placa;
    }

    public double getPrecioAlquiler() {
        return precioAlquiler;
    }

    public double getLitrosInicial() {
        return litrosInicial;
    }

    // Setters
    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setPrecioAlquiler(double precioAlquiler) {
        this.precioAlquiler = precioAlquiler;
    }

    public void setLitrosInicial(double litrosInicial) {
        this.litrosInicial = litrosInicial;
    }
}
