import java.io.*;
import java.util.Scanner;
import java.net.*;
import java.nio.file.*;

public class ReceiveF
{
   public static void main(String args[])
   {
      try
      {
         //Connection
         InetAddress addr = InetAddress.getByName(args[0]);   
         Socket s = new Socket(addr,44444);
         System.out.println("Connected!\n\n");
         
         Scanner in = new Scanner(System.in);
         System.out.println("Enter filename: ");
         String name = in.next();
         
         Path path = FileSystems.getDefault().getPath(".", name);
      
/*         int i = 1;
         File file = new File(name);
         while(true)
         {
            file = new File(name);
            if(!file.isFile())
               break;
            name = name + i;
            i++;
         }
         
         
         DataInputStream din=new DataInputStream(s.getInputStream());
         String str = din.readUTF();
         int l = Integer.parseInt(str);
         
         InputStream is = s.getInputStream();
         FileOutputStream fout = new FileOutputStream(file);
         BufferedOutputStream bout = new BufferedOutputStream(fout);
         byte[] arr = new byte[l];
         
         int bytesRead = is.read(arr, 0, arr.length);
         bout.write(arr, 0, bytesRead);*/
         
         DataInputStream din=new DataInputStream(s.getInputStream());
         
         int len = din.readInt();
         
         InputStream is = s.getInputStream();

         byte[] file = new byte[len];        
         int bytesRead = is.read(file, 0, file.length);
         
         Files.write(path, file);
         
         System.out.println("Received!");

//         bout.flush();
//         bout.close();
         s.close();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }  
}     
