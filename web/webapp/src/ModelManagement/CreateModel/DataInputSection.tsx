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
    //Todo 베어링 말고도 추가해야됨
    switch (selectedPart) {
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
    }
  }

  function findFeatureCols(selectedPart: any) {
    //Todo 베어링 말고도 추가해야됨
    switch (selectedPart) {
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
                options={partTypes.map((d) => ({
                  value: d.value,
                  label: d.label,
                }))}
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
