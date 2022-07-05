import React, {useContext, useEffect, useMemo, useState} from "react";
import {Button} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {useTranslation} from "react-i18next";
import {Column} from "react-table";
import {OpenApiContext, Pageable, RailSensorData} from "../api";
import {RailSensorDataDetail} from "../common/RailSensorDataDetail";
import {Paginator} from "../common/Paginator";
import {Table} from "../common/Table";

export const PredictedResults: React.FC<{ algorithmName: string }> = () => {
    const [trainsList, setTrainsList] = useState<string[]>([]);
    const [carsList, setCarsList] = useState<string[]>([]);

    const [selectedTrain, setSelectedTrain] = useState<string>();
    const [selectedCar, setSelectedCar] = useState<string>();
    const [severity, setSeverity] = useState<number>();
    const [wb, setWb] = useState<string>("W");
    const [fromDate, setFromDate] = useState<Date>();
    const [toDate, setToDate] = useState<Date>(new Date());

    const [railConditionData, setRailConditionData] = useState<RailSensorData[]>(
    []
    );
    const [paginate, setPaginate] = useState<Pageable>();
    const [totalPage, setTotalPage] = useState<number>(1);
    const [selectedData, setSelectedData] = useState<RailSensorData>();

    const { t } = useTranslation();

    const { datasetControllerApi } = useContext(OpenApiContext);

    const columns = useMemo<Column<RailSensorData>[]>(
        () => [
            {
                Header: t("history.defect_score").toString(),
                accessor: (v: any) => {
                    let result
                    let color

                    if (v.defectScore === 0) {
                        result = "0"
                    } else if (v.defectScore === 10){
                        result = "매우 양호"
                        color = "#73CAF5"
                    } else if (v.defectScore === 20) {
                        result = "양호"
                        color = "#78FF9E"
                    } else if (v.defectScore === 30) {
                        result = "적정"
                        color = "#E4E86D"
                    } else if (v.defectScore === 40) {
                        result = "심각"
                        color = "#FFD285"
                    } else {
                        result = "매우 심각"
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
                Header: t("history.type").toString(),
                accessor: "type",
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
                Header: t("history.airtemp").toString(),
                accessor: "airTemp",
            },
            {
                Header: t("history.temp_v").toString(),
                accessor: "tempV",
            },
            {
                Header: t("history.temp_c").toString(),
                accessor: "tempC",
            },
            {
                Header: t("history.entry_speed").toString(),
                accessor: "entrySpeed",
            },
            {
                Header: t("history.AE_v").toString(),
                accessor: "aeV",
            },
            {
                Header: t("history.AE_c").toString(),
                accessor: "aeC",
            },
            {
                Header: t("history.vib_v").toString(),
                accessor: "vibV",
            },
            {
                Header: t("history.vib_c").toString(),
                accessor: "vibC",
            },
            {
                Header: t("history.total_value").toString(),
                accessor: "totalValue",
            },
            {
                Header: t("history.total_count").toString(),
                accessor: "totalCount",
            },
            {
                Header: t("history.sound_v").toString(),
                accessor: "soundV",
            },
            {
                Header: t("history.sound_c").toString(),
                accessor: "soundC",
            },
            {
                Header: t("history.load_v").toString(),
                accessor: "loadV",
            },
            {
                Header: t("history.load_c").toString(),
                accessor: "loadC",
            },
            {
                Header: t("history.round").toString(),
                accessor: "round",
            },
            {
                Header: t("history.weighting_state_equation").toString(),
                accessor: "weightingStateEquation",
            },
            {
                Header: t("history.time").toString(),
                accessor: (item) => {
                    const time = item.measurementTime
                        ? new Date(item.measurementTime)
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

  useEffect(() => {
    const thisDate = new Date();
    thisDate.setMonth(thisDate.getMonth() - 6);
    setFromDate(thisDate);
  }, []);

  useEffect(() => {
    if (selectedTrain) {
      datasetControllerApi
        ?.getCarLists(selectedTrain)
        .then((res) => setCarsList(res.data));
    }
  }, [selectedTrain, datasetControllerApi]);

  useEffect(() => {
    datasetControllerApi
      ?.getTrainLists()
      .then((res) => setTrainsList(res.data));
  }, [datasetControllerApi]);

  function handleSearchData(pageable?: Pageable) {
      datasetControllerApi
          ?.getAllConditionData(
              wb,
              selectedTrain,
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
              severity,
              // defectScore is must above 0.
              true,
              // state 2 mean select all defectUser data.
              2,
              pageable?.pageNumber,
              pageable?.pageSize
          )
          .then((res) => {
              setRailConditionData(res.data.content || []);
              setPaginate(res.data.pageable);
              setTotalPage(res.data.totalPages || 1);
          });
  }

  return (
    <>
      <Container fluid className="p-3 pt-5">
        <Row className="row mb-2">
          <Col xs={1} className="Col pe-0">
            {t("scd.trainno")}
          </Col>
          <Col xs={2} className="Col ps-0">
            <Form.Select
              size="sm"
              value={selectedTrain}
              onChange={(v) => {
                setSelectedTrain((v.target as any).value);
              }}
            >
              <option value={undefined}>{t("combo.all")}</option>
              {trainsList.map((trainNumber) => (
                <option value={trainNumber}>{trainNumber}</option>
              ))}
            </Form.Select>
          </Col>
          <Col xs={1} className="Col pe-0">
            {t("scd.carno")}
          </Col>
          <Col xs={2} className="Col ps-0">
            <Form.Select
              size="sm"
              value={selectedCar}
              onChange={(v) => setSelectedCar((v.target as any).value)}
            >
              <option>{t("combo.all")}</option>
              {carsList.map((car) => (
                <option value={car}>{car}</option>
              ))}
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
        </Row>
        <Row className="row mb-2">
          <Col xs={1} className="Col pe-0">
            {t("scd.parts")}
          </Col>
          <Col xs={2} className="Col ps-0">
            <Form.Select
              size="sm"
              value={wb}
              onChange={(v) => setWb((v.target as any).value)}
            >
              <option value="W">W</option>
              <option value="B">B</option>
            </Form.Select>
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
            <Button type="button" onClick={() => handleSearchData(paginate)}>
              {t("btn.search")}
            </Button>
          </Col>
        </Row>

        <div>
          <div style={{ width: "130%" }}>
              <Table
                  columns={columns}
                  data={railConditionData}
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
                              handleSearchData(newPaginate);
                          }}
                      />
                  </div>
              </div>
              </div>
          {selectedData && (
            <div style={{ flex: 1, padding: "1em" }}>
              <RailSensorDataDetail data={selectedData} />
            </div>
          )}
        </div>
      </Container>
    </>
  );
};
