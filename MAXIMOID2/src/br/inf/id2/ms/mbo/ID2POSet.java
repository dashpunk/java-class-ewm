package br.inf.id2.ms.mbo;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;
import psdi.util.MXApplicationException;

// Referenced classes of package psdi.app.workorder:
//            WOSet, LocationCustom, LocationCustomSetRemote
//psdi.id2.mapa.ID2LocationCustomSet
//psdi.tloam.app.location.TloamLocationSet
//ANTES ESTAVA EXTENDENDO DE LocationSet
public class ID2POSet extends psdi.plust.app.po.PlusTPOSet
        implements ID2POSetRemote {

    public ID2POSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
        System.out.println("---------- IDPOSet");
    }

    @Override
    protected void addMbo(Mbo mbo, int i) {
        System.out.println("---------------------- addMbo(Mbo mbo, int i)");
        super.addMbo(mbo, i);
        faz();
    }




    /**
     * @TODO Se for necess�rio acrescentar mais algum par�metro, fa�a tamb�m na classe ID2PlusTPR.java
     * ATEN��O!!!
     */
    @Override
    protected void addMbo(Mbo mbo) {
        System.out.println("********************** add(Mbo)");
        super.addMbo(mbo);
        faz();

    }

    public void faz() {
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
                        System.out.println("###################### Coordena��o n�o � nulo e tem relacionamento");
                        if (getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO") != null) {
                        	getMbo().setValue("ID2CODCOO", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO"));
                            getMbo().setValue("ID2SEC", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2SEC"));
                            System.out.println("################# ID2DIR = " + getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2DIR"));
                            getMbo().setValue("ID2DIR", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2DIR"));
                        } else {
                            System.out.println("##################### Exce��o");
                            throw new MXApplicationException("pr", "SemLotacao");
                        }
                    }
                    System.out.println("####################### Opera��o finalizada!");
                }
            }
        } catch (Exception ex) {
            System.out.println("*****************************exceção 1 " +ex.getMessage());
            ex.getStackTrace();
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
    System.out.println("************** save() ENTROU NA CONDIÇÃO ");
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
