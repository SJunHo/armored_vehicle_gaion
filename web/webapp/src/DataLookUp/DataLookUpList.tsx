import { Table } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import { useTranslation } from "react-i18next";
import { DataLookUpNew } from "./DataLookUpComponent/DataLookupNew";
import { DataLookUpEdit } from "./DataLookUpComponent/DataLookUpEdit";
import { DataLookUpDelete } from "./DataLookUpComponent/DataLookUpDelete";
import React, { useContext, useEffect, useState } from "react";
import { DataLookup, OpenApiContext } from "../api";

export const DataLookUpList: React.FC<{ algorithName: string }> = () => {
  const { t } = useTranslation();
  const [dataLookups, setdataLookups] = useState<DataLookup[]>([]);
  const [editingRow, setEditingRow] = useState<DataLookup>();
  const [deletingRow, setDeletingRow] = useState<DataLookup>();
  const [reload, setReload] = useState(1);
  const { datasetControllerApi } = useContext(OpenApiContext);

  useEffect(() => {
    datasetControllerApi?.getAllDataLookups().then((data) => {
      setdataLookups(data.data);
    });
  }, [datasetControllerApi, reload]);

  return (
    <div className="p-4">
      <h2>{t("navbar.pp.dm")}</h2>
      <DataLookUpNew algorithName={""} />
      <Table>
        <thead>
          <tr>
            <th>{t("pp.dsm.dsn")}</th>
            <th>{t("pp.es.index")}</th>
            <th>Updated Time</th>
            <th>{t("pp.dsm.deli")}</th>
            <th>{t("pp.dsm.ilc")}</th>
            <th>{t("pp.dsm.edit")}</th>
            <th>{t("pp.dsm.delete")}</th>
          </tr>
        </thead>
        <tbody>
          {dataLookups.map((dataLookup) => {
            const a =
              dataLookup.updatedTime !== undefined
                ? new Date(dataLookup.updatedTime)
                : undefined;
            return (
              <tr>
                <td>{dataLookup.lookupName}</td>
                <td>{dataLookup.index}</td>
                <td>
                  {
                    (dataLookup.updatedTime =
                      a !== undefined
                        ? a.toLocaleDateString("en-CA") +
                          " " +
                          a.toLocaleTimeString()
                        : undefined)
                  }
                </td>
                <td>{dataLookup.delimiter}</td>
                <td>{dataLookup.indexOfLabeledField}</td>
                <td>
                  <Button
                    variant="primary"
                    onClick={() => setEditingRow(dataLookup)}
                    size="sm"
                  >
                    Edit
                  </Button>
                </td>
                <td>
                  <Button
                    variant="primary"
                    onClick={() => setDeletingRow(dataLookup)}
                    size="sm"
                  >
                    Delete
                  </Button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </Table>
      <DataLookUpDelete
        onDelete={() => {
          datasetControllerApi
            ?.deleteDataLookup(deletingRow?.lookupName!)
            .then(() => setReload((old) => old + 1));
          setDeletingRow(undefined);
        }}
        visible={deletingRow !== undefined}
        onCancel={() => setDeletingRow(undefined)}
      />
      <DataLookUpEdit
        selectedData={editingRow}
        visible={editingRow !== undefined}
        onChangeVisible={(v) => {
          if (!v) {
            setEditingRow(undefined);
          }
        }}
        onUpdate={(v) => {
          datasetControllerApi
            ?.updateDataLookup(v)
            .then((data) =>
              setdataLookups((old) =>
                old.map((oldData) =>
                  oldData.lookupName === data.data.lookupName
                    ? data.data
                    : oldData
                )
              )
            )
            .then(() => setReload((old) => old + 1));
          setEditingRow(undefined);
        }}
      />
    </div>
  );
};
