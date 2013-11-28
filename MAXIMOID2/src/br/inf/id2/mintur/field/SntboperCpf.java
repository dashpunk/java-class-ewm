package br.inf.id2.mintur.field;

import br.inf.id2.common.util.Uteis;
import br.inf.id2.common.util.Validar;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class SntboperCpf extends MboValueAdapter {

    public SntboperCpf(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        if (!Validar.CPF(Uteis.getApenasNumeros(getMboValue().getString()))) {
            throw new MXApplicationException("id2message", "CpfInvalido");
        }
        MboSet operadores;
        operadores = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SNTBOPER", getMboValue().getMbo().getUserInfo());

        operadores.setWhere("cpf = '" + Uteis.getApenasNumeros(getMboValue().getString()) + "'");
        operadores.reset();

        if (operadores.count() > 0) {
            throw new MXApplicationException("sntboper", "cpfExistente");
        }

        getMboValue().setValue(Uteis.getApenasNumeros(getMboValue().getString()), MboConstants.NOVALIDATION_AND_NOACTION);

        super.validate();
    }
}
