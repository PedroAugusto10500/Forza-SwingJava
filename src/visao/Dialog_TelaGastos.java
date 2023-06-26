/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package visao;

import controle.DespesaControle;
import controle.GastosControle;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.Despesa;
import modelos.Gastos;
import modelos.IDespesas;
import modelos.IGastosCRUD;
import modelos.Proprietario;

/**
 *
 * @author Aluno
 */
public class Dialog_TelaGastos extends javax.swing.JDialog {

    private final String nomeDoArquivoNoDisco;
    private final String nomeDoArquivoModeloGastos;

    //Atributos
    IDespesas despesasControle = null;
    IGastosCRUD gastosControle = null;
    DefaultTableModel modeloGastoTotal;

    Scanner nc = null;
    Proprietario gasto = null;
    int indexContatoEmEdicao = -1;

    //Construtor
    public Dialog_TelaGastos(java.awt.Frame parent, boolean modal) throws Exception {
        super(parent, modal);
        initComponents();
        despesasControle = new DespesaControle();
        gastosControle = new GastosControle();
        modeloGastoTotal = (DefaultTableModel) jTableGastosTotal.getModel();
        nomeDoArquivoNoDisco = "./src/bancodedados/contatosBD.csv";
        nomeDoArquivoModeloGastos = "./src/bancodedados/veiculosBD.csv";

        ComboBoxModeloGastos();
        MostrarDadosGastos();
        //MostrarGastosMensal();
        MostrarGastoAnual();
        MostrarDespesaMensal();
        MostrarGastoAnual();
        MostrarCalculosMes(nomeDoArquivoNoDisco, nomeDoArquivoNoDisco, modeloGastoTotal);
        
    }

    // Criar gastos
    private Gastos criarGastos() throws Exception {
        return new Gastos(
                jFormattedTextFielID.getText(),
                jFormattedTextField_DATAGASTO.getText(),
                (String) jComboBoxVEICULOGastos.getSelectedItem(),
                jTextField_Descrição.getText(),
                (String) jComboBoxCategoriaGastos.getSelectedItem(),
                jFormattedTextFieldValor.getText()
        );
    }
//Mostrar dados na Tabela Geral
    private void MostrarDadosGastos() {
        try {
            ArrayList<Gastos> listaDeGastos = gastosControle.listagemDeGastos();
            DefaultTableModel modeloGastoTotal = (DefaultTableModel) jTableGastosTotal.getModel();
            modeloGastoTotal.setRowCount(0);

            for (Gastos gasto : listaDeGastos) {
                String[] linha = new String[7];
                linha[0] = gasto.getId();
                linha[1] = gasto.getData();
                linha[2] = gasto.getVeiculo();
                linha[3] = gasto.getDescricao();
                linha[4] = gasto.getCategoria();
                linha[5] = gasto.getValor();
                modeloGastoTotal.addRow(linha);
            }

            System.out.println("Lista De Gastos: " + listaDeGastos);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(rootPane, erro.getMessage());
        }
    }

// private void MostrarCalculosMes(String veiculo, String ano, String mes, DefaultTableModel model) {
//    try {
//       List<Gastos> listaCalculos = gastosControle.buscar(veiculo, mes, ano);
//
//        for (Gastos gasto : listaCalculos) {
//            String[] linha = new String[3];
//            //linha[0] = gasto.getId();
//            linha[0] = gasto.getData();
//           // linha[1] = gasto.getVeiculo();
//            linha[1] = gasto.getCategoria();
//            linha[2] = gasto.getValor();
//            model.addRow(linha);
//        }
//        System.out.println("Valor total dos gastos do proprietário " + veiculo + mes + ano);
//    } catch (Exception erro) {
//        JOptionPane.showMessageDialog(rootPane, erro.getMessage());
//    }
//}
    
