import React, {useContext, useEffect, useMemo, useState} from "react";
import {useTranslation} from "react-i18next";
import Row from "react-bootstrap/Row";
import Card from "react-bootstrap/Card";
import {chunk, range, sum, zip} from "lodash";
import styles from "./styles.module.css";
import {ClassificationResponse, OpenApiContext, RegressionResponse} from "../api";
import {useParams} from "react-router-dom";
import {Table} from "../common/Table";
import {Column} from "react-table";
import "../../src/css/style.css"
import {Legend, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis} from "recharts";

type Props = {
  result: ClassificationResponse;
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

  const [eachResidualValue, setEachResidualValue] = useState<any>([]);

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
  // console.log(count)

  let residualKeyValuesList = []
  for (let i = 0; i <= Object.keys(count).length; i++) {
    residualKeyValuesList.push({
      x: Number(Object.keys(count)[i]),
      y: Number(Object.values(count)[i])
    })
  }
  console.log(roundResidualList)
  console.log(residualKeyValuesList)

  function filterResidualData(data: any) {
    const chartDataset: { index: number; residual: undefined }[] = [];
    data.map((v: any, i: number) => {
      const rowData = {
        index: i,
        residual: v,
      };
      chartDataset.push(rowData)
    })
    return chartDataset;
  }

  function filterChartData(data: any) {
    const chartDataset: { index: number; actual: undefined; predict: undefined; }[] = [];
    data.map((v: any, i: number) => {
      const rowData = {
        index: i,
        actual: v[0],
        predict: v[1]
      };
      chartDataset.push(rowData)
    })
    return chartDataset;
  }

  const CustomTooltip = (data: any) => {
    // console.log('CustomTooltip -> data', data)
    const {active, payload, label} = data;
    console.log(active, payload, label)
    if (active && payload && payload.length) {
      return (
        <div className="custom-tooltip">
          {payload.map((v: { name: any; value: any; }) => {
            return <p className="label">{`${v.name} : ${v.value}`}</p>
          })}
        </div>
      );
    }
    return null;
  };
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
          </Row>
        </CustomCardBody>
      </CustomCardContainer>
      <CustomCardContainer>
        <CustomCardHeader>
          <strong>결과 차트</strong>
        </CustomCardHeader>
        <CustomCardBody>
          <Row>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"실제 VS 예측 Chart"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <div style={{position: 'relative', minWidth: "600px", width: '100%', paddingBottom: '250px'}}>
                    <div
                      style={{
                        position: 'absolute',
                        left: 0,
                        right: 0,
                        bottom: 0,
                        top: 0,
                      }}
                    >
                      <ResponsiveContainer>
                        <LineChart
                          width={600}
                          height={300}
                          margin={{
                            top: 5,
                            right: 30,
                            left: 20,
                            bottom: 5,
                          }}
                          data={filterChartData(actualPredictedValues)}
                        >
                          <XAxis tick={{fill: 'white'}} dataKey="index"/>
                          <YAxis tick={{fill: 'white'}}/>
                          <Line
                            strokeWidth={1}
                            type="monotone"
                            dataKey="actual"
                            stroke="#8884d8"
                            activeDot={{r: 8}}
                          />
                          <Line
                            strokeWidth={1}
                            type="monotone"
                            dataKey="predict"
                            stroke="#82ca9d"
                          />
                          <Tooltip
                            content={<CustomTooltip/>}
                            //viewBox={{ x: 100, y: 140, width: 400, height: 400 }}
                          />
                          <Legend/>
                        </LineChart>
                      </ResponsiveContainer>
                    </div>
                  </div>
                </CustomCardBody>
              </CustomCardContainer>
            </div>
            <div className="col-lg-6">
              <CustomCardContainer>
                <CustomCardHeader>
                  <strong>{"잔차 Chart"}</strong>
                </CustomCardHeader>
                <CustomCardBody>
                  <div style={{position: 'relative', minWidth: "600px", width: '100%', paddingBottom: '250px'}}>
                    <div
                      style={{
                        position: 'absolute',
                        left: 0,
                        right: 0,
                        bottom: 0,
                        top: 0,
                      }}
                    >
                      <ResponsiveContainer>
                        <LineChart
                          width={600}
                          height={300}
                          margin={{
                            top: 5,
                            right: 30,
                            left: 20,
                            bottom: 5,
                          }}
                          data={filterResidualData(residualList)}
                        >
                          <XAxis tick={{fill: 'white'}} dataKey="index"/>
                          <YAxis tick={{fill: 'white'}}/>
                          <Tooltip
                            content={<CustomTooltip/>}
                            //viewBox={{ x: 100, y: 140, width: 400, height: 400 }}
                          />
                          <Legend/>
                          <Line
                            isAnimationActive={false}
                            strokeWidth={1}
                            type="monotone"
                            dataKey="residual"
                            stroke="#8884d8"
                            activeDot={{r: 8}}
                          />
                        </LineChart>
                      </ResponsiveContainer>
                    </div>
                  </div>
                </CustomCardBody>
              </CustomCardContainer>
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
              partType={result.partType || ""}
              predictionInfo={predictedActualFeatureLine}
              featureCols={result.listFeatures || []}
            />
          )}
        </CustomCardBody>
      </CustomCardContainer>
    </>
  )
}


