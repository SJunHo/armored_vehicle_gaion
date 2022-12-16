import React, {useContext, useEffect, useState} from "react";
// import {range, sum} from "lodash";
// import {Card, Col, Container, Row} from "react-bootstrap";
// import BootstrapTable from "react-bootstrap/Table";
// import {
//   Hint,
//   LineMarkSeries,
//   RadialChart,
//   VerticalBarSeries,
//   FlexibleXYPlot,
//   XAxis,
//   YAxis,
//   RadialChartPoint,
// } from "react-vis";
// import "react-vis/dist/style.css";
// import {AllTimeESStats, OpenApiContext} from "../api";
// import style from "./dashboard.module.css";
//
// export const Dashboard: React.FC = () => {
//   const [allTimeStats, setAllTimeStats] = useState<AllTimeESStats>();
//   const [monthlyCount, setMonthlyCount] = useState<number[]>();
//   const [dailyCount, setDailyCount] = useState<number[]>();
//   const [selectedB, setSelectedB] = useState<RadialChartPoint>();
//   const [selectedW, setSelectedW] = useState<RadialChartPoint>();
//
//   const {datasetControllerApi} = useContext(OpenApiContext);
//
//   useEffect(() => {
//     datasetControllerApi
//       ?.allTimeStats()
//       .then((res) => setAllTimeStats(res.data));
//     datasetControllerApi
//       ?.countRecordsMonthlyLast6Months()
//       .then((res) => setMonthlyCount(res.data));
//     datasetControllerApi
//       ?.countRecordsDailyInLast30Days()
//       .then((res) => setDailyCount(res.data));
//   }, [datasetControllerApi]);
//
//   const defectMatrix = allTimeStats?.defectMatrix || {};
//   const matrixData = range(10, 60, 10).map((userScore) => ({
//     defectUser: dataLevel[userScore / 10 - 1],
//     "10": (defectMatrix["10"] || {})[userScore.toString()],
//     "20": (defectMatrix["20"] || {})[userScore.toString()],
//     "30": (defectMatrix["30"] || {})[userScore.toString()],
//     "40": (defectMatrix["40"] || {})[userScore.toString()],
//     "50": (defectMatrix["50"] || {})[userScore.toString()],
//   }));
//   const correctCount = range(10, 60, 10).reduce(
//     (prev, cur) =>
//       prev + ((defectMatrix[cur.toString()] || {})[cur.toString()] || 0),
//     0
//   );
//   const accuracy = (correctCount / (allTimeStats?.defectUserGt0 || 1)) * 100;
//   const totalWeightingB = sum(allTimeStats?.weightingB || []);
//   const totalWeightingW = sum(allTimeStats?.weightingW || []);
//
//   const [value, setValue] = useState<any>();
//   const [barValue, setBarValue] = useState<any>();
//   const [barValue2, setBarValue2] = useState<any>();
//   const [barValue3, setBarValue3] = useState<any>();
//   const [barValue4, setBarValue4] = useState<any>();
//
//   return (
//     <Container fluid className="bg-light pt-3">
//       <Container fluid className={style.dashboardPage}>
//         <Row
//           className="gx-2 gy-2"
//           style={{alignItems: "stretch", gridAutoRows: "1fr"}}
//         >
//           <Col md={4}>
//             <Card className={style.fullHeightCard}>
//               <Card.Body className="d-flex flex-column">
//                 <div style={{textAlign: "center"}} className="fw-bold fs-4">
//                   일별 측정건수(최근 30일)
//                 </div>
//
//                 <div className="flex-grow-1">
//                   <FlexibleXYPlot height={250}>
//                     <XAxis/>
//                     <YAxis style={{fontSize: 8}}/>
//                     <LineMarkSeries
//                       data={(dailyCount || []).map((data, index) => ({
//                         x: index,
//                         y: data,
//                       }))}
//                       lineStyle={{stroke: colorPalette[0]}}
//                       markStyle={{stroke: colorPalette[5]}}
//                       onValueMouseOver={(v) => setValue(v)}
//                       onSeriesMouseOut={() => setValue(undefined)}
//                     />
//                     <Hint
//                       style={{visibility: value ? "visible" : "hidden"}}
//                       value={value || {}}
//                     />
//                   </FlexibleXYPlot>
//                 </div>
//               </Card.Body>
//             </Card>
//           </Col>
//           <Col md={4}>
//             <Card className={style.fullHeightCard}>
//               <Card.Body className="d-flex flex-column">
//                 <div style={{textAlign: "center"}} className="fw-bold fs-4">
//                   월별 측정건수(최근 6개월)
//                 </div>
//                 <div className="flex-grow-1">
//                   <FlexibleXYPlot xType="ordinal" stackBy="y" height={250}>
//                     <XAxis/>
//                     <YAxis style={{fontSize: 8}}/>
//                     <VerticalBarSeries
//                       barWidth={0.4}
//                       onValueMouseOver={(v) => setBarValue(v)}
//                       onSeriesMouseOut={() => setBarValue(undefined)}
//                       color="#0b8d9b"
//                       data={(monthlyCount || []).map((data, index) => ({
//                         x: index,
//                         y: data,
//                       }))}
//                     />
//                     <Hint
//                       style={{visibility: barValue ? "visible" : "hidden"}}
//                       value={barValue || {}}
//                     />
//                   </FlexibleXYPlot>
//                 </div>
//               </Card.Body>
//             </Card>
//           </Col>
//           <Col md={4}>
//             <Card className={style.fullHeightCard}>
//               <Card.Body>
//                 <div style={{textAlign: "center"}} className="fw-bold fs-4">
//                   부품별 결함검출 현황
//                 </div>
//
//                 <Container>
//                   <Row>
//                     <Col md={5}>
//                       <RadialChart
//                         width={200}
//                         height={200}
//                         data={(allTimeStats?.weightingW || []).map(
//                           (weightingW, index) => {
//                             return {
//                               angle:
//                                 (2 * Math.PI * weightingW) / totalWeightingW,
//                               color: colorPalette2[index],
//                               label: dataLevel[index],
//                               subLabel: weightingW,
//                               style:
//                                 colorPalette2[index] ===
//                                 selectedW?.color?.toString()
//                                   ? {
//                                     stroke: "black",
//                                     strokeWidth: "3px",
//                                     zIndex: 1,
//                                   }
//                                   : {zIndex: 1000},
//                             };
//                           }
//                         )}
//                         colorType={"literal"}
//                         onSeriesMouseOut={() => setSelectedW(undefined)}
//                         onValueMouseOver={(datapoint) => {
//                           setSelectedW(datapoint);
//                         }}
//                       >
//                         <Hint
//                           style={{
//                             visibility: selectedW ? "visible" : "hidden",
//                           }}
//                           value={
//                             selectedW
//                               ? {
//                                 value: selectedW.subLabel,
//                                 label: selectedW.label,
//                               }
//                               : {}
//                           }
//                         />
//                       </RadialChart>
//                       <div className={style.titleCenter}>차륜</div>
//                     </Col>
//                     <Col md={2}/>
//                     <Col md={5}>
//                       <RadialChart
//                         width={200}
//                         height={200}
//                         data={(allTimeStats?.weightingB || []).map(
//                           (weightingB, index) => {
//                             return {
//                               angle:
//                                 (2 * Math.PI * weightingB) / totalWeightingB,
//                               color: colorPalette2[index],
//                               label: dataLevel[index],
//                               subLabel: weightingB,
//                               style:
//                                 colorPalette2[index] ===
//                                 selectedB?.color?.toString()
//                                   ? {
//                                     stroke: "black",
//                                     strokeWidth: "3px",
//                                     zIndex: 1,
//                                   }
//                                   : {zIndex: 1000},
//                             };
//                           }
//                         )}
//                         colorType={"literal"}
//                         onSeriesMouseOut={() => setSelectedB(undefined)}
//                         onValueMouseOver={(datapoint) => {
//                           setSelectedB(datapoint);
//                         }}
//                       >
//                         <Hint
//                           style={{
//                             visibility: selectedB ? "visible" : "hidden",
//                           }}
//                           value={
//                             selectedB
//                               ? {
//                                 value: selectedB.subLabel,
//                                 label: selectedB.label,
//                               }
//                               : {}
//                           }
//                         />
//                       </RadialChart>
//                       <div className={style.titleCenter}>차축 베어링</div>
//                     </Col>
//                   </Row>
//                 </Container>
//               </Card.Body>
//             </Card>
//           </Col>
//           <Col md={4}>
//             <Card className={style.fullHeightCard}>
//               <Card.Body className="d-flex flex-column">
//                 <div style={{textAlign: "center"}} className="fw-bold fs-4">
//                   Type별 고장진단 예측 현황(Top 10)
//                 </div>
//
//                 <div className="flex-grow-1">
//                   <FlexibleXYPlot
//                     className="clustered-stacked-bar-chart-example"
//                     xType="ordinal"
//                     stackBy="y"
//                     height={250}
//                   >
//                     <XAxis/>
//                     <YAxis style={{fontSize: 8}}/>
//                     {["0", "10", "20", "30", "40", "50"].map((score) => (
//                       <VerticalBarSeries
//                         barWidth={0.6}
//                         onValueMouseOver={(v) => setBarValue2(v)}
//                         onSeriesMouseOut={() => setBarValue2(undefined)}
//                         color={colorPalette[parseInt(score, 10) / 10]}
//                         data={Object.keys(allTimeStats?.byType || {}).map(
//                           (key) => ({
//                             x: key,
//                             y:
//                               ((allTimeStats?.byType || {})[key] || {})[
//                                 score
//                                 ] || 0,
//                           })
//                         )}
//                       />
//                     ))}
//                     <Hint
//                       style={{visibility: barValue2 ? "visible" : "hidden"}}
//                       value={barValue2 || {}}
//                     />
//                   </FlexibleXYPlot>
//                 </div>
//               </Card.Body>
//             </Card>
//           </Col>
//           <Col md={4}>
//             <Card className={style.fullHeightCard}>
//               <Card.Body className="d-flex flex-column">
//                 <div style={{textAlign: "center"}} className="fw-bold fs-4">
//                   편성별 고장전조 예측 현황(Top 10)
//                 </div>
//                 <div className="flex-grow-1">
//                   <FlexibleXYPlot
//                     className="clustered-stacked-bar-chart-example"
//                     xType="ordinal"
//                     stackBy="y"
//                     height={250}
//                   >
//                     <XAxis/>
//                     <YAxis style={{fontSize: 8}}/>
//                     {["0", "10", "20", "30", "40", "50"].map((score) => (
//                       <VerticalBarSeries
//                         barWidth={0.6}
//                         onValueMouseOver={(v) => setBarValue3(v)}
//                         onSeriesMouseOut={() => setBarValue3(undefined)}
//                         color={colorPalette[parseInt(score, 10) / 10]}
//                         data={Object.keys(allTimeStats?.byTrainNo || {}).map(
//                           (key) => ({
//                             x: key,
//                             y:
//                               ((allTimeStats?.byTrainNo || {})[key] || {})[
//                                 score
//                                 ] || 0,
//                           })
//                         )}
//                       />
//                     ))}
//                     <Hint
//                       style={{visibility: barValue3 ? "visible" : "hidden"}}
//                       value={barValue3 || {}}
//                     />
//                   </FlexibleXYPlot>
//                 </div>
//               </Card.Body>
//             </Card>
//           </Col>
//           <Col md={4}>
//             <Card className={style.fullHeightCard}>
//               <Card.Body className="d-flex flex-column">
//                 <div style={{textAlign: "center"}} className="fw-bold fs-4">
//                   차량별 고장전조 예측 현황(Top 10)
//                 </div>
//
//                 <div className="flex-grow-1">
//                   <FlexibleXYPlot
//                     className="clustered-stacked-bar-chart-example"
//                     xType="ordinal"
//                     stackBy="y"
//                     height={250}
//                   >
//                     <XAxis/>
//                     <YAxis style={{fontSize: 8}}/>
//                     {["0", "10", "20", "30", "40", "50"].map((score) => (
//                       <VerticalBarSeries
//                         barWidth={0.6}
//                         color={colorPalette[parseInt(score, 10) / 10]}
//                         onValueMouseOver={(v) => setBarValue4(v)}
//                         onSeriesMouseOut={() => setBarValue4(undefined)}
//                         data={Object.keys(allTimeStats?.byCarNo || {}).map(
//                           (key) => ({
//                             x: key,
//                             y:
//                               ((allTimeStats?.byCarNo || {})[key] || {})[
//                                 score
//                                 ] || 0,
//                           })
//                         )}
//                       />
//                     ))}
//                     <Hint
//                       style={{visibility: barValue4 ? "visible" : "hidden"}}
//                       value={barValue4 || {}}
//                     />
//                   </FlexibleXYPlot>
//                 </div>
//               </Card.Body>
//             </Card>
//           </Col>
//           <Col md={3}>
//             <Card className={style.fullHeightCard}>
//               <Card.Body>
//                 <div style={{textAlign: "center"}} className="fw-bold fs-4">
//                   AI 기반 고장전조 예측 및 작업자 평가 비교
//                 </div>
//                 <Row className="gx-2">
//                   <Col>
//                     <div className={style.countStats}>
//                       <div>{allTimeStats?.defectScoreGt0}</div>
//                     </div>
//                     <div style={{textAlign: "center"}}>AI 진단(건)</div>
//                   </Col>
//                   <Col>
//                     <div className={style.countStats}>
//                       <div>{allTimeStats?.defectUserGt0}</div>
//                     </div>
//                     <div style={{textAlign: "center"}}>작업자 평가(건)</div>
//                   </Col>
//                 </Row>
//               </Card.Body>
//             </Card>
//           </Col>
//           <Col md={6}>
//             <Card className={style.fullHeightCard}>
//               <Card.Body>
//                 <BootstrapTable style={{height: "100%"}}>
//                   <thead>
//                   <tr>
//                     <th>#</th>
//                     {dataLevel.map((level) => (
//                       <th>{level}</th>
//                     ))}
//                   </tr>
//                   </thead>
//                   <tbody>
//                   {matrixData.map((data) => (
//                     <tr>
//                       <td>{data.defectUser}</td>
//                       <td>{data["10"] || "0"}</td>
//                       <td>{data["20"] || "0"}</td>
//                       <td>{data["30"] || "0"}</td>
//                       <td>{data["40"] || "0"}</td>
//                       <td>{data["50"] || "0"}</td>
//                     </tr>
//                   ))}
//                   </tbody>
//                 </BootstrapTable>
//               </Card.Body>
//             </Card>
//           </Col>
//           <Col md={3}>
//             <Card className={style.fullHeightCard}>
//               <Card.Body
//                 style={{
//                   display: "flex",
//                   flexDirection: "column",
//                   alignItems: "center",
//                 }}
//               >
//                 <div className="fw-bold fs-4">AI 기반 진단 정확도</div>
//                 <div className={style.gaugeWrapper}>
//                   <div className={style.gauge}>
//                     <div
//                       className={style.meter}
//                       style={{
//                         transform: `rotate(${(accuracy / 100) * 180}deg)`,
//                       }}
//                     />
//                   </div>
//
//                   <span
//                     className={style.needle}
//                     style={{
//                       transform: `rotate(${(accuracy / 100) * 180 - 90}deg)`,
//                     }}
//                   />
//                 </div>
//
//                 <div className={style.gaugeScore}>{accuracy.toFixed(0)}%</div>
//               </Card.Body>
//             </Card>
//           </Col>
//         </Row>
//       </Container>
//     </Container>
//   );
// };
//
// export const colorPalette = [
//   "#205B4A",
//   "#2A7A63",
//   "#34987c",
//   "#34987c",
//   "#34987c",
//   "#34987c",
// ];
//
// export const colorPalette2 = [
//   "#11E7FF",
//   "#11E7FF",
//   "#0B8D9B",
//   "#075E67",
//   "#05474E",
// ];
//
// const dataLevel = ["매우 양호", "양호", "적정", "심각", "매우 심각"];
