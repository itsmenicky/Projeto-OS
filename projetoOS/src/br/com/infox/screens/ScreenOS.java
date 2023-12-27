/*
 * Copyright (C) 2023 Nickolas Martins
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package br.com.infox.screens;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Tela de Ordem de Serviço
 *
 * @author Nickolas Martins
 * @version 1.1
 */
public class ScreenOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private String tipo;

    /**
     * Criação do formulário de tela OS
     */
    public ScreenOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    /**
     * Método responsável por consultar um cliente no banco de dados
     */
    private void pesquisar() {
        String sql = "select id_client as Id, name as Nome, phone as fone from tb_clients where name like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtOSSearch.getText() + "%");
            rs = pst.executeQuery();
            tbClients.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * Método responsável por setar campos da tabela de clientes no formulário
     */
    private void setar_campo() {
        int setar = tbClients.getSelectedRow();
        txtOSIdCli.setText(tbClients.getModel().getValueAt(setar, 0).toString());
    }

    /**
     * Método responsável por adicionar uma OS ao banco de dados
     */
    private void emitir_os() {
        String sql = "insert into tb_os (tipo, situacao, equipment,defect,service,technician,valor,id_client) values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cbOSSit.getSelectedItem().toString());
            pst.setString(3, txtOSEquip.getText());
            pst.setString(4, txtOSDef.getText());
            pst.setString(5, txtOSServ.getText());
            pst.setString(6, txtOSTec.getText());
            //substituindo virgula pelo ponto
            pst.setString(7, txtOSTotal.getText().replace(",", "."));
            pst.setString(8, txtOSIdCli.getText());
            if (txtOSIdCli.getText().isEmpty() || txtOSEquip.getText().isEmpty() || txtOSDef.getText().isEmpty() || cbOSSit.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int emissao = pst.executeUpdate();
                if (emissao > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso!");
                    txtOSIdCli.setText(null);
                    txtOSEquip.setText(null);
                    txtOSDef.setText(null);
                    txtOSServ.setText(null);
                    txtOSTec.setText(null);
                    txtOSTotal.setText(null);
                    txtOSSearch.setText(null);
                    ((DefaultTableModel) tbClients.getModel()).setRowCount(0);
                    cbOSSit.setSelectedItem(" ");
                    btnEditOS.setEnabled(false);
                    btnOSDelete.setEnabled(false);
                    btnOSPrint.setEnabled(false);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * Método responsável por pesquisar uma OS no banco de dados
     */
    private void pesquisar_os() {
        String num_os = JOptionPane.showInputDialog("Insira o número da OS");
        String sql = "select os,date_format(date_time, '%d/%m/%Y - %H:%i'), tipo, situacao, equipment, defect, service, technician, valor, id_client from tb_os where os=" + num_os;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOS.setText(rs.getString(1));
                txtOSDate.setText(rs.getString(2));

                String rbTipo = rs.getString(3);
                if (rbTipo.equals("Orcamento")) {
                    rbtOSOrc.setSelected(true);
                    tipo = "Orcamento";
                } else {
                    rbtOS.setSelected(true);
                    tipo = "Ordem de servico";
                }
                cbOSSit.setSelectedItem(rs.getString(4));
                txtOSEquip.setText(rs.getString(5));
                txtOSDef.setText(rs.getString(6));
                txtOSServ.setText(rs.getString(7));
                txtOSTec.setText(rs.getString(8));
                txtOSTotal.setText(rs.getString(9));
                txtOSIdCli.setText(rs.getString(10));

                btnAddOS.setEnabled(false);
                txtOSSearch.setEnabled(false);
                tbClients.setVisible(false);
                btnEditOS.setEnabled(true);
                btnOSDelete.setEnabled(true);
                btnOSPrint.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "OS não encontrada");
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS Inválida");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    /**
     * Método responsável por atualizar uma OS no banco de dados
     */
    private void alterar() {
        String sql = "update tb_os set tipo=?, situacao=?, equipment=?, defect=?, service=?, technician=?, valor=? where os=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cbOSSit.getSelectedItem().toString());
            pst.setString(3, txtOSEquip.getText());
            pst.setString(4, txtOSDef.getText());
            pst.setString(5, txtOSServ.getText());
            pst.setString(6, txtOSTec.getText());
            pst.setString(7, txtOSTotal.getText());
            pst.setString(8, txtOS.getText());
            if (txtOSIdCli.getText().isEmpty() || txtOSEquip.getText().isEmpty() || txtOSDef.getText().isEmpty() || cbOSSit.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int atualizacao = pst.executeUpdate();
                if (atualizacao > 0) {
                    JOptionPane.showMessageDialog(null, "OS atualizada com sucesso!");
                    txtOS.setText(null);
                    txtOSDate.setText(null);
                    txtOSIdCli.setText(null);
                    txtOSEquip.setText(null);
                    txtOSDef.setText(null);
                    txtOSServ.setText(null);
                    txtOSTec.setText(null);
                    txtOSTotal.setText(null);
                    btnAddOS.setEnabled(true);
                    txtOSSearch.setEnabled(true);
                    tbClients.setVisible(true);
                    cbOSSit.setSelectedItem(" ");
                    btnEditOS.setEnabled(false);
                    btnOSDelete.setEnabled(false);
                    btnOSPrint.setEnabled(false);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    /**
     * Método responsável por deletar uma OS do banco de dados
     */
    private void delete_os() {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir essa OS?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            String sql = "delete from tb_os where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOS.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS excluída com sucesso.");
                }
                btnAddOS.setEnabled(true);
                txtOSSearch.setEnabled(true);
                tbClients.setVisible(true);
                txtOS.setText(null);
                txtOSDate.setText(null);
                txtOSIdCli.setText(null);
                txtOSEquip.setText(null);
                txtOSDef.setText(null);
                txtOSServ.setText(null);
                txtOSTec.setText(null);
                txtOSTotal.setText(null);
                cbOSSit.setSelectedItem(" ");
                btnEditOS.setEnabled(false);
                btnOSDelete.setEnabled(false);
                btnOSPrint.setEnabled(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * Método responsável por fazer a impressão de uma OS
     */
    private void imprimir_os() {
        conexao = ModuloConexao.conector();
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desta OS?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                HashMap<String, Object> filtro = new HashMap<>();
filtro.put("os", Integer.parseInt(txtOS.getText()));
                filtro.put("os", Integer.parseInt(txtOS.getText()));
                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/Reports/OS.jasper"), filtro, conexao);
                JasperViewer.viewReport(print, false);
                 btnAddOS.setEnabled(true);
                txtOSSearch.setEnabled(true);
                tbClients.setVisible(true);
                txtOS.setText(null);
                txtOSDate.setText(null);
                txtOSIdCli.setText(null);
                txtOSEquip.setText(null);
                txtOSDef.setText(null);
                txtOSServ.setText(null);
                txtOSTec.setText(null);
                txtOSTotal.setText(null);
                cbOSSit.setSelectedItem(" ");
                btnEditOS.setEnabled(false);
                btnOSDelete.setEnabled(false);
                btnOSPrint.setEnabled(false);
            } catch (NumberFormatException | JRException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOS = new javax.swing.JTextField();
        txtOSDate = new javax.swing.JTextField();
        rbtOSOrc = new javax.swing.JRadioButton();
        rbtOS = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cbOSSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtOSSearch = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtOSIdCli = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbClients = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtOSEquip = new javax.swing.JTextField();
        txtOSDef = new javax.swing.JTextField();
        txtOSTec = new javax.swing.JTextField();
        txtOSServ = new javax.swing.JTextField();
        txtOSTotal = new javax.swing.JTextField();
        btnAddOS = new javax.swing.JButton();
        btnCheckOS = new javax.swing.JButton();
        btnEditOS = new javax.swing.JButton();
        btnOSDelete = new javax.swing.JButton();
        btnOSPrint = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("OS");
        setPreferredSize(new java.awt.Dimension(640, 519));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("N° OS");

        jLabel2.setText("Data");

        txtOS.setEditable(false);
        txtOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOSActionPerformed(evt);
            }
        });

        txtOSDate.setEditable(false);
        txtOSDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        buttonGroup1.add(rbtOSOrc);
        rbtOSOrc.setText("Orçamento");
        rbtOSOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOSOrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOS);
        rbtOS.setText("Ordem de Serviço");
        rbtOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOSOrc)
                        .addGap(18, 18, 18)
                        .addComponent(rbtOS))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOSDate, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOSDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOSOrc)
                    .addComponent(rbtOS))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel3.setText("Situação");

        cbOSSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Na Bancada", "Entrega OK", "Orçamento REPROVADO", "Aguardando Aprovação", "Aguardando Peças", "Abandonado pelo Cliente", "Retornou" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtOSSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOSSearchKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icons/checkSearchIcon.png"))); // NOI18N

        jLabel5.setText("*Id");

        txtOSIdCli.setEditable(false);

        tbClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Fone"
            }
        ));
        tbClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbClientsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbClients);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtOSSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOSIdCli, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtOSIdCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtOSSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("*Equipamento");

        jLabel7.setText("*Defeito");

        jLabel8.setText("Total");

        jLabel9.setText("Serviço");

        jLabel10.setText("Técnico");

        txtOSEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOSEquipActionPerformed(evt);
            }
        });

        txtOSTotal.setText("0");

        btnAddOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icons/addOS.png"))); // NOI18N
        btnAddOS.setToolTipText("Criar OS");
        btnAddOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddOS.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAddOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOSActionPerformed(evt);
            }
        });

        btnCheckOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icons/checkOS.png"))); // NOI18N
        btnCheckOS.setToolTipText("Checar OS");
        btnCheckOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCheckOS.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCheckOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckOSActionPerformed(evt);
            }
        });

        btnEditOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icons/editOS.png"))); // NOI18N
        btnEditOS.setToolTipText("Editar OS");
        btnEditOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditOS.setEnabled(false);
        btnEditOS.setPreferredSize(new java.awt.Dimension(80, 80));
        btnEditOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditOSActionPerformed(evt);
            }
        });

        btnOSDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icons/deleteOS.png"))); // NOI18N
        btnOSDelete.setToolTipText("Deletar OS");
        btnOSDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSDelete.setEnabled(false);
        btnOSDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOSDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSDeleteActionPerformed(evt);
            }
        });

        btnOSPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icons/printOS.png"))); // NOI18N
        btnOSPrint.setToolTipText("Imprimir OS");
        btnOSPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSPrint.setEnabled(false);
        btnOSPrint.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOSPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbOSSit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtOSTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtOSEquip, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtOSServ, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(txtOSDef, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtOSTec, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(btnAddOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCheckOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnOSDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnOSPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbOSSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOSEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOSDef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOSServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOSTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOSTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOSDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOSPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(641, 546));
    }// </editor-fold>//GEN-END:initComponents

    private void txtOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOSActionPerformed

    /**
     * Evento responsável por atribuir um valor a variável tipo através dos
     * radio buttons
     *
     * @param evt
     */
    private void rbtOSOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOSOrcActionPerformed

        tipo = "Orcamento";
    }//GEN-LAST:event_rbtOSOrcActionPerformed

    private void txtOSEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOSEquipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOSEquipActionPerformed

    /**
     * Evento responsável por chamar o método pesquisar
     *
     * @param evt
     */
    private void txtOSSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOSSearchKeyReleased
        pesquisar();
    }//GEN-LAST:event_txtOSSearchKeyReleased

    /**
     * Evento responsável por chamar o método setar campo
     *
     * @param evt
     */
    private void tbClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbClientsMouseClicked
        setar_campo();
    }//GEN-LAST:event_tbClientsMouseClicked

    /**
     * Evento responsável por atribuir um valor a variável tipo através dos
     * radio buttons
     *
     * @param evt
     */
    private void rbtOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOSActionPerformed

        tipo = "Ordem de Servico";
    }//GEN-LAST:event_rbtOSActionPerformed

    /**
     * Evento responsável por, na abertura do fomulário, setar automaticamento o
     * radio button Orcamento
     *
     * @param evt
     */
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

        rbtOSOrc.setSelected(true);
        tipo = "Orcamento";
    }//GEN-LAST:event_formInternalFrameOpened

    /**
     * Evento responsável por chamar o método emitir_os
     *
     * @param evt
     */
    private void btnAddOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOSActionPerformed
        emitir_os();
    }//GEN-LAST:event_btnAddOSActionPerformed

    /**
     * Evento responsável por chamar o método pesquisar_os
     *
     * @param evt
     */
    private void btnCheckOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckOSActionPerformed
        pesquisar_os();
    }//GEN-LAST:event_btnCheckOSActionPerformed

    /**
     * Evento responsável por chamar o método alterar
     *
     * @param evt
     */
    private void btnEditOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOSActionPerformed
        alterar();
    }//GEN-LAST:event_btnEditOSActionPerformed

    /**
     * Evento responsável por chamar o método delete_os
     *
     * @param evt
     */
    private void btnOSDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSDeleteActionPerformed
        delete_os();
    }//GEN-LAST:event_btnOSDeleteActionPerformed

    /**
     * Evento responsável por chamar o método imprimir_os
     *
     * @param evt
     */
    private void btnOSPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSPrintActionPerformed
        imprimir_os();
    }//GEN-LAST:event_btnOSPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddOS;
    private javax.swing.JButton btnCheckOS;
    private javax.swing.JButton btnEditOS;
    private javax.swing.JButton btnOSDelete;
    private javax.swing.JButton btnOSPrint;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbOSSit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtOS;
    private javax.swing.JRadioButton rbtOSOrc;
    private javax.swing.JTable tbClients;
    private javax.swing.JTextField txtOS;
    private javax.swing.JTextField txtOSDate;
    private javax.swing.JTextField txtOSDef;
    private javax.swing.JTextField txtOSEquip;
    private javax.swing.JTextField txtOSIdCli;
    private javax.swing.JTextField txtOSSearch;
    private javax.swing.JTextField txtOSServ;
    private javax.swing.JTextField txtOSTec;
    private javax.swing.JTextField txtOSTotal;
    // End of variables declaration//GEN-END:variables
}
