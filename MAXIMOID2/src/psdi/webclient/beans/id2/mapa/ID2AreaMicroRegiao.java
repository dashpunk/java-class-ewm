package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.id2.mapa.*;
import psdi.mbo.*;
import psdi.util.MXException;
import psdi.webclient.beans.location.LocationAppBean;
import psdi.server.MXServer;

public class ID2AreaMicroRegiao extends LocationAppBean {

    public ID2AreaMicroRegiao()
            throws MXException {
    }

    @Override
    public int SAVE() throws MXException, RemoteException {

        MboSet contatoMboSet = (MboSet) MXServer.getMXServer().getMboSet("MATBCON", getMbo().getUserInfo());

        int ret = -1;

        MboSet estabelecimentos;

        for (int p = 0; p < getMbo().getMboSet("ID2AREASLOC").count(); p++) {


            estabelecimentos = (MboSet) getMbo().getMboSet("ID2AREASLOC").getMbo(p).getMboSet("MAAREAPROP");

            if (estabelecimentos != null) {
                boolean existe = false;
                for (int i = 0; i < estabelecimentos.count(); i++) {


                    Mbo estabelecimento = (Mbo) estabelecimentos.getMbo(i);

                    existe = false;

                    for (int j = 0; j < getMbo().getMboSet("MATBCON").count(); j++) {


                        if (getMbo().getMboSet("MATBCON").getMbo(j).getString("MAESTRURID").equals(estabelecimento.getString("LOCATION")) && getMbo().getMboSet("MATBCON").getMbo(j).getString("MAMICREGID").equals(getMbo().getString("LOCATION"))) {
                            existe = true;
                            break;
                        }

                    }


                    if (!existe) {
                        MboRemote contato = contatoMboSet.add();



                        contato.setValue("MAESTRURID", estabelecimento.getString("LOCATION"));
                        contato.setValue("MAMICREGID", getMbo().getString("LOCATION"));


                        contatoMboSet.save();
                        //getMbo().getMboSet("MATBCON").save();

                    }

                }

            }
        }
        contatoMboSet.save();
        return super.SAVE();
    }
}
