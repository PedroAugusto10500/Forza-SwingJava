/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package visao;
import java.awt.image.BufferedImage;
import java.io.File;
//import static controle.ContatoController.validarCPF;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.Marca;
import controle.MarcaControle;
import controle.ModeloVeiculoControle;
import controle.VeiculosControle;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.event.ChangeListener;
import modelos.IMarcaCRUD;
import modelos.IModeloVeiculoCRUD;
import modelos.IVeiculosCRUD;
import modelos.ModeloVeiculo;
import modelos.Proprietario;
import modelos.Veiculos;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;




/**
 *
 * @author T-Gamer
 */
public class Dialog_TelaVeiculos extends javax.swing.JDialog {
    //Marca Veiculo
    IMarcaCRUD marcaControle = null;
    DefaultTableModel modeloMarca;
    Scanner nm = null;
    Marca marca = null;
    int indexMarcaEmEdicao = -1;
    
    BufferedImage imagem;
    File imagemLogo;
    
    
    //Modelo Veiculo
    IModeloVeiculoCRUD modeloControle = null;
    DefaultTableModel modeloModeloVeiculo;
    Scanner nmv = null;
    ModeloVeiculo modelo = null;
    int indexModeloEmEdicao = -1;
    
    BufferedImage imagemVeiculo;
    File imagemModeloVeiculo;
    private final String nomeDoArquivo;
    
    
    //Veiculo
    IVeiculosCRUD veiculoControle = null;
    DefaultTableModel modeloVeiculo;
    Scanner nv = null;
    Veiculos veiculo = null;
    int indexVeiculoEmEdicao = -1;
    private final String arquivoProprietario;
    private final String nomeDoArquivoModelo;
    
    
    
    
    public Dialog_TelaVeiculos(java.awt.Frame parent, boolean modal) throws Exception {
    super(parent, modal);
    initComponents();
    this.setLocationRelativeTo(null);
    
    //Marca
    marcaControle = new MarcaControle();
    modeloMarca = (DefaultTableModel) jTableMarca.getModel();
    MostrarDados();
    
    
    //Modelo
    modeloControle = new ModeloVeiculoControle();
    modeloModeloVeiculo = (DefaultTableModel) jTableModeloVeiculo.getModel();
    nomeDoArquivo = "./src/bancodedados/MarcaBD.csv";
    ComboBox();
    MostrarDadosModelo();

    //Veiculo
    veiculoControle = new VeiculosControle();
    modeloVeiculo = (DefaultTableModel) jTableVeiculo.getModel();
    arquivoProprietario = "./src/bancodedados/contatosBD.csv";
    nomeDoArquivoModelo = "./src/bancodedados/ModeloBD.csv";
    MostrarDadosVeiculo();
    ComboBoxModelo();
}
    
    
    //aparecer imagem na tabela
public class ImagemRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ImageIcon) {
            ImageIcon imagem = (ImageIcon) value;
            JLabel label = new JLabel(imagem);
            label.setOpaque(true);
            label.setBackground(table.getBackground());
            return label;
        } else {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
    


    
    
    
 //mostrar os dados da marca
  private void MostrarDados() {
    try {
        ArrayList<Marca> listaDeMarcas = marcaControle.listagemDeMarcas();
        modeloMarca = (DefaultTableModel) jTableMarca.getModel();
        modeloMarca.setNumRows(0);

        if (listaDeMarcas.isEmpty()) {
            return;
        }

        int larguraImagem = 90; // Especifica a largura desejada da imagem
        int alturaImagem = 70;  // Especifica a altura desejada da imagem

        for (int pos = 0; pos < listaDeMarcas.size(); pos++) {
            Marca marca = listaDeMarcas.get(pos);
            Object[] linha = new Object[3];
            linha[0] = marca.getId();
            linha[1] = marca.getDescricao();

            // Redimensionar a imagem para as dimensões desejadas
            ImageIcon imagem = new ImageIcon(marca.getImagemLogoMarca());
            Image imagemRedimensionada = imagem.getImage().getScaledInstance(larguraImagem, alturaImagem, Image.SCALE_SMOOTH);
            linha[2] = new ImageIcon(imagemRedimensionada);

            modeloMarca.addRow(linha);
        }

        // Configurar o renderizador de imagem para a coluna de imagem
        jTableMarca.getColumnModel().getColumn(2).setCellRenderer(new ImagemRenderer());
    } catch (Exception erro) {
        JOptionPane.showMessageDialog(rootPane, erro.getMessage());
    }
}
  
        //Mostrar Dados do  Modelo do Veiculo
        private void MostrarDadosModelo() {
    try {
        ArrayList<ModeloVeiculo> listaModelos = modeloControle.listagemModelos();
        modeloModeloVeiculo = (DefaultTableModel) jTableModeloVeiculo.getModel();
        modeloModeloVeiculo.setNumRows(0);
        if (listaModelos.isEmpty()) {
            return;
        }
        for (int pos = 0; pos < listaModelos.size(); pos++) {
            ModeloVeiculo modelo = listaModelos.get(pos);
            String[] linha = new String[4];
            linha[0] = modelo.getId();
            linha[1] = modelo.getDescricao();
            linha[2] = modelo.getComboMarca();
            linha[3] = modelo.getImagemModelo();
            
            modeloModeloVeiculo.addRow(linha);
        }
    } catch (Exception erro) {
        JOptionPane.showMessageDialog(rootPane, erro.getMessage());
    }
}

         //Mostrar Dados Veiculo
         private void MostrarDadosVeiculo() {
    try {
        ArrayList<Veiculos> listaDeVeiculos = veiculoControle.listagemDeVeiculos();
        modeloVeiculo = (DefaultTableModel) jTableVeiculo.getModel();
        modeloVeiculo.setNumRows(0);
        if (listaDeVeiculos.isEmpty()) {
            return;
        }
        for (int pos = 0; pos < listaDeVeiculos.size(); pos++) {
            Veiculos veiculo = listaDeVeiculos.get(pos);
            String[] linha = new String[6];
            linha[0] = veiculo.getPlaca();
            linha[1] = veiculo.getKm();
            linha[2] = veiculo.getCategoria();
            linha[3] = veiculo.getCombustivel();
            linha[4] = veiculo.getProprietario();
            linha[5] = veiculo.getModelo();
            
            modeloVeiculo.addRow(linha);
        }
    } catch (Exception erro) {
        JOptionPane.showMessageDialog(rootPane, erro.getMessage());
    } }
        
        //Limpar Campos Marca
      private void limparCampos() {
        jTextFieldIDMarca.setText("");
        jTextFieldDescricaoMarca.setText("");
        jLabelFOTOMARCA.setIcon(null);
    }
      
      //Limpar Campos Modelo do Veiculo
      private void limparCamposModelo() {
        jTextFieldIDModelo.setText("");
        jTextFieldDescriModelo.setText("");
        jLabelFOTOVEICULOModelo.setIcon(null);
        jComboBoxMARCAModelo.setSelectedItem(0);
    }
      
      //Limpar Campos Veiculo
      private void limparCamposVeiculo() {
        jFormattedTextField_Placa.setText("");
        jTextFieldKM.setText("");
        jComboBoxCombustivel.setSelectedItem(null);
        jComboBoxCategoria.setSelectedItem(null);
        jComboBoxProprietario.setSelectedItem(null);
        jComboBoxModelo.setSelectedItem(null);
    }
        
          // metodo para pegar os dados digitados nos campos da marca
         private Marca criarMarca() throws Exception {
        return new Marca(jTextFieldIDMarca.getText(), jTextFieldDescricaoMarca.getText(), jTextFieldFOTOMARCA.getText());
    }
         
         // metodo para pegar os dados digitados nos campos do modelo
         private ModeloVeiculo criarModelo() throws Exception {
        return new ModeloVeiculo(jTextFieldIDModelo.getText(), jTextFieldDescriModelo.getText(), (String) jComboBoxMARCAModelo.getSelectedItem(), jTextFieldFOTOMODELOVEICULO.getText());
    }
       
         // metodo para pegar os dados digitados nos campos do veiculo
         private Veiculos criarVeiculo() throws Exception {
        return new Veiculos(jFormattedTextField_Placa.getText(), jTextFieldKM.getText(), (String) jComboBoxCombustivel.getSelectedItem(), (String) jComboBoxCategoria.getSelectedItem(), (String) jComboBoxProprietario.getSelectedItem(), (String) jComboBoxModelo.getSelectedItem());
    }

         //Alocar as marcas registradas na comboBox
public void ComboBox() throws Exception {
    try {
        FileReader fr = new FileReader(nomeDoArquivo);
        try (BufferedReader br = new BufferedReader(fr)) {
            String linha = "";

            // Remove os itens antigos, exceto "Atualizar teira"
            removeItemsFromComboBox();

            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String descricao = vetorStr[1];

                // Verifica se o item já existe no modelo da combobox
                boolean itemExistente = false;
                for (int i = 0; i < jComboBoxMARCAModelo.getItemCount(); i++) {
                    String item = (String) jComboBoxMARCAModelo.getItemAt(i);
                    if (item.equals(descricao)) {
                        itemExistente = true;
                        break;
                    }
                }

                // Adiciona o item apenas se ele não existir no modelo
                if (!itemExistente) {
                    jComboBoxMARCAModelo.addItem(descricao);
                }
            }
        }
    } catch (IOException erro) {
        JOptionPane.showMessageDialog(null, erro.getMessage(), "Método comboBox", JOptionPane.ERROR_MESSAGE);
    }
}

