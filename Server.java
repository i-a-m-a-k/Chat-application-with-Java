import java.io.*;
import java.net.*;

public class Server
{
   public static void main(String args[])
   {
      try
      {
         //Connection
         ServerSocket ss = new ServerSocket(44444);
         System.out.println("Waiting...");
         Socket s = ss.accept();
         System.out.println("Connected!\n");
         
         //Text
         DataInputStream din=new DataInputStream(s.getInputStream());
         DataOutputStream dout = new DataOutputStream(s.getOutputStream());         
         BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

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
                     System.out.println("Client: " + rx);
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
            ss.close();
         }
         
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
}      
