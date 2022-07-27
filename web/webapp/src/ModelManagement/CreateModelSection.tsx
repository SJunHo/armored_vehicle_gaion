import React, { useContext, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Spinner from "react-bootstrap/Spinner";
import {
  Controller,
  FormProvider,
  useForm,
  useFormContext,
} from "react-hook-form";
import { useTranslation } from "react-i18next";
import Select2 from "react-select";
import {RegressionResponse, RandomForestClassificationResponse} from "../api";
import { OpenApiContext } from "../api/OpenApiContext";
import { InputWrapper } from "../common/Common";
import { Section } from "../common/Section/Section";

import { CreateModelResult } from "./CreateModelResult";
import styles from "./styles.module.css";
import {DataInputSection} from "./CreateModel/DataInputSection";
import {LifeDataInputSection} from "./CreateModel/LifeDataInputSection";

const SPLIT_TRAIN_TEST_STRATEGIES = ["auto", "all", "sqrt", "log2", "onethird"];

export const CreateModelSection: React.FC<{ algorithmName: string }> = ({
  algorithmName,
}) => {
  const methods = useForm({
    defaultValues: { fraction: 80 },
  });
  const { register, handleSubmit, watch } = methods;
  const [isTraining, setIsTraining] = React.useState(false);

  const { t } = useTranslation();
  const { mlControllerApi } = useContext(OpenApiContext);
  const [result, setResult] = React.useState<RandomForestClassificationResponse>();
  const [result2, setResult2] = React.useState<RegressionResponse>();


  async function handleTrain(input: any) {
    setResult(undefined);
    setResult2(undefined);
    setIsTraining(true);
    console.log(algorithmName);
    let newResult;
    try {
      switch (algorithmName) {
        case "rfc": {
          newResult = await mlControllerApi?.trainRfc(input);
          break;
        }
        case "svm": {
          newResult = await mlControllerApi?.trainSVM(input);
          break;
        }
        case "lr": {
          newResult = await mlControllerApi?.trainLr(input);
          break;
        }

        case "mlp": {
          newResult = await mlControllerApi?.trainMLP(input);
          break;
        }
        case "kmean": {
          newResult = await mlControllerApi?.trainKmean(input);
          break;
        }
        case "if": {
          newResult = await mlControllerApi?.trainIsolationForest(input);
          break;
        }
        case "linear": {
          console.log("linear");
          newResult = await mlControllerApi?.trainLinearRegression(input);
          break;
        }
        case "lasso": {
          console.log("lasso");
          newResult = await mlControllerApi?.trainLassoRegression(input);
          break;
        }
      }
    } catch (e) {
      setIsTraining(false);
    }

    setResult(newResult?.data);
    setResult2(newResult?.data);
    setIsTraining(false);
  }

  useEffect(() => {
    methods.reset({
      fraction: 80,
      ...(algorithmName === "kmean"
        ? {
            numClusters: 3,
          }
        : {}),
    });
  }, [algorithmName, methods]);

  return (
    <div>
      <FormProvider {...methods}>
        <Form onSubmit={handleSubmit(handleTrain)}>
          <div className="d-flex gap-3">
            <DataInputSection algorithmName={algorithmName} />
            <Section
                className={styles.trainInputSection}
                title={"모델 파라미터 설정"}
                bottomTitle=""
            >
              <div className={styles.trainInputBody}>
                <InputWrapper title={t("ml.common.stt")}>
                  <Form.Range
                      {...register("fraction")}
                      step={10}
                      min={0}
                      max={100}
                  />
                  <span>
                    {t("ml.common.ratio")} {watch("fraction")}/
                    {100 - watch("fraction")}
                  </span>
                </InputWrapper>
                <AdditionalParams algorithmName={algorithmName} />
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
        <CreateModelResult algorithmName={algorithmName} result={result} result2={result2} />
      )}
    </div>
  );
};

