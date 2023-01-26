import React, {useCallback, useContext, useEffect, useMemo, useState,} from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {CellProps, Column, Row as TableRow} from "react-table";
import {DbModelResponse, OpenApiContext, UpdateModelInput} from "../api";
import {ALGORITHM_INFO} from "../common/Common";
import {Section} from "../common/Section/Section";
import {Table} from "../common/Table";

export const TrainingModelList: React.FC<{ algorithmName: string }> = ({
                                                                         algorithmName,
                                                                       }) => {
  const [models, setModels] = useState<DbModelResponse[]>([]);
  const [selectedModels, setSelectedModels] = useState<DbModelResponse[]>([]);
  const {mlControllerApi} = useContext(OpenApiContext);
  console.log("bbb");
  console.log(algorithmName)

  useEffect(() => {
    mlControllerApi
      ?.getModels(ALGORITHM_INFO[algorithmName].className)
      .then(data => {
        setModels(data.data);
      });
  }, [mlControllerApi, algorithmName]);

  const handleRowSelected = useCallback((rows: TableRow<DbModelResponse>[]) => {
    setSelectedModels(rows.map(row => row.original));
  }, []);

  const handleUpdateRows = useCallback(
    (row: DbModelResponse, v: UpdateModelInput) => {
      mlControllerApi
        ?.updateModel(ALGORITHM_INFO[algorithmName].className, row.algorithmResponseId!, v)
        .then(res => {
          setModels(old =>
            old.map(oldModel => {
              if (oldModel.algorithmResponseId === res.data.algorithmResponseId) {
                return res.data;
              }
              return oldModel;
            })
          );
        });
    },
    [mlControllerApi, algorithmName]
  );

  const classificationColumns = useMemo<Column<DbModelResponse>[]>(
    () => [
      {
        Header: "Model Name",
        accessor: "modelName",
      },
      {
        Header: "Weighted F-measure",
        accessor: item => item.weightedFMeasure,
      },
      {
        Header: "Weighted False Positive Rate",
        accessor: item => item.weightedFalsePositiveRate,
      },
      {
        Header: "Weighted Precision",
        accessor: item => item.weightedPrecision,
      },
      {
        Header: "Weighted Recall",
        accessor: item => item.weightedRecall,
      },
      {
        Header: "Weighted True Positive Rate",
        accessor: item => item.weightedTruePositiveRate,
      },
      {
        Header: "설명",
        accessor: "description",
        Cell: EditableCell,
        onChange: (row: DbModelResponse, v: string) => handleUpdateRows(row, {description: v, checked: row.checked}),
      },
      {
        // eslint-disable-next-line no-useless-concat
        Header: "선택 (자동저장)",
        accessor: "checked",
        Cell: EditableCheckboxCell,
        onChange: (row: DbModelResponse, v: boolean) =>
          handleUpdateRows(row, {description: row.description, checked: v}),
      },
    ],
    [handleUpdateRows]
  );

  const regressionColumns = useMemo<Column<DbModelResponse>[]>(
    () => [
      {
        Header: "Model Name",
        accessor: "modelName",
      },
      {
        Header: "R-squared",
        accessor: item => item.r2,
      },
      {
        Header: "Root Mean Squared Error",
        accessor: item => item.rootMeanSquaredError,
      },
      {
        Header: "설명",
        accessor: "description",
        Cell: EditableCell,
        onChange: (row: DbModelResponse, v: string) => handleUpdateRows(row, {description: v, checked: row.checked}),
      },
      {
        // eslint-disable-next-line no-useless-concat
        Header: "선택 (자동저장)",
        accessor: "checked",
        Cell: EditableCheckboxCell,
        onChange: (row: DbModelResponse, v: boolean) =>
          handleUpdateRows(row, {description: row.description, checked: v}),
      },
    ],
    [handleUpdateRows]
  );

  return (
    <Section title={ALGORITHM_INFO[algorithmName].name}>
      {algorithmName === "linear" || algorithmName === "lasso" ? (
        <Table
          onRowsSelected={handleRowSelected}
          columns={regressionColumns}
          data={models}/>
      ) : (
        <Table
          onRowsSelected={handleRowSelected}
          columns={classificationColumns}
          data={models}
        />
      )}
      <Row className="d-flex justify-content-between">
        <Col md={2}>
          <Button
            variant="danger"
            onClick={async () => {
              await Promise.all(
                selectedModels.map(selectedModel =>
                  mlControllerApi?.deleteModel(ALGORITHM_INFO[algorithmName].className, selectedModel.algorithmResponseId!))
              ).then(() => {
                setModels(old => old.filter(oldModel => !selectedModels.find(sm => sm.algorithmResponseId === oldModel.algorithmResponseId)));
                setSelectedModels([]);
              });
            }}
          >
            삭제
          </Button>
        </Col>
        {/*<Col md={1}>*/}
        {/*  <Button>저장</Button>*/}
        {/*</Col>*/}
      </Row>
    </Section>
  );
};

function EditableCheckboxCell<T extends object>({
                                                  value: initialValue,
                                                  row,
                                                  // column: { id },
                                                  column, // This is a custom function that we supplied to our table instance
                                                }: CellProps<T, any>) {
  console.log(initialValue);
  return (
    <Form.Check
      checked={initialValue}
      onChange={v =>
        // @ts-ignore
        column.onChange(row.original, v.target.checked)
      }
    />
  );
}

// Create an editable cell renderer
function EditableCell<T extends object>({
                                          value: initialValue,
                                          row,
                                          // column: { id },
                                          column, // This is a custom function that we supplied to our table instance
                                        }: CellProps<T, any>) {
  // We need to keep and update the state of the cell normally
  const [value, setValue] = React.useState(initialValue);

  // We'll only update the external data when the input is blurred
  const onBlur = () => {
    // @ts-ignore
    column.onChange(row.original, value);
  };

  // If the initialValue is changed external, sync it up with our state
  React.useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  return (
    <input
      value={value || ""}
      onChange={e => {
        setValue(e.target.value);
      }}
      onBlur={onBlur}
    />
  );
}
