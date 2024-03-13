package com.AutoTrading.Server;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AutoTrading_Mapper {
	void InsertAutoTradingInfo(ArrayList<String> AutoTradingInfo);
}
