package test.interview.contenttocassandra.internal.data;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "chunks")
public class Chunk {
	
	@PartitionKey
	private UUID chunk_id;
	private UUID url_id;
	private int chunk_seq_id;
	private String chunk_data;
	
	public UUID getChunk_id() {
		return chunk_id;
	}
	public void setChunk_id(UUID chunk_id) {
		this.chunk_id = chunk_id;
	}
	public UUID getUrl_id() {
		return url_id;
	}
	public void setUrl_id(UUID url_id) {
		this.url_id = url_id;
	}
	public int getChunk_seq_id() {
		return chunk_seq_id;
	}
	public void setChunk_seq_id(int chunk_seq_id) {
		this.chunk_seq_id = chunk_seq_id;
	}
	public String getChunk_data() {
		return chunk_data;
	}
	public void setChunk_data(String chunk_data) {
		this.chunk_data = chunk_data;
	}
	@Override
	public String toString() {
		return "Chunk [chunk_id=" + chunk_id + ", url_id=" + url_id + ", chunk_seq_id=" + chunk_seq_id + ", chunk_data="
				+ chunk_data + "]";
	}
	
	
}
