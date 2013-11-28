package br.inf.id2.me.bean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Patrick
 */
public class AdicionaKitsDeMateriais extends AppBean {

	public AdicionaKitsDeMateriais() {
		super();
		System.out.println("*** AdicionaKitsDeMateriais ***");
	}

	public void adicionar() throws Exception {

//		System.out.println("*** antes origem");
		DataBean origem = app.getDataBean("existing_mrlines");
		MboSetRemote registroSelec = app.getDataBean("existing_mrlines").getMboSet();
//		System.out.println("*** origem.count " + origem.count());

		MboSetRemote destino = app.getDataBean("MAINRECORD").getMbo().getMboSet("RL02MRLINE");

		boolean existe;

//		System.out.println("*** getCurrentPosition "+ registroSelec.getCurrentPosition());
		existe = false;
		for (int j = 0; j < destino.count(); j++) {
//			System.out.println("*** for1");
			if (destino.getMbo(j).getString("ITEMNUM").equalsIgnoreCase(origem.getMboSet().getMbo(registroSelec.getCurrentPosition()).getMboSet("MXRL01MXTBENTMATDETPCL").getMbo(j).getString("ITEMNUM"))) {
				existe = true;
//				System.out.println("*** if existe");
				break;
			}
		}
		if (!existe) {
//			System.out.println("*** isSelected / !existe");

			for(int i = 0; i<origem.getMbo().getMboSet("MXRL01MXTBENTMATDETPCL").count();i++){
//				System.out.println("*** for2 "+i);
				MboRemote mboDestino = (MboRemote) destino.add();
	
//				System.out.println("*** ORDERQTY "+ origem.getMboSet().getMbo(registroSelec.getCurrentPosition()).getMboSet("MXRL01MXTBENTMATDETPCL").getMbo(i).getString("ORDERQTY"));
				mboDestino.setValue("QTY", origem.getMboSet().getMbo(registroSelec.getCurrentPosition()).getMboSet("MXRL01MXTBENTMATDETPCL").getMbo(i).getString("ORDERQTY"));
//				System.out.println("*** ITEMNUM "+origem.getMboSet().getMbo(registroSelec.getCurrentPosition()).getMboSet("MXRL01MXTBENTMATDETPCL").getMbo(i).getString("ITEMNUM"));
				mboDestino.setValue("ITEMNUM", origem.getMboSet().getMbo(registroSelec.getCurrentPosition()).getMboSet("MXRL01MXTBENTMATDETPCL").getMbo(i).getString("ITEMNUM"));
//				System.out.println("*** DESCRIPTION "+origem.getMboSet().getMbo(registroSelec.getCurrentPosition()).getMboSet("MXRL01MXTBENTMATDETPCL").getMbo(i).getMboSet("MXRL01ITEM").getMbo(0).getString("DESCRIPTION"));
				mboDestino.setValue("DESCRIPTION", origem.getMboSet().getMbo(registroSelec.getCurrentPosition()).getMboSet("MXRL01MXTBENTMATDETPCL").getMbo(i).getMboSet("MXRL01ITEM").getMbo(0).getString("DESCRIPTION"));
				//System.out.println("*** ORDERUNIT "+ origem.getMboSet().getMbo(registroSelec.getCurrentPosition()).getMboSet("MXRL01MXTBENTMATDETPCL").getMbo(i).getString("ORDERUNIT"));
				//mboDestino.setValue("ORDERUNIT", origem.getMboSet().getMbo(registroSelec.getCurrentPosition()).getMboSet("MXRL01MXTBENTMATDETPCL").getMbo(i).getString("ORDERUNIT"));
//				System.out.println("*** STORELOC "+ app.getDataBean("MAINRECORD").getMbo().getString("STORELOC"));
				mboDestino.setValue("STORELOC", app.getDataBean("MAINRECORD").getMbo().getString("STORELOC"));
//				System.out.println("*** MRNUM "+app.getDataBean("MAINRECORD").getMbo().getString("MRNUM"));
				mboDestino.setValue("MRNUM", app.getDataBean("MAINRECORD").getMbo().getString("MRNUM"));
			}
		}

		sessionContext.queueRefreshEvent();
		Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
		Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(),null, sessionContext));
		Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
	}
}
