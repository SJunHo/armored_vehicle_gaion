import React, {useContext, useEffect, useMemo, useState} from "react";
import {Button} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {useTranslation} from "react-i18next";
import {Column} from "react-table";
import {OpenApiContext, Pageable, RailSensorData} from "../api";
import {RailSensorDataDetail} from "../common/RailSensorDataDetail";
import {Paginator} from "../common/Paginator";
import {Table} from "../common/Table";
import {Page} from "../common/Page/Page";
import {TabHeader} from "../common/TabHeader/TabHeader";
import styles from "../ModelManagement/styles.module.css";
import {Route, Switch} from "react-router-dom";
import {TrainingModelList} from "../ModelManagement/TrainingModelList";
import {DataPrediction} from "../ModelManagement/DataPrediction";
import {CreateModelSection} from "../ModelManagement/CreateModelSection";

type Props = {
  algorithmName: string;
};

export const Judgement: React.FC<Props> = ({algorithmName}) => {

  return (
    <Page>
      <TabHeader
        headers={[
          {
            id: "read",
            title: algorithmName == "linear" || algorithmName == "lasso" ? "잔존수명 예지 결과 조회" : "고장진단 결과 조회",
          },
          {
            id: "judge",
            title: algorithmName == "linear" || algorithmName == "lasso" ? "잔존수명 작업자 판정" : "고장전조 작업자 판정",
          },
        ]}
      />
      <div className={styles.page}>
        <Switch>
          {/*   <Route
            path="/ml/:algorithmName/models"
            render={() => <TrainingModelList algorithmName={algorithmName}/>}
          />
          <Route
            path="/ml/:algorithmName/predict"
            render={() => <DataPrediction algorithmName={algorithmName}/>}
          />
          <Route
            path="/ml/:algorithmName/train"
            render={() => <CreateModelSection algorithmName={algorithmName}/>}
          />
          <Route
            path="/ml/:algorithmName/"
            render={() => <CreateModelSection algorithmName={algorithmName}/>}
          />*/}
        </Switch>
      </div>
    </Page>
  );
};
