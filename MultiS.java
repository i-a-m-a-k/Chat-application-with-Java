import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiS
{

   static final int port = 44444;
   
   public static void main(String args[])
   {
      try
      {
         //Connection
         int i = 0;
         Socket s[] = new Socket[10];
         
         while(i!=10)
         {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting...");
            s[i] = ss.accept();
            System.out.println("Connected!" + i + "\n");
            
            Thread thread = new Thread(new Runnable()
            {
               Thread send = new Thread(new Runnable()
             {
               public void run()
               {
                  try
                  {
                     String tx = "";
                     while( !tx.contains("Bye"))
                     {
                        tx = br.readLine();
                        
                        if(tx.equals(""))
                           continue;
                           
                        /*else if(tx.equals("/send"))
                        {
                           System.out.println("Enter name:");
                           String name = in.next();
                           Chat.FileTransfer.Send(s,name);
                        }*/
                        else
                        {   
                           dout.writeUTF(tx);
                           dout.flush();
                        }
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
                     while(!rx.equals("/bye"))
                     {
                        rx = din.readUTF();
                        
                       /* if(rx.equals("/send"))
                        {
                           System.out.println("Enter name:");
                           String name = in.next();
                           Chat.FileTransfer.Receive(s,name);
                        }
                        
                        else*/
                           System.out.println("Client: " + rx);
                     }
                  }
                  catch(Exception e)
                  {
                     e.printStackTrace();
                  }
               }
            });   
        });
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
}  
