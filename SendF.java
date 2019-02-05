import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SendF
{
   public static void main(String args[])
   {
      try
      {
         Scanner in = new Scanner(System.in);
         
         //Connection
         ServerSocket ss = new ServerSocket(44444);
         System.out.println("Waiting...");
         Socket s = ss.accept();
         System.out.println("Connected!\n");
         
         //File
         String wd = System.getProperty("usr.dir");
         System.out.println("Enter file name: ");
         String a = in.next();
         File file = new File(a);
         
         BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
         DataOutputStream dout = new DataOutputStream(s.getOutputStream());
         
         //Convert to byte array and send
         byte[] arr = new byte[ (int) file.length()];
         String l = new String("" + arr.length);
         dout.writeUTF(l);
         bin.read(arr, 0, arr.length);
         OutputStream os = s.getOutputStream();
         os.write(arr, 0, arr.length);


         os.flush();
         ss.close();
               
     }
     catch(Exception e)
     {
         e.printStackTrace();
     }
   }
 }


/*
https://stackoverflow.com/questions/20947166/file-transfer-java-socket-programming
http://www.java2s.com/Code/Java/Network-Protocol/TransferafileviaSocket.htm
*/
