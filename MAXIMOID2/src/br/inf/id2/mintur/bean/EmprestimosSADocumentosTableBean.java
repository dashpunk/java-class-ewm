package br.inf.id2.mintur.bean;

import psdi.server.MXServer;
import psdi.webclient.system.beans.DataBean;
import psdi.workflow.WorkFlowServiceRemote;

/**
 * <h4>Descrição</h4> Sobrescrita de {@linkplain ROUTEWF() método ROUTEWF()}
 * para que se inicie o fluxo MXPREMDO01 <h4>Notas</h4>
 *
 * <h4>Referências</h4>
 *
 * <h4>Revisões</h4>
 *
 * <h4>Aplicações</h4> MXAPEMP - Emprestimos SA
 *
 *
 * @author Ricardo Gomes
 * @since 1.5
 * @since 20/01/2012 17:40
 * @version 1.0
 * {@link #ROUTEWF() routeWf}
 * @see
 *
 *
 */
public class EmprestimosSADocumentosTableBean extends DataBean {

    public EmprestimosSADocumentosTableBean() {
    }

    public int ROTEARLINHA() {
        System.out.println("---ROTEARLINHA");
        try {
            System.out.println("---ROTEARLINHA count " + getMboSet().count());
            System.out.println("---ROTEARLINHA currentPosition " + getMboSet().getCurrentPosition());
            System.out.println("---ROTEARLINHA mboAtual " + getMboSet().getMbo(getMboSet().getCurrentPosition()).getDate("MXDTARET"));

            MXServer mxs;
            try {
                System.out.println("---ROTEARLINHA a");
                mxs = MXServer.getMXServer();
                System.out.println("---ROTEARLINHA b");
                WorkFlowServiceRemote wsrmt = (WorkFlowServiceRemote) mxs.lookup("WORKFLOW");
                System.out.println("---ROTEARLINHA c");
//            WOActivityRemote mbo = (WOActivityRemote) getMbo();
                System.out.println("---ROTEARLINHA d");
                wsrmt.initiateWorkflow("MXPREMDO01", getMboSet().getMbo(getMboSet().getCurrentPosition()));
                System.out.println("---ROTEARLINHA e");
                fireStructureChangedEvent();
                System.out.println("---ROTEARLINHA f");
                refreshTable();
                System.out.println("---ROTEARLINHA g");
                this.sessionContext.queueRefreshEvent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return EVENT_HANDLED;
    }

    /**
     * Sobrescrita do método para iniciar o fluxo MXPREMDO01
     *
     * @return
     */
    public int ROUTEWF() {
        System.out.println("---ROUTEWF");
        try {
            System.out.println("---ROUTEWF count " + getMboSet().count());
            System.out.println("---ROUTEWF currentPosition " + getMboSet().getCurrentPosition());
            System.out.println("---ROUTEWF mboAtual " + getMboSet().getMbo(getMboSet().getCurrentPosition()).getDate("MXDTARET"));

            MXServer mxs;
            try {
                System.out.println("---ROUTEWF a");
                mxs = MXServer.getMXServer();
                System.out.println("---ROUTEWF b");
                WorkFlowServiceRemote wsrmt = (WorkFlowServiceRemote) mxs.lookup("WORKFLOW");
                System.out.println("---ROUTEWF c");
//            WOActivityRemote mbo = (WOActivityRemote) getMbo();
                System.out.println("---ROUTEWF d");
                wsrmt.initiateWorkflow("MXPREMDO01", getMboSet().getMbo(getMboSet().getCurrentPosition()));
                System.out.println("---ROUTEWF e");
                fireStructureChangedEvent();
                System.out.println("---ROUTEWF f");
                refreshTable();
                System.out.println("---ROUTEWF g");
                this.sessionContext.queueRefreshEvent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return 1;

        //        MXServer mxs;
//        try {
//            mxs = MXServer.getMXServer();
//            WorkFlowServiceRemote wsrmt = (WorkFlowServiceRemote) mxs.lookup("WORKFLOW");
//
////            WOActivityRemote mbo = (WOActivityRemote) getMbo();
//            wsrmt.initiateWorkflow(getMbo().getString("MXPREMDO01"), getMbo());
//            fireStructureChangedEvent();
//            refreshTable();
//            this.sessionContext.queueRefreshEvent();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 1;
    }
}
