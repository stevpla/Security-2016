

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;






public class Insert_Financial_Expenses_Window  extends JFrame implements ActionListener
{
    
    private JLabel LAMOUNT, LDATE, LDESCRIPTION ;
    private JTextField TFAMOUNT, TFDESCRIPTION ;
    private JComboBox JCBDAY, JCBMONTH, JCBYEAR ;
    private String [ ] DAY, MONTH, YEAR ;
    private JButton SAVE_EXPENSES, CLEAR, CANCEL ;
    private FileOutputStream OUTPUT_STREAM = null ;
    private ObjectOutputStream OUTPUT_STREAM_2 = null ;
    private FileInputStream INPUT_STREAM = null ;
    private ObjectInputStream INPUT_STREAM_2 = null ;
    private Date DaTe = null ;
    private boolean DATE_FLAG = false ;
    private byte [ ] SYMMETRIC_KEY_TEMP = null, DECRYPTED_SYMMETRIC_KEY = null ;
    private PrivateKey PRIVATE_KEY_TEMP = null ;
    private Cipher CIPHER = null ;
    
    
    //Constructor
    public Insert_Financial_Expenses_Window ( )
    {
        super ( "Insert_Financial_Expenses" ) ;
       
        this.addWindowListener ( new java.awt.event.WindowAdapter ( ) 
        {
              @Override             //Otan patiseis to kokkino x tote aytomata ginetai LGOOUT
             public void windowClosing(java.awt.event.WindowEvent windowEvent) 
             {
                 Main_Menu OBJ = new Main_Menu ( ) ;     
             }
        } ) ;
        //Windows Closing
        setSize ( 900, 700 ) ;       // Size of Window
        setLocationRelativeTo ( null ) ;  // Display Window in center of Screen
        setVisible ( true ) ;
        
        
        
        // 1st Row
        LDESCRIPTION = new JLabel ( "Describe Expenses : " ) ;
        LDESCRIPTION.setForeground ( Color.BLACK ) ;
        LDESCRIPTION.setFont  (new Font ( "Courier New", Font.BOLD, 23 ) ) ;
        LDESCRIPTION.setBounds ( 35, 80, 400, 40 ) ;
        
        TFDESCRIPTION = new JTextField ( 40 ) ;
        TFDESCRIPTION.setForeground ( Color.BLACK ) ;
        TFDESCRIPTION.setBounds ( 305, 85, 400, 33 ) ;
        
        // 2nd Row
        LAMOUNT = new JLabel ( "Enter the amount of Expenses : " ) ;
        LAMOUNT.setForeground ( Color.BLACK ) ;
        LAMOUNT.setFont  (new Font ( "Courier New", Font.BOLD, 23 ) ) ;
        LAMOUNT.setBounds ( 35, 180, 450, 40 ) ;
        
        TFAMOUNT = new JTextField ( 20 ) ;
        TFAMOUNT.setForeground ( Color.BLACK ) ;
        TFAMOUNT.setBounds ( 460, 187, 220, 27 ) ;
        
        //Date
        LDATE = new JLabel ( "-- Enter Date : " ) ;
        LDATE.setForeground ( Color.BLUE ) ;
        LDATE.setFont  (new Font ( "Courier New", Font.BOLD, 23 ) ) ;
        LDATE.setBounds ( 50, 250, 400, 40 ) ;
        
        //Month
        MONTH = new String [ ] { "01", "02", "03", "04", "05", "06", "07", "08", "09", 
        "10", "11", "12" } ;
        
        //Year
        YEAR = new String [ ] { "2016", "2017", "2018", "2019", "2020", "2012" } ;
 
        //DAY
        DAY = new String [ ] { "01", "02", "03", "04", "05", "06", "07", "08", "09", 
        "10", "11", "12", "13", "14", "15", "16", "17", "18",
        "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" } ;
        
        //Combo Boxes
        JCBDAY = new JComboBox ( DAY ) ;
        JCBDAY.setBounds ( 280, 250 ,150, 42 ) ;
        
        JCBMONTH = new JComboBox ( MONTH ) ;
        JCBMONTH.setBounds ( 470, 250 ,150, 42 ) ;
        
        JCBYEAR = new JComboBox ( YEAR ) ;
        JCBYEAR.setBounds ( 660, 250 ,150, 42 ) ;
       
        //Buttons
        SAVE_EXPENSES = new JButton ( "Save_Expenses" )  ;
        SAVE_EXPENSES.setForeground ( Color.BLACK ) ;
        SAVE_EXPENSES.setBounds ( 100, 500, 200, 40 ) ;
      
        CANCEL = new JButton ( "Cancel" ) ;   // Just go previous Window
        CANCEL.setForeground ( Color.BLACK ) ;
        CANCEL.setBounds ( 320, 500, 200, 40 ) ;
        
        CLEAR = new JButton ( "Clear" ) ;   // Just go previous Window
        CLEAR.setForeground ( Color.BLACK ) ;
        CLEAR.setBounds ( 540, 500, 200, 40 ) ;
        
        
        // Manager Layout
        Container pane = getContentPane ( ) ;
        pane.setLayout ( null ) ;
        
        SAVE_EXPENSES.addActionListener ( this ) ;
        CLEAR.addActionListener ( this ) ;
        CANCEL.addActionListener ( this ) ;
        
        
        // Additions Components
        pane.add ( LDESCRIPTION ) ;
        pane.add ( TFDESCRIPTION ) ;
        pane.add ( LAMOUNT ) ;
        pane.add ( TFAMOUNT ) ;
        pane.add ( LDATE ) ;
        pane.add ( JCBDAY ) ;
        pane.add ( JCBMONTH ) ;
        pane.add ( JCBYEAR ) ;
        pane.add ( CANCEL ) ;
        pane.add ( SAVE_EXPENSES ) ;
        pane.add ( CLEAR ) ;
        
        setContentPane ( pane ) ;
    } // End of Constructor
    
    
    
    
    //Method Actions
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
        if ( CLEAR == source )
        {
            TFAMOUNT.setText ( "" ) ;
            TFDESCRIPTION.setText ( "" ) ;
            JCBDAY.setSelectedIndex ( 0 ) ;
            JCBMONTH.setSelectedIndex ( 0 ) ;
            JCBYEAR.setSelectedIndex ( 0 ) ;
        }
        
