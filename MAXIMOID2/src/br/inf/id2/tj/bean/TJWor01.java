package br.inf.id2.tj.bean;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import br.inf.id2.common.util.Executa;
import br.inf.id2.tj.mbo.MatUseTrans;
import java.util.Date;
import psdi.app.workorder.WPMaterial;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.webclient.system.beans.DataBean;

/**
 *
 * @author Ricardo S Gomes
 */
public class TJWor01 extends psdi.plusp.webclient.beans.pluspwo.PlusPWorkorderAppBean {

    public TJWor01() {
    }

    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {
        /*     System.out.println("================ TARGSTARTDATE = " + getMbo().getString("TARGSTARTDATE"));
        System.out.println("================ DTINIPREV = " + getMbo().getString("DTINIPREV"));
        boolean readOnly = getMbo().getMboValueData("TARGSTARTDATE").isReadOnly();
        if (readOnly) {
        System.out.println("============= TARGSTARTDATE readonly to false");
        getMbo().setFieldFlag("TARGSTARTDATE", MboConstants.READONLY, false);
        }
        System.out.println("============= TARGSTARTDATE value from DTINIPREV");
        getMbo().setValue("TARGSTARTDATE", getMbo().getDate("DTINIPREV"), MboConstants.NOVALIDATION_AND_NOACTION);
        if (readOnly) {
        System.out.println("============= TARGSTARTDATE readonly to true");
        getMbo().setFieldFlag("TARGSTARTDATE", MboConstants.READONLY, true);
        }
        System.out.println("======= TJWor01 ini");
        System.out.println("====== AMSUBMAT " + Executa.somaValor("LINECOST", getMbo().getMboSet("RL02WPMATERIAL")));
        System.out.println("====== AMSUBMAT count " + getMbo().getMboSet("RL02WPMATERIAL").count());
        getMbo().setValue("AMSUBMAT", Executa.somaValor("LINECOST", getMbo().getMboSet("RL02WPMATERIAL")), MboConstants.NOVALIDATION_AND_NOACTION);
        System.out.println("====== amsubserv " + Executa.somaValor("LINECOST", getMbo().getMboSet("RL01WPMATERIAL")));
        System.out.println("====== amsubserv count " + getMbo().getMboSet("RL01WPMATERIAL").count());
        getMbo().setValue("AMSUBSERV", Executa.somaValor("LINECOST", getMbo().getMboSet("RL01WPMATERIAL")), MboConstants.NOVALIDATION_AND_NOACTION);
        System.out.println("====== AMSUBMAO " + Executa.somaValor("LINECOST", getMbo().getMboSet("SHOWPLANLABOR")));
        System.out.println("====== AMSUBMAOAMSUBMAO count " + getMbo().getMboSet("SHOWPLANLABOR").count());
        getMbo().setValue("AMSUBMAO", Executa.somaValor("LINECOST", getMbo().getMboSet("SHOWPLANLABOR")), MboConstants.NOVALIDATION_AND_NOACTION);


        double amSubMat = 0;
        double amSubServ = 0;

        if (!getMbo().isNull("AMSUBMAT")) {
        amSubMat = getMbo().getDouble("AMSUBMAT");
        }
        if (!getMbo().isNull("AMSUBSERV")) {
        amSubServ = getMbo().getDouble("AMSUBSERV");
        }
        System.out.println("--------- amSubMat = " + amSubMat);
        System.out.println("--------- amSubServ = " + amSubServ);

        getMbo().setValue("AMSUBPLAN", amSubMat + amSubServ);

        MboRemote mbo;

        double sm = 0;
        double ss = 0;

        for (int i = 0; ((mbo = getMbo().getMboSet("RL01MATUSETRANS").getMbo(i)) != null); i++) {
        if (mbo.getString("ISSUETYPE").equalsIgnoreCase("DEVOLVER") || mbo.getString("ISSUETYPE").equalsIgnoreCase("RETURN")) {
        sm -= mbo.getDouble("LINECOST");
        } else {
        sm += mbo.getDouble("LINECOST");
        }
        }

        for (int i = 0; ((mbo = getMbo().getMboSet("SHOWACTUALSERVICE1").getMbo(i)) != null); i++) {
        if (mbo.getString("ISSUETYPE").equalsIgnoreCase("DEVOLVER") || mbo.getString("ISSUETYPE").equalsIgnoreCase("RETURN")) {
        ss -= mbo.getDouble("LINECOST");
        } else {
        ss += mbo.getDouble("LINECOST");
        }
        }

        getMbo().setValue("AMSUBMATEXE", sm, MboConstants.NOVALIDATION_AND_NOACTION);
        getMbo().setValue("AMSUBSERVEXE", ss, MboConstants.NOVALIDATION_AND_NOACTION);

        System.out.println("---- point a");
        double bdiServ = 0;
        System.out.println("*** (RL01BDIEXESERV).count " + getMbo().getMboSet("RL01BDIEXESERV").count());
        if (getMbo().getMboSet("RL01BDIEXESERV").count() > 0) {
        bdiServ = getMbo().getMboSet("RL01BDIEXESERV").getMbo(0).getDouble("BDI");
        System.out.println("*** bdiServ " + bdiServ);
        }
        System.out.println("---- point b");
        double bdiMat = 0;
        System.out.println("*** (rl01bdiexemat).count " + getMbo().getMboSet("rl01bdiexemat").count());
        if (getMbo().getMboSet("rl01bdiexemat").count() > 0) {
        bdiMat = getMbo().getMboSet("rl01bdiexemat").getMbo(0).getDouble("BDI");
        System.out.println("*** bdiMat " + bdiMat);
        }
        System.out.println("---- point c");
        double AMSUBSERVEXE = getMbo().getDouble("AMSUBSERVEXE");
        System.out.println("---- point d");
        double AMSUBMATEXE = getMbo().getDouble("AMSUBMATEXE");
        System.out.println("---- point e");

        getMbo().setValue("AMTOTALBDIEXE", (((bdiServ / 100) + 1) * AMSUBSERVEXE)
        + (((bdiMat / 100) + 1) * AMSUBMATEXE), MboConstants.NOVALIDATION_AND_NOACTION);

        System.out.println("---- point f");

        double AMSUBSERV = getMbo().getDouble("AMSUBSERV");
        double AMSUBMAT = getMbo().getDouble("AMSUBMAT");

        System.out.println("---- point g");
        bdiServ = 0;
        System.out.println("*** rl01purchview.count - " + getMbo().getMboSet("rl01bdiplanserv").count());
        if (getMbo().getMboSet("rl01purchview").count() > 0) {
        System.out.println("*** FLBDISERV - " + getMbo().getMboSet("rl01purchview").getMbo(0).getDouble("FLBDISERV"));
        bdiServ = getMbo().getMboSet("rl01purchview").getMbo(0).getDouble("FLBDISERV");
        }
        System.out.println("---- point h");
        bdiMat = 0;
        System.out.println("*** rl01purchview.count - " + getMbo().getMboSet("rl01purchview").count());
        if (getMbo().getMboSet("rl01purchview").count() > 0) {
        System.out.println("*** FLBDIMAT - " + getMbo().getMboSet("rl01purchview").getMbo(0).getDouble("FLBDIMAT"));
        bdiMat = getMbo().getMboSet("rl01purchview").getMbo(0).getDouble("FLBDIMAT");
        }
        System.out.println("---- point i");

        getMbo().setValue("AMTOTALBDIPLAN", (((bdiServ / 100) + 1) * AMSUBSERV)
        + (((bdiMat / 100) + 1) * AMSUBMAT), MboConstants.NOVALIDATION_AND_NOACTION);

        System.out.println("---- point j");
        getMbo().setValue("AMTOTALEXE", getMbo().getDouble("AMSUBSERVEXE") + getMbo().getDouble("AMSUBMATEXE"), MboConstants.NOVALIDATION_AND_NOACTION);

         */
        //calcule();
        System.out.println("----- save");
        int resultado = super.SAVE();
        System.out.println("----- save FIM");

        /*        System.out.println("--- refaz calculos");
        MboRemote mboWorkOrder;
        WPMaterial mboWpMaterial;
        MatUseTrans mboMatUseTrans;

        System.out.println("---- RL01MATUSETRANS count " + getMbo().getMboSet("RL01MATUSETRANS").count());
        for (int i = 0; ((mboMatUseTrans = (MatUseTrans) getMbo().getMboSet("RL01MATUSETRANS").getMbo(i)) != null); i++) {
        calculaMtUseTrans(mboMatUseTrans);
        }

        System.out.println("---- SHOWACTUALSERVICE1 count " + getMbo().getMboSet("SHOWACTUALSERVICE1").count());
        for (int i = 0; ((mboMatUseTrans = (MatUseTrans) getMbo().getMboSet("SHOWACTUALSERVICE1").getMbo(i)) != null); i++) {
        calculaMtUseTrans(mboMatUseTrans);
        }

        System.out.println("---- RL01WPMATERIAL count " + getMbo().getMboSet("RL01WPMATERIAL").count());
        for (int i = 0; ((mboWpMaterial = (WPMaterial) getMbo().getMboSet("RL01WPMATERIAL").getMbo(i)) != null); i++) {
        calculaWpMatrial(mboWpMaterial);
        }

        System.out.println("---- RL02WPMATERIAL count " + getMbo().getMboSet("RL02WPMATERIAL").count());
        for (int i = 0; ((mboWpMaterial = (WPMaterial) getMbo().getMboSet("RL02WPMATERIAL").getMbo(i)) != null); i++) {
        calculaWpMatrial(mboWpMaterial);
        }
         *
         * 
         */



        /*
        boolean controleReadOnly = false;
        System.out.println("--save 1.");
        
        controleReadOnly = getMbo().getMboSet("RL01MATUSETRANS").isFlagSet(MboConstants.READONLY);
        System.out.println("-controleReadOnly = "+controleReadOnly);
        if (controleReadOnly) {
        System.out.println("-controleReadOnly a");
        getMbo().getMboSet("RL01MATUSETRANS").setFlag(MboConstants.READONLY, true);
        }
        getMbo().getMboSet("RL01MATUSETRANS").save(MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);
        if (controleReadOnly) {
        System.out.println("-controleReadOnly b");
        getMbo().getMboSet("RL01MATUSETRANS").setFlag(MboConstants.READONLY, true);
        }


        System.out.println("--save 2");
        controleReadOnly = getMbo().getMboSet("SHOWACTUALSERVICE1").isFlagSet(MboConstants.READONLY);
        if (controleReadOnly) {
        getMbo().getMboSet("SHOWACTUALSERVICE1").setFlag(MboConstants.READONLY, true);
        }
        getMbo().getMboSet("SHOWACTUALSERVICE1").save(MboConstants.NOACCESSCHECK);
        if (controleReadOnly) {
        getMbo().getMboSet("SHOWACTUALSERVICE1").setFlag(MboConstants.READONLY, true);
        }


        System.out.println("--save 3");
        controleReadOnly = getMbo().getMboSet("RL01WPMATERIAL").isFlagSet(MboConstants.READONLY);
        if (controleReadOnly) {
        getMbo().getMboSet("RL01WPMATERIAL").setFlag(MboConstants.READONLY, true);
        }
        getMbo().getMboSet("RL01WPMATERIAL").save(MboConstants.NOACCESSCHECK);
        if (controleReadOnly) {
        getMbo().getMboSet("RL01WPMATERIAL").setFlag(MboConstants.READONLY, true);
        }



        System.out.println("--save 4");
        controleReadOnly = getMbo().getMboSet("RL02WPMATERIAL").isFlagSet(MboConstants.READONLY);
        if (controleReadOnly) {
        getMbo().getMboSet("RL02WPMATERIAL").setFlag(MboConstants.READONLY, true);
        }
        getMbo().getMboSet("RL02WPMATERIAL").save(MboConstants.NOACCESSCHECK);
        if (controleReadOnly) {
        getMbo().getMboSet("RL02WPMATERIAL").setFlag(MboConstants.READONLY, true);
        }
        
        
         */
        System.out.println("--FIM..............");
        return resultado;

    }

