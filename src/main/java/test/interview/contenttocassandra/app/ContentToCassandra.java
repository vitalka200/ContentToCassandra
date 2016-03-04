package test.interview.contenttocassandra.app;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.interview.contenttocassandra.internal.data.Address;
import test.interview.contenttocassandra.internal.data.Chunk;
import test.interview.contenttocassandra.internal.data.ContentDao;
import test.interview.contenttocassandra.internal.workers.DataDownloader;
import test.interview.contenttocassandra.internal.workers.DataInserter;

public class ContentToCassandra {
	protected static final Logger logger = LoggerFactory.getLogger(ContentToCassandra.class);
	
	public static void main(String[] args) {
		String url = null; 
		
		if (args.length > 1) {
			url = args[1];
			logger.info("Starting to work on url: '{}'", url);
		} else {
			logger.info("Please provide URL to work with...");
		}
		
		InitAndStartThreads(url);
		
		printDBData();
		
		logger.info("Closing connections...");
		ContentDao.getInstance().teardown();
	}
	
	public static void InitAndStartThreads(String url) {
		Address addr = new Address();
		addr.setUrl_addr(url);
		addr.setUrl_id(UUID.randomUUID());
		ContentDao.getInstance().addUrl(addr);
		
		LinkedBlockingQueue<Chunk> queue = new LinkedBlockingQueue<>();
		Thread[] threads = {new Thread(new DataDownloader(addr, queue)),
				new Thread(new DataInserter(queue))};
		
		for (Thread t : threads) {
			t.start();
		}
		
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				logger.error("{}", e);
				System.exit(-1);
			}
		}
	}

	public static void printDBData() {
		ContentDao dao = ContentDao.getInstance();
		
		logger.info("Listing Databse informtion");
		logger.info("###########################");
		for (Address address : dao.getAllUrls()) {
			logger.info("URL: '{}', Chunks: '{}'", 
					address.getUrl_addr(), dao.getAllUrlChunks(address).size());
		}
	}
}
