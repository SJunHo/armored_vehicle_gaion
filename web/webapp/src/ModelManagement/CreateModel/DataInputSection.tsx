import React, { useContext, useEffect } from "react";
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

export const DataInputSection: React.FC<Props> = ({ algorithmName }) => {
  const [indices, setIndices] = React.useState<string[]>([]);
  const [columns, setColumns] = React.useState<string[]>([]);
  const { t } = useTranslation();
  const { control, watch, setValue } = useFormContext();

  const { mlControllerApi } = useContext(OpenApiContext);
  const classCol = watch("classCol");
  const selectedIndice = watch("trainingESIndex");

  useEffect(() => {
    mlControllerApi?.getEsDatasetIndicesList().then((res) => {
      setIndices(res.data);
    });
  }, [mlControllerApi]);

  useEffect(() => {
    if (selectedIndice && mlControllerApi) {
      mlControllerApi?.getIndexMappings(selectedIndice).then((res) => {
        setColumns(res.data);
      });
    }
  }, [mlControllerApi, selectedIndice]);
  console.log(ALGORITHM_INFO, algorithmName);

  return (
    <Section
      className={styles.trainInputSection}
      title={"학습데이터 및 클래스 레이블, 특징 벡터 설정"}
      bottomTitle={ALGORITHM_INFO[algorithmName].name}
    >
      <div className={styles.trainInputBody}>
        <InputWrapper title={t("ml.common.td")}>
          <Controller
            name="trainingESIndex"
            control={control}
            render={({ field }) => (
              <Select2
                {...field}
                value={
                  field.value
                    ? { label: field.value, value: field.value }
                    : undefined
                }
                onChange={(v) => setValue("trainingESIndex", v?.value)}
                options={indices.map((indice) => ({
                  value: indice,
                  label: indice,
                }))}
              />
            )}
          />
        </InputWrapper>
        <InputWrapper title={t("ml.common.cf")}>
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
              <div className="">{t("ml.common.ff")}</div>
              <Button
                size="sm"
                onClick={() =>
                  setValue(
                    "featureCols",
                    columns.filter((col) => col !== classCol)
                  )
                }
              >
                {t("ml.common.btn.sa")}
              </Button>
              <Button
                size="sm"
                onClick={() => setValue("featureCols", [])}
                variant="secondary"
              >
                {t("ml.common.btn.ca")}
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
