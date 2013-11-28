package br.inf.id2.valec.bean;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import br.inf.id2.common.util.Data;

/**
 *
 * @author Dyogo
 *
 */
public class ControleCentrosCusto extends psdi.webclient.system.beans.AppBean {

    public ControleCentrosCusto() {
    }

    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    
    @Override
    public synchronized void listenerChangedEvent(DataBean speaker) {
    	System.out.println("################# Listener - ControleCentrosCusto");
    	try {
    		
			if (getMbo().getMboSet("RL01DPORAR") != null && getMbo().getMboSet("RL01DPORAR").count() > 0) {
				
				if (getMbo().getMboSet("RL01DPORAR").getMbo().getMboSet("RL01DIAVIA").count() >= 1) {
					return;
				}
				
		        MboSet tbMembro = (MboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01DPORAR").getMbo().getMboSet("RL01DIAVIA");
		        System.out.println("############# Quantidade de Pessoas = " + getMbo().getMboSet("RL01PER").count());
		        for (int i=0; i < getMbo().getMboSet("RL01PER").count(); i++) {
		        	Mbo mboNovoMembro = (Mbo) tbMembro.add();
			        mboNovoMembro.setValue("PERSONID", getMbo().getMboSet("RL01PER").getMbo(i).getString("PERSONID"));
			        mboNovoMembro.setValue("MXTBDPORARID", getMbo().getMboSet("RL01DPORAR").getMbo().getString("MXTBDPORARID"));
			        mboNovoMembro.setValue("MXDIACON", "0");
			        //tbMembro.save();
			        //refreshTable();
			        //reloadTable();
					
					String valorDiaria = getMbo().getString("MXVPDIA");
					if (valorDiaria != null && !valorDiaria.equals("")) {
				        mboNovoMembro.setValue("MXSALDO", valorDiaria);
				        mboNovoMembro.setValue("MXSALDOINI", valorDiaria);
					} else {
						mboNovoMembro.setValue("MXSALDO", "0");
						mboNovoMembro.setValue("MXSALDOINI", "0");
					}
		        }

				System.out.println("############### ID DO PAI=" + getMbo().getMboSet("RL01DPORAR").getMbo().getString("MXTBDPORARID"));

	        }

    	} catch (Exception e) {
    		System.out.println("############### ERRO Listener: " + e.getMessage());
    		e.printStackTrace();
    	}
    	
    }
    
    
    
    @Override
    public int SAVE() throws MXException, RemoteException {
    
    	super.SAVE();
    	atualizaValores();
    	return super.SAVE();
    }

	private void atualizaValores() throws MXException, RemoteException {
    	MboSet tbOrcamentos = (MboSet)app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01DPORAR");
    	System.out.println("############## Quantidade de Orçamento = " + tbOrcamentos);
    	if (tbOrcamentos != null) {
    		for (int i=0; i < tbOrcamentos.count(); i++ ) {
    			System.out.println("########### Orcamento (" + i + ")");
    			Mbo mboOrcamento = (Mbo) tbOrcamentos.getMbo(i);
    			MboSet setMembrosCentro = (MboSet)mboOrcamento.getMboSet("RL01DIAVIA");
    			System.out.println("############## SetMembros do Centro de Custo = " + setMembrosCentro);
    			if (setMembrosCentro != null) {
	    			for (int j=0; j < setMembrosCentro.count(); j++ ) {
	        			System.out.println("########### Membros Centro (" + j + ")");
	    				Mbo mboMembro = (Mbo) setMembrosCentro.getMbo(j);
	    				MboSet setContaCorrente = (MboSet)mboMembro.getMboSet("RL01CONCOR");
	    				double iDiaria = mboMembro.getDouble("MXSALDOINI");
	    				double iDiariasCons = 0;
	    				System.out.println("############# Diaria = " + iDiaria);
	    				if (setContaCorrente != null) {
	    					for (int k=0; k < setContaCorrente.count(); k++ ) {
	    						Mbo mboConta = (Mbo) setContaCorrente.getMbo(k);
	    						System.out.println("############ Valor do Crédito: " + mboConta.getDouble("MXCREDIA"));
	    						if (mboConta.getString("MXTIPOPE").equals("CREDITO")) {
	    							iDiaria += mboConta.getDouble("MXCREDIA");
	    						} else if (mboConta.getString("MXTIPOPE").equals("DEBITO") && !mboConta.getString("MXSTATUS").equals("CANCELADO")) {
	    							iDiaria -= mboConta.getDouble("MXCREDIA");
	    							iDiariasCons += mboConta.getDouble("MXCREDIA");
	    						}
	    					}
	    				}
	    				System.out.println("############### Setando o MXSaldo = " + iDiaria);
	    				mboMembro.setValue("MXSALDO", iDiaria);
	    				mboMembro.setValue("MXDIACON", iDiariasCons);
	    				
	    				Date dtInicial = mboOrcamento.getDate("MXDATINI");
	    				Date dtFinal = mboOrcamento.getDate("MXDATFIM");
	    				int iDiasPeriodo = Data.recuperaDiasEntreDatas(dtInicial, dtFinal);
	    				System.out.println("############## Dias Período = " + iDiasPeriodo);
	    				if (iDiasPeriodo < iDiaria) {
	    					throw new MXApplicationException("passagens", "NumeroDiariasSuperiorPeriodo");
	    				}
	    			}
    			}
    		}
    	}
	}
    
    
    
}
