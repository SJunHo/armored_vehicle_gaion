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

/*모델관리*/
import { OpenApiContextProvider } from "./api";

/*시스템 설정*/
import Driver_CIS from "./components/analysis/SystemSettings/driver_cis.component";
import Setting_PRC from "./components/analysis/SystemSettings/setting_prc.component";
import SettingThresholds from "./components/analysis/SystemSettings/settingthresholds.component";
import LifeThresholds from "./components/analysis/SystemSettings/lifethresholds.component";
import CIM from "./components/analysis/SystemSettings/cim.component";
import CIMList from "./components/analysis/SystemSettings/cim-list.component";
import AddCIM from "./components/analysis/SystemSettings/add-cim.component";
import ManageUsers from "./components/analysis/SystemSettings/manageusers.component";
import AddUser from "./components/analysis/SystemSettings/add-user.component";
import UserLog from "./components/analysis/SystemSettings/userlog-list.component";
import MonitorRemaining from "./components/analysis/FaultDiagnosisModel/monitorremainingusefullife.component";
/*전자 매뉴얼*/
import Electronmanual from "./components/analysis/ElectronManual/electronmanual.component";
import { logout } from "./actions/login/auth";
import { clearMessage } from "./actions/login/message";

import { history } from './helpers/history';

// import AuthVerify from "./common/auth-verify";
import EventBus from "./common/EventBus";
import ManageUsersList from "./components/analysis/SystemSettings/manageusers-list.component";
import PrivateRoute from "./components/login/PrivateRoute";

import LogOutComp from "./components/login/logout.component";

import { AuthorizedRoute } from "./common/AuthorizedRoute";
import {ModelManagement} from "./ModelManagement/ModelManagement";
import { MeContextProvider } from "./api/MeContext";
import { Judgement} from "./Judgement/Judgement";

import {ClassificationDataUpload} from "./DataLookUp/ClassificationDataUpload";
import {RegressionDataUpload} from "./DataLookUp/RegressionDataUpload";

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

              {/*시스템 설정*/}
            <PrivateRoute exact path="/driver_cis" component={Driver_CIS} />
            <PrivateRoute exact path="/setting_prc" component={Setting_PRC} />
            <PrivateRoute exact path="/settingthresholds" component={SettingThresholds} />
            <PrivateRoute exact path="/lifethresholds" component={LifeThresholds} />
            <PrivateRoute exact path="/cimList" component={CIMList} />
            <PrivateRoute exact path="/cimAdd" component={AddCIM} />
            <PrivateRoute exact path="/cim/:id" component={CIM} />
            <PrivateRoute exact path="/manageusers" component={ManageUsersList} />
            <PrivateRoute exact path="/addUser" component={AddUser} />
            <PrivateRoute exact path="/manageuser/:id" component={ManageUsers} />
            <PrivateRoute exact path="/userLogList" component={UserLog} />

            <PrivateRoute exact path="/monitorremainingusefullife" component={MonitorRemaining} />
              {/*전자 매뉴얼*/}
            <PrivateRoute exact path="/electronmanual" component={Electronmanual} />
          </Switch>
        <MeContextProvider>
        <OpenApiContextProvider>
            <Switch>
              <AuthorizedRoute
                path="/ml/:algorithmName/:tab"
                component={ModelManagement}
              />
              <AuthorizedRoute
                path="/ml/:algorithmName"
                exact
                component={ModelManagement}
              />
              <AuthorizedRoute
                path="/judgement"
                exact
                component={Judgement}
              />
              <AuthorizedRoute
                path="/judgement/:tab"
                exact
                component={Judgement}
              />
              <AuthorizedRoute path="/data/classification-upload" exact component={ClassificationDataUpload} />
              <AuthorizedRoute path="/data/regression-upload" exact component={RegressionDataUpload} />
            </Switch>
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
