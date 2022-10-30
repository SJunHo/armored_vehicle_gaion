import React, {useCallback, useContext, useEffect, useMemo, useState} from "react";
import {Button} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {useTranslation} from "react-i18next";
import {Column, Row as TableRow} from "react-table";
import {OpenApiContext, Pageable, SensorBearing, SensorEngine, SensorGearbox, SensorWheel, SensorTempLife } from "../api";
import {Paginator} from "../common/Paginator";
import {Table} from "../common/Table";
import Spinner from "react-bootstrap/Spinner";

export const PredictedResults: React.FC<{ algorithmName: string }> = () => {
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

    const { t } = useTranslation();

    const { predictedResultDatabaseControllerApi } = useContext(OpenApiContext);

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
        [t, part]
    );

    const SensorGearboxDataColumns = useMemo<Column<SensorGearbox>[]>(
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
                Header: "wrpm",
                accessor: "wrpm",
            },
            {
                Header: "gvOverallRms",
                accessor: "gvOverallRms",
            },
            {
                Header: "gvWheel1x",
                accessor: "gvWheel1x",
            },
            {
                Header: "gvWheel2x",
                accessor: "gvWheel2x",
            },
            {
                Header: "gvPinion1x",
                accessor: "gvPinion1x",
            },
            {
                Header: "gvPinion2x",
                accessor: "gvPinion2x",
            },
            {
                Header: "gvGmf1x",
                accessor: "gvGmf1x",
            },
            {
                Header: "gvGmf2x",
                accessor: "gvGmf2x",
            },
            {
                Header: "filenm",
                accessor: "filenm",
            },
        ],
        [t]
    );

    const SensorWheelDataColumns = useMemo<Column<SensorWheel>[]>(
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
                Header: "lwv2x",
                accessor: "lwv2x",
            },
            {
                Header: "lwv3x",
                accessor: "lwv3x",
            },
            {
                Header: "lwsFault3",
                accessor: "lwsFault3",
            },
            {
                Header: "rwv2x",
                accessor: "rwv2x",
            },
            {
                Header: "rwv3x",
                accessor: "rwv3x",
            },
            {
                Header: "rwsFault3",
                accessor: "rwsFault3",
            },
            {
                Header: "wRpm",
                accessor: "wrpm",
            },
            {
                Header: "filenm",
                accessor: "filenm",
            },
        ],[t]
    );

    const SensorEngineDataColumns = useMemo<Column<SensorEngine>[]>(
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
                Header: "evOverallRms",
                accessor: "evOverallRms",
            },
            {
                Header: "ev12x",
                accessor: "ev12x",
            },
            {
                Header: "ev1x",
                accessor: "ev1x",
            },
            {
                Header: "evCrestfactor",
                accessor: "evCrestfactor",
            },
            {
                Header: "ach",
                accessor: "ach",
            },
            {
                Header: "acv",
                accessor: "acv",
            },
            {
                Header: "aca",
                accessor: "aca",
            },
            {
                Header: "la",
                accessor: "la",
            },
            {
                Header: "lo",
                accessor: "lo",
            },
            {
                Header: "wrpm",
                accessor: "wrpm",
            },
            {
                Header: "filenm",
                accessor: "filenm",
            },
        ],
        [t]
    );

    const SensorTempLifeDataColumns = useMemo<Column<SensorTempLife>[]>(
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
                accessor: "time",
            },
            {
                Header: "Cpu Util",
                accessor: "cpuUtil",
            },
            {
                Header: "Disk Accesses",
                accessor: "diskAccesses",
            },
            {
                Header: "Disk Blocks",
                accessor: "diskBlocks",
            },{
                Header: "Disk Util",
                accessor: "diskUtil",
            },
            {
                Header: "INST RETIRED",
                accessor: "instRetired",
            },
            {
                Header: "Last Level",
                accessor: "lastLevel",
            },
            {
                Header: "Memory Bus",
                accessor: "memoryBus",
            },
            {
                Header: "Core Cycle",
                accessor: "coreCycle",
            },
        ],
        [t]
    );

    const handleConditionSelected =
        useCallback((v: TableRow<any[]>[]) => {setSelectedData(v?.map((i) => i.original))},[]);

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

  // function handleSearchData(pageable?: Pageable) {
  //     predictedResultDatabaseControllerApi
  //         ?.getSensorData(
  //             part,
  //             pageable?.pageNumber,
  //         )
  //         .then((res) => {
  //             setSensorConditionData(res.data.content || []);
  //             setPaginate(res.data.pageable);
  //             setTotalPage(res.data.totalPages || 1);
  //         });
  // }

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
            <Col xs={1} className="Col ps-0">
                <Form.Select
                    size="sm"
                    value={selectedSda}
                    onChange={(v) => setSelectedSda((v.target as any).value)}
                >
                    <option>{t("combo.all")}</option>
                    {sdaList.map((sda) => (
                        <option value={sda}>{sda}</option>
                    ))}
                    {console.log(selectedSda)}
                    {console.log(sdaList)}
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
            <Col xs={1}>{t("scd.time")}</Col>
            <Col xs={1} className="col">
                <Form.Control
                    size="sm"
                    type="date"
                    value={fromDate?.toLocaleDateString("en-CA")}
                    onChange={(v) => setFromDate(new Date((v.target as any).value))}
                />
            </Col>
            <div className="fixed">~</div>
            <Col xs={1} className="col pe-0">
                <Form.Control
                    type="date"
                    size="sm"
                    value={toDate?.toLocaleDateString("en-CA")}
                    onChange={(v) => setToDate(new Date((v.target as any).value))}
                />
            </Col>
            <Col xs={1}>
                <Button
                    type="button"
                    onClick={() => handleSearchConditionData(part)}
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
                    {console.log(fromDate, toDate, part)}
                </Button>
            </Col>
        </Row>

        <div className="d-flex flex-column">
          <div style={{ flex: 1 }}>
              <Table
                  columns={tableColumns}
                  data={sensorConditionData}
                  // onRowsSelected={handleConditionSelected}
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
                              // handleSearchData(newPaginate);
                          }}
                      />
                  </div>
              </div>
          </div>
        </div>
      </Container>
    </>
  );
};
