/**
 * 
 */
package server;


import lagern.FachPOA;
import lagern.FachPackage.EInvalidCount;
import lagern.FachPackage.ENotEnoughPieces;



/**
 * 
 * @author Martin Schindler, Sebastian Mueller
 * 
 */
public class LagerFachImpl extends FachPOA {
    
    private String name;
    private LagerImpl store;
    private int count;  // stored pieces
    
    
    
    /**
     * 
     * @param name
     */
    public LagerFachImpl(String name) {
        this.name = name;
    }//LagerFachImpl
    
    /**
     * 
     * @param name
     * @param store
     */
    public LagerFachImpl(String name, LagerImpl store) {
        this.name = name;
        this.store = store;
        this.count = 0;
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
        if(anzahl >= 0 && (this.count + anzahl) <= Integer.MAX_VALUE){
            this.count += anzahl;
        	store.informiereMonitore(String.format("name: %s, %d pieces stored. new number of pieces: %d", this.name, anzahl, this.count));
        }else{
        	store.informiereMonitore(String.format("ERROR: Invalid argument. name: %s, no pieces stored. number of pieces: %d", this.name, this.count));
            throw new EInvalidCount();
        }//if
    }//einlagern

    
    /* (non-Javadoc)
     * @see lagern.FachOperations#auslagern(int)
     */
    @Override
    public void auslagern(int anzahl) throws EInvalidCount, ENotEnoughPieces {
        String msg;
        if(anzahl >= 0)
            if(anzahl <= this.count){
                this.count -= anzahl;
                msg = String.format("name: %s, %d pieces removed. number of pieces: %d", this.name, anzahl, this.count);
        		store.informiereMonitore(msg);
            }else{
                msg = String.format("ERROR: %d > %d(stored pieces) name: %s, no pieces removed. number of pieces: %d", anzahl, this.count, this.name, this.count);
            	store.informiereMonitore(msg);
                throw new ENotEnoughPieces(msg);
            }//if
        else{
            msg = String.format("ERROR: Invalid argument. name: %s, no pieces removed. number of pieces: %d", this.name, this.count);
        	store.informiereMonitore(msg);
            throw new EInvalidCount(msg);
        }//if
    }//auslagern
    
    
    public void setLager(LagerImpl lager) {
		this.store = lager;
	}//setLager

}//LagerFachImpl
