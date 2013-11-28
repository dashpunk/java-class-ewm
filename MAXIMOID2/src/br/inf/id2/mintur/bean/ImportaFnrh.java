package br.inf.id2.mintur.bean;

import br.inf.id2.common.util.Data;
import br.inf.id2.common.util.Uteis;
import br.inf.id2.snrhos.dominio.Fnrh;
import br.inf.id2.snrhos.dominio.Hospede;
import br.inf.id2.snrhos.util.Criptografia;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.List;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;


import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.UploadFile;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Ricardo S Gomes
 */
public class ImportaFnrh extends DataBean {

    private List retornoExcel = null;
    private String breakLine = "\r\n";
    String cnpj = "";

    public ImportaFnrh() {
        System.out.println("... 0858");

    }

    public void setaCabecalho() throws MXException {
        throw new MXApplicationException("system", "invaliddate");
    }

    public int execute() throws MXException, RemoteException {

        //geraLog("############## - iniciando upload.");
        //if (clientSession.hasLongOpStarted()) {
        //geraLog("############ if 1");
        //if (clientSession.runLongOp(this, "execute")) {
        //geraLog("############ if 2 UPLOAD");
        System.out.println("-------------> Execute 1701");

        UploadFile file = (UploadFile) app.get("importfile");
        //geraLog("-------------" + file.getFullFileName());
        file.setDirectoryName("c:/doclinks");
        //geraLog("############ FILE " + file.getFileName() + file.getAbsoluteFileName());

        String fileContent = "";
        try {
            //geraLog("############ TRY");
            fileContent = file.getFileOutputStream().toString("UTF-8");
            try {
                //geraLog("****************** ESCREVE NO DISCO.");
                file.writeToDisk();
                //geraLog("****************** OK.");
                //retornoExcel = LerPlanilhaExcel.readExcel(file.getAbsoluteFileName());
                File fis = null;

                fis = new File(file.getAbsoluteFileName());

                System.out.println("### nome arquivo " + fis.getName());
                System.out.println("### nome arquivo " + fis.getName().substring(0, 22));
                String[] nomeDet = fis.getName().split("_");
                System.out.println("### cnpj " + nomeDet[0]);

                System.out.println("### antes mbosetimp");
                MboSet importacao = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SNTBIMPORTACAO", sessionContext.getUserInfo());
                importacao.setWhere("DESCRIPTION = '" + fis.getName().substring(0, 22) + "'");
                importacao.reset();
                System.out.println("### apos mbosetimp");

                boolean importar = (importacao.count() > 0);

                if (importar) {
                    throw new MXApplicationException("fnrh", "arquivoImportacaoDublicado");
                }

                cnpj = nomeDet[0];
                //XmlLoader xml = new XmlLoader();
                try {
                    FileReader reader = new FileReader(fis.getAbsolutePath());

                    BufferedReader leitor = new BufferedReader(reader);

                    String linha = null;
                    String valor = "";
                    System.out.println("----- conteudo 1921");
                    System.out.println(leitor.toString());
                    System.out.println("----- conteudo FIM");
                    XStream xStream = new XStream(new DomDriver());
                    System.out.println("----- conteudo a");
                    Fnrh fnrh = new Fnrh();
                    System.out.println("----- conteudo a1");
                    String aXml = "";
                    String aXmlLinha = "";
                    while ((aXmlLinha = leitor.readLine()) != null) {
                        aXml += aXmlLinha;
                    }
                    System.out.println("------------------------------------");
                    System.out.println(aXml);
                    System.out.println("------------------------------------");
//                    Criptografia criptografia = new Criptografia("Bem vindo ao snrhos!");
//                    System.out.println("--- cripto new");
//                    String bXml = criptografia.decrypt(aXml.trim());
//                    
//                    String[] v1 = aXml.split("vector>");
//                    System.out.println(v1.length);
//                    aXml = "<list>" + v1[1] + "list>";
                    aXml = d(aXml.trim(), 3);
                    System.out.println("--- cript after");
                    System.out.println(aXml);
                    System.out.println("---------apos dec-------------------------");
                    List<Fnrh> retorno = (List<Fnrh>) xStream.fromXML(aXml);
                    System.out.println("----- conteudo b " + retorno.size());
                    System.out.println("----- conteudo c " + retorno.get(0).getHospede().getNomeCompleto());
                    System.out.println("");

                    sincronizaFnrhs(retorno);
                } catch (Exception e) {
                    System.out.println("--- ex " + e.getMessage());
                    geraLog(e.getMessage());
                    e.printStackTrace();
                }
                System.out.println("### iniciando add imp");
                MboRemote imp;
                System.out.println("### add imp");
                imp = importacao.add();
                System.out.println("### value");
                imp.setValue("DESCRIPTION", fis.getName().substring(0, 22));
                System.out.println("### salve");
                importacao.save();
                System.out.println("### fim imp");
                //tratamentoImportacao();
            } catch (IOException e) {
                geraLog(e.getMessage());
                //geraLog("ERRO AO IMPORTAR" + e.getMessage());
            }
        } catch (UnsupportedEncodingException uee) {
            geraLog(uee.getMessage());
            //geraLog("Unsupported encoding exception!!!");
            //geraLog("############ ERRO");
            uee.printStackTrace();
        }



        WebClientEvent event = sessionContext.getCurrentEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
        sessionContext.queueRefreshEvent();
        //Cria dois objetos de evento, sendo que o eventType receberá a página no qual queremos redirecionar.
        fetchRecordData();

        fetchTableData();

        fetchTableData(startrow);


        return 1;

    }