    public int RUNMXDUR() throws MXException, RemoteException {
        MboRemote mboa;

        MboSet workorder;
        workorder = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WORKORDER", getMbo().getUserInfo());
        workorder.setOrderBy("WONUM");
        workorder.reset();
        System.out.println("---- workorder " + workorder.count());

        for (int a = 0; ((mboa = workorder.getMbo(a)) != null); a++) {
            System.out.println("---- a " + a);
            MboSet wostatus;
            wostatus = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WOSTATUS", getMbo().getUserInfo());
            wostatus.setWhere("wonum = \'" + mboa.getString("wonum") + "\' ");
            wostatus.setOrderBy("wostatusid");
            wostatus.reset();


            System.out.println("v3------- count  " + wostatus.count());

            MboRemote mbo;
            MboRemote mbob;
            for (int i = 0; ((mbo = wostatus.getMbo(i)) != null); i++) {
                Date dataFinal;
                if (((mbob = wostatus.getMbo(i + 1)) == null)) {
                    dataFinal = new Date();
                } else {
                    dataFinal = mbob.getDate("CHANGEDATE");
                }
                System.out.println("- dataFinal " + i + " " + dataFinal);
                System.out.println("- changeby " + mbo.getString("CHANGEBY"));
                Date data = mbo.getDate("CHANGEDATE");
                System.out.println("--- data " + data);
                try {
                    Date duracao = null;
                    duracao = Data.getHorasUteis(data, dataFinal, getMbo().getUserInfo());

                    System.out.println("--- duracao = " + duracao);

                    String b = "" + duracao.getTime() / 3600000.00D;
                    System.out.println("b = " + b);
                    int h = 0;
                    int m = 0;
                    if (b.contains(".")) {
                        String[] valor = b.split("\\.");

                        Double x = Double.valueOf("0." + valor[1]) * 60;

                        System.out.println(" x " + Math.round(x));

                        h = Integer.valueOf(valor[0]);
                        m = (int) Math.round(x);
                    } else {
                        h = Integer.valueOf(b);
                        m = 0;
                    }
                    int horas = h * 60 + m;

                    // System.out.println("--result = " + h + ":" + Uteis.adicionaValorEsquerda(String.valueOf(m), "0", 2));

                    mbo.setValue("MXDUR", "" + horas, MboConstants.NOACCESSCHECK);

                } catch (Exception e) {
                    System.out.println("---- e " + e.getMessage());
                }
            }
            System.out.println("------ antes do save");

            wostatus.save(MboConstants.NOVALIDATION_AND_NOACTION);

            System.out.println("------ apos do save");
        }
        throw new MXApplicationException("id2", "concluido");
        //return EVENT_HANDLED;
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        super.dataChangedEvent(speaker);
        calcule();
    }

