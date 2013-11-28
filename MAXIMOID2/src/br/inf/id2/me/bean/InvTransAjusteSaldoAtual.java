package br.inf.id2.me.bean;

import psdi.webclient.system.beans.DataBean;

/**
 *
 * @author Ricardo S Gomes
 */
public class InvTransAjusteSaldoAtual extends psdi.webclient.beans.inventory.AdjustCurBalanceBean {

    String justificativa = "";
    String itemNum = "";

    public InvTransAjusteSaldoAtual() {
        super();
    }

    @Override
    public synchronized void fireDataChangedEvent(DataBean speaker) {
        super.fireDataChangedEvent(speaker);
        /*try {

        System.out.println("--- speaker " + speaker.getUniqueIdName());
        MboRemote invbalances = app.getDataBean().getMboSet().getMbo().getMboSet("INVBALANCES").getMbo(app.getDataBean().getMboSet().getMbo().getMboSet("INVBALANCES").getCurrentPosition());
        MboRemote invtrans = app.getDataBean().getMboSet().getMbo().getMboSet("INVTRANS").getMbo(app.getDataBean().getMboSet().getMbo().getMboSet("INVTRANS").getCurrentPosition());
        justificativa = invbalances.getString("MEJUS");
        itemNum = invbalances.getString("ITEMNUM");

        //MboRemote invtrans = app.getDataBean().getMboSet().getMbo().getMboSet("INVBALANCES").getMbo(app.getDataBean().getMboSet().getMbo().getMboSet("INVBALANCES").getCurrentPosition()).getMboSet("INVTRANS").getMbo(app.getDataBean().getMboSet().getMbo().getMboSet("INVBALANCES").getMbo(app.getDataBean().getMboSet().getMbo().getMboSet("INVBALANCES").getCurrentPosition()).getMboSet("INVTRANS").getCurrentPosition());
        System.out.println("--- valor de MEJUS " + invbalances.getString("MEJUS"));
        //invtrans.setValue("MEJUS", invbalances.getString("MEJUS"));
        System.out.println("--- mboUniq " + getMbo().getUniqueIDName());
        System.out.println("--- invtrans count " + getMbo().getMboSet("INVTRANS").count());
        //System.out.println("--- invtrans current " + getMbo().getMboSet("INVTRANS").getCurrentPosition());
        //getMbo().getMboSet("INVTRANS").getMbo().setValue("MEJUS", invbalances.getString("MEJUS"));
        //System.out.println("--- invtrans va " + getMbo().getMboSet("INVTRANS").getMbo(0).getDate("TRANSDATE"));
        //System.out.println("--- invtrans vb "+getMbo().getMboSet("INVTRANS").getMbo(33).getDate("TRANSDATE"));

        } catch (MXException ex) {
        Logger.getLogger(InvTransAjusteSaldoAtual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
        Logger.getLogger(InvTransAjusteSaldoAtual.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
