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
import {Route, Switch, useHistory, useParams} from "react-router-dom";
import {TrainingModelList} from "../ModelManagement/TrainingModelList";
import {DataPrediction} from "../ModelManagement/DataPrediction";
import {CreateModelSection} from "../ModelManagement/CreateModelSection";
import {JudgementLookup} from "./JudgementLookup";
import {JudgementUserInput} from "./JudgementUserInput";

type Props = {
  algorithmName: string;
};

export const Judgement: React.FC<Props> = ({algorithmName}) => {
  const {tab = "lookup"} = useParams<{ tab?: string }>()
  const history = useHistory();
  const handleChangeTab = (v: string) => {
    history.push(`/judgement/${v}`);
  };

  return (
    <Page>
      <TabHeader
        headers={[
          {
            id: "lookup",
            title: algorithmName == "linear" || algorithmName == "lasso" ? "잔존수명 예지 결과 조회" : "고장진단 결과 조회",
          },
          {
            id: "judge",
            title: algorithmName == "linear" || algorithmName == "lasso" ? "잔존수명 작업자 판정" : "고장전조 작업자 판정",
          },
        ]}
        activeTabId={tab}
        onChangeActiveTab={(v) => handleChangeTab(v)}
      />
      <div className={styles.page}>
        <Switch>
          <Route
            path="/judgement/lookup"
            render={() => <JudgementLookup/>}
          />
          <Route
            path="/judgement/input"
            render={() => <JudgementUserInput/>}
          />
          <Route
            path="/judgement/"
            render={() => <JudgementLookup/>}
          />
        </Switch>
      </div>
    </Page>
  );
};
