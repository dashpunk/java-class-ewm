/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package psdi.id2.mapa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ID2
 */
public class Estados {
    List estados;

    public Estados() {
        estados = new ArrayList();
    }

    public List obterEstados() {
        estados.add("DF");
        estados.add("RS");
        estados.add("SP");
        estados.add("RN");
        estados.add("GO");
        estados.add("SC");
        return estados;
    }

}
