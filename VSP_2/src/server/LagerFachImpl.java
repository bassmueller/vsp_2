package server;

import lagern.FachPOA;
import lagern.FachPackage.EInvalidCount;
import lagern.FachPackage.ENotEnoughPieces;


/**
 * @author 
 *
 */
public class LagerFachImpl extends FachPOA {
    
    private String name;
    private LagerImpl store;
    
    private int count;  // stored pieces
    
    public LagerFachImpl(String name) {
        this.name = name;
    }//LagerFachImpl
    
    public LagerFachImpl(String name, LagerImpl store) {
        this.name = name;
        this.store = store;
    }//LagerFachImpl
    
    /* (non-Javadoc)
     * @see lagern.FachOperations#anzahl()
     */
    @Override
    public int anzahl() {
        return this.count;
    }//anzahl

    
    /* (non-Javadoc)
     * @see lagern.FachOperations#name()
     */
    @Override
    public String name() {
        return this.name;
    }//name

    
    /* (non-Javadoc)
     * @see lagern.FachOperations#einlagern(int)
     */
    @Override
    public void einlagern(int anzahl) throws EInvalidCount {
        if(anzahl >= 0 && (this.count + anzahl) <= Integer.MAX_VALUE)
            this.count += anzahl;
        else
            throw new EInvalidCount();
        store.informiereMonitore(String.format("name: %s, %d pieces stored. new number of pieces: %d", this.name, anzahl, this.count));
    }//einlagern

    
    /* (non-Javadoc)
     * @see lagern.FachOperations#auslagern(int)
     */
    @Override
    public void auslagern(int anzahl) throws EInvalidCount, ENotEnoughPieces {
        if(anzahl >= 0)
            if(anzahl <= this.count)
                this.count -= anzahl;
            else
                throw new ENotEnoughPieces();
        else
            throw new EInvalidCount();
        	store.informiereMonitore(String.format("name: %s, %d pieces removed. number of pieces: %d", this.name, anzahl, this.count));
    }//auslagern
    
    public void setLager(LagerImpl lager) {
		this.store = lager;
	}

}//LagerFachImpl