    //Mostra o Resultado do Filtro da Tabela Gasto Mensal 
    private void MostrarCalculosMes(String mes, String ano, DefaultTableModel model) {
        try {
            ArrayList<Despesa> despesaMensal = despesasControle.despesaMensal();// Obtém uma lista de despesas mensais
            DefaultTableModel modeloGastoTotal = (DefaultTableModel) jTable1TableMensal.getModel();// Obtém o modelo da tabela
            modeloGastoTotal.setRowCount(0);// Limpa as linhas do modelo da tabela

            for (Despesa gasto : despesaMensal) {// Verifica se o mês e ano da despesa correspondem aos parâmetros fornecidos
                String mesAnoGasto = gasto.getMesAno(); // Obtém o mês e ano da despesa no formato "MM/AAAA"
                String[] partes = mesAnoGasto.split("/");// Divide o mês e ano em partes separadas
                String mesGasto = partes[0];// Obtém o mês da despesa
                String anoGasto = partes[1]; // Obtém o ano da despesa

                if (mesGasto.equals(mes) && anoGasto.equals(ano)) {
                    String[] linha = new String[7];// Cria um array para armazenar os dados da linha da tabela
                    linha[0] = gasto.getMesAno();// Define o mês e ano na primeira coluna da linha
                    linha[1] = gasto.getCategoria();// Define a categoria na segunda coluna da linha
                    linha[2] = gasto.getValor();// Define o valor na terceira coluna da linha
                    modeloGastoTotal.addRow(linha);// Adiciona a linha ao modelo da tabela
                }
            }

            System.out.println("Lista De Gastos: " + despesaMensal);// Imprime a lista de despesas mensais no console
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(rootPane, erro.getMessage());// Exibe uma mensagem de erro em uma caixa de diálogo
        }
    }

    //Mostrar Todos os Gastos Mensais na Tabela 
//    private void MostrarGastosMensal() {
//        try {
//            ArrayList<Gastos> listaDeGastos = gastosControle.gastoMensal();
//            DefaultTableModel modeloGastoTotal = (DefaultTableModel) jTable1TableMensal.getModel();
//            modeloGastoTotal.setRowCount(0);
//
//            for (Gastos gasto : listaDeGastos) {
//                String[] linha = new String[3]; // Alterado de 7 para 6 para remover a coluna "Veículo"
//                linha[0] = gasto.getData();
//                // linha[] = gasto.getVeiculo(); // Comentado para remover a exibição do veículo na tabela
//                // linha[2] = gasto.getDescricao();
//                linha[1] = gasto.getCategoria();
//                linha[2] = gasto.getValor();
//                modeloGastoTotal.addRow(linha);
//            }
//
//            System.out.println("Lista De Gastos: " + listaDeGastos);
//        } catch (Exception erro) {
//            JOptionPane.showMessageDialog(rootPane, erro.getMessage());
//        }
//    }

    // Mostrar todod os valores do Relatório Mensal 
    
    private void MostrarDespesaMensal() {
        try {
            ArrayList<Despesa> despesaMensal = despesasControle.despesaMensal();
            DefaultTableModel modeloGastoTotal = (DefaultTableModel) jTable1TableMensal.getModel();
            modeloGastoTotal.setRowCount(0);

            for (Despesa gasto : despesaMensal) {
                String[] linha = new String[7];
                // linha[0] = gasto.getId();
                linha[0] = gasto.getMesAno();
                // linha[1] = gasto.getVeiculo();
                //linha[2] = gasto.getDescricao();
                linha[1] = gasto.getCategoria();
                linha[2] = gasto.getValor();
                modeloGastoTotal.addRow(linha);
            }

            System.out.println("Lista De Gastos: " + despesaMensal);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(rootPane, erro.getMessage());
        }
    }

    private void MostrarGastoAnual() {
        try {
            ArrayList<Gastos> listaDeGastos = gastosControle.gastoAnual();
            DefaultTableModel modeloGastoTotal = (DefaultTableModel) jTable2TableAnual.getModel();
            modeloGastoTotal.setRowCount(0);

            for (Gastos gasto : listaDeGastos) {
                String[] linha = new String[7];
                linha[0] = gasto.getId();
                linha[1] = gasto.getData();
                linha[2] = gasto.getVeiculo();
                linha[3] = gasto.getDescricao();
                linha[4] = gasto.getCategoria();
                linha[5] = gasto.getValor();
                modeloGastoTotal.addRow(linha);
            }

            System.out.println("Lista De Gastos: " + listaDeGastos);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(rootPane, erro.getMessage());
        }
    }

