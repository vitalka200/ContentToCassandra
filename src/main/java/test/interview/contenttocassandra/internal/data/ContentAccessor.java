package test.interview.contenttocassandra.internal.data;

import java.util.UUID;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;  

@Accessor
public interface ContentAccessor {

	@Query("SELECT * FROM chunks WHERE url_id=:uuid AND chunk_seq_id=:seq_num ALLOW FILTERING")
	public Result<Chunk> getUrlChunkByIndex(@Param("uuid") UUID url_uuid, @Param("seq_num") int seq_num);
	
	@Query("SELECT * FROM chunks WHERE url_id=:uuid ALLOW FILTERING")
	public Result<Chunk> getAllUrlChunks(@Param("uuid") UUID url_uuid);
	
	@Query("INSERT INTO urls (url_id, url_addr) VALUES(:uuid , :url)")
	public void addUrl(@Param("uuid") UUID uuid, @Param("url") String url);
	
	@Query("INSERT INTO chunks (chunk_id , url_id , chunk_seq_id , chunk_data) "
			+ "VALUES(:c_id, :u_id, :c_s_id, :c_data)")
	public void addChunk(@Param("c_id")UUID chunk_id, @Param("u_id")UUID url_id,
			@Param("c_s_id")int chunk_seq_id, @Param("c_data")String chunk_data);
	
	@Query("SELECT * FROM urls WHERE url_id=:uuid ALLOW FILTERING")
	public Result<Address> getUrlById(@Param("uuid")UUID url_uuid);
	
	@Query("SELECT * FROM urls WHERE url_addr=:addr ALLOW FILTERING")
	public Result<Address> getUrlByAddr(@Param("addr")String url_addr);
	
	@Query("SELECT * FROM chunks WHERE chunk_id=:c_id ALLOW FILTERING")
	public Result<Address> getChunkById(@Param("c_id")UUID chunk_id);
	
	@Query("SELECT * FROM urls")
	public Result<Address> getAllUrl();
	
}
