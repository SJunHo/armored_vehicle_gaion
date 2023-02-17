import React, {useContext, useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {OpenApiContext, Pageable} from "../api";
import {Paginator} from "../common/Paginator";
import {Table} from "../common/Table";
import {CSVLink} from "react-csv";
import {partTypes} from "../ModelManagement/useDataPredictionColumns";
import {Section} from "../common/Section/Section";
import moment from "moment";
import {useJudgementLookupColumns} from "./useJudgementLookupColumns";

export const JudgementLookup: React.FC = () => {
  const [partType, setPartType] = useState<string>("BLB");
  const [carsList, setCarsList] = useState<string[]>([]);
  const [selectedCar, setSelectedCar] = useState<string>();
  const [modelList, setModelList] = useState<string[]>([]);
  const [selectedModel, setSelectedModel] = useState<string>();

  const [fromDate, setFromDate] = useState<Date>();
  const [toDate, setToDate] = useState<Date>(new Date());

  const [paginate, setPaginate] = useState<Pageable>();
  const [totalPage, setTotalPage] = useState<number>(0);

  const [predictedData, setPredictedData] = useState<any[]>([]);
  const [judgedData, setJudgedData] = useState<any[]>([]);

  const {databaseJudgementControllerApi} = useContext(OpenApiContext);

  const notNeededColumnsForAnother = ["ai", "ac_", "DATE", "idx", "_ID"];

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


  function handleObjectKey(data: any) {
    const result = data.slice()
    result.forEach(((eachMap: any) => Object.keys(eachMap).forEach(function (eachKey: string) {
      // delete not needed
      notNeededColumnsForAnother.map((el: string) => {
        if (eachKey.includes(el)) {
          delete eachMap[eachKey]
        }
      })
    })))

    var userKey = Object.keys(result[0]).filter(el => {
      if (el.includes("user_")) return true
    })
    var targetColumnName = userKey[0].split('_')[1]
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
          setPredictedData(res.data.content || []);
          setPaginate(res.data.pageable);
          setTotalPage(res.data.totalPages || 1);
        }
      );
      databaseJudgementControllerApi?.getLeftBallUserLBSFData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        console.log(res)
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
        console.log(result)
      });
    }
    if (partType === 'BLI') {
      databaseJudgementControllerApi?.getBearingLeftInsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getLeftInsideUserLBPFIData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'BLO') {
      databaseJudgementControllerApi?.getBearingLeftOutsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getLeftOutsideUserLBPFOData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'BLR') {
      databaseJudgementControllerApi?.getBearingLeftRetainerPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getLeftRetainerUserLFTFData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'BRB') {
      databaseJudgementControllerApi?.getBearingRightBallPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getRightBallUserRBSFData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'BRI') {
      databaseJudgementControllerApi?.getBearingRightInsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getRightInsideUserRBPFIData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'BRO') {
      databaseJudgementControllerApi?.getBearingRightOutsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getRightOutsideUserRBPFOData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'BRR') {
      databaseJudgementControllerApi?.getBearingRightRetainerPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getRightRetainerUserRFTFData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'E') {
      databaseJudgementControllerApi?.getEnginePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getEngineUserEngineData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'G') {
      databaseJudgementControllerApi?.getGearboxPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
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
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
        console.log(data)
        console.log(result)
      });
    }
    if (partType === 'WL') {
      databaseJudgementControllerApi?.getWheelLeftPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getLeftWheelUserLW(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'WR') {
      databaseJudgementControllerApi?.getWheelRightPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getRightWheelUserRW(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
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
                <Form.Control
                  size="sm"
                  type="date"
                  value={fromDate?.toLocaleDateString("en-CA")}
                  onChange={(v) => setFromDate(new Date((v.target as any).value))}
                />
              </Col>
              <div className="font-weight-bold">~</div>
              <Col xs={5}>
                <Form.Control
                  type="date"
                  size="sm"
                  value={toDate?.toLocaleDateString("en-CA")}
                  onChange={(v) => setToDate(new Date((v.target as any).value))}
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
                data={predictedData || []}
              />
            </div>
            <div id="paginator" className="pt-4">
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
            </div>
            <div style={{float: 'right'}}>
              <Button className="mr20"> 자가 학습 반영 </Button>
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
    </Container>
  );
};

