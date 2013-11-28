package br.inf.id2.mintur.field;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class SntbfnrhSnnumcpfSntipdocSnnumdoc extends psdi.app.company.FldCompanyCompany {

    public SntbfnrhSnnumcpfSntipdocSnnumdoc(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("--- SntbfnrhSnnumcpfSntipdocSnnumdoc");
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        System.out.println("---> SntbfnrhSnnumcpfSntipdocSnnumdoc validate");
        super.validate();
        String numDoc = "";
        System.out.println("-----------> NUMDOC a " + getMboValue("SNNUMDOC").getString());
        numDoc = getMboValue("SNNUMDOC").getString();
//            getMboValue("SNNUMDOC").setValue(, MboConstants.NOVALIDATION_AND_NOACTION);
        System.out.println("-----------> NUMDOC b " + getMboValue("SNNUMDOC").getString());
        System.out.println("-----------> NUMDOC c " + numDoc);
        numDoc = numDoc.replaceAll("\\.", "").replaceAll("\\-", "");
        System.out.println("-----------> NUMDOC d " + numDoc);

        MboSet hospedeSet;
        hospedeSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SNTBHOSPEDES", getMboValue().getMbo().getUserInfo());

        if (getMboValue().getName().equalsIgnoreCase("SNNUMCPF")) {
            if (getMboValue().getString() == null || getMboValue().getString().equals("")) {
                return;
            }
            hospedeSet.setWhere("SNNUMCPF = \'" + getMboValue().getString() + "\'");
        } else {

            if ((getMboValue("SNTIPDOC").getString() == null || getMboValue("SNTIPDOC").getString().equals("")) || (getMboValue("SNNUMDOC").getString() == null || getMboValue("SNNUMDOC").getString().equals(""))) {
                return;
            }


            hospedeSet.setWhere("SNTIPDOC = \'" + getMboValue("SNTIPDOC").getString() + "\' and SNNUMDOC = \'" + numDoc + "\'");
        }
        hospedeSet.reset();

        MboRemote mbo;
        System.out.println("--- SntbfnrhSnnumcpfSntipdocSnnumdoc count " + hospedeSet.count());

        if ((mbo = hospedeSet.getMbo(0)) != null) {
            System.out.println("--- SntbfnrhSnnumcpfSntipdocSnnumdoc mbo ");
            getMboValue("SNCELULARDDD").setValue(mbo.getString("SNCELULARDDD"), MboConstants.NOACCESSCHECK);
            getMboValue("SNCELULARDDI").setValue(mbo.getString("SNCELULARDDI"), MboConstants.NOACCESSCHECK);
            getMboValue("SNCELULARNUM").setValue(mbo.getString("SNCELULARNUM"), MboConstants.NOACCESSCHECK);

            getMboValue("SNDTNASCIMENTO").setValue(mbo.getDate("SNDTNASCIMENTO"), MboConstants.NOACCESSCHECK);
            getMboValue("SNEMAIL").setValue(mbo.getString("SNEMAIL"), MboConstants.NOACCESSCHECK);

            getMboValue("SNPAISRES").setValue(mbo.getString("SNPAISRES"), MboConstants.NOACCESSCHECK);
            getMboValue("SNESTADORES").setValue(mbo.getString("SNESTADORES"), MboConstants.NOACCESSCHECK);
            getMboValue("SNCIDADERES").setValue(mbo.getString("SNCIDADERES"), MboConstants.NOACCESSCHECK);
            getMboValue("SNCIDADERESLK").setValue(mbo.getString("SNCIDADERES"), MboConstants.NOACCESSCHECK);

            getMboValue("SNNACIONALIDADE").setValue(mbo.getString("SNNACIONALIDADE"), MboConstants.NOACCESSCHECK);
            getMboValue("SNNOMECOMPLETO").setValue(mbo.getString("SNNOMECOMPLETO"), MboConstants.NOACCESSCHECK);

            if (!getMboValue().getName().equalsIgnoreCase("SNNUMCPF")) {
                System.out.println("--- SntbfnrhSnnumcpfSntipdocSnnumdoc !cpf ");
                getMboValue("SNNUMCPF").setValue(mbo.getString("SNNUMCPF"), MboConstants.NOVALIDATION_AND_NOACTION);
            }

            getMboValue("SNOCUPACAO").setValue(mbo.getString("SNOCUPACAO"), MboConstants.NOACCESSCHECK);
            getMboValue("SNORGEXP").setValue(mbo.getString("SNORGEXP"), MboConstants.NOACCESSCHECK);

            getMboValue("SNRESIDENCIA").setValue(mbo.getString("SNRESIDENCIA"), MboConstants.NOACCESSCHECK);
            getMboValue("SNSEXO").setValue(mbo.getString("SNSEXO"), MboConstants.NOACCESSCHECK);
            getMboValue("SNTELEFONEDDD").setValue(mbo.getString("SNTELEFONEDDD"), MboConstants.NOACCESSCHECK);
            getMboValue("SNTELEFONEDDI").setValue(mbo.getString("SNTELEFONEDDI"), MboConstants.NOACCESSCHECK);
            getMboValue("SNTELEFONENUM").setValue(mbo.getString("SNTELEFONENUM"), MboConstants.NOACCESSCHECK);
            if (getMboValue().getName().equalsIgnoreCase("SNNUMCPF")) {
                System.out.println("--- SntbfnrhSnnumcpfSntipdocSnnumdoc cpf ");
                getMboValue("SNTIPDOC").setValue(mbo.getString("SNTIPDOC"), MboConstants.NOVALIDATION_AND_NOACTION);
                numDoc = mbo.getString("SNNUMDOC");
            }
        } else {
//            System.out.println("--- SntbfnrhSnnumcpfSntipdocSnnumdoc !mbo ");
//            getMboValue("SNCELULARDDD").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNCELULARDDI").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNCELULARNUM").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNCIDADERES").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNDTNASCIMENTO").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNEMAIL").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNESTADORES").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNNACIONALIDADE").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNNOMECOMPLETO").setValueNull(MboConstants.NOACCESSCHECK);
////            if (!getMboValue().getName().equalsIgnoreCase("SNNUMCPF")) {
////                System.out.println("--- SntbfnrhSnnumcpfSntipdocSnnumdoc !cpf ");
////                getMboValue("SNNUMCPF").setValueNull(MboConstants.NOACCESSCHECK);
////            }
//            getMboValue("SNOCUPACAO").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNORGEXP").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNPAISRES").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNRESIDENCIA").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNSEXO").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNTELEFONEDDD").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNTELEFONEDDI").setValueNull(MboConstants.NOACCESSCHECK);
//            getMboValue("SNTELEFONENUM").setValueNull(MboConstants.NOACCESSCHECK);
////            if (getMboValue().getName().equalsIgnoreCase("SNNUMCPF")) {
////                System.out.println("--- SntbfnrhSnnumcpfSntipdocSnnumdoc cpf ");
////                getMboValue("SNTIPDOC").setValueNull(MboConstants.NOACCESSCHECK);
////                getMboValue("SNNUMDOC").setValueNull(MboConstants.NOACCESSCHECK);
////            }
        }
        getMboValue("SNNUMDOC").setValue(numDoc, MboConstants.NOVALIDATION_AND_NOACTION);
        System.out.println("--- SntbfnrhSnnumcpfSntipdocSnnumdoc FIM ");
    }
}
