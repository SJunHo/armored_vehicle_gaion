package kr.gaion.railroad2.common;

import kr.gaion.railroad2.dataset.config.StorageConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Utilities {
	@NonNull private final StorageConfig storageConfig;

	/**
	 * To make URL for reading-writing to Elastic-search
	 */
	public String getURL(String index, String type) {
		return index +
				"/" +
				type;
	}

	/**
	 * To convert all field values from an object to a list of values
	 *
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> convertAllFieldsToList(Object obj) {
		ArrayList<T> list = new ArrayList<>();
		Field[] fields = obj.getClass().getDeclaredFields();

		for (Field f : fields) {
			try {
				list.add((T) f.get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * To get list of sub directories in specified directory
	 *
	 * @param dir
	 * @return
	 */
	public String[] getListFoldersInDir(String dir) {
		File file = new File(dir);
		return file.list((current, name) -> new File(current, name).isDirectory());
	}

	/**
	 * To get list of files in specified directory
	 *
	 * @param dir
	 * @return
	 */
	public String[] getListFilesInDir(String dir) {
		File file = new File(dir);
		return file.list((current, name) -> new File(current, name).isFile());
	}

	/**
	 * to make file path
	 */
	public String getPathInWorkingFolder(String... strings) throws IOException {

		String homeDir = this.storageConfig.getHomeDir();
		if (homeDir == null) {
			throw new IOException("The environment home directory (ETRI_RRS_HOME) is not yet set.");
		}
		return Paths.get(homeDir, strings).toString();
	}

	/**
	 * to create temple File
	 *
	 * @return
	 * @throws IOException
	 */
	public File makeTempleFile() throws IOException {
		String rootPath = this.getProjTmpFolder();
		File dir = new File(rootPath);

		if (!dir.exists())
			dir.mkdirs();

		// Create the temple file on Spark server
		File tmp = File.createTempFile("file_", ".tmp", dir);
		return tmp;
	}

	/**
	 * to create temple File
	 */
	public File makeTempleFile(String filename) throws IOException {
		String rootPath = this.getProjTmpFolder();
		File dir = new File(rootPath);

		if (!dir.exists())
					dir.mkdirs();

		// Create the temple file on Spark server
		return File.createTempFile(filename + "_", ".tmp", dir);
	}

	/**
	 * to round the double number
	 *
	 * @param value
	 * @param places
	 * @return
	 */
	public double roundDouble(Object value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		double d = 0.0;
		try {
			d = (double) value;
			if (Double.isNaN(d)) {
				d = 0.0;
			}
		} catch (Exception e) {
			d = 0.0;
		}
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * to delete file or directory if it already existed
	 */
	public void deleteFileOrDirIfExisted(String path) throws IOException {
		File fileOrDir = new File(path);
		if (fileOrDir.exists()) {
			if (fileOrDir.isFile()) {
				FileUtils.forceDelete(fileOrDir);
			} else if (fileOrDir.isDirectory()) {
				FileUtils.deleteDirectory(fileOrDir);
			}
		}
	}

	/**
     * to get temple folder for current project
	 */
	public String getProjTmpFolder() throws IOException {
		return getPathInWorkingFolder(this.storageConfig.getDataDir(), "tmp");
	}
}