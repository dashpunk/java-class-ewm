package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.inf.id2.common.util.Data;
import br.inf.id2.common.util.Validar;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.controller.BoundComponentInstance;

/**
 *
 * @author Ricardo S Gomes
 */
public class Snapfnrh extends psdi.webclient.system.beans.AppBean {

    public Snapfnrh() {
        System.out.println("--- Snapfnrh 16 ");
    }

    @Override
    public int INSERT() throws MXException, RemoteException {
        int retorno = super.INSERT();
        System.out.println("---INSERT a");
        try {
            preencheCnpj();
        } catch (Exception e) {
        }

        return retorno;
    }
//
//    @Override
//    protected void initialize() throws MXException, RemoteException {
//        super.initialize();
//        System.out.println("---initialize a");
//        try {
//            preencheCnpj();
//        } catch (Exception e) {
//        }
//    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        System.out.println("--- Snaphosp02.SAVE()");

//        String campoPais = "BGSTDSCPAIS";
        String campoPais = "SNNACIONALIDADE";
        String campoPaisBrasil = "BRASIL";

        //TODO carregar
        boolean isMercoSul = false;
        boolean semCie = true;


        boolean semCpf = true;
        boolean semRg = true;
        boolean semCertidaoNascimento = true;
        boolean semPassaporte = true;

        MboRemote mbo;

        //MERCOSUL
        System.out.println("mboSet bloco " + getMbo().getMboSet("SNRLTER01").getMbo(0).getMboSet("BGRLBLOCO01").count());
        for (int i = 0; ((mbo = getMbo().getMboSet("SNRLTER01").getMbo(0).getMboSet("BGRLBLOCO01").getMbo(i)) != null); i++) {
            System.out.println("i " + i + " bloco " + mbo.getString("BGSTCODBLOCO"));
            //fiz dessa forma para que se for necessário pesquisar outro bloco economico ele continue
            if (mbo.getString("BGSTCODBLOCO").equalsIgnoreCase("MERCOSUL")) {
                isMercoSul = true;
            }
        }


        if (getMbo().getString("SNTIPDOC").equalsIgnoreCase("CERTNASC") && !getMbo().getString("SNNUMDOC").equals("")) {
            semCertidaoNascimento = false;
        }
        if (getMbo().getString("SNTIPDOC").equalsIgnoreCase("PASSPORT") && !getMbo().getString("SNNUMDOC").equals("")) {
            semPassaporte = false;
        }
        if (getMbo().getString("SNTIPDOC").equalsIgnoreCase("RG") && !getMbo().getString("SNNUMDOC").equals("")) {
            semRg = false;
        }
        if (!getMbo().getString("SNNUMCPF").equalsIgnoreCase("")) {
            semCpf = false;
        }
        if (!getMbo().getString("SNNUMCPF").equalsIgnoreCase("") && !Validar.CPF(getMbo().getString("SNNUMCPF"))) {
            throw new MXApplicationException("id2message", "CpfInvalido");
        }
        if (getMbo().getString("SNTIPDOC").equalsIgnoreCase("CIE") && !getMbo().getString("SNNUMDOC").equals("")) {
            semCie = false;
        }

        System.out.println("semCpf " + semCpf);
        System.out.println("semRg  " + semRg);
        System.out.println("semCertidaoNascimento  " + semCertidaoNascimento);
        System.out.println("semPassaporte  " + semPassaporte);

        System.out.println("idade " + Data.getMeses(getMbo().getDate("SNDTNASCIMENTO")) / 12);

        if (getMbo().getString(campoPais).equalsIgnoreCase(campoPaisBrasil)
                && ((Data.getMeses(getMbo().getDate("SNDTNASCIMENTO")) / 12) >= 18)
                && (semCpf)) {
            throw new MXApplicationException("fnrh", "cpfRgObrigatorio");
        }

        if (getMbo().getString(campoPais).equalsIgnoreCase(campoPaisBrasil)
                && ((Data.getMeses(getMbo().getDate("SNDTNASCIMENTO")) / 12) < 18)
                && (semRg && semCertidaoNascimento && semCpf)) {
            throw new MXApplicationException("fnrh", "rgOuCertidaoObrigatorio");
        }

        if (!getMbo().getString(campoPais).equalsIgnoreCase(campoPaisBrasil)) {
            if (!isMercoSul && semPassaporte) {
                throw new MXApplicationException("fnrh", "passaporteObrigatorio");
            }
            if (isMercoSul && (semPassaporte && semCie)) {
                throw new MXApplicationException("fnrh", "passaporteCieObrigatorio");
            }
        }


        System.out.println("--- Snapfnrh01.SAVE()");

        System.out.println("*** ANTES dataHoraInicialMenorFinal A");
        if (getMbo().getDate("SNPREVSAI") != null && getMbo().getDate("SNPREVENT") != null) {
            if (Data.dataHoraInicialMenorFinal(getMbo().getDate("SNPREVSAI"), getMbo().getDate("SNPREVENT"))) {
                throw new MXApplicationException("fnrh", "prevCheckoutMenorCheckin");
            }
        }

