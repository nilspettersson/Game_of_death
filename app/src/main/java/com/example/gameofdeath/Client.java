package com.example.gameofdeath;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	
	private Socket client;
	
	private DataInputStream input;
	private DataOutputStream output;
	
	public Client(String ip, int port) {
		try {
			client=new Socket(ip, port);
			
			input=new DataInputStream(client.getInputStream());
			output=new DataOutputStream(client.getOutputStream());

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public DataInputStream getInput() {
		return input;
	}

	public void setInput(DataInputStream input) {
		this.input = input;
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public void setOutput(DataOutputStream output) {
		this.output = output;
	}
	
	
	
	
	

}
