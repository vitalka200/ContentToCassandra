package test.interview.contenttocassandra.internal.workers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.interview.contenttocassandra.internal.Config;
import test.interview.contenttocassandra.internal.data.Address;
import test.interview.contenttocassandra.internal.data.Chunk;

public class DataDownloader implements Runnable {
	protected static final Logger logger = LoggerFactory.getLogger(DataDownloader.class);
	
	private LinkedBlockingQueue<Chunk> queue;
	private Address addr;
	
	public DataDownloader(Address addr, LinkedBlockingQueue<Chunk> queue) {
		this.queue = queue;
		this.addr  = addr;
	}

	@Override
	public void run() {
		try {
			URL url = new URL(addr.getUrl_addr());
			BufferedInputStream is = new BufferedInputStream(url.openStream());
			int chunk_seq = 0;
			byte[] buffer = new byte[Config.MAX_CHUNK_SIZE];
			
			while((is.read(buffer, 0, Config.MAX_CHUNK_SIZE)) != -1) {
				Chunk chunk = new Chunk();
				chunk.setChunk_id(UUID.randomUUID());
				chunk.setChunk_seq_id(chunk_seq++);
				chunk.setUrl_id(addr.getUrl_id());
				chunk.setChunk_data(new String(buffer));
				
				queue.put(chunk);
			}
			Chunk marker = new Chunk();
			marker.setChunk_seq_id(Integer.MIN_VALUE);
			// We putting marker chunk in order to inform DataInserter
			// That we finished downloading, and there will no be more data.
			queue.put(marker);
			
			is.close();
			logger.info("Download finished.");
		} catch (IOException | InterruptedException e) {
			logger.error("{}", e);
			System.exit(-1);
		}
		
	}

}
