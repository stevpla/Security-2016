    
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;









public class Modify_appearance_Window extends JFrame implements ActionListener
{
    
    
    private String GLOBAL_IDENT = null ;
    private JLabel LGEN,LDESCRIPTION, LAMOUNT, LDATE ;
    private int H = 0 ;  //Height
    private ArrayList < JButton > List_Button = new ArrayList < JButton > ( ) ;
    private ArrayList < Object > TEMP_LIST = new ArrayList < Object > ( ) ;
    private ArrayList < Object > Y = new ArrayList < Object > ( ) ;
    
    
    
    public Modify_appearance_Window ( String IDENT, ArrayList < Object > LiSt, ArrayList < Object > Y )
    {
        
        super ( "Select Revenue to modify" ) ;
        this.GLOBAL_IDENT = IDENT ;
        this.TEMP_LIST = LiSt ;
        this.Y = Y ;
        setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE ) ;      
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
        setSize ( 1300, 950 ) ;       // Size of Window
        setLocationRelativeTo ( null ) ;  // Display Window in center of Screen
        setVisible ( true ) ;
        
        
        
        
        
        //Manager
        Container pane = getContentPane ( ) ;  //Diaxeiristis
        pane.setLayout ( null ) ;   //Deactivate Manager Layout
           
        setContentPane ( pane ) ;
        //
        if ( this.GLOBAL_IDENT.equals ( "R" ) )
        {
           LGEN = new JLabel ( "REVENUES" ) ;
           LGEN.setForeground ( Color.BLACK ) ;
           LGEN.setFont ( new Font ( "Courier New", Font.BOLD, 30 ) ) ;
           LGEN.setBounds ( 320, 0, 500, 80 ) ;
           pane.add ( LGEN ) ;
        }
        else if ( this.GLOBAL_IDENT.equals ( "E" ) )
        {
           LGEN = new JLabel ( "EXPENSES" ) ;
           LGEN.setForeground ( Color.BLACK ) ;
           LGEN.setFont ( new Font ( "Courier New", Font.BOLD, 30 ) ) ;
           LGEN.setBounds ( 320, 0, 500, 80 ) ;
           pane.add ( LGEN ) ;
        }
        LDESCRIPTION = new JLabel ( "Description  " ) ;
        LDESCRIPTION.setForeground ( Color.BLUE ) ;
        LDESCRIPTION.setFont ( new Font ( "Courier New", Font.BOLD, 30 ) ) ;
        LDESCRIPTION.setBounds ( 30, 10, 500, 80 ) ;
        
        LAMOUNT = new JLabel ( "Amount  " ) ;
        LAMOUNT.setForeground ( Color.BLUE ) ;
        LAMOUNT.setFont ( new Font ( "Courier New", Font.BOLD, 30 ) ) ;
        LAMOUNT.setBounds ( 550, 10, 400, 80 ) ;
        
        LDATE = new JLabel ( "Date  " ) ;
        LDATE.setForeground ( Color.BLUE ) ;
        LDATE.setFont ( new Font ( "Courier New", Font.BOLD, 30 ) ) ;
        LDATE.setBounds ( 950, 10, 200, 80 ) ;
        
