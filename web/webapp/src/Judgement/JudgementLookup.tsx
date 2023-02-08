import React, {useContext, useEffect, useMemo, useState} from "react";
import {Button} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {Column} from "react-table";
import {OpenApiContext, Pageable} from "../api";
import {Paginator} from "../common/Paginator";
import {Table} from "../common/Table";
import {CSVLink} from "react-csv";
import {
  partTypes,
  SensorBearingLeftBallInput,
  SensorBearingLeftInsideInput,
  SensorBearingLeftOutsideInput,
  SensorBearingLeftRetainerInput,
  SensorBearingRightBallInput,
  SensorBearingRightInsideInput,
  SensorBearingRightOutsideInput,
  SensorBearingRightRetainerInput,
  SensorEngineInput,
  SensorGearboxInput,
  SensorWheelLeftInput,
  SensorWheelRightInput
} from "./tableColumns";
import {Section} from "../common/Section/Section";
import moment from "moment";

export const JudgementLookup: React.FC = () => {
  const [partType, setPartType] = useState<string>("BLB");
  const [carsList, setCarsList] = useState<string[]>([]);
  const [selectedCar, setSelectedCar] = useState<string>();

  const [fromDate, setFromDate] = useState<Date>();
  const [toDate, setToDate] = useState<Date>(new Date());

  const [paginate, setPaginate] = useState<Pageable>();
  const [totalPage, setTotalPage] = useState<number>(0);

  const [tableColumn, setTableColumn] = useState<any>();
  const [predictedData, setPredictedData] = useState<any[]>([]);

  const [judgedData, setJudgedData] = useState<any[]>([]);

  const {databaseJudgementControllerApi} = useContext(OpenApiContext);

  const notNeededColumnsForAnother = ["ai", "ac_", "DATE", "ID", "idx"];
  const notNeededColumnsForEngine = ["ai", "DATE", "ID", "idx"];

  const SensorBearingLeftBallColumns = useMemo<Column<SensorBearingLeftBallInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_LBSF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_LBSF == null ? "" : data.user_LBSF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_LBSF_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_LBSF_DATE == null ? "" : new Date(value.user_LBSF_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_LBSF_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_LBSF_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_LBSF_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "L_B_V_1X",
        accessor: "l_B_V_1X",
      },
      {
        Header: "L_B_V_6912BSF",
        accessor: "l_B_V_6912BSF",
      },
      {
        Header: "L_B_V_32924BSF",
        accessor: "l_B_V_32924BSF",
      },
      {
        Header: "L_B_V_32922BSF",
        accessor: "l_B_V_32922BSF",
      },
      {
        Header: "L_B_V_Crestfactor",
        accessor: "l_B_V_Crestfactor",
      },
      {
        Header: "L_B_V_Demodulation",
        accessor: "l_B_V_Demodulation",
      },
      {
        Header: "L_B_S_Fault1",
        accessor: "l_B_S_Fault1",
      },
      {
        Header: "L_B_S_Fault2",
        accessor: "l_B_S_Fault2",
      },
      {
        Header: "L_B_T_Temperature",
        accessor: "l_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorBearingLeftInsideColumns = useMemo<Column<SensorBearingLeftInsideInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_LBPFI == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_LBPFI == null ? "" : data.user_LBPFI == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_LBPFI_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_LBPFI_DATE == null ? "" : new Date(value.user_LBPFI_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_LBPFI_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_LBPFI_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_LBPFI_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "L_B_V_1X",
        accessor: "l_B_V_1X",
      },
      {
        Header: "L_B_V_6912BPFI",
        accessor: "l_B_V_6912BPFI",
      },
      {
        Header: "L_B_V_32924BPFI",
        accessor: "l_B_V_32924BPFI",
      },
      {
        Header: "L_B_V_32922BPFI",
        accessor: "l_B_V_32922BPFI",
      },
      {
        Header: "L_B_V_Crestfactor",
        accessor: "l_B_V_Crestfactor",
      },
      {
        Header: "L_B_V_Demodulation",
        accessor: "l_B_V_Demodulation",
      },
      {
        Header: "L_B_S_Fault1",
        accessor: "l_B_S_Fault1",
      },
      {
        Header: "L_B_S_Fault2",
        accessor: "l_B_S_Fault2",
      },
      {
        Header: "L_B_T_Temperature",
        accessor: "l_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorBearingLeftOutsideColumns = useMemo<Column<SensorBearingLeftOutsideInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_LBPFO == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_LBPFO == null ? "" : data.user_LBPFO == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_LBPFO_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_LBPFO_DATE == null ? "" : new Date(value.user_LBPFO_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_LBPFO_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_LBPFO_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_LBPFO_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "L_B_V_1X",
        accessor: "l_B_V_1X",
      },
      {
        Header: "L_B_V_6912BPFO",
        accessor: "l_B_V_6912BPFO",
      },
      {
        Header: "L_B_V_32924BPFO",
        accessor: "l_B_V_32924BPFO",
      },
      {
        Header: "L_B_V_32922BPFO",
        accessor: "l_B_V_32922BPFO",
      },
      {
        Header: "L_B_V_Crestfactor",
        accessor: "l_B_V_Crestfactor",
      },
      {
        Header: "L_B_V_Demodulation",
        accessor: "l_B_V_Demodulation",
      },
      {
        Header: "L_B_S_Fault1",
        accessor: "l_B_S_Fault1",
      },
      {
        Header: "L_B_S_Fault2",
        accessor: "l_B_S_Fault2",
      },
      {
        Header: "L_B_T_Temperature",
        accessor: "l_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorBearingLeftRetainerColumns = useMemo<Column<SensorBearingLeftRetainerInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_LFTF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_LFTF == null ? "" : data.user_LFTF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_LFTF_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_LFTF_DATE == null ? "" : new Date(value.user_LFTF_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_LFTF_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_LFTF_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_LFTF_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "L_B_V_1X",
        accessor: "l_B_V_1X",
      },
      {
        Header: "L_B_V_6912FTF",
        accessor: "l_B_V_6912FTF",
      },
      {
        Header: "L_B_V_32924FTF",
        accessor: "l_B_V_32924FTF",
      },
      {
        Header: "l_B_V_32922FTF",
        accessor: "l_B_V_32922FTF",
      },
      {
        Header: "L_B_V_Crestfactor",
        accessor: "l_B_V_Crestfactor",
      },
      {
        Header: "L_B_V_Demodulation",
        accessor: "l_B_V_Demodulation",
      },
      {
        Header: "L_B_S_Fault1",
        accessor: "l_B_S_Fault1",
      },
      {
        Header: "L_B_S_Fault2",
        accessor: "l_B_S_Fault2",
      },
      {
        Header: "L_B_T_Temperature",
        accessor: "l_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorBearingRightBallColumns = useMemo<Column<SensorBearingRightBallInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_RBSF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_RBSF == null ? "" : data.user_RBSF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_RBSF_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_RBSF_DATE == null ? "" : new Date(value.user_RBSF_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_RBSF_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_RBSF_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_RBSF_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "R_B_V_1X",
        accessor: "r_B_V_1X",
      },
      {
        Header: "R_B_V_6912BSF",
        accessor: "r_B_V_6912BSF",
      },
      {
        Header: "R_B_V_32924BSF",
        accessor: "r_B_V_32924BSF",
      },
      {
        Header: "R_B_V_32922BSF",
        accessor: "r_B_V_32922BSF",
      },
      {
        Header: "R_B_V_Crestfactor",
        accessor: "r_B_V_Crestfactor",
      },
      {
        Header: "R_B_V_Demodulation",
        accessor: "r_B_V_Demodulation",
      },
      {
        Header: "R_B_S_Fault1",
        accessor: "r_B_S_Fault1",
      },
      {
        Header: "R_B_S_Fault2",
        accessor: "r_B_S_Fault2",
      },
      {
        Header: "R_B_T_Temperature",
        accessor: "r_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorBearingRightInsideColumns = useMemo<Column<SensorBearingRightInsideInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_RBPFI == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_RBPFI == null ? "" : data.user_RBPFI == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_RBPFI_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_RBPFI_DATE == null ? "" : new Date(value.user_RBPFI_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_RBPFI_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_RBPFI_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_RBPFI_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "R_B_V_1X",
        accessor: "r_B_V_1X",
      },
      {
        Header: "R_B_V_6912BPFI",
        accessor: "r_B_V_6912BPFI",
      },
      {
        Header: "R_B_V_32924BPFI",
        accessor: "r_B_V_32924BPFI",
      },
      {
        Header: "R_B_V_32922BPFI",
        accessor: "r_B_V_32922BPFI",
      },
      {
        Header: "R_B_V_Crestfactor",
        accessor: "r_B_V_Crestfactor",
      },
      {
        Header: "R_B_V_Demodulation",
        accessor: "r_B_V_Demodulation",
      },
      {
        Header: "R_B_S_Fault1",
        accessor: "r_B_S_Fault1",
      },
      {
        Header: "R_B_S_Fault2",
        accessor: "r_B_S_Fault2",
      },
      {
        Header: "R_B_T_Temperature",
        accessor: "r_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorBearingRightOutsideColumns = useMemo<Column<SensorBearingRightOutsideInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_RBPFO == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_RBPFO == null ? "" : data.user_RBPFO == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_RBPFO_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_RBPFO_DATE == null ? "" : new Date(value.user_RBPFO_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_RBPFO_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_RBPFO_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_RBPFO_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "R_B_V_1X",
        accessor: "r_B_V_1X",
      },
      {
        Header: "R_B_V_6912BPFO",
        accessor: "r_B_V_6912BPFO",
      },
      {
        Header: "R_B_V_32924BPFO",
        accessor: "r_B_V_32924BPFO",
      },
      {
        Header: "R_B_V_32922BPFO",
        accessor: "r_B_V_32922BPFO",
      },
      {
        Header: "R_B_V_Crestfactor",
        accessor: "r_B_V_Crestfactor",
      },
      {
        Header: "R_B_V_Demodulation",
        accessor: "r_B_V_Demodulation",
      },
      {
        Header: "R_B_S_Fault1",
        accessor: "r_B_S_Fault1",
      },
      {
        Header: "R_B_S_Fault2",
        accessor: "r_B_S_Fault2",
      },
      {
        Header: "R_B_T_Temperature",
        accessor: "r_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorBearingRightRetainerColumns = useMemo<Column<SensorBearingRightRetainerInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_RFTF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_RFTF == null ? "" : data.user_RFTF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_RFTF_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_RFTF_DATE == null ? "" : new Date(value.user_RFTF_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_RFTF_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_RFTF_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_RFTF_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "R_B_V_1X",
        accessor: "r_B_V_1X",
      },
      {
        Header: "R_B_V_6912FTF",
        accessor: "r_B_V_6912FTF",
      },
      {
        Header: "R_B_V_32924FTF",
        accessor: "r_B_V_32924FTF",
      },
      {
        Header: "R_B_V_32922FTF",
        accessor: "r_B_V_32922FTF",
      },
      {
        Header: "R_B_V_Crestfactor",
        accessor: "r_B_V_Crestfactor",
      },
      {
        Header: "R_B_V_Demodulation",
        accessor: "r_B_V_Demodulation",
      },
      {
        Header: "R_B_S_Fault1",
        accessor: "r_B_S_Fault1",
      },
      {
        Header: "R_B_S_Fault2",
        accessor: "r_B_S_Fault2",
      },
      {
        Header: "R_B_T_Temperature",
        accessor: "r_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorWheelLeftColumns = useMemo<Column<SensorWheelLeftInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_LW == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_LW == null ? "" : data.user_LW == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_LW_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_LW_DATE == null ? "" : new Date(value.user_LW_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_LW_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_LW_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_LW_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "L_W_V_2X",
        accessor: "l_W_V_2X",
      },
      {
        Header: "L_W_V_3X",
        accessor: "l_W_V_3X",
      },
      {
        Header: "L_W_S_Fault3",
        accessor: "l_W_S_Fault3",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorWheelRightColumns = useMemo<Column<SensorWheelRightInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_RW == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_RW == null ? "" : data.user_RW == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_RW_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_RW_DATE == null ? "" : new Date(value.user_RW_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_RW_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_RW_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_RW_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "R_W_V_2X",
        accessor: "r_W_V_2X",
      },
      {
        Header: "R_W_V_3X",
        accessor: "r_W_V_3X",
      },
      {
        Header: "R_W_S_Fault3",
        accessor: "r_W_S_Fault3",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorGearboxColumns = useMemo<Column<SensorGearboxInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_GEAR == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_GEAR == null ? "" : data.user_GEAR == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_GEAR_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_GEAR_DATE == null ? "" : new Date(value.user_GEAR_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_GEAR_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_GEAR_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_GEAR_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "G_V_OverallRMS",
        accessor: "g_V_OverallRMS",
      },
      {
        Header: "G_V_Wheel1X",
        accessor: "g_V_Wheel1X",
      },
      {
        Header: "G_V_Wheel2X",
        accessor: "g_V_Wheel2X",
      },
      {
        Header: "G_V_Pinion1X",
        accessor: "g_V_Pinion1X",
      },
      {
        Header: "G_V_Pinion2X",
        accessor: "g_V_Pinion2X",
      },
      {
        Header: "G_V_GMF1X",
        accessor: "g_V_GMF1X",
      },
      {
        Header: "G_V_GMF2X",
        accessor: "g_V_GMF2X",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

  const SensorEngineColumns = useMemo<Column<SensorEngineInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_ENGINE == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "작업자 판정값",
        accessor: (data) => {
          let result = data.user_ENGINE == null ? "" : data.user_ENGINE == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "모델 판정 날짜",
        accessor: "ai_ENGINE_DATE",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_ENGINE_DATE == null ? "" : new Date(value.user_ENGINE_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_ENGINE_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_ENGINE_MODEL",
      },
      {
        Header: "작업자 ID",
        accessor: "user_ENGINE_ID",
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "E_V_OverallRMS",
        accessor: "e_V_OverallRMS",
      },
      {
        Header: "E_V_1_2X",
        accessor: "e_V_1_2X",
      },
      {
        Header: "E_V_1X",
        accessor: "e_V_1X",
      },
      {
        Header: "E_V_Crestfactor",
        accessor: "e_V_Crestfactor",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    []
  );

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

  function handleSearchTablesColumns(partType: any) {
    switch (partType) {
      case "BLB":
        // Bearing Left Ball
        return SensorBearingLeftBallColumns
      case "BLI":
        // Bearing Left Inside
        return SensorBearingLeftInsideColumns
      case "BLO":
        // Bearing Left Outside
        return SensorBearingLeftOutsideColumns
      case "BLR":
        // Bearing Left Retainer
        return SensorBearingLeftRetainerColumns
      case "BRB":
        // Bearing Right Ball
        return SensorBearingRightBallColumns
      case "BRI":
        // Bearing Right Inside
        return SensorBearingRightInsideColumns
      case "BRO":
        // Bearing Right Outside
        return SensorBearingRightOutsideColumns
      case "BRR":
        // Bearing Right Retainer
        return SensorBearingRightRetainerColumns
      case "WL":
        // Wheel Left
        return SensorWheelLeftColumns
      case "WR":
        // Wheel Right
        return SensorWheelRightColumns
      case "G":
        // Gearbox
        return SensorGearboxColumns
      case "E":
        // Engine
        return SensorEngineColumns
    }
  }

  function handleObjectKey(data: any) {
    data.forEach(((eachMap: any) => Object.keys(eachMap).forEach(function (eachKey: string) {
      // delete not needed
      notNeededColumnsForAnother.map((el: string) => {
        if (eachKey.includes(el)) {
          delete eachMap[eachKey]
        }
      })
    })))

    var userKey = Object.keys(data[0]).filter(el => {
      if (el.includes("user_")) return true
    })
    var targetColumnName = userKey[0].split('_')[1]
    var newAiKey = "AI_" + targetColumnName

    const isUpperCase = (string: string) => /^[A-Z]*$/.test(string)

    data.forEach((el: any) => {
      el[newAiKey] = el[userKey[0]]
      delete el[userKey[0]]
      for (var i in el) {
        if (i.toString() == 'date') {
          el["DATE"] = el['date']
          delete el['date']
        } else if (!isUpperCase(i.charAt(0))) {
          var upperCaseKey = i.charAt(0).toUpperCase() + i.slice(1)
          el[upperCaseKey] = el[i];
          delete el[i];
        }
      }
    })

    return data
  }

  function handleSearchData(pageable?: Pageable) {
    if (selectedCar == undefined) {
      return []
    }
    setTableColumn(handleSearchTablesColumns(partType))
    //다운로드 데이터 조회
    if (partType === 'BLB') {
      databaseJudgementControllerApi?.getBearingLeftBallPredictedData(
        selectedCar,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
          setPredictedData(res.data.content || []);
          setPaginate(res.data.pageable);
          setTotalPage(res.data.totalPages || 1);
        }
      );
      databaseJudgementControllerApi?.getLeftBallUserLBSFData(
        selectedCar,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'BLI') {
      databaseJudgementControllerApi?.getBearingLeftInsidePredictedData(
        selectedCar,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getLeftInsideUserLBPFIData(
        selectedCar,
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
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getLeftOutsideUserLBPFOData(
        selectedCar,
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
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getLeftRetainerUserLFTFData(
        selectedCar,
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
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getRightBallUserRBSFData(
        selectedCar,
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
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getRightInsideUserRBPFIData(
        selectedCar,
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
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getRightOutsideUserRBPFOData(
        selectedCar,
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
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getRightRetainerUserRFTFData(
        selectedCar,
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
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getEngineUserEngineData(
        selectedCar,
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
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getGearboxUserGearData(
        selectedCar,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        var result = res.data.length == 0 ? [] : handleObjectKey(res.data)
        setJudgedData(result);
      });
    }
    if (partType === 'WL') {
      databaseJudgementControllerApi?.getWheelLeftPredictedData(
        selectedCar,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getLeftWheelUserLW(
        selectedCar,
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
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
        pageable?.pageNumber,
        pageable?.pageSize
      ).then((res) => {
        setPredictedData(res.data.content || []);
        setPaginate(res.data.pageable);
        setTotalPage(res.data.totalPages || 1);
      });
      databaseJudgementControllerApi?.getRightWheelUserRW(
        selectedCar,
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

  return (
    <Container className="p-0">
      <Section title="검색 조건 입력">
        <Row className="row">
          <Col xs={1} className="text-right">
            부품 선택
          </Col>
          <Col xs={2}>
            <Form.Select
              size="sm"
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
          <div>
            <Button type="button" onClick={() => {
              handleSearchData()
            }}>검색</Button>
          </div>
        </Row>
      </Section>
      <Section title="결과 조회">
        <Row>
          <Col xs={12}>
            <div className="overflow-auto" id="table">
              <Table
                columns={tableColumn || []}
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
                  handleSearchData(newPaginate)
                }}
              />
            </div>
            <div style={{float: 'right'}}>
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

