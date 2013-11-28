package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.Set;
import java.util.TreeSet;

import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Willians Andrade 
 *
 */
// br.inf.id2.mapa.field.id2mapa01Status
public class id2mapa01Status extends MboValueAdapter {

    public id2mapa01Status(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        MboSet matbLotaba;

        matbLotaba = (MboSet) getMboValue().getMbo().getMboSet("MARL01LOTABA");

        if (getMboValue().getString().equals("CONCLUÍDO") && !getMboValue("ID2SEMABA").getBoolean()) {

            if (getMboValue().getMbo().getMboSet("MARL01LOTABA").count() == 0) {

                throw new MXApplicationException("madataba", "lotesDeAbateVazio");
            }

            Set setExiste = new TreeSet();

            for (int i = 0; ((matbLotaba.getMbo(i)) != null); i++) {
                if (setExiste.contains(matbLotaba.getMbo(i).getString("MANUMLOT") + "#" + matbLotaba.getMbo(i).getString("MATIPLOT"))) {
                    throw new MXApplicationException("mtlotaba", "ManumlotJaExiste");
                } else {
                    setExiste.add(matbLotaba.getMbo(i).getString("MANUMLOT") + "#" + matbLotaba.getMbo(i).getString("MATIPLOT"));
                }

                if (matbLotaba.getMbo(i).getString("MANUMLOT").equals("")) {
                    throw new MXApplicationException("mtlotaba", "manumlotIsNull");
                }

                if (matbLotaba.getMbo(i).getInt("MAQTDFEMMOR") == 0 && matbLotaba.getMbo(i).getInt("MAQTDFEM") > 0) {
                    throw new MXApplicationException("mtlotaba", "femMorIsNull");
                }

                if (matbLotaba.getMbo(i).getInt("MAQTDMACMOR") == 0 && matbLotaba.getMbo(i).getInt("MAQTDMAC") > 0) {
                    throw new MXApplicationException("mtlotaba", "macMorIsNull");
                }

                if (matbLotaba.getMbo(i).getInt("MAQTDFEM") == 0 && matbLotaba.getMbo(i).getInt("MAQTDMAC") == 0) {
                    throw new MXApplicationException("mtlotaba", "femeaIsNullOuMachoIsNull");
                }
            }
        }

    }
}
