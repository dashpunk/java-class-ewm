package br.inf.id2.ms.mbo;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Date;
import psdi.mbo.*;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class PlusTPOLineSet extends psdi.plust.app.po.PlusTPOLineSet
        implements PlusTPOLineSetRemote {

    public PlusTPOLineSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {

        super(mboserverinterface);
    }

    @Override
    public void save() throws MXException, RemoteException {
        System.out.println("----- save v6");

        super.save();
        atualizaSaldo(true);

    }

    @Override
    public void saveMbos() throws MXException, RemoteException {
        System.out.println("----- saveMbos v6");

        super.saveMbos();
        atualizaSaldo(true);

    }

    public void atualizaSaldo(boolean save) throws MXException, RemoteException {
        System.out.println("......PlusTPOLineSet count " + count());
        MboRemote mbo;

        for (int j = 0; ((mbo = getMbo(j)) != null); j++) {

            MboSetRemote polineSet;
            polineSet = psdi.server.MXServer.getMXServer().getMboSet("POLINE", getUserInfo());
            polineSet.setWhere("PRLINEID = " + mbo.getInt("PRLINEID") + " AND POLINEID <> " + mbo.getInt("POLINEID"));
            polineSet.reset();

            System.out.println("PO after reset () polineid =  " + mbo.getInt("POLINEID"));
            System.out.println("PO after reset () count = " + polineSet.count());

            double valor = polineSet.sum("ORDERQTY");

            if (!mbo.toBeDeleted()) {
                valor += mbo.getDouble("ORDERQTY");
            }

            System.out.println("PO valor = " + valor);


            MboSetRemote prlineSet = psdi.server.MXServer.getMXServer().getMboSet("PRLINE", getUserInfo());
            prlineSet.setWhere("PRLINEID = " + mbo.getInt("PRLINEID"));
            prlineSet.reset();
            System.out.println("PO prline count " + prlineSet.count());
            if (prlineSet.count() > 0) {
                System.out.println("bef setValue");
                prlineSet.getMbo(0).setValue("ID2SALDO", prlineSet.getMbo(0).getDouble("ORDERQTY") - valor, MboConstants.NOACCESSCHECK);
                System.out.println("............ valor ref " + (prlineSet.getMbo(0).getDouble("ORDERQTY") - valor));
                if (prlineSet.getMbo(0).getDouble("ORDERQTY") - valor > 0) {
                    prlineSet.getMbo(0).setValueNull("PONUM", MboConstants.NOACCESSCHECK);
                    prlineSet.getMbo(0).setValueNull("POLINENUM", MboConstants.NOACCESSCHECK);
                    prlineSet.getMbo(0).setValueNull("POLINEID", MboConstants.NOACCESSCHECK);
                    prlineSet.getMbo(0).setValue("ID2STATUS", "ENVIADO", MboConstants.NOACCESSCHECK);
                } else if (prlineSet.getMbo(0).getDouble("ORDERQTY") - valor == 0) {
                    System.out.println("........ atribuindo valores ");
                    prlineSet.getMbo(0).setValue("PONUM", mbo.getString("PONUM"), MboConstants.NOACCESSCHECK);
                    prlineSet.getMbo(0).setValue("POLINENUM", mbo.getDouble("POLINENUM"), MboConstants.NOACCESSCHECK);
                    prlineSet.getMbo(0).setValue("POLINEID", mbo.getDouble("POLINEID"), MboConstants.NOACCESSCHECK);
                    prlineSet.getMbo(0).setValue("ID2STATUS", "TR", MboConstants.NOACCESSCHECK);
                    System.out.println("........ valores atribuidos");
                } else {
                    mbo.setValue("ORDERQTY", 1, MboConstants.NOACCESSCHECK);
                    throw new MXApplicationException("poline", "prlineId2SaldoNegativo");
                }
                System.out.println("aft save");
                prlineSet.save(MboConstants.NOACCESSCHECK);

                verificacaoParaMudancaDeStatus(prlineSet.getMbo(0).getString("PRNUM"));

            }


        }
    }

    protected Mbo getMboInstance(MboSet mboset)
            throws MXException, RemoteException {
        return new br.inf.id2.ms.mbo.PlusTPOLine(mboset);
    }

    private void verificacaoParaMudancaDeStatus(String prNum) throws MXException, RemoteException {
        System.out.println("--- verificacaoParaMudancaDeStatus inicio");
        MboSetRemote prLineSet = psdi.server.MXServer.getMXServer().getMboSet("PRLINE", getUserInfo());
        prLineSet.setWhere("PRNUM = '" + prNum + "'");
        prLineSet.reset();

        MboRemote mbo;

        boolean todosZerados = true;

        System.out.println("--- verificacaoParaMudancaDeStatus count a " + prLineSet.count());
        for (int j = 0; ((mbo = prLineSet.getMbo(j)) != null); j++) {
            if (mbo.getDouble("ID2SALDO") > 0) {
                System.out.println("ACHOU ZERADO");
                todosZerados = false;
                break;
            }
        }

        System.out.println("--- todosZerados " + todosZerados);

        MboSetRemote prSet = psdi.server.MXServer.getMXServer().getMboSet("PR", getUserInfo());
        prSet.setWhere("PRNUM = '" + prNum + "'");
        prSet.reset();

        String status = (todosZerados ? "PLANEJADO" : "ENVIADO");

        System.out.println("---- status = " + status);

        if (prSet.count() > 0) {
            System.out.println("--- vals ");
            System.out.println(status);
            System.out.println(prSet.getMbo(0).getString("STATUS"));
            System.out.println("--- vals FIM ");
            if (!status.equalsIgnoreCase(prSet.getMbo(0).getString("STATUS"))) {
                prSet.getMbo(0).setValue("STATUS", status, MboConstants.NOACCESSCHECK);
                prSet.save();
                System.out.println("---apos save 1");
                MboSetRemote prStatusSet = psdi.server.MXServer.getMXServer().getMboSet("PRSTATUS", getUserInfo());
                prStatusSet.setWhere("PRNUM = '" + prNum + "'");

                prStatusSet.setOwner(prSet.getMbo(0));
                
                mbo = prStatusSet.add();

                System.out.println("---apos add X");
                mbo.setValue("PRNUM", prNum, MboConstants.NOACCESSCHECK);
                mbo.setValue("CHANGEDATE", new Date(), MboConstants.NOACCESSCHECK);
                mbo.setValue("STATUS", status, MboConstants.NOACCESSCHECK);
                mbo.setValue("CHANGEBY", getUserName(), MboConstants.NOACCESSCHECK);
                mbo.setValue("SITEID", "CGGPL", MboConstants.NOACCESSCHECK);
                mbo.setValue("ORGID", "MS", MboConstants.NOACCESSCHECK);
                System.out.println("---antes save 2");
                prStatusSet.save();
                System.out.println("---apos save 2");
            }

        }
    }
}
