
package modelos;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Antonio
 */
public class Despesa {
    
    
    
    private String mesAno;
    private String veiculo;
    private String descricao;
    private String categoria;
    private String valor;

    public Despesa(String mesAno, String veiculo, String descricao, String categoria, String valor) {
        
        this.mesAno = mesAno;
        this.veiculo = veiculo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
    }



    public String getMesAno() {
        return mesAno;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
//        public String getValorFormatado() {
//        double valorDouble = Double.parseDouble(valor);
//        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
//        return format.format(valorDouble);
//    }
    
}
