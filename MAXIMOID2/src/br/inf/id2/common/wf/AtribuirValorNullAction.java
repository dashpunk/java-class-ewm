package br.inf.id2.common.wf;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXException;

/**
 *
 * @author Ricardo s Gomes
 */
public class AtribuirValorNullAction implements ActionCustomClass {

    public AtribuirValorNullAction() {
        super();
        System.out.println("____AtribuirValorNull");
    }

    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params) throws MXException, java.rmi.RemoteException {
//        System.out.println("___applyCustomAction");
//        System.out.println("___applyCustomAction COUNT " + params.length);
//        for (Object object : params) {
//            System.out.println("_______ parametro " + (String) object);
//            mbo.setValueNull((String) object, MboConstants.NOACCESSCHECK);
//        }
//        System.out.println("___applyCustomAction antes do save()");
//        mbo.getThisMboSet().save();
//        System.out.println("___applyCustomAction FIM");

//        System.out.println("___applyCustomAction-------------------------------------------");
//        System.out.println("___applyCustomAction COUNT " + params.length);
        String entidade = mbo.getName();
        System.out.println("...Entidade " + entidade);
        String entidadeId = entidade + "ID";
        System.out.println("...EntidadeID " + entidadeId);
        try {
            Properties prop;
            prop = MXServer.getMXServer().getConfig();
            String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url");
            String username = prop.getProperty("mxe.db.user", "sifimp");
            String password = prop.getProperty("mxe.db.password", "id2maximo");

            Class.forName(driver).newInstance();
            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
            Statement stmt = conexao.createStatement();
            String sql = "UPDATE " + entidade;
            String atributo;
            for (Object object : params) {
                System.out.println("_______ parametro " + (String) object);
                if (sql.equalsIgnoreCase("UPDATE " + entidade)) {
                    atributo = (String) object;
                    sql = "UPDATE " + entidade + " SET " + atributo + " = null";
                } else {
                    atributo = (String) object;
                    sql += ", " + atributo + " = null";

                }
            }
            sql = sql + " WHERE " + entidadeId + " = " + mbo.getInt(entidadeId);

            System.out.println("... sql " + sql);

            PreparedStatement ps = conexao.prepareStatement(sql);

            int r = ps.executeUpdate();

            conexao.commit();

            System.out.println("*** commit");

            conexao.close();

            System.out.println("___applyCustomAction FIM");

        } catch (Exception e) {
            System.out.println("*** e = " + e.getMessage());
        }
    }
}
