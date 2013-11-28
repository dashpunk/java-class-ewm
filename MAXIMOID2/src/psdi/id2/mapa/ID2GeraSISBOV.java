package psdi.id2.mapa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import psdi.app.asset.Asset;
import psdi.app.asset.AssetSet;
import psdi.app.pr.PRLineSet;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXException;

public class ID2GeraSISBOV implements ActionCustomClass {

    public ID2GeraSISBOV() {
    	super();
    	System.out.println("############################### ENTROU NO CONSTRUTOR()");
        //super();
    }

    private String repeatString(String valor, int Qtd) {
        int i = 1;
        String ret = "";
        for (i = 1; i <= Qtd; i++) {
            ret = ret + valor;
        }
        return ret;
    }

    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
            throws MXException, java.rmi.RemoteException {
    	
    	System.out.println("############################### ENTROU NO APPLYCUSTOMACTION()");
        Asset animal;
        MboSet numericdomain;
        PRLineSet prlineset;
        AssetSet animaisset;

        String val1;

        int i = 0;
        float ival;

        int digito = 0;

        try {
            prlineset = (PRLineSet) mbo.getMboSet("PRLINE");
            numericdomain = (MboSet) MXServer.getMXServer().getMboSet("NUMERICDOMAIN", mbo.getUserInfo());
            numericdomain.setWhere("domainid='MA_NUDM00'");
            numericdomain.reset();
            animaisset = (AssetSet) MXServer.getMXServer().getMboSet("ASSET", mbo.getUserInfo());

            ival = numericdomain.getMbo(0).getInt("VALUE");

            while (prlineset.getMbo(i) != null) {
            	//Acrescentado a pedido do Tesck
                String itemnum = prlineset.getMbo(i).getString("ITEMNUM");
                System.out.println("############################### PRLINE Count: " + prlineset.count());
                System.out.println("############################### PRLINE posicao: " + i);
                System.out.println("############################### ITEMNUM: " + itemnum);
                
            	if (itemnum.equalsIgnoreCase("EL01") ||	itemnum.equalsIgnoreCase("EL04")) {
                    Uteis.espera("############################## entrou loop 1");
                    if (prlineset.getMbo(i).getInt("ORDERQTY") > 0) {
                        Uteis.espera("############################## entrou loop 2 de ORDERQTY");
                        for (int qtd = 0; qtd < prlineset.getMbo(i).getInt("ORDERQTY"); qtd++) {
                            animal = (Asset) animaisset.add();
                            Uteis.espera("ADD ASSET");

                            val1 = String.valueOf(ival).replaceAll("\\.0", "");
                            Uteis.espera("VAL1 "+val1);
                            Uteis.espera("ANTES GERACAO DIGITO ");
                            digito = calculaDigitoSISBOV("076" + repeatString("0", 11 - val1.length()) + val1);
                            Uteis.espera("DEPOIS GERACAO DIGITO "+digito);

                            Uteis.espera("NUMERO DE CONTROLE "+"076"+repeatString("0",11-val1.length())+val1+digito);
                            animal.setValue("ASSETNUM", "076" + repeatString("0", 11 - val1.length()) + val1 + digito);
                            Uteis.espera("APLICA ITEMNUM");
                            animal.setValue("ITEMNUM", "BOVINO");
                            Uteis.espera("APLICA LOCATION / MASTORELOC");
                            if (prlineset.getMbo(i).getString("MASTORELOC") != null) {
                                animal.setValue("LOCATION", prlineset.getMbo(i).getString("MASTORELOC"));
                            } else {
                                animal.setValue("LOCATION", prlineset.getMbo(i).getString("LOCATION"));
                            }
                            Uteis.espera("APLICA VENDOR");
                            animal.setValue("MANUFACTURER", mbo.getString("VENDOR"));
                            Uteis.espera("APLICA PRNUM");
                            animal.setValue("ID2PRNUM", mbo.getString("PRNUM"));

                            Uteis.espera("ANTES DE SALVAR ANIMAL");
                            animal.setValue("MAITEMTYPE2", itemnum);
                            //animal.save();
                            Uteis.espera("APOS SALVAR");

                            ival = ival + 1;
                        }
                        animaisset.save();
                    }
            	}
                i++;
            }

            Uteis.espera("################################ ANTES DE ATUALIZAR NUMERICDOMAIN");
            Uteis.espera("################################ VALOR do iVAL=" + ival);
            Connection connection = MXServer.getMXServer().getDBManager().getConnection(mbo.getUserInfo().getConnectionKey());
            Statement statement = connection.createStatement();
            statement.executeUpdate("update numericdomain set value=" + ival + " where domainid='MA_NUDM00'");
            Uteis.espera("################################ APOS ATUALIZAR NUMERICDOMAIN");

        } catch (IOException ioex) {
            Uteis.espera(ioex.getMessage());
        } catch (Throwable throwable) {

            Uteis.espera(throwable.getMessage());
        }

    }

    private int calculaDigitoSISBOV(String valor) {
        int m = 2;
        int ret = 0;
        //Uteis.espera("---------------------------------"+valor);
        for (int i = valor.length() - 1; i >= 0; i--) {
            //Uteis.espera("--------------------------------- valor de i "+i);
            //Uteis.espera("--------------------------------- valor de m "+m);
            //Uteis.espera("--------------------------------- numero de i "+valor.charAt(i));
            //Uteis.espera("--------------------------------- ret antes "+ret);
            ret = ret + Integer.parseInt(valor.charAt(i) + "") * m;
            //Uteis.espera("--------------------------------- ret depois "+ret);
            m++;
        }
        //Uteis.espera("--------------------------------- ret antes do mod "+ret);
        ret = (ret * 10) % 11;
        //Atualizado por solicita��o da Karina com atuoriza��o do Jess�.
        if (ret > 9) ret = 0;        
        //Uteis.espera("--------------------------------- ret apos o mod "+ret);
        return ret;
    }
};





