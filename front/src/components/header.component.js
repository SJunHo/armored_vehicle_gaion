import React, { Component } from "react";
import { connect } from "react-redux";
import { Navbar, Container, Nav, NavDropdown } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

import { logout } from "../actions/login/auth";
import { clearMessage } from "../actions/login/message";

import { history } from '../helpers/history';

// import AuthVerify from "./common/auth-verify";
import EventBus from "../common/EventBus";

class HeaderComp extends Component {
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
      <Navbar variant="dark" bg="dark" expand="lg">
        <Container fluid>
          <Navbar.Brand href="/">SCAS</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav>
              <NavDropdown
                id="nav-dropdown-dark"
                title="통계정보"
                menuVariant="dark"
              >
                <NavDropdown.Item href="/statistical">통계정보 조회</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/vehicleinformation">차량정보 조회</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/driverPostureCorrection">운전자 교정 정보</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/partsreplacementcycle">부품 교환주기</NavDropdown.Item>
              </NavDropdown>

              <NavDropdown
                id="nav-dropdown-dark-example"
                title="학습데이터 수집"
                menuVariant="dark"
              >
                <NavDropdown.Item href="/learningdata">학습 데이터</NavDropdown.Item>
              </NavDropdown>

              <NavDropdown
                id="nav-dropdown-dark-example"
                title="고장진단 모델"
                menuVariant="dark"
              >
                <NavDropdown.Item href="/randomforest">램덤포레스트</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/supportvectormachine">서포트 벡터 머신</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/multilayerneuralnetworks">다층 신경망</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/isolateramdhamforest">Isolate 램덤포레스트</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/logicicregression">로직스틱 회귀</NavDropdown.Item>
              </NavDropdown>

              <NavDropdown
                id="nav-dropdown-dark-example"
                title="잔존수명예지 모델"
                menuVariant="dark"
              >
                <NavDropdown.Item href="/linearregression">선형 회귀</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/rasoregression">라소 회귀</NavDropdown.Item>
              </NavDropdown>


              <NavDropdown
                id="nav-dropdown-dark-example"
                title="시스템 설정"
                menuVariant="dark"
              >
                <NavDropdown.Item href="/driver_cis">운전자 교정정보 설정</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/Setting_prc">부품 교환주기 설정</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/settingthresholds">임계값 설정</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/cimList">공통정보 관리</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/manageusers">사용자 관리</NavDropdown.Item>
              </NavDropdown>

              <NavDropdown
                id="nav-dropdown-dark-example"
                title="전자메뉴얼"
                menuVariant="dark"
              >
                <NavDropdown.Item href="/electronmanual">전자메뉴얼</NavDropdown.Item>
              </NavDropdown>
            </Nav>

            {
              currentUser && (
                <Nav className="ml-auto">
                  <Nav.Link href="/" onClick={this.logOut}>
                    LogOut
                  </Nav.Link>
                </Nav>
              )
            }
          </Navbar.Collapse >
        </Container >
      </Navbar >

    );
  }
}

function mapStateToProps(state) {
  const { user } = state.auth;
  return {
    user,
  };
}

export default connect(mapStateToProps)(HeaderComp);