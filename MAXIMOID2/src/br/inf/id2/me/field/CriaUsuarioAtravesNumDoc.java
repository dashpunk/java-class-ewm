package br.inf.id2.me.field;

import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;

import br.inf.id2.common.util.Uteis;
import br.inf.id2.common.util.Validar;
import java.util.ArrayList;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboConstants;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class CriaUsuarioAtravesNumDoc extends MAXTableDomain {

    int aonde = 0;

    public CriaUsuarioAtravesNumDoc(MboValue mbv)
            throws MXException {
        super(mbv);

        setRelationship("TBDOCPES", "dsnumdoc = :dsnumdoc and dsnumdoc not in (select dsnumdoc from mxtbrhcase)");
        setErrorMessage("person", "InvalidPerson");
        setListCriteria((new StringBuilder()).append("personid not in (select personid from mxtbrhcase) and itdocs = :itdocs").toString());
    }

    public void validate() throws MXException, RemoteException {
        if (getMboValue().isNull()) {
            System.out.println("------------------- is null");
            return;
        }


        //a pedido do Felipe Marinho dia 27/10/2011
        String tipoDocumento = getMboValue("ITDOCS").getString();
        String nrDocumento = getMboValue("DSNUMDOC").getString();
        System.out.println("... ITDOCS " + tipoDocumento);
        System.out.println("... DSNUMDOC " + nrDocumento);
        String numDocumento = "";
        if (tipoDocumento.equalsIgnoreCase("CPF")) {

            if (!Validar.CPF(Uteis.getApenasNumeros(nrDocumento))) {
                throw new MXApplicationException("id2message", "CpfInvalido");
            }

            numDocumento = Uteis.getValorMascarado("999.999.999-99", Uteis.getApenasNumeros(nrDocumento), true);

           getMboValue().setValue(nrDocumento, MboConstants.NOVALIDATION_AND_NOACTION);
        } else{
        	 nrDocumento =  Uteis.getApenasNumeros(nrDocumento);
        	 getMboValue().setValue(nrDocumento, MboConstants.NOVALIDATION_AND_NOACTION);
        }

        //String numDocumento = Uteis.retiraCaracteresEspeciais(getMboValue().getString());




        //FIM das alterações requisitadas pelo Felipe Marinho dia 27/10/2011

        MboRemote mbo = getMboValue().getMbo();


        MboSet mboSetDocPes = (MboSet) getMboValue().getMbo().getMboSet("MXRLDOCPES02");

        MboRemote gMbo;
        boolean encontrado = false;

        for (int i = 0; ((gMbo = mboSetDocPes.getMbo(i)) != null); i++) {
            System.out.println("------------- i " + i + " valorDoc " + gMbo.getString("DSNUMDOC"));
            encontrado = gMbo.getString("DSNUMDOC").equalsIgnoreCase(numDocumento);
            if (encontrado) {
                System.out.println("----------encontrou");
                getMboValue().getMbo().setValue("PERSONID", gMbo.getString("PERSONID"));
                getMboValue().getMbo().setValue("MXSALVO", true);
                //getMboValue().getMbo().getThisMboSet().save();
                break;
            }
        }

        System.out.println("------------- antes da validação de encontrado");

        if (!encontrado) {
            String yesNoId = getClass().getName();
            int userInput = MXApplicationYesNoCancelException.getUserInput(yesNoId, MXServer.getMXServer(), getMboValue().getMbo().getUserInfo());
            System.out.println("retorno = " + userInput);
            switch (userInput) {
                case MXApplicationYesNoCancelException.NULL:
                    System.out.println("---------------- userImpot null");
                    Object params[] = {
                        getMboValue().getString()
                    };
                    throw new MXApplicationYesNoCancelException(yesNoId, "rhcase", "novoDocumento", params);

                case MXApplicationYesNoCancelException.YES:
                    System.out.println("---------------- userImpot YES");
                    System.out.println("----------- pessoas");
                    MboSet pessoas;
                    pessoas = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("PERSON", mbo.getUserInfo());
                    pessoas.setWhere("personid = '---'");
                    pessoas.reset();

                    System.out.println("----------- documentos");
                    MboSet documentos;
                    documentos = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("TBDOCPES", mbo.getUserInfo());
                    documentos.setWhere("personid = '---'");
                    documentos.reset();


                    System.out.println("----------- pessoa");
                    MboRemote pessoa = pessoas.add();
                    String personId = pessoa.getString("PERSONID");
                    System.out.println("---------- personId " + personId);
                    System.out.println("---------- " + pessoa.getString("HASLD"));
                    System.out.println("---------- " + pessoa.getString("LANGCODE"));
                    System.out.println("---------- " + pessoa.getString("PERSONID"));
                    System.out.println("---------- " + pessoa.getString("PERSONUID"));

                    //pessoa.setValue("HASLD", null);
                    //pessoa.setValue("LANGCODE", null);
                    //pessoa.setValue("PERSONID", null);
                    //pessoa.setValue("PERSONUID", null)


                    //pessoa.setValue("ACCEPTINGWFMAIL", true);

                    //pessoa.setValue("LOCTOSERVREQ", true);
                    System.out.println("------------------ bb  antes " + ++aonde);
                    pessoa.setValue("MEDEF", false);
                    System.out.println("------------------ bb  antes " + ++aonde);
                    pessoa.setValue("MEPAT", false);
                    System.out.println("------------------ bb  antes " + ++aonde);
                    pessoa.setFieldFlag("STATUS", MboConstants.READONLY, false);
                    pessoa.setFieldFlag("STATUSDATE", MboConstants.READONLY, false);
                    pessoa.setFieldFlag("STATUSIFACE", MboConstants.READONLY, false);
                    pessoa.setFieldFlag("TRANSEMAILELECTION", MboConstants.READONLY, false);
                    //pessoa.setValue("STATUS", "ATIVO");
                    System.out.println("------------------ bb  antes " + ++aonde);
                    // pessoa.setValue("STATUSDATE", new Date());
                    //pessoa.setValue("STATUSIFACE", false);
                    //pessoa.setValue("TRANSEMAILELECTION", "SEMPRE");




                    System.out.println("----------- pessoas save");
                    pessoas.save();
                    System.out.println("----------- pessoas save a");

                    System.out.println("----------- documento");
                    MboRemote documento = documentos.add();


                    System.out.println("------------------ cc  antes " + ++aonde);
                    documento.setValue("PERSONID", personId);
                    System.out.println("------------------ cc  antes " + ++aonde);
                    documento.setValue("ITDOCS", mbo.getString("ITDOCS"));
                    System.out.println("------------------ cc  antes " + ++aonde);
                    documento.setValue("DSNUMDOC", mbo.getString("DSNUMDOC"));


                    System.out.println("----------- documentos save");
                    documentos.save();
                    System.out.println("----------- documentos save a");

                    System.out.println("----------- personId");
                    mbo.setValue("PERSONID", personId);
                    System.out.println("----------- personId a");
                    getMboValue().getMbo().setValue("MXSALVO", false);
                    break;
                case MXApplicationYesNoCancelException.NO: // '\020'
                    System.out.println("---------------- userImpot NO");
                    //throw new MXApplicationException("system", "null");
                    getMboValue().setValueNull();
                default:
                    System.out.println("---------------- userImpot DEFAULT");
                    break;
            }


        }



    }

    @Override
    public void action() throws MXException, RemoteException {
        //super.action();
        System.out.println("------------------- action ");
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        System.out.println("--------------- getList()");
        return super.getList();

    }

    @Override
    public void chooseActualDomainValues() throws MXException, RemoteException {
        System.out.println("chooseActualDomainValues");
        super.chooseActualDomainValues();
    }

    @Override
    public MboSetRemote getMboSet() throws MXException, RemoteException {
        System.out.println("getMboSet");
        return super.getMboSet();
    }

    @Override
    public MboSetRemote getMboSet(String where) throws MXException, RemoteException {
        System.out.println("getMboSet b");
        return super.getMboSet(where);
    }

    @Override
    public MboSetRemote getMboSet(String where, String identifier) throws MXException, RemoteException {
        System.out.println("getMboSet c");
        return super.getMboSet(where, identifier);
    }

    @Override
    public boolean hasList() {
        System.out.println("hasList");
        return super.hasList();
    }

    @Override
    public void setRelationship(String objectName, String whereClause) {
        System.out.println("setRelationship");
        super.setRelationship(objectName, whereClause);
    }

    @Override
    public void setListCriteria(String listWhere) {
        System.out.println("setListCriteria");
        super.setListCriteria(listWhere);
    }

    @Override
    public void setErrorMessage(String eg, String ek) {
        System.out.println("setErrorMessage");
        super.setErrorMessage(eg, ek);
    }

    @Override
    public String getListCriteria() {
        System.out.println("getListCriteria");
        return super.getListCriteria();
    }

    @Override
    public void setValueFromLookup(MboRemote sourceMbo) throws MXException, RemoteException {
        System.out.println("setValueFromLookup");
        super.setValueFromLookup(sourceMbo);
    }

    @Override
    public void setValueFromLookup(MboRemote sourceMbo, long accessModifier) throws MXException, RemoteException {
        System.out.println("setValueFromLookup");
        super.setValueFromLookup(sourceMbo, accessModifier);
    }

    @Override
    protected String[][] getMatchingAttrs(String sourceName) throws MXException, RemoteException {
        System.out.println("getMatchingAttrs");
        return super.getMatchingAttrs(sourceName);
    }

    @Override
    public void setLookupKeyMapInOrder(String[][] map) {
        System.out.println("setLookupKeyMapInOrder");
        super.setLookupKeyMapInOrder(map);
    }

    @Override
    public void setLookupKeyMapInOrder(String[] targetKeys, String[] sourceKeys) {
        System.out.println("setLookupKeyMapInOrder");
        super.setLookupKeyMapInOrder(targetKeys, sourceKeys);
    }

    @Override
    public void setKeyMap(String sourceMboName, String[] targetKeys, String[] sourceKeys) {
        System.out.println("setKeyMap");
        super.setKeyMap(sourceMboName, targetKeys, sourceKeys);
    }

    @Override
    public void addToLookupMapCache(String source, String[][] map) throws MXException, RemoteException {
        System.out.println("addToLookupMapCache");
        super.addToLookupMapCache(source, map);
    }

    @Override
    public void setMultiKeyWhereForLookup(String w) {
        System.out.println("setMultiKeyWhereForLookup");
        super.setMultiKeyWhereForLookup(w);
    }

    @Override
    public MboSetRemote smartFill(String value, boolean exact) throws MXException, RemoteException {
        System.out.println("smartFill");
        return super.smartFill(value, exact);
    }

    @Override
    public MboSetRemote smartFillWithoutReset(String value, boolean exact) throws MXException, RemoteException {
        System.out.println("smartFillWithoutReset");
        return super.smartFillWithoutReset(value, exact);
    }

    @Override
    public MboSetRemote smartFind(String value, boolean exact) throws MXException, RemoteException {
        System.out.println("smartFind");
        return super.smartFind(value, exact);
    }

    @Override
    public MboSetRemote smartFind(String sourceObj, String value, boolean exact) throws MXException, RemoteException {
        System.out.println("smartFind b");
        return super.smartFind(sourceObj, value, exact);
    }

    @Override
    public MboSetRemote smartFindWithoutReset(String value, boolean exact) throws MXException, RemoteException {
        System.out.println("smartFindWithoutReset");
        return super.smartFindWithoutReset(value, exact);
    }

    @Override
    public String getMatchingAttr() throws MXException, RemoteException {
        System.out.println("getMatchingAttr");
        return super.getMatchingAttr();
    }

    @Override
    public String getMatchingAttr(String sourceObjectName) throws MXException, RemoteException {
        System.out.println("getMatchingAttr b");
        return super.getMatchingAttr(sourceObjectName);
    }

    @Override
    public void addConditionalListWhere(String attribute, String condition, String where) {
        System.out.println("addConditionalListWhere");
        super.addConditionalListWhere(attribute, condition, where);
    }

    @Override
    public void clearConditionalListWhere() {
        System.out.println("clearConditionalListWhere");
        super.clearConditionalListWhere();
    }

    @Override
    public String evalConditionalWhere(ArrayList conditionalWhereList) throws MXException, RemoteException {
        System.out.println("evalConditionalWhere");
        return super.evalConditionalWhere(conditionalWhereList);
    }
}