public void removeItemsFromComboBox() {
   jComboBoxMARCAModelo.removeAllItems();
}


//Alocar os modelos registrados na comboBox
public void ComboBoxModelo() throws Exception {
    try {
        FileReader fr = new FileReader(nomeDoArquivoModelo);
        try (BufferedReader br = new BufferedReader(fr)) {
            String linha = "";

            // Remove os itens antigos
            removeItemsFromComboBoxModelo();

            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String descricao = vetorStr[1];

                // Verifica se o item já existe no modelo da combobox
                boolean itemExistente = false;
                for (int i = 0; i < jComboBoxModelo.getItemCount(); i++) {
                    String item = (String) jComboBoxModelo.getItemAt(i);
                    if (item.equals(descricao)) {
                        itemExistente = true;
                        break;
                    }
                }

                // Adiciona o item apenas se ele não existir no modelo
                if (!itemExistente) {
                    jComboBoxModelo.addItem(descricao);
                }
            }
        }
    } catch (IOException erro) {
        JOptionPane.showMessageDialog(null, erro.getMessage(), "Método comboBox", JOptionPane.ERROR_MESSAGE);
    }
}

public void removeItemsFromComboBoxModelo() {
    jComboBoxModelo.removeAllItems();
}


//Alocar os proprietarios registrados na comboBox
public void ComboBoxProprietario() throws Exception {
    try {
        FileReader fr = new FileReader(arquivoProprietario);
        try (BufferedReader br = new BufferedReader(fr)) {
            String linha = "";

            // Remove os itens antigos
            removeItemsFromComboBoxProprietario();

            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String descricao = vetorStr[0];

                // Verifica se o item já existe no modelo da combobox
                boolean itemExistente = false;
                for (int i = 0; i < jComboBoxProprietario.getItemCount(); i++) {
                    String item = (String) jComboBoxProprietario.getItemAt(i);
                    if (item.equals(descricao)) {
                        itemExistente = true;
                        break;
                    }
                }

                // Adiciona o item apenas se ele não existir no modelo
                if (!itemExistente) {
                    jComboBoxProprietario.addItem(descricao);
                }
            }
        }
    } catch (IOException erro) {
        JOptionPane.showMessageDialog(null, erro.getMessage(), "Método comboBox", JOptionPane.ERROR_MESSAGE);
    }
}

