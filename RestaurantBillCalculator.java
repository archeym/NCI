//6thJulyDipJavaProject
//Author by Arkadijs Makarenko
//arkadijs.makarenko@gmail.com
//RestaurantBillCalculator.java

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

public class RestaurantBillCalculator extends JFrame {

    private JLabel restaurantJLabel;// JLabel for Restaurant
    private JPanel waiterJPanel;// JPanel to display waiter information

    private JLabel tableNumberJLabel;// JLabel and JTextField for table number
    private JTextField tableNumberJTextField;

    private JComboBox tableNumberJComboBox;

    private JLabel waiterNameJLabel;// JLabel and JTextField for waiter name
    private JTextField waiterNameJTextField;

    private JPanel menuItemsJPanel;// JPanel to display menu items

    private JLabel beverageJLabel;// JLabel and JComboBox for beverage
    private JComboBox beverageJComboBox;

    private JLabel appetizerJLabel;// JLabel and JComboBox for appetizer
    private JComboBox appetizerJComboBox;

    private JLabel mainCourseJLabel;// JLabel and JComboBox for main course
    private JComboBox mainCourseJComboBox;

    private JLabel dessertJLabel;// JLabel and JComboBox for dessert
    private JComboBox dessertJComboBox;

    private JButton calculateBillJButton;// JButton for calculate bill
    private JButton saveTableJButton; //JBotton for save table
    private JButton payBillJButton; //JButton for bill

    private JLabel subtotalJLabel;// JLabel and JTextField for subtotal
    private JTextField subtotalJTextField;

    private JLabel taxJLabel;// JLabel and JTextField for tax
    private JTextField taxJTextField;

    private JLabel totalJLabel;// JLabel and JTextField for total
    private JTextField totalJTextField;

    private final static double TAX_RATE = 0.10;// constant for tax rate

    private Connection myConnection;// declare instance variables for database processing
    private Statement myStatement;
    private ResultSet myResultSet;

    private ArrayList billItems = new ArrayList();// declare instance variable ArrayList to hold bill items
    private double subtotal;//other instance variables

