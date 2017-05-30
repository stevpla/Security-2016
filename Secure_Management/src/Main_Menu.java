
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.HashMap;
import javax.crypto.SealedObject;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import sun.security.provider.SHA;



public class Main_Menu extends JFrame implements ActionListener
{
    
    
    
    //Components
    private JButton INSERT_REVENUES, INSERT_EXPENSES, MODIFY_REVENUES, MODIFY_EXPENSES, MONTHLY_REPORT, SIGN_UP, LOGOUT ;
    private JLabel USER_LABEL, SIGN_LABEL ;
    private static JLabel IMAGE_LABEL ; //Mas eikozizei to onoma tou xrhsth pou einai LOG ON
    private static JLabel USER_NAME  = new JLabel ( "   -- " ) ;  //Replace by User Name
    private KeyPairGenerator OBJECT_KEY = null ;
    private KeyPair KEY_PAIR = null ;
    public static PrivateKey PRIVATE_KEY = null ;
    private PublicKey PUBLIC_KEY = null ;
    private static boolean CHECK_LOG_FLAG = false  ; //For authentication
    private FileOutputStream OUTPUT_STREAM = null ;
    private ObjectOutputStream OUTPUT_STREAM_2 = null ;
    public static PrivateKey  PRIVATE_KEY_APP = null ;
    private String IDENTIFIER [ ] = null ;
    private FileInputStream INPUT_STREAM = null ;
    private ObjectInputStream INPUT_STREAM_2 = null ;
    private Signature Digital_Sign = null ;
    private MessageDigest Message_Digest = null ;
    private byte [ ] File_Bytes = new byte [ 1024 ], MY_BYTES = null, TOTAL_HASH_FROM_3HASHES = null ;
    private HashMap < String, byte [ ] > HASH_MAP = null ;

  
 
    
    
