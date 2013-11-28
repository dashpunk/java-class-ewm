package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;

public class NumeroProtocoloPadrao extends MboValueAdapter {
	public NumeroProtocoloPadrao(MboValue mbv) {
		super(mbv);
		System.out.println("*** NumeroProtocoloPadrao ***");
	}

	public void initValue() throws MXException, RemoteException {
		super.initValue();
		
		System.out.println("*** initValue");
		
		numeroSolitacaoAprovacao();
//		numeroSolicitacaoVistoria();
		numeroRegistroEstabelecimento();
	}

	private void numeroSolitacaoAprovacao() throws MXException {
		System.out.println("*** numeroSolitacaoAprovacao");
		try {
			if ((getMboValue().getMbo().getString("ID2NUMPROTOCOLOAPR") != null) && (getMboValue().getMbo().getString("LOCATION") != null) && (!getMboValue().getMbo().getString("ID2NUMPROTOCOLOAPR").startsWith("SAP"))) {
				
				String valor = "SAP."+ Uteis.retiraCaracteresEspeciais(Uteis.adicionaValorEsquerda(getMboValue().getMbo().getString("LOCATION"), "0", 9)) + "."+ new GregorianCalendar().get(GregorianCalendar.YEAR);

				getMboValue().getMbo().setValue("ID2NUMPROTOCOLOAPR", valor);
			}
		} catch (RemoteException re) {
			System.out.println("*** Exceção ao definir o valor de ID2NUMPROTOCOLOAPR: "+ re.getMessage());
		}
	}
	
	private void numeroSolicitacaoVistoria() throws MXException {
		System.out.println("*** numeroSolicitacaoVistoria");
        try {
        	if ((getMboValue().getMbo().getString("ID2NUMPROTOCOLO") != null) && (getMboValue().getMbo().getString("LOCATION") != null) && (!getMboValue().getMbo().getString("ID2NUMPROTOCOLO").startsWith("SVT"))) {
        		
	        	String valor = "SVT." + Uteis.adicionaValorEsquerda(getMboValue().getMbo().getString("LOCATION"),"0", 9)+ "."+ new GregorianCalendar().get(GregorianCalendar.YEAR);
	        	
	        	getMboValue().getMbo().setValue("ID2NUMPROTOCOLO",valor);
        	}
        } catch (RemoteException re) {
        	System.out.println("*** Exceção ao definir o valor de ID2NUMPROTOCOLO: " + re.getMessage());
        }
	}
	
	private void numeroRegistroEstabelecimento() throws MXException {
		System.out.println("*** numeroRegistroEstabelecimento");
        try {
        	if ((getMboValue().getMbo().getString("ID2NUMPROTOCOLOREG") != null) && (getMboValue().getMbo().getString("LOCATION") != null) && (!getMboValue().getMbo().getString("ID2NUMPROTOCOLOREG").startsWith("SRE"))) {
        		
		        	String valor = "SRE." + Uteis.adicionaValorEsquerda(getMboValue().getMbo().getString("LOCATION"),"0", 9)+ "."+ new GregorianCalendar().get(GregorianCalendar.YEAR);
		        	
		        	getMboValue().getMbo().setValue("ID2NUMPROTOCOLOREG",valor);
	        	}
        } catch (RemoteException re) {
        	System.out.println("*** Exceção ao definir o valor de ID2NUMPROTOCOLOREG: " + re.getMessage());
        }
	}
}
