import React, { useContext, useMemo } from "react";
import { Page } from "../common/Page/Page";
import { useParams } from "react-router-dom";
import { FormProvider, useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { FSResponse, OpenApiContext } from "../api";
import Form from "react-bootstrap/Form";
import { DataInputSection } from "./CreateModel/DataInputSection";
import { Section } from "../common/Section/Section";
import styles from "./styles.module.css";
import { InputWrapper } from "../common/Common";
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import Card from "react-bootstrap/Card";
import { Table } from "../common/Table";
import { Column } from "react-table";

export const FeatureSelection: React.FC = () => {
  const { algorithmName } = useParams<{ algorithmName: string }>();
  const methods = useForm({
    defaultValues: { bin: 16, numberPrincipalComponents: 8 },
  });
  const { register, handleSubmit } = methods;
  const [isTraining, setIsTraining] = React.useState(false);

  const { t } = useTranslation();
  const { mlControllerApi } = useContext(OpenApiContext);
  const [result, setResult] = React.useState<FSResponse>();

  console.log(result);

  async function handleTrain(input: any) {
    console.log(input, algorithmName);
    setIsTraining(true);
    if (algorithmName === "chisq") {
      mlControllerApi
        ?.chiSquareFS(input)
        .then((res) => {
          setResult(res.data);
        })
        .finally(() => setIsTraining(false));
    } else {
      // pca
      mlControllerApi
        ?.pcaDimensionalityReduction(input)
        .then((res) => {
          setResult(res.data);
        })
        .finally(() => setIsTraining(false));
    }
  }

  const columns = useMemo<Column<any>[]>(
    () => [
      {
        Header: "Class",
        accessor: "0",
      },
      ...(result?.selectedFields || []).map((field, index) => ({
        Header: field,
        accessor: (index + 1).toString(),
      })),
    ],
    [result]
  );

  const handleDownloadCSV = () => {
    if (result?.csv) {
      const hidEl = document.createElement("a");
      hidEl.href = "data:text/csv;charset=utf-8," + encodeURI(result.csv);
      hidEl.target = "_blank";
      hidEl.download = "result.csv";
      hidEl.click();
    }
  };

  return (
    <Page>
      <FormProvider {...methods}>
        <Form onSubmit={handleSubmit(handleTrain)}>
          <div className="d-flex gap-3">
            <DataInputSection algorithmName={algorithmName} />
            <Section
              className={styles.trainInputSection}
              title={""}
              bottomTitle=""
            >
              <div className={styles.trainInputBody}>
                {algorithmName === "chisq" && (
                  <InputWrapper
                    rowLayout
                    labelWidth={6}
                    className={styles.body2Input}
                    title={t("ml.common.sb")}
                  >
                    <Form.Control
                      {...register("bin", { valueAsNumber: true })}
                    />
                  </InputWrapper>
                )}
                {algorithmName === "pca" && (
                  <InputWrapper
                    rowLayout
                    labelWidth={6}
                    className={styles.body2Input}
                    title={t("ml.common.npc")}
                  >
                    <Form.Control
                      {...register("numberPrincipalComponents", {
                        valueAsNumber: true,
                      })}
                    />
                  </InputWrapper>
                )}
              </div>
            </Section>
          </div>

          <div className="d-flex flex-row-reverse mt-3 pe-0">
            <Button disabled={isTraining} type="submit">
              {isTraining && (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              )}
              {t("ml.predict.train")}
            </Button>
          </div>
        </Form>
      </FormProvider>
      {result && (
        <>
          <Card className="mt-3">
            <Card.Header>
              <strong>{t("ml.common.mei")}</strong>
            </Card.Header>
            <Card.Body className="d-grid gap-3 container-fluid">
              {result.filteredFeatures && (
                <Table
                  data={result.filteredFeatures.map((ff) => ff.split(","))}
                  columns={columns}
                  paginationOptions={{ pageSize: 20, pageIndex: 0 }}
                />
              )}
            </Card.Body>
          </Card>
          <div className="d-flex flex-row-reverse mt-3 pe-0">
            <Button type="button" onClick={handleDownloadCSV}>
              {t("btn.fulldownload")}
            </Button>
          </div>
        </>
      )}
    </Page>
  );
};
