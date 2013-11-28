package br.inf.id2.seedf.bean;

import br.inf.id2.common.util.Validar;
import java.rmi.RemoteException;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class SolicitacaoVagaRemanejamento extends AppBean {

    public int SAVE() throws MXException, RemoteException {
        if (!Validar.preenchimentoMinimoObrigatorio((MboSet) getMbo().getMboSet("SEERL01PSVTEL"), 2)) {
            throw new MXApplicationException("seetbsolva", "ocorrenciasSEERL01SVTELInvalida");
        }

        if (!Validar.preenchimentoMinimoObrigatorio((MboSet) getMbo().getMboSet("SEERL01PSVEMAIL"), 1)) {
            throw new MXApplicationException("seetbsolva", "ocorrenciasSEERL01SVEMAILInvalida");
        }

        /*if (!Validar.preencimentoMaximoObrigatorio((MboSet) getMbo().getMboSet("SEERL01PSVIEP"), "SEEOP", "PRIMEIRA", 1)) {
        throw new MXApplicationException("company", "ocorrenciasOrdemPreferenciaInvalida");
        }

        if (!Validar.preencimentoMaximoObrigatorio((MboSet) getMbo().getMboSet("SEERL01PSVEND"), "SEEPREF", "PRIMEIRA", 1)) {
        throw new MXApplicationException("company", "ocorrenciasEndPreferenciaInvalida");
        }*/

        getMbo().setValue("SEEPSOLVANUM", getMbo().getMboSet("SEERL01CALESC").getMbo(0).getMboSet("SEERL01ESTMAT").getMbo(0).getString("SEEANOLET").concat("/").concat(getMbo().getString("PRESOLVAID")));

        return super.SAVE();
    }
}
