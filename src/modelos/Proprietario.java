/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

public class Proprietario {
    // atributos
    private String cpf = "";
    private String nomeCompleto = "";
    private String email = "";
    private String telefone = "";
    private String cnh = "";
    private String categoria = "";
    private String imagemPessoal = "";
    private String imagemCnh = "";

    // construtores
    public Proprietario() {
        this.cpf = "";
        this.nomeCompleto = "";
        this.email = "";
        this.telefone = "";
        this.cnh = "";
        this.categoria = "";
        this.imagemPessoal = "";
        this.imagemCnh = "";
    }

    public Proprietario(String cpf, String nomeCompleto, String email, String telefone, String cnh, String categoria, String imagemPessoal, String imagemCnh) throws Exception {
        if (cpf.replaceAll("\\D", "").trim().isEmpty()) {
            
        }
        this.cpf = cpf;
        if (nomeCompleto.trim().isEmpty()) {
           
        }
        this.nomeCompleto = nomeCompleto;
        if (email.trim().isEmpty()) {
            
        }
        this.email = email;

        String telefoneFormatado = telefone.replaceAll("\\D", "").trim();
        if (telefoneFormatado.isEmpty() || telefoneFormatado.length() != 13) {
            
        }
        this.telefone = telefone;
        if (cnh.trim().isEmpty()) {
            
        }
        this.cnh = cnh;
        if (categoria.trim().isEmpty()) {
            
        }
        this.categoria = categoria;

        
        
        this.imagemPessoal = imagemPessoal;

        
        
        this.imagemCnh = imagemCnh;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagemPessoal() {
        return imagemPessoal;
    }

    public void setImagemPessoal(String imagemPessoal) {
        this.imagemPessoal = imagemPessoal;
    }

    public String getImagemCnh() {
        return imagemCnh;
    }

    public void setImagemCnh(String imagemCnh) {
        this.imagemCnh = imagemCnh;
    }

   


    
    @Override
    public String toString() {
        return cpf + ";" + nomeCompleto + ";" + email + ";" + telefone + ";" + cnh + ";" + categoria + ";" + imagemPessoal + ";" + imagemCnh;
    }
}
