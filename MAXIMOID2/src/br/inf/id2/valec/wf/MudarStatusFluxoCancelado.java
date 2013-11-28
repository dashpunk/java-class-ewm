package br.inf.id2.valec.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MudarStatusFluxoCancelado implements ActionCustomClass {

    public MudarStatusFluxoCancelado() {
    	super();
    	System.out.println("############################### ENTROU NO CONSTRUTOR()");
        //super();
    }


    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
            throws MXException, java.rmi.RemoteException {
    	
    	System.out.println("############################### ENTROU NO APPLYCUSTOMACTION() - MudarStatusFluxo");
    	if (mbo.getMboSet("RL01CONCOR").count() > 0) {

    		MboSet setDiaVia = (MboSet) mbo.getMboSet("RL01DIAVIA");
        	if (setDiaVia != null && setDiaVia.count() > 0) {
        		mbo.getMboSet("RL01CONCOR").getMbo(0).setValue("MXSTATUS", "CANCELADO");
        		MboSet setContaCorrente = (MboSet) setDiaVia.getMbo(0).getMboSet("RL01CONCOR");
        		atualizaDiarias(setContaCorrente, (Mbo)setDiaVia.getMbo(0), (Mbo) mbo);
        	}
    	}
    	mbo.getMboSet("RL01CONCOR").save();
    }
    
	public void atualizaDiarias(MboSet setContaCorrente, Mbo mboMembro, Mbo mboAtual) throws MXException, RemoteException {
		
		int iDiaria = mboMembro.getInt("MXSALDOINI");
		int iDiariasCons = 0;
		System.out.println("############# Diaria = " + iDiaria);
		if (setContaCorrente != null) {
			for (int k=0; k < setContaCorrente.count(); k++ ) {
				Mbo mboConta = (Mbo) setContaCorrente.getMbo(k);
				System.out.println("############ Valor do Crédito: " + mboConta.getInt("MXCREDIA") + "| Status = " + mboConta.getString("MXSTATUS") + "|SOLVIAID = " + mboAtual.getInt("TBSOLVIAID"));
				if (mboConta.getString("MXTIPOPE").equals("CREDITO")) {
					iDiaria += mboConta.getInt("MXCREDIA");
				} else if (mboConta.getString("MXTIPOPE").equals("DEBITO") && !mboConta.getString("MXSTATUS").equals("CANCELADO") && (mboConta.getInt("TBSOLVIAID") != mboAtual.getInt("TBSOLVIAID"))) {
					iDiaria -= mboConta.getInt("MXCREDIA");
					iDiariasCons += mboConta.getInt("MXCREDIA");
				}
			}
		}
		
		System.out.println("################ Valor da Diária=" + iDiaria + "|Saldo Atual=" + mboMembro.getInt("MXSALDO")); 
		
		if (iDiaria < 0) {
			throw new MXApplicationException("passagens", "NumeroDiariasInferiorSaldo");
		}
		
		System.out.println("############### Setando o MXSaldo = " + iDiaria);
		mboMembro.setValue("MXSALDO", iDiaria);
		mboMembro.setValue("MXDIACON", iDiariasCons);
		
	}
}

