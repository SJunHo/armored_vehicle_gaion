package kr.gaion.armoredVehicle.common;

import com.google.common.io.ByteStreams;
import kr.gaion.armoredVehicle.spark.SparkConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class HdfsHelperService {
  @NonNull private final SparkConfig sparkConfig;
	@NonNull private final SparkSession spark;

  /**
	 * to delete file
	 */
	public void deleteIfExist(String fileNameFullPath) throws Exception {
		if (this.sparkConfig.isEnableHdfs()) {
			this.deleteFileIfExisted(fileNameFullPath);
		} else {
			try {
				FileUtils.forceDelete(new File(fileNameFullPath));
			} catch (FileNotFoundException e) {
				// file does not exist
				e.printStackTrace();
			}
		}
	}

	/**
	 * to clean directory but not the directory
	 */
	public void cleanDirectory(String directory) throws Exception {
		if (this.sparkConfig.isEnableHdfs()) {
			this.deleteFileIfExisted(directory);
		} else {
			FileUtils.cleanDirectory(new File(directory));
		}
	}

	/**
	 * to read data from file saved to HDFS
	 *
	 * @param fileNameFullPath
	 * @return
	 * @throws Exception
	 */
	public String readFileFromHdfs(String fileNameFullPath) throws Exception {

		Configuration hdfsConf = this.spark.sparkContext().hadoopConfiguration();

		Path path = new Path(fileNameFullPath);
		FileSystem fs = path.getFileSystem(hdfsConf);
		FSDataInputStream fis = fs.open(path);

		String fileContent = IOUtils.toString(fis, "UTF-8");
		fis.close();

		return fileContent;

	}

	/**
	 *
	 * @param fileNameFullPath
	 * @param os
	 * @throws Exception
	 */
	public void streamingLargeFile(String fileNameFullPath, OutputStream os) throws Exception {

		Configuration hdfsConf = this.spark.sparkContext().hadoopConfiguration();
		Path path = new Path(fileNameFullPath);
		FileSystem fs = path.getFileSystem(hdfsConf);

		BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(path), Charset.forName("UTF-8")));
		try {
			String line;
			line = br.readLine();
			while (line != null) {
				line += System.lineSeparator();
				os.write(line.getBytes());
				os.flush();
				// be sure to read the next line otherwise you'll get an infinite loop
				line = br.readLine();
			}
		} finally {
			// you should close out the BufferedReader
			br.close();
		}

	}

	/**
	 * to write byte array (data) to HDFS
	 *
	 * @param data
	 * @param path
	 * @throws Exception
	 */
	public void writeDataToHdfs(byte[] data, String path) throws Exception {
		Configuration hdfsConf = this.spark.sparkContext().hadoopConfiguration();
		FileSystem hdfs = FileSystem.get(hdfsConf);
		Path file = new Path(path);
		if (hdfs.exists(file)) {
			hdfs.delete(file, true); // set true to delete recursively (in case path is directory)
			log.info(String.format("Successfully deleted %s from HDFS", path));
		} else {
			// if path is file
			if (hdfs.isFile(file)) {
				log.info("Path is file.");
				Path parent = file.getParent();
				if (hdfs.exists(parent)) {
					log.info(String.format("Path %s already existed from HDFS", parent.toString()));
					// continue
				} else {
					hdfs.mkdirs(parent);
					log.info(String.format("Created path %s from HDFS", parent.toString()));
				}
			} else if (hdfs.isDirectory(file)) {
				log.info("Path is directory.");
				hdfs.mkdirs(file);
				log.info(String.format("Created path %s from HDFS", file.toString()));
			} else {
				// is there any case else?
			}
		}
		OutputStream os = hdfs.create(file);
		os.write(data);
		os.close();
		hdfs.close();
		log.info(String.format("Successfully created file %s from HDFS", path)); // #PC0017
	}

	/**
	 * to write data from an input stream to an output stream
	 *
	 * @param inputstream
	 * @param path
	 * @throws Exception
	 */
	public void writeDataToHdfs(InputStream inputstream, String path) throws Exception {
		Configuration hdfsConf = this.spark.sparkContext().hadoopConfiguration();
		FileSystem hdfs = FileSystem.get(hdfsConf);
		Path file = new Path(path);
		if (hdfs.exists(file)) {
			hdfs.delete(file, true); // set true to delete recursively (in case path is directory)
			log.info(String.format("Successfully deleted %s from HDFS", path));
		} else {
			// if path is file
			if (hdfs.isFile(file)) {
				log.info("Path is file.");
				Path parent = file.getParent();
				if (hdfs.exists(parent)) {
					log.info(String.format("Path %s already existed from HDFS", parent.toString()));
					// continue
				} else {
					hdfs.mkdirs(parent);
					log.info(String.format("Created path %s from HDFS", parent.toString()));
				}
			} else if (hdfs.isDirectory(file)) {
				log.info("Path is directory.");
				hdfs.mkdirs(file);
				log.info(String.format("Created path %s from HDFS", file.toString()));
			} else {
				// is there any case else?
			}
		}
		OutputStream os = hdfs.create(file);
		// IOUtils.copy(inputstream, os);
		ByteStreams.copy(inputstream, os);
		inputstream.close();
		os.close();
		hdfs.close();
		log.info(String.format("Successfully created file %s from HDFS", path)); // #PC0017
	}

	/**
	 * to delete existed file from HDFS
	 *
	 * @param fileFullPath
	 * @throws Exception
	 */
	public void deleteFileIfExisted(String fileFullPath) throws Exception {
		Configuration hdfsConf = this.spark.sparkContext().hadoopConfiguration();
		FileSystem hdfs = FileSystem.get(hdfsConf);
		Path file = new Path(fileFullPath);

		if (hdfs.exists(file)) {
			hdfs.delete(file, true); // set true to delete recursively (in case path is directory)
			log.info(String.format("Successfully deleted %s from HDFS", fileFullPath));
		}
		hdfs.close();
	}

	/**
	 * to get list of directories from specified path
	 *
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String[] getListDirectories(String path) throws Exception {
		Configuration hdfsConf = this.spark.sparkContext().hadoopConfiguration();
		FileSystem hdfs = FileSystem.get(hdfsConf);
		Path file = new Path(path);
		FileStatus[] fileStatus = hdfs.listStatus(file);
		if (fileStatus.length > 0) {
			List<String> listDirectories = new ArrayList<>();
			for (FileStatus status : fileStatus) {
				if (status.isDirectory()) {
					listDirectories.add(status.getPath().getName());
				}
			}
			String[] retVal = new String[listDirectories.size()];
			return listDirectories.toArray(retVal);
		} else {
			return new String[0];
		}

	}

	/**
	 * to get list of files under specified directory
	 *
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	public String[] getListFilesInDir(String dir) throws Exception {
		Configuration hdfsConf = this.spark.sparkContext().hadoopConfiguration();
		FileSystem hdfs = FileSystem.get(hdfsConf);
		Path file = new Path(dir);
		FileStatus[] fileStatus = hdfs.listStatus(file);
		if (fileStatus.length > 0) {
			List<String> listFIles = new ArrayList<>();
			for (FileStatus status : fileStatus) {
				if (status.isFile()) {
					listFIles.add(status.getPath().getName());
				}
			}
			String[] retVal = new String[listFIles.size()];
			return listFIles.toArray(retVal);
		} else {
			return new String[0];
		}
	}

	/**
	 * to get list of files (full path) under specified directory
	 *
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	public String[] getListFilesFullPathInDir(String dir) throws Exception {
		Configuration hdfsConf = this.spark.sparkContext().hadoopConfiguration();
		FileSystem hdfs = FileSystem.get(hdfsConf);
		Path file = new Path(dir);
		FileStatus[] fileStatus = hdfs.listStatus(file);
		if (fileStatus.length > 0) {
			List<String> listFIles = new ArrayList<>();
			for (FileStatus status : fileStatus) {
				if (status.isFile()) {
					listFIles.add(status.getPath().toString());
				}
			}
			String[] retVal = new String[listFIles.size()];
			return listFIles.toArray(retVal);
		} else {
			return new String[0];
		}
	}

	/**
	 * to copy a file from local to HDFS
	 *
	 * @param localPath
	 * @param hdfsPath
	 * @throws Exception
	 */
	public void copyFileFromLocalToHdfs(String localPath, String hdfsPath) throws Exception {
		Configuration hdfsConf = this.spark.sparkContext().hadoopConfiguration();
		FileSystem hdfs = FileSystem.get(hdfsConf);
		Path src = new Path(localPath);
		Path dst = new Path(hdfsPath);
		hdfs.copyFromLocalFile(src, dst);
	}

	/**
	 * to copy a file from HDFS to local
	 *
	 * @param localPath
	 * @param hdfsPath
	 * @throws Exception
	 */
	public void copyFileFromHdfsToLocal(String hdfsPath, String localPath) throws Exception {
		Configuration hdfsConf = this.spark.sparkContext().hadoopConfiguration();
		FileSystem hdfs = FileSystem.get(hdfsConf);
		Path src = new Path(hdfsPath);
		Path dst = new Path(localPath);
		hdfs.copyToLocalFile(src, dst);
	}
}
