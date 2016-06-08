package edu.keith.bean;

import edu.keith.EsConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by QiGuanYi on 2016/5/9.
 */
@Document(indexName = EsConstants.INDEX_NAME_GOODS,type = EsConstants.TYPE_NAME_DESCRIBE)
public class GoodsDescribe {
	@Id
	@Field(index = FieldIndex.not_analyzed,store = true)
	private String goodsId;
	@Field(type = FieldType.String,analyzer = "ik",searchAnalyzer = "ik",store = true)
	private String name;
	@Field(type = FieldType.String,analyzer = "ik",searchAnalyzer = "ik",store = true)
	private String describe;
	@Field(type = FieldType.Nested,analyzer = "ik",searchAnalyzer = "ik",store = true)
	private List<String> tags;

}
