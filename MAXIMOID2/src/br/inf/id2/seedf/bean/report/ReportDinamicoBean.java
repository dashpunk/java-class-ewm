/*
 * Esta classe  a bean que apia a tela de passagem de parmetros para o relatrio e faz a chamada do servlet.
 * Para que essa classe funcione, algumas interaes em tela devem ocorrer, como por exemplo passar o nome do relatrio incluindo
 * a extenso .jasper no atributo "Valor do Evento" no boto de OK da caixa de dilogo
 */
/**
 *
 * @author Marcelo Lima
 */
package br.inf.id2.seedf.bean.report;

import psdi.util.*;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.*;

import java.util.*;
import java.rmi.*;

public class ReportDinamicoBean extends AppBean {

    public ReportDinamicoBean() {
    }

    public void Imprimir() {
        try {
            //Map reportParams = new HashMap();
            Hashtable reportParams = new Hashtable();
            java.lang.String[] attrs = getAttributes();
            String resultado = "";
            String atributo = "";

            boolean primeiro = true;

            for (int i = 0; i < attrs.length; i++) {
                System.out.println("----i " + i);
                if (!getMbo().isNull(attrs[i])) {
                    if (attrs[i].equals("AREA_SUPERIOR")) {
                        atributo = "aresup.description";
                    } else if (attrs[i].equals("INSTITUICAO_EDUCACIONAL")) {
                        atributo = "insedu.description";
                    } else if (attrs[i].equals("SEEANOLET")) {
                        atributo = "estmat.seeanolet";
                    } else if (attrs[i].equals("SEECURSO")) {
                        atributo = "ofeie.seecurso";
                    } else if (attrs[i].equals("SIGLA_ETA_EDU")) {
                        atributo = "etaedu.see_sigla";
                    } else if (attrs[i].equals("SIGLA_MOD_ENS")) {
                        atributo = "modens.see_sigla";
                    } else if (attrs[i].equals("TURMA")) {
                        atributo = "turma.seesigla";
                    } else if (attrs[i].equals("TURNO")) {
                        atributo = "turno.description";
                    } else if (attrs[i].equals("TURNO_OFERTA")) {
                        atributo = "ofecalesc.seeturno";
                    }

                    System.out.println("--------------->>>> " + attrs[i]);
                    System.out.println("--------------->>>> " + getMbo().getString(attrs[i]));

                    if (!primeiro) {
                        resultado = resultado + " and " + atributo + " = #" + getMbo().getString(attrs[i]) + "#";
                        System.out.println("---result " + resultado);
                    } else {
                        resultado =  atributo + " = #" + getMbo().getString(attrs[i]) + "#";
                        System.out.println("---result " + resultado);
                        primeiro = false;
                    }
                }
            }
            System.out.println("---Resultado " + resultado);
            reportParams.put("CLAUSULA", resultado);
            System.out.println("--- ta indo " + reportParams.get("CLAUSULA"));
            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);
            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}