public void removeItemsFromComboBoxProprietario() {
    jComboBoxProprietario.removeAllItems();
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldKM = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboBoxModelo = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jComboBoxCategoria = new javax.swing.JComboBox<>();
        jComboBoxProprietario = new javax.swing.JComboBox<>();
        jFormattedTextField_Placa = new javax.swing.JTextField();
        jComboBoxCombustivel = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPaneMarcaModelo = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanelModelo = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableMarca = new javax.swing.JTable();
        jLabelFOTOMARCA = new javax.swing.JLabel();
        jButtonFOTOMARCA = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldDescricaoMarca = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldIDMarca = new javax.swing.JTextField();
        jButtonINSERIRMARCA = new javax.swing.JButton();
        jButtonALTERARMARCA = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jButtonvoltarmarca = new javax.swing.JButton();
        jButtonbuscarmarca = new javax.swing.JButton();
        jTextFieldFOTOMARCA = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabelFOTOVEICULOModelo = new javax.swing.JLabel();
        jButtonFOTOMODELO = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldDescriModelo = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldIDModelo = new javax.swing.JTextField();
        jButtonINSERIRMODELO = new javax.swing.JButton();
        jButtonALTERARMODELO = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableModeloVeiculo = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jTextFieldFOTOMODELOVEICULO = new javax.swing.JTextField();
        jButtonbuscarmarca1 = new javax.swing.JButton();
        jButtonvoltarmodelo = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBoxMARCAModelo = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButton1_INCLUIR = new javax.swing.JButton();
        jButton4_ALTERAR = new javax.swing.JButton();
        jButton2_BUSCAR = new javax.swing.JButton();
        jButton3_LISTA = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableVeiculo = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("jMenu5");

        jMenu6.setText("File");
        jMenuBar3.add(jMenu6);

        jMenu7.setText("Edit");
        jMenuBar3.add(jMenu7);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusable(false);
        setUndecorated(true);
        setSize(new java.awt.Dimension(1280, 590));
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_info.png"))); // NOI18N
        jLabel12.setToolTipText("Padrão exemplo: ABC-1234. \n Mercosul exemplo: ABC1A23.");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, -1, -1));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 90, -1, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setText("Placa:");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 50, 15));

        jLabel9.setText("____________________________________________________");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 220, -1));

        jTextFieldKM.setBackground(new java.awt.Color(102, 102, 102));
        jTextFieldKM.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextFieldKM.setBorder(null);
        jPanel2.add(jTextFieldKM, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 190, 20));

        jLabel23.setText("___________________________________________________");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 200, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("Quilometragem:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 120, 15));

        jComboBoxModelo.setBackground(new java.awt.Color(53, 53, 53));
        jComboBoxModelo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Modelo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 1, 12))); // NOI18N
        jComboBoxModelo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxModeloMouseClicked(evt);
            }
        });
        jComboBoxModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxModeloActionPerformed(evt);
            }
        });
        jComboBoxModelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxModeloKeyPressed(evt);
            }
        });
        jPanel2.add(jComboBoxModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 170, 50));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_info.png"))); // NOI18N
        jLabel28.setToolTipText("Digite a KM atual do veículo!");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, -1, -1));

        jComboBoxCategoria.setBackground(new java.awt.Color(53, 53, 53));
        jComboBoxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Conversível", "SUV", "Sedã", "Buggy", "Cupê", "Hatchback", "Minivan", "Perua/SW", "Picape", "Utilitário esportivo", "Van/Utilitário" }));
        jComboBoxCategoria.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Categoria:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 1, 12))); // NOI18N
        jComboBoxCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategoriaActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBoxCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 170, 50));
        jComboBoxCategoria.getAccessibleContext().setAccessibleName("");

        jComboBoxProprietario.setBackground(new java.awt.Color(53, 53, 53));
        jComboBoxProprietario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Proprietário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 1, 12))); // NOI18N
        jComboBoxProprietario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxProprietarioMouseClicked(evt);
            }
        });
        jComboBoxProprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProprietarioActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBoxProprietario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 170, 50));

        jFormattedTextField_Placa.setBackground(new java.awt.Color(102, 102, 102));
        jFormattedTextField_Placa.setBorder(null);
        jPanel2.add(jFormattedTextField_Placa, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 210, 20));

        jComboBoxCombustivel.setBackground(new java.awt.Color(53, 53, 53));
        jComboBoxCombustivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gasolina Comum", "Gasolina Aditivada", "Diesel", "Etanol", "Elétrico", "Híbrido", "GNV" }));
        jComboBoxCombustivel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tipos de Combustível:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 1, 12))); // NOI18N
        jComboBoxCombustivel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCombustivelActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBoxCombustivel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 170, 50));

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 100, 460, 280);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jTabbedPaneMarcaModelo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneMarcaModeloMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanelModelo.setBackground(new java.awt.Color(102, 102, 102));
        jPanelModelo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanelModelo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableMarca.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jTableMarca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descrição", "Logo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMarca.setRowHeight(70);
        jTableMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMarcaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableMarca);

        jPanelModelo.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 540, 140));

        jLabelFOTOMARCA.setBackground(new java.awt.Color(0, 0, 0));
        jLabelFOTOMARCA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanelModelo.add(jLabelFOTOMARCA, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 120, 70));

        jButtonFOTOMARCA.setBackground(new java.awt.Color(51, 51, 51));
        jButtonFOTOMARCA.setFont(new java.awt.Font("Ebrima", 1, 8)); // NOI18N
        jButtonFOTOMARCA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_galeria.png"))); // NOI18N
        jButtonFOTOMARCA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButtonFOTOMARCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFOTOMARCAActionPerformed(evt);
            }
        });
        jPanelModelo.add(jButtonFOTOMARCA, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 120, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Descrição:");
        jPanelModelo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 60, -1));

        jTextFieldDescricaoMarca.setBackground(new java.awt.Color(102, 102, 102));
        jTextFieldDescricaoMarca.setBorder(null);
        jPanelModelo.add(jTextFieldDescricaoMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 150, 20));

        jLabel14.setText("_______________________________");
        jPanelModelo.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 160, 20));

        jLabel7.setText("_______________________________");
        jPanelModelo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 160, 20));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("ID:");
        jPanelModelo.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 30, -1));

        jTextFieldIDMarca.setBackground(new java.awt.Color(102, 102, 102));
        jTextFieldIDMarca.setBorder(null);
        jTextFieldIDMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDMarcaActionPerformed(evt);
            }
        });
        jPanelModelo.add(jTextFieldIDMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 150, 20));

        jButtonINSERIRMARCA.setBackground(new java.awt.Color(102, 102, 102));
        jButtonINSERIRMARCA.setFont(new java.awt.Font("Ebrima", 1, 10)); // NOI18N
        jButtonINSERIRMARCA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_incluir icon.png"))); // NOI18N
        jButtonINSERIRMARCA.setText("INCLUIR");
        jButtonINSERIRMARCA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButtonINSERIRMARCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonINSERIRMARCAActionPerformed(evt);
            }
        });
        jPanelModelo.add(jButtonINSERIRMARCA, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 280, 30));

        jButtonALTERARMARCA.setBackground(new java.awt.Color(102, 102, 102));
        jButtonALTERARMARCA.setFont(new java.awt.Font("Ebrima", 1, 10)); // NOI18N
        jButtonALTERARMARCA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_alterar icon.png"))); // NOI18N
        jButtonALTERARMARCA.setText("ALTERAR");
        jButtonALTERARMARCA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButtonALTERARMARCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonALTERARMARCAActionPerformed(evt);
            }
        });
        jPanelModelo.add(jButtonALTERARMARCA, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 170, 260, 30));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_info.png"))); // NOI18N
        jLabel25.setToolTipText("Escolha a imagem da logo da marca!");
        jPanelModelo.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, -1));

        jButtonvoltarmarca.setBackground(new java.awt.Color(102, 102, 102));
        jButtonvoltarmarca.setFont(new java.awt.Font("Ebrima", 1, 10)); // NOI18N
        jButtonvoltarmarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_voltarP.png"))); // NOI18N
        jButtonvoltarmarca.setText("VOLTAR");
        jButtonvoltarmarca.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButtonvoltarmarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonvoltarmarcaActionPerformed(evt);
            }
        });
        jPanelModelo.add(jButtonvoltarmarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, 260, 30));

        jButtonbuscarmarca.setBackground(new java.awt.Color(102, 102, 102));
        jButtonbuscarmarca.setFont(new java.awt.Font("Ebrima", 1, 10)); // NOI18N
        jButtonbuscarmarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_buscar icon.png"))); // NOI18N
        jButtonbuscarmarca.setText("BUSCAR");
        jButtonbuscarmarca.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButtonbuscarmarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonbuscarmarcaActionPerformed(evt);
            }
        });
        jPanelModelo.add(jButtonbuscarmarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 280, 30));

        jTextFieldFOTOMARCA.setBackground(new java.awt.Color(102, 102, 102));
        jTextFieldFOTOMARCA.setForeground(new java.awt.Color(102, 102, 102));
        jTextFieldFOTOMARCA.setBorder(null);
        jTextFieldFOTOMARCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFOTOMARCAActionPerformed(evt);
            }
        });
        jPanelModelo.add(jTextFieldFOTOMARCA, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 60, -1));

        jButton3.setBackground(new java.awt.Color(55, 55, 55));
        jButton3.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho.png"))); // NOI18N
        jButton3.setText("Ocultar");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanelModelo.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 130, 20));

        jPanel1.add(jPanelModelo, java.awt.BorderLayout.CENTER);

        jTabbedPaneMarcaModelo.addTab("MARCA", jPanel1);

        jPanel10.setBackground(new java.awt.Color(102, 102, 102));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelFOTOVEICULOModelo.setBackground(new java.awt.Color(51, 51, 51));
        jLabelFOTOVEICULOModelo.setForeground(new java.awt.Color(51, 51, 51));
        jLabelFOTOVEICULOModelo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel10.add(jLabelFOTOVEICULOModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 120));

        jButtonFOTOMODELO.setBackground(new java.awt.Color(51, 51, 51));
        jButtonFOTOMODELO.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jButtonFOTOMODELO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_galeria.png"))); // NOI18N
        jButtonFOTOMODELO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButtonFOTOMODELO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFOTOMODELOActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonFOTOMODELO, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 200, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Descrição:");
        jPanel10.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 60, -1));

        jTextFieldDescriModelo.setBackground(new java.awt.Color(102, 102, 102));
        jTextFieldDescriModelo.setBorder(null);
        jPanel10.add(jTextFieldDescriModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 150, 20));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("ID:");
        jPanel10.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 30, -1));

        jTextFieldIDModelo.setBackground(new java.awt.Color(102, 102, 102));
        jTextFieldIDModelo.setBorder(null);
        jTextFieldIDModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDModeloActionPerformed(evt);
            }
        });
        jPanel10.add(jTextFieldIDModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 150, 20));

        jButtonINSERIRMODELO.setBackground(new java.awt.Color(102, 102, 102));
        jButtonINSERIRMODELO.setFont(new java.awt.Font("Ebrima", 1, 10)); // NOI18N
        jButtonINSERIRMODELO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_incluir icon.png"))); // NOI18N
        jButtonINSERIRMODELO.setText("INCLUIR");
        jButtonINSERIRMODELO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButtonINSERIRMODELO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonINSERIRMODELOActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonINSERIRMODELO, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 190, 30));

        jButtonALTERARMODELO.setBackground(new java.awt.Color(102, 102, 102));
        jButtonALTERARMODELO.setFont(new java.awt.Font("Ebrima", 1, 10)); // NOI18N
        jButtonALTERARMODELO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_alterar icon.png"))); // NOI18N
        jButtonALTERARMODELO.setText("ALTERAR");
        jButtonALTERARMODELO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButtonALTERARMODELO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonALTERARMODELOActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonALTERARMODELO, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, 210, 30));

        jLabel22.setText("_______________________________");
        jPanel10.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 160, 20));

        jLabel10.setText("_______________________________");
        jPanel10.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 160, 20));

        jTableModeloVeiculo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descrição", "Marca", "Imagem Veículo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableModeloVeiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableModeloVeiculoMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTableModeloVeiculo);

        jPanel10.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 500, 130));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_info.png"))); // NOI18N
        jLabel24.setToolTipText("Escolha a imagem do modelo do veículo!");
        jPanel10.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        jTextFieldFOTOMODELOVEICULO.setBackground(new java.awt.Color(102, 102, 102));
        jTextFieldFOTOMODELOVEICULO.setForeground(new java.awt.Color(102, 102, 102));
        jTextFieldFOTOMODELOVEICULO.setBorder(null);
        jTextFieldFOTOMODELOVEICULO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFOTOMODELOVEICULOActionPerformed(evt);
            }
        });
        jPanel10.add(jTextFieldFOTOMODELOVEICULO, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 20, -1));

        jButtonbuscarmarca1.setBackground(new java.awt.Color(102, 102, 102));
        jButtonbuscarmarca1.setFont(new java.awt.Font("Ebrima", 1, 10)); // NOI18N
        jButtonbuscarmarca1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_buscar icon.png"))); // NOI18N
        jButtonbuscarmarca1.setText("BUSCAR");
        jButtonbuscarmarca1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButtonbuscarmarca1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonbuscarmarca1ActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonbuscarmarca1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 200, 190, 30));

        jButtonvoltarmodelo.setBackground(new java.awt.Color(102, 102, 102));
        jButtonvoltarmodelo.setFont(new java.awt.Font("Ebrima", 1, 10)); // NOI18N
        jButtonvoltarmodelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_voltarP.png"))); // NOI18N
        jButtonvoltarmodelo.setText("VOLTAR");
        jButtonvoltarmodelo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButtonvoltarmodelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonvoltarmodeloActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonvoltarmodelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 200, 210, 30));

        jButton2.setBackground(new java.awt.Color(55, 55, 55));
        jButton2.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho.png"))); // NOI18N
        jButton2.setText("Ocultar");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 130, 20));

        jComboBoxMARCAModelo.setBackground(new java.awt.Color(51, 51, 51));
        jComboBoxMARCAModelo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Marca:"))));
        jComboBoxMARCAModelo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxMARCAModeloMouseClicked(evt);
            }
        });
        jComboBoxMARCAModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMARCAModeloActionPerformed(evt);
            }
        });
        jPanel10.add(jComboBoxMARCAModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, 100, 60));

        jTabbedPaneMarcaModelo.addTab("MODELO", jPanel10);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneMarcaModelo, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneMarcaModelo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3);
        jPanel3.setBounds(460, 100, 820, 280);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setLayout(null);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/whatsapp (1).png"))); // NOI18N
        jLabel11.setToolTipText("+55(62)99999-9999");
        jPanel4.add(jLabel11);
        jLabel11.setBounds(640, 10, 24, 24);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/instagram (1).png"))); // NOI18N
        jLabel16.setToolTipText("@forza");
        jPanel4.add(jLabel16);
        jLabel16.setBounds(670, 10, 24, 24);

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/sinal-do-twitter.png"))); // NOI18N
        jLabel20.setToolTipText("@forza");
        jPanel4.add(jLabel20);
        jLabel20.setBounds(700, 10, 24, 24);

        jLabel29.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Desenvolvido por Forza ® Direitos Reservados.");
        jPanel4.add(jLabel29);
        jLabel29.setBounds(550, 40, 271, 17);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(-10, 540, 1290, 70);

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/NavBar_Veiculo.png"))); // NOI18N
        jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        getContentPane().add(jLabel17);
        jLabel17.setBounds(0, 0, 1280, 100);

        jPanel5.setBackground(new java.awt.Color(20, 20, 20));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1_INCLUIR.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1_INCLUIR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_incluir icon.png"))); // NOI18N
        jButton1_INCLUIR.setText("INCLUIR");
        jButton1_INCLUIR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton1_INCLUIR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_INCLUIRActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1_INCLUIR, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 180, 40));

        jButton4_ALTERAR.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton4_ALTERAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_alterar icon.png"))); // NOI18N
        jButton4_ALTERAR.setText("ALTERAR");
        jButton4_ALTERAR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton4_ALTERAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4_ALTERARActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4_ALTERAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 180, 40));

        jButton2_BUSCAR.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2_BUSCAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/m_buscar icon.png"))); // NOI18N
        jButton2_BUSCAR.setText("BUSCAR");
        jButton2_BUSCAR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton2_BUSCAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2_BUSCARActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2_BUSCAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 180, 40));

        jButton3_LISTA.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3_LISTA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_voltarP.png"))); // NOI18N
        jButton3_LISTA.setText(" VOLTAR");
        jButton3_LISTA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton3_LISTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3_LISTAActionPerformed(evt);
            }
        });
        jPanel5.add(jButton3_LISTA, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 180, 40));

        jTableVeiculo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Placa", "Quilometragem", "Categoria", "Tipo de Combustível", "Proprietário", "Modelo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVeiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableVeiculoMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTableVeiculo);

        jPanel5.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 820, 120));

        jButton1.setBackground(new java.awt.Color(53, 53, 53));
        jButton1.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho.png"))); // NOI18N
        jButton1.setText("Ocultar");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 130, 20));

        getContentPane().add(jPanel5);
        jPanel5.setBounds(-10, 380, 1290, 160);

        jMenuBar1.setBackground(new java.awt.Color(0, 0, 0));

        jMenu2.setBackground(new java.awt.Color(0, 0, 0));
        jMenu2.setBorder(null);
        jMenu2.setForeground(new java.awt.Color(255, 255, 255));
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/menu.png"))); // NOI18N
        jMenu2.setFont(new java.awt.Font("Ebrima", 1, 11)); // NOI18N

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/icon_voltarP.png"))); // NOI18N
        jMenuItem2.setText("Menu Principal");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho.png"))); // NOI18N
        jMenuItem1.setText("FECHAR PROGRAMA");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(1276, 641));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1_INCLUIRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_INCLUIRActionPerformed
