import React, {
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
} from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import { useTranslation } from "react-i18next";
import { CellProps, Column, Row as TableRow } from "react-table";
import { ModelResponse, OpenApiContext, UpdateModelInput } from "../api";
import { ALGORITHM_INFO } from "../common/Common";
import { Section } from "../common/Section/Section";
import { Table } from "../common/Table";

export const TrainingModelList: React.FC<{ algorithmName: string }> = ({
  algorithmName,
}) => {
  const [models, setModels] = useState<ModelResponse[]>([]);
  const [selectedModels, setSelectedModels] = useState<ModelResponse[]>([]);
  const { mlControllerApi } = useContext(OpenApiContext);
  console.log(models);

  const { t } = useTranslation();

  useEffect(() => {
    mlControllerApi
      ?.getModels(ALGORITHM_INFO[algorithmName].className)
      .then(data => {
        setModels(data.data);
      });
  }, [mlControllerApi, algorithmName]);

  const handleRowSelected = useCallback((rows: TableRow<ModelResponse>[]) => {
    setSelectedModels(rows.map(row => row.original));
  }, []);

  const handleUpdateRows = useCallback(
    (row: ModelResponse, v: UpdateModelInput) => {
      mlControllerApi
        ?.updateModel(ALGORITHM_INFO[algorithmName].className, row.esId!, v)
        .then(res => {
          setModels(old =>
            old.map(oldModel => {
              if (oldModel.esId === res.data.esId) {
                return res.data;
              }
              return oldModel;
            })
          );
        });
    },
    [mlControllerApi, algorithmName]
  );

  const columns = useMemo<Column<ModelResponse>[]>(
    () => [
      {
        Header: "Model Name",
        accessor: "modelName",
      },
      {
        Header: "Weighted F-measure",
        accessor: item => item.response?.weightedFMeasure,
      },
      {
        Header: "Weighted False Positive Rate",
        accessor: item => item.response?.weightedFalsePositiveRate,
      },
      {
        Header: "Weighted Precision",
        accessor: item => item.response?.weightedPrecision,
      },
      {
        Header: "Weighted Recall",
        accessor: item => item.response?.weightedRecall,
      },
      {
        Header: "Weighted True Positive Rate",
        accessor: item => item.response?.weightedTruePositiveRate,
      },
      {
        Header: t("table.column.notes").toString(),
        accessor: "description",
        Cell: EditableCell,
        onChange: (row: ModelResponse, v: string) =>
          handleUpdateRows(row, { description: v, checked: row.checked }),
      },
      {
        Header: t("table.column.register").toString(),
        accessor: "checked",
        Cell: EditableCheckboxCell,
        onChange: (row: ModelResponse, v: boolean) =>
          handleUpdateRows(row, { description: row.description, checked: v }),
      },
    ],
    [t, handleUpdateRows]
  );
  return (
    <Section title={ALGORITHM_INFO[algorithmName].name}>
      <Table
        onRowsSelected={handleRowSelected}
        columns={columns}
        data={models}
      />
      <Row className="d-flex justify-content-between">
        <Col md={2}>
          <Button
            variant="danger"
            onClick={async () => {
              await Promise.all(
                selectedModels.map(selectedModel =>
                  mlControllerApi?.deleteModel(
                    ALGORITHM_INFO[algorithmName].className,
                    selectedModel.esId!
                  )
                )
              ).then(() => {
                setModels(old =>
                  old.filter(
                    oldModel =>
                      !selectedModels.find(sm => sm.esId === oldModel.esId)
                  )
                );
                setSelectedModels([]);
              });
            }}
          >
            {t("ml.common.btn.dm")}
          </Button>
        </Col>
        <Col md={1}>
          <Button>{t("pp.fu.id.btn.save")}</Button>
        </Col>
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
      value={value}
      onChange={e => {
        setValue(e.target.value);
      }}
      onBlur={onBlur}
    />
  );
}
