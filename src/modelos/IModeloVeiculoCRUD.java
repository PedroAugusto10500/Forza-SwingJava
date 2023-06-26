/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author hSua
 */
public interface IModeloVeiculoCRUD {
    
    
    public ModeloVeiculo buscarPorID(String id) throws Exception;
    
    public void alterar(int indexModeloAtual, ModeloVeiculo objModelo) throws Exception;

 
    public void incluir(ModeloVeiculo objModelo)throws Exception;


    public ArrayList <ModeloVeiculo> listagemModelos() throws Exception;
}