        if ( CANCEL == source )
        {
            this.dispose ( ) ;
            Main_Menu obj = new Main_Menu ( ) ;
        }
        
      
        if ( SAVE_EXPENSES == source )
        {
            if ( TFAMOUNT.getText ( ).equals ( "" )   ||   TFDESCRIPTION.getText ( ).equals ( "" ) )
            {
                JOptionPane.showMessageDialog ( null, "Please enter all fields before continue", "WARNING", JOptionPane.WARNING_MESSAGE, null ) ;
            }
            else
            {
                //Now make date, take date, month, and year and produce the date
                SimpleDateFormat DF = new SimpleDateFormat ( "dd/MM/yyyy" ) ;   //Define Format to check Valid Dates
                DF.setLenient ( false ) ;   // will make the parse method throw ParseException when the  string that entered is not in the specified format.
                try
                 {
                       DaTe = DF.parse ( (  ( String ) JCBDAY.getSelectedItem ( )+ "/" + ( String ) JCBMONTH.getSelectedItem ( )+ "/" + ( String )  JCBYEAR.getSelectedItem ( )  ).trim ( ) ) ;
                       DATE_FLAG = true ;
                 }
                 catch ( ParseException PE ) 
                 {
                     JOptionPane.showMessageDialog ( null, "Error -> " + PE.getLocalizedMessage ( ), "ParseException", JOptionPane.ERROR_MESSAGE, null ) ;
                 }
                
                
                
                
              if ( DATE_FLAG == true )
              {
                //Parse amount to double type
                double AmOuNt = Double.parseDouble ( TFAMOUNT.getText ( ) ) ;
                //create object to merge the 3 infos which be saved to File
                //But first open streams to write to the suitable File
                try
                {
                    //Now we open stream for read the symmetric key for User to encrypt the Financial Record
                    this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\SYMMETRIC_KEY.data" ) ;
                    this.INPUT_STREAM_2 = new ObjectInputStream ( this.INPUT_STREAM ) ;
                    SYMMETRIC_KEY_TEMP = ( byte [ ] ) this.INPUT_STREAM_2.readObject ( ) ;  //SOS MAY READ An object of byte array Type
                    //BUT have to read private key from file
                    this.INPUT_STREAM = null ;
                    this.INPUT_STREAM_2 = null ;
                    this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\PRIVATE_KEY.data" ) ;
                    this.INPUT_STREAM_2 = new ObjectInputStream ( this.INPUT_STREAM ) ;
                    PRIVATE_KEY_TEMP = ( PrivateKey ) this.INPUT_STREAM_2.readObject ( ) ;
                    //
                    //Now we have to decrypt Symmetric key with the private key to use it for encryption
                    try
                    {
                       CIPHER = Cipher.getInstance ( "RSA" ) ;
                       CIPHER.init ( Cipher.DECRYPT_MODE, PRIVATE_KEY_TEMP ) ;      //DECRYPT_MODE MODE static inT ,Constant used to initialize cipher to DECRYPT_MODE mode.
                       DECRYPTED_SYMMETRIC_KEY = CIPHER.doFinal ( SYMMETRIC_KEY_TEMP ) ;  // save and decrypt symmetric key to DECRYPTED_SYMMETRIC_KEY
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
                    catch ( IllegalBlockSizeException IBSE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + IBSE.getLocalizedMessage ( ), "Exception - IllegalBlockSizeException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    catch ( BadPaddingException BPE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + BPE.getLocalizedMessage ( ), "Exception - BadPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    //Now we encrypt the Financial Revenue with Symmetric algorithm AES, with symmetric key
                    try
                    {
                       //From byte to Key ,in order to use Decrypted Symmetric key to encrypt with this the Revenues
                       SecretKey DECRYPTES_NORMAL_SYMMETRIC_KEY_FOR_USE = new SecretKeySpec ( DECRYPTED_SYMMETRIC_KEY, "AES" ) ;
                       //Convert bytes to A SecretKey
                       CIPHER = null ;
                       CIPHER = Cipher.getInstance ( "AES" ) ;
                       CIPHER.init ( Cipher.ENCRYPT_MODE, DECRYPTES_NORMAL_SYMMETRIC_KEY_FOR_USE ) ;      //ENCRYPT_MODE MODE static inT ,Constant used to initialize cipher to ENCRYPT_MODE mode.
                       //What is SealedObject?
                       //SealedObject encapsulate the original java object(it should implements Serializable). It use the cryptographic algorithm to seals the serialized content of the object.
                       SealedObject SEALEDOBJECT = new SealedObject ( new Financial_Expenses ( TFDESCRIPTION.getText ( ), AmOuNt, DaTe ), CIPHER ) ;
                       //Constructs a SealedObject from any Serializable object.
                       
                       //And now i am able to write it to File
                       this.OUTPUT_STREAM = new FileOutputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\EXPENSES.data", true ) ;
                       this.OUTPUT_STREAM_2 = new ObjectOutputStream ( this.OUTPUT_STREAM ) ;
                       this.OUTPUT_STREAM_2.writeObject ( SEALEDOBJECT ) ;  //Write the ENCRYPTED OBJECT - REVENUE
                    }
                    catch ( InvalidKeyException IKE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + IKE.getLocalizedMessage ( ), "Exception - InvalidKeyException", JOptionPane.ERROR_MESSAGE, null ) ;
                        IKE.printStackTrace ( ) ;
                    }
                    catch ( NoSuchAlgorithmException NSAE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + NSAE.getLocalizedMessage ( ), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    catch ( NoSuchPaddingException NSPE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + NSPE.getLocalizedMessage ( ), "Exception - NoSuchPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    catch ( IllegalBlockSizeException IBSE )
                    {
                        JOptionPane.showMessageDialog ( null, "Error -> " + IBSE.getLocalizedMessage ( ), "Exception - IllegalBlockSizeException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    JOptionPane.showMessageDialog ( null, "Successfull Insertion", "INFO", JOptionPane.INFORMATION_MESSAGE, null ) ;
                    int ANSWER = JOptionPane.showConfirmDialog (null, "Would You Like to insert new EXPENSES?","Info", JOptionPane.YES_NO_OPTION ) ;
                    if ( ANSWER == JOptionPane.YES_OPTION )
                    {
                       TFAMOUNT.setText ( "" ) ;
                       TFDESCRIPTION.setText ( "" ) ;
                       JCBDAY.setSelectedIndex ( 0 ) ;
                       JCBMONTH.setSelectedIndex ( 0 ) ;
                       JCBYEAR.setSelectedIndex ( 0 ) ;
                    }
                    else
                    {
                        this.dispose ( ) ;
                        Main_Menu OBJ = new Main_Menu ( ) ;
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
                    JOptionPane.showMessageDialog ( null, "Error -> " + IOE.getLocalizedMessage ( ), "Exception - IOException", JOptionPane.ERROR_MESSAGE, null ) ;
                }
              } //flag
              else
              {
                  JOptionPane.showMessageDialog ( null, "Please try a valid DATE ", "Invalid Date", JOptionPane.ERROR_MESSAGE, null ) ;
              }//flag - false
            }
        }//End of Save_Expenses
    }//End of method
} //End of Class