    private void calcule() {
        System.out.println("----- calcule() INICIO");
        
        try {
            // Verifica antes a situa��o do STATUS se est� FECHADA. Se estiver fechada n�o fa�a nada
            String status = getMbo().getString("STATUS");
            if (status.equals("FECHADA")) {
            	return;
            }
            
            System.out.println();
            System.out.println("================ TARGSTARTDATE = " + getMbo().getString("TARGSTARTDATE"));
            System.out.println("================ DTINIPREV = " + getMbo().getString("DTINIPREV"));
            boolean readOnly = getMbo().getMboValueData("TARGSTARTDATE").isReadOnly();
            if (readOnly) {
                System.out.println("============= TARGSTARTDATE readonly to false");
                getMbo().setFieldFlag("TARGSTARTDATE", MboConstants.READONLY, false);
            }
            System.out.println("============= TARGSTARTDATE value from DTINIPREV");
            getMbo().setValue("TARGSTARTDATE", getMbo().getDate("DTINIPREV"), MboConstants.NOVALIDATION_AND_NOACTION);
            if (readOnly) {
                System.out.println("============= TARGSTARTDATE readonly to true");
                getMbo().setFieldFlag("TARGSTARTDATE", MboConstants.READONLY, true);
            }
            System.out.println("======= TJWor01 ini");
            System.out.println("====== AMSUBMAT " + Executa.somaValor("LINECOST", getMbo().getMboSet("RL02WPMATERIAL")));
            System.out.println("====== AMSUBMAT count " + getMbo().getMboSet("RL02WPMATERIAL").count());
            getMbo().setValue("AMSUBMAT", Executa.somaValor("LINECOST", getMbo().getMboSet("RL02WPMATERIAL")), MboConstants.NOVALIDATION_AND_NOACTION);
            System.out.println("====== amsubserv " + Executa.somaValor("LINECOST", getMbo().getMboSet("RL01WPMATERIAL")));
            System.out.println("====== amsubserv count " + getMbo().getMboSet("RL01WPMATERIAL").count());
            getMbo().setValue("AMSUBSERV", Executa.somaValor("LINECOST", getMbo().getMboSet("RL01WPMATERIAL")), MboConstants.NOVALIDATION_AND_NOACTION);
            System.out.println("====== AMSUBMAO " + Executa.somaValor("LINECOST", getMbo().getMboSet("SHOWPLANLABOR")));
            System.out.println("====== AMSUBMAOAMSUBMAO count " + getMbo().getMboSet("SHOWPLANLABOR").count());
            getMbo().setValue("AMSUBMAO", Executa.somaValor("LINECOST", getMbo().getMboSet("SHOWPLANLABOR")), MboConstants.NOVALIDATION_AND_NOACTION);


            double amSubMat = 0;
            double amSubServ = 0;

            if (!getMbo().isNull("AMSUBMAT")) {
                amSubMat = getMbo().getDouble("AMSUBMAT");
            }
            if (!getMbo().isNull("AMSUBSERV")) {
                amSubServ = getMbo().getDouble("AMSUBSERV");
            }
            System.out.println("--------- amSubMat = " + amSubMat);
            System.out.println("--------- amSubServ = " + amSubServ);

            getMbo().setValue("AMSUBPLAN", amSubMat + amSubServ);

            MboRemote mbo;

            double sm = 0;
            double ss = 0;

            for (int i = 0; ((mbo = getMbo().getMboSet("RL01MATUSETRANS").getMbo(i)) != null); i++) {
                if (mbo.getString("ISSUETYPE").equalsIgnoreCase("DEVOLVER") || mbo.getString("ISSUETYPE").equalsIgnoreCase("RETURN")) {
                    sm -= mbo.getDouble("LINECOST");
                } else {
                    sm += mbo.getDouble("LINECOST");
                }
            }

            for (int i = 0; ((mbo = getMbo().getMboSet("SHOWACTUALSERVICE1").getMbo(i)) != null); i++) {
                if (mbo.getString("ISSUETYPE").equalsIgnoreCase("DEVOLVER") || mbo.getString("ISSUETYPE").equalsIgnoreCase("RETURN")) {
                    ss -= mbo.getDouble("LINECOST");
                } else {
                    ss += mbo.getDouble("LINECOST");
                }
            }

            getMbo().setValue("AMSUBMATEXE", sm, MboConstants.NOVALIDATION_AND_NOACTION);
            getMbo().setValue("AMSUBSERVEXE", ss, MboConstants.NOVALIDATION_AND_NOACTION);

            System.out.println("---- point a");
            double bdiServ = 0;
            double bdiMat = 0;
            System.out.println("*** (RL02BDIEXESERV).count " + getMbo().getMboSet("RL02BDIEXESERV").count());
            if (getMbo().getMboSet("RL02BDIEXESERV").count() > 0) {
                bdiServ = getMbo().getMboSet("RL02BDIEXESERV").getMbo(0).getDouble("FLBDISERV");
                System.out.println("*** bdiServ " + bdiServ);
                bdiMat = getMbo().getMboSet("RL02BDIEXESERV").getMbo(0).getDouble("FLBDIMAT");
                System.out.println("*** bdiMat " + bdiMat);
            }
            System.out.println("---- point c");
            double AMSUBSERVEXE = getMbo().getDouble("AMSUBSERVEXE");
            System.out.println("---- point d");
            double AMSUBMATEXE = getMbo().getDouble("AMSUBMATEXE");
            System.out.println("---- point e");
            System.out.println("------- bdiServ " + bdiServ);
            System.out.println("------- AMSUBSERVEXE " + AMSUBSERVEXE);
            System.out.println("------- bdiMat " + bdiMat);
            System.out.println("------- AMSUBMATEXE " + AMSUBMATEXE);
            getMbo().setValue("AMTOTALBDIEXE", (((bdiServ / 100) + 1) * AMSUBSERVEXE)
                    + (((bdiMat / 100) + 1) * AMSUBMATEXE), MboConstants.NOVALIDATION_AND_NOACTION);

            System.out.println("---- point f");

            double AMSUBSERV = getMbo().getDouble("AMSUBSERV");
            double AMSUBMAT = getMbo().getDouble("AMSUBMAT");

            System.out.println("---- point g");

            /* Trecho retirado após entendimento com o Eduardo Assis
            bdiServ = 0;
            System.out.println("*** rl01purchview.count - " + getMbo().getMboSet("rl01bdiplanserv").count());
            if (getMbo().getMboSet("rl01purchview").count() > 0) {
            System.out.println("*** FLBDISERV - " + getMbo().getMboSet("rl01purchview").getMbo(0).getDouble("FLBDISERV"));
            bdiServ = getMbo().getMboSet("rl01purchview").getMbo(0).getDouble("FLBDISERV");
            }
            System.out.println("---- point h");
            bdiMat = 0;
            System.out.println("*** rl01purchview.count - " + getMbo().getMboSet("rl01purchview").count());
            if (getMbo().getMboSet("rl01purchview").count() > 0) {
            System.out.println("*** FLBDIMAT - " + getMbo().getMboSet("rl01purchview").getMbo(0).getDouble("FLBDIMAT"));
            bdiMat = getMbo().getMboSet("rl01purchview").getMbo(0).getDouble("FLBDIMAT");
            }
             * 
             */
            System.out.println("---- point i");

            getMbo().setValue("AMTOTALBDIPLAN", (((bdiServ / 100) + 1) * AMSUBSERV)
                    + (((bdiMat / 100) + 1) * AMSUBMAT), MboConstants.NOVALIDATION_AND_NOACTION);

            System.out.println("---- point j");
            getMbo().setValue("AMTOTALEXE", getMbo().getDouble("AMSUBSERVEXE") + getMbo().getDouble("AMSUBMATEXE"), MboConstants.NOVALIDATION_AND_NOACTION);

        } catch (MXException ex) {
            Logger.getLogger(TJWor01.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TJWor01.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("----- calcule() FIM");
    }

    public int MXCALCULO() throws MXException, RemoteException {
        MboRemote mboWorkOrder;
        WPMaterial mboWpMaterial;
        MatUseTrans mboMatUseTrans;

        MboSet workorder;
        workorder = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WORKORDER", getMbo().getUserInfo());
        workorder.setOrderBy("WONUM");
        workorder.reset();
        System.out.println("---- workorder " + workorder.count());

        for (int a = 0; ((mboWorkOrder = workorder.getMbo(a)) != null); a++) {
        	System.out.println("################ Workorder (" + a + ") = " + mboWorkOrder.getString("STATUS"));
            for (int i = 0; ((mboMatUseTrans = (MatUseTrans) mboWorkOrder.getMboSet("RL01MATUSETRANS").getMbo(i)) != null); i++) {
            	//mboMatUseTrans.setValue("MXQUANTIDADE", mboMatUseTrans.getDouble("POSITIVEQUANTITY"), MboConstants.NOACCESSCHECK);
            	//mboMatUseTrans.setValue("QUANTITY", - mboMatUseTrans.getDouble("POSITIVEQUANTITY"), MboConstants.NOACCESSCHECK);
            	System.out.println("################# Antes 1");
                calculaMtUseTrans(mboMatUseTrans);
                System.out.println("################# Depois 1");
            }

            for (int i = 0; ((mboMatUseTrans = (MatUseTrans) mboWorkOrder.getMboSet("SHOWACTUALSERVICE1").getMbo(i)) != null); i++) {
            	//mboMatUseTrans.setValue("MXQUANTIDADE", mboMatUseTrans.getDouble("POSITIVEQUANTITY"), MboConstants.NOACCESSCHECK);
            	//mboMatUseTrans.setValue("QUANTITY", - mboMatUseTrans.getDouble("POSITIVEQUANTITY"), MboConstants.NOACCESSCHECK);
            	System.out.println("################# Antes 2");
            	calculaMtUseTrans(mboMatUseTrans);
            	System.out.println("################# Depois 2");
                
            }

            for (int i = 0; ((mboWpMaterial = (WPMaterial) mboWorkOrder.getMboSet("RL01WPMATERIAL").getMbo(i)) != null); i++) {
            	//mboWpMaterial.setValue("MXQUANTIDADE", mboWpMaterial.getDouble("POSITIVEQUANTITY"), MboConstants.NOACCESSCHECK);
            	//mboWpMaterial.setValue("QUANTITY", - mboWpMaterial.getDouble("POSITIVEQUANTITY"), MboConstants.NOACCESSCHECK);
            	System.out.println("################# Antes 3");
            	calculaWpMatrial(mboWpMaterial);
            	System.out.println("################# Depois 3");
                
            }
            for (int i = 0; ((mboWpMaterial = (WPMaterial) mboWorkOrder.getMboSet("RL02WPMATERIAL").getMbo(i)) != null); i++) {
            	//mboWpMaterial.setValue("MXQUANTIDADE", mboWpMaterial.getDouble("POSITIVEQUANTITY"), MboConstants.NOACCESSCHECK);
            	//mboWpMaterial.setValue("QUANTITY", - mboWpMaterial.getDouble("POSITIVEQUANTITY"), MboConstants.NOACCESSCHECK);
            	System.out.println("################# Antes 4");
            	calculaWpMatrial(mboWpMaterial);
            	System.out.println("################# Depois 4");
                
            }
        }
        throw new MXApplicationException("id2", "concluido");
        //return EVENT_HANDLED;
    }

    private void calculaMtUseTrans(MatUseTrans mbo) throws MXException, RemoteException {
        boolean controleReadOnly = mbo.isFlagSet(MboConstants.READONLY);
        System.out.println("--------------- controleReadOnly " + controleReadOnly);
        if (controleReadOnly) {
            mbo.setFlag(MboConstants.READONLY, false);
        }
        //System.out.println("---> Entrou no Unitcost");
        double amcustmaoobra = mbo.getDouble("AMCUSTMAOOBRA");
        double amcustmate = mbo.getDouble("AMCUSTMATE");
        //double amcustmatetjdf = getMboValue().getMbo().getDouble("AMCUSTMATETJDF");

        double valor = 0;
        if (!mbo.getBoolean("YNMATETJDF")) {
            valor = amcustmaoobra + amcustmate;

        } else {
            valor = amcustmaoobra;
        }

        System.out.println("---- altera unitcost");
        mbo.setValue("UNITCOST", valor, MboConstants.NOACCESSCHECK);
        System.out.println("---- altera unitcost FIM");
        double unitcost = mbo.getDouble("UNITCOST");

        double positiveQuantity = mbo.getDouble("POSITIVEQUANTITY");

        valor = (unitcost * positiveQuantity);

        System.out.println("---- altera linecost");
        /*mbo.setFieldFlag("LINECOST", MboConstants.READONLY, false);
        mbo.setValue("LINECOST", valor, MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);
        mbo.setFieldFlag("LINECOST", MboConstants.READONLY, true);
         *
         */
        mbo.setValue("LINECOST", valor, MboConstants.NOACCESSCHECK);
        System.out.println("---- altera linecost FIM");

        valor = mbo.getDouble("MXQUANTIDADE");
        //System.out.println("---validade() WpMaterialMxQuantidade " + valor);
        boolean yn = mbo.getBoolean("YNMATETJDF");
        boolean material = mbo.getString("DSTIPO").equals("MATERIAL");

        System.out.println("---validade() yn " + yn);

        if (yn && material) {
            System.out.println("--- MatUseTransMxQuantidade y");
            mbo.setValue("POSITIVEQUANTITY", 0, MboConstants.NOACCESSCHECK);
            System.out.println("--- MatUseTransMxQuantidade positivequantity");
            mbo.setValue("QUANTITY", 0, MboConstants.NOACCESSCHECK);
            System.out.println("--- MatUseTransMxQuantidade quantity");

        } else {
            mbo.setValue("POSITIVEQUANTITY", valor, MboConstants.NOACCESSCHECK);
            mbo.setValue("QUANTITY", valor, MboConstants.NOACCESSCHECK);
            //getMboValue().getMbo().setValue("QUANTITY", valor, MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);
        }
        mbo.save();
        if (controleReadOnly) {
            mbo.setFlag(MboConstants.READONLY, true);
        }

    }

    private void calculaWpMatrial(WPMaterial mbo) throws MXException, RemoteException {
        boolean controleReadOnly = mbo.isFlagSet(MboConstants.READONLY);
        System.out.println("--------------- controleReadOnly " + controleReadOnly);
        if (controleReadOnly) {
            mbo.setFlag(MboConstants.READONLY, false);
        }
        //System.out.println("---> Entrou no Unitcost");
        double amcustmaoobra = mbo.getDouble("AMCUSTMAOOBRA");
        double amcustmate = mbo.getDouble("AMCUSTMATE");
        //double amcustmatetjdf = getMboValue().getMbo().getDouble("AMCUSTMATETJDF");

        double valor = 0;
        if (!mbo.getBoolean("YNMATETJDF2")) {
            valor = amcustmaoobra + amcustmate;

        } else {
            valor = amcustmaoobra;
        }

        mbo.setValue("UNITCOST", valor, MboConstants.NOACCESSCHECK);

        double unitcost = mbo.getDouble("UNITCOST");

        double positiveQuantity = mbo.getDouble("ITEMQTY");

        valor = (unitcost * positiveQuantity);

        mbo.setFieldFlag("LINECOST", MboConstants.READONLY, false);
        mbo.setValue("LINECOST", valor, MboConstants.NOACCESSCHECK);
        mbo.setFieldFlag("LINECOST", MboConstants.READONLY, true);

        valor = mbo.getDouble("MXQUANTIDADE");
        System.out.println("---validade() WpMaterialMxQuantidade " + valor);
        boolean yn = mbo.getBoolean("YNMATETJDF2");
        System.out.println("---validade() yn " + yn);
        boolean material = mbo.getString("DSTIPO").equals("MATERIAL");

        if (yn && material) {
            mbo.setValue("ITEMQTY", 0, MboConstants.NOACCESSCHECK);
        } else {
            mbo.setValue("ITEMQTY", valor, MboConstants.NOACCESSCHECK);
        }

        mbo.save();
        if (controleReadOnly) {
            mbo.setFlag(MboConstants.READONLY, true);
        }
    }
}
