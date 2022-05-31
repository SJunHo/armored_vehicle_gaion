package kr.gaion.armoredVehicle.algorithm.featureSelector;

import kr.gaion.armoredVehicle.algorithm.AlgorithmConfig;
import kr.gaion.armoredVehicle.algorithm.MLAlgorithm;
import kr.gaion.armoredVehicle.algorithm.ModelUtilService;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseStatus;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.AlgorithmResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.ClusterResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.FSResponse;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import kr.gaion.armoredVehicle.spark.ElasticsearchSparkService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.feature.PCA;
import org.apache.spark.ml.feature.PCAModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.elasticsearch.common.util.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Log4j
public class PcaDimensionalityReduction extends MLAlgorithm<BaseAlgorithmTrainInput, BaseAlgorithmPredictInput> {

  public PcaDimensionalityReduction(@NonNull ElasticsearchSparkService elasticsearchSparkService, @NonNull Utilities utilities, @NonNull StorageConfig storageConfig, @NonNull ModelUtilService modelUtil, @NonNull EsConnector esConnector, @NonNull FSChiSqSelector chiSqSelector, @NonNull AlgorithmConfig algorithmConfig, @NonNull DataConfig dataConfig, @NonNull SparkSession sparkSession, @NonNull ModelService modelService) {
    super(elasticsearchSparkService, utilities, storageConfig, modelUtil, esConnector, chiSqSelector, algorithmConfig, dataConfig, sparkSession, "PcaDimensionalityReduction", modelService);
  }

  public Dataset<Row> computePcaDataframeApi(BaseAlgorithmTrainInput config) throws Exception {
    // get setting values
    int numPrincipalComponents = config.getNumberPrincipalComponents();
    String[] featureCols = config.getFeatureCols().toArray(new String[0]);
    if (numPrincipalComponents <= 0) {
      numPrincipalComponents = 2;
    } else if (numPrincipalComponents > featureCols.length) {
      numPrincipalComponents = featureCols.length;
    }

    // get input data
    Dataset<Row> inputData = this.elasticsearchSparkService.getLabeledDatasetFromElasticsearch(config);

    var pca = train(numPrincipalComponents, inputData);
    return pca.transform(inputData);
  }

  private static PCAModel train(int numPrincipalComponents, Dataset<Row> inputData) {
    // transformation
    return new PCA().setInputCol("features").setOutputCol(ClusterResponse.PCA_FEATURES).setK(numPrincipalComponents).fit(inputData);
  }

  public static Dataset<Row> computePcaDataframeApiFromDF(BaseAlgorithmInput config, Dataset<Row> prepInputDF) throws Exception {
    // get setting values
    int numPrincipalComponents = config.getNumberPrincipalComponents();
    String[] featureCols = config.getFeatureCols().toArray(new String[0]);

    if (numPrincipalComponents <= 0) {
      numPrincipalComponents = 2;
    } else if (numPrincipalComponents > featureCols.length) {
      numPrincipalComponents = featureCols.length;
    }

    // transformation
    PCAModel pca = new PCA().setInputCol("features")
        .setOutputCol(ClusterResponse.PCA_FEATURES)
        .setK(numPrincipalComponents)
        .fit(prepInputDF);

    Dataset<Row> result;
    if (Arrays.asList(prepInputDF.columns()).contains(ClusterResponse.TAG_COLUMN)) {
      result = pca.transform(prepInputDF)
          .select(ClusterResponse.ID_COLUMN,
              ClusterResponse.PCA_FEATURES, ClusterResponse.FEATURES,
              ClusterResponse.TAG_COLUMN);
    } else {
      result = pca.transform(prepInputDF)
          .select(ClusterResponse.ID_COLUMN, ClusterResponse.PCA_FEATURES, ClusterResponse.FEATURES);
    }

    return result;
  }

  private static JavaRDD<String> reduceData(Dataset<Row> data, String csvDelimiter) {
    return data.select(ClusterResponse.ID_COLUMN, ClusterResponse.PCA_FEATURES).toJavaRDD().map(new Function<>() {
      private static final long serialVersionUID = -8196827940842158326L;

      @Override
      public String call(Row features) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append('"').append(features.getAs(0).toString()).append('"');  // add id-column value
        strBuilder.append(csvDelimiter);
        org.apache.spark.ml.linalg.Vector vector = (org.apache.spark.ml.linalg.Vector) (features.get(1));
        String strVector = vector.toString();
        strBuilder.append(strVector, 1, strVector.length() - 1);  // remove Brackets

        return strBuilder.toString();
      }
    });

  }

  private static JavaRDD<Row> getDataToSavingToFile(Dataset<Row> data, String[] header) {
    return data.select(ClusterResponse.ID_COLUMN, ClusterResponse.PCA_FEATURES).toJavaRDD().map(new Function<>() {
      private static final long serialVersionUID = -4715890591062606256L;

      @Override
      public Row call(Row features) {
        Object[] objArr = new Object[header.length];
        int objArrIter = -1;
        objArr[++objArrIter] = features.getAs(0).toString();
        org.apache.spark.ml.linalg.Vector vector = (org.apache.spark.ml.linalg.Vector) (features.get(1));
        for (double d : vector.toArray()) {
          objArr[++objArrIter] = Double.toString(d);
        }

        return RowFactory.create(objArr);
      }
    });
  }

  public FSResponse train(BaseAlgorithmTrainInput config) throws Exception {
    log.info("Start PcaDimensionalityReduction");
    Dataset<Row> data = this.computePcaDataframeApi(config);
    var selectedFields = Arrays.stream(data.schema().fields()).map(StructField::name).collect(Collectors.toList());
    var csvDelimiter = this.storageConfig.getCsvDelimiter();

    // map data to return
    JavaRDD<String> reducedData = reduceData(data, csvDelimiter);

    // save transformed data to .CSV file
    int numPrincipalComponents = config.getNumberPrincipalComponents();
    String[] featureCols = new String[numPrincipalComponents];
    for (int index = 0; index < numPrincipalComponents; ++index) {
      featureCols[index] = "F" + (index + 1);
    }
    String[] header = ArrayUtils.concat(new String[] { ClusterResponse.ID_COLUMN }, featureCols);
    // make DataFrame for transformed data
    JavaRDD<Row> dataForSavingToFile = getDataToSavingToFile(data, header);
    StructType st = new StructType();
    for (String col: header) {
      st = st.add(col, DataTypes.StringType);
    }
    Dataset<Row> resultDf = this.sparkSession.createDataFrame(dataForSavingToFile, st);
    // save transformed results to CSV file
    this.saveTransformedData("DefaultModel", "train", resultDf);

    FSResponse response = new FSResponse(ResponseType.OBJECT_DATA);
    response.setNumPrincipalComponents(numPrincipalComponents);
    int maxResults = this.algorithmConfig.getMaxResult();
    response.setFilteredFeatures(reducedData.take(maxResults));
    response.setSelectedFields(selectedFields);
    response.setIdCol("idCol");
    response.setClassCol(config.getClassCol());

    response.setStatus(ResponseStatus.SUCCESS);
    log.info("Completed PcaDimensionalityReduction.");

    return response;
  }

  @Override
  public AlgorithmResponse predict(BaseAlgorithmPredictInput input) throws Exception {
    return null;
  }
}
