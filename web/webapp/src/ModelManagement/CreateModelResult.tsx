import React, {useMemo, useRef, useState} from "react";
import {useTranslation} from "react-i18next";
import Row from "react-bootstrap/Row";
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import {chunk, range, sum, zip} from "lodash";
import styles from "./styles.module.css";
import {RegressionResponse, RandomForestClassificationResponse} from "../api";
import {useParams} from "react-router-dom";
import {Table} from "../common/Table";
import {Column} from "react-table";
import {
  FlexibleWidthXYPlot,
  MarkSeries,
  FlexibleXYPlot,
  VerticalBarSeries,
  LineSeries,
  DiscreteColorLegend,
  XAxis,
  YAxis,
  Hint,
  Crosshair,
  RectSeries,
  VerticalRectSeries
} from "react-vis";
import {BarChart, Bar, XAxis as X, YAxis as Y, CartesianGrid, Tooltip, Legend} from "recharts"
import Select2 from "react-select";
import ReactTooltip from "react-tooltip";
import {log} from "util";
import {auto} from "@popperjs/core";
import "../../src/css/style.css"

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
    confusionMatrix = [],
    labels = [],
    predictedActualFeatureLine: resultPredictedActualFeatureLine,
    predictionInfo,
  } = result;
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

  const predictedActualFeatureLine =
    resultPredictedActualFeatureLine || predictionInfo;
  const matrixSize = Math.sqrt(confusionMatrix?.length || 0);

  const countByLabels = chunk(confusionMatrix, matrixSize).map((c) => sum(c));
  let roundResidualList: number[] = []
  residualList?.forEach((value => {
    // roundResidualList.push(Number(value.toFixed(1)))
    roundResidualList.push(Number(Math.round(value)))
  }))

  let count = roundResidualList?.reduce((accumulator: any, value: number) => {
    return {...accumulator, [value]: (accumulator[value] || 0) + 1};
  }, {});

  let residualKeyValuesList = []
  for (let i = 0; i <= Object.keys(count).length; i++) {
    residualKeyValuesList.push({
      x: Number(Object.keys(count)[i]),
      y: Number(Object.values(count)[i])
    })
  }
  return (
    <CustomCardContainer>
      <CustomCardHeader>
        <strong>{"진단 모델 성능(요약)"}</strong>
      </CustomCardHeader>
      <CustomCardBody>
        {(algorithmName === "linear" || algorithmName === "lasso") && (
          <RegressionResult algorithmName={algorithmName} result={result} result2={result2}/>
        )}
        {(algorithmName === "if") && (
          <ClusterDiagram algorithmName={algorithmName} result={result} result2={result2}/>
        )}
        {(algorithmName === "rfc" || algorithmName === "mlp" || algorithmName === "svc" || algorithmName === "lr") && (
          <ClassificationResult algorithmName={algorithmName} result={result} result2={result2}/>
        )}
      </CustomCardBody>
    </CustomCardContainer>
  );
};

