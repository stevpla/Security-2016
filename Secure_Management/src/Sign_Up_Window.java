
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;




public class Sign_Up_Window extends JFrame implements ActionListener
{
    
    private JLabel L1, L2, L3, L4, NAME_LABEL ;
    private JButton Sign_Up, CLEAR ;
    private JTextField LOGIN_NAME_FIELD, NAME_SUR_FIELD ;
    private JPasswordField Password_1, Password_2 ;
    private Cipher CIPHER = null ;
    private FileInputStream INPUT_STREAM = null ;
    private ObjectInputStream INPUT_STREAM_2 = null ;
    private ObjectOutputStream OUTPUT_STREAM_2 = null ;
    private FileOutputStream OUTPUT_STREAM = null ;
    private PublicKey OBJ = null ;
    private KeyGenerator KEY_GENERATOR = null ;
    private SecretKey SYMMETRIC_KEY = null ;
    private boolean LOGIN_NAME_EXISTS_FLAG = false, Counter_Byte_Exists_Hash = false ;
    private USERS_INFO USERS_INFO_OBJ = null ;
    private HashSet HASHSET_SALTS = null ;
    private byte [ ] BYTE_UNIQUE = null, SALT_AND_PASSWORD = null, TEMP_SALT = null   ;
   
    
    
    
    public Sign_Up_Window ( )
    {
        super  ( "Sign Up" ) ;
        setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE ) ;   //Close Current Window
        setSize ( 800, 550 ) ;
        setLocationRelativeTo ( null ) ;
        setVisible ( true ) ;
        
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
        //Locating and Initializing Components
        
        L1 = new JLabel ( "Please Enter Name & Password to Sign Up  " ) ;
        L1.setForeground ( Color.BLACK ) ;
        L1.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        L1.setBounds ( 100, 50, 900, 40 ) ;
        
        L2 = new JLabel ( "Enter User_Name : " ) ;
        L2.setForeground ( Color.BLACK ) ;
        L2.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        L2.setBounds ( 40, 160, 900, 40 ) ;
        
        NAME_LABEL = new JLabel ( "Enter Name & Surname : " ) ;
        NAME_LABEL.setForeground ( Color.BLACK ) ;
        NAME_LABEL.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        NAME_LABEL.setBounds ( 40, 220, 900, 40 ) ;
        
        L3 = new JLabel ( "Enter Password : " ) ;
        L3.setForeground ( Color.BLACK ) ;
        L3.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        L3.setBounds ( 40, 280, 900, 40 ) ;
        
        L4 = new JLabel ( "Re-enter Password : " ) ;
        L4.setForeground ( Color.BLACK ) ;
        L4.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        L4.setBounds ( 40, 340, 900, 40 ) ;
        
        //Buttons
        Sign_Up = new JButton ( "Sign_Up" ) ;
        Sign_Up.setForeground ( Color.BLACK ) ;
        Sign_Up.setBounds ( 200, 400, 120, 60 ) ;
        
        CLEAR = new JButton ( "Clear" ) ;
        CLEAR.setForeground ( Color.BLACK ) ;
        CLEAR.setBounds ( 350, 400, 120, 60 ) ;
        
        //Fields
        LOGIN_NAME_FIELD = new JTextField ( 20 ) ;
        LOGIN_NAME_FIELD.setForeground ( Color.BLACK ) ;
        LOGIN_NAME_FIELD.setBounds ( 300, 166, 220, 27 ) ;
        
        NAME_SUR_FIELD  = new JTextField ( 40 ) ;
        NAME_SUR_FIELD.setForeground ( Color.BLACK ) ;
        NAME_SUR_FIELD.setBounds ( 370, 226, 220, 27 ) ;
        
        Password_1 = new JPasswordField ( 20 ) ;
        Password_1.setForeground ( Color.BLACK ) ;
        Password_1.setBounds ( 281, 286, 220, 27 ) ;
        
