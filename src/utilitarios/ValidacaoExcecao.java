/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitarios;

/**
 *
 * @author T-Gamer
 */
public class ValidacaoExcecao {

    public static boolean cpfIsValido(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            return false;
        }
        if (digitosIguais(cpf)) {
            return false;
        }
        char digito10 = getPrimeiroDigitoVerificadorCNPJ(cpf);
        char digito11 = getSegundoDigitoVerificadorCPF(cpf);

        return ((digito10 == cpf.charAt(9)) && (digito11 == cpf.charAt(10)));
    }

    private static char getPrimeiroDigitoVerificadorCNPJ(String cpf) {
        char digitoVerificado;

        int soma = 0;
        int peso = 10;
        int numero = 0;

        // (48 eh a posicao de '0' na tabela ASCII)
        for (int i = 0; i < 9; i++) {
            numero = (int) (cpf.charAt(i) - 48);
            soma += numero * peso;
            peso--;
        }

        int resultado = 11 - (soma % 11);
        if (resultado == 10 || resultado == 11) {
            digitoVerificado = '0';
        } else {
            digitoVerificado = (char) (resultado + 48); // converte no respectivo caractere numerico
        }
        return digitoVerificado;
    }

    private static char getSegundoDigitoVerificadorCPF(String cpf) {
        char digitoVerificador;
        int soma = 0;
        int peso = 11;
        int numero;

        for (int i = 0; i < 10; i++) {
            numero = (int) (cpf.charAt(i) - 48);
            soma += (numero * peso);
            peso--;
        }

        int resultado = 11 - (soma % 11);
        if ((resultado == 10) || (resultado == 11)) {
            digitoVerificador = '0';
        } else {
            digitoVerificador = (char) (resultado + 48);
        }
        return digitoVerificador;
    }

    private static boolean digitosIguais(String string) {
        return string.equals("00000000000") || string.equals("11111111111")
                || string.equals("22222222222") || string.equals("33333333333")
                || string.equals("44444444444") || string.equals("55555555555")
                || string.equals("66666666666") || string.equals("77777777777")
                || string.equals("88888888888") || string.equals("99999999999");
    }

    public boolean validarCNH(String cnh) {
        // Regex para validar CNH (formato AAA000000000)
        String regexCNH = "^[A-Z]{3}\\d{9}$";

        return cnh.matches(regexCNH);
    }

    public static boolean validarTelefone(String telefone) {
        //Baseado no original para javascript:

        //retira todos os caracteres não-numéricos (incluindo espaço,tab, etc)
        telefone = telefone.replaceAll("\\D", "");


        //Se tiver 11 caracteres, verificar se começa com 9 o celular
        if (telefone.length() == 11 && Integer.parseInt(telefone.substring(4, 5)) != 9) {
            return false;
        }

        //verifica se o numero foi digitado com todos os dígitos iguais
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(telefone.charAt(0) + "{" + telefone.length() + "}");
        java.util.regex.Matcher m = p.matcher(telefone);
        if (m.find()) {
            return false;
        }

        //DDDs validos
        Integer[] codigosDDD = {
            11, 12, 13, 14, 15, 16, 17, 18, 19,
            21, 22, 24, 27, 28, 31, 32, 33, 34,
            35, 37, 38, 41, 42, 43, 44, 45, 46,
            47, 48, 49, 51, 53, 54, 55, 61, 62,
            64, 63, 65, 66, 67, 68, 69, 71, 73,
            74, 75, 77, 79, 81, 82, 83, 84, 85,
            86, 87, 88, 89, 91, 92, 93, 94, 95,
            96, 97, 98, 99};
        //verifica se o DDD é valido (sim, da pra verificar rsrsrs)
        if (java.util.Arrays.asList(codigosDDD).indexOf(Integer.parseInt(telefone.substring(2, 4))) == -1) {
            return false;
        }

        //se passar por todas as validações acima, então está tudo certo
        return true;
    }
    
    
private boolean validarPlaca(String placa) {
    // Verificar se a placa está no formato do padrão Mercosul
    String regexMercosul = "^[A-Z]{3}\\d[A-Z]{1}[A-Z\\d]{1}[A-Z\\d]{2}$";
    if (placa.matches(regexMercosul)) {
        return true;
    }

    // Verificar se a placa está no formato do padrão antigo
    String regexPadraoAntigo = "^[A-Z]{3}-\\d{4}$";
    if (placa.matches(regexPadraoAntigo)) {
        return true;
    }

    return false;
}
}