        System.out.println("--- relacionamento " + getMbo().getMboSet("MTRL01MAXUSER").count());
        if (getMbo().getMboSet("MTRL01MAXUSER").count() == 0) {
            throw new MXApplicationException("fnrh", "semCnpjVinculado");
        }

        //Atualiza cadastro do hóspede
        MboSet hospedeSet;
        hospedeSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SNTBHOSPEDES", getMbo().getUserInfo());


        if (getMbo().getString("SNNUMCPF") != null && !getMbo().getString("SNNUMCPF").equals("")) {
            hospedeSet.setWhere("SNNUMCPF = \'" + getMbo().getString("SNNUMCPF") + "\'");
        } else if ((getMbo().getString("SNTIPDOC") != null && !getMbo().getString("SNTIPDOC").equals("")) && (getMbo().getString("SNNUMDOC") != null && !getMbo().getString("SNNUMDOC").equals(""))) {
            hospedeSet.setWhere("SNTIPDOC = \'" + getMbo().getString("SNTIPDOC") + "\' and SNNUMDOC = \'" + getMbo().getString("SNNUMDOC") + "\'");
        } else {
            throw new MXApplicationException("fnrh", "nenhumDocumentoEncontrado");
        }
        hospedeSet.reset();

        mbo = null;
        System.out.println("--------------- hospedeSet.count " + hospedeSet.count());

        if ((mbo = hospedeSet.getMbo(0)) == null) {
            System.out.println("--------------- novo ");
            mbo = hospedeSet.add();
        }

        mbo.setValue("SNCELULARDDD", getMbo().getString("SNCELULARDDD"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNCELULARDDI", getMbo().getString("SNCELULARDDI"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNCELULARNUM", getMbo().getString("SNCELULARNUM"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNCIDADERES", getMbo().getString("SNCIDADERES"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNDTNASCIMENTO", getMbo().getString("SNDTNASCIMENTO"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNEMAIL", getMbo().getString("SNEMAIL"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNESTADORES", getMbo().getString("SNESTADORES"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNNACIONALIDADE", getMbo().getString("SNNACIONALIDADE"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNNOMECOMPLETO", getMbo().getString("SNNOMECOMPLETO"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNNUMCPF", getMbo().getString("SNNUMCPF"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNNUMDOC", getMbo().getString("SNNUMDOC"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNOCUPACAO", getMbo().getString("SNOCUPACAO"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNORGEXP", getMbo().getString("SNORGEXP"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNPAISRES", getMbo().getString("SNPAISRES"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNRESIDENCIA", getMbo().getString("SNRESIDENCIA"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNSEXO", getMbo().getString("SNSEXO"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNTELEFONEDDD", getMbo().getString("SNTELEFONEDDD"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNTELEFONEDDI", getMbo().getString("SNTELEFONEDDI"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNTELEFONENUM", getMbo().getString("SNTELEFONENUM"), MboConstants.NOACCESSCHECK);
        mbo.setValue("SNTIPDOC", getMbo().getString("SNTIPDOC"), MboConstants.NOACCESSCHECK);

        hospedeSet.save();

        super.SAVE();

        throw new MXApplicationException("fnrh", "registroSalvo");

    }

    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {


        preencheCnpj();
        return super.fetchRecordData();
    }

    @Override
    public void bindComponent(BoundComponentInstance boundComponent) {
        super.bindComponent(boundComponent);

        System.out.println("---bindComponent");
        try {
            preencheCnpj();

        } catch (RemoteException ex) {
            Logger.getLogger(Snapfnrh.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MXException ex) {
            Logger.getLogger(Snapfnrh.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void preencheCnpj() throws RemoteException, MXException {
        if (getMbo() != null) {
            System.out.println("---bindComponent cnpj " + getMbo().isNull("SNCODCNPJ"));
        }
        if (getMbo() != null && getMbo().isNull("SNCODCNPJ")) {
            System.out.println("--- relacionamento " + getMbo().getMboSet("MTRL01MAXUSER").count());

            System.out.println("--- vcnpj " + getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ"));
            String cnpj = getMbo().getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ");
            System.out.println("--- cnpj " + cnpj);

            getMbo().setValue("SNCODCNPJ", cnpj, MboConstants.NOACCESSCHECK);

            String uf = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("SG_UF");
            String localidade = getMbo().getMboSet("SNRL01CNPJ").getMbo(0).getString("NO_LOCALIDADE");
            System.out.println("--- uf " + uf);
            System.out.println("--- localidade " + localidade);
            getMbo().setValue("SG_UF", uf, MboConstants.NOACCESSCHECK);
            getMbo().setValue("NO_LOCALIDADE", localidade, MboConstants.NOACCESSCHECK);
            System.out.println("--- gcnpj " + getMbo().getString("SNCODCNPJ"));
            refreshTable();
        }
    }
}
