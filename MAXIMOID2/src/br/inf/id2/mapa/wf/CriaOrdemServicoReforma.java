package br.inf.id2.mapa.wf;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class CriaOrdemServicoReforma implements ActionCustomClass {

    public CriaOrdemServicoReforma() {
        super();
//        System.out.println("*** CriaOrdemServicoReforma ***");
    }

    /**
     *
     * @param mbo
     * @param params Primeiro parametro nome do aplicativo
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
            throws MXException, java.rmi.RemoteException {

//        System.out.println("*** ENTROU NO APPLYCUSTOMACTION()");

        mbo.setValueNull("");
        MboSet workorder;
        workorder = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WORKORDER", mbo.getUserInfo());
        workorder.setWhere("ORIGRECORDID = -1");
        workorder.reset();

//        System.out.println("*** após reset");

        MboRemote aMbo;
        aMbo = workorder.add();
//        System.out.println("*** após add");

        //aMbo = mbo.getMboSet("MARLWOR01").add();
        if (params != null && params[0] != null) {
//            System.out.println("*** antes dentro app ");
            aMbo.setValue("APP", (String) params[0]);
//            System.out.println("*** após dentro app ");
        }
//        System.out.println("*** após app");

        int aonde = 0;

//        System.out.println("*** aonde "+ ++aonde);
        aMbo.setValue("REPORTEDBY", mbo.getString("ID2SOL"));
//        System.out.println("*** aonde "+ ++aonde);
        aMbo.setValue("ID2CEPCODE", mbo.getString("ID2CEPCODE"));
//        System.out.println("*** aonde "+ ++aonde);
        aMbo.setValue("ORIGRECORDID", mbo.getString("MATBREFID"));
//        System.out.println("*** aonde "+ ++aonde);
        aMbo.setValue("LOCATION", mbo.getString("LOCATION"));
//        System.out.println("*** aonde "+ ++aonde);
        aMbo.setValue("ID2AREINT", "Inspeção de produtos de origem animal");
//        System.out.println("*** aonde "+ ++aonde);
        aMbo.setValue("ID2TIPOS", "Laudo Higiênico Sanitário Tecnológico");
//        System.out.println("*** aonde "+ ++aonde);
        aMbo.setValue("ID2TIPLOC", "Terreno");

//        System.out.println("*** antes save");
        aMbo.getThisMboSet().save();
//        System.out.println("*** após save");
        
    }
}
