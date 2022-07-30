package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Array;
import org.hibernate.spatial.dialect.postgis.*;
import org.hibernate.spatial.GeolatteGeometryType;

/**
 * The persistent class for the area database table.
 * 
 */
@Entity
@NamedQuery(name="Area.findAll", query="SELECT a FROM Area a")
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name="created_at")
	private Timestamp createdAt;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Area() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Array getGeo() {
		return this.geo;
	}

	public void setGeo(Array geo) {
		this.geo = geo;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}