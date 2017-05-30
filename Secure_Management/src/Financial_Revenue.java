
import java.io.Serializable;
import java.util.Date;





public class Financial_Revenue implements Serializable
{
    String DESCRIPTION ;
    double AMOUNT ;
    Date DATE ;
    
    //Constructor
    public Financial_Revenue ( String DESCRIPTION, double AMOUNT, Date DATE )
    {
       this.DESCRIPTION = DESCRIPTION ;
       this.AMOUNT = AMOUNT ;
       this.DATE = DATE ; 
    }
    
    //Setters for Modify
    public void Set_DESCRIPTION ( String DESCRIPTION )
    {
        this.DESCRIPTION = DESCRIPTION ;
    }
    
    public void Set_AMOUNT ( double AMOUNT )
    {
        this.AMOUNT = AMOUNT ;
    }
    
    public void Set_DATE ( Date DATE )
    {
        this.DATE = DATE ;
    }
    
    //Getters for See Report
    public String Get_DESCRIPTION ( )
    {
        return this.DESCRIPTION ;
    }
    
    public double Get_AMOUNT ( )
    {
        return this.AMOUNT ;
    }
    
    public Date Get_DATE ( )
    {
        return this.DATE ;
    }
    
}
