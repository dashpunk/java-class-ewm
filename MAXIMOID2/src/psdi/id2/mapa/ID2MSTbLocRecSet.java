package psdi.id2.mapa;

import java.lang.Long;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;
import psdi.id2.Uteis;
import psdi.id2.Executa;


// Referenced classes of package psdi.app.workorder:
//            WOSet, LocationCustom, LocationCustomSetRemote
//psdi.id2.mapa.ID2LocationCustomSet
//psdi.tloam.app.location.TloamLocationSet
//ANTES ESTAVA EXTENDENDO DE LocationSet
public class ID2MSTbLocRecSet extends CustomMboSet
        implements ID2MSTbLocRecSetRemote {

    public ID2MSTbLocRecSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
    }

    @Override
    public void remove() throws MXException, RemoteException {
        Uteis.espera("***************** remove() ID2MSTbLocRecSet");
        atribuiSubtotais();
        super.remove();
    }

    private void atribuiSubtotais() throws MXException, RemoteException {
        Uteis.espera("***************** save() em ID2MSTbLocRecSet");
        Long qtdEntrega = getMboValueData("MS_QTDENT").getDataAsLong();
        Uteis.espera("***************** save() qtdEntrada = " + qtdEntrega);
        Long somaQtdDis = Executa.somaValor(getMbo().getMboSet("ID2RELDETREC"), "MS_QTDDIS");
        Uteis.espera("***************** save() somaQtdDis = " + somaQtdDis);
        getMbo().setValue("MS_SUBTOTAL", qtdEntrega - somaQtdDis);
    }

    public void validate() throws MXException, RemoteException {
        atribuiSubtotais();
        super.validate();
        Uteis.espera("***************** save() FIM");
    }
}
