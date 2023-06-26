
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.Comparator;
//import java.util.Date;
import java.util.List;
import modelos.Gastos;
//import modelos.IGastosCRUD;
import modelos.IGastosCRUD;
//import modelos.Proprietario;

/**
 *
 * @author Antonio
 */
public class GastosDAO implements IGastosCRUD {

    //Atributos
    private String nomeDoArquivoNoDisco = null;
    private String nomeDoArquivoTemporario = null;

    public GastosDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedados/gastosTotalBD.csv";
        nomeDoArquivoTemporario = "./src/bancodedados/gastosTotalBD-temp.csv";
    }

    
    @Override
    public void incluir(Gastos objGastos) throws Exception {
        try {

            //cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            //Escreve no arquivo
            bw.write(objGastos.toString() + "\n");
            //fecha o arquivo
            bw.close();

        } catch (Exception erro) {
            String msg = "Metodo Incluir Contato - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    // Mostra todos os gastos cadastrados no arquivo.csv
    @Override
    public ArrayList<Gastos> listagemDeGastos() throws Exception {
        ArrayList<Gastos> agenda = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String id = vetorStr[0];
                String data = vetorStr[1];
                String veiculo = vetorStr[2];
                String descricao = vetorStr[3];
                String categoria = vetorStr[4];
                String valor = vetorStr[5];

                Gastos gasto = new Gastos(id, data, veiculo, descricao, categoria, valor);

                agenda.add(gasto);
            }
            return agenda;
        } catch (Exception erro) {
            String msg = "Erro no método listagemDeGastos - " + erro.getMessage();
            throw new Exception(msg);
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    //Ordena  os dados da listagem de contatos é Chamado na Jtable Anual
@Override
public ArrayList<Gastos> gastoAnual() throws Exception {
    ArrayList<Gastos> agenda = new ArrayList<>();
    BufferedReader br = null;
    try {
        br = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
        String linha = "";
        while ((linha = br.readLine()) != null) {
            String vetorStr[] = linha.split(";");
            String id = vetorStr[0];
            String data = vetorStr[1];
            String veiculo = vetorStr[2];
            String descricao = vetorStr[3];
            String categoria = vetorStr[4];
            String valor = vetorStr[5];

            Gastos gasto = new Gastos(id, data, veiculo, descricao, categoria, valor);

            agenda.add(gasto);
        }

        // Ordenar a lista por proprietário (veiculo) e categoria
        agenda.sort(Comparator.comparing(Gastos::getVeiculo)
                .thenComparing(Gastos::getCategoria));

        return agenda;
    } catch (Exception erro) {
        String msg = "Erro no método listagemDeGastos - " + erro.getMessage();
        throw new Exception(msg);
    } finally {
        if (br != null) {
            br.close();
        }
    }
}
    
    
// relatório Mensal 
//@Override
//public ArrayList<Gastos> listagemDeGastos() throws Exception {
//    ArrayList<Gastos> agenda = new ArrayList<>();
//    BufferedReader br = null;
//    try {
//        br = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
//        String linha = "";
//        while ((linha = br.readLine()) != null) {
//            String vetorStr[] = linha.split(";");
//            String id = vetorStr[0];
//            String data = vetorStr[1];
//            String veiculo = vetorStr[2];
//            String descricao = vetorStr[3];
//            String categoria = vetorStr[4];
//            String valor = vetorStr[5];
//
//            Gastos gasto = new Gastos(id, data, veiculo, descricao, categoria, valor);
//
//            // Verificar se já existe um gasto com a mesma categoria, mês e ano
//            boolean encontrado = false;
//            for (Gastos existente : agenda) {
//                if (existente.getCategoria().equals(categoria) &&
//                    existente.getMes().equals(gasto.getMes()) &&
//                    existente.getAno().equals(gasto.getAno())) {
//                    // Atualizar o valor somando ao gasto existente
//                    double novoValor = Double.parseDouble(existente.getValor()) + Double.parseDouble(valor);
//                    existente.setValor(String.valueOf(novoValor));
//                    encontrado = true;
//                    break;
//                }
//            }
//
//            if (!encontrado) {
//                // Adicionar o novo objeto Gastos à lista
//                agenda.add(gasto);
//            }
//        }
//        return agenda;
//    } catch (Exception erro) {
//        String msg = "Erro no método listagemDeGastos - " + erro.getMessage();
//        throw new Exception(msg);
//    } finally {
//        if (br != null) {
//            br.close();
//        }
//    }
//}

// Filtro Buscar por veiculo 
    @Override
    public List<Gastos> buscarPorVeiculo(String veiculo) throws Exception {
        List<Gastos> registrosEncontrados = new ArrayList<>();
        double valorTotal = 0.0; // Variável para armazenar o valor total dos gastos

        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";

            while ((linha = br.readLine()) != null) {
                String[] vetorStr = linha.split(";");
                String id = vetorStr[0];
                String dataAux = vetorStr[1];
                String veiculoAux = vetorStr[2];
                String descricao = vetorStr[3];
                String categoria = vetorStr[4];
                String valor = vetorStr[5];

                Gastos objetoContato = new Gastos(id, dataAux, veiculoAux, descricao, categoria, valor);

                if (veiculo.equalsIgnoreCase(veiculoAux)) {
                    registrosEncontrados.add(objetoContato);
                    String valorSemSimboloMoeda = valor.replace("R$", "").trim();
                    String valorSemSeparadorMilhar = valorSemSimboloMoeda.replace(".", "");
                    String valorSemSeparadorDecimal = valorSemSeparadorMilhar.replace(",", ".");
                    valorTotal += Double.parseDouble(valorSemSeparadorDecimal); // Atualizar o valor total dos gastos
                }
            }

            br.close();
        } catch (Exception erro) {
            // Tratar o erro adequadamente
            String msg = "Erro ao buscar os gastos - " + erro.getMessage();
            throw new Exception(msg);
        }

        System.out.println("Valor total dos gastos do veículo " + veiculo + ": " + valorTotal);
        return registrosEncontrados;
    }

    @Override
    public void alterar(int indexGastosAtual, Gastos objGastos) throws Exception {
        BufferedReader brArquivoPrincipal = null;
        BufferedWriter bwArquivoTemporario = null;

        File arquivoTemporario = new File(nomeDoArquivoTemporario);
        File arquivoPrincipal = new File(nomeDoArquivoNoDisco);

        try {
            brArquivoPrincipal = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
            bwArquivoTemporario = new BufferedWriter(new FileWriter(nomeDoArquivoTemporario, true));

            String linha = "";
            int index = 0;

            while ((linha = brArquivoPrincipal.readLine()) != null) {

                if (index == indexGastosAtual) {
                    // se é o gasto atual, escreve o novo gasto
                    bwArquivoTemporario.write(objGastos.toString() + "\n");

                    System.out.println(indexGastosAtual);
                    System.out.println(objGastos.toString());
                } else { // senão, escreve do jeito que estava antes
                    bwArquivoTemporario.write(linha + "\n");
                }
                bwArquivoTemporario.flush();
                index++;
            }
        } finally {
            if (brArquivoPrincipal != null) {
                brArquivoPrincipal.close();
            }
            if (bwArquivoTemporario != null) {
                bwArquivoTemporario.close();
            }

            arquivoPrincipal.delete();
            arquivoTemporario.renameTo(arquivoPrincipal);
        }
    }
    
    
// Filtro Tabela Anual
    @Override
    public List<Gastos> buscar(String veiculo, String mes, String ano) throws Exception {

        List<Gastos> registrosEncontrados = new ArrayList<>();
        double valorTotal = 0.0; // Variável para armazenar o valor total dos gastos

        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";

            while ((linha = br.readLine()) != null) {
                String[] vetorStr = linha.split(";");
                String id = vetorStr[0];
                String dataAux = vetorStr[1];
                String veiculoAux = vetorStr[2];
                String descricao = vetorStr[3];
                String categoria = vetorStr[4];
                String valor = vetorStr[5];

                Gastos objetoContato = new Gastos(id, dataAux, veiculoAux, descricao, categoria, valor);

                int mesDado = Integer.parseInt(dataAux.split("/")[1]);
                int anoDado = Integer.parseInt(dataAux.split("/")[2]);
                if (mesDado == Integer.parseInt(mes) && anoDado == Integer.parseInt(ano) && veiculoAux.equalsIgnoreCase(veiculo)) {
                    registrosEncontrados.add(objetoContato);
                    valorTotal += Double.parseDouble(valor); // Atualizar o valor total dos gastos
                }
            }

            br.close();

            // Ordenar os registros pela data
//            Collections.sort(registrosEncontrados, new Comparator<Gastos>() {
//                public int compare(Gastos g1, Gastos g2) {
//                    Date data1 = parseData(g1.getData());
//                    Date data2 = parseData(g2.getData());
//                    return data1.compareTo(data2);
//                }
//            });
        } catch (Exception erro) {
            //String msg = "Método buscar - " + erro.getMessage();
            //throw new Exception(msg);
        }

        System.out.println("Valor total dos gastos do veículo " + veiculo + ": " + valorTotal);
        return registrosEncontrados;
    }

