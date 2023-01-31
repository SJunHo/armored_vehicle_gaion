import React, {useContext, useState} from "react";
import {FormControl} from "react-bootstrap";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import {Page} from "../common/Page/Page";
import {Section} from "../common/Section/Section";
import {TabHeader} from "../common/TabHeader/TabHeader";
import {OpenApiContext} from "../api";
import Spinner from "react-bootstrap/Spinner";

export const ClassificationDataUpload: React.FC = () => {
  const [selectedTab, setSelectedTab] = useState("upload");
  const [uploadedFiles, setUploadedFiles] = useState<File[]>([]);
  const [uploading, setUploading] = useState(false);
  const {datasetControllerApi, datasetDatabaseControllerApi} = useContext(OpenApiContext);

  return (
    <Page>
      <TabHeader
        headers={[
          {
            id: "upload",
            title: "파일 업로드",
          },
        ]}
        activeTabId={selectedTab}
        onChangeActiveTab={(v) => setSelectedTab(v)}
      />
      {selectedTab === "upload" && (
        <UploadPage
          uploading={uploading}
          onUploadFile={(files, selectedPart) => {
            console.log(selectedPart)
            setUploading(true);
            datasetDatabaseControllerApi
              ?.uploadCSVFileAndImportDB(selectedPart, files)
              .then(() => setUploadedFiles(files))
              .finally(() => setUploading(false));
          }}
        />
      )}
    </Page>
  );
};

const UploadPage: React.FC<{
  onUploadFile: (files: File[], selectedPart: any) => any;
  uploading: boolean;
}> = ({onUploadFile, uploading}) => {
  const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [selectedDataType, setSelectedDataType] = useState<any>("B");


  return (
    <div className="page-padding">
      <Section title={"고장진단 모델 학습 데이터 업로드"}>
        <Form>
          <Row className="d-flex mt-2 mb-4">
            <Col md={2}>
              <Form.Select
                size="lg"
                value={selectedDataType}
                onChange={(v) => setSelectedDataType((v.target as any).value)}
              >
                <option value={'B'}>[고장진단] 베어링</option>
                <option value={'W'}>[고장진단] 휠</option>
                <option value={'G'}>[고장진단] 기어박스</option>
                <option value={'E'}>[고장진단] 엔진</option>
              </Form.Select>
            </Col>
          </Row>
          <FormControl
            style={{height: "43px"}}
            type="file"
            multiple
            onChange={(v) => {
              // @ts-ignore
              setSelectedFiles([...v.target.files]);
            }}
          />
        </Form>
        <Row className="d-flex mt-4">
          <Col md={2}>
            <Button
              disabled={uploading}
              type="submit"
              onClick={() => onUploadFile(selectedFiles, selectedDataType)}
            >
              {uploading && (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              )}
              파일 업로드
            </Button>
          </Col>
        </Row>
      </Section>
    </div>
  );
};
