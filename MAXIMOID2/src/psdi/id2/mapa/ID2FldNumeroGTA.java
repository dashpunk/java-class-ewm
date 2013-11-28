package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.id2.Validar;
import psdi.id2.Uteis;

/**
 * Título: Validar
 * Descrição: Classe auxiliar para o mbovalue ID2NUMGTA
 * Empresa: ID² Tecnologia
 *
 * @author Ricardo S Gomes
 * @since 28 de Maio de 2010
 * @version 1.0
 */
public class ID2FldNumeroGTA extends MboValueAdapter {

    /**
     * Método construtor de ID2FldNumeroGTA
     * @param mbv 
     * @throws MXException
     */
    public ID2FldNumeroGTA(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *
     * Sobrescrita do método validate  
     *<p>
     * Valida se o <b>código da GTA</b> tem o tamanho <b>igual a 6</b>
     *@throws MXException 
     * @throws RemoteException
     * @since 28 de Maio de 2010
     */
    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        //Uteis.espera("********************* Antes de Testar ID2FldNumeroGta: " + getMboValue().getString());
        if ((!Validar.tamnhoIgual(Uteis.getApenasNumeros(getMboValue().getString()), 6)) || (getMboValue().getString().equals("000000"))) {
            throw new MXApplicationException("company", "CodigoGTAInvalido");
        }

    }
}
