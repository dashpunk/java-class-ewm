package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.BoundComponentInstance;

public class AprovParecerTecDipoa extends AppBean {

    String nome;
    String cargo;
	
    public AprovParecerTecDipoa()
    {
        nome = "---";
        cargo = "---";
    }

    protected void initialize()
        throws MXException, RemoteException
    {
        super.initialize();
        atualizaAtributos();
    }

    public int SAVE()
        throws MXException, RemoteException
    {
        atualizaAtributos();
        return super.SAVE();
    }

    public int INSERT()
        throws MXException, RemoteException
    {
        int retorno = super.INSERT();
        atualizaAtributos();
        return retorno;
    }

    public synchronized void dataChangedEvent(DataBean speaker)
    {
        super.dataChangedEvent(speaker);
        atualizaAtributos();
    }

    public synchronized void listenerChangedEvent(DataBean speaker)
    {
        super.listenerChangedEvent(speaker);
        atualizaAtributos();
    }

    public void bindComponent(BoundComponentInstance boundComponent)
    {
        super.bindComponent(boundComponent);
        if(boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2RESAPRDIPPROJ") || boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2RESAPRLEI") || boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2RESAPRMEL") || boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2RESAPRPES") || boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2RESAPROVO"))
            atualizaAtributos();
    }

    public synchronized int execute()
        throws MXException, RemoteException
    {
        int resultado = super.execute();
        atualizaAtributos();
        return resultado;
    }

    private void atualizaAtributos()
    {
        try
        {
            if(nome.equalsIgnoreCase("---"))
            {
                MboSet pessoa = (MboSet)MXServer.getMXServer().getMboSet("PERSON", sessionContext.getUserInfo());
                pessoa.setWhere((new StringBuilder()).append("personid = '").append(sessionContext.getUserInfo().getUserName()).append("'").toString());
                pessoa.reset();
                System.out.println("after reset");
                nome = pessoa.getMbo(0).getString("DISPLAYNAME");
                cargo = pessoa.getMbo(0).getString("TITLE");
                System.out.println((new StringBuilder()).append("nome ").append(nome).toString());
                System.out.println((new StringBuilder()).append("cargo ").append(cargo).toString());
                Thread.sleep(0L);
            }
        	// Solicitação do André referente a alteração do nome da DISPLAYNAME da PERSON afeta o bloco condicional abaixo. alterado em 10/07/2012
			if(this.nome.equalsIgnoreCase("DICAR - Chefe")) {
				getMbo().setFieldFlag(new String[] {"ID2RESAPRDIPPROJ"},7L, false);
				Thread.sleep(0L);
				getMbo().setValue("ID2RESAPRDIPPROJ", this.nome, 9L);
				Thread.sleep(0L);
				getMbo().setFieldFlag(new String[] { "ID2RESAPRDIPPROJ"},7L, true);
			} else if(this.nome.equalsIgnoreCase("DILEI - Chefe")) {
				getMbo().setFieldFlag(new String[] {"ID2RESAPRLEI"},7L, false);
				Thread.sleep(0L);
				getMbo().setValue("ID2RESAPRLEI", this.nome, 9L);
				Thread.sleep(0L);
				getMbo().setFieldFlag(new String[] { "ID2RESAPRLEI"},7L, true);
			} else if(this.nome.equalsIgnoreCase("DICS - Chefe")) {
				getMbo().setFieldFlag(new String[] {"ID2RESAPRMEL"},7L, false);
				Thread.sleep(0L);
				getMbo().setValue("ID2RESAPRMEL", this.nome, 9L);
				Thread.sleep(0L);
				getMbo().setFieldFlag(new String[] { "ID2RESAPRMEL"},7L, true);
			} else if(this.nome.equalsIgnoreCase("DIPES - Chefe")){
				getMbo().setFieldFlag(new String[] {"ID2RESAPRPES"},7L, false);
				Thread.sleep(0L);
				getMbo().setValue("ID2RESAPRPES", this.nome, 9L);
				Thread.sleep(0L);
				getMbo().setFieldFlag(new String[] { "ID2RESAPRPES"},7L, true);
			} else if(this.nome.equalsIgnoreCase("DICAO - Chefe")) {
				getMbo().setFieldFlag(new String[] {"ID2RESAPROVO"},7L, false);
				Thread.sleep(0L);
				getMbo().setValue("ID2RESAPROVO", this.nome, 9L);
				Thread.sleep(0L);
				getMbo().setFieldFlag(new String[] { "ID2RESAPROVO"},7L, true);
				
			}  
        }
        catch(InterruptedException ex)
        {
            Logger.getLogger(AprovParecerTecDipoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(MXException ex)
        {
            Logger.getLogger(AprovParecerTecDipoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(RemoteException ex)
        {
            Logger.getLogger(AprovParecerTecDipoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}