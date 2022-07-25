import React, {
  useContext,
  useEffect,
  useMemo,
  useState,
  useCallback,
} from "react"
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
// import Table from "react-bootstrap/Table";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import { useTranslation } from "react-i18next";
import { Column, Row as TableRow } from "react-table";
import {
  DataInputOption,
  DataProvider,
  DbModelResponse,
  OpenApiContext,
  RailSensorData,
} from "../api";
import { ALGORITHM_INFO } from "../common/Common";
import { Section } from "../common/Section/Section";
import { Table } from "../common/Table";

export const DataPrediction: React.FC<{ algorithmName: string }> = ({
  algorithmName,
}) => {
  const [predicting, setPredicting] = useState(false);
  const [saving, setSaving] = useState(false);
  const [searchingData, setSearchingData] = useState(false);
  const [models, setModels] = useState<DbModelResponse[]>([]);
  const [selectedModel, setSelectedModel] = useState<DbModelResponse>();
  const [railConditionData, setRailConditionData] = useState<RailSensorData[]>(
    []
  );
  const [selectedData, setSelectedData] = useState<RailSensorData[]>();
  const [wb, setWb] = useState<string>("W");

  const { t } = useTranslation();
  const columns = useMemo<Column<DbModelResponse>[]>(
    () => [
      {
        Header: "Model Name",
        accessor: "modelName",
      },
      {
        Header: t("table.column.notes").toString(),
        accessor: "description",
      },
    ],
    [t]
  );

  const conditionDataColumns = useMemo<Column<RailSensorData>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: "defectScore",
      },
      {
        Header: "ID",
        accessor: "id",
      },
      {
        Header: t("history.tno").toString(),
        accessor: "trainNo",
      },
      {
        Header: t("history.cno").toString(),
        accessor: "carNo",
      },
      {
        Header: t("history.wb").toString(),
        accessor: "wb",
      },
      {
        Header: t("history.lr").toString(),
        accessor: "lr",
      },
      {
        Header: t("history.ns").toString(),
        accessor: "ns",
      },
      {
        Header: t("history.ot").toString(),
        accessor: "oneTwo",
      },
      {
        Header: t("history.time").toString(),
        accessor: (item) => {
          const time = item.time ? new Date(item.time) : undefined;
          return time
            ? time.toLocaleDateString("en-CA") +
                " " +
                time.toLocaleTimeString("kr")
            : "-";
        },
      },
    ],
    [t]
  );
  // const conditionDataGearBoxColumns = useMemo<Column<RailSensorData>[]>(
  //   () => [
  //     {
  //       Header: "예측 결과",
  //       accessor: "defectScore",
  //     },
  //     {
  //       Header: "ID",
  //       accessor: "id",
  //     },
  //     {
  //       Header: t("history.tno").toString(),
  //       accessor: "trainNo",
  //     },
  //     {
  //       Header: t("history.cno").toString(),
  //       accessor: "carNo",
  //     },
  //     {
  //       Header: t("history.wb").toString(),
  //       accessor: "wb",
  //     },
  //     {
  //       Header: t("history.lr").toString(),
  //       accessor: "lr",
  //     },
  //     {
  //       Header: t("history.ns").toString(),
  //       accessor: "ns",
  //     },
  //     {
  //       Header: t("history.ot").toString(),
  //       accessor: "oneTwo",
  //     },
  //     {
  //       Header: t("history.time").toString(),
  //       accessor: (item) => {
  //         const time = item.time ? new Date(item.time) : undefined;
  //         return time
  //           ? time.toLocaleDateString("en-CA") +
  //           " " +
  //           time.toLocaleTimeString("kr")
  //           : "-";
  //       },
  //     },
  //   ],
  //   [t]
  // );
  // const conditionDataWheelColumns = useMemo<Column<RailSensorData>[]>(
  //   () => [
  //     {
  //       Header: "예측 결과",
  //       accessor: "defectScore",
  //     },
  //     {
  //       Header: "ID",
  //       accessor: "id",
  //     },
  //     {
  //       Header: t("history.tno").toString(),
  //       accessor: "trainNo",
  //     },
  //     {
  //       Header: t("history.cno").toString(),
  //       accessor: "carNo",
  //     },
  //     {
  //       Header: t("history.wb").toString(),
  //       accessor: "wb",
  //     },
  //     {
  //       Header: t("history.lr").toString(),
  //       accessor: "lr",
  //     },
  //     {
  //       Header: t("history.ns").toString(),
  //       accessor: "ns",
  //     },
  //     {
  //       Header: t("history.ot").toString(),
  //       accessor: "oneTwo",
  //     },
  //     {
  //       Header: t("history.time").toString(),
  //       accessor: (item) => {
  //         const time = item.time ? new Date(item.time) : undefined;
  //         return time
  //           ? time.toLocaleDateString("en-CA") +
  //           " " +
  //           time.toLocaleTimeString("kr")
  //           : "-";
  //       },
  //     },
  //   ],
  //   [t]
  // );
  // const conditionDataEngineColumns = useMemo<Column<RailSensorData>[]>(
  //   () => [
  //     {
  //       Header: "예측 결과",
  //       accessor: "defectScore",
  //     },
  //     {
  //       Header: "ID",
  //       accessor: "id",
  //     },
  //     {
  //       Header: t("history.tno").toString(),
  //       accessor: "trainNo",
  //     },
  //     {
  //       Header: t("history.cno").toString(),
  //       accessor: "carNo",
  //     },
  //     {
  //       Header: t("history.wb").toString(),
  //       accessor: "wb",
  //     },
  //     {
  //       Header: t("history.lr").toString(),
  //       accessor: "lr",
  //     },
  //     {
  //       Header: t("history.ns").toString(),
  //       accessor: "ns",
  //     },
  //     {
  //       Header: t("history.ot").toString(),
  //       accessor: "oneTwo",
  //     },
  //     {
  //       Header: t("history.time").toString(),
  //       accessor: (item) => {
  //         const time = item.time ? new Date(item.time) : undefined;
  //         return time
  //           ? time.toLocaleDateString("en-CA") +
  //           " " +
  //           time.toLocaleTimeString("kr")
  //           : "-";
  //       },
  //     },
  //   ],
  //   [t]
  // );



  const { datasetControllerApi, datasetDatabaseControllerApi, mlControllerApi } = useContext(OpenApiContext);

  useEffect(() => {
    mlControllerApi
      ?.getModels(ALGORITHM_INFO[algorithmName].className)
      .then((data) => {
        setModels((data.data || []).filter((model) => model.checked));
      });
  }, [mlControllerApi, algorithmName]);

  function handleSearchConditionData() {
    setSearchingData(true);
    datasetDatabaseControllerApi
      ?.getAllConditionDataDB(
        wb,
        undefined,
        undefined,
        undefined,
        undefined,
        undefined,
        false
      )
      .then((res) => setRailConditionData(res.data.content || []))
      .finally(() => setSearchingData(false));
  }

  async function handleClassifyData() {
    const res = await mlControllerApi?.predict(algorithmName, {
      classCol: "defect_score",
      modelName: selectedModel?.modelName,
      dataProvider: DataProvider.Ktme,
      dataInputOption: DataInputOption.Es,
      listFieldsForPredict: selectedModel?.listFeatures,
    });
    const predictedData = res?.data.predictionInfo || [];
    setRailConditionData((old) =>
      old.map((row) => {
        const selectedIndex = selectedData!.findIndex(
          (selectedId) => selectedId.esId === row.esId
        );
        if (selectedIndex !== -1) {
          row.defectScore = JSON.parse(
            "[" + predictedData[selectedIndex] + "]"
          )[0];
        }
        return row;
      })
    );
  }

  async function handleOutlierDetectionData() {
    const res = await mlControllerApi?.predictCluster(algorithmName, {
      classCol: "defect_score",
      modelName: selectedModel?.modelName,
      dataProvider: DataProvider.Ktme,
      dataInputOption: DataInputOption.Es,
      listFieldsForPredict: selectedModel?.listFeatures,
    });
    const predictedData = res?.data.predictionInfo || [];
    setRailConditionData((old) =>
      old.map((row) => {
        const selectedIndex = selectedData!.findIndex(
          (selectedId) => selectedId.esId === row.esId
        );
        if (selectedIndex !== -1) {
          row.defectScore = JSON.parse(
            "[" + predictedData[selectedIndex] + "]"
          )[0];
        }
        return row;
      })
    );
  }

  async function handlePredictData() {
    setPredicting(true);
    if (algorithmName === "kmean" || algorithmName === "if") {
      await handleOutlierDetectionData().finally(() => setPredicting(false));
    } else {
      await handleClassifyData().finally(() => setPredicting(false));
    }
  }

  async function handleUpdateData() {
    setSaving(true);
    datasetControllerApi
      ?.updateRailSensorData(
        selectedData!.map((es) => ({
          esId: es.esId,
          gdefectProb: es.defectScore,
        }))
      )
      .finally(() => setSaving(false));
  }

  const handleConditionDataSelected = useCallback(
    (v: TableRow<RailSensorData>[]) => {
      setSelectedData(v.map((i) => i.original));
    },
    []
  );

  const handleModelSelected = useCallback((v: TableRow<DbModelResponse>[]) => {
    setSelectedModel(v[0]?.original);
  }, []);

  return (
    <Container fluid>
      <Section title="고장전조 예측 수행" className="mb-2">
        <Row className="row mb-2">
          <Col xs={1} className="Col pe-0">
            {t("pp.tsg.parts")}
          </Col>
          <Col xs={3} className="Col ps-0">
            <Form.Select
              size="sm"
              value={wb}
              onChange={(v) => setWb((v.target as any).value)}
            >
              <option value="W">차륜(휠)</option>
              <option value="B">차축(베어링)</option>
              <option value="E">엔진(윤활)</option>
              <option value="G">감속기(기어박스)</option>
            </Form.Select>
          </Col>
          <Col xs={1} className="Col pe-0" />

          <Col xs={2} className="Col pe-0">
            {/*{t("ml.run.result")}*/}
          </Col>
          <Col xs={3} className="Col ps-0">
            {/*<Form.Control size="sm" type="number" />*/}
          </Col>
          <Col className="Col d-grid gap-2">
            <Button
              className="button btn-block font-monospace fw-bold"
              onClick={() => handleSearchConditionData()}
              size="sm"
              disabled={searchingData}
            >
              {searchingData && (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              )}
              {t("btn.search")}
            </Button>
          </Col>
        </Row>
        <Row>
          <Col md={10}>
            <Table
              data={models}
              columns={columns}
              isSingleRowSelect
              onRowsSelected={handleModelSelected}
            />
          </Col>
          <Col className="Col d-grid gap-2">
            <Button
              className="button font-monospace fw-bold"
              onClick={handlePredictData}
              size="sm"
              variant="danger"
              disabled={predicting}
            >
              {predicting && (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              )}
              {t("ml.run.predict")}
            </Button>
          </Col>
        </Row>
      </Section>
      <Row className="row mb-1 fw-bold">
        <Col>고장전조 예측 결과</Col>
      </Row>
      <Table
        columns={conditionDataColumns}
        data={railConditionData}
        onRowsSelected={handleConditionDataSelected}
      />

      <Row className="row justify-content-end">
        <Col className="Col col-1 d-grid gap-2">
          <Button
            className="button font-monospace fw-bold"
            onClick={handleUpdateData}
            size="sm"
            disabled={predicting}
          >
            {saving && (
              <Spinner
                as="span"
                animation="border"
                size="sm"
                role="status"
                aria-hidden="true"
              />
            )}
            {t("pp.fu.db.save")}
          </Button>
        </Col>
      </Row>
    </Container>
  );
};
