
package controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import modelos.Gastos;
import modelos.IGastosCRUD;
import persistencia.GastosDAO;

/**
 *
 * @author Antonio
 */
public class GastosControle implements IGastosCRUD {

    //Atributos
    IGastosCRUD gastosPersistencia = null;

    public GastosControle() {
        gastosPersistencia = new GastosDAO();

    }

    public Object buscarPorId(String id) {
        try {
            ArrayList<Gastos> gastos = listagemDeGastos();
            for (Gastos gasto : gastos) {
                if (gasto.getId().equals(id)) {
                    return gasto;
                }
            }
        } catch (Exception e) {
            // Tratar a exceção
        }
        return null;
    }

    @Override
    public void incluir(Gastos objGastos) throws Exception {
        try {
            // Validar ID
            if (!objGastos.getId().matches("\\d+")) {
                throw new Exception("O ID da marca deve conter apenas números!");
            }

            // Validar se já existe uma marca com o mesmo ID
            if (buscarPorId(objGastos.getId()) != null) {
                throw new Exception("Já existe uma marca com esse ID!");
            }

            // Validar e formatar a data
            validarData(objGastos.getData());

            // Validar e formatar o valor
            validarValor(objGastos.getValor());

            // Adicionar o objeto
            gastosPersistencia.incluir(objGastos);
        } catch (Exception erro) {
            String msg = "Erro ao incluir Gastos - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    private void validarData(String data) throws Exception {
        // Verificar se a data contém apenas números e a barra "/"
        if (!data.matches("[0-9/]+")) {
            throw new Exception("Formato de data inválido. Apenas números e a barra '/' são permitidos.");
        }

        // Verificar se a data é válida em termos de formato
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            sdf.parse(data);
        } catch (ParseException e) {
            throw new Exception("Data inválida. Certifique-se de usar o formato dd/MM/yyyy.");
        }
    }

    private void validarValor(String valor) throws Exception {
        // Verificar se o valor contém apenas números, o símbolo "R$" e pontos como separador decimal
        if (!valor.matches("^\\d+(\\.\\d+)?$")) {
            throw new Exception("Formato de valor inválido. O valor deve ser um número inteiro ou decimal.");

        }

        // Implemente a lógica adicional de validação, se necessário
    }

    @Override
    public ArrayList<Gastos> listagemDeGastos() throws Exception {

        return gastosPersistencia.listagemDeGastos();

    }

    @Override
    public List<Gastos> buscarPorVeiculo(String veiculo) throws Exception {
        // Verifica se o campo proprietário está vazio, caso esteja, lança uma exceção
        if (veiculo.isEmpty()) {
            throw new Exception("Preencha o campo Proprietário para realizar a busca!");
        }

        // Realiza a busca dos gastos pelo proprietário
        List<Gastos> gastos = gastosPersistencia.buscarPorVeiculo(veiculo);

        // Verifica se foram encontrados gastos
        if (gastos.isEmpty()) {
            throw new Exception("Nenhum gasto encontrado para o proprietário informado!");
        }

        // Retorna a lista de gastos encontrados
        return gastos;
    }

    @Override
    public void alterar(int indexGastosAtual, Gastos objGastos) throws Exception {
        try {

            // Validar e formatar a data
            validarData(objGastos.getData());

            // Validar e formatar o valor
            validarValor(objGastos.getValor());

            // Realizar a alteração do gasto
            gastosPersistencia.alterar(indexGastosAtual, objGastos);
        } catch (Exception erro) {
            String msg = "Erro ao alterar Gastos - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public List<Gastos> buscar(String veiculo, String mes, String ano) throws Exception {

        return gastosPersistencia.buscar(veiculo, mes, ano);

    }

//    @Override
//    public List<Gastos> buscar(String veiculo, String ano) throws Exception {
//
//        return gastosPersistencia.buscar(veiculo, ano);
//
//    }

//    @Override
//    public ArrayList<Gastos> gastoMensal() throws Exception {
//
//        return gastosPersistencia.gastoMensal();
//
//    }

    @Override
    public ArrayList<Gastos> gastoAnual() throws Exception {

        return gastosPersistencia.gastoAnual();

    }

}
