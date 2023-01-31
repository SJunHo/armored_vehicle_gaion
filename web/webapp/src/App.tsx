import React from 'react'
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {OpenApiContextProvider} from "./api";
import "./App.css";
import {Header} from "./common/Header/Header";
import "./i18n";
import {ModelManagement} from "./ModelManagement/ModelManagement";
import {AuthorizedRoute} from "./common/AuthorizedRoute";
import {MeContextProvider} from "./api/MeContext";
import {ClassificationDataUpload} from "./DataLookUp/ClassificationDataUpload";
import {RegressionDataUpload} from "./DataLookUp/RegressionDataUpload";


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
            path="/data/classification-upload"
            component={ClassificationDataUpload}
          />
          <AuthorizedRoute
            path="/data/regression-upload"
            component={RegressionDataUpload}
          />
          <AuthorizedRoute
            path="/ml/:algorithmName/:tab"
            component={ModelManagement}
          />
          <AuthorizedRoute
            path="/ml/:algorithmName"
            exact
            component={ModelManagement}
          />
        </Switch>
      </OpenApiContextProvider>
    </MeContextProvider>
  );
}

export default App;