try {
    if (jFormattedTextField_Placa.getText().isBlank()
            || jTextFieldKM.getText().isBlank()
            || jComboBoxCategoria.getSelectedItem() == null
            || jComboBoxModelo.getSelectedItem() == null
            || jComboBoxProprietario.getSelectedItem() == null
            || jComboBoxCombustivel.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos para continuar!");
    } else {
        veiculoControle.incluir(criarVeiculo());
        MostrarDadosVeiculo();
        ComboBoxModelo();
        limparCamposVeiculo();
        System.out.println(criarVeiculo());
    }
} catch (Exception erro) {
    JOptionPane.showMessageDialog(this, erro.getMessage());
}


    }//GEN-LAST:event_jButton1_INCLUIRActionPerformed

    private void jButton2_BUSCARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2_BUSCARActionPerformed

    // Botão buscar veículo
    
    try {
        String placa = jFormattedTextField_Placa.getText().toString();

        // Verifica se o campo de placa está preenchido
        if (placa.isEmpty()) {
            throw new Exception("Preencha o campo placa para buscar o veículo!");
        }

        // Chama o método de busca de veículo do controle
        Veiculos buscaVeiculo = veiculoControle.buscarPlaca(placa);

        if (buscaVeiculo == null) {
            throw new Exception("Proprietário inexistente para a placa: " + placa);
        }

        // Limpa a tabela de resultados
        modeloVeiculo.setRowCount(0);

        // Adiciona o veículo encontrado à tabela
        modeloVeiculo.addRow(buscaVeiculo.toString().split(";"));
    } catch (Exception erro) {
        JOptionPane.showMessageDialog(this, erro.getMessage());
    }


    
    }//GEN-LAST:event_jButton2_BUSCARActionPerformed

    private void jButton4_ALTERARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4_ALTERARActionPerformed
