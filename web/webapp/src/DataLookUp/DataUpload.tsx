import React, {useContext, useState} from "react";
import {FormControl} from "react-bootstrap";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import {useTranslation} from "react-i18next";
import {Page} from "../common/Page/Page";
import {Section} from "../common/Section/Section";
import {TabHeader} from "../common/TabHeader/TabHeader";
import {DataFormat, ImportESDataFromFileInput, OpenApiContext} from "../api";
import Select2 from "react-select";
import {InputWrapper} from "../common/Common";
import Spinner from "react-bootstrap/Spinner";
import Select from "react-select";

export const DataUpload: React.FC = () => {
  const [selectedTab, setSelectedTab] = useState("upload");
  const [uploadedFiles, setUploadedFiles] = useState<File[]>([]);
  const [uploading, setUploading] = useState(false);
  const [importing, setImporting] = useState(false);
  const {datasetControllerApi, datasetDatabaseControllerApi} = useContext(OpenApiContext);

  return (
    <Page>
      <TabHeader
        headers={[
          {
            id: "upload",
            title: "파일 업로드",
          },
          // {
          //   id: "import",
          //   title: t("pp.fu.id.tab"),
          // },
        ]}
        activeTabId={selectedTab}
        onChangeActiveTab={(v) => setSelectedTab(v)}
      />
      {selectedTab === "upload" && (
        <UploadPage
          uploading={uploading}
          onUploadFile={(files) => {
            setUploading(true);
            datasetDatabaseControllerApi
              ?.uploadCSVFileAndImportDB('', files)
              .then(() => setUploadedFiles(files))
              .finally(() => setUploading(false));
          }}
        />
      )}
      {/*{selectedTab === "import" && (*/}
      {/*  <ImportPage*/}
      {/*    importing={importing}*/}
      {/*    uploadedFiles={uploadedFiles}*/}
      {/*    onSubmit={(v) => {*/}
      {/*      setImporting(true);*/}
      {/*      datasetControllerApi*/}
      {/*        ?.importESIndexFromCSV(v)*/}
      {/*        ?.finally(() => setImporting(false));*/}
      {/*    }}*/}
      {/*  />*/}
      {/*)}*/}
    </Page>
  );
};

const UploadPage: React.FC<{
  onUploadFile: (files: File[]) => any;
  uploading: boolean;
}> = ({onUploadFile, uploading}) => {
  const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [selectedDataType, setSelectedDataType] = useState<any>(null);


  return (
    <div className="page-padding">
      <Section title={"학습데이터 업로드"}>
        <Form>
          <Row className="d-flex mt-2 mb-4">
            <Col md={2}>
              <Form.Select
                size="lg"
                value={selectedDataType}
                onChange={(v) => setSelectedDataType((v.target as any).value)}
              >
                <option value={'B'}>베어링</option>
                <option value={'W'}>휠</option>
                <option value={'G'}>기어박스</option>
                <option value={'E'}>엔진</option>
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
              onClick={() => onUploadFile(selectedFiles)}
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

// const ImportPage: React.FC<{
//   uploadedFiles: File[];
//   onSubmit: (v: ImportESDataFromFileInput) => any;
//   importing: boolean;
// }> = ({ uploadedFiles, onSubmit, importing }) => {
//   const { t } = useTranslation();
//   const [input, setInput] = useState<ImportESDataFromFileInput>({
//     format: DataFormat.Csv,
//     delimiter: ",",
//   });
//
//   return (
//     <>
//
//       <Section title={t("pp.fu.id.desc")}>
//         <Form>
//           <InputWrapper title={t("pp.lbl.uploadedFiles")}>
//             <Select2
//               options={uploadedFiles.map((uploadedFile) => ({
//                 value: uploadedFile.name,
//                 label: uploadedFile.name,
//               }))}
//               isMulti
//               onChange={(v) =>
//                 setInput((old) => ({
//                   ...old,
//                   listUploadedFiles: v?.map((v) => v.value),
//                 }))
//               }
//             />
//           </InputWrapper>
//           <Row>
//             <Col md={3}>
//               <InputWrapper title={t("pp.lbl.fileFormat")}>
//                 <Form.Select
//                   value={input.format}
//                   onChange={(v) =>
//                     // @ts-ignore
//                     setInput((old) => ({ ...old, format: v.target.value }))
//                   }
//                 >
//                   <option value={DataFormat.Csv}>CSV</option>
//                   <option value={DataFormat.Dense}>Dense</option>
//                   <option value={DataFormat.Basket}>Basket</option>
//                   <option value={DataFormat.Sparse}>Sparse</option>
//                 </Form.Select>
//               </InputWrapper>
//             </Col>
//             <Col md={3}>
//               <InputWrapper title={t("pp.es.index")}>
//                 <Form.Control
//                   value={input.indexW}
//                   onChange={(v) =>
//                     setInput((old) => ({ ...old, indexW: v.target.value }))
//                   }
//                 />
//               </InputWrapper>
//             </Col>
//             <Col md={3}>
//               <InputWrapper title={t("pp.lbl.key")}>
//                 <Form.Control
//                   value={input.columnPrimaryKey}
//                   onChange={(v) =>
//                     setInput((old) => ({
//                       ...old,
//                       columnPrimaryKey: v.target.value,
//                     }))
//                   }
//                 />
//               </InputWrapper>
//             </Col>
//             <Col md={3}>
//               <InputWrapper title={t("pp.lbl.delimiter")}>
//                 <Form.Control value={input.delimiter} />
//               </InputWrapper>
//             </Col>
//           </Row>
//           <Row>
//             <Col>
//             <Form.Check
//               checked={input.clearOldData}
//               label={t("pp.chkbx.truncate")}
//               onChange={(v) =>
//                 setInput((old) => ({ ...old, clearOldData: v.target.checked }))
//               }
//             />
//             </Col>
//           </Row>
//         </Form>
//         <Row>
//           <Col md={1}>
//             <Button disabled={importing} onClick={() => onSubmit(input)}>
//               {importing && (
//                 <Spinner
//                   as="span"
//                   animation="border"
//                   size="sm"
//                   role="status"
//                   aria-hidden="true"
//                 />
//               )}
//               {t("pp.fu.id.btn.save")}
//             </Button>
//           </Col>
//         </Row>
//       </Section>
//     </>
//   );
// };
