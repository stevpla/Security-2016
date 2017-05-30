
import javax.swing.* ;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



public class Authentication_Window extends JFrame implements ActionListener
{
    private JLabel Label_UserName, Label_Password, Wrong_Password, Wrong_User_Name ;
    private JTextField Field_UserName ;
    private JPasswordField Field_Password ;
    private JButton LOG_IN, CLEAR ;
    private String IDENTIFIER_FINAL = null ;
    private FileInputStream INPUT_STREAM = null ;
    private ObjectInputStream INPUT_STREAM_2 = null ;
    private USERS_INFO OBJ = null ;
    private int FAIL_COUNTER = 0 ;
    private Cipher CIPHER  = null ;
    private static String PATHNAME_USER = null ;
    private PrivateKey PRIVATE_KEY_TEMP = null ;
    
     //Dhmiourgos
    public Authentication_Window ( String PARAMETER_IDENTIFIER )
    {
        super  ( "Authenticate" ) ;
        setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE ) ;   //Close Current Window
        setSize ( 600, 350 ) ;
        setLocationRelativeTo ( null ) ;
        setVisible ( true ) ;
        
        IDENTIFIER_FINAL = PARAMETER_IDENTIFIER ;  //Save value
         //
        //https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
        //In this technique a WindowListener interface implentation is added to the frame, where the listener has a method, windowClosing(), that is called when the frame is closed.
        //In practice, on overrides the windowClosing() method of WindowAdapter, a no-op implementation of WindowListener.   This way, one doesn't have to worry about all the other methods of the WindowListener.
        this.addWindowListener ( new java.awt.event.WindowAdapter ( ) 
        {
              @Override
             public void windowClosing(java.awt.event.WindowEvent windowEvent) 
             {
                      Main_Menu a = new Main_Menu ( ) ;
             }
        } ); 
        //
     
        // 1st Row
        Label_UserName = new JLabel ( "Enter User name : " ) ;
        Label_UserName.setForeground ( Color.BLACK ) ;
        Label_UserName.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        Label_UserName.setBounds ( 10, 20, 900, 40 ) ;
        
        Field_UserName = new JTextField ( 20 ) ;
        Field_UserName.setForeground ( Color.BLACK ) ;
        Field_UserName.setBounds ( 218, 26, 220, 27 ) ;
        
        Wrong_Password = new JLabel ( "" ) ;
        Wrong_Password.setForeground ( Color.RED ) ;
        Wrong_Password.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        Wrong_Password.setBounds ( 218, 120, 180, 40 ) ;
        
        Wrong_User_Name = new JLabel ( "" ) ;
        Wrong_User_Name.setForeground ( Color.RED ) ;
        Wrong_User_Name.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        Wrong_User_Name.setBounds ( 218, 40, 180, 40 ) ;
        
        // 2nd row
        Label_Password = new JLabel ( " Enter password : " ) ;
        Label_Password.setForeground ( Color.BLACK ) ;
        Label_Password.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        Label_Password.setBounds ( 10, 100, 900, 40 ) ;
        
        Field_Password = new JPasswordField ( 20 ) ;
        Field_Password.setForeground ( Color.BLACK ) ;
        Field_Password.setBounds ( 218, 106, 220, 27 ) ;
        
        // 3rd row
        LOG_IN = new JButton ( " Log in " ) ;
        LOG_IN.setForeground ( Color.BLACK ) ;
        LOG_IN.setBounds ( 150, 200, 140, 40 ) ;
        
        CLEAR = new JButton ( " Clear " ) ;
        CLEAR.setForeground ( Color.BLACK ) ;
        CLEAR.setBounds ( 300, 200, 140, 40 ) ;
        
        // Manager Layout ΝΟ
        Container pane = getContentPane ( ) ;
        
        pane.setLayout ( null ) ;
        
        // Additions
        pane.add ( Label_UserName ) ;
        pane.add ( Label_Password ) ;
        pane.add ( Field_UserName ) ;
        pane.add ( Field_Password ) ;
        pane.add ( LOG_IN ) ;
        pane.add ( CLEAR ) ;
        pane.add ( Wrong_Password ) ;
        pane.add ( Wrong_User_Name ) ;

