package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;

import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ID2ExploracaoPecuariaAppBean extends psdi.webclient.beans.storeroom.StoreroomAppBean {

    /**
     * Método construtor de ID2ExploracaoPecuariaAppBean
     */
    public ID2ExploracaoPecuariaAppBean() {
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        System.out.println("-----------------> dataChangedEvent");
        System.out.println("-----------------> dataChangedEvent");
        System.out.println("-----------------> dataChangedEvent");
        System.out.println("-----------------> dataChangedEvent");
        System.out.println("-----------------> dataChangedEvent");
        System.out.println("-----------------> dataChangedEvent");
        try {
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());

            if ((speaker.getUniqueIdName().equals("ID2VACEXPPECID")) || (speaker.getUniqueIdName().equals("ID2STOCOMMID"))) {
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                System.out.println("--------------- reloadTable()");
                refreshTable();
                reloadTable();
            }

        } catch (MXException ex) {
            Logger.getLogger(ID2ExploracaoPecuariaAppBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ID2ExploracaoPecuariaAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.dataChangedEvent(speaker);
    }

    /**
     *
     * Sobrescrita do método validate BMXZZ0002E
     *<p>
     * Valida se a data de encerramento é posterior a de statu
     * @since 30/03/2010
     */
    public int SAVE() throws MXException, RemoteException {

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
        return super.SAVE();
    }

    public void ADDROWONTABLE() {
        WebClientEvent event = sessionContext.getCurrentEvent();
        System.out.println("Teste 1");
        Utility.sendEvent(new WebClientEvent("addrow", (String) event.getValue(), event.getValue(), sessionContext));
        System.out.println("Teste 2");
    }

    @Override
    public synchronized void fireDataChangedEvent(DataBean speaker) {
        super.fireDataChangedEvent(speaker);
        try {
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            System.out.println("-----------> speaker " + speaker.getUniqueIdName());
            
            if (speaker.getUniqueIdName().equalsIgnoreCase("LOCATIONSID")) {
                if (getMbo().isNew()) {
                    System.out.println("------- isNew");
                    getMbo().setFieldFlag("LOCATION", MboConstants.READONLY, false);
                    System.out.println("------- flag");
                    MboSet exploracoes;
                    exploracoes = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC04", getMbo().getUserInfo());
                    exploracoes.setWhere("id2codprop = '" + getMbo().getString("ID2CODPROP") + "'");
                    System.out.println("------- before reset");
                    exploracoes.reset();
                    System.out.println("------- after reset");
                    getMbo().setValue("LOCATION", getMbo().getString("ID2CODPROP").concat("-").concat("" + (exploracoes.count() + 1)), MboConstants.ALLROWS);
                    //System.out.println("------- after setValue " + getMbo().getString("ID2CODPROP").concat("-").concat("" + (exploracoes.count() + 1)));
                    getMbo().setFieldFlag("LOCATION", MboConstants.READONLY, true);
                    System.out.println("------- after flag");
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
            Logger.getLogger(ID2ExploracaoPecuariaAppBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ID2ExploracaoPecuariaAppBean.class.getName()).log(Level.SEVERE, null, ex);
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
}
