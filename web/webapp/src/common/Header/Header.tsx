import React, { useContext } from "react";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { useTranslation } from "react-i18next";
import styles from "./styles.module.css";
import { MeContext } from "../../api/MeContext";

export const Header: React.FC = () => {
  const { t } = useTranslation();
  const { user } = useContext(MeContext);
  console.log(user);
  return (
    <Navbar fixed="top" expand="lg" variant="dark" className={styles.navbar}>
      <Container fluid={true}>
        <Navbar.Collapse className="collapse">
          <Nav>
            {/*<Nav.Link href={"/dashboard"}>*/}
            {/*  <>*/}
            {/*    <span className="glyphicon glyphicon-book" />*/}
            {/*    Dashboard*/}
            {/*    <span className="glyphicon glyphicon-menu-down" />*/}
            {/*  </>*/}
            {/*</Nav.Link>*/}
            {/*<Nav.Link href={"/dataset"}>*/}
            {/*  <>*/}
            {/*    <span className="glyphicon glyphicon-book" />*/}
            {/*    {t("Status Data Tracking")}*/}
            {/*    <span className="glyphicon glyphicon-menu-down" />*/}
            {/*  </>*/}
            {/*</Nav.Link>*/}

            {/*<NavDropdown*/}
            {/*  title={*/}
            {/*    <>*/}
            {/*      <span className="glyphicon glyphicon-book" />*/}
            {/*      {t("pp.header.lookup")}*/}
            {/*      <span className="glyphicon glyphicon-menu-down" />*/}
            {/*    </>*/}
            {/*  }*/}
            {/*>*/}
            {/*  <NavDropdown.Item href="/data/upload">*/}
            {/*    {t("pp.fu.up.upload")}*/}
            {/*  </NavDropdown.Item>*/}
            {/*  <NavDropdown.Item href="/data/lookup">*/}
            {/*    {t("pp.popup.header.lookupDef")}*/}
            {/*  </NavDropdown.Item>*/}
            {/*</NavDropdown>*/}

            <NavDropdown
              title={
                <>
                  <span className="glyphicon glyphicon-book" />
                  {t("pp.header.lookup")}
                  <span className="glyphicon glyphicon-menu-down" />
                </>
              }
            >
              <NavDropdown.Item href="/data/upload">
                {t("pp.fu.up.upload")}
              </NavDropdown.Item>

            </NavDropdown>

            <NavDropdown
              title={
                <>
                  <span className="glyphicon glyphicon-road" />
                  AI 모델 관리
                  <span className="glyphicon glyphicon-menu-down" />
                </>
              }
            >
              <NavDropdown.Header>전처리</NavDropdown.Header>
              <NavDropdown.Item href="/ml/fs/chisq">
                Chi-Square Selector
              </NavDropdown.Item>
              <NavDropdown.Item href="/ml/fs/pca">
                Principal Components Analysis (PCA)
              </NavDropdown.Item>
              <NavDropdown.Header>분류</NavDropdown.Header>
              <NavDropdown.Item href="/ml/svm">
                Linear Support Vector Machine
              </NavDropdown.Item>
              {/*<NavDropdown.Item href="/ml/svc">*/}
              {/*  Linear Support Vector Classifier*/}
              {/*</NavDropdown.Item>*/}
              <NavDropdown.Item href="/ml/rfc">Random Forest</NavDropdown.Item>
              <NavDropdown.Item href="/ml/mlp">
                Multiple Layers Perceptron
              </NavDropdown.Item>

              <NavDropdown.Item href="/ml/lr">
                Logistic Regression
              </NavDropdown.Item>
              <NavDropdown.Header>군집화</NavDropdown.Header>
              <NavDropdown.Item href="/ml/kmean">
                K-Means based Outlier Detection
              </NavDropdown.Item>
              <NavDropdown.Item href="/ml/if">
                Isolation Forest
              </NavDropdown.Item>
            </NavDropdown>

            <NavDropdown
              title={
                <>
                  <span className="glyphicon glyphicon-road" />
                  잔존 수명 예지
                  <span className="glyphicon glyphicon-menu-down" />
                </>
              }
            >
              <NavDropdown.Header>전처리</NavDropdown.Header>
              <NavDropdown.Item href="/ml/fs/chisq">
                Chi-Square Selector
              </NavDropdown.Item>
              <NavDropdown.Item href="/ml/fs/pca">
                Principal Components Analysis (PCA)
              </NavDropdown.Item>
              <NavDropdown.Header>회귀</NavDropdown.Header>
              <NavDropdown.Item href="/ml/linear">
                Linear Regression
              </NavDropdown.Item>
              <NavDropdown.Item href="/ml/lasso">
                Lasso Regression
              </NavDropdown.Item>
            </NavDropdown>


            <NavDropdown
              title={
                <>
                  <span className="glyphicon glyphicon-th" />
                  예측결과 조회
                  <span className="glyphicon glyphicon-menu-down" />
                </>
              }
            >
              <NavDropdown.Header>
                {t("Failure Diagnostics")}
              </NavDropdown.Header>
              <NavDropdown.Item href="/data/fault_diagnosis_result_history">
                {t("ML Failure Diagnostics")}
              </NavDropdown.Item>
              <NavDropdown.Item href="/data/fault_diagnosis_user_input">
                {t("Maintain User Input")}
              </NavDropdown.Item>
              <NavDropdown.Item href="/data/fault_diagnosis_history_page">
                {t("navbar.cd.fdrh")}
              </NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
        <Navbar.Collapse className="justify-content-end">
          <Nav>
            <Nav.Link href={"#"}>
              <span className="glyphicon glyphicon-cog" /> 설정
            </Nav.Link>
            <NavDropdown
              className="dropstart"
              title={
                <>
                  <span>{user?.username}</span>
                  <span className="fa fa-user-circle" />
                  <span className="glyphicon glyphicon-menu-down" />
                </>
              }
            >
              <NavDropdown.ItemText>
                사용자 :{" "}
                <strong>
                  {user?.firstName} {user?.lastName}
                </strong>
              </NavDropdown.ItemText>
              <NavDropdown.ItemText>권한 : [ROLE_ADMIN]</NavDropdown.ItemText>
              <NavDropdown.Item href="?lang=ko">한국어</NavDropdown.Item>
              <NavDropdown.Item href="?lang=en">English</NavDropdown.Item>
              <NavDropdown.Item href="/auth/logout">로그아웃</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};