        setContentPane ( pane ) ;
        LOG_IN.addActionListener ( this ) ;
        CLEAR.addActionListener ( this ) ;
    }  //Telos dhmioyrgoy
    
    
    
    
    public static String FIND_PATHNAME ( )
    {
        return PATHNAME_USER ;
    }
    
    
    
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
        if ( source == LOG_IN )
        {
            if ( Field_UserName.getText ( ).equals ( "" )   ||   Field_Password.getText ( ).equals ( "" ) )
            {
                JOptionPane.showMessageDialog ( null, "Please give username & password", "WARNING", JOptionPane.WARNING_MESSAGE, null ) ;
            }
            else
            { 
              //First see if the file exists to check the Username
              File TEST_IF_THERE_IS_USERS_INFO = new File ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\USERS_INFO.data" ) ;
              
              if ( TEST_IF_THERE_IS_USERS_INFO.exists ( ) )
              {  
                try
                {
                    INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\USERS_INFO.data" ) ;
                    while ( true )  //until reach end of file read every OBJECT and see the username if is equal with username by Guest
                    {
                        INPUT_STREAM_2 = new ObjectInputStream ( INPUT_STREAM ) ;
                        OBJ = ( USERS_INFO ) INPUT_STREAM_2.readObject ( ) ;
                        if ( OBJ.GET_LOGIN_NAME ( ).equals ( Field_UserName.getText ( ) ) )
                        {
                            FAIL_COUNTER ++ ;
                            break ;  //Continue ouside loop, indeed there is Guest with this username inside File
                        }
                    }
                }
                catch ( FileNotFoundException FNFE )
                {
                    JOptionPane.showMessageDialog ( null, "Error ->  " + FNFE.getLocalizedMessage ( ), "Exception - FileNotFoundException ",  JOptionPane.ERROR_MESSAGE, null ) ;
                }
                catch ( ClassNotFoundException CNFE )
                {
                    JOptionPane.showMessageDialog ( null, "Error ->  " + CNFE.getLocalizedMessage ( ), "Exception - ClassNotFoundException ",  JOptionPane.ERROR_MESSAGE, null ) ;
                }
                catch ( IOException IOE )
                {
                    //JOptionPane.showMessageDialog ( null, "Error ->  " + IOE.getLocalizedMessage ( ), "Exception - IOException ",  JOptionPane.ERROR_MESSAGE, null ) ;
                }
                
                //Check if username founded in file to say if authenticate was FAILED
                if ( FAIL_COUNTER == 0 )
                {
                    JOptionPane.showMessageDialog ( null, "Sorry but you gave wrong Info or you doesnt exist !!", "ERROR", JOptionPane.ERROR_MESSAGE, null ) ;
                    JOptionPane.showMessageDialog ( null, "Please try again !!", "Info", JOptionPane.INFORMATION_MESSAGE, null ) ;
                    this.dispose ( ) ;
                    Authentication_Window AW = new Authentication_Window ( IDENTIFIER_FINAL ) ;
                }
                else
                {
                    byte [ ] AUTHENTICATE_HASH_VALUE = null ;
                    
                    byte [ ] AUTHENTICATE_SALT = OBJ.GET_SALT ( ) ;   //extract salt of specific USER
                    byte [ ] AUTHENTICATE_PASSWORD_BYTES = Field_Password.getText ( ).getBytes ( ) ;   //Getting bytes amount from String -> PASSWORD
                    
                    //Total BYTE ARRAY
                    byte [ ] AUTHENTICATE_SALT_AND_PASSWORD = new byte [ AUTHENTICATE_SALT.length + AUTHENTICATE_PASSWORD_BYTES.length ] ;
                    for ( int i = 0 ;    i < AUTHENTICATE_SALT_AND_PASSWORD.length ;   ++i )
                    {
                         AUTHENTICATE_SALT_AND_PASSWORD [ i ] = i < AUTHENTICATE_SALT.length ? AUTHENTICATE_SALT [ i ] : AUTHENTICATE_PASSWORD_BYTES [ i - AUTHENTICATE_SALT.length ] ;
                    }
                    
                    MessageDigest MESSAGE_DIGEST = null ;
                    try
                    {
                        MESSAGE_DIGEST = MessageDigest.getInstance ( "SHA-256" ) ;
                    }
                    catch ( NoSuchAlgorithmException ex ) 
                    {
                       JOptionPane.showMessageDialog ( null, "Error -> " + ex.getLocalizedMessage ( ), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    MESSAGE_DIGEST.update ( AUTHENTICATE_SALT_AND_PASSWORD ) ;
                    AUTHENTICATE_HASH_VALUE = MESSAGE_DIGEST.digest ( ) ;  //Finishing HASH - SHA-256
                    
                    StringBuffer sb = new StringBuffer ( ) ;
                    for ( int i = 0 ;   i < AUTHENTICATE_HASH_VALUE.length ;  i++ )
                    {
                        sb.append ( Integer.toString ( ( AUTHENTICATE_HASH_VALUE [ i ] & 0xff ) + 0x100, 16 ).substring ( 1 ) ) ;
                    }
 
                    JOptionPane.showMessageDialog ( null, "HASH_VALUE at AUTHENTICATION =>  " +sb.toString ( ), MESSAGE_DIGEST.toString ( ), JOptionPane.INFORMATION_MESSAGE, null ) ;
                    //must be same with HASH_VALUE at SIGN_UP if the password is the right one
                    //Finishes a multiple-partdecryption operation, depending on how this cipher was initialized.
                    byte [ ] FINAL_DECRYPTED_PASSWORD = null ;
                    try
                    {   
                          //Open stream to read private key from file.data
                          try
                          {
                              this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\PRIVATE_KEY.data" ) ;
                              this.INPUT_STREAM_2 = new ObjectInputStream ( this.INPUT_STREAM ) ;
                              PRIVATE_KEY_TEMP = ( PrivateKey ) this.INPUT_STREAM_2.readObject ( ) ;  //read private key from file and save it to ref private key temp
                          }
                          catch ( FileNotFoundException FNFE )
                          {
                              JOptionPane.showMessageDialog ( null, "Error -> " + FNFE.getLocalizedMessage ( ), "Exception - FileNotFoundException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          catch ( ClassNotFoundException CNFE )
                          {
                              JOptionPane.showMessageDialog ( null, "Error -> " + CNFE.getLocalizedMessage ( ), "Exception - ClassNotFoundException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          catch ( IOException IOE )
                          {
                              JOptionPane.showMessageDialog ( null, "Error -> " + IOE.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          //Decrypt the encrypted password of User
                          CIPHER = Cipher.getInstance ( "RSA" ) ;
                          CIPHER.init ( Cipher.DECRYPT_MODE, PRIVATE_KEY_TEMP ) ;      //DECRYPT_MODE MODE static inT ,Constant used to initialize cipher to DECRYPT_MODE mode.
                          FINAL_DECRYPTED_PASSWORD = CIPHER.doFinal ( OBJ.GET_ENCRYPTED_PASSWORD ( ) ) ;  // save decrypted password
                    }
                    catch ( InvalidKeyException IKE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + IKE.getLocalizedMessage ( ), "Exception - InvalidKeyException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    catch ( IllegalBlockSizeException IBSE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + IBSE.getLocalizedMessage ( ), "Exception - IllegalBlockSizeException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    catch ( BadPaddingException BPE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + BPE.getLocalizedMessage ( ), "Exception - BadPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    catch ( NoSuchAlgorithmException NSAE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + NSAE.getLocalizedMessage ( ), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    catch  ( NoSuchPaddingException NSPE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + NSPE.getLocalizedMessage ( ), "Exception - NoSuchPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    
                    if ( Arrays.equals ( FINAL_DECRYPTED_PASSWORD, AUTHENTICATE_HASH_VALUE ) )
                    {
                        JOptionPane.showMessageDialog ( null, "Successfull LOG ON", "LOG ON", JOptionPane.INFORMATION_MESSAGE, null ) ;
                        PATHNAME_USER = Field_UserName.getText ( ) ;  //Take name to write files revenue and expenses to correct folder
                        Main_Menu.Note_User ( Field_UserName.getText ( ) ) ; //Note in Panel the name of user which is logged at currrent time
                        Main_Menu.Set_CHECK_LOG_FLAG ( ) ;  //Flag to check the authentication flag
                        this.dispose ( ) ;
                        if ( IDENTIFIER_FINAL.equals ( "IFR" ) )
                        {
                            Insert_Financial_Revenues_Window OBJ = new Insert_Financial_Revenues_Window ( ) ;
                        }
                        else if ( IDENTIFIER_FINAL.equals ( "MFR" ) )
                        {
                            MODIFY_GUI OBJ = new MODIFY_GUI ( "R" ) ;
                        }
                        else if ( IDENTIFIER_FINAL.equals ( "IFE" ) )
                        {
                            Insert_Financial_Expenses_Window OBJ = new Insert_Financial_Expenses_Window ( ) ;
                        }
                        else if ( IDENTIFIER_FINAL.equals ( "MFE" ) )
                        {
                            MODIFY_GUI OBJ = new MODIFY_GUI ( "E" ) ;         
                        }
                        else if ( IDENTIFIER_FINAL.equals ( "SMR" ) )
                        {
                            Monthly_Report OBJ = new Monthly_Report ( ) ;    
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog ( null, "Wrong Password,Try again", "Error - Wrong Password", JOptionPane.ERROR_MESSAGE, null ) ;
                        this.dispose ( ) ;
                        Authentication_Window OBJ = new Authentication_Window ( IDENTIFIER_FINAL ) ;
                    }
                    FAIL_COUNTER = 0 ;  //CLEAR
                }
              }
              else  //File does not exists, no one sign up...
              {
                  JOptionPane.showMessageDialog ( null, "Please sign up before do an Financial Operation", "Info", JOptionPane.INFORMATION_MESSAGE, null ) ;
                  this.dispose ( ) ;
                  Main_Menu OBJ = new Main_Menu ( ) ;
              }
            } //End else
        }
        
        
        if ( source == CLEAR )
        {
            Field_UserName.setText ( null ) ;
            Field_Password.setText ( null ) ;
            Wrong_Password.setText ( null ) ;
            Wrong_User_Name.setText ( null ) ;
        }   // End of Clear-Action  
    }   // End of actionPerformed
    
    
}  //TELOS KLASIS
    
    

    
