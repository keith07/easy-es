package edu.keith.demo.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by keith on 2016/4/30 0030.
 */
public interface App {
    Map<String, String> map = new HashMap<String, String>();
    String CLOSED_MSG = "#################closed####################";
    long DELIVERIED_TAG = -1;

    interface ESProp {
        String INDEX_NAME = "heros";
        String DAIDONGXI_INDEX_NAME = "daidongxi";
        String TYPE_NEWS_INFO = "news_info";
        String TYPE_PRODUCT_INFO = "product_info";
        String TYPE_STORY_INFO = "story_info";
        String TYPE_TASK_INFO = "task_info";
        String TYPE_USER_INFO = "user_info";
        String TYPE_BRANDCASE_INFO = "brandcase_info";
        String INDEX_STORE_TYPE = "memory";
        int SHARDS = 2;
        int REPLICAS = 1;
        String REFRESH_INTERVAL = "-1";
    }
}