export const ClusterDiagram: React.FC<Props> = ({
                                                  result,
                                                  algorithmName, result2
                                                }) => {
  const {
    predictedActualFeatureLine: resultPredictedActualFeatureLine,
    confusionMatrix = [],
    labels = [],
    predictionInfo
  } = result;
  const predictedActualFeatureLine = resultPredictedActualFeatureLine || predictionInfo;
  const matrixSize = Math.sqrt(confusionMatrix?.length || 0);
  const countByLabels = chunk(confusionMatrix, matrixSize).map((c) => sum(c));

  const data = useMemo<any[][]>(
    () =>
      (predictionInfo || []).map((actual) => JSON.parse("[" + actual + "]")),
    [predictionInfo]
  );

  console.log(matrixSize)
  return (
    <>
      <CustomCardContainer>
        <CustomCardHeader>
          <strong>{"결과"}</strong>
        </CustomCardHeader>
      </CustomCardContainer>
      <CustomCardContainer>
        <CustomCardHeader>
          <strong>{"혼잡 매트릭스"}</strong>
        </CustomCardHeader>
        <CustomCardBody>
          <Row>
            <div className="table-responsive">
              <table className="table table-bordered ">
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
                      <td className={i === j ? `${styles.tableBlack}` : `${styles.notTableBlack}`}>
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
              partType={result.partType || ""}
              predictionInfo={predictedActualFeatureLine}
              featureCols={result.listFeatures || []}
            />
          )}
        </CustomCardBody>
      </CustomCardContainer>
    </>

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

  const data = useMemo<any[][]>(
    () =>
      (predictionInfo || []).map((actual) => JSON.parse("[" + actual + "]")),
    [predictionInfo]
  );

  const {algorithmName} = useParams<{ algorithmName: string }>();
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
                      <td className={i === j ? `${styles.tableBlack}` : `${styles.notTableBlack}`}>
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
              partType={result.partType || ""}
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
  partType: string,
  predictionInfo: string[];
  featureCols: string[];
}> = ({partType, predictionInfo, featureCols}) => {
  const {algorithmName} = useParams<{ algorithmName: string }>();
  const {lifeThresholdControllerApi} = useContext(OpenApiContext);
  const [threshold, setThreshold] = useState<any>();

  const {t} = useTranslation();
  const wholeBearingCycle = 540000
  const wholeWheelCycle = 160000
  const wholeGearboxCycle = 1080000
  const wholeEngineCycle = 480000
  useEffect(() => {
    if (partType && (algorithmName === "linear" || algorithmName === "lasso")) {
      lifeThresholdControllerApi?.getThresholdList2()
        .then((res: any) => {
          switch (partType) {
            case "B_LIFE":
              setThreshold(wholeBearingCycle)
              // setThreshold(res.data.find((v: any) => {
              //   return v.snsrtype === "BEARING"
              // }).distance)
              break
            case "W_LIFE":
              setThreshold(wholeWheelCycle)

              // setThreshold(res.data.find((v: any) => {
              //   return v.snsrtype === "WHEEL"
              // }).distance)
              break
            case "E_LIFE":
              setThreshold(wholeEngineCycle)
              // setThreshold(res.data.find((v: any) => {
              //   return v.snsrtype === "ENGINE"
              // }).distance)
              break
            case "G_LIFE":
              setThreshold(wholeGearboxCycle)
              // setThreshold(res.data.find((v: any) => {
              //   return v.snsrtype === "REDUCER"
              // }).distance)
              break
          }
        })
    }
  }, [algorithmName, lifeThresholdControllerApi, partType])


  const columns = useMemo<Column<any[]>[]>(
    () => [
      {
        Header: algorithmName === "kmean" || algorithmName === "if" ? "Actual" : "Predicted",
        accessor: (data0) => {
          if (algorithmName === "linear" || algorithmName === "lasso") {
            return ((threshold - data0[0]) * 100 / threshold).toFixed(1);
          } else {
            return data0[0]
          }
        },
      },
      {
        Header: algorithmName === "kmean" || algorithmName === "if" ? "Predicted" : "Actual",
        accessor: (data0) => {
          if (algorithmName === "linear" || algorithmName === "lasso") {
            return ((threshold - data0[1]) * 100 / threshold).toFixed(1);
          } else {
            return data0[1]
          }
        },
      },
      ...featureCols.map((featureCol, i) => ({
        Header: featureCol,
        accessor: (data0: any[]) => data0[i + 2],
      })),
    ],
    [algorithmName, featureCols, threshold]
  );
  const data = useMemo<any[][]>(
    () => predictionInfo.map((actual) => JSON.parse("[" + actual + "]")),
    [predictionInfo]
  );
  return (
    <div className="overflow-auto">
      <Table
        data={data}
        columns={columns}
        paginationOptions={{pageIndex: 0, pageSize: 20}}
      />
    </div>
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