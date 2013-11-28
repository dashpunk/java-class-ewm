package br.inf.id2.mintur.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class MrRequestedBy extends psdi.app.mr.FldMRRequestedBy {

    public MrRequestedBy(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();   
        subistituiValor();        
        System.out.println("---MrRequestedBy action");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        subistituiValor();
        System.out.println("---MrRequestedBy validate");
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        System.out.println("---MrRequestedBy initValue");
    }
    

    private void subistituiValor() {

        System.out.println("---CriarRequisicaoRL subistituiValor");
        try {
            System.out.println("---CriarRequisicaoRL count a " + getMboValue().getMbo().getMboSet("MXRL01TBCASE").count());
            if (getMboValue().getMbo().getMboSet("MXRL01TBCASE").count() > 0) {
                System.out.println("a");
                if (getMboValue().getMbo().getMboSet("MXRL01TBCASE").getMbo(0).getMboSet("RHRLUO0003").count() > 0) {
                    System.out.println("b");
                    if (getMboValue().getMbo().getMboSet("MXRL01TBCASE").getMbo(0).getMboSet("RHRLUO0003").getMbo(0).getMboSet("RHSTSGLUO").count() > 0) {
                        System.out.println("c");
                        String valor = getMboValue().getMbo().getMboSet("MXRL01TBCASE").getMbo(0).getMboSet("RHRLUO0003").getMbo(0).getMboSet("RHSTSGLUO").getMbo(0).getString("RHSTSGLUO");
                        
                        boolean readonly = getMboValue("RHSTSGLUO").isFlagSet(MboConstants.READONLY);
                        System.out.println("---readonly "+readonly);
                        if (readonly) {
                            getMboValue("RHSTSGLUO").setFlag(MboConstants.READONLY, false);
                        }
                        System.out.println("--c1");
                        getMboValue().getMbo().setValue("RHSTSGLUO", valor, MboConstants.NOVALIDATION_AND_NOACTION);
                        System.out.println("--c2");
                        if (readonly) {
                            getMboValue("RHSTSGLUO").setFlag(MboConstants.READONLY, true);
                        }
                        System.out.println("--c3");
                        
                        System.out.println("c fim");
                    }
                }
            }
        } catch (MXException e) {
            System.out.println("---CriarRequisicaoRL e " + e.getMessage());
            e.getStackTrace();
        } catch (RemoteException e) {
            System.out.println("---CriarRequisicaoRL e " + e.getMessage());
            e.getStackTrace();
        }

    }
}
