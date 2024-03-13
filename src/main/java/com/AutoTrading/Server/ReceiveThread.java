package com.AutoTrading.Server;

import org.springframework.stereotype.Component;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

@Component
public class ReceiveThread extends Thread {

	public static HashMap<String,PrintWriter> ClientAddrMap = new HashMap<String,PrintWriter>();
	public static HashMap<String,Socket> ClientSocketMap = new HashMap<String,Socket>();
    static Socket socket;	static PrintWriter out; static String Message;
	private final AutoTrading_Classify autoTradingClassify;
	public ReceiveThread (Socket Socket, AutoTrading_Classify autoTradingClassify) throws IOException {
		socket = Socket;
		this.autoTradingClassify = autoTradingClassify;
	}


	@Override
	public void run() {
		try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "euc-kr")), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "euc-kr"));                    	
        	autoTradingClassify.Classify(ExchangeBufferReadertoString(in));
		} catch (Exception e) { System.out.println(e.getMessage()); }
		finally { 
			try { socket.close(); }
			catch (IOException e) {	System.out.println(e.getMessage()); }
		}
	}
	

	private String ExchangeBufferReadertoString(BufferedReader in) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null) {
			if (line.contains("AutoTrading_Web_Connection")) {
				AddClientList("Web");
			}
		    stringBuilder.append(line);
		}
		String receivedData = stringBuilder.toString();
		return receivedData;
	}


	// 특정 클라이언트에 정보 보내기
	public static void SendInfoToClient (String SpecificClient, String Info) {
		if (ClientAddrMap.get(SpecificClient) != null) {
			PrintWriter value = ClientAddrMap.get(SpecificClient);
			value.println(Info);
			value.flush();
		}
	}
	
	
	// 클라이언트 저장
	public static void AddClientList(String str) throws Exception {
		if (ClientSocketMap.get(str) != null) {
			System.out.println(str+"클라이언트 재접속");
			ClientSocketMap.get(str).close();
		}
		ClientAddrMap.put(str,out);
		ClientSocketMap.put(str,socket);
		SendInfoToClient(str,"Success Client Connection!");
	}
	
	
	// 클라이언트 삭제
	public static void RemoveClientList(String[] User) throws Exception {
		if (ClientSocketMap.get(User[1]) != null) ClientSocketMap.get(User[1]).close();
		ClientAddrMap.remove(User[1]);
		ClientSocketMap.remove(User[1]);
	}
}