    //Default Constructor
    public Main_Menu ( )  
    {
        super ( "Secure Management of Revenue - Expenses" ) ;
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE ) ;      //If you press X the red one icon then terminate this Window
        //
        //https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
        //In this technique a WindowListener interface implentation is added to the frame, where the listener has a method, windowClosing(), that is called when the frame is closed.
        //In practice, on overrides the windowClosing() method of WindowAdapter, a no-op implementation of WindowListener.   This way, one doesn't have to worry about all the other methods of the WindowListener.
        this.addWindowListener ( new java.awt.event.WindowAdapter ( ) 
        {
              @Override             //Otan patiseis to kokkino x tote aytomata ginetai LGOOUT
             public void windowClosing(java.awt.event.WindowEvent windowEvent) 
             {
                       int Answer = JOptionPane.showConfirmDialog ( null,  "Are you sure to close this window?", "Sure for Exit ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE ) ;
                               if ( Answer == JOptionPane.YES_OPTION)
                               {  
                                   //Now Check for Revenue FILE and later for the Expenses File and then Exit..but the signatures keep them in a file
                                   System.exit( 1 ) ;
                               }
                               else
                               {
                                    setDefaultCloseOperation ( WindowConstants.DO_NOTHING_ON_CLOSE ) ;
                               }
             }
        } ); 
        //
        
        //Here will be created PUBLIC * PRIVATE KEY PAIR
         File CHECK_IF_EXISTS_PUBLIC_KEY_Data = new File ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\PUBLIC_KEY.data" ) ;
         if ( CHECK_IF_EXISTS_PUBLIC_KEY_Data.exists ( ) ) //after key pair created, the else block code never be executed, because
         { //the file already has been created
            // Do ..Nothing
         }
         else  //1st time there is no such file so else block code will be executed
         {   
           try
           {
               OBJECT_KEY = KeyPairGenerator.getInstance ( "RSA" ) ;   // Returns a KeyPairGenerator object that generates public/private key pairs for the specified algorithm.
               OBJECT_KEY.initialize ( 2048 ) ;    //Initialzing Key PAIR Generator for a certain keysize
               //is a tradeoff between security and performance
           }
           catch ( NoSuchAlgorithmException N )
           {
               JOptionPane.showMessageDialog ( null, "Error -> " + N.getLocalizedMessage ( ), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null ) ;
           }
           KEY_PAIR = OBJECT_KEY.genKeyPair ( ) ;   //Generates a key pair.
           PRIVATE_KEY = KEY_PAIR.getPrivate ( ) ;  //Returns a reference to the private key component of this key pair.
           PUBLIC_KEY = KEY_PAIR.getPublic ( ) ;    //Returns a reference to the public key component of this key pair.
           //Now write Public Key to a file
           try
           {
               this.OUTPUT_STREAM =  new FileOutputStream ( "PUBLIC_KEY.data" ) ;
               this.OUTPUT_STREAM_2 = new ObjectOutputStream ( OUTPUT_STREAM ) ;  // 1st Level of Stream, create Object OutputStream with FileOutputStream as parameter
               this.OUTPUT_STREAM_2.writeObject (  PUBLIC_KEY ) ;   //Wirte public key in file.data
               this.OUTPUT_STREAM =  new FileOutputStream ( "PRIVATE_KEY.data" ) ;
               this.OUTPUT_STREAM_2 = new ObjectOutputStream ( OUTPUT_STREAM ) ;  // 1st Level of Stream, create Object OutputStream with FileOutputStream as parameter
               this.OUTPUT_STREAM_2.writeObject (  PRIVATE_KEY ) ;   //Wirte private key in file.data
           }
           catch ( FileNotFoundException F )
           {
               JOptionPane.showMessageDialog ( null , "Error -> " + F.getLocalizedMessage ( ), "Exception - FileNotFoundException", JOptionPane.ERROR_MESSAGE, null ) ;
           }
           catch ( IOException I )
           {
               JOptionPane.showMessageDialog ( null , "Error -> " + I.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
           }
         }  //End of else
        // END OF KEY GENERATION- KEY PAIR
        
        
        setSize ( 1200, 850 ) ;       // Size of Window
        setLocationRelativeTo ( null ) ;  // Display Window in center of Screen
        setVisible ( true ) ;
        
        //
        IDENTIFIER = new String [ ] { "IFR", "IFE","MFR","MFE","SMR" } ;
        
        //Initialize Components
        //Buttons
        INSERT_REVENUES = new JButton ( "Insert_Financial_Revenues" ) ;
        INSERT_REVENUES.setForeground ( Color.BLACK ) ;
        INSERT_REVENUES.setBounds ( 280, 180, 200, 60 ) ;
        
        INSERT_EXPENSES = new JButton ( "Insert_Financial_Expenses" ) ;
        INSERT_EXPENSES.setForeground ( Color.BLACK ) ;
        INSERT_EXPENSES.setBounds ( 280, 280, 200, 60 ) ;
        
        MODIFY_REVENUES = new JButton ( "Modify_Financial_Revenues" ) ;
        MODIFY_REVENUES.setForeground ( Color.BLACK ) ;
        MODIFY_REVENUES.setBounds ( 500, 180, 200, 60 ) ;
     
        MODIFY_EXPENSES = new JButton ( "Modify_Financial_Expenses" ) ;
        MODIFY_EXPENSES.setForeground ( Color.BLACK ) ;
        MODIFY_EXPENSES.setBounds ( 500, 280, 200, 60 ) ;
        
        MONTHLY_REPORT = new JButton ( "See_Monthly_Report" ) ;
        MONTHLY_REPORT.setForeground ( Color.BLACK ) ;
        MONTHLY_REPORT.setBounds ( 720, 180, 170, 60 ) ;
        
        //---Buttons
        SIGN_UP = new JButton ( "Sign_Up" ) ;
        SIGN_UP.setForeground ( Color.gray ) ;
        SIGN_UP.setBounds ( 860, 600, 120, 60 ) ;  
        
        LOGOUT = new JButton ( "Logout" ) ;
        LOGOUT.setForeground ( Color.RED ) ;
        LOGOUT.setBounds ( 720, 280, 170, 60 ) ;
        //=======
        
        //Labels
        USER_LABEL = new JLabel ( "User : " ) ;
        USER_LABEL.setForeground ( Color.BLUE ) ;
        USER_LABEL.setFont  (new Font ( "Courier New", Font.BOLD, 30 ) ) ;
        USER_LABEL.setBounds ( 250, 50, 900, 40 ) ;
        
        USER_NAME.setForeground ( Color.BLUE ) ;
        USER_NAME.setFont  (new Font ( "Courier New", Font.BOLD, 30 ) ) ;
        USER_NAME.setBounds ( 350, 50, 550, 40 ) ;  
        
        SIGN_LABEL = new JLabel ( "If you are not a User, you can Sign up !!" ) ;
        SIGN_LABEL.setForeground ( Color.BLUE ) ;
        SIGN_LABEL.setFont  (new Font ( "Courier New", Font.BOLD, 30 ) ) ;
        SIGN_LABEL.setBounds ( 100, 720, 900, 40 ) ;
        
        IMAGE_LABEL = new JLabel ( ) ;
        IMAGE_LABEL.setIcon ( new ImageIcon ( this .getClass ( ). getResource ("ss.jpg") ) ) ;
        IMAGE_LABEL.setBounds ( 380, 130, 800, 700 ) ; 
        
        //Prosthetontas ta koympia
        INSERT_REVENUES.addActionListener ( this ) ;
        INSERT_EXPENSES.addActionListener ( this ) ;
        MODIFY_REVENUES.addActionListener ( this ) ;
        MODIFY_EXPENSES.addActionListener ( this ) ;
        MONTHLY_REPORT.addActionListener ( this ) ;
        SIGN_UP.addActionListener ( this ) ;
        LOGOUT.addActionListener ( this ) ;
        
        Container pane = getContentPane ( ) ;  //Diaxeiristis
        pane.setLayout ( null ) ;   //Deactivate Manager Layout
    
        //
        pane.add ( INSERT_REVENUES ) ;
        pane.add ( INSERT_EXPENSES ) ;
        pane.add ( MODIFY_REVENUES ) ;
        pane.add ( MODIFY_EXPENSES ) ;
        pane.add ( MONTHLY_REPORT ) ;
        pane.add ( SIGN_UP ) ;
        pane.add ( LOGOUT ) ;
        pane.add ( MONTHLY_REPORT ) ;
        pane.add ( USER_LABEL ) ;
        pane.add ( USER_NAME ) ;
        pane.add ( SIGN_LABEL ) ;
        pane.add ( IMAGE_LABEL ) ;
        
        setContentPane ( pane ) ;
        
    } //End of Constructor
    
    //Static method because we change a static variable, and notes the name of the USER which is LOG ON at the current moment
    public static void Note_User ( String x )
    {
        USER_NAME.setText ( "" ) ;
        USER_NAME.setText ( " " + x ) ;
    }
    
    //In Authentication Window, when a user loged on then the flag turns in true
    public static void Set_CHECK_LOG_FLAG ( )
    {
        CHECK_LOG_FLAG = true ;
    }
    
    // Actions
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
        if ( INSERT_REVENUES == source )
        {  
            this.dispose ( ) ;
            //1st time for YOU
            if ( CHECK_LOG_FLAG == false )
            {
                Authentication_Window AW = new Authentication_Window ( IDENTIFIER [ 0 ] ) ;
            }//You alread logged on, and app <remember you>
            else
            {
                Insert_Financial_Revenues_Window IFR = new Insert_Financial_Revenues_Window ( ) ;
            }
        }
        
        if ( INSERT_EXPENSES == source )
        {
            this.dispose ( ) ;
            //1st time for YOU
            if ( CHECK_LOG_FLAG == false )
            {
                Authentication_Window AW = new Authentication_Window ( IDENTIFIER [ 1 ] ) ;
            }//You alread logged on, and app <remember you>
            else  
            {
                Insert_Financial_Expenses_Window IFE = new Insert_Financial_Expenses_Window ( ) ;
            }
        }
        
        if ( MODIFY_REVENUES == source )
        {
            this.dispose ( ) ;
            //1st time for YOU
            if ( CHECK_LOG_FLAG == false )
            {
                Authentication_Window AW = new Authentication_Window ( IDENTIFIER [ 2 ] ) ;
            }//You alread logged on, and app <remember you>
            else  
            {
                MODIFY_GUI O = new MODIFY_GUI ( "R" ) ;
            }
        }
        
        if ( MODIFY_EXPENSES == source )
        {
            this.dispose ( ) ;
            //1st time for YOU
            if ( CHECK_LOG_FLAG == false )
            {
                Authentication_Window AW = new Authentication_Window ( IDENTIFIER [ 3 ] ) ;
            }//You alread logged on, and app <remember you>
            else  
            {
                MODIFY_GUI O = new MODIFY_GUI ( "E" ) ;
            }
        }
        
        if ( MONTHLY_REPORT == source )
        {
            this.dispose ( ) ;
            //1st time for YOU
            if ( CHECK_LOG_FLAG == false )
            {
                Authentication_Window AW = new Authentication_Window ( IDENTIFIER [ 4 ] ) ;
            }//You alread logged on, and app <remember you>
            else  
            {
                Monthly_Report OBJ = new Monthly_Report ( ) ;
            }
        }
        
        if ( SIGN_UP == source )
        {
            this.dispose ( ) ;
            Sign_Up_Window obj = new Sign_Up_Window ( ) ;
        }
        
        if ( LOGOUT == source)
        {
            if ( CHECK_LOG_FLAG == false )
            {
                JOptionPane.showMessageDialog ( null, "There is no Log On User Right Now..", "Info", JOptionPane.INFORMATION_MESSAGE, null ) ;
            }
            else
            {
               //Call method that signatures
               Total_FILES_Digital_Signature ( ) ;
               CHECK_LOG_FLAG = false ;  //Make it false so, authentication required, next time a user wants to do an operation
               USER_NAME.setText ( "" ) ;  //Clear name because you logouted
               JOptionPane.showMessageDialog ( null, "Successfull Logout", "Logout", JOptionPane.INFORMATION_MESSAGE, null ) ;
            }
        }   
     }//Telos methodou ACTION
    
    
    
    
    
    
    
    
    
