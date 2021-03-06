/**
 * CHECK-LIST /  SVS
 * Classe que carrega todo a lista de lista para o SVS
 * br.inf.ctis.ms.bean.MsCadCron
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author willians.andrade
 *
 */
public class MsSvsCheckList extends psdi.webclient.system.beans.AppBean {
	
	public MsSvsCheckList() {
		System.out.print("||||||||||||||||||   SVS ######## --- MsSvsCheckLists");
	}
	
	@Override
	public void save() throws MXException {
		
			
		try {
			
			if(getMbo().getMboSet("MSTBINSUMOS").isEmpty()){
				
				MboRemote mbo;
				MboRemote mboDestino = null;
				
				for (int i = 0; ((mbo= getMbo().getMboSet("POLINE").getMbo(i)) !=null); i++) {
					mboDestino = getMbo().getMboSet("MSTBINSUMOS").add();
					System.out.println("############ add() na MSTBINSUMOS");
					
					mboDestino.setValue("MSTBCADDEMSVSID", getMbo().getInt("MSTBCADDEMSVSID"), 2L);
					mboDestino.setValue("polineid", mbo.getInt("polineid"), 2L);
					mboDestino.setValue("ponum", mbo.getString("ponum"), 2L);
					mboDestino.setValue("ID2ITEMNUM", mbo.getString("ID2ITEMNUM"), 2L);
					mboDestino.setValue("DESCRIPTION", mbo.getString("DESCRIPTION"), 2L);
					mboDestino.setValue("MSNUMQNT", mbo.getDouble("orderqty"), 2L);
				}
			}
			
			String PersonID = sessionContext.getUserInfo().getPersonId();
			System.out.print("CTIS # --- Capturado PERSONID " + PersonID);
			
			if (getMbo().isNull("MSRESPCRON")){
				System.out.print("CTIS # --- Setado valor do PERSONID");
				getMbo().setValue("MSRESPCRON", PersonID, MboConstants.NOACCESSCHECK);
			}
			
			System.out.print("||||||||||||||||||   SVS ######## --- MsSvsCheckLists - Entrou no Metodo SAVE()");
			
			if (getMbo().getMboSet("MSTBSVSCHECK").isEmpty()) {
				
				System.out.print("||||||||||||||||||   SVS ######## --- MsSvsCheckLists - Tabela está vazia");
				
				MboSet mboCheckListALN = (MboSet) getMbo().getMboSet("MSCHECKLIST");
				int iTamanhoCHK = mboCheckListALN.count();
				
				for (int i = 0; i < iTamanhoCHK; i++) {
					
					MboRemote mboDestino = null;
					mboDestino = getMbo().getMboSet("MSTBSVSCHECK").add();
					
					mboDestino.add();
					mboDestino.setValue("MSTBCADDEMSVSID", getMbo().getInt("MSTBCADDEMSVSID"));
					mboDestino.setValue("MSIDCHECK", mboCheckListALN.getMbo(i).getString("VALUE"));
					mboDestino.setValue("DESCRIPTION", mboCheckListALN.getMbo(i).getString("DESCRIPTION"));
				}
			}
			
			super.save();
			
			System.out.print("||||||||||||||||||   SVS ######## --- MsSvsCheckLists - SALVOU()");
			
			reloadTable();
			refreshTable();
			
		} catch (RemoteException ex) {
			Logger.getLogger(MsAlmox.class.getName()).log(Level.SEVERE, null, ex);
		}
	}	
}
