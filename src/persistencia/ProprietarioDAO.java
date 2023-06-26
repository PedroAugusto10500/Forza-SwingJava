/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import modelos.Proprietario;
import utilitarios.ManipularImagem;
import modelos.IProprietarioCRUD;

/**
 *
 * @author TGAMER
 */
public class ProprietarioDAO implements IProprietarioCRUD {

    //Atributos
    private String nomeDoArquivoNoDisco = null;
    private String nomeDoArquivoTemporario = null;
    
    public ProprietarioDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedados/contatosBD.csv";
        nomeDoArquivoTemporario = "./src/bancodedados/contatosBD-temp.csv";
    }
    
    @Override
    public void incluir(Proprietario objContato) throws Exception {
        try {


            //cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            //Escreve no arquivo
            bw.write(objContato.toString() + "\n");
            //fecha o arquivo
            bw.close();
            
        } catch (Exception erro) {
            String msg = "Metodo Incluir Contato - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
    
    @Override
    public void alterar(int indexContatoAtual, Proprietario objContato) throws Exception {
        BufferedReader brArquivoPrincipal = null;
        BufferedWriter bwArquivoTemporario = null;
        
        File arquivoTemporario = new File(nomeDoArquivoTemporario);
        File arquivoPrincipal = new File(nomeDoArquivoNoDisco);
        
        try {
            brArquivoPrincipal = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
            bwArquivoTemporario = new BufferedWriter(new FileWriter(nomeDoArquivoTemporario, true));
            
            String linha = "";
            int index = 0;
            //Proprietario contato;
            while ((linha = brArquivoPrincipal.readLine()) != null) {               

                if (index == indexContatoAtual) {
                    // se e o cpf atual, escreve o novo contato
                    bwArquivoTemporario.write(objContato.toString() + "\n");
                    
                    System.out.println(indexContatoAtual);
                    System.out.println(objContato.toString());
                } else { // senao, escreve do jeito que estava antes
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
    
    @Override
    public Proprietario buscar(String cpf) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String cpfAux = vetorStr[0];
                String nomeCompleto = vetorStr[1];
                String email = vetorStr[2];
                String telefone = vetorStr[3];
                String cnh = vetorStr[4];
                String categoria = vetorStr[5];
                String imagemPessoal = vetorStr[6];
                String imagemCnh = vetorStr[7];
                Proprietario objetoContato = new Proprietario(cpfAux, nomeCompleto, email, telefone, cnh, categoria, imagemPessoal, imagemCnh);
                if (cpf.equals(cpfAux)) {
                    br.close();
                    return objetoContato;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            String msg = "Metodo buscar Contato - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
    
    @Override
    public ArrayList<Proprietario> listagemDeContatos() throws Exception {
        ArrayList<Proprietario> agenda = new ArrayList();
        BufferedReader br = null;
        try {
            Proprietario contato;
            br = new BufferedReader(new FileReader(nomeDoArquivoNoDisco));
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String cpf = vetorStr[0];
                String nomeCompleto = vetorStr[1];
                String email = vetorStr[2];
                String telefone = vetorStr[3];
                String cnh = vetorStr[4];
                String categoria = vetorStr[5];
                String imagemPessoal = vetorStr[6];
                String imagemCnh = vetorStr[7];
                contato = new Proprietario(cpf, nomeCompleto, email, telefone, cnh, categoria, imagemPessoal, imagemCnh );

                agenda.add(contato);
            }
            return agenda;
        } catch (Exception erro) {
            String msg = "Metodo listagemDeContatos Contato - " + erro.getMessage();
            throw new Exception(msg);
        } finally {
            if (br != null) {
                br.close();
            }
        }
        
    }
    



}