export const RegressionResult: React.FC<Props> = ({result, result2}) => {
  const {
    confusionMatrix = [],
    labels = [],
    predictedActualFeatureLine: resultPredictedActualFeatureLine,
    predictionInfo,
  } = result;

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

  const predictedActualFeatureLine =
    resultPredictedActualFeatureLine || predictionInfo;
  const matrixSize = Math.sqrt(confusionMatrix?.length || 0);

  const countByLabels = chunk(confusionMatrix, matrixSize).map((c) => sum(c));
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
  for (let i = 0; i <= Object.keys(count).length; i++) {
    residualKeyValuesList.push({
      x: Number(Object.keys(count)[i]),
      y: Number(Object.values(count)[i])
    })
  }
  return (
    <Row className="container">
      <CustomCardContainer className={styles.cardBody}>
        <CustomCardHeader className={styles.cardHeader}>
          <strong>{"진단 모델 성능(요약)"}</strong>
        </CustomCardHeader>
        <CustomCardBody className="d-grid gap-3 container">
          <Row>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"R2"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <h1 className={styles.center}>{result2?.r2}</h1>
                </CustomCardBody>
              </CustomCardContainer>
            </div>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"RMSE"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <h1 className={styles.center}>{result2?.rootMeanSquaredError}</h1>
                </CustomCardBody>
              </CustomCardContainer>
            </div>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"실제 VS 예측 Line Chart"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
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
                      style={{fontSize: 12}}
                      tickTotal={10}
                    />
                    <YAxis
                      style={{fontSize: 12}}
                      tickTotal={10}
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
                </CustomCardBody>
              </CustomCardContainer>
            </div>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"실제 VS 예측 Scatter Chart"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <DiscreteColorLegend
                    orientation="vertical"
                    items={[
                      {title: 'Actual Values', color: '#9E520D'},
                      {title: 'Predicted Values', color: 'black'}
                    ]}
                  />
                  <FlexibleXYPlot height={300}>
                    <XAxis
                      style={{fontSize: 12}}
                      tickTotal={10}
                    />
                    <YAxis
                      style={{fontSize: 12}}
                      tickTotal={10}
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
                </CustomCardBody>
              </CustomCardContainer>
            </div>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"잔차 Line Chart"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <div data-tip data-for="tooltip">
                    <FlexibleXYPlot height={300}>
                      <XAxis
                        // title="index"
                        style={{fontSize: 12}}
                        tickTotal={10}
                      />
                      <YAxis
                        // title="value"
                        style={{fontSize: 12}}
                        tickTotal={10}
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
                    <div> {"잔차 : " + eachResidualValue[1]?.toFixed(3)} </div>
                  </ReactTooltip>
                </CustomCardBody>
              </CustomCardContainer>
            </div>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"잔차 Histogram"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <div style={{display: "flex"}}>
                    <BarChart
                      width={868}
                      height={300}
                      data={residualKeyValuesList}
                    >
                      <X dataKey="x"/>
                      <Y/>
                      <Bar dataKey="y"/>
                    </BarChart>
                  </div>
                </CustomCardBody>
              </CustomCardContainer>
            </div>
          </Row>
          <Row>
            {predictedActualFeatureLine && (
              <PredictionInfoSection
                predictionInfo={predictedActualFeatureLine}
                featureCols={result.listFeatures || []}
              />
            )}
          </Row>
        </CustomCardBody>
      </CustomCardContainer>
    </Row>
  )
}


export const ClusterDiagram: React.FC<Props> = ({
                                                  result: {predictionInfo, listFeatures},
                                                  algorithmName, result2
                                                }) => {
  const [selectedXAxis, setSelectedXAxis] = useState<number>();
  const [selectedYAxis, setSelectedYAxis] = useState<number>();
  const colorPalette2 = [
    "#11E7FF",
    "#11E7FF",
    "#0B8D9B",
    "#075E67",
    "#05474E",
  ];

  const data = useMemo<any[][]>(
    () =>
      (predictionInfo || []).map((actual) => JSON.parse("[" + actual + "]")),
    [predictionInfo]
  );

  return (
    <CustomCardContainer>
      <CustomCardHeader>
        <strong>Clusters</strong>
      </CustomCardHeader>
      <CustomCardBody>
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
          <XAxis/>
          <YAxis/>
          <MarkSeries
            colorType="literal"
            data={
              selectedXAxis !== undefined && selectedYAxis !== undefined
                ? data.map((item) => ({
                  x: item[selectedXAxis],
                  y: item[selectedYAxis],
                  color: colorPalette2[algorithmName === "kmean" ? item[1] : item[0]],
                }))
                : []
            }
          />
        </FlexibleWidthXYPlot>
      </CustomCardBody>
    </CustomCardContainer>
  );
};

