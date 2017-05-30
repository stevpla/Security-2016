
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JOptionPane ;








public class MODIFY_GUI extends JFrame implements ActionListener
{
    
    
    
    private String GLOBAL_IDENT, DAY [ ], MONTH [ ], YEAR [ ] ;
    private JLabel LABEL ;
    private JButton SEARCH ;
    private JComboBox JCB_DAY, JCB_MONTH, JCB_YEAR ;
    private boolean DATE_FLAG = false ;
    private FileInputStream INPUT_STREAM = null ;
    private ObjectInputStream INPUT_STREAM_2 = null ;
    private SecretKey ORIGINAL_SYMMETRIC_KEY  = null ;
    private ArrayList < Object > My_lIST = new ArrayList < Object > ( ) ;
    private ArrayList < Object > Y = new ArrayList < Object > ( ) ;
    
    
    
    
    public MODIFY_GUI ( String IDENT )
    {
        super ( "Insert Modification Date " ) ;
        GLOBAL_IDENT = IDENT ;
        
        setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE ) ;      //If you press X the red one icon then terminate this Window
        //
        //https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
        //In this technique a WindowListener interface implentation is added to the frame, where the listener has a method, windowClosing(), that is called when the frame is closed.
        //In practice, on overrides the windowClosing() method of WindowAdapter, a no-op implementation of WindowListener.   This way, one doesn't have to worry about all the other methods of the WindowListener.
        this.addWindowListener ( new java.awt.event.WindowAdapter ( ) 
        {
              @Override             
             public void windowClosing ( java.awt.event.WindowEvent windowEvent ) 
             {
                 Main_Menu OBJ = new Main_Menu ( ) ;
             }
        } ); 
        //
        setSize ( 1050, 300 ) ;       // Size of Window
        setLocationRelativeTo ( null ) ;  // Display Window in center of Screen
        setVisible ( true ) ;
        
        //
        Container pane = getContentPane ( ) ;  //Diaxeiristis
        pane.setLayout ( null ) ;   //Deactivate Manager Layout
        
        //
        if ( GLOBAL_IDENT.equals ( "R" ) )
        {
           LABEL = new JLabel ( "Determine the Date you want to modify Revenues" ) ;
           LABEL.setForeground ( Color.BLACK ) ;
           LABEL.setFont ( new Font ( "Courier New", Font.BOLD, 26 ) ) ;
           LABEL.setBounds ( 100, 50, 900, 40 ) ;
           pane.add ( LABEL ) ;
        }
        else //"E"
        {
           LABEL = new JLabel ( "Determine the Date you want to modify Expenses" ) ;
           LABEL.setForeground ( Color.BLACK ) ;
           LABEL.setFont ( new Font ( "Courier New", Font.BOLD, 26 ) ) ;
           LABEL.setBounds ( 100, 50, 900, 40 ) ;
           pane.add ( LABEL ) ;
        }
        
