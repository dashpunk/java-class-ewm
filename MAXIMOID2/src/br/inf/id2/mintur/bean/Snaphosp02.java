package br.inf.id2.mintur.bean;

import br.inf.id2.common.util.Data;
import br.inf.id2.common.util.Executa;
import br.inf.id2.common.util.Validar;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * 
 * @author ricardo
 */
public class Snaphosp02 extends psdi.webclient.beans.common.StatefulAppBean {

    public Snaphosp02() {
        System.out.println("--- Snaphosp02 16:26");
    }

    @Override
    public int SAVE() throws MXException, RemoteException {

        System.out.println("--- Snaphosp02.SAVE()");

        String campoPais = "BGSTDSCPAIS";
        String campoPaisBrasil = "BRASIL";

        //TODO carregar
        boolean isMercoSul = false;
        boolean semCie = true;


        boolean semCpf = true;
        boolean semRg = true;
        boolean semCertidaoNascimento = true;
        boolean semPassaporte = true;


        System.out.println("preenchimento endereco " + Validar.getMboSetTamanho(getMbo().getMboSet("BGRLENDE01")));
        if (Validar.getMboSetTamanho(getMbo().getMboSet("BGRLENDE01")) == 0) {
            throw new MXApplicationException("fnrh", "preenchimentoMinimoEndereco");
        }

        MboRemote mbo;
        System.out.println("mboSet bloco " + getMbo().getMboSet("SNRLTER01").getMbo(0).getMboSet("BGRLBLOCO01").count());
        for (int i = 0; ((mbo = getMbo().getMboSet("SNRLTER01").getMbo(0).getMboSet("BGRLBLOCO01").getMbo(i)) != null); i++) {
            System.out.println("i " + i + " bloco " + mbo.getString("BGSTCODBLOCO"));
            //fiz dessa forma para que se for necessário pesquisar outro bloco economico ele continue
            if (mbo.getString("BGSTCODBLOCO").equalsIgnoreCase("MERCOSUL")) {
                isMercoSul = true;
            }
        }

        mbo = null;
        for (int i = 0; ((mbo = getMbo().getMboSet("SNRLDOC01").getMbo(i)) != null); i++) {
        	if (!mbo.toBeDeleted()) {
            if (mbo.getString("SNTIPDOC").equalsIgnoreCase("CERTNASC")) {
                semCertidaoNascimento = false;
            }
            if (mbo.getString("SNTIPDOC").equalsIgnoreCase("PASSPORT")) {
                semPassaporte = false;
            }
            if (mbo.getString("SNTIPDOC").equalsIgnoreCase("RG")) {
                semRg = false;
            }
            if (mbo.getString("SNTIPDOC").equalsIgnoreCase("CPF")) {
                if (!Validar.CPF(mbo.getString("SNNUMDOC"))) {
                    throw new MXApplicationException("id2message", "CpfInvalido");
                }
                semCpf = false;
            }
            if (mbo.getString("SNTIPDOC").equalsIgnoreCase("CIE")) {
                semCie = false;
            }
        	}
        }

        System.out.println("boolean " + semCpf);
        System.out.println("boolean  " + semRg);
        System.out.println("boolean  " + semCertidaoNascimento);
        System.out.println("boolean  " + semPassaporte);

        System.out.println("idade " + Data.getMeses(getMbo().getDate("BIRTHDATE")) / 12);




        if (getMbo().getString(campoPais).equalsIgnoreCase(campoPaisBrasil)
                && ((Data.getMeses(getMbo().getDate("BIRTHDATE")) / 12) >= 18)
                && (semCpf)) {
            throw new MXApplicationException("fnrh", "cpfRgObrigatorio");
        }

        if (getMbo().getString(campoPais).equalsIgnoreCase(campoPaisBrasil)
                && ((Data.getMeses(getMbo().getDate("BIRTHDATE")) / 12) < 18)
                && (semRg && semCertidaoNascimento && semCpf)) {
            throw new MXApplicationException("fnrh", "rgOuCertidaoObrigatorio");
        }

        if (!getMbo().getString(campoPais).equalsIgnoreCase(campoPaisBrasil)) {
        	System.out.println("*** 1");
            if (!isMercoSul && semPassaporte) {
            	System.out.println("*** 2");
                throw new MXApplicationException("fnrh", "passaporteObrigatorio");
            }
            if (isMercoSul && (semPassaporte && semCie)) {
            	System.out.println("*** 3");
                throw new MXApplicationException("fnrh", "passaporteCieObrigatorio");
            }
        }
//        if (!getMbo().getString(campoPais).equalsIgnoreCase(campoPaisBrasil)
//                && semPassaporte && isMercoSul) {
//        	System.out.println("*** 4");
//            throw new MXApplicationException("fnrh", "passaporteObrigatorio");
//        }


        System.out.println("--- Snaphosp02.SAVE() FIM");
        return super.SAVE();
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        super.dataChangedEvent(speaker);
        try {
//            System.out.println("--- speaker " + speaker.getUniqueIdName());
            //Endereco
            if (speaker.getUniqueIdName().equalsIgnoreCase("BGTBENDE01ID")) {
//                System.out.println("BGTBENDE01ID inicio");
//                System.out.println("BGTBENDE01ID count "+getMbo().getMboSet("BGRLENDE01").count());
                
                Executa.setSelecionaAtualDeselecionarDemais(getMbo().getMboSet("BGRLENDE01"), "ISPRIMARY");
//                System.out.println("BGTBENDE01ID FIM");
            }
        } catch (MXException ex) {
            Logger.getLogger(Snaphosp02.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Snaphosp02.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
