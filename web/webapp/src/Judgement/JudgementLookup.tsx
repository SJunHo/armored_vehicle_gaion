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
import {Page} from "../common/Page/Page";

const partTypes = [
  {
    value: "BLB",
    label: "베어링 좌측 볼",
  },
  {
    value: "BLI",
    label: "베어링 좌측 내륜",
  },
  {
    value: "BLO",
    label: "베어링 좌측 외륜",
  },
  {
    value: "BLR",
    label: "베어링 좌측 리테이너",
  },
  {
    value: "BRB",
    label: "베어링 우측 볼",
  },
  {
    value: "BRI",
    label: "베어링 우측 내륜",
  },
  {
    value: "BRO",
    label: "베어링 우측 외륜",
  },
  {
    value: "BRR",
    label: "베어링 우측 리테이너",
  },
  // wheel
  {
    value: "WL",
    label: "차륜 좌측",
  },
  {
    value: "WR",
    label: "차륜 우측",
  },
  {
    value: "G",
    label: "감속기(기어박스)",
  },
  {
    value: "E",
    label: "엔진",
  }
]

export const JudgementLookup: React.FC = () => {
  //Select 박스
  //1. 부품 선택
  //2. 차량 ID
  //3. 기간?
  // => 조회

  // 조회 조건에 따라 컬럼에 생성 후 데이터 가시화
  //

  const [partType, setPartType] = useState<string>("BLB");
  const [carsList, setCarsList] = useState<string[]>([]);
  const [selectedCar, setSelectedCar] = useState<string>();


  const [severity, setSeverity] = useState<number>();
  const [wb, setWb] = useState<string>("W");
  const [fromDate, setFromDate] = useState<Date>();
  const [toDate, setToDate] = useState<Date>(new Date());

  const [paginate, setPaginate] = useState<Pageable>();
  const [totalPage, setTotalPage] = useState<number>(1);

  const {datasetControllerApi, databaseJudgementControllerApi} = useContext(OpenApiContext);

  useEffect(() => {
    const thisDate = new Date();
    thisDate.setMonth(thisDate.getMonth() - 6);
    setFromDate(thisDate);
  }, []);

  useEffect(() => {
    if (partType) {
      databaseJudgementControllerApi?.findDistinctByCarId(partType)
        .then((res) => setCarsList(res.data));
    }
  }, [partType, datasetControllerApi]);
  return (
    <Page>
      <Container fluid className="p-3 pt-5">
        <Row className="row mb-2">
          <Col xs={1} className="Col">
            부품 선택
          </Col>
          <Col xs={2} className="Col">
            <Form.Select
              size="sm"
              value={partType}
              onChange={(v) => {
                setPartType((v.target as any).value);
              }}
            >
              {partTypes.map((part) => (
                <option value={part.value}>{part.label}</option>
              ))}
            </Form.Select>
          </Col>
          <Col xs={1} className="Col">
            차량 선택
          </Col>
          <Col xs={2} className="Col">
            <Form.Select
              size="sm"
              value={selectedCar}
              onChange={(v) => setSelectedCar((v.target as any).value)}
            >
              {carsList.map((car) => (
                <option value={car}>{car}</option>
              ))}
            </Form.Select>
          </Col>
          <Col xs={1}>기간</Col>
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

          </Col>
        </Row>

        <div>
          <div style={{width: "130%"}}>

            <div>
              <div style={{display: 'inline-block'}}>
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

                  }}
                />
              </div>
            </div>
          </div>

        </div>
      </Container>
    </Page>
  );
};
