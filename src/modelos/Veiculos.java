/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Antonio
 */
public class Veiculos {
    //Atributos
    private String placa;
    private String km;
    private String categoria;
    private String Combustivel;
    private String proprietario;
    private String modelo;

    //Construtores
    public Veiculos() {
        this.placa = "";
        this.km = "";
        this.categoria =  "";
        this.Combustivel = "";
        this.proprietario = "";
        this.modelo = "";
    }

    public Veiculos(String placa, String km, String categoria, String Combustivel, String proprietario, String modelo) throws Exception {   
        this.placa = placa;
        this.km = km;
        this.categoria =  categoria;
        this.Combustivel = Combustivel;
        this.proprietario = proprietario;
        this.modelo = modelo;
    }

    //Get and Set
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCombustivel() {
        return Combustivel;
    }

    public void setCombustivel(String Combustivel) {
        this.Combustivel = Combustivel;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return placa + ";" + km + ";" + categoria + ";" + Combustivel + ";" + proprietario + ";" + modelo;
    }    
}
