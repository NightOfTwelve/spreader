package com.nali.spreader.lucene;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.junit.Test;

public class TestMmj {

	@Test
	public void test() {

	}

	private List<Document> get() {
		List<Document> docs = new ArrayList<Document>();
		List<String> cs = new ArrayList<String>();
		cs.add("@新浪财经：【印度降息降现金储备率促增长】从2月9日起回购利率由8%降至7.75%，现金储备率由4.25%降至4%，为银行系统增加1800亿卢比流动性。印度成为亚洲2013年首个降息的主要经济体，之前通胀降至三年低点但仍超7%，但央行称促增长仍然有一定空间。http://t.cn/zY2WE6c  ");
		cs.add("@新闻晨报：【温暖一生的礼物 流浪猫咪的腊肉】晚上，家门口发现了这块腊肉，张望时见到我常喂的那只流浪猫站在墙角，它怯生生地望了我一会，然后撒娇似地朝我叫了几声，转身消失。我不知道它等了我多久，也不知道它叼这块肉送给我需要花费多少气力，但我知道这是我今年收到最珍贵的礼物。[礼物] via左伊蓝咖啡  http://ww3.sinaimg.cn/large/4e5b54d8jw1e1aewhv9z9j.jpg ");
		cs.add("@南方周末：【《随笔》精选】穆勒在今天为何依然重要：每个人都得把不妨碍他人作为实现自由的条件；否则，人人都要自由，却个个都会“不由”。这是约翰•穆勒在《论自由》一书里首次明确提出的。如果每个人都有权根据自己的喜好阻挠别人的话，世界上就没有谁可以按照自以为对的方式生活了。http://t.cn/zY2JErb http://ww2.sinaimg.cn/large/61b8c41ejw1e1aev9x5nlj.jpg ");
		for (String c : cs) {
			Document doc = new Document();
			doc.add(new Field("content", c, Field.Store.YES,
					Field.Index.ANALYZED));
			docs.add(doc);
		}
		return docs;
	}
}
