package zookeeper;

import java.io.BufferedReader;
import java.io.File;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;//for panel window
import java.util.*;  //for array list
import java.awt.event.*;//for event action
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ZooKeeper {

  //import files for animals and habitats

  private static final String ANIMALS_FILE = "animals.txt";
  private static final String HABITATS_FILE = "habitats.txt";

  //variables for storing user input

  String zooKeeper = "";
  String monitorAnimal = "";
  String monitorHabitat = "";
  String exit = "Exit";

  private Scanner scnr;

  //shows user options and create scanner for input

  public void run() {

    scnr = new Scanner(System.in); 
    showUserOptions();
    zooKeeper = scnr.nextLine();

// the entered text will be converted to upper case to check if monitor animal was input
    if (zooKeeper.trim().equalsIgnoreCase("Monitor Animal")) {

      System.out.println("Which Animal you would like to monitor?");
      System.out.println("\nLion");
      System.out.println("Tiger");
      System.out.println("Bear");
      System.out.println("Giraffe");

// Animal details
      monitorAnimal = scnr.next();
      showInfoForAnimals(monitorAnimal.toUpperCase());
    }

// the entered text will be converted to upper case to check if monitor habitat was input
    if (zooKeeper.trim().equalsIgnoreCase("Monitor Habitat")) {
      System.out.println("Which Habitat you would like to monitor?");
      System.out.println("\nPenguin Habitat");
      System.out.println("Bird House");
      System.out.println("Aquarium");

// habitats details

      monitorHabitat = scnr.next();
      showInfoForHabitats(monitorHabitat.toUpperCase());
    }

// exit program if exit is input

    if (zooKeeper.trim().equalsIgnoreCase(exit)) {
      System.exit(0);
    }

  }



  //display user options

  public void showUserOptions() {
	  
    System.out.println("\nWhat would you like to do: Monitor Animal? Monitor Habitat?\n\nType 'Exit' to exit program");

  }

  public void showInfoForHabitats(String selectedHabitat) {

    File file = new File(HABITATS_FILE);
    BufferedReader br = null;
    String line = "";
    int lines = 0;
    
    try {

// to read the contents from the file
      br = new BufferedReader(new FileReader(file));
      while ((line = br.readLine()) != null) {

        if (lines == 1 || lines == 2 || lines == 3) {//this condition will display the next three lines from the file when the habitat name get matched

          lines++;

          if (line.startsWith("*")) {//if the warning message is found replaces the asterisks from the warning message

            alertUser(line.replaceAll("\\*", ""));
          }
          System.out.println(line);
        }
        if (lines >= 4) {
          lines = 0;
          run();

        }

//trim method is used to remove the space

// to check whether the entered animal matches with the habitat name present in the file

        if (line.trim().equalsIgnoreCase("Habitat - " + selectedHabitat)) {

          lines++;
        }
      }
    } catch (FileNotFoundException e) {

      e.printStackTrace();

    } catch (IOException e) {

      e.printStackTrace();

    } finally {

      if (br != null) {

        try {

          br.close();

        } catch (IOException e) {

          e.printStackTrace();

        }

      }

    }

  }



  public void showInfoForAnimals(String selectedAnimal) {

    File file = new File(ANIMALS_FILE);

    BufferedReader br = null;

    String line = "";

    int lines = 0;

    try {

// to read the contents from the file

      br = new BufferedReader(new FileReader(file));

      while ((line = br.readLine()) != null) {



        //display the next four lines from the file when the animal name is matched

        if (lines == 1 || lines == 2 || lines == 3 || lines == 4) { 

          lines++;

          //replaces the asterisks from the warning message

          if (line.startsWith("*")) { 

            alertUser(line.replaceAll("\\*", ""));

          }

          System.out.println(line);

        }



        if (lines >= 5) {

          lines = 0;

          run();

        }

        //checks entered animal matches with the animal name in the file

        if (line.trim().equalsIgnoreCase("ANIMAL - " + selectedAnimal)) { 

          lines++;

        }



      }

    } catch (FileNotFoundException e) {

      e.printStackTrace();

    } catch (IOException e) {

      e.printStackTrace();

    } finally {

      if (br != null) {

        try {

          br.close(); // br closed

        } catch (IOException e) {

          e.printStackTrace();

        }

      }

    }

  }



  /**

   * @param message - to pass the warning message from the file method to

   * alert the user if any warning is found in the file

   */

  public void alertUser(String message) {

//shows the dialogue on the top of the console window once the warning is noticed

    final JDialog dialog = new JDialog();

    dialog.setAlwaysOnTop(true);

    JOptionPane.showMessageDialog(dialog, message, "ALERT", JOptionPane.INFORMATION_MESSAGE);

  }



  public static void main(String[] args) {
    
    try {
      File f = new File("login.txt");
      //if file doesn't exist the create it
      if (f.createNewFile()) {
        login l= new login();
       
        //login panel is created
        l.userLogin();
        
      }else {
        login l= new login();
       
        //login panel is created
        l.userLogin();
        
      }
      }catch (IOException e) {
     
      e.printStackTrace();
    }
    
   
  }

}
//login class to display login panel
class login {
  
