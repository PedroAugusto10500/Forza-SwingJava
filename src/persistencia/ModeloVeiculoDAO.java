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
import java.io.IOException;
import java.util.ArrayList;
import modelos.IModeloVeiculoCRUD;
import modelos.Marca;
import modelos.ModeloVeiculo;
import utilitarios.ManipularImagem;


public class ModeloVeiculoDAO implements IModeloVeiculoCRUD {
    private String nomeDoArquivo = null; // Nome do arquivo de armazenamento dos dados
    private String nomeDoArquivoTemporario = null; // Nome do arquivo temporário para alterações

    /**
     * Construtor da classe.
     * Define os nomes dos arquivos utilizados para armazenamento.
     */
    public ModeloVeiculoDAO() {
        nomeDoArquivo = "./src/bancodedados/ModeloBD.csv";
        nomeDoArquivoTemporario = "./src/bancodedados/ModeloBD-temp.csv";
    }

    
    // Inclui um objeto ModeloVeiculo no arquivo de armazenamento.
     
    @Override
    public void incluir(ModeloVeiculo objModelo) throws Exception {
        try {
            // Cria o arquivo FileWriter para escrever no arquivo
            FileWriter fw = new FileWriter(nomeDoArquivo, true);
            // Cria o buffer do arquivo para escrever no arquivo de forma mais eficiente
            BufferedWriter bw = new BufferedWriter(fw);
            // Escreve no arquivo a representação em string do objeto ModeloVeiculo, seguida de uma nova linha
            bw.write(objModelo.toString() + "\n");
            // Fecha o arquivo
            bw.close();
        } catch (Exception erro) {
            String msg = "Metodo Incluir Contato - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    
    // Retorna uma lista de todos os objetos ModeloVeiculo armazenados no arquivo.
     
    @Override
    public ArrayList<ModeloVeiculo> listagemModelos() throws Exception {
        ArrayList<ModeloVeiculo> modelos = new ArrayList<>();
        BufferedReader br = null;
        try {
            ModeloVeiculo modelo;
            br = new BufferedReader(new FileReader(nomeDoArquivo));
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String id = vetorStr[0];
                String descricao = vetorStr[1];
                String comboMarca = vetorStr[2];
                String imagemModelo = vetorStr[3];
                modelo = new ModeloVeiculo(id, descricao, comboMarca, imagemModelo);
                modelos.add(modelo);
            }
            return modelos;
        } catch (IOException erro) {
            String msg = "Método listagemDeMarcas - " + erro.getMessage();
            throw new Exception(msg);
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    
    // Busca um objeto ModeloVeiculo no arquivo pelo seu ID.
     
    @Override
    public ModeloVeiculo buscarPorID(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivo);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String idAux = vetorStr[0];
                String descricao = vetorStr[1];
                String comboMarca = vetorStr[2];
                String imagemModelo = vetorStr[3];
                ModeloVeiculo objetoModelo = new ModeloVeiculo(idAux, descricao, comboMarca, imagemModelo);
                if (id.equals(idAux)) {
                    br.close();
                    return objetoModelo;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            String msg = "Método buscar - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    // Altera um objeto ModeloVeiculo no arquivo.
    
    @Override
    public void alterar(int indexModeloAtual, ModeloVeiculo objModelo) throws Exception {
        BufferedReader brArquivoPrincipal = null;
        BufferedWriter bwArquivoTemporario = null;

        File arquivoTemporario = new File(nomeDoArquivoTemporario);
        File arquivoPrincipal = new File(nomeDoArquivo);

        try {
            brArquivoPrincipal = new BufferedReader(new FileReader(nomeDoArquivo));
            bwArquivoTemporario = new BufferedWriter(new FileWriter(nomeDoArquivoTemporario, true));

            String linha = "";
            int index = 0;

            while ((linha = brArquivoPrincipal.readLine()) != null) {
                if (index == indexModeloAtual) {
                    // Se é o modelo atual, escreve o novo modelo
                    bwArquivoTemporario.write(objModelo.toString() + "\n");
                    System.out.println(indexModeloAtual);
                    System.out.println(objModelo.toString());
                } else {
                    // Senão, escreve do jeito que estava antes
                    bwArquivoTemporario.write(linha + "\n");
                }
                // Limpa o buffer e escreve as alterações no arquivo temporário
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

            // Exclui o arquivo principal e renomeia o arquivo temporário para o nome do arquivo principal
            arquivoPrincipal.delete();
            arquivoTemporario.renameTo(arquivoPrincipal);
        }
    }
}
