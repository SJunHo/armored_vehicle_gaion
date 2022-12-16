import React from "react";
// import Container from "react-bootstrap/Container";
// import Row from "react-bootstrap/Row";
// import { RailSensorData } from "../api";
// import {
//   RailConditionDataCard,
//   RailConditionDataCardProps,
// } from "./RailConditionDataCard";
// import {
//   RailConditionDataCardWithScorce,
//   RailConditionDataCardWithScorceProps,
// } from "./RailConditionDataCardWithScorce";
//
// export const RailSensorDataDetail: React.FC<{ data: RailSensorData }> = ({
//   data,
// }) => {
//   const Card1: RailConditionDataCardProps[] = [
//     { label: "Entry Speed", value: data.entrySpeed },
//     { label: "Air Temperature", value: data.airTemp },
//     { label: "Total Value", value: data.totalValue },
//     { label: "Total Count", value: data.totalCount },
//     { label: "Weighting State equation", value: data.weightingStateEquation },
//   ];
//
//   const Card2: RailConditionDataCardWithScorceProps[] = [
//     { label: "Vibration", value: data.vibV, score: data.vibC },
//     { label: "LoadCell", value: data.loadV, score: data.loadC },
//     { label: "Temperature", value: data.tempV, score: data.tempC },
//     { label: "AE", value: data.aeV, score: data.aeC },
//     { label: "Sound", value: data.soundV, score: data.soundC },
//   ];
//   return (
//     <>
//       <Container fluid className="container fw-bold bg-light px-5 py-2">
//         <Row className="row justify-content-center align-top mb-2 mt-3 flex-nowrap">
//           {Card1.map(Card1 => (
//             <RailConditionDataCard {...Card1} />
//           ))}
//         </Row>
//       </Container>
//       <Container fluid className="container fw-bold p-1 bg-light">
//         <Row className="row justify-content-start mt-3 ">
//           {Card2.map(Card2 => (
//             <RailConditionDataCardWithScorce {...Card2} />
//           ))}
//         </Row>
//       </Container>
//     </>
//   );
// };
