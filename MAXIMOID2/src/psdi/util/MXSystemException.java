package psdi.util;


// Referenced classes of package psdi.util:
//            MXException

public class MXSystemException extends MXException
{

    public MXSystemException(String eg, String ek)
    {
        super(eg, ek);
    }

    public MXSystemException(String eg, String ek, Object params[])
    {
        super(eg, ek, params);
    }

    public MXSystemException(String eg, String ek, Throwable t)
    {
        super(eg, ek, t);
    }

    public MXSystemException(String eg, String ek, Object p[], Throwable t)
    {
        super(eg, ek, p, (t.getMessage().indexOf("ORA-00001") != -1 ? null : t));
        System.out.println("___MXSystemException(?,?,?,?) "+t.getMessage().indexOf("unique constraint"));
        
    }
    
//    private String getValorEg(Throwable t) {
//        super()
//    }

    static final long serialVersionUID = 0x4de18e21e6d8cc85L;
}
