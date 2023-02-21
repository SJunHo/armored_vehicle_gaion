import React, {useCallback, useContext, useEffect, useMemo, useState,} from "react"
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {Column, Row as TableRow} from "react-table";
import {DataInputOption, DataProvider, DbModelResponse, OpenApiContext} from "../api";
import {ALGORITHM_INFO} from "../common/Common";
import {Section} from "../common/Section/Section";
import {Table} from "../common/Table";
import {useDataPredictionColumns} from "./useDataPredictionColumns";

export const DataPrediction: React.FC<{ algorithmName: string }> = ({algorithmName}) => {
  const [predicting, setPredicting] = useState(false);
  const [saving, setSaving] = useState(false);
  const [searchingData, setSearchingData] = useState(false);
  const [searchingModels, setSearchingModels] = useState(false);
  const [models, setModels] = useState<DbModelResponse[]>([]);
  const [selectedModel, setSelectedModel] = useState<DbModelResponse>();

  const [carsList, setCarsList] = useState<string[]>([]);
  const [selectedCar, setSelectedCar] = useState<string>();

  const [conditionData, setConditionData] = useState<any[]>([]);
  const [selectedData, setSelectedData] = useState<any[]>([]);
  const [selectedDataIdx, setSelectedDataIdx] = useState<any[]>([]);
  const [partType, setPartType] = useState<string>("");
  const [targetClassCol, setTargetClassCol] = useState<string>("");
  const [fromDate, setFromDate] = useState<Date>(new Date());
  const [toDate, setToDate] = useState<Date>(new Date());
  const {databaseJudgementControllerApi, datasetDatabaseControllerApi, mlControllerApi} = useContext(OpenApiContext);

  useEffect(() => {
    const thisDate = new Date();
    thisDate.setMonth(thisDate.getMonth() - 6);
    setFromDate(thisDate);
  }, []);

  useEffect(() => {
    if (partType && (algorithmName === "linear" || algorithmName === "lasso")) {
      datasetDatabaseControllerApi?.findDistinctByCarIdFromLifeData(partType)
        .then((res) => {
          setCarsList(res.data)
          setSelectedCar(res.data[0])
        });
    } else if (partType) {
      databaseJudgementControllerApi?.findDistinctByCarId(partType)
        .then((res) => {
          setCarsList(res.data)
          setSelectedCar(res.data[0])
        });
    }
  }, [partType, databaseJudgementControllerApi, algorithmName, datasetDatabaseControllerApi]);

  const wholeBearingCycle = 540000
  const wholeWheelCycle = 160000
  const wholeGearboxCycle = 1080000
  const wholeEngineCycle = 480000

  const handleConditionSelected =
    useCallback((v: TableRow<any[]>[]) => {
      setSelectedData(v?.map((i) => i.original))
      setSelectedDataIdx(v?.map((i) => i.values.idx))
    }, []);

  const handleModelSelected = useCallback((v: TableRow<DbModelResponse>[]) => {
    setSelectedModel(v[0]?.original);
  }, []);

  const modelResponseColumns = useMemo<Column<DbModelResponse>[]>(
    () => [
      {
        Header: "모델 이름",
        accessor: "modelName",
      },
      {
        Header: "메모",
        accessor: "description",
      },
      {
        Header: "정확도",
        accessor: "accuracy",
      },
      {
        Header: "RMSE",
        accessor: "rootMeanSquaredError",
      },
    ],
    []
  );


  function handleSearchModel() {
    if (partType === "" || null) {
      alert("부품이 선택되지 않았습니다.")
      setPredicting(false)
      return null
    }
    setSearchingModels(true);
    mlControllerApi
      ?.getModels(ALGORITHM_INFO[algorithmName].className)
      .then((data) => {
        setModels((data.data || []).filter((model) => model.checked && model.partType === partType));
      })
      .finally(() => setSearchingModels(false));
  }

  function handleSearchConditionData(partType: any) {
    if (partType === "" || null) {
      alert("부품이 선택되지 않았습니다.")
      setPredicting(false)
      return null
    }
    if (selectedCar == undefined) {
      return []
    }
    handleSettingClassColByPart(partType)
    setSearchingData(true);
    if (["BLB", "BLI", "BLO", "BLR", "BRB", "BRI", "BRO", "BRR"].includes(partType)) {
      datasetDatabaseControllerApi?.getUnlabeledBearingData(selectedCar, partType, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"))
        .then((res) => {
          setConditionData(res.data || [])
        })
        .finally(() => setSearchingData(false));
    } else if (["WL", "WR"].includes(partType)) {
      datasetDatabaseControllerApi?.getUnlabeledWheelData(selectedCar, partType, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"))
        .then((res) => {
          setConditionData(res.data || [])
        })
        .finally(() => setSearchingData(false));
    } else if (partType === "G") {
      datasetDatabaseControllerApi?.getUnlabeledGearboxData(selectedCar, partType, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"))
        .then((res) => {
          setConditionData(res.data || [])
        })
        .finally(() => setSearchingData(false));
    } else if (partType === "E") {
      datasetDatabaseControllerApi?.getUnlabeledEngineData(selectedCar, partType, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data || [])
        })
        .finally(() => setSearchingData(false));
    } else if (partType === "B_LIFE") {
      datasetDatabaseControllerApi?.getUnlabeledBearingLifeData(selectedCar, partType, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data || [])
        })
        .finally(() => setSearchingData(false));
    } else if (partType === "W_LIFE") {
      datasetDatabaseControllerApi?.getUnlabeledWheelLifeData(selectedCar, partType, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data || [])
        })
        .finally(() => setSearchingData(false));
    } else if (partType === "G_LIFE") {
      datasetDatabaseControllerApi?.getUnlabeledGearboxLifeData(selectedCar, partType, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data || [])
        })
        .finally(() => setSearchingData(false));
    } else if (partType === "E_LIFE") {
      datasetDatabaseControllerApi?.getUnlabeledEngineLifeData(selectedCar, partType, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data || [])
        })
        .finally(() => setSearchingData(false));
    }
  }

  function handleSettingClassColByPart(partType: any) {
    switch (partType) {
      case "BLB":
        // Bearing Left Ball
        setTargetClassCol("ai_LBSF");
        break
      case "BLI":
        // Bearing Left Inside
        setTargetClassCol("ai_LBPFI");
        break
      case "BLO":
        // Bearing Left Outside
        setTargetClassCol("ai_LBPFO");
        break
      case "BLR":
        // Bearing Left Retainer
        setTargetClassCol("ai_LFTF");
        break
      case "BRB":
        // Bearing Right Ball
        setTargetClassCol("ai_RBSF");
        break
      case "BRI":
        // Bearing Right Inside
        setTargetClassCol("ai_RBPFI");
        break
      case "BRO":
        // Bearing Right Outside
        setTargetClassCol("ai_RBPFO");
        break
      case "BRR":
        // Bearing Right Retainer
        setTargetClassCol("ai_RFTF");
        break
      case "WL":
        // Wheel Left
        setTargetClassCol("ai_LW");
        break
      case "WR":
        // Wheel Right
        setTargetClassCol("ai_RW");
        break
      case "G":
        // Gearbox
        setTargetClassCol("ai_GEAR");
        break
      case "E":
        // Engine
        setTargetClassCol("ai_ENGINE");
        break
      case "B_LIFE":
      case "W_LIFE":
      case "G_LIFE":
      case "E_LIFE":
        // part of remaining life
        setTargetClassCol("ai_Trip");
        break
    }
  }

  async function handleClassificationData() {
    const res = await mlControllerApi?.classificationPredict(algorithmName, {
      classCol: targetClassCol,
      modelName: selectedModel?.modelName,
      dataProvider: DataProvider.Ktme,
      dataInputOption: DataInputOption.Db,
      listFieldsForPredict: selectedModel?.listFeatures,
      dataType: partType,
      dbDocIds: selectedDataIdx
    });
    const predictedData = res?.data.predictionInfo || [];
    setConditionData((old) =>
      old.map((row) => {
        const selectedIndex = selectedData!.findIndex(
          (selectedId) => selectedId.idx === row.idx
        );
        let resultArr;
        if (selectedIndex !== -1) {
          resultArr = JSON.parse(
            "[" + predictedData[selectedIndex] + "]"
          )
          row[targetClassCol] = resultArr[resultArr.length - 1];
          row.aiAlgorithm = algorithmName;
          row.aiModel = selectedModel?.modelName;
        }
        return row;
      })
    );
  }

  async function handleRegressionData() {
    const res = await mlControllerApi?.regressionPredict(algorithmName, {
      classCol: targetClassCol,
      modelName: selectedModel?.modelName,
      dataProvider: DataProvider.Ktme,
      dataInputOption: DataInputOption.Db,
      listFieldsForPredict: selectedModel?.listFeatures,
      dataType: partType,
      dbDocIds: selectedDataIdx
    });
    const predictedData = res?.data.predictionInfo || [];
    setConditionData((old) =>
      old.map((row) => {
        const selectedIndex = selectedData!.findIndex(
          (selectedId) => selectedId.idx === row.idx
        );
        let resultArr;
        if (selectedIndex !== -1) {
          resultArr = JSON.parse(
            "[" + predictedData[selectedIndex] + "]"
          )
          row[targetClassCol] = resultArr[0];
          row.aiAlgorithm = algorithmName;
          row.aiModel = selectedModel?.modelName;
        }
        return row;
      })
    );
  }

  async function handleOutlierDetectionData() {
    const res = await mlControllerApi?.predictCluster(algorithmName, {
      classCol: targetClassCol,
      modelName: selectedModel?.modelName,
      dataProvider: DataProvider.Ktme,
      dataInputOption: DataInputOption.Db,
      listFieldsForPredict: selectedModel?.listFeatures,
      dataType: partType,
      dbDocIds: selectedDataIdx
    });
    const predictedData = res?.data.predictionInfo || [];
    setConditionData((old) =>
      old.map((row) => {
        const selectedIndex = selectedData!.findIndex(
          (selectedId) => selectedId.idx === row.idx
        );
        let resultArr;
        if (selectedIndex !== -1) {
          var score = JSON.parse("[" + predictedData[selectedIndex] + "]")[1];
          score > 0.5 ? row[targetClassCol] = "1.0" : row[targetClassCol] = "0.0"
          row.aiAlgorithm = algorithmName;
          row.aiModel = selectedModel?.modelName;
        }
        return row;
      })
    );
  }

  async function handlePredictData() {
    setPredicting(true);
    if (selectedModel?.modelName === undefined || null) {
      alert("모델이 선택되지 않았습니다.")
      setPredicting(false)
    } else {
      if (algorithmName === "if") {
        await handleOutlierDetectionData().finally(() => setPredicting(false));
      } else if (algorithmName === "linear" || algorithmName === "lasso") {
        await handleRegressionData().finally(() => setPredicting(false));
      } else {
        await handleClassificationData().finally(() => setPredicting(false));
      }
    }
  }

  function handleUpdateData() {
    setSaving(true);
    if (algorithmName === "linear" || algorithmName === "lasso") {
      switch (partType) {
        case "B_LIFE":
          datasetDatabaseControllerApi
            ?.updateData(
              selectedData!.map((inputs) => ({
                partType: partType,
                id: inputs.idx,
                aiAlgorithmName: selectedModel?.algorithmType,
                aiPredict: wholeBearingCycle - inputs[targetClassCol],
                aiModelName: selectedModel?.modelName,
              }))
            )
            .finally(() => {
              alert("저장되었습니다")
              setSaving(false)
            });
          break;
        case "W_LIFE":
          datasetDatabaseControllerApi
            ?.updateData(
              selectedData!.map((inputs) => ({
                partType: partType,
                id: inputs.idx,
                aiAlgorithmName: selectedModel?.algorithmType,
                aiPredict: wholeWheelCycle - inputs[targetClassCol],
                aiModelName: selectedModel?.modelName,
              }))
            )
            .finally(() => {
              alert("저장되었습니다")
              setSaving(false)
            });
          break;
        case "G_LIFE":
          datasetDatabaseControllerApi
            ?.updateData(
              selectedData!.map((inputs) => ({
                partType: partType,
                id: inputs.idx,
                aiAlgorithmName: selectedModel?.algorithmType,
                aiPredict: wholeGearboxCycle - inputs[targetClassCol],
                aiModelName: selectedModel?.modelName,
              }))
            )
            .finally(() => {
              alert("저장되었습니다")
              setSaving(false)
            });
          break;
        case "E_LIFE":
          datasetDatabaseControllerApi
            ?.updateData(
              selectedData!.map((inputs) => ({
                partType: partType,
                id: inputs.idx,
                aiAlgorithmName: selectedModel?.algorithmType,
                aiPredict: wholeEngineCycle - inputs[targetClassCol],
                aiModelName: selectedModel?.modelName,
              }))
            )
            .finally(() => {
              alert("저장되었습니다")
              setSaving(false)
            });
          break;
      }
    } else {
      datasetDatabaseControllerApi
        ?.updateData(
          selectedData!.map((inputs) => ({
            partType: partType,
            id: inputs.idx,
            aiAlgorithmName: selectedModel?.algorithmType,
            aiPredict: parseInt(inputs[targetClassCol]),
            aiModelName: selectedModel?.modelName,
          }))
        )
        .finally(() => {
          alert("저장되었습니다")
          setSaving(false)
        });
    }
  }

  const columns = useDataPredictionColumns(partType)

  return (
    <Container fluid>
      <Section title={algorithmName === "linear" || algorithmName === "lasso" ? "잔존수명 예지 수행" : "고장예지 예측 수행"}
               className="mb-2">
        <Row className="row mb-2">
          <Col xs={1} className="Col pe-0 text-white">
            부품선택
          </Col>
          <Col xs={1} className="Col ps-0">
            <Form.Select
              size="sm"
              value={partType}
              onChange={(v) => {
                setPartType((v.target as any).value)
              }}
            >
              {algorithmName === "linear" || algorithmName === "lasso" ? (
                <>
                  <option value="">선택해주세요.</option>
                  <option value="B_LIFE">베어링</option>
                  <option value="W_LIFE">휠</option>
                  <option value="G_LIFE">감속기(기어박스)</option>
                  <option value="E_LIFE">엔진</option>
                </>
              ) : (
                <>
                  <option value="">선택해주세요.</option>
                  <option value="BLB">베어링 좌측 볼</option>
                  <option value="BLO">베어링 좌측 외륜</option>
                  <option value="BLI">베어링 좌측 내륜</option>
                  <option value="BLR">베어링 좌측 리테이너</option>
                  <option value="BRB">베어링 우측 볼</option>
                  <option value="BRO">베어링 우측 내륜</option>
                  <option value="BRI">베어링 우측 외륜</option>
                  <option value="BRR">베어링 우측 리테이너</option>
                  <option value="WL">차륜 좌측</option>
                  <option value="WR">차륜 우측</option>
                  <option value="G">감속기(기어박스)</option>
                  <option value="E">엔진</option>
                </>
              )}
            </Form.Select>
          </Col>

          <Col xs={1} className="Col ps-0" style={{marginLeft: "50px"}}>
            <Button
              className="button btn-block font-monospace fw-bold"
              onClick={() => {
                handleSearchModel()
              }}
              size="sm"
            >
              모델 조회
            </Button>
          </Col>
          <Col xs={1} className="text-right">
            차량 선택
          </Col>
          <Col xs={1}>
            <Form.Select
              size="sm"
              value={selectedCar}
              onChange={(v) => setSelectedCar((v.target as any).value)}
            >
              {carsList.map((car) => (
                <option key={car} value={car}>{car}</option>
              ))}
            </Form.Select>
          </Col>
          <Col xs={1} className="text-right">기간</Col>
          <Col xs={2}>
            <Form.Control
              size="sm"
              type="date"
              value={fromDate?.toLocaleDateString("en-CA")}
              onChange={(v) => setFromDate(new Date((v.target as any).value))}
            />
          </Col>
          <div className="font-weight-bold">~</div>
          <Col xs={2}>
            <Form.Control
              type="date"
              size="sm"
              value={toDate?.toLocaleDateString("en-CA")}
              onChange={(v) => setToDate(new Date((v.target as any).value))}
            />
          </Col>
        </Row>
        <Row>
          <Col xl={12}>
            <Table
              data={models}
              columns={modelResponseColumns}
              isSingleRowSelect
              onRowsSelected={handleModelSelected}
            />
          </Col>
        </Row>
        <Row>
          <Col className="Col d-grid gap-2">
            <Button
              className="button btn-block font-monospace fw-bold"
              onClick={() => handleSearchConditionData(partType)}
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
              데이터 조회
            </Button>
          </Col>
          <Col className="Col d-grid gap-2">
            <Button
              className="button font-monospace fw-bold w-100"
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
              예측 수행
            </Button>
          </Col>
        </Row>
      </Section>
      <Section title={algorithmName === "linear" || algorithmName === "lasso" ? "잔존수명 예지 결과" : "고장예지 예측 결과"}
               className="mb-2">
        <Col xl={12}>
          <div className="w-100 overflow-auto">
            <Table
              columns={columns}
              data={conditionData}
              autoResetSelectedRows={false}
              onRowsSelected={handleConditionSelected}
              getRowId={(row: any) => (row as any).idx}
              paginationOptions={{
                pageSize: 20,
                pageIndex: 0,
              }}
            />
          </div>
        </Col>
        <Row className="row justify-content-end">
          <Col className="Col col-1 d-grid p-0">
            <Button
              className="button font-monospace fw-bold"
              onClick={handleUpdateData}
              size="lg"
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
              결과 저장
            </Button>
          </Col>
        </Row>
      </Section>
    </Container>
  );
};
