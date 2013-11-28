package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.plust.app.pr.PlusTPRLine;
import psdi.util.MXException;

// Referenced classes of package psdi.app.workorder:
//            WOSet, LocationCustom, LocationCustomSetRemote
//psdi.id2.mapa.ID2LocationCustomSet
//psdi.tloam.app.location.TloamLocationSet
//ANTES ESTAVA EXTENDENDO DE LocationSet
public class ID2PRLine extends PlusTPRLine
        implements ID2PRLineSetRemote {

    public ID2PRLine(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
    }

    @Override
    public void remove() throws MXException, RemoteException {
        Uteis.espera("***************** remove() ID2PRLineSet");
        atribuiSubtotais();
        super.remove();
    }

    private void atribuiSubtotais() throws MXException, RemoteException {
        Uteis.espera("***************** save() ID2PRLineSet");
        Long qtdEntrega = getMboValueData("ORDERQTY").getDataAsLong();
        Uteis.espera("***************** save() qtdEntrega = " + qtdEntrega);
        Long somaQtdEnt = Executa.somaValor(getMbo().getMboSet("ID2RELPRLINE"), "MS_QTDENT");
        Uteis.espera("***************** save() somaQtdEnt = " + somaQtdEnt);
        getMbo().setValue("ID2SUBTOTAL", qtdEntrega - somaQtdEnt);

    }

    public void validate() throws MXException, RemoteException {
        Uteis.espera("******************validate ID2PRLineSet");

        if (getMboValueData("PR.ID2DATAPLANO") != null) {
            if ((!getMbo().isNew()) && (!getMboValueData("PR.ID2DATAPLANO").isNull())) {
                Uteis.espera("******************validate alterado");
                getMbo().setValue("ID2STATUS", "ALTERADO");
            }
        }
        atribuiSubtotais();
        super.validate();
        Uteis.espera("***************** save() FIM");
    }

	public void save()
          throws MXException, java.rmi.RemoteException
	{
		getMboSet("ID2RELPRLINE").save();
	}
}
