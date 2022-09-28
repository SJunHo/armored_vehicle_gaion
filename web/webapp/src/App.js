import React, { Component } from "react";
import { connect } from "react-redux";
import { Router, Switch, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import HeaderComp from "./components/header.component";
import Login from "./components/login/login.component";

/*통계정보*/
import Statistical from "./components/analysis/StatisticalInfo/statistical.component";
import searchEachInfo from "./components/analysis/StatisticalInfo/vehicleinformation.component";
import driverPostureCorrection from  "./components/analysis/StatisticalInfo/driverPostureCorrection.component"
import PartsReplacementCycle from "./components/analysis/StatisticalInfo/partsreplacementcycle.component"

/*학습데이터 수집*/
import LearningData from "./components/analysis/LearningDataCollection/learningdata.component";

/*모델관리*/
import { OpenApiContextProvider } from "./api";

/*잔존수명예지 모델*/
import LinearRegression from "./components/analysis/MRLF/linearregression.component";
import RasoRegession from "./components/analysis/MRLF/rasoregession.component";

/*시스템 설정*/
import Driver_CIS from "./components/analysis/SystemSettings/driver_cis.component";
import Setting_PRC from "./components/analysis/SystemSettings/setting_prc.component";
import SettingThresholds from "./components/analysis/SystemSettings/settingthresholds.component";
import CIM from "./components/analysis/SystemSettings/cim.component";
import CIMList from "./components/analysis/SystemSettings/cim-list.component";
import AddCIM from "./components/analysis/SystemSettings/add-cim.component";
import ManageUsers from "./components/analysis/SystemSettings/manageusers.component";
import AddUser from "./components/analysis/SystemSettings/add-user.component";

/*전자 매뉴얼*/
import Electronmanual from "./components/analysis/ElectronManual/electronmanual.component";
import { logout } from "./actions/login/auth";
import { clearMessage } from "./actions/login/message";

import { history } from './helpers/history';

// import AuthVerify from "./common/auth-verify";
import EventBus from "./common/EventBus";
import ManageUsersList from "./components/analysis/SystemSettings/manageusers-list.component";
import PublicRoute from "./components/login/PublicRoute";
import PrivateRoute from "./components/login/PrivateRoute";

import LogOutComp from "./components/login/logout.component";

import { AuthorizedRoute } from "./common/AuthorizedRoute";
import {DataUpload} from "./DataLookUp/DataUpload";
import {ModelManagement} from "./ModelManagement/ModelManagement";
import {Dashboard} from "./Dashboard/Dashboard";
import {PredictedResults} from "./ResultPrediction/PredictedResults";
import {PredictedResultsUserInput} from "./ResultPredictionUserInput/PredictedResultsUserInput";
import {SavedPredictedResult} from "./SavedResultPrediction/SavedPredictedResult";
import {FeatureSelection} from "./ModelManagement/FeatureSelection";
import {DatasetManagement} from "./DatasetManagement/DatasetManagement";
import {DataLookUpList} from "./DataLookUp/DataLookUpList";
import { MeContextProvider } from "./api/MeContext";
class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      currentUser: undefined,
    };

    history.listen((location) => {
      props.dispatch(clearMessage()); // clear message when changing location
    });
  }

  componentDidMount() {
    const user = this.props.user;

    if (user) {
      this.setState({
        currentUser: user,
      });
    }

    EventBus.on("logout", () => {
      this.logOut();
    });
  }

  componentWillUnmount() {
    EventBus.remove("logout");
  }

  logOut() {
    this.props.dispatch(logout());
    this.setState({
      currentUser: undefined,
    });
  }

  render() {
    const { currentUser } = this.state;

    return (

      <div>
        {
          currentUser && (<HeaderComp />)
        }
        <Router history={history}>
          <Switch>
            <Route exact path="/" component={Login}/>
            <Route exact path="/logout" component={LogOutComp}/>

              {/*통계정보*/}
            <PrivateRoute exact path="/statistical" component={Statistical} />
            <PrivateRoute exact path="/searchEachInfo" component={searchEachInfo} />
            <PrivateRoute exact path="/searchEachInfo/:id" component={searchEachInfo} />
            <PrivateRoute exact path="/driverPostureCorrection" component={driverPostureCorrection} />
            <PrivateRoute exact path="/partsreplacementcycle" component={PartsReplacementCycle} />

              {/*학습 데이터*/}
            <PrivateRoute exact path="/data/upload" component={DataUpload} />

              {/*고장진단 모델*/}
            {/*<PrivateRoute exact path="/ml/rfc" component={Model} />*/}
            {/*<PrivateRoute exact path="/ml/svc" component={SupportVectorMachine} />*/}
            {/*<PrivateRoute exact path="/ml/mlp" component={MultilayerNeuralNetworks} />*/}
            {/*<PrivateRoute exact path="/ml/if" component={IsolateRamdhamForest} />*/}
            {/*<PrivateRoute exact path="/ml/lr" component={LogicicRegession} />*/}
            {/* <PrivateRoute path="/dashboard" exact component={Dashboard} />
            <PrivateRoute
              path="/data/fault_diagnosis_result_history"
              component={PredictedResults}
            />
            <PrivateRoute
              path="/data/fault_diagnosis_user_input"
              component={PredictedResultsUserInput}
            />
            <PrivateRoute
              path="/data/fault_diagnosis_history_page"
              component={SavedPredictedResult}
            />

            <PrivateRoute
              path="/ml/fs/:algorithmName"
              exact
              component={FeatureSelection}
            />
            <MeContextProvider>
              <OpenApiContextProvider>
            <PrivateRoute
              path="/ml/:algorithmName/:tab"
              component={ModelManagement}
            />
            <Route
              path="/ml/:algorithmName"
              exact
              component={ModelManagement}
            />
            </OpenApiContextProvider>
            </MeContextProvider>
            <Route path="/dataset" component={DatasetManagement} />

            <PrivateRoute path="/data/lookup" component={DataLookUpList} />
            <PrivateRoute path="/data/upload" component={DataUpload} /> */}
            {/* <OpenApiContextProvider>
              <PrivateRoute path="/ml/:algorithmName/:tab" component={ModelManagement} />
              <PrivateRoute path="/ml/:algorithmName" exact component={ModelManagement} />
            </OpenApiContextProvider> */}



              {/*잔존수명예지 모델*/}
            <PrivateRoute exact path="/linearregression" component={LinearRegression} />
            <PrivateRoute exact path="/rasoregression" component={RasoRegession} />

              {/*시스템 설정*/}
            <PrivateRoute exact path="/driver_cis" component={Driver_CIS} />
            <PrivateRoute exact path="/setting_prc" component={Setting_PRC} />
            <PrivateRoute exact path="/settingthresholds" component={SettingThresholds} />
            <PrivateRoute exact path="/cimList" component={CIMList} />
            <PrivateRoute exact path="/cimAdd" component={AddCIM} />
            <PrivateRoute exact path="/cim/:id" component={CIM} />
            <PrivateRoute exact path="/manageusers" component={ManageUsersList} />
            <PrivateRoute exact path="/addUser" component={AddUser} />
            <PrivateRoute exact path="/manageuser/:id" component={ManageUsers} />

              {/*전자 매뉴얼*/}
            <PrivateRoute exact path="/electronmanual" component={Electronmanual} />
          </Switch>
        <MeContextProvider>
        <OpenApiContextProvider>
          <div className="content-wrapper">
            <Switch>
              <AuthorizedRoute path="/dashboard" exact component={Dashboard} />
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

              <AuthorizedRoute
                path="/ml/fs/:algorithmName"
                exact
                component={FeatureSelection}
              />

              <AuthorizedRoute
                path="/ml/:algorithmName/:tab"
                component={ModelManagement}
              />
              <Route
                path="/ml/:algorithmName"
                exact
                component={ModelManagement}
              />
              <Route path="/dataset" component={DatasetManagement} />

              <AuthorizedRoute path="/data/lookup" component={DataLookUpList} />
              <AuthorizedRoute path="/data/upload" component={DataUpload} />
              <AuthorizedRoute path="/" exact component={DataUpload} />
            </Switch>
          </div>
        </OpenApiContextProvider>
      </MeContextProvider>
        </Router>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { user } = state.auth;
  return {
    user,
  };
}

export default connect(mapStateToProps)(App);