        DAY  = new String [ ] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"
        , "26", "27", "28", "29","30", "31" } ;
        
        JCB_DAY = new JComboBox ( DAY ) ;
        JCB_DAY.setBounds ( 100, 100 ,150, 35 ) ;
        
        MONTH  = new String [ ] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" } ;
        
        JCB_MONTH = new JComboBox ( MONTH ) ;
        JCB_MONTH.setBounds ( 300, 100 ,150, 35 ) ;
        
        YEAR  = new String [ ] { "2016", "2017", "2018" } ;
        
        JCB_YEAR = new JComboBox ( YEAR ) ;
        JCB_YEAR.setBounds ( 500, 100 ,150, 35 ) ;
        
        SEARCH = new JButton ( "Search" ) ;
        SEARCH.setForeground ( Color.BLACK ) ;
        SEARCH.setBounds ( 720, 100, 170, 40 ) ;
        
        pane.add ( JCB_DAY ) ;
        pane.add ( JCB_MONTH ) ;
        pane.add ( JCB_YEAR ) ;
        pane.add ( SEARCH ) ;
        //
        SEARCH.addActionListener ( this ) ;
        
    }//End of Constructor
    
    
    
    
    
    
    
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
        if ( SEARCH == source )
        {
            
            SimpleDateFormat DF = new SimpleDateFormat ( "dd/MM/yyyy" ) ;   //Define Format to check Valid Dates
            Date DaTe = null ;
            DF.setLenient ( false ) ;   // will make the parse method throw ParseException when the  string that entered is not in the specified format.
            try
            {
                DaTe = DF.parse ( (  ( String ) JCB_DAY.getSelectedItem ( )+ "/" + ( String ) JCB_MONTH.getSelectedItem ( )+ "/" + ( String )  JCB_YEAR.getSelectedItem ( )  ).trim ( ) ) ;
                DATE_FLAG = true ;
            }
            catch ( ParseException PE ) 
            {
                JOptionPane.showMessageDialog ( null, "Error -> " + PE.getLocalizedMessage ( ), "ParseException", JOptionPane.ERROR_MESSAGE, null ) ;
            }
            
            if ( DATE_FLAG == true )
            {
                DATE_FLAG = false ;   //clear
                
              if ( GLOBAL_IDENT.equals ( "R" ) )
              { 
                try
                {
                    //First take private key to decrypt symmetric key
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
                    {  //decrypt it now using private key
                       CIPHER = Cipher.getInstance ( "RSA" ) ;
                       CIPHER.init ( Cipher.DECRYPT_MODE, PRIVATE_KEY ) ;
                       byte [ ] DECRYPTED_SK = CIPHER.doFinal ( ENCRYPTED_SYMMETRIC_KEY ) ;
                       //convert bytes to Key
                       ORIGINAL_SYMMETRIC_KEY = new SecretKeySpec ( DECRYPTED_SK, "AES" ) ;
                       //
                      //Now read from 2 files the objects and decrypt each object and check date
                      this.INPUT_STREAM = null ;
                      this.INPUT_STREAM_2 = null ;
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
                    catch ( BadPaddingException BPE )
                    {
                         JOptionPane.showMessageDialog ( null, "Error -> " + BPE.getLocalizedMessage ( ), "Exception - BadPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    catch ( InvalidKeyException IKE )
                    {
                         JOptionPane.showMessageDialog ( null, "Error -> " + IKE.getLocalizedMessage ( ), "Exception - InvalidKeyException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    //READ FILE REVENUE NOW
                   this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\REVENUES.data" ) ;
                   
                  try
                  {
                     while ( true )
                     {
                       this.INPUT_STREAM_2 = new ObjectInputStream ( this.INPUT_STREAM ) ;
                       SealedObject REVENUE_x = ( SealedObject ) this.INPUT_STREAM_2.readObject ( ) ;
                       //FIRST decrypt it
                       CIPHER = null ;
                       CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                       CIPHER.init ( Cipher.DECRYPT_MODE, ORIGINAL_SYMMETRIC_KEY  ) ;
                       Financial_Revenue ORIGINAL_FINANCIAL_OBJECT = ( Financial_Revenue ) REVENUE_x.getObject ( CIPHER ) ;
                       //Now we have the decrypted revenue and we can compare to see if has the date the user choose
                       if ( ( ORIGINAL_FINANCIAL_OBJECT.Get_DATE ( ) ).compareTo ( DaTe )    ==   0 )
                       {
                           My_lIST.add ( ORIGINAL_FINANCIAL_OBJECT ) ;  //Add it to the List
                       }
                       else
                       {   //keep the rest Revenues
                           Y.add ( ORIGINAL_FINANCIAL_OBJECT ) ;
                       }
                     }
                  }
                  catch ( IOException IOE )
                  {
                      //Just break , EOF
                  }
                   //Now pass the list to selection GUI
                   this.dispose ( ) ;
                   Modify_appearance_Window ObJ = new Modify_appearance_Window ( "R", My_lIST, Y ) ;
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
                catch ( BadPaddingException BPE )
                {
                    JOptionPane.showMessageDialog ( null, "Error -> " + BPE.getLocalizedMessage ( ), "Exception - BadPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                }
                catch ( InvalidKeyException IKE )
                {
                     JOptionPane.showMessageDialog ( null, "Error -> " + IKE.getLocalizedMessage ( ), "Exception - InvalidKeyException", JOptionPane.ERROR_MESSAGE, null ) ;
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
              else if ( GLOBAL_IDENT.equals ( "E" ) )   //Expense, "E"
              {
                try
                {
                    //First take private key to decrypt symmetric key
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
                    {  //decrypt it now using private key
                       CIPHER = Cipher.getInstance ( "RSA" ) ;
                       CIPHER.init ( Cipher.DECRYPT_MODE, PRIVATE_KEY ) ;
                       byte [ ] DECRYPTED_SK = CIPHER.doFinal ( ENCRYPTED_SYMMETRIC_KEY ) ;
                       //convert bytes to Key
                       ORIGINAL_SYMMETRIC_KEY = new SecretKeySpec ( DECRYPTED_SK, "AES" ) ;
                       //
                      //Now read from 2 files the objects and decrypt each object and check date
                      this.INPUT_STREAM = null ;
                      this.INPUT_STREAM_2 = null ;
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
                    catch ( BadPaddingException BPE )
                    {
                         JOptionPane.showMessageDialog ( null, "Error -> " + BPE.getLocalizedMessage ( ), "Exception - BadPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    catch ( InvalidKeyException IKE )
                    {
                         JOptionPane.showMessageDialog ( null, "Error -> " + IKE.getLocalizedMessage ( ), "Exception - InvalidKeyException", JOptionPane.ERROR_MESSAGE, null ) ;
                    }
                    //READ FILE REVENUE NOW
                   this.INPUT_STREAM = new FileInputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\EXPENSES.data" ) ;
                   
                  try
                  {
                     while ( true )
                     {
                       this.INPUT_STREAM_2 = new ObjectInputStream ( this.INPUT_STREAM ) ;
                       SealedObject EXPENSE_x = ( SealedObject ) this.INPUT_STREAM_2.readObject ( ) ;
                       //FIRST decrypt it
                       CIPHER = null ;
                       CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                       CIPHER.init ( Cipher.DECRYPT_MODE, ORIGINAL_SYMMETRIC_KEY  ) ;
                       Financial_Expenses ORIGINAL_FINANCIAL_OBJECT = ( Financial_Expenses ) EXPENSE_x.getObject ( CIPHER ) ;
                       //Now we have the decrypted revenue and we can compare to see if has the date the user choose
                       if ( ( ORIGINAL_FINANCIAL_OBJECT.Get_DATE ( ) ).compareTo ( DaTe )    ==   0 )
                       {
                           My_lIST.add ( ORIGINAL_FINANCIAL_OBJECT ) ;  //Add it to the List
                       }
                       else
                       {   //keep the rest Revenues
                           Y.add ( ORIGINAL_FINANCIAL_OBJECT ) ;
                       }
                     }
                  }
                  catch ( IOException IOE )
                  {
                      //Just break , EOF
                  }
                   //Now pass the list to selection GUI
                   this.dispose ( ) ;
                   Modify_appearance_Window ObJ = new Modify_appearance_Window ( "E", My_lIST, Y ) ;
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
                catch ( BadPaddingException BPE )
                {
                    JOptionPane.showMessageDialog ( null, "Error -> " + BPE.getLocalizedMessage ( ), "Exception - BadPaddingException", JOptionPane.ERROR_MESSAGE, null ) ;
                }
                catch ( InvalidKeyException IKE )
                {
                     JOptionPane.showMessageDialog ( null, "Error -> " + IKE.getLocalizedMessage ( ), "Exception - InvalidKeyException", JOptionPane.ERROR_MESSAGE, null ) ;
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
              }//end of else "E"
            }
            else
            {
                JOptionPane.showMessageDialog ( null, "Please try a valid DATE ", "Invalid Date", JOptionPane.ERROR_MESSAGE, null ) ;
            }
            
        }
       
    }//End of action
    
}//End of Class
