package kr.gaion.armoredVehicle.algorithm;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kr.gaion.armoredVehicle.algorithm.dto.PredictionInfo;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.AlgorithmResponse;
import kr.gaion.armoredVehicle.common.HdfsHelperService;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.dataset.service.FileSystemStorageService;
//import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
import kr.gaion.armoredVehicle.spark.SparkConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class ModelUtilService {
	@NonNull private final SparkConfig sparkConfig;
	@NonNull private final Utilities utilities;
	@NonNull private final StorageConfig storageConfig;
	@NonNull public final HdfsHelperService hdfsHelperService;
//	@NonNull private final EsConnector esConnector;
	@NonNull private final FileSystemStorageService fileSystemStorageService;

	private final Gson gson = new Gson();

	public boolean checkFile(String filePath) {
		try {
			FileUtils.touch(new File(filePath));
		} catch (IOException e) {
			return false;
			// TODO: handle exception
		}
		return true;
	}

	public String savePredictedInfoToCSV(List<PredictionInfo<?, ?>> listPredictionInfo, String fileNameWithoutExtension) throws IOException {
		StringBuilder dataBuilder = new StringBuilder();
		StringBuilder jsonElementBuilder = null;
		String lineSeparator = System.lineSeparator();
		String csvSeparator = ",";
		String fileName = fileNameWithoutExtension + ".csv";
		String fileOutput = this.utilities.getPathInWorkingFolder(this.storageConfig.getDataDir(), fileName);
		JsonObject jsonObj;

		for (PredictionInfo<?, ?> predictInfo : listPredictionInfo) {
			dataBuilder.append(predictInfo.getPredictedValue());
			dataBuilder.append(csvSeparator);
			jsonObj = gson.fromJson(predictInfo.getFeatures().toString(), JsonObject.class);
			JsonElement values = jsonObj.get("values");
			// to remove square brackets [] at index 0 and length of string
			jsonElementBuilder = new StringBuilder(values.toString());
			jsonElementBuilder.deleteCharAt(0);
			jsonElementBuilder.deleteCharAt(jsonElementBuilder.length() - 1);
			dataBuilder.append(jsonElementBuilder);
			dataBuilder.append(lineSeparator);
		}

		if (checkFile(fileOutput)) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput));
			writer.write(dataBuilder.toString());
			writer.close();
		} else {
			System.out.println("Target file is existed and currently locked");
			return null;
		}

		return fileOutput;
	}

	/**
	 * to delete model if existed
	 * @param path
	 */
	public void deleteModelIfExisted(String path) throws Exception {
		if (sparkConfig.isEnableHdfs()) {
			this.hdfsHelperService.deleteFileIfExisted(path);
		}else {
			this.utilities.deleteFileOrDirIfExisted(path);
		}
	}

	public void saveTransformedData(Dataset<Row> df, String algorithmName, String modelName, String action) throws IOException {
		// save to default directory
		String hdfsFileNameFullPath = this.utilities.getPathInWorkingFolder(
				this.storageConfig.getDataDir(), algorithmName, modelName, action);
		df.coalesce(1).write().format("com.databricks.spark.csv").option("header", "true").mode(SaveMode.Overwrite)
				.save(hdfsFileNameFullPath);
	}

	public void saveTrainedResults(BaseAlgorithmTrainInput config, AlgorithmResponse response, String algorithmName) throws Exception {
		String jsonRes = gson.toJson(response);
		String fileNameFullPath = Paths
				.get(this.storageConfig.getDataDir(), "trained-results", algorithmName, config.getModelName(), ".json")
				.toString();
		this.fileSystemStorageService.saveFile(fileNameFullPath, jsonRes.getBytes());
	}
}