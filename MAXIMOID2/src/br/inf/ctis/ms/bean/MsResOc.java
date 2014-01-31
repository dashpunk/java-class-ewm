package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsResOc extends AppBean {

    public MsResOc() {
        super();
    }

    @Override
    public void initialize() throws MXException, RemoteException {
        super.initialize();
        
        getMbo().setValue("MSALDSCDESPACHO1_LONGDESCRIPTION", "\tCumpre-nos informar que os medicamentos SULFATO DE HIDROXICLOROQUINA 400 MG, OPTIVE UD, PENTOXIFILINA 400 MG, VACINA AQUOSA DE INALANTES + CANDIDINA 10-3 0,5 ML, TOCILIZUMABE 20 MG/ML EM 10 ML, NITRAZEPAM 5 MG, FENOBARBITAL 0,04, CARBONATO DE LÍTIO 300 MG, BACLOFENO 10 MG e SOMATROPINA 15 MG não serão adquiridos por falta de interesse na cotação pelos fornecedores.\n\t Cumpre-nos informar que o suplemento alimentar PREGOMIN PEPTI e os correlatos FRALDAS GERIÁTRICSA TAMANHO G e FRALDAS DESCARTÁVEIS INFANTIL TAMANHO XG	, objetos da demanda judicial em tela, não constam na tabela CMED uma vez que esta só é aplicada a medicamentos, o que impossibilita a análise quanto ao atendimento do CAP, obedecendo ao disposto na RESOLUÇÃO CMED Nº 3, de 02 de março de 2011.\n\t Cumpre-nos informar que a proposta da empresa HOSP-LOG, CNPJ 06.081.203/0001-36, foi desconsiderada, tendo em vista o impedimento de licitar com órgãos do Governo Federal, conforme informação do SICAF – Sistema de Cadastramento Unificado de Fornecedores à fl. 109. Informamos ainda que a proposta desconsiderada foi substituída pela da empresa NORPROD, CNPJ 07.803.384/0002-00, conforme apresentada às fls. 110-112.");
    }
}
