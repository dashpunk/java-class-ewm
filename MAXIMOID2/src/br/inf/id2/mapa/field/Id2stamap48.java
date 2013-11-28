package br.inf.id2.mapa.field;

import java.rmi.RemoteException;

import br.inf.id2.mapa.bean.GtaEmissaoAppBean;

import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class Id2stamap48 extends MboValueAdapter{
	 
	public Id2stamap48(MboValue mbv) {
	        super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		
		
		String entidade = getMboValue().getMbo().getName();
		String relacionamento = "";
		String campoStatus = "";
		String campoCondenacao = "";
		String grupoMensagem = "";
		
		if (entidade.equalsIgnoreCase("MAVWMAPA01")) {
			relacionamento = "MARL01PROCON";
			grupoMensagem = "id2mapa01";
		} 
		else if (entidade.equalsIgnoreCase("MAVWMAPA02")) {
			relacionamento = "MARL01PROCON";
			grupoMensagem = "id2mapa02";
			
		}
		else if (entidade.equalsIgnoreCase("MAVWMAPA03")) {
			relacionamento = "MARL01PROCON";
			grupoMensagem = "id2mapa03";
			
		}
		else if (entidade.equalsIgnoreCase("MAVWMAPA04")) {
			relacionamento = "MARL01PROCON";
			campoStatus = "ID2STAMAP48";
			campoCondenacao = "ID2SEMCON48";
			grupoMensagem = "id2mapa04";
			
		}
		else if (entidade.equalsIgnoreCase("MAVWMAPA05")) {
			relacionamento = "MARL01PROCON";
			campoStatus = "ID2STAMAP48";
			campoCondenacao = "ID2SEMCON48";
			grupoMensagem = "id2mapa05";
			
		}
		else if (entidade.equalsIgnoreCase("MAVWMAPA06")) {
			relacionamento = "MARL01PROCON";
			campoStatus = "ID2STAMAP48";
			campoCondenacao = "ID2SEMCON48";
			grupoMensagem = "id2mapa06";
			
		}
		else if (entidade.equalsIgnoreCase("MAVWMAPA07")) {
			relacionamento = "MARL01PROCON";
			campoStatus = "ID2STAMAP48";
			campoCondenacao = "ID2SEMCON48";
			grupoMensagem = "id2mapa07";
			
		}
		else if (entidade.equalsIgnoreCase("MAVWMAPA08")) {
			relacionamento = "MARL01PROCON";
			campoStatus = "ID2STAMAP48";
			campoCondenacao = "ID2SEMCON48";
			grupoMensagem = "id2mapa08";
			
		}
		
		
		 MboSet mboMaProcon = (MboSet) getMboValue().getMbo().getMboSet(relacionamento);
	        if (getMboValue().getMbo().getBoolean(campoCondenacao) == false) {
	            if (mboMaProcon.count() <= 0 && getMboValue().getMbo().getString(campoStatus).substring(0, 4).equalsIgnoreCase("CONC")) {
	                throw new MXApplicationException(grupoMensagem, "condProdutoNull");
	            }
	        }

	        for (int i = 0; ((mboMaProcon.getMbo(i)) != null); i++) {
	            if (!getMboValue().getMbo().isNew() && mboMaProcon.getMbo(i).toBeDeleted()) {
	                mboMaProcon.getMbo().getThisMboSet().save();
	                if (mboMaProcon.count() <= 0 && getMboValue().getMbo().getString(campoStatus).substring(0, 4).equalsIgnoreCase("CONC")) {
	                    throw new MXApplicationException(grupoMensagem, "condProdutoNull");
	                }
	            }
	        }
	        
	        super.validate();
	}
	
	
	

}
