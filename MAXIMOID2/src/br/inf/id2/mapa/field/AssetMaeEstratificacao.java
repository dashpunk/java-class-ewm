package br.inf.id2.mapa.field;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  br.inf.id2.mapa.field.AssetMaeEstratificacao
 */
public class AssetMaeEstratificacao extends MboValueAdapter {

    public AssetMaeEstratificacao(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        ////System.out.println("----- AssetMaeEstratificacao INICIO");

        if ((getMboValue().getMbo().isNull("MADATENASC")) || (getMboValue().getMbo().isNull("MAGEN"))) {
            getMboValue().getMbo().setValueNull("MAESTRATIFICACAO");
        } else {
            Date dataNascimento = getMboValue().getMbo().getDate("MADATENASC");
            String genero = getMboValue().getMbo().getString("MAGEN");

            ////System.out.println("-- dataNascimento " + dataNascimento);
            ////System.out.println("-- genero " + genero);


            int meses = Data.getMeses(dataNascimento);

            ////System.out.println("-- meses " + meses);

            int aFatorGenero;

            int resultado = 0;
            if (genero.equals("M")) {
                aFatorGenero = 0;
            } else {
                aFatorGenero = 4;
            }

            if (meses < 13) {
                resultado = aFatorGenero + 1;
            } else if ((meses > 12) && (meses < 25)) {
                resultado = aFatorGenero + 2;
            } else if ((meses > 24) && (meses < 37)) {
                resultado = aFatorGenero + 3;
            } else if (meses > 36) {
                resultado = aFatorGenero + 4;
            }

            ////System.out.println("== resultado " + resultado);

            getMboValue().getMbo().setValue("MAESTRATIFICACAO", "1.1."+resultado);
            
            ////System.out.println("----- AssetMaeEstratificacao FIM");

        }
    }
}
