package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.Iterator;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.MultiselectDataBean;
import psdi.webclient.system.controller.BoundComponentInstance;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * ID2 Tecnologia S.A.
 * 
* <h4>Descrição da Classe</h4> - Classe criada com o intuito de setar alguns
 * valores de acordo com a seleção dos registros na dialog
 * 
* <h4>Notas</h4>
 * 
* <h4>Referências</h4> Aplicada a tela
 * 
* <h4>Revisões</h4> - Revisão 1.0 -
 *
 * @author Ricardo S Gomes: Classe inicialmente criada.
 * 
*
 * @since 13/03/2012 10:00
 * @version 1.0
 */
public class DefinestatusAppBeanI extends DataBean {

    String statusSel = "";

    public DefinestatusAppBeanI() {
        super();
        System.out.println("_______________ Construtor - DefinestatusAppBeanF");
    }

    @Override
    public void bindComponent(BoundComponentInstance boundComponent) {

        super.bindComponent(boundComponent);

        //TODO melhorar isso
        try {
            System.out.println("..... bindComponent status origem " + getMbo().getString("FKMXDSCSTAORI"));
            System.out.println("..... bindComponent status statusSel " + statusSel);
            getMbo().setValue("FKMXDSCSTAORI", statusSel, MboConstants.NOACCESSCHECK);
            System.out.println("..... bindComponentstatus origem " + getMbo().getString("FKMXDSCSTAORI"));
        } catch (MXException e) {
            System.out.println("... bindComponent MXException " + e.getMessage());
        } catch (RemoteException e) {
            System.out.println("... bindComponent RemoteException " + e.getMessage());
        }

    }

//    @Override
//    public synchronized boolean fetchRecordData() throws MXException, RemoteException {
//        
//        System.out.println("..... fetchRecordData status origem " + getMbo().getString("FKMXDSCSTAORI"));
//        System.out.println("..... fetchRecordData status statusSel " + statusSel);
//        getMbo().setValue("FKMXDSCSTAORI", statusSel, MboConstants.NOACCESSCHECK);
//        System.out.println("..... fetchRecordData origem " + getMbo().getString("FKMXDSCSTAORI"));
//        
//        return super.fetchRecordData();
//    }

    @Override
    protected void initialize() throws MXException, RemoteException {

        System.out.println("________________ initialize - DefinestatusAppBeaD");
        super.initialize();

        //Verifica se todos os status dos registros selecionados s�o iguais    	
        int i = 0;
        int status = 0;
        String dsStatus = "";
        String codPrograma = "";
        MboSetRemote tableBeanDestino = app.getResultsBean().getMboSet();

        if (app == null || app.getResultsBean() == null || tableBeanDestino == null) {
            return;
        }

        System.out.println("####### Selection = " + tableBeanDestino.getSelection());
        if (tableBeanDestino.getSelection() != null && tableBeanDestino.getSelection().size() >= 1) {

            Iterator itSelec = tableBeanDestino.getSelection().iterator();

            while (itSelec.hasNext()) {
                MboRemote item = (MboRemote) itSelec.next();
                System.out.println("############### Destino (" + i + ") Status = " + item.getString("DSSTAINT"));
                System.out.println("############### Destino (" + i + ") Tecnico = " + item.getString("FKSTCODTEC"));
                if (i != 0 && status != item.getInt("FKMXNUCODSTATUS")) {
                    System.out.println("############### O status anterior � diferente do atual");
                    Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
                    sessionContext.queueRefreshEvent();
                    WebClientEvent event = sessionContext.getCurrentEvent();
                    Utility.showMessageBox(event, new MXApplicationException("dialog", "statusSelecionadosDiferentes"));
                    return;
                    //throw new MXApplicationException("dialog", "statusSelecionadosDiferentes");
                }

                if (i != 0 && !item.getString("upcodprog").equals(codPrograma)) {
                    System.out.println("############### O codPrograma anterior � diferente do atual");
                    Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
                    sessionContext.queueRefreshEvent();
                    WebClientEvent event = sessionContext.getCurrentEvent();
                    Utility.showMessageBox(event, new MXApplicationException("dialog", "codProgramaSelecionadosDiferentes"));
                    return;
                    //throw new MXApplicationException("dialog", "statusSelecionadosDiferentes");
                }


                status = item.getInt("FKMXNUCODSTATUS");
                codPrograma = item.getString("upcodprog");
                dsStatus = item.getString("DSSTAINT");
                i++;
            }

            DataBean tableBeanOrigem = app.getDataBean("definesta");
            //tableBeanOrigem.getMbo().setValue("FKMXDSCSTAORI", dsStatus, MboConstants.NOVALIDATION_AND_NOACTION);
            tableBeanOrigem.getMbo().setValue("FKMXNUCODSTAORI", status, MboConstants.NOVALIDATION_AND_NOACTION);
            System.out.println("___ antes de limpar o campo " + tableBeanOrigem.getMbo().getString("FKSTCODTECFINORI"));
            tableBeanOrigem.getMbo().setValueNull("FKSTCODTECFINORI", MboConstants.NOACCESSCHECK);
            System.out.println("___ depois de limpar o campo " + tableBeanOrigem.getMbo().getString("FKSTCODTECFINORI"));


            System.out.println("..... status origem " + getMbo().getString("FKMXDSCSTAORI"));
            System.out.println("..... status destino " + tableBeanDestino.getMbo().getString("dsstaint"));
            statusSel = tableBeanDestino.getMbo().getString("dsstaint");
            getMbo().setValue("FKMXDSCSTAORI", statusSel, MboConstants.NOACCESSCHECK);
            System.out.println("..... status origem " + getMbo().getString("FKMXDSCSTAORI"));
        } else {
            System.out.println("############## Caiu na exce��o...");
            Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
            sessionContext.queueRefreshEvent();
            WebClientEvent event = sessionContext.getCurrentEvent();
            Utility.showMessageBox(event, new MXApplicationException("dialog", "deveEstarSelecionadoAoMenosUm"));
            return;
            //throw new MXApplicationException("dialog", "deveEstarSelecionadoAoMenosUm");
        }

        System.out.println("############# Fim do initialize...");
    }