const AdditionalParams: React.FC<{ algorithmName: string }> = ({
  algorithmName,
}) => {
  const { t } = useTranslation();
  const { register, control, setValue } = useFormContext();
  return (
    <div className={styles.trainInputBodyDetailSection}>
      {algorithmName === "rfc" && (
        <>
          <InputWrapper
            rowLayout
            labelWidth={6}
            className={styles.body2Input}
            title={t("ml.common.fss")}
          >
            <Controller
              defaultValue="auto"
              control={control}
              name="featureSubsetStrategy"
              render={({ field }) => (
                <Select2
                  isClearable
                  placeholder={t("ml.common.psop")}
                  options={SPLIT_TRAIN_TEST_STRATEGIES.map((strategy) => ({
                    value: strategy,
                    label: strategy,
                  }))}
                  value={
                    field.value
                      ? { label: field.value, value: field.value }
                      : undefined
                  }
                  onChange={(v) => setValue("featureSubsetStrategy", v?.value)}
                />
              )}
            />
          </InputWrapper>
          <InputWrapper
            rowLayout
            labelWidth={6}
            className={styles.body2Input}
            title={t("ml.common.imp")}
          >
            <Form.Control {...register("impurity", { value: "gini" })} />
          </InputWrapper>
          <InputWrapper
            rowLayout
            labelWidth={6}
            className={styles.body2Input}
            title={t("ml.common.mbin")}
          >
            <Form.Control
              type="number"
              {...register("maxBins", { valueAsNumber: true, value: 4 })}
            />
          </InputWrapper>
          <InputWrapper
            labelWidth={6}
            rowLayout
            className={styles.body2Input}
            title={t("ml.common.not")}
          >
            <Form.Control
              type="number"
              {...register("numTrees", { value: 4 })}
            />
          </InputWrapper>
          <InputWrapper
            labelWidth={6}
            rowLayout
            className={styles.body2Input}
            title={t("ml.common.mdof")}
          >
            <Form.Control
              {...register("maxDepths", { valueAsNumber: true, value: 4 })}
              type="number"
            />
          </InputWrapper>
        </>
      )}
      {algorithmName === "mlp" && (
        <>
          <InputWrapper
            rowLayout
            labelWidth={6}
            className={styles.body2Input}
            title={t("ml.common.bls")}
          >
            <Form.Control
              type="number"
              min="1"
              {...register("blockSize", { valueAsNumber: true, value: 128 })}
            />
          </InputWrapper>
          <InputWrapper
            rowLayout
            labelWidth={6}
            className={styles.body2Input}
            title={t("ml.common.maxit")}
          >
            <Form.Control
              type="number"
              min="1"
              {...register("maxIter", { valueAsNumber: true, value: 100 })}
            />
          </InputWrapper>
        </>
      )}
      {["svm", "lr"].includes(algorithmName) && (
        <InputWrapper
          rowLayout
          labelWidth={6}
          className={styles.body2Input}
          title={t("ml.common.maxit")}
        >
          <Form.Control
            type="number"
            min="1"
            {...register("maxIter", { valueAsNumber: true, value: 100 })}
          />
        </InputWrapper>
      )}
      {algorithmName === "lr" && (
        <>
          <InputWrapper
            rowLayout
            labelWidth={6}
            className={styles.body2Input}
            title={t("Reg Param")}
          >
            <Form.Control
              type="number"
              min="0"
              {...register("regParam", { valueAsNumber: true, value: 0 })}
            />
          </InputWrapper>
          <InputWrapper
            rowLayout
            labelWidth={6}
            className={styles.body2Input}
            title={t("Elastic Net Param")}
          >
            <Form.Control
              type="number"
              min="0"
              {...register("elasticNetParam", {
                valueAsNumber: true,
                value: 0,
              })}
            />
          </InputWrapper>
        </>
      )}
      {algorithmName === "kmean" && <KmeanSection />}
      {algorithmName === "if" && <IsolationForestSection />}
      {algorithmName === "linear" && <LinearRegression />}
      {algorithmName === "lasso" && <LinearRegression />}

      <InputWrapper
        labelWidth={6}
        className={styles.body2Input}
        rowLayout
        title={t("ml.common.sm")}
      >
        <Form.Control {...register("modelName", { value: "DefaultModel" })} />
      </InputWrapper>
    </div>
  );
};

