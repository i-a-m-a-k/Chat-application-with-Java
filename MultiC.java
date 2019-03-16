import java.io.*;  
import java.net.*;
import java.util.Scanner;

public class MultiC
{
   public static void main(String[] args)
   {  
      try
      {      
             
         InetAddress addr = InetAddress.getByName(args[0]);
         
         Socket s = new Socket(addr,44444);
         System.out.println("Connected!\n\n");
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
}
