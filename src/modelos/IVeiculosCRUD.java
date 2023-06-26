/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos;

import java.util.ArrayList;

/**
 *
 * @author Antonio
 */
public interface IVeiculosCRUD {
    public void incluir(Veiculos objVeiculo) throws Exception;

    public void alterar(int indexVeiculoAtual, Veiculos objVeiculo) throws Exception;

    public Veiculos buscarPlaca(String placa) throws Exception;

    public ArrayList<Veiculos> listagemDeVeiculos() throws Exception;
}
