package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.app.location.LocationSetRemote;
import psdi.id2.mapa.Uteis;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.util.MXApplicationException;

public class ID2CooExtTableBean extends psdi.webclient.system.beans.DataBean {

    public ID2CooExtTableBean() {
    }

    public void save() throws MXException {
        Uteis.espera("********* save()");

        //ID2GRALAT	ID2GRALON	ID2MINLAT	ID2MINLON	ID2ORILAT	ID2ORILON	ID2SEGLAT	ID2SEGLON
        try {
            if ((getMbo().getString("ID2GRALAT") != null) && (getMbo().getString("ID2UTMLAT") == null)) {
                String aDMSOrientacao = "";

                if (getMbo().getString("ID2ORILAT").equals("S")) {
                    aDMSOrientacao = "-";
                }
                String aDMSDegrees = getMbo().getString("ID2GRALAT");
                String aDMSMinuto = getMbo().getString("ID2MINLAT");
                String aDMSSegundo = getMbo().getString("ID2SEGLAT");
                String aDMS = "";
                aDMS.concat(aDMSOrientacao);
                aDMS.concat(aDMSDegrees);
                aDMS.concat(".");
                aDMS.concat(aDMSMinuto);
                aDMS.concat(aDMSSegundo.replace(".", "").replace(",", ""));
                Uteis.espera("*****************aDMS= "+aDMS);
                getMbo().setValue("ID2UTMLAT", Uteis.converteDMS2UTM(Double.valueOf(aDMS)));

                Uteis.converteDMS2UTM(Double.valueOf(aDMS));
                Uteis.espera("*****************getMbo().getString(\"ID2UTMLAT\") = "+getMbo().getString("ID2UTMLAT"));
            }

        Uteis.espera("********* FIM ");
        } catch (Exception e) {
            Uteis.espera("********* erro em save() "+e.getMessage());
        }

        super.save();
    }
}
