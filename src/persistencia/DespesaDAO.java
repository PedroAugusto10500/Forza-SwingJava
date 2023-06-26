/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Despesa;
import modelos.IDespesas;
//import modelos.Despesa;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import modelos.Gastos;


/**
 *
 * @author Antonio
 */
public class DespesaDAO implements IDespesas {

    //Atributos
    private String nomeDoArquivoNoDisco = null;

    public DespesaDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedados/gastosTotalBD.csv";

    }

    @Override
    public ArrayList<Despesa> despesaMensal() throws Exception {

        
//        ArrayList<Despesa> agenda = new ArrayList<>();
//BufferedReader br = null;
//try {
//    br = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
//    String linha = "";
//    while ((linha = br.readLine()) != null) {
//        String vetorStr[] = linha.split(";");
//        String dataStr = vetorStr[1];
//        LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//        String mesAno = data.format(DateTimeFormatter.ofPattern("MM/yyyy"));
//        String veiculo = vetorStr[2];
//        String descricao = vetorStr[3];
//        String categoria = vetorStr[4];
//        String valor = vetorStr[5];
//
//        float valorFloat = Float.parseFloat(valor);
//        NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance();
//        String valorFormatado = formatadorMoeda.format(valorFloat);
//
//        Despesa despesa = new Despesa(mesAno, veiculo, descricao, categoria, valorFormatado);
//
//        // Verificar se já existe uma despesa com a mesma categoria e mês/ano
//        boolean encontrado = false;
//        for (Despesa existente : agenda) {
//            if (existente.getCategoria().equals(categoria) && existente.getMesAno().equals(mesAno)) {
//                // Atualizar o valor somando à despesa existente
//                float novoValor = Float.parseFloat(existente.getValor().substring(3)) + valorFloat;
//                String novoValorFormatado = "R$" + formatadorMoeda.format(novoValor);
//                existente.setValor(novoValorFormatado);
//                encontrado = true;
//                break;
//            }
//        }
//
//        if (!encontrado) {
//            // Adicionar o novo objeto Despesas à lista
//            agenda.add(despesa);
//        }
//    }
//
//    // Ordenar a lista de despesas por veículo
//    agenda.sort(Comparator.comparing(Despesa::getVeiculo));
//    return agenda;
//} catch (Exception erro) {
//    String msg = "Erro no método listagemDeDespesas - " + erro.getMessage();
//    throw new Exception(msg);
//} finally {
//    if (br != null) {
//        br.close();
//    }
//}
        
        
 //----------------------------------------------------------------------------------------       
        
        
        ArrayList<Despesa> agenda = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                //String id = vetorStr[0];
                String dataStr = vetorStr[1];
                LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String mesAno = data.format(DateTimeFormatter.ofPattern("MM/yyyy"));
                String veiculo = vetorStr[2];
                String descricao = vetorStr[3];
                String categoria = vetorStr[4];
                String valor = vetorStr[5];
                double valorDouble = Double.parseDouble(valor);

                // Formatar o valor como moeda corrente do Brasil
                Locale brLocale = new Locale("pt", "BR");
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(brLocale);
                String valorFormatado = currencyFormat.format(valorDouble);
               
                
                Despesa despesa = new Despesa(mesAno, veiculo, descricao, categoria, valor);

                // Verificar se já existe uma despesa com a mesma categoria e mês/ano
                boolean encontrado = false;
                for (Despesa existente : agenda) {
                    if (existente.getCategoria().equals(categoria) && existente.getMesAno().equals(mesAno)) {
                        // Atualizar o valor somando à despesa existente
                        double novoValor = Double.parseDouble(existente.getValor()) + Double.parseDouble(valor);
                        existente.setValor(String.valueOf(novoValor));
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    // Adicionar o novo objeto Despesas à lista
                    agenda.add(despesa);
                }
            }

            // Ordenar a lista de despesas por veículo
            agenda.sort(Comparator.comparing(Despesa::getVeiculo));
            return agenda;
        } catch (Exception erro) {
            String msg = "Erro no método listagemDeDespesas - " + erro.getMessage();
            throw new Exception(msg);
        } finally {
            if (br != null) {
                br.close();
            }
        }
        
        
        
        
        

        
        
        //------------------------------------------------------------------------------------------------------------------
        //Sem Number Format
        
//            ArrayList<Despesa> agenda = new ArrayList<>();
//    BufferedReader br = null;
//    try {
//        br = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
//        String linha = "";
//        while ((linha = br.readLine()) != null) {
//            String vetorStr[] = linha.split(";");
//            String dataStr = vetorStr[1];
//            LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//            String mesAno = data.format(DateTimeFormatter.ofPattern("MM/yyyy"));
//            String veiculo = vetorStr[2];
//            String descricao = vetorStr[3];
//            String categoria = vetorStr[4];
//            String valor = vetorStr[5];
//
//            Despesa despesa = new Despesa(mesAno, veiculo, descricao, categoria, valor);
//
//            // Verificar se já existe uma despesa com a mesma categoria e mês/ano
//            boolean encontrado = false;
//            for (Despesa existente : agenda) {
//                if (existente.getCategoria().equals(categoria) && existente.getMesAno().equals(mesAno)) {
//                    // Atualizar o valor somando à despesa existente
//                    float novoValor = Float.parseFloat(existente.getValor()) + Float.parseFloat(valor);
//                    existente.setValor(String.valueOf(novoValor));
//                    encontrado = true;
//                    break;
//                }
//            }
//
//            if (!encontrado) {
//                // Adicionar o novo objeto Despesas à lista
//                agenda.add(despesa);
//            }
//        }
//
//        // Ordenar a lista de despesas por veículo
//        agenda.sort(Comparator.comparing(Despesa::getVeiculo));
//        return agenda;
//    } catch (Exception erro) {
//        String msg = "Erro no método listagemDeDespesas - " + erro.getMessage();
//        throw new Exception(msg);
//    } finally {
//        if (br != null) {
//            br.close();
//        }
//    }
//        
//
    }

   
}