export const PreprocessingSection: React.FC = () => {
  const { t } = useTranslation();
  const { register } = useFormContext();
  return (
    <>
      <Row>
        <InputWrapper
          className={`col-md-6`}
          rowLayout
          labelWidth={6}
          title={t("ml.common.fs")}
        >
          <Form.Check
            {...register("featureSelectionEnableFlg")}
            type="checkbox"
          />
        </InputWrapper>
      </Row>

      <div className={styles.trainInputBodyDetailSection}>
        <InputWrapper
          rowLayout
          labelWidth={6}
          className={styles.body2Input}
          title={t("ml.common.caof")}
        >
          <Form.Control
            {...register("numberFeatures", { valueAsNumber: true })}
            placeholder={t("ml.common.nf")}
            type="number"
            min="1"
          />
        </InputWrapper>
        <InputWrapper
          rowLayout
          labelWidth={6}
          className={styles.body2Input}
          title={t("ml.common.dv")}
        >
          <Form.Control
            placeholder={t("ml.common.sb")}
            type="number"
            min="1"
            {...register("bin", { valueAsNumber: true })}
          />
        </InputWrapper>
      </div>
    </>
  );
};

export const KmeanSection: React.FC = () => {
  const { t } = useTranslation();

  const { register } = useFormContext();
  return (
    <>
      <InputWrapper
        rowLayout
        labelWidth={6}
        className={styles.body2Input}
        title={t("ml.clustering.numClusters")}
      >
        <Form.Control
          type="number"
          min="1"
          {...register("numClusters", { valueAsNumber: true, value: 4 })}
        />
      </InputWrapper>
      <InputWrapper
        rowLayout
        labelWidth={6}
        className={styles.body2Input}
        title={t("ml.clustering.numIters")}
      >
        <Form.Control
          type="number"
          {...register("numIters", { valueAsNumber: true, value: 100 })}
        />
      </InputWrapper>
    </>
  );
};

export const IsolationForestSection: React.FC = () => {
  const { t } = useTranslation();

  const { register } = useFormContext();
  return (
    <>
      <InputWrapper
        rowLayout
        labelWidth={6}
        className={styles.body2Input}
        title={t("ml.clustering.numClusters")}
      >
        <Form.Control
          type="number"
          min="0.0"
          {...register("numClusters", { valueAsNumber: true, value: 4 })}
        />
      </InputWrapper>
      <InputWrapper
        rowLayout
        labelWidth={6}
        className={styles.body2Input}
        title={t("ml.clustering.maxFeatures")}
      >
        <Form.Control
          type="number"
          min="0.0"
          {...register("maxFeatures", { valueAsNumber: true, value: 1 })}
        />
      </InputWrapper>
      <InputWrapper
        rowLayout
        labelWidth={6}
        className={styles.body2Input}
        title={t("ml.clustering.bootstrap")}
      >
        <Form.Check {...register("bootstrap")} />
      </InputWrapper>
    </>
  );
};

export const LinearRegression: React.FC = () => {
  const { t } = useTranslation();

  const { register } = useFormContext();
  return (
    <>
      <InputWrapper
        rowLayout
        labelWidth={6}
        className={styles.body2Input}
        title={t("ml.regression.maxIter")}
      >
        <Form.Control
          type="number"
          {...register("maxIter", { valueAsNumber: true, value: 100 })}
          placeholder={"default iteration 100"}
        />
      </InputWrapper>
      <InputWrapper
        rowLayout
        labelWidth={6}
        className={styles.body2Input}
        title={t("ml.regression.regParams")}
      >
        <Form.Control
          type="number"
          step="0.01"
          {...register("regParams", { valueAsNumber: true, value: 0.01 }, )}
          placeholder={"default value 0.01"}
        />
      </InputWrapper>
    </>
  );
};

