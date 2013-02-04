package com.nali.spreader.lucene;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.remote.ISegmenAnalyzerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestSegmen {
	@Autowired
	private ISegmenAnalyzerService replyService;

	@Test
	public void test() {
		Set<String> set = replyService
				.getContentSegmen("要官员公开财产有法律依据吗？全国人大有公布财产公开的法律吗？官员也是人，也有隐私；官员是公仆，不是老百姓的奴隶。");
		System.out.println(set);
	}

	public static void main(String... args) throws IOException {
		String url = "http://e.weibo.com/1644489953/zdvzgtZas?ref=http%3A%2F%2Fwww.weibo.com%2F327888787%3Fwvr%3D5%26lf%3Dreg%26wvr%3D5%26lf%3Dreg";
		// fetch the specified URL and parse to a HTML DOM
		// Document doc = Jsoup
		// .connect(url)
		// .userAgent(
		// "Mozilla/5.0 (Windows NT 6.1; rv:17.0) Gecko/20100101 Firefox/17.0 FirePHP/0.7.1")
		// .get();
		// Elements el = doc.select("div#news_content");
		// String txt = el.text();
		// System.out.println(txt);
		String xx = "safdade\"adfadb\")}";
		// String tt = xx.substring(xx.indexOf("sa"), xx.lastIndexOf("\""));
		// String x1 = tt.substring(xx.indexOf("s"));
		System.out.println(xx.indexOf("dad"));
		System.out.println(xx.substring(1));

	}

	@Test
	public void test2() {
		// String url = "http://weibo.com/1197161814/zdMhlw1mb";
		String url = "http://weibo.com/1644739071/zdFe18nj2?from=singleweibo&wvr=5&loc=hotweibo&c=pl#1357800696442";
		try {
			Document doc = Jsoup
					.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; rv:17.0) Gecko/20100101 Firefox/17.0 FirePHP/0.7.1")
					.header("Cookie",
							"SUS=SID-2437267261-1357798477-XD-v69zs-208a81f6869d432cd9aac7daee69f153; SUE=es%3D1b1459b4fe1a04359bfb97994a153d5c%26ev%3Dv1%26es2%3D556c83696a522c42421efb1f615ad5c3%26rs0%3DSrJz9t%252FLgEjK53TbSqfrMuNLJE8sceaNz6Zo%252FIfmcCBL9aK4aFr3ah3VDxX3B6QEH%252BN32VBRdpgrRF16WTOMzvlvIEt8B7zgNTjSiJLSBertXtIDAxwaaPcrQRZ%252Bc8v%252FIioq%252Bpthe%252B%252BRDEODp9lrFdzmQyV%252FjMflgn5zuiXhl20%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1357798477%26et%3D1357884877%26d%3Dc909%26i%3Dd2e5%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26uid%3D2437267261%26user%3Deixie9%2540gmail.com%26ag%3D4%26name%3Deixie9%2540gmail.com%26nick%3Dtry-catch-finally%26fmp%3D%26lcp%3D; ALF=1358403275; SSOLoginState=1357798477; v=5; NSC_wjq_xfjcp.dpn_w3.6_w4=ffffffff0941013845525d5f4f58455e445a4a423660; un=eixie9@gmail.com; _s_tentry=weibo.com; UOR=weibo.com,weibo.com,; Apache=1635338560161.2668.1357798491400; SINAGLOBAL=1635338560161.2668.1357798491400; ULV=1357798491578:1:1:1:1635338560161.2668.1357798491400:; wvr=5; SinaRot/3/327888787%3Fwvr%3D5%26uut%3Dfin%26from%3Dreg=41")
					.get();
			Elements els = doc.getElementsByTag("script");
			for (Element el : els) {
				String script = el.toString();
				if (script.indexOf("pl_content_weiboDetail") > 1) {
					String str = el.childNode(0).toString();
					String xx = str.substring(str.indexOf("html\":\"") + 7,
							str.lastIndexOf("\""));
					String ss = "";
					// = WeiboMsg.decode(xx);
					read(ss);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void read(String html) {
		Document doc = Jsoup.parse(html);
		// "a[usercard] , span.W_textb"
		Elements els = doc.select("dl.comment_list>dd");
		Elements wbels = doc.select("div.WB_text>p>em");
		// System.out.println(wbels.get(0).childNode(0).toString());
		String wb = wbels.get(0).childNode(0).toString();
		for (Element e : els) {
			// System.out.println(e);
			// Elements ees = e.select("a[usercard] + span.S_txt2");
			List<Node> cnode = e.childNodes();
			StringBuffer buff = new StringBuffer();
			for (Node n : cnode) {
				String nodeName = n.nodeName();
				String text = n.attr("text");
				if ("#text".equals(nodeName)) {
					if (StringUtils.isBlank(text)) {
						continue;
					} else {
						buff.append(text);
					}
				}
				if ("a".equals(nodeName)) {
					String usercard = n.attr("usercard");
					if (StringUtils.isNotBlank(usercard)
							&& usercard.startsWith("name=")) {
						String atUser = n.childNode(0).toString();
						if (StringUtils.isNotEmpty(atUser)) {
							buff.append(atUser);
						}
					}
					String actionType = n.attr("action-type");
					if (StringUtils.isNotEmpty(actionType)
							&& "feed_list_url".equals(actionType)) {
						String href = n.attr("href");
						if (StringUtils.isNotEmpty(href)) {
							buff.append(href);
						}
					}
				}
				String classType = n.attr("class");
				if ("S_txt2".equals(classType)) {
					break;
				}
				if ("img".equals(nodeName)) {
					String face = n.attr("type");
					String faceTitle = n.attr("title");
					if ("face".equals(face)
							&& StringUtils.isNotEmpty(faceTitle)) {
						buff.append(faceTitle);
					}
				}
			}
			System.out.println("回复>>>" + getBuff(buff.toString()));
		}
	}

	private String getBuff(String buff) {
		return buff.substring(buff.indexOf("：") + 1);
	}
}
