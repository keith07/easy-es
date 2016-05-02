package edu.keith;

import edu.keith.bean.App;
import edu.keith.bean.TaskInfo;
import edu.keith.service.ElasticsearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by keith on 2016/4/30 0030.
 */
public class AppMain {

    private static final Logger logger = LogManager.getLogger(AppMain.class);

    public void start() {
        FileSystemXmlApplicationContext context = null;
        try {
            context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring-mvc.xml");
        } catch (Exception e) {
            logger.error("An error occurred, applicationContext will close.", e);
            if (context != null) {
                context.close();
            }
            context = null;
            logger.error(App.CLOSED_MSG);
        }
    }

    /**
            * 插入
    * @author 高国藩
    * @date 2015年6月16日 上午10:14:21
            */
    @Test
    public void insertNo() {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring-mvc.xml");
        ElasticsearchService service = context
                .getBean(ElasticsearchService.class);
        List<TaskInfo> taskInfoList = new ArrayList<TaskInfo>();
        for (int i = 0; i < 20; i++) {
            taskInfoList.add(new TaskInfo(String.valueOf((i + 5)), i + 5, "我是中国人"
                    + i, "taskArea", "taskTags", i + 5, "1996-02-03", "霍华德"));
        }
        service.insertOrUpdateTaskInfo(taskInfoList);
    }

    /**
            * 查询
    * @author 高国藩
    * @date 2015年6月16日 上午10:14:21
            */
    @Test
    public void serchNo() {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring-mvc.xml");
        ElasticsearchService service = context
                .getBean(ElasticsearchService.class);
        List<Map<String, Object>> al = service.queryForObject("task_info",
                new String[] { "taskContent", "taskArea" }, "中国", "taskArea", SortOrder.DESC,
                0, 2);

        for (int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i));
        }

    }

    /**
            * filter查询
    * @author 高国藩
    * @date 2015年6月16日 上午10:14:21
            */
    @Test
    public void serchFilter() {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring-mvc.xml");

        ElasticsearchService service = context.getBean(ElasticsearchService.class);
        List<Map<String, Object>> al = service.queryForObjectForElasticSerch("task_info", "taskContent", "高",19,20);

        for (int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i));
        }

    }
}