export const ClassificationResult: React.FC<Props> = ({result, result2}) => {
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

  const {algorithmName} = useParams<{ algorithmName: string }>();
  const {t} = useTranslation();
  return (
    <>
      <CustomCardContainer>
        <CustomCardHeader>
          <strong>{"결과"}</strong>
        </CustomCardHeader>
        <CustomCardBody>
          <Row>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"정확도"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <h1 className={styles.center}>{result.accuracy}</h1>
                </CustomCardBody>
              </CustomCardContainer>
            </div>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"정밀도"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <h1 className={styles.center}>{result.weightedPrecision}</h1>
                </CustomCardBody>
              </CustomCardContainer>
            </div>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"재현율"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <h1 className={styles.center}>{result.weightedRecall}</h1>
                </CustomCardBody>
              </CustomCardContainer>
            </div>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"F1 - 스코어"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <h1 className={styles.center}>{result.weightedFMeasure}</h1>
                </CustomCardBody>
              </CustomCardContainer>
            </div>
          </Row>
        </CustomCardBody>
      </CustomCardContainer>
      <CustomCardContainer>
        <CustomCardHeader>
          <strong>{"혼잡 매트릭스"}</strong>
        </CustomCardHeader>
        <CustomCardBody>
          <Row>
            <div className="table-responsive">
              <table className="table table-bordered table-striped">
                <thead className={styles.textCenter}>
                <tr className={`${styles.tableBlack}`}>
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
                    {"실제 클래스"}
                  </th>
                </tr>
                <tr
                  id="actualLabels"
                  className={`${styles.tableBlack} ${styles.textCenter}`}
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
                    className={`${styles.tableBlack} ${styles.predictCell}`}
                    rowSpan={matrixSize + 1}
                  >
                    {"예측된 클래스"}
                  </th>
                </tr>
                {range(0, matrixSize).map((i) => (
                  <tr>
                    <td className={`${styles.tableBlack}`}>{labels[i]}</td>
                    {range(0, matrixSize).map((j) => (
                      <td className={i === j ? `${styles.tableBlack}` : ""}>
                        {confusionMatrix[i * matrixSize + j]}
                      </td>
                    ))}
                  </tr>
                ))}
                </tbody>
              </table>
            </div>
          </Row>
        </CustomCardBody>
      </CustomCardContainer>
      <CustomCardContainer>
        <CustomCardHeader>
          <strong>예측 결과</strong>
        </CustomCardHeader>
        <CustomCardBody>
          {predictedActualFeatureLine && (
            <PredictionInfoSection
              predictionInfo={predictedActualFeatureLine}
              featureCols={result.listFeatures || []}
            />
          )}
        </CustomCardBody>
      </CustomCardContainer>
    </>

  );
};

export const PredictionInfoSection: React.FC<{
  predictionInfo: string[];
  featureCols: string[];
}> = ({predictionInfo, featureCols}) => {
  const {algorithmName} = useParams<{ algorithmName: string }>();
  const {t} = useTranslation();
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
    <>
      <Table
        data={data}
        columns={columns}
        paginationOptions={{pageIndex: 0, pageSize: 20}}
      />
    </>
  );
};

// export const RFCTree: React.FC = () => {
//   const {t} = useTranslation();
//   return (
//     <Row>
//       <div className="col-lg-12">
//         <Card>
//           <CustomCardHeader>
//             <strong>{t("ml.common.tree")}</strong>
//             {t("ml.common.lodt")}
//           </CustomCardHeader>
//           <CustomCardBody>
//             <div className="list-group" id="treeList"></div>
//             <form id="treeDataForm" style={{display: "none"}}>
//               <input name="treeData" id="treeDataInput"/>
//             </form>
//           </CustomCardBody>
//           <Card.Footer/>
//         </Card>
//       </div>
//     </Row>
//   );
// };

function CustomCardContainer(props: any) {
  return (
    <>
      <style type="text/css">
        {`.card-body {
            background-color: #464667;
            }
        .card{
          padding:0px;
        }`
        }
      </style>

      <Card {...props}/>
    </>
  );
}


function CustomCardHeader(props: any) {
  return (
    <>
      <style type="text/css">
        {`.card-header {
            background-color: #2c2c44;
         }
           .card{
            padding:0px;
         }`
        }
      </style>

      <Card.Header {...props}/>
    </>
  );
}

function CustomCardBody(props: any) {
  return (
    <>
      <style type="text/css">
        {`.card-body {
            background-color: #3A3A5A;
          }
          .card{
            padding:0px;
          }`
        }
      </style>

      <Card.Body {...props}/>
    </>
  );
}