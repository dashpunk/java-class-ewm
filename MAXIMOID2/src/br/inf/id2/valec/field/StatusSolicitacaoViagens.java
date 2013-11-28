package br.inf.id2.valec.field;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Executa;

/**
 * 
 * @author Dyogo
 *  
 */
public class StatusSolicitacaoViagens extends MboValueAdapter {

    public StatusSolicitacaoViagens(MboValue mbv) {
        super(mbv);
        System.out.println("############# CONSTRUTOR");
    }

    @Override
    public void validate() throws MXException, RemoteException {

    	System.out.println("############# Entrou no Validate");
        String status = getMboValue().getString();
        int mxReplan = getMboValue().getMbo().getInt("MXREPLAN");
        System.out.println("############# STATUS do SOLVIA = " + status + " | MXREPLAN = " + mxReplan);
        
        if (status != null) {
        	
        	if (status.equals("EM REPLANEJAMENTO")) {
        		
        		//Copiando o detalhamento de solicitação para o histório (DETSOL para ADETSOL)
        		MboSetRemote mboSetOrigem = getMboValue().getMbo().getMboSet("RL01DETSOL");
        		MboSetRemote mboSetDestino = getMboValue().getMbo().getMboSet("RL01ADETSOL");
//        		try {
//	        		String[] attrs = mboSetOrigem.getKeyAttributes();
//	        		System.out.println("################# KEY ATTRS = " + attrs);
//	        		for (int i=0; i < attrs.length; i++) {
//	        			System.out.println("#### ATTR(" + i + ") = " + attrs[i]);
//	        			System.out.println("###### Valor " + attrs[i] + " = " + mboSetOrigem.getMbo(0).getMboValueData(attrs[i]).getData());
//	        		}
//        		} catch (Exception e) {
//        			System.out.println("##################### EXCEÇÃO...");
//        		}

        		System.out.println("######## Revisão anterior = " + getMboValue().getMbo().getInt("MXREPLAN"));
        		
        		getMboValue().getMbo().setValue("MXREPLAN", mxReplan+1, MboConstants.NOVALIDATION_AND_NOACTION);
        		Executa.duplicaMBO(mboSetOrigem, mboSetDestino,
        						   new String[] {"TBDETSOLID", "DESCRIPTION", "HASLD", "TBSOLVIAID", "ITLOCORI",
        						   				 "ITLOCDES", "SITEMPER", "DCVALDIA", "DCVALPAS", "ALLOCDES",
        						   				 "ALLOCORI", "DTDTAINI", "DTDATFIM", "DSMETRANS",
        						   				 "TBTEMPID", "TBLOCID", "DSPERSAI", "DCDESPLOC", "YNDESLOC",
        						   				 "TBMETRANSID"}, new String[] {"MXREVISAO"}, new String[]{"" + mxReplan});
        		

        		//Copiando o detalhamento de solicitação para o histório (TBSOLVIA para MXTBSOLVIA)
        		MboSetRemote mboSetOrigemSol = getMboValue().getMbo().getThisMboSet();
        		MboSetRemote mboSetDestinoSol = getMboValue().getMbo().getMboSet("RL01MXTBSOLVIA");

        		getMboValue().getMbo().setValue("MXREPLAN", mxReplan+1, MboConstants.NOVALIDATION_AND_NOACTION);
        		Executa.duplicaMBO(mboSetOrigemSol, mboSetDestinoSol,
        						   new String[] {"DCDESPPAS", "DCMEIDIA", "DCTOTDESLOC", "DCVALTOT", "DESCRIPTION",
        						   				 "DSENQ", "DSJUS", "DSMETRANS", "DSMOTVIA", "DSPERFIMSEM",
        						   				 "DSREFDES", "DSSTA", "MXTOTDIA", "TBSOLVIAID", "UPNOMCAT", "UPSOL",
        						   				 "UPVIA"}, new String[] {"MXREPLAN"}, new String[]{"" + mxReplan});

        		
        		
        		System.out.println("######## Revisão depois = " + getMboValue().getMbo().getInt("MXREPLAN"));
        		//Atualizando os valores
        		double meiaDiaria = getMboValue().getMbo().getDouble("DCMEIDIA");
        		atualizaValoresMeiaDiaria((MboSet)mboSetDestino, meiaDiaria);

        	}
        	
        }

        super.validate();
        
    }

	private void atualizaValoresMeiaDiaria(MboSet mboSetDestino, double meiaDiaria) throws MXException, RemoteException {

		System.out.println("################ Atualizando valores em replanejamento. Tamanho do Set=" + mboSetDestino.count());
		for (int i=0; i< mboSetDestino.count(); i++) {
			Mbo mboDestino = (Mbo) mboSetDestino.getMbo(i);
			if (mboDestino != null) {
				System.out.println("################ SITEMPER=" + mboDestino.getString("SITEMPER") + "| Setando Meia diária=" + meiaDiaria);
				if (mboDestino.getString("SITEMPER") == null && mboDestino.getString("SITEMPER").trim().equals("")) {
					//Setando meia diária
					//mboDestino.setValue("SITEMPER", 0.5);
				}
				mboDestino.setValue("DCMEIDIA", meiaDiaria);
			}
		}
		mboSetDestino.save();
		
	}
}
