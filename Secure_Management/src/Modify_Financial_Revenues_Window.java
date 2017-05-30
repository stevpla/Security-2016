
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;







public class Modify_Financial_Revenues_Window extends JFrame implements ActionListener
{
    
    
    private JLabel LGEN, LDESCRIPTION, LAMOUNT ;
    private JTextField TFDESCRIPTION, TFAMOUNT ;
    private JButton MODIFY ;
    private Financial_Revenue FR ;
    private ArrayList < Object > LIST = new ArrayList < Object > ( ) ;
    private ArrayList < Object > ALL_LIST = new ArrayList < Object > ( ) ;
    private FileOutputStream OUTPUT_STREAM = null ;
    private ObjectOutputStream OUTPUT_STREAM_2 = null ;
    private FileInputStream INPUT_STREAM = null ;
    private ObjectInputStream INPUT_STREAM_2 = null ;
    private byte [ ] SYMMETRIC_KEY_TEMP = null, DECRYPTED_SYMMETRIC_KEY = null ;
    private PrivateKey PRIVATE_KEY_TEMP = null ;
    private Cipher CIPHER = null ;
    
    
    
    //Constructor
    public Modify_Financial_Revenues_Window ( Financial_Revenue FR, ArrayList < Object > LIST, ArrayList < Object > ALL_LIST )
    {
        super ( "Modify Financial Revenue" ) ;
        
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
                 Main_Menu OBJ = new Main_Menu ( ) ;
             }
        } ); 
        //
        
        setSize ( 800, 650 ) ;       // Size of Window
        setLocationRelativeTo ( null ) ;  // Display Window in center of Screen
        setVisible ( true ) ;
        
        //
        this.FR = FR ;
        this.LIST = LIST ;
        this.ALL_LIST = ALL_LIST ;
        
        //
        LGEN = new JLabel ( "Modify your Financial_Revenue " ) ;
        LGEN.setForeground ( Color.BLACK ) ;
        LGEN.setFont  (new Font ( "Courier New", Font.BOLD, 30 ) ) ;
        LGEN.setBounds ( 100, 10, 700, 40 ) ;
        
        LDESCRIPTION = new JLabel ( "Description :" ) ;
        LDESCRIPTION.setForeground ( Color.BLACK ) ;
        LDESCRIPTION.setFont  (new Font ( "Courier New", Font.BOLD, 30 ) ) ;
        LDESCRIPTION.setBounds ( 20, 110, 500, 40 ) ;
        
        LAMOUNT = new JLabel ( "Amount :" ) ;
        LAMOUNT.setForeground ( Color.BLACK ) ;
        LAMOUNT.setFont  (new Font ( "Courier New", Font.BOLD, 30 ) ) ;
        LAMOUNT.setBounds ( 20, 200, 900, 40 ) ;
        
        //
        TFDESCRIPTION = new JTextField ( 20 ) ;
        TFDESCRIPTION.setForeground ( Color.BLACK ) ;
        TFDESCRIPTION.setBounds ( 260, 115, 350, 30 ) ;
        
        TFAMOUNT = new JTextField ( 20 ) ;
        TFAMOUNT.setForeground ( Color.BLACK ) ;
        TFAMOUNT.setBounds ( 170, 205, 90, 30 ) ;
        
        //
        MODIFY = new JButton ( "Modify" ) ;
        MODIFY.setForeground ( Color.gray ) ;
        MODIFY.setBounds ( 300, 500, 120, 60 ) ;
        MODIFY.addActionListener ( this ) ;
        
        //Manager
        Container pane = getContentPane ( ) ;  //Diaxeiristis
        pane.setLayout ( null ) ;   //Deactivate Manager Layout
    
        //
        pane.add ( LGEN ) ;
        pane.add ( LDESCRIPTION ) ;
        pane.add ( LAMOUNT ) ;
        pane.add ( TFDESCRIPTION ) ;
        pane.add ( TFAMOUNT ) ;
        pane.add ( MODIFY ) ;
        setContentPane ( pane ) ;
    }//End of Constructor
    
    
    
    //action
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
        
       try
       {
            //Now we open stream for read the symmetric key for User to encrypt the Financial Record
            this.INPUT_STREAM = new FileInputStream("C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME() + "\\SYMMETRIC_KEY.data");
            this.INPUT_STREAM_2 = new ObjectInputStream(this.INPUT_STREAM);
            SYMMETRIC_KEY_TEMP = (byte[]) this.INPUT_STREAM_2.readObject();  //SOS MAY READ An object of byte array Type
            //BUT have to read private key from file
            this.INPUT_STREAM = null;
            this.INPUT_STREAM_2 = null;
            this.INPUT_STREAM = new FileInputStream("C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\PRIVATE_KEY.data");
            this.INPUT_STREAM_2 = new ObjectInputStream(this.INPUT_STREAM);
            PRIVATE_KEY_TEMP = (PrivateKey) this.INPUT_STREAM_2.readObject();
                    //
            //Now we have to decrypt Symmetric key with the private key to use it for encryption
            try
            {
                CIPHER = Cipher.getInstance("RSA");
                CIPHER.init(Cipher.DECRYPT_MODE, PRIVATE_KEY_TEMP);      //DECRYPT_MODE MODE static inT ,Constant used to initialize cipher to DECRYPT_MODE mode.
                DECRYPTED_SYMMETRIC_KEY = CIPHER.doFinal(SYMMETRIC_KEY_TEMP);  // save and decrypt symmetric key to DECRYPTED_SYMMETRIC_KEY
            }
            catch (NoSuchAlgorithmException NSAE) 
            {
                JOptionPane.showMessageDialog(null, "Error -> " + NSAE.getLocalizedMessage(), "Exception - NoSuchAlgorithmException", JOptionPane.ERROR_MESSAGE, null);
            } 
            catch (NoSuchPaddingException NSPE) 
            {
                JOptionPane.showMessageDialog(null, "Error -> " + NSPE.getLocalizedMessage(), "Exception - NoSuchPaddingException", JOptionPane.ERROR_MESSAGE, null);
            } 
            catch (InvalidKeyException IKE) 
            {
                JOptionPane.showMessageDialog(null, "Error -> " + IKE.getLocalizedMessage(), "Exception - InvalidKeyException", JOptionPane.ERROR_MESSAGE, null);
            } 
            catch (IllegalBlockSizeException IBSE) 
            {
                JOptionPane.showMessageDialog(null, "Error -> " + IBSE.getLocalizedMessage(), "Exception - IllegalBlockSizeException", JOptionPane.ERROR_MESSAGE, null);
            } 
            catch (BadPaddingException BPE) 
            {
                JOptionPane.showMessageDialog(null, "Error -> " + BPE.getLocalizedMessage(), "Exception - BadPaddingException", JOptionPane.ERROR_MESSAGE, null);
            }

       }//try end block
       catch ( ClassNotFoundException CNFE ) 
       {

       }
       catch ( IOException IOE )
       {
           
       }
        
        
        
        
        if ( MODIFY == source )
        { 
            //extract values from the Object FR, keep them
            String DES = this.FR.Get_DESCRIPTION ( ) ;
            double AM = this.FR.Get_AMOUNT ( ) ;
            Date DAT = this.FR.Get_DATE ( ) ;
            
            //change with Set from textfields
            if ( !TFDESCRIPTION.getText ( ).equals ( "" )   &&   TFAMOUNT.getText ( ).equals ( "" ) )
            {
               this.FR.Set_DESCRIPTION ( TFDESCRIPTION.getText ( ) ) ;
               //search into list to find old delete it and add new
                for ( int i = 0 ;     i < this.LIST.size ( ) ;    i ++ )
                {
                    Financial_Revenue X = ( Financial_Revenue ) this.LIST.get ( i ) ;
                    if ( ( X.Get_DESCRIPTION ( ).equals ( DES ) )  &&  ( X.Get_AMOUNT ( ) == AM )   &&  (  X.Get_DATE ( ).compareTo ( DAT )  )   ==  0  )
                    {
                        LIST.remove ( i ) ;
                        LIST.add ( this.FR ) ;
                    }
                }
                //Merge 2 lists, and overwrite Data.
                 ArrayList < Financial_Revenue > TOTAL = new ArrayList < Financial_Revenue > ( ) ;
                    
                 for ( int i = 0 ;    i < this.LIST.size ( ) ;     i ++ )
                 {
                     TOTAL.add ( ( Financial_Revenue ) this.LIST.get ( i ) ) ;
                 }
                 for ( int i = 0 ;    i < this.ALL_LIST.size ( ) ;     i ++ )
                 {
                     TOTAL.add ( ( Financial_Revenue ) this.ALL_LIST.get ( i ) ) ;
                 }
                 //write to new file
                 try
                 {
                    this.OUTPUT_STREAM = new FileOutputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\REVENUES.data" ) ;
                    for ( int i = 0 ;     i < TOTAL.size ( ) ;    i ++ )
                    {
                        this.OUTPUT_STREAM_2 = new ObjectOutputStream ( this.OUTPUT_STREAM ) ;
                        try
                        {  //From byte to Key ,in order to use Decrypted Symmetric key to encrypt with this the Revenues
                            SecretKey DECRYPTES_NORMAL_SYMMETRIC_KEY_FOR_USE = new SecretKeySpec ( DECRYPTED_SYMMETRIC_KEY, "AES" ) ;
                            //Convert bytes to A SecretKey
                            CIPHER = null ;
                            CIPHER = Cipher.getInstance ( "AES" ) ;
                            CIPHER.init ( Cipher.ENCRYPT_MODE, DECRYPTES_NORMAL_SYMMETRIC_KEY_FOR_USE ) ;      //ENCRYPT_MODE MODE static inT ,Constant used to initialize cipher to ENCRYPT_MODE mode.
                            //What is SealedObject?
                            //SealedObject encapsulate the original java object(it should implements Serializable). It use the cryptographic algorithm to seals the serialized content of the object.
                            SealedObject SEALEDOBJECT = new SealedObject ( TOTAL.get ( i ) , CIPHER ) ;
                            //Constructs a SealedObject from any Serializable object.
                             this.OUTPUT_STREAM_2.writeObject ( SEALEDOBJECT ) ;
                        }
                        catch ( NoSuchAlgorithmException NSAE )
                        {
                        
                        }
                        catch ( NoSuchPaddingException NSPE )
                        {
                        
                        }
                        catch ( IllegalBlockSizeException IBSE )
                        {
                        
                        }
                        catch ( InvalidKeyException IKE )
                        {
                            
                        }
                     }//END FOR
                 }//TRY END BLOCK
                 catch ( IOException IOE )
                 {
                     //..
                 }
                 //
                 JOptionPane.showMessageDialog ( null, "Successfull Modification", "Info", JOptionPane.INFORMATION_MESSAGE, null ) ;
                 this.dispose ( ) ;
                 Main_Menu obj = new Main_Menu ( ) ;
                 /////////
             }
             else if ( TFDESCRIPTION.getText ( ).equals ( "" )   &&   ! TFAMOUNT.getText ( ).equals ( "" ) )
             {
               this.FR.Set_AMOUNT ( Double.parseDouble (TFAMOUNT.getText ( ) ) ) ;
               //search into list to find old delete it and add new
                for ( int i = 0 ;     i < this.LIST.size ( ) ;    i ++ )
                {
                    Financial_Revenue X = ( Financial_Revenue ) this.LIST.get ( i ) ;
                    if ( ( X.Get_DESCRIPTION ( ).equals ( DES ) )  &&  ( X.Get_AMOUNT ( ) == AM )   &&  (  X.Get_DATE ( ).compareTo ( DAT )  )   ==  0  )
                    {
                        LIST.remove ( i ) ;
                        LIST.add ( this.FR ) ;
                    }
                }
                //Merge 2 lists, and overwrite Data.
                 ArrayList < Financial_Revenue > TOTAL = new ArrayList < Financial_Revenue > ( ) ;
                    
                 for ( int i = 0 ;    i < this.LIST.size ( ) ;     i ++ )
                 {
                     TOTAL.add ( ( Financial_Revenue ) this.LIST.get ( i ) ) ;
                 }
                 for ( int i = 0 ;    i < this.ALL_LIST.size ( ) ;     i ++ )
                 {
                     TOTAL.add ( ( Financial_Revenue ) this.ALL_LIST.get ( i ) ) ;
                 }
                 //write to new file
                 try
                 {
                    this.OUTPUT_STREAM = new FileOutputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\REVENUES.data" ) ;
                    for ( int i = 0 ;     i < TOTAL.size ( ) ;    i ++ )
                    {
                        this.OUTPUT_STREAM_2 = new ObjectOutputStream ( this.OUTPUT_STREAM ) ;
                        try
                        {  //From byte to Key ,in order to use Decrypted Symmetric key to encrypt with this the Revenues
                            SecretKey DECRYPTES_NORMAL_SYMMETRIC_KEY_FOR_USE = new SecretKeySpec ( DECRYPTED_SYMMETRIC_KEY, "AES" ) ;
                            //Convert bytes to A SecretKey
                            CIPHER = null ;
                            CIPHER = Cipher.getInstance ( "AES" ) ;
                            CIPHER.init ( Cipher.ENCRYPT_MODE, DECRYPTES_NORMAL_SYMMETRIC_KEY_FOR_USE ) ;      //ENCRYPT_MODE MODE static inT ,Constant used to initialize cipher to ENCRYPT_MODE mode.
                            //What is SealedObject?
                            //SealedObject encapsulate the original java object(it should implements Serializable). It use the cryptographic algorithm to seals the serialized content of the object.
                            SealedObject SEALEDOBJECT = new SealedObject ( TOTAL.get ( i ) , CIPHER ) ;
                            //Constructs a SealedObject from any Serializable object.
                             this.OUTPUT_STREAM_2.writeObject ( SEALEDOBJECT ) ;
                        }
                        catch ( NoSuchAlgorithmException NSAE )
                        {
                        
                        }
                        catch ( NoSuchPaddingException NSPE )
                        {
                        
                        }
                        catch ( IllegalBlockSizeException IBSE )
                        {
                        
                        }
                        catch ( InvalidKeyException IKE )
                        {
                            
                        }
                     }//END FOR
                 }//TRY END BLOCK
                 catch ( IOException IOE )
                 {
                     //..
                 }
                 //
                 JOptionPane.showMessageDialog ( null, "Successfull Modification", "Info", JOptionPane.INFORMATION_MESSAGE, null ) ;
                 this.dispose ( ) ;
                 Main_Menu obj = new Main_Menu ( ) ;
                 /////////
             }
             else if ( !TFDESCRIPTION.getText ( ).equals ( "" )   &&    !TFAMOUNT.getText ( ).equals ( "" ) )
             {
                this.FR.Set_AMOUNT ( Double.parseDouble (TFAMOUNT.getText ( ) ) ) ;
                this.FR.Set_DESCRIPTION ( TFDESCRIPTION.getText ( ) ) ;
               //search into list to find old delete it and add new
                for ( int i = 0 ;     i < this.LIST.size ( ) ;    i ++ )
                {
                    Financial_Revenue X = ( Financial_Revenue ) this.LIST.get ( i ) ;
                    if ( ( X.Get_DESCRIPTION ( ).equals ( DES ) )  &&  ( X.Get_AMOUNT ( ) == AM )   &&  (  X.Get_DATE ( ).compareTo ( DAT )  )   ==  0  )
                    {
                        LIST.remove ( i ) ;
                        LIST.add ( this.FR ) ;
                    }
                }
                //Merge 2 lists, and overwrite Data.
                 ArrayList < Financial_Revenue > TOTAL = new ArrayList < Financial_Revenue > ( ) ;
                    
                 for ( int i = 0 ;    i < this.LIST.size ( ) ;     i ++ )
                 {
                     TOTAL.add ( ( Financial_Revenue ) this.LIST.get ( i ) ) ;
                 }
                 for ( int i = 0 ;    i < this.ALL_LIST.size ( ) ;     i ++ )
                 {
                     TOTAL.add ( ( Financial_Revenue ) this.ALL_LIST.get ( i ) ) ;
                 }
                 //write to new file
                 try
                 {
                    this.OUTPUT_STREAM = new FileOutputStream ( "C:\\Users\\LU$er\\Desktop\\My Folder\\Locations\\NetBeansProjects\\Secure_Management\\" + Authentication_Window.FIND_PATHNAME ( ) +"\\REVENUES.data" ) ;
                    for ( int i = 0 ;     i < TOTAL.size ( ) ;    i ++ )
                    {
                        this.OUTPUT_STREAM_2 = new ObjectOutputStream ( this.OUTPUT_STREAM ) ;
                        try
                        {  //From byte to Key ,in order to use Decrypted Symmetric key to encrypt with this the Revenues
                            SecretKey DECRYPTES_NORMAL_SYMMETRIC_KEY_FOR_USE = new SecretKeySpec ( DECRYPTED_SYMMETRIC_KEY, "AES" ) ;
                            //Convert bytes to A SecretKey
                            CIPHER = null ;
                            CIPHER = Cipher.getInstance ( "AES" ) ;
                            CIPHER.init ( Cipher.ENCRYPT_MODE, DECRYPTES_NORMAL_SYMMETRIC_KEY_FOR_USE ) ;      //ENCRYPT_MODE MODE static inT ,Constant used to initialize cipher to ENCRYPT_MODE mode.
                            //What is SealedObject?
                            //SealedObject encapsulate the original java object(it should implements Serializable). It use the cryptographic algorithm to seals the serialized content of the object.
                            SealedObject SEALEDOBJECT = new SealedObject ( TOTAL.get ( i ) , CIPHER ) ;
                            //Constructs a SealedObject from any Serializable object.
                             this.OUTPUT_STREAM_2.writeObject ( SEALEDOBJECT ) ;
                        }
                        catch ( NoSuchAlgorithmException NSAE )
                        {
                        
                        }
                        catch ( NoSuchPaddingException NSPE )
                        {
                        
                        }
                        catch ( IllegalBlockSizeException IBSE )
                        {
                        
                        }
                        catch ( InvalidKeyException IKE )
                        {
                            
                        }
                     }//END FOR
                 }//TRY END BLOCK
                 catch ( IOException IOE )
                 {
                     //..
                 }
                 //
                 JOptionPane.showMessageDialog ( null, "Successfull Modification", "Info", JOptionPane.INFORMATION_MESSAGE, null ) ;
                 this.dispose ( ) ;
                 Main_Menu obj = new Main_Menu ( ) ;
                 /////////
             }
             else if ( TFDESCRIPTION.getText ( ).equals ( "" )   ||    TFAMOUNT.getText ( ).equals ( "" ) )
             {
                 JOptionPane.showMessageDialog ( null, "Please try to change something", "WARNING", JOptionPane.WARNING_MESSAGE, null ) ;
             }
         } //end of button MODIFY -...
        
    }//End of action
    
    
    
    
}//END CLASS
