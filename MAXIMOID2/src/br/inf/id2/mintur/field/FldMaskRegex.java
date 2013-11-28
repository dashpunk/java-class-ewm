package br.inf.id2.mintur.field;

import java.util.regex.Pattern;
import psdi.id2.Uteis;
import psdi.id2.Validar;
import psdi.mbo.*;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class FldMaskRegex extends MboValueAdapter {

    /**
     * Método construtor de ID2FldEmissaoTipoLocal
     * @param mbv 
     * @throws MXException
     */
    public FldMaskRegex(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        super.initValue();

        String valor = getMboValue().getString();
        String mascara = getMboValue().getMbo().getString("RL01DOCS.DSMASC");
        String expressao = getMboValue().getMbo().getString("RL01DOCS.DSVALICONT");
        String tpDocumento = getMboValue().getMbo().getString("RL01DOCS.UPTIPODOC");

        //System.out.println("--- valor "+valor);
        //System.out.println("--- mascara "+mascara);
        //System.out.println("--- expressao "+expressao);
        //System.out.println("--- tpDocumento "+tpDocumento);

        if (!mascara.equals("")) {
            String valorMascarado = Uteis.getValorMascarado(mascara, valor, true);

            Uteis.espera("*********** vm " + valorMascarado);

            try {
                getMboValue().setValue(valorMascarado, MboConstants.NOVALIDATION_AND_NOACTION);
            } catch (Exception e) {
                //System.out.println("************** e = " + e.getMessage());
                throw new MXApplicationException("tbdocpes", "MascaraInválida");

            }

        }

        if (!expressao.equals("")) {
            valor = getMboValue().getString();

            Uteis.espera("*********** v " + valor);

            if (!Pattern.matches(expressao, valor)) {
                throw new MXApplicationException("tbdocpes", "ExpressaoNaoEncontrada");
            }

        }

        //System.out.println("------------- Antes de CPF. "+tpDocumento);
        if (tpDocumento.equals("CPF")) {
            //System.out.println("------------- em CPF. "+tpDocumento);
            valor = getMboValue().getString();

            if (!Validar.CPF(Uteis.getApenasNumeros(valor))) {
                throw new MXApplicationException("tbdocpes", "DigitoInvalido");
            }

        }


    }
}