//Alterar Veiculos
try {
    veiculoControle.alterar(indexVeiculoEmEdicao, criarVeiculo());
    System.out.println(criarVeiculo());
    System.out.println(indexVeiculoEmEdicao);
    System.out.println(criarVeiculo());
    jComboBoxProprietario.removeAllItems();
    jComboBoxModelo.removeAllItems();
    MostrarDadosVeiculo();
} catch (Exception ex) {
    JOptionPane.showMessageDialog(this, ex.getMessage());
}  

    }//GEN-LAST:event_jButton4_ALTERARActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        TelaPrincipal Voltar = new TelaPrincipal();
        Voltar.setVisible(true);
        dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTableVeiculoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableVeiculoMouseClicked
       
       indexVeiculoEmEdicao = jTableVeiculo.getSelectedRow();

        Object[] dadosVeiculo = {
            modeloVeiculo.getValueAt(indexVeiculoEmEdicao, 0),
            modeloVeiculo.getValueAt(indexVeiculoEmEdicao, 1),
            modeloVeiculo.getValueAt(indexVeiculoEmEdicao, 2),
            modeloVeiculo.getValueAt(indexVeiculoEmEdicao, 3),
            modeloVeiculo.getValueAt(indexVeiculoEmEdicao, 4),
            modeloVeiculo.getValueAt(indexVeiculoEmEdicao, 5),
        };
        jFormattedTextField_Placa.setEditable(false); 
        jFormattedTextField_Placa.setText("" + dadosVeiculo[0]);
        jTextFieldKM.setText("" + dadosVeiculo[1]);
         jComboBoxCategoria.setSelectedItem("" + dadosVeiculo[2]);
        jComboBoxCombustivel.setSelectedItem("" + dadosVeiculo[3]);
        jComboBoxProprietario.setSelectedItem("" + dadosVeiculo[4]);
        jComboBoxModelo.setSelectedItem("" + dadosVeiculo[5]);
        
        
        
    }//GEN-LAST:event_jTableVeiculoMouseClicked

    private void jTextFieldFOTOMODELOVEICULOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFOTOMODELOVEICULOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFOTOMODELOVEICULOActionPerformed

    private void jTableModeloVeiculoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableModeloVeiculoMouseClicked
        indexModeloEmEdicao = jTableModeloVeiculo.getSelectedRow();

        Object[] dadosModelo = {
            modeloModeloVeiculo.getValueAt(indexModeloEmEdicao, 0),
            modeloModeloVeiculo.getValueAt(indexModeloEmEdicao, 1),
            modeloModeloVeiculo.getValueAt(indexModeloEmEdicao, 2),
            modeloModeloVeiculo.getValueAt(indexModeloEmEdicao, 3),// Adicione a coluna da imagem aqui
        };
        jTextFieldIDModelo.setEditable(false); 
        jTextFieldIDModelo.setText("" + dadosModelo[0]);
        jTextFieldDescriModelo.setText("" + dadosModelo[1]);
        jComboBoxMARCAModelo.setSelectedItem("" + dadosModelo[2]);
        jTextFieldFOTOMODELOVEICULO.setText("" + dadosModelo[3]);
        ImageIcon imagemModelo = new ImageIcon("" + dadosModelo[3]);
        jLabelFOTOVEICULOModelo.setIcon(new ImageIcon(imagemModelo.getImage().getScaledInstance(jLabelFOTOVEICULOModelo.getWidth(), jLabelFOTOVEICULOModelo.getHeight(), Image.SCALE_DEFAULT)));

       
    }//GEN-LAST:event_jTableModeloVeiculoMouseClicked

    
    private void jButtonALTERARMODELOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonALTERARMODELOActionPerformed
   try {
    modeloControle.alterar(indexModeloEmEdicao, criarModelo());
    System.out.println(criarModelo());
    System.out.println(indexModeloEmEdicao);
    System.out.println(criarModelo());
    MostrarDadosModelo();
} catch (Exception ex) {
    JOptionPane.showMessageDialog(this, ex.getMessage());
}     
    }//GEN-LAST:event_jButtonALTERARMODELOActionPerformed

    private void jButtonINSERIRMODELOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonINSERIRMODELOActionPerformed
  try {
    if (jTextFieldDescriModelo.getText().isBlank() || jTextFieldIDModelo.getText().isBlank() || jLabelFOTOVEICULOModelo.getIcon() == null) {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos para incluir o modelo!");
    } else {
        modeloControle.incluir(criarModelo());
        MostrarDadosModelo();
        limparCampos();
        System.out.println(criarModelo());
    }
} catch (Exception erro) {
    JOptionPane.showMessageDialog(this, erro.getMessage());
}
    }//GEN-LAST:event_jButtonINSERIRMODELOActionPerformed

    private void jTextFieldIDModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDModeloActionPerformed

    private void jButtonFOTOMODELOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFOTOMODELOActionPerformed
