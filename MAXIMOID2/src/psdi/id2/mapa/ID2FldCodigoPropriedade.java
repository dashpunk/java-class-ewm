package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.app.company.FldCompanyCompany;
import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.app.common.FldChangeStatus;
import psdi.app.rfq.virtual.*;

/**
 *
 * @author ID2
 * 
 */
public class ID2FldCodigoPropriedade extends psdi.app.location.FldLocLocation {

    /**
     * Método construtor de ID2PropriedadeRuralAppBean
     * @param mbv 
     * @throws MXException
     */
    public ID2FldCodigoPropriedade(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *
     * Sobrescrita do método validate  BMXZZ0001E
     *<p>
     * Valida se o <b>código da propriedade</b> tem o tamanho <b>igual a 11</b>
     *@throws MXException 
     * @throws RemoteException
     * @since 10/03/2010
     */
    @Override
    public void validate() throws MXException, RemoteException {
        String valor = new String();
        valor = getMboValue().getString();
        super.validate();
        String param[] = {valor};
        if (valor.length() == 11) {
            return;
        } else {
            //busca mensagem do maximo, grupo mensagem e chave mensagem
            throw new MXApplicationException("company", "CodigoPropriedadeInvalido", param);
        }
    }

    /**
     *  Sobrescrita do método <b>initValue</b>
     * <p>
     * Carrega a quantidade total de itens dos relacionamentos: ID2LUCTYPE14,
     * ID2LOC04VWLOC01 e ID2LUCTYPE01, para os campos: Quantidade de
     * Proprietários, Quantidade de Explorações Agropecuárias e Quandidede
     * de Produtores, respectivamente
     * 
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    @Override
    public void initValue() throws MXException, java.rmi.RemoteException {

        super.initValue();

        MboRemote location = getMboValue().getMbo();

        try {

            if (location != null) {
                getMboValue().getMbo().setValue("ID2QTDPROP", (getMboValue().getMbo().getMboSet("ID2LUCTYPE14").count()));
                getMboValue().getMbo().setValue("ID2QTDEXPL", (getMboValue().getMbo().getMboSet("ID2LOC04VWLOC01").count()));
                getMboValue().getMbo().setValue("ID2QTDPROD", (getMboValue().getMbo().getMboSet("ID2LUCTYPE01").count()));
                //getMboValue().getMbo().setValue("CLASSSTRUCTURE.HIERARCHYPATH", location.getString("CLASSIFICATIONID"));
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}