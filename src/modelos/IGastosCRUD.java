

package modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hSua
 */
public interface IGastosCRUD {

    public void incluir(Gastos objGastos) throws Exception;

    public void alterar(int indexGastosAtual, Gastos objGastos) throws Exception;

    public ArrayList<Gastos> listagemDeGastos() throws Exception;

    
    public List<Gastos> buscar(String veiculo, String mes, String ano) throws Exception; //Filtro Buscar Ano 

   // public List<Gastos> buscar(String veiculo, String ano) throws Exception; // ACHO QUE PODE APAGAR

    public List<Gastos> buscarPorVeiculo(String veiculo) throws Exception; // Botão Buscar da tela de Gastos

    //public ArrayList<Gastos> gastoMensal() throws Exception;

    public ArrayList<Gastos> gastoAnual() throws Exception;

    // public Gastos buscar( String Proprietario) throws Exception;
}
