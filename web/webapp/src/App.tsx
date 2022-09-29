import React from 'react'
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {OpenApiContextProvider} from "./api";
import "./App.css";
import {Header} from "./common/Header/Header";
import {DatasetManagement} from "./DatasetManagement/DatasetManagement";
import "./i18n";
import {ModelManagement} from "./ModelManagement/ModelManagement";
import {AuthorizedRoute} from "./common/AuthorizedRoute";
import {Login} from "./Auth/Login";
import {Logout} from "./Auth/Logout";
import {MeContextProvider} from "./api/MeContext";
import {DataLookUpList} from "./DataLookUp/DataLookUpList";
import {DataUpload} from "./DataLookUp/DataUpload";
import {PredictedResults} from "./ResultPrediction/PredictedResults";
import {PredictedResultsUserInput} from "./ResultPredictionUserInput/PredictedResultsUserInput";
import {SavedPredictedResult} from "./SavedResultPrediction/SavedPredictedResult";

import {FeatureSelection} from "./ModelManagement/FeatureSelection";
import {Dashboard} from "./Dashboard/Dashboard";

function App() {
  return (
    <Router>
      <Switch>
        {/*<Route path="/auth/login" component={Login} />*/}
        {/*<Route path="/auth/logout" component={Logout} />*/}
        <Route path="/" component={Main}/>
      </Switch>
    </Router>
  );
}

function Main() {
  return (
    <MeContextProvider>
      <OpenApiContextProvider>
        <Header/>
        <Switch>
          <AuthorizedRoute
            path="/data/upload"
            component={PredictedResults}
          />
          <AuthorizedRoute
            path="/data/fault_diagnosis_result_history"
            component={PredictedResults}
          />
          <AuthorizedRoute
            path="/data/fault_diagnosis_user_input"
            component={PredictedResultsUserInput}
          />
          <AuthorizedRoute
            path="/data/fault_diagnosis_history_page"
            component={SavedPredictedResult}
          />

          {/*<AuthorizedRoute*/}
          {/*  path="/ml/fs/:algorithmName"*/}
          {/*  exact*/}
          {/*  component={FeatureSelection}*/}
          {/*/>*/}

          <AuthorizedRoute
            path="/ml/:algorithmName/:tab"
            component={ModelManagement}
          />
          <Route
            path="/ml/:algorithmName"
            exact
            component={ModelManagement}
          />
          {/*<Route path="/dataset" component={DatasetManagement} />*/}

        </Switch>
      </OpenApiContextProvider>
    </MeContextProvider>
  );
}

export default App;
