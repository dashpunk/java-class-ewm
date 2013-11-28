package br.inf.id2.seedf.field;

import br.inf.id2.common.util.Executa;
import psdi.mbo.*;


import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class SeeTbAmbSeeArea extends MboValueAdapter {

    public SeeTbAmbSeeArea(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    public void validate() throws MXException, RemoteException {

        super.validate();
        int onde = 0;
        //System.out.println("Inicio ++++++++++++++++++++++ " + ++onde);

        //System.out.println("++++++++++++++++++++++ " + ++onde);
        //System.out.println("++++-=-=-=-=-=-= valor ADAPTADO: " + getMboValue().getMbo().getString("SEECLAAMB").equals("ADAPTADO"));
        if (getMboValue().getMbo().getString("SEECLAAMB").equals("ADAPTADO")) {
            //System.out.println("++++++++++++++++++++++ " + ++onde);
            if (getMboValue().getMbo().getMboSet("SEERL01AMB") != null) {
                //System.out.println("++++++++++++++++++++++ " + ++onde);
                if (getMboValue().getMbo().getMboSet("SEERL01AMB").count() > 0) {
                    //System.out.println("++++++++++++++++++++++ " + ++onde);
                    if (getMboValue().getMbo().getMboSet("SEERL02AMB") != null) {
                        //System.out.println("++++++++++++++++++++++ " + ++onde);
                        if (getMboValue().getMbo().getMboSet("SEERL02AMB").count() > 0) {
                            //System.out.println("*******************Soma dos irmãos: " + Executa.somaValor(getMboValue().getMbo().getMboSet("SEERL02AMB"), "SEEAREA", "ASSETNUM", getMboValue().getMbo().getString("ASSETNUM")));
                            //System.out.println("*******************Valor ambiente anterior: " + getMboValue().getMbo().getMboSet("SEERL01AMB").getMbo(0).getDouble("SEEAREA"));
                            Double valorTotal = Executa.somaValor(getMboValue().getMbo().getMboSet("SEERL02AMB"), "SEEAREA", "ASSETNUM", getMboValue().getMbo().getString("ASSETNUM"));
                            for (int j = 0; j < getMboValue().getMbo().getThisMboSet().count(); j++) {

                                if (getMboValue().getMbo().getThisMboSet().getMbo(j).getString("ASSETNUM").equals(getMboValue().getMbo().getString("ASSETNUM"))) {
                                    if (getMboValue().getMbo().getThisMboSet().getMbo(j).getInt("SEEAMBANTID") == getMboValue().getMbo().getInt("SEEAMBANTID")) {
                                        //System.out.println("++++ En "+getMboValue().getMbo().getThisMboSet().getMbo(j).getDouble("SEEAREA"));

                                        valorTotal += getMboValue().getMbo().getThisMboSet().getMbo(j).getDouble("SEEAREA");
                                    }
                                }

                            }
                            //System.out.println("000000000000000000000 valorTotal: "+valorTotal);
                            //System.out.println("000000000000000000000 valorAmbiente: "+getMboValue().getMbo().getMboSet("SEERL01AMB").getMbo(0).getDouble("SEEAREA"));
                            if (valorTotal > getMboValue().getMbo().getMboSet("SEERL01AMB").getMbo(0).getDouble("SEEAREA")) {
                                throw new MXApplicationException("company", "somaAreaAmbienteInvalida");
                            }
                        }
                    }
                }
            }

        }
        //System.out.println("Fim ++++++++++++++++++++++ " + ++onde);
        Double valorAtual = getMboValue().getDouble();
        Double resultado = valorAtual / 1.2;

        //System.out.println("*************************************");
        //System.out.println("OfertaVaga************** valorAtual " + valorAtual);
        //System.out.println("OfertaVaga************** resultado " + resultado);
        //System.out.println("*************************************");

        getMboValue().getMbo().setValue("SEECAPFIS", resultado.intValue());


    }
}
