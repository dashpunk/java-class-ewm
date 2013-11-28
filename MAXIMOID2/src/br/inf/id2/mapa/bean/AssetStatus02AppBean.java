package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.DBConnect;

import psdi.util.MXException;
import psdi.webclient.beans.servicedesk.TableBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class AssetStatus02AppBean extends TableBean {

    /**
     *
     */
    public AssetStatus02AppBean() {
    }

    @Override
    public synchronized int execute() throws MXException, RemoteException {

        System.out.println("----     public AssetStatus02AppBean()");
        MboSet poSet;
        poSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("PO", sessionContext.getUserInfo());

        poSet.setWhere("ponum in (select maponum from matranseleline where assetnum in (select assetnum from asset where assetnum = '" + app.getDataBean().getMboSet().getMbo().getString("assetnum") + "'))");

        poSet.reset();
        System.out.println("    public AssetStatus02AppBean() apos reset, count " + poSet.count());
        System.out.println("    public AssetStatus02AppBean() apos reset, location " + poSet.getMbo(0).getString("STORELOC"));


        //app.getDataBean().getMboSet().getMbo().setValue("LOCATION", poSet.getMbo(0).getString("STORELOC"), MboConstants.NOVALIDATION_AND_NOACTION);
        Properties prop;
        prop = MXServer.getMXServer().getConfig();
        byte[] bytes = null;
        String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
        String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@192.168.1.215:1521:dftop1");
        String username = prop.getProperty("mxe.db.user", "dbmaximo");
        String password = prop.getProperty("mxe.db.password", "id2maximo");

        System.out.println("----     public AssetStatus02AppBean() apos setValue");

        Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));

        try {
            Class.forName(driver).newInstance();
            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
            Statement stmt = conexao.createStatement();
            PreparedStatement ps = conexao.prepareStatement("UPDATE ASSET SET LOCATION = '" + poSet.getMbo(0).getString("STORELOC") + "' WHERE ASSETNUM = '" + app.getDataBean().getMboSet().getMbo().getString("assetnum") + "'");
            ps.execute();
            conexao.commit();
        } catch (Exception e) {
            System.out.println("---e "+e.getMessage());
        }

        return super.execute();
    }
}