JFileChooser arquivo = new JFileChooser();
arquivo.setDialogTitle("Selecione uma Imagem");
arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
int op = arquivo.showOpenDialog(this);
if (op == JFileChooser.APPROVE_OPTION) {
    File file = arquivo.getSelectedFile();

    // Check if the selected file is larger than 10 MB
    long fileSizeInBytes = file.length();
    long fileSizeInMB = fileSizeInBytes / (1024 * 1024);

    if (fileSizeInMB > 10) {
        JOptionPane.showMessageDialog(this, "O tamanho da imagem selecionada é maior do que 10 MB.", "Erro", JOptionPane.ERROR_MESSAGE);
    } else {
        imagemModeloVeiculo = file;
        String imagemCaminho = file.getAbsolutePath();
        jTextFieldFOTOMODELOVEICULO.setText(imagemCaminho);

        ImageIcon imagemPerfil = new ImageIcon(imagemModeloVeiculo.getAbsolutePath());

        jLabelFOTOVEICULOModelo.setIcon(new ImageIcon(imagemPerfil.getImage().getScaledInstance(jLabelFOTOVEICULOModelo.getWidth(), jLabelFOTOVEICULOModelo.getHeight(), Image.SCALE_DEFAULT)));
    }
}


    }//GEN-LAST:event_jButtonFOTOMODELOActionPerformed

    private void jTextFieldFOTOMARCAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFOTOMARCAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFOTOMARCAActionPerformed

    private void jButtonALTERARMARCAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonALTERARMARCAActionPerformed
       try {
    marcaControle.alterar(indexMarcaEmEdicao, criarMarca());
    System.out.println(criarMarca());
    System.out.println(indexMarcaEmEdicao);
    System.out.println(criarMarca());
    limparCampos();
    MostrarDados();
} catch (Exception ex) {
    JOptionPane.showMessageDialog(this, ex.getMessage());
}

    }//GEN-LAST:event_jButtonALTERARMARCAActionPerformed

    private void jButtonINSERIRMARCAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonINSERIRMARCAActionPerformed
