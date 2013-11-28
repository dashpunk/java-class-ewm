package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.id2.Validar;
import psdi.id2.Uteis;
import psdi.id2.Executa;


public class ID2PRAppBean extends psdi.webclient.beans.po.POAppBean {

    public ID2PRAppBean() {
    }

    public int SAVE() throws MXException, RemoteException {
        int retorno = super.SAVE();

        Executa.setReadOnly((MboSet) getMbo().getMboSet("PRLINE"), new String[]{
                    //"ID2ITEMNUM",
                    //"ID2UNIDFORNECIMENTO",
                    "ORDERQTY",
                    "UNITCOST",
                    "LINECOST",
                    //"ID2STOQUEESTRATEGICO",
                    //"ID2CARACARMAZ",
                    "REMARK",
                    //"ID2STATUS",
                    //"ID2DISTDIRETA",
                    //"ID2ULTIMOFORNECEDOR",
                    //"ID2ULTIMAMODALIDADE",
                    //"ID2PPA",
                    //"ID2FUNCIONAL",
                    //"ID2LEADTIME"
        }, false);

        return retorno;
    }

}
