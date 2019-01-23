package easyorderappdesktop.transferObject;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Imanol
 */
public class MyFtpFile {

	private String name;
	private String path;
	private boolean directory;

	public MyFtpFile() {
	}

	public MyFtpFile(String name, String path, boolean isDirectory) {
		this.name = name;
		this.path = path;
		this.directory = isDirectory;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public boolean isDirectory() {
		return directory;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	public String getAbsolutePath() {
		String absolutePath = null;
		final String root = "/";

		if(path.equals(root)){
			absolutePath = path+name;
		} else {
			absolutePath=path+"/"+name;
		}

		Logger.getLogger("easyorderappclient").log(Level.INFO,"MyFtpFile: Absolute path={0}",absolutePath);

		return absolutePath;
	}

	/**
	 * HashCode method implementation for the entity.
	 *
	 * @return An integer value as hashcode for the object.
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (name != null ? name.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MyFtpFile other = (MyFtpFile) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
