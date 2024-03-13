package com.AutoTrading.Server;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AutoTrading_Service{

	@Autowired
	private AutoTrading_Mapper autoTrading;

	@Transactional
	public void InsertAutoTradingInfo(ArrayList<String> AutoTradingInfoList) {
		autoTrading.InsertAutoTradingInfo(AutoTradingInfoList);
	}
	

  
}
