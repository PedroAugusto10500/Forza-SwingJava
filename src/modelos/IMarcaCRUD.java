/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos;

import java.io.File;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Aluno
 */
public interface IMarcaCRUD {
    public void incluir(Marca objMarca)throws Exception;
    
    public Marca buscarPorID(String id) throws Exception;
    
    public void alterar(int indexMarcaAtual, Marca objMarca) throws Exception;

    public ArrayList<Marca> listagemDeMarcas() throws Exception;



}
