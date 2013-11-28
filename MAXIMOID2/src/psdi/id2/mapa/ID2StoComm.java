package psdi.id2.mapa;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.*;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2StoComm extends Mbo
        implements ID2StoCommRemote {

    /** Creates a new instance of ID2GrupoRisco */
    public ID2StoComm(MboSet mboset) throws MXException, RemoteException {
        super(mboset);
    }

    public void init()
            throws MXException {
        super.init();
    }


    @Override
    public void save() throws MXException, RemoteException {

        if (isNew()) {
            Uteis.espera("///////////////////////// INVENTORY");
            //INVENTORY
            int idGrupoEspecie = getInt("ID2GRPESP");
            System.out.println("############ Id do Grupo Especie=" + idGrupoEspecie);
            try {
                System.out.println("............... ID2GRPESP = "+getString("ID2GRPESP"));
                if (getString("ID2GRPESP").equalsIgnoreCase("6")) {
                    System.out.println(".... aves");
                    Executa.adiciona((MboSet) getMboSet("ID2ITEM"), (MboSet) getMboSet("ID2INVENTORY"), (MboSet) getMboSet("ID2INVBALANCES"),
                            new Object[]{"ITEMSETID", "#CADA", "#CADA", /*"ORGID",  "SITEID", "ESTOC",   *0,*/ 0, 0, 0, 0, 0, "ITEMNUM", getMboValue("LOCATION"), 1, 1, 1, /*inventoryId,*/ 1 /* status*/, getMboValue("LOCATION"), getMboValue("LOCATION")},
                            new String[]{"ITEMSETID", "ORDERUNIT", "ISSUEUNIT", /*"ORGID",  "SITEID", "CATEGORY", "CCF", */ "DELIVERYTIME", "ISSUE1YRAGO", "ISSUE2YRAGO", "ISSUE3YRAGO", "ISSUEYTD", "ITEMNUM", "LOCATION", "MAXLEVEL", "MINLEVEL", "ORDERQTY", /*"INVENTORYID", */ "INTERNAL" /*, "STATUS", "STATUSDATE"*/, "BINNUM", "LOTNUM"}); //destino
                } else {
                    System.out.println(".... não aves");
                    Executa.adiciona((MboSet) getMboSet("ID2ITEM"), (MboSet) getMboSet("ID2INVENTORY"), (MboSet) getMboSet("ID2INVBALANCES"),
                            new Object[]{"ITEMSETID", "#CADA", "#CADA", /*"ORGID",  "SITEID", "ESTOC",   *0,*/ 0, 0, 0, 0, 0, "ITEMNUM", getMboValue("LOCATION"), 1, 1, 1, /*inventoryId,*/ 1 /* status*/},
                            new String[]{"ITEMSETID", "ORDERUNIT", "ISSUEUNIT", /*"ORGID",  "SITEID", "CATEGORY", "CCF", */ "DELIVERYTIME", "ISSUE1YRAGO", "ISSUE2YRAGO", "ISSUE3YRAGO", "ISSUEYTD", "ITEMNUM", "LOCATION", "MAXLEVEL", "MINLEVEL", "ORDERQTY", /*"INVENTORYID", */ "INTERNAL" /*, "STATUS", "STATUSDATE"*/}); //destino

                }
            } catch (Exception ex) {
                Uteis.espera("*************** Erro em INVENTORY = " + ex.getMessage());
                Uteis.espera("*************** Erro em INVENTORY = " + ex.getStackTrace());
            }

        }

        Uteis.espera(">>>>>>>>>>>>>>>>>>>>>>> FIM");

        super.save();
    }
}
