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
import modelos.IMarcaCRUD;
import modelos.Marca;
import modelos.Proprietario;
import utilitarios.ManipularImagem;


public class MarcaDAO implements IMarcaCRUD {

    private String nomeDoArquivo = null;
    private String nomeDoArquivoTemporario = null;
    
    public MarcaDAO() {
        // Define o nome do arquivo de dados e o arquivo temporário utilizado nas operações de alteração
        nomeDoArquivo = "./src/bancodedados/MarcaBD.csv";
        nomeDoArquivoTemporario = "./src/bancodedados/MarcaBD-temp.csv";
    }

    // Inclui uma nova marca no arquivo de dados.
    @Override
    public void incluir(Marca objMarca) throws Exception {
        try {
            // Cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivo, true);
            // Cria o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            // Escreve no arquivo a representação da marca em formato de string
            bw.write(objMarca.toString() + "\n");
            // Fecha o arquivo
            bw.close();
        } catch (Exception erro) {
            String msg = "Método Incluir - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    
    // Retorna uma lista de todas as marcas presentes no arquivo de dados.
     
    @Override
    public ArrayList<Marca> listagemDeMarcas() throws Exception {
        ArrayList<Marca> marcas = new ArrayList<>();
        BufferedReader br = null;
        try {
            Marca marca;
            br = new BufferedReader(new FileReader(nomeDoArquivo));
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String id = vetorStr[0];
                String descricao = vetorStr[1];
                String imagemLogoMarca = vetorStr[2];
                marca = new Marca(id, descricao, imagemLogoMarca);
                marcas.add(marca);
            }
            return marcas;
        } catch (IOException erro) {
            String msg = "Método listagemDeMarcas - " + erro.getMessage();
            throw new Exception(msg);
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    
    // Altera uma marca no arquivo de dados.
    @Override
    public void alterar(int indexMarcaAtual, Marca objMarca) throws Exception {
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
                if (index == indexMarcaAtual) {
                    // Se é a marca atual, escreve a nova marca no arquivo temporário
                    bwArquivoTemporario.write(objMarca.toString() + "\n");
                    System.out.println(indexMarcaAtual);
                    System.out.println(objMarca.toString());
                } else {
                    // Senão, escreve a linha atual do jeito que estava antes no arquivo temporário
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
            
            // Substitui o arquivo principal pelo arquivo temporário
            arquivoPrincipal.delete();
            arquivoTemporario.renameTo(arquivoPrincipal);
        }
    }

   
    // Busca uma marca por seu ID no arquivo de dados.
    @Override
    public Marca buscarPorID(String id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivo);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String idAux = vetorStr[0];
                String descricao = vetorStr[1];
                String imagemLogoMarca = vetorStr[2];
                Marca objetoMarca = new Marca(idAux, descricao, imagemLogoMarca);
                if (id.equals(idAux)) {
                    br.close();
                    return objetoMarca;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            String msg = "Método buscar - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
}
