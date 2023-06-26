/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelos.Proprietario;
import persistencia.ProprietarioDAO;
import utilitarios.ValidacaoExcecao;
import static utilitarios.ValidacaoExcecao.cpfIsValido;
import modelos.IProprietarioCRUD;

/**
 *
 * @author Pedro Augusto
 */

public class ProprietarioControle implements IProprietarioCRUD {

    //Atributos
    IProprietarioCRUD contatoPersistencia = null;
    ValidarEmail validaEmail = new ValidarEmail();
    
    

    public ProprietarioControle() {
        contatoPersistencia = new ProprietarioDAO();
    }

    @Override
public void alterar(int indexContatoAtual, Proprietario objContato) throws Exception {
    try {
       
        if (!ValidacaoExcecao.validarTelefone(objContato.getTelefone())) {
            throw new Exception("Telefone inválido!!");
        }
      

        // Validando Nome Completo
        if (!objContato.getNomeCompleto().contains(" ")) {
            throw new Exception("Nome inválido!!");
        }
        if (objContato.getNomeCompleto().substring(0, objContato.getNomeCompleto().indexOf(" ")).length() < 2
                || objContato.getNomeCompleto().substring(objContato.getNomeCompleto().lastIndexOf(" ") + 1)
                        .length() < 2) {
            throw new Exception("O contato na agenda deve ter nome e sobrenome");
        }
        if (!objContato.getNomeCompleto().matches("^[\\p{L} ]+$")) {
            throw new Exception("O nome completo deve conter apenas letras");
        }

        // Validar email
        if (!validaEmail.validate(objContato.getEmail())) {
            throw new Exception("Email inválido!!");
        }

        
        contatoPersistencia.alterar(indexContatoAtual, objContato);
    } catch (Exception e) {
        // Aqui você pode lidar com a exceção de alguma forma adequada,
        // como exibir uma mensagem de erro, registrar o erro em um log, etc.
        throw e; // Lança novamente a exceção para propagar o erro.
    }
}
    public class ValidarEmail {

        private Pattern pattern;
        private Matcher matcher;

        private final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@([\\da-zA-Z-]+\\.)+[a-zA-Z]{2,6}$";

        public ValidarEmail() {
            pattern = Pattern.compile(EMAIL_PATTERN);
        }

        public boolean validate(String email) {
            matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
    




@Override
public void incluir(Proprietario objContato) throws Exception {
    try {
        // validando CPF
        if (contatoPersistencia.buscar(objContato.getCpf()) != null) {
            throw new Exception("Já existe este CPF em sua agenda!!");
        }
        if (!ValidacaoExcecao.validarTelefone(objContato.getTelefone())) {
            throw new Exception("Telefone inválido!!");
        }
        if (!cpfIsValido(objContato.getCpf())) {
            throw new Exception("CPF inválido!!");
        }

        // Validando Nome Completo 
        if (!objContato.getNomeCompleto().contains(" ")) {
            throw new Exception("Nome inválido!!");
        }
        if (objContato.getNomeCompleto().substring(0, objContato.getNomeCompleto().indexOf(" ")).length() < 2 || objContato.getNomeCompleto().substring(objContato.getNomeCompleto().lastIndexOf(" ") + 1).length() < 2) {
            throw new Exception("O contato na agenda deve ter nome e sobrenome");
        }
        if (!objContato.getNomeCompleto().matches("^[\\p{L} ]+$")) {
            throw new Exception("O nome completo deve conter apenas letras");
        }

        // Validar email
        if (!validaEmail.validate(objContato.getEmail())) {
            throw new Exception("Email inválido!!");
        }

        // Validar Cnh
       

        // Adicionar o obj.
        contatoPersistencia.incluir(objContato);

        // Exibir mensagem de usuário incluído
        JOptionPane.showMessageDialog(null, "Usuário incluído com sucesso!");

    } catch (Exception erro) {
        String msg = "Método listagemDeContatos Contato - " + erro.getMessage();
        throw new Exception(msg);
    }
}




    @Override
    public Proprietario buscar(String cpf) throws Exception {
        //instancia com com referencia a contato e verifica passando parametro CPF
        Proprietario contato = contatoPersistencia.buscar(cpf);
        //Verifica se o campo cpf esta vazio, caso esteja retorna para preencher o campo
        if (cpf.equals("")) {
            throw new Exception("Preencha o campo CPF para buscar o contato!");
        }
        //verifica se retorno do contato esta null, caso esteja null -> retorna contato inexistente;
        if (contato == null) {
            throw new Exception("Contato inexistente!");
        }
        //se passar pela condição ele retorna o contato;
        return contato;
    }

    @Override
    public ArrayList<Proprietario> listagemDeContatos() throws Exception {
        //retorna arraylist de contatos
        return contatoPersistencia.listagemDeContatos();

    }



}
