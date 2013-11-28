package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author Ricardo S Gomes App: MTAPFISEVE
 */
public class FiscalizacaoEventosFiscalizacaoTableBean extends DataBean {

    public FiscalizacaoEventosFiscalizacaoTableBean() {
    }

    @Override
    public int addrow() throws MXException {

        System.out.println("--- FiscalizacaoEventosFiscalizacaoTableBean addrow N");

        int retorno = super.addrow();

        MboRemote mboItem;
        MboRemote mboItemDestino;

//        System.out.println("--- antes de recuperar os valores ");
        try {
//            System.out.println(">>> itens count  " + app.getDataBean("MAINRECORD").getMbo().getMboSet("MXRL01VW02ITEFIS").count());
            for (int i = 0; ((mboItem = app.getDataBean("MAINRECORD").getMbo().getMboSet("MXRL01VW02ITEFIS").getMbo(i)) != null); i++) {
//                System.out.println(">>> itens i " + i);
                mboItemDestino = getMbo().getMboSet("MTVW02EVOSER").add();

                mboItemDestino.setValue("MTTBFISVISID", getMbo().getInt("MTTBFISVISID"), MboConstants.NOACCESSCHECK);
                mboItemDestino.setValue("mttbitefisid", mboItem.getInt("mttbitefisid"), MboConstants.NOACCESSCHECK);
                //TODO: descomentar a linha abaixo assim que for criado o atributo no banco
                mboItemDestino.setValue("mtcoditem", mboItem.getString("mtcoditem"), MboConstants.NOACCESSCHECK);
                mboItemDestino.setValue("description", mboItem.getString("description"), MboConstants.NOACCESSCHECK);
                mboItemDestino.setValue("mtitemcla", mboItem.getString("mtitemcla"), MboConstants.NOACCESSCHECK);
                mboItemDestino.setValue("mtitemsub", mboItem.getString("mtitemsub"), MboConstants.NOACCESSCHECK);

            }
//            String valorA = app.getDataBean("MAINRECORD").getMbo().getString("dsnumpro");
//            Long valorB = app.getDataBean("MAINRECORD").getMbo().getLong("mttbfisconid");
//            String valorC = app.getDataBean("MAINRECORD").getMbo().getString("UPCODPROG");
//            System.out.println("--- valores ");
//            System.out.println(valorA);
//            System.out.println(valorB);
//            System.out.println("--- antes de setar o valor");
//            getMbo().setValue("mtnumpro", valorA, MboConstants.NOACCESSCHECK);
//            getMbo().setValue("mttbfisconid", valorB, MboConstants.NOACCESSCHECK);
//            getMbo().setValue("UPCODPROG", valorC, MboConstants.NOACCESSCHECK);
//            System.out.println("--- apos setar o valor");

//            System.out.println("--- count na meta " + app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01META").count());
            if (app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01META").count() > 0) {
//                System.out.println("--- atribuir valor");
                getMbo().setValue("MTMETAID", app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01META").getMbo(0).getLong("ITMETAID"));
//                System.out.println("--- valor atribuido " + getMbo().getLong("MTMETAID"));
            }



            /*
             * Para alimentar tabela "Critérios":
             *
             * -Relacionamento: MXRL01TBAVAL -campos Origem:
             * TBAVALID,DESCRIPTION -Campos Destino: TBAVALID,AVALCRIT
             * (RESPECTIVAMENTE)
             *
             */

            MboRemote mbo;
            MboRemote mbob;

            //Critério
//            System.out.println("---criterio " + app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01TBAVAL").count());
            for (int i = 0; ((mbo = app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01TBAVAL").getMbo(i)) != null); i++) {
//                System.out.println("---i " + i);
//                System.out.println("---v1 " + mbo.getLong("TBAVALID"));
//                System.out.println("---v2 " + mbo.getString("DESCRIPTION"));
                mbob = getMbo().getMboSet("MXRL01FISVISAVAL").add();
                mbob.setValue("TBAVALID", mbo.getLong("TBAVALID"));
                mbob.setValue("AVALCRIT", mbo.getString("DESCRIPTION"));
                mbob.setValue("MTTBFISVISID", getMbo().getLong("MTTBFISVISID"));
            }

            /*
             *
             * Para alimentar tabela "Plano de Trabalho":
             *
             * -Relacionamento: MXRL01TBPLAAPL -campos Origem:
             * TBPLAAPLID,DESCRIPTION,DSQUAN,DSUNI -Campos Destino:
             * TBPLAAPLID,PLADESC,PLAQUAPRO,PLAUNI (RESPECTIVAMENTE)
             *
             */
//            System.out.println("---Plano de Trabalho " + app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01TBPLAAPL").count());
            for (int i = 0; ((mbo = app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01TBPLAAPL").getMbo(i)) != null); i++) {
//                System.out.println("---i " + i);
//                System.out.println("---v1 " + mbo.getLong("TBPLAAPLID"));
//                System.out.println("---v2 " + mbo.getString("DESCRIPTION"));
//                System.out.println("---v3 " + mbo.getString("DSQUAN"));
//                System.out.println("---v4 " + mbo.getString("DSUNI"));
                mbob = getMbo().getMboSet("MXRL01FISVISPLA").add();
//                mbob.setValue("TBPLAAPLID", mbo.getLong("TBPLAAPLID"));
                mbob.setValue("PLADESC", mbo.getString("DESCRIPTION"));
                mbob.setValue("PLAQUAPRO", mbo.getString("DSQUAN")); //ta como aln
                mbob.setValue("PLAUNI", mbo.getString("DSUNI"));
                mbob.setValue("MTTBFISVISID", getMbo().getLong("MTTBFISVISID"));
            }


            String tipo = app.getDataBean("MAINRECORD").getMbo().getString("MTTIPACAO");
//            System.out.println("___ tipo " + tipo);
//            System.out.println("___ tipo " + tipo.substring(0, 4));
            if (tipo.substring(0, 4).equalsIgnoreCase("FISC")) {
                MboRemote mboMeta;
                MboRemote mboMetaDestino;
                MboRemote mboEtapa;
                MboRemote mboEtapaDestino;
                MboRemote mboParcela;
                MboRemote mboParcelaDestino;
                boolean meta = false;
                boolean etapa = false;
                boolean parcela = false;
                
                int id = getMbo().getInt("mttbfisvisid");

                int aonde = 0;
//                System.out.println("... count em meta " + app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01META").count());
                for (int i = 0; ((mboMeta = app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01META").getMbo(i)) != null); i++) {
//                    System.out.println("... i em meta " + i);
                    meta = true;
                    aonde = 0;
                    mboMetaDestino = getMbo().getMboSet("MXRL01FISVISMETA").add();
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("itmetaid", mboMeta.getLong("itmetaid"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("dsnummeta", mboMeta.getString("dsnummeta"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("description", mboMeta.getString("description"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("dsesp", mboMeta.getString("dsesp"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("dsnumpro", mboMeta.getString("dsnumpro"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("itprpid", mboMeta.getLong("itprpid"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("amvalo", mboMeta.getDouble("amvalo"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("amvalmet", mboMeta.getDouble("amvalmet"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("dtini", mboMeta.getString("dtini"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("dtinimet", mboMeta.getDate("dtinimet"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("dtter", mboMeta.getString("dtter"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboMetaDestino.setValue("mttbfisvisid", id);
                    //System.out.println(">>> aonde " + ++aonde);

//                    System.out.println("... count em etapa " + mboMeta.getMboSet("RL01TBETA").count());

                    for (int j = 0; ((mboEtapa = mboMeta.getMboSet("RL01TBETA").getMbo(j)) != null); j++) {
//                        System.out.println("... j em etapa " + j);
                        etapa = true;
                        aonde = 0;
                        mboEtapaDestino = mboMetaDestino.getMboSet("MXRL01FISVISETAP").add();
                        mboEtapaDestino.setValue("itmetaid", mboEtapa.getLong("itmetaid"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("itnummeta", mboEtapa.getLong("itnummeta"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("dsnummeta", mboEtapa.getString("dsnumeta"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("description", mboEtapa.getString("description"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("dsesp", mboEtapa.getString("dsesp"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("amvalo", mboEtapa.getDouble("amvalo"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("amvaltxt", mboEtapa.getDouble("amvaltxt"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("dtini", mboEtapa.getDate("dtini"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("dtinieta", mboEtapa.getDate("dtinieta"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("dtter", mboEtapa.getDate("dter"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("dttereta", mboEtapa.getDate("dttereta"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboEtapaDestino.setValue("idproposta", mboEtapa.getLong("idproposta"));
                        //System.out.println(">>> aonde " + ++aonde);
                        mboMetaDestino.setValue("mttbfisvisid", id);
                        //System.out.println(">>> aonde " + ++aonde);
                    }
                }

//                System.out.println("... count em parcela " + app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01TBPAR").count());
                for (int i = 0; ((mboParcela = app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01TBPAR").getMbo(i)) != null); i++) {
//                    System.out.println("... i em parcela " + i);
                    parcela = true;
                    aonde = 0;
                    mboParcelaDestino = getMbo().getMboSet("MXRL01FISVISPAR").add();
                    mboParcelaDestino.setValue("itidpar", mboParcela.getLong("itidpar"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboParcelaDestino.setValue("dsnumpar", mboParcela.getString("dsnumpar"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboParcelaDestino.setValue("description", mboParcela.getString("description"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboParcelaDestino.setValue("dsnumpro", mboParcela.getString("dsnumpro"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboParcelaDestino.setValue("itprpid", mboParcela.getLong("itprpid"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboParcelaDestino.setValue("uptipo", mboParcela.getString("uptipo"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboParcelaDestino.setValue("upmes", mboParcela.getString("upmes"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboParcelaDestino.setValue("upano", mboParcela.getString("upano"));
                    //System.out.println(">>> aonde " + ++aonde);
                    mboParcelaDestino.setValue("amvalo", mboParcela.getDouble("amvalo"));
                    System.out.println(">>> aonde " + ++aonde);
//                    System.out.println("*** id "+id);
                    mboParcelaDestino.setValue("mttbfisvisid", id);
//                    System.out.println(">>> aonde " + ++aonde);
                }


            }

        } catch (RemoteException ex) {
            ex.getStackTrace();
            Logger.getLogger(FiscalizacaoEventosFiscalizacaoTableBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }
}
