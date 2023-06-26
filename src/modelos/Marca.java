/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Aluno
 */
public class Marca {
    // Atributos
    private String id = "";
    private String descricao = "";
    private String imagemLogoMarca = "";
    
    // Construtores
    public Marca() {
        this.id = "";
        this.descricao = "";
        this.imagemLogoMarca = "";
    }
    
    //Get and Set
    public Marca(String id, String descricao, String imagemLogoMarca) throws Exception {
        this.id = id;
        this.descricao = descricao;
        this.imagemLogoMarca = imagemLogoMarca;
    }

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

    public String getImagemLogoMarca() {
        return imagemLogoMarca;
    }

    public void setImagemLogoMarca(String imagemLogoMarca) {
        this.imagemLogoMarca = imagemLogoMarca;
    }
    

    @Override
    public String toString() {
        return id + ";" + descricao + ";" + imagemLogoMarca;
    }
}