try {
    if (jTextFieldIDMarca.getText().isBlank() || jTextFieldDescricaoMarca.getText().isBlank() || jLabelFOTOMARCA.getIcon() == null) {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos para incluir a marca!");
    } else {
        // Adicionar a imagem ao modelo da tabela
        
        marcaControle.incluir(criarMarca());
        MostrarDados();
        limparCampos();
        System.out.println(criarMarca());
    }
} catch (Exception erro) {
    JOptionPane.showMessageDialog(this, erro.getMessage());
}

        
    }//GEN-LAST:event_jButtonINSERIRMARCAActionPerformed

    private void jTextFieldIDMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDMarcaActionPerformed

    private void jButtonFOTOMARCAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFOTOMARCAActionPerformed
JFileChooser arquivo = new JFileChooser();
arquivo.setDialogTitle("Selecione uma Imagem");
arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
int op = arquivo.showOpenDialog(this);
if (op == JFileChooser.APPROVE_OPTION) {
    File file = arquivo.getSelectedFile();

    // Check if the selected file is larger than 10 MB
    long fileSizeInBytes = file.length();
    long fileSizeInMB = fileSizeInBytes / (1024 * 1024);

    if (fileSizeInMB > 10) {
        JOptionPane.showMessageDialog(this, "O tamanho da imagem selecionada é maior do que 10 MB.", "Erro", JOptionPane.ERROR_MESSAGE);
    } else {
        imagemLogo = file;
        String imagemCaminho = file.getAbsolutePath();
        jTextFieldFOTOMARCA.setText(imagemCaminho);

        ImageIcon imagemPerfil = new ImageIcon(imagemLogo.getAbsolutePath());

        jLabelFOTOMARCA.setIcon(new ImageIcon(imagemPerfil.getImage().getScaledInstance(jLabelFOTOMARCA.getWidth(), jLabelFOTOMARCA.getHeight(), Image.SCALE_DEFAULT)));
    }
}
    }//GEN-LAST:event_jButtonFOTOMARCAActionPerformed

    private void jTableMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMarcaMouseClicked
        indexMarcaEmEdicao = jTableMarca.getSelectedRow();

    Object[] dadosMarca = {
        modeloMarca.getValueAt(indexMarcaEmEdicao, 0),
        modeloMarca.getValueAt(indexMarcaEmEdicao, 1),
        modeloMarca.getValueAt(indexMarcaEmEdicao, 2) // Coluna da imagem
    };
    jTextFieldIDMarca.setEditable(false);
    jTextFieldDescricaoMarca.setEditable(false);
    jTextFieldIDMarca.setText("" + dadosMarca[0]);
    jTextFieldDescricaoMarca.setText("" + dadosMarca[1]);
    
    // Verificar se o valor da coluna é uma instância de ImageIcon
    if (dadosMarca[2] instanceof ImageIcon) {
        ImageIcon imagemMarca = (ImageIcon) dadosMarca[2];
        jLabelFOTOMARCA.setIcon(new ImageIcon(imagemMarca.getImage().getScaledInstance(jLabelFOTOMARCA.getWidth(), jLabelFOTOMARCA.getHeight(), Image.SCALE_SMOOTH)));
    } else {
        jLabelFOTOMARCA.setIcon(null); // Limpar o ícone do JLabel se não houver uma imagem válida
    }
    }//GEN-LAST:event_jTableMarcaMouseClicked

    private void jButtonbuscarmarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonbuscarmarcaActionPerformed
          try {
            Marca buscaContato = marcaControle.buscarPorID(jTextFieldIDMarca.getText());
            System.out.println(buscaContato);
            modeloMarca.setNumRows(0); // limpando a tabela
            modeloMarca.addRow(buscaContato.toString().split(";"));
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
        
        
        
    }//GEN-LAST:event_jButtonbuscarmarcaActionPerformed

    private void jButtonvoltarmarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonvoltarmarcaActionPerformed
         try {
         jTextFieldDescricaoMarca.setEditable(true);
         jTextFieldIDMarca.setEditable(true); 
         jTextFieldFOTOMARCA.setText("");
            MostrarDados();
            limparCampos();
            System.out.println(marcaControle.listagemDeMarcas());
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }//GEN-LAST:event_jButtonvoltarmarcaActionPerformed

    private void jButtonbuscarmarca1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonbuscarmarca1ActionPerformed
  try {
            ModeloVeiculo buscaModelo = modeloControle.buscarPorID(jTextFieldIDModelo.getText());
            System.out.println(buscaModelo);
            modeloModeloVeiculo.setNumRows(0); // limpando a tabela
            modeloModeloVeiculo.addRow(buscaModelo.toString().split(";"));
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }//GEN-LAST:event_jButtonbuscarmarca1ActionPerformed

    private void jButtonvoltarmodeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonvoltarmodeloActionPerformed
         try {
         jTextFieldIDModelo.setEditable(true); 
         jTextFieldFOTOMODELOVEICULO.setText("");
            MostrarDadosModelo();
            limparCamposModelo();
            System.out.println(modeloControle.listagemModelos());
            jComboBoxMARCAModelo.removeAllItems();
           
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }

    }//GEN-LAST:event_jButtonvoltarmodeloActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

    System.exit(0);
    

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    if (jTableModeloVeiculo.isVisible()) {
        // Se a tabela está visível, ocultá-la
        jTableModeloVeiculo.setVisible(false);
        jButton2.setText("Visualizar");  // Altera o texto do botão para "Mostrar"
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho (1).png")));
    } else {
        // Se a tabela está oculta, mostrá-la novamente
        jTableModeloVeiculo.setVisible(true);
        jButton2.setText("Ocultar");  // Altera o texto do botão para "Ocultar"
      jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho.png")));

        // Limpar o modelo da tabela antes de adicionar os novos dados
        modeloModeloVeiculo.setRowCount(0);

        // Chamar o método para preencher o modelo da tabela com os dados
        MostrarDadosModelo();
    }

    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         if (jTableMarca.isVisible()) {
        // Se a tabela está visível, ocultá-la
        jTableMarca.setVisible(false);
        jButton3.setText("Visualizar");  // Altera o texto do botão para "Mostrar"
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho (1).png")));
    } else {
        // Se a tabela está oculta, mostrá-la novamente
        jTableMarca.setVisible(true);
        jButton3.setText("Ocultar");  // Altera o texto do botão para "Ocultar"
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho.png")));

        // Limpar o modelo da tabela antes de adicionar os novos dados
        modeloMarca.setRowCount(0);

        // Chamar o método para preencher o modelo da tabela com os dados
        MostrarDados();
    }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBoxProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProprietarioActionPerformed
    try {
            Object selectedItem = jComboBoxProprietario.getSelectedItem(); // Salva o item selecionado

            removeItemsFromComboBoxProprietario();
            ComboBoxProprietario();

           
                jComboBoxProprietario.setSelectedItem(selectedItem); // Define o item selecionado novamente
            
        } catch (Exception ex) {
            Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jComboBoxProprietarioActionPerformed

    private void jComboBoxModeloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxModeloKeyPressed

    }//GEN-LAST:event_jComboBoxModeloKeyPressed

    private void jComboBoxModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxModeloActionPerformed
        try {
        Object selectedItem = jComboBoxModelo.getSelectedItem(); // Salva o item selecionado

        removeItemsFromComboBoxModelo();
        ComboBoxModelo();

        jComboBoxModelo.setSelectedItem(selectedItem); // Define o item selecionado novamente
    } catch (Exception ex) {
        Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jComboBoxModeloActionPerformed

    private void jComboBoxModeloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxModeloMouseClicked
     try {
        Object selectedItem = jComboBoxModelo.getSelectedItem(); // Salva o item selecionado

        removeItemsFromComboBoxModelo();
        ComboBoxModelo();

       
     
            jComboBoxModelo.setSelectedItem(selectedItem); // Define o item selecionado novamente
        
    } catch (Exception ex) {
        Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jComboBoxModeloMouseClicked

    private void jComboBoxProprietarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxProprietarioMouseClicked
  try {
        Object selectedItem = jComboBoxProprietario.getSelectedItem(); // Salva o item selecionado

        removeItemsFromComboBoxProprietario();
        ComboBoxProprietario();

       
     
            jComboBoxProprietario.setSelectedItem(selectedItem); // Define o item selecionado novamente
       
    } catch (Exception ex) {
        Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        
        
    }//GEN-LAST:event_jComboBoxProprietarioMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         if (jTableVeiculo.isVisible()) {
        // Se a tabela está visível, ocultá-la
        jTableVeiculo.setVisible(false);
        jButton1.setText("Visualizar");  // Altera o texto do botão para "Mostrar"
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho (1).png")));
    } else {
        // Se a tabela está oculta, mostrá-la novamente
        jTableVeiculo.setVisible(true);
        jButton1.setText("Ocultar");  // Altera o texto do botão para "Ocultar"
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/olho.png")));

        // Limpar o modelo da tabela antes de adicionar os novos dados
        modeloVeiculo.setRowCount(0);

        // Chamar o método para preencher o modelo da tabela com os dados
        MostrarDadosVeiculo();
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBoxCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoriaActionPerformed
        // TODO add your handling code here:
        
        
        
        
        
        
        
        
    }//GEN-LAST:event_jComboBoxCategoriaActionPerformed

    private void jComboBoxCombustivelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCombustivelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCombustivelActionPerformed

    private void jTabbedPaneMarcaModeloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneMarcaModeloMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPaneMarcaModeloMouseClicked

    private void jComboBoxMARCAModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMARCAModeloActionPerformed
        // Combobox marca da tela de cadastro de modelos
        try {
            Object selectedItem = jComboBoxMARCAModelo.getSelectedItem(); // Salva o item selecionado

            removeItemsFromComboBox();
            ComboBox();

           
            
                jComboBoxMARCAModelo.setSelectedItem(selectedItem); // Define o item selecionado novamente
            
        } catch (Exception ex) {
            Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jComboBoxMARCAModeloActionPerformed

    private void jComboBoxMARCAModeloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxMARCAModeloMouseClicked
         try {
        Object selectedItem = jComboBoxMARCAModelo.getSelectedItem(); // Salva o item selecionado

        removeItemsFromComboBox();
        ComboBox();

       
            jComboBoxMARCAModelo.setSelectedItem(selectedItem); // Define o item selecionado novamente
        
    } catch (Exception ex) {
        Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jComboBoxMARCAModeloMouseClicked

    private void jButton3_LISTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3_LISTAActionPerformed
        //Botão voltar veiculos

        try {
            jFormattedTextField_Placa.setEditable(true);
            MostrarDadosVeiculo();
            limparCamposVeiculo();
            System.out.println(veiculoControle.listagemDeVeiculos());
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }

    }//GEN-LAST:event_jButton3_LISTAActionPerformed

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
            java.util.logging.Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dialog_TelaVeiculos dialog = null;
                try {
                    dialog = new Dialog_TelaVeiculos(new javax.swing.JFrame(), true);
                } catch (Exception ex) {
                    Logger.getLogger(Dialog_TelaVeiculos.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JButton jButton1_INCLUIR;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton2_BUSCAR;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton3_LISTA;
    private javax.swing.JButton jButton4_ALTERAR;
    private javax.swing.JButton jButtonALTERARMARCA;
    private javax.swing.JButton jButtonALTERARMODELO;
    private javax.swing.JButton jButtonFOTOMARCA;
    private javax.swing.JButton jButtonFOTOMODELO;
    private javax.swing.JButton jButtonINSERIRMARCA;
    private javax.swing.JButton jButtonINSERIRMODELO;
    private javax.swing.JButton jButtonbuscarmarca;
    private javax.swing.JButton jButtonbuscarmarca1;
    private javax.swing.JButton jButtonvoltarmarca;
    private javax.swing.JButton jButtonvoltarmodelo;
    private javax.swing.JComboBox<String> jComboBoxCategoria;
    private javax.swing.JComboBox<String> jComboBoxCombustivel;
    private javax.swing.JComboBox<String> jComboBoxMARCAModelo;
    private javax.swing.JComboBox<String> jComboBoxModelo;
    private javax.swing.JComboBox<String> jComboBoxProprietario;
    private javax.swing.JTextField jFormattedTextField_Placa;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFOTOMARCA;
    private javax.swing.JLabel jLabelFOTOVEICULOModelo;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelModelo;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPaneMarcaModelo;
    private javax.swing.JTable jTableMarca;
    private javax.swing.JTable jTableModeloVeiculo;
    private javax.swing.JTable jTableVeiculo;
    private javax.swing.JTextField jTextFieldDescriModelo;
    private javax.swing.JTextField jTextFieldDescricaoMarca;
    private javax.swing.JTextField jTextFieldFOTOMARCA;
    private javax.swing.JTextField jTextFieldFOTOMODELOVEICULO;
    private javax.swing.JTextField jTextFieldIDMarca;
    private javax.swing.JTextField jTextFieldIDModelo;
    private javax.swing.JTextField jTextFieldKM;
    // End of variables declaration//GEN-END:variables
}
