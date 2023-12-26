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

import br.com.infox.dal.ModuloConexao;
import java.io.InputStream;
import java.util.Date;
import java.text.DateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Interface principal do sistema
 *
 * @author Nickolas Martins
 * @version 1.1
 */
public class MainScreen extends javax.swing.JFrame {

    Connection conexao = null;

    /**
     * Criação da tela principal
     */
    public MainScreen() {
        initComponents();
        conexao = ModuloConexao.conector();
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        desktop = new javax.swing.JDesktopPane();
        lblUser = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        menuCad = new javax.swing.JMenu();
        menuCadCli = new javax.swing.JMenuItem();
        MenuCadOS = new javax.swing.JMenuItem();
        menuCadUser = new javax.swing.JMenuItem();
        menuRel = new javax.swing.JMenu();
        menuRelCli = new javax.swing.JMenuItem();
        menuRelSer = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuHelpAbout = new javax.swing.JMenuItem();
        menuOp = new javax.swing.JMenu();
        menuOpExit = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema OS");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblUser.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUser.setText("Usuário");

        lblDate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDate.setText("Data");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Bem-Vindo");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icons/osIcon.png"))); // NOI18N

        menuCad.setText("Cadastro");
        menuCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadActionPerformed(evt);
            }
        });

        menuCadCli.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuCadCli.setText("Cliente");
        menuCadCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadCliActionPerformed(evt);
            }
        });
        menuCad.add(menuCadCli);

        MenuCadOS.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MenuCadOS.setText("OS");
        MenuCadOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuCadOSActionPerformed(evt);
            }
        });
        menuCad.add(MenuCadOS);

        menuCadUser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuCadUser.setText("Usuarios");
        menuCadUser.setEnabled(false);
        menuCadUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadUserActionPerformed(evt);
            }
        });
        menuCad.add(menuCadUser);

        menu.add(menuCad);

        menuRel.setText("Relatório");
        menuRel.setEnabled(false);

        menuRelCli.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuRelCli.setText("Clientes");
        menuRelCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelCliActionPerformed(evt);
            }
        });
        menuRel.add(menuRelCli);

        menuRelSer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuRelSer.setText("Serviços");
        menuRelSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelSerActionPerformed(evt);
            }
        });
        menuRel.add(menuRelSer);

        menu.add(menuRel);

        menuHelp.setText("Ajuda");

        menuHelpAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuHelpAbout.setText("Sobre");
        menuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuHelpAbout);

        menu.add(menuHelp);

        menuOp.setText("Opções");

        menuOpExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuOpExit.setText("Sair");
        menuOpExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpExitActionPerformed(evt);
            }
        });
        menuOp.add(menuOpExit);

        menu.add(menuOp);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(desktop)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUser)
                    .addComponent(lblDate)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(desktop)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUser)
                        .addGap(162, 162, 162)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                        .addComponent(lblDate)
                        .addGap(25, 25, 25))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Evento responsável por abrir a tela Clientes no desktop
     *
     * @param evt
     */
    private void menuCadCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadCliActionPerformed
        ScreenClient client = new ScreenClient();
        client.setVisible(true);
        desktop.add(client);
    }//GEN-LAST:event_menuCadCliActionPerformed

    private void relatorio_servicos() {
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a emissão deste relatório?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                conexao = ModuloConexao.conector();
                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/Reports/servicos.jasper"), null, conexao);
                JasperViewer.viewReport(print, false);
            } catch (JRException e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
    }
    
    private void relatorio_clientes(){
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a emissão deste relatório?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                conexao = ModuloConexao.conector();
                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/Reports/Clientes.jasper"), null, conexao);
                JasperViewer.viewReport(print, false);
            } catch (JRException e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
    }

    /**
     * Evento responsável por mostrar a data atual na tela principal
     *
     * @param evt
     */
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        lblDate.setText(formatador.format(data));
    }//GEN-LAST:event_formWindowActivated

    /**
     * Evento responsável pela saída do programa
     *
     * @param evt
     */
    private void menuOpExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpExitActionPerformed
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_menuOpExitActionPerformed

    /**
     * Evento responsável pela abertura da tela Sobre no desktop
     *
     * @param evt
     */
    private void menuHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpAboutActionPerformed
        AboutScreen sobre = new AboutScreen();
        sobre.setVisible(true);
    }//GEN-LAST:event_menuHelpAboutActionPerformed

    /**
     * Evento responsável pela abertura da tela Usuários no desktop
     *
     * @param evt
     */
    private void menuCadUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadUserActionPerformed
        ScreenUser usuario = new ScreenUser();
        usuario.setVisible(true);
        desktop.add(usuario);
    }//GEN-LAST:event_menuCadUserActionPerformed

    private void menuCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadActionPerformed
        ScreenClient cliente = new ScreenClient();
        cliente.setVisible(true);
        desktop.add(cliente);
    }//GEN-LAST:event_menuCadActionPerformed

    /**
     * Evento responsável pela abertura da tela OS no desktop
     *
     * @param evt
     */
    private void MenuCadOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuCadOSActionPerformed
        ScreenOS os = new ScreenOS();
        os.setVisible(true);
        desktop.add(os);
    }//GEN-LAST:event_MenuCadOSActionPerformed

    /**
     * Evento responsável pela emissão de relatório de Clientes
     *
     * @param evt
     */
    private void menuRelCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelCliActionPerformed
         relatorio_clientes();
    }//GEN-LAST:event_menuRelCliActionPerformed

    /**
     * Evento responsável pela emissão do relatório de Serviços
     *
     * @param evt
     */
    private void menuRelSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelSerActionPerformed
        relatorio_servicos();
    }//GEN-LAST:event_menuRelSerActionPerformed

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
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuCadOS;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblDate;
    public static javax.swing.JLabel lblUser;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menuCad;
    private javax.swing.JMenuItem menuCadCli;
    public static javax.swing.JMenuItem menuCadUser;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuHelpAbout;
    private javax.swing.JMenu menuOp;
    private javax.swing.JMenuItem menuOpExit;
    public static javax.swing.JMenu menuRel;
    private javax.swing.JMenuItem menuRelCli;
    private javax.swing.JMenuItem menuRelSer;
    // End of variables declaration//GEN-END:variables
}
