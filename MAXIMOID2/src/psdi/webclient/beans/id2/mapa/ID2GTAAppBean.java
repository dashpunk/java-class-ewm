package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.app.location.LocationSetRemote;
import psdi.id2.mapa.Uteis;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.*;
import psdi.webclient.system.controller.*;

public class ID2GTAAppBean extends psdi.webclient.beans.po.POAppBean {

    public ID2GTAAppBean() {
    }

    public int SAVE() throws MXException, RemoteException {
    Uteis.espera("***************SAVE() "+getMbo().getString("STATUS"));
        if (getMbo().getString("STATUS").equals("CRIAR") || getMbo().getString("STATUS").equals("GRAVADA")) {
            Uteis.espera("***************Antes de chamar");
            getMbo().setValue("ID2CODBARRA", gerarCodigoBarras());

            Uteis.espera("***************Após de chamar");

            getMbo().setFieldFlag("STORELOC", psdi.mbo.MboConstants.READONLY, false);

            if (getMbo().getString("id2tipolocal").equals("02")) {
                getMbo().setValue("STORELOC", getMbo().getString("ID2PROABA"));
            } else if (getMbo().getString("id2tipolocal").equals("03")) {
                getMbo().setValue("STORELOC", getMbo().getString("ID2PROAGL"));
            } else if (getMbo().getString("id2tipolocal").equals("04")) {
                getMbo().setValue("STORELOC", getMbo().getString("ID2PROEXP"));
            }

            int i = 0;
            do {
                MboRemote linhaAnimal = getMbo().getMboSet("POLINE").getMbo(i);

                if (getMbo().getString("id2tipolocaldest").equals("02")) {
                    linhaAnimal.setValue("STORELOC", getMbo().getString("ID2DESABA"));
                } else if (getMbo().getString("id2tipolocaldest").equals("03")) {
                    linhaAnimal.setValue("STORELOC", getMbo().getString("ID2DESAGL"));
                } else if (getMbo().getString("id2tipolocaldest").equals("04")) {
                    linhaAnimal.setValue("STORELOC", getMbo().getString("ID2DESEXP"));
                }

                if (linhaAnimal == null) {
                    break;
                }
                i++;
            } while (true);
        }

        return super.SAVE();
    }

