package entidades;

public class Automovil {
    private String placa, modelo, color, marca;
    private int garajeId;
    private String nombreGaraje; // solo para mostrar
    private String estado; // nuevo campo

    public Automovil() {
        this("", "", "", "",  "disponible",0);
    }

    public Automovil(String placa, String modelo, String color, String marca, int garajeId) {
        this(placa, modelo, color, marca, "disponible",garajeId);
    }

    public Automovil(String placa, String modelo, String color, String marca, String nombreGaraje) {
        this.placa = placa;
        this.modelo = modelo;
        this.color = color;
        this.marca = marca;
        this.nombreGaraje = nombreGaraje;
        this.estado = "disponible";
    }

    public Automovil(String placa, String modelo, String color, String marca, String estado,int garajeId) {
        this.placa = placa;
        this.modelo = modelo;
        this.color = color;
        this.marca = marca;
        this.garajeId = garajeId;
        this.estado = estado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getGarajeId() {
        return garajeId;
    }

    public void setGarajeId(int garajeId) {
        this.garajeId = garajeId;
    }

    public String getNombreGaraje() {
        return nombreGaraje;
    }

    public void setNombreGaraje(String nombreGaraje) {
        this.nombreGaraje = nombreGaraje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
