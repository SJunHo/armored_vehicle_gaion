import React from "react";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
// import styles from "./common.module.css";

export const InputWrapper: React.FC<Omit<React.HTMLAttributes<HTMLDivElement>, "title"> & {
  title: string | React.ReactComponentElement<any, any>;
  rowLayout?: boolean;
  labelWidth?: number;
}> = ({title, labelWidth, rowLayout = false, children, className}) => {
  return (
    <Form.Group
      as={rowLayout ? Row : Col}
      className={`mb-3 ${className || ""}`}
    >
      <Form.Label column sm={labelWidth} style={{color: "white"}}>
        {title}
      </Form.Label>
      <Col sm={12 - (labelWidth || 0)}>{children}</Col>
    </Form.Group>
  );
};

type AlgorithmInfo = {
  name: string;
  className: string;
};

export const ALGORITHM_INFO: { [key: string]: AlgorithmInfo } = {
  rfc: {
    name: "Random Forest Classifier",
    className: "RandomForestClassifier",
  },
  svc: {
    name: "Linear Support Vector Classifier",
    className: "SVCClassifier",
  },
  lr: {
    name: "Logistic Regression",
    className: "LogisticRegression",
  },
  mlp: {
    name: "Multi-layer Perceptron",
    className: "MLPClassifier",
  },
  if: {
    name: "Isolation Forest",
    className: "IsolationForestOutlierDetection",
  },
  linear: {
    name: "Linear Regression",
    className: "LinearRegression",
  },
  lasso: {
    name: "Lasso Regression",
    className: "LassoRegression",
  },
};

// <div
//       className={`${styles.formInputWrapper} ${rowLayout ? styles.row : ""} ${
//         className || ""
//       }`}
//       {...props}
//     >
//       <div style={{ width: labelWidth || "auto" }}>{title}</div>
//       {children}
//     </div>