  public ArrayList<user> ul=new ArrayList<user>();
  public login()
  {

  }
  public void newUser()
  {
    JFrame frame=new JFrame("New user register");
    JPanel rpanel = new JPanel();
    JLabel username = new JLabel("User - ");
    JLabel password = new JLabel("Pass - ");
    JTextField ruser = new JTextField(15);
    JTextField rpass = new JPasswordField(15);
    JButton rbutton = new JButton("Register");
    rpanel.setLayout (null); 

    //all component position is set on the panel
    ruser.setBounds(70,30,150,20);
    rpass.setBounds(70,65,150,20);
    rbutton.setBounds(110,100,80,20);
    
    username.setBounds(20,28,80,20);
    password.setBounds(20,63,80,20);
    //all components are added to the panel
    rpanel.add(rbutton);
    rpanel.add(ruser);
    rpanel.add(rpass);
   
    rpanel.add(username);
    rpanel.add(password);

    //Event listener is added to login button and is handled on occurring of event.
    rbutton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          String uname = ruser.getText();
          String upass = rpass.getText();
          System.out.println(uname);
          ul.add(new user(uname,upass));

          frame.dispose();
          try {
            //add new user to file
            FileWriter fw = new FileWriter("login.txt",true);
            for(int i=0;i<ul.size();i++)
            fw.write(String.format("%s,%s\n",ul.get(i).getUser(),ul.get(i).getPass()));
            fw.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException f) {
         
          f.printStackTrace();
          }

          userLogin();
        }
        });
    frame.add(rpanel);
    frame.setSize(300,200);
    frame.setLocation(500,280);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

  }
  public void userLogin()
  {
    JFrame frame=new JFrame("user Login");
    JPanel lpanel = new JPanel();
    JLabel username = new JLabel("User - ");
    JLabel password = new JLabel("Pass - ");
    JTextField luser = new JTextField(15);
    JTextField lpass = new JPasswordField(15);
    JButton lbutton = new JButton("Login");
    JButton nuser = new JButton("New User?");
    
    lpanel.setLayout (null); 

    //all component position is set on the panel
    luser.setBounds(70,30,150,20);
    lpass.setBounds(70,65,150,20);
    lbutton.setBounds(110,100,80,20);
    nuser.setBounds(110,135,80,20);
    username.setBounds(20,28,80,20);
    password.setBounds(20,63,80,20);
    //all components are added to the panel
    lpanel.add(lbutton);
    lpanel.add(luser);
    lpanel.add(lpass);
    lpanel.add(nuser);
    lpanel.add(username);
    lpanel.add(password);
    //Event listener is added to login button and is handled on occurring of event.
    lbutton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          boolean flag=false;
          String uname = luser.getText();
          String upass = lpass.getText();
          try{


          Scanner scan =new Scanner(new File("login.txt"));
          //read all user and password from the file 
          while(scan.hasNextLine())
          {
            String[] cred=scan.nextLine().split(",");
            System.out.println(cred[0]);
           
              ul.add(new user(cred[0],cred[1]));

          }
           scan.close();
            
          }catch(Exception f)
          {
           f.printStackTrace();

          }
          
          
          for(int i=0;i<ul.size();i++)
          {
           //check if user exist with correct credentials.
            if(uname.equals(ul.get(i).getUser()) && upass.equals(ul.get(i).getPass()))
            {
              JOptionPane.showMessageDialog(null,"Login successfully");
              flag=true;
              frame.dispose();
              ZooKeeper zooKeeper = new ZooKeeper();

              zooKeeper.run();
            }
            else if(uname.equals("") && upass.equals(""))
            JOptionPane.showMessageDialog(null,"Insert username and password");
            else 
            {
              
              luser.setText("");
              lpass.setText("");

              
            }
       

          }
          if(!flag)
            JOptionPane.showMessageDialog(null,"Wrong Username or Wrong Password");
          frame.dispose();
          System.exit(0);
        }
        });
    //Event listener is added to new user button and is handled on occurring of event.
    nuser.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        
        newUser();
        frame.dispose();
      }
    });
    frame.add(lpanel);
    frame.setSize(300,200);
    frame.setLocation(500,280);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
  
  
}  
//user class is implemented to store user data
class user{
  String name;
  String pass;
  public user(String name,String pass)
  {
    this.name=name;
    this.pass=pass;
  }
  public String getUser()
  {
    return name;

  }
  public String getPass()
  {
    return pass;
  }
}
