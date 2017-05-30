
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
import javax.swing.JOptionPane;








public class APPEARANCE_WINDOW extends JFrame implements ActionListener
{
    
    
    
    private JLabel LDATE, LDESCRIPTION, LAMOUNT, LCOSTR, LCOSTE, LLEFT_C, LRIGHT_C  ;
    private JButton BBACK ;
    private int H = 0 ;  //Height
    private double COST_R, COST_E ;
    
    public APPEARANCE_WINDOW ( ArrayList < Object > T_LIST )
    {
        
        super ( "GUI - FINANCIAL REVENUES - EXPENSES" ) ;
        setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE ) ;      //If you press X the red one icon then terminate this Window
        //
        //https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
        //In this technique a WindowListener interface implentation is added to the frame, where the listener has a method, windowClosing(), that is called when the frame is closed.
        //In practice, on overrides the windowClosing() method of WindowAdapter, a no-op implementation of WindowListener.   This way, one doesn't have to worry about all the other methods of the WindowListener.
        this.addWindowListener ( new java.awt.event.WindowAdapter ( ) 
        {
              @Override             //Otan patiseis to kokkino x tote aytomata ginetai LGOOUT
             public void windowClosing(java.awt.event.WindowEvent windowEvent) 
             {
                 Main_Menu Obj = new Main_Menu ( ) ;
             }
        } ) ; 
        //
        setSize ( 1300, 950 ) ;       // Size of Window
        setLocationRelativeTo ( null ) ;  // Display Window in center of Screen
        setVisible ( true ) ;
        
        //Manager
        Container pane = getContentPane ( ) ;  //Diaxeiristis
        pane.setLayout ( null ) ;   //Deactivate Manager Layout
    
      
    
        
        setContentPane ( pane ) ;
        //
        LDESCRIPTION = new JLabel ( "Description  " ) ;
        LDESCRIPTION.setForeground ( Color.BLUE ) ;
        LDESCRIPTION.setFont ( new Font ( "Courier New", Font.BOLD, 35 ) ) ;
        LDESCRIPTION.setBounds ( 30, 10, 500, 80 ) ;
        
        LCOSTR = new JLabel ( "Total_Cost_Revenues ->  " ) ;
        LCOSTR.setForeground ( Color.BLACK ) ;
        LCOSTR.setFont ( new Font ( "Courier New", Font.BOLD, 28 ) ) ;
        LCOSTR.setBounds ( 25, 800, 500, 80 ) ;
        
        LCOSTE = new JLabel ( "Total_Cost_Expenses ->  " ) ;
        LCOSTE.setForeground ( Color.BLACK ) ;
        LCOSTE.setFont ( new Font ( "Courier New", Font.BOLD, 28 ) ) ;
        LCOSTE.setBounds ( 750, 800, 500, 80 ) ;
        
        LAMOUNT = new JLabel ( "Amount  " ) ;
        LAMOUNT.setForeground ( Color.BLUE ) ;
        LAMOUNT.setFont ( new Font ( "Courier New", Font.BOLD, 35 ) ) ;
        LAMOUNT.setBounds ( 550, 10, 400, 80 ) ;
        
        LDATE = new JLabel ( "Date  " ) ;
        LDATE.setForeground ( Color.BLUE ) ;
        LDATE.setFont ( new Font ( "Courier New", Font.BOLD, 35 ) ) ;
        LDATE.setBounds ( 870, 10, 200, 80 ) ;
        //
        LLEFT_C = new JLabel ( ) ;
        LLEFT_C.setForeground ( Color.GREEN ) ;
        LLEFT_C.setFont ( new Font ( "Courier New", Font.BOLD, 27 ) ) ;
        LLEFT_C.setBounds ( 410, 800, 200, 80 ) ;
        //
        LRIGHT_C = new JLabel ( ) ;
        LRIGHT_C.setForeground ( Color.GREEN ) ;
        LRIGHT_C.setFont ( new Font ( "Courier New", Font.BOLD, 27 ) ) ;
        LRIGHT_C.setBounds ( 1130, 800, 200, 80 ) ;
        
        //button
        BBACK = new JButton ( "Back" ) ;
        BBACK.setForeground ( Color.RED ) ;
        BBACK.setBounds ( 500, 820, 170, 60 ) ;
        
        //
        pane.add ( LDESCRIPTION ) ;
        pane.add ( LAMOUNT ) ;
        pane.add ( LDATE ) ;
        pane.add ( BBACK ) ;
        pane.add ( LCOSTR ) ;
        pane.add ( LCOSTE ) ;
        pane.add ( LLEFT_C ) ;
        pane.add ( LRIGHT_C ) ;
        BBACK.addActionListener ( this ) ;
        
