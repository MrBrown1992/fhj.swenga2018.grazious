package at.fh.swenga.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

@Entity
@Table(name = "photo")
public class PhotoModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 8098173157518993615L;
	
	@Id
	@Column(name = "photoId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer photoId;
	
	@Column(name = "filename", nullable = false)
	private String filename;

	public PhotoModel() {
		super();
	}

	public PhotoModel(String filename) {
		super();
		this.filename = filename;
	}

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}