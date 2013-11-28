package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2BloqGta extends AppBean {

    public ID2BloqGta() {
        super();
    }

    @Override
    public synchronized void fireDataChangedEvent(DataBean speaker) {
        try {
            //System.out.println("-------------- fireDataChangedEvent speaker " + speaker.getUniqueIdName());

            limpaMboSet(app.getDataBean("MAINRECORD").getMbo().getString("ID2LOCALBLOQ"));

            //getMboSet().unselectAll();
            refreshTable();
            reloadTable();
        } catch (MXException ex) {
            Logger.getLogger(ID2BloqGta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ID2BloqGta.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.fireDataChangedEvent(speaker);

    }

    public void bloqueioTransito() throws MXException, RemoteException {

        MboRemote mbo;
        MboRemote mbob;
        try {
            String mboSetPercorrer = "ID2BLOQ" + app.getDataBean("MAINRECORD").getMbo().getString("ID2LOCALBLOQ");
            System.out.println("----- updateLocation init novo");
            Properties prop;
            prop = MXServer.getMXServer().getConfig();
            String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url");
            String username = prop.getProperty("mxe.db.user", "dbmaximo");
            String password = prop.getProperty("mxe.db.password", "id2maximo");
            Class.forName(driver).newInstance();
            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
            Statement stmt = conexao.createStatement();
            System.out.println("---- primeiro for");
            /*for (int i = 0; ((mbo = app.getDataBean("MAINRECORD").getMbo().getMboSet(mboSetPercorrer).getMbo(i)) != null); i++) {
            if (mbo.isSelected()) {
            if (mbo.isNull("MAOBSBLOQ")) {
            throw new MXApplicationException("id2bloqgta", "maobsbloqIsNull");
            }
            }
            }*/

            System.out.println("---- segundo for " + app.getDataBean("MAINRECORD").getMbo().getMboSet(mboSetPercorrer).count());
            for (int i = 0; ((mbo = app.getDataBean("MAINRECORD").getMbo().getMboSet(mboSetPercorrer).getMbo(i)) != null); i++) {
                System.out.println("-------- sf i " + i);
                if (mbo.isSelected()) {
                    System.out.println("-------- sf i selecionado " + i);
                    //System.out.println("------------------ selecionada " + i + " | Location = " + mbo.getString("LOCATION"));
                /*
                     * Alteração requerida pelo Bruno Freitas devido ao bloqueio ser por espécie
                    if (mbo.getString("ID2BLOCKSTATUS").equalsIgnoreCase("S")) {
                    mbo.setValue("ID2BLOCKSTATUS", "N");
                    } else {
                    mbo.setValue("ID2BLOCKSTATUS", "S");
                    }
                     *
                     */
                    System.out.println("-------- sf j " + app.getDataBean("MAINRECORD").getMbo().getMboSet(mboSetPercorrer).getMbo(i).getMboSet("ID2ESPLOCAL").count());
                    for (int j = 0; ((mbob = app.getDataBean("MAINRECORD").getMbo().getMboSet(mboSetPercorrer).getMbo(i).getMboSet("ID2ESPLOCAL").getMbo(j)) != null); j++) {
                        System.out.println("-------- sf j " + j);
                        if (mbob.isSelected()) {
                            System.out.println("-------- selecionado j " + j);

                            Integer opcao = Integer.valueOf(app.getDataBean("MAINRECORD").getMbo().getString("ID2LOCALBLOQ"));

                            //UF
                            if (opcao == 1) {
                                System.out.println("------ opcao 1");

                                //UF -> Propriedade
                                String sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select location from id2vwloc01 where id2locuf = ?)";

                                System.out.println("sql = " + sql);
                                PreparedStatement ps = conexao.prepareStatement(sql);

                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                int r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);

                                //UF -> Exploração
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select location from id2vwloc04 where id2locuf = ?)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);


                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));
                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);


                                //UF -> Aglomeração
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select location from id2vwloc03 where id2locuf = ?)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);


                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);


                                //UF -> Abatedouro
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select location from id2vwloc02 where id2locuf = ?)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);


                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);

                                //UF -> Área
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select a.location from ID2AREALOC a, ID2VWLOCH b where a.id2arealoc = ? and a.location = b.filho)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);

                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);


                            }


                            //Município
                            if (opcao == 2) {
                                System.out.println("------ opcao 2");

                                //Município -> Propriedade
                                String sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select location from id2vwloc01 where id2codmun = ?)";

                                System.out.println("sql = " + sql);
                                PreparedStatement ps = conexao.prepareStatement(sql);

                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                int r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);

                                //Município -> Exploração
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select a.location from id2vwloc04 a, id2vwloc01 b where b.id2codmun = ? and a.id2codprop = b.location)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);


                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));
                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);


                                //Município -> Aglomeração
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select location from id2vwloc03 where id2codmun = ?)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);


                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);


                                //Município -> Abatedouro
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select location from id2vwloc02 where id2codmun     = ?)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);


                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);


                                //Município -> Área
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select a.location from ID2AREALOC a, ID2VWLOCH b where a.id2arealoc = ? and a.location = b.filho)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);

                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);


                            }


                            //Áreas
                            if (opcao == 3) {
                                System.out.println("------ opcao 1");

                                //Áreas -> Propriedade
                                String sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select c.location from ID2AREALOC a, ID2VWLOCH b, id2vwloc01 c where a.id2arealoc = ? and a.location = b.filho and b.pai = c.id2locuf group by c.location)";

                                System.out.println("sql = " + sql);
                                PreparedStatement ps = conexao.prepareStatement(sql);

                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                int r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);

                                //Áreas -> Exploração
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select d.location from ID2AREALOC a, ID2VWLOCH b, id2vwloc01 c, id2vwloc04 d where a.id2arealoc = ? and a.location = b.filho and b.pai = c.id2locuf and c.location = d.id2codprop group by d.location)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);

                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);

                                //Áreas -> Aglomeração
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select c.location from ID2AREALOC a, ID2VWLOCH b, id2vwloc03 c where a.id2arealoc = ? and a.location = b.filho and b.pai = c.id2locuf group by c.location)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);

                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);

                                //Áreas -> Abatedouro
                                sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where ID2CODESP = ? AND LOCATION in (select c.location from ID2AREALOC a, ID2VWLOCH b, id2vwloc02 c where a.id2arealoc = ? and a.location = b.filho and b.pai = c.id2locuf group by c.location)";

                                System.out.println("sql = " + sql);
                                ps = conexao.prepareStatement(sql);

                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("ID2CODESP"));
                                ps.setString(4, mbob.getString("LOCATION"));

                                r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);
                            }

                            if (opcao == 4) {
                                System.out.println("------ opcao 4");

                                //Propriedade -> EXPLORAÇÃO
                                String sql = "UPDATE ID2STOCOMM SET MABLOQSTATUS = ? "
                                        //+ ", MADTVALBLO = ? "
                                        + ", MAOBSBLO = ? "
                                        + " where LOCATION in (select LOCATION from LOCATIONS b where b.LOCATION = ID2STOCOMM.location and b.ID2CODPROP = ?)";
                                System.out.println("sql = " + sql);
                                PreparedStatement ps = conexao.prepareStatement(sql);

                                System.out.println("------ valores b");
                                System.out.println(mbob.getString("MABLOQSTATUS"));
                                System.out.println(mbob.getString("MADTVALBLO"));
                                System.out.println(mbob.getString("MAOBSBLO"));
                                System.out.println(mbob.getString("LOCATION"));
                                System.out.println("------ valores a");

                                ps.setString(1, mbob.getString("MABLOQSTATUS"));
                                //ps.setDate(2, new java.sql.Date(mbob.getDate("MADTVALBLO").getTime()));
                                ps.setString(2, mbob.getString("MAOBSBLO"));
                                ps.setString(3, mbob.getString("LOCATION"));

                                int r = ps.executeUpdate();
                                System.out.println("----- updateLocation execute a " + r);
                            }
                        }


                    }
                }

            }
            conexao.commit();
            System.out.println("----- commit");

            conexao.close();
            System.out.println("----- close");
        } catch (Exception e) {
            System.out.println("----- e " + e.getMessage());
        }

        refreshTable();
        reloadTable();
        save();
        throw new MXApplicationException("system", "concluido");
    }

    private void limpaMboSet(String index) throws MXException, RemoteException {
        if (!index.equalsIgnoreCase("01")) {
            app.getDataBean("MAINRECORD").getMbo().getMboSet("ID2BLOQ01").unselectAll();
        }
        if (!index.equalsIgnoreCase("02")) {
            app.getDataBean("MAINRECORD").getMbo().getMboSet("ID2BLOQ02").unselectAll();
        }
        if (!index.equalsIgnoreCase("03")) {
            app.getDataBean("MAINRECORD").getMbo().getMboSet("ID2BLOQ03").unselectAll();
        }
        if (!index.equalsIgnoreCase("04")) {
            app.getDataBean("MAINRECORD").getMbo().getMboSet("ID2BLOQ04").unselectAll();
        }
        if (!index.equalsIgnoreCase("05")) {
            app.getDataBean("MAINRECORD").getMbo().getMboSet("ID2BLOQ05").unselectAll();
        }
        if (!index.equalsIgnoreCase("06")) {
            app.getDataBean("MAINRECORD").getMbo().getMboSet("ID2BLOQ06").unselectAll();
        }
        if (!index.equalsIgnoreCase("07")) {
            app.getDataBean("MAINRECORD").getMbo().getMboSet("ID2BLOQ07").unselectAll();
        }

    }

    /* @Override
    protected void initialize() throws MXException, RemoteException {
    super.initialize();
    getMboSet().add();
    getMboSet().select(getMboSet().count() - 1);
    }
     * 
     */
}
