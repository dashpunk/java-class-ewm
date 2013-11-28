package br.inf.id2.mapa.bean;

import br.inf.id2.common.util.Executa;
import br.inf.id2.common.util.Uteis;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.DBConnect;

import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ExploracaoPecuaria extends psdi.webclient.beans.storeroom.StoreroomAppBean {

    String valor;
    private String mboName;
    String where;
    String mboId;

    /**
     * Método construtor de ID2ExploracaoPecuariaAppBean
     */
    public ExploracaoPecuaria() {
    }

    /**
     *
     * Sobrescrita do método validate BMXZZ0002E
     *<p>
     * Valida se a data de encerramento é posterior a de statu
     * @since 30/03/2010
     */
    public int SAVE() throws MXException, RemoteException {
        System.out.println("SAVE");
        String param[] = {new String()};

        Date aDataStatus = getMbo().getDate("ID2SUSPEITAS.ID2DATSUS");
        Date aDataEncerramento = getMbo().getDate("ID2SUSPEITAS.ID2DATENC");

        if (aDataEncerramento != null) {
            if (!(aDataEncerramento.after(aDataStatus))) {
                throw new MXApplicationException("company", "DataEncerramentoInvalida", param);
            }
        }

        if (getMbo().getDouble("ID2ARETOT") > getMbo().getMboSet("ID2VWLOC01").getMbo(0).getDouble("ID2ARETOT")) {
            throw new MXApplicationException("company", "AreaTotalInvalida", param);
        }

        if (getMbo().getMboSet("ID2LUCTYPE01").count() == 0) {
            throw new MXApplicationException("company", "SemProdutores", param);
        }
        /* if (getMbo().isNew()) {
        System.out.println("------- isNew");
        getMbo().setFieldFlag("LOCATION", MboConstants.READONLY, false);
        System.out.println("------- flag");
        MboSet exploracoes;
        exploracoes = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC04", sessionContext.getUserInfo());
        exploracoes.setWhere("id2codprop = '" + getMbo().getString("ID2CODPROP") + "'");
        System.out.println("------- before reset");
        exploracoes.reset();
        System.out.println("------- after reset");
        getMbo().setValue("LOCATION", getMbo().getString("ID2CODPROP").concat("-").concat("" + (exploracoes.count() + 1)), MboConstants.NOVALIDATION_AND_NOACTION);
        System.out.println("------- after setValue " + getMbo().getString("ID2CODPROP").concat("-").concat("" + (exploracoes.count() + 1)));
        getMbo().setFieldFlag("LOCATION", MboConstants.READONLY, true);
        System.out.println("------- after flag");
        }
         *
         */
        //validaDoenca();

        System.out.println("QBBBBBBBBBBBBBBBB before save");
        //inserirInventoryGalpao();
        int resultado = super.SAVE();
        System.out.println("QBBBBBBBBBBBBBBBB after save");

        System.out.println("----- save " + resultado);

        //updateLocation();
        /*try {
        Thread.sleep(1000);
        refreshTable();
        reloadTable();
        initializeApp();
        initialize();

        } catch (InterruptedException ex) {
        Logger.getLogger(ExploracaoPecuaria.class.getName()).log(Level.SEVERE, null, ex);
        }
         * 
         */
        System.out.println("----- after updateLocation ");



        return resultado;
    }

    private void updateLocation() throws RemoteException, MXException {

        if (!getMbo().getString("ID2LOCATION").equalsIgnoreCase(getMbo().getString("LOCATION"))) {

            System.out.println("----- updateLocation init");
            try {
                Properties prop;
                prop = MXServer.getMXServer().getConfig();
                String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
                String url = prop.getProperty("mxe.db.url");
                String username = prop.getProperty("mxe.db.user", "dbmaximo");
                String password = prop.getProperty("mxe.db.password", "id2maximo");


                System.out.println("---------- location = " + getMbo().getString("LOCATION"));
                System.out.println("---------- id2location = " + getMbo().getString("ID2LOCATION"));
                Class.forName(driver).newInstance();
                java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
                Statement stmt = conexao.createStatement();
                PreparedStatement ps = conexao.prepareStatement("UPDATE LOCATIONS SET LOCATION = ? WHERE LOCATION = ?");
                ps.setString(1, getMbo().getString("ID2LOCATION"));
                ps.setString(2, getMbo().getString("LOCATION"));


                System.out.println("----- updateLocation execute b");
                int r = ps.executeUpdate();
                System.out.println("----- updateLocation execute a " + r);

                ps = conexao.prepareStatement("UPDATE ID2LOCATIONUSERCUST SET LOCATION = ? WHERE LOCATION = ?");
                ps.setString(1, getMbo().getString("ID2LOCATION"));
                ps.setString(2, getMbo().getString("LOCATION"));


                System.out.println("----- updateLocation execute2 b");
                r = ps.executeUpdate();
                System.out.println("----- updateLocation execute2 a " + r);
                conexao.commit();
                System.out.println("----- commit");

                conexao.close();
            } catch (Exception e) {
                System.out.println("----- e = " + e.getMessage());
            }
        }

    }

    private void validaDoenca() throws RemoteException {

        int contador1 = 0;
        int contador2 = 0;
        int contador3 = 0;
        System.out.println("----- validaDoenca init");
        Properties prop;
        prop = MXServer.getMXServer().getConfig();
        String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
        String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@192.168.1.215:1521:dftop1");
        String username = prop.getProperty("mxe.db.user", "dbmaximo");
        String password = prop.getProperty("mxe.db.password", "id2maximo");
        try {

            Class.forName(driver).newInstance();
            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
            Statement stmt = conexao.createStatement();
            PreparedStatement ps1 = conexao.prepareStatement("SELECT MACALENID, MADTINI, MADTFIM from (select * from MACALEN order by MADTINI desc) where HAZARDID = \'0001\' and ROWNUM <= 2");

            PreparedStatement ps2 = conexao.prepareStatement("SELECT ID2CODEXPPEC, ID2DATVAC, MACALENID from ID2VACEXPPEC WHERE ID2CODEXPPEC = :VALOR");
            ps2.setString(1, getMbo().getString("LOCATION"));

            ResultSet rs1;
            ResultSet rs2 = ps2.executeQuery();


            PreparedStatement ps4;

            boolean encontrado;
            System.out.println("----- validaDoenca rs2 b");
            while (rs2.next()) {
                System.out.println("----- validaDoenca rs2 contador " + --contador2);
                encontrado = false;
                rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    System.out.println("----- validaDoenca rs1 contador " + --contador1);
                    if (rs2.getInt("MACALENID") == rs1.getInt("MACALENID")) {
                        encontrado = true;
                        System.out.println("----- validaDoenca rs1 encontrado");

                        break;
                    }
                }
                PreparedStatement ps3 = conexao.prepareStatement("select count(*) total from ID2EXPDOENCA where MACALENID = :AMACALENID AND ID2CODEXPPEC = :AID2CODEXPPEC");
                ps3.setInt(1, rs2.getInt("MACALENID"));
                ps3.setString(2, rs2.getString("ID2CODEXPPEC"));
                ResultSet rs3 = ps3.executeQuery();
                rs3.next();
                System.out.println("----- validaDoenca rs3");
                boolean registroExiste = (rs3.getInt("total") != 0);

                System.out.println("----- validaDoenca registroExiste " + registroExiste);

                if (registroExiste) {
                    ps4 = conexao.prepareStatement("UPDATE ID2EXPDOENCA SET ID2APTO = :ENCONTRADO where MACALENID = :AMACALENID AND ID2CODEXPPEC = :AID2CODEXPPEC");
                } else {
                    ps4 = conexao.prepareStatement("INSERT INTO ID2EXPDOENCA (ID2APTO, MACALENID, ID2CODEXPPEC) VALUES (:ENCONTRADO, :AMACALENID , :AID2CODEXPPEC)");
                }
                System.out.println("----- validaDoenca ps4 encontrado   " + encontrado);
                System.out.println("----- validaDoenca ps4 MACALENID    " + rs2.getInt("MACALENID"));
                System.out.println("----- validaDoenca ps4 ID2CODEXPPEC " + rs2.getString("ID2CODEXPPEC"));
                ps4.setBoolean(1, encontrado);
                ps4.setInt(2, rs2.getInt("MACALENID"));
                ps4.setString(3, rs2.getString("ID2CODEXPPEC"));


                int resultado = ps4.executeUpdate();

                System.out.println("----- validaDoenca ps4 resultado   " + resultado);

            }

            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            System.out.println("----- e = " + e.getMessage());
        }
        System.out.println("----- validaDoenca end");
    }

    public void ADDROWONTABLE() {
        System.out.println("ADDROWONTABLE");
        WebClientEvent event = sessionContext.getCurrentEvent();
        System.out.println("Teste 1");
        Utility.sendEvent(new WebClientEvent("addrow", (String) event.getValue(), event.getValue(), sessionContext));
        System.out.println("Teste 2");
    }

    //@Override
    public synchronized void fireDataChangedEvent2(DataBean speaker) {
        System.out.println("fireDataChangedEvent");
        super.fireDataChangedEvent(speaker);
        try {

            if (speaker.getUniqueIdName().equalsIgnoreCase("LOCATIONSID")) {
                if (getMbo().isNew()) {
                    if (!getMbo().getString("ID2LOCATION").equalsIgnoreCase(getMbo().getString("LOCATION"))) {

                        if (valor == null) {
                            valor = getMbo().getString("LOCATION");
                            System.out.println("--------- valor " + valor);
                        }
                        System.out.println("------- isNew");
                        getMbo().setFieldFlag("LOCATION", MboConstants.READONLY, false);
                        System.out.println("------- flag");
                        MboSet exploracoes;
                        exploracoes = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC04", getMbo().getUserInfo());
                        exploracoes.setWhere("id2codprop = '" + getMbo().getString("ID2CODPROP") + "'");
                        System.out.println("------- before reset");
                        exploracoes.reset();
                        System.out.println("------- after reset");
                        getMbo().setValue("ID2LOCATION", getMbo().getString("ID2CODPROP").concat("-").concat("" + (exploracoes.count() + 1)));
                        getMbo().setValue("LOCATION", getMbo().getString("ID2CODPROP").concat("-").concat("" + (exploracoes.count() + 1)));
                        //System.out.println("------- after setValue " + getMbo().getString("ID2CODPROP").concat("-").concat("" + (exploracoes.count() + 1)));
                        getMbo().setFieldFlag("LOCATION", MboConstants.READONLY, true);
                        System.out.println("------- after flag");
                    }
                }



                //MboSetRemote relacionamentos = recuperaRelacionamentos(getMbo());
                //System.out.println("-------- count relation a " + relacionamentos.count());

                /* MboRemote mbo;
                for (int i = 0; i < getMbo().getMboSet("ID2LUCTYPE01").count(); i++) {
                System.out.println("############## before "+getMbo().getMboSet("ID2LUCTYPE01").getMbo(i).getString("LOCATION"));
                getMbo().getMboSet("ID2LUCTYPE01").getMbo(i).setValue("LOCATION", getMbo().getString("LOCATION"));
                System.out.println("############### i " + i);
                //getMbo().getMboSet("ID2LUCTYPE01").getMbo(i).setFieldFlag("LOCATION", MboConstants.READONLY, false);
                //getMbo().getMboSet("ID2LUCTYPE01").getMbo(i).setValue("LOCATION", getMbo().getString("LOCATION"));
                //getMbo().getMboSet("ID2LUCTYPE01").getMbo(i).setFieldFlag("LOCATION", MboConstants.READONLY, true);

                }
                System.out.println("############### FIM");
                 *
                 */



                /*
                for (int i = 0; i < relacionamentos.count(); i++) {
                System.out.println("--------------- i " + relacionamentos.getMbo(i).getString("NAME"));
                MboSetRemote aMboSet = getMbo().getMboSet(relacionamentos.getMbo(i).getString("NAME"));
                System.out.println("-------- count relation b " + aMboSet.count());
                if (relacionamentos.getMbo(i).getString("NAME").equalsIgnoreCase("ID2LUCTYPE01")) {
                for (int j = 0; j < aMboSet.count(); j++) {
                System.out.println("---------- j " + j);
                try {
                aMboSet.getMbo(j).setFieldFlag("LOCATION", MboConstants.READONLY, false);
                aMboSet.getMbo(j).setValue("LOCATION", getMbo().getString("LOCATION"));
                aMboSet.getMbo(j).setFieldFlag("LOCATION", MboConstants.READONLY, true);
                System.out.println("-------- foi");
                } catch (Exception e) {
                System.out.println("-------- n foi foi " + e.getMessage());
                }
                }
                }
                }
                 * 
                 */

            }
        } catch (MXException ex) {
            Logger.getLogger(ExploracaoPecuaria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ExploracaoPecuaria.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private MboSet recuperaRelacionamentos(MboRemote mbo) throws MXException, RemoteException {

        MboSet relacionamentos;
        relacionamentos = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MAXRELATIONSHIP", mbo.getUserInfo());
        System.out.println("###################### QUANTIDADE DE RELACIONAMENTOS ANTES = " + relacionamentos.count());
        System.out.println("-------------- mbo.getName() " + mbo.getName());
        relacionamentos.setWhere("PARENT = '" + mbo.getName() + "'");
        relacionamentos.reset();
        System.out.println("###################### QUANTIDADE DE RELACIONAMENTOS DEPOIS = " + relacionamentos.count());
        return relacionamentos;

    }

    private void inserirInventoryGalpao() throws MXException, RemoteException {

        MboRemote mbo;

        for (int i = 0; ((mbo = getMbo().getMboSet("MATBNUCAVE").getMbo(i)) != null); i++) {

            if (mbo.isNew()) {
                System.out.println("///////////////////////// INVENTORY");
                try {
                    Executa.adicionaInventory((MboSet) mbo.getMboSet("ITEM"), (MboSet) mbo.getMboSet("INVENTORY"), (MboSet) mbo.getMboSet("INVBALANCES"),
                            new Object[]{"#" + mbo.getString("LOCATION"), "ITEMNUM", "ITEMSETID", "#CADA", "#CADA", /*"ORGID",  "SITEID", "ESTOC",   *0,*/ 0, 0, 0, 0, 0, 1, 1, 1, /*inventoryId,*/ 1 /* status*/, "#" + mbo.getString("LOCATION"), "#" + mbo.getString("DESCRIPTION")},
                            new String[]{"LOCATION", "ITEMNUM", "ITEMSETID", "ORDERUNIT", "ISSUEUNIT", /*"ORGID",  "SITEID", "CATEGORY", "CCF", */ "DELIVERYTIME", "ISSUE1YRAGO", "ISSUE2YRAGO", "ISSUE3YRAGO", "ISSUEYTD", "MAXLEVEL", "MINLEVEL", "ORDERQTY", /*"INVENTORYID", */ "INTERNAL" /*, "STATUS", "STATUSDATE"*/, "BINNUM", "LOTNUM"},
                            new Object[]{"#" + mbo.getString("LOCATION"), "#" + mbo.getString("DESCRIPTION")},
                            new String[]{"BINNUM", "LOTNUM"}); //destino
                } catch (Exception ex) {
                    Uteis.espera("*************** Erro em INVENTORY = " + ex.getMessage());
                }

            }
        }


    }
}