        //
        pane.add ( LDESCRIPTION ) ;
        pane.add ( LAMOUNT ) ;
        pane.add ( LDATE ) ;
        
        
        //First see if List is Empty, user has NO Revenues or Expenses, basicaly check if user requests Modify Revenue or Expense , and then see the List
        if ( GLOBAL_IDENT.equals ( "R" ) )
        {
            if ( LiSt.isEmpty ( ) )
            {
              JLabel EMPTY = new JLabel ( "No Revenues " ) ;
              EMPTY.setForeground ( Color.BLUE ) ;
              EMPTY.setFont ( new Font ( "Courier New", Font.BOLD, 50 ) ) ;
              EMPTY.setBounds ( 250, 350, 385, 80 ) ;
              pane.add ( EMPTY ) ;
            }
            else
            {   //show all Revenues in the GUI
                for ( int i = 0 ;    i < LiSt.size ( ) ;     i ++ )
                {
                     //ArrayList contains objects as Object type, so we have to check with instance of the elements
                      if ( LiSt.get ( i ) instanceof Financial_Revenue  )
                      {    //cast it to take the real object
                          Financial_Revenue X = ( Financial_Revenue ) LiSt.get ( i ) ;
                            //Calculate total cost Revenue - Expenses
                          JLabel LD = new JLabel ( X.Get_DESCRIPTION ( ) ) ;
                          LD.setForeground ( Color.BLACK ) ;
                          LD.setFont ( new Font ( "Courier New", Font.BOLD, 25 ) ) ;
                          LD.setBounds ( 30, H + 80, 600, 40 ) ;
                          pane.add ( LD ) ;

                          JLabel LA = new JLabel ( " " + X.Get_AMOUNT ( ) ) ;  //Concatenation double _>< string
                          LA.setForeground ( Color.black ) ;
                          LA.setFont ( new Font( "Courier New", Font.BOLD, 25 ) ) ;
                          LA.setBounds (550, H + 80, 600, 40 ) ;
                          pane.add ( LA ) ;

                          //Date to String
                          String dateString = null ;
                          SimpleDateFormat sdfr = new SimpleDateFormat ( "dd/MMM/yyyy" ) ;
    
                          try 
                          {   
                              dateString = sdfr.format ( X.Get_DATE ( ) ) ;
                          } 
                          catch ( Exception ex ) 
                          {
                          }
                          //
                          JButton Button_T = new JButton(dateString) ;
                          Button_T.setForeground ( Color.BLACK ) ;
                          Button_T.setBounds ( 830, H + 80, 300, 40 ) ;  //
                          pane.add ( Button_T ) ;
                          List_Button.add ( Button_T ) ;  //prosthese kathe kympi sti Lista
                          List_Button.get ( i ).addActionListener ( this ) ; 

                          H = H + 60;  //increase height of components
                      } 
                }//end of for
            } // end of Empty List
        }
        else if ( GLOBAL_IDENT.equals ( "E" ) )
        {
            if ( LiSt.isEmpty ( ) )
            {
              JLabel EMPTY = new JLabel ( "No Expenses " ) ;
              EMPTY.setForeground ( Color.BLUE ) ;
              EMPTY.setFont ( new Font ( "Courier New", Font.BOLD, 35 ) ) ;
              EMPTY.setBounds ( 250, 350, 400, 80 ) ;
              pane.add ( EMPTY ) ;
            }
            else
            {
               //show all Revenues in the GUI
               for ( int i = 0 ;    i < LiSt.size ( ) ;     i ++ )
               {
                  if ( LiSt.get ( i ) instanceof Financial_Expenses ) 
                  {
                          Financial_Expenses X = (Financial_Expenses) LiSt.get(i);
                          //Calculate total cost Revenue - Expenses
                          JLabel LD = new JLabel(X.Get_DESCRIPTION());
                          LD.setForeground(Color.BLACK);
                          LD.setFont(new Font("Courier New", Font.BOLD, 25));
                          LD.setBounds(30, H + 80, 600, 40);
                          pane.add(LD);

                          JLabel LA = new JLabel ( " " + X.Get_AMOUNT ( ) ) ;  //Concatenation double _>< string
                          LA.setForeground ( Color.black ) ;
                          LA.setFont ( new Font ( "Courier New", Font.BOLD, 25 ) ) ;
                          LA.setBounds ( 550, H + 80, 600, 40 ) ;
                          pane.add ( LA ) ;

                          //Date to String
                          String dateString = null ;
                          SimpleDateFormat sdfr = new SimpleDateFormat ( "dd/MMM/yyyy" ) ;

                          try 
                          {
                              dateString = sdfr.format ( X.Get_DATE ( ) ) ;
                          } 
                          catch ( Exception ex )
                          {
                          }
                          //
                          JButton Button_T = new JButton(dateString) ;
                          Button_T.setForeground ( Color.BLACK ) ;
                          Button_T.setBounds ( 830, H + 80, 300, 40 ) ;  //
                          pane.add ( Button_T ) ;
                          List_Button.add ( Button_T ) ;  //prosthese kathe kympi sti Lista
                          List_Button.get ( i ).addActionListener ( this ) ;

                          H = H + 60 ;  //increase height of components
                   }
                }//End of For
             }//End of else
         }//END OF "E"
    } //End of Constructor
    
    
    
    
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
        
        if ( GLOBAL_IDENT.equals ( "R" ) )
        {
             for ( int j = 0 ;      j < List_Button.size ( ) ;    j ++ )
             {
                 if ( List_Button.get ( j )  == source )
                 {
                     Financial_Revenue R = ( Financial_Revenue ) TEMP_LIST.get ( j ) ;
                     this.dispose ( ) ;
                     Modify_Financial_Revenues_Window OBJ = new Modify_Financial_Revenues_Window ( R, TEMP_LIST, this.Y ) ;
                 }
             }
        }
        else if ( GLOBAL_IDENT.equals ( "E" ) )
        {
            for ( int j = 0 ;      j < List_Button.size ( ) ;    j ++ )
             {
                 if ( List_Button.get ( j )  == source )
                 {
                     Financial_Expenses E = ( Financial_Expenses ) TEMP_LIST.get ( j ) ;
                     this.dispose ( ) ;
                     Modify_Financial_Expenses_Window OBJ = new Modify_Financial_Expenses_Window ( E, TEMP_LIST, this.Y ) ;
                 }
             }
            
        }
    }//End of action
     
     
}//End of class
