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
import {Page} from "../common/Page/Page";
import {CSVLink} from "react-csv";
import {
  SensorBearingLeftBallInput,
  SensorBearingLeftOutsideInput,
  SensorBearingLeftInsideInput,
  SensorBearingLeftRetainerInput
  ,
  SensorBearingRightBallInput,
  SensorBearingRightInsideInput,
  SensorBearingRightOutsideInput,
  SensorBearingRightRetainerInput,
  SensorEngineInput,
  SensorGearboxInput,
  SensorWheelLeftInput,
  SensorWheelRightInput
} from "./tableColumns";
import {CSVDownload} from "react-csv";

export const JudgementLookup: React.FC = () => {
  const [partType, setPartType] = useState<string>("BLB");
  const [carsList, setCarsList] = useState<string[]>([]);
  const [selectedCar, setSelectedCar] = useState<string>();

  const [fromDate, setFromDate] = useState<Date>();
  const [toDate, setToDate] = useState<Date>(new Date());

  const [paginate, setPaginate] = useState<Pageable>();
  const [totalPage, setTotalPage] = useState<number>();

  const [tableColumn, setTableColumn] = useState<any>();
  const [predictedData, setPredictedData] = useState<any[]>([]);

  const [judgedData, setJudgedData] = useState<any[]>([]);

  const {datasetControllerApi, databaseJudgementControllerApi} = useContext(OpenApiContext);

  const SensorBearingLeftBallColumns = useMemo<Column<SensorBearingLeftBallInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_LBSF"
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
        Header: "수행시간",
        accessor: "ai_LBSF_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_LBSF",
      },
      {
        Header: "작업자 ID",
        accessor: "user_LBSF_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_LBSF_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingLeftInsideColumns = useMemo<Column<SensorBearingLeftInsideInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_LBPFI",
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
        Header: "수행시간",
        accessor: "ai_LBPFI_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_LBPFI",
      },
      {
        Header: "작업자 ID",
        accessor: "user_LBPFI_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_LBPFI_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingLeftOutsideColumns = useMemo<Column<SensorBearingLeftOutsideInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_LBPFO",
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
        Header: "수행시간",
        accessor: "ai_LBPFO_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_LBPFO",
      },
      {
        Header: "작업자 ID",
        accessor: "user_LBPFO_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_LBPFO_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingLeftRetainerColumns = useMemo<Column<SensorBearingLeftRetainerInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_LFTF",
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
        Header: "수행시간",
        accessor: "ai_LFTF_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_LFTF",
      },
      {
        Header: "작업자 ID",
        accessor: "user_LFTF_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_LFTF_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingRightBallColumns = useMemo<Column<SensorBearingRightBallInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_RBSF",
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
        Header: "수행시간",
        accessor: "ai_RBSF_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_RBSF",
      },
      {
        Header: "작업자 ID",
        accessor: "user_RBSF_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_RBSF_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingRightInsideColumns = useMemo<Column<SensorBearingRightInsideInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_RBPFI",
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
        Header: "수행시간",
        accessor: "ai_RBPFI_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_RBPFI",
      },
      {
        Header: "작업자 ID",
        accessor: "user_RBPFI_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_RBPFI_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingRightOutsideColumns = useMemo<Column<SensorBearingRightOutsideInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_RBPFO",
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
        Header: "수행시간",
        accessor: "ai_RBPFO_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_RBPFO",
      },
      {
        Header: "작업자 ID",
        accessor: "user_RBPFO_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_RBPFO_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingRightRetainerColumns = useMemo<Column<SensorBearingRightRetainerInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_RFTF",
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
        Header: "수행시간",
        accessor: "ai_RFTF_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_RFTF",
      },
      {
        Header: "작업자 ID",
        accessor: "user_RFTF_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_RFTF_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorWheelLeftColumns = useMemo<Column<SensorWheelLeftInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_LW",
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
        Header: "수행시간",
        accessor: "ai_LW_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_LW",
      },
      {
        Header: "작업자 ID",
        accessor: "user_LW_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_LW_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorWheelRightColumns = useMemo<Column<SensorWheelRightInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_RW",
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
        Header: "수행시간",
        accessor: "ai_RW_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_RW",
      },
      {
        Header: "작업자 ID",
        accessor: "user_RW_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_RW_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorGearboxColumns = useMemo<Column<SensorGearboxInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_GEAR",
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
        Header: "수행시간",
        accessor: "ai_GEAR_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_GEAR",
      },
      {
        Header: "작업자 ID",
        accessor: "user_GEAR_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_GEAR_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorEngineColumns = useMemo<Column<SensorEngineInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과",
        accessor: "ai_ENGINE",
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
        Header: "수행시간",
        accessor: "ai_ENGINE_DATE",
      },
      {
        Header: "작업자 판정값",
        accessor: "user_ENGINE",
      },
      {
        Header: "작업자 ID",
        accessor: "user_ENGINE_ID",
      },
      {
        Header: "작업자 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.user_ENGINE_DATE).toLocaleString("ko-KR")
        }
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const partTypes = [
    // bearing
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
  }, [partType, datasetControllerApi]);

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

  function handleSearchData(pageable?: Pageable) {
    if (selectedCar == undefined) {
      return []
    }
    setTableColumn(handleSearchTablesColumns(partType))
    //다운로드 데이터 조회
    if (partType == 'BLB') {
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
      });
      databaseJudgementControllerApi?.getLeftBallUserLBSFData(
        selectedCar,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        res.data.forEach(((eachMap: any) => Object.keys(eachMap).forEach(function (eachKey: string) {
          if (eachKey.includes("ai")) {
            delete eachMap[eachKey];
          }
        })))
        setJudgedData(res.data);
      });
    }
    if (partType == 'BLI') {
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
        setJudgedData(res.data);
      });
    }
    if (partType == 'BLO') {
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
        setJudgedData(res.data);
      });
    }
    if (partType == 'BLR') {
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
        setJudgedData(res.data);
      });
    }
    if (partType == 'BRB') {
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
        setJudgedData(res.data);
      });
    }
    if (partType == 'BRI') {
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
        setJudgedData(res.data);
      });
    }
    if (partType == 'BRO') {
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
        setJudgedData(res.data);
      });
    }
    if (partType == 'BRR') {
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
        setJudgedData(res.data);
      });
    }

    if (partType == 'E') {
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
        setJudgedData(res.data);
      });
    }
    if (partType == 'G') {
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
        setJudgedData(res.data);
      });
    }
    if (partType == 'WL') {
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
        setJudgedData(res.data);
      });
    }
    if (partType == 'WR') {
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
        setJudgedData(res.data || []);
      });
    }
  }

  /*  function onClickDownloadButtonHandler() {
      // Download to data with judged defectUser values
      switch (partType) {
        case "BLB": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getLeftBallUserLBSFData(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "BLI": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getLeftInsideUserLBPFIData(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "BLO": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getLeftOutsideUserLBPFOData(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "BLR": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getLeftRetainerUserLFTFData(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "BRB": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getRightBallUserRBSFData(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "BRI": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getRightInsideUserRBPFIData(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "BRO": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getRightOutsideUserRBPFOData(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "BRR": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getRightRetainerUserRFTFData(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "WL": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getLeftWheelUserLW(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "WR": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getRightWheelUserRW(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data || []);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "G": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getGearboxUserGearData(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
        case "E": {
          if (selectedCar != null) {
            databaseJudgementControllerApi?.getEngineUserEngineData(
              selectedCar,
              fromDate?.toLocaleDateString("en-US"),
              toDate?.toLocaleDateString("en-US"),
            ).then((res) => {
              setJudgedData(res.data);
            });
          } else {
            alert("차량을 먼저 선택해주세요.")
            return null
          }
          break
        }
      }
    }*/

  return (
    <Page>
      <Container className="p-3 pt-5 w-100 d-inline">
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
                // handleSearchTablesColumns((v.target as any).value)
              }}
            >
              {partTypes.map((part) => (
                <option key={part.value} value={part.value}>{part.label}</option>
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
                <option key={car} value={car}>{car}</option>
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
            <Button type="button" onClick={() => {
              handleSearchData()
            }}>검색</Button>
          </Col>
        </Row>
        <Row className="d-inline-block" style={{width: "100%"}}>
          <div className="overflow-auto">
            {(totalPage) &&
							<Table
								columns={tableColumn}
								data={predictedData}
							/>
            }
          </div>
          <div style={{display: 'inline-block'}}>
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
              data={judgedData || []}
              filename={'CSV 데이터'}
              onClick={() => {
                console.log("링크 클릭함");
              }}
            >
              Download me
            </CSVLink>;
          </div>
        </Row>
      </Container>
    </Page>
  );
};



