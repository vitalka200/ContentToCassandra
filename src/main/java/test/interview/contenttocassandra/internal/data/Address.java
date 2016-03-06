package test.interview.contenttocassandra.internal.data;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.Objects;

@Table(name = "urls")
public class Address {
	
	@PartitionKey
	private UUID url_id;
	private String url_addr;
	public UUID getUrl_id() {
		return url_id;
	}
	public void setUrl_id(UUID url_id) {
		this.url_id = url_id;
	}
	public String getUrl_addr() {
		return url_addr;
	}
	public void setUrl_addr(String url_addr) {
		this.url_addr = url_addr;
	}
	@Override
	public String toString() {
		return "Address [url_id=" + url_id + ", url_addr=" + url_addr + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Address) {
			Address that = (Address)obj;
			return Objects.equal(this.getUrl_addr(), that.getUrl_addr());
		}
		return false;
	}
	
}