    public RestaurantBillCalculator(String databaseUserName, String databasePassword) {// constructor
        try {
            String url = "jdbc:mysql://localhost:3306/restaurant";
            String driver = "com.mysql.jdbc.Driver";

            myConnection = DriverManager.getConnection(url, databaseUserName, databasePassword);

            myStatement = myConnection.createStatement();

            myResultSet = myStatement.executeQuery("SELECT * FROM menu ");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        createUserInterface();//set up GUI  

    } // end constructor

    private void createUserInterface() {// create and position GUI components; register event handlers

        Container contentPane = getContentPane();// get content pane for attaching GUI components

        contentPane.setLayout(null);// enable explicit positioning of GUI components 

        restaurantJLabel = new JLabel(); // set up restaurantJLabel
        restaurantJLabel.setBounds(80, 10, 128, 24);
        restaurantJLabel.setText("Restourant");
        restaurantJLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        contentPane.add(restaurantJLabel);

        createWaiterJPanel();//set up waiterJPanel
        contentPane.add(waiterJPanel);

        createMenuItemsJPanel();// set up menuItemsJPanel
        contentPane.add(menuItemsJPanel);

        calculateBillJButton = new JButton(); //set up calculateBillJButton
        calculateBillJButton.setBounds(180, 365, 90, 25);
        calculateBillJButton.setText("Calculate Bill");
        calculateBillJButton.setBorder(BorderFactory.createRaisedBevelBorder());
        calculateBillJButton.setEnabled(false);
        calculateBillJButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        calculateBillJButtonActionPerformed(event);

                    }
                } // end anonymous inner class

        ); // end addActionListener

        contentPane.add(calculateBillJButton);

        subtotalJLabel = new JLabel();// set up subtotalJLabel
        subtotalJLabel.setBounds(15, 340, 56, 16);
        subtotalJLabel.setText("Subtotal:");
        contentPane.add(subtotalJLabel);

        subtotalJTextField = new JTextField();//set up subtotalJTextField
        subtotalJTextField.setBounds(75, 340, 80, 20);
        subtotalJTextField.setEditable(false);
        subtotalJTextField.setBorder(BorderFactory.createLoweredBevelBorder());
        subtotalJTextField.setHorizontalAlignment(JTextField.RIGHT);
        contentPane.add(subtotalJTextField);

        saveTableJButton = new JButton();
        saveTableJButton.setBounds(180, 330, 90, 25);
        saveTableJButton.setText("Save Table");
        saveTableJButton.setBorder(BorderFactory.createRaisedBevelBorder());
        saveTableJButton.setEnabled(false);
        saveTableJButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        saveTableJButtonActionPerformed(event);//

                    }
                }//end Annonymos inner class

        );//end ActionListener

        contentPane.add(saveTableJButton);

        payBillJButton = new JButton();
        payBillJButton.setBounds(180, 400, 90, 25);
        payBillJButton.setText("Pay Bill");
        payBillJButton.setBorder(BorderFactory.createRaisedBevelBorder());
        payBillJButton.setEnabled(false);
        payBillJButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        payBillJButtonActionPerformed(event);

                    }
                }//end Annonymos inner class

        );//end ActionListener 

        contentPane.add(payBillJButton);

        taxJLabel = new JLabel();// set up taxJLabel
        taxJLabel.setBounds(15, 370, 60, 16);
        taxJLabel.setText("Tax:");
        contentPane.add(taxJLabel);

        taxJTextField = new JTextField(); //set up taxJTextField
        taxJTextField.setBounds(75, 370, 80, 20);
        taxJTextField.setEditable(false);
        taxJTextField.setBorder(BorderFactory.createLoweredBevelBorder());
        taxJTextField.setHorizontalAlignment(JTextField.RIGHT);
        contentPane.add(taxJTextField);

        totalJLabel = new JLabel();//set up totalJLabel
        totalJLabel.setBounds(15, 400, 60, 16);
        totalJLabel.setText("Total:");
        contentPane.add(totalJLabel);

        totalJTextField = new JTextField(); //  set up totalJTextField
        totalJTextField.setBounds(75, 400, 80, 20);
        totalJTextField.setEditable(false);
        totalJTextField.setBorder(BorderFactory.createLoweredBevelBorder());
        totalJTextField.setHorizontalAlignment(JTextField.RIGHT);
        contentPane.add(totalJTextField);

        setTitle("Restaurant Bill Calculator");// set properties of application's window
        setSize(300, 500);// set windows size
        setVisible(true);//display windows

        addWindowListener( //  when user quits application
                new WindowAdapter(){//anonymous inner class  
 
                    public void windowClosing(WindowEvent event) {//  event handler called when close button is clicked
                        frameWindowClosing(event);
                    }

                } //  end anonymous inner class

        ); // end addWindowListener

    } // end method createUserInterface

    private void createWaiterJPanel() { //  set up waiterJPanel

        waiterJPanel = new JPanel();  // set up waiterJPanel
        waiterJPanel.setBounds(20, 48, 240, 90);
        waiterJPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Waiter Information"));
        waiterJPanel.setLayout(null);

        tableNumberJLabel = new JLabel(); //  set up tableNumberJLabel
        tableNumberJLabel.setBounds(45, 25, 95, 25);
        tableNumberJLabel.setText("Table Number:");
        waiterJPanel.add(tableNumberJLabel);

        tableNumberJComboBox = new JComboBox();
        tableNumberJComboBox.setBounds(170, 25, 65, 25);
        waiterJPanel.add(tableNumberJComboBox);
        tableNumberJComboBox.addItem("");
        loadTableNumbers();
        tableNumberJComboBox.addItemListener(
                new ItemListener() {//annonymous inner class
                    public void itemStateChanged(ItemEvent event) {//event handler called when item in tableNumberJComboBox selected
                        tableNumberJComboBoxItemStateChanged(event);

                    }
                }//end annonymous inner class
        );//end ItemListener

        waiterNameJLabel = new JLabel(); //  set up waiterNameJLabel
        waiterNameJLabel.setBounds(45, 60, 85, 25);
        waiterNameJLabel.setText("Waiter Name:");
        waiterJPanel.add(waiterNameJLabel);

        waiterNameJTextField = new JTextField();//set up waiterNameJTextField
        waiterNameJTextField.setBounds(140, 60, 90, 25);
        waiterNameJTextField.setEditable(false);
        waiterJPanel.add(waiterNameJTextField);

    } // end method createWaiterJPanel

    private void createMenuItemsJPanel() { //create menuItemsJPanel

        menuItemsJPanel = new JPanel();//  set up menuItemsJPanel
        menuItemsJPanel.setBounds(20, 155, 240, 150);
        menuItemsJPanel.setEnabled(false);
        menuItemsJPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Menu Items"));
        menuItemsJPanel.setLayout(null);

        beverageJLabel = new JLabel();//  set up beverageJLabel
        beverageJLabel.setBounds(10, MENU_ITEMS_TOP, 80, 24);
        beverageJLabel.setText("Beverage:");
        menuItemsJPanel.add(beverageJLabel);

        beverageJComboBox = new JComboBox();// set up beverageJComboBox
        beverageJComboBox.setBounds(COMBO_LEFT_X, MENU_ITEMS_TOP, COMBO_WIDTH_X, COMBO_HEIGHT);
        beverageJComboBox.setEnabled(false);
        menuItemsJPanel.add(beverageJComboBox);
        beverageJComboBox.addItemListener(
                new ItemListener(){ // anonymous inner class
                   
                    public void itemStateChanged(ItemEvent event) {//  event handler called when item in beverageJComboBox
                        beverageJComboBoxItemStateChanged(event);
                    }

                } // end anonymous inner class

        ); // end addItemListener

        beverageJComboBox.addItem("");//  add items to beverageJComboBox
        loadCategory("Beverage", beverageJComboBox);

        appetizerJLabel = new JLabel();// set up appetizerJLabel
        appetizerJLabel.setBounds(10, MENU_ITEMS_TOP+MENU_ITEMS_SPACE, 125, 25);
        appetizerJLabel.setText("Appetizer:");
        menuItemsJPanel.add(appetizerJLabel);

        appetizerJComboBox = new JComboBox();// set up appetizerJComboBox
        appetizerJComboBox.setBounds(COMBO_LEFT_X, MENU_ITEMS_TOP+MENU_ITEMS_SPACE, COMBO_WIDTH_X, COMBO_HEIGHT);
        appetizerJComboBox.setEnabled(false);
        menuItemsJPanel.add(appetizerJComboBox);
        appetizerJComboBox.addItemListener(
                new ItemListener(){ // anonymous inner class

                    public void itemStateChanged(ItemEvent event) {//  event handler called when item in appetizerJComboBox
                        appetizerJComboBoxItemStateChanged(event);
                    }

                } // end anonymous inner class

        ); // end addItemListener

        appetizerJComboBox.addItem("");//  add items to appetizerJComboBox
        loadCategory("Appetizer", appetizerJComboBox);

        mainCourseJLabel = new JLabel();// set up mainCourseJLabel
        mainCourseJLabel.setBounds(10, MENU_ITEMS_TOP+2*MENU_ITEMS_SPACE, 125, 25);
        mainCourseJLabel.setText("Main Course:");
        menuItemsJPanel.add(mainCourseJLabel);

        mainCourseJComboBox = new JComboBox();// set up mainCourseJComboBox 
        mainCourseJComboBox.setBounds(COMBO_LEFT_X, MENU_ITEMS_TOP+2*MENU_ITEMS_SPACE, COMBO_WIDTH_X, COMBO_HEIGHT);
        mainCourseJComboBox.setEnabled(false);
        menuItemsJPanel.add(mainCourseJComboBox);
        mainCourseJComboBox.addItemListener(
                new ItemListener(){ // anonymous inner class

                    public void itemStateChanged(ItemEvent event) {//  event handler called when item in mainCourseJComboBox
                        mainCourseJComboBoxItemStateChanged(event);
                    }

                } // end anonymous inner class

        ); // end addItemListener

        mainCourseJComboBox.addItem("");// add items to mainCourseJComboBox
        loadCategory("Main Course", mainCourseJComboBox);

        dessertJLabel = new JLabel(); //  set up dessertJLabel
        dessertJLabel.setBounds(10, MENU_ITEMS_TOP+3*MENU_ITEMS_SPACE, 125, 25);
        dessertJLabel.setText("Dessert:");
        menuItemsJPanel.add(dessertJLabel);

        dessertJComboBox = new JComboBox();// set up dessertJComboBox 
        dessertJComboBox.setBounds(COMBO_LEFT_X, MENU_ITEMS_TOP+3*MENU_ITEMS_SPACE, COMBO_WIDTH_X, COMBO_HEIGHT);
        dessertJComboBox.setEnabled(false);
        menuItemsJPanel.add(dessertJComboBox);
        dessertJComboBox.addItemListener(
                new ItemListener() {// anonymous inner class

                    public void itemStateChanged(ItemEvent event) {//  event handler called when item in dessertJComboBox
                        dessertJComboBoxItemStateChanged(event);
                    }

                } // end anonymous inner class

        ); // end addItemListener

        dessertJComboBox.addItem("");// add items to dessertJComboBox
        loadCategory("Dessert", dessertJComboBox);

    } // end method createMenuItemsJPanel
    
    private static final int MENU_ITEMS_SPACE = 30;//refactor-itroduce-constant
    private static final int MENU_ITEMS_TOP = 25;
    private static final int COMBO_HEIGHT = 25;
    private static final int COMBO_WIDTH_X = 135;
    private static final int COMBO_LEFT_X = 100;

    private void loadTableNumbers() {

        try {//read all table numbers from database

            myResultSet = myStatement.executeQuery("SELECT tableNumber FROM restaurantTables");//obtain all the numbers

            while (myResultSet.next() == true) {//add numbers to tableNumberJComboBox

                tableNumberJComboBox.addItem(String.valueOf(myResultSet.getInt("tableNumber")));
            }
            myResultSet.close();//close myResultSet
        }//end try
        catch (SQLException exception) {//catch SQLException
            exception.printStackTrace();//print stack trace
        }

    }//end method loadTableNumber

    private void loadCategory(//  add items to JComboBox
            String category, JComboBox categoryJComboBox) {

        try {//read all items from database for specifield category

            myResultSet = myStatement.executeQuery("SELECT name FROM " + "menu WHERE category ='" + category + "'");//obtain all items in specifield category

            while (myResultSet.next() == true) {//add items to JComboBox
                categoryJComboBox.addItem(myResultSet.getString("name"));
            }
            myResultSet.close();//close myResult
        }//end try
        //catch SQLExeption
        catch (SQLException exception) {
            exception.printStackTrace();
        }

    } // end method loadCategory

    private void tableNumberJComboBoxItemStateChanged(ItemEvent event) {
        String selectedTableNumber = (String) event.getItem();

        if (!selectedTableNumber.equals("") && event.getStateChange() == ItemEvent.SELECTED) {//select a number
            try {
                myResultSet = myStatement.executeQuery("SELECT * FROM "
                        + "restaurantTables WHERE tableNumber="
                        + Integer.parseInt(selectedTableNumber));
                if (myResultSet.next() == true) {
                    waiterNameJTextField.setText(myResultSet.getString("WaiterName"));
                    subtotal = myResultSet.getDouble("subtotal");
                    displayTotal(subtotal);
                }
                myResultSet.close();//close myResultSet
            }//end try
            catch (SQLException exception) {
                exception.printStackTrace();
            }

            menuItemsJPanel.setEnabled(true);//enable JComboBoxs in menuItemsJPanel
            beverageJComboBox.setEnabled(true);
            appetizerJComboBox.setEnabled(true);
            mainCourseJComboBox.setEnabled(true);
            dessertJComboBox.setEnabled(true);
            tableNumberJComboBox.setEnabled(true);
            
            waiterJPanel.setEnabled(false);//disable JComboBox in waiter JPanel
            
            saveTableJButton.setEnabled(true);//enable buttons
            calculateBillJButton.setEnabled(true);
            payBillJButton.setEnabled(true);

        }//end if
    }//end method tableNumberJComboBox

    private void beverageJComboBoxItemStateChanged(ItemEvent event) {//  user select beverage

        if (event.getStateChange() == ItemEvent.SELECTED) {//select an Item
            billItems.add((String) beverageJComboBox.getSelectedItem());
        }

    } // end method beverageJComboBoxItemStateChanged

    private void appetizerJComboBoxItemStateChanged(ItemEvent event) {//  user select appetizer

        if (event.getStateChange() == ItemEvent.SELECTED) {//select an Item
            billItems.add((String) appetizerJComboBox.getSelectedItem());
        }

    } // end method appetizerJComboBoxItemStateChanged

    private void mainCourseJComboBoxItemStateChanged(ItemEvent event) { //  user select main course

        if (event.getStateChange() == ItemEvent.SELECTED) {//select an Item
            billItems.add((String) mainCourseJComboBox.getSelectedItem());
        }

    } // end method mainCourseJComboBoxItemStateChanged

    private void dessertJComboBoxItemStateChanged(ItemEvent event) {//  user select dessert

        if (event.getStateChange() == ItemEvent.SELECTED) {//select an Item
            billItems.add((String) dessertJComboBox.getSelectedItem());
        }

    } // end method dessertJComboBoxItemStateChanged

    private void calculateBillJButtonActionPerformed(ActionEvent event) {//  user click CalculateBillJButton


        String tableNumber = tableNumberJComboBox.toString();
        String waiterName = waiterNameJTextField.getText();

        if (tableNumber.length() == 0 || waiterName.length() == 0) {
            JOptionPane.showMessageDialog(null, "Waiter name and/or table number not entered");
        } else {
            subtotal = calculateSubtotal();
            displayTotal(subtotal);//dispaly subtotal,tax and total
        }

    } // end method calculateBillJButtonActionPerformed

    private void saveTableJButtonActionPerformed(ActionEvent event) {

        subtotal = calculateSubtotal();//calculate subtotal

        updateTable();//update subtotal in database

        resetJFrame();//reset JFrame
    }

    private void payBillJButtonActionPerformed(ActionEvent event)//payBillButton action performed method sets subtotal 0,resets, updates
    {
        subtotal = 0;//subtotal = calculateSubtotal();

        updateTable();//update subtotal in database

        resetJFrame();//reset JFrame
    }

    private void updateTable()// updateTable () method updates the subtotal coloumn in DataBase
    {

        try {//update subtotal for table number in database

            myStatement.executeUpdate("UPDATE restaurantTables SET "
                    + "subtotal=" + subtotal + " WHERE tableNumber ="
                    + Integer.parseInt((String) tableNumberJComboBox.getSelectedItem())
            );
        }//end try
        catch (SQLException exception)//catch SQLException
        {
            exception.printStackTrace();
        }

        try {
            PreparedStatement updateEntry
                    = myConnection.prepareStatement("UPDATE restaurantTables  SET " + "waitername = ?" + " WHERE tableNumber = "
                            + Integer.parseInt((String) tableNumberJComboBox.getSelectedItem())
                    );

            updateEntry.setString(1, waiterNameJTextField.getText());// specify the PreparedStatement's arguments
            updateEntry.executeUpdate();

        }//end try
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }// end method updateTable

    private void resetJFrame() {

        billItems = new ArrayList();// reset instance variable
        billItems.clear();

        menuItemsJPanel.setEnabled(false);//reset and disable menuItemsJPanel
        beverageJComboBox.setSelectedIndex(0);
        beverageJComboBox.setEnabled(false);
        appetizerJComboBox.setSelectedIndex(0);
        appetizerJComboBox.setEnabled(false);
        dessertJComboBox.setSelectedIndex(0);
        dessertJComboBox.setEnabled(false);
        mainCourseJComboBox.setSelectedIndex(0);
        mainCourseJComboBox.setEnabled(false);

        waiterJPanel.setEnabled(true);//reset and enable waiterJPanel
        tableNumberJComboBox.setEnabled(true);
        tableNumberJComboBox.setSelectedIndex(0);

        waiterNameJTextField.setText("");//clear text fields
        subtotalJTextField.setText("");
        taxJTextField.setText("");
        totalJTextField.setText("");

        payBillJButton.setEnabled(false);//disable JButtons
        saveTableJButton.setEnabled(false);
        calculateBillJButton.setEnabled(false);

    }//end resetJFrame

    private void displayTotal(double subTotal) {

        DecimalFormat dollars = new DecimalFormat("$0.00");//define display format

        subtotalJTextField.setText(dollars.format(subTotal));//display subtotal

        double tax = subTotal * TAX_RATE;//calculate and display tax
        taxJTextField.setText(dollars.format(tax));

        totalJTextField.setText(dollars.format(subTotal + tax));//display total

    }//end method displayTotal

    private double calculateSubtotal() {//  calculate subtotal

        double subTotal = 0;
        Object[] items = billItems.toArray();

        try {//get data from database

            for (int i = 0; i < items.length; i++) {//get price for each item in items array

                myResultSet = myStatement.executeQuery("SELECT price " + "FROM menu WHERE name='" + (String) items[i] + "'");//execute query to get price

                if (myResultSet.next() == true) {//myResultSet not empty
                    subTotal += myResultSet.getDouble("price");
                }
                myResultSet.close();//close myResultSet
            }//end for
        }//end try//end try
        catch (SQLException exception) {//catch SQLException
            exception.printStackTrace();
        }
        return subTotal;

    } // end method calculateSubtotal

    private void frameWindowClosing(WindowEvent event) {// user close window

        try {
            myStatement.close();
            myConnection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            System.exit(0);
        }

    }  // end method frameWindowClosing

    public static void main(String[] args) {// main method

      //  if (args.length == 2) {// check command-line arguments

            String databaseUserName = "root"; //args[0];  // get 2 command line arguments representing database(name and password)
            String databasePassword = "root"; //args[1];

            RestaurantBillCalculator application = new RestaurantBillCalculator(databaseUserName, databasePassword);// create new RestaurantBillCalculator
            application.setVisible(true);

            application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// terminates the program if the JFrame is closed
       // } else {
           // JOptionPane.showMessageDialog(null, "Database Username/Password incorrect");
           // System.out.println("Usage: java " + "RestaurantBillCalculator databaseUserName databasePassword");
            //System.exit(0);
        }

    //} // end main method

} // end class RestaurantBillCalculator