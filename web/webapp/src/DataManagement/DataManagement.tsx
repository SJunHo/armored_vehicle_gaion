import React, { useState } from "react";
import { Button, Card, Form, ProgressBar, Row } from "react-bootstrap";
import Select from "react-select/src/Select";
import { Page } from "../common/Page/Page";
import { Section, SubSection } from "../common/Section/Section";
import { TabHeader } from "../common/TabHeader/TabHeader";

export const DataManagement: React.FC = () => {
  const [selectedTab, setSelectedTab] = useState("1");
  return (
    <Card>
      <Form name="searchForm">
        <Row>
          <Select placeholder="차량선택">
            
          </Select>
        </Row>
      </Form>
    </Card>
  );
};

const DataUpload: React.FC = () => {
  return (
    <Section title="파일 업로드 설정">
      <SubSection title="업로드한 파일을 선택하새요. (Spark Rest-API Server)">
        <div className="container-fluid flex-column d-flex gap-2 p-2">
          <ProgressBar />
          <div className="d-flex gap-2">
            <Button variant="secondary">파일선택</Button>
            <span>선택된 없음</span>
          </div>
        </div>
      </SubSection>
    </Section>
  );
};
