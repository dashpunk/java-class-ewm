package br.inf.id2.mintur.field;

import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Ricardo S Gomes
 */
public class MttbfisconDsnumpro extends MboValueAdapter {

    public MttbfisconDsnumpro(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("------ MttbfisconDsnumpro() 1904");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        System.out.println("------ MttbfisconDsnumpro validate()");
        executaCalculo();
    }

    private void executaCalculo() {
        try {
            System.out.println("------ MttbfisconDsnumpro executaCalculo()");
            System.out.println("--- antes do relacionamento MXRL01TBDADOPRO ");
            System.out.println("--- relacionamento MXRL01TBDADOPRO count() " + getMboValue().getMbo().getMboSet("MXRL01TBDADOPRO").count());

            Long itprpid = null;
            if (getMboValue().getMbo().getMboSet("MXRL01TBDADOPRO").count() > 0) {
                System.out.println("--- antes do itprpid");
                itprpid = getMboValue().getMbo().getMboSet("MXRL01TBDADOPRO").getMbo(0).getLong("ITPRPID");
                System.out.println("--- apos do itprpid " + itprpid);
                getMboValue("ITPRPID").setValue(itprpid, MboConstants.NOACCESSCHECK);
                System.out.println("--- apos setvalue ");

            }
        } catch (MXException ex) {
            ex.getStackTrace();
            Logger.getLogger(MttbfisconDsnumpro.class.getName()).log(Level.SEVERE, null, ex);

        } catch (RemoteException ex) {
            ex.getStackTrace();
            Logger.getLogger(MttbfisconDsnumpro.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
}
