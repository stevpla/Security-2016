
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Calendar;
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




public class Monthly_Report extends JFrame implements ActionListener
{
    
    
    private JLabel LABEL_CHOOSE_MONTH, INFO_LABEL ;
    private JButton CHOOSE_MONTH_BUTTON ;
    private FileInputStream INPUT_STREAM = null ;
    private ObjectInputStream INPUT_STREAM_2 = null ;
    private ArrayList < Object > LIST = new ArrayList < Object > ( ) ;
    private JComboBox JCB_MONTH ;
    private String [ ] MONTH = null ;
    
    
    
    
    
    
    
    
    public Monthly_Report ( )
    {
        super ( "Monthly Report of Revenues and Expenses" ) ;
        setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE ) ;      //If you press X the red one icon then terminate this Window
        //
        //https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
        //In this technique a WindowListener interface implentation is added to the frame, where the listener has a method, windowClosing(), that is called when the frame is closed.
        //In practice, on overrides the windowClosing() method of WindowAdapter, a no-op implementation of WindowListener.   This way, one doesn't have to worry about all the other methods of the WindowListener.
        this.addWindowListener ( new java.awt.event.WindowAdapter ( ) 
        {
              @Override             //Otan patiseis to kokkino x tote aytomata ginetai LGOOUT
             public void windowClosing ( java.awt.event.WindowEvent windowEvent ) 
             {
                 Main_Menu ObJ = new Main_Menu ( ) ;
             }
        } ); 
        setSize ( 1250, 300 ) ;       // Size of Window
        setLocationRelativeTo ( null ) ;  // Display Window in center of Screen
        setVisible ( true ) ;
        
        
        //
        LABEL_CHOOSE_MONTH = new JLabel ( "Choose the month you want to see Revenues-Expenses" ) ;
        LABEL_CHOOSE_MONTH.setForeground ( Color.BLACK ) ;
        LABEL_CHOOSE_MONTH.setFont ( new Font ( "Courier New", Font.BOLD, 26 ) ) ;
        LABEL_CHOOSE_MONTH.setBounds ( 15, 50, 900, 40 ) ;
        
        INFO_LABEL = new JLabel ( "                Press <Search Button>" ) ;
        INFO_LABEL.setForeground ( Color.BLUE ) ;
        INFO_LABEL.setFont ( new Font ( "Courier New", Font.BOLD, 28 ) ) ;
        INFO_LABEL.setBounds ( 20, 100, 800, 100 ) ;
        
        MONTH  = new String [ ] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" } ;
        
        JCB_MONTH = new JComboBox ( MONTH ) ;
        JCB_MONTH.setBounds ( 830, 50 ,150, 35 ) ;
        
        CHOOSE_MONTH_BUTTON = new JButton ( "Search" ) ;
        CHOOSE_MONTH_BUTTON.setForeground ( Color.BLACK ) ;
        CHOOSE_MONTH_BUTTON.setBounds ( 1000, 50, 180, 38 ) ;
        
        Container pane = getContentPane ( ) ;  //Diaxeiristis
        pane.setLayout ( null ) ;   //Deactivate Manager Layout
    
        //
        pane.add ( LABEL_CHOOSE_MONTH ) ;
        pane.add ( CHOOSE_MONTH_BUTTON ) ;
        pane.add ( JCB_MONTH ) ;
        pane.add ( INFO_LABEL ) ;
        //
        CHOOSE_MONTH_BUTTON.addActionListener ( this ) ;

