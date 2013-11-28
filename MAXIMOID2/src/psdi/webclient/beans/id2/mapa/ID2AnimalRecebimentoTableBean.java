package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.app.location.LocationSetRemote;
import psdi.id2.mapa.Uteis;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.util.MXApplicationException;

public class ID2AnimalRecebimentoTableBean extends psdi.webclient.beans.receipts.ReceiptsTableBean {

    public ID2AnimalRecebimentoTableBean() {
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
        Uteis.espera("****************  initialize");
        super.initialize();
        int ret = -1;

        try {
            int onde = 0;
            Uteis.espera("Antes--------");
            //System.out.println("---------------------------------- Inicio");

            MboSet linhas = (MboSet) parent.getMbo().getMboSet("PARENTMATRECTRANS");
            Uteis.espera("Depois-------");
            MboSet itens = (MboSet) parent.getMbo().getMboSet("POLINE");
            //MboSet itens = (MboSet) parent.getMbo().getMboSet("SERVRECEIPTINPUT");
            //MboSet itens = (MboSet) parent.getMbo().getMboSet("RECEIPTINPUT");


            linhas.clear();
            Mbo item;
            //System.out.println("---------------------------------- apos Mbos e MboSets");
            Uteis.espera("Depois 1 -------");
            /* aqui
            parent.getMbo().setFieldFlag("STORELOC", psdi.mbo.MboConstants.READONLY, false);
            parent.getMbo().setFieldFlag("ID2PROABA", psdi.mbo.MboConstants.READONLY, true);
            parent.getMbo().setFieldFlag("ID2PROAGL", psdi.mbo.MboConstants.READONLY, true);
            parent.getMbo().setFieldFlag("ID2PROEXP", psdi.mbo.MboConstants.READONLY, true);
            parent.getMbo().setFieldFlag("ID2DESABA", psdi.mbo.MboConstants.READONLY, true);
            parent.getMbo().setFieldFlag("ID2DESAGL", psdi.mbo.MboConstants.READONLY, true);
            parent.getMbo().setFieldFlag("ID2DESEXP", psdi.mbo.MboConstants.READONLY, true);
            Uteis.espera("Depois 2-------");

             */
            //System.out.println("Tipo do local Proced?ncia " + parent.getMbo().getString("id2tipolocal"));
            //System.out.println("Tipo do local Destino " + parent.getMbo().getString("id2tipolocaldest"));

            if (itens != null) {
                Uteis.espera("Total de itens ------------- " + itens.count());

                for (int i = 0; i < itens.count(); i++) {

                    item = (Mbo) itens.getMbo(i);

                    Uteis.espera("Adicionou--------");

                    //System.out.println("antes do add");
                    ret = super.addrow();
                    MboRemote linhaAnimal = getMbo();


                    /*
                     * MATRECEIPTINPUT
                     */
                    /*
                    //System.out.println("---------------------------------- sets " + ++onde);
                    linhaAnimal.setValue("ITEMNUM", item.getString("ITEMNUM"));


                    //linhaAnimal.setValue("TOSTORELOC", item.getString("TOSTORELOC"));
                    linhaAnimal.setValue("TOSTORELOC", item.getString("STORELOC"));

                    //linhaAnimal.setValue("TRANSDATE", item.getDate("TRANSDATE"));
                    //linhaAnimal.setValue("ACTUALDATE", item.getDate("ACTUALDATE"));

                    linhaAnimal.setValue("QUANTITY", item.getInt("QTYREQUESTED"));
                    linhaAnimal.setValue("QTYREQUESTED", item.getInt("RECEIVEDQTY"));

                    //linhaAnimal.setValue("RECEIVEDUNIT", item.getString("RECEIVEDUNIT"));

                    linhaAnimal.setValue("ISSUETYPE", "TRANSFER");

                    linhaAnimal.setValue("PONUM", item.getString("PONUM"));
                    linhaAnimal.setValue("CONVERSION", 1);
                    linhaAnimal.setValue("ENTERBY", item.getString("ENTERBY"));
                    linhaAnimal.setValue("REQUESTBY", item.getString("ENTERBY"));
                    linhaAnimal.setValue("TOTALCURBAL", item.getFloat("ORDERQTY"));

                    //linhaAnimal.setValue("FINANCIALPERIOD", item.getString("FINANCIALPERIOD"));

                    linhaAnimal.setValue("CURRENCYCODE", "R$");
                    linhaAnimal.setValue("EXCHANGERATE", 1);

                    linhaAnimal.setValue("CURRENCYUNITCOST", 0);
                    linhaAnimal.setValue("CURRENCYLINECOST", 0);
                    linhaAnimal.setValue("DESCRIPTION", item.getString("DESCRIPTION"));
                    linhaAnimal.setValue("REMARK", item.getString("REMARK"));
                    linhaAnimal.setValue("FROMSTORELOC", item.getString("TOSTORELOC"));
                    linhaAnimal.setValue("LOADEDCOST", 0);
                    linhaAnimal.setValue("PRORATED", 0);
                    linhaAnimal.setValue("STATUS", "COMP");


                    linhaAnimal.setValue("CURBAL", item.getInt("RECEIVEDQTY"));
                    linhaAnimal.setValue("EXCHANGERATE2", item.getInt("EXCHANGERATE"));
                    linhaAnimal.setValue("LINECOST2", item.getInt("CURRENCYLINECOST")); //0

                    linhaAnimal.setValue("MATRECTRANSID", item.getInt("MATRECTRANSID"));

                    linhaAnimal.setValue("ORGID", "ORG_01");
                    linhaAnimal.setValue("SITEID", "SITE_01");

                    linhaAnimal.setValue("COSTINFO", 0);

                    linhaAnimal.setValue("ENTEREDASTASK", 0);

                    linhaAnimal.setValue("FROMSITEID", "SITE_01");

                    //linhaAnimal.setValue("ISDISTRIBUTED", 0);

                    //linhaAnimal.setValue("POLINEID", parent.getMbo().getMboSet("POLINE_PROXIMO").getMbo(0).getInt("POLINEID") + 1);

                    linhaAnimal.setValue("LINETYPE", "ITEM");
                    linhaAnimal.setValue("ITEMSETID", "CI_01");
                    //TODO melhorar isso - fazer um método para buscar os valores antes dos "."
                    linhaAnimal.setFieldFlag("COMMODITYGROUP", psdi.mbo.MboConstants.CHANGEDBY_USER, true);
                    linhaAnimal.setValue("COMMODITYGROUP", item.getString("ITEMNUM").substring(0, 1));
                    linhaAnimal.setFieldFlag("COMMODITY", psdi.mbo.MboConstants.CHANGEDBY_USER, true);
                    linhaAnimal.setValue("COMMODITY", item.getString("ITEMNUM").substring(0, 3));
                    linhaAnimal.setFieldFlag("COMMODITYGROUP", psdi.mbo.MboConstants.READONLY, false);
                    linhaAnimal.setFieldFlag("COMMODITY", psdi.mbo.MboConstants.READONLY, false);
                    //comentado pois esta somente leitura
                    linhaAnimal.setValue("LANGCODE", "PT");
                    linhaAnimal.setValue("INSPECTEDQTY", 0);
                    linhaAnimal.setValue("POSITEID", "SITE_01");
                    linhaAnimal.setValue("HASLD", 0);
                    linhaAnimal.setValue("DOWNLOADED", 0);
                    linhaAnimal.setValue("TLOAMWLICENSE", 0);

                    linhaAnimal.setValue("TOSITEID", "SITE_01");

                    linhaAnimal.setValue("CONVERSION", 1);

                    linhaAnimal.setValue("MKTPLCITEM", 0);
                     */

                    linhaAnimal.setValue("ITEMNUM", item.getString("ITEMNUM"));



                    //linhaAnimal.setValue("TOSTORELOC", item.getString("TOSTORELOC"));
                    linhaAnimal.setValue("TOSTORELOC", item.getString("STORELOC"));
                    Uteis.espera("******************* local " + ++onde);
                    linhaAnimal.setValue("POLINENUM", item.getInt("POLINENUM"));
                    Uteis.espera("******************* local " + ++onde);
                    linhaAnimal.setFieldFlag("ORDERQTY", psdi.mbo.MboConstants.CHANGEDBY_USER, true);
                    linhaAnimal.setFieldFlag("ORDERQTY", psdi.mbo.MboConstants.READONLY, false);
                    linhaAnimal.setValue("ORDERQTY", item.getInt("ORDERQTY"));
                    //linhaAnimal.setValue("QTYREQUESTED", item.getInt("RECEIVEDQTY"));
                    Uteis.espera("******************* local " + ++onde);



                    //linhaAnimal.setValue("TRANSDATE", item.getDate("TRANSDATE"));
                    //linhaAnimal.setValue("ACTUALDATE", item.getDate("ACTUALDATE"));

                    //linhaAnimal.setValue("QUANTITY", item.getInt("ORDERQTY"));
                    //linhaAnimal.setValue("QTYREQUESTED", item.getInt("QTYTORECEIVE"));

                    //linhaAnimal.setValue("RECEIVEDUNIT", item.getString("RECEIVEDUNIT"));

                    //linhaAnimal.setValue("ISSUETYPE", "ISSUE");
                    linhaAnimal.setValue("DESCRIPTION", item.getString("DESCRIPTION"));
                    linhaAnimal.setValue("REMARK", item.getString("REMARK"));



                    linhaAnimal.setValue("PONUM", item.getString("PONUM"));

                    //linhaAnimal.setValue("CONVERSION", 1);

                    linhaAnimal.setValue("ENTERBY", item.getString("ENTERBY"));

                    //linhaAnimal.setValue("TOTALCURBAL", item.getFloat("ORDERQTY"));

                    //linhaAnimal.setValue("CURBAL", item.getInt("RECEIVEDQTY"));


                    //linhaAnimal.setValue("TOTALCURBAL", item.getFloat("ORDERQTY"));

                    //linhaAnimal.setValue("FINANCIALPERIOD", item.getString("FINANCIALPERIOD"));

                    //linhaAnimal.setValue("CURRENCYCODE", "R$");
                    //Uteis.espera("******************* local "+ ++onde);

                    //linhaAnimal.setValue("EXCHANGERATE", 1);
                    //Uteis.espera("******************* local "+ ++onde);

                    //linhaAnimal.setValue("CURRENCYUNITCOST", 0);
                    //Uteis.espera("******************* local "+ ++onde);
                    //linhaAnimal.setValue("CURRENCYLINECOST", 0);
                    //Uteis.espera("******************* local "+ ++onde);

                    linhaAnimal.setValue("FROMSTORELOC", item.getString("STORELOC"));
                    //Uteis.espera("******************* local "+ ++onde);
                    //linhaAnimal.setValue("LOADEDCOST", 0);
                    //Uteis.espera("******************* local "+ ++onde);
                    //linhaAnimal.setValue("PRORATED", 0);
                    //Uteis.espera("******************* local "+ ++onde);
                    //linhaAnimal.setValue("STATUS", "COMP");
                    //Uteis.espera("******************* local "+ ++onde);

                    linhaAnimal.setValue("MATRECTRANSID", item.getInt("POLINEID"));
                    //Uteis.espera("******************* local "+ ++onde);

                    linhaAnimal.setValue("ORGID", "ORG_01");
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setValue("SITEID", "SITE_01");
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setValue("COSTINFO", 0);
                    //Uteis.espera("******************* local "+ ++onde);

                    //linhaAnimal.setValue("ENTEREDASTASK", 0);
                    //Uteis.espera("******************* local "+ ++onde);

                    linhaAnimal.setValue("FROMSITEID", "SITE_01");
                    //Uteis.espera("******************* local "+ ++onde);

                    //linhaAnimal.setValue("ISDISTRIBUTED", 0);

                    //linhaAnimal.setValue("POLINEID", parent.getMbo().getMboSet("POLINE_PROXIMO").getMbo(0).getInt("POLINEID") + 1);

                    linhaAnimal.setValue("LINETYPE", "ITEM");

                    //Uteis.espera("******************* local "+ ++onde);
                    //linhaAnimal.setValue("EXCHANGERATE2", 1);

                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setValue("ITEMSETID", "CI_01");
                    Uteis.espera("******************* local " + ++onde);
                    //TODO melhorar isso - fazer um método para buscar os valores antes dos "."
                    linhaAnimal.setFieldFlag("COMMODITYGROUP", psdi.mbo.MboConstants.CHANGEDBY_USER, true);
                    linhaAnimal.setValue("COMMODITYGROUP", item.getString("ITEMNUM").substring(0, 1));
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setFieldFlag("COMMODITY", psdi.mbo.MboConstants.CHANGEDBY_USER, true);
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setValue("COMMODITY", item.getString("ITEMNUM").substring(0, 3));
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setFieldFlag("COMMODITYGROUP", psdi.mbo.MboConstants.READONLY, false);
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setFieldFlag("COMMODITY", psdi.mbo.MboConstants.READONLY, false);
                    //comentado pois esta somente leitura
                    linhaAnimal.setValue("LANGCODE", "PT");
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setValue("INSPECTEDQTY", 0);
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setValue("POSITEID", "SITE_01");
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setValue("HASLD", 0);
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setValue("DOWNLOADED", 0);
                    //Uteis.espera("******************* local "+ ++onde);
                    linhaAnimal.setValue("TLOAMWLICENSE", 0);

                    //linhaAnimal.setValue("TOSITEID", "SITE_01");
                    //Uteis.espera("******************* local "+ ++onde);

                    linhaAnimal.setValue("CONVERSION", 1);
                    //Uteis.espera("******************* local "+ ++onde);

                    //linhaAnimal.setValue("MKTPLCITEM", 0);
                    //Uteis.espera("******************* local "+ ++onde);
                    //linhaAnimal.setValue("LINECOST2", 0); //0

                    Uteis.espera("Fim-------");
                }
            }
        } catch (Exception e) {
            Uteis.espera("erro-------" + e.getMessage());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ID2AnimalRecebimentoTableBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ID2AnimalRecebimentoTableBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}




