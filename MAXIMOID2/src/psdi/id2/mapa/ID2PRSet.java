package psdi.id2.mapa;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.app.location.LocationSet;
import psdi.app.pr.PRSet;
import psdi.mbo.*;
import psdi.util.MXException;
import psdi.plust.app.pr.PlusTPRSet;
import psdi.util.MXApplicationException;

// Referenced classes of package psdi.app.workorder:
//            WOSet, LocationCustom, LocationCustomSetRemote
//psdi.id2.mapa.ID2LocationCustomSet
//psdi.tloam.app.location.TloamLocationSet
//ANTES ESTAVA EXTENDENDO DE LocationSet
public class ID2PRSet extends PlusTPRSet
        implements ID2PRSetRemote {


    public ID2PRSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
            throws MXException, RemoteException {
        return new ID2PlusTPR(mboset);
    }

    /**
     * @TODO Se for necessário acrescentar mais algum parâmetro, faça também na classe ID2PlusTPR.java
     * ATENÇÃO!!!
     */
    @Override
    protected void addMbo(Mbo mbo) {
        System.out.println("********************** add(Mbo)");
        super.addMbo(mbo);
        try {
            if (getMbo() != null) {
                System.out.println("*************** --- not nul");
                if (getMbo().toBeAdded()) {

                    System.out.println("*************** --- toBeAdded (Alterado)");
                    if ((getMbo().getMboSet("MS_RL04PER") != null) && (getMbo().isNull("ID2CODCOO"))) {
						/*
                        System.out.println("*************** SAVE() Entrou total de linhas do relacionamento = " + getMbo().getMboSet("MS_RL04PER").count());
                        if (getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("SECRETARIA") != null) {
                            getMbo().setValue("ID2CODSEC", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("SECRETARIA"));
                            System.out.println("*************** SAVE() Entrou 1 " + getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("SECRETARIA"));
                        }
						*/
                    	System.out.println("###################### Coordenação não é nulo e tem relacionamento");
                        if (getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO") != null) {
                            getMbo().setValue("ID2CODCOO", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO"));
                            getMbo().setValue("ID2SEC", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2SEC"));
                            System.out.println("################# ID2DIR = " + getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2DIR"));
                            getMbo().setValue("ID2DIR", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2DIR"));
                        } else {
                        	System.out.println("##################### Exceção");
                            throw new MXApplicationException("pr", "SemLotacao");
                        }
                    }
                    System.out.println("####################### Operação finalizada!");
                }
            }
        } catch (Exception ex) {
            System.out.println("*****************************exceÃ§Ã£o 1");
        }

    }

    /*
    @Override
    public void validate() throws MXException, RemoteException {
    System.out.println("********************** validate()");
    if ((getMbo().getMboSet("MS_RL04PER") != null) && (getMbo().isNull("ID2CODSEC"))) {
    System.out.println("*************** SAVE() Entrou total de linhas do relacionamento = " + getMbo().getMboSet("MS_RL04PER").count());
    if (getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("SECRETARIA") != null) {
    getMbo().setValue("ID2CODSEC", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("SECRETARIA"));
    System.out.println("*************** SAVE() Entrou 1 " + getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("SECRETARIA"));
    }
    if (getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("LOTACAO") != null) {
    getMbo().setValue("ID2CODCOO", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("LOTACAO"));
    System.out.println("*************** SAVE() Entrou 2 " + getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("LOTACAO"));
    }

    }
    super.validate();
    }
     */
    /*
    public void save() throws MXException, RemoteException {
    System.out.println("************** save() Antes");
    super.save();
    System.out.println("************** save() Depois "+getMbo().getString("STATUS"));
    if (getMbo().getString("STATUS").equals("APRV")) {
    System.out.println("************** save() ENTROU NA CONDIÃ‡ÃƒO ");
    Executa.setReadOnly((MboSet) getMbo().getMboSet("PRLINE"), new String[]{
    "ORDERUNIT",
    "ORDERQTY",
    "ID2ESTOQUEESTRATEGICO",
    "UNITCOST",
    "COST"
    }, false);
    }
    System.out.println("************** save() Fim");
    }*/


}
