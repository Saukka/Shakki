
package shakki.nappulat;



public class EpäNappula extends Nappula{
    
    public EpäNappula (int x, int y) {
        super(-1, x, y, -1);
        
        this.numero = 0;
        this.arvo = 0;
    }
    
    @Override
    public EpäNappula kopioi() {
        
        EpäNappula n = new EpäNappula(x, y);
        
        return n;
        
    }
    
    
}