    //Filtro Tabela Anual 
    private void MostrarCalculosAnual(String veiculo, String ano, String mes, DefaultTableModel model) {
        try {
            ArrayList<Gastos> listaCalculos = (ArrayList<Gastos>) gastosControle.buscar(veiculo, mes, ano);

            for (Gastos gasto : listaCalculos) {
                Object[] linha = new Object[7];
                linha[0] = gasto.getId();
                linha[1] = gasto.getData();
                linha[2] = gasto.getVeiculo();
                linha[3] = gasto.getDescricao();
                linha[4] = gasto.getCategoria();
                linha[5] = gasto.getValor();
                model.addRow(linha);
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(rootPane, erro.getMessage());
        }
    }

    // Aqui você coloca a classe CategoriaGastosRenderer
    public class CategoriaGastosRenderer extends DefaultListCellRenderer {

        private Map<String, ImageIcon> imagemPorCategoria;

        public CategoriaGastosRenderer() {
            imagemPorCategoria = new HashMap<>();
            imagemPorCategoria.put("Combustivel", new ImageIcon("C:\\Users\\Antonio\\Documents\\NetBeansProjects\\Teste\\src\\icones\\gasolina.png"));// Mapeia a categoria com a imagem correspondente
            imagemPorCategoria.put("Manutenção", new ImageIcon("C:\\Users\\Antonio\\Documents\\NetBeansProjects\\Teste\\src\\icones\\servico-automotivo.png"));
            imagemPorCategoria.put("Seguro", new ImageIcon("C:\\Users\\Antonio\\Documents\\NetBeansProjects\\Teste\\src\\icones\\protecao.png"));
            imagemPorCategoria.put("Imposto", new ImageIcon("C:\\Users\\Antonio\\Documents\\NetBeansProjects\\Teste\\src\\icones\\car-loan.png"));
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Configurar o texto do item
            renderer.setText(value.toString());

            // Obter a imagem correspondente à categoria
            ImageIcon imagemIcon = imagemPorCategoria.get(value.toString());

            // Adicionar a imagem ao lado do texto
            renderer.setIcon(imagemIcon);
            renderer.setIconTextGap(10); // Definir a distância entre a imagem e o texto

            return renderer;
        }
    }

// No construtor da classe ou em um método de inicialização
    //Limpar Campos Modelo do Veiculo
    private void limparCamposGastos() {
        jTextField_Descrição.setText("");
        jFormattedTextFielID.setText("");
        jFormattedTextField_DATAGASTO.setText("");
        jFormattedTextFieldValor.setText("");

        jComboBoxCategoriaGastos.setSelectedItem(0);
        jComboBoxVEICULOGastos.setSelectedItem(0);
    }

    public void ComboBoxModeloGastos() throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoModeloGastos);
            try (BufferedReader br = new BufferedReader(fr)) {
                String linha = "";

                // Remove os itens antigos
                removeItemsFromComboBoxModeloGastos();

                while ((linha = br.readLine()) != null) {
                    String vetorStr[] = linha.split(";");
                    String descricao = vetorStr[0];

                    // Verifica se o item já existe no modelo da combobox
                    boolean itemExistente = false;
                    for (int i = 0; i < jComboBoxVEICULOGastos.getItemCount(); i++) {
                        String item = (String) jComboBoxVEICULOGastos.getItemAt(i);
                        if (item.equals(descricao)) {
                            itemExistente = true;
                            break;
                        }
                    }

                    // Adiciona o item apenas se ele não existir no modelo
                    if (!itemExistente) {
                        jComboBoxVEICULOGastos.addItem(descricao);
                    }
                }
            }
        } catch (IOException erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage(), "Método comboBox", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeItemsFromComboBoxModeloGastos() {
        jComboBoxVEICULOGastos.removeAllItems();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextField_Descrição = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jComboBoxCategoriaGastos = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jFormattedTextFielID = new javax.swing.JTextField();
        jTextFieldParaAdicionarNaCombo = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButtonAdicionarNacomboCategoria = new javax.swing.JButton();
        jFormattedTextField_DATAGASTO = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jComboBoxVEICULOGastos = new javax.swing.JComboBox<>();
        jFormattedTextFieldValor = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableGastosTotal = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1TableMensal = new javax.swing.JTable();
        jTextFieldMes = new javax.swing.JTextField();
        jTextFieldANUAL = new javax.swing.JTextField();
        jButtonCalculoMensal = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2TableAnual = new javax.swing.JTable();
        jTextFieldAno = new javax.swing.JTextField();
        jButtonAnual = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1_ATELRARGASTOS = new javax.swing.JButton();
        jButton1_CALCULARTOTAL = new javax.swing.JButton();
        jButton1_CALCULARTOTAL1 = new javax.swing.JButton();
        jButton1_CALCULARTOTAL2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFocusable(false);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Data:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 45, 15));

        jLabel6.setText("_____________________________________");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 190, -1));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 308, -1, -1));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_info.png"))); // NOI18N
        jLabel20.setToolTipText("Digite a data!");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, -1, -1));

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Tipo De Gastos"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setText("________________________________________");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 200, -1));

        jTextField_Descrição.setBackground(new java.awt.Color(102, 102, 102));
        jTextField_Descrição.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField_Descrição.setBorder(null);
        jTextField_Descrição.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_DescriçãoActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField_Descrição, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 190, 20));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Descrição:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 80, 20));

        jLabel19.setText("________________________________________");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, 180, 20));

        jComboBoxCategoriaGastos.setBackground(new java.awt.Color(53, 53, 53));
        jComboBoxCategoriaGastos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Combustivel", "Manutenção", "Seguro", "Imposto" }));
        jComboBoxCategoriaGastos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Categoria:"));
        jComboBoxCategoriaGastos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBoxCategoriaGastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategoriaGastosActionPerformed(evt);
            }
        });
        jPanel4.add(jComboBoxCategoriaGastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 350, 60));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("ID:");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 45, 15));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_info.png"))); // NOI18N
        jLabel14.setToolTipText("Digite um ID!");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_info.png"))); // NOI18N
        jLabel12.setToolTipText("Digite a descrição do Gasto!");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, -1, -1));

        jFormattedTextFielID.setBackground(new java.awt.Color(102, 102, 102));
        jFormattedTextFielID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFormattedTextFielID.setBorder(null);
        jPanel4.add(jFormattedTextFielID, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 200, -1));

        jTextFieldParaAdicionarNaCombo.setBackground(new java.awt.Color(102, 102, 102));
        jTextFieldParaAdicionarNaCombo.setBorder(null);
        jPanel4.add(jTextFieldParaAdicionarNaCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, 170, 20));

        jLabel38.setText("________________________________________");
        jPanel4.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 200, -1));

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel1.setText("Nova categoria:");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 100, -1));

        jButtonAdicionarNacomboCategoria.setBackground(new java.awt.Color(102, 102, 102));
        jButtonAdicionarNacomboCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_incluir icon.png"))); // NOI18N
        jButtonAdicionarNacomboCategoria.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAdicionarNacomboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarNacomboCategoriaActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonAdicionarNacomboCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 40, 40));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 800, 230));

        jFormattedTextField_DATAGASTO.setBackground(new java.awt.Color(102, 102, 102));
        jFormattedTextField_DATAGASTO.setBorder(null);
        try {
            jFormattedTextField_DATAGASTO.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField_DATAGASTO.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFormattedTextField_DATAGASTO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField_DATAGASTOActionPerformed(evt);
            }
        });
        jPanel2.add(jFormattedTextField_DATAGASTO, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 180, 20));

        jLabel13.setText("_____________________________________");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 190, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Valor:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 45, 15));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_info.png"))); // NOI18N
        jLabel18.setToolTipText("Escolha o Veiculo!");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, -1, -1));

        jComboBoxVEICULOGastos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Veiculo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 1, 12))); // NOI18N
        jComboBoxVEICULOGastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxVEICULOGastosActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBoxVEICULOGastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 300, 40));

        jFormattedTextFieldValor.setBackground(new java.awt.Color(102, 102, 102));
        jFormattedTextFieldValor.setBorder(null);
        jPanel2.add(jFormattedTextFieldValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 95, 190, 20));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_info.png"))); // NOI18N
        jLabel21.setToolTipText("Digite o valor de Gasto!");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1260, 250));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/Controle Gastos.png"))); // NOI18N
        jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 100));

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setLayout(null);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/whatsapp (1).png"))); // NOI18N
        jLabel22.setToolTipText("+55(62)99999-9999");
        jPanel5.add(jLabel22);
        jLabel22.setBounds(640, 20, 24, 24);

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/instagram (1).png"))); // NOI18N
        jLabel23.setToolTipText("@forza");
        jPanel5.add(jLabel23);
        jLabel23.setBounds(670, 20, 24, 24);

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/sinal-do-twitter.png"))); // NOI18N
        jLabel24.setToolTipText("@forza");
        jPanel5.add(jLabel24);
        jLabel24.setBounds(700, 20, 24, 24);

        jLabel29.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Desenvolvido por Forza ® Direitos Reservados.");
        jPanel5.add(jLabel29);
        jLabel29.setBounds(550, 50, 271, 17);

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));
        jPanel6.setLayout(null);

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/whatsapp (1).png"))); // NOI18N
        jLabel25.setToolTipText("+55(62)99999-9999");
        jPanel6.add(jLabel25);
        jLabel25.setBounds(640, 20, 24, 24);

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/instagram (1).png"))); // NOI18N
        jLabel26.setToolTipText("@forza");
        jPanel6.add(jLabel26);
        jLabel26.setBounds(670, 20, 24, 24);

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/sinal-do-twitter.png"))); // NOI18N
        jLabel27.setToolTipText("@forza");
        jPanel6.add(jLabel27);
        jLabel27.setBounds(700, 20, 24, 24);

        jLabel30.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Desenvolvido por Forza ® Direitos Reservados.");
        jPanel6.add(jLabel30);
        jLabel30.setBounds(550, 50, 271, 17);

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));
        jPanel7.setLayout(null);

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/whatsapp (1).png"))); // NOI18N
        jLabel28.setToolTipText("+55(62)99999-9999");
        jPanel7.add(jLabel28);
        jLabel28.setBounds(640, 20, 24, 24);

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/instagram (1).png"))); // NOI18N
        jLabel31.setToolTipText("@forza");
        jPanel7.add(jLabel31);
        jLabel31.setBounds(670, 20, 24, 24);

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/sinal-do-twitter.png"))); // NOI18N
        jLabel32.setToolTipText("@forza");
        jPanel7.add(jLabel32);
        jLabel32.setBounds(700, 20, 24, 24);

        jLabel33.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Desenvolvido por Forza ® Direitos Reservados.");
        jPanel7.add(jLabel33);
        jLabel33.setBounds(550, 50, 271, 17);

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/whatsapp (1).png"))); // NOI18N
        jLabel34.setToolTipText("+55(62)99999-9999");
        jPanel3.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/instagram (1).png"))); // NOI18N
        jLabel35.setToolTipText("@forza");
        jPanel3.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/sinal-do-twitter.png"))); // NOI18N
        jLabel36.setToolTipText("@forza");
        jPanel3.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Desenvolvido por Forza ® Direitos Reservados.");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 271, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 1260, 70));

        jPanel1.setBackground(new java.awt.Color(20, 20, 20));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableGastosTotal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Data", "Veiculo", "Descrição", "Categoria", "R$ Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableGastosTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableGastosTotalMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableGastosTotal);

        jPanel8.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 820, 150));

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho.png"))); // NOI18N
        jButton1.setText("Ocultar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 20));

        jTabbedPane1.addTab("GERAL", jPanel8);

        jPanel10.setBackground(new java.awt.Color(102, 102, 102));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1TableMensal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Categoria", "R$ Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1TableMensal);

        jPanel10.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 825, 130));

        jTextFieldMes.setBackground(new java.awt.Color(51, 51, 51));
        jTextFieldMes.setForeground(new java.awt.Color(204, 204, 204));
        jTextFieldMes.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Mes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 0, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jTextFieldMes.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextFieldMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMesActionPerformed(evt);
            }
        });
        jPanel10.add(jTextFieldMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        jTextFieldANUAL.setBackground(new java.awt.Color(51, 51, 51));
        jTextFieldANUAL.setForeground(new java.awt.Color(204, 204, 204));
        jTextFieldANUAL.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Ano", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel10.add(jTextFieldANUAL, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 150, 40));

        jButtonCalculoMensal.setBackground(new java.awt.Color(51, 51, 51));
        jButtonCalculoMensal.setForeground(new java.awt.Color(51, 51, 51));
        jButtonCalculoMensal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_buscar icon.png"))); // NOI18N
        jButtonCalculoMensal.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonCalculoMensal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCalculoMensalActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonCalculoMensal, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 30, 40));

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_voltarP.png"))); // NOI18N
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 30, 40));

        jTabbedPane1.addTab("MENSAL", jPanel10);

        jPanel11.setBackground(new java.awt.Color(102, 102, 102));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable2TableAnual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Data", "Veículo", "Descrição", "Categoria", "R$ Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2TableAnual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2TableAnualMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2TableAnual);

        jPanel11.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 825, 130));

        jTextFieldAno.setBackground(new java.awt.Color(51, 51, 51));
        jTextFieldAno.setForeground(new java.awt.Color(204, 204, 204));
        jTextFieldAno.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Ano", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 0, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jTextFieldAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAnoActionPerformed(evt);
            }
        });
        jPanel11.add(jTextFieldAno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 40));

        jButtonAnual.setBackground(new java.awt.Color(51, 51, 51));
        jButtonAnual.setForeground(new java.awt.Color(51, 51, 51));
        jButtonAnual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_buscar icon.png"))); // NOI18N
        jButtonAnual.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAnual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnualActionPerformed(evt);
            }
        });
        jPanel11.add(jButtonAnual, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 30, 40));

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_voltarP.png"))); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 30, 40));

        jTabbedPane1.addTab("ANUAL", jPanel11);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 830, 200));

        jButton1_ATELRARGASTOS.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1_ATELRARGASTOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_alterar icon.png"))); // NOI18N
        jButton1_ATELRARGASTOS.setText("ALTERAR");
        jButton1_ATELRARGASTOS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton1_ATELRARGASTOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_ATELRARGASTOSActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1_ATELRARGASTOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 180, 40));

        jButton1_CALCULARTOTAL.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1_CALCULARTOTAL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_voltarP.png"))); // NOI18N
        jButton1_CALCULARTOTAL.setText("   VOLTAR");
        jButton1_CALCULARTOTAL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton1_CALCULARTOTAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_CALCULARTOTALActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1_CALCULARTOTAL, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 180, 40));

        jButton1_CALCULARTOTAL1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1_CALCULARTOTAL1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_incluir icon.png"))); // NOI18N
        jButton1_CALCULARTOTAL1.setText("INCLUIR TOTAL");
        jButton1_CALCULARTOTAL1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton1_CALCULARTOTAL1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_CALCULARTOTAL1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1_CALCULARTOTAL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 40));

        jButton1_CALCULARTOTAL2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1_CALCULARTOTAL2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_buscar icon.png"))); // NOI18N
        jButton1_CALCULARTOTAL2.setText("BUSCAR");
        jButton1_CALCULARTOTAL2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton1_CALCULARTOTAL2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_CALCULARTOTAL2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1_CALCULARTOTAL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 170, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 1260, 220));

        jMenu1.setBorder(null);
        jMenu1.setForeground(new java.awt.Color(51, 51, 51));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/menu.png"))); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_voltarP.png"))); // NOI18N
        jMenuItem1.setText("MENU PRINCIPAL");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho.png"))); // NOI18N
        jMenuItem2.setText("SAIR");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_DescriçãoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_DescriçãoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_DescriçãoActionPerformed

    private void jComboBoxCategoriaGastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoriaGastosActionPerformed

        // Criar o renderizador personalizado
        CategoriaGastosRenderer renderer = new CategoriaGastosRenderer();

        // Definir o renderizador personalizado para o JComboBox de categoria de gastos
        jComboBoxCategoriaGastos.setRenderer(renderer);


    }//GEN-LAST:event_jComboBoxCategoriaGastosActionPerformed

    private void jButton1_ATELRARGASTOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_ATELRARGASTOSActionPerformed
        try {
            gastosControle.alterar(indexContatoEmEdicao, criarGastos());
            System.out.println(criarGastos());
            System.out.println(indexContatoEmEdicao);
            System.out.println(criarGastos());
            limparCamposGastos();
            MostrarDadosGastos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }


    }//GEN-LAST:event_jButton1_ATELRARGASTOSActionPerformed

    private void jButton1_CALCULARTOTALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_CALCULARTOTALActionPerformed
        try {
            jFormattedTextFielID.setEditable(true);
            jComboBoxVEICULOGastos.setEnabled(true);

            MostrarDadosGastos();
            MostrarCalculosAnual(nomeDoArquivoNoDisco, nomeDoArquivoNoDisco, nomeDoArquivoNoDisco, modeloGastoTotal);
            //MostrarCalculosMensal(nomeDoArquivoNoDisco, nomeDoArquivoNoDisco, nomeDoArquivoNoDisco, modeloGastoTotal);
            limparCamposGastos();
            System.out.println(gastosControle.listagemDeGastos());

            jTextFieldAno.setText("");
            jTextFieldMes.setText("");
            jTextFieldANUAL.setText("");
            ((DefaultTableModel) jTable1TableMensal.getModel()).setNumRows(0);
            ((DefaultTableModel) jTable2TableAnual.getModel()).setNumRows(0);// Limpar a tabela jTable1TableMensal
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }

    }//GEN-LAST:event_jButton1_CALCULARTOTALActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        TelaPrincipal Voltar = new TelaPrincipal();
        Voltar.setVisible(true);
        dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1_CALCULARTOTAL1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_CALCULARTOTAL1ActionPerformed
        MostrarDadosGastos();
        //MostrarGastosMensal();
        MostrarGastoAnual();
        MostrarDespesaMensal();
        

        if (jTextField_Descrição.getText().isBlank() || jFormattedTextFielID.getText().isBlank() || jFormattedTextField_DATAGASTO.getText().isBlank() || jFormattedTextFieldValor.getText().isBlank() || jComboBoxVEICULOGastos.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos para incluir o gasto!");
        } else {
            try {
                gastosControle.incluir(criarGastos());
                MostrarDadosGastos();
                limparCampos();
                System.out.println(criarGastos());
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(this, erro.getMessage());
            }
        }
    }

    private void limparCampos() {
        jTextField_Descrição.setText("");
        jFormattedTextFielID.setText("");
        jFormattedTextField_DATAGASTO.setText("");
        jFormattedTextFieldValor.setText("");
        jComboBoxVEICULOGastos.setSelectedItem(null);


    }//GEN-LAST:event_jButton1_CALCULARTOTAL1ActionPerformed
