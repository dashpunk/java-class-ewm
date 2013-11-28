package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.id2.Validar;
import psdi.id2.mapa.Extenso;
import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.po.POAppBean;
import br.inf.id2.common.util.Data;
import psdi.mbo.*;

public class GtaEmissaoAppBean extends POAppBean {

    public GtaEmissaoAppBean()
            throws MXException {
    }

    public void carregarItensEmEmissao() throws MXException, RemoteException {
        MboSet linhas = (MboSet) getMbo().getMboSet("POLINE");
        MboSet itens = (MboSet) getMbo().getMboSet("ID2PROEXP_SALDO");
        if (itens != null) {
            for (int i = 0; i < itens.count(); i++) {
                Mbo item = (Mbo) itens.getMbo(i);
                if (item.getString("ITEMNUM").substring(0, 3).equals(getMbo().getString("COMMODITY"))) {
                    Mbo linha = (Mbo) linhas.add();
                    linha.setValue("PONUM", getMbo().getString("PONUM"));
                    linha.setValue("ITEMNUM", item.getString("ITEMNUM"));
                    linha.setValue("STORELOC", getMbo().getString("ID2PROEXP"));
                    linha.setValue("ORDERQTY", 1);
                    linha.setValue("ORDERUNIT", "CADA");
                    linha.setValue("UNITCOST", 0);
                    linha.setValue("RECEIVEDUNITCOST", 0);
                    linha.setValue("RECEIVEDTOTALCOST", 0);
                    linha.setValue("REJECTEDQTY", 0);
                    linha.setValue("ENTERDATE", new Date());
                    linha.setValue("ENTERBY", "MAXADMIN");
                    linha.setValue("DESCRIPTION", "...");
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
                    linha.setValue("POLINEID", getMbo().getMboSet("POLINE_PROXIMO").getMbo(0).getInt("POLINEID") + 1);
                    linha.setValue("LINECOST2", 0);
                    linha.setValue("SITEID", "SITE_01");
                    linha.setValue("ORGID", "ORG_01");
                    linha.setValue("ISDISTRIBUTED", 0);
                    linha.setValue("LINETYPE", "ITEM");
                    linha.setValue("ITEMSETID", "CI_01");
                    linha.setFieldFlag("COMMODITYGROUP", 16L, true);
                    linha.setValue("COMMODITYGROUP", item.getString("ITEMNUM").substring(0, 1));
                    linha.setFieldFlag("COMMODITY", 16L, true);
                    linha.setValue("COMMODITY", item.getString("ITEMNUM").substring(0, 3));
                    linha.setFieldFlag("COMMODITYGROUP", 7L, false);
                    linha.setFieldFlag("COMMODITY", 7L, false);
                    linha.setValue("TOSITEID", "SITE_01");
                    linha.setValue("LANGCODE", "PT");
                    linha.setValue("CONVERSION", 1);
                    linha.setValue("HASLD", 0);
                    linha.setValue("MKTPLCITEM", 0);
                }
            }

        }
    }

