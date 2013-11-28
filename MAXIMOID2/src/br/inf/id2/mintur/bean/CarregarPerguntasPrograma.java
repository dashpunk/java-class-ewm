/**
 * @author Patrick
 */
package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import br.inf.id2.common.util.Validar;

public class CarregarPerguntasPrograma extends AppBean {

	public CarregarPerguntasPrograma() {
	}

	public synchronized void listenerChangedEvent(DataBean speaker) {
		System.out.println("*** CarregarPerguntasPrograma ***");

		try {

			if (speaker.getUniqueIdName().equals("TBAVALRESPID")) {

				boolean andicionou = false;
				MboSetRemote rl01aval = getMbo().getMboSet("RL01AVALRESP").getMbo(0).getMboSet("RL01AVAL");
//				System.out.println("*** Tamanho(rl01aval) "+ Validar.getMboSetTamanho(rl01aval));
				if (Validar.getMboSetTamanho(rl01aval) > 0) {
					MboRemote mbo;
					int cont = 0;
					for (int i = 0; ((mbo = rl01aval.getMbo(i)) != null); i++) {
						andicionou = true;
						cont++;
						MboRemote memo = (MboRemote) getMbo().getMboSet("RL01AVALRESP").add();

//						System.out.println("*** DESCRIPTION - " + i + " - "+ rl01aval.getMbo(i).getString("DESCRIPTION"));
						memo.setValue("DESCRIPTION", rl01aval.getMbo(i).getString("DESCRIPTION"));

//						System.out.println("*** DSNUMPRO="+ getMbo().getString("DSNUMPRO"));
						memo.setValue("DSNUMPRO", getMbo().getString("DSNUMPRO"));

//						System.out.println("*** ITPROID="+ getMbo().getString("UPCODPROG"));
						memo.setValue("ITPROID", getMbo().getString("UPCODPROG"));

						refreshTable();
						reloadTable();
						getMbo().getMboSet("RL01AVALRESP").select(getMbo().getMboSet("RL01AVALRESP").count() - 1);
					}
//					System.out.println("*** if do cont. cont= " + cont);

					if (andicionou) {
						MboRemote mbo1;
						for (int i = 0; ((mbo1 = getMbo().getMboSet("RL01AVALRESP").getMbo(i)) != null); i++) {
//							System.out.println("*** DESCRIPTION delete "+ mbo1.getString("DESCRIPTION"));
							if (mbo1.isNull("DESCRIPTION")) {
//								System.out.println("*** if do delete ***");
								getMbo().getMboSet("RL01AVALRESP").getMbo(i).delete();
							}
						}
						getMbo().getMboSet("RL01AVALRESP").save();
					}
				}
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}

	}

}
