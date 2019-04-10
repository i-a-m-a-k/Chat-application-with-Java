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
	public static InetAddress arr[] = new InetAddress[10];
	public static int i = 0;
	public static ChatThread c[] = new ChatThread[10];
	public static Socket s[] = new Socket[10];
	
	public void run()
	{	
		try
		{
			ServerSocket ss = new ServerSocket(44444);
			System.out.println("Waiting...");
			
			while(true)
			{
				s[i] = ss.accept();
				System.out.println("Connected! " + i + " \n");
				
				DataInputStream din=new DataInputStream(s[i].getInputStream());
				String ip = din.readUTF();
				
				InetAddress addr = InetAddress.getByName(ip);
				
				arr[i] = addr;
				
				c[i] = new ChatThread(addr,s[i]);
				c[i].run();
				i++;
			}
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}


class ChatThread extends Listener implements Runnable
{
	Socket so;
	InetAddress ip;
	
	//static Listener l;
	//static InetAddress arr[] = new InetAddress[10];
	//static int i = l.i;
	//static ChatThread c[] = new ChatThread[10];
	//static Socket s[] = new Socket[10];
	
	ChatThread(InetAddress a, Socket ss)
	{
		so = ss;
		ip = a;
		/*for(int j = 0; j<l.i ; j++)
		{
			this.arr[j] =  l.arr[j];
			this.c[j] = l.c[j];
			this.s[j] = l.s[j];
		}*/
	
	}
	public void run()
	{	
	
		try
		{
			Scanner in = new Scanner(System.in);
			System.out.println("OK");
			DataInputStream din = new DataInputStream(so.getInputStream());
			DataOutputStream dout = new DataOutputStream(so.getOutputStream());         
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			
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
							System.out.println(rx);
								
							if(rx.equals(""))
								continue;
							
							/*if(rx.equals("/send"))
							{
								System.out.println("Enter name:");
								String name = in.next();
								Chat.FileTransfer.Receive(s,name);
							}*/
							
							else
							{
								String str[] = rx.split("#@#@#@");
								InetAddress ip = InetAddress.getByName(str[0]);
								int p = -1;
								System.out.println(i);
								
								for(int j=0; j<i; j++)
									System.out.println(arr[j]);
								
								for(int j=0; j<i; j++)
								{
									if(arr[j].equals(ip))
									{
										System.out.println("if");
										p = j;
										break;
									}
								}
								
								System.out.println(p);
								
								if(p != -1)
								{
									Socket socket = s[p];	
									ChatHandler.Send(socket, str[1]);
								}
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			});
	        
			//send.start();
			receive.start();
	        
			if(/*!send.isAlive()||*/!receive.isAlive())
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

class ChatHandler
{
	public static void Send(Socket s, String str)
	{
		try
		{
			System.out.println(str);
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());	
			dout.writeUTF(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
