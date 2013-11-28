package br.inf.id2.mapa.field;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;
import java.util.Date;
import psdi.mbo.*;

/**
 * ID2 Tecnologia S.A.
 * 
* <h4>Descrição da Classe</h4> - Classe criada para atender a demanda de
 * aplicar uma máscara no campo número do protocolo.
 * 
*
 * <h4>Notas</h4> - A classe deve ser aplicada no atributo locationsid do objeto id2vwlo05
 * 
*
 * <h4>Referências</h4> - Classe aplicada na tela macalen
 * 
* <h4>Revisões</h4> - Revisão 1.0 -
 *
 * @author Ricardo S Gomes: Classe inicialmente criada.
 * 
*
 * @since 17/05/2012 10:39
 * @version 1.0
 * 
*
 */
public class Id2vwloc05Id2numprotocolo extends MboValueAdapter {

    public Id2vwloc05Id2numprotocolo(MboValue mbv) {
        super(mbv);
        System.out.println("Id2vwloc05Id2numprotocolo");
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        int ano = Data.getAno(new Date());


        System.out.println("__ANO  = " + ano);
        MboSet mboSet;
        mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC05", getMboValue().getMbo().getUserInfo());

        mboSet.setWhere("where cast(substr(coalesce(ID2NUMPROTOCOLO, \'SVT.000000001.2012\'),5,9) as integer)-1 not in "
                + "(select cast(substr(coalesce(x.ID2NUMPROTOCOLO, \'SVT.000000001.2012\'),5,9) as integer) from id2vwloc05 x) and substr(coalesce(ID2NUMPROTOCOLO, 'SVT.000000001.2012'),15,4) = \'" + ano + "\'");
        mboSet.reset();

        System.out.println("___ mboSet count " + mboSet.count());

        MboRemote mbo;
        for (int i = 0; ((mbo = mboSet.getMbo(i)) != null); i++) {
            System.out.println("___ i " + i);
            System.out.println("___ ID2NUMPROTOCOLO " + mbo.getString("ID2NUMPROTOCOLO"));
            if (!mbo.getString("ID2NUMPROTOCOLO").equalsIgnoreCase("SVT.000000001." + ano)) {
                atribuirValorComMascara(mbo.getString("ID2NUMPROTOCOLO"), -1);
                return;
            }
        }
        mboSet.setWhere("where ID2NUMPROTOCOLO = max(ID2NUMPROTOCOLO)and substr(coalesce(ID2NUMPROTOCOLO, 'SVT.000000001.2012'),15,4) = \'" + ano + "\'");
        mboSet.reset();
        System.out.println("___ mboSet count " + mboSet.count());
        if (mboSet.count() == 0) {
            System.out.println("___ if 1");
            atribuirValorComMascara("SVT.000000001." + ano, 0);
            return;
        }
        System.out.println("___ > 0 " + mboSet.getMbo(0).getString("ID2NUMPROTOCOLO"));
        atribuirValorComMascara(mboSet.getMbo(0).getString("ID2NUMPROTOCOLO"), +1);

    }

    private void atribuirValorComMascara(String valor, int fator) throws MXException, RemoteException {
        System.out.println("___atribuirValorComMascara");
        System.out.println("___ valor " + valor);
        System.out.println("___ fator " + fator);
        int sequencia = Integer.valueOf(valor.substring(4, 13)) + fator;
        System.out.println("___ sequencia " + sequencia);

        valor = "SVT." + Uteis.adicionaValorEsquerda(String.valueOf(sequencia), "0", 9) + "." + Data.getAno(new Date());
        System.out.println("___ novo valor"+valor);
        getMboValue("ID2NUMPROTOCOLO").setValue(valor, MboConstants.NOACCESSCHECK);
        System.out.println("___ FIM");
        
    }
}