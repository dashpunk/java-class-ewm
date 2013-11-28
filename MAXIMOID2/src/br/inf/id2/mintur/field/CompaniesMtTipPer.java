package br.inf.id2.mintur.field;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class CompaniesMtTipPer extends MboValueAdapter {

    public CompaniesMtTipPer(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("---CompaniesMtTipPer 2202");
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        System.out.println("---CompaniesMtTipPer.validate()");
        getMboValue("COMPANY").setValueNull(MboConstants.NOACCESSCHECK);
        System.out.println("---CompaniesMtTipPer.validate() end");
        super.validate();
    }

//    @Override
//    public void initValue() throws MXException, RemoteException {
//        System.out.println("---CompaniesMtTipPer.initValue()");
//        verificaPreenchimento();
//        System.out.println("---CompaniesMtTipPer.initValue() end");
//        super.initValue();
//    }
//
//    private void verificaPreenchimento() throws RemoteException, MXException {
//        System.out.println("---CompaniesMtTipPer.vp()");
//        getMboValue("COMPANY").setFlag(MboConstants.READONLY, false);
//        getMboValue().getMbo().setValueNull("COMPANY");
//        if (getMboValue().getMbo().isNull("MTTIPPER") || getMboValue("MTTIPPER").getString().equals("")) {
//            System.out.println("---CompaniesMtTipPer.vp() is null");
//            System.out.println("---CompaniesMtTipPer.vp() nocompany null");
//            getMboValue("COMPANY").setFlag(MboConstants.READONLY, true);
//            System.out.println("---CompaniesMtTipPer.vp() nocompany readonly");
//        } else {
//            System.out.println("---CompaniesMtTipPer.vp() not null");
//            getMboValue("COMPANY").setFlag(MboConstants.READONLY, false);
//            System.out.println("---CompaniesMtTipPer.vp() nocompany readonly false");
//        }
//
//        System.out.println("---CompaniesMtTipPer.vp() end");
//
//    }



}
