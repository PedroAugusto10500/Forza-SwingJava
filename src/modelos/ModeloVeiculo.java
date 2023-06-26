/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author hSua
 */
public class ModeloVeiculo {
    //Atributos
    private String id;
    private String descricao;
    private String imagemModelo;
    private String comboMarca;
    
    // Construtores
    public ModeloVeiculo() {
        this.id = "";
        this.descricao = "";
        this.imagemModelo = "";
        this.comboMarca = "";
    }
    
    public ModeloVeiculo(String id, String descricao, String imagemModelo, String comboMarca) throws Exception {
        this.id = id;
        this.descricao = descricao;
        this.imagemModelo = imagemModelo;
        this.comboMarca = comboMarca;
    }

   // Métodos de acesso aos atributos (Get and Set)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemModelo() {
        return imagemModelo;
    }

    public void setImagemModelo(String imagemModelo) {
        this.imagemModelo = imagemModelo;
    }

    public String getComboMarca() {
        return comboMarca;
    }

    public void setComboMarca(String comboMarca) {
        this.comboMarca = comboMarca;
    }
   
    
    @Override
    public String toString() {
        return id + ";" + descricao + ";" + comboMarca + ";" + imagemModelo;
    }
}
