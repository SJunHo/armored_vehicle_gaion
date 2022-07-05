import React from "react";
import Card from "react-bootstrap/Card";
import Col from "react-bootstrap/Col";

export interface RailConditionDataCardProps {
  label: string;
  value?: number;
}

export const RailConditionDataCard: React.FC<RailConditionDataCardProps> = ({
  label,
  value,
}) => {
  return (
    <>
      <Col xs={3} className="col mb-2">
        <Card className="card border-success  rounded-0 m-0 p-0">
          <Card.Header className="card rounded-0 p-0 sm text-center bg-success text-light">
            {label}
          </Card.Header>
          <Card.Body className="card border-0  px-0 pt-0 pb-3  rounded-0">
            <Card.Text className="card border-0 rounded-0 border-0 text-center fs-3 text-warning">
              {value || 0}
            </Card.Text>
          </Card.Body>
        </Card>
      </Col>
    </>
  );
};