    public void geraLog(String texto) {
//        try {
//            BufferedWriter bufferOut = new BufferedWriter(new FileWriter(" /u01/doclinks/logimport.txt", true));
//
//            bufferOut.write(texto + breakLine);
//            bufferOut.close();
//        } catch (IOException ex) {
//            geraLog(ex.getMessage());
//        }
    }

    private void sincronizaFnrhs(List<Fnrh> retorno) throws MXException, RemoteException {
        System.out.println("--------------------------------------------- sincronizaFnrhs " + retorno.size());
        for (int i = 0; i < retorno.size(); i++) {
            System.out.println("--------------------------------------------- sincronizaFnrhs " + i);
            sincronizarFnrh(retorno.get(i));
        }
        System.out.println("--------------------------------------------- sincronizaFnrhs FIM");

    }

    private void sincronizarFnrh(Fnrh fnrh) throws MXException, RemoteException {
        System.out.println("--------------------------------------------- sincronizarFnrh ");
//TODO: reavaliar
//        String personID = sincronizaPessoa(fnrh.getHospede());
        //verifica se ja exite
        MboSet fnrhs;
        fnrhs = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SNTBFNRH", sessionContext.getUserInfo());
        fnrhs.setWhere("SNPERSONID = -32");
//        fnrhs.setWhere("SNPERSONID = '" + personID + "'");
//        SqlFormat sqf = new SqlFormat("SNPERSONID = '" + personID + "' AND SNENT = :1");
//        sqf.setDate(1, fnrh.getEntrada());
//        fnrhs.setWhere(sqf.format());
//        fnrhs.reset();

        MboRemote mboFnrh;
        boolean encontrado = false;
//        System.out.println("fnrhs count " + fnrhs.count());
//        for (int i = 0; ((mbo = fnrhs.getMbo(i)) != null); i++) {
//            System.out.println("i " + i);
//            System.out.println("D1 " + mbo.getDate("SNENT"));
//            System.out.println("D2 " + fnrh.getEntrada());
//            System.out.println("DF " + Data.recuperaDiasEntreDatas(mbo.getDate("SNENT"), fnrh.getEntrada()));
//
//            if (Data.recuperaDiasEntreDatas(mbo.getDate("SNENT"), fnrh.getEntrada()) == 0) {
//                encontrado = true;
//                break;
//            }
//
//        }

//        System.out.println("----- FNRH cont " + fnrhs.count());

//        MboRemote mbo;

        if (!encontrado) {
            System.out.println("--------------------------1943 Nao encontrado");
            mboFnrh = fnrhs.add();
//            mboFnrh.setValue("SNPERSONID", personID, MboConstants.NOACCESSCHECK);
            mboFnrh.setValue("SNREGIMP", true, MboConstants.NOACCESSCHECK);
            mboFnrh.setValue("BGSTDSCPAIS", fnrh.getPaisProcedencia().getDescricao(), MboConstants.NOACCESSCHECK);
            mboFnrh.setValue("BGSTDSCESTADO", fnrh.getEstadoProcedencia().getDescricao(), MboConstants.NOACCESSCHECK);
            if (fnrh.getDsProcedenciaCidade() != null && fnrh.getDsProcedenciaCidade().length() > 0) {
                System.out.println("*** fnrh.getDsProcedenciaCidade() " + fnrh.getDsProcedenciaCidade());
                mboFnrh.setValue("BGSTDSCCIDADE2", fnrh.getDsProcedenciaCidade(), MboConstants.NOVALIDATION_AND_NOACTION);
            } else {
                System.out.println("*** fnrh.getCidadeProcedencia().getDescricao() " + fnrh.getCidadeProcedencia().getDescricao());
                mboFnrh.setValue("BGSTDSCCIDADE", fnrh.getCidadeProcedencia().getDescricao(), MboConstants.NOVALIDATION_AND_NOACTION);
            }
            System.out.println("*** apos origem");

            mboFnrh.setValue("BGSTDSCPAISDEST", fnrh.getPaisDestino().getDescricao(), MboConstants.NOACCESSCHECK);
            mboFnrh.setValue("BGSTDSCESTADODEST", fnrh.getEstadoDestino().getDescricao(), MboConstants.NOACCESSCHECK);
            if (fnrh.getDsDestinoCidade() != null && fnrh.getDsDestinoCidade().length() > 0) {
                mboFnrh.setValue("BGSTDSCCIDADEDEST2", fnrh.getDsDestinoCidade(), MboConstants.NOVALIDATION_AND_NOACTION);
            } else {
                mboFnrh.setValue("BGSTDSCCIDADEDEST", fnrh.getCidadeDestino().getDescricao(), MboConstants.NOVALIDATION_AND_NOACTION);
            }
            System.out.println("*** apos destino");

            mboFnrh.setValue("SNENT", fnrh.getEntrada(), MboConstants.NOACCESSCHECK);
            System.out.println("*** apos 1");
            mboFnrh.setValue("SNSAI", fnrh.getSaida(), MboConstants.NOACCESSCHECK);
            System.out.println("*** apos 2");
            mboFnrh.setValue("SNOBS", fnrh.getObservacoes(), MboConstants.NOACCESSCHECK);
            System.out.println("*** apos 3");
            mboFnrh.setValue("SNNUMHOSP", String.valueOf(fnrh.getNumeroHospedes()), MboConstants.NOACCESSCHECK);
            System.out.println("*** apos 4");
            mboFnrh.setValue("SNUHNUM", fnrh.getNumeroUh(), MboConstants.NOACCESSCHECK);
            System.out.println("**** cnpj " + cnpj);
            mboFnrh.setValue("SNCODCNPJ", cnpj, MboConstants.NOACCESSCHECK);
            System.out.println("**** cnpj " + mboFnrh.getString("SNCODCNPJ"));

            System.out.println("----------------> Hóspede");


            mboFnrh.setValue("SNSTCODCEP2", fnrh.getHospede().getCep(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNSTDSCPAIS", fnrh.getHospede().getPais().getDescricao(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNSTDSCESTADO", fnrh.getHospede().getEstado().getDescricao(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNSTDSCCIDADE", fnrh.getHospede().getCidade().getDescricao(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNSTDSCCIDADE2", fnrh.getHospede().getDsCidade(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNSTDSCLOGRADOURO", fnrh.getHospede().getResidenciaLogradouro(), MboConstants.NOACCESSCHECK);


            //Dados do hóspede
            mboFnrh.setValue("SNCELULARDDD", fnrh.getHospede().getCelularDdd(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNCELULARDDI", fnrh.getHospede().getCelularDdi(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNCELULARNUM", fnrh.getHospede().getCelular(), MboConstants.NOACCESSCHECK);



            mboFnrh.setValue("SNEMAIL", fnrh.getHospede().getEmail(), MboConstants.NOACCESSCHECK);
            //10

            mboFnrh.setValue("SNNACIONALIDADE", fnrh.getHospede().getNacionalidade().getDescricao(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNNOMECOMPLETO", fnrh.getHospede().getNomeCompleto(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNNUMCPF", fnrh.getHospede().getCpf(), MboConstants.NOVALIDATION_AND_NOACTION);

            mboFnrh.setValue("SNNUMDOC", fnrh.getHospede().getIdentidadeNumero(), MboConstants.NOVALIDATION_AND_NOACTION);

            mboFnrh.setValue("SNOCUPACAO", fnrh.getHospede().getProfissao(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNORGEXP", fnrh.getHospede().getIdentidadeOrgao(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNRESIDENCIA", fnrh.getHospede().getResidenciaLogradouro(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNTELEFONEDDD", fnrh.getHospede().getTelefoneDdd(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNTELEFONEDDI", fnrh.getHospede().getTelefoneDdi(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNTELEFONENUM", fnrh.getHospede().getTelefone(), MboConstants.NOACCESSCHECK);
            //20

            mboFnrh.setValue("SNPAISRES", fnrh.getHospede().getPais().getDescricao(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNESTADORES", fnrh.getHospede().getEstado().getDescricao(), MboConstants.NOACCESSCHECK);

            mboFnrh.setValue("SNCIDADERES", fnrh.getHospede().getDsCidade(), MboConstants.NOACCESSCHECK);


            mboFnrh.setValue("SNSEXO", fnrh.getHospede().getGenero(), MboConstants.NOACCESSCHECK);


            String tipoDocumento = fnrh.getHospede().getIdentidadeTipo();

            if (tipoDocumento.equalsIgnoreCase("PASSAPORTE")) {
                tipoDocumento = "PASSAPORT";
            } else if (tipoDocumento.equalsIgnoreCase("Carteira de Identidade")) {
                tipoDocumento = "RG";
            } else if (tipoDocumento.equalsIgnoreCase("Certidão de Nascimento")) {
                tipoDocumento = "CN";
            } else if (tipoDocumento.equalsIgnoreCase("Cédula de Identidade Estrangeira")) {
                tipoDocumento = "CIE";
            } else {
                tipoDocumento = "";
            }

            mboFnrh.setValue("SNTIPDOC", tipoDocumento, MboConstants.NOVALIDATION_AND_NOACTION);


            mboFnrh.setValue("SNDTNASCIMENTO", fnrh.getHospede().getNascimento(), MboConstants.NOACCESSCHECK);

            System.out.println("----------------> FIM Hóspede");
//            mboFnrh.setValue("SNSTDSCCOMP", fnrh.getHospede().getResidenciaLogradouro(), MboConstants.NOACCESSCHECK);
//            mboFnrh.setValue("SNNUNUMNUM", fnrh.getHospede()(), MboConstants.NOACCESSCHECK);
//            mboFnrh.setValue("SNSTDSCBAIRRO", fnrh.getHospede().getResidenciaLogradouro(), MboConstants.NOACCESSCHECK);

            System.out.println("*** apos 5");

            String motivo = "";
            if (fnrh.getReligiao() == 1) {
                motivo = "01";
            }
            if (fnrh.getLazer() == 1) {
                motivo = "02";
            }
            if (fnrh.getNegocio() == 1) {
                motivo = "03";
            }
            if (fnrh.getCongresso() == 1) {
                motivo = "04";
            }
            if (fnrh.getParentes() == 1) {
                motivo = "05";
            }
            if (fnrh.getEstudos() == 1) {
                motivo = "06";
            }
            if (fnrh.getSaude() == 1) {
                motivo = "07";
            }
            if (fnrh.getCompras() == 1) {
                motivo = "08";
            }
            if (fnrh.getOutroMotivo() == 1) {
                motivo = "09";
            }

            String meioTransporte = "";
            if (fnrh.getAviao() == 1) {
                meioTransporte = "01";
            }
            if (fnrh.getAutomovel() == 1) {
                meioTransporte = "02";
            }
            if (fnrh.getOnibus() == 1) {
                meioTransporte = "03";
            }
            if (fnrh.getMoto() == 1) {
                meioTransporte = "04";
            }
            if (fnrh.getNavio() == 1) {
                meioTransporte = "05";
            }
            if (fnrh.getTrem() == 1) {
                meioTransporte = "06";
            }
            if (fnrh.getOutroMeioTransporte() == 1) {
                meioTransporte = "07";
            }


            System.out.println("*** apos 6");
            mboFnrh.setValue("SNMOTVIA", motivo, MboConstants.NOACCESSCHECK);
            System.out.println("*** apos 7");
            mboFnrh.setValue("SNTIPTRAN", meioTransporte, MboConstants.NOACCESSCHECK);
            System.out.println("*** apos 8");
            mboFnrh.setValue("SNSTATUS", "CHECKOUT", MboConstants.NOACCESSCHECK);
//            mboFnrh.setValue("SNSTATUS", "CHECKIN", MboConstants.NOACCESSCHECK);
            System.out.println("antes do save()");

            //Atualizar Hóspede
            MboSet hospedeSet;
            hospedeSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SNTBHOSPEDES", sessionContext.getUserInfo());

            System.out.println("---------------------> SNTBHOSPEDE");

            System.out.println("---------------------> SNTBHOSPEDE " + mboFnrh.getString("SNNUMCPF"));
            System.out.println("---------------------> SNTBHOSPEDE " + mboFnrh.getString("SNTIPDOC"));
            System.out.println("---------------------> SNTBHOSPEDE " + mboFnrh.getString("SNNUMDOC"));

            if (mboFnrh.getString("SNNUMCPF") != null && !mboFnrh.getString("SNNUMCPF").equals("")) {
                System.out.println("CPF");
                hospedeSet.setWhere("SNNUMCPF = \'" + mboFnrh.getString("SNNUMCPF") + "\'");
            } else if ((mboFnrh.getString("SNTIPDOC") != null && !mboFnrh.getString("SNTIPDOC").equals("")) && (mboFnrh.getString("SNNUMDOC") != null && !mboFnrh.getString("SNNUMDOC").equals(""))) {
                System.out.println("DOCNUM");
                hospedeSet.setWhere("SNTIPDOC = \'" + mboFnrh.getString("SNTIPDOC") + "\' and SNNUMDOC = \'" + mboFnrh.getString("SNNUMDOC") + "\'");
            } else {
//                throw new MXApplicationException("fnrh", "nenhumDocumentoEncontrado");
            }
            hospedeSet.reset();
            System.out.println("RESET");
            MboRemote mbo;
            System.out.println("--------------- hospedeSet.count " + hospedeSet.count());

            if ((mbo = hospedeSet.getMbo(0)) == null) {
                System.out.println("--------------- novo ");
                mbo = hospedeSet.add();
            }

            mbo.setValue("SNCELULARDDD", mboFnrh.getString("SNCELULARDDD"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNCELULARDDI", mboFnrh.getString("SNCELULARDDI"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNCELULARNUM", mboFnrh.getString("SNCELULARNUM"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNCIDADERES", mboFnrh.getString("SNCIDADERES"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNDTNASCIMENTO", mboFnrh.getString("SNDTNASCIMENTO"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNEMAIL", mboFnrh.getString("SNEMAIL"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNESTADORES", mboFnrh.getString("SNESTADORES"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNNACIONALIDADE", mboFnrh.getString("SNNACIONALIDADE"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNNOMECOMPLETO", mboFnrh.getString("SNNOMECOMPLETO"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNNUMCPF", mboFnrh.getString("SNNUMCPF"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNNUMDOC", mboFnrh.getString("SNNUMDOC"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNOCUPACAO", mboFnrh.getString("SNOCUPACAO"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNORGEXP", mboFnrh.getString("SNORGEXP"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNPAISRES", mboFnrh.getString("SNPAISRES"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNRESIDENCIA", mboFnrh.getString("SNRESIDENCIA"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNSEXO", mboFnrh.getString("SNSEXO"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNTELEFONEDDD", mboFnrh.getString("SNTELEFONEDDD"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNTELEFONEDDI", mboFnrh.getString("SNTELEFONEDDI"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNTELEFONENUM", mboFnrh.getString("SNTELEFONENUM"), MboConstants.NOACCESSCHECK);
            mbo.setValue("SNTIPDOC", mboFnrh.getString("SNTIPDOC"), MboConstants.NOACCESSCHECK);

            hospedeSet.save();
            System.out.println("--------------- hospedeSet.save ");

            System.out.println("--- relacionamento " + mboFnrh.getMboSet("MTRL01MAXUSER").count());

            System.out.println("--- vcnpj " + mboFnrh.getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ"));
            String cnpj = mboFnrh.getMboSet("MTRL01MAXUSER").getMbo(0).getString("CO_CNPJ");
            System.out.println("--- cnpj " + cnpj);

            mboFnrh.setValue("SNCODCNPJ", cnpj, MboConstants.NOACCESSCHECK);

            String uf = mboFnrh.getMboSet("SNRL01CNPJ").getMbo(0).getString("SG_UF");
            String localidade = mboFnrh.getMboSet("SNRL01CNPJ").getMbo(0).getString("NO_LOCALIDADE");
            System.out.println("--- uf " + uf);
            System.out.println("--- localidade " + localidade);
            mboFnrh.setValue("SG_UF", uf, MboConstants.NOACCESSCHECK);
            mboFnrh.setValue("NO_LOCALIDADE", localidade, MboConstants.NOACCESSCHECK);
            System.out.println("--- gcnpj " + mboFnrh.getString("SNCODCNPJ"));

            fnrhs.save();
            System.out.println("antes do save() FNRH");
            //Motivo
            //Meio Transporte
        }


        //loop no retorno
        //Verifica se a pessoa existe cria ou edita
        //se nao existir cria e adiciona atelefone, endereco, email  e documento
        //se existir
        // ver se o endereco mudou
        // ver se o telefone mudou
        // ver se o email mudou
        //Verifica se a fnrh não existe cria
    }

    private static String e(String text, int inte) {
        String endResult = "";
        inte = inte * 7;
        for (char a : text.toCharArray()) {
            for (int i = 0; i < inte; i++) {
                if (!(a >= 123 || a < 31)) {
                    if (a + 1 != 123) {
                        a += 1;
                    } else {
                        a = 32;
                    }
                }
            }
            endResult += a;
        }
        return endResult;
    }

    private static String d(String text, int inte) {
        String endResult = "";
        inte = inte * 7;
        for (char a : text.toCharArray()) {
            for (int i = 0; i < inte; i++) {
                if (!(a >= 123 || a < 31)) {
                    if (a - 1 != 31) {
                        a -= 1;
                    } else {
                        a = 122;
                    }
                } else {
                    break;
                }
            }
            endResult += a;
        }
        return endResult;
    }

    private String sincronizaPessoa(Hospede hospede) throws MXException, RemoteException {
        //TODO controle de excecao exceto para pessoa
        System.out.println("--------------------------------------------- sincronizaPessoa ");
        boolean inserir = false;
        String retorno = "";
        MboSet pessoas;
        pessoas = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("BGVWPEFI01", sessionContext.getUserInfo());
        MboSet documentos;
        documentos = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SNTBDOCPES", sessionContext.getUserInfo());
        MboSet fones;
        fones = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("PHONE", sessionContext.getUserInfo());

        System.out.println("apos mboSets");

        String documento = "";
        if ((documento = hospede.getIdentidadeNumero()) == null) {
            if ((documento = hospede.getCpf()) == null) {
                throw new MXApplicationException("fnrhImport", "documentoInvalido");
            }
        }
        System.out.println("Documento " + hospede.getIdentidadeNumero());
        System.out.println("Documento " + hospede.getCpf());
        documentos.setWhere("SNNUMDOC = '" + hospede.getIdentidadeNumero() + "' OR SNNUMDOC = '" + Uteis.getApenasNumeros(hospede.getCpf()) + "'");
        documentos.reset();
        System.out.println("apos reset documento ");
        MboRemote mboDocumento;
        MboRemote mboPessoa;
        MboRemote mboEndereco;
        MboRemote mboFone;

        if ((mboDocumento = documentos.getMbo(0)) == null) {
            System.out.println("NOVO");
            inserir = true;
            mboPessoa = pessoas.add();
            String[] nome = hospede.getNomeCompleto().split(" ");
            if (nome.length < 2) {
                throw new MXApplicationException("fnrhImport", "nomeInvalido");
            }
            String nomePrimeiro = nome[0];
            String nomeultimo = nome[nome.length - 1];

            System.out.println("Nome " + hospede.getNomeCompleto() + " - " + nomePrimeiro + " " + nomeultimo);

            mboPessoa.setValue("FIRSTNAME", nomePrimeiro, MboConstants.NOACCESSCHECK);
            mboPessoa.setValue("LASTNAME", nomePrimeiro, MboConstants.NOACCESSCHECK);
            mboPessoa.setValue("DISPLAYNAME", hospede.getNomeCompleto(), MboConstants.NOACCESSCHECK);


            mboPessoa.setValue("BGSTDSCPROFI", hospede.getProfissao(), MboConstants.NOACCESSCHECK);

            System.out.println("Nascimento " + hospede.getNascimento());
            mboPessoa.setValue("BIRTHDATE", hospede.getNascimento(), MboConstants.NOACCESSCHECK);
            mboPessoa.setValue("BGSTCODSEXO", hospede.getGenero(), MboConstants.NOACCESSCHECK);

            System.out.println("Pais " + hospede.getNacionalidade().getIso());
            mboPessoa.setValue("BGSTCODPAIS", hospede.getNacionalidade().getIso(), MboConstants.NOACCESSCHECK);
            mboPessoa.setValue("MXCODAPP", "SNAPHOSP02", MboConstants.NOACCESSCHECK);
            System.out.println("antes do save");
            pessoas.save();
            retorno = mboPessoa.getString("PERSONID");
            System.out.println("apos save");
            //Documentos
            if (hospede.getIdentidadeNumero() != null && hospede.getIdentidadeNumero().length() > 0) {
                try {
                    System.out.println("---------------- Documento Ientidade----------------------");
                    System.out.println("Identidade " + hospede.getIdentidadeNumero());
                    mboDocumento = documentos.add();
                    System.out.println("PessoaID " + mboPessoa.getString("PERSONID"));
                    mboDocumento.setValue("PERSONID", mboPessoa.getString("PERSONID"), MboConstants.NOACCESSCHECK);
                    String tipoDocumento = "";
                    /*
                     * PASSAPORTE Carteira de Identidade Cédula de Identidade
                     * Estrangeira Certidão de Nascimento
                     */
                    System.out.println("Tipo doc " + hospede.getIdentidadeTipo());
                    if (hospede.getIdentidadeTipo().equalsIgnoreCase("Carteira de Identidade")) {
                        tipoDocumento = "RG";
                    } else if (hospede.getIdentidadeTipo().equalsIgnoreCase("Cédula de Identidade Estrangeira")) {
                        //TODO validar
                        tipoDocumento = "RGI";
                    } else if (hospede.getIdentidadeTipo().equalsIgnoreCase("Certidão de Nascimento")) {
                        //TODO validar
                        tipoDocumento = "CN";
                    } else if (hospede.getIdentidadeTipo().equalsIgnoreCase("PASSAPORTE")) {
                        //TODO validar
                        tipoDocumento = "PASSAPORTE";
                    }

                    mboDocumento.setValue("SNTIPDOC", tipoDocumento, MboConstants.NOACCESSCHECK);
                    mboDocumento.setValue("SNNUMDOC", hospede.getIdentidadeNumero(), MboConstants.NOACCESSCHECK);
                    mboDocumento.setValue("SNORGEXP", hospede.getIdentidadeOrgao(), MboConstants.NOACCESSCHECK);
                    mboDocumento.setValue("SNSTATUS", "ATIVO", MboConstants.NOACCESSCHECK);

                    System.out.println("antes do save");
                    documentos.save();
                    System.out.println("apos save");
                } catch (Exception e) {
                    System.out.println("--- não foi possível inserir o documento " + e.getMessage());
                    e.getStackTrace();
                }

            }
            if (hospede.getCpf() != null && hospede.getCpf().length() > 0) {
                try {
                    System.out.println("---------------- Documento CPF--------------------------");
                    System.out.println("CPF " + Uteis.getApenasNumeros(hospede.getCpf()));
                    mboDocumento = documentos.add();
                    System.out.println("PessoaID " + mboPessoa.getString("PERSONID"));
                    mboDocumento.setValue("PERSONID", mboPessoa.getString("PERSONID"), MboConstants.NOACCESSCHECK);


                    mboDocumento.setValue("SNTIPDOC", "CPF", MboConstants.NOACCESSCHECK);
                    mboDocumento.setValue("SNNUMDOC", Uteis.getApenasNumeros(hospede.getCpf()), MboConstants.NOACCESSCHECK);
                    mboDocumento.setValue("SNSTATUS", "ATIVO", MboConstants.NOACCESSCHECK);
                    System.out.println("antes do save");
                    documentos.save();
                    System.out.println("apos save");
                } catch (Exception e) {
                    System.out.println("--- não foi possível inserir o cpf " + e.getMessage());
                    e.getStackTrace();
                }
            }



            //EMAIL
            email(mboPessoa, hospede);

            //TELEFONE
            System.out.println("----------------------TELEFONE--------------");
            if (hospede.getTelefone() != null && hospede.getTelefone().length() > 0) {
                try {
                    System.out.println("TELEFONE");
                    mboFone = fones.add();
                    mboFone.setValue("PERSONID", mboPessoa.getString("PERSONID"), MboConstants.NOACCESSCHECK);
                    mboFone.setValue("BGNUCODDDI", hospede.getTelefoneDdi(), MboConstants.NOACCESSCHECK);
                    mboFone.setValue("BGNUCODDDD", hospede.getTelefoneDdd(), MboConstants.NOACCESSCHECK);
                    mboFone.setValue("PHONENUM", hospede.getTelefone(), MboConstants.NOACCESSCHECK);
                    mboFone.setValue("BGSTCODTIPCON", "PESSOAL", MboConstants.NOACCESSCHECK);
                    //mboFone.setValue("TYPE", "HOME", MboConstants.NOACCESSCHECK);
                    mboFone.setValue("BGSTCODSTATUS", "ATIVO", MboConstants.NOACCESSCHECK);
                    System.out.println("TELEFONE FIM");
                    fones.save();
                    System.out.println("TELEFONE SAVE");
                } catch (Exception e) {
                    System.out.println("--- não foi possível inserir o telefone " + e.getMessage());
                    e.getStackTrace();
                }
            }
            if (hospede.getCelular() != null && hospede.getCelular().length() > 0) {
                try {
                    System.out.println("CELULAR");
                    mboFone = fones.add();
                    mboFone.setValue("PERSONID", mboPessoa.getString("PERSONID"), MboConstants.NOACCESSCHECK);
                    mboFone.setValue("BGNUCODDDI", hospede.getCelularDdi(), MboConstants.NOACCESSCHECK);
                    mboFone.setValue("BGNUCODDDD", hospede.getCelularDdd(), MboConstants.NOACCESSCHECK);
                    mboFone.setValue("PHONENUM", hospede.getCelular(), MboConstants.NOACCESSCHECK);
                    mboFone.setValue("BGSTCODTIPCON", "PESSOAL", MboConstants.NOACCESSCHECK);
                    mboFone.setValue("TYPE", "CELULAR", MboConstants.NOACCESSCHECK);
                    mboFone.setValue("BGSTCODSTATUS", "ATIVO", MboConstants.NOACCESSCHECK);
                    System.out.println("CELULAR FIM");
                    fones.save();
                    System.out.println("CELULAR SAVE");
                } catch (Exception e) {
                    System.out.println("--- não foi possível inserir o celular " + e.getMessage());
                    e.getStackTrace();
                }
            }


            //ENDEREÇO
            endereco(mboPessoa, hospede);

        } else {
            System.out.println("EDITAR");
            System.out.println("documento.personid " + mboDocumento.getString("PERSONID"));
            pessoas.setWhere("personid = '" + mboDocumento.getString("PERSONID") + "'");
            pessoas.reset();
            retorno = mboDocumento.getString("PERSONID");
            mboPessoa = pessoas.getMbo(0);
            //ENDEREÇO
            endereco(mboPessoa, hospede);
        }


        return retorno;


    }

    public static String lpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = filler + valueToPad;
        }
        return valueToPad;
    }

    private void endereco(MboRemote mboPessoa, Hospede hospede) throws MXException, RemoteException {
        MboSet enderecos;
        enderecos = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("BGTBENDE01", sessionContext.getUserInfo());

        //ENDERECO
        System.out.println("----------------------ENDERECO--------------1000");
        enderecos.setWhere("PERSONID = '" + mboPessoa.getString("PERSONID") + "' AND ISPRIMARY = 1");
        enderecos.reset();

        System.out.println("---- encontrados " + enderecos.count());

        enderecos.deleteAll(MboConstants.NOACCESSCHECK);
        System.out.println("---deleteAndRemoveAll");
        enderecos.save();
        System.out.println("---dave");


        try {
            MboRemote mboEndereco = enderecos.add();
            mboEndereco.setValue("PERSONID", mboPessoa.getString("PERSONID"), MboConstants.NOACCESSCHECK);
            mboEndereco.setValue("BGSTCODCEP2", hospede.getCep(), MboConstants.NOACCESSCHECK);
            mboEndereco.setValue("BGSTDSCLOGRADOURO", hospede.getResidenciaLogradouro(), MboConstants.NOACCESSCHECK);
            // TODO não tem equivalencia mboEndereco.setValue("BGNUNUMNUM", hospede.get(), MboConstants.NOACCESSCHECK);
            // TODO não tem equivalencia mboEndereco.setValue("BGSTDSCBAIRRO", hospede.get(), MboConstants.NOACCESSCHECK);
            mboEndereco.setValue("BGSTDSCPAIS", hospede.getPais().getDescricao(), MboConstants.NOACCESSCHECK);
            mboEndereco.setValue("BGSTDSCESTADO", hospede.getEstado().getDescricao(), MboConstants.NOACCESSCHECK);
            mboEndereco.setValue("ISPRIMARY", true, MboConstants.NOACCESSCHECK);
            String cidade = "";

            if (hospede.getPais().getDescricao().equalsIgnoreCase("brasil")) {
                cidade = "BGSTDSCCIDADE";
            } else {
                cidade = "BGSTDSCCIDADE2";
            }
            if (hospede.getDsCidade() != null && hospede.getDsCidade().length() > 0) {
                mboEndereco.setValue(cidade, hospede.getDsCidade(), MboConstants.NOACCESSCHECK);
            } else {
                mboEndereco.setValue(cidade, hospede.getCidade().getDescricao(), MboConstants.NOACCESSCHECK);
            }
            mboEndereco.setValue("BGSTCODTIPCON", "RESIDENCIAL", MboConstants.NOACCESSCHECK);
            mboEndereco.setValue("BGSTCODSTATUS", "ATIVO", MboConstants.NOACCESSCHECK);
            System.out.println("endereco antes de save");
            enderecos.save();
            System.out.println("endereco apos save");
        } catch (Exception e) {
            System.out.println("--- não foi possível inserir o endereco " + e.getMessage());
            e.getStackTrace();
        }
    }

    private void email(MboRemote mboPessoa, Hospede hospede) {
        System.out.println("----------------------EMAIL---------------------");
        if (hospede.getEmail() != null && hospede.getEmail().length() > 0) {
            try {
                MboSet emails;
                emails = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("EMAIL", sessionContext.getUserInfo());
                emails.setWhere("PERSONID = '" + mboPessoa.getString("PERSONID") + "' AND ISPRIMARY = 1");
                emails.reset();

                System.out.println("---- encontrados " + emails.count());

                emails.deleteAll(MboConstants.NOACCESSCHECK);
                System.out.println("---deleteAndRemoveAll");
                emails.save();

                MboRemote mboEmail;
                System.out.println("add");
                mboEmail = emails.add();
                System.out.println("vals");
                mboEmail.setValue("PERSONID", mboPessoa.getString("PERSONID"), MboConstants.NOACCESSCHECK);

                mboEmail.setValue("EMAILADDRESS", hospede.getEmail(), MboConstants.NOACCESSCHECK);
                mboEmail.setValue("TYPE", "PESSOAL", MboConstants.NOACCESSCHECK);
                mboEmail.setValue("BGSTCODSTATUS", "ATIVO", MboConstants.NOACCESSCHECK);
                mboEmail.setValue("ISPRIMARY", true, MboConstants.NOACCESSCHECK);

                System.out.println("emails save");
                emails.save();
                System.out.println("emails save fim ");
            } catch (Exception e) {
                System.out.println("--- não foi possível inserir o email " + e.getMessage());
                e.getStackTrace();
            }

        }

    }
}