        setContentPane ( pane ) ;
        
    }//End of constructor
    
    
    
    
    
    //action method
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
        if ( CHOOSE_MONTH_BUTTON == source )
        {
            int TEMP_CHOICE = JCB_MONTH.getSelectedIndex ( ) + 1 ;
            System.out.println ( " The selected month is -> " + TEMP_CHOICE ) ;
            //Sos if works
            try
            {   //First take private key to decrypt symmetric key
                this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\PRIVATE_KEY.data" ) ;
                this.INPUT_STREAM_2 = new ObjectInputStream ( this.INPUT_STREAM ) ;
                PrivateKey PRIVATE_KEY = ( PrivateKey ) this.INPUT_STREAM_2.readObject ( ) ;
                
                //Then take encrypted symmetric key and decrypt it
                this.INPUT_STREAM = null ;
                this.INPUT_STREAM_2 = null ;
                //
                this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\SYMMETRIC_KEY.data" ) ;
                this.INPUT_STREAM_2 = new ObjectInputStream ( this.INPUT_STREAM ) ;
                byte [ ] ENCRYPTED_SYMMETRIC_KEY = ( byte [ ] ) this.INPUT_STREAM_2.readObject ( ) ;
                //decrypt it now with the private key
                Cipher CIPHER = null ;
                try
                { //decrypt it now using private key
                  CIPHER = Cipher.getInstance ( "RSA" ) ;
                  CIPHER.init ( Cipher.DECRYPT_MODE, PRIVATE_KEY ) ;
                  byte [ ] DECRYPTED_SK = CIPHER.doFinal ( ENCRYPTED_SYMMETRIC_KEY ) ;
                  //convert bytes to Key
                  SecretKey ORIGINAL_SYMMETRIC_KEY = new SecretKeySpec ( DECRYPTED_SK, "AES" ) ;
                  //
                  //Now read from 2 files the objects and decrypt each object and check date
                  this.INPUT_STREAM = null ;
                  this.INPUT_STREAM_2 = null ;
                  //REVENUES.data
                  try
                  {
                     this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\REVENUES.data" ) ;
                     while ( true )
                     {
                         this.INPUT_STREAM_2 = new ObjectInputStream ( this.INPUT_STREAM ) ;
                         SealedObject ENCRYPTED_OBJECT_FINANCIAL_R_E = ( SealedObject ) this.INPUT_STREAM_2.readObject ( ) ;  //read object from file
                         //Now decrypt object in order to save it to List to process after
                         CIPHER = null ;
                         CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                         CIPHER.init ( Cipher.DECRYPT_MODE, ORIGINAL_SYMMETRIC_KEY  ) ;
                         Financial_Revenue ORIGINAL_FINANCIAL_OBJECT = ( Financial_Revenue ) ENCRYPTED_OBJECT_FINANCIAL_R_E.getObject ( CIPHER ) ;
                         //extract orginal object from decrypted Sealed Object
                         //Retrieves the original (encapsulated) object.
         
                         //Now check Date to see if is in Month that User choose
                         Date Temp_date = ORIGINAL_FINANCIAL_OBJECT.Get_DATE ( ) ;
                         int month = Temp_date.getMonth ( ) +1  ;
                         //with the value 0 representing January.
                         System.out.println ( "O minas aytou tou esoDou einai -> " + month ) ;
                         //
                         if ( month == TEMP_CHOICE )
                         {
                             LIST.add ( ORIGINAL_FINANCIAL_OBJECT ) ;
                         }
                     }  //End of while
                  } //End of try
                  catch ( IOException IOE )
                  {
                      //End of file reached
                  }
                  //Now for the Expenses.data
                  try
                  {
                     this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\EXPENSES.data" ) ;
                     while ( true )
                     {
                         this.INPUT_STREAM_2 = new ObjectInputStream ( this.INPUT_STREAM ) ;
                         SealedObject ENCRYPTED_OBJECT_FINANCIAL_R_E = ( SealedObject ) this.INPUT_STREAM_2.readObject ( ) ;  //read object from file
                         //Now decrypt object in order to save it to List to process after
                         CIPHER = null ;
                         CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                         CIPHER.init ( Cipher.DECRYPT_MODE, ORIGINAL_SYMMETRIC_KEY  ) ;
                         Financial_Expenses ORIGINAL_FINANCIAL_OBJECT = ( Financial_Expenses ) ENCRYPTED_OBJECT_FINANCIAL_R_E.getObject ( CIPHER ) ;
                         //extract orginal object from decrypted Sealed Object
                         //Retrieves the original (encapsulated) object.
                         
                         //Now check Date to see if is in Month that User choose
                         Date Temp_date = ORIGINAL_FINANCIAL_OBJECT.Get_DATE ( ) ;
                         int month = Temp_date.getMonth ( ) +1  ;
                         //Calendar cal = Calendar.getInstance ( ) ;
                         //cal.setTime ( Temp_date ) ;
                         //int month = cal.get ( Calendar.MONTH ) ;
                         System.out.println ( "O minas aytou tou esoDou einai -> " + month ) ;
                         //
                         if ( month == TEMP_CHOICE )
                         {
                             LIST.add ( ORIGINAL_FINANCIAL_OBJECT) ;
                         }
                     }  //End of while
                  } //End of try
                  catch ( IOException IOE )
                  {
                      //End of file reached
                  }
                  //Now pass LIST to GUI, to appear to User
                  this.dispose ( ) ;
                  APPEARANCE_WINDOW GUI = new APPEARANCE_WINDOW ( LIST ) ;
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
        }
    }//End of action
 
    
    
}//End of class
