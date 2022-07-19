import React, { Component } from "react";
import { connect } from "react-redux";
import { Router, Switch, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import HeaderComp from "./components/header.component";
import Login from "./components/login/login.component";
import Register from "./components/login/register.component";
import BoardUser from "./components/board-user.component";
import BoardModerator from "./components/board-moderator.component";


/*통계정보*/
import Statistical from "./components/statistical.component";
import VehicleInformation from "./components/vehicleinformation.component"
import DriverCalibration from "./components/drivercalibration.component"
import PartsReplacementCycle from "./components/partsreplacementcycle.component"

/*학습데이터 수집*/
import LearningData from "./components/analysis/LearningDataCollection/learningdata.component";

/*고장진단 모델*/
import RandomForest from "./components/analysis/FaultDiagnosisModel/randomforest.component";
import SupportVectorMachine from "./components/analysis/FaultDiagnosisModel/supportvectormachine.component";
import MultilayerNeuralNetworks from "./components/analysis/FaultDiagnosisModel/multilayerneuralnetworks.component";
import IsolateRamdhamForest from "./components/analysis/FaultDiagnosisModel/isolateramdhamforest.component";
import LogicicRegession from "./components/analysis/FaultDiagnosisModel/logicicregression.component";

/*잔존수명예지 모델*/
import LinearRegression from "./components/analysis/MRLF/linearregression.component";
import RasoRegession from "./components/analysis/MRLF/rasoregession.component";

/*시스템 설정*/
import Driver_CIS from "./components/analysis/SystemSettings/driver_cis.component";
import Setting_PRC from "./components/analysis/SystemSettings/setting_prc.component";
import SettingThresholds from "./components/analysis/SystemSettings/settingthresholds.component";
import CIM from "./components/analysis/SystemSettings/cim.component";
import ManageUsers from "./components/analysis/SystemSettings/manageusers.component";

/*전자 매뉴얼*/
import Electronmanual from "./components/analysis/ElectronManual/electronmanual.component";



import { logout } from "./actions/login/auth";
import { clearMessage } from "./actions/login/message";

import { history } from './helpers/history';

// import AuthVerify from "./common/auth-verify";
import EventBus from "./common/EventBus";

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
            <Route exact path="/register" component={Register}/>
            <Route exact path="/user" component={BoardUser}/>
            <Route exact path="/mode" component={BoardModerator}/>

              {/*통계정보*/}
            <Route exact path="/statistical" component={Statistical} />
            <Route exact path="/vehicleinformation" component={VehicleInformation} />
            <Route exact path="/drivercalibration" component={DriverCalibration} />
            <Route exact path="/partsreplacementcycle" component={PartsReplacementCycle} />

              {/*학습 데이터*/}
            <Route exact path="/learningdata" component={LearningData} />

              {/*고장진단 모델*/}
            <Route exact path="/randomforest" component={RandomForest} />
            <Route exact path="/supportvectormachine" component={SupportVectorMachine} />
            <Route exact path="/multilayerneuralnetworks" component={MultilayerNeuralNetworks} />
            <Route exact path="/isolateramdhamforest" component={IsolateRamdhamForest} />
            <Route exact path="/logicicregression" component={LogicicRegession} />


              {/*잔존수명예지 모델*/}
            <Route exact path="/linearregression" component={LinearRegression} />
            <Route exact path="/rasoregression" component={RasoRegession} />

              {/*시스템 설정*/}
            <Route exact path="/driver_cis" component={Driver_CIS} />
            <Route exact path="/setting_prc" component={Setting_PRC} />
            <Route exact path="/settingthresholds" component={SettingThresholds} />
            <Route exact path="/cim" component={CIM} />
            <Route exact path="/manageusers" component={ManageUsers} />

              {/*전자 매뉴얼*/}
            <Route exact path="/electronmanual" component={Electronmanual} />
          </Switch>
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