    public int SAVE()
            throws MXException, RemoteException {
        int ret = -1;
        //Uteis.espera("*************** String<>");
        String camposMeioTransporte[] = {
            "ID2MTRANSAPE", "ID2MTRANSROD", "ID2MTRANSFER", "ID2MTRANSAER", "ID2MTRANSMAR"
        };
        //Uteis.espera("*************** antes de preencimentoObrigatorio()");
        if (!Validar.preencimentoMinimoObrigatorio(getMbo().getMboValueData(camposMeioTransporte), 1)) {
            throw new MXApplicationException("company", "MeioTransporteFaltando");
        }
        //Uteis.espera("*************** ap\363s de preencimentoObrigatorio()");
        int quantidadeTotal = 0;
        //Uteis.espera("***************Antes de chamar...");
        MboSet linhas = (MboSet) getMbo().getMboSet("POLINE");
        //Uteis.espera("***************Ap\363s de chamar...");
        if (linhas != null) {
            for (int i = 0; i < linhas.count(); i++) {
                Mbo linha = (Mbo) linhas.getMbo(i);
                try {
                    quantidadeTotal += linha.getInt("ORDERQTY");
                } catch (MXException ex) {
                }
            }

            Extenso valorExtenso = new Extenso(quantidadeTotal);
            getMbo().setValue("ID2TOTEXT", valorExtenso.toString());
            for (int i = 0; i < linhas.count(); i++) {
                Mbo linha = (Mbo) linhas.getMbo(i);
                try {
                    if (linha.getInt("ORDERQTY") == 0) {
                        linha.delete();
                    }
                } catch (Exception ex) {
                    //Uteis.espera((new StringBuilder()).append("--------------------------- Exce\347\343o lopp. ").append(ex.getMessage()).toString());
                }
            }

        }

        // Criado por Dyogo, solicitado por Alexandre Tesck, via e-mail no dia 22/03.
        Date dataValidade = getMbo().getDate("ID2VALIDADE");
        // Criado por Leysson, solicitado por Bruno, no dia 23/02/2012
        if (dataValidade == null) {
            throw new MXApplicationException("gta", "DataNull");
        } else if (Data.dataInicialMenorFinal(dataValidade, new Date())) {
            throw new MXApplicationException("gta", "DataValidadeMenorQueDataAtual");
        }

        //TODO: coloco o codbarras onde?

        if (getMbo().getString("COMMODITYGROUP").equalsIgnoreCase("6")) {
            //Criado por Patrick, solicitado por, Bruno por email no dia 06/09
            int somaMachoFemea = getMbo().getInt("MAAVEMAS") + getMbo().getInt("MAAVEFEM");
            int qtdAnimais = 0;

            MboRemote rel;
            for (int i = 0; ((rel = getMbo().getMboSet("POLINE").getMbo(i)) != null); i++) {
                qtdAnimais += rel.getInt("ORDERQTY");
            }

            if (!(somaMachoFemea == qtdAnimais)) {
                throw new MXApplicationException("po", "qtdAnimalNaoConfere");
            }
        }

        int retorno = super.SAVE();

        System.out.println("-----  id2StrComm init");
        MboSet id2StoComm;
        id2StoComm = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2STOCOMM", sessionContext.getUserInfo());

        id2StoComm.setWhere("LOCATION = '" + getMbo().getString("ID2STORELOCDEST") + "'"
                + "AND ID2GRPESP = '" + getMbo().getString("COMMODITYGROUP") + "'"
                + "AND ID2CODESP = '" + getMbo().getString("COMMODITY") + "'");
        id2StoComm.reset();
        System.out.println("-----  id2StrComm count = " + id2StoComm.count());

        if (id2StoComm.count() == 0) {
            MboRemote mbo;
            mbo = id2StoComm.add();
            System.out.println("-----  id2StrComm after add");
            mbo.setValue("LOCATION", getMbo().getString("ID2STORELOCDEST"));
            mbo.setValue("ID2GRPESP", getMbo().getString("COMMODITYGROUP"));
            mbo.setValue("ID2CODESP", getMbo().getString("COMMODITY"));
            mbo.setValue("ID2SALDO", 0);
            System.out.println("-----  id2StrComm after setValues");
        }

        id2StoComm.save();

        return retorno;

    }

    @Override
    public synchronized boolean fetchRecordData() throws MXException,
            RemoteException {
        if (getMbo() != null) {
            if (getMbo().getString("ID2ADDUF").equals("") || getMbo().getString("ID2LOCUF").equals("")) {
                if (getMbo().getMboSet("MAADDRESS").count() != 0) {
                    String ufSig = getMbo().getMboSet("MAADDRESS").getMbo(0).getString("ID2ADDUF");
                    String ufCod = getMbo().getMboSet("MAADDRESS").getMbo(0).getString("ID2LOCUF");
                    if (getMbo().getString("ID2ADDUF").equals("")) {
                        getMbo().setValue("ID2ADDUF", ufSig, MboConstants.NOACCESSCHECK);
                    }
                    if (getMbo().getString("ID2LOCUF").equals("")) {
                        getMbo().setValue("ID2LOCUF", ufCod, MboConstants.NOACCESSCHECK);
                    }
                }
            }
            if (!getMbo().getString("ID2LOCAL").equals("")) {
                MboSet matbEmissao = (MboSet) getMbo().getMboSet("MARL00LOC");
                MboSetRemote mboSet2 = matbEmissao.getMbo(0).getMboSet("ID2LOCADDRESS");
                getMbo().setValue("ID2MUNEMISSAO", mboSet2.getMbo(0).getString("ADDRESS4"), MboConstants.NOACCESSCHECK);
            } else {
                getMbo().setValue("ID2MUNEMISSAO", "", MboConstants.NOACCESSCHECK);
            }

        }
        return super.fetchRecordData();
    }

