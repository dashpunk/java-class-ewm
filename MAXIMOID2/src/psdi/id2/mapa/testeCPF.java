package psdi.id2.mapa;

public class testeCPF {

    private final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public testeCPF() {
        testeCPF testemain = new testeCPF();
    }

    public static void main(String args[]) {
        /*
        try
        {
        if(CNPJ("06128237000139") || CNPJ("06.128.237/0001-39"))
        {
        String cnpj = "06128237000139";
        cnpj = cnpj.replaceAll("/","");
         */
        //javax.swing.text.MaskFormatter formatCPF = new javax.swing.text.MaskFormatter("**.***.***/****-**");
				/*
        formatCPF.setValueContainsLiteralCharacters(false);
        System.out.println(formatCPF.valueToString(cnpj));
        }
        }
        catch (Exception e)
        {
        System.out.println(e.getMessage());
        }
         */
        String NUMGTA = "000000";
        NUMGTA = NUMGTA.substring(0, NUMGTA.length() - ("876").length()) + "876";
        System.out.println(NUMGTA);

        java.util.Date data = new java.util.Date();
        String DIA = ("00").substring(0, ("00").length() - (data.getDate() + "").length()) + data.getDate();
        String MES = ("00").substring(0, ("00").length() - (data.getMonth() + "").length()) + data.getMonth();
        String ANO = (data.getYear() + 1900) + "";
        System.out.println(DIA + MES + ANO);

        int j = 2;
        int res = 0;
        Integer digito;
        String ver1 = "";
        for (int i = ver1.length() - 1; i >= 0; i--) {
            digito = new Integer(ver1.substring(i, i + 1));
            res = res + (digito.intValue() * j);
            j++;
            if (j > 9) {
                j = 2;
            }
        }
        res = (11 - (res % 11)) > 9 ? 0 : 11 - (res % 11);
        System.out.println(res);
    }

    private static boolean CNPJ(String str_cnpj) {
        int soma = 0, aux, dig;

        str_cnpj = str_cnpj.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "");
        if (str_cnpj.length() != 14) {
            return false;
        }

        String cnpj_calc = str_cnpj.substring(0, 12);

        char[] chr_cnpj = str_cnpj.toCharArray();

        // Primeira parte
        for (int i = 0; i < 4; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
            }
        }

        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

        // Segunda parte
        soma = 0;
        for (int i = 0; i < 5; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
            }
        }

        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

        return str_cnpj.equals(cnpj_calc);
    }
}
