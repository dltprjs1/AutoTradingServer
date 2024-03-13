package com.AutoTrading.Server;

import java.io.File;
import java.util.ArrayList;
import org.ini4j.Ini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoTrading_Classify {
	String SendMessages = "";
	String TimeCheck = "";
	
	@Autowired
	private AutoTrading_Controller Controller;

    public void Classify(String Message) throws Exception {
    	System.out.println(Message);
    	SendMessages = Message;
		File iniFile = new File("Server.ini");
		Ini ini = new Ini(iniFile);
		for (int i = 1; i <= ini.get("AutoTradingHeader").size(); i++) {
			if (Message.contains(ini.get("AutoTradingHeader", String.valueOf(i)))) {
				Save(Manufacture(Message.split("#")));
			}
		}
	}


	private static ArrayList<String> Manufacture(String[] Before_Process) {
		ArrayList<String> Result = new ArrayList<>();
        for (String beforeProcess : Before_Process) {
            System.out.println(beforeProcess);
            Result.add(beforeProcess);
        }
		return Result;
	}


	private void Save(ArrayList<String> AutoTradingInfoList) {
		if (!TimeCheck.equals(AutoTradingInfoList.get(1))) {			// 네트워크 지연으로 인한 데이터 중복 수신 필터
			Controller.Insert(AutoTradingInfoList);
			ReceiveThread.SendInfoToClient("Web", SendMessages);
			TimeCheck = AutoTradingInfoList.get(1);
		}		
	}


}
