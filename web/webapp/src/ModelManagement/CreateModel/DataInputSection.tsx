import React, {useContext, useEffect, useState} from "react";
import Button from "react-bootstrap/Button";
import { Controller, useFormContext } from "react-hook-form";
import { useTranslation } from "react-i18next";
import Select2 from "react-select";
import { OpenApiContext } from "../../api/OpenApiContext";
import { ALGORITHM_INFO, InputWrapper } from "../../common/Common";
import { Section } from "../../common/Section/Section";
import styles from "../styles.module.css";

type Props = {
  algorithmName: string;
};

const partTypes = [
  {
    value: "B",
    label: "베어링",
  },
  {
    value: "W",
    label: "휠",
  },
  {
    value: "E",
    label: "엔진",
  },
  {
    value: "G",
    label: "기어박스",
  },
  {
    value: "T",
    label: "임시데이터",
  }
]

export const DataInputSection: React.FC<Props> = ({ algorithmName }) => {

  const [indices, setIndices] = React.useState<string[]>([]);
  const [columns, setColumns] = React.useState<string[]>([]);
  const { t } = useTranslation();
  const { control, watch, setValue } = useFormContext();

  const { mlControllerApi } = useContext(OpenApiContext);
  const classCol = watch("classCol");
  const selectedIndice = watch("fileName");
  const [selectedPart, setSelectedPart] = useState("");

  useEffect(() => {
    if(selectedPart && mlControllerApi){
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
            render={({ field }) => (
              <Select2
                className="text-black"
                {...field}
                value={
                  field.value
                    ? { label: field.value, value: field.value }
                    : undefined
                }
                onChange={(v) => {
                  setValue("partType", v?.value)
                  setSelectedPart(v?.value)
                  console.log(selectedPart)
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
            render={({ field }) => (
              <Select2
                {...field}
                value={
                  field.value
                    ? { label: field.value, value: field.value }
                    : undefined
                }
                onChange={(v) => setValue("fileName", v?.value)}
                options={indices.map((indice) => ({
                  value: indice,
                  label: indice,
                }))}
              />
            )}
          />
        </InputWrapper>
        <InputWrapper title="클래스 레이블 :">
          <Controller
            name="classCol"
            control={control}
            render={({ field }) => (
              <Select2
                {...field}
                value={
                  field.value
                    ? { label: field.value, value: field.value }
                    : undefined
                }
                onChange={(v) => setValue("classCol", v?.value)}
                options={columns.map((col) => ({
                  value: col,
                  label: col,
                }))}
              />
            )}
          />
        </InputWrapper>
        <InputWrapper
          title={
            <div className="d-flex gap-3">
              <div className="">특징 벡터 : </div>
              <Button
                className="ml-1"
                size="sm"
                onClick={() =>
                  setValue(
                    "featureCols",
                    columns.filter((col) => col !== classCol)
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
            render={({ field }) => (
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
                options={columns
                  .filter((col) => col !== classCol)
                  .map((col) => ({ value: col, label: col }))}
              />
            )}
          />
        </InputWrapper>
      </div>
    </Section>
  );
};
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