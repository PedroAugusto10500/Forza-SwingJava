/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import modelos.IVeiculosCRUD;
import modelos.Proprietario;
import modelos.Veiculos;


public class VeiculosDAO implements IVeiculosCRUD{

    private String nomeDoArquivoNoDisco = null;
    private String nomeDoArquivoTemporario = null;
    
    public VeiculosDAO() {
        // Define os nomes dos arquivos utilizados para persistência
        nomeDoArquivoNoDisco = "./src/bancodedados/veiculosBD.csv";
        nomeDoArquivoTemporario = "./src/bancodedados/veiculosBD-temp.csv";
    }
    
    @Override
    public void incluir(Veiculos objVeiculo) throws Exception {
        try {
            // Cria o arquivo para escrita
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            // Cria o buffer de escrita
            BufferedWriter bw = new BufferedWriter(fw);
            // Escreve os dados do veículo no arquivo
            bw.write(objVeiculo.toString() + "\n");
            // Fecha o arquivo
            bw.close();
        } catch (Exception erro) {
            String msg = "Metodo Incluir Veiculo - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void alterar(int indexVeiculoAtual, Veiculos objVeiculo) throws Exception {
        BufferedReader brArquivoPrincipal = null;
        BufferedWriter bwArquivoTemporario = null;
        
        File arquivoTemporario = new File(nomeDoArquivoTemporario);
        File arquivoPrincipal = new File(nomeDoArquivoNoDisco);
        
        try {
            // Abre o arquivo principal para leitura
            brArquivoPrincipal = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
            // Abre o arquivo temporário para escrita
            bwArquivoTemporario = new BufferedWriter(new FileWriter(nomeDoArquivoTemporario, true));
            
            String linha = "";
            int index = 0;
            
            // Lê cada linha do arquivo principal
            while ((linha = brArquivoPrincipal.readLine()) != null) {               

                if (index == indexVeiculoAtual) {
                    // Se é o veículo atual, escreve o novo veículo no arquivo temporário
                    bwArquivoTemporario.write(objVeiculo.toString() + "\n");
                    
                    System.out.println(indexVeiculoAtual);
                    System.out.println(objVeiculo.toString());
                } else { // Senão, escreve a linha do jeito que estava antes
                    bwArquivoTemporario.write(linha + "\n");
                }
                bwArquivoTemporario.flush();
                index++;
            }
        } finally {
            // Fecha os arquivos
            if (brArquivoPrincipal != null) {
                brArquivoPrincipal.close();
            }
            if (bwArquivoTemporario != null) {
                bwArquivoTemporario.close();
            }
            
            // Deleta o arquivo principal
            arquivoPrincipal.delete();
            // Renomeia o arquivo temporário para o nome do arquivo principal
            arquivoTemporario.renameTo(arquivoPrincipal);
        }
    }

    @Override
    public Veiculos buscarPlaca(String placa) throws Exception {
        try {
            // Abre o arquivo para leitura
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            // Lê cada linha do arquivo
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String placaAUX = vetorStr[0];
                String km = vetorStr[1];
                String categoria = vetorStr[2];
                String combustivel = vetorStr[3];
                String proprietario = vetorStr[4];
                String modelo = vetorStr[5];
                Veiculos objetoVeiculos = new Veiculos(placaAUX, km, categoria, combustivel, proprietario, modelo);
                // Verifica se a placa corresponde à placa buscada
                if (placa.equals(placaAUX)) {
                    br.close();
                    return objetoVeiculos;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            String msg = "Método buscarPlaca - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<Veiculos> listagemDeVeiculos() throws Exception {
        ArrayList<Veiculos> lista = new ArrayList();
        BufferedReader br = null;
        try {
            Veiculos veiculo;
            // Abre o arquivo para leitura
            br = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
            String linha = "";
            // Lê cada linha do arquivo
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String placa = vetorStr[0];
                String km = vetorStr[1];
                String combustivel = vetorStr[2];
                String categoria = vetorStr[3];
                String proprietario = vetorStr[4];
                String modelo = vetorStr[5];
                veiculo = new Veiculos(placa, km, categoria, combustivel, proprietario, modelo);

                lista.add(veiculo);
            }
            return lista;
        } catch (Exception erro) {
            String msg = "Metodo listagemDeVeiculos - " + erro.getMessage();
            throw new Exception(msg);
        } finally {
            // Fecha o arquivo
            if (br != null) {
                br.close();
            }
        }     
    } 
}
