import React, {useEffect, useState} from "react";
import Pagination from "react-bootstrap/Pagination";
import "./common.module.css";

type PaginatorType = {
  pageCount: number;
  size?: number;
  selectedPage?: number;
  onChange?: (index: number) => any;
};

export const Paginator: React.FC<PaginatorType> = ({
                                                     pageCount,
                                                     onChange,
                                                     selectedPage = 0,
                                                   }) => {
  const numberOfPage = pageCount;
  const [internalSelectedPage, setInternalSelectedPage] =
    useState(selectedPage);

  useEffect(() => {
    setInternalSelectedPage(selectedPage);
  }, [selectedPage]);

  const handleChangePage = (index: number) => {
    onChange ? onChange(index) : setInternalSelectedPage(index);
  };

  const firstBlockCount =
    numberOfPage <= 10
      ? numberOfPage
      : selectedPage < 5 && selectedPage >= 2
        ? 6
        : 3;
  const lastBlockCount =
    numberOfPage <= 10
      ? 0
      : selectedPage < numberOfPage - 2 && selectedPage > numberOfPage - 6
        ? 6
        : 3;
  return (
    <Pagination id="paginator">
      <Pagination.Prev
        onClick={() => {
          if (internalSelectedPage > 0) {
            handleChangePage(internalSelectedPage - 1);
          }
        }}
      />
      {new Array(firstBlockCount).fill(0).map((_, index) => (
        <Pagination.Item
          key={index + 1}
          onClick={() => {
            handleChangePage(index);
          }}
        >
          {index + 1}
        </Pagination.Item>
      ))}
      {numberOfPage > 10 &&
        selectedPage > 4 &&
        selectedPage < numberOfPage - 5 && (
          <>
            <Pagination.Ellipsis/>
            {new Array(5).fill(0).map((_, index) => (
              <Pagination.Item
                key={index + selectedPage}
                onClick={() => {
                  handleChangePage(index + selectedPage - 2);
                }}
              >
                {index + selectedPage - 1}
              </Pagination.Item>
            ))}
          </>
        )}
      {numberOfPage > 10 && (
        <>
          <Pagination.Ellipsis/>
          {new Array(lastBlockCount).fill(0).map((_, index) => (
            <Pagination.Item
              key={numberOfPage - lastBlockCount + index}
              onClick={() => {
                handleChangePage(numberOfPage - lastBlockCount + index);
              }}
            >
              {numberOfPage - lastBlockCount + index + 1}
            </Pagination.Item>
          ))}
        </>
      )}
      <Pagination.Next
        onClick={() => {
          if (internalSelectedPage < numberOfPage - 1) {
            handleChangePage(internalSelectedPage + 1);
          }
        }}
      />
    </Pagination>
  );
};
