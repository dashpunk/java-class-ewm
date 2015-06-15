package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;


public class MsNuNumQuantidadeEntrega extends MboValueAdapter{
	
	public MsNuNumQuantidadeEntrega(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
	
		double parcelaVolume = 0d;
		double parcelaPeso = 0d;
		Double valor = 0d;
		MboRemote mbo;
		
		parcelaVolume = (((getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMCUBAGEMVOLUME") * getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMQTDEMBALAGENS"))
				/ getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) * getMboValue().getDouble());
		System.out.println("########## parcelaVolumeEntrega = " + parcelaVolume);
		
		parcelaPeso = (((getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMEMBALAGEMPESO") * getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMQTDEMBALAGENS")) 
						/ getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) * getMboValue().getDouble());		
		System.out.println("########## parcelaPesoEntrega = " + parcelaPeso);
					
		for (int i = 0; ((mbo = getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("MSTBPREVISAOENTREGA").getMbo(i)) != null); i++) {
			
			System.out.println("########## Data: " + mbo.getString("MSALDTAENTREGA") + " ########## Quantidade: " + mbo.getDouble("MSNUNUMQUANTIDADE"));
			if(getMboValue("MSTBPREVISAOENTREGAID").getInt() != mbo.getInt("MSTBPREVISAOENTREGAID")){
				valor += mbo.getDouble("MSNUNUMQUANTIDADE");
			}
			
			
			System.out.println("########## valor: " + valor);
		}
		
		valor += getMboValue().getDouble();
		
		System.out.println("########## valor+qtd: " + valor);

        if (getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getString("ID2DISTDIRETA").equalsIgnoreCase("AMBOS")) {
        	if (valor >= getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) {
	            throw new MXApplicationException("ambos", "QuantidadeDeveSerMenor");
	        }
        } else {
	        if (valor > getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) {
	            throw new MXApplicationException("entrega", "QuantidadeExcedida");
	        } 
        }
        
        getMboValue().getMbo().setValue("MSNUNUMVOLUME", parcelaVolume, MboConstants.NOACCESSCHECK);
    	getMboValue().getMbo().setValue("MSNUNUMPESO", parcelaPeso, MboConstants.NOACCESSCHECK);
    	getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("MSTBPREVISAOENTREGA").save();
    	
	}

}
