import React, {useContext, useEffect, useMemo, useState} from "react";
import {Page} from "../common/Page/Page";
import {TabHeader} from "../common/TabHeader/TabHeader";
import styles from "../ModelManagement/styles.module.css";
import {Route, Switch, useHistory, useParams} from "react-router-dom";
import {JudgementLookup} from "./JudgementLookup";
import {JudgementUserInput} from "./JudgementUserInput";


export const Judgement: React.FC = () => {
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
            title: "고장진단 결과 조회",
          },
          {
            id: "input",
            title: "고장전조 작업자 판정",
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
