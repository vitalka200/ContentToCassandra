package test.interview.contenttocassandra.internal.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

import test.interview.contenttocassandra.internal.Config;  

public class ContentDao {
	protected static final Logger logger = LoggerFactory.getLogger(ContentDao.class);
	private static ContentDao INSTANCE;
	private static MappingManager manager;
	private static Session session;
	private static Cluster cluster;
	private static ContentAccessor accessor;
	
	protected ContentDao() {
		if (INSTANCE != null) {
			logger.error("Already instantiated");
			throw new IllegalStateException("Already instantiated");
		}
	}
	
	public static ContentDao getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new ContentDao();
		}
		if (cluster == null || cluster.isClosed()) {
			setupConectionEnv();
		}
		return INSTANCE;
	}
	
	private static void setupConectionEnv() {
		logger.debug("Creating new Cluster connection...");
		cluster = Cluster.builder()
		         .addContactPoint(Config.SERVER_NAME)
		         .build();
		session = cluster.connect(Config.CAS_KEYSPACE);
		manager = new MappingManager(session);
		accessor = manager.createAccessor(ContentAccessor.class);
		logger.debug("Cluster connection created.");
	}
	
	public void teardown() {
		logger.debug("Tearing down connection.");
		cluster.close();
		logger.debug("Tearingdown completed.");
	}
	
	public List<Chunk> getAllUrlChunks(Address addr) {
		return accessor.getAllUrlChunks(addr.getUrl_id()).all();
	}
	
	public void addUrlChunk(Chunk chunk) {
		accessor.addChunk(chunk.getChunk_id(), chunk.getUrl_id(),
				chunk.getChunk_seq_id(), chunk.getChunk_data());
	}
	
	public void addUrl(Address addr) {
		Address existingAddr = getAddrByUrl(addr.getUrl_addr());
		if (!addr.equals(existingAddr))
			accessor.addUrl(addr.getUrl_id(), addr.getUrl_addr());
	}

	public Address getAddrByUrl(String url_addr) {
		return accessor.getUrlByAddr(url_addr).one();
	}
	
	public Chunk getUrlChunkBySeqId(Address addr, int seq_num) {
		return accessor.getUrlChunkByIndex(addr.getUrl_id(), seq_num).one();
	}
	
	public List<Address> getAllUrls() {
		return accessor.getAllUrl().all();
	}
}
