import React, {useContext, useEffect, useMemo, useState} from "react";
import {Button} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {useTranslation} from "react-i18next";
import {Column} from "react-table";
import {OpenApiContext, Pageable, SensorBearing, SensorEngine, SensorGearbox, SensorWheel, SensorTempLife } from "../api";
import {RailSensorDataDetail} from "../common/RailSensorDataDetail";
import {Paginator} from "../common/Paginator";
import {Table} from "../common/Table";

export const PredictedResultsUserInput: React.FC<{ algorithmName: string }> = () => {
    const [searchingData, setSearchingData] = useState(false);
    const [tableColumns, setTableColumns] = useState<any>([]);

    const [sdaList, setSdaList] = useState<string[]>([]);

    const [selectedPart, setSelectedPart] = useState<string>();
    const [selectedSda, setSelectedSda] = useState<string>();

    const [severity, setSeverity] = useState<number>();
    const [part, setPart] = useState<string>("W");
    const [partState, setPartState] = useState<string>("정상");

    const [fromDate, setFromDate] = useState<Date>(new Date('2000-01-01'));
    const [toDate, setToDate] = useState<Date>(new Date());

    const [sensorConditionData, setSensorConditionData] = useState<any>([]);

    const [paginate, setPaginate] = useState<Pageable>();
    const [totalPage, setTotalPage] = useState<number>(1);
    const [selectedData, setSelectedData] = useState<any[]>();

    const { predictedResultDatabaseControllerApi, datasetControllerApi } = useContext(OpenApiContext);
    const { t } = useTranslation();

    // Part that receives and stores defectUser values.
    const [updateDefectUserList, setUpdateDefectUserList] = useState< { esId: string; defectUser: number; }[] >([]);
    const [totalUpdateDefectUserList, setTotalUpdateDefectUserList] = useState<{ esId: string; defectUser: number; }[] >([]);

    const SensorBearingColumns = useMemo<Column<SensorBearing>[]>(
        () => [
            {
                Header: t("history.aiPredict").toString(),
                accessor: (v: any) => {
                    let result
                    let color
                    if (v.aiPredict === 0) {
                        result = "정상"
                        color = "#73CAF5"
                    } else if (v.aiPredict === 1){
                        result = "고장"
                        color = "#F59287"
                    }
                    return (
                        <>
                            <div style={{ backgroundColor: color, border: "thick double #DEE2E6" }}>
                                {result}
                            </div>
                        </>
                    )
                }
            },
            {
                Header: t("history.defect_user").toString(),
                Cell: (value?: any) => {
                    return (
                        <>
                            <form className={"d-flex"}>
                                <div className="m-1">
                                    <input
                                        type="radio"
                                        name="defectUser"
                                        key={value.row.original.esId}
                                        value={0}
                                        onClick={() =>
                                           onClickHandler(0, value.data, value.row.original.esId)
                                        }
                                        style={{border: '0px', width: '100%', height: '1em'}}
                                    />
                                    <label className="m-1"> 정상 </label>
                                </div>
                                <div className="m-1">
                                    <input
                                        type="radio"
                                        name="defectUser"
                                        key={value.row.original.esId}
                                        value={1}
                                        onClick={() =>
                                            onClickHandler(1, value.data, value.row.original.esId)
                                        }
                                        style={{border: '0px', width: '100%', height: '1em'}}
                                    />
                                    <label className="m-1"> 고장 </label>
                                </div>
                            </form>
                        </>
                    );
                    },
            },
            {
                Header: t("history.carId").toString(),
                accessor: "carId",
            },
            {
                Header: t("history.timeIndex").toString(),
                accessor: "timeIndex",
            },
            {
                Header: t("history.aiAlgorithm").toString(),
                accessor: "aiAlgorithm",
            },
            {
                Header: t("history.aiModel").toString(),
                accessor: "aiModel",
            },
            {
                Header: t("history.lbvOverallRMS").toString(),
                accessor: "lbvOverallRMS",
            },
            {
                Header: t("history.lbv1x").toString(),
                accessor: "lbv1x",
            },
            {
                Header: t("history.lbv6912bpfo").toString(),
                accessor: "lbv6912bpfo",
            },
            {
                Header: t("history.lbv6912bpfi").toString(),
                accessor: "lbv6912bpfi",
            },
            {
                Header: t("history.lbv6912bsf").toString(),
                accessor: "lbv6912bsf",
            },
            {
                Header: t("history.lbv6912ftf").toString(),
                accessor: "lbv6912ftf",
            },
            {
                Header: t("history.lbv32924bpfo").toString(),
                accessor: "lbv32924bpfo",
            },
            {
                Header: t("history.lbv32924bpfi").toString(),
                accessor: "lbv32924bpfi",
            },
            {
                Header: t("history.lbv32924bsf").toString(),
                accessor: "lbv32924bsf",
            },
            {
                Header: t("history.lbv32924ftf").toString(),
                accessor: "lbv32924ftf",
            },
            {
                Header: t("history.lbv32922bpfo").toString(),
                accessor: "lbv32922bpfo",
            },
            {
                Header: t("history.lbv32922bpfi").toString(),
                accessor: "lbv32922bpfi",
            },
            {
                Header: t("history.lbv32922bsf").toString(),
                accessor: "lbv32922bsf",
            },
            {
                Header: t("history.lbv32922ftf").toString(),
                accessor: "lbv32922ftf",
            },
            {
                Header: t("history.lbvCrestfactor").toString(),
                accessor: "lbvCrestfactor",
            },
            {
                Header: t("history.lbvDemodulation").toString(),
                accessor: "lbvDemodulation",
            },
            {
                Header: t("history.lbsFault1").toString(),
                accessor: "lbsFault1",
            },
            {
                Header: t("history.lbsFault2").toString(),
                accessor: "lbsFault2",
            },
            {
                Header: t("history.lbtTemperature").toString(),
                accessor: "lbtTemperature",
            },
            {
                Header: t("history.rbvOverallRMS").toString(),
                accessor: "rbvOverallRMS",
            },
            {
                Header: t("history.rbv1x").toString(),
                accessor: "rbv1x",
            },
            {
                Header: t("history.rbv6912bpfo").toString(),
                accessor: "rbv6912bpfo",
            },
            {
                Header: t("history.rbv6912bpfi").toString(),
                accessor: "rbv6912bpfi",
            },
            {
                Header: t("history.rbv6912bsf").toString(),
                accessor: "rbv6912bsf",
            },
            {
                Header: t("history.rbv6912ftf").toString(),
                accessor: "rbv6912ftf",
            },
            {
                Header: t("history.rbv32924bpfo").toString(),
                accessor: "rbv32924bpfo",
            },
            {
                Header: t("history.rbv32924bpfi").toString(),
                accessor: "rbv32924bpfi",
            },
            {
                Header: t("history.rbv32924bsf").toString(),
                accessor: "rbv32924bsf",
            },
            {
                Header: t("history.rbv32924ftf").toString(),
                accessor: "rbv32924ftf",
            },
            {
                Header: t("history.rbv32922bpfo").toString(),
                accessor: "rbv32922bpfo",
            },
            {
                Header: t("history.rbv32922bpfi").toString(),
                accessor: "rbv32922bpfi",
            },
            {
                Header: t("history.rbv32922bsf").toString(),
                accessor: "rbv32922bsf",
            },
            {
                Header: t("history.rbv32922ftf").toString(),
                accessor: "rbv32922ftf",
            },
            {
                Header: t("history.rbvCrestfactor").toString(),
                accessor: "rbvCrestfactor",
            },
            {
                Header: t("history.rbvDemodulation").toString(),
                accessor: "rbvDemodulation",
            },
            {
                Header: t("history.rbsFault1").toString(),
                accessor: "rbsFault1",
            },
            {
                Header: t("history.rbsFault2").toString(),
                accessor: "rbsFault2",
            },
            {
                Header: t("history.rbtTemperature").toString(),
                accessor: "rbtTemperature",
            },
            {
                Header: t("history.wrpm").toString(),
                accessor: "wrpm",
            },
            {
                Header: t("history.operateDateTime").toString(),
                accessor: (item) => {
                    const time = item.operateDateTime
                        ? new Date(item.operateDateTime)
                        : undefined;
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

    // 검색 기간
  useEffect(() => {
    const thisDate = new Date();
    thisDate.setMonth(thisDate.getMonth() - 6);
    setFromDate(thisDate);
  }, []);

    // 차량 선택
    useEffect(() => {
        predictedResultDatabaseControllerApi
            ?.getSdaList(selectedSda)
            .then((res) => setSdaList(res.data));
    }, [predictedResultDatabaseControllerApi]);

    // 부품 선택

    // 부품 고장 여부

  function onClickHandler(score: any, valueArr: any, esId: any) {
      // Whenever defectUser value comes in through radio button,
      // it is saved in 'updateDefectUserList' in the form of 'index: {esId: ~, defectUser: ~}' one by one.
      const updateList: { esId: string; defectUser: number; }[] = []

      valueArr.forEach(function (e: any) {
          const updateDict = { esId: " ", defectUser: 0 }

          if (e.esId === esId) {
              e.defectUser = score;
          }

          updateDict["esId"] = e.esId;
          updateDict["defectUser"] =  e.defectUser;

          if (updateDict["defectUser"] !== 0) {
              updateList.push(updateDict)
          }
      })

      setUpdateDefectUserList(updateList)
  }

  console.log(updateDefectUserList)

  function onPaginationHandler(nowPageDefectUserList: any) {
      // When moving table pages, concat and save the list of values of the current page to 'totalUpdateDefectUserList'.
      setTotalUpdateDefectUserList(totalUpdateDefectUserList.concat(nowPageDefectUserList))
  }

  function timeout(delay: number) {
      return new Promise( res => setTimeout(res, delay)
      );
  }

  async function onClickSaveButtonHandler(updateList: any, totalUpdateList?: any) {
      console.log("updateDefectUserList", updateList, "totalUpdateDefectUserList", totalUpdateList)

      setTotalUpdateDefectUserList(totalUpdateList.concat(updateList))

      let proceed = window.confirm(t("alert.message.save"));

      if (proceed) {
          alert(t("alert.message.complete"))

          console.log("totalUpdateList", totalUpdateList)

          // Update to ES with edited defectUser values
          datasetControllerApi?.updateRailSensorData(updateList)

          await timeout(3000)
          console.log("done")

          // handleSearchData()
      }
  }

    console.log("totalUpdateList", totalUpdateDefectUserList)

    function handleSearchConditionData(part:any) {
        setSearchingData(true);
        if(part === "B"){
            predictedResultDatabaseControllerApi?.getSensorData(part, fromDate?.toLocaleDateString("en-CA"), toDate?.toLocaleDateString("en-CA"))
                .then((res) => setSensorConditionData(res.data.sensorBearing?.content || []))
                .finally(() => setSearchingData(false));
            console.log(sensorConditionData)
            setTableColumns(SensorBearingColumns)
        } else if(part ==="W"){
            predictedResultDatabaseControllerApi?.getSensorData(part, fromDate?.toLocaleDateString("en-CA"), toDate?.toLocaleDateString("en-CA"))
                .then((res) => setSensorConditionData(res.data.sensorWheel?.content || []))
                .finally(() => setSearchingData(false));
            setTableColumns(SensorWheelDataColumns)
        }else if(part ==="G"){
            predictedResultDatabaseControllerApi?.getSensorData(part, fromDate?.toLocaleDateString("en-CA"), toDate?.toLocaleDateString("en-CA"))
                .then((res) => setSensorConditionData(res.data.sensorGearbox?.content || []))
                .finally(() => setSearchingData(false));
            setTableColumns(SensorGearboxDataColumns)
        }else if(part ==="E"){
            predictedResultDatabaseControllerApi?.getSensorData(part, fromDate?.toLocaleDateString("en-CA"), toDate?.toLocaleDateString("en-CA"))
                .then((res) => setSensorConditionData(res.data.sensorEngine?.content || []))
                .finally(() => setSearchingData(false));
            setTableColumns(SensorEngineDataColumns)
        } else if(part === "T") {
            predictedResultDatabaseControllerApi?.getSensorData(part, fromDate?.toLocaleDateString("en-CA"), toDate?.toLocaleDateString("en-CA"))
                .then((res) => setSensorConditionData(res.data.sensorTempLife?.content || []))
                .finally(() => setSearchingData(false));
            setTableColumns(SensorTempLifeDataColumns)
        }
    }

  return (
    <>
      <Container fluid className="p-3 pt-5">
        <Row className="row mb-2">
          <Col xs={1} className="Col pe-0">
            {t("scd.carno")}
          </Col>
          <Col xs={2} className="Col ps-0">
            <Form.Select
              size="sm"
              value={selectedSda}
              onChange={(v) => setSelectedSda((v.target as any).value)}
            >
              <option value={undefined}>{t("combo.all")}</option>
              {sdaList.map((sda) => (
                <option value={sda}>{sda}</option>
              ))}
            </Form.Select>
          </Col>
            <Col xs={1} className="Col pe-0">
                {t("scd.parts")}
            </Col>
            <Col xs={1} className="Col ps-0">
                <Form.Select
                    size="sm"
                    value={part}
                    onChange={(v) => setPart((v.target as any).value)}
                >
                    <option value="W">차륜(휠)</option>
                    <option value="B">차축(베어링)</option>
                    <option value="E">엔진(윤활)</option>
                    <option value="G">감속기(기어박스)</option>
                    <option value="T">잔존수명(임시)</option>
                </Form.Select>
            </Col>
            <Col xs={1} className="Col pe-0">
                {t("scd.partstate")}
            </Col>
            <Col xs={1} className="Col ps-0">
                <Form.Select
                    size="sm"
                    value={partState}
                    onChange={(v) => setPartState((v.target as any).value)}
                >
                    <option>{t("combo.all")}</option>
                    <option value="0">정상</option>
                    <option value="1">고장</option>
                </Form.Select>
            </Col>
          <Col xs={1} className="Col pe-0">
            {t("dashboard.sp")}
          </Col>
          <Col xs={2} className="Col ps-0">
            <Form.Control
              size="sm"
              type="number"
              value={severity}
              onChange={(v) => setSeverity((v.target as any).value)}
            />
          </Col>
            <Col xs={1}>{t("scd.time")}</Col>
            <Col xs={2} className="col ps-0">
                <Form.Control
                    size="sm"
                    type="date"
                    value={fromDate?.toLocaleDateString("en-CA")}
                    onChange={(v) => setFromDate(new Date((v.target as any).value))}
                />
            </Col>
            <div className="fixed">~</div>
            <Col xs={2} className="col pe-0">
                <Form.Control
                    type="date"
                    size="sm"
                    value={toDate?.toLocaleDateString("en-CA")}
                    onChange={(v) => setToDate(new Date((v.target as any).value))}
                />
            </Col>
            <Col xs={2}>
                <Button type="button" onClick={
                    () => handleSearchConditionData(paginate)
                }>
                    {t("btn.search")}
                </Button>
            </Col>
        </Row>

        <div>
          <div style={{ width: "145%" }}>
            <Table
              columns={tableColumns}
              data={sensorConditionData}
            />
              <div>
                  <div style={{display : 'inline-block'}}>
                      <Paginator
                          pageCount={totalPage}
                          size={paginate?.pageSize || 0}
                          selectedPage={paginate?.pageNumber || 0}
                          onChange={(v) => {
                              const newPaginate = {
                                  ...paginate,
                                  pageNumber: v,
                              };
                              setPaginate(newPaginate);
                              sensorConditionData(newPaginate);
                          }}
                      />
                  </div>
                  <div style={{float: 'right'}}>
                      <Button type="button" className="btn btn-primary m-lg-1"
                              onClick={() => {
                                  onClickSaveButtonHandler(updateDefectUserList, totalUpdateDefectUserList)
                              }}>
                          {t("btn.save")}
                      </Button>
                  </div>
              </div>
              </div>
          {/*{selectedData && (*/}
          {/*  <div style={{ flex: 1, padding: "1em" }}>*/}
          {/*    <RailSensorDataDetail data={selectedData} />*/}
          {/*  </div>*/}
          {/*)}*/}
        </div>
      </Container>
    </>
  );
};

