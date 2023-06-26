/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos;
//import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFormattedTextField;



public interface IProprietarioCRUD {

    public void incluir(Proprietario objContato) throws Exception;

    public void alterar(int  indexContatoAtual, Proprietario objContato) throws Exception;

    public Proprietario buscar(String cpf) throws Exception;

    public ArrayList<Proprietario> listagemDeContatos() throws Exception;
    //public void alterar(Proprietario contatoAlterado)throws Exception;

}
