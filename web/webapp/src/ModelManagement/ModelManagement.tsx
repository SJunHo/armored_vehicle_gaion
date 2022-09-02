import React from "react";
import { useTranslation } from "react-i18next";
import { Route, Switch, useHistory, useParams } from "react-router-dom";
import { Page } from "../common/Page/Page";
import { TabHeader } from "../common/TabHeader/TabHeader";
import { CreateModelSection } from "./CreateModelSection";
import { DataPrediction } from "./DataPrediction";
import styles from "./styles.module.css";
import { TrainingModelList } from "./TrainingModelList";

export const ModelManagement: React.FC = () => {
  const { t } = useTranslation();
  const { algorithmName, tab = "train" } =
    useParams<{ algorithmName: string; tab?: string }>();
  const history = useHistory();

  const handleChangeTab = (v: string) => {
    history.push(`/ml/${algorithmName}/${v}`);
  };

  return (
    <Page>
      <TabHeader
        headers={[
          {
            id: "train",
            title: algorithmName == "linear"?t("ml.common.tab.lifeCreate") : t("ml.common.tab.mg"),
          },
          {
            id: "models",
            title: algorithmName == "linear"?t("ml.common.tab.lifeMg") : t("table.column.manage"),
          },
          {
            id: "predict",
            title: algorithmName == "linear"?t("ml.common.tab.lifePredict") : t("ml.common.tab.troubleshot"),
          },
        ]}
        activeTabId={tab}
        onChangeActiveTab={(v) => handleChangeTab(v)}
      />
      <div className={styles.page}>
        <Switch>
          <Route
            path="/ml/:algorithmName/models"
            render={() => <TrainingModelList algorithmName={algorithmName} />}
          />
          <Route
            path="/ml/:algorithmName/predict"
            render={() => <DataPrediction algorithmName={algorithmName} />}
          />
          <Route
            path="/ml/:algorithmName/train"
            render={() => <CreateModelSection algorithmName={algorithmName} />}
          />
          <Route
            path="/ml/:algorithmName/"
            render={() => <CreateModelSection algorithmName={algorithmName} />}
          />
        </Switch>
      </div>
    </Page>
  );
};
