package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.app.person.FldPersonID;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldRequestedBy extends FldPersonID {

    public ID2FldRequestedBy(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        //Uteis.espera("***************-------------- initiValue");
        super.initValue();
        MboSet pessoa = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("PERSON", getMboValue().getMbo().getUserInfo());
        pessoa.setWhere("personid = '" + getMboValue().getString() + "'");
        pessoa.reset();
        if ((pessoa.getMbo(0) != null) && (getMboValue().getMbo().isNull("ID2SECRETARIA"))) {
            //Uteis.espera("*************** SAVE() Entrou total de linhas do relacionamento = " + pessoa.count());
            if (pessoa.getMbo(0).getString("MS_SIGSEC") != null) {
                getMboValue().getMbo().setValue("ID2SECRETARIA", pessoa.getMbo(0).getString("MS_SIGSEC"));
                //Uteis.espera("*************** SAVE() Entrou 1 " + pessoa.getMbo(0).getString("MS_SIGSEC"));
            }
            if (pessoa.getMbo(0).getString("MS_AREDEM") != null) {
                getMboValue().getMbo().setValue("ID2COORDENACAO", pessoa.getMbo(0).getString("MS_AREDEM"));
                //Uteis.espera("*************** SAVE() Entrou 2 " + pessoa.getMbo(0).getString("MS_AREDEM"));
            }

        }
    }
}