    /**
     * Adiciona os itens com saldo
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    public void carregarItens() throws MXException, java.rmi.RemoteException {
        int onde = 1;

        MboSet linhas = (MboSet) getMbo().getMboSet("POLINE");
        MboSet itens = (MboSet) getMbo().getMboSet("ID2PROEXP_SALDO");
        Mbo linha;
        Mbo item;


        //remove linhas existentes
        //linhas.reset();

        if (itens != null) {
            for (int i = 0; i < itens.count(); i++) {
                item = (Mbo) itens.getMbo(i);

                //caso seja da mesma commodity
                if (item.getString("ITEMNUM").substring(0, 3).equals(getMbo().getString("COMMODITY"))) {

                    //Uteis.espera("antes do add");
                    //TODO false esse
                    linha = (Mbo) linhas.add();
                    //Uteis.espera("apos do add");

                    linha.setValue("PONUM", getMbo().getString("PONUM"));
                    linha.setValue("ITEMNUM", item.getString("ITEMNUM"));
                    //Código da Exploração
                    linha.setValue("STORELOC", getMbo().getString("ID2PROEXP"));
                    //Quantidade Emitida
                    linha.setValue("ORDERQTY", 1);
                    //Unidade
                    linha.setValue("ORDERUNIT", "CADA");
                    //Custo
                    linha.setValue("UNITCOST", 0);
                    linha.setValue("RECEIVEDUNITCOST", 0);
                    linha.setValue("RECEIVEDTOTALCOST", 0);
                    linha.setValue("REJECTEDQTY", 0);
                    linha.setValue("ENTERDATE", new Date());
                    //TODO Buscarquem ta logado
                    linha.setValue("ENTERBY", "MAXADMIN");
                    //TODO fata esse
                    linha.setValue("DESCRIPTION", "...");
                    //TODO Buscarquem ta logado
                    linha.setValue("REQUESTEDBY", "MAXADMIN");
                    linha.setValue("ISSUE", 0);
                    linha.setValue("POLINENUM", i);
                    linha.setValue("TAXED", 0);
                    linha.setValue("CHARGESTORE", 0);
                    linha.setValue("LINECOST", 0);
                    linha.setValue("TAX1", 0);
                    linha.setValue("TAX2", 0);
                    linha.setValue("TAX3", 0);
                    linha.setValue("RECEIPTREQD", 0);
                    linha.setValue("TAX4", 0);
                    linha.setValue("TAX5", 0);
                    linha.setValue("CATEGORY", "ESTOC");
                    linha.setValue("LOADEDCOST", 0);
                    linha.setValue("PRORATESERVICE", 0);
                    linha.setValue("RECEIPTSCOMPLETE", 0);
                    linha.setValue("INSPECTIONREQUIRED", 0);
                    linha.setValue("PRORATECOST", 0);
                    //TODO proximo id
                    //TODO Relacionamento POLINE_PROXIMO
                    linha.setValue("POLINEID", getMbo().getMboSet("POLINE_PROXIMO").getMbo(0).getInt("POLINEID") + 1);
                    //Uteis.espera(String.valueOf(getMbo().getMboSet("POLINE_PROXIMO").getMbo(0).getInt("POLINEID")));
                    linha.setValue("LINECOST2", 0);
                    linha.setValue("SITEID", "SITE_01");
                    linha.setValue("ORGID", "ORG_01");
                    linha.setValue("ISDISTRIBUTED", 0);
                    //somente leitura?
                    //linha.setValue("ENTEREDASTASK", 0);
                    linha.setValue("LINETYPE", "ITEM");
                    linha.setValue("ITEMSETID", "CI_01");
                    //TODO melhorar isso - fazer um método para buscar os valores antes dos "."
                    linha.setFieldFlag("COMMODITYGROUP", psdi.mbo.MboConstants.CHANGEDBY_USER, true);
                    linha.setValue("COMMODITYGROUP", item.getString("ITEMNUM").substring(0, 1));
                    linha.setFieldFlag("COMMODITY", psdi.mbo.MboConstants.CHANGEDBY_USER, true);
                    linha.setValue("COMMODITY", item.getString("ITEMNUM").substring(0, 3));
                    linha.setFieldFlag("COMMODITYGROUP", psdi.mbo.MboConstants.READONLY, false);
                    linha.setFieldFlag("COMMODITY", psdi.mbo.MboConstants.READONLY, false);
                    //comentado pois esta somente leitura
                    linha.setValue("TOSITEID", "SITE_01");
                    linha.setValue("LANGCODE", "PT");
                    linha.setValue("CONVERSION", 1);
                    linha.setValue("HASLD", 0);
                    linha.setValue("MKTPLCITEM", 0);
                    //linhas.commit();
                    //linhas.saveMbos();
                }
            }
        }
    }

    public String gerarCodigoBarras() {
        int onde = 0;
        Uteis.espera("***************Início gerarCodigoBarras " + ++onde);
        String codigoBarras = "";
        Uteis.espera("***************gerarCodigoBarras " + ++onde);
        try {
            String NUMGTA = "000000";
            String SERIE;
            String UF = getMbo().getString("ID2LOCUF");
            String DATAEMISSAO = ("00").substring(0, ("00").length() - (getMbo().getMboSet("ID2EMISSAO").getDate("STATUSDATE").getDate() + "").length()) + getMbo().getMboSet("ID2EMISSAO").getDate("STATUSDATE").getDate() + ("00").substring(0, ("00").length() - (getMbo().getMboSet("ID2EMISSAO").getDate("STATUSDATE").getMonth() + "").length()) + getMbo().getMboSet("ID2EMISSAO").getDate("STATUSDATE").getMonth() + ((getMbo().getMboSet("ID2EMISSAO").getDate("STATUSDATE").getYear() + 1900) + "");
            int iQTD = new Double(getMbo().getMboSet("POLINE").sum("ORDERQTY")).intValue();
            String QTDANIMAL = ("0000000").substring(0, ("0000000").length() - (iQTD + "").length()) + iQTD;
            String ESPECIE = "7"; //getMbo().getString("COMMODITY");
            String PROPRIEDADE = getMbo().getString("ID2LOCPROEXP.ID2VWLOC01.LOCATION").substring(getMbo().getMboSet("").getString("LOCATION").length() - 10, getMbo().getMboSet("").getString("LOCATION").length());
            String MUNICIPIO = getMbo().getString("ID2LOCDESEXP.ID2VWLOC01.ID2CODMUN").substring(getMbo().getMboSet("").getString("ID2CODMUN").length() - 6, getMbo().getMboSet("").getString("ID2CODMUN").length());
            String DIGITO1;
            String DIGITO2;
            String DIGITO3;

            char cSerie = getMbo().getString("ID2SERIE").charAt(0);
            int iSerie = (cSerie - 65) + 1;
            if (iSerie < 10) {
                SERIE = "0" + iSerie;
            } else {
                SERIE = "" + iSerie;
            }

            NUMGTA = NUMGTA.substring(0, (NUMGTA.length()) - getMbo().getString("ID2NUMGTA").length()) + getMbo().getString("ID2NUMGTA");

            String verificador = UF + SERIE + NUMGTA;

            int j = 2;
            int res = 0;

            Integer digito;

            for (int i = verificador.length() - 1; i >= 0; i--) {
                digito = new Integer(verificador.substring(i, i + 1));
                res = res + (digito.intValue() * j);
                j++;
                if (j > 9) {
                    j = 2;
                }
            }
            DIGITO1 = ((11 - (res % 11)) > 9 ? 0 : 11 - (res % 11)) + "";

            verificador = UF + SERIE + NUMGTA + DIGITO1 + DATAEMISSAO + ESPECIE + QTDANIMAL;

            j = 2;
            res = 0;

            for (int i = verificador.length() - 1; i >= 0; i--) {
                digito = new Integer(verificador.substring(i, i + 1));
                res = res + (digito.intValue() * j);
                j++;
                if (j > 9) {
                    j = 2;
                }
            }
            DIGITO2 = ((11 - (res % 11)) > 9 ? 0 : 11 - (res % 11)) + "";

            verificador = UF + SERIE + NUMGTA + DIGITO1 + DATAEMISSAO + ESPECIE + QTDANIMAL + DIGITO2 + PROPRIEDADE + MUNICIPIO;

            j = 2;
            res = 0;

            for (int i = verificador.length() - 1; i >= 0; i--) {
                digito = new Integer(verificador.substring(i, i + 1));
                res = res + (digito.intValue() * j);
                j++;
                if (j > 9) {
                    j = 2;
                }
            }
            DIGITO3 = ((11 - (res % 11)) > 9 ? 0 : 11 - (res % 11)) + "";

            codigoBarras = verificador + DIGITO3;

        } catch (MXException mxe) {
        } catch (RemoteException re) {
        }
        return codigoBarras;
    }
}