// ComoBox Veiculo
    private void jComboBoxVEICULOGastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxVEICULOGastosActionPerformed
        try {
            Object selectedItem = jComboBoxVEICULOGastos.getSelectedItem(); // Salva o item selecionado

            removeItemsFromComboBoxModeloGastos();
            ComboBoxModeloGastos();

            jComboBoxVEICULOGastos.setSelectedItem(selectedItem); // Define o item selecionado novamente

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_jComboBoxVEICULOGastosActionPerformed

    //MouseClicked tabela Geral
    private void jTableGastosTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableGastosTotalMouseClicked
        indexContatoEmEdicao = jTableGastosTotal.getSelectedRow();
        MostrarDadosGastos();
        Object[] dadosGastos = {
            modeloGastoTotal.getValueAt(indexContatoEmEdicao, 0),
            modeloGastoTotal.getValueAt(indexContatoEmEdicao, 1),
            modeloGastoTotal.getValueAt(indexContatoEmEdicao, 2),
            modeloGastoTotal.getValueAt(indexContatoEmEdicao, 3),
            modeloGastoTotal.getValueAt(indexContatoEmEdicao, 4),
            modeloGastoTotal.getValueAt(indexContatoEmEdicao, 5)};
        jFormattedTextFielID.setEditable(false);
        jComboBoxVEICULOGastos.setEnabled(false);

        jFormattedTextFielID.setText("" + dadosGastos[0]);
        jFormattedTextField_DATAGASTO.setText("" + dadosGastos[1]);
        jComboBoxVEICULOGastos.setSelectedItem("" + dadosGastos[2]);
        jTextField_Descrição.setText("" + dadosGastos[3]);
        jComboBoxCategoriaGastos.setSelectedItem("" + dadosGastos[4]);

        jFormattedTextFieldValor.setText("" + dadosGastos[5]);


    }//GEN-LAST:event_jTableGastosTotalMouseClicked

    private void jFormattedTextField_DATAGASTOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField_DATAGASTOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField_DATAGASTOActionPerformed

    private void jTable2TableAnualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2TableAnualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2TableAnualMouseClicked

    private void jButton1_CALCULARTOTAL2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_CALCULARTOTAL2ActionPerformed
