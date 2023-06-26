/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import modelos.IMarcaCRUD;
import modelos.Marca;
import persistencia.MarcaDAO;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelos.Proprietario;

/**
 *
 * author hSua
 */
public class MarcaControle implements IMarcaCRUD {
    
    IMarcaCRUD marcaPersistencia = null;
    
    public MarcaControle() {
        marcaPersistencia = new MarcaDAO();
    }

    // Busca uma marca pelo ID
    public Object buscarPorId(String id) {
        try {
            ArrayList<Marca> marcas = listagemDeMarcas();
            for (Marca marca : marcas) {
                if (marca.getId().equals(id)) {
                    return marca;
                }
            }
        } catch (Exception e) {
            // Tratar a exceção
        }
        return null;
    }

    // Busca uma marca pela descrição
    public Object buscarPorDescricao(String descricao) {
        try {
            ArrayList<Marca> marcas = listagemDeMarcas();
            for (Marca marca : marcas) {
                if (marca.getDescricao().equalsIgnoreCase(descricao)) {
                    return marca;
                }
            }
        } catch (Exception e) {
            // Tratar a exceção
        }
        return null;
    }

    // Inclui uma nova marca
    @Override
    public void incluir(Marca objMarca) throws Exception {
        // Validar ID
        if (!objMarca.getId().matches("\\d+")) {
            throw new Exception("O ID da marca deve conter apenas números!");
        }

        // Validar se já existe uma marca com o mesmo ID
        if (buscarPorId(objMarca.getId()) != null) {
            throw new Exception("Já existe uma marca com esse ID!");
        }

        // Validar se já existe uma marca com a mesma descrição
        if (buscarPorDescricao(objMarca.getDescricao()) != null) {
            throw new Exception("Essa marca já existe!");
        }

        // Validar Nome da Marca
        if (!objMarca.getDescricao().matches("^[A-Za-z]+$")) {
            throw new Exception("O nome da marca deve conter apenas letras!");
        }

        try {
            marcaPersistencia.incluir(objMarca);
            JOptionPane.showMessageDialog(null, "Marca incluída com sucesso!");
        } catch (Exception erro) {
            String msg = "Método incluir(Marca objMarca) - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    // Altera uma marca existente
    @Override
    public void alterar(int indexMarcaAtual, Marca objMarca) throws Exception {
        try {
            marcaPersistencia.alterar(indexMarcaAtual, objMarca);
        } catch (Exception e) {
            throw e; // Lança novamente a exceção para propagar o erro.
        }
    }

    // Busca uma marca pelo ID
    @Override
    public Marca buscarPorID(String id) throws Exception {
        // Verifica se o campo ID está vazio, caso esteja, lança uma exceção
        if (id.equals("")) {
            throw new Exception("Preencha o campo ID para buscar a marca!");
        }

        // Busca a marca pelo ID
        Marca marca = marcaPersistencia.buscarPorID(id);

        // Verifica se a marca existe
        if (marca == null) {
            throw new Exception("Marca inexistente!");
        }

        // Retorna a marca encontrada
        return marca;
    }

    // Retorna uma listagem de todas as marcas
    @Override
    public ArrayList<Marca> listagemDeMarcas() throws Exception {
        return marcaPersistencia.listagemDeMarcas();
    }
}
