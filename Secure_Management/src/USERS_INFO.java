
import java.io.Serializable;








public class USERS_INFO implements Serializable
{
    
    
    
    //Fields
    String LOGIN_NAME ,
           NAME_SURNAME ;
    
    byte [ ] SALT ,
             ENCRYPTED_PASSWORD ;
    
    
    //Default
    public USERS_INFO ( )
    {
        this.LOGIN_NAME = null ;
        this.NAME_SURNAME = null ;
        this.SALT = null ;
        this.ENCRYPTED_PASSWORD = null ;
    }
    
    //Overload Constructor
    public USERS_INFO ( String LOGIN_NAME, String NAME_SURNAME, byte [] SALT, byte [] ENCRYPTED_PASSWORD )
    {
        this.LOGIN_NAME = LOGIN_NAME ;
        this.NAME_SURNAME = NAME_SURNAME ;
        this.SALT = SALT ;
        this.ENCRYPTED_PASSWORD = ENCRYPTED_PASSWORD ;
    }
    
    
    //SETTERS
    public void SET_LOGIN_NAME ( String LOGIN_NAME )
    {
        this.LOGIN_NAME = LOGIN_NAME ;
    }
    
    public void SET_NAME_SURNAME ( String NAME_SURNAME )
    {
        this.NAME_SURNAME = NAME_SURNAME ;
    }
    
    public void Set_SALT ( byte [] SALT )
    {
        this.SALT = SALT ;
    }
    
    public void SET_LOGIN_NAME ( byte [] ENCRYPTED_PASSWORD )
    {
        this.ENCRYPTED_PASSWORD = ENCRYPTED_PASSWORD ;
    }
   
    
    
    //GETTERS
    public String GET_LOGIN_NAME ( )
    {
        return this.LOGIN_NAME ;
    }
    
    public String GET_NAME_SURNAME ( )
    {
        return this.NAME_SURNAME ;
    }
   
    public byte [] GET_SALT ( )
    {
        return this.SALT ;
    }
    
    public byte [] GET_ENCRYPTED_PASSWORD ( )
    {
        return this.ENCRYPTED_PASSWORD ;
    }
    
    public String toString ( )
    {
        return " LOGIN_NAME -> " + this.GET_LOGIN_NAME ( ) + "  , NAME_SURNAME -> " + this.GET_NAME_SURNAME ( ) + " , SALT -> " + this.GET_SALT ( ) + " , ENCRYPTED_PASSWORD -> " + this.GET_ENCRYPTED_PASSWORD ( ) ;
    }
}
