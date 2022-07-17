import React, { useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import Row from "react-bootstrap/Row";
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import { chunk, range, sum } from "lodash";
import styles from "./styles.module.css";
import {LinearRegressionTrainResponse, RandomForestClassificationResponse} from "../api/gen";
import { useParams } from "react-router-dom";
import { Table } from "../common/Table";
import { Column } from "react-table";
import { FlexibleWidthXYPlot, MarkSeries, XAxis, YAxis } from "react-vis";
import Select2 from "react-select";
import { colorPalette2 } from "../Dashboard/Dashboard";
import {LinearRegression} from "./CreateModelSection";

type Props = {
  result: RandomForestClassificationResponse;
  result2? : LinearRegressionTrainResponse;
  algorithmName: string;
};

export const CreateModelResult: React.FC<Props> = ({
  result,
  result2,
  algorithmName
}) => {
  const {
    accuracy,
    weightedFMeasure,
    weightedFalsePositiveRate,
    weightedPrecision,
    weightedRecall,
    weightedTruePositiveRate,
  } = result;
  const { t } = useTranslation();

  console.log(result2)
  return (
    <Card className="mt-3">
      <Card.Header>
        <strong>{t("ml.common.mei")}</strong>
      </Card.Header>
      <Card.Body className="d-grid gap-3 container-fluid">
        {
          algorithmName === "linear" || algorithmName === "lasso" ? (
            <>
              <Row>
                <div className="col-lg-6">
                  <Card>
                    <Card.Header>
                      <strong>{t("ml.common.r2")}</strong>
                    </Card.Header>
                    <Card.Body>
                      <h1 className={styles.center}>{result2?.r2}</h1>
                    </Card.Body>
                  </Card>
                </div>
                <div className="col-lg-6">
                  <Card>
                    <Card.Header>
                      <strong>{t("ml.common.rmse")}</strong>
                    </Card.Header>
                    <Card.Body>
                      <h1 className={styles.center}>{result2?.rootMeanSquaredError}</h1>
                    </Card.Body>
                  </Card>
                </div>
              </Row>
            </>)
            : algorithmName !== "kmean" && algorithmName !== "if" ? (
              <>
              <Row>
                <div className="col-lg-4">
                  <Card>
                    <Card.Header>
                      <strong>{t("ml.common.accuracy")}</strong>
                    </Card.Header>
                    <Card.Body>
                      <h1 className={styles.center}>{accuracy}</h1>
                    </Card.Body>
                  </Card>
                </div>
                <div className="col-lg-4">
                  <Card>
                    <Card.Header>
                      <strong>{t("ml.common.precision")}</strong>
                    </Card.Header>
                    <Card.Body>
                      <h1 className={styles.center}>{weightedPrecision}</h1>
                    </Card.Body>
                  </Card>
                </div>
                <div className="col-lg-4">
                  <Card>
                    <Card.Header>
                      <strong>{t("ml.common.recall")}</strong>
                    </Card.Header>
                    <Card.Body>
                      <h1 className={styles.center}>{weightedRecall}</h1>
                    </Card.Body>
                  </Card>
                </div>
              </Row>
              <Row>
                <div className="col-lg-4">
                  <Card>
                    <Card.Header>
                      <strong>{t("ml.common.fsc")}</strong>
                    </Card.Header>
                    <Card.Body>
                      <h1 className={styles.center}>{weightedFMeasure}</h1>
                    </Card.Body>
                  </Card>
                </div>
                <div className="col-lg-4">
                  <Card>
                    <Card.Header>
                      <strong>{t("ml.common.fpr")}</strong>
                    </Card.Header>
                    <Card.Body>
                      <h1 className={styles.center}>
                        {weightedFalsePositiveRate}
                      </h1>
                    </Card.Body>
                  </Card>
                </div>
                <div className="col-lg-4">
                  <Card>
                    <Card.Header>
                      <strong>{t("ml.common.tpr")}</strong>
                    </Card.Header>
                    <Card.Body>
                      <h1 className={styles.center}>
                        {weightedTruePositiveRate}
                      </h1>
                    </Card.Body>
                  </Card>
                </div>
              </Row>
            </>)
            : null
        }

        {(algorithmName === "kmean" || algorithmName === "if") && (
          <ClusterDiagram algorithmName={algorithmName} result={result} result2={result2} />
        )}
        <ClassificationResult algorithmName={algorithmName} result={result} result2={result2}/>
      </Card.Body>
    </Card>
  );
};

export const ClusterDiagram: React.FC<Props> = ({
  result: { predictionInfo, listFeatures },
  algorithmName,result2
}) => {
  const [selectedXAxis, setSelectedXAxis] = useState<number>();
  const [selectedYAxis, setSelectedYAxis] = useState<number>();
  const data = useMemo<any[][]>(
    () =>
      (predictionInfo || []).map((actual) => JSON.parse("[" + actual + "]")),
    [predictionInfo]
  );

  return (
    <Card>
      <Card.Header>
        <strong>Clusters</strong>
      </Card.Header>
      <Card.Body>
        <Container fluid>
          <div className="d-flex flex-row-reverse">
            <Select2
              className={styles.axisSelector}
              value={
                selectedYAxis !== undefined
                  ? {
                      label: (listFeatures || [])[selectedYAxis],
                      value: selectedYAxis,
                    }
                  : undefined
              }
              onChange={(v) => setSelectedYAxis(v?.value)}
              options={(listFeatures || []).map((f, i) => ({
                label: f,
                value: i,
              }))}
            />
            <div>YAxis</div>
            <Select2
              className={styles.axisSelector}
              value={
                selectedXAxis !== undefined
                  ? {
                      label: (listFeatures || [])[selectedXAxis],
                      value: selectedXAxis,
                    }
                  : undefined
              }
              options={(listFeatures || []).map((f, i) => ({
                label: f,
                value: i,
              }))}
              onChange={(v) => setSelectedXAxis(v?.value)}
            />
            <div>XAxis</div>
          </div>
          <FlexibleWidthXYPlot height={300}>
            <XAxis />
            <YAxis />
            <MarkSeries
              colorType="literal"
              data={
                selectedXAxis !== undefined && selectedYAxis !== undefined
                  ? data.map((item) => ({
                      x: item[selectedXAxis],
                      y: item[selectedYAxis],
                      color:
                        colorPalette2[
                          algorithmName === "kmean" ? item[1] : item[0]
                        ],
                    }))
                  : []
              }
            />
          </FlexibleWidthXYPlot>
        </Container>
      </Card.Body>
    </Card>
  );
};

export const ClassificationResult: React.FC<Props> = ({ result,result2 }) => {
  const {
    confusionMatrix = [],
    labels = [],
    predictedActualFeatureLine: resultPredictedActualFeatureLine,
    predictionInfo,
  } = result;
  const predictedActualFeatureLine =
    resultPredictedActualFeatureLine || predictionInfo;
  const matrixSize = Math.sqrt(confusionMatrix?.length || 0);

  const countByLabels = chunk(confusionMatrix, matrixSize).map((c) => sum(c));

  const { algorithmName } = useParams<{ algorithmName: string }>();
  const { t } = useTranslation();
  return (
    <Card>
      <Card.Header>
        <strong>{t("ml.common.cr")}</strong>
      </Card.Header>
      <Card.Body>
        <Container fluid>
          {algorithmName === "linear" || algorithmName === 'lasso' ?  (
            <Row></Row>
            )
            : algorithmName !== "kmean" && algorithmName !== "if" ?  (
              <Row>
              <h4 className={styles.center}>{t("ml.common.cm")}</h4>
              <div className="table-responsive">
                <table className="table table-bordered table-hover table-striped">
                  <thead className={styles.textCenter}>
                  <tr className="table-info">
                    <th
                      id="commonCell"
                      colSpan={2}
                      className="col-md-3"
                      rowSpan={matrixSize}
                    />
                    <th
                      id="actualCell"
                      colSpan={matrixSize}
                      className={styles.textCenter}
                    >
                      {t("ml.common.ac")}
                    </th>
                  </tr>
                  <tr
                    id="actualLabels"
                    className={`table-info ${styles.textCenter}`}
                  >
                    {range(0, matrixSize).map((i) => (
                      <th>
                        {labels[i]} ({countByLabels[i]})
                      </th>
                    ))}
                  </tr>
                  </thead>
                  <tbody id="tableBody">
                  <tr id="firstRow">
                    <th
                      className={`table-info ${styles.predictCell}`}
                      rowSpan={matrixSize + 1}
                    >
                      {t("ml.common.pc")}
                    </th>
                  </tr>
                  {range(0, matrixSize).map((i) => (
                    <tr>
                      <td className="table-info">{labels[i]}</td>
                      {range(0, matrixSize).map((j) => (
                        <td className={i === j ? "table-success" : ""}>
                          {confusionMatrix[i * matrixSize + j]}
                        </td>
                      ))}
                    </tr>
                  ))}
                  </tbody>
                </table>
              </div>
            </Row>)
          : null}
          <br />
          {predictedActualFeatureLine && (
            <PredictionInfoSection
              predictionInfo={predictedActualFeatureLine}
              featureCols={result.listFeatures || []}
            />
          )}
        </Container>
      </Card.Body>
      <Card.Footer />
    </Card>
  );
};

export const PredictionInfoSection: React.FC<{
  predictionInfo: string[];
  featureCols: string[];
}> = ({ predictionInfo, featureCols }) => {
  const { algorithmName } = useParams<{ algorithmName: string }>();
  const { t } = useTranslation();
  const columns = useMemo<Column<any[]>[]>(
    () => [
      {
        Header: algorithmName === "kmean" ? "Actual" : "Predicted",
        accessor: (data0) => data0[0],
      },
      {
        Header: algorithmName === "kmean" ? "Predicted" : "Actual",
        accessor: (data0) => data0[1],
      },
      ...featureCols.map((featureCol, i) => ({
        Header: featureCol,
        accessor: (data0: any[]) => data0[i + 2],
      })),
    ],
    [featureCols]
  );
  const data = useMemo<any[][]>(
    () => predictionInfo.map((actual) => JSON.parse("[" + actual + "]")),
    [predictionInfo]
  );
  return (
    <Container fluid>
      <h4 className={styles.center}>{t("ml.common.paf")}</h4>
      <div style={{ width: "100%", overflowX: "scroll" }}>
        <Table
          data={data}
          columns={columns}
          paginationOptions={{ pageIndex: 0, pageSize: 20 }}
        />
      </div>
    </Container>
  );
};

export const RFCTree: React.FC = () => {
  const { t } = useTranslation();
  return (
    <Row>
      <div className="col-lg-12">
        <Card>
          <Card.Header>
            <strong>{t("ml.common.tree")}</strong>
            {t("ml.common.lodt")}
          </Card.Header>
          <Card.Body>
            <div className="list-group" id="treeList"></div>
            <form id="treeDataForm" style={{ display: "none" }}>
              <input name="treeData" id="treeDataInput" />
            </form>
          </Card.Body>
          <Card.Footer />
        </Card>
      </div>
    </Row>
  );
};
