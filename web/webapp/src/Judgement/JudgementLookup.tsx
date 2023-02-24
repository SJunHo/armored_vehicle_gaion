import React, {useContext, useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {ClassificationResponse, OpenApiContext} from "../api";
import {Table} from "../common/Table";
import {CSVLink} from "react-csv";
import {partTypes} from "../ModelManagement/useDataPredictionColumns";
import {Section} from "../common/Section/Section";
import moment from "moment";
import {useJudgementLookupColumns} from "./useJudgementLookupColumns";
import {CreateModelResult} from "../ModelManagement/CreateModelResult";
import DatePicker from "react-datepicker";


export const JudgementLookup: React.FC = () => {
  const [partType, setPartType] = useState<string>("BLB");
  const [isTraining, setIsTraining] = React.useState(false);
  const [carsList, setCarsList] = useState<string[]>([]);
  const [selectedCar, setSelectedCar] = useState<string>();
  const [modelList, setModelList] = useState<string[]>([]);
  const [selectedModel, setSelectedModel] = useState<string>();
  const [retrainingResult, setRetrainingResult] = useState<ClassificationResponse>();
  const [algorithmName, setAlgorithmName] = useState<string>();


  const [fromDate, setFromDate] = useState<Date>();
  const [toDate, setToDate] = useState<Date>(new Date());

  const [predictedData, setPredictedData] = useState<any[]>([]);
  const [judgedData, setJudgedData] = useState<any[]>([]);
  const [judgedRetrainingData, setJudgedRetrainingData] = useState<any[]>([]);

  const {databaseJudgementControllerApi, mlControllerApi} = useContext(OpenApiContext);

  const notNeededColumnsForAnother = ["ai", "ac_", "DATE", "idx", "_ID"];
  const notNeededColumnsForRetraining = ["sdaId", "ai", "_ID", "_DATE", "_ALGO", "_MODEL"];

  useEffect(() => {
    const thisDate = new Date();
    thisDate.setMonth(thisDate.getMonth() - 6);
    setFromDate(thisDate);
  }, []);

  useEffect(() => {
    if (partType) {
      databaseJudgementControllerApi?.findDistinctByCarId(partType)
        .then((res) => {
          setCarsList(res.data)
          setSelectedCar(res.data[0])
        });
    }
  }, [partType, databaseJudgementControllerApi]);

  useEffect(() => {
    if (partType) {
      databaseJudgementControllerApi?.findDistinctByModelName(partType)
        .then((res) => {
          setModelList(res.data)
          setSelectedModel(res.data[0])
        });
    }
  }, [partType, databaseJudgementControllerApi]);

  function findClassLabel(selectedPart: any) {
    switch (selectedPart) {
      // bearing
      case "BLB" :
        return "AI_LBSF"
      case "BLO" :
        return "AI_LBPFO"
      case "BLI" :
        return "AI_LBPFI"
      case "BLR" :
        return "AI_LFTF"
      case "BRB" :
        return "AI_RBSF"
      case "BRO" :
        return "AI_RBPFO"
      case "BRI" :
        return "AI_RBPFI"
      case "BRR" :
        return "AI_RFTF"
      // wheel
      case "WL" :
        return "AI_LW"
      case "WR" :
        return "AI_RW"
      // gearbox
      case "G" :
        return "AI_GEAR"
      // engine
      case "E" :
        return "AI_ENGINE"
      // part of remaining life
      case "B_LIFE" :
      case "W_LIFE" :
      case "G_LIFE" :
      case "E_LIFE" :
        return "Trip"
    }
  }

  type reTrainingInput = {
    newData: any[];
    modelName: string;
    partType: string;
    algorithmName: string;
  };

  function removeKeyFromObject(obj: any, key: any) {
    delete obj[key];
    return obj;
  }

  function handleObjectKeyToRetraining(data: any) {
    const result = data.slice()
    result.forEach(((eachMap: any) => Object.keys(eachMap).forEach(function (eachKey: string) {
      // delete not needed
      notNeededColumnsForRetraining.map((el: string) => {
        if (eachKey.includes(el)) {
          delete eachMap[eachKey]
        }
      })
    })))

    var aiKey = findClassLabel(partType)
    var targetCol = aiKey?.split('_')[1] || ""
    var userKey = "user_" + targetCol
    var newAiKey = "AI_" + targetCol

    const isUpperCase = (string: string) => /^[A-Z]*$/.test(string)


    result.forEach((obj: any) => {
      if (obj["user_" + targetCol] != null) {
        const oldValue = obj["user_" + targetCol]
        removeKeyFromObject(obj, "user_" + targetCol)
        obj["ai_" + targetCol] = oldValue
      }

      for (let i in obj) {
        switch (i.toString()) {
          case 'idx':
            obj["IDX"] = obj['idx']
            delete obj['idx']
            break
          case 'date':
            obj["DATE"] = obj['date']
            delete obj['date']
            break
          case 'sdaId':
            obj['SDAID'] = obj['sdaId']
            delete obj['sdaId']
            break
          default:
            if (!isUpperCase(i.charAt(0))) {
              var upperCaseKey = i.charAt(0).toUpperCase() + i.charAt(1).toUpperCase() + i.slice(2)
              obj[upperCaseKey] = obj[i];
              delete obj[i];
            }
        }
      }
    })

    return result
  }


  async function handleReTraining() {
    setIsTraining(true);
    let input: reTrainingInput = {} as reTrainingInput
    let handleData = handleObjectKeyToRetraining(judgedRetrainingData)

    input.newData = handleData
    input.modelName = selectedModel || ""
    input.partType = partType
    input.algorithmName = findAlgorithmName(predictedData[0])


    let newResult;
    try {
      switch (findAlgorithmName(predictedData[0])) {
        case "RandomForestClassifier": {
          newResult = await mlControllerApi?.retrainRfc(input);
          break;
        }
        case "SVCClassifier": {
          newResult = await mlControllerApi?.retrainSVC(input);
          break;
        }
        case "LogisticRegression": {
          newResult = await mlControllerApi?.retrainLr(input);
          break;
        }
        case "MLPClassifier": {
          newResult = await mlControllerApi?.retrainMLP(input);
          break;
        }
        case "IsolationForestOutlierDetection": {
          newResult = await mlControllerApi?.retrainIF(input);
          break;
        }
      }
    } catch (e) {
      setIsTraining(false);
    }
    setRetrainingResult(newResult?.data);
    setIsTraining(false);
  }


  function findAlgorithmName(data: any) {
    const keysArray = Object.keys(data)
    var aiKey = keysArray.filter((key) => key.includes("_ALGO"))
    var algorithmFullName = data[aiKey[0]]
    switch (algorithmFullName) {
      case "RandomForestClassifier": {
        setAlgorithmName("rfc")
        break;
      }
      case "SVCClassifier": {
        setAlgorithmName("svc")
        break;
      }
      case "LogisticRegression": {
        setAlgorithmName("lr")
        break;
      }
      case "MLPClassifier": {
        setAlgorithmName("mlp")
        break;
      }
      case "IsolationForestOutlierDetection": {
        setAlgorithmName("if")
        break;
      }
    }

    return algorithmFullName
  }

  function handleObjectKeyToCSV(data: any) {
    const result = data.slice()
    result.forEach(((eachMap: any) => Object.keys(eachMap).forEach(function (eachKey: string) {
      // delete not needed
      notNeededColumnsForAnother.map((el: string) => {
        if (eachKey.includes(el)) {
          delete eachMap[eachKey]
        }
      })
    })))

    var aiKey = findClassLabel(partType)
    var targetColumnName = aiKey?.split('_')[1] || ""
    var userKey = "user_" + targetColumnName
    var newAiKey = "AI_" + targetColumnName

    const isUpperCase = (string: string) => /^[A-Z]*$/.test(string)

    result.forEach((el: any) => {
        el[newAiKey] = el[userKey[0]]
        delete el[userKey[0]]
        for (let i in el) {
          switch (i.toString()) {
            case 'date':
              el["DATE"] = el['date']
              delete el['date']
              break
            case 'sdaId':
              el['SDAID'] = el['sdaId']
              delete el['sdaId']
              break
            default:
              if (!isUpperCase(i.charAt(0))) {
                var upperCaseKey = i.charAt(0).toUpperCase() + i.slice(1)
                el[upperCaseKey] = el[i];
                delete el[i];
              }
          }
        }
      }
    )

    return result
  }


  function handleSearchData() {
    if (selectedCar == undefined || selectedModel == undefined) {
      return []
    }
    //다운로드 데이터 조회
    if (partType === 'BLB') {
      databaseJudgementControllerApi?.getBearingLeftBallPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
          setPredictedData(res.data || []);
        }
      );
      databaseJudgementControllerApi?.getLeftBallUserLBSFData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'BLI') {
      databaseJudgementControllerApi?.getBearingLeftInsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getLeftInsideUserLBPFIData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'BLO') {
      databaseJudgementControllerApi?.getBearingLeftOutsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getLeftOutsideUserLBPFOData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'BLR') {
      databaseJudgementControllerApi?.getBearingLeftRetainerPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getLeftRetainerUserLFTFData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'BRB') {
      databaseJudgementControllerApi?.getBearingRightBallPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getRightBallUserRBSFData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'BRI') {
      databaseJudgementControllerApi?.getBearingRightInsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getRightInsideUserRBPFIData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'BRO') {
      databaseJudgementControllerApi?.getBearingRightOutsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getRightOutsideUserRBPFOData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'BRR') {
      databaseJudgementControllerApi?.getBearingRightRetainerPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getRightRetainerUserRFTFData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'E') {
      databaseJudgementControllerApi?.getEnginePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getEngineUserEngineData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'G') {
      databaseJudgementControllerApi?.getGearboxPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getGearboxUserGearData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'WL') {
      databaseJudgementControllerApi?.getWheelLeftPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getLeftWheelUserLW(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
    if (partType === 'WR') {
      databaseJudgementControllerApi?.getWheelRightPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
      databaseJudgementControllerApi?.getRightWheelUserRW(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var data = res.data.map(a => {
          return {...a}
        })
        var result = res.data.length == 0 ? [] : handleObjectKeyToCSV(res.data)
        setJudgedData(result);
        setJudgedRetrainingData(data)
      });
    }
  }

  function changePartTypeToKorean(partName: string) {
    switch (partName) {
      case "BLB":
        // Bearing Left Ball
        return "베어링좌측볼"
      case "BLI":
        // Bearing Left Inside
        return "베어링좌측내부"
      case "BLO":
        // Bearing Left Outside
        return "베어링좌측외부"
      case "BLR":
        // Bearing Left Retainer
        return "베어링좌측리테이너"
      case "BRB":
        // Bearing Right Ball
        return "베어링우측볼"
      case "BRI":
        // Bearing Right Inside
        return "베어링우측내부"
      case "BRO":
        // Bearing Right Outside
        return "베어링우측외부"
      case "BRR":
        // Bearing Right Retainer
        return "베어링우측리테이너"
      case "WL":
        // Wheel Left
        return "좌측휠"
      case "WR":
        // Wheel Right
        return "우측휠"
      case "G":
        // Gearbox
        return "기어박스"
      case "E":
        // Engine
        return "엔진"
    }
  }

  const columns = useJudgementLookupColumns(partType)

  return (
    <Container className="p-0">
      <Section title="검색 조건 입력">
        <Row className="row">
          <Col xs={2} className="text-right">
            부품 선택
            <Form.Select
              size="sm"
              className="ml-2"
              value={partType}
              onChange={(v) => {
                setPartType((v.target as any).value);
                // handleSearchTablesColumns((v.target as any).value)
              }}
            >
              {partTypes.map((part) => (
                <option key={part.value} value={part.value}>{part.label}</option>
              ))}
            </Form.Select>
          </Col>
          <Col xs={2} className="text-right">
            차량 선택
            <Form.Select
              size="sm"
              className="ml-2"
              value={selectedCar}
              onChange={(v) => setSelectedCar((v.target as any).value)}
            >
              {carsList.map((car) => (
                <option key={car} value={car}>{car}</option>
              ))}
            </Form.Select>
          </Col>
          <Col xs={2} className="text-right">
            모델 선택
            <Form.Select
              size="sm"
              className="ml-2"
              value={selectedModel}
              onChange={(v) => setSelectedModel((v.target as any).value)}
            >
              {modelList.map((model) => (
                <option key={model} value={model}>{model}</option>
              ))}
            </Form.Select>
          </Col>
          <Col xs={5}>
            <Row className="ml-5">
              기간
              <Col xs={5}>
                <DatePicker
                  className="text-dark"
                  dateFormat="yyyy-MM-dd"
                  selected={fromDate}
                  onChange={(v: Date) => {
                    setFromDate(v)
                  }}
                />
              </Col>
              <div className="font-weight-bold">~</div>
              <Col xs={5}>
                <DatePicker
                  className="text-dark"
                  dateFormat="yyyy-MM-dd"
                  selected={toDate}
                  onChange={(v: Date) => {
                    setToDate(v)
                  }}
                />
              </Col>
            </Row>
          </Col>
        </Row>
        <Row>
          <Col xs={12}>
            <div style={{float: 'right'}}>
              <Button type="button" onClick={() => {
                handleSearchData()
              }}>검색</Button>
            </div>
          </Col>
        </Row>
      </Section>
      <Section title="결과 조회">
        <Row>
          <Col xs={12}>
            <div className="overflow-auto" id="table">
              <Table
                columns={columns}
                data={predictedData}
                paginationOptions={{
                  pageSize: 20,
                  pageIndex: 0,
                }}
              />
            </div>
            {/*<div id="paginator" className="pt-4">
              <Paginator
                pageCount={totalPage || 0}
                size={paginate?.pageSize || 0}
                selectedPage={paginate?.pageNumber || 0}
                onChange={(v) => {
                  const newPaginate = {
                    ...paginate,
                    pageNumber: v,
                  };
                  setPaginate(newPaginate)
                  handleSearchData()
                }}
              />
            </div>*/}
            <div style={{float: 'right'}}>
              <Button className="mr20" onClick={handleReTraining}> 자가 학습 반영 </Button>
              <CSVLink
                data={judgedData}
                filename={`${changePartTypeToKorean(partType)}_${moment(new Date()).format("YYYYMMDD_HHmmss")}`}
                onClick={() => {
                  if (judgedData.length == 0) {
                    alert("작업자 판정 데이터가 없습니다")
                    return false;
                  } else {
                    return true;
                  }
                }}
              >
                <Button> 파일 다운로드 </Button>
              </CSVLink>
            </div>
          </Col>
        </Row>
      </Section>
      {retrainingResult && algorithmName && (
        <>
          <CreateModelResult algorithmName={algorithmName} result={retrainingResult}/>
        </>
      )}
    </Container>
  );
};

