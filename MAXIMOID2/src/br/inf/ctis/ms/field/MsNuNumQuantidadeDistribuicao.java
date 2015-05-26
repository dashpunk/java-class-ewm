package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MsNuNumQuantidadeDistribuicao extends MboValueAdapter{
	
	public MsNuNumQuantidadeDistribuicao(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
		
		double parcelaVolume = 0d;
		double parcelaPeso = 0d;
		Double valor = 0d;
		MboRemote mbo;
		
		for (int i = 0; ((mbo = getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("MSTBPREVISAODISTRIBUICAO").getMbo(i)) != null); i++) {
			
			System.out.println("########## Data: " + mbo.getString("MSALDTADISTRIBUICAO") + " ########## Quantidade: " + mbo.getDouble("MSNUNUMQUANTIDADE"));
			valor += mbo.getDouble("MSNUNUMQUANTIDADE");
			
			System.out.println("########## valor: " + valor);
		}

		valor += getMboValue().getDouble();
		
		System.out.println("########## valor+qtd: " + valor);

        if (valor > getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) {
            throw new MXApplicationException("distribuicao", "QuantidadeExcedida");
        }
        
        parcelaVolume = (((getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMCUBAGEMVOLUME") * getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMQTDEMBALAGENS")) 
				/ getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) * getMboValue().getDouble());
		System.out.println("########## parcelaVolumeEntrega = " + parcelaVolume);
		
		parcelaPeso = (((getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMEMBALAGEMPESO") * getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMQTDEMBALAGENS")) 
						/ getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) * getMboValue().getDouble());		
		System.out.println("########## parcelaPesoEntrega = " + parcelaPeso);
		
		getMboValue().getMbo().setValue("MSNUNUMVOLUME", parcelaVolume);
		getMboValue().getMbo().setValue("MSNUNUMPESO", parcelaPeso);
        
	}

}
