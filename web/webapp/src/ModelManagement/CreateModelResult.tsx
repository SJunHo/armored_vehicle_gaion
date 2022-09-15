import React, {useMemo, useRef, useState} from "react";
import { useTranslation } from "react-i18next";
import Row from "react-bootstrap/Row";
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import {chunk, range, sum, zip} from "lodash";
import styles from "./styles.module.css";
import {RegressionResponse, RandomForestClassificationResponse} from "../api";
import { useParams } from "react-router-dom";
import { Table } from "../common/Table";
import { Column } from "react-table";
import {
    FlexibleWidthXYPlot, MarkSeries, FlexibleXYPlot, VerticalBarSeries, LineSeries, DiscreteColorLegend, XAxis, YAxis, Hint, Crosshair, RectSeries, VerticalRectSeries
} from "react-vis";
import {BarChart, Bar, XAxis as X, YAxis as Y, CartesianGrid, Tooltip, Legend} from "recharts"
import Select2 from "react-select";
import {colorPalette, colorPalette2} from "../Dashboard/Dashboard";
import ReactTooltip from "react-tooltip";
import {log} from "util";

type Props = {
  result: RandomForestClassificationResponse;
  result2?: RegressionResponse;
  algorithmName: string;
};

export const CreateModelResult: React.FC<Props> = ({
  result,
  result2,
  algorithmName,
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

    let indexList: number[] = []
    let predictedValues: number[] = []
    let actualValues: number[] = []
    let residualList: number[] = []

    result2?.predictionInfo?.forEach((value1, index, array) => {
        indexList.push(index)
        predictedValues.push(Number(value1.split(',')[0]))
        actualValues.push(Number(value1.split(',')[1]))
    })

    actualValues?.forEach((value1, index) => {
        var x = actualValues[index] - predictedValues[index]
        residualList.push(x)
    })

    const actualPredictedValues = zip(actualValues, predictedValues)
    // console.log(actualPredictedValues)

    const [eachResidualValue, setEachResidualValue] = useState<any>([]);
    // console.log(eachResidualValue)

    let roundResidualList: number[] = []
    residualList?.forEach((value => {
        // roundResidualList.push(Number(value.toFixed(1)))
        roundResidualList.push(Number(Math.round(value)))
    }))
    // console.log(roundResidualList)

    let count = roundResidualList?.reduce((accumulator: any, value: number) => {
        return {...accumulator, [value]: (accumulator[value] || 0) + 1};
    }, {});
    // console.log(count)

    let residualKeyValuesList = []
    for(let i=0; i <= Object.keys(count).length; i++) {
        residualKeyValuesList.push({
            x: Number(Object.keys(count)[i]),
            y: Number(Object.values(count)[i])
        })
    }
    console.log(residualKeyValuesList)


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
                  <div className="col-lg-6">
                      <Card>
                          <Card.Header>
                              <strong>{t("ml.common.actual_predicted_line")}</strong>
                          </Card.Header>
                          <Card.Body>
                              <div>
                                  <DiscreteColorLegend
                                      orientation="vertical"
                                      items={[
                                          {title: 'Actual Values', color: '#9E520D'},
                                          {title: 'Predicted Values', color: '#00819E'}
                                      ]}
                                  />
                              </div>
                              <FlexibleXYPlot height={300}>
                                  <XAxis
                                      style={{ fontSize: 12 }}
                                      tickTotal = { 10 }
                                  />
                                  <YAxis
                                      style={{ fontSize: 12 }}
                                      tickTotal = { 10 }
                                  />
                                  <LineSeries
                                      data={(actualValues || []).map((data: any, index: any) => ({
                                          x: index,
                                          y: data,
                                      }))}
                                      stroke="#9E520D"
                                  />
                                  <LineSeries
                                      data={(predictedValues || []).map((data: any, index: any) => ({
                                          x: index,
                                          y: data,
                                      }))}
                                      stroke="#00819E"
                                      strokeStyle="solid"
                                  />
                              </FlexibleXYPlot>
                          </Card.Body>
                      </Card>
                  </div>
                  <div className="col-lg-6">
                      <Card>
                          <Card.Header>
                              <strong>{t("ml.common.actual_predicted_scatter")}</strong>
                          </Card.Header>
                          <Card.Body>
                              <DiscreteColorLegend
                                  orientation="vertical"
                                  items={[
                                      {title: 'Actual Values', color: '#9E520D'},
                                      {title: 'Predicted Values', color: 'black'}
                                  ]}
                              />
                              <FlexibleXYPlot height={300}>
                                  <XAxis
                                      style={{ fontSize: 12 }}
                                      tickTotal = { 10 }
                                  />
                                  <YAxis
                                      style={{ fontSize: 12 }}
                                      tickTotal = { 10 }
                                  />
                                  <LineSeries
                                      data={(actualPredictedValues || []).map((data: any, index: any) => ({
                                          x: data[1],
                                          y: data[1],
                                      }))}
                                      stroke="#9E520D"
                                  />
                                  <MarkSeries
                                      data={(actualPredictedValues || []).map((data: any, index: any) => ({
                                          x: data[0],
                                          y: data[1],
                                      }))}
                                      sizeType="literal"
                                      _sizeValue={1}
                                      color="black"
                                  />
                              </FlexibleXYPlot>
                          </Card.Body>
                      </Card>
                  </div>
                  <div className="col-lg-6">
                      <Card>
                          <Card.Header>
                              <strong>{t("ml.common.residuals_line")}</strong>
                          </Card.Header>
                          <Card.Body>
                              <div data-tip data-for="tooltip">
                                  <FlexibleXYPlot height={300}>
                                      <XAxis
                                          // title="index"
                                          style={{ fontSize: 12 }}
                                          tickTotal = { 10 }
                                      />
                                      <YAxis
                                          // title="value"
                                          style={{ fontSize: 12 }}
                                          tickTotal = { 10 }
                                      />
                                      <LineSeries
                                          // data={(result2?.residuals || []).map((data: any, index: any) => ({
                                          data={(residualList || []).map((data: any, index: any) => ({
                                              x: index,
                                              y: data,
                                          }))}
                                          stroke="#3296D7"
                                          onNearestXY={(v) => setEachResidualValue([v.x, v.y])}
                                          onSeriesMouseOut={() => setEachResidualValue([undefined, undefined])}
                                      />
                                  </FlexibleXYPlot>
                              </div>
                              <ReactTooltip id="tooltip">
                                  <div> {eachResidualValue[0]} </div>
                                  <div> {t("ml.common.residuals") + " : " + eachResidualValue[1]?.toFixed(3)} </div>
                              </ReactTooltip>
                          </Card.Body>
                      </Card>
                  </div>
                  <div className="col-lg-6">
                      <Card>
                          <Card.Header>
                              <strong>{t("ml.common.residuals_histogram")}</strong>
                          </Card.Header>
                          <Card.Body>
                              <div style={{display: "flex"}}>
                                  <BarChart
                                      width={868}
                                      height={300}
                                      data={residualKeyValuesList}
                                  >
                                      <X dataKey="x"/>
                                      <Y />
                                      <Bar dataKey="y" />
                                  </BarChart>
                              </div>
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
        <strong>{t("ml.common.result")}</strong>
      </Card.Header>
      <Card.Body>
        <Container fluid>
          {algorithmName === "linear" || algorithmName === 'lasso' ?  (
            <Row> </Row>
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
      {/*<h4 className={styles.center}>{t("ml.common.result")}</h4>*/}
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