    public int selMemos() throws MXException, java.rmi.RemoteException {

        super.execute();

        DataBean tableBeanOrigem = app.getDataBean("definesta");
        System.out.println("############# TableBeanOrigem = " + tableBeanOrigem);
        System.out.println("############# FKSTCODTECORI = " + tableBeanOrigem.getMbo().getString("FKSTCODTECFINORI"));
        //System.out.println("############# FKNOM = " + tableBeanOrigem.getMbo().getMboSet("RL04PER").getMbo(0).getString("DISPLAYNAME"));


        MboSetRemote tableBeanDestino = app.getResultsBean().getMboSet();

        MboRemote mbo;
        int cont = 0;
        for (int i = 0; ((mbo = tableBeanDestino.getMbo(i)) != null); i++) {
            if (mbo.isSelected()) {
                
//                mbo.setValue("FKSTCODTEC", tableBeanOrigem.getMbo().getString("FKSTCODTECFINORI"), MboConstants.NOVALIDATION_AND_NOACTION);
//                mbo.setValue("FKSTNOMTEC", tableBeanOrigem.getMbo().getMboSet("RL04PER").getMbo(0).getString("DISPLAYNAME"), MboConstants.NOVALIDATION_AND_NOACTION);

                //Adicionado para definir os status tamb�m.
//                System.out.println("########### FKMXNUCODSTAORI=" + tableBeanOrigem.getMbo().getString("FKMXNUCODSTAORI"));
//                System.out.println("########### FKMXDSCSTAORI=" + tableBeanOrigem.getMbo().getString("FKMXDSCSTAORI"));
                System.out.println("___ contador "+ ++cont+" OBS "+tableBeanOrigem.getMbo().getString("DSOBSSTA"));
                mbo.setValue("DSSTAINT", tableBeanOrigem.getMbo().getString("FKMXDSCSTAORI"), MboConstants.NOVALIDATION_AND_NOACTION);
                mbo.setValue("FKMXNUCODSTATUS", tableBeanOrigem.getMbo().getString("FKMXNUCODSTAORI"), MboConstants.NOVALIDATION_AND_NOACTION);
                mbo.setValue("DSOBSSTA", tableBeanOrigem.getMbo().getString("DSOBSSTAORI"), MboConstants.NOVALIDATION_AND_NOACTION);
                System.out.println("___ contador "+ cont+" OBS "+tableBeanOrigem.getMbo().getString("DSOBSSTA"));
//                System.out.println("*** FKSTCODTECORI " + tableBeanOrigem.getMbo().getString("FKSTCODTECORI"));
//                System.out.println("*** RL04PER.count() " + tableBeanOrigem.getMbo().getMboSet("RL04PER").count());
//                System.out.println("*** DISPLAYNAME " + tableBeanOrigem.getMbo().getMboSet("RL04PER").getMbo(0).getString("DISPLAYNAME"));
            }

        }
        
        System.out.println("___antes do delete");
        tableBeanOrigem.getMbo().delete(MboConstants.NOACCESSCHECK);
        System.out.println("___depois do delete");
        tableBeanDestino.save();
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        return 1;
    }
}
