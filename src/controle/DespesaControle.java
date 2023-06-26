/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.ArrayList;
import modelos.Despesa;
import modelos.IDespesas;
import persistencia.DespesaDAO;

/**
 *
 * @author Antonio
 */
public class DespesaControle implements IDespesas{

   
    
            //Atributos
    IDespesas despesapersistencia = null;
 
    
    //Construtor

    public DespesaControle() {
        despesapersistencia = (IDespesas) new DespesaDAO(); // Inicializa o atributo despesapersistencia com uma nova instância de DespesaDAO convertida para o tipo IDespesas.
        
    }

    @Override
    public ArrayList<Despesa> despesaMensal() throws Exception {
    
        
        return despesapersistencia.despesaMensal();// Chama o método despesaMensal() do objeto despesapersistencia.
    
    }

    
}