        //Now see the List
        if ( T_LIST.isEmpty ( ) )
        {
            JLabel error = new JLabel ( "There is no Revenues or Expenses availabe for this month" ) ;
            error.setForeground ( Color.RED ) ;
            error.setFont ( new Font ( "Courier New", Font.BOLD, 35 ) ) ;
            error.setBounds ( 250, 300, 800, 40 ) ;
            pane.add ( error ) ;
        }
        else
        {
            for ( int i = 0 ;    i < T_LIST.size ( ) ;   i ++ )
            {  //ArrayList contains objects as Object type, so we have to check with instance of the elements
              if ( T_LIST.get ( i ) instanceof Financial_Revenue  )
              {  //cast it to take the real object
                 Financial_Revenue X = ( Financial_Revenue ) T_LIST.get ( i ) ;
                //Calculate total cost Revenue - Expenses
                COST_R = COST_R + X.Get_AMOUNT ( ) ;
                JLabel LD = new JLabel ( X.Get_DESCRIPTION ( ) ) ;
                LD.setForeground ( Color.RED ) ;
                LD.setFont ( new Font ( "Courier New", Font.BOLD, 25 ) ) ;
                LD.setBounds ( 30, H + 80, 600, 40 ) ;
                pane.add ( LD ) ;
                
                JLabel LA = new JLabel ( " " + X.Get_AMOUNT ( ) ) ;  //Concatenation double _>< string
                LA.setForeground ( Color.RED ) ;
                LA.setFont ( new Font ( "Courier New", Font.BOLD, 25 ) ) ;
                LA.setBounds ( 550, H + 80, 600, 40 ) ;
                pane.add ( LA ) ;
                
                //Date to String
                String dateString = null;
                SimpleDateFormat sdfr = new SimpleDateFormat ( "dd/MMM/yyyy" ) ;
    
                try
                {
	           dateString = sdfr.format ( X.Get_DATE ( ) ) ;
                }
                catch ( Exception ex )
                {
                }
                //
                JLabel LDA = new JLabel ( dateString ) ;
                LDA.setForeground ( Color.RED ) ;
                LDA.setFont ( new Font ( "Courier New", Font.BOLD, 25 ) ) ;
                LDA.setBounds ( 830, H + 80, 600, 40 ) ;
                pane.add ( LDA ) ;
                
                H = H + 60 ;  //increase height of components
              }
              else if ( T_LIST.get ( i ) instanceof Financial_Expenses )
              {
                 Financial_Expenses X = ( Financial_Expenses ) T_LIST.get ( i ) ;
                //Calculate total cost Revenue - Expenses
                COST_E = COST_E + X.Get_AMOUNT ( ) ;
                JLabel LD = new JLabel ( X.Get_DESCRIPTION ( ) ) ;
                LD.setForeground ( Color.BLACK ) ;
                LD.setFont ( new Font ( "Courier New", Font.BOLD, 25 ) ) ;
                LD.setBounds ( 30, H + 80, 600, 40 ) ;
                pane.add ( LD ) ;
                
                JLabel LA = new JLabel ( " " + X.Get_AMOUNT ( ) ) ;  //Concatenation double _>< string
                LA.setForeground ( Color.black ) ;
                LA.setFont ( new Font ( "Courier New", Font.BOLD, 25 ) ) ;
                LA.setBounds ( 550, H + 80, 600, 40 ) ;
                pane.add ( LA ) ;
                
                //Date to String
                String dateString = null;
                SimpleDateFormat sdfr = new SimpleDateFormat ( "dd/MMM/yyyy" ) ;
    
                try
                {
	           dateString = sdfr.format ( X.Get_DATE ( ) ) ;
                }
                catch ( Exception ex )
                {
                }
                //
                JLabel LDA = new JLabel ( dateString ) ;
                LDA.setForeground ( Color.BLACK ) ;
                LDA.setFont ( new Font ( "Courier New", Font.BOLD, 25 ) ) ;
                LDA.setBounds ( 830, H + 80, 600, 40 ) ;
                pane.add ( LDA ) ;
                
                H = H + 60 ;  //increase height of components
              }
            }
            //Now add cost to gui
            LLEFT_C.setText ( "" + COST_R ) ;
            LRIGHT_C.setText ( "" + COST_E ) ;
        }
    }//End of Constructor
    
    
    
    //action method
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
        if ( BBACK == source )
        {
            this.dispose ( ) ;
            Main_Menu o = new Main_Menu ( ) ;
        }
    }//End of action
    
    
}//End of Class
