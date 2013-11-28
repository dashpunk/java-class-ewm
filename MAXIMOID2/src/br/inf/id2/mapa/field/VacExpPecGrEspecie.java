package br.inf.id2.mapa.field;

import br.inf.id2.common.util.Executa;
import br.inf.id2.common.util.Uteis;
import java.rmi.RemoteException;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 * 
 */
public class VacExpPecGrEspecie extends MboValueAdapter {

    public VacExpPecGrEspecie(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        //System.out.print("---- inicio VacExpPecGrEspecie");

        try {
            //System.out.print("------ MboSetOrigem");
            MboSet mboSetOrigem = (MboSet) getMboValue().getMbo().getMboSet("MACALENESTRATP");

            MboSet mboSetOrigemSaldo = (MboSet) getMboValue().getMbo().getMboSet("INVBALANCES");

            mboSetOrigem.setOrderBy("ITEMNUM");
            //System.out.print("------ MboSetDestino");
            MboSet mboSetDestino = (MboSet) getMboValue().getMbo().getMboSet("MATBESTVAC");
            mboSetDestino.deleteAndRemoveAll();
            //System.out.print("------ antes de adiciona");
            String vacinaId = "#".concat(getMboValue().getMbo().getString("ID2VACEXPPECID"));
            String location = "#".concat(getMboValue().getMbo().getString("ID2CODEXPPEC"));

            Executa.adiciona(mboSetOrigem, mboSetDestino,
                    new Object[]{"ITEMNUM", location, vacinaId},
                    new String[]{"ITEMNUM", "LOCATION", "VACINAID"}); //destino

            //System.out.print("------ apos adiciona");
            mboSetDestino.setOrderBy("ITEMNUM");

            //System.out.print("-----------Iniciando atualizacao de dados");

            int curbal = 0;

            for (int i = 0; i < mboSetDestino.count(); i++) {
                for (int j = 0; j < mboSetOrigemSaldo.count(); j++) {

                    //System.out.print("----------->a ITEMNUM " + mboSetOrigemSaldo.getMbo(j).getString("ITEMNUM"));
                    //System.out.print("----------->b ITEMNUM " + mboSetDestino.getMbo(i).getString("ITEMNUM"));

                    if (mboSetOrigemSaldo.getMbo(j).getString("ITEMNUM").equals(mboSetDestino.getMbo(i).getString("ITEMNUM"))) {
                        //System.out.println("------> combinacao encontrada--------------");
                        curbal = mboSetOrigemSaldo.getMbo(j).getInt("CURBAL");
                    }

                }
                //System.out.println("----------> atualizando atributo");
                mboSetDestino.getMbo(i).setValue("CURBAL", curbal);
                //System.out.println("----------> atributo atualizado");
                curbal = 0;
            }

            for (int i = mboSetDestino.count() - 1; i >= 0; i--) {
                //System.out.println("----- value of curbal " + mboSetDestino.getMbo(i).getInt("CURBAL"));
                if (mboSetDestino.getMbo(i).getInt("CURBAL") == 0) {
                    //System.out.println("----- delete()");
                    mboSetDestino.getMbo(i).delete();
                }

            }
        } catch (Exception ex) {
            System.out.print("*************** Exception em VacExpPecGrEspecie = " + ex.getMessage());
        }

        //System.out.print("--- final de VacExpPecGrEspecie");
        super.validate();

    }
}
