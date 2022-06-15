import React, { Component } from "react";
import { connect } from "react-redux";
import { Router, Switch, Route, Link } from "react-router-dom";
import {Navbar, Container, Nav, NavDropdown} from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import Login from "./components/login/login.component";
import Register from "./components/login/register.component";
import Home from "./components/home.component";
import Profile from "./components/login/profile.component";
import BoardUser from "./components/board-user.component";
import BoardModerator from "./components/board-moderator.component";
import BoardAdmin from "./components/board-admin.component";

import Tutorial from "./components/monitoring/tutorial.component";
import TutorialsList from "./components/monitoring/tutorials-list.component";
import AddTutorial from "./components/monitoring/add-tutorial.component"
import ExistingSensorData from "./components/monitoring/DataCollection/existingsensordata.component";
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
      showModeratorBoard: false,
      showAdminBoard: false,
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
        showModeratorBoard: user.roles.includes("ROLE_MODERATOR"),
        showAdminBoard: user.roles.includes("ROLE_ADMIN"),
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
      showModeratorBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    });
  }

  render() {
    const { currentUser, showModeratorBoard, showAdminBoard } = this.state;

    return (
      <Router history={history}>
        <div>
        <Navbar variant="dark" bg="dark" expand="lg">
          <Container fluid>
          <Navbar.Brand href="/">SCAS</Navbar.Brand>
            <Navbar.Toggle aria-controls="navbar-dark-example" />
            <Navbar.Collapse id="navbar-dark-example">
              <Nav>
                <NavDropdown
                  id="nav-dropdown-dark-example"
                  title="통계정보"
                  menuVariant="dark"
                >
                  <NavDropdown.Item href="/tutorials">통계정보 조회</NavDropdown.Item>
                  <NavDropdown.Item href="#action/3.2">이상치 차량 조회</NavDropdown.Item>
                  <NavDropdown.Item href="#action/3.3">차량정보 조회</NavDropdown.Item>
                  <NavDropdown.Item href="#action/3.4">운전자 교정 정보</NavDropdown.Item>
                </NavDropdown>
              </Nav>
              <Nav>
                <NavDropdown
                  id="nav-dropdown-dark-example"
                  title="데이터수집"
                  menuVariant="dark"
                >
                  <NavDropdown.Item href="/existingsensordata">추가 센서 데이터</NavDropdown.Item>
                  <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                  <NavDropdown.Divider />
                  <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item>
                </NavDropdown>
              </Nav>
            </Navbar.Collapse>
          </Container>
        </Navbar>
          <nav className="navbar navbar-expand navbar-dark bg-dark">
            <div className="navbar-nav mr-auto">
              <li className="nav-item">
                <Link to={"/home"} className="nav-link">
                  Home
                </Link>
              </li>
              {showModeratorBoard && (
                <li className="nav-item">
                  <Link to={"/mode"} className="nav-link">
                    Moderate Board
                  </Link>
                </li>
              )}

              {currentUser && (
                <li className="nav-item">
                  <Link to={"/tutorials"} className="nav-link">
                    tutorialList
                  </Link>
                </li>
              )}

              {currentUser && (
                <li className="nav-item">
                  <Link to={"/existingsensordata"} className="nav-link">
                    기존 센서 데이터
                  </Link>
                </li>
              )}

              {showAdminBoard && (
                <li className="nav-item">
                  <Link to={"/admin"} className="nav-link">
                    Admin Board
                  </Link>
                </li>
              )}

              {currentUser && (
                <li className="nav-item">
                  <Link to={"/user"} className="nav-link">
                    User
                  </Link>
                </li>
              )}
            </div>

            {currentUser ? (
              <div className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link to={"/profile"} className="nav-link">
                    {currentUser.username}
                  </Link>
                </li>
                <li className="nav-item">
                  <a href="/login" className="nav-link" onClick={this.logOut}>
                    LogOut
                  </a>
                </li>
              </div>
            ) : (
              <div className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link to={"/login"} className="nav-link">
                    Login
                  </Link>
                </li>

                <li className="nav-item">
                  <Link to={"/register"} className="nav-link">
                    Sign Up
                  </Link>
                </li>
              </div>
            )}
          </nav>

          <div className="container mt-3">
            <Switch>
              <Route exact path={["/", "/home"]} component={Home} />
              <Route exact path="/login" component={Login} />
              <Route exact path="/register" component={Register} />
              <Route exact path="/profile" component={Profile} />
              <Route exact path="/user" component={BoardUser} />
              <Route exact path="/mode" component={BoardModerator} />
              <Route exact path="/tutorials" component={TutorialsList} />
              <Route exact path="/existingsensordata" component={ExistingSensorData} />
              <Route exact path="/add" component={AddTutorial} />
              <Route exact path="/tutorials/:id" component={Tutorial} />
              <Route exact path="/admin" component={BoardAdmin} />
            </Switch>
          </div>

          {/* <AuthVerify logOut={this.logOut}/> */}
        </div>
      </Router>
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
