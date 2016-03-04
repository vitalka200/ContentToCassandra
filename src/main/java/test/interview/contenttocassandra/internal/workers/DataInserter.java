package test.interview.contenttocassandra.internal.workers;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.interview.contenttocassandra.internal.data.Chunk;
import test.interview.contenttocassandra.internal.data.ContentDao;

public class DataInserter implements Runnable {
	
	protected static final Logger logger = LoggerFactory.getLogger(DataInserter.class);
	
	private LinkedBlockingQueue<Chunk> queue;
	
	public DataInserter(LinkedBlockingQueue<Chunk> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			do {
				Chunk chunk = queue.take();
				// If we see 'marker' chunk at the end of queue,
				// we finished downloading and there will no be more data.
				if (chunk.getChunk_seq_id() < 0) {
					break;
				}
				ContentDao.getInstance().addUrlChunk(chunk);
			} while (true);
			logger.info("Inserting data finished.");
		} catch (InterruptedException e) {
			logger.error("{}", e);
			System.exit(-1);
		}
		
	}
}