    public String gerarCodigoBarras(Date aDataEmissao) {
        int onde = 0;
        //Uteis.espera("************************** gerar c\363d barras - entrou");
        String codigoBarras = "";
        try {
            System.out.println("####### before Objec []");
            Thread.sleep(1000);
            Object[] valores = retornaValores();

            System.out.println("####### after Objec []");
            Thread.sleep(1000);
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            String numGta = "000000";

            String UF = getMbo().getString("ID2LOCUF");

            numGta = (new StringBuilder()).append(numGta.substring(0, numGta.length() - getMbo().getString("ID2NUMGTA").length())).append(getMbo().getString("ID2NUMGTA")).toString();

            char cSerie = getMbo().getString("ID2SERIE").charAt(0);
            int iSerie = (cSerie - 65) + 1;
            String serie;
            if (iSerie < 10) {
                serie = (new StringBuilder()).append("0").append(iSerie).toString();
            } else {
                serie = (new StringBuilder()).append("").append(iSerie).toString();
            }

            String especie = getMbo().getString("COMMODITY");
            String grupoEspecie = getMbo().getString("COMMODITYGROUP");
            int qtdAnimais = (new Double(getMbo().getMboSet("POLINE").sum("ORDERQTY"))).intValue();

            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            String DATAEMISSAO = (new StringBuilder()).append("00".substring(0, "00".length() - (new StringBuilder()).append(aDataEmissao.getDate()).append("").toString().length())).append(aDataEmissao.getDate()).append("00".substring(0, "00".length() - (new StringBuilder()).append(aDataEmissao.getMonth()).append("").toString().length())).append(aDataEmissao.getMonth()).append(aDataEmissao.getYear() + 1900).append("").toString();
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());

            String QTDANIMAL = (new StringBuilder()).append("0000000".substring(0, "0000000".length() - (new StringBuilder()).append(qtdAnimais).append("").toString().length())).append(qtdAnimais).toString();

            String origem = "00000";
            String codOrigem = "";

            //TODO: validar amanhã com o Bruno onde buscar essas informações
            if (getMbo().getString("ID2TIPOLOCAL").equals("01")) {
                codOrigem = getMbo().getString("ID2PROEXPPROP");
            }
            if (getMbo().getString("ID2TIPOLOCAL").equals("02")) {
                codOrigem = getMbo().getString("ID2PROEXPPROP");
            }
            if (getMbo().getString("ID2TIPOLOCAL").equals("03")) { //aglomeração
                codOrigem = getMbo().getString("ID2PRONUMCONT");
            }
            if (getMbo().getString("ID2TIPOLOCAL").equals("04")) {  //exploração
                codOrigem = getMbo().getString("ID2PROEXPPROP");
            }
            origem = (new StringBuilder()).append("00000".substring(0, "00000".length() - (new StringBuilder()).append(codOrigem).append("").toString().length())).append(codOrigem).toString();
            //TODO: codigo municipio
            String codMunicipio = "55555";

            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            //String PROPRIEDADE = getMbo().getString("ID2LOCPROEXP.ID2VWLOC01.LOCATION").substring(getMbo().getString("ID2LOCPROEXP.ID2VWLOC01.LOCATION").length() - 10, getMbo().getString("ID2LOCPROEXP.ID2VWLOC01.LOCATION").length());
            //String MUNICIPIO = getMbo().getString("ID2LOCDESEXP.ID2VWLOC01.ID2CODMUN").substring(getMbo().getString("ID2LOCDESEXP.ID2VWLOC01.ID2CODMUN").length() - 6, getMbo().getString("ID2LOCDESEXP.ID2VWLOC01.ID2CODMUN").length());
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());

            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());

            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            String verificador = (new StringBuilder()).append(UF).append(serie).append(numGta).toString();
            int j = 2;
            int res = 0;
            for (int i = verificador.length() - 1; i >= 0; i--) {
                Integer digito = new Integer(verificador.substring(i, i + 1));
                res += digito.intValue() * j;
                if (++j > 9) {
                    j = 2;
                }
            }

            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());

            String DIGITO1 = (new StringBuilder()).append(11 - res % 11 <= 9 ? 11 - res % 11 : 0).append("").toString();
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());

            verificador = (new StringBuilder()).append(UF).append(serie).append(numGta).append(DIGITO1).append(DATAEMISSAO).append(especie).append(QTDANIMAL).toString();
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            j = 2;
            res = 0;
            verificador = verificador.replace(".", "");
            //Uteis.espera("************************** gerar c\363d barras. Verificador ... " + verificador);
            for (int i = verificador.length() - 1; i >= 0; i--) {
                Integer digito = new Integer(verificador.substring(i, i + 1));
                res += digito.intValue() * j;
                if (++j > 9) {
                    j = 2;
                }
            }
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            String DIGITO2 = (new StringBuilder()).append(11 - res % 11 <= 9 ? 11 - res % 11 : 0).append("").toString();
            verificador = (new StringBuilder()).append(UF).append(serie).append(numGta).append(DIGITO1).append(DATAEMISSAO).append(especie).append(QTDANIMAL).append(DIGITO2).append(origem).append(codMunicipio).toString();
            //verificador = (new StringBuilder()).append(UF).append(serie).append(numGta).append(DIGITO1).append(DATAEMISSAO).append(especie).append(QTDANIMAL).append(DIGITO2).toString();
            j = 2;
            res = 0;
            verificador = verificador.replace(".", "");
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());

            for (int i = verificador.length() - 1; i >= 0; i--) {
                Integer digito = new Integer(verificador.substring(i, i + 1));
                res += digito.intValue() * j;
                if (++j > 9) {
                    j = 2;
                }
            }


            String DIGITO3 = (new StringBuilder()).append(11 - res % 11 <= 9 ? 11 - res % 11 : 0).append("").toString();
            codigoBarras = (new StringBuilder()).append(verificador).append(DIGITO3).toString();


            //codigoBarras = (new StringBuilder()).append(verificador).toString();

        } catch (InterruptedException ex) {
            Logger.getLogger(GtaEmissaoAppBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MXException mxe) {
            //Uteis.espera("************************** gerar c\363d barras exception 1... " + mxe.getMessage());
        } catch (RemoteException re) {
            //Uteis.espera("************************** gerar c\363d barras exception 1... " + re.getMessage());
        }
        //Uteis.espera("************************** gerar c\363d barras fim... " + codigoBarras);
        return codigoBarras;
    }

    private Object[] retornaValores() throws RemoteException {
        Object[] retorno = new Object[3];
        try {
            Properties prop;
            prop = MXServer.getMXServer().getConfig();
            byte[] bytes = null;
            String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@192.168.1.215:1521:dftop1");
            String username = prop.getProperty("mxe.db.user", "dbmaximo");
            String password = prop.getProperty("mxe.db.password", "id2maximo");
            try {
                Class.forName(driver).newInstance();
                java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
                Statement stmt = conexao.createStatement();
                PreparedStatement ps = conexao.prepareStatement("SELECT CHANGEDATE FROM POSTATUS WHERE STATUS = 'EM_TRANSITO' AND PONUM = ?");
                ps.setString(1, getMbo().getString("PONUM"));
                ResultSet rs = ps.executeQuery();
                retorno[0] = rs.getDate("CHANGEDATE");
                System.out.println("############ punum " + getMbo().getString("PONUM"));
                System.out.println("############ retorn[0] " + retorno[0]);
                Thread.sleep(5000);
                ps = conexao.prepareStatement("SELECT SUM(ORDERQTY) TOTAL FROM POLINE WHERE PONUM = ?");
                ps.setString(1, getMbo().getString("PONUM"));
                rs = ps.executeQuery();
                retorno[1] = rs.getInt("TOTAL");
                System.out.println("############ retorn[1] " + retorno[1]);
                Thread.sleep(5000);
                ps = conexao.prepareStatement("select id2codmun from id2vwloc01 where  location in (select id2codprop from id2vwloc04 where location in (select id2storelocdest from po where ponum = ?) " + " union " + "select id2codmun from id2vwloc02 where location in (select id2storelocdest from po where ponum = ?) " + " union " + "select id2codmun  from id2vwloc03 where location in (select id2storelocdest from po where ponum = ?)");
                ps.setString(1, getMbo().getString("PONUM"));
                ps.setString(2, getMbo().getString("PONUM"));
                ps.setString(3, getMbo().getString("PONUM"));
                rs = ps.executeQuery();
                retorno[1] = rs.getInt("id2codmun");
                System.out.println("############ retorn[2] " + retorno[2]);
                Thread.sleep(5000);

            } catch (Exception ex) {
                System.out.println("------------- ex " + ex.getMessage());
                ex.getStackTrace();
            }
            Thread.sleep(5000);

        } catch (InterruptedException ex) {
            Logger.getLogger(GtaEmissaoAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
        super.initialize();
        int posicaoAutal = app.getDataBean("MAINRECORD").getMbo().getMboSet("nomedorelacionamento").getCurrentPosition();
        Long valorParametro = app.getDataBean("MAINRECORD").getMbo().getMboSet("nomedorelacionamento").getMbo(posicaoAutal).getLong("nomedocampo");
    }
}
