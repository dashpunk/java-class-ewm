package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author willians.andrade
 * 
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
			
			double CIFPorcent = MSTBCOTSVS.getFloat("MSNUMCIF") * 3.5/100;
			double CIFPorcentCIF = MSTBCOTSVS.getFloat("MSNUMCIF") + CIFPorcent;			
			
			MSTBCOTSVS.setValue("MSNUMPERCENT", CIFPorcent);
			MSTBCOTSVS.setValue("MSNUMCIFEPERCENT", CIFPorcentCIF );
			MSTBCOTSVS.setValue("MSNUMTOTALUSD", CIFPorcentCIF );
			
			System.out.print("CTIS # " + CIFPorcent + ", "+ CIFPorcentCIF);			
		}				

	}

}
