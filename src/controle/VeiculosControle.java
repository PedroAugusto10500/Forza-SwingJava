/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.ArrayList;
import modelos.IVeiculosCRUD;
import modelos.Proprietario;
import modelos.Veiculos;
import persistencia.VeiculosDAO;


public class VeiculosControle implements IVeiculosCRUD {
    IVeiculosCRUD veiculoPersistencia = null;

    public VeiculosControle() {
        veiculoPersistencia = new VeiculosDAO();
    }

    
    // Verifica se uma placa de veículo já está cadastrada.
    
    private boolean verificarPlacaRepetida(String placa) {
        try {
            // Obter a lista de veículos cadastrados
            ArrayList<Veiculos> veiculos = listagemDeVeiculos();

            // Percorrer a lista de veículos e verificar se alguma placa é igual à placa fornecida
            for (Veiculos veiculo : veiculos) {
                if (veiculo.getPlaca().equals(placa)) {
                    return true; // Placa encontrada, é repetida
                }
            }
        } catch (Exception e) {
            // Tratar o erro ao obter a lista de veículos cadastrados
            e.printStackTrace();
        }

        return false; // Placa não encontrada, não é repetida
    }

    @Override
    public void incluir(Veiculos objVeiculo) throws Exception {
        try {
            // Validar a placa
            if (verificarPlacaRepetida(objVeiculo.getPlaca())) {
                throw new Exception("Placa já cadastrada. Por favor, escolha outra placa.");
            }
            if (!validarPlaca(objVeiculo.getPlaca())) {
                throw new Exception("Placa inválida. Por favor, insira uma placa válida no formato padrão ou Mercosul.");
            }

            // Validar o campo "km"
            if (!validarKm(objVeiculo.getKm())) {
                throw new Exception("Valor inválido para o campo \"km\". Por favor, insira um valor numérico.");
            }

            // Adicionar o objeto
            veiculoPersistencia.incluir(objVeiculo);
        } catch (Exception erro) {
            String msg = "Método incluir - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    
    // Valida o formato da placa do veículo.
     
    private boolean validarPlaca(String placa) {
        String regex = "^[A-Z]{3}-\\d{4}$"; // Formato AAA-1234
        String regexMercosul = "^[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}$"; // Formato ABC1D23
        return placa.matches(regex) || placa.matches(regexMercosul);
    }

    
     // Valida se o valor do campo "km" é um número válido.
     
    private boolean validarKm(String km) {
        try {
            Double.parseDouble(km);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void alterar(int indexVeiculoAtual, Veiculos objVeiculo) throws Exception {
        try {
            // Validar o campo "km"
            if (!validarKm(objVeiculo.getKm())) {
                throw new Exception("Valor inválido para o campo \"km\". Por favor, insira um valor numérico.");
            }

            veiculoPersistencia.alterar(indexVeiculoAtual, objVeiculo);
        } catch (Exception e) {
            throw e; // Lança novamente a exceção para propagar o erro.
        }
    }

    @Override
    public Veiculos buscarPlaca(String placa) throws Exception {
        if (placa.equals("")) {
            throw new Exception("Preencha o campo placa para buscar o veículo!");
        }

        // Busca o veículo pelo número da placa
        Veiculos veiculo = veiculoPersistencia.buscarPlaca(placa);

        // Verifica se o veículo existe
        if (veiculo == null) {
            throw new Exception("Veículo inexistente para a placa: " + placa);
        }

        // Retorna o veículo encontrado
        return veiculo;
    }

    @Override
    public ArrayList<Veiculos> listagemDeVeiculos() throws Exception {
        return veiculoPersistencia.listagemDeVeiculos();
    }
}
