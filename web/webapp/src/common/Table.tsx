import React, { useEffect, useRef } from "react";
import BootstrapTable from "react-bootstrap/Table";
import {
  Column,
  Row,
  usePagination,
  UsePaginationOptions,
  UsePaginationState,
  useRowSelect,
  UseRowSelectOptions,
  useTable,
} from "react-table";
import { Paginator } from "./Paginator";
// import styles from "./table.module.css";

type TableProps<T extends Object> = {
  columns: Column<T>[];
  data: T[];
  onRowClick?: (row: Row<T>) => any;
  onRowsSelected?: (rows: Row<T>[]) => any;
  isSingleRowSelect?: boolean;
  total?: number;
  paginationOptions?: UsePaginationState<T>;
  onChangePage?: (pageSize: number, pageIndex: number) => any;
} & UsePaginationOptions<T> &
  UseRowSelectOptions<T>;

const IndeterminateCheckbox = React.forwardRef<{}, { indeterminate?: boolean }>(
  ({ indeterminate, ...rest }, ref) => {
    const defaultRef = React.useRef();
    const resolvedRef =
      (ref as React.RefObject<HTMLInputElement>) || defaultRef;

    React.useEffect(() => {
      if (resolvedRef.current) {
        resolvedRef.current.indeterminate = indeterminate || false;
      }
    }, [resolvedRef, indeterminate]);

    return (
      <>
        <input
          className="w-auto m-1"
          type="checkbox"
          ref={resolvedRef}
          {...rest}
        />
      </>
    );
  }
);

export function Table<T extends Object>({
  data,
  total,
  columns,
  onRowClick,
  paginationOptions,
  onChangePage,
  onRowsSelected,
  isSingleRowSelect = false,
  ...props
}: TableProps<T>): React.ReactElement {
  const selectedRowsChangedKey = useRef<number>();
  const {
    pageCount,
    prepareRow,
    rows,
    getTableProps,
    headers,
    getTableBodyProps,
    page,
    state: { pageSize, pageIndex },
    gotoPage,
    setPageSize,
    selectedFlatRows,
  } = useTable<T>(
    {
      columns,
      data,
      initialState: paginationOptions,
      stateReducer: (newState, action) => {
        if (
          ["toggleRowSelected", "toggleAllRowsSelected"].includes(action.type)
        ) {
          selectedRowsChangedKey.current = Date.now();
          if (isSingleRowSelect) {
            // @ts-ignore
            newState.selectedRowIds = {
              [action.id]: action.value,
            };
          }
        }
        return newState;
      },
      ...props,
    },
    usePagination,
    useRowSelect,
    hooks => {
      if (onRowsSelected) {
        hooks.visibleColumns.push(columns => [
          // Let's make a column for selection
          {
            id: "selection",
            width: "20px",
            // The header can use the table's getToggleAllRowsSelectedProps method
            // to render a checkbox
            Header: ({ getToggleAllRowsSelectedProps }) => (
              <div>
                <IndeterminateCheckbox {...getToggleAllRowsSelectedProps()} />
              </div>
            ),
            // The cell can use the individual row's getToggleRowSelectedProps method
            // to the render a checkbox
            Cell: ({ row }: any) => (
              <div>
                <IndeterminateCheckbox {...row.getToggleRowSelectedProps()} />
              </div>
            ),
          },
          ...columns,
        ]);
      }
    }
  );

  useEffect(() => {
    onRowsSelected && onRowsSelected(selectedFlatRows);
  }, [selectedRowsChangedKey, selectedFlatRows, onRowsSelected]);

  useEffect(() => {
    if (paginationOptions?.pageSize) {
      setPageSize(paginationOptions.pageSize);
    }
  }, [paginationOptions, setPageSize]);

  return (
    <>
      <BootstrapTable
        striped
        className="table align-middle text-center overflow-visible"
        {...getTableProps()}
      >
        <thead className="bg-secondary text-light border-right-success">
          <tr className="container overflow-hidden" >
            {headers.map(header => (
              <th
                style={{ position: "sticky", top: 0 }}
                {...header.getHeaderProps()}
              >
                {header.render("Header")}
              </th>
            ))}
          </tr>
        </thead>

        <tbody {...getTableBodyProps()}>
          {(paginationOptions ? page : rows).map(row => {
            prepareRow(row);
            return (
              <tr
                {...row.getRowProps()}
                onClick={() => onRowClick && onRowClick(row)}
              >
                {row.cells.map(cell => (
                  <td {...cell.getCellProps()} >
                    {cell.render("Cell")}
                  </td>
                ))}
              </tr>
            );
          })}
        </tbody>
      </BootstrapTable>
      {paginationOptions && (
        <div>
          <Paginator
            pageCount={pageCount}
            size={pageSize}
            selectedPage={pageIndex}
            onChange={v => {
              gotoPage(v);
              onChangePage && onChangePage(paginationOptions.pageSize, v);
            }}
          />
        </div>
      )}
    </>
  );
}
