package com.clnn;

public class TestThreadLocal {

    public static final ThreadLocal<CNumber> TL = new ThreadLocal();

    public static final ThreadLocal<CNumber> TLI = ThreadLocal.withInitial(()->new CNumber());

    public static void setTL(CNumber cNumber){
        TL.set(cNumber);
    }
    public static CNumber getTL(){
        return TL.get();
    }

    public static void setTLI(CNumber cNumber){
        TLI.set(cNumber);
    }
    public static CNumber getTLI(){
        return TLI.get();
    }
}
