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
        
        getMbo().setValue("MSALDSCDESPACHO1_LONGDESCRIPTION", "\tCumpre-nos informar que os medicamentos SULFATO DE HIDROXICLOROQUINA 400 MG, OPTIVE UD, PENTOXIFILINA 400 MG, VACINA AQUOSA DE INALANTES + CANDIDINA 10-3 0,5 ML, TOCILIZUMABE 20 MG/ML EM 10 ML, NITRAZEPAM 5 MG, FENOBARBITAL 0,04, CARBONATO DE L�TIO 300 MG, BACLOFENO 10 MG e SOMATROPINA 15 MG n�o ser�o adquiridos por falta de interesse na cota��o pelos fornecedores.\n\t Cumpre-nos informar que o suplemento alimentar PREGOMIN PEPTI e os correlatos FRALDAS GERI�TRICSA TAMANHO G e FRALDAS DESCART�VEIS INFANTIL TAMANHO XG	, objetos da demanda judicial em tela, n�o constam na tabela CMED uma vez que esta s� � aplicada a medicamentos, o que impossibilita a an�lise quanto ao atendimento do CAP, obedecendo ao disposto na RESOLU��O CMED N� 3, de 02 de mar�o de 2011.\n\t Cumpre-nos informar que a proposta da empresa HOSP-LOG, CNPJ 06.081.203/0001-36, foi desconsiderada, tendo em vista o impedimento de licitar com �rg�os do Governo Federal, conforme informa��o do SICAF � Sistema de Cadastramento Unificado de Fornecedores � fl. 109. Informamos ainda que a proposta desconsiderada foi substitu�da pela da empresa NORPROD, CNPJ 07.803.384/0002-00, conforme apresentada �s fls. 110-112.");
    }
}