    //Method at closing....
    public void Total_FILES_Digital_Signature (  )
    {
        
        //1st for SYMMETRIC_KEY.data for the current User
        INPUT_STREAM = null ;
        INPUT_STREAM_2 = null ;
        
        try
        {
            Message_Digest = MessageDigest.getInstance ( "SHA-256" ) ;
        }
        catch ( NoSuchAlgorithmException NSAE )
        {
            //
        }
        //
        //
        try
        {
          INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) + "\\SYMMETRIC_KEY.data" ) ;
          while ( true )
          {
              INPUT_STREAM_2 = new ObjectInputStream ( INPUT_STREAM ) ;
              byte [ ] Encrypted_Symmetric_Key_User = ( byte [ ] ) INPUT_STREAM_2.readObject ( ) ;
              Message_Digest.update ( Encrypted_Symmetric_Key_User ) ;
          } 
        }
        catch ( ClassNotFoundException CNFE )
        {
            //
        }
        catch ( IOException IOE )
        {
            //
        }
        //
        byte [ ] HASHED_SYMMETRIC_KEY_USER = Message_Digest.digest ( ) ;
        
        
        
        
        
        
        //-----------------
        
        
        
        
        
        
        //2nd for REVENUES.data for the current User
        INPUT_STREAM = null ;
        INPUT_STREAM_2 = null ;
        
        try
        {
            Message_Digest = null ;
            Message_Digest = MessageDigest.getInstance ( "SHA-256" ) ;
        }
        catch ( NoSuchAlgorithmException NSAE )
        {
            //
        }
        //
        //
        try
        {
          INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\REVENUES.data" ) ;
          while ( true )
          {
              System.out.println ( "xaxaa " ) ;
              INPUT_STREAM_2 = new ObjectInputStream ( INPUT_STREAM ) ;
              SealedObject Encrypted_Revenues_User = ( SealedObject ) INPUT_STREAM_2.readObject ( ) ;  //SOS
              //http://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
              ByteArrayOutputStream bos = new ByteArrayOutputStream ( ) ;
              ObjectOutput out = null;
              try 
              {
                 out = new ObjectOutputStream ( bos ) ;   
                 out.writeObject ( Encrypted_Revenues_User ) ;
                 MY_BYTES = bos.toByteArray ( ) ;
              }
              catch ( Exception E )
              {
                  
              }
              Message_Digest.update ( MY_BYTES ) ;
          } 
        }
        catch ( ClassNotFoundException CNFE )
        {
            //
        }
        catch ( IOException IOE )
        {
            //
        }
        //
        byte [ ] HASHED_REVENUES_USER = Message_Digest.digest ( ) ;
        
        
        
        
        
        
        
        //---------------
        
        
        
        
        
        //3rd for EXPENSES.data for the current User
        INPUT_STREAM = null ;
        INPUT_STREAM_2 = null ;
        
        try
        {
            Message_Digest = null ;
            Message_Digest = MessageDigest.getInstance ( "SHA-256" ) ;
        }
        catch ( NoSuchAlgorithmException NSAE )
        {
            //
        }
        //
        //
        try
        {
          INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\EXPENSES.data" ) ;
          while ( true )
          {
              System.out.println ( "xaxaa " ) ;
              INPUT_STREAM_2 = new ObjectInputStream ( INPUT_STREAM ) ;
              SealedObject Encrypted_Revenues_User = ( SealedObject ) INPUT_STREAM_2.readObject ( ) ;  //SOS
              //http://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
              ByteArrayOutputStream bos = new ByteArrayOutputStream ( ) ;
              ObjectOutput out = null;
              try 
              {
                 out = new ObjectOutputStream ( bos ) ;   
                 out.writeObject ( Encrypted_Revenues_User ) ;
                 MY_BYTES = bos.toByteArray ( ) ;
              }
              catch ( Exception E )
              {
                  
              }
              Message_Digest.update ( MY_BYTES ) ;
          } 
        }
        catch ( ClassNotFoundException CNFE )
        {
            //
        }
        catch ( IOException IOE )
        {
            //
        }
        //
        byte [ ] HASHED_EXPENSES_USER = Message_Digest.digest ( ) ;
        
        //Now we produced 3 Hashes, so we will create the pairs (STRING-FILENAME   --  Hash value )
        
        
        HASH_MAP = new HashMap < String, byte [ ] > ( ) ;
        
        HASH_MAP.put ( "SYMMETRIC_KEY", HASHED_SYMMETRIC_KEY_USER ) ;
        HASH_MAP.put ( "REVENUES.data", HASHED_REVENUES_USER ) ;
        HASH_MAP.put ( "EXPENSES.data", HASHED_EXPENSES_USER ) ;
        
            ByteArrayOutputStream bos = new ByteArrayOutputStream ( ) ;
            try
            {
                ObjectOutputStream out = new ObjectOutputStream ( bos ) ;
                out.writeObject ( HASH_MAP ) ;
                TOTAL_HASH_FROM_3HASHES = bos.toByteArray ( ) ;   //hash from 3 hashes
            }
            catch ( Exception E )
            {
                
            }
            //Compute TOTAL HASH
            MessageDigest MD_ = null ;
            try
            {
                MD_ = MessageDigest.getInstance ( "SHA-256" ) ;
            }
            catch ( NoSuchAlgorithmException NSAE )
            {
                //
            }
            MD_.update ( TOTAL_HASH_FROM_3HASHES ) ;
            byte [ ] TOTAL_HASH = MD_.digest ( );

        //Now we will sign this TOTAL HASH
            try
            {
                //Note: When specifying the signature algorithm name, you should also include the name of the message digest algorithm used by the signature algorithm.
                 Digital_Sign = Signature.getInstance ( "SHA256withRSA" ) ;  
            }
            catch ( NoSuchAlgorithmException NSAE )
            {
                
            }
            //Before a Signature object can be used for signing or verifying, it must be initialized. The initialization method for signing requires a private key.
            try
            {
               //Here open stream to read PRIVATE KEY
               INPUT_STREAM = null ;
               INPUT_STREAM_2 = null ;
               PrivateKey PrivKey = null ;
               try
               {
                  INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\PRIVATE_KEY.data" ) ;
                  INPUT_STREAM_2 = new ObjectInputStream ( INPUT_STREAM ) ;
                  PrivKey = ( PrivateKey ) INPUT_STREAM_2.readObject ( ) ;
               }
               catch ( ClassNotFoundException CNFE )
               {
                   
               }
               catch ( IOException IOE )
               {
                   
               }
               
               //
                
               Digital_Sign.initSign ( PrivKey ) ;
               Digital_Sign.update ( TOTAL_HASH ) ;
               //Generate the Signature
               //Once all of the data has been supplied to the Signature object, you can generate the digital signature of that data.
               byte [ ] DIGITAL_SIGNATURE_TOTAL = Digital_Sign.sign ( ) ;
               //Write to suitable File
               OUTPUT_STREAM = null ;
               OUTPUT_STREAM_2 = null ;
               //
               try
               {
                 OUTPUT_STREAM = new FileOutputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\DIG_SIGN.data" ) ;
                 OUTPUT_STREAM_2 = new ObjectOutputStream ( OUTPUT_STREAM ) ;
                 OUTPUT_STREAM_2.writeObject ( DIGITAL_SIGNATURE_TOTAL ) ;
               }
               catch ( Exception E )
               {
                   
               }
               
            }
            catch ( InvalidKeyException IKE )
            {
                
            }
            catch ( SignatureException SE )
            {
                
            }
    }//END OF Digital method
}//END CLASS
        
        
  
    

