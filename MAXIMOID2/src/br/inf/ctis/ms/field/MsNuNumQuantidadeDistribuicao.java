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
		double saldoDistribuicao = 0d;
		double pesoTotal = 0d;
		double volumeTotal = 0d;
		Double valor = 0d;
		int contador = 0;
		MboRemote mbo;
		
		parcelaVolume = (((getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMCUBAGEMVOLUME") * getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMQTDEMBALAGENS")) 
				/ getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) * getMboValue().getDouble());
		System.out.println("########## parcelaVolumeEntrega = " + parcelaVolume);
		
		parcelaPeso = (((getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMEMBALAGEMPESO") * getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMQTDEMBALAGENS")) 
						/ getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) * getMboValue().getDouble());		
		System.out.println("########## parcelaPesoEntrega = " + parcelaPeso);
		
		for (int i = 0; ((mbo = getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getMboSet("MSTBPREVISAODISTRIBUICAO").getMbo(i)) != null); i++) {
			
			System.out.println("########## Data: " + mbo.getString("MSALDTADISTRIBUICAO") + " ########## Quantidade: " + mbo.getDouble("MSNUNUMQUANTIDADE"));
			if(getMboValue("MSTBPREVISAODISTRIBUICAOID").getInt() != mbo.getInt("MSTBPREVISAODISTRIBUICAOID")){
				valor += mbo.getDouble("MSNUNUMQUANTIDADE");
				pesoTotal += mbo.getDouble("MSNUNUMPESO");
				volumeTotal += mbo.getDouble("MSNUNUMVOLUME");
				contador++;
			}
			
			System.out.println("########## valor: " + valor);
		}

		valor += getMboValue().getDouble();
		pesoTotal += parcelaPeso;
		volumeTotal += parcelaVolume;
		contador++;
		
		System.out.println("########## valor+qtd: " + valor);
		System.out.println("########## contador: " + contador);

        if (valor > getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) {
            throw new MXApplicationException("distribuicao", "QuantidadeExcedida");
        }
        
        saldoDistribuicao = getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY") - valor;
        
		getMboValue().getMbo().setValue("MSNUNUMVOLUME", parcelaVolume);
		getMboValue().getMbo().setValue("MSNUNUMPESO", parcelaPeso);
		getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).setValue("MSNUNUMPARCELASDISTRIBUICAO", contador);
		getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).setValue("MSNUNUMSALDODISTRIBUICAO", saldoDistribuicao);
		getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).setValue("MSNUNUMPESOTOTALDISTRIBUICAO", pesoTotal);
		getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).setValue("MSNUNUMVOLUMETOTALDISTRIBUICAO", volumeTotal);
        
	}

}
