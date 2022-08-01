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
  SensorBearing,
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
  const [railConditionData, setRailConditionData] = useState<SensorBearing[]>([]);
  const [selectedData, setSelectedData] = useState<SensorBearing[]>();
  const [tableColumns, setTableColumns] = useState<any>([]);
  const [sensorObject, setSensorObject] = useState<Object>();
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
      {
        Header: t("ml.common.accuracy").toString(),
        accessor: "accuracy",
      },
      // {
      //   Header: t("ml.common.fsco").toString(),
      //   accessor: "weightedFMeasure",
      // },
      // {
      //   Header: t("ml.common.wfp").toString(),
      //   accessor: "weightedFalsePositiveRate",
      // },
      // {
      //   Header: t("ml.common.precision").toString(),
      //   accessor: "weightedPrecision",
      // },
      // {
      //   Header: t("ml.common.recall").toString(),
      //   accessor: "weightedRecall",
      // },
      // {
      //   Header: t("ml.common.wtp").toString(),
      //   accessor: "weightedTruePositiveRate",
      // },
      // {
      //   Header: t("ml.common.r2").toString(),
      //   accessor: "r2",
      // },
      {
        Header: t("ml.common.rmse").toString(),
        accessor: "rootMeanSquaredError",
      },
    ],
    [t]
  );


  const SensorBearingDataColumns = useMemo<Column<SensorBearing>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: "aiPredict",
      },
      {
        Header: "알고리즘",
        accessor: "aiAlgorithm",
      },
      {
        Header: "모델이름",
        accessor: "aiModel",
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "날짜",
        accessor: "operateDateTime",
      },
      {
        Header: "carId",
        accessor: "carId",
      },
      {
        Header: "lbvOverallRMS",
        accessor: "lbvOverallRMS",
      },
      {
        Header: "lbv1x",
        accessor: "lbv1x",
      },
      {
        Header: "lbv6912bpfo",
        accessor: "lbv6912bpfo",
      },{
        Header: "lbv6912bpfi",
        accessor: "lbv6912bpfi",
      },
      {
        Header: "lbv6912bsf",
        accessor: "lbv6912bsf",
      },
      {
        Header: "lbv6912ftf",
        accessor: "lbv6912ftf",
      },
      {
        Header: "lbv32924bpfo",
        accessor: "lbv32924bpfo",
      },
      {
        Header: "lbv32924bpfi",
        accessor: "lbv32924bpfi",
      },
      {
        Header: "lbv32924bsf",
        accessor: "lbv32924bsf",
      },
      {
        Header: "lbv32924ftf",
        accessor: "lbv32924ftf",
      },
      {
        Header: "lbv32922bpfo",
        accessor: "lbv32922bpfo",
      },
      {
        Header: "lbv32922bpfi",
        accessor: "lbv32922bpfi",
      },
      {
        Header: "lbv32922bsf",
        accessor: "lbv32922bsf",
      },
      {
        Header: "lbv32922ftf",
        accessor: "lbv32922ftf",
      },
      {
        Header: "lbvCrestfactor",
        accessor: "lbvCrestfactor",
      },
      {
        Header: "lbvDemodulation",
        accessor: "lbvDemodulation",
      },
      {
        Header: "lbsFault1",
        accessor: "lbsFault1",
      },
      {
        Header: "lbsFault2",
        accessor: "lbsFault2",
      },
      {
        Header: "lbtTemperature",
        accessor: "lbtTemperature",
      },
      {
        Header: "rbvOverallRMS",
        accessor: "rbvOverallRMS",
      },
      {
        Header: "rbv1x",
        accessor: "rbv1x",
      },
      {
        Header: "rbv6912bpfo",
        accessor: "rbv6912bpfo",
      },
      {
        Header: "rbv6912bpfi",
        accessor: "rbv6912bpfi",
      },
      {
        Header: "rbv6912bsf",
        accessor: "rbv6912bsf",
      },
      {
        Header: "rbv6912ftf",
        accessor: "rbv6912ftf",
      },
      {
        Header: "rbv32924bpfo",
        accessor: "rbv32924bpfo",
      },
      {
        Header: "rbv32924bpfi",
        accessor: "rbv32924bpfi",
      },
      {
        Header: "rbv32924bsf",
        accessor: "rbv32924bsf",
      },
      {
        Header: "rbv32924ftf",
        accessor: "rbv32924ftf",
      },
      {
        Header: "rbv32922bpfo",
        accessor: "rbv32922bpfo",
      },
      {
        Header: "rbv32922bpfi",
        accessor: "rbv32922bpfi",
      },
      {
        Header: "rbv32922bsf",
        accessor: "rbv32922bsf",
      },
      {
        Header: "rbv32922ftf",
        accessor: "rbv32922ftf",
      },
      {
        Header: "rbvCrestfactor",
        accessor: "rbvCrestfactor",
      },
      {
        Header: "rbvDemodulation",
        accessor: "rbvDemodulation",
      },
      {
        Header: "rbsFault1",
        accessor: "rbsFault1",
      },
      {
        Header: "rbsFault2",
        accessor: "rbsFault2",
      },
      {
        Header: "rbtTemperature",
        accessor: "rbtTemperature",
      },
      {
        Header: "filenm",
        accessor: "filenm",
      },
      {
        Header: "wrpm",
        accessor: "wrpm",
      },
    ],
    []
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

  function handleSearchConditionData(wb:any) {
    setSearchingData(true);
    if(wb == "B"){
      datasetDatabaseControllerApi?.getUnlabeledBearingData(wb)
        .then((res) => setRailConditionData(res.data || []))
        .finally(() => setSearchingData(false));
      setTableColumns(SensorBearingDataColumns)
      console.log(railConditionData)
    }else if(wb =="W"){
      datasetDatabaseControllerApi?.getUnlabeledBearingData(wb)
        .then((res) => setRailConditionData(res.data || []))
        .finally(() => setSearchingData(false));
      setTableColumns(SensorBearingDataColumns)
    }else if(wb =="G"){
      datasetDatabaseControllerApi?.getUnlabeledBearingData(wb)
        .then((res) => setRailConditionData(res.data || []))
        .finally(() => setSearchingData(false));
      setTableColumns(SensorBearingDataColumns)
    }else if(wb =="E"){
      datasetDatabaseControllerApi?.getUnlabeledBearingData(wb)
        .then((res) => setRailConditionData(res.data || []))
        .finally(() => setSearchingData(false));
      setTableColumns(SensorBearingDataColumns)
    }
  }

  async function handleClassifyData() {
    const res = await mlControllerApi?.predict(algorithmName, {
      classCol: "Ai_Predict",
      modelName: selectedModel?.modelName,
      dataProvider: DataProvider.Ktme,
      dataInputOption: DataInputOption.Db,
      listFieldsForPredict: selectedModel?.listFeatures,
      dataType: wb
    });
    const predictedData = res?.data.predictionInfo || [];

    setRailConditionData((old) =>
      old.map((row) => {
        const selectedIndex = selectedData!.findIndex(
          (selectedId) => selectedId.idx === row.idx
        );
        if (selectedIndex !== -1) {
          row.aiPredict = JSON.parse(
            "[" + predictedData[selectedIndex] + "]"
          )[0];
          row.aiAlgorithm = algorithmName;
          row.aiModel = selectedModel?.modelName;
        }
        return row;
      })
    );
  }

  async function handleOutlierDetectionData() {
    const res = await mlControllerApi?.predictCluster(algorithmName, {
      classCol: "Ai_Predict",
      modelName: selectedModel?.modelName,
      dataProvider: DataProvider.Ktme,
      dataInputOption: DataInputOption.Es,
      listFieldsForPredict: selectedModel?.listFeatures,
    });
    const predictedData = res?.data.predictionInfo || [];
    // setRailConditionData((old) =>
    //   old.map((row) => {
    //     const selectedIndex = selectedData!.findIndex(
    //       (selectedId) => selectedId.esId === row.esId
    //     );
    //     if (selectedIndex !== -1) {
    //       row.defectScore = JSON.parse(
    //         "[" + predictedData[selectedIndex] + "]"
    //       )[0];
    //     }
    //     return row;
    //   })
    // );
  }

  async function handlePredictData() {
    setPredicting(true);
    if (algorithmName === "kmean" || algorithmName === "if") {
      await handleOutlierDetectionData().finally(() => setPredicting(false));
    } else {
      await handleClassifyData().finally(() => setPredicting(false));
    }
  }

  // async function handleUpdateData() {
  //   setSaving(true);
  //   datasetControllerApi
  //     ?.updateRailSensorData(
  //       selectedData!.map((es) => ({
  //         esId: es.esId,
  //         gdefectProb: es.defectScore,
  //       }))
  //     )
  //     .finally(() => setSaving(false));
  // }

  const handleConditionDataSelected =
    useCallback((v: TableRow<SensorBearing>[]) => {setSelectedData(v?.map((i) => i.original))},[]);



  const handleModelSelected = useCallback((v: TableRow<DbModelResponse>[]) => {
    setSelectedModel(v[0]?.original);
  }, []);
  console.log(selectedModel)

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
              onClick={() => handleSearchConditionData(wb)}
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
        columns={tableColumns}
        data={railConditionData}
        onRowsSelected={handleConditionDataSelected}
      />

      <Row className="row justify-content-end">
        <Col className="Col col-1 d-grid gap-2">
          <Button
            className="button font-monospace fw-bold"
            // onClick={handleUpdateData}
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
