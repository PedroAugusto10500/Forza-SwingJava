
package modelos;



/**
 *
 * @author Antonio
 */
public class Gastos {

    private String id = "";
    private String data = "";
    private String veiculo = "";
    private String descricao = "";
    private String categoria = "";
    private String valor = "";

    public Gastos() {
    this.id = "";
     this.data = "";
     this.veiculo = "";
     this.descricao = "";
     this.categoria = "";
     this.valor = "";
    }

    public Gastos(String id, String data, String veiculo, String descricao, String categoria, String valor) {
    this.id = id;
     this.data = data;
     this.veiculo = veiculo;
     this.descricao = descricao;
     this.categoria = categoria;
     this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

  
    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    // Está ligado ao método  public ArrayList<Gastos> gastoMensal() throws Exception { da classe gastosDAO
        public String getMes() {
        // Lógica para extrair o mês da data
        String[] partes = data.split("/");
        return partes[1]; // Supondo que a data esteja no formato dd/mm/aaaa e o mês esteja na posição 1
    }

    public String getAno() {
        // Lógica para extrair o ano da data
        String[] partes = data.split("/");
        return partes[2]; // Supondo que a data esteja no formato dd/mm/aaaa e o ano esteja na posição 2
    }

   @Override
public String toString() {
    return id + ";" + data + ";" + veiculo + ";" + descricao + ";" + categoria + ";" + valor;
}


//    public void setAno(int parseAno) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    public void setMes(int parseMes) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

}
