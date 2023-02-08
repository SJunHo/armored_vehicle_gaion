import React, {Component} from "react";
import {connect} from "react-redux";
import {Container, Nav, Navbar, NavDropdown} from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

import {logout} from "../actions/login/auth";
import {clearMessage} from "../actions/login/message";

import {history} from '../helpers/history';

// import AuthVerify from "./common/auth-verify";
import EventBus from "../common/EventBus";
import {
  FaAngleDoubleLeft,
  FaAngleDoubleRight,
  FaChartBar,
  FaCog,
  FaDatabase,
  FaExclamationCircle,
  FaExclamationTriangle,
  FaFileAlt,
  FaSignOutAlt
} from "react-icons/fa";

class HeaderComp extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
    this.handleSelectedNavbar = this.handleSelectedNavbar.bind(this);
    this.state = {
      currentUser: undefined,
      selectedNavbar: false,
      selectedShow: true,
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

    window.onclick = function (event) {
      //.screen-darken이 존재하는 경우에만 remove처리함
      if (document.querySelector('.screen-darken')) {
        document.body.removeChild(document.querySelector('.screen-darken'));
      }
      if (event.target.closest(".nav-item.dropdown.show")) {
        const el_overlay = document.createElement('span');
        el_overlay.className = 'screen-darken';
        document.body.appendChild(el_overlay)
      }
    }
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevState.selectedNavbar !== this.state.selectedNavbar) {
      let elm = document.getElementsByClassName("dropdown");
      let seleted = null;
      Object.entries(elm).forEach((el) => {
        if (el[1].classList.value.includes("selected")) {
          seleted = el[1].classList.value;
        }
      });
      if (seleted === null) {
        this.setState({
          selectedShow: true
        });
      } else {
        if (seleted.includes("show") === true) {
          this.setState({
            selectedShow: false
          });
        } else {
          this.setState({
            selectedShow: true
          });
        }
      }
    }
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

  toggleCollapse(param) {
    const navbar = document.getElementsByClassName('navbar');
    navbar[0].classList.toggle("active");
  }


  handleSelectedNavbar(e) {
    this.setState({
      selectedNavbar: !this.state.selectedNavbar
    });
  }

  render() {
    const {currentUser} = this.state;
    const location = window.location.pathname;

    return (
      <Navbar variant="dark" expand="lg">
        <Container fluid>
          <Navbar.Brand href="/">SCAS</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav>
              <NavDropdown
                id="nav-dropdown-dark"
                title={<><FaChartBar/> <span>통계정보  &gt;</span></>}
                menuVariant="dark"
                className={
                  this.state.selectedShow === true &&
                  (location.includes("/statistical") ? "selected"
                    : location.includes("/searchEachInfo") ? "selected"
                      : location.includes("/driverPostureCorrection") ? "selected"
                        : location.includes("/partsreplacementcycle") ? "selected" : "")}
                onClick={(e) => this.handleSelectedNavbar(e)}
              >
                <NavDropdown.Item href="/statistical">통계정보 조회</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/searchEachInfo">차량정보 조회</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/driverPostureCorrection">운전자 교정 정보</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/partsreplacementcycle">부품 교환주기</NavDropdown.Item>
              </NavDropdown>

              <NavDropdown
                id="nav-dropdown-dark-example"
                title={<><FaDatabase/> <span>학습데이터 수집  &gt;</span></>}
                menuVariant="dark"
                className={
                  this.state.selectedShow === true &&
                  (location.includes("/learningdata") ? "selected" : "")}
                onClick={(e) => this.handleSelectedNavbar(e)}
              >
                <NavDropdown.Item href="/data/classification-upload">[고장예지] 학습 데이터</NavDropdown.Item>
                <NavDropdown.Item href="/data/regression-upload">[잔존수명] 학습 데이터</NavDropdown.Item>
              </NavDropdown>

              <NavDropdown
                id="nav-dropdown-dark-example"
                title={<><FaExclamationCircle/> <span>고장예지 모델  &gt;</span></>}
                menuVariant="dark"
                className={
                  this.state.selectedShow === true &&
                  (location.includes("/ml/rfc") ? "selected"
                    : location.includes("/ml/svc") ? "selected"
                      : location.includes("/ml/mlp") ? "selected"
                        : location.includes("/ml/is") ? "selected"
                          : location.includes("/ml/lr") ? "selected"
                            : location.includes("/monitordiagnostictroublealerts") ? "selected" : "")}
                onClick={(e) => this.handleSelectedNavbar(e)}
              >
                <NavDropdown.Item href="/ml/rfc">랜덤 포레스트</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/ml/svc">서포트 벡터 머신</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/ml/mlp">다층 신경망</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/ml/if">Isolate 랜덤 포레스트</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/ml/lr">로지스틱 회귀</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/judgement">작업자 판정</NavDropdown.Item>
              </NavDropdown>

              <NavDropdown
                id="nav-dropdown-dark-example"
                title={<><FaExclamationTriangle/> <span>잔존수명예지 모델  &gt;</span></>}
                menuVariant="dark"
                className={
                  this.state.selectedShow === true &&
                  (location.includes("/ml/linear") ? "selected"
                    : location.includes("/ml/lasso") ? "selected"
                      : location.includes("/monitorremainingusefullife") ? "selected" : "")}
                onClick={(e) => this.handleSelectedNavbar(e)}
              >
                <NavDropdown.Item href="/ml/linear">선형 회귀</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/ml/lasso">라소 회귀</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/monitorremainingusefullife">AI 잔존수명예지 결과 조회</NavDropdown.Item>
              </NavDropdown>

              <NavDropdown
                id="nav-dropdown-dark-example"
                title={<><FaCog/> <span>시스템 설정  &gt;</span></>}
                className={
                  this.state.selectedShow === true &&
                  (location.includes("/driver_cis") ? "selected"
                    : location.includes("/Setting_prc") ? "selected"
                      : location.includes("/settingthresholds") ? "selected"
                        : location.includes("/cimList") ? "selected"
                          : location.includes("/manageusers") ? "selected" : "")}
                onClick={(e) => this.handleSelectedNavbar(e)}
                menuVariant="dark"
              >
                <NavDropdown.Item href="/driver_cis">운전자 교정정보 설정</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/Setting_prc">부품 교환주기 설정</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/settingthresholds">임계값 설정</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/cimList">공통정보 관리</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/manageusers">사용자 관리</NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item href="/userLogList">사용자 로그인 기록</NavDropdown.Item>
              </NavDropdown>

              <NavDropdown
                id="nav-dropdown-dark-example"
                title={<><FaFileAlt/> <span>전자매뉴얼  &gt;</span></>}
                menuVariant="dark"
                className={
                  this.state.selectedShow === true &&
                  (location.includes("/electronmanual") ? "selected" : "")}
                onClick={(e) => this.handleSelectedNavbar(e)}

              >
                <NavDropdown.Item href="/electronmanual">전자메뉴얼</NavDropdown.Item>
              </NavDropdown>
            </Nav>
            <div className="mt-5"></div>
            <Nav className="gnb-close">
              <Nav.Link className="nav-item-2" onClick={() => {
                this.toggleCollapse();
              }}>
                <div className="full"><FaAngleDoubleLeft/> <span>접기</span></div>
                <div className="collapse"><FaAngleDoubleRight/></div>
              </Nav.Link>
            </Nav>
            {
              currentUser && (
                <Nav className="ml-auto btn01">
                  <Nav.Link className="nav-item-2" href="/" onClick={this.logOut}>
                    <FaSignOutAlt/><span>로그아웃</span>
                  </Nav.Link>
                </Nav>
              )
            }
          </Navbar.Collapse>
        </Container>
      </Navbar>

    );
  }
}

function mapStateToProps(state) {
  const {user} = state.auth;
  return {
    user,
  };
}

export default connect(mapStateToProps)(HeaderComp);