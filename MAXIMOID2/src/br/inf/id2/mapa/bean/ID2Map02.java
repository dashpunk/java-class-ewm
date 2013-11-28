package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;

/**
 * 
 * @author Leysson Barbosa Moreira
 * 
 */
public class ID2Map02 extends AppBean {

	public ID2Map02() {

	}
	
	
	@Override
 	public  void save() throws MXException {

	try {
		if(getMbo() != null) {
			validarSalvar();
		}
	} catch (RemoteException e) {
		e.printStackTrace();
	}
 		super.save();
 	}

	
	private void validarSalvar() throws MXException, RemoteException {
		MboRemote mboa = null;
		MboRemote mbob = null;
		MboRemote mboc = null;
		MboSet mboSetMadataba;
		mboSetMadataba = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC06", sessionContext.getUserInfo());
		Integer locationID = getMbo().getInt("LOCATIONSID");
		
		
		  // Não permite a inserção de registros, sendo que para a data especificada e a especie o mapa de abate não foi concluido, correção ou fechado.
        
		if(getMbo().getString("MAPSTATUS").equals("02") || getMbo().getString("MAPSTATUS").equals("04")) {
		
				if (getMbo().getDate("MADATABA") != null) {
		        	SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yy");
		            String data = formatador.format(getMbo().getDate("MADATABA"));
		        	mboSetMadataba.setWhere("MAPSTATUS NOT IN ('04','02') AND to_char(MADATABA) = '" + data + "' AND COMMODITY = '" + getMbo().getString("COMMODITY") + "\'" + " AND appname = \'" + "ID2MAP01" + "\' AND MANUMREG = \'"+getMbo().getString("MANUMREG")+"\'");        	
		        	mboSetMadataba.reset();
		            System.out.println("Total: " + mboSetMadataba.count());
	           
		            if (mboSetMadataba.count() > 0) {
		                    throw new MXApplicationException("madataba", "mapaDeAbateNConcluido");
		            }
			   }
        }
		
		
		//Início da verificação de Diagnósticos e Partes
		MboSet matbLotaba = (MboSet) getMbo().getMboSet("MATBLOTABA02");
		Mbo mboLotAba;
				
		for (int i = 0; ((matbLotaba.getMbo(i)) != null); i++) {
				mboLotAba = (Mbo) matbLotaba.getMbo(i);
				System.out.println("mboLotAba: " + mboLotAba.getString("MANUMLOT"));
					
				MboSet diagnosticos = (MboSet) mboLotAba.getMboSet("MATBDIADON");
					
				for (int j = 0; ((diagnosticos.getMbo(j)) != null); j++) {
					Mbo mboDiag = (Mbo) diagnosticos.getMbo(j);
					
					MboSet partes = (MboSet) mboDiag.getMboSet("MATBPARDON");
						
					if(diagnosticos.getMbo(j).toBeDeleted()) {
							super.save();
					}
					
					if (partes == null || partes.count() == 0) {
						throw new MXApplicationException("ID2Map02", "naoExistePartesAcometidos");
					}
					
					for(int y = 0; ((partes.getMbo(y)) != null); y++) {
						if (partes.getMbo(0).toBeDeleted() && partes.getCurrentPosition() == y) {
								throw new MXApplicationException("ID2Map02", "naoExistePartesAcometidos");
					    }
				    }
			}
		}
	
        // Não permite a inserção de registros com a mesma data e espécie
        if (getMbo().getDate("MADATABA") != null) {
            mboSetMadataba.setWhere("LOCATIONSID <> " + locationID + " AND COMMODITY = \'" + getMbo().getString("COMMODITY") + "\'" + " AND appname = \'" + appName.toUpperCase() + "\' AND MANUMREG = \'"+getMbo().getString("MANUMREG")+"\'");
            mboSetMadataba.reset();
            
            MboRemote mbo;
            for (int n = 0; ((mbo = mboSetMadataba.getMbo(n)) != null); n++) {
                if (getMbo().getDate("MADATABA").equals(mbo.getDate("MADATABA"))) {
                    throw new MXApplicationException("madataba", "existemRegistrosEmDuplicidade");
                }
            }
        }
        
		
        //Verifica se diagnósticos e partes sofreu alguma alteração se sim seta no campo: ID2DATCHANGE data e a hora atual.
		for (int i = 0; ((mboa = getMbo().getMboSet("MATBLOTABA02").getMbo(i)) != null); i++) {				
			
			for (int j = 0; ((mbob = mboa.getMboSet("MATBDIADON").getMbo(j)) != null); j++) {
				
				if(mbob.isNew() || mbob.getInitialValue("NUMANI").asInt() != -1 || mbob.getInitialValue("DESCRIPTION").asString().equals(mbob.getString("DESCRIPTION"))) {
					getMbo().setValue("ID2DATCHANGE", new Date());
				}
				
				if (mbob.getInt("NUMANI") > mboa.getInt("MAQTDTOTAL")) {
					mbob.setValueNull("NUMANI");
					throw new MXApplicationException("ID2Map02",
							"ValorMaiorQueTotal");
				}
			
				for (int n = 0; ((mboc = mboa.getMboSet("MATBPARDON").getMbo(n)) != null); n++) {
					  if(mboc.isNew() || mboc.getInitialValue("NUMPART").asInt() != -1 || mboc.getInitialValue("DESCRIPTION").asString().equals(mboc.getString("DESCRIPTION"))) {
							getMbo().setValue("ID2DATCHANGE", new Date());
					  }	
			     }
			}
		}
		super.save();
	}
	
	
	@Override
	public int toggledeleterow() throws MXException {
		// TODO Auto-generated method stub
		return super.toggledeleterow();
	}
	
	
	
	
	@Override
	public synchronized void fireDataChangedEvent(DataBean arg0) {
		super.fireDataChangedEvent(arg0);
		try {
			System.out.println("fireDataChangedEvent(DataBean arg0) "+arg0.getUniqueIdName());
			if (arg0.getUniqueIdName().equalsIgnoreCase("LOCATIONSID")) {
				reload();
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public synchronized void fireChildChangedEvent() {
		super.fireChildChangedEvent();
		reload();
	}

	private void reload() {
		reloadTable();
	}	
}
