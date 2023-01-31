import React, {useContext, useEffect, useState} from "react";
import Button from "react-bootstrap/Button";
import {Controller, useFormContext} from "react-hook-form";
import {useTranslation} from "react-i18next";
import Select2 from "react-select";
import {OpenApiContext} from "../../api/OpenApiContext";
import {ALGORITHM_INFO, InputWrapper} from "../../common/Common";
import {Section} from "../../common/Section/Section";
import styles from "../styles.module.css";

type Props = {
  algorithmName: string;
};

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

const lifePartTypes = [
  {
    value: "B_LIFE",
    label: "[잔존수명] 베어링",
  },
  {
    value: "W_LIFE",
    label: "[잔존수명] 휠",
  },
  {
    value: "G_LIFE",
    label: "[잔존수명] 감속기(기어박스)",
  },
  {
    value: "E_LIFE",
    label: "[잔존수명] 엔진",
  }
]

export const DataInputSection: React.FC<Props> = ({algorithmName}) => {

  const [indices, setIndices] = React.useState<string[]>([]);
  const [columns, setColumns] = React.useState<string[]>([]);
  const [fileName, setFileName] = React.useState<string>("");
  const {t} = useTranslation();
  const {control, watch, setValue} = useFormContext();

  const {mlControllerApi} = useContext(OpenApiContext);
  const classCol = watch("classCol");
  const selectedIndice = watch("fileName");
  const [selectedPart, setSelectedPart] = useState("");

  useEffect(() => {
    if (selectedPart && mlControllerApi) {
      mlControllerApi?.getTrainingDataList(selectedPart).then((res) => {
        setIndices(res.data);
      });
    }
  }, [mlControllerApi, selectedPart]);

  useEffect(() => {
    if (selectedIndice && mlControllerApi) {
      mlControllerApi?.getTrainingDataColumnList(selectedIndice).then((res) => {
        setColumns(res.data);
      });
    }
  }, [mlControllerApi, selectedIndice]);

  function CustomSelect(props: any) {
    return (
      <>
        <style type="text/css">
          {`.table_style table td {
            font-size: 11px;
            color: #cfdee7;
            padding: 7px 10px;
            text-align: center;
            border-bottom: 1px solid #1e313d;
            /*background: #325165;*/
          }`}
        </style>

        <Select2 {...props}/>
      </>
    );
  }

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

  function findFeatureCols(selectedPart: any) {
    switch (selectedPart) {
      // bearing
      case "BLB":
        return ["W_RPM", "L_B_V_1X", "L_B_V_6912BSF", "L_B_V_32924BSF", "L_B_V_32922BSF", "L_B_V_Crestfactor", "L_B_V_Demodulation",
          "L_B_S_Fault1", "L_B_S_Fault2", "L_B_T_Temperature", "AC_h", "AC_v", "AC_a"]
      case "BLO" :
        return ["W_RPM", "L_B_V_1X", "L_B_V_6912BPFO", "L_B_V_32924BPFO", "L_B_V_32922BPFO", "L_B_V_Crestfactor", "L_B_V_Demodulation",
          "L_B_S_Fault1", "L_B_S_Fault2", "L_B_T_Temperature", "AC_h", "AC_v", "AC_a"]
      case "BLI" :
        return ["W_RPM", "L_B_V_1X", "L_B_V_6912BPFI", "L_B_V_32924BPFI", "L_B_V_32922BPFI", "L_B_V_Crestfactor", "L_B_V_Demodulation",
          "L_B_S_Fault1", "L_B_S_Fault2", "L_B_T_Temperature", "AC_h", "AC_v", "AC_a"]
      case "BLR" :
        return ["W_RPM", "L_B_V_1X", "L_B_V_6912FTF", "L_B_V_32924FTF", "L_B_V_32922FTF", "L_B_V_Crestfactor", "L_B_V_Demodulation",
          "L_B_S_Fault1", "L_B_S_Fault2", "L_B_T_Temperature", "AC_h", "AC_v", "AC_a"]
      case "BRB" :
        return ["W_RPM", "R_B_V_1X", "R_B_V_6912BSF", "R_B_V_32924BSF", "R_B_V_32922BSF", "R_B_V_Crestfactor", "R_B_V_Demodulation",
          "R_B_S_Fault1", "R_B_S_Fault2", "R_B_T_Temperature", "AC_h", "AC_v", "AC_a"]
      case "BRO" :
        return ["W_RPM", "R_B_V_1X", "R_B_V_6912BPFO", "R_B_V_32924BPFO", "R_B_V_32922BPFO", "R_B_V_Crestfactor", "R_B_V_Demodulation",
          "R_B_S_Fault1", "R_B_S_Fault2", "R_B_T_Temperature", "AC_h", "AC_v", "AC_a"]
      case "BRI" :
        return ["W_RPM", "R_B_V_1X", "R_B_V_6912BPFI", "R_B_V_32924BPFI", "R_B_V_32922BPFI", "R_B_V_Crestfactor", "R_B_V_Demodulation",
          "R_B_S_Fault1", "R_B_S_Fault2", "R_B_T_Temperature", "AC_h", "AC_v", "AC_a"]
      case "BRR" :
        return ["W_RPM", "R_B_V_1X", "R_B_V_6912FTF", "R_B_V_32924FTF", "R_B_V_32922FTF", "R_B_V_Crestfactor", "R_B_V_Demodulation",
          "R_B_S_Fault1", "R_B_S_Fault2", "R_B_T_Temperature", "AC_h", "AC_v", "AC_a"]
      // wheel
      case "WL" :
        return ["W_RPM", "L_W_V_2X", "L_W_V_3X", "L_W_S_Fault3", "AC_h", "AC_v", "AC_a"]
      case "WR" :
        return ["W_RPM", "R_W_V_2X", "R_W_V_3X", "R_W_S_Fault3", "AC_h", "AC_v", "AC_a"]
      // gearbox
      case "G" :
        return ["W_RPM", "G_V_OverallRMS", "G_V_Wheel1X", "G_V_Wheel2X", "G_V_Pinion1X", "G_V_Pinion2X", "G_V_GMF1X", "G_V_GMF2X", "AC_h", "AC_v", "AC_a"]
      // engine
      case "E" :
        return ["W_RPM", "E_V_OverallRMS", "E_V_1_2X", "E_V_1X", "E_V_Crestfactor", "AC_h", "AC_v", "AC_a"]
      // bearing remaining life
      case "B_LIFE":
        return ["B_OverallRMS", "B_1X", "B_6912BPFO", "B_6912BPFI", "B_6912BSF", "B_6912FTF",
          "B_32924BPFO", "B_32924BPFI", "B_32924BSF", "B_32924FTF", "B_32922BPFO", "B_32922BPFI", "B_32922BSF", "B_32922FTF",
          "B_CrestFactor", "B_Demodulation", "B_Fault1", "B_Fault2", "B_Temperature"]
      // wheel remaining life
      case "W_LIFE":
        return ["W_2X", "W_3X", "W_Fault3"]
      // gearbox remaining life
      case "G_LIFE":
        return ["G_OverallRMS", "G_Wheel1X", "G_Wheel2X", "G_Pinion1X", "G_Pinion2X", "G_Wheel1X", "G_Wheel2X"]
      // engine remaining life
      case "E_LIFE":
        return ["E_OverallRMS", "E_1_2X", "E_1X", "E_CrestFactor"]
    }
  }

  return (
    <Section
      className={styles.trainInputSection}
      title={"학습데이터 및 클래스 레이블, 특징 벡터 설정"}
      bottomTitle={ALGORITHM_INFO[algorithmName].name}
    >
      <div className={styles.trainInputBody}>
        <InputWrapper title={"부품 선택"}>
          <Controller
            name="partType"
            control={control}
            render={({field}) => (
              <Select2
                className="text-black"
                {...field}
                value={
                  field.value
                    ? {label: field.value, value: field.value}
                    : undefined
                }
                onChange={(v) => {
                  setValue("partType", v?.value)
                  setValue("classCol", findClassLabel(v?.value))
                  setSelectedPart(v?.value)
                }}
                options={algorithmName === "linear" || algorithmName === "lasso" ? (
                  lifePartTypes.map((d) => ({
                    value: d.value,
                    label: d.label,
                  }))
                ) : (
                  partTypes.map((d) => ({
                    value: d.value,
                    label: d.label,
                  }))
                )}
              />
            )}
          />
        </InputWrapper>
        <InputWrapper title="학습데이터 선택">
          <Controller
            name="fileName"
            control={control}
            render={({field}) => (
              <Select2
                {...field}
                value={
                  field.value
                    ? {label: field.value, value: field.value}
                    : undefined
                }
                onChange={(v) => {
                  setValue("fileName", v?.value)
                  setFileName(v?.value)
                }}
                options={indices.map((indice) => ({
                  value: indice,
                  label: indice,
                }))}
              />
            )}
          />
        </InputWrapper>
        <InputWrapper
          title={
            <div className="d-flex gap-3">
              <div className="">특징 벡터 :</div>
              <Button
                className="ml-1"
                size="sm"
                onClick={() =>
                  setValue(
                    "featureCols",
                    findFeatureCols(selectedPart),
                  )
                }
              >
                모두선택
              </Button>
              <Button
                className="ml-1"
                size="sm"
                onClick={() => setValue("featureCols", [])}
                variant="secondary"
              >
                모두삭제
              </Button>
            </div>
          }
        >
          <Controller
            name="featureCols"
            control={control}
            render={({field}) => (
              <Select2
                {...field}
                isMulti
                onChange={(v) =>
                  setValue(
                    "featureCols",
                    v.map((v) => v.value)
                  )
                }
                value={field.value?.map((v: string) => ({
                  label: v,
                  value: v,
                }))}
                options={findFeatureCols(selectedPart)?.map((col) => ({value: col, label: col}))}
              />
            )}
          />
        </InputWrapper>
      </div>
    </Section>
  );
};
