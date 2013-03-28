package com.nali.spreader.lucene.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.search.NRTManagerReopenThread;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.nali.spreader.data.ReplySearch;
import com.nali.spreader.lucene.service.ISearchService;

@Service
public class SearchService implements ISearchService, DisposableBean {
	private static final Logger logger = Logger.getLogger(SearchService.class);
	private Directory dir;
	private IndexWriter indexWriter;
	private NRTManager nrtMgr;
	private TrackingIndexWriter tiw;
	private String INDEX_PATH;
	// private static final String FILE_PATH = "/usr/local/spreadersearch";
	private NRTManagerReopenThread reopenThread;

	@PostConstruct
	public void init() {
		try {
			Configuration cfg = new PropertiesConfiguration("env.properties");
			INDEX_PATH = cfg.getString("spreader.lucene.index");
			logger.info("load env.properties>>>>>>" + INDEX_PATH);
			dir = FSDirectory.open(new File(INDEX_PATH));
			indexWriter = new IndexWriter(dir, new IndexWriterConfig(
					Version.LUCENE_36, new IKAnalyzer(true)));
			SearcherFactory searcherFactory = new SearcherFactory();
			tiw = new TrackingIndexWriter(indexWriter);
			nrtMgr = new NRTManager(tiw, searcherFactory);
			reopenThread = new NRTManagerReopenThread(nrtMgr, 0.5, 0.0025);
			reopenThread.setDaemon(true);
			reopenThread.setName("NRTManagerDaemon");
			reopenThread.start();
		} catch (IOException e) {
			logger.error("Directory open fail ", e);
		} catch (ConfigurationException e) {
			logger.error("read env.properties error" + e);
		}
	}

	@Override
	public long index(List<Field> fields) {
		if (fields != null) {
			Document doc = new Document();
			for (Field f : fields) {
				doc.add(f);
			}
			try {
				long result = tiw.addDocument(doc, new IKAnalyzer(true));
				indexWriter.commit();
				return result;
			} catch (IOException e) {
				logger.error(" addDocument fail ", e);
			}
		}
		return 0;
	}

	@Override
	public void indexDocs(List<Document> docs) {
		if (docs != null) {
			try {
				for (Document doc : docs) {
					String content = doc.get("content");
					indexWriter.deleteDocuments(new Term("content", content));
					indexWriter.forceMergeDeletes();
					indexWriter.addDocument(doc);
				}
				// tiw.addDocuments(docs);
				indexWriter.commit();
			} catch (IOException e) {
				logger.error(e, e);
			}
		}
	}

	@Override
	public boolean indexDoc(Document doc) {
		if (doc == null) {
			return false;
		}
		try {
			indexWriter.addDocument(doc);
			indexWriter.commit();
			return true;
		} catch (CorruptIndexException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}
		return false;
	}

	@Override
	public long delete(Query... queries) {
		try {
			return tiw.deleteDocuments(queries);
		} catch (IOException e) {
			logger.error(e, e);
		}
		return 0;
	}

	@Override
	public long delete(Term... terms) {
		try {
			return tiw.deleteDocuments(terms);
		} catch (IOException e) {
			logger.error(e, e);
		}
		return 0;
	}

	@Override
	public long deleteAll() {
		try {
			return tiw.deleteAll();
		} catch (IOException e) {
			logger.error(e, e);
		}
		return 0;
	}

	@Override
	public long update(Term t, Document doc) {
		try {
			return tiw.updateDocument(t, doc);
		} catch (IOException e) {
			logger.error(e, e);
		}
		return 0;
	}

	private IndexSearcher getIndexSearcher() {
		return nrtMgr.acquire();
	}

	private List<DocumentScore> getDocument(String keyword, String[] fieldName,
			int rows) {
		IndexSearcher searcher = getIndexSearcher();
		List<DocumentScore> list = new ArrayList<DocumentScore>();
		try {
			String key = keyword;
			if (StringUtils.isBlank(keyword)) {
				return list;
			}
			IKAnalyzer ik = new IKAnalyzer();
			MultiFieldQueryParser m = new MultiFieldQueryParser(
					Version.LUCENE_36, fieldName, ik);
			m.setDefaultOperator(Operator.OR);
			Query query = m.parse(key);
			// TermQuery query = new TermQuery(new Term(fieldName[0], keyword));
			TopDocs tds = searcher.search(query, rows);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				float score = sd.score;
				DocumentScore ds = new DocumentScore();
				ds.setDoc(doc);
				ds.setScore(score);
				list.add(ds);
			}
		} catch (CorruptIndexException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		} catch (ParseException e) {
			logger.error(e, e);
		} finally {
			try {
				nrtMgr.release(searcher);
			} catch (IOException e) {
				logger.error(e, e);
			}
		}
		return list;
	}

	@Override
	public List<ReplySearch> searchReply(String keyword, String[] fieldName,
			int rows) {
		List<DocumentScore> docs = getDocument(keyword, fieldName, rows);
		List<ReplySearch> data = new ArrayList<ReplySearch>();
		if (docs != null) {
			for (DocumentScore ds : docs) {
				Document doc = ds.getDoc();
				float score = ds.getScore();
				String text = doc.get("content");
				ReplySearch rs = new ReplySearch();
				rs.setScore(score);
				rs.setReply(text);
				data.add(rs);
			}
		}
		return data;
	}

	/**
	 * 防止重复建立索引导致的死锁
	 */
	@Override
	public void destroy() throws Exception {
		if (IndexWriter.isLocked(dir)) {
			indexWriter.close();
			IndexWriter.unlock(dir);
		}
	}

	private class DocumentScore {
		private Document doc;
		private Float score;

		public Document getDoc() {
			return doc;
		}

		public void setDoc(Document doc) {
			this.doc = doc;
		}

		public Float getScore() {
			return score;
		}

		public void setScore(Float score) {
			this.score = score;
		}
	}
}
