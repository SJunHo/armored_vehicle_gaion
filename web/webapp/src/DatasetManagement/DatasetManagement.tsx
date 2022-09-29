import React, {
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
} from "react";
import { Button } from "react-bootstrap";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import { useTranslation } from "react-i18next";
import { Column } from "react-table";
import { OpenApiContext, Pageable, RailSensorData } from "../api";
import { Paginator } from "../common/Paginator";
import { RailSensorDataDetail } from "../common/RailSensorDataDetail";
import { Table } from "../common/Table";

export const DatasetManagement: React.FC<{ algorithmName: string }> = () => {
  const [trainsList, setTrainsList] = useState<string[]>([]);
  const [carsList, setCarsList] = useState<string[]>([]);

  const [selectedTrain, setSelectedTrain] = useState<string>();
  const [selectedCar, setSelectedCar] = useState<string>();
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
        accessor: item => {
          const time = item.measurementTime ? new Date(item.measurementTime) : undefined;
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
    datasetControllerApi?.getTrainLists().then(res => setTrainsList(res.data));
  }, [datasetControllerApi]);

  useEffect(() => {
    const thisDate = new Date();
    thisDate.setMonth(thisDate.getMonth() - 6);
    setFromDate(thisDate);
  }, []);

  useEffect(() => {
    if (selectedTrain) {
      datasetControllerApi
        ?.getCarLists(selectedTrain)
        .then(res => setCarsList(res.data));
    }
  }, [selectedTrain, datasetControllerApi]);

  const handleSearchData = useCallback(
    (
      wb,
      selectedTrain,
      selectedCar,
      fromDate,
      toDate,
      undefined,
      pageable?: Pageable
    ) => {
      setSelectedData(undefined);
      datasetControllerApi
        ?.getAllConditionData(
          wb,
          selectedTrain,
          selectedCar,
          fromDate?.toLocaleDateString("en-US"),
          toDate?.toLocaleDateString("en-US"),
          undefined,
          false,
            // state 1 mean select defectUser is must above 0 data.
          1,
          pageable?.pageNumber,
          pageable?.pageSize
        )
        .then(res => {
          setRailConditionData(res.data.content || []);
          setPaginate(res.data.pageable);
          setTotalPage(res.data.totalPages || 1);
        });
    },
    [datasetControllerApi]
  );

  useEffect(() => {
    const thisDate = new Date();
    thisDate.setMonth(thisDate.getMonth() - 6);
    handleSearchData(
      "W",
      undefined,
      undefined,
      thisDate,
      new Date(),
      undefined,
      {
        offset: 0,
        pageSize: 10,
      }
    );
  }, [handleSearchData]);

  return (
    <>
      <Container fluid className="p-3 pt-2">
        <Row className="row mb-2 mt-0 pt-0">
          <h5>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="27"
              height="27"
              fill="currentColor"
              className="bi bi-nut-fill"
              viewBox="0 0 16 16"
            >
              <path d="M4.58 1a1 1 0 0 0-.868.504l-3.428 6a1 1 0 0 0 0 .992l3.428 6A1 1 0 0 0 4.58 15h6.84a1 1 0 0 0 .868-.504l3.429-6a1 1 0 0 0 0-.992l-3.429-6A1 1 0 0 0 11.42 1H4.58zm5.018 9.696a3 3 0 1 1-3-5.196 3 3 0 0 1 3 5.196z" />
            </svg>
            <span> 상태데이터 조회</span>
          </h5>
        </Row>

        <Row className="row mb-2">
          <Col xs={1} className="Col pe-0">
            {t("scd.trainno")}
          </Col>
          <Col xs={2} className="Col ps-0">
            <Form.Select
              size="sm"
              value={selectedTrain}
              onChange={v => {
                setSelectedTrain((v.target as any).value);
              }}
            >
              <option value={undefined}>{t("combo.all")}</option>
              {trainsList.map(trainNumber => (
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
              onChange={v => setSelectedCar((v.target as any).value)}
            >
              <option>{t("combo.all")}</option>
              {carsList.map(car => (
                <option value={car}>{car}</option>
              ))}
            </Form.Select>
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
              onChange={v => setWb((v.target as any).value)}
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
              onChange={v => setFromDate(new Date((v.target as any).value))}
            />
          </Col>

          <div className="fixed">~</div>

          <Col xs={2} className="col pe-0">
            <Form.Control
              type="date"
              size="sm"
              value={toDate?.toLocaleDateString("en-CA")}
              onChange={v => setToDate(new Date((v.target as any).value))}
            />
          </Col>
          <Col xs={2}>
            <Button
              onClick={() =>
                handleSearchData(
                  wb,
                  selectedTrain,
                  selectedCar,
                  fromDate,
                  toDate,
                  undefined,
                  paginate
                )
              }
            >
              조회
            </Button>
          </Col>
        </Row>

        <div className="d-flex flex-column">
          <div style={{ flex: 1 }}>
            <Table
              columns={columns}
              data={railConditionData}
              onRowClick={v => setSelectedData(v.original)}
            />
            <Paginator
              pageCount={totalPage}
              size={paginate?.pageSize || 0}
              selectedPage={paginate?.pageNumber || 0}
              onChange={v => {
                const newPaginate = {
                  ...paginate,
                  pageNumber: v,
                };
                setPaginate(newPaginate);
                handleSearchData(
                  wb,
                  selectedTrain,
                  selectedCar,
                  fromDate,
                  toDate,
                  undefined,
                  newPaginate
                );
              }}
            />
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