//    private Date parseData(String data) {
//        try {
//            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//            return format.parse(data);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null; // Trate o erro de parsing de data adequadamente no seu código
//        }
//    }

//    @Override
//    public List<Gastos> buscar(String veiculo, String ano) throws Exception {
//        List<Gastos> registrosEncontrados = new ArrayList<>();
//        double valorTotal = 0.0; // Variável para armazenar o valor total dos gastos
//
//        try {
//            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
//            BufferedReader br = new BufferedReader(fr);
//            String linha = "";
//
//            while ((linha = br.readLine()) != null) {
//                String[] vetorStr = linha.split(";");
//                String id = vetorStr[0];
//                String dataAux = vetorStr[1];
//                String veiculoAux = vetorStr[2];
//                String descricao = vetorStr[3];
//                String categoria = vetorStr[4];
//                String valor = vetorStr[5];
//
//                Gastos objetoContato = new Gastos(id, dataAux, veiculoAux, descricao, categoria, valor);
//
//                int anoDado = Integer.parseInt(dataAux.split("/")[2]);
//                if (anoDado == Integer.parseInt(ano) && veiculoAux.equalsIgnoreCase(veiculo)) {
//                    registrosEncontrados.add(objetoContato);
//                    valorTotal += Double.parseDouble(valor); // Atualizar o valor total dos gastos
//                }
//            }
//
//            br.close();
//
//            // Ordenar os registros pela data
////            Collections.sort(registrosEncontrados, new Comparator<Gastos>() {
////                public int compare(Gastos g1, Gastos g2) {
////                    Date data1 = parseData(g1.getData());
////                    Date data2 = parseData(g2.getData());
////                    return data1.compareTo(data2);
////                }
////            });
//        } catch (Exception erro) {
//            //String msg = "Método buscar - " + erro.getMessage();
//            //throw new Exception(msg);
//        }
//
//        System.out.println("Valor total dos gastos do veículo " + veiculo + ": " + valorTotal);
//        return registrosEncontrados;
//    }

