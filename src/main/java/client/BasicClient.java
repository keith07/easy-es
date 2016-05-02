package client;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;

public class BasicClient {
	TransportClient client;

	public void doConnect() {
		Settings settings = Settings.settingsBuilder()
				.put("client.transport.ping_timeout", "20s")
				.put("client.transport.nodes_sampler_interval","20s")
				.put("client.transport.sniff", true)
				.put("cluster.name", "es-cluster").build();
		client = TransportClient.builder().settings(settings).build();
		client.addTransportAddress(new InetSocketTransportAddress(
				new InetSocketAddress("192.168.1.41", 9301)));
	}

	public void createIndex(String index,String type,String id) throws IOException {
		IndexRequestBuilder requestBuilder = client.prepareIndex(index,type,id).setRefresh(true);
		IndexResponse response = requestBuilder
				.setSource(
						XContentFactory.jsonBuilder().startObject()
								.field("author", "569874")
								.field("author_name", "riching")
								.field("mark", 232)
								.field("body", "北京不错，但是人太多了")
								.field("createDate", "20130801175520")
								.field("valid", true).endObject()).setTTL(8000)
				.execute().actionGet();
		System.out.println(response.getId());
	}
	
	public String getById(String index,String type,String id){
		GetResponse responseGet = client.prepareGet(index,type,id).execute().actionGet();
		String result = responseGet.getSourceAsString();
		return result;
	}
	
	public SearchResponse search(String index, String type, String id) {
		SearchRequestBuilder builder = client.prepareSearch(index)
				.setTypes(type).setSearchType(SearchType.DEFAULT).setFrom(0)
				.setSize(100);
		BoolQueryBuilder qb = QueryBuilders.boolQuery()
				.must(new QueryStringQueryBuilder("北京").field("body"))
				.should(new QueryStringQueryBuilder("太多").field("body"));
		builder.setQuery(qb);
		SearchResponse response = builder.execute().actionGet();
		System.out.println("  " + response);
		System.out.println(response.getHits().getTotalHits());
		return response;
	}
	
	public static void main(String[] args) throws IOException{
		BasicClient client = new BasicClient();
		client.doConnect();
		String index = "comment_index";
		String type = "comment_ugc";
		String id = "comment_123674";
//		client.createIndex(index, type, id);
		String result = client.getById(index, type,id);
//		String result = client.getById("megacorp", "employee","2");
		System.out.println(result);
	}
}
