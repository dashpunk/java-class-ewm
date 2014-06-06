package br.inf.id2.ms.bean;

import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author andrel.almeida
 */
public class MSCLPO02 extends psdi.webclient.beans.po.POAppBean {

    // variaveis
	int qtdAnexoMsg = 0;
	
    public MSCLPO02() {
    }

    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {

        Executa.atualizaAtributo((MboSet) getMbo().getMboSet("POLINE"), "GLDEBITACCT", "0.0.0.0");

        //verifica se algum poline foi deletado
        MboRemote mbo;
        MboSet prlineSet;
        System.out.println("---- prline count() " + getMbo().getMboSet("POLINE").count());
        for (int i = 0; ((mbo = getMbo().getMboSet("POLINE").getMbo(i)) != null); i++) {
            System.out.println("---- i " + i);
            if (mbo.toBeDeleted()) {
                System.out.println("---- i " + i + " del");
                MboSet polineSet;
                polineSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("POLINE", sessionContext.getUserInfo());

                polineSet.setWhere("polineid <> " + mbo.getInt("POLINEID") + " AND prlineid = " + mbo.getInt("PRLINEID"));
                polineSet.reset();

                System.out.println("---- polinecrit count " + polineSet.count());

                if (polineSet.count() == 0) {

                    prlineSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("PRLINE", sessionContext.getUserInfo());

                    prlineSet.setWhere("prlineid = " + mbo.getInt("PRLINEID"));
                    prlineSet.reset();
                    System.out.println("---- prlineSetCrit count " + prlineSet.count());
                    MboRemote mbob;
                    if ((mbob = prlineSet.getMbo(0)) != null) {
                        System.out.println("---- prlineSetCrit b");
                        mbob.setValue("ID2STATUS", "ENVIADO", MboConstants.NOACCESSCHECK);
                        System.out.println("---- prlineSetCrit a");
                        prlineSet.save();
                        System.out.println("---- prlineSetCrit a save");
                    }

                }

            } else {
                System.out.println("---- não marcado para deletar");
                prlineSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("PRLINE", sessionContext.getUserInfo());

                prlineSet.setWhere("prlineid = " + mbo.getInt("PRLINEID"));
                prlineSet.reset();
                System.out.println("---- prlineSetCrit count " + prlineSet.count());
                MboRemote mbob;
                if ((mbob = prlineSet.getMbo(0)) != null) {
                    System.out.println("---- prlineSetCrit b");
                    mbob.setValue("ID2STATUS", "TR", MboConstants.NOACCESSCHECK);
                    System.out.println("---- prlineSetCrit a");
                    prlineSet.save();
                    System.out.println("---- prlineSetCrit a save");
                }
            }

        }
        
        System.out.println("*************** Adicionando valores aos campos...");
        System.out.println("########### MS_RL04PER = " + getMbo().getMboSet("MS_RL04PER"));
        System.out.println("########### ID2CODCOO = " + getMbo().isNull("ID2CODCOO"));
        if ((getMbo().getMboSet("MS_RL04PER") != null) && (getMbo().isNull("ID2CODCOO"))) {
        	System.out.println("###################### ID2Lotacao = " + getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO"));
            if (getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO") != null) {
                getMbo().setValue("ID2CODCOO", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO"));
                getMbo().setValue("ID2SEC", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2SEC"));
                System.out.println("################# ID2DIR = " + getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2DIR"));
                getMbo().setValue("ID2DIR", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2DIR"));
            } else {
                System.out.println("##################### Excecao");
                throw new MXApplicationException("pr", "SemLotacao");
            }
        }
        System.out.println("####################### Campos adicionados!");
        

        return super.SAVE();
    }

    @SuppressWarnings("null")
	@Override
    protected void initialize() throws MXException, RemoteException {
    	super.initialize();
    	MboRemote mboPec = null;    	  	
    	    	
    	System.out.println(">>>>>>>>>>>>Quantidade de registros na tabela de Anexos do PEC: "+ getMbo().getMboSet("MSPECANEXOS").count());
    	
    	mboPec.setValue("MSQTDANEXOPEC",getMbo().getMboSet("MSPECANEXOS").count(), MboConstants.NOACCESSCHECK);
    	System.out.println(">>>>>>>>>>>>Setando a quantidade de anexos/mensagens");
    	super.save();
    }

}


