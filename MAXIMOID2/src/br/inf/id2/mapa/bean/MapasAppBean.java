package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.BoundComponentInstance;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class MapasAppBean extends psdi.webclient.system.beans.AppBean {

    String campo = "";
    String relacionamento = "";

    public MapasAppBean() {
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
        System.out.println("------ MapasAppBean initialize()");
        System.out.println("----- MapasAppBean()");

        if (app.getApp().equalsIgnoreCase("id2map01")) {
            campo = "MANUMREG";
            relacionamento = "MATBSALABA";
        }
        if (app.getApp().equalsIgnoreCase("id2map03")) {
            campo = "MANUMREG";
            relacionamento = "MAPROCON";
        }
        if (app.getApp().equalsIgnoreCase("id2map04")) {
            campo = "MANUMREG";
            relacionamento = "MAMAPPRO";
        }
        if (app.getApp().equalsIgnoreCase("id2map05")) {
            campo = "MANUMREG";
            relacionamento = "MARLRECE";
        }
        if (app.getApp().equalsIgnoreCase("id2map06")) {
            campo = "MANUMREG";
            relacionamento = "MARLCOM";
        }
        if (app.getApp().equalsIgnoreCase("id2map07")) {
            campo = "MANUMREG";
            relacionamento = "MARLIMP";
        }

        System.out.println("-----> MapasAppBean() campo " + campo);
        System.out.println("-----> MapasAppBean() relacionamento " + relacionamento);

        super.initialize();
        getMbo().setFieldFlag(campo, MboConstants.READONLY, true);
        System.out.println("---> READONLY");
        //verificaMboSet();
    }


    @Override
    public void bindComponent(BoundComponentInstance boundComponent) {
        System.out.println("------ MapasAppBean bindComponent() " + boundComponent.getProperty("dataattribute"));
        super.bindComponent(boundComponent);
        try {
            if (boundComponent.getProperty("dataattribute").equalsIgnoreCase(campo)) {
                verificaMboSet();
            }
        } catch (MXException ex) {
            Logger.getLogger(MapasAppBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(MapasAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        System.out.println("------ MapasAppBean dataChangedEvent()");
        super.dataChangedEvent(speaker);
        try {
            verificaMboSet();
        } catch (MXException ex) {
            Logger.getLogger(MapasAppBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(MapasAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void fireChildChangedEvent() {
        System.out.println("------ MapasAppBean fireChildChangedEvent()");
        super.fireChildChangedEvent();
        try {
            verificaMboSet();
        } catch (MXException ex) {
            Logger.getLogger(MapasAppBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(MapasAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        System.out.println("-----MapasAppBean SAVE()");
        
        
        //Solicitado pelo André 19/10/2011
       /* String[] meses = {"Janeiro", "Fevereiro", 
                "Março", "Abril", "Maio", "Junho", 
                "Julho", "Agosto", "Setembro", "Outubro",
  	            "Novembro", "Dezembro"};  */
        
        if (getMbo().isNull("MADATCON")) {
            throw new MXApplicationException("ID2VWLOC06", "ID2VWLOC06");
        }
        
        MboSet mboSetVwLoc06;
        mboSetVwLoc06 = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC06", sessionContext.getUserInfo());
        System.out.println("########## Quantidade Antes: " + mboSetVwLoc06.count());
        
        //Incluido por Leysson e solicitado pelo André dia 20/10/2011
        mboSetVwLoc06.setWhere("MADATCON = '" + getMbo().getString("MADATCON") + "' AND APPNAME = '" + appName.toUpperCase() + "'");
        System.out.println("######### App = " + appName.toUpperCase());
        mboSetVwLoc06.reset();
        System.out.println("########## Quantidade Depois: " + mboSetVwLoc06.count());
        if(mboSetVwLoc06.count() > 0 && getMbo().isNew()) {
        	throw new MXApplicationException("mtlotaba", "DataConJaExiste");
        }
        
     
        
        System.out.println("########## Salvando mes condenacao ##########");
        
        String mes = getMbo().getString("MADATCON");
        System.out.println("########## MesCondenacao: " + mes );
    	
        //String[] valores = mes.split("/");
        		
        //getMbo().setValue("ID2MAPMES", meses[Integer.valueOf(valores[0])-1]);
        getMbo().setValue("ID2MAPMES", mes.substring(0,2));
        
        System.out.println("########## Salvando ano condenacao ##########");
        
        String ano = getMbo().getString("MADATCON");
        System.out.println("########## AnoCondenacao: " + ano.substring(3) );
        
        getMbo().setValue("ID2ANOMAP", ano.substring(3));
        
       
        //###################################################################
        
        
        int retorno = super.SAVE();
        verificaMboSet();
        return retorno;
    }

    private void verificaMboSet() throws MXException, RemoteException {

        System.out.println("------ MapasAppBean verificaMboSet() " + app.getApp());

        System.out.println("------- count " + getMbo().getMboSet(relacionamento).count());

        getMbo().setFieldFlag(campo, MboConstants.READONLY, (getMbo().getMboSet(relacionamento).count() > 0));

        System.out.println("------ MapasAppBean FIM ");

    }
}
