/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelos.IModeloVeiculoCRUD;
import modelos.Marca;
import modelos.ModeloVeiculo;
import persistencia.ModeloVeiculoDAO;


public class ModeloVeiculoControle implements IModeloVeiculoCRUD {
    IModeloVeiculoCRUD modeloPersistencia = null;

    public ModeloVeiculoControle() {
        modeloPersistencia = (IModeloVeiculoCRUD) new ModeloVeiculoDAO();
    }

    
    // Busca um modelo de veículo por ID.
     
    public ModeloVeiculo buscarPorId(String id) throws Exception {
        ArrayList<ModeloVeiculo> modelos = listagemModelos();
        for (ModeloVeiculo modelo : modelos) {
            if (modelo.getId().equals(id)) {
                return modelo;
            }
        }
        return null;
    }

    
    // Busca um modelo de veículo por descrição.
     
    public ModeloVeiculo buscarPorDescricao(String descricao) throws Exception {
        ArrayList<ModeloVeiculo> modelos = listagemModelos();
        for (ModeloVeiculo modelo : modelos) {
            if (modelo.getDescricao().equalsIgnoreCase(descricao)) {
                return modelo;
            }
        }
        return null;
    }

    
    // Inclui um novo modelo de veículo.
     
    @Override
    public void incluir(ModeloVeiculo objModelo) throws Exception {
        // Validação do ID da marca
        if (!objModelo.getId().matches("\\d+")) {
            throw new Exception("O ID da marca deve conter apenas números!");
        }

        // Verifica se já existe um modelo com o mesmo ID
        if (buscarPorId(objModelo.getId()) != null) {
            throw new Exception("Já existe um modelo com esse ID!");
        }

        // Validação do nome do modelo
        if (!objModelo.getDescricao().matches(".*")) {
            throw new Exception("O nome do modelo deve conter qualquer caractere!");
        }

        try {
            modeloPersistencia.incluir(objModelo);
            JOptionPane.showMessageDialog(null, "Modelo incluído com sucesso!");
        } catch (Exception erro) {
            String msg = "Método incluir(Marca objMarca) - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    
    // Altera um modelo de veículo existente.
     
    @Override
    public void alterar(int indexModeloAtual, ModeloVeiculo objModelo) throws Exception {
        try {
            modeloPersistencia.alterar(indexModeloAtual, objModelo);
        } catch (Exception e) {
            throw e; // Lança novamente a exceção para propagar o erro.
        }
    }
    
     //Retorna a lista de modelos de veículos.
     
    @Override
    public ArrayList<ModeloVeiculo> listagemModelos() throws Exception {
        return modeloPersistencia.listagemModelos();
    }

      //Busca um modelo de veículo por ID.
     
    @Override
    public ModeloVeiculo buscarPorID(String id) throws Exception {
        // Verifica se o campo ID está vazio, caso esteja, lança uma exceção
        if (id.equals("")) {
            throw new Exception("Preencha o campo ID para buscar o modelo!");
        }

        // Busca o modelo pelo ID
        ModeloVeiculo modelo = modeloPersistencia.buscarPorID(id);

        // Verifica se o modelo existe
        if (modelo == null) {
            throw new Exception("Modelo inexistente!");
        }

        // Retorna o modelo encontrado
        return modelo;
    }
}
