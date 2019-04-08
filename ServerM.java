import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerM
{	
	public static void main(String args[])
	{	
		Listener l = new Listener();
		l.run();
	}
	
}

class Listener implements Runnable
{
	static InetAddress arr[] = new InetAddress[10];
	static int i = 0;
	
	public void run()
	{
		Socket s;
		
		try
		{
			ServerSocket ss = new ServerSocket(44444);
			System.out.println("Waiting...");
			
			while(true)
			{
				s = ss.accept();
				System.out.println("Connected!\n");
				
				DataInputStream din=new DataInputStream(s.getInputStream());
				String ip = din.readUTF();
				
				InetAddress addr = InetAddress.getByName(ip);
				
				arr[i++] = addr;
				
				ChatThread c = new ChatThread(addr,s);
				c.run();
			}
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}


class ChatThread implements Runnable
{
	Socket s = new Socket();
	InetAddress ip;
	
	ChatThread(InetAddress a, Socket ss)
	{
		s = ss;
		ip = a;
	}
	public void run()
	{	
	
		try
		{
			Scanner in = new Scanner(System.in);
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
						while( !tx.equalsIgnoreCase("Bye"))
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
						while(!rx.equalsIgnoreCase("Bye"))
						{
							rx = din.readUTF();
								
							if(rx.equals("/send"))
							{
								System.out.println("Enter name:");
								String name = in.next();
								Chat.FileTransfer.Receive(s,name);
							}
							else
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
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
