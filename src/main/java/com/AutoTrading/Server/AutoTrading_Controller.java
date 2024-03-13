package com.AutoTrading.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;

@Controller
public class AutoTrading_Controller {

    @Autowired
    private AutoTrading_Service autoTradingService;

    public void Insert(ArrayList<String> AutoTradingListInfo) {
        int size = AutoTradingListInfo.size();
        if (AutoTradingListInfo.size() <= 23){
            for (int i = 1; i <= 23-size; i++) {
                AutoTradingListInfo.add("");
            }
        }
        AutoTradingListInfo.add("1");
        autoTradingService.InsertAutoTradingInfo(AutoTradingListInfo);
    }
}
