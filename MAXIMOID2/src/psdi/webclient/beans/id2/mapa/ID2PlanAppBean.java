package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;

import psdi.app.asset.Asset;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.common.StatefulAppBean;

/**
 *
 * @author Jesse Rovira
 */
public class ID2PlanAppBean extends StatefulAppBean {

    /**
     *
     */
    public ID2PlanAppBean() {
    }

    /**
     *
     * @return @throws MXException
     * @throws RemoteException
     */
    public int SAVE()
            throws MXException, RemoteException {
        System.out.println("############################# Entrou no ID2PlanAPPBean SAVE()");

        try {

            for (int i = 0; i < getMboSet().count(); i++) {

                Asset asset = (Asset) getMboSet().getMbo(i);
                System.out.println("################# Iteracao " + i + " ASSET: " + asset);

                System.out.println("################ ASSET SELECIONADO? = " + asset.isSelected());
                if (asset.isSelected()) {
                    try {
                        /*
                         * System.out.println("############### DATA = " +
                         * asset.getDate("MADATENASC")); GregorianCalendar
                         * gregoriancalendar = new GregorianCalendar();
                         * gregoriancalendar.setTime(asset.getDate("MADATENASC"));
                         * gregoriancalendar.setLenient(false); java.util.Date
                         * date = gregoriancalendar.getTime();
                         */
                    } catch (Exception exception) {
                        throw new MXApplicationException("asset", "DataNascimentoInvalida");
                    }

                    asset.changeStatus("EM USO", false, false, false, false);

                    asset.save();

                }

            }

            getMboSet().save();
        } catch (MXException mxexception) {
            mxexception.printStackTrace();
        } catch (RemoteException remoteexception) {
            remoteexception.printStackTrace();
        }
        int j = super.SAVE();
        return j;
    }
}
