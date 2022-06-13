import React from "react";
import Card from "react-bootstrap/Card";
import Col from "react-bootstrap/Col";
import { useTranslation } from "react-i18next";

export interface RailConditionDataCardWithScorceProps {
  label: string;
  value?: number;
  score?: number;
}

export const RailConditionDataCardWithScorce: React.FC<RailConditionDataCardWithScorceProps> =
  ({ label, value, score }) => {
    const { t } = useTranslation();

    return (
      <>
        <Col className="col mb-2">
          <Card className="card border-0 rounded-0 m-0 p-0">
            <Card.Body className="card border-0 rounded-0 p-0">
              <Card.Title className="card border-0 fs-6 border-light rounded-0 text-start ps-2 pt-2 pb-0 mb-0">
                {label}
              </Card.Title>
              <Card.Text className="card border-0 border-light rounded-0 text-start fs-3 text-danger ps-2 pb-0 m-0">
                {value}
              </Card.Text>
            </Card.Body>
            <Card.Footer className="card border-0 rounded-0 p-0 sm text-center bg-success text-light">
              {t("history.score")} {score}
            </Card.Footer>
          </Card>
        </Col>
      </>
    );
  };
