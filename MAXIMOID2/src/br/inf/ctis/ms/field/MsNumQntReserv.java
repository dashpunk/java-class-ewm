package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MsNumQntReserv extends MboValueAdapter {

	private double valorinicial = 0d;
	
	public MsNumQntReserv(MboValue mbv) {
		super(mbv);
		
		/*try {
			System.out.println("########## Entrou construtor() : " + getMboValue().getDouble());
			valorinicial = getMboValue().getDouble();
		} catch (MXException e) {
			Logger.getLogger(MsContrato.class.getName()).log(Level.SEVERE, null, e);
		}*/
		
	}
	
	@Override
	public void init() throws MXException, RemoteException {
		
		super.init();
		
		System.out.println("########## Entrou init() : " + getMboValue().getDouble());
		valorinicial = getMboValue().getDouble();
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		
		System.out.print("########## Entrou na classe MsNumQntReserv.");
		super.validate();
		
		MboRemote MSTBMEDALMOX = getMboValue().getMbo();

		if (!MSTBMEDALMOX.isNull("MSNUMQNTTOTAL")) {

			double Total = MSTBMEDALMOX.getFloat("MSNUMQNTTOTAL")
					- MSTBMEDALMOX.getFloat("MSNUMQNTRESERV");

			MSTBMEDALMOX.setValue("MSNUMQNTDISP", Total);

			System.out.print("CTIS # " + Total);

		}

		
		double valor = 0d;
		valor = getMboValue().getDouble();
		System.out.println("########## valor = " + valor);
		System.out.println("########## valorinicial = " +valorinicial);
		
		double reserva = 0d;
		MboRemote mbo;
		
		if (valor < valorinicial) {
			
			for (int i = 0; ((mbo = getMboValue().getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(i)) != null); i++) {
				
				System.out.println("########## MSQTD = " + mbo.getDouble("MSQTD"));
				reserva += mbo.getDouble("MSQTD");
				System.out.println("########## reserva = " + reserva);
			}
			
			System.out.println("########## valor = " + valor);
			System.out.println("########## reserva = " + reserva);
			
			if (valor < reserva) {
				getMboValue().setValue(reserva);
				System.out.println("########## valor menor que reserva----------");
				throw new MXApplicationException("medalmox", "quantidademenorquereservanecessaria");
			} else {
				System.out.println("########## VALOR MAIOR QUE RESERVA++++++++++");
				getMboValue().setValue(valor);
			}
		}
		

	}

}