        Password_2 = new JPasswordField ( 20 ) ;
        Password_2.setForeground ( Color.BLACK ) ;
        Password_2.setBounds ( 325, 346, 220, 27 ) ;
        
        
        CLEAR.addActionListener ( this ) ;
        Sign_Up.addActionListener ( this ) ;
        
        //Prosthetontas sto PANE
        Container pane = getContentPane ( ) ;
        
        pane.setLayout ( null ) ;   //Apenergopoioume ton diaxeiristi
        
        // Additions Components
        pane.add ( LOGIN_NAME_FIELD ) ;
        pane.add ( NAME_LABEL ) ;
        pane.add ( NAME_SUR_FIELD ) ;
        pane.add ( Password_1 ) ;
        pane.add ( Password_2 ) ;
        pane.add ( Sign_Up ) ;
        pane.add ( CLEAR ) ;
        pane.add ( L1 ) ;
        pane.add ( L2 ) ;
        pane.add ( L3 ) ;
        pane.add ( L4 ) ;
        
        setContentPane ( pane ) ;
        
    }//TELOS Constructor
    
    
   
    
    
    // Actions
    
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        //CANCEL
        if ( source == CLEAR )
        {
            LOGIN_NAME_FIELD.setText ( "" ) ;
            NAME_SUR_FIELD.setText ( "" ) ;
            Password_1.setText ( "" ) ;
            Password_2.setText ( "" ) ;
        }
        
        
        //SIGN_UP
        if ( source == Sign_Up )
        {
            if ( LOGIN_NAME_FIELD.getText ( ).equals ( "" ) || Password_1.getPassword ( ).length == 0 || Password_2.getPassword ( ).length == 0 || NAME_SUR_FIELD.getText ( ).equals ( "" ) )
            {
                JOptionPane.showMessageDialog ( this, "Please enter all Fields before continue", "Warning", JOptionPane.WARNING_MESSAGE ) ;
            }
            else //When you entered as input all necessary Fields
            {
                if (   ! ( Password_1.getText ( ) ).equals ( Password_2.getText ( ) )     )    //PREPEI KAI TA 2 PEDIA KODIKON NA EINAI TA IDIA
                {
                    JOptionPane.showMessageDialog ( this, "Please enter valid Password both Fields", "Warning", JOptionPane.WARNING_MESSAGE ) ;
                    Password_1.setText ( "" ) ;
                    Password_2.setText ( "" ) ;
                }
                else  //You have to enter the same paassword for safety both JPassword Fields
                {
                    
                  File CHECK_IF_EXISTS_USERS_INFO_Data = new File ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\USERS_INFO.data" ) ;
    
                  if ( CHECK_IF_EXISTS_USERS_INFO_Data.exists ( ) ) // 1st SIGN_UP, NO MEANING TO CHECK FOR LOGIN_NAME, 
                  {  //First check if the Login Name that User entered already exists
                      try
                      {   System.out.println ( " Sosara leme" ) ;
                           this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\USERS_INFO.data" ) ;
                           while ( true )
                           {
                              this.INPUT_STREAM_2 = new ObjectInputStream ( INPUT_STREAM ) ;
                              this.USERS_INFO_OBJ = ( USERS_INFO ) INPUT_STREAM_2.readObject ( ) ;  //Take key from File with casting
                              System.out.println ( "Name that exists -> " + USERS_INFO_OBJ.GET_LOGIN_NAME ( ) ) ;
                              if ( USERS_INFO_OBJ.GET_LOGIN_NAME ( ).equals ( LOGIN_NAME_FIELD.getText ( ) ) )
                              {   // System.out.println ( "Name that exists -> " + USERS_INFO_OBJ.GET_LOGIN_NAME ( ) ) ;
                                  LOGIN_NAME_EXISTS_FLAG = true ;  //If the login name founded, then deny Sign_UP, and try again
                              } 
                           }
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
                          //JOptionPane.showMessageDialog ( null, "Error -> " + IOE.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
                      }
                      
                      if ( LOGIN_NAME_EXISTS_FLAG == true )
                      {
                          LOGIN_NAME_EXISTS_FLAG = false ;  //Clear flag
                          JOptionPane.showMessageDialog ( null, "The Login Name you have entered, already exists.. Please try again", null, WIDTH, null ) ;
                          LOGIN_NAME_FIELD.setText ( "" ) ;
                          NAME_SUR_FIELD.setText ( "" ) ;
                          Password_1.setText ( "" ) ;
                          Password_2.setText ( "" ) ;
                          //
                          INPUT_STREAM = null ;
                          INPUT_STREAM_2 = null ;
                          USERS_INFO_OBJ = null ;
                      }
                      else //The Login Name that User choose, is clear,so allow him to Sign_Up
                      {
                          byte [ ] PASSWORD_BYTES = Password_1.getText ( ).getBytes ( ) ;   //Getting bytes amount from String -> PASSWORD
                    
                          byte [ ] SALT = new byte [ 20 ] ;  //20 Allocation Memory
                          new Random ( ).nextBytes ( SALT ) ;// Filling byte array - SALT with random amount of bytes for user
                          System.out.println ( " to salt gia ton 2o kai > xristi einau " +SALT ) ;
                          //Check is salt is using by other User, so produce different and random
                          try
                          {
                              HASHSET_SALTS = new HashSet < byte [ ] > ( ) ;
                              this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\USERS_INFO.data" ) ;
                              while ( true )  //read all .data to check every salt
                              {
                                 this.INPUT_STREAM_2 = new ObjectInputStream ( this.INPUT_STREAM ) ;
                                 USERS_INFO OBJECT = ( USERS_INFO ) this.INPUT_STREAM_2.readObject ( ) ;
                                 System.out.println ( " SALT OF 1ST from opening file" + OBJECT.GET_SALT ( ) ) ;
                                 HASHSET_SALTS.add ( OBJECT.GET_SALT ( ) ) ;   //add every salt from file to hash set
                              }
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
                              //JOptionPane.showMessageDialog ( null, "Error ->MIPOS ?? " + IOE.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }   //Check for same salts
                              if ( HASHSET_SALTS.contains ( SALT ) )
                              {  //until produce different Salt
                                  Counter_Byte_Exists_Hash = true ;
                                  do
                                  {
                                      BYTE_UNIQUE = new byte [ 20 ] ;
                                      new Random ( ).nextBytes ( BYTE_UNIQUE ) ;// Filling byte array - SALT with random amount of bytes for
                                  } while ( HASHSET_SALTS.contains ( BYTE_UNIQUE ) ) ;
                              }
                          //Now we are sure for Unique salt for every user
                         
                          //Merge 2 bytes array to one total BYTE , to get total bytes for hashing
                          if ( Counter_Byte_Exists_Hash == true  )
                          {
                              SALT_AND_PASSWORD = new byte [ BYTE_UNIQUE.length + PASSWORD_BYTES.length ] ;//CONCATENATION TO FINAL BYTE ARRAY
                              TEMP_SALT = BYTE_UNIQUE ;
                          }
                          else
                          {    
                              SALT_AND_PASSWORD = new byte [ SALT.length + PASSWORD_BYTES.length ] ;//CONCATENATION TO FINAL BYTE ARRAY
                              TEMP_SALT = SALT ;
                          }
                          //Now we have the final Salt which is different from the rest Salts, Unique !!
                          for ( int i = 0 ;    i < SALT_AND_PASSWORD.length ;   ++i )
                          {
                              SALT_AND_PASSWORD [ i ] = i < TEMP_SALT.length ? TEMP_SALT [ i ] : PASSWORD_BYTES [ i - TEMP_SALT.length ] ;
                          } 
                    
                          MessageDigest MESSAGE_DIGEST = null ;
                          try
                          {
                              MESSAGE_DIGEST = MessageDigest.getInstance ( "SHA-256" ) ;
                              //Returns a MessageDigest object that implements the specified digest algorithm.
                          }
                          catch ( NoSuchAlgorithmException ex ) 
                          {
                             JOptionPane.showMessageDialog ( null, "Error -> " + ex.getLocalizedMessage ( ), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          MESSAGE_DIGEST.update ( SALT_AND_PASSWORD ) ;      //Updates the digest using the specified byte.
                          byte [ ] HASH_VALUE = MESSAGE_DIGEST.digest ( ) ;  // SHA-256 Completes the hash computation by performing final operations such as padding.
                          //and store it to byte array
                          System.out.println ( " To thash value gia ton 2on xristi kai > einai -> " + HASH_VALUE ) ;
                          StringBuffer sb = new StringBuffer ( ) ;
                          for ( int i = 0 ;   i < HASH_VALUE.length ;  i++ )
                          {
                              sb.append ( Integer.toString ( ( HASH_VALUE [ i ] & 0xff ) + 0x100, 16 ).substring ( 1 ) ) ;  
                          }
 
                          JOptionPane.showMessageDialog ( null, "HASH_VALUE at SIGN_UP =>  " +sb.toString ( ), MESSAGE_DIGEST.toString ( ), JOptionPane.INFORMATION_MESSAGE, null ) ;
                          //APPEAR HASH VALUE - DIGEST
                          //Now encrpyt Hash value with public key
                          try
                          {
                             
                             //Now Open Stream to Open PUBLIC KEY FROM File.dat, TO READ PUBLIC KEY TO ENCRYPT HASH DIGEST
                             try
                             {
                                INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\PUBLIC_KEY.data" ) ;
                                INPUT_STREAM_2 = new ObjectInputStream ( INPUT_STREAM ) ;
                                OBJ = ( PublicKey ) INPUT_STREAM_2.readObject ( ) ;  //Take key from File with casting
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
                                 //JOptionPane.showMessageDialog ( null, "Error -> " + IOE.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
                             }
                             //End of READ KEY FROM FLLE
                       
                             //
                             try
                             {
                                 CIPHER = Cipher.getInstance ( "RSA" ) ; //Returns a Cipher object that implements the specified transformation.
                                 //RSA IS ASSYMETRIC ENCRYPTION, the decryption requires A PRIVATE KEY//Initializes this cipher with a key.
                                 CIPHER.init ( Cipher.ENCRYPT_MODE, OBJ ) ;    
                                 //ENCRYPT MODE static inT ,Constant used to initialize cipher to encryption mode
                                 byte [ ] FINAL_ENCRYPTED_PASSWORD = CIPHER.doFinal ( HASH_VALUE ) ;  //Encrypted hash value is now saved yo byte array
                                 //Encrypts data in a single-part operation, or finishes a multiple-part operation.
                                 //Now create FILE < USERS_INFO >
                                 try
                                 {
                                   this.OUTPUT_STREAM =  new FileOutputStream ( "USERS_INFO.data", true ) ;  //Do not overwrite file but add every time
                                   this.OUTPUT_STREAM_2 = new ObjectOutputStream ( this.OUTPUT_STREAM ) ;
                                   if ( Counter_Byte_Exists_Hash == true )
                                   {
                                       this.OUTPUT_STREAM_2.writeObject ( new USERS_INFO ( LOGIN_NAME_FIELD.getText ( ), NAME_SUR_FIELD.getText ( ), BYTE_UNIQUE, FINAL_ENCRYPTED_PASSWORD ) ) ;
                                       Counter_Byte_Exists_Hash = false ;
                                   }
                                   else
                                   {
                                       this.OUTPUT_STREAM_2.writeObject ( new USERS_INFO ( LOGIN_NAME_FIELD.getText ( ), NAME_SUR_FIELD.getText ( ), SALT, FINAL_ENCRYPTED_PASSWORD ) ) ;
                                       Counter_Byte_Exists_Hash = false ;   
                                   }
                                 }
                                 catch ( FileNotFoundException FNFE )
                                 {
                                     JOptionPane.showMessageDialog ( null, "Error -> " + FNFE.getLocalizedMessage ( ), "Exception - FileNotFoundException", JOptionPane.ERROR_MESSAGE, null ) ;
                                 }
                                 catch ( IOException IOE )
                                 {
                                     //JOptionPane.showMessageDialog ( null, "Error -> " + IOE.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
                                 }
                              }
                              catch ( InvalidKeyException IKE )
                              {
                                 JOptionPane.showMessageDialog ( null, "Error -> " + IKE.getLocalizedMessage ( ), "Exception - InvalidKeyException", JOptionPane.ERROR_MESSAGE, null ) ;
                              }
                             catch ( IllegalBlockSizeException IBSE )
                             {
                                 JOptionPane.showMessageDialog ( null, "Error -> " + IBSE.getLocalizedMessage ( ), "Exception - IllegalBlockSizeException", JOptionPane.ERROR_MESSAGE, null ) ;
                             }
                             catch (  BadPaddingException BPE )
                             {
                                 JOptionPane.showMessageDialog ( null, "Error -> " + BPE.getLocalizedMessage ( ), "Exception - BadPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                             }
                          }
                          catch ( NoSuchPaddingException NSPE )
                          {
                              JOptionPane.showMessageDialog ( null, "Error -> " + NSPE.getLocalizedMessage ( ), "Exception - NoSuchPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          catch ( NoSuchAlgorithmException NSAE )
                          {
                              JOptionPane.showMessageDialog ( null, "Error -> " + NSAE.getLocalizedMessage ( ), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          //End of which salt is choosen
                          //NOW CREATE FOLDER FOR EVERY USER TO SAVE FOR HIM A KEY
                          File USERNAME = new File ( LOGIN_NAME_FIELD.getText ( ) ) ;
                          USERNAME.mkdir ( ) ;
      
                          //Now create symmmetric key for User in a file inside Folder - SYMMETRIC_KEY..data
                          try
                          {  //Returns a KeyGenerator object that generates secret keys for the specified algorithm.
                             KEY_GENERATOR = KeyGenerator.getInstance ( "AES" ) ;  // symetric encryption, secret key is use for aes algorithm
                             //KeyGenerator objects are reusable, i.e., after a key has been generated,
                             //the same KeyGenerator object can be re-used to generate further keys.
                             //The same key for decryption
                             KEY_GENERATOR.init ( 256 ) ;  //Initializes this key generator for a certain keysize.
                             SYMMETRIC_KEY = KEY_GENERATOR.generateKey ( ) ;  //Generates a secret key. SYMMETRIC
                             System.out.println ( "To summetriko kleisdi gia ton 2o kai > xristi  einai  -> " + SYMMETRIC_KEY ) ;
                       
                             //Now encrypt the symmetric key with the public
                             CIPHER = Cipher.getInstance ( "RSA" ) ; //Returns a Cipher object that implements the specified transformation.
                             //RSA IS ASSYMETRIC ENCRYPTION, the decryption requires A PRIVATE KEY
                             CIPHER.init ( Cipher.ENCRYPT_MODE, OBJ ) ;
                       
                             try
                             {
                                //Extract encoded format from SECRET KEY, and these bytes pass ass input to ECRYPTION
                                byte [ ] ENCRYPTED_SYMMETRIC_KEY = CIPHER.doFinal ( SYMMETRIC_KEY.getEncoded ( ) ) ;
                          
                                //Now save it to SYMMETRIC_KEY.data
                                try
                                {
                                   this.OUTPUT_STREAM =  new FileOutputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + LOGIN_NAME_FIELD.getText ( ) + "\\SYMMETRIC_KEY.data" ) ;  //Do not overwrite file but add every time
                                   this.OUTPUT_STREAM_2 = new ObjectOutputStream ( this.OUTPUT_STREAM ) ;
                                   this.OUTPUT_STREAM_2.writeObject ( ENCRYPTED_SYMMETRIC_KEY ) ; 
                                }
                                catch ( FileNotFoundException FNFE )
                                {
                                    JOptionPane.showMessageDialog ( null, "Error -> " + FNFE.getLocalizedMessage ( ), "Exception - FileNotFoundException", JOptionPane.ERROR_MESSAGE, null ) ;
                                }
                                catch ( IOException IOE )
                                {
                                    //JOptionPane.showMessageDialog ( null, "Error -> " + IOE.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
                                }
                             }
                             catch ( IllegalBlockSizeException IBSE )
                             {
                                 JOptionPane.showMessageDialog ( null, "Error ->" + IBSE.getLocalizedMessage ( ), "Exception - IllegalBlockSizeException", JOptionPane.ERROR_MESSAGE, null ) ;
                             }
                             catch ( BadPaddingException BPE )
                             {
                                 JOptionPane.showMessageDialog ( null, "Error -> " + BPE.getLocalizedMessage ( ), "Exception - BadPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                             } 
                          }
                          catch ( NoSuchAlgorithmException NSAE )
                          {
                              JOptionPane.showMessageDialog ( null, "Error -> " + NSAE.getLocalizedMessage ( ), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          catch ( NoSuchPaddingException NSPE )
                          {
                              JOptionPane.showMessageDialog ( null, "Error -> " + NSPE.getLocalizedMessage ( ), "Exception - NoSuchPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          catch ( InvalidKeyException IKE )
                          {
                              JOptionPane.showMessageDialog ( null, "Error -> " + IKE.getLocalizedMessage ( ), "Exception - InvalidKeyException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          JOptionPane.showMessageDialog ( null, "Successfull Sign Up", "Info", JOptionPane.INFORMATION_MESSAGE, null ) ;
                          this.dispose ( ) ;  
                          Main_Menu OBJ = new Main_Menu ( ) ;   //Now all finished good, go to Menu
                      } //Loginname is ok
                  }
                  else //this block of code will be exetuced when will completed the 1t SIGN_UP, and the USERS_INFO.data will be created, so here it has
                  {  //meaning to check the Login Name if already exists
                     //At first Sign_Up there is no reason to check if the Login Name is already exists,
                       byte [ ] PASSWORD_BYTES = Password_1.getText ( ).getBytes ( ) ;   //Getting bytes amount from String -> PASSWORD
                    
                       byte [ ] SALT = new byte [ 20 ] ;  //20 Allocation Memory
                       new Random ( ).nextBytes ( SALT ) ;// Filling byte array - SALT with random amount of bytes for user
                       System.out.println ( " To salt tou 1ou xristi poy kanei sign up einai-> " +SALT ) ;
                       //Merge 2 bytes array to one total BYTE , to get total bytes for hashing
                       byte [ ] SALT_AND_PASSWORD = new byte [ SALT.length + PASSWORD_BYTES.length ] ;//CONCATENATION TO FINAL BYTE ARRAY
                       for ( int i = 0 ;    i < SALT_AND_PASSWORD.length ;   ++i )
                       {
                           //http://stackoverflow.com/questions/10336899/what-is-a-question-mark-and-colon-operator-used-for
                             SALT_AND_PASSWORD [ i ] = i < SALT.length ? SALT [ i ] : PASSWORD_BYTES [ i - SALT.length ] ;
                       } 
                       // Now produce hash
                       MessageDigest MESSAGE_DIGEST = null ;
                       try
                       {
                           MESSAGE_DIGEST = MessageDigest.getInstance ( "SHA-256" ) ;
                           //Returns a MessageDigest object that implements the specified digest algorithm.
                       }
                       catch ( NoSuchAlgorithmException ex ) 
                       {
                          JOptionPane.showMessageDialog ( null, "Error -> " + ex.getLocalizedMessage ( ), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null ) ;
                       }
                       MESSAGE_DIGEST.update ( SALT_AND_PASSWORD ) ;      //Updates the digest using the specified byte.
                       byte [ ] HASH_VALUE = MESSAGE_DIGEST.digest ( ) ;  // SHA-256 Completes the hash computation by performing final operations such as padding.
                       //and store it to byte array
                       System.out.println ( " To HASH VALUE ( salt + password ) tou 1ou xristi poy kanei sign up einai-> " +HASH_VALUE ) ;
                       StringBuffer sb = new StringBuffer ( ) ;
                       for ( int i = 0 ;   i < HASH_VALUE.length ;  i++ )
                       {
                           sb.append ( Integer.toString ( ( HASH_VALUE [ i ] & 0xff ) + 0x100, 16 ).substring ( 1 ) ) ;  
                       }
                       JOptionPane.showMessageDialog ( null, "HASH_VALUE at SIGN_UP =>  " +sb.toString ( ), MESSAGE_DIGEST.toString ( ), JOptionPane.INFORMATION_MESSAGE, null ) ;
                       //APPEAR HASH VALUE - DIGEST
                       //Now encrpyt Hash value with public key
                       try
                       {
                          CIPHER = Cipher.getInstance ( "RSA" ) ; //Returns a Cipher object that implements the specified transformation.
                          //RSA IS ASSYMETRIC ENCRYPTION, the decryption requires A PRIVATE KEY
                          //Now Open Stream to Open PUBLIC KEY FROM File.dat, TO READ PUBLIC KEY TO ENCRYPT HASH DIGEST
                          try
                          {
                              INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\PUBLIC_KEY.data" ) ;
                              INPUT_STREAM_2 = new ObjectInputStream ( INPUT_STREAM ) ;
                              OBJ = ( PublicKey ) INPUT_STREAM_2.readObject ( ) ;  //Take key from File with casting
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
                              //JOptionPane.showMessageDialog ( null, "Error -> " + IOE.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          //End of READ KEY FROM FLLE
                       
                          //
                          try
                          {   //Initializes this cipher with a key.
                              CIPHER.init ( Cipher.ENCRYPT_MODE, OBJ ) ;    
                              //ENCRYPT MODE static inT ,Constant used to initialize cipher to encryption mode
                              byte [ ] FINAL_ENCRYPTED_PASSWORD = CIPHER.doFinal ( HASH_VALUE ) ;  //Encrypted hash value is now saved yo byte array
                              //Encrypts data in a single-part operation, or finishes a multiple-part operation.
                              //Now create FILE < USERS_INFO >
                              try
                              {
                                 this.OUTPUT_STREAM =  new FileOutputStream ( "USERS_INFO.data", true ) ;  //Do not overwrite file but add every time
                                 this.OUTPUT_STREAM_2 = new ObjectOutputStream ( this.OUTPUT_STREAM ) ;
                                 this.OUTPUT_STREAM_2.writeObject ( new USERS_INFO ( LOGIN_NAME_FIELD.getText ( ), NAME_SUR_FIELD.getText ( ), SALT, FINAL_ENCRYPTED_PASSWORD ) ) ; 
                              }
                              catch ( FileNotFoundException FNFE )
                              {
                                  JOptionPane.showMessageDialog ( null, "Error -> " + FNFE.getLocalizedMessage ( ), "Exception - FileNotFoundException", JOptionPane.ERROR_MESSAGE, null ) ;
                              }
                              catch ( IOException IOE )
                              {
                                  //JOptionPane.showMessageDialog ( null, "Error -> " + IOE.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
                             }
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
                       }
                       catch ( NoSuchPaddingException NSPE )
                       {
                           JOptionPane.showMessageDialog ( null, "Error -> " + NSPE.getLocalizedMessage ( ), "Exception - NoSuchPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                       }
                       catch ( NoSuchAlgorithmException NSAE )
                       {
                           JOptionPane.showMessageDialog ( null, "Error -> " + NSAE.getLocalizedMessage ( ), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null ) ;
                       }
                       //NOW CREATE FOLDER FOR EVERY USER TO SAVE FOR HIM A KEY
                       File USERNAME = new File ( LOGIN_NAME_FIELD.getText ( ) ) ;
                       USERNAME.mkdir ( ) ;
      
                       //Now create symmmetric key for User in a file inside Folder - SYMMETRIC_KEY..data
                       try
                       {  //Returns a KeyGenerator object that generates secret keys for the specified algorithm.
                            KEY_GENERATOR = KeyGenerator.getInstance ( "AES" ) ;  // symetric encryption, secret key is use for aes algorithm
                          //KeyGenerator objects are reusable, i.e., after a key has been generated,
                          //the same KeyGenerator object can be re-used to generate further keys.
                          //The same key for decryption
                          KEY_GENERATOR.init ( 128 ) ;  //Initializes this key generator for a certain keysize.
                          SYMMETRIC_KEY = KEY_GENERATOR.generateKey ( ) ;  //Generates a secret key. SYMMETRIC
                          System.out.println ( "To summetriko kleidi gia ton 1on xristi einai gia - " + SYMMETRIC_KEY ) ;
                       
                          //Now encrypt the symmetric key with the public
                          CIPHER = Cipher.getInstance ( "RSA" ) ; //Returns a Cipher object that implements the specified transformation.
                          //RSA IS ASSYMETRIC ENCRYPTION, the decryption requires A PRIVATE KEY
                          CIPHER.init ( Cipher.ENCRYPT_MODE, OBJ ) ;
                       
                          try
                          {
                             //Extract encoded format from SECRET KEY, and these bytes pass ass input to ECRYPTION
                             byte [ ] ENCRYPTED_SYMMETRIC_KEY = CIPHER.doFinal ( SYMMETRIC_KEY.getEncoded ( ) ) ;
                             //Now save it to SYMMETRIC_KEY.data
                             try
                             {
                                 this.OUTPUT_STREAM =  new FileOutputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + LOGIN_NAME_FIELD.getText ( ) + "\\SYMMETRIC_KEY.data" ) ;  //Do not overwrite file but add every time
                                 this.OUTPUT_STREAM_2 = new ObjectOutputStream ( this.OUTPUT_STREAM ) ;
                                 this.OUTPUT_STREAM_2.writeObject ( ENCRYPTED_SYMMETRIC_KEY ) ; 
                             }
                             catch ( FileNotFoundException FNFE )
                             {
                                 JOptionPane.showMessageDialog ( null, "Error -> " + FNFE.getLocalizedMessage ( ), "Exception - FileNotFoundException", JOptionPane.ERROR_MESSAGE, null ) ;
                             }
                             catch ( IOException IOE )
                             {
                                 //JOptionPane.showMessageDialog ( null, "Error -> " + IOE.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
                             }
                          }
                          catch ( IllegalBlockSizeException IBSE )
                          {
                              JOptionPane.showMessageDialog ( null, "Error ->" + IBSE.getLocalizedMessage ( ), "Exception - IllegalBlockSizeException", JOptionPane.ERROR_MESSAGE, null ) ;
                          }
                          catch ( BadPaddingException BPE )
                          {
                              JOptionPane.showMessageDialog ( null, "Error -> " + BPE.getLocalizedMessage ( ), "Exception - BadPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                          } 
                       }
                       catch ( NoSuchAlgorithmException NSAE )
                       {
                           JOptionPane.showMessageDialog ( null, "Error -> " + NSAE.getLocalizedMessage ( ), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null ) ;
                       }
                       catch ( NoSuchPaddingException NSPE )
                       {
                           JOptionPane.showMessageDialog ( null, "Error -> " + NSPE.getLocalizedMessage ( ), "Exception - NoSuchPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                       }
                       catch ( InvalidKeyException IKE )
                       {
                           JOptionPane.showMessageDialog ( null, "Error -> " + IKE.getLocalizedMessage ( ), "Exception - InvalidKeyException", JOptionPane.ERROR_MESSAGE, null ) ;
                       }
                       JOptionPane.showMessageDialog ( null, "Successfull Sign Up", "Info", JOptionPane.INFORMATION_MESSAGE, null ) ;
                       this.dispose ( ) ;  
                      Main_Menu OBJ = new Main_Menu ( ) ;   //Now all finished good, go to Menu
                  }//End of else, file exists
                }//End of else
            }//End of else
        }//End of If Sign_Up_Window
    } //End Action Methodou
} //End CLASS

    