// Botão buscar veículo
        try {
            MostrarDadosGastos();
            String veiculo = jComboBoxVEICULOGastos.getSelectedItem().toString();

            // Chama o método de busca de gastos do controle
            List<Gastos> gastos = gastosControle.buscarPorVeiculo(veiculo);

            if (gastos.isEmpty()) {
                throw new Exception("Nenhum gasto encontrado para o proprietário informado!");
            }

            // Limpa a tabela de resultados
            modeloGastoTotal.setRowCount(0);

            // Adiciona os gastos encontrados à tabela
            for (Gastos gasto : gastos) {
                String[] dadosGasto = gasto.toString().split(";");
                modeloGastoTotal.addRow(dadosGasto);
            }

            // Atualiza a exibição da tabela
            modeloGastoTotal.fireTableDataChanged();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }


    }//GEN-LAST:event_jButton1_CALCULARTOTAL2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTableGastosTotal.isVisible()) {
            // Se a tabela está visível, ocultá-la
            jTableGastosTotal.setVisible(false);
            jButton1.setText("Visualizar");  // Altera o texto do botão para "Mostrar"
            jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho (1).png")));
        } else {
            // Se a tabela está oculta, mostrá-la novamente
            jTableGastosTotal.setVisible(true);
            jButton1.setText("Ocultar");  // Altera o texto do botão para "Ocultar"
            jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho.png")));
            // Limpar o modelo da tabela antes de adicionar os novos dados
            modeloGastoTotal.setRowCount(0);

            // Chamar o método para preencher o modelo da tabela com os dados
            MostrarDadosGastos();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButtonCalculoMensalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCalculoMensalActionPerformed
         MostrarDespesaMensal();
        //Filtro Buscar Tabela Mensal
        try {
            //String veiculo = (String) jComboBoxVEICULOGastos.getSelectedItem();
            String mes = jTextFieldMes.getText();
            String ano = jTextFieldANUAL.getText();

            DefaultTableModel modeloGastoTotal = (DefaultTableModel) jTable1TableMensal.getModel();
            modeloGastoTotal.setRowCount(0); // Limpar o modelo da tabela antes de adicionar novos dados

            MostrarCalculosMes(mes, ano, modeloGastoTotal);
            //MostrarDespesaMensal();
            System.out.println("Dados adicionados à tabela com sucesso.");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }


    }//GEN-LAST:event_jButtonCalculoMensalActionPerformed

    private void jButtonAnualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnualActionPerformed

        // Filtro Tabela Anual 
        try {
            String veiculo = (String) jComboBoxVEICULOGastos.getSelectedItem();
            String ano = jTextFieldAno.getText();

            DefaultTableModel model = (DefaultTableModel) jTable2TableAnual.getModel();
            model.setNumRows(0);

            for (int mes = 1; mes <= 12; mes++) {
                String mesFormatado = String.format("%02d", mes);
                MostrarCalculosAnual(veiculo, ano, mesFormatado, model);
            }

            System.out.println("Exibindo gastos do proprietário " + veiculo + " no ano " + ano);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }

    }//GEN-LAST:event_jButtonAnualActionPerformed

    private void jTextFieldAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAnoActionPerformed

    private void jButtonAdicionarNacomboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarNacomboCategoriaActionPerformed
        // Obtenha o texto digitado na JTextField
        String novoItem = jTextFieldParaAdicionarNaCombo.getText();

        // Verifique se o texto não está vazio
        if (!novoItem.isEmpty()) {
            // Adicione o novo item ao modelo da combobox
            jComboBoxCategoriaGastos.addItem(novoItem);

            // Limpe a JTextField
            jTextFieldParaAdicionarNaCombo.setText("");
        }

        // Use a classe CategoriaGastosRenderer para exibir ícones na combobox
        jComboBoxCategoriaGastos.setRenderer(new CategoriaGastosRenderer());


    }//GEN-LAST:event_jButtonAdicionarNacomboCategoriaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        // Botão Voltar da Tabela Mensal

        // MostrarGastosMensal();
         MostrarDespesaMensal();
        jTextFieldANUAL.setText("");
        jTextFieldMes.setText("");

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
         
        MostrarGastoAnual();
        MostrarDespesaMensal();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        MostrarGastoAnual();
        jTextFieldAno.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextFieldMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dialog_TelaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dialog_TelaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dialog_TelaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dialog_TelaGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dialog_TelaGastos dialog = null;
                try {
                    dialog = new Dialog_TelaGastos(new javax.swing.JFrame(), true);
                } catch (Exception ex) {
                    Logger.getLogger(Dialog_TelaGastos.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton1_ATELRARGASTOS;
    private javax.swing.JButton jButton1_CALCULARTOTAL;
    private javax.swing.JButton jButton1_CALCULARTOTAL1;
    private javax.swing.JButton jButton1_CALCULARTOTAL2;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonAdicionarNacomboCategoria;
    private javax.swing.JButton jButtonAnual;
    private javax.swing.JButton jButtonCalculoMensal;
    private javax.swing.JComboBox<String> jComboBoxCategoriaGastos;
    private javax.swing.JComboBox<String> jComboBoxVEICULOGastos;
    private javax.swing.JTextField jFormattedTextFielID;
    private javax.swing.JTextField jFormattedTextFieldValor;
    private javax.swing.JFormattedTextField jFormattedTextField_DATAGASTO;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1TableMensal;
    private javax.swing.JTable jTable2TableAnual;
    private javax.swing.JTable jTableGastosTotal;
    private javax.swing.JTextField jTextFieldANUAL;
    private javax.swing.JTextField jTextFieldAno;
    private javax.swing.JTextField jTextFieldMes;
    private javax.swing.JTextField jTextFieldParaAdicionarNaCombo;
    private javax.swing.JTextField jTextField_Descrição;
    // End of variables declaration//GEN-END:variables

}
