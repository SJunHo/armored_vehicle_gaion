package kr.gaion.railroad2.algorithm.featureSelector;

import kr.gaion.railroad2.algorithm.dto.ResponseStatus;
import kr.gaion.railroad2.algorithm.dto.ResponseType;
import kr.gaion.railroad2.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.railroad2.algorithm.dto.response.FSResponse;
import kr.gaion.railroad2.spark.ElasticsearchSparkService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.feature.ChiSqSelector;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FSChiSqSelector {
  @NonNull
  private final ElasticsearchSparkService elasticsearchSparkService;

  /**
   * to train with DataFrame API
   */
  public FSResponse trainWithDataFrameApi(BaseAlgorithmTrainInput config) {

    // Get setting values
    String classCol = config.getClassCol();

    String[] selectedFeatures = selectFeaturesDataframeApi(config);

    // Load data from Elasticsearch
    Dataset<Row> originalData = this.elasticsearchSparkService.getDatasetFromElasticsearch();
    originalData.cache();                                                          // #PC0032
    Dataset<Row> filteredData = originalData.select(classCol, selectedFeatures);

    FSResponse response = new FSResponse(ResponseType.OBJECT_DATA);
    response.setFilteredFeatures(filteredData.takeAsList(100).stream().map(row -> row.mkString(",")).collect(Collectors.toList()));
    response.setSelectedFields(Arrays.asList(selectedFeatures));
    response.setCsv(filteredData.collectAsList().stream().map(row -> row.mkString(",")).collect(Collectors.joining("\n")));
    response.setClassCol(classCol);
    response.setStatus(ResponseStatus.SUCCESS);

    return response;
  }

  private static JavaRDD<String> doFilterData(Dataset<Row> originalData, String classCol, String[] selectedFeatures) {
    return originalData.select(classCol, selectedFeatures).toJavaRDD().map(new Function<>() {
      private static final long serialVersionUID = 6171871082204044187L;

      @Override
      public String call(Row row) {
        StringBuilder dataBuilder = new StringBuilder();
        dataBuilder.append('"').append(row.get(0)).append('"');
        dataBuilder.append(",");
        for (int index = 1; index <= selectedFeatures.length; ++index) {
          double value = 0.0;
          try {
            value = Double.parseDouble(row.getString(index));
          } catch (Exception e) {
            value = 0.0;
          }
          dataBuilder.append(value);
          dataBuilder.append(",");
        }
        dataBuilder.deleteCharAt(dataBuilder.length() - 1);
        return dataBuilder.toString();
      }

    });
  }

  public String[] selectFeaturesDataframeApi(BaseAlgorithmTrainInput config) {
    // get data from elasticsearch
    Dataset<Row> originalData = this.elasticsearchSparkService.getLabeledDatasetFromElasticsearch(config);                        // #PC0023
    // Using StringIndexer 																													// #PC0026
    StringIndexerModel labelIndexer = new StringIndexer()                                          // #PC0026
        .setInputCol("label")                                        // #PC0026
        .setOutputCol("index")                                        // #PC0026
        .fit(originalData);                                          // #PC0026
    Dataset<Row> indexedData = labelIndexer.transform(originalData);                                    // #PC0026
    indexedData.cache();                                                          // #PC0032

    var selector = new ChiSqSelector().setNumTopFeatures(8).setOutputCol("selectedFeatures").setLabelCol("index");
    var model = selector.fit(indexedData);
    return Arrays.stream(model.selectedFeatures())
        .mapToObj(fi -> config.getFeatureCols().get(fi))
        .collect(Collectors.toList())
        .toArray(new String[]{});
  }
}