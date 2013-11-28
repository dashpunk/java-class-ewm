package br.inf.id2.me.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Dyogo Dantas
 *  
 */
public class UnidadeOrganizacionalCapacitacao extends MboValueAdapter {

    public UnidadeOrganizacionalCapacitacao(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();

        System.out.println("###################### UnidadeOrganizacionalCapacitacao - initValue");
        if (getMboValue().getMbo().isNew()) {
            System.out.println("############## " + getMboValue().getMbo().getMboSet("MXRLRHCASE01"));
            System.out.println("############## " + getMboValue().getMbo().getMboSet("MXRLRHCASE01").getMbo(0));
            System.out.println("############## " + getMboValue().getMbo().getMboSet("MXRLRHCASE01").getMbo(0).getInt("MXUOEXEID"));
//            System.out.println("############## " + getMboValue().getMbo().getMboSet("MXRLRHCASE01").getMbo(0).getInt("MXUOLOTID"));

            if (getMboValue().getMbo().getMboSet("MXRLRHCASE01").getMbo(0).getInt("MXUOEXEID") == 0) {
                MboRemote mbo;
                Integer das = null;
                Integer quadro = null;
                Integer outro = null;
                System.out.println("--- UnidadeOrganizacionalCapacitacao for count "+getMboValue().getMbo().getMboSet("MXRLRHCASE01").getMbo(0).getMboSet("MXRLVAGA01").count());
                for (int i = 0; ((mbo = getMboValue().getMbo().getMboSet("MXRLRHCASE01").getMbo(0).getMboSet("MXRLVAGA01").getMbo(i)) != null); i++) {
                    System.out.println("--- UnidadeOrganizacionalCapacitacao i "+i);
                    System.out.println("--- UnidadeOrganizacionalCapacitacao mxtipcar "+mbo.getString("MXTIPCAR"));

                    if (mbo.getString("MXTIPCAR").equalsIgnoreCase("DAS")) {
                        System.out.println("--- unidade... das");
                        das = mbo.getInt("MXTBUAID");
                        break;
                    }
                    if (mbo.getString("MXTIPCAR").equalsIgnoreCase("QUADRO")) {
                        System.out.println("--- unidade... quadro");
                        quadro = mbo.getInt("MXTBUAID");
                    }
                    if (!mbo.getString("MXTIPCAR").equalsIgnoreCase("QUADRO") && !mbo.getString("MXTIPCAR").equalsIgnoreCase("DAS")) {
                        System.out.println("--- unidade... outros");
                        outro = mbo.getInt("MXTBUAID");
                    }

                }

                Integer valor;
                if (das != null) {
                    valor = das;
                } else if (quadro != null) {
                    valor = quadro;
                } else {
                    valor = outro;
                }

                System.out.println("--- unidade... valor "+valor);
                if (valor == null) {
                    throw new MXApplicationException("MXRLVAGA01", "relacionamentoVazio");
                }

                getMboValue().getMbo().setValue("MXTBUAID", valor);
            } else {
                getMboValue().getMbo().setValue("MXTBUAID", getMboValue().getMbo().getMboSet("MXRLRHCASE01").getMbo(0).getInt("MXUOEXEID"));
            }
        }
    }
}