// Gastos Mensal 
    // EU ACHO QUE ESSE MÉTODO PODE SER APAGADO
//    @Override
//    public ArrayList<Gastos> gastoMensal() throws Exception {
//
//        ArrayList<Gastos> agenda = new ArrayList<>();
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
//            String linha = "";
//            while ((linha = br.readLine()) != null) {
//                String vetorStr[] = linha.split(";");
//                String id = vetorStr[0];
//                String data = vetorStr[1];
//                String veiculo = vetorStr[2];
//                String descricao = vetorStr[3];
//                String categoria = vetorStr[4];
//                String valor = vetorStr[5];
//
//                Gastos gasto = new Gastos(id, data, veiculo, descricao, categoria, valor);
//
//                // Verificar se já existe um gasto com a mesma categoria, mês e ano
//                boolean encontrado = false;
//                for (Gastos existente : agenda) {
//                    if (existente.getCategoria().equals(categoria)
//                            && existente.getMes().equals(gasto.getMes())
//                            && existente.getAno().equals(gasto.getAno())) {
//                        // Atualizar o valor somando ao gasto existente
//                        double novoValor = Double.parseDouble(existente.getValor()) + Double.parseDouble(valor);
//                        existente.setValor(String.valueOf(novoValor));
//                        encontrado = true;
//                        break;
//                    }
//                }
//
//                if (!encontrado) {
//                    // Adicionar o novo objeto Gastos à lista
//                    agenda.add(gasto);
//                }
//            }
//
//            // Ordenar a lista de gastos por veículo
//            agenda.sort(Comparator.comparing(Gastos::getVeiculo));
//            return agenda;
//        } catch (Exception erro) {
//            String msg = "Erro no método listagemDeGastos - " + erro.getMessage();
//            throw new Exception(msg);
//        } finally {
//            if (br != null) {
//                br.close();
//            }
//        }
//
//    }

}
