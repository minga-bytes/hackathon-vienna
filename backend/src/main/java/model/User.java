package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	
	public User(Integer id, Timestamp createdAt, String username, List<Area> areas) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.username = username;
		this.areas = areas;
	}

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name="created_at")
	private Timestamp createdAt;

	private String username;

	//bi-directional many-to-one association to Area
	@OneToMany(mappedBy="user")
	private List<Area> areas;

	public User() {
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

	public String get() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Area> getAreas() {
		return this.areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public Area addArea(Area area) {
		getAreas().add(area);
		area.setUser(this);

		return area;
	}

	public Area removeArea(Area area) {
		getAreas().remove(area);
		area.setUser(null);

		return area;
	}

}