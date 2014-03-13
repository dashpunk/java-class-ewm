package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author willians.andrade
 * 12/03/2014: Solicitado Alteração pelo Rafael para Mudar o Valor
 * de 3,5 pelo informado no campo de tela MSNUMTAXA
 */

public class MsNumFob extends MboValueAdapter {

	public MsNumFob(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		
		System.out.print("********** Entrou na classe MsNumFob.");
		super.validate();

		MboRemote MSTBCOTSVS = getMboValue().getMbo();

		MboSetRemote MAINRECORD = getMboValue().getMbo().getMboSet("MSTBCADDEMSVS");
		float Taxa = MAINRECORD.getMbo(0).getFloat("MSNUMTAXA");
		
		if (!MAINRECORD.getMbo(0).isNull("MSNUMTAXA")){
			// PUFOB(USD)
			if (!MSTBCOTSVS.isNull("MSNUMFOBUSD")) {	
				
				double dezPorcFOB = MSTBCOTSVS.getFloat("MSNUMFOBUSD") * 10/100;
				
				MSTBCOTSVS.setValue("MSNUMPUFOBUSD", dezPorcFOB );
				
				System.out.print("CTIS # " + dezPorcFOB);						
				
			}
				
			// CIF(USD), BUFFER(USD) e (Frete + Seguro(USD))
			if (!MSTBCOTSVS.isNull("MSNUMFOBUSD") && !MSTBCOTSVS.isNull("MSNUMFRETEUSD") && !MSTBCOTSVS.isNull("MSNUMSEGUROUSD")) {
				
				float CIF = MSTBCOTSVS.getFloat("MSNUMFOBUSD") + MSTBCOTSVS.getFloat("MSNUMFRETEUSD") + MSTBCOTSVS.getFloat("MSNUMSEGUROUSD");
				double BUFFER = (MSTBCOTSVS.getFloat("MSNUMFRETEUSD") + MSTBCOTSVS.getFloat("MSNUMSEGUROUSD")) * 10/100;
				float FRETESEGURO = MSTBCOTSVS.getFloat("MSNUMFRETEUSD") + MSTBCOTSVS.getFloat("MSNUMSEGUROUSD");
						
				MSTBCOTSVS.setValue("MSNUMCIF", CIF );
				MSTBCOTSVS.setValue("MSNUMBUFFERUSD", BUFFER );
				MSTBCOTSVS.setValue("MSNUMFRETEESEGUSD", FRETESEGURO );
				
				System.out.print("CTIS # " + CIF + ", "+ BUFFER + ", "+ FRETESEGURO);			
			}
			
	
			// (3,5%(USD)), (3,5% + CIF(USD)) e TOTAL(USD)
			if (!MSTBCOTSVS.isNull("MSNUMCIF")) {
				
				double FOBeTAXA = MSTBCOTSVS.getFloat("MSNUMFOBUSD") * Taxa/100;
				double CIFPorcent = MSTBCOTSVS.getFloat("MSNUMCIF") * Taxa/100;
				double CIFPorcentCIF = MSTBCOTSVS.getFloat("MSNUMCIF") + CIFPorcent;			
				
				MSTBCOTSVS.setValue("MSNUMPERCENT", FOBeTAXA);
				MSTBCOTSVS.setValue("MSNUMCIFEPERCENT", CIFPorcentCIF );
				MSTBCOTSVS.setValue("MSNUMTOTALUSD", CIFPorcentCIF );
				
				System.out.print("CTIS # " + CIFPorcent + ", "+ CIFPorcentCIF);			
			}	
		} else {
			throw new MXApplicationException("mscadcot", "informeTaxaParaCalculos");
		}

	}

}
