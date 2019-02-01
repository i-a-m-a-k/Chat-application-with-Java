import java.io.*;  
import java.net.*;
import java.util.Scanner;

public class Client
{
   public static void main(String[] args)
   {  
      try
      {      
             
         InetAddress addr = InetAddress.getByName(args[0]);
         
         Socket s = new Socket(addr,44444);
         System.out.println("Connected!\n\n");
         
         Scanner in = new Scanner(System.in);
         DataInputStream din=new DataInputStream(s.getInputStream());
         DataOutputStream dout = new DataOutputStream(s.getOutputStream());
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
                  
         Thread send = new Thread(new Runnable()
         {
            public void run()
            {
               try
               {
                  String tx = "";
                  while(!tx.contains("Bye"))
                  {
                     tx = br.readLine();
                     if(tx.equals(""))
                        continue;
                     dout.writeUTF(tx);
                     dout.flush();
                  }
               }
               catch(Exception e)
               {
                  e.printStackTrace();
               }
            }
         });
         
         Thread receive = new Thread(new Runnable()
         {
            public void run()
            {
               try
               {
                  String rx = "";               
                  while(!rx.contains("Bye"))
                  {
                     rx = din.readUTF();
                     System.out.println("Server: " + rx);
                  }
               }
               catch(Exception e)
               {
                  e.printStackTrace();
               }
            }
         });
         
         send.start();
         receive.start();
         
         if(!send.isAlive()||!receive.isAlive())
         {
            din.close();
            dout.close();
            s.close();
         }
          
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      
   }  
}  